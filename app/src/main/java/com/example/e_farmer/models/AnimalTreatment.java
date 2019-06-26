package com.example.e_farmer.models;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class AnimalTreatment extends BaseObservable {
    @Id(assignable = true)
    private long id;
    public ToOne<User> user;

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

    public AnimalTreatment() {
    }

    @Bindable
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
