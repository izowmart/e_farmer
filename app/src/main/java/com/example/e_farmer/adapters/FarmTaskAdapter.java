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
import com.example.e_farmer.databinding.SingleFarmTaskItemBinding;
import com.example.e_farmer.models.FarmTask;

import java.util.ArrayList;
import java.util.List;

public class FarmTaskAdapter  extends RecyclerView.Adapter<FarmTaskAdapter.FarmTaskViewHolder> implements Filterable {

    private FarmTask farmTask;
    private Context context;
    private List<FarmTask> farmTaskList =  new ArrayList<>();
    private List<FarmTask> mFarmTaskList = new ArrayList<>();
    private ArrayList<FarmTask> filteredList;
    private FarmTaskAdapter.OnItemClickListener listener;


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
    public void onBindViewHolder(@NonNull FarmTaskViewHolder holder, final int position) {
        farmTask = farmTaskList.get(position);
        holder.binding.setFarmTasks(farmTask);
        holder.binding.taskVert.setOnClickListener(new View.OnClickListener() {
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
                                listener.onItemClickEdit(farmTaskList.get(position));
                                Toast.makeText(context, "Edit card", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.delete_card:
                                // The method below checks to avoid clicking on an item that has already been deleted
                                listener.onItemClickDelete(farmTaskList.get(position));
                                Toast.makeText(context, "card is being deleting", Toast.LENGTH_SHORT).show();
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
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public interface OnItemClickListener {
        void onItemClickDelete(FarmTask tasks);

        void onItemClickEdit(FarmTask tasks);
    }
    public void setUpdatedData(List<FarmTask> farmTasks) {
        this.farmTaskList = farmTasks;
        this.mFarmTaskList = farmTasks;
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
