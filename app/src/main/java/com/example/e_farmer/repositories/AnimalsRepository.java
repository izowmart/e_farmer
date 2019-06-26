package com.example.e_farmer.repositories;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.e_farmer.FarmerApp;
import com.example.e_farmer.Settings;
import com.example.e_farmer.models.Animals;
import com.example.e_farmer.models.Animals_;
import com.example.e_farmer.models.User;
import com.example.e_farmer.models.User_;


import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;


public class AnimalsRepository {
    private static final String TAG = "AnimalsRepository";
    private static AnimalsRepository instance;
    private ArrayList<Animals> animalsArrayList = new ArrayList<>();
    private List<Animals> animalsList;
    private Box<Animals> animalsBox;
    private BoxStore farmerApp;

    public static AnimalsRepository getInstance() {
        if (instance == null) {
            instance = new AnimalsRepository();
        }
        return instance;
    }

    //Through this method we are able to set data to our viewmodel
    public MutableLiveData<List<Animals>> getAnimals() {
        setAnimals();
        MutableLiveData<List<Animals>> data = new MutableLiveData<>();
        data.setValue(animalsArrayList);
        return data;
    }

    private void setAnimals() {
        long user_id = Settings.getUserId();
//        here we shall get our list item from the database.objectbox
//        objectBox initialization
        farmerApp = FarmerApp.getBoxStore();
        animalsBox = farmerApp.boxFor(Animals.class);
        animalsList = animalsBox.query().build().find();

        Log.d(TAG, "setAnimals: " + Animals_.userId);

        animalsArrayList.addAll(animalsList);
        Log.d(TAG, "setAnimals: animal list was set here " + animalsArrayList);
    }
}
