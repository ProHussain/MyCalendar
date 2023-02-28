package com.example.mycalendar;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mycalendar.databinding.CategoryItemBinding;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CategoryItemBinding binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return Config.categories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        CategoryItemBinding binding;

        public CategoryViewHolder(@NonNull CategoryItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void bindData(int position) {
            binding.categoryName.setText(Config.categories.get(position).getName());
            Glide.with(binding.getRoot().getContext())
                    .load(Config.categories.get(position).getLogo())
                    .into(binding.categoryImage);
            binding.getRoot().setOnClickListener(v -> {
                Intent intent = new Intent(binding.getRoot().getContext(), SliderActivity.class);
                intent.putExtra("position", position);
                binding.getRoot().getContext().startActivity(intent);
            });
        }
    }
}
