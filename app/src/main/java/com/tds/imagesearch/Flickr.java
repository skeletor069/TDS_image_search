package com.tds.imagesearch;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Flickr {
    Context context;
    String apiKey = "766733a614a9c15a873ac2b130ef2db7";
    RequestQueue queue;
    StringRequest stringRequest;
    String TAG = "FLICKR";

    public Flickr(Context context){
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }

    public void Search(String searchKey){
        String searchUrl = GetRequestUrl(searchKey);
        SendStringRequest(searchUrl);
    }

    String GetRequestUrl(String searchKey){
        return "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key="+apiKey+"&text="+searchKey+"&format=json";
    }

    void SendStringRequest(String url){
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String jsonPart = response.substring(14, response.length() - 1);
                        try {
                            JSONObject jsonObject = new JSONObject(jsonPart);
                            JSONArray jsonArray = new JSONArray(((JSONObject)jsonObject.get("photos")).getString("photo"));
                            Log.d(TAG, "onResponse: photo count " + jsonArray.length());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        Log.d(TAG, "onResponse: " + response.substring(14, response.length() - 1));

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }

}
