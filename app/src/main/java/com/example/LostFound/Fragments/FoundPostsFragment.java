package com.example.LostFound.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.LostFound.Adapters.RecyclerViewAdapter;
import com.example.LostFound.Models.Posts;
import com.example.LostFound.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FoundPostsFragment extends Fragment {


    DatabaseReference databaseReference;

    View v;
    RecyclerViewAdapter adapter;
    List<Posts> list;
    RecyclerView recyclerView;


    public FoundPostsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        databaseReference = FirebaseDatabase.getInstance().getReference("Posts").child("Found");
        list = new ArrayList<Posts>();
        getPostData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_found_posts, container, false);

        recyclerView = v.findViewById(R.id.recyclerView);

        databaseReference = FirebaseDatabase.getInstance().getReference("Posts").child("Found");
        list = new ArrayList<Posts>();

        adapter = new RecyclerViewAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    public void getPostData() {

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Posts posts = new Posts(snapshot.child("Name").getValue().toString()
                        , snapshot.child("Location").getValue().toString()
                        , snapshot.child("Description").getValue().toString()
                        , snapshot.child("image").getValue().toString());
                list.add(posts);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}