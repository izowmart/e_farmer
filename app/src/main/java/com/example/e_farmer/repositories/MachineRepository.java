package com.example.e_farmer.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.e_farmer.FarmerApp;
import com.example.e_farmer.Settings;
import com.example.e_farmer.models.Machine;
import com.example.e_farmer.models.Machine_;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class MachineRepository {
    private static final String TAG = "MachineRepository";
    private static MachineRepository instance;
    private List<Machine> machineList;
    private Box<Machine> machineBox;
    private BoxStore farmerApp;

    public static MachineRepository getInstance() {
        if (instance == null) {
            instance = new MachineRepository();
        }
        return instance;
    }

    //Through this method we are able to set data to our viewmodel
    public MutableLiveData<List<Machine>> getMachine() {
        setMachine();
        MutableLiveData<List<Machine>> data = new MutableLiveData<>();
        data.setValue(machineList);
        return data;
    }

    private void setMachine() {
        long user_id = Settings.getUserId();
//        here we shall get our list item from the database.objectbox
//        objectBox initialization
        farmerApp = FarmerApp.getBoxStore();
        machineBox = farmerApp.boxFor(Machine.class);
        machineList = machineBox.query().build().find();

        Log.d(TAG, "setMachine: " + Machine_.userId);

    }
}