package com.example.e_farmer.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_farmer.models.Machine;
import com.example.e_farmer.repositories.MachineRepository;

import java.util.List;

public class MachineViewmodel extends AndroidViewModel {
    private static final String TAG = "MachineViewmodel";
    private MachineRepository machineRepository;

    private LiveData<List<Machine>> allMachine;

    public MachineViewmodel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "MachineViewModel: Retrieving data from the database");

        machineRepository = new MachineRepository(application);
        allMachine = machineRepository.getMachine();
    }

    public void insert(Machine machine) {
        machineRepository.insert(machine);
    }

    public void update(Machine machine) {
        machineRepository.update(machine);
    }

    public void delete(Machine machine) {
        machineRepository.delete(machine);
    }

    public LiveData<List<Machine>> getAllMachine() {
        return allMachine;
    }

}