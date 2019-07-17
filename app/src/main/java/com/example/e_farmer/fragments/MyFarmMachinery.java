package com.example.e_farmer.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_farmer.IMainActivity;
import com.example.e_farmer.MyMachinery;
import com.example.e_farmer.R;
import com.example.e_farmer.adapters.MachineAdapter;
import com.example.e_farmer.models.Machine;
import com.example.e_farmer.viewmodels.MachineViewmodel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MyFarmMachinery extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private FloatingActionButton fab;
    private MachineViewmodel machineViewmodel;
    private MachineAdapter machineAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView searchView;

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
        View view = inflater.inflate(R.layout.my_farm_machinery, container, false);
        mRecyclerView = view.findViewById(R.id.machine_recyclerview);
        fab = view.findViewById(R.id.fab_machine);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyMachinery.class);
                startActivity(intent);
            }
        });

        searchView = view.findViewById(R.id.machine_searchview);
        swipeRefreshLayout = view.findViewById(R.id.machine_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        initSearchView();
        initRecyclerView();
        //        instantiate the viewmodel class here
        machineViewmodel = ViewModelProviders.of(this).get(MachineViewmodel.class);

        machineViewmodel.getAllMachine().observe(this, new Observer<List<Machine>>() {
            @Override
            public void onChanged(List<Machine> machines) {
                machineAdapter.setUpdatedData(machines);
                machineAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }
    private void initSearchView() {
        //Associate searchable configuration with the searchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        //listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                machineAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                machineAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    private void initRecyclerView() {
        machineAdapter = new MachineAdapter(getContext());
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(machineAdapter);
    }

    @Override
    public void onRefresh() {
        machineAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }
}