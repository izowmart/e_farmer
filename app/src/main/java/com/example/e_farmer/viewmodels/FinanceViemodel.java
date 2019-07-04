package com.example.e_farmer.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_farmer.models.Finance;
import com.example.e_farmer.repositories.FinanceRepository;

import java.util.List;

public class FinanceViemodel extends ViewModel {

    private FinanceRepository financeRepository;

    //    mutableLive data its changeable unlike LiveData which is observable only.mutableLiveData is a subclass of LiveData.
    private MutableLiveData<List<Finance>> mFinance;
    private MutableLiveData<Integer> mProfit;
    private MutableLiveData<Integer> mExpenditure;
    private MutableLiveData<Integer> mIncome;

    public void init() {
        if (mFinance != null) {
//            here we check if our mAnimals list has anything,if it has we terminate the process here and use what we already have
            return;
        }
        financeRepository = FinanceRepository.getInstance();
        mFinance = financeRepository.getFinance();

        mProfit = financeRepository.getProfit();
        mExpenditure = financeRepository.getExpenditure();
        mIncome = financeRepository.getIncome();
    }


    public LiveData<List<Finance>> getFinance() {
        return mFinance;
    }

    public LiveData<Integer> getProfit() {
        return mProfit;

    }

    public LiveData<Integer> getExpenditure() {

        return mExpenditure;
    }

    public LiveData<Integer> getIncome() {

        return mIncome;
    }
}
