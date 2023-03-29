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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.LostFound.Adapters.FoundPostAdapter;
import com.example.LostFound.Database.PostViewModel;
import com.example.LostFound.Models.FoundPost;
import com.example.LostFound.Models.LostPost;
import com.example.LostFound.R;

import java.util.ArrayList;
import java.util.List;

public class FoundPostsFragment extends Fragment {

    private PostViewModel postViewModel;

    View v;
    ArrayList<LostPost> list;
    RecyclerView recyclerView;
    FoundPostAdapter adapter;


    public FoundPostsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
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

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        postViewModel.getAllFoundPosts().observe(getViewLifecycleOwner(), new Observer<List<FoundPost>>() {
            @Override
            public void onChanged(List<FoundPost> posts) {
                adapter.setFoundPosts(posts);
            }
        });
    }

    @Override
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
        ArrayList<FoundPost> filteredList = new ArrayList<FoundPost>();

        postViewModel.getAllFoundPosts().observe(getViewLifecycleOwner(), new Observer<List<FoundPost>>() {
            @Override
            public void onChanged(List<FoundPost> posts) {
                for (FoundPost item : posts) {
                    if (item.getTags().contains(text.toLowerCase())) {
                        filteredList.add(item);
                    }
                }
                if (filteredList.isEmpty()) {
                    adapter.deleteFoundPosts(filteredList);
                    Toast.makeText(getContext(), "There is no such posts!", Toast.LENGTH_SHORT).show();;
                } else {
                    Log.i("KLOR", "onChanged: " + filteredList);
                    adapter.setFoundPosts(filteredList);
                }
            }
        });
    }
}