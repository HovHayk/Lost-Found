package com.example.LostFound.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.LostFound.Activities.PostLostPage;
import com.example.LostFound.Models.Posts;
import com.example.LostFound.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class Adapter extends FirebaseRecyclerAdapter<Posts, Adapter.myViewHolder> {

    public Adapter(@NonNull FirebaseRecyclerOptions<Posts> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Posts model) {
        holder.postName.setText(model.getPostName());
        holder.postDescription.setText(model.getPostDescription());
        holder.postLocation.setText(model.getPostLocation());
        Glide.with(holder.postImage.getContext()).load(model.getPostImage()).into(holder.postImage);


        holder.postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new PostLostPage()).addToBackStack(null).commit();
            }
        });


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        ImageView postImage;
        TextView postName, postLocation, postDescription;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            postImage = itemView.findViewById(R.id.image_of_post);
            postName = itemView.findViewById(R.id.name_of_post);
            postLocation = itemView.findViewById(R.id.location_of_post);
            postDescription = itemView.findViewById(R.id.description_of_post);
        }
    }


}
