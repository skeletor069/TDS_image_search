package com.tds.imagesearch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class Flickr {
    Context context;
    LayoutInflater inflater;
    GridView gridView;
    String apiKey = "766733a614a9c15a873ac2b130ef2db7";
    RequestQueue queue;
    StringRequest stringRequest;
    JSONArray photoList = new JSONArray();
    String TAG = "FLICKR";
    MyGridAdapter gridAdapter;

    public Flickr(Context context, LayoutInflater inflater, GridView gridView){
        this.context = context;
        this.inflater = inflater;
        this.gridView = gridView;
        queue = Volley.newRequestQueue(context);

    }

    public void Search(String searchKey){
        String searchUrl = GetRequestUrl(searchKey);
        SendStringRequest(searchUrl);
    }

    public Object GetObjectAtPosition(int position){
        return gridAdapter.getItem(position);
    }

    public String GetOriginalImgUrl(Object jsonObject) {
        return GetImageUrl((JSONObject) jsonObject, "_b");
    }

    String GetRequestUrl(String searchKey){
        return "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key="+apiKey+"&text="+searchKey+"&format=json";
    }

    String GetImageUrl(JSONObject object, String option){
        String url = "";
        try {
            url = "https://farm"+object.getString("farm")+".staticflickr.com/"+object.getString("server")+"/"+object.getString("id")+"_"+object.getString("secret")+option+".jpg";
        } catch (JSONException e) {
            //e.printStackTrace();
        }
        return url;
    }

    void SendStringRequest(String url){
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String jsonPart = response.substring(14, response.length() - 1);
                        try {
                            JSONObject jsonObject = new JSONObject(jsonPart);
                            photoList = new JSONArray(((JSONObject)jsonObject.get("photos")).getString("photo"));
                            gridAdapter = new MyGridAdapter();
                            gridView.setAdapter(gridAdapter);
                            Log.d(TAG, "onResponse: photo count " + photoList.length());
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

    class MyGridAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return photoList.length();
        }

        @Override
        public Object getItem(int i) {
            try {
                return photoList.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            convertView = inflater.inflate(R.layout.image_grid_item, viewGroup,  false);
            ImageView imageView = convertView.findViewById(R.id.imageView);
            Log.d(TAG, "getView: downloading");
            new DownloadImageTask(imageView).execute(GetImageUrl((JSONObject) getItem(position), "_s"));


//            Uri imageUri = Uri.parse(GetImageUrl((JSONObject) getItem(position), "_n"));
//            Log.d(TAG, "getView: " + (imageUri==null));
//            if(imageUri != null)
//                imageView.setImageURI(imageUri);

//            try {
//                imageView.setImageURI(Uri.parse(GetImageThumbUrl((JSONObject) getItem(position))));
//            }catch (Exception e){
//                e.printStackTrace();
//            }
            return convertView;
        }
    }

//    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
//        ImageView bmImage;
//        public DownloadImageTask(ImageView bmImage) {
//            this.bmImage = bmImage;
//        }
//
//        protected Bitmap doInBackground(String... urls) {
//            String urldisplay = urls[0];
//            Bitmap bmp = null;
//            try {
//                InputStream in = new java.net.URL(urldisplay).openStream();
//                bmp = BitmapFactory.decodeStream(in);
//            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
//            return bmp;
//        }
//        protected void onPostExecute(Bitmap result) {
//            bmImage.setImageBitmap(result);
//        }
//    }

}
