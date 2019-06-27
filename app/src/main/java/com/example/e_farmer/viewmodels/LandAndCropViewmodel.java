package com.example.e_farmer.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_farmer.models.LandAndCrop;
import com.example.e_farmer.repositories.LandAndCropRepository;

import java.util.List;

public class LandAndCropViewmodel extends ViewModel {
    private LandAndCropRepository landAndCropRepository;

    //    mutableLive data its changeable unlike LiveData which is observable only.mutableLiveData is a subclass of LiveData.
    private MutableLiveData<List<LandAndCrop>> mLandAndCrop;

    public void init(){
        if(mLandAndCrop != null){
//            here we check if our mLandAndCrop list has anything,if it has we terminate the process here and use what we already have
            return;
        }
        landAndCropRepository = LandAndCropRepository.getInstance();
        mLandAndCrop = landAndCropRepository.getLandAndCrop();
    }

    public LiveData<List<LandAndCrop>> getLandAndCrop(){
        return mLandAndCrop;
    }
}