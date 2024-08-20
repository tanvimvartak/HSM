package com.example.hsm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ViewImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        String imageUrl = getIntent().getStringExtra("imageUrl").toString();

        ImageView displayImage = findViewById(R.id.displayNoticeImage);

        Glide.with(ViewImage.this).load(imageUrl).into(displayImage);

    }
}