package com.example.LostFound.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.LostFound.Models.Post;
import com.example.LostFound.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

@Database(entities = {Post.class, User.class}, exportSchema = false, version = 1)
public abstract class LostFoundDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "project_database.db";
    public static LostFoundDatabase instance;

    public abstract PostDAO postDAO();

    public static synchronized LostFoundDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext()
                    , LostFoundDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    public static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private PostDAO postDAO;
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


        private PopulateDbAsyncTask(LostFoundDatabase db) {
            postDAO = db.postDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {

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
            return null;
        }
    }
}

