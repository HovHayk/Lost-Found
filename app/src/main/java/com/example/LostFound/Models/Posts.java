package com.example.LostFound.Models;

import java.util.ArrayList;

public class Posts {
    public String id;
    public String name;
    public String location;
    public String description;
    public String image;
    public ArrayList<String> tags;

    public Posts() {

    }


    public Posts(String name, String location, String description, String image) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String Location) {
        this.location = Location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String Description) {
        this.description = Description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
}

