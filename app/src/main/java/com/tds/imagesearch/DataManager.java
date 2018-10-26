package com.tds.imagesearch;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;

public class DataManager {
    static DataManager instance = null;
    ArrayList<String> favoriteImages;
    SharedPreferences preferences;
    Gson gsonBuilder;

    private DataManager(){
        favoriteImages = new ArrayList<String>();
        gsonBuilder = new GsonBuilder().create();
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

    ArrayList<JSONObject> GetJSONObjectList(){
        ArrayList<JSONObject> temp = new ArrayList<JSONObject>();
        for(int i = 0 ; i < favoriteImages.size(); i++){
            try {
                temp.add(new JSONObject(favoriteImages.get(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return temp;
    }

    public JSONArray GetFavoriteImages(){
        try {
            JSONArray temp = new JSONArray(gsonBuilder.toJson(favoriteImages));
            return temp;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}
