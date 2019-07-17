package com.example.e_farmer.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.example.e_farmer.database.AppDatabase;
import com.example.e_farmer.database.UserDao;
import com.example.e_farmer.models.User;


public class UserRepository {

    private static final String TAG = "UserRepository";
    public UserDao userDao;


    public UserRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        userDao = database.userDao();

    }

    public void insert(User user) {
        new InsertUserAsyncTask(userDao).execute(user);
    }

    public User getCurrentUser(String user_id) {
        Log.d(TAG, "getCurrentUser: with id "+ user_id);
       return userDao.getUser(user_id);

    }

    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao userDao;

        private InsertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... user) {
            userDao.insert(user[0]);
            return null;
        }
    }

}


