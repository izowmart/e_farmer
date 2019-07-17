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
import com.example.e_farmer.databinding.SingleMachineItemBinding;
import com.example.e_farmer.models.Machine;
import com.example.e_farmer.models.Machine;

import java.util.ArrayList;
import java.util.List;

public class MachineAdapter extends RecyclerView.Adapter<MachineAdapter.MachineViewHolder> implements Filterable {

    private Machine machine;
    private Context context;
    private List<Machine> machineList =  new ArrayList<>();
    private List<Machine> mMachineList = new ArrayList<>();
    private ArrayList<Machine> filteredList;

    public MachineAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MachineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleMachineItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.single_machine_item,parent,false);
        return new MachineViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MachineViewHolder holder, int position) {
        machine = machineList.get(position);
        holder.binding.setMachine(machine);
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    machineList = mMachineList;
                } else {
                    filteredList = new ArrayList<>();
                    for (Machine row : machineList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    machineList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = machineList;
                return filterResults;
            }


            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                machineList = (List<Machine>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return machineList.size();
    }

    public void setUpdatedData(List<Machine> machines) {
        this.machineList = machines;
        this.mMachineList = machines;
        notifyDataSetChanged();
    }

    public class MachineViewHolder extends RecyclerView.ViewHolder{
        SingleMachineItemBinding binding;

        public MachineViewHolder(@NonNull SingleMachineItemBinding itemView) {
            super(itemView.machineCardview);
            this.binding = itemView;
        }
    }
}
