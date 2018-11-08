package com.github.pashmentov96.fragments;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreferences {
    private static final String MY_PREFERENCES = "MyPreferences";
    private static final String FLAG_IS_LOADED = "Is loaded from Internet";

    private SharedPreferences sharedPreferences;

    public MyPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
    }

    public boolean getVariable() {
        return sharedPreferences.getBoolean(FLAG_IS_LOADED, false);
    }

    public void setVariable(boolean variable) {
        sharedPreferences.edit().putBoolean(FLAG_IS_LOADED, variable).apply();
    }

}
