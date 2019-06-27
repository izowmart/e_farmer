package com.example.e_farmer.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.e_farmer.FarmerApp;
import com.example.e_farmer.Settings;
import com.example.e_farmer.models.LandAndCrop;
import com.example.e_farmer.models.LandAndCrop_;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class LandAndCropRepository {
    private static final String TAG = "LandAndCropRepository";

    private static LandAndCropRepository instance;
    private ArrayList<LandAndCrop> animalsArrayList = new ArrayList<>();
    private List<LandAndCrop> landCropList;
    private Box<LandAndCrop> landAndCropBox;
    private BoxStore farmerApp;

    public static LandAndCropRepository getInstance() {
        if (instance == null) {
            instance = new LandAndCropRepository();
        }
        return instance;
    }

    //Through this method we are able to set data to our viewmodel
    public MutableLiveData<List<LandAndCrop>> getLandAndCrop() {
        setLandAndCrop();
        MutableLiveData<List<LandAndCrop>> data = new MutableLiveData<>();
        data.setValue(animalsArrayList);
        return data;
    }

    private void setLandAndCrop() {
        long user_id = Settings.getUserId();
//        here we shall get our list item from the database.objectbox
//        objectBox initialization
        farmerApp = FarmerApp.getBoxStore();
        landAndCropBox = farmerApp.boxFor(LandAndCrop.class);
        landCropList = landAndCropBox.query().build().find();

        Log.d(TAG, "setLandAndCrop: " + LandAndCrop_.userId);

        animalsArrayList.addAll(landCropList);
        Log.d(TAG, "setLandAndCrop: animal list was set here " + animalsArrayList);
    }
}