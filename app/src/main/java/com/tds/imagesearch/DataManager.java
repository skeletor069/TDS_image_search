package com.tds.imagesearch;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;


import java.util.ArrayList;

public class DataManager {
    static DataManager instance = null;
    ArrayList<String> favoriteImages = new ArrayList<String>();
    SharedPreferences preferences;

    private DataManager(){
        //favoriteImages = new ArrayList<String>();
        Log.d("ViewImage", "DataManager: Instantiated");
    }

    public static DataManager GetInstance(){
        if(instance == null)
            instance = new DataManager();
        return instance;
    }

    public void Initialize(SharedPreferences preferences){
        this.preferences = preferences;
        LoadFavoriteImages();
    }

    void LoadFavoriteImages() {
        String savedData = preferences.getString("favorite","");
        if(savedData != "") {
            Gson gson = new Gson();
            favoriteImages = gson.fromJson(savedData, new TypeToken<ArrayList<String>>() {
            }.getType());
        }
    }

    public void AddAsFavorite(JSONObject imageObj){
        if(!favoriteImages.contains(imageObj.toString()))
            favoriteImages.add(imageObj.toString());
    }

    public void RemoveFromFavorite(JSONObject imageObj){
        if(favoriteImages.contains(imageObj.toString())){
            favoriteImages.remove(imageObj.toString());
        }
    }

    public boolean IsFavourite(JSONObject imageObj){
        return favoriteImages.contains(imageObj.toString());
    }

//    public JSONArray GetFavoriteImages(){
//        return favoriteImages;
//    }


}
