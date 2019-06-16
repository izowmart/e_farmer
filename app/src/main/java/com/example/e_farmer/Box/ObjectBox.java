package com.example.e_farmer.Box;

import android.content.Context;

import com.example.e_farmer.models.MyObjectBox;

import io.objectbox.BoxStore;

public class ObjectBox {
    private static BoxStore boxStore;

    static void init(Context context) {
        boxStore = MyObjectBox.builder()
                .androidContext(context.getApplicationContext())
                .build();
    }


    public static BoxStore getBoxStore() {
        return boxStore;
    }
}
