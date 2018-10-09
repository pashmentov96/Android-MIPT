package com.github.pashmentov96.androidapplication;

import java.util.ArrayList;
import java.util.List;

public class StorageOfPersons {
    private static List<Person> personList = new ArrayList<>();
    public static void generate() {
        for (int i = 0; i < 5; ++i) {
            personList.add(new Person(5 * i, "Max" + String.valueOf(i),
                    "Please, give me something", R.drawable.kotiki));
            personList.add(new Person(5 * i + 1, "Vasya" + String.valueOf(i),
                    "I am very pretty :)", R.drawable.kotiki1));
            personList.add(new Person(5 * i + 2, "Masha" + String.valueOf(i),
                    "Oh.. I very sad now", R.drawable.kotiki2));
            personList.add(new Person(5 * i + 3, "Musya" + String.valueOf(i),
                    "I like boxes", R.drawable.kotiki3));
            personList.add(new Person(5 * i + 4, "Boris" + String.valueOf(i),
                    "I like sleep", R.drawable.kotiki4));
        }
    }

    public static List<Person> getPersonList() {
        return personList;
    }

    public static Person getPersonById(long id) {
        for (Person person : personList) {
            if (person.getId() == id) {
                return person;
            }
        }
        return null;
    }
}
