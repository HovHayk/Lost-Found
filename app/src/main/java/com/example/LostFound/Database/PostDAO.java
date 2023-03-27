package com.example.LostFound.Database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.LostFound.Models.Post;

import java.util.List;

@Dao
public interface PostDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPost(Post posts);

    @Update
    void updatePost(Post posts);

    @Delete
    void deletePost(Post posts);

    @Query("DELETE FROM post_table")
    void deleteAllPosts();

    @Query("SELECT * FROM post_table ORDER BY p_name DESC")
    LiveData<List<Post>> getAllPosts();


    /*@Delete
    List<Integer> deleteListOfPosts(List<Post> posts); */
}
