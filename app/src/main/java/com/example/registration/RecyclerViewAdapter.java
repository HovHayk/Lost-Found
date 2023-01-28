package com.example.registration;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context context;
    List<Posts> list;

    public RecyclerViewAdapter(Context context, List<Posts> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.recycler_view_row,parent,false);
        return new MyViewHolder(v);

    }




    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Posts posts = list.get(position);
        holder.postName.setText(posts.getName());
        holder.postPlace.setText(posts.getPlace());
        holder.postDescription.setText(posts.getDescription());
//        holder.category.setText(posts.getCategory());

        String imageUri = null;
        imageUri = posts.getImage();
        Picasso.get().load(imageUri).into(holder.imageForPost);

    }




    @Override
    public int getItemCount() {


        return list.size();


    }





    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageForPost;
        TextView postName, postPlace, postDescription, category;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageForPost = itemView.findViewById(R.id.imageForPost);
            postName = itemView.findViewById(R.id.postName);
            postPlace = itemView.findViewById(R.id.postPlace);
            postDescription = itemView.findViewById(R.id.postDescription);
            category = itemView.findViewById(R.id.spinnerCategory);

        }
    }

}





















