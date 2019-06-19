package com.example.e_farmer.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_farmer.IMainActivity;
import com.example.e_farmer.R;

public class MyDashboard extends Fragment implements View.OnClickListener {

    IMainActivity iMainActivity;
    private CardView animals,farm_task, animal_treatment,land_crop_mngt,workers_timesheet,farm_mngt,farm_machinery;
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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.my_dashboard_fragment,container,false);
        animals = view.findViewById(R.id.animals);
        farm_task = view.findViewById(R.id.farm_tasks);
        animal_treatment = view.findViewById(R.id.animal_treatment);
        land_crop_mngt = view.findViewById(R.id.land_crop_mngt);
        workers_timesheet = view.findViewById(R.id.workers_timesheet);
        farm_mngt = view.findViewById(R.id.farm_mngt);
        farm_machinery = view.findViewById(R.id.farm_machinery);

        animals.setOnClickListener(this);
        farm_task.setOnClickListener(this);
        animal_treatment.setOnClickListener(this);
        land_crop_mngt.setOnClickListener(this);
        workers_timesheet.setOnClickListener(this);
        farm_mngt.setOnClickListener(this);
        farm_machinery.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
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
                case R.id.workers_timesheet:
                iMainActivity.inflateFragment(getString(R.string.workers_time_sheet));
                break;
                case R.id.farm_mngt:
                iMainActivity.inflateFragment(getString(R.string.farm_management));
                break;
                case R.id.farm_machinery:
                iMainActivity.inflateFragment(getString(R.string.farm_machinery));
                break;
        }

    }

}
