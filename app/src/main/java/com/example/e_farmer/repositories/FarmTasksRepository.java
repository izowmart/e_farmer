package com.example.e_farmer.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.e_farmer.Settings;
import com.example.e_farmer.database.AppDatabase;
import com.example.e_farmer.database.FarmTaskDao;
import com.example.e_farmer.models.FarmTask;

import java.util.List;


public class FarmTasksRepository {
    private static final String TAG = "FarmTasksRepository";

    private FarmTaskDao farmTaskDao;
    private LiveData<List<FarmTask>> taskList;

    public FarmTasksRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        farmTaskDao = database.farmTaskDao();
        taskList = farmTaskDao.getAllTasks(Settings.getUserId());
    }

    public void insert(FarmTask tasks) {
        new InsertFarmTaskAsyncTask(farmTaskDao).execute(tasks);
    }

    public void update(FarmTask tasks) {
        new UpdateFarmTaskAsyncTask(farmTaskDao).execute(tasks);
    }

    public void delete(FarmTask tasks) {
        new DeleteFarmTaskAsyncTask(farmTaskDao).execute(tasks);
    }

    public LiveData<List<FarmTask>> getFarmTask() {
        return taskList;
    }

    private static class InsertFarmTaskAsyncTask extends AsyncTask<FarmTask, Void, Void> {

        private FarmTaskDao farmTaskDao;

        private InsertFarmTaskAsyncTask(FarmTaskDao farmTaskDao) {
            this.farmTaskDao = farmTaskDao;
        }

        @Override
        protected Void doInBackground(FarmTask... tasks) {
            farmTaskDao.insert(tasks[0]);
            return null;
        }
    }

    private static class UpdateFarmTaskAsyncTask extends AsyncTask<FarmTask, Void, Void> {
        private FarmTaskDao farmTaskDao;

        private UpdateFarmTaskAsyncTask(FarmTaskDao farmTaskDao) {
            this.farmTaskDao = farmTaskDao;
        }

        @Override
        protected Void doInBackground(FarmTask... tasks) {
            farmTaskDao.update(tasks[0]);
            return null;
        }
    }

    private static class DeleteFarmTaskAsyncTask extends AsyncTask<FarmTask, Void, Void> {
        private FarmTaskDao farmTaskDao;

        private DeleteFarmTaskAsyncTask(FarmTaskDao farmTaskDao) {
            this.farmTaskDao = farmTaskDao;
        }

        @Override
        protected Void doInBackground(FarmTask... tasks) {
            farmTaskDao.delete(tasks[0]);
            return null;
        }
    }

}
