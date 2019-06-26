package com.example.e_farmer.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.e_farmer.FarmerApp;
import com.example.e_farmer.Settings;
import com.example.e_farmer.models.AnimalTreatment;
import com.example.e_farmer.models.Animals;
import com.example.e_farmer.models.Animals_;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class AnimalTreatmentRepository {

    private static final String TAG = "AnimalTreatmentReposito";

    private static AnimalTreatmentRepository instance;
    private ArrayList<AnimalTreatment> animalTreatmentArrayList = new ArrayList<>();
    private List<AnimalTreatment> animalTreatmentList;
    private Box<AnimalTreatment> animalTreatmentBox;
    private BoxStore farmerApp;

    public static AnimalTreatmentRepository getInstance(){
        if(instance == null){
            instance = new AnimalTreatmentRepository();

        }
        return instance;
    }
    //Through this method we are able to set data to our viewmodel
    public MutableLiveData<List<AnimalTreatment>> getAnimalTreatment() {
        setAnimalTreatment();
        MutableLiveData<List<AnimalTreatment>> data = new MutableLiveData<>();
        data.setValue(animalTreatmentArrayList);
        return data;
    }

    private void setAnimalTreatment() {
        long user_id = Settings.getUserId();
//        here we shall get our list item from the database.objectbox
//        objectBox initialization
        farmerApp = FarmerApp.getBoxStore();
        animalTreatmentBox = farmerApp.boxFor(AnimalTreatment.class);
        animalTreatmentList = animalTreatmentBox.query().build().find();

        Log.d(TAG, "setAnimals: " + Animals_.userId);

        animalTreatmentArrayList.addAll(animalTreatmentList);
        Log.d(TAG, "setAnimalsTreatment: animalTreatment list was set here " + animalTreatmentArrayList);
    }
}
