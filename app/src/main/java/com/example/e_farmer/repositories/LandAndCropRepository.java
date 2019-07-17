package com.example.e_farmer.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.e_farmer.Settings;
import com.example.e_farmer.database.AppDatabase;
import com.example.e_farmer.database.LandAndCropDao;
import com.example.e_farmer.models.LandAndCrop;

import java.util.List;

public class LandAndCropRepository {
    private static final String TAG = "LandAndCropRepository";

    private LandAndCropDao landAndCropDao;
    private LiveData<List<LandAndCrop>> landAndCropList;

    public LandAndCropRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        landAndCropDao = database.landAndCropDao();
        landAndCropList = landAndCropDao.getAllLands(Settings.getUserId());
    }

    public void insert(LandAndCrop land) {
        new InsertLandAndCropAsyncTask(landAndCropDao).execute(land);
    }

    public void update(LandAndCrop land) {
        new UpdateLandAndCropAsyncTask(landAndCropDao).execute(land);
    }

    public void delete(LandAndCrop land) {
        new DeleteLandAndCropAsyncTask(landAndCropDao).execute(land);
    }

    public LiveData<List<LandAndCrop>> getLandAndCrop() {
        return landAndCropList;
    }

    private static class InsertLandAndCropAsyncTask extends AsyncTask<LandAndCrop, Void, Void> {

        private LandAndCropDao landAndCropDao;

        private InsertLandAndCropAsyncTask(LandAndCropDao landAndCropDao) {
            this.landAndCropDao = landAndCropDao;
        }

        @Override
        protected Void doInBackground(LandAndCrop... land) {
            landAndCropDao.insert(land[0]);
            return null;
        }
    }

    private static class UpdateLandAndCropAsyncTask extends AsyncTask<LandAndCrop, Void, Void> {
        private LandAndCropDao landAndCropDao;

        private UpdateLandAndCropAsyncTask(LandAndCropDao landAndCropDao) {
            this.landAndCropDao = landAndCropDao;
        }

        @Override
        protected Void doInBackground(LandAndCrop... land) {
            landAndCropDao.update(land[0]);
            return null;
        }
    }

    private static class DeleteLandAndCropAsyncTask extends AsyncTask<LandAndCrop, Void, Void> {
        private LandAndCropDao landAndCropDao;

        private DeleteLandAndCropAsyncTask(LandAndCropDao landAndCropDao) {
            this.landAndCropDao = landAndCropDao;
        }

        @Override
        protected Void doInBackground(LandAndCrop... land) {
            landAndCropDao.delete(land[0]);
            return null;
        }
    }

}
