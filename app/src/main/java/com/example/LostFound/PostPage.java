package com.example.LostFound;

public class PostPage {
    public String postName;
    public String postPlace;
    public String postDescription;
    public String postImage;

    public PostPage(String postName, String postPlace, String postDescription, String postImage) {
        this.postName = postName;
        this.postPlace = postPlace;
        this.postDescription = postDescription;
        this.postImage = postImage;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostPlace() {
        return postPlace;
    }

    public void setPostPlace(String postPlace) {
        this.postPlace = postPlace;
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
}
