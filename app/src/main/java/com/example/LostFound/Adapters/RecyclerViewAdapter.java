package com.example.LostFound.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.LostFound.Fragments.FoundPostsFragment;
import com.example.LostFound.Fragments.LostPostsFragment;
import com.example.LostFound.Models.Posts;
import com.example.LostFound.databinding.RecyclerViewRowBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    FirebaseRecyclerOptions<Posts> options;
    LostPostsFragment lostPostsFragment;
    FoundPostsFragment foundPostsFragment;
    Context context;
    List<Posts> list;


    public RecyclerViewAdapter(Context context, List<Posts> list) {
        this.context = context;
        this.list = list;
    }

    public RecyclerViewAdapter(FoundPostsFragment foundPostsFragment, List<Posts> list) {
        this.foundPostsFragment = foundPostsFragment;
        this.list = list;
    }


    public RecyclerViewAdapter(LostPostsFragment lostPostsFragment, List<Posts> list) {
        this.lostPostsFragment = lostPostsFragment;
        this.list = list;
    }

    public RecyclerViewAdapter(FirebaseRecyclerOptions<Posts> options) {
        this.options = options;
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
            Glide.with(itemView.getContext()).load(post.image).into(binding.imageOfPost);
            binding.nameOfPost.setText(post.name);
            binding.locationOfPost.setText(post.location);
            binding.descriptionOfPost.setText(post.description);
        }
    }

}





















