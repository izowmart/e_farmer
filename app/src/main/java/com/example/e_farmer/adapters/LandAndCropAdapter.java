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
import com.example.e_farmer.databinding.SingleLandCropItemBinding;
import com.example.e_farmer.models.LandAndCrop;
import com.example.e_farmer.models.LandAndCrop;

import java.util.ArrayList;
import java.util.List;

public class LandAndCropAdapter extends RecyclerView.Adapter<LandAndCropAdapter.LandAndCropViewHolder> implements Filterable {

    private LandAndCrop landAndCrop;
    private Context context;
    private List<LandAndCrop> landAndCrops =  new ArrayList<>();
    private List<LandAndCrop> mLandAndCrop = new ArrayList<>();
    private ArrayList<LandAndCrop> filteredList;
    private OnItemClickListener listener;

    public LandAndCropAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public LandAndCropViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleLandCropItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.single_land_crop_item,parent,false);
        return new LandAndCropViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LandAndCropViewHolder holder, final int position) {
        landAndCrop = landAndCrops.get(position);
        holder.binding.setLandCrop(landAndCrop);
        holder.binding.landVert.setOnClickListener(new View.OnClickListener() {
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
                                listener.onItemClickEdit(landAndCrops.get(position));
                                return true;
                            case R.id.delete_card:
                                // The method below checks to avoid clicking on an item that has already been deleted
                                listener.onItemClickDelete(landAndCrops.get(position));
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
                    landAndCrops = mLandAndCrop;
                } else {
                    filteredList = new ArrayList<>();
                    for (LandAndCrop row : landAndCrops) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    landAndCrops = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = landAndCrops;
                return filterResults;
            }


            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                landAndCrops = (List<LandAndCrop>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public interface OnItemClickListener {
        void onItemClickDelete(LandAndCrop landAndCrop);

        void onItemClickEdit(LandAndCrop landAndCrop);
    }

    @Override
    public int getItemCount() {
        return landAndCrops.size();
    }

    public void setUpdatedData(List<LandAndCrop> landAndCrops) {
        this.landAndCrops = landAndCrops;
        this.mLandAndCrop = landAndCrops;
        notifyDataSetChanged();
    }

    public class LandAndCropViewHolder extends RecyclerView.ViewHolder{
        SingleLandCropItemBinding binding;
        public LandAndCropViewHolder(@NonNull SingleLandCropItemBinding itemView) {
            super(itemView.landCropCardview);
            this.binding = itemView;
        }
    }
}
