package com.example.LostFound.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.LostFound.Models.FoundPost;
import com.example.LostFound.Models.LostPost;

import java.util.List;

@Dao
public interface PostDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLostPost(LostPost posts);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFoundPost(FoundPost post);

    @Update
    void updatePost(LostPost posts);

    @Delete
    void deletePost(LostPost posts);

    @Query("DELETE FROM lost_post_table")
    void deleteAllLostPosts();

    @Query("DELETE FROM found_post_table" )
    void deleteAllFoundPosts();

    @Query("SELECT * FROM lost_post_table ORDER BY lost_p_name DESC")
    LiveData<List<LostPost>> getAllLostPosts();

    @Query("SELECT * FROM found_post_table ORDER BY found_p_name DESC")
    LiveData<List<FoundPost>> getAllFoundPosts();

    /*@Delete
    List<Integer> deleteListOfPosts(List<LostPost> posts); */

}
