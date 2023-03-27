package com.example.LostFound.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.LostFound.databinding.ItemTagBinding;

import java.util.ArrayList;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagViewHolder> {
    private final ArrayList<String> tags;

    public TagAdapter(ArrayList<String> tags) {
        this.tags = tags;
    }

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTagBinding itemTagBinding = ItemTagBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TagViewHolder(itemTagBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        holder.setTag(tags.get(position));
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    static class TagViewHolder extends RecyclerView.ViewHolder {

        ItemTagBinding binding;

        TagViewHolder(ItemTagBinding itemTagBinding) {
            super(itemTagBinding.getRoot());
            binding = itemTagBinding;
        }

        void setTag(String tag) {
            binding.tag.setText(tag);
        }
    }
}

