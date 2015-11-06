package com.neoresearch.bookmyspace;

import android.support.v4.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import java.util.HashMap;

public class AddLocations extends Fragment implements View.OnClickListener {

    EditText h_no,street,landmark;

   ImageButton redirect;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.addlocations, container, false);
        h_no=(EditText)v.findViewById(R.id.home_number);
        street=(EditText)v.findViewById(R.id.street);
        landmark=(EditText)v.findViewById(R.id.landmark);

        redirect=(ImageButton)v.findViewById(R.id.imageButton4);
        redirect.setOnClickListener(this);
        return v;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.imageButton4 :
           {((ProvideParking)getActivity()).setCurrentItem(0, true);}
        }
    }
}
