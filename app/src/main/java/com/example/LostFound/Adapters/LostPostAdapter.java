package com.example.LostFound.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.LostFound.Models.LostPost;
import com.example.LostFound.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LostPostAdapter extends RecyclerView.Adapter<LostPostAdapter.PostHolder> {

    private List<LostPost> lostPosts = new ArrayList<>();

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_row, parent, false);

        return new PostHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        LostPost currentLottPost = lostPosts.get(position);
        holder.name.setText(currentLottPost.getName());
        holder.location.setText(currentLottPost.getLocation());
        holder.description.setText(currentLottPost.getDescription());
        Picasso.get().load(currentLottPost.getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return lostPosts.size();
    }

    public void setLostPosts(List<LostPost> posts) {
        this.lostPosts = posts;
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