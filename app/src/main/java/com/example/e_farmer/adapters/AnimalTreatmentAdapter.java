package com.example.e_farmer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_farmer.R;
import com.example.e_farmer.databinding.SingleAnimalTreatmentItemBinding;
import com.example.e_farmer.models.AnimalTreatment;
import com.example.e_farmer.models.Animals;
import com.example.e_farmer.models.AnimalTreatment;

import java.util.ArrayList;
import java.util.List;

public class AnimalTreatmentAdapter extends RecyclerView.Adapter<AnimalTreatmentAdapter.TreatmentViewHolder> implements Filterable {

    private AnimalTreatment animalTreatment;
    private Context context;
    private List<AnimalTreatment> animalTreatmentList =  new ArrayList<>();
    private List<AnimalTreatment> mAnimalTreatmentList =  new ArrayList<>();
    private ArrayList<AnimalTreatment> filteredList;

    public AnimalTreatmentAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TreatmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleAnimalTreatmentItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.single_animal_treatment_item,parent,false);
        return new TreatmentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TreatmentViewHolder holder, int position) {
        animalTreatment = animalTreatmentList.get(position);
        holder.binding.setTreatment(animalTreatment);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    animalTreatmentList = mAnimalTreatmentList;
                } else {
                    filteredList = new ArrayList<>();
                    for (AnimalTreatment row : animalTreatmentList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getType().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    animalTreatmentList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = animalTreatmentList;
                return filterResults;
            }


            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                animalTreatmentList = (List<AnimalTreatment>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return animalTreatmentList.size();
    }

    public void setUpdatedData(List<AnimalTreatment> animalTreatments) {
        this.animalTreatmentList = animalTreatments;
        this.mAnimalTreatmentList = animalTreatments;
    }

    public class TreatmentViewHolder extends RecyclerView.ViewHolder{
        SingleAnimalTreatmentItemBinding binding;
        public TreatmentViewHolder(@NonNull SingleAnimalTreatmentItemBinding binding) {
            super(binding.animalTreatmentCardview);
            this.binding = binding;
        }
    }
}
