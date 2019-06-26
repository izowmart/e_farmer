package com.example.e_farmer.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_farmer.models.AnimalTreatment;
import com.example.e_farmer.models.Animals;

import java.util.ArrayList;
import java.util.List;

public class AnimalTreatmentAdapter extends RecyclerView.Adapter<AnimalTreatmentAdapter.TreatmentViewHolder> {

    private AnimalTreatment animalTreatment;
    private Context context;
    private List<AnimalTreatment> animalTreatmentList =  new ArrayList<>();

    public AnimalTreatmentAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TreatmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TreatmentViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return animalTreatmentList.size();
    }

    public void setUpdatedData(List<AnimalTreatment> animalTreatments) {
        this.animalTreatmentList = animalTreatments;
    }

    public class TreatmentViewHolder extends RecyclerView.ViewHolder{
        public TreatmentViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
