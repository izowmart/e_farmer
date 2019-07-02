package com.example.e_farmer;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settings {
//    Keys for shared preference
//    This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "e_farmer";
//    check if the user is logged in or not
    public static final String LOGGED_IN_SHARED_PREF = "loggedin";
//    check for the current signed in user
    public static final String SIGNED_IN_USER_ID = "user_id";
//    check if its first time use
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLauch";
//    check if its facebook or google
    private static final String IS_FACEBOOK = "IsFacebook";

    /**
     * This class is responsible for handling the Shared Preferences, We saved the application
     * as logged here!
     * <p>
     * We are basically using this file to create and handle user sessions!
     * <p>
     * This file is cleared when the User Logs Out of the application
     */

    private static SharedPreferences settings;
    private static SharedPreferences defaultPrefs;

    public static void init(Context context){
        settings = context.getSharedPreferences(SHARED_PREF_NAME,0);
        defaultPrefs = PreferenceManager.getDefaultSharedPreferences(context);

    }
    /**
     * Set Logged in
     */
    public static void setUserId(long id){
        settings.edit()
                .putLong(SIGNED_IN_USER_ID,id)
                .apply();
    }
    public static long getUserId(){
        return settings.getLong(SIGNED_IN_USER_ID,-1);
    }
    public static boolean isLoggedIn(){
        return settings.getBoolean(LOGGED_IN_SHARED_PREF, false);

    }
    public static void setLoggedInSharedPref(boolean loggedIn){
        settings.edit()
                .putBoolean(LOGGED_IN_SHARED_PREF,loggedIn)
                .apply();
    }
    public static void setFirstTimeLaunch(boolean isFirstTime){
        settings.edit()
                .putBoolean(IS_FIRST_TIME_LAUNCH,isFirstTime)
                .apply();
    }
    public static boolean isFirstTimeLaunch() {
        return settings.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
    public static void setIsFacebook(boolean isFacebook) {
        settings.edit()
                .putBoolean(IS_FIRST_TIME_LAUNCH, isFacebook)
                .apply();
    }

    public static boolean isFacebook() {
        return settings.getBoolean(IS_FACEBOOK, true);
    }
}


