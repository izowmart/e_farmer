package com.example.e_farmer.utils;

import androidx.recyclerview.widget.DiffUtil;

import com.example.e_farmer.models.FarmTask;

import java.util.List;

public class FarmTaskDiffUtil extends DiffUtil.Callback {
    private List<FarmTask> oldList;
    private List<FarmTask> newList;

    public FarmTaskDiffUtil(List<FarmTask> oldList, List<FarmTask> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldPosition, int newPosition) {
        return oldList.get(oldPosition) == newList.get(newPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldPosition, int newPosition) {
        FarmTask oldItems =  oldList.get(oldPosition);
        FarmTask newItems =  newList.get(newPosition);
        return oldItems.getName().equals(newItems.getName()) &&
                oldItems.getId()== newItems.getId()&&
                oldItems.getAssignee().equals(newItems.getAssignee())&&
                oldItems.getStart().equals(newItems.getStart())&&
                oldItems.getDue().equals(newItems.getDue())&&
                oldItems.getDescription().equals(newItems.getDescription());
    }
}
