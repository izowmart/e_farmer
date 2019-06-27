package com.example.e_farmer.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.e_farmer.adapters.MyAnimalAdapter;
import com.example.e_farmer.models.Animals;
import com.example.e_farmer.viewmodels.MyAnimalViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_farmer.AddAnimal;
import com.example.e_farmer.IMainActivity;
import com.example.e_farmer.R;

import java.util.List;

public class MyAnimals extends Fragment {
    private FloatingActionButton fab;
    private MyAnimalViewModel myAnimalViewModel;
    private MyAnimalAdapter myAnimalAdapter;
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
        myAnimalViewModel = ViewModelProviders.of(this).get(MyAnimalViewModel.class);
        myAnimalViewModel.init();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_animals, container, false);

        mRecyclerView = view.findViewById(R.id.animal_recyclerview);
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddAnimal.class);
                startActivity(intent);
            }
        });

        initRecyclerView();
        myAnimalViewModel.getAnimal().observe(this, new Observer<List<Animals>>() {
            @Override
            public void onChanged(List<Animals> animals) {
                myAnimalAdapter.setUpdatedData(animals);
                myAnimalAdapter.notifyDataSetChanged();

            }
        });

        return view;
    }

    private void initRecyclerView() {
        myAnimalAdapter = new MyAnimalAdapter(getContext());
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(myAnimalAdapter);
    }
}
