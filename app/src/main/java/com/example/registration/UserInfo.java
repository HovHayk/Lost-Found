package com.example.registration;

public class UserInfo {

    public String userName;
    public String userCity;
    public String userId;
    public String userEmail;
    public int userPhone;

    public UserInfo(String userName, String userCity, String userId, String email, int userPhone) {
        this.userName = userName;
        this.userCity = userCity;
        this.userId = userId;
        this.userEmail = email;
        this.userPhone = userPhone;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return userEmail;
    }

    public void setEmail(String email) {
        this.userEmail = email;
    }

    public int getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(int userPhone) {
        this.userPhone = userPhone;
    }
}