package com.example.registration;

import android.widget.Spinner;

public class Posts {
    public String name;
    public String place;
    public String description;
//    String category;
    public String image;


    public Posts(String name, String place, String description, String image) {
        this.name = name;
        this.place = place;
        this.description = description;
//        this.category = category;
        this.image = image;
    }


    public String getName() {
        return name;
    }


    public String getPlace() {
        return place;
    }


    public String getDescription() {
        return description;
    }


    /*public String getCategory() {
        return category;
    }*/

    public String getImage() {
        return image;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

