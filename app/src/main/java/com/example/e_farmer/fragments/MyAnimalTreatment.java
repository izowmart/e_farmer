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

import com.example.e_farmer.AddAnimalTreatment;
import com.example.e_farmer.IMainActivity;
import com.example.e_farmer.R;
import com.example.e_farmer.adapters.AnimalTreatmentAdapter;
import com.example.e_farmer.models.AnimalTreatment;
import com.example.e_farmer.viewmodels.AnimalTreatmentViewmodel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MyAnimalTreatment extends Fragment {

    private FloatingActionButton fabTreatment;
    private AnimalTreatmentViewmodel animalTreatmentViewModel;
    private AnimalTreatmentAdapter animalTreatmentAdapter;
    private RecyclerView mRecyclerView;

    IMainActivity iMainActivity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iMainActivity = (IMainActivity) this.getActivity();

        //        instantiate the viewmodel class here
        animalTreatmentViewModel = ViewModelProviders.of(this).get(AnimalTreatmentViewmodel.class);
        animalTreatmentViewModel.init();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iMainActivity.setToolbarTitle(getTag());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_animal_treatment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.animal_recyclerview);
        fabTreatment = view.findViewById(R.id.fab_treatment);
        fabTreatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddAnimalTreatment.class);
                startActivity(intent);
            }
        });

        initRecyclerView();

        animalTreatmentViewModel.getAnimal().observe(this, new Observer<List<AnimalTreatment>>() {
            @Override
            public void onChanged(List<AnimalTreatment> animalTreatments) {
                animalTreatmentAdapter.setUpdatedData(animalTreatments);
                animalTreatmentAdapter.notifyDataSetChanged();
            }
        });
    }
    private void initRecyclerView() {
        animalTreatmentAdapter = new AnimalTreatmentAdapter(getContext());
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(animalTreatmentAdapter);
    }

}
