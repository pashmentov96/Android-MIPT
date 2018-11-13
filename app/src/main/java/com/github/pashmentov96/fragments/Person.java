package com.github.pashmentov96.fragments;

import com.google.gson.annotations.SerializedName;

public class Person {
    private String name;
    @SerializedName("info")
    private String note;
    @SerializedName("image")
    private String imageURL;

    public Person(String name, String note, String imageURL) {
        this.name = name;
        this.note = note;
        this.imageURL = imageURL;
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

    @Override
    public String toString() {
        return name + ";" + note + ";" + imageURL;
    }
}
