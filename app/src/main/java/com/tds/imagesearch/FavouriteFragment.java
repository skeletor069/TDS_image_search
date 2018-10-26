package com.tds.imagesearch;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FavouriteFragment extends Fragment {

    GridView gridView;
    Flickr flickr;
    View rootView;
    String TAG = "FavoriteFramgment";
    JSONArray favoriteImages;

    public FavouriteFragment(){
        favoriteImages = new JSONArray();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.favourites_fragment, container, false);
        gridView = (GridView) rootView.findViewById(R.id.gridViewFavorite);
        flickr = new Flickr(getContext(), inflater, gridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Object viewData = flickr.GetObjectAtPosition(position);
                Log.d(TAG, "onItemClick: " + viewData.toString());
                startActivity(new Intent(getActivity(), ViewImage.class).putExtra("imgData", viewData.toString()));

            }
        });
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Do your Work
            JSONArray favoriteImages = DataManager.GetInstance().GetFavoriteImages();
            Log.d(TAG, "setUserVisibleHint: I am visible");
            if(favoriteImages != null) {
                flickr.LoadFavorites(favoriteImages);
            }
        } else {
            // Do your Work
            Log.d(TAG, "setUserVisibleHint: I am not visible");
        }
    }



}
