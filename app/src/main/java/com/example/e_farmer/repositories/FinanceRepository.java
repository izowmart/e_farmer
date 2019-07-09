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
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;

public class FinanceRepository {
    private static final String TAG = "FinanceRepository";

    private static FinanceRepository instance;
    private List<Finance> financeList;
    private static Box<Finance> financeBox;
    private static BoxStore farmerApp;


    public static FinanceRepository getInstance() {
        farmerApp = FarmerApp.getBoxStore();
        financeBox = farmerApp.boxFor(Finance.class);
        if (instance == null) {
            instance = new FinanceRepository();
        }
        return instance;
    }

    //Through this method we are able to set data to our viewmodel
    public MutableLiveData<List<Finance>> getFinance() {
        financeList = financeBox.query().build().find();
        MutableLiveData<List<Finance>> data = new MutableLiveData<>();
        data.setValue(financeList);
        return data;
    }

    public void delete(Finance finance){
        Query<Finance> financeQuery = financeBox.query().equal(Finance_.id,finance.getId()).build();
        financeBox.remove(financeQuery.findFirst());
    }
}