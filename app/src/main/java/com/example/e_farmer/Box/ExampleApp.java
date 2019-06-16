package com.example.e_farmer.Box;

import android.app.Application;

public class ExampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ObjectBox.init(this);
    }

}
