package com.example.registration;

public class UserInfo {
    public String userName;
    public String userCity;
    public int userPhone;
//    public int userPosts;

    public UserInfo(String userName, String userCity, int userPhone) {
        this.userName = userName;
        this.userCity = userCity;
        this.userPhone = userPhone;
//        this.userPosts = userPosts;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserCity() {
        return userCity;
    }

    public int getUserPhone() {
        return userPhone;
    }

//    public int getUserPosts() {
//        return userPosts;
//    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public void setUserPhone(int userPhone) {
        this.userPhone = userPhone;
    }

//    public void setUserPosts(int userPosts) {
//        this.userPosts = userPosts;
//    }
}
