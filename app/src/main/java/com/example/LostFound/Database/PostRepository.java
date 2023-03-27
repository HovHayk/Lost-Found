package com.example.LostFound.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.LostFound.Models.Post;

import java.util.List;

public class PostRepository {
    private PostDAO postDAO;
    private LiveData<List<Post>> allPosts;

    public PostRepository(Application application) {
        LostFoundDatabase database = LostFoundDatabase.getInstance(application);

        postDAO = database.postDAO();
        allPosts = postDAO.getAllPosts();
    }

    public void insert(Post post) {
        new InsertPostAsyncTask(postDAO).execute(post);
    }

    public void update(Post post) {
        new UpdatePostAsyncTask(postDAO).execute(post);
    }

    public void delete(Post post) {
        new DeletePostAsyncTask(postDAO).execute(post);
    }

    public void deleteAllPosts() {
        new DeleteAllPostsAsyncTask(postDAO).execute();
    }

    public LiveData<List<Post>> getAllPosts() {

        return allPosts;
    }

    private static class InsertPostAsyncTask extends AsyncTask<Post, Void, Void> {

        private PostDAO postDAO;

        private InsertPostAsyncTask(PostDAO postDAO) {
            this.postDAO = postDAO;
        }

        @Override
        protected Void doInBackground(Post... posts) {
            postDAO.insertPost(posts[0]);
            return null;
        }
    }

    private static class UpdatePostAsyncTask extends AsyncTask<Post, Void, Void> {

        private PostDAO postDAO;

        private UpdatePostAsyncTask(PostDAO postDAO) {
            this.postDAO = postDAO;
        }

        @Override
        protected Void doInBackground(Post... posts) {
            postDAO.updatePost(posts[0]);
            return null;
        }
    }

    private static class DeletePostAsyncTask extends AsyncTask<Post, Void, Void> {

        private PostDAO postDAO;

        private DeletePostAsyncTask(PostDAO postDAO) {
            this.postDAO = postDAO;
        }

        @Override
        protected Void doInBackground(Post... posts) {
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
            postDAO.deleteAllPosts();
            return null;
        }
    }
}
