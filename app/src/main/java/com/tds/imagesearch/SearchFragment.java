package com.tds.imagesearch;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class SearchFragment extends Fragment implements View.OnClickListener {

    Button searchBtn;
    EditText searchBox;
    Switch googleSwitch, flickrSwitch, gettySwitch;
    String TAG = "SearchFragment";
    Flickr flickr;


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
        flickr = new Flickr(getActivity());
        return rootView;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buttonSearch){
            if(searchBox.getText().toString().equals("")){
                Toast.makeText(getActivity(), getActivity().getString(R.string.search_empty_warning), Toast.LENGTH_LONG).show();
                return;
            }
            flickr.Search(searchBox.getText().toString());

        }
    }
}
