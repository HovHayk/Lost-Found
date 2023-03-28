package com.example.LostFound.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.LostFound.Models.FoundPost;
import com.example.LostFound.Models.LostPost;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class PostViewModel extends AndroidViewModel {
    private PostRepository repository;
    private PostDAO postDAO;
    private LostFoundDatabase database;
    private LiveData<List<LostPost>> allLostPosts;
    private LiveData<List<FoundPost>> allFoundPosts;
    private FirebaseFirestore firebaseFirestore;

    public PostViewModel(@NonNull Application application) {
        super(application);

        database = LostFoundDatabase.getInstance(application);
        postDAO = database.postDAO();
        repository = new PostRepository(application);
        allLostPosts = repository.getAllPosts();
        allFoundPosts = repository.getAllFoundPost();
    }

    public void insert(LostPost post) {
        repository.insert(post);
    }

    public void update(LostPost post) {
        repository.update(post);
    }

    public void delete(LostPost post) {
        repository.delete(post);
    }

    public void deleteAllPosts() {
        repository.deleteAllPosts();
    }


    public LiveData<List<LostPost>> getAllLostPosts() {
        firebaseFirestore = FirebaseFirestore.getInstance();

        AsyncTask.execute(() -> {
            firebaseFirestore.collection("Lost Posts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {

                        AsyncTask.execute(() -> {
                            postDAO.deleteAllLostPosts();
                        });

                        for (DocumentSnapshot snapshot : task.getResult()) {
                            String name = snapshot.getString("name");
                            String location = snapshot.getString("location");
                            String description = snapshot.getString("description");
                            String image = snapshot.getString("image");
                            String uid = snapshot.getString("id");
                            //ArrayList<String> tags = snapshot.get("tags", ArrayList<String>.class);

                            AsyncTask.execute(() -> {
                                postDAO.insertLostPost(new LostPost(name, location, description, image));
                            });

                        }
                    }
                }
            });
        });
        return allLostPosts;
    }

    public LiveData<List<FoundPost>> getAllFoundPosts() {
        firebaseFirestore = FirebaseFirestore.getInstance();

        AsyncTask.execute(() -> {
            firebaseFirestore.collection("Found Posts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {

                        AsyncTask.execute(() -> {
                            postDAO.deleteAllFoundPosts();
                        });

                        for (DocumentSnapshot snapshot : task.getResult()) {
                            String name = snapshot.getString("name");
                            String location = snapshot.getString("location");
                            String description = snapshot.getString("description");
                            String image = snapshot.getString("image");
                            String uid = snapshot.getString("id");
                            //ArrayList<String> tags = snapshot.get("tags", ArrayList<String>.class);

                            AsyncTask.execute(() -> {
                                postDAO.insertFoundPost(new FoundPost(name, location, description, image));
                            });
                        }
                    }
                }
            });
        });
        return allFoundPosts;
    }
}
