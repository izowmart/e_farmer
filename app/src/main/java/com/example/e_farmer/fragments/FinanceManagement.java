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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_farmer.FinanceActivity;
import com.example.e_farmer.IMainActivity;
import com.example.e_farmer.R;
import com.example.e_farmer.adapters.FinanceAdapter;
import com.example.e_farmer.models.Finance;
import com.example.e_farmer.viewmodels.FinanceViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FinanceManagement extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "FinanceManagement";
    public static final String NAME="name";
    public static final String CATEGORY="category";
    public static final String DATE="date";
    public static final String QUANTITY="quantity";
    public static final String NOTES="notes";
    public static final String ID="myId";

    private FloatingActionButton fab;
    private FinanceViewModel financeViewModel;
    private FinanceAdapter financeAdapter;
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

        // instantiate the viewmodel class here
        financeViewModel = ViewModelProviders.of(this).get(FinanceViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_finance_mngt, container, false);
        mRecyclerView = view.findViewById(R.id.finance_recyclerview);
        fab = view.findViewById(R.id.fab_finance);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FinanceActivity.class);
                startActivity(intent);
            }
        });
        searchView = view.findViewById(R.id.finance_searchview);
        swipeRefreshLayout = view.findViewById(R.id.finance_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        initSearchView();
        initRecyclerView();
        financeViewModel.getAllFinnace().observe(this, new Observer<List<Finance>>() {
            @Override
            public void onChanged(List<Finance> finances) {
                financeAdapter.setUpdatedData(finances);
                financeAdapter.notifyDataSetChanged();
            }
        });

        financeAdapter.setOnItemClickListener(new FinanceAdapter.OnItemClickListener() {
            @Override
            public void onItemClickDelete(Finance finance) {
                // Here, we can do whatever we want with our card item selected.
                financeViewModel.delete(finance);
            }

            @Override
            public void onItemClickEdit(Finance finance) {
                Intent intent = new Intent(getContext(),FinanceActivity.class);
                intent.putExtra(ID,finance.getId());
                intent.putExtra(NAME,finance.getName());
                intent.putExtra(CATEGORY,finance.getCategory());
                intent.putExtra(DATE,finance.getTransaction_date());
                intent.putExtra(QUANTITY,finance.getQuantity());
                intent.putExtra(NOTES,finance.getNotes());

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
                financeAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                financeAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    private void initRecyclerView() {
        financeAdapter = new FinanceAdapter(getContext());
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(financeAdapter);
    }

    @Override
    public void onRefresh() {
        financeAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }
}
