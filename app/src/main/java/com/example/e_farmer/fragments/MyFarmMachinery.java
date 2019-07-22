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
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.e_farmer.IMainActivity;
import com.example.e_farmer.MyMachinery;
import com.example.e_farmer.R;
import com.example.e_farmer.adapters.MachineAdapter;
import com.example.e_farmer.models.Machine;
import com.example.e_farmer.viewmodels.MachineViewmodel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MyFarmMachinery extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = "MyFarmMachinery";
    public static final String MACHINERY_NAME="machine_name";
    public static final String MACHINE_TYPE="machine_type";
    public static final String YOR="yor";
    public static final String DATE_OF_PURCHASE="date_of_purchase";
    public static final String ORIGINAL_PRICE="original_rpice";
    public static final String CURRENT_PRICE="current_price";
    public static final String MILEAGE="mileage";
    public static final String MACHINE_DESCRIPTION="description";
    public static final String MACHINE_ID="id";

    private FloatingActionButton fab;
    private MachineViewmodel machineViewmodel;
    private MachineAdapter machineAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView searchView;
    private FrameLayout machineryEmpty;
    private RelativeLayout machineRel;

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
        machineryEmpty = view.findViewById(R.id.machine_layout_empty);
        machineRel = view.findViewById(R.id.machine_rel);
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
                if (machines.isEmpty()) {
                    machineRel.setVisibility(View.GONE);
                    machineryEmpty.setVisibility(View.VISIBLE);
                }else{
                    machineRel.setVisibility(View.VISIBLE);
                    machineryEmpty.setVisibility(View.GONE);
                }
                machineAdapter.setUpdatedData(machines);
                machineAdapter.notifyDataSetChanged();
            }
        });

        machineAdapter.setOnItemClickListener(new MachineAdapter.OnItemClickListener() {
            @Override
            public void onItemClickDelete(Machine machine) {
                // Here, we can do whatever we want with our card item selected.
                machineViewmodel.delete(machine);
            }

            @Override
            public void onItemClickEdit(Machine machine) {

                Intent intent = new Intent(getContext(), MyMachinery.class);
                intent.putExtra(MACHINE_ID,machine.getId());
                intent.putExtra(MACHINE_DESCRIPTION,machine.getNotes());
                intent.putExtra(MILEAGE,machine.getMilage());
                intent.putExtra(CURRENT_PRICE,machine.getCurrent_price());
                intent.putExtra(ORIGINAL_PRICE,machine.getOriginal_price());
                intent.putExtra(DATE_OF_PURCHASE,machine.getPurchase_date());
                intent.putExtra(YOR,machine.getRegistration_year());
                intent.putExtra(MACHINE_TYPE,machine.getType());
                intent.putExtra(MACHINERY_NAME,machine.getName());

                startActivity(intent);
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