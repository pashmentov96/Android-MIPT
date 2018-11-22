package com.github.pashmentov96.fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PersonDatabase.db";
    private static final int VERSION = 1;

    public AppSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        PersonContract.createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
