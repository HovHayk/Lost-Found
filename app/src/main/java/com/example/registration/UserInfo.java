package com.example.registration;

public class UserInfo {

    public String userName;
    public String userCity;
    public String userId;
    public String userEmail;
    public String userPhone;

    public UserInfo(String name, String city, String id, String email, String phone) {
        this.userName = name;
        this.userCity = city;
        this.userId = id;
        this.userEmail = email;
        this.userPhone = phone;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }



    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String city) {
        this.userCity = city;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String id) {
        this.userId = id;
    }



    public String getEmail() {
        return userEmail;
    }

    public void setEmail(String email) {
        this.userEmail = email;
    }



    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String phone) {
        this.userPhone = phone;
    }
}