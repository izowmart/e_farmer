package com.example.e_farmer.viewmodels;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_farmer.models.Animals;
import com.example.e_farmer.repositories.AnimalsRepository;

import java.util.List;

public class MyAnimalViewModel extends ViewModel {
    private AnimalsRepository animalsRepo;

    //    mutableLive data its changeable unlike LiveData which is observable only.mutableLiveData is a subclass of LiveData.
    private MutableLiveData<List<Animals>> mAnimals;

    public void init(){
        if(mAnimals != null){
//            here we check if our mAnimals list has anything,if it has we terminate the process here and use what we already have
            return;
        }
        animalsRepo = AnimalsRepository.getInstance();
        mAnimals = animalsRepo.getAnimals();
    }

    public LiveData<List<Animals>> getAnimal(){
        return mAnimals;
    }

}
