package com.example.mycalendar.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {
    @SerializedName("image")
    private List<String> image;

    @SerializedName("logo")
    private List<String> logo;

    @SerializedName("name")
    private String name;

    public List<String> getImage() {
        return image;
    }

    public List<String> getLogo() {
        return logo;
    }

    public String getName() {
        return name;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public void setLogo(List<String> logo) {
        this.logo = logo;
    }

    public void setName(String name) {
        this.name = name;
    }
}
