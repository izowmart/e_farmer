package com.example.e_farmer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.e_farmer.fragments.MyAnimalTreatment;
import com.example.e_farmer.models.AnimalTreatment;
import com.example.e_farmer.viewmodels.AnimalTreatmentViewModel;

import java.util.Calendar;


public class AddAnimalTreatment extends AppCompatActivity {

    private static final String TAG = "AddAnimalTreatment";

    private Toolbar toolbar;
    private EditText animalType, animalTreatmentReason, animalTag, animalMedicineName, animalDosageStart, animalDosageEnd, animalDosagePrescription, animalVetName, animalVetContacts, animalDescription;
    private Button saveMedication, savingBtn;
    private ProgressBar progressBar;
    private String type, tag, treatment_reason, medicine_name, dosage_start, dosage_end, dosage_prescription, vet_name, vet_contacts, description;

    private AnimalTreatment animalTreatment;
    private AnimalTreatmentViewModel animalTreatmentViewModel;
    private String userId;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal_treatment);

        userId = Settings.getUserId();
        animalTreatmentViewModel = ViewModelProviders.of(this).get(AnimalTreatmentViewModel.class);

        animalType = findViewById(R.id.animal_treatment_type);
        animalTreatmentReason = findViewById(R.id.animal_treatment_reason);
        animalTag = findViewById(R.id.animal_treatment_tag);
        animalMedicineName = findViewById(R.id.animal_medicine_name);
        animalDosageStart = findViewById(R.id.animal_dose_start);
        animalDosageEnd = findViewById(R.id.animal_dose_end);
        animalDosagePrescription = findViewById(R.id.animal_dose_prescription);
        animalVetName = findViewById(R.id.animal_vet_name);
        animalVetContacts = findViewById(R.id.animal_vet_contacts);
        animalDescription = findViewById(R.id.animal_descr);
        saveMedication = findViewById(R.id.save_medication_btn);

        // This convention should follow to have a successful toolbar set with the correct set title.
        toolbar = findViewById(R.id.toolbar_animal_treatment);
        toolbar.setTitle("");
        intent = getIntent();

        if (intent.hasExtra(MyAnimalTreatment.TREATMENT_NAME)) {
            toolbar.setTitle("Edit Animal Treatment");

            animalType.setText(intent.getStringExtra(MyAnimalTreatment.TREATMENT_NAME));
            animalTreatmentReason.setText(intent.getStringExtra(MyAnimalTreatment.TREATMENT_REASON));
            animalTag.setText(intent.getStringExtra(MyAnimalTreatment.TREATMENT_TAG));
            animalMedicineName.setText(intent.getStringExtra(MyAnimalTreatment.MEDICINE));
            animalDosageStart.setText(intent.getStringExtra(MyAnimalTreatment.DOSAGE_START));
            animalDosageEnd.setText(intent.getStringExtra(MyAnimalTreatment.DOSAGE_END));
            animalDosagePrescription.setText(intent.getStringExtra(MyAnimalTreatment.PRESCRIPTION));
            animalVetName.setText(intent.getStringExtra(MyAnimalTreatment.VETINERY));
            animalVetContacts.setText(intent.getStringExtra(MyAnimalTreatment.VET_CONTACTS));
            animalDescription.setText(intent.getStringExtra(MyAnimalTreatment.TREATMENT_DESCRIPRION));

        }else {
            toolbar.setTitle("Add Animal Treatment");
        }

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }



        savingBtn = findViewById(R.id.saving_medication_btn);
        progressBar = findViewById(R.id.treatment_progress_bar);

        saveMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAnimalMedication();
            }
        });
        animalDosageStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(animalDosageStart);
            }
        });

        animalDosageEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(animalDosageEnd);
            }
        });

    }


    private void setDate(final EditText animalDosage) {
        DatePickerDialog datePickerDialog;
        int year, month, dayOfMonth;
        Calendar calendar;

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(AddAnimalTreatment.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                animalDosage.setText(MessageFormat.format("{0}-{1}-{2}", day, month + 1, year));
                animalDosage.setText(day + "-" + (month + 1) + "-" + year);

            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();

    }

    private void addAnimalMedication() {
        type = animalType.getText().toString().trim();
        tag = animalTag.getText().toString().trim();
        treatment_reason = animalTreatmentReason.getText().toString().trim();
        medicine_name = animalMedicineName.getText().toString().trim();
        dosage_start = animalDosageStart.getText().toString().trim();
        dosage_end = animalDosageEnd.getText().toString().trim();
        dosage_prescription = animalDosagePrescription.getText().toString().trim();
        vet_name = animalVetName.getText().toString().trim();
        vet_contacts = animalVetContacts.getText().toString().trim();
        description = animalDescription.getText().toString().trim();

        if (TextUtils.isEmpty(type)) {
            animalType.setError("Field Required!");
        } else if (TextUtils.isEmpty(tag)) {
            animalTag.setError("Field Required!");
        } else if (TextUtils.isEmpty(treatment_reason)) {
            animalTreatmentReason.setError("Field Required!");
        } else if (TextUtils.isEmpty(medicine_name)) {
            animalMedicineName.setError("Field Required!");
        } else if (TextUtils.isEmpty(dosage_start)) {
            animalDosageStart.setError("Field Required!");
        } else if (TextUtils.isEmpty(dosage_end)) {
            animalDosageEnd.setError("Field Required!");
        } else if (TextUtils.isEmpty(dosage_prescription)) {
            animalDosagePrescription.setError("Field Required!");
        } else if (TextUtils.isEmpty(vet_name)) {
            animalVetName.setError("Field Required!");
        } else if (TextUtils.isEmpty(vet_contacts)) {
            animalVetContacts.setError("Field Required!");
        } else if (TextUtils.isEmpty(description)) {
            animalDescription.setError("Field Required!");
        } else {

            AsyncTask<Void, Void, Void> treatment_async = new AsyncTask<Void, Void, Void>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    progressBar.setVisibility(View.VISIBLE);
                    saveMedication.setVisibility(View.GONE);
                    savingBtn.setVisibility(View.VISIBLE);
                }

                @Override
                protected Void doInBackground(Void... voids) {
                    try {
                        Thread.sleep(1500);

                        if (intent.hasExtra(MyAnimalTreatment.TREATMENT_NAME)) {
                            AnimalTreatment eAnimalTreatment = new AnimalTreatment(userId, type, tag, treatment_reason, medicine_name, dosage_start, dosage_end, dosage_prescription, vet_name, vet_contacts, description);
                            eAnimalTreatment.setId(intent.getIntExtra(MyAnimalTreatment.TREATMENT_ID,-1));

                            animalTreatmentViewModel.update(eAnimalTreatment);
                        } else {
                            animalTreatment = new AnimalTreatment(userId, type, tag, treatment_reason, medicine_name, dosage_start, dosage_end, dosage_prescription, vet_name, vet_contacts, description);
                            animalTreatmentViewModel.insert(animalTreatment);
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    progressBar.setVisibility(View.GONE);
                    saveMedication.setVisibility(View.VISIBLE);
                    savingBtn.setVisibility(View.GONE);

                    Intent toDashboardActivity = new Intent(AddAnimalTreatment.this, MainActivity.class);
                    startActivity(toDashboardActivity);
                    finish();
                    Toast.makeText(AddAnimalTreatment.this, "Medication added successfully", Toast.LENGTH_SHORT).show();

                }
            };

            treatment_async.execute();


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
