package com.example.mycalendar.adapters;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mycalendar.ui.CategoryFragment;

public class MyPageChangeCallback extends ViewPager2.OnPageChangeCallback {
    private final TabAdapter mAdapter;

    public MyPageChangeCallback(TabAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void onPageSelected(int position) {
        CategoryFragment fragment = (CategoryFragment) mAdapter.fragments.get(position);

        if (fragment != null) {
            fragment.updateValue(position);
        }
    }
}
