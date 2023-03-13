package com.example.LostFound;

import java.util.ArrayList;

public class Posts {
    public String id;
    public String postName;
    public String postLocation;
    public String postDescription;
    public String postImage;
    public String postTags;



    public Posts(String name, String location, String description, String image) {
        this.postName = name;
        this.postLocation = location;
        this.postDescription = description;
        this.postImage = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostLocation() {
        return postLocation;
    }

    public void setPostLocation(String postLocation) {
        this.postLocation = postLocation;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getTags() {
        return postTags;
    }

    public void setTags(String tags) {
        this.postTags = tags;
    }
}

