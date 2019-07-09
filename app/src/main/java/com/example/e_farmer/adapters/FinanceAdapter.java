package com.example.e_farmer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

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
    private List<Finance> financeList = new ArrayList<>();
    OnItemClickListener listener;

    public FinanceAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FinanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleFinanceItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.single_finance_item, parent, false);
        return new FinanceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FinanceViewHolder holder, final int position) {
        finance = financeList.get(position);
        holder.binding.setFinance(finance);
        holder.binding.financeVent.setOnClickListener(new View.OnClickListener() {
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
                                // The method below checks to avoid clicking on an item that has already been deleted
//                                if (listener != null && position != RecyclerView.NO_POSITION) {
//                                    listener.onItemClick(finance);
//                                }
                                Toast.makeText(context, "Edit card", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.delete_card:
                                // The method below checks to avoid clicking on an item that has already been deleted
                                if (listener != null && position != RecyclerView.NO_POSITION) {
                                    listener.onItemClickDelete(finance);
                                }
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

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClickDelete(Finance finance);
        void onItemClickEdit(Finance finance);
    }

    @Override
    public int getItemCount() {
        return financeList.size();
    }

    public void setUpdatedData(List<Finance> finances) {
        this.financeList = finances;
        notifyDataSetChanged();
    }

    public class FinanceViewHolder extends RecyclerView.ViewHolder {
        SingleFinanceItemBinding binding;

        public FinanceViewHolder(@NonNull SingleFinanceItemBinding itemView) {
            super(itemView.financeCardview);
            this.binding = itemView;

        }
    }
}
