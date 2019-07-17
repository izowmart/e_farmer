package com.example.e_farmer.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.e_farmer.models.Animals;
import com.example.e_farmer.repositories.AnimalsRepository;

import java.util.List;

public class MyAnimalViewModel extends AndroidViewModel {
    private static final String TAG = "MyAnimalViewModel";
    private AnimalsRepository animalsRepo;

    private LiveData<List<Animals>> allAnimals;

    public MyAnimalViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "AnimalsViewModel: Retrieving data from the database");

        animalsRepo = new AnimalsRepository(application);
        allAnimals = animalsRepo.getAnimals();
    }

    public void insert(Animals animals) {
        animalsRepo.insert(animals);
    }

    public void update(Animals animals) {
        animalsRepo.update(animals);
    }

    public void delete(Animals animals) {
        animalsRepo.delete(animals);
    }

    public LiveData<List<Animals>> getAllAnimals() {
        return allAnimals;
    }

}