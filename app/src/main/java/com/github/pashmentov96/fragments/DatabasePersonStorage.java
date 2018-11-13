package com.github.pashmentov96.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabasePersonStorage {
    private final AppSQLiteOpenHelper mySQLiteOpenHelper;
    private static final String LOG = "MyLogs";

    public DatabasePersonStorage(Context context) {
        mySQLiteOpenHelper = new AppSQLiteOpenHelper(context);
    }

    public void addPerson(Person person) {
        SQLiteDatabase database = mySQLiteOpenHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PersonContract.Columns.NAME, person.getName());
        contentValues.put(PersonContract.Columns.NOTE, person.getNote());
        contentValues.put(PersonContract.Columns.IMAGE_URL, person.getImageURL());

        database.insert(PersonContract.TABLE_NAME, null, contentValues);
    }

    public void addPersons(List<Person> personList) {
        for (int i = 0; i < personList.size(); ++i) {
            this.addPerson(personList.get(i));
        }
    }

    public List<Person> loadPersons() {
        List<Person> personList = new ArrayList<>();

        SQLiteDatabase database = mySQLiteOpenHelper.getWritableDatabase();

        Cursor cursor = null;
        try {
            cursor = database.query(
                    PersonContract.TABLE_NAME,
                    new String[]{PersonContract.Columns._ID, PersonContract.Columns.NAME, PersonContract.Columns.NOTE, PersonContract.Columns.IMAGE_URL},
                    null,
                    null,
                    null,
                    null,
                    null
            );

            while (cursor.moveToNext()) {
                Person person = new Person(
                        cursor.getString(cursor.getColumnIndex(PersonContract.Columns.NAME)),
                        cursor.getString(cursor.getColumnIndex(PersonContract.Columns.NOTE)),
                        cursor.getString(cursor.getColumnIndex(PersonContract.Columns.IMAGE_URL))
                );
                personList.add(person);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return personList;
    }

    public Person findPersonById(int id) {
        Log.d(LOG, "I want to find " + id);
        SQLiteDatabase database = mySQLiteOpenHelper.getWritableDatabase();

        Cursor cursor = null;
        Person person = null;
        try {
            cursor = database.query(
                    PersonContract.TABLE_NAME,
                    new String[]{PersonContract.Columns._ID, PersonContract.Columns.NAME, PersonContract.Columns.NOTE, PersonContract.Columns.IMAGE_URL},
                    PersonContract.Columns._ID + " = " + String.valueOf(id + 1),
                    null,
                    null,
                    null,
                    null
            );
            if (cursor.getCount() == 1) {
                Log.d(LOG, String.valueOf(cursor.getPosition()));
                cursor.moveToFirst();
                person = new Person(
                        cursor.getString(cursor.getColumnIndex(PersonContract.Columns.NAME)),
                        cursor.getString(cursor.getColumnIndex(PersonContract.Columns.NOTE)),
                        cursor.getString(cursor.getColumnIndex(PersonContract.Columns.IMAGE_URL))
                );
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        Log.d(LOG, person.getName());
        return person;
    }
}
