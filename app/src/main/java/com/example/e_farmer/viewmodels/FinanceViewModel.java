package com.example.e_farmer.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_farmer.models.Finance;
import com.example.e_farmer.repositories.FinanceRepository;

import java.util.List;

public class FinanceViewModel extends AndroidViewModel {

    private static final String TAG = "FinanceViewModel";
    private FinanceRepository financeRepository;
    private LiveData<List<Finance>> allFinance;

    public FinanceViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "FinanceViewModel: Retrieving data from the database");
        financeRepository = new FinanceRepository(application);
        allFinance = financeRepository.getFinance();
    }

    public void insert(Finance finance) {
        financeRepository.insert(finance);
    }

    public void update(Finance finance) {
        financeRepository.update(finance);
    }

    public void delete(Finance finance) {
        financeRepository.delete(finance);
    }


    public LiveData<List<Finance>> getAllFinnace() {
        return allFinance;
    }

}
