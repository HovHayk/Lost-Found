package com.example.LostFound;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class LostPostsFragment extends Fragment {


    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<Posts> options;

    View v;
    RecyclerViewAdapter adapter;
    PostAdapter postAdapter;
    List<Posts> list;
    RecyclerView recyclerView;

    MenuItem menuItem;
    SearchView searchView;


    public LostPostsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);


        databaseReference = FirebaseDatabase.getInstance().getReference("Posts").child("Lost");
        list = new ArrayList<Posts>();
        //getPostData();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_lost_posts, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("Posts").child("Lost");

        recyclerView = v.findViewById(R.id.recyclerView);

        list = new ArrayList<Posts>();

        FirebaseRecyclerOptions<Posts> options = new FirebaseRecyclerOptions.Builder<Posts>()
                .setQuery(databaseReference, Posts.class)
                .build();



        //adapter = new RecyclerViewAdapter(getContext(), list);
        postAdapter = new PostAdapter(options);
        recyclerView.setAdapter(postAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;

    }


    /*@Override
    public void onStart() {

        super.onStart();

        if (databaseReference != null) {

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            list.add(ds.getValue(Posts.class));
                        }
                        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), list);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }


*/










































    /*public void getPostData() {

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

    }*/
}