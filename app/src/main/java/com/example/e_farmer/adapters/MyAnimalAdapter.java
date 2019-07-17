package com.example.e_farmer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_farmer.R;
import com.example.e_farmer.databinding.SingleAnimalItemCardBinding;
import com.example.e_farmer.models.Animals;
import com.example.e_farmer.models.Animals;

import java.util.ArrayList;
import java.util.List;

public class MyAnimalAdapter extends RecyclerView.Adapter<MyAnimalAdapter.MyAnimalViewholder> implements Filterable {

    private Animals animals;
    private Context context;
    private List<Animals> animalList =  new ArrayList<>();
    private List<Animals> mAnimalList = new ArrayList<>();
    private ArrayList<Animals> filteredList;

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
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    animalList = mAnimalList;
                } else {
                    filteredList = new ArrayList<>();
                    for (Animals row : animalList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getType().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    animalList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = animalList;
                return filterResults;
            }


            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                animalList = (List<Animals>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }

    public void setUpdatedData(List<Animals> animals) {
        this.animalList = animals;
        this.mAnimalList = animals;
    }


    public class MyAnimalViewholder extends RecyclerView.ViewHolder {
        SingleAnimalItemCardBinding binding;

        public MyAnimalViewholder(@NonNull SingleAnimalItemCardBinding binding) {
            super(binding.animalCardview);
            this.binding = binding;
        }
    }
}
