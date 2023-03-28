package com.example.LostFound.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "found_post_table")
public class FoundPost {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "u_id")
    public String uid;
    @ColumnInfo(name = "found_p_name")
    public String name;
    @ColumnInfo(name = "found_p_location")
    public String location;
    @ColumnInfo(name = "found_p_description")
    public String description;
    @ColumnInfo(name = "found_p_image")
    public String image;
    @ColumnInfo(name = "found_p_tags")
    public String tags;

    @Ignore
    public FoundPost() {
    }

    public FoundPost(String name, String location, String description, String image) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.image = image;
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