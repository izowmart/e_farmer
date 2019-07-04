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

    private int current_finance = 0;
    private int current_income = 0;
    private int current_profit = 0;

    public static FinanceRepository getInstance() {
        if (instance == null) {
            instance = new FinanceRepository();
        }
        return instance;
    }

    //Through this method we are able to set data to our viewmodel
    public MutableLiveData<List<Finance>> getFinance() {
        getFinanceList();
        MutableLiveData<List<Finance>> data = new MutableLiveData<>();
        data.setValue(financeList);
        return data;
    }

    private List<Finance> getFinanceList() {
        farmerApp = FarmerApp.getBoxStore();
        financeBox = farmerApp.boxFor(Finance.class);
        financeList = financeBox.query().build().find();

        return financeList;
    }

    public MutableLiveData<Integer> getIncome(){
        farmerApp = FarmerApp.getBoxStore();
        financeBox = farmerApp.boxFor(Finance.class);
        Finance finance = financeBox.query().build().findFirst();

        Log.d(TAG, "getIncome: " + current_income);
        MutableLiveData<Integer> income_data = new MutableLiveData<>();
        income_data.setValue(finance != null ? finance.getTotal_income() : null);

        return income_data;

    }

    public MutableLiveData<Integer> getExpenditure(){
        farmerApp = FarmerApp.getBoxStore();
        financeBox = farmerApp.boxFor(Finance.class);
        Finance finance = financeBox.query().build().findFirst();

        Log.d(TAG, "getExpenditure: " + current_finance);
        MutableLiveData<Integer> expenditure_data = new MutableLiveData<>();

        expenditure_data.setValue(finance != null ? finance.getTotal_expenditure() : null);

        return expenditure_data;

    }

    public MutableLiveData<Integer> getProfit() {
        farmerApp = FarmerApp.getBoxStore();
        financeBox = farmerApp.boxFor(Finance.class);
        Finance finance = financeBox.query().build().findFirst();

        Log.d(TAG, "getProfit: " + current_profit);
        MutableLiveData<Integer> profit_data = new MutableLiveData<>();
        profit_data.setValue(finance != null ? finance.getProfit() : null);

        return profit_data;
    }

//    public MutableLiveData<Integer> getProfit() {
//        getFinanceList();
//        for (int i = 0; i < financeList.size(); i++) {
//            Finance finance = financeList.get(i);
//            current_profit += finance.getProfit();
//        }
//
//        Log.d(TAG, "getProfit: " + current_profit);
//        MutableLiveData<Integer> profit_data = new MutableLiveData<>();
//        profit_data.setValue(current_profit);
//
//        return profit_data;
//    }


}