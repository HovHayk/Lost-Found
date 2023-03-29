package com.example.LostFound.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "my_lost_post_table")
public class MyLostPost {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "u_id")
    public String uid;
    @ColumnInfo(name = "my_lost_p_name")
    public String name;
    @ColumnInfo(name = "my_lost_p_location")
    public String location;
    @ColumnInfo(name = "my_lost_p_description")
    public String description;
    @ColumnInfo(name = "my_lost_p_image")
    public String image;
    @ColumnInfo(name = "my_lost_p_tags")
    public String tags;

    @Ignore
    public MyLostPost() { }

    public MyLostPost(String name, String location, String description, String image, String tags) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.image = image;
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "LostPost{" +
                "id='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", tags=" + tags +
                '}';
    }
}


