package com.example.e_farmer.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_farmer.models.Machine;
import com.example.e_farmer.repositories.MachineRepository;

import java.util.List;

public class MachineViewmodel extends ViewModel {
    private MachineRepository machineRepository;

    //    mutableLive data its changeable unlike LiveData which is observable only.mutableLiveData is a subclass of LiveData.
    private MutableLiveData<List<Machine>> mMachine;

    public void init(){
        if(mMachine != null){
    //    here we check if our mAnimals list has anything,if it has we terminate the process here and use what we already have
            return;
        }
        machineRepository = MachineRepository.getInstance();
        mMachine = machineRepository.getMachine();
    }

    public LiveData<List<Machine>> getMachine(){
        return mMachine;
    }
}
