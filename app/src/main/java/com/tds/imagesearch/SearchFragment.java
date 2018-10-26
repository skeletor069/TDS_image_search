package com.tds.imagesearch;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.Toast;

public class SearchFragment extends Fragment implements View.OnClickListener {

    Button searchBtn;
    EditText searchBox;
    Switch googleSwitch, flickrSwitch, gettySwitch;
    GridView gridView;
    String TAG = "SearchFragment";
    Flickr flickr;
    Switch selectedSwith;


    public SearchFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_fragment, container, false);
        searchBox = (EditText) rootView.findViewById(R.id.editTextSearch);
        googleSwitch = (Switch) rootView.findViewById(R.id.switch1);
        flickrSwitch = (Switch) rootView.findViewById(R.id.switch2);
        gettySwitch = (Switch) rootView.findViewById(R.id.switch3);
        searchBtn = (Button) rootView.findViewById(R.id.buttonSearch);
        searchBtn.setOnClickListener(this);
        flickrSwitch.setChecked(true);
        selectedSwith = flickrSwitch;
        gridView = (GridView) rootView.findViewById(R.id.gridView);
        flickr = new Flickr(getActivity(), inflater, gridView);


        SetListeners();
        return rootView;
    }

    private void SetListeners() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if(flickrSwitch.isChecked()){
                    Object viewData = flickr.GetObjectAtPosition(position);
                    startActivity(new Intent(getActivity(), ViewImage.class).putExtra("imgData", viewData.toString()));
                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buttonSearch){
            if(searchBox.getText().toString().equals("")){
                Toast.makeText(getActivity(), getActivity().getString(R.string.search_empty_warning), Toast.LENGTH_LONG).show();
                return;
            }
            if(!flickrSwitch.isChecked()){
                Toast.makeText(getContext(), getResources().getString(R.string.swith_warning), Toast.LENGTH_LONG).show();
                return;
            }
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            flickr.Search(searchBox.getText().toString());

        }
    }
}
