package com.example.LostFound.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.LostFound.Adapters.FoundPostAdapter;
import com.example.LostFound.Database.PostViewModel;
import com.example.LostFound.Models.FoundPost;
import com.example.LostFound.R;

import java.util.List;

public class FoundPostsFragment extends Fragment {

    private PostViewModel postViewModel;

    RecyclerView recyclerView;
    FoundPostAdapter adapter;


    public FoundPostsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adapter = new FoundPostAdapter();

        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        postViewModel.getAllFoundPosts().observe(getViewLifecycleOwner(), new Observer<List<FoundPost>>() {
            @Override
            public void onChanged(List<FoundPost> posts) {
                adapter.setFoundPosts(posts);
            }
        });
    }
}