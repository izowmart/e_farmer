package com.example.e_farmer.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_farmer.models.AnimalTreatment;
import com.example.e_farmer.repositories.AnimalTreatmentRepository;

import java.util.List;

public class AnimalTreatmentViewModel extends AndroidViewModel {
    private static final String TAG = "AnimalTreatmentViewMode";

    private AnimalTreatmentRepository animalTreatmentRepo;
    private LiveData<List<AnimalTreatment>> allAnimalTreatment;

    public AnimalTreatmentViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "AnimalTreatmentViewModel: Retrieving data from the database");

        animalTreatmentRepo = new AnimalTreatmentRepository(application);
        allAnimalTreatment = animalTreatmentRepo.getAnimalTreatment();
    }

    public void insert(AnimalTreatment treatment) {
        animalTreatmentRepo.insert(treatment);
    }

    public void update(AnimalTreatment treatment) {
        animalTreatmentRepo.update(treatment);
    }

    public void delete(AnimalTreatment treatment) {
        animalTreatmentRepo.delete(treatment);
    }

    public LiveData<List<AnimalTreatment>> getAllAnimalTreatment() {
        return allAnimalTreatment;
    }

}