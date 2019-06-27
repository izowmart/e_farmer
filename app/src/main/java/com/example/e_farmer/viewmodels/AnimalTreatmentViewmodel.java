package com.example.e_farmer.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_farmer.models.AnimalTreatment;
import com.example.e_farmer.repositories.AnimalTreatmentRepository;

import java.util.List;

public class AnimalTreatmentViewmodel extends ViewModel {
    private AnimalTreatmentRepository animalTreatmentRepo;

    //    mutableLive data its changeable unlike LiveData which is observable only.mutableLiveData is a subclass of LiveData.
    private MutableLiveData<List<AnimalTreatment>> mAnimalTreatment;

    public void init(){
        if(mAnimalTreatment != null){
//            here we check if our mAnimals list has anything,if it has we terminate the process here and use what we already have
            return;
        }
        animalTreatmentRepo = AnimalTreatmentRepository.getInstance();
        mAnimalTreatment = animalTreatmentRepo.getAnimalTreatment();
    }


    public LiveData<List<AnimalTreatment>> getAnimalTreatment(){
        return mAnimalTreatment;
    }
}
