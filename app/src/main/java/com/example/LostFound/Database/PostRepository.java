package com.example.LostFound.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.LostFound.Models.FoundPost;
import com.example.LostFound.Models.LostPost;

import java.util.List;

public class PostRepository {
    private PostDAO postDAO;
    private LiveData<List<LostPost>> allLostPosts;
    private LiveData<List<FoundPost>> allFoundPosts;

    public PostRepository(Application application) {
        LostFoundDatabase database = LostFoundDatabase.getInstance(application);

        postDAO = database.postDAO();
        allLostPosts = postDAO.getAllLostPosts();
        allFoundPosts = postDAO.getAllFoundPosts();
    }

    public void insert(LostPost post) {
        new InsertPostAsyncTask(postDAO).execute(post);
    }

    public void update(LostPost post) {
        new UpdatePostAsyncTask(postDAO).execute(post);
    }

    public void delete(LostPost post) {
        new DeletePostAsyncTask(postDAO).execute(post);
    }

    public void deleteAllPosts() {
        new DeleteAllPostsAsyncTask(postDAO).execute();
    }

    public LiveData<List<LostPost>> getAllPosts() {
        return allLostPosts;
    }

    public LiveData<List<FoundPost>> getAllFoundPost() {
        return allFoundPosts;
    }

    private static class InsertPostAsyncTask extends AsyncTask<LostPost, Void, Void> {

        private PostDAO postDAO;

        private InsertPostAsyncTask(PostDAO postDAO) {
            this.postDAO = postDAO;
        }

        @Override
        protected Void doInBackground(LostPost... posts) {
            postDAO.insertLostPost(posts[0]);
            return null;
        }
    }

    private static class UpdatePostAsyncTask extends AsyncTask<LostPost, Void, Void> {

        private PostDAO postDAO;

        private UpdatePostAsyncTask(PostDAO postDAO) {
            this.postDAO = postDAO;
        }

        @Override
        protected Void doInBackground(LostPost... posts) {
            postDAO.updatePost(posts[0]);
            return null;
        }
    }

    private static class DeletePostAsyncTask extends AsyncTask<LostPost, Void, Void> {

        private PostDAO postDAO;

        private DeletePostAsyncTask(PostDAO postDAO) {
            this.postDAO = postDAO;
        }

        @Override
        protected Void doInBackground(LostPost... posts) {
            postDAO.deletePost(posts[0]);
            return null;
        }
    }

    private static class DeleteAllPostsAsyncTask extends AsyncTask<Void, Void, Void> {

        private PostDAO postDAO;

        private DeleteAllPostsAsyncTask(PostDAO postDAO) {
            this.postDAO = postDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            postDAO.deleteAllLostPosts();
            return null;
        }
    }
}