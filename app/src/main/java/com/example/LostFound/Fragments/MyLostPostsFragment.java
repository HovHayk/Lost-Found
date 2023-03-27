package com.example.LostFound.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.LostFound.Models.Post;
import com.example.LostFound.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class MyLostPostsFragment extends Fragment {


    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;
    CollectionReference collectionReference;

    View v;
    ArrayList<Post> list;
    RecyclerView recyclerView;


    MenuItem menuItem;
    SearchView searchView;

    String id;

    public MyLostPostsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

        collectionReference = FirebaseFirestore.getInstance().collection("Lost Post");
        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        list = new ArrayList<Post>();
        id = auth.getCurrentUser().getUid();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_posts, container, false);

        collectionReference = FirebaseFirestore.getInstance().collection("Lost Post");
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = v.findViewById(R.id.recyclerView);
        auth = FirebaseAuth.getInstance();

        id = auth.getCurrentUser().getUid();
        list = new ArrayList<Post>();


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        return v;
    }


    /*public void getPostData() {

        firebaseFirestore.collection("Lost Post")
                .whereEqualTo("id", id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot snapshot : task.getResult()) {
                                Post posts = new Post();
                                posts.id = snapshot.getId();
                                posts.name = snapshot.get("name").toString();
                                posts.description = snapshot.get("description").toString();
                                posts.location = snapshot.get("location").toString();
                                posts.image = snapshot.get("image").toString();
                                posts.tags = (ArrayList<String>) snapshot.get("tags");
                                list.add(posts);
                            }
                            adapter = new PostsAdapter(getActivity(), list, MyLostPostsFragment.this);
                            recyclerView.setAdapter(adapter);

                        }
                    }
                });

    }

    @Override
    public void onPostClicked(int position) {
        firebaseFirestore.collection("Lost Post")
                .document()
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot snapshot = task.getResult();
                            if (snapshot.exists()) {
                                Post posts = new Post();
                                posts.name = snapshot.getString("name");
                                posts.description = snapshot.getString("name");
                                posts.location = snapshot.getString("name");
                                posts.image = snapshot.getString("name");
                                posts.tags = (ArrayList<String>) snapshot.get("tags");

                                Intent intent = new Intent(getActivity(), PostPage.class);
                                intent.putExtra("NAME", posts.name);
                                intent.putExtra("DESCRIPTION", posts.description);
                                intent.putExtra("LOCATION", posts.location);
                                intent.putExtra("IMAGE", posts.image);
                                intent.putExtra("TAGS", posts.tags);
                                startActivity(intent);
                            }
                        }
                    }
                });


    }*/


}