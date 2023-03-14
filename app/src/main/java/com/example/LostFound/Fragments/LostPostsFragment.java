package com.example.LostFound.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.LostFound.Adapters.Adapter;
import com.example.LostFound.Adapters.RecyclerViewAdapter;
import com.example.LostFound.Models.Posts;
import com.example.LostFound.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class LostPostsFragment extends Fragment {


    DatabaseReference databaseReference;

    View v;
    List<Posts> list;
    RecyclerView recyclerView;


    Adapter adapter;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_lost_posts, container, false);


        databaseReference = FirebaseDatabase.getInstance().getReference("Posts").child("Lost");
        recyclerView = v.findViewById(R.id.recyclerView);

        list = new ArrayList<Posts>();


        FirebaseRecyclerOptions<Posts> options = new FirebaseRecyclerOptions.Builder<Posts>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Posts").child("Lost"), Posts.class)
                        .build();

        Log.i("POMODORO", "onCreateView: " + options);


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


















    /*@Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setIconified(true);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mainSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mainSearch(query);
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void mainSearch(String query) {

        FirebaseRecyclerOptions<Posts> options = new FirebaseRecyclerOptions.Builder<Posts>()
                .setQuery(databaseReference.orderByChild("tags").startAt(query).endAt(query + "\uf8ff"), Posts.class)
                .build();

        PostAdapter mainAdapter = new PostAdapter(options);
        mainAdapter.startListening();
        recyclerView.setAdapter(mainAdapter);
    }*/










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