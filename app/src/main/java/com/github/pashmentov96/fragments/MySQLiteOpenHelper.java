package com.github.pashmentov96.fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PersonDatabase.db";
    private static final int VERSION = 1;
    public static final String TABLE_NAME = "person_table";

    public interface Columns extends BaseColumns {
        String NAME = "name";
        String NOTE = "note";
        String IMAGE_URL = "ImageURL";
    }

    public MySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME
                        + " ( "
                        + Columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + Columns.NAME + " TEXT NOT NULL,"
                        + Columns.NOTE + " TEXT NOT NULL,"
                        + Columns.IMAGE_URL + " TEXT NOT NULL"
                        + " );"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
