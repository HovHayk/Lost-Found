package com.example.LostFound.Fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.LostFound.Models.Post;
import com.example.LostFound.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class FoundPostsFragment extends Fragment {

    FirebaseFirestore firebaseFirestore;
    CollectionReference collectionReference;

    View v;
    ArrayList<Post> list;
    RecyclerView recyclerView;
    CardView cardView;

    public FoundPostsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

        firebaseFirestore = FirebaseFirestore.getInstance();
        list = new ArrayList<Post>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_posts, container, false);

        collectionReference = FirebaseFirestore.getInstance().collection("Found Post");
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = v.findViewById(R.id.recyclerView);
        cardView = v.findViewById(R.id.recycler_row_card);

        list = new ArrayList<Post>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        return v;

    }


    /*public void getPostData() {

        firebaseFirestore.collection("Found Post")
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
                            adapter = new PostsAdapter(getActivity(), list, FoundPostsFragment.this);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                });
    }

    @Override
    public void onPostClicked(int position) {
        firebaseFirestore.collection("Lost Post")
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
                            }
                        }
                    }
                });
    }*/

}