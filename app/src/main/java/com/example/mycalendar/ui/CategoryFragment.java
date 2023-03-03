package com.example.mycalendar.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycalendar.Config;
import com.example.mycalendar.R;
import com.example.mycalendar.adapters.CategoryAdapter;
import com.example.mycalendar.databinding.FragmentCategoryBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {
    FragmentCategoryBinding binding;
    int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void updateValue(int position) {
       Log.e("TAG", "updateValue: " + position);
       this.position = position;
        List<String> categories = new ArrayList<>();
        for (int i = 0; i < Config.categories.get(position).getLogo().size(); i++) {
            categories.add(Config.categories.get(position).getLogo().get(i));
        }
        Log.e("TAG", "onCreateView: " + position);
        Log.e("TAG", "onCreateView: " + Config.categories.get(position).getName());
        binding.rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.rvCategories.setAdapter(new CategoryAdapter(categories, position));
    }
}