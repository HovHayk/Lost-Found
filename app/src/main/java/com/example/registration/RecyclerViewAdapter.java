package com.example.registration;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.registration.databinding.RecyclerViewRowBinding;
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
        RecyclerViewRowBinding recyclerViewRowBinding = RecyclerViewRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(recyclerViewRowBinding);
    }




    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setPost(list.get(position));
    }




    @Override
    public int getItemCount() {
        return list.size();
    }





    public static class MyViewHolder extends RecyclerView.ViewHolder {

        RecyclerViewRowBinding binding;

        public MyViewHolder(@NonNull RecyclerViewRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setPost(Posts post){
            Glide.with(itemView.getContext()).load(post.image).into(binding.imageForPost);
            binding.postName.setText(post.name);
            binding.postPlace.setText(post.place);
            binding.postDescription.setText(post.description);
            binding.postCategory.setText("Default category");
        }
    }

}




















