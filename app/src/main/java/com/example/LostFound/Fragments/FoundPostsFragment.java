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
import com.example.LostFound.Adapters.Adapter;
import com.example.LostFound.Models.Posts;
import com.example.LostFound.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

public class FoundPostsFragment extends Fragment {


    DatabaseReference databaseReference;

    View v;
    List<Posts> list;
    RecyclerView recyclerView;


    Adapter adapter;

    MenuItem menuItem;
    SearchView searchView;




    public FoundPostsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);


        databaseReference = FirebaseDatabase.getInstance().getReference("Posts").child("Found");
        list = new ArrayList<Posts>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_lost_posts, container, false);


        databaseReference = FirebaseDatabase.getInstance().getReference("Posts").child("Found");
        recyclerView = v.findViewById(R.id.recyclerView);

        list = new ArrayList<Posts>();


        FirebaseRecyclerOptions<Posts> options = new FirebaseRecyclerOptions.Builder<Posts>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Posts").child("Found"), Posts.class)
                .build();


        adapter = new Adapter(options);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;

    }


    @Override
    public void onStart() {

        super.onStart();
        adapter.startListening();

    }

    @Override
    public void onStop() {

        super.onStop();
        adapter.stopListening();
    }


}


















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