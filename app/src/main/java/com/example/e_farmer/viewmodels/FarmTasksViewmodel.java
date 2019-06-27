package com.example.e_farmer.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_farmer.models.FarmTask;
import com.example.e_farmer.repositories.FarmTasksRepository;

import java.util.List;

public class FarmTasksViewmodel extends ViewModel {
    private FarmTasksRepository farmTasksRepository;

    //    mutableLive data its changeable unlike LiveData which is observable only.mutableLiveData is a subclass of LiveData.
    private MutableLiveData<List<FarmTask>> mFarmTasks;

    public void init(){
        if(mFarmTasks != null){
//            here we check if our mFarmTasks list has anything,if it has we terminate the process here and use what we already have
            return;
        }
        farmTasksRepository = FarmTasksRepository.getInstance();
        mFarmTasks = farmTasksRepository.getFarmTask();
    }

    public LiveData<List<FarmTask>> getFarmTasks(){
        return mFarmTasks;
    }
}