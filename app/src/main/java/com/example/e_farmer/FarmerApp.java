package com.example.e_farmer;

import android.app.Application;

public class FarmerApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //        initialize preferences
        Settings.init(this);
    }
}
