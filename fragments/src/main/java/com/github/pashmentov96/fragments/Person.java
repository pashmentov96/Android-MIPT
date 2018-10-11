package com.github.pashmentov96.fragments;

public class Person {
    private long id;
    private String name;
    private String note;
    private int imageRes;

    public Person(long id, String name, String note, int imageRes) {
        this.id = id;
        this.name = name;
        this.note = note;
        this.imageRes = imageRes;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNote() {
        return note;
    }

    public int getImageRes() {
        return imageRes;
    }
}
