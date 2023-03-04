package com.example.mycalendar.ui;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.example.mycalendar.BuildConfig;
import com.example.mycalendar.Config;
import com.example.mycalendar.R;
import com.example.mycalendar.adapters.MyPageChangeCallback;
import com.example.mycalendar.adapters.TabAdapter;
import com.example.mycalendar.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    ActionBarDrawerToggle toggle;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.appBarMain.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.navView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    break;
                case R.id.nav_rate:
                    RateUs();
                    break;
                case R.id.nav_share:
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "My Calendar");
                    intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + getPackageName());
                    startActivity(Intent.createChooser(intent, "Share via"));
                    break;
                case R.id.nav_about:
                    AboutUs();
                    break;
                case R.id.nav_contact:
                    Intent intent1 = new Intent(Intent.ACTION_SEND);
                    intent1.setType("text/plain");
                    intent1.putExtra(Intent.EXTRA_EMAIL, new String[]{Config.CONTACT_US_EMAIL});
                    intent1.putExtra(Intent.EXTRA_SUBJECT, "My Calendar");
                    startActivity(Intent.createChooser(intent1, "Contact via"));
                    break;
                case R.id.nav_privacy:
                    Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(Config.PRIVACY_POLICY_URL));
                    startActivity(intent2);
                    break;
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        });

        setUpViewPager();
    }

    private void setUpViewPager() {
        TabAdapter adapter = new TabAdapter(this);
        for (int i = 0; i < Config.categories.size(); i++) {
            adapter.addTab(Config.categories.get(i).getName(), new CategoryFragment());
        }
        binding.appBarMain.viewPager.setAdapter(adapter);
        binding.appBarMain.viewPager.setCurrentItem(0);
        MyPageChangeCallback pageChangeCallback = new MyPageChangeCallback(adapter);
        binding.appBarMain.viewPager.registerOnPageChangeCallback(pageChangeCallback);
        binding.appBarMain.viewPager.setOffscreenPageLimit(Config.categories.size());
        new TabLayoutMediator(binding.appBarMain.tableLayout, binding.appBarMain.viewPager,
                (tab, position) -> tab.setText(adapter.getTabTitle(position))
        ).attach();
    }

    private void AboutUs() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("About Us");
        builder.setMessage("My Calendar is a simple calendar app that helps you manage your calendars. \n\n" +
                "Version: " + BuildConfig.VERSION_NAME + "\n\n" +
                "Developed by Hashmac");
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void RateUs() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }
}