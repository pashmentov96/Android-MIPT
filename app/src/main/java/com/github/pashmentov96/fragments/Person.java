package com.github.pashmentov96.fragments;

import com.google.gson.annotations.SerializedName;

public class Person {
    private int id;
    private String name;
    @SerializedName("info")
    private String note;
    @SerializedName("image")
    private String imageURL;

    public Person(int id, String name, String note, String imageURL) {
        this.id = id;
        this.name = name;
        this.note = note;
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNote() {
        return note;
    }

    public String getImageURL() {
        return imageURL;
    }
}
