package com.example.LostFound.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.LostFound.Models.Posts;
import com.example.LostFound.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class PostAdapter extends FirebaseRecyclerAdapter<Posts, PostAdapter.personsViewholder> {

    public PostAdapter(@NonNull FirebaseRecyclerOptions<Posts> options) {
        super(options);
    }



    protected void
    onBindViewHolder(@NonNull personsViewholder holder, int position, @NonNull Posts posts) {
        holder.name.setText(posts.getPostName());
        holder.description.setText(posts.getPostDescription());
        holder.location.setText(posts.getPostLocation());
        Picasso.get().load(posts.getPostImage()).into(holder.image);
    }

    @NonNull
    @Override
    public personsViewholder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        return new personsViewholder(view);
    }


    static class personsViewholder extends RecyclerView.ViewHolder {

        TextView name, description, location;
        ImageView image;

        public personsViewholder(@NonNull View itemView) {

            super(itemView);

            name = itemView.findViewById(R.id.name_of_post);
            description = itemView.findViewById(R.id.description_of_post);
            location = itemView.findViewById(R.id.location_of_post);
            image = itemView.findViewById(R.id.image_of_post);
        }
    }
}
