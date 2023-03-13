package com.example.LostFound;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class PostAdapter extends FirebaseRecyclerAdapter<Posts, PostAdapter.personsViewholder> {

    public PostAdapter(@NonNull FirebaseRecyclerOptions<Posts> options) {
        super(options);
    }



    protected void
    onBindViewHolder(@NonNull personsViewholder holder, int position, @NonNull Posts model) {

        holder.name.setText(model.getPostName());
        holder.description.setText(model.getPostDescription());
        holder.location.setText(model.getPostLocation());
        Picasso.get().load(model.getPostImage()).into(holder.image);
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

            name = itemView.findViewById(R.id.post_name);
            description = itemView.findViewById(R.id.post_description);
            location = itemView.findViewById(R.id.post_location);
            image = itemView.findViewById(R.id.post_image);
        }
    }
}
