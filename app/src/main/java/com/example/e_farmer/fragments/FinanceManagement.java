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

import com.example.e_farmer.FinanceActivity;
import com.example.e_farmer.IMainActivity;
import com.example.e_farmer.R;
import com.example.e_farmer.adapters.FinanceAdapter;
import com.example.e_farmer.models.Finance;
import com.example.e_farmer.viewmodels.FinanceViemodel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FinanceManagement extends Fragment {

    private FloatingActionButton fab;
    private FinanceViemodel financeViemodel;
    private FinanceAdapter financeAdapter;
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

        //        instantiate the viewmodel class here
        financeViemodel = ViewModelProviders.of(this).get(FinanceViemodel.class);
        financeViemodel.init();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.my_finance_mngt,container,false);
        mRecyclerView = view.findViewById(R.id.finance_recyclerview);
        fab = view.findViewById(R.id.fab_finance);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FinanceActivity.class);
                startActivity(intent);
            }
        });

        initRecyclerView();
        financeViemodel.getFinance().observe(this, new Observer<List<Finance>>() {
            @Override
            public void onChanged(List<Finance> finances) {
                financeAdapter.setUpdatedData(finances);
                financeAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    private void initRecyclerView() {
        financeAdapter = new FinanceAdapter(getContext());
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(financeAdapter);
    }
}
