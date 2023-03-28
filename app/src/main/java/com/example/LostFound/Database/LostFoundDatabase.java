package com.example.LostFound.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.LostFound.Models.FoundPost;
import com.example.LostFound.Models.LostPost;
import com.example.LostFound.Models.User;
import com.google.firebase.firestore.FirebaseFirestore;

@Database(entities = {LostPost.class, FoundPost.class, User.class}, exportSchema = false, version = 2)
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

            return null;
        }
    }
}

