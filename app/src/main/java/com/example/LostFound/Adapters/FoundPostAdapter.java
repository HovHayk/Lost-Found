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
import com.example.LostFound.Models.FoundPost;
import com.example.LostFound.Models.LostPost;
import com.example.LostFound.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoundPostAdapter extends RecyclerView.Adapter<FoundPostAdapter.PostHolder> {

    private List<FoundPost> foundPosts = new ArrayList<>();

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_row, parent, false);

        return new PostHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        FoundPost currentFoundPost = foundPosts.get(position);
        holder.name.setText(currentFoundPost.getName());
        holder.location.setText(currentFoundPost.getLocation());
        holder.description.setText(currentFoundPost.getDescription());
        Picasso.get().load(currentFoundPost.getImage()).into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PostPage.class);
                intent.putExtra("NAME", currentFoundPost.getName());
                intent.putExtra("LOCATION", currentFoundPost.getLocation());
                intent.putExtra("DESCRIPTION", currentFoundPost.getDescription());
                intent.putExtra("IMAGE", currentFoundPost.getImage());
                intent.putExtra("TAGS", currentFoundPost.getTags());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foundPosts.size();
    }

    public void setFoundPosts(List<FoundPost> posts) {
        this.foundPosts = posts;
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