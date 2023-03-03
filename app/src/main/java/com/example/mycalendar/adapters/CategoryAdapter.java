package com.example.mycalendar.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mycalendar.Config;
import com.example.mycalendar.databinding.CategoryItemBinding;
import com.example.mycalendar.ui.SliderActivity;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private final List<String> categories;
    private static int categoryPosition;

    public CategoryAdapter(List<String> categories, int categoryPosition) {
        this.categories = categories;
        CategoryAdapter.categoryPosition = categoryPosition;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CategoryItemBinding binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bindData(categories.get(position),position);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        CategoryItemBinding binding;

        public CategoryViewHolder(@NonNull CategoryItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void bindData(String url,int position) {
//            binding.categoryName.setText(Config.categories.get(position).getName());
            Glide.with(binding.getRoot().getContext())
                    .load(url)
                    .into(binding.categoryImage);
            binding.getRoot().setOnClickListener(v -> {
                Intent intent = new Intent(binding.getRoot().getContext(), SliderActivity.class);
                intent.putExtra("categoryPosition", categoryPosition);
                intent.putExtra("position", position);
                binding.getRoot().getContext().startActivity(intent);
            });
        }
    }
}
