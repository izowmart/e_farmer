package com.example.e_farmer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.PopupMenu;
import android.widget.Toast;

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
    private AnimalTreatmentAdapter.OnItemClickListener listener;

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
    public void onBindViewHolder(@NonNull TreatmentViewHolder holder, final int position) {
        animalTreatment = animalTreatmentList.get(position);
        holder.binding.setTreatment(animalTreatment);
        holder.binding.treatmentVert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //        inflate menu
                PopupMenu popupMenu = new PopupMenu(context, view);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.card_overvflow, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit_card:
                                listener.onItemClickEdit(animalTreatmentList.get(position));
                                return true;
                            case R.id.delete_card:
                                // The method below checks to avoid clicking on an item that has already been deleted
                                listener.onItemClickDelete(animalTreatmentList.get(position));
                                Toast.makeText(context, "Card Deleted", Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
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
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public interface OnItemClickListener {
        void onItemClickDelete(AnimalTreatment treatment);

        void onItemClickEdit(AnimalTreatment treatment);
    }

    @Override
    public int getItemCount() {
        return animalTreatmentList.size();
    }

    public void setUpdatedData(List<AnimalTreatment> animalTreatments) {
        this.animalTreatmentList = animalTreatments;
        this.mAnimalTreatmentList = animalTreatments;
        notifyDataSetChanged();
    }

    public class TreatmentViewHolder extends RecyclerView.ViewHolder{
        SingleAnimalTreatmentItemBinding binding;
        public TreatmentViewHolder(@NonNull SingleAnimalTreatmentItemBinding binding) {
            super(binding.animalTreatmentCardview);
            this.binding = binding;
        }
    }
}
