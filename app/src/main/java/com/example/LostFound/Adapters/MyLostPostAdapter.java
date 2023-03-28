package com.example.LostFound.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.LostFound.Activities.PostPage;
import com.example.LostFound.Models.LostPost;
import com.example.LostFound.Models.MyLostPost;
import com.example.LostFound.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyLostPostAdapter extends RecyclerView.Adapter<MyLostPostAdapter.PostHolder> {

    private List<MyLostPost> myLostPosts = new ArrayList<>();

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_row, parent, false);

        return new PostHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        MyLostPost currentMyLostPost = myLostPosts.get(position);
        holder.name.setText(currentMyLostPost.getName());
        holder.location.setText(currentMyLostPost.getLocation());
        holder.description.setText(currentMyLostPost.getDescription());
        Picasso.get().load(currentMyLostPost.getImage()).into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PostPage.class);
                intent.putExtra("NAME", currentMyLostPost.getName());
                intent.putExtra("LOCATION", currentMyLostPost.getLocation());
                intent.putExtra("DESCRIPTION", currentMyLostPost.getDescription());
                intent.putExtra("IMAGE", currentMyLostPost.getImage());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myLostPosts.size();
    }

    public void setMyLostPosts(List<MyLostPost> posts) {
        this.myLostPosts = posts;
        notifyDataSetChanged();
    }

    class PostHolder extends RecyclerView.ViewHolder {
        private TextView name, location, description;
        private ImageView image;

        public PostHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_of_post);
            location = itemView.findViewById(R.id.location_of_post);
            description = itemView.findViewById(R.id.description_of_post);
            image = itemView.findViewById(R.id.image_of_post);
        }
    }
}
