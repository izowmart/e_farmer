package com.example.e_farmer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_farmer.R;
import com.example.e_farmer.databinding.SingleFarmTaskItemBinding;
import com.example.e_farmer.models.FarmTask;
import com.example.e_farmer.models.FarmTask;
import com.example.e_farmer.utils.FarmTaskDiffUtil;

import java.util.ArrayList;
import java.util.List;

public class FarmTaskAdapter  extends RecyclerView.Adapter<FarmTaskAdapter.FarmTaskViewHolder> implements Filterable {

    private FarmTask farmTask;
    private Context context;
    private List<FarmTask> farmTaskList =  new ArrayList<>();
    private List<FarmTask> mFarmTaskList = new ArrayList<>();
    private ArrayList<FarmTask> filteredList;

    private FarmTaskDiffUtil farmTaskDiffUtil;

    public FarmTaskAdapter(Context context) {
        this.context = context;
    }

    public void setUpdatedData(List<FarmTask> farmTasks) {
        this.mFarmTaskList = farmTasks;
        farmTaskDiffUtil = new FarmTaskDiffUtil(farmTaskList, farmTasks);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(farmTaskDiffUtil);

        farmTaskList.addAll(farmTasks);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public FarmTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleFarmTaskItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.single_farm_task_item,parent,false);
        return new FarmTaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FarmTaskViewHolder holder, int position) {
        farmTask = farmTaskList.get(position);
        holder.binding.setFarmTasks(farmTask);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    farmTaskList = mFarmTaskList;
                } else {
                    filteredList = new ArrayList<>();
                    for (FarmTask row : farmTaskList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    farmTaskList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = farmTaskList;
                return filterResults;
            }


            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                farmTaskList = (List<FarmTask>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return farmTaskList.size();
    }


    public class FarmTaskViewHolder extends RecyclerView.ViewHolder{
        SingleFarmTaskItemBinding binding;

        public FarmTaskViewHolder(@NonNull SingleFarmTaskItemBinding itemView) {
            super(itemView.farmTaskCardview);
            this.binding = itemView;
        }
    }
}
