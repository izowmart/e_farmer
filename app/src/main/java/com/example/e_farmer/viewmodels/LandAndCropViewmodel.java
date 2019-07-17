package com.example.e_farmer.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_farmer.models.LandAndCrop;
import com.example.e_farmer.repositories.LandAndCropRepository;

import java.util.List;

public class LandAndCropViewmodel extends AndroidViewModel {
    private static final String TAG = "LandAndCropViewmodel";

    private LandAndCropRepository landAndCropRepo;
    private LiveData<List<LandAndCrop>> allLandAndCrop;

    public LandAndCropViewmodel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "LandAndCropViewModel: Retrieving data from the database");

        landAndCropRepo = new LandAndCropRepository(application);
        allLandAndCrop = landAndCropRepo.getLandAndCrop();
    }

    public void insert(LandAndCrop landAndCrop) {
        landAndCropRepo.insert(landAndCrop);
    }

    public void update(LandAndCrop landAndCrop) {
        landAndCropRepo.update(landAndCrop);
    }

    public void delete(LandAndCrop landAndCrop) {
        landAndCropRepo.delete(landAndCrop);
    }

    public LiveData<List<LandAndCrop>> getAllLandAndCrop() {
        return allLandAndCrop;
    }

}