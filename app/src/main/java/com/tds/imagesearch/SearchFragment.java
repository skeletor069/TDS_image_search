package com.tds.imagesearch;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import static android.widget.Toast.LENGTH_LONG;

public class SearchFragment extends Fragment implements View.OnClickListener {

    RequestQueue queue;
    StringRequest stringRequest;
    Button searchBtn;
    EditText searchBox;
    Switch googleSwitch, flickrSwitch, gettySwitch;
    String TAG = "SearchFragment";


    public SearchFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_fragment, container, false);
        queue = Volley.newRequestQueue(getActivity());
        searchBox = (EditText) rootView.findViewById(R.id.editTextSearch);
        googleSwitch = (Switch) rootView.findViewById(R.id.switch1);
        flickrSwitch = (Switch) rootView.findViewById(R.id.switch2);
        gettySwitch = (Switch) rootView.findViewById(R.id.switch3);
        searchBtn = (Button) rootView.findViewById(R.id.buttonSearch);
        searchBtn.setOnClickListener(this);



        return rootView;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buttonSearch){
            if(searchBox.getText().toString().equals("")){
                Toast.makeText(getActivity(), getActivity().getString(R.string.search_empty_warning), Toast.LENGTH_LONG).show();
                return;
            }

            SearchInFlickr(searchBox.getText().toString());

        }
    }

    void SearchInFlickr(String searchText){
        String searchUrl = GetRequestUrl(searchText);
        Log.d(TAG, "SearchInFlickr: " + searchUrl);
        UpdateJSONRequest(searchUrl);

    }

    String GetRequestUrl(String searchText){
        return "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=766733a614a9c15a873ac2b130ef2db7&text="+searchText+"&format=json";
    }

    void UpdateJSONRequest(String url){
        stringRequest = new StringRequest(Request.Method.GET, GetRequestUrl("cat"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Toast.makeText(getActivity(), "Response is: "+ response.substring(0,500), Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }
}
