package com.tds.imagesearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewImage extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView;
    ImageView favoriteBtn;
    String TAG = "ViewImage";
    JSONObject imgJsonObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        imageView = (ImageView) findViewById(R.id.imageFullScreen);
        favoriteBtn = (ImageView) findViewById(R.id.favoriteBtn);
        favoriteBtn.setOnClickListener(this);
        String imageData = getIntent().getStringExtra("imgData");
        try {
            imgJsonObj = new JSONObject(imageData);
            new DownloadImageTask(imageView).execute(Flickr.GetOriginalImgUrl(imgJsonObj));
            if(DataManager.GetInstance().IsFavourite(imgJsonObj))
                SetButtonStateFavorite();
            else
                SetButtonStateNormal();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.general_error), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.favoriteBtn){
            if(!DataManager.GetInstance().IsFavourite(imgJsonObj)){
                DataManager.GetInstance().AddAsFavorite(imgJsonObj);
                SetButtonStateFavorite();
            }else{
                DataManager.GetInstance().RemoveFromFavorite(imgJsonObj);
                SetButtonStateNormal();
            }
        }
    }

    void SetButtonStateFavorite(){
        favoriteBtn.setImageResource(R.drawable.ic_favorite_red_24dp);
    }

    void SetButtonStateNormal(){
        favoriteBtn.setImageResource(R.drawable.ic_favorite_border_red_24dp);
    }
}
