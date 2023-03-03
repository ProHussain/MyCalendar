package com.example.mycalendar.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mycalendar.Config;
import com.example.mycalendar.R;
import com.example.mycalendar.model.Response;
import com.example.mycalendar.network.MyAPI;
import com.example.mycalendar.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loadData();
    }

    private void loadData() {
        MyAPI api = RetrofitClient.getRetrofitInstance().create(MyAPI.class);
        api.getData().enqueue(new Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call, @NonNull retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    Config.categories = response.body() != null ? response.body().getCategories() : null;
                    Config.ads = response.body() != null ? response.body().getAds() : null;
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SplashActivity.this, "Something went wrong, Please check your internet connection", Toast.LENGTH_SHORT).show();
                    Log.e("SplashActivity", "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                Log.e("SplashActivity", "onFailure: " + t.getMessage());
                Toast.makeText(SplashActivity.this, "Something went wrong, Please check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}