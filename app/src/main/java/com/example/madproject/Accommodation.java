package com.example.madproject;

public class Accommodation {
    private int imageResource; // Or String for URL if loading from network
    private String title;
    private String location;
    private String priceRange;

    public Accommodation(int imageResource, String title, String location, String priceRange) {
        this.imageResource = imageResource;
        this.title = title;
        this.location = location;
        this.priceRange = priceRange;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getPriceRange() {
        return priceRange;
    }
}
