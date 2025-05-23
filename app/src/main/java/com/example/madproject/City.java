package com.example.madproject;

public class City {
    private int imageResId;
    private String name;

    public City(int imageResId, String name) {
        this.imageResId = imageResId;
        this.name = name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getName() {
        return name;
    }
}

