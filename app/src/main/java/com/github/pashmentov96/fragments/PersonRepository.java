package com.github.pashmentov96.fragments;

import android.content.Context;
import android.util.Log;

import java.util.List;

public class PersonRepository {
    private AppPreferences appPreferences;
    private DatabasePersonStorage databasePersonStorage;

    public PersonRepository(Context context) {
        appPreferences = new AppPreferences(context);
        databasePersonStorage = new DatabasePersonStorage(context);
    }

    public List<Person> loadPersons() {
        if (appPreferences.isLoadedFromNet()) {
            return databasePersonStorage.loadPersons();
        } else {
            NetPersonLoader netPersonLoader = new NetPersonLoader();
            List<Person> personList = netPersonLoader.loadPersons();
            databasePersonStorage.addPersons(personList);
            appPreferences.setLoadedFromNet(true);
            return personList;
        }
    }
}
