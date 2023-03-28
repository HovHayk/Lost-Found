package com.example.LostFound.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.LostFound.Models.LostPost;
import com.example.LostFound.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyFoundPostsFragment extends Fragment {


    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;

    View v;
    ArrayList<LostPost> list;
    RecyclerView recyclerView;


    MenuItem menuItem;
    SearchView searchView;

    String id;

    public MyFoundPostsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);


        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        list = new ArrayList<LostPost>();
        id = auth.getCurrentUser().getUid();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_posts, container, false);

        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = v.findViewById(R.id.recyclerView);
        auth = FirebaseAuth.getInstance();

        id = auth.getCurrentUser().getUid();
        list = new ArrayList<LostPost>();

        //getPostData();


        return v;

    }


    /*public void getPostData() {

        firebaseFirestore.collection("Found LostPost")
                .whereEqualTo("id", id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot snapshot : task.getResult()) {
                                LostPost posts = new LostPost();
                                posts.id = snapshot.getId();
                                posts.name = snapshot.get("name").toString();
                                posts.description = snapshot.get("description").toString();
                                posts.location = snapshot.get("location").toString();
                                posts.image = snapshot.get("image").toString();
                                posts.tags = (ArrayList<String>) snapshot.get("tags");
                                list.add(posts);
                            }
                            adapter = new PostsAdapter(getActivity(), list, MyFoundPostsFragment.this);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                });

    }


    @Override
    public void onPostClicked(int position) {
        firebaseFirestore.collection("Found LostPost")
                .document()
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot snapshot = task.getResult();
                            if (snapshot.exists()) {
                                LostPost posts = new LostPost();
                                posts.name = snapshot.getString("name");
                                posts.description = snapshot.getString("name");
                                posts.location = snapshot.getString("name");
                                posts.image = snapshot.getString("name");
                                posts.tags = (ArrayList<String>) snapshot.get("tags");

                                Intent intent = new Intent(getActivity(), FoundPostPage.class);
                                intent.putExtra("NAME", posts.name);
                                intent.putExtra("DESCRIPTION", posts.description);
                                intent.putExtra("LOCATION", posts.location);
                                intent.putExtra("IMAGE", posts.image);
                                intent.putExtra("TAGS", posts.tags);
                                startActivity(intent);
                            }
                        }
                    }
                });
    }*/
}
