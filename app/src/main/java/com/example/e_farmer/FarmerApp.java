package com.example.e_farmer;

import android.app.Application;
import android.content.Context;

import com.example.e_farmer.Settings;
import com.example.e_farmer.models.MyObjectBox;

import io.objectbox.BoxStore;

public class FarmerApp extends Application {
    private static BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        init(this);
        // init Settings shared preference
        Settings.init(this);

    }

    public static void init(Context context) {
        boxStore = MyObjectBox.builder()
                .androidContext(context.getApplicationContext())
                .build();
    }


    public static BoxStore getBoxStore() {
        return boxStore;
    }
}
