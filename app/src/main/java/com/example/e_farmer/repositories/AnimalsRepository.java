package com.example.e_farmer.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.e_farmer.Settings;
import com.example.e_farmer.database.AnimalsDao;
import com.example.e_farmer.database.AppDatabase;
import com.example.e_farmer.models.Animals;


import java.util.List;

public class AnimalsRepository {
    private static final String TAG = "AnimalsRepository";
    private AnimalsDao animalsDao;
    private LiveData<List<Animals>> animalList;

    public AnimalsRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        animalsDao = database.animalsDao();
        animalList = animalsDao.getAllAnimals(Settings.getUserId());
    }

    public void insert(Animals animals) {
        new InsertAnimalsAsyncTask(animalsDao).execute(animals);
    }

    public void update(Animals animals) {
        new UpdateAnimalsAsyncTask(animalsDao).execute(animals);
    }

    public void delete(Animals animals) {
        new DeleteAnimalsAsyncTask(animalsDao).execute(animals);
    }

    public LiveData<List<Animals>> getAnimals() {
        return animalList;
    }

    private static class InsertAnimalsAsyncTask extends AsyncTask<Animals, Void, Void> {

        private AnimalsDao animalsDao;

        private InsertAnimalsAsyncTask(AnimalsDao animalsDao) {
            this.animalsDao = animalsDao;
        }

        @Override
        protected Void doInBackground(Animals... animals) {
            animalsDao.insert(animals[0]);
            return null;
        }
    }

    private static class UpdateAnimalsAsyncTask extends AsyncTask<Animals, Void, Void> {
        private AnimalsDao animalsDao;

        private UpdateAnimalsAsyncTask(AnimalsDao animalsDao) {
            this.animalsDao = animalsDao;
        }

        @Override
        protected Void doInBackground(Animals... animals) {
            animalsDao.update(animals[0]);
            return null;
        }
    }

    private static class DeleteAnimalsAsyncTask extends AsyncTask<Animals, Void, Void> {
        private AnimalsDao animalsDao;

        private DeleteAnimalsAsyncTask(AnimalsDao animalsDao) {
            this.animalsDao = animalsDao;
        }

        @Override
        protected Void doInBackground(Animals... animals) {
            animalsDao.delete(animals[0]);
            return null;
        }
    }

}
