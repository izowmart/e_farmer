package com.example.e_farmer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.e_farmer.models.AnimalTreatment;
import com.example.e_farmer.models.Animals;
import com.example.e_farmer.models.User;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class AddAnimalTreatment extends AppCompatActivity {

    private static final String TAG = "AddAnimalTreatment";

    private Toolbar toolbar;
    private EditText animalType, animalTreatmentReason, animalTag, animalMedicineName, animalDosageStart, animalDosageEnd, animalDosagePrescription, animalVetName, animalVetContacts, animalDescription;
    private Button saveMedication;
    private String type, tag, treatment_reason, medicine_name, dosage_start, dosage_end, dosage_prescription, vet_name, vet_contacts, description;

    private AnimalTreatment animalTreatment;
    private Box<AnimalTreatment> animalTretmentBox;
    private BoxStore farmerApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal_treatment);

//        objectBox initialization
        farmerApp = FarmerApp.getBoxStore();
        animalTretmentBox = farmerApp.boxFor(AnimalTreatment.class);

        // This convention should follow to have a successful toolbar set with the correct set title.
        toolbar = findViewById(R.id.toolbar_animal_treatment);
        toolbar.setTitle("Add Animal Treatment");
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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

        saveMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAnimalMedication();
            }
        });

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

            // This where everything is collected and stored in our local database
            User user = new User();

            animalTreatment = new AnimalTreatment();

            animalTreatment.setTag(tag);
            animalTreatment.setType(type);
            animalTreatment.setTreatment_reason(treatment_reason);
            animalTreatment.setMedicine_name(medicine_name);
            animalTreatment.setDosage_start(dosage_start);
            animalTreatment.setDosage_end(dosage_end);
            animalTreatment.setDosage_prescription(dosage_prescription);
            animalTreatment.setVet_name(vet_name);
            animalTreatment.setVet_contacts(vet_contacts);
            animalTreatment.setDescription(description);

            animalTreatment.user.setTarget(user);
            animalTretmentBox.put(animalTreatment);

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
