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
    private OnItemClickListener listener;

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
    public void onBindViewHolder(@NonNull MyAnimalViewholder holder, final int position) {
        animals = animalList.get(position);
        holder.binding.setAnimal(animals);
        holder.binding.animalVert.setOnClickListener(new View.OnClickListener() {
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
                                listener.onItemClickEdit(animalList.get(position));
                                return true;
                            case R.id.delete_card:
                                // The method below checks to avoid clicking on an item that has already been deleted
                                listener.onItemClickDelete(animalList.get(position));
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
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public interface OnItemClickListener {
        void onItemClickDelete(Animals animal);

        void onItemClickEdit(Animals animal);
    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }

    public void setUpdatedData(List<Animals> animals) {
        this.animalList = animals;
        this.mAnimalList = animals;
        notifyDataSetChanged();
    }


    public class MyAnimalViewholder extends RecyclerView.ViewHolder {
        SingleAnimalItemCardBinding binding;

        public MyAnimalViewholder(@NonNull SingleAnimalItemCardBinding binding) {
            super(binding.animalCardview);
            this.binding = binding;
        }
    }
}
