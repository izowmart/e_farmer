package com.example.e_farmer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_farmer.R;
import com.example.e_farmer.databinding.SingleAnimalItemCardBinding;
import com.example.e_farmer.models.Animals;

import java.util.ArrayList;
import java.util.List;

public class MyAnimalAdapter extends RecyclerView.Adapter<MyAnimalAdapter.MyAnimalViewholder> {

    private Animals animals;
    private Context context;
    private List<Animals> animalList =  new ArrayList<>();

    public MyAnimalAdapter(Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public MyAnimalViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleAnimalItemCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.single_animal_item_card,parent,false);
        return new MyAnimalViewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAnimalViewholder holder, int position) {
        animals = animalList.get(position);
        holder.binding.setAnimal(animals);
    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }

    public void setUpdatedData(List<Animals> animals) {
        this.animalList = animals;
    }


    public class MyAnimalViewholder extends RecyclerView.ViewHolder {
        SingleAnimalItemCardBinding binding;

        public MyAnimalViewholder(@NonNull SingleAnimalItemCardBinding binding) {
            super(binding.animalCardview);
            this.binding = binding;
        }
    }
}
