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

import com.example.e_farmer.AddAnimalTreatment;
import com.example.e_farmer.IMainActivity;
import com.example.e_farmer.R;
import com.example.e_farmer.adapters.AnimalTreatmentAdapter;
import com.example.e_farmer.models.AnimalTreatment;
import com.example.e_farmer.viewmodels.AnimalTreatmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MyAnimalTreatment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = "MyAnimalTreatment";
    public static final String TREATMENT_NAME="treatment_name";
    public static final String TREATMENT_TAG="treatment_tag";
    public static final String TREATMENT_REASON="treatment_reason";
    public static final String MEDICINE="medicine";
    public static final String DOSAGE_START="dosage_start";
    public static final String DOSAGE_END="dosage_end";
    public static final String PRESCRIPTION="prescription";
    public static final String VETINERY="vet_name";
    public static final String VET_CONTACTS="vet_contacts";
    public static final String TREATMENT_DESCRIPRION="description";
    public static final String TREATMENT_ID="id";

    private FloatingActionButton fabTreatment;
    private AnimalTreatmentViewModel animalTreatmentViewModel;
    private AnimalTreatmentAdapter animalTreatmentAdapter;
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

        //        instantiate the viewmodel class here
        animalTreatmentViewModel = ViewModelProviders.of(this).get(AnimalTreatmentViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.my_animal_treatment,container,false);

        mRecyclerView = view.findViewById(R.id.animal_treatment_recyclerview);
        fabTreatment = view.findViewById(R.id.fab_treatment);
        fabTreatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddAnimalTreatment.class);
                startActivity(intent);
            }
        });
        searchView = view.findViewById(R.id.treatment_searchview);
        swipeRefreshLayout = view.findViewById(R.id.treatment_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        initSearchView();

        initRecyclerView();

        animalTreatmentViewModel.getAllAnimalTreatment().observe(this, new Observer<List<AnimalTreatment>>() {
            @Override
            public void onChanged(List<AnimalTreatment> animalTreatments) {
                animalTreatmentAdapter.setUpdatedData(animalTreatments);
                animalTreatmentAdapter.notifyDataSetChanged();
            }
        });

        animalTreatmentAdapter.setOnItemClickListener(new AnimalTreatmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClickDelete(AnimalTreatment treatment) {

                // Here, we can do whatever we want with our card item selected.
                animalTreatmentViewModel.delete(treatment);
            }

            @Override
            public void onItemClickEdit(AnimalTreatment treatment) {

                Intent intent = new Intent(getContext(), AddAnimalTreatment.class);
                intent.putExtra(TREATMENT_ID,treatment.getId());
                intent.putExtra(TREATMENT_DESCRIPRION,treatment.getDescription());
                intent.putExtra(VET_CONTACTS,treatment.getVet_contacts());
                intent.putExtra(VETINERY,treatment.getVet_name());
                intent.putExtra(PRESCRIPTION,treatment.getDosage_prescription());
                intent.putExtra(DOSAGE_END,treatment.getDosage_end());
                intent.putExtra(DOSAGE_START,treatment.getDosage_start());
                intent.putExtra(MEDICINE,treatment.getMedicine_name());
                intent.putExtra(TREATMENT_REASON,treatment.getTreatment_reason());
                intent.putExtra(TREATMENT_TAG,treatment.getTag());
                intent.putExtra(TREATMENT_NAME,treatment.getType());

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
                animalTreatmentAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                animalTreatmentAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }
    private void initRecyclerView() {
        animalTreatmentAdapter = new AnimalTreatmentAdapter(getContext());
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(animalTreatmentAdapter);
    }

    @Override
    public void onRefresh() {
        animalTreatmentAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }
}
