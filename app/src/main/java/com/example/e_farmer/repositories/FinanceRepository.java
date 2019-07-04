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
}