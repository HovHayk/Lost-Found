package com.example.LostFound.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "User")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "user_id")
    public String uid;
    @ColumnInfo(name = "user_name")
    public String name;
    @ColumnInfo(name = "user_city")
    public String city;
    @ColumnInfo(name = "user_email")
    public String email;
    @ColumnInfo(name = "user_phone")
    public String phone;

    public User(String name, String city, String uid, String email, String phone) {
        this.name = name;
        this.city = city;
        this.uid = uid;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}