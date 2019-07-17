package com.example.e_farmer.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.e_farmer.Settings;
import com.example.e_farmer.database.AppDatabase;
import com.example.e_farmer.database.FinanceDao;
import com.example.e_farmer.models.Finance;

import java.util.List;

public class FinanceRepository {
    private static final String TAG = "FinanceRepository";

    private FinanceDao financeDao;
    private LiveData<List<Finance>> financeList;

    public FinanceRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        financeDao = database.financeDao();
        financeList = financeDao.getAllFinance(Settings.getUserId());
    }

    public void insert(Finance finance) {
        new InsertFinanceAsyncTask(financeDao).execute(finance);
    }

    public void update(Finance finance) {
        new UpdateFinanceAsyncTask(financeDao).execute(finance);
    }

    public void delete(Finance finance) {
        new DeleteFinanceAsyncTask(financeDao).execute(finance);
    }

    public LiveData<List<Finance>> getFinance() {
        return financeList;
    }

    private static class InsertFinanceAsyncTask extends AsyncTask<Finance, Void, Void> {

        private FinanceDao financeDao;

        private InsertFinanceAsyncTask(FinanceDao financeDao) {
            this.financeDao = financeDao;
        }

        @Override
        protected Void doInBackground(Finance... finances) {
            financeDao.insert(finances[0]);
            return null;
        }
    }

    private static class UpdateFinanceAsyncTask extends AsyncTask<Finance, Void, Void> {
        private FinanceDao financeDao;

        private UpdateFinanceAsyncTask(FinanceDao financeDao) {
            this.financeDao = financeDao;
        }

        @Override
        protected Void doInBackground(Finance... finances) {
            financeDao.update(finances[0]);
            return null;
        }
    }

    private static class DeleteFinanceAsyncTask extends AsyncTask<Finance, Void, Void> {
        private FinanceDao financeDao;

        private DeleteFinanceAsyncTask(FinanceDao financeDao) {
            this.financeDao = financeDao;
        }

        @Override
        protected Void doInBackground(Finance... finances) {
            financeDao.delete(finances[0]);
            return null;
        }
    }

}