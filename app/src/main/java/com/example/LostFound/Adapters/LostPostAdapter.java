package com.example.LostFound.Adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.LostFound.Activities.PostPage;
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
        LostPost currentLostPost = lostPosts.get(position);
        holder.name.setText(currentLostPost.getName());
        holder.location.setText(currentLostPost.getLocation());
        holder.description.setText(currentLostPost.getDescription());
        Picasso.get().load(currentLostPost.getImage()).into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PostPage.class);
                intent.putExtra("UID", currentLostPost.getUid());
                intent.putExtra("UEMAIL", currentLostPost.getUEmail());
                intent.putExtra("NAME", currentLostPost.getName());
                intent.putExtra("LOCATION", currentLostPost.getLocation());
                intent.putExtra("DESCRIPTION", currentLostPost.getDescription());
                intent.putExtra("IMAGE", currentLostPost.getImage());
                intent.putExtra("TAGS", currentLostPost.getTags());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.i("QNK", "getItemCount: " + lostPosts.size());
        return lostPosts.size();
    }

    public void setLostPosts(List<LostPost> posts) {
        this.lostPosts = posts;
        Log.i("QNK", "setLostPosts: " + posts.size());
        notifyDataSetChanged();
    }

    public void deleteLostPosts(List<LostPost> posts) {
        this.lostPosts = posts;
        lostPosts.clear();
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
