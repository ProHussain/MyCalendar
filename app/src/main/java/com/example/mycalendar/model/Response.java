package com.example.mycalendar.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {
    @SerializedName("Categories")
    private List<Category> categories;

    @SerializedName("Ads")
    private Ads ads;

    public List<Category> getCategories() {
        return categories;
    }

    public Ads getAds() {
        return ads;
    }
}
