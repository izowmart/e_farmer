package com.example.e_farmer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_farmer.R;
import com.example.e_farmer.databinding.SingleFarmTaskItemBinding;
import com.example.e_farmer.models.FarmTask;
import com.example.e_farmer.utils.FarmTaskDiffUtil;

import java.util.ArrayList;
import java.util.List;

public class FarmTaskAdapter  extends RecyclerView.Adapter<FarmTaskAdapter.FarmTaskViewHolder> {

    private FarmTask farmTask;
    private Context context;
    private List<FarmTask> farmTaskList =  new ArrayList<>();
    private FarmTaskDiffUtil farmTaskDiffUtil;

    public FarmTaskAdapter(Context context) {
        this.context = context;
    }

    public void setUpdatedData(List<FarmTask> farmTasks) {
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
