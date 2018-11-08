package com.github.pashmentov96.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PersonRepository {
    private final MySQLiteOpenHelper mySQLiteOpenHelper;

    public PersonRepository(Context context) {
        mySQLiteOpenHelper = new MySQLiteOpenHelper(context);
    }

    public void addPerson(Person person) {
        SQLiteDatabase database = mySQLiteOpenHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteOpenHelper.Columns.NAME, person.getName());
        contentValues.put(MySQLiteOpenHelper.Columns.NOTE, person.getNote());
        contentValues.put(MySQLiteOpenHelper.Columns.IMAGE_URL, person.getImageURL());

        database.insert(MySQLiteOpenHelper.TABLE_NAME, null, contentValues);
    }

    public List<Person> loadPersons() {
        List<Person> personList = new ArrayList<>();

        SQLiteDatabase database = mySQLiteOpenHelper.getWritableDatabase();

        Cursor cursor = database.query(
                MySQLiteOpenHelper.TABLE_NAME,
                new String[] {MySQLiteOpenHelper.Columns._ID, MySQLiteOpenHelper.Columns.NAME, MySQLiteOpenHelper.Columns.NOTE, MySQLiteOpenHelper.Columns.IMAGE_URL},
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            Person person = new Person(
                    cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.Columns.NAME)),
                    cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.Columns.NOTE)),
                    cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.Columns.IMAGE_URL))
                    );
            personList.add(person);
        }
        cursor.close();
        return personList;
    }

    public Person findPersonById(int id) {
        Log.d("MyLogs", "I want to find " + id);
        SQLiteDatabase database = mySQLiteOpenHelper.getWritableDatabase();

        Cursor cursor = database.query(
                MySQLiteOpenHelper.TABLE_NAME,
                new String[] {MySQLiteOpenHelper.Columns._ID, MySQLiteOpenHelper.Columns.NAME, MySQLiteOpenHelper.Columns.NOTE, MySQLiteOpenHelper.Columns.IMAGE_URL},
                MySQLiteOpenHelper.Columns._ID + " = " + String.valueOf(id + 1),
                null,
                null,
                null,
                null
        );
        if (cursor.getCount() != 1) {
            return null;
        }
        Log.d("MyLogs", String.valueOf(cursor.getPosition()));
        cursor.moveToFirst();
        Person person = new Person(
                    cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.Columns.NAME)),
                    cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.Columns.NOTE)),
                    cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.Columns.IMAGE_URL))
        );
        cursor.close();
        Log.d("MyLogs", person.getName());
        return person;
    }
}
