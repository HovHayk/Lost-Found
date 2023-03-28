package com.example.LostFound.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.LostFound.Adapters.MyFoundPostAdapter;
import com.example.LostFound.Adapters.MyLostPostAdapter;
import com.example.LostFound.Database.PostViewModel;
import com.example.LostFound.Models.LostPost;
import com.example.LostFound.Models.MyFoundPost;
import com.example.LostFound.Models.MyLostPost;
import com.example.LostFound.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MyFoundPostsFragment extends Fragment {

    private PostViewModel postViewModel;
    RecyclerView recyclerView;
    MyFoundPostAdapter adapter;

    public MyFoundPostsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        adapter = new MyFoundPostAdapter();

        return inflater.inflate(R.layout.fragment_posts, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        postViewModel.getAllMyFoundPosts().observe(getViewLifecycleOwner(), new Observer<List<MyFoundPost>>() {
            @Override
            public void onChanged(List<MyFoundPost> posts) {
                adapter.setMyFoundPosts(posts);
            }
        });
    }
}
