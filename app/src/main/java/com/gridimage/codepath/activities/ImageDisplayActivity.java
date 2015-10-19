package com.gridimage.codepath.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.gridimage.codepath.models.ImageResult;
import com.squareup.picasso.Picasso;

public class ImageDisplayActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        ImageResult imageResult = (ImageResult) getIntent().getSerializableExtra("imageResult");
        ImageView ivImage = (ImageView) findViewById(R.id.ivImage);
        Picasso.with(this).load(imageResult.fullUrl).fit().into(ivImage);
    }
}
