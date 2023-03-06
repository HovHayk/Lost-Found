package com.example.LostFound;

public class Posts {
    public String postName;
    public String postPlace;
    public String postDescription;
    public String postImage;


    public Posts(String name, String place, String description, String image) {
        this.postName = name;
        this.postPlace = place;
        this.postDescription = description;
        this.postImage = image;
    }


    public String getName() {
        return postName;
    }

    public String getPlace() {
        return postPlace;
    }

    public String getDescription() {
        return postDescription;
    }

    public String getImage() {
        return postImage;
    }

    public void setName(String name) {
        this.postName = name;
    }

    public void setPlace(String place) {
        this.postPlace = place;
    }

    public void setDescription(String description) {
        this.postDescription = description;
    }

    public void setImage(String image) {
        this.postImage = image;
    }
}

