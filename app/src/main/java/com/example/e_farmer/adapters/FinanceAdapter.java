package com.example.e_farmer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_farmer.R;
import com.example.e_farmer.databinding.SingleFinanceItemBinding;
import com.example.e_farmer.models.Finance;

import java.util.ArrayList;
import java.util.List;

public class FinanceAdapter extends RecyclerView.Adapter<FinanceAdapter.FinanceViewHolder> {
    private Finance finance;
    private Context context;
    private List<Finance> financeList =  new ArrayList<>();

    public FinanceAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FinanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleFinanceItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.single_finance_item,parent,false);
        return new FinanceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FinanceViewHolder holder, int position) {
        finance = financeList.get(position);
        holder.binding.setFinance(finance);
    }

    @Override
    public int getItemCount() {
        return financeList.size();
    }

    public void setUpdatedData(List<Finance> finances) {
        this.financeList = finances;
        notifyDataSetChanged();
    }

    public class FinanceViewHolder extends RecyclerView.ViewHolder{
        SingleFinanceItemBinding binding;
        public FinanceViewHolder(@NonNull SingleFinanceItemBinding itemView) {
            super(itemView.financeCardview);
            this.binding = itemView;
        }
    }
}
