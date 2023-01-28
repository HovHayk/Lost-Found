package com.example.registration;

public class NewPost {
    String name;
    String place;
    String description;
    String category;
    String image;

    public NewPost(String name, String place, String description, String category, String image) {
        this.name = name;
        this.place = place;
        this.description = description;
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }
}