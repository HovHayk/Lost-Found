package com.example.LostFound.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.LostFound.Models.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class PostViewModel extends AndroidViewModel {
    private PostRepository repository;
    private PostDAO postDAO;
    LostFoundDatabase database;
    private LiveData<List<Post>> allPosts;
    private FirebaseFirestore firebaseFirestore;

    public PostViewModel(@NonNull Application application) {
        super(application);

        database = LostFoundDatabase.getInstance(application);
        postDAO = database.postDAO();
        repository = new PostRepository(application);
        allPosts = repository.getAllPosts();
    }

    public void insert(Post post) {
        repository.insert(post);
    }

    public void update(Post post) {
        repository.update(post);
    }

    public void delete(Post post) {
        repository.delete(post);
    }

    public void deleteAllPosts() {
        repository.deleteAllPosts();
    }

    public LiveData<List<Post>> getAllPosts() {
        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Lost Posts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (DocumentSnapshot snapshot: task.getResult()) {
                        String name = snapshot.getString("name");
                        String location = snapshot.getString("location");
                        String description = snapshot.getString("description");
                        String image = snapshot.getString("image");
                        String uid = snapshot.getString("id");
                        //ArrayList<String> tags = snapshot.get("tags", ArrayList<String>.class);

                        AsyncTask.execute(() -> {
                            postDAO.insertPost(new Post(name, location, description, image));
                        });
                    }
                }
            }
        });
        return allPosts;
    }
}
