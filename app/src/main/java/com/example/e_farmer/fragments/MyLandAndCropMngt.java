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
import com.example.e_farmer.LandAndCropMngt;
import com.example.e_farmer.R;
import com.example.e_farmer.adapters.LandAndCropAdapter;
import com.example.e_farmer.models.LandAndCrop;
import com.example.e_farmer.viewmodels.LandAndCropViewmodel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MyLandAndCropMngt extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = "MyLandAndCropMngt";
    public static final String LAND_NAME="land_name";
    public static final String SQUARE_NUMBER="square_number";
    public static final String TASK_NAME="task_name";
    public static final String TASK_START="task_start";
    public static final String TASK_END="task_end";
    public static final String LAND_DESCRIPTION="land_description";
    public static final String LAND_ID="land_id";

    private FloatingActionButton landCropFab;
    private LandAndCropViewmodel landAndCropViewmodel;
    private LandAndCropAdapter landAndCropAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView searchView;
    private FrameLayout landEmpty;
    private RelativeLayout landREl;

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
        landAndCropViewmodel = ViewModelProviders.of(this).get(LandAndCropViewmodel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.my_land_crop_mngt,container,false);
        mRecyclerView = view.findViewById(R.id.land_crop_recyclerview);
        landEmpty = view.findViewById(R.id.land_layout_empty);
        landREl = view.findViewById(R.id.land_rel);

        landCropFab = view.findViewById(R.id.fab_land_crop);
        landCropFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LandAndCropMngt.class);
                startActivity(intent);
            }
        });
        searchView = view.findViewById(R.id.land_crop_search);
        swipeRefreshLayout = view.findViewById(R.id.land_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        initSearchView();

        initRecyclerView();
        landAndCropViewmodel.getAllLandAndCrop().observe(this, new Observer<List<LandAndCrop>>() {
            @Override
            public void onChanged(List<LandAndCrop> landAndCrops) {
                if (landAndCrops.isEmpty()) {
                    landREl.setVisibility(View.GONE);
                    landEmpty.setVisibility(View.VISIBLE);
                }else{
                    landREl.setVisibility(View.VISIBLE);
                    landEmpty.setVisibility(View.GONE);
                }
                landAndCropAdapter.setUpdatedData(landAndCrops);
                landAndCropAdapter.notifyDataSetChanged();
            }
        });

        landAndCropAdapter.setOnItemClickListener(new LandAndCropAdapter.OnItemClickListener() {
            @Override
            public void onItemClickDelete(LandAndCrop landAndCrop) {
                // Here, we can do whatever we want with our card item selected.
                landAndCropViewmodel.delete(landAndCrop);
            }

            @Override
            public void onItemClickEdit(LandAndCrop landAndCrop) {

                Intent intent = new Intent(getContext(), LandAndCropMngt.class);
                intent.putExtra(LAND_ID,landAndCrop.getId());
                intent.putExtra(LAND_DESCRIPTION,landAndCrop.getDescription());
                intent.putExtra(TASK_END,landAndCrop.getCompletion_date());
                intent.putExtra(TASK_START,landAndCrop.getStart_date());
                intent.putExtra(TASK_NAME,landAndCrop.getTask());
                intent.putExtra(SQUARE_NUMBER,landAndCrop.getSquare_number());
                intent.putExtra(LAND_NAME,landAndCrop.getName());

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
                landAndCropAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                landAndCropAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    private void initRecyclerView() {
        landAndCropAdapter = new LandAndCropAdapter(getContext());
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(landAndCropAdapter);
    }


    @Override
    public void onRefresh() {
        landAndCropAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }
}
