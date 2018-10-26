package com.tds.imagesearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ViewImage extends AppCompatActivity {

    ImageView imageView;
    ImageView favoriteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        imageView = (ImageView) findViewById(R.id.imageFullScreen);
        favoriteBtn = (ImageView) findViewById(R.id.favoriteBtn);
        String url = getIntent().getStringExtra("imgUrl");
        new DownloadImageTask(imageView).execute(url);
    }
}
