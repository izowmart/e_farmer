package com.example.e_farmer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_farmer.R;
import com.example.e_farmer.databinding.SingleFarmTaskItemBinding;
import com.example.e_farmer.models.FarmTask;

import java.util.ArrayList;
import java.util.List;

public class FarmTaskAdapter  extends RecyclerView.Adapter<FarmTaskAdapter.FarmTaskViewHolder> {

    private FarmTask farmTask;
    private Context context;
    private List<FarmTask> farmTaskList =  new ArrayList<>();

    public FarmTaskAdapter(Context context) {
        this.context = context;
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
    public int getItemCount() {
        return farmTaskList.size();
    }

    public void setUpdatedData(List<FarmTask> farmTasks) {
        this.farmTaskList = farmTasks;
        notifyDataSetChanged();
    }

    public class FarmTaskViewHolder extends RecyclerView.ViewHolder{
        SingleFarmTaskItemBinding binding;

        public FarmTaskViewHolder(@NonNull SingleFarmTaskItemBinding itemView) {
            super(itemView.farmTaskCardview);
            this.binding = itemView;
        }
    }
}
