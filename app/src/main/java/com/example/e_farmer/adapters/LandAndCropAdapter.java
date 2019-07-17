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
    public void onBindViewHolder(@NonNull LandAndCropViewHolder holder, int position) {
        landAndCrop = landAndCrops.get(position);
        holder.binding.setLandCrop(landAndCrop);
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

    @Override
    public int getItemCount() {
        return landAndCrops.size();
    }

    public void setUpdatedData(List<LandAndCrop> landAndCrops) {
        this.landAndCrops = landAndCrops;
        this.mLandAndCrop = landAndCrops;
    }

    public class LandAndCropViewHolder extends RecyclerView.ViewHolder{
        SingleLandCropItemBinding binding;
        public LandAndCropViewHolder(@NonNull SingleLandCropItemBinding itemView) {
            super(itemView.landCropCardview);
            this.binding = itemView;
        }
    }
}
