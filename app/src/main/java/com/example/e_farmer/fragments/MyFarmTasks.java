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
import com.example.e_farmer.R;
import com.example.e_farmer.adapters.FarmTaskAdapter;
import com.example.e_farmer.models.FarmTask;
import com.example.e_farmer.viewmodels.FarmTasksViewmodel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MyFarmTasks extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private FloatingActionButton fab;
    private FarmTasksViewmodel farmTasksViewmodel;
    private FarmTaskAdapter farmTaskAdapter;
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
        searchView = view.findViewById(R.id.farm_task_searchview);
        swipeRefreshLayout = view.findViewById(R.id.task_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        initSearchView();

        initRecyclerView();
        //        instantiate the viewmodel class here
        farmTasksViewmodel = ViewModelProviders.of(this).get(FarmTasksViewmodel.class);

        farmTasksViewmodel.getAllFarmTask().observe(this, new Observer<List<FarmTask>>() {
            @Override
            public void onChanged(List<FarmTask> farmTasks) {
                farmTaskAdapter.setUpdatedData(farmTasks);
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
                farmTaskAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                farmTaskAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    private void initRecyclerView() {
        farmTaskAdapter = new FarmTaskAdapter(getContext());
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(farmTaskAdapter);
    }

    @Override
    public void onRefresh() {
        farmTaskAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }
}
