package com.example.mycalendar.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.mycalendar.Config;
import com.example.mycalendar.R;
import com.example.mycalendar.adapters.SliderAdapter;
import com.example.mycalendar.databinding.ActivitySliderBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class SliderActivity extends AppCompatActivity {
    ActivitySliderBinding binding;
    int categoryPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySliderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        categoryPosition = getIntent().getIntExtra("categoryPosition", 0);
        binding.toolbar.setTitle(Config.categories.get(categoryPosition).getName());
        binding.viewPager.setAdapter(new SliderAdapter(this, categoryPosition));
        binding.viewPager.setCurrentItem(getIntent().getIntExtra("position", 0));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.slider_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.download) {
            if (Build.VERSION.SDK_INT >=32) {
                DownloadImageTask downloadTask = new DownloadImageTask();
                downloadTask.execute(Config.categories.get(categoryPosition).getImage().get(binding.viewPager.getCurrentItem() + 1));
            } else {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
                } else {
                    DownloadImageTask downloadTask = new DownloadImageTask();
                    downloadTask.execute(Config.categories.get(categoryPosition).getImage().get(binding.viewPager.getCurrentItem() + 1));
                }
            }
        } else if (item.getItemId() == R.id.share) {
            ShareImage();
        }
        return super.onOptionsItemSelected(item);
    }

    private void ShareImage() {
        String imageUrl = Config.categories.get(categoryPosition).getImage().get(binding.viewPager.getCurrentItem() + 1);
        new ShareImageTask().execute(imageUrl);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                DownloadImageTask downloadTask = new DownloadImageTask();
                downloadTask.execute(Config.categories.get(categoryPosition).getImage().get(binding.viewPager.getCurrentItem() + 1));
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String imageUrl = urls[0];
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                String fileName = System.currentTimeMillis() + ".jpg";
                File downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                if (downloadsDirectory.exists()) {
                    downloadsDirectory.mkdirs();
                }
                File file = new File(downloadsDirectory, fileName);
                FileOutputStream out = null;
                try {
                    out = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                    MediaScannerConnection.scanFile(SliderActivity.this, new String[]{file.getAbsolutePath()}, null, (path, uri) -> {
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return "Downloaded successfully!";
            } catch (Exception e) {
                e.printStackTrace();
                return "Download failed!";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }

    private class ShareImageTask extends AsyncTask<String, Void, String> {
        File file;
        @Override
        protected String  doInBackground(String... urls) {
            String imageUrl = urls[0];
            try {
                URL url = new URL(imageUrl);
                Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                file = new File(getExternalCacheDir(), "image.jpg");
                FileOutputStream fos = new FileOutputStream(file);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Uri uri = FileProvider.getUriForFile(SliderActivity.this, getPackageName() + ".provider", file);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/jpeg");
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Download and View more images at https://play.google.com/store/apps/details?id=" + getPackageName());
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(shareIntent, "Share image via"));
        }
    }

}