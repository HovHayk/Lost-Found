package com.example.LostFound.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.LostFound.R;

import java.util.ArrayList;

public class FoundPostPageFragment extends Fragment {

    ImageView postImage;
    TextView postName, postLocation, postDescription;

    String id, name, location, description, image;
    ArrayList<String> tags;

    public FoundPostPageFragment() {
        // Required empty public constructor
    }

    public FoundPostPageFragment(String id, String name, String location, String description, String image, ArrayList<String> tags) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.description = description;
        this.image = image;
        this.tags = tags;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_post_page, container, false);


        postName = v.findViewById(R.id.post_name);
        postLocation = v.findViewById(R.id.post_location);
        postDescription = v.findViewById(R.id.post_description);
        postImage = v.findViewById(R.id.post_image);

        postName.setText(name);
        postLocation.setText(location);
        postDescription.setText(description);
        Glide.with(getContext()).load(image).into(postImage);


        return v;
    }

}