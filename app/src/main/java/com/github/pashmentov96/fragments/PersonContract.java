package com.github.pashmentov96.fragments;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class PersonContract {
    public static final String TABLE_NAME = "person_table";

    public interface Columns extends BaseColumns {
        String NAME = "name";
        String NOTE = "note";
        String IMAGE_URL = "ImageURL";
    }

    public static void createTable(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME
                        + " ( "
                        + PersonContract.Columns._ID + " INTEGER PRIMARY KEY,"
                        + PersonContract.Columns.NAME + " TEXT NOT NULL,"
                        + PersonContract.Columns.NOTE + " TEXT NOT NULL,"
                        + PersonContract.Columns.IMAGE_URL + " TEXT NOT NULL"
                        + " );"
        );
    }
}
