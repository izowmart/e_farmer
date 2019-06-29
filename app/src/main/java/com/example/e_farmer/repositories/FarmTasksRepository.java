package com.example.e_farmer.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.e_farmer.FarmerApp;
import com.example.e_farmer.Settings;
import com.example.e_farmer.models.Animals_;
import com.example.e_farmer.models.FarmTask;
import com.example.e_farmer.models.FarmTask_;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class FarmTasksRepository {
    private static final String TAG = "FarmTasksRepository";

    private static FarmTasksRepository instance;
    private List<FarmTask> farmTasks;
    private Box<FarmTask> farmTaskBox;
    private BoxStore farmerApp;

    public static FarmTasksRepository getInstance() {
        if (instance == null) {
            instance = new FarmTasksRepository();

        }
        return instance;
    }

    //Through this method we are able to set data to our viewmodel
    public MutableLiveData<List<FarmTask>> getFarmTask() {
        setFarmTask();
        MutableLiveData<List<FarmTask>> data = new MutableLiveData<>();
        data.setValue(farmTasks);
        return data;
    }

    private void setFarmTask() {
        long user_id = Settings.getUserId();
//        here we shall get our list item from the database.objectbox
//        objectBox initialization
        farmerApp = FarmerApp.getBoxStore();
        farmTaskBox = farmerApp.boxFor(FarmTask.class);
        farmTasks = farmTaskBox.query().build().find();

        Log.d(TAG, "setFarmTasks: " + FarmTask_.userId);

    }
}
