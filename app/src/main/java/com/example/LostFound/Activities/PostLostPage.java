package com.example.LostFound.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.LostFound.Models.Posts;
import com.example.LostFound.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class PostLostPage extends Fragment {

    DatabaseReference databaseReference;

    ImageView postImage;
    TextView postName, postLocation, postDescription;

    public PostLostPage() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseReference = FirebaseDatabase.getInstance().getReference("Posts").child("Lost");



        setPostInfo();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_post_lost_page, container, false);


        postName = v.findViewById(R.id.post_name);
        postLocation = v.findViewById(R.id.post_location);
        postDescription = v.findViewById(R.id.post_description);
        postImage = v.findViewById(R.id.post_image);

        return v;
    }


    public void setPostInfo() {

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Posts posts = new Posts(snapshot.child("Name").getValue().toString()
                        , snapshot.child("Location").getValue().toString()
                        , snapshot.child("Description").getValue().toString()
                        , snapshot.child("image").getValue().toString());



                Picasso.get().load(posts.getPostImage()).into(postImage);
                postName.setText(posts.getPostName());
                postLocation.setText(posts.getPostLocation());
                postDescription.setText(posts.getPostDescription());

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