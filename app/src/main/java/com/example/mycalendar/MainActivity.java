package com.example.mycalendar;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.mycalendar.databinding.ActivityMainBinding;

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
        binding.appBarMain.rvCategories.setLayoutManager(new GridLayoutManager(this, 2));
        binding.appBarMain.rvCategories.setAdapter(new CategoryAdapter());
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