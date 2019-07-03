package com.example.e_farmer.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.e_farmer.IMainActivity;
import com.example.e_farmer.R;
import com.example.e_farmer.models.Finance;
import com.example.e_farmer.viewmodels.FinanceViemodel;

import java.util.List;

public class MyDashboard extends Fragment implements View.OnClickListener {

    private static final String TAG = "MyDashboard";
    IMainActivity iMainActivity;
    private CardView animals, farm_task, animal_treatment, land_crop_mngt, finance_management, farm_machinery;
    private TextView revenue, expenditure, profit;
    private Finance finance;

    private FinanceViemodel financeViemodel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iMainActivity = (IMainActivity) this.getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        iMainActivity.setToolbarTitle(getTag());

        // instantiate the viewmodel class here to get the finance record
        financeViemodel = ViewModelProviders.of(this).get(FinanceViemodel.class);
        financeViemodel.init();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_dashboard_fragment, container, false);
        animals = view.findViewById(R.id.animals);
        farm_task = view.findViewById(R.id.farm_tasks);
        animal_treatment = view.findViewById(R.id.animal_treatment);
        land_crop_mngt = view.findViewById(R.id.land_crop_mngt);
        finance_management = view.findViewById(R.id.finance_management);
        farm_machinery = view.findViewById(R.id.farm_machinery);
        revenue = view.findViewById(R.id.revenue);
        expenditure = view.findViewById(R.id.expenditure);
        profit = view.findViewById(R.id.profit);

        animals.setOnClickListener(this);
        farm_task.setOnClickListener(this);
        animal_treatment.setOnClickListener(this);
        land_crop_mngt.setOnClickListener(this);
        finance_management.setOnClickListener(this);
        farm_machinery.setOnClickListener(this);


        financeViemodel.getFinance().observe(this, new Observer<List<Finance>>() {
            @Override
            public void onChanged(List<Finance> finances) {
//                finance = new Finance();
//                if(finances.size() != 0) {
//                    revenue.setText((CharSequence) finances.get(finance.getTotal_revenue()));
//                    expenditure.setText((CharSequence) finances.get(finance.getTotal_expenditure()));
//                    profit.setText((CharSequence) finances.get(finance.getProfit()));
//
//                    Log.d(TAG, "onChanged: " + revenue);
//                    Log.d(TAG, "onChanged: " + profit);
//                    Log.d(TAG, "onChanged: " + expenditure);
//                }else{
//                    revenue.setText("0.00");
//                    expenditure.setText("0.00");
//                    profit.setText("0.00");
//
//                    Log.d(TAG, "onChanged: " + revenue);
//                    Log.d(TAG, "onChanged: " + profit);
//                    Log.d(TAG, "onChanged: " + expenditure);
//                }
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.animals:
                iMainActivity.inflateFragment(getString(R.string.animals));
                break;
            case R.id.farm_tasks:
                iMainActivity.inflateFragment(getString(R.string.farm_tasks));
                break;
            case R.id.animal_treatment:
                iMainActivity.inflateFragment(getString(R.string.animal_treatment));
                break;
            case R.id.land_crop_mngt:
                iMainActivity.inflateFragment(getString(R.string.crop_management));
                break;
            case R.id.farm_machinery:
                iMainActivity.inflateFragment(getString(R.string.farm_machinery));
                break;
            case R.id.finance_management:
                iMainActivity.inflateFragment(getString(R.string.finance_management));
                break;
        }

    }

}
