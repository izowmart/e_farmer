package com.example.e_farmer.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_farmer.IMainActivity;
import com.example.e_farmer.R;
import com.example.e_farmer.adapters.FarmTaskAdapter;
import com.example.e_farmer.models.FarmTask;
import com.example.e_farmer.viewmodels.FarmTasksViewmodel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MyFarmTasks extends Fragment {

    private FloatingActionButton fab;
    private FarmTasksViewmodel farmTasksViewmodel;
    private FarmTaskAdapter farmTaskAdapter;
    private RecyclerView mRecyclerView;

    IMainActivity iMainActivity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iMainActivity = (IMainActivity) this.getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iMainActivity.setToolbarTitle(getTag());


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.my_farm_tasks,container,false);
        mRecyclerView = view.findViewById(R.id.farm_task_recyclerview);
        fab = view.findViewById(R.id.fab_farm_task);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), com.example.e_farmer.MyFarmTasks.class);
                startActivity(intent);
            }
        });

        initRecyclerView();
        //        instantiate the viewmodel class here
        farmTasksViewmodel = ViewModelProviders.of(this).get(FarmTasksViewmodel.class);
        farmTasksViewmodel.init();

        farmTasksViewmodel.getFarmTasks().observe(this, new Observer<List<FarmTask>>() {
            @Override
            public void onChanged(List<FarmTask> farmTasks) {
                farmTaskAdapter.setUpdatedData(farmTasks);
            }
        });

        return view;
    }

    private void initRecyclerView() {
        farmTaskAdapter = new FarmTaskAdapter(getContext());
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(farmTaskAdapter);
    }

}
