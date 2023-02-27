package com.example.registration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LostPostsFragment extends Fragment {


    DatabaseReference databaseReference;

    View v;
    RecyclerViewAdapter adapter;
    List<Posts> list;
    RecyclerView recyclerView;


    public LostPostsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        databaseReference = FirebaseDatabase.getInstance().getReference("Posts").child("Lost");
        list = new ArrayList<Posts>();
        getPostData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_lost_posts, container, false);

        recyclerView = v.findViewById(R.id.recyclerView);

        databaseReference = FirebaseDatabase.getInstance().getReference("Posts").child("Lost");
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
                Posts posts = new Posts(Objects.requireNonNull(snapshot.child("Name").getValue()).toString()/*,snapshot.child("Place").getValue().toString()*/
                        , Objects.requireNonNull(snapshot.child("Description").getValue()).toString()
                        , Objects.requireNonNull(snapshot.child("image").getValue()).toString());
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