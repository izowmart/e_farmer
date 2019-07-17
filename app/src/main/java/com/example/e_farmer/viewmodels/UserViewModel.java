package com.example.e_farmer.viewmodels;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.e_farmer.models.User;
import com.example.e_farmer.repositories.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private static final String TAG = "UserViewModel";
    private UserRepository userRepository;
    private User user;
    private List<User> allUsers;

    public UserViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "UserViewModel: Instantiation of UserViewModel");

        if(allUsers == null){
            userRepository = new UserRepository(application);
        }

    }

    public void insert(User user) {
        userRepository.insert(user);
    }

    public User getCurrentUser(String user_id) {
        user = userRepository.getCurrentUser(user_id);
        return user;
    }


}