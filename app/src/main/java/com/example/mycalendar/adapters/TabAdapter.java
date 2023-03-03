package com.example.mycalendar.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabAdapter extends FragmentStateAdapter {
    List<String> tabs = new ArrayList<>();
    List<Fragment> fragments = new ArrayList<>();

    public void addTab(String tab, Fragment fragment) {
        tabs.add(tab);
        fragments.add(fragment);
    }

    public String getTabTitle(int position) {
        return tabs.get(position);
    }

    public TabAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}