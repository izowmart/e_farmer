package com.example.e_farmer.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.e_farmer.Settings;
import com.example.e_farmer.database.AnimalTreatmentDao;
import com.example.e_farmer.database.AppDatabase;
import com.example.e_farmer.models.AnimalTreatment;

import java.util.List;

public class AnimalTreatmentRepository {

    private static final String TAG = "AnimalTreatmentRepository";

    private AnimalTreatmentDao animalTreatmentDao;
    private LiveData<List<AnimalTreatment>> animalTreatmentList;

    public AnimalTreatmentRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        animalTreatmentDao = database.animalTreatmentDao();
        animalTreatmentList = animalTreatmentDao.getAllTreatment(Settings.getUserId());
    }

    public void insert(AnimalTreatment treatment) {
        new InsertAnimalTreatmentAsyncTask(animalTreatmentDao).execute(treatment);
    }

    public void update(AnimalTreatment treatment) {
        new UpdateAnimalTreatmentAsyncTask(animalTreatmentDao).execute(treatment);
    }

    public void delete(AnimalTreatment treatment) {
        new DeleteAnimalTreatmentAsyncTask(animalTreatmentDao).execute(treatment);
    }

    public LiveData<List<AnimalTreatment>> getAnimalTreatment() {
        return animalTreatmentList;
    }

    private static class InsertAnimalTreatmentAsyncTask extends AsyncTask<AnimalTreatment, Void, Void> {

        private AnimalTreatmentDao animalTreatmentDao;

        private InsertAnimalTreatmentAsyncTask(AnimalTreatmentDao animalTreatmentDao) {
            this.animalTreatmentDao = animalTreatmentDao;
        }

        @Override
        protected Void doInBackground(AnimalTreatment... treatment) {
            animalTreatmentDao.insert(treatment[0]);
            return null;
        }
    }

    private static class UpdateAnimalTreatmentAsyncTask extends AsyncTask<AnimalTreatment, Void, Void> {
        private AnimalTreatmentDao animalTreatmentDao;

        private UpdateAnimalTreatmentAsyncTask(AnimalTreatmentDao animalTreatmentDao) {
            this.animalTreatmentDao = animalTreatmentDao;
        }

        @Override
        protected Void doInBackground(AnimalTreatment... treatment) {
            animalTreatmentDao.update(treatment[0]);
            return null;
        }
    }

    private static class DeleteAnimalTreatmentAsyncTask extends AsyncTask<AnimalTreatment, Void, Void> {
        private AnimalTreatmentDao animalTreatmentDao;

        private DeleteAnimalTreatmentAsyncTask(AnimalTreatmentDao animalTreatmentDao) {
            this.animalTreatmentDao = animalTreatmentDao;
        }

        @Override
        protected Void doInBackground(AnimalTreatment... treatment) {
            animalTreatmentDao.delete(treatment[0]);
            return null;
        }
    }

}