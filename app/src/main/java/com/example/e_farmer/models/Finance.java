package com.example.e_farmer.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Finance extends BaseObservable {
    @Id
    private long id;
    public ToOne<User> user;

    private String name;
    private String category;
    private String transaction_date;
    private String total_amount;
    private String quantity;
    private String payment_type;
    private String notes;
    private String finance_type;
    private int profit;
    private int total_revenue;
    private int total_expenditure;

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
    public Integer getProfit() {
        return profit;
    }

    public void setProfit(Integer profit) {
        this.profit = profit;
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
    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    @Bindable
    public int getTotal_revenue() {
        return total_revenue;
    }

    public void setTotal_revenue(int total_revenue) {
        this.total_revenue = total_revenue;
    }

    @Bindable
    public int getTotal_expenditure() {
        return total_expenditure;
    }

    public void setTotal_expenditure(int total_expenditure) {
        this.total_expenditure = total_expenditure;
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
