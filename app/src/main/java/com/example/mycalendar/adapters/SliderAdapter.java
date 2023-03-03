package com.example.mycalendar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.mycalendar.Config;
import com.example.mycalendar.R;

import java.util.List;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    List<String> images;

    public SliderAdapter(Context context,int position) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        images = Config.categories.get(position).getImage();
    }

    @Override
    public int getCount() {
        return images.size()-1;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.item_viewpager, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.view_pager_image);
        Glide.with(context)
                .load(images.get(position+1))
                .into(imageView);
        container.addView(itemView);
        imageView.setOnClickListener(v -> Toast.makeText(context, "you clicked image " + (position + 1), Toast.LENGTH_LONG).show());
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}