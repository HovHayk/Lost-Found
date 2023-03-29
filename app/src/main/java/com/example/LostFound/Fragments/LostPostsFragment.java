package com.example.LostFound.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import android.view.ViewGroup;
import android.widget.Toast;

import com.example.LostFound.Adapters.LostPostAdapter;
import com.example.LostFound.Database.PostViewModel;
import com.example.LostFound.Models.LostPost;
import com.example.LostFound.R;

import java.util.ArrayList;
import java.util.List;

public class LostPostsFragment extends Fragment {

    private PostViewModel postViewModel;
    private ArrayList<LostPost> lostPosts;

    RecyclerView recyclerView;
    LostPostAdapter adapter;

    public LostPostsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        adapter = new LostPostAdapter();
        lostPosts = new ArrayList<>();

        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lostPosts = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        postViewModel.getAllLostPosts().observe(getViewLifecycleOwner(), new Observer<List<LostPost>>() {
            @Override
            public void onChanged(List<LostPost> posts) {
                adapter.setLostPosts(posts);
            }
        });
    }

    /*@Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String tags) {
                filterPosts(tags);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }


    private void filterPosts(String text) {
        ArrayList<LostPost> filteredList = new ArrayList<LostPost>();

        postViewModel.getAllLostPosts().observe(getViewLifecycleOwner(), new Observer<List<LostPost>>() {
            @Override
            public void onChanged(List<LostPost> posts) {
                for (LostPost item : posts) {
                    if (item.getTags().contains(text.toLowerCase())) {
                        if (!filteredList.contains(item)) {
                            filteredList.add(item);
                        }
                    }
                }
                if (filteredList.isEmpty()) {
                    adapter.deleteLostPosts(filteredList);
                } else {
                    adapter.setLostPosts(filteredList);
                }
            }
        });
    }*/
}