package com.example.e_farmer.models;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.SET_DEFAULT;

@Entity(tableName = "treatments", foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "userId",
        onDelete = SET_DEFAULT))
public class AnimalTreatment extends BaseObservable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userId;
    private String type;
    private String tag;
    private String treatment_reason;
    private String medicine_name;
    private String dosage_start;
    private String dosage_end;
    private String dosage_prescription;
    private String vet_name;
    private String vet_contacts;
    private String description;

    public AnimalTreatment(String userId, String type, String tag, String treatment_reason, String medicine_name, String dosage_start, String dosage_end, String dosage_prescription, String vet_name, String vet_contacts, String description) {
        this.userId = userId;
        this.type = type;
        this.tag = tag;
        this.treatment_reason = treatment_reason;
        this.medicine_name = medicine_name;
        this.dosage_start = dosage_start;
        this.dosage_end = dosage_end;
        this.dosage_prescription = dosage_prescription;
        this.vet_name = vet_name;
        this.vet_contacts = vet_contacts;
        this.description = description;
    }

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Bindable
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Bindable
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Bindable
    public String getTreatment_reason() {
        return treatment_reason;
    }

    public void setTreatment_reason(String treatment_reason) {
        this.treatment_reason = treatment_reason;
    }

    @Bindable
    public String getMedicine_name() {
        return medicine_name;
    }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    @Bindable
    public String getDosage_start() {
        return dosage_start;
    }

    public void setDosage_start(String dosage_start) {
        this.dosage_start = dosage_start;
    }

    @Bindable
    public String getDosage_end() {
        return dosage_end;
    }

    public void setDosage_end(String dosage_end) {
        this.dosage_end = dosage_end;
    }

    @Bindable
    public String getDosage_prescription() {
        return dosage_prescription;
    }

    public void setDosage_prescription(String dosage_prescription) {
        this.dosage_prescription = dosage_prescription;
    }

    @Bindable
    public String getVet_name() {
        return vet_name;
    }

    public void setVet_name(String vet_name) {
        this.vet_name = vet_name;
    }

    @Bindable
    public String getVet_contacts() {
        return vet_contacts;
    }

    public void setVet_contacts(String vet_contacts) {
        this.vet_contacts = vet_contacts;
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
