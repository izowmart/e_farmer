package com.example.e_farmer.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.e_farmer.Settings;
import com.example.e_farmer.database.AppDatabase;
import com.example.e_farmer.database.MachineDao;
import com.example.e_farmer.models.Machine;

import java.util.List;


public class MachineRepository {
    private static final String TAG = "MachineRepository";
    private MachineDao machineDao;
    private LiveData<List<Machine>> machineList;

    public MachineRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        machineDao = database.machineDao();
        machineList = machineDao.getAllMachines(Settings.getUserId());
    }

    public void insert(Machine machine) {
        new InsertMachineAsyncTask(machineDao).execute(machine);
    }

    public void update(Machine machine) {
        new UpdateMachineAsyncTask(machineDao).execute(machine);
    }

    public void delete(Machine machine) {
        new DeleteMachineAsyncTask(machineDao).execute(machine);
    }

    public LiveData<List<Machine>> getMachine() {
        return machineList;
    }

    private static class InsertMachineAsyncTask extends AsyncTask<Machine, Void, Void> {

        private MachineDao machineDao;

        private InsertMachineAsyncTask(MachineDao machineDao) {
            this.machineDao = machineDao;
        }

        @Override
        protected Void doInBackground(Machine... machine) {
            machineDao.insert(machine[0]);
            return null;
        }
    }

    private static class UpdateMachineAsyncTask extends AsyncTask<Machine, Void, Void> {
        private MachineDao machineDao;

        private UpdateMachineAsyncTask(MachineDao machineDao) {
            this.machineDao = machineDao;
        }

        @Override
        protected Void doInBackground(Machine... machine) {
            machineDao.update(machine[0]);
            return null;
        }
    }

    private static class DeleteMachineAsyncTask extends AsyncTask<Machine, Void, Void> {
        private MachineDao machineDao;

        private DeleteMachineAsyncTask(MachineDao machineDao) {
            this.machineDao = machineDao;
        }

        @Override
        protected Void doInBackground(Machine... machine) {
            machineDao.delete(machine[0]);
            return null;
        }
    }

}
