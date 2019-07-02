package com.example.e_farmer.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.e_farmer.FarmerApp;
import com.example.e_farmer.Settings;
import com.example.e_farmer.models.Finance;
import com.example.e_farmer.models.Finance_;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class FinanceRepository {
    private static final String TAG = "FinanceRepository";

    private static FinanceRepository instance;
    private List<Finance> financeList;
    private Box<Finance> financeBox;
    private BoxStore farmerApp;

    public static FinanceRepository getInstance() {
        if (instance == null) {
            instance = new FinanceRepository();
        }
        return instance;
    }

    //Through this method we are able to set data to our viewmodel
    public MutableLiveData<List<Finance>> getFinance() {
        setFinance();
        MutableLiveData<List<Finance>> data = new MutableLiveData<>();
        data.setValue(financeList);
        return data;
    }

    private void setFinance() {
        long user_id = Settings.getUserId();
//        here we shall get our list item from the database.objectbox
//        objectBox initialization
        farmerApp = FarmerApp.getBoxStore();
        financeBox = farmerApp.boxFor(Finance.class);
        financeList = financeBox.query().build().find();

        Log.d(TAG, "setFinance: " + Finance_.user.relationId);

    }

//    public MutableLiveData<Object> getRevenue(){
//        farmerApp = FarmerApp.getBoxStore();
//        financeBox = farmerApp.boxFor(Finance.class);
//        Finance revenue = financeBox.query().build().findFirst();
//
//        MutableLiveData<Object> revenue_data = new MutableLiveData<>();
//        revenue_data.setValue(revenue != null ? revenue.getTotal_revenue() : 0);
//
//        Log.d(TAG, "getRevenue: " + (revenue != null ? revenue.getTotal_revenue() : 0));
//
//        return (MutableLiveData<Object>) (revenue != null ? revenue.getTotal_revenue() : 0);
//
//    }
//
//    public MutableLiveData<Object>  getExpenditure(){
//        farmerApp = FarmerApp.getBoxStore();
//        financeBox = farmerApp.boxFor(Finance.class);
//        Finance expenditure = financeBox.query().build().findFirst();
//
//        MutableLiveData<Object> expenditure_data = new MutableLiveData<>();
//        expenditure_data.setValue(expenditure != null ? expenditure.getTotal_expenditure() : 0);
//        Log.d(TAG, "getExpenditure: " + (expenditure != null ? expenditure.getTotal_expenditure() : 0));
//        return (MutableLiveData<Object>)(expenditure != null ? expenditure.getTotal_expenditure() : 0);
//    }
//
//    public MutableLiveData<Object>  getProfit(){
//        farmerApp = FarmerApp.getBoxStore();
//        financeBox = farmerApp.boxFor(Finance.class);
//        Finance profit = financeBox.query().build().findFirst();
//
//        MutableLiveData<Object> expenditure_data = new MutableLiveData<>();
//        expenditure_data.setValue(profit != null ? profit.getProfit() : 0);
//        Log.d(TAG, "getProfit: " + (profit != null ? profit.getProfit() : 0));
//        return profit != null ? profit.getProfit() : 0;
//    }


}