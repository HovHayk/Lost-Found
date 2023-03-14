package com.example.LostFound.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.LostFound.Fragments.LostPostPageFragment;
import com.example.LostFound.Models.Posts;
import com.example.LostFound.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class Adapter extends FirebaseRecyclerAdapter<Posts, Adapter.myViewHolder> {

    public Adapter(@NonNull FirebaseRecyclerOptions<Posts> options) {
        super(options);
        Log.i("POMODORO", "Adapter: " + options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Posts model) {
        holder.postName.setText(model.getName());
        holder.postDescription.setText(model.getDescription());
        holder.postLocation.setText(model.getLocation());
        Glide.with(holder.postImage.getContext()).load(model.getImage()).into(holder.postImage);


        holder.postCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new LostPostPageFragment(model.getId(), model.getName(), model.getLocation(), model.getDescription(), model.getImage(), model.getTags())).addToBackStack(null).commit();
            }
        });
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        return new myViewHolder(view);
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        CardView postCard;
        ImageView postImage;
        TextView postName, postLocation, postDescription;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            postCard = itemView.findViewById(R.id.recycler_row_card);
            postImage = itemView.findViewById(R.id.image_of_post);
            postName = itemView.findViewById(R.id.name_of_post);
            postLocation = itemView.findViewById(R.id.location_of_post);
            postDescription = itemView.findViewById(R.id.description_of_post);
        }
    }


}
