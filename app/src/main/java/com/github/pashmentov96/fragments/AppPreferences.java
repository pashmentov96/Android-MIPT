package com.github.pashmentov96.fragments;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    private static final String MY_PREFERENCES = "AppPreferences";
    private static final String FLAG_IS_LOADED = "Is loaded from Internet";

    private SharedPreferences sharedPreferences;

    public AppPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
    }

    public boolean isLoadedFromNet() {
        return sharedPreferences.getBoolean(FLAG_IS_LOADED, false);
    }

    public void setLoadedFromNet(boolean variable) {
        sharedPreferences.edit().putBoolean(FLAG_IS_LOADED, variable).apply();
    }

}
