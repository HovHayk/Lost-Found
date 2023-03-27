package com.example.LostFound.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.LostFound.Models.Post;
import com.example.LostFound.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {
    private List<Post> posts = new ArrayList<>();


    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_row, parent, false);

        return new PostHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        Post currentPost = posts.get(position);
        holder.name.setText(currentPost.getName());
        holder.location.setText(currentPost.getLocation());
        holder.description.setText(currentPost.getDescription());
        Picasso.get().load(currentPost.getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
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
