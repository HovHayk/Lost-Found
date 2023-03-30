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

import com.example.LostFound.Adapters.FoundPostAdapter;
import com.example.LostFound.Adapters.LostPostAdapter;
import com.example.LostFound.Database.PostViewModel;
import com.example.LostFound.Models.FoundPost;
import com.example.LostFound.Models.LostPost;
import com.example.LostFound.R;

import java.util.ArrayList;
import java.util.List;

public class FoundSearchFragment extends Fragment {

    RecyclerView recyclerView;
    FoundPostAdapter adapter;

    public FoundSearchFragment() {
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

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

    }
}