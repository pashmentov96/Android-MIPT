package com.github.pashmentov96.fragments;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StorageOfPersons {
    private static List<Person> personList = new ArrayList<>();
    private static boolean isCached = false;
    public static void addPersons(List<Person> persons) {
        if (!isCached) {
            for (int i = 0; i < persons.size(); ++i) {
                Log.d("MyLogs", i + " " + persons.get(i).getName() + " " + persons.get(i).getNote() + " " + persons.get(i).getImageURL());
            }
            personList = persons;
            isCached = true;
        }
    }

    public static List<Person> getPersonList() {
        return personList;
    }

    public static Person getPerson(int position) {
       if (position >= personList.size()) {
           return null;
       }
       return personList.get(position);
    }

    public static boolean getIsCached() {
        return isCached;
    }
}
