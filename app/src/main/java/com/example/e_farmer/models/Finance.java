package com.example.e_farmer.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Finance extends BaseObservable {
    @Id(assignable = true)
    private long id;
    public ToOne<User> user;

    private String name;
    private String category;
    private String transaction_date;
    private String item_amount;
    private String quantity;
    private String payment_type;
    private String notes;
    private String finance_type;

    public Finance() {
    }

    @Bindable
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Bindable
    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    @Bindable
    public String getFinance_type() {
        return finance_type;
    }

    public void setFinance_type(String finance_type) {
        this.finance_type = finance_type;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Bindable
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Bindable
    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    @Bindable
    public String getItem_amount() {
        return item_amount;
    }

    public void setItem_amount(String item_amount) {
        this.item_amount = item_amount;
    }

    @Bindable
    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Bindable
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
