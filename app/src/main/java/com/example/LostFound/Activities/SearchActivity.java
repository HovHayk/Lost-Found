package com.example.LostFound.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.LostFound.Adapters.FoundPostAdapter;
import com.example.LostFound.Adapters.LostPostAdapter;
import com.example.LostFound.Database.PostViewModel;
import com.example.LostFound.Fragments.FoundPostsFragment;
import com.example.LostFound.Fragments.FoundSearchFragment;
import com.example.LostFound.Fragments.LostPostsFragment;
import com.example.LostFound.Fragments.LostSearchFragment;
import com.example.LostFound.Helpers.SearchHelper;
import com.example.LostFound.Models.FoundPost;
import com.example.LostFound.Models.LostPost;
import com.example.LostFound.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private PostViewModel postViewModel;

    ProgressBar progressBar;
    BottomNavigationView bottomNavigationView;
    TextView back, error, suggestion;
    EditText search;
    RecyclerView recyclerView;

    LostPostAdapter lostPostAdapter;
    FoundPostAdapter foundPostAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        progressBar = findViewById(R.id.progress_bar);
        error = findViewById(R.id.text_error_message);
        suggestion = findViewById(R.id.text_suggestion_message);
        back = findViewById(R.id.ic_back);
        search = findViewById(R.id.search);
        recyclerView = findViewById(R.id.posts_recycler_view);
        bottomNavigationView = findViewById(R.id.btmNavView);

        lostPostAdapter = new LostPostAdapter();
        foundPostAdapter = new FoundPostAdapter();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        progressBar.setVisibility(View.INVISIBLE);
        suggestion.setVisibility(View.VISIBLE);
        setBottomNavigationView();

    }

    public void setBottomNavigationView() {

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.btmnav_lost:
                    suggestion.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    setFragment(new LostSearchFragment());
                    searchMethodForLost();
                    return true;
                case R.id.btmnav_found:
                    suggestion.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    setFragment(new FoundSearchFragment());
                    searchMethodForFound();
                    return true;
                default:
                    return false;
            }
        });
    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    public void searchMethodForLost() {

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                recyclerView.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                error.setVisibility(View.INVISIBLE);

                postViewModel = new ViewModelProvider(SearchActivity.this).get(PostViewModel.class);
                postViewModel.getAllLostPosts().observe(SearchActivity.this, new Observer<List<LostPost>>() {
                    @Override
                    public void onChanged(List<LostPost> posts) {

                        List<LostPost> searchResult = SearchHelper.searchInLostPosts(charSequence.toString(), posts);

                        (new Handler()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.INVISIBLE);
                                if (searchResult.isEmpty()) {
                                    recyclerView.setVisibility(View.INVISIBLE);
                                    error.setVisibility(View.VISIBLE);
                                } else if (search == null) {
                                    recyclerView.setVisibility(View.INVISIBLE);
                                    error.setVisibility(View.INVISIBLE);
                                    progressBar.setVisibility(View.VISIBLE);
                                } else {
                                    recyclerView.setVisibility(View.VISIBLE);
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                    recyclerView.setAdapter(lostPostAdapter);
                                    lostPostAdapter.setLostPosts(searchResult);
                                }
                            }
                        }, 500);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void searchMethodForFound() {

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                recyclerView.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                error.setVisibility(View.INVISIBLE);

                postViewModel = new ViewModelProvider(SearchActivity.this).get(PostViewModel.class);
                postViewModel.getAllFoundPosts().observe(SearchActivity.this, new Observer<List<FoundPost>>() {
                    @Override
                    public void onChanged(List<FoundPost> posts) {
                        List<FoundPost> searchResult = SearchHelper.searchInFoundPosts(charSequence.toString(), posts);

                        (new Handler()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                                if (searchResult.isEmpty()) {
                                    foundPostAdapter.deleteFoundPosts(searchResult);
                                    error.setVisibility(View.VISIBLE);
                                } else {
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                    recyclerView.setAdapter(foundPostAdapter);
                                    foundPostAdapter.setFoundPosts(searchResult);
                                }
                            }
                        }, 500);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}