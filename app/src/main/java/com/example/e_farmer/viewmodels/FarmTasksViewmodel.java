package com.example.e_farmer.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.e_farmer.models.FarmTask;
import com.example.e_farmer.repositories.FarmTasksRepository;

import java.util.List;

public class FarmTasksViewmodel extends AndroidViewModel {
    private static final String TAG = "FarmTasksViewmodel";

    private FarmTasksRepository animalTreatmentRepo;
    private LiveData<List<FarmTask>> allFarmTask;

    public FarmTasksViewmodel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "FarmTaskViewModel: Retrieving data from the database");

        animalTreatmentRepo = new FarmTasksRepository(application);
        allFarmTask = animalTreatmentRepo.getFarmTask();
    }

    public void insert(FarmTask task) {
        animalTreatmentRepo.insert(task);
    }

    public void update(FarmTask task) {
        animalTreatmentRepo.update(task);
    }

    public void delete(FarmTask task) {
        animalTreatmentRepo.delete(task);
    }

    public LiveData<List<FarmTask>> getAllFarmTask() {
        return allFarmTask;
    }

}