package com.example.e_farmer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.e_farmer.models.AnimalTreatment;
import com.example.e_farmer.models.Finance;
import com.example.e_farmer.models.User;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class FinanceActivity extends AppCompatActivity {

    private static final String TAG = "FinanceActivity";

    private Toolbar toolbar;
    private EditText itemName, itemCategory, transactionDate, itemAmount, itemQuantity, itemNotes;
    private Spinner paymentType, financeType;
    private Button save_finance_btn, savingBtn;
    private ProgressBar progressBar;
    private String name, category, transaction_date, item_amount, quantity, notes, payment_type, finance_type;
    private String[] payment_type_items, finance_type_items;

    private int total_amount;
    private int profit = 0;
    private int total_expenditure = 0;
    private int total_income = 0;

    private Finance finance;
    private Box<Finance> financeBox;
    private BoxStore farmerApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);

        //        objectBox initialization
        farmerApp = FarmerApp.getBoxStore();
        financeBox = farmerApp.boxFor(Finance.class);

        // This convention should follow to have a successful toolbar set with the correct set title.
        toolbar = findViewById(R.id.toolbar_finance);
        toolbar.setTitle("Add Expense/Revenue");
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        itemName = findViewById(R.id.item_name);
        itemCategory = findViewById(R.id.item_category);
        transactionDate = findViewById(R.id.transaction_date);
        itemAmount = findViewById(R.id.amount);
        itemQuantity = findViewById(R.id.quantity);
        itemNotes = findViewById(R.id.notes);

        paymentType = findViewById(R.id.spinner_payment_methode);
        financeType = findViewById(R.id.spinner_type);
        save_finance_btn = findViewById(R.id.save_finance_btn);

        savingBtn = findViewById(R.id.saving_finance_btn);
        progressBar = findViewById(R.id.finance_progress_bar);

//        spinner 1
        // Setting up spinner arrayAdapter
        // Setting up spinner using arrayAdapter
        payment_type_items = new String[]{"Cash", "Mobile", "Bank"};
        ArrayAdapter<String> payment_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, payment_type_items);
        payment_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentType.setAdapter(payment_adapter);
        paymentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                payment_type = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        spinner item 2
        finance_type_items = new String[]{"Income", "Expenditure"};
        ArrayAdapter<String> finance_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, finance_type_items);
        finance_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        financeType.setAdapter(finance_adapter);

        financeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                finance_type = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        save_finance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFinanceTransaction();
            }
        });

        transactionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(transactionDate);
            }
        });
    }

    private void setDate(final EditText editText) {
        DatePickerDialog datePickerDialog;
        int year, month, dayOfMonth;
        Calendar calendar;

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(FinanceActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                editText.setText(day + "-" + (month + 1) + "-" + year);

            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();

    }

    private void addFinanceTransaction() {
        name = itemName.getText().toString().trim();
        category = itemCategory.getText().toString().trim();
        transaction_date = transactionDate.getText().toString().trim();
        item_amount = itemAmount.getText().toString().trim();
        quantity = itemQuantity.getText().toString().trim();
        notes = itemNotes.getText().toString().trim();

//        payment_type = paymentType.getAdapter()..getText().toString().trim();
//        finance_type = financeType.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            itemName.setError("Field Required!");
        } else if (TextUtils.isEmpty(category)) {
            itemCategory.setError("Field Required!");
        } else if (TextUtils.isEmpty(transaction_date)) {
            transactionDate.setError("Field Required!");
        } else if (TextUtils.isEmpty(item_amount)) {
            itemAmount.setError("Field Required!");
        } else if (TextUtils.isEmpty(quantity)) {
            itemQuantity.setError("Field Required!");
        } else if (TextUtils.isEmpty(notes)) {
            itemNotes.setError("Field Required!");
        } else {

            AsyncTask<Void, Void, Void> finance_async = new AsyncTask<Void, Void, Void>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    progressBar.setVisibility(View.VISIBLE);
                    save_finance_btn.setVisibility(View.GONE);
                    savingBtn.setVisibility(View.VISIBLE);
                }

                @Override
                protected Void doInBackground(Void... voids) {
                    try {
                        Thread.sleep(1500);
                        // This where everything is collected and stored in our local database
                        User user = new User();

                        // setup the finance revenue and expenditures.
                        financeSetup();

                        finance = new Finance();

                        finance.setName(name);
                        finance.setCategory(category);
                        finance.setFinance_type(finance_type);
                        finance.setPayment_type(payment_type);
                        finance.setTransaction_date(transaction_date);
                        finance.setTotal_amount(total_amount);
                        finance.setTotal_expenditure(total_expenditure);
                        finance.setTotal_income(total_income);
                        finance.setProfit(profit);
                        finance.setQuantity(quantity);
                        finance.setNotes(notes);

                        finance.user.setTarget(user);
                        financeBox.put(finance);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }


                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    progressBar.setVisibility(View.GONE);
                    save_finance_btn.setVisibility(View.VISIBLE);
                    savingBtn.setVisibility(View.GONE);

                    Intent toDashboardActivity = new Intent(FinanceActivity.this, MainActivity.class);
                    startActivity(toDashboardActivity);
                    finish();
                    Toast.makeText(FinanceActivity.this, "Transaction added successfully", Toast.LENGTH_SHORT).show();

                }
            };

            finance_async.execute();

        }

    }

    //    this method allows us to set the finance record to our dashboard item.
    public void financeSetup() {
        total_amount = Integer.valueOf(item_amount) * Integer.valueOf(quantity);

        if (finance_type.equals("Income")) {
            total_income += total_amount;
            profit += total_amount;
        } else {

            total_expenditure += total_amount;
            profit -= total_amount;
        }

        Log.d(TAG, "financeSetup: profit " + profit);
        Log.d(TAG, "financeSetup: total_income " + total_income);
        Log.d(TAG, "financeSetup: total_amount " + total_amount);
        Log.d(TAG, "financeSetup: total_expenditure " + total_expenditure);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:

                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
