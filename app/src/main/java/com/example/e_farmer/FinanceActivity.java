package com.example.e_farmer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.e_farmer.models.Finance;
import com.example.e_farmer.models.User;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class FinanceActivity extends AppCompatActivity {

    private static final String TAG = "FinanceActivity";

    private Toolbar toolbar;
    private EditText itemName, itemCategory, transactionDate, itemAmount, itemQuantity, itemNotes;
    private Spinner paymentType, financeType;
    private Button save_finance_btn;
    private String name, category, transaction_date, item_amount, quantity, notes,payment_type,finance_type;
    private String[] payment_type_items, finance_type_items;

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

//        spinner 1
        // Setting up spinner arrayAdapter
        // Setting up spinner using arrayAdapter
        payment_type_items = new String[]{"Mobile", "Bank","Cash"};
        ArrayAdapter<String> payment_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, payment_type_items);
        payment_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentType.setAdapter(payment_adapter);

//        spinner item 2
        finance_type_items = new String[]{"Income", "Revenue"};
        ArrayAdapter<String> finance_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, finance_type_items);
        finance_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        financeType.setAdapter(finance_adapter);

        save_finance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFinanceTransaction();
            }
        });
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

            // This where everything is collected and stored in our local database
            User user = new User();

            finance = new Finance();

            finance.setName(name);
            finance.setCategory(category);
            finance.setTransaction_date(transaction_date);
            finance.setItem_amount(item_amount);
            finance.setQuantity(quantity);
            finance.setNotes(notes);
            finance.setFinance_type("Revenue");
            finance.setPayment_type("Mobile");

            finance.user.setTarget(user);
            financeBox.put(finance);

        }
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
