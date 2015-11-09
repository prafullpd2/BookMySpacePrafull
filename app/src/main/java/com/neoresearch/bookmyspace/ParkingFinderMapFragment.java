package com.neoresearch.bookmyspace;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

/**
 * A fragment that launches other parts of the demo application.
 */
public class ParkingFinderMapFragment extends Fragment implements View.OnClickListener {

    Button  searchButton, rangeIncrement, rangeDecrement;
    TextView rangeText;
    AutoCompleteTextView searchText;
    LatLng latLng,resultLatLng,currentLatLng;
    Marker resultMarker = null;
    MarkerOptions markerOptions,resultMarkerOption=null;
    MapView mMapView;
    GoogleMap googleMap;
    private EditText editText;
    String string;
    private View v;
    private Address addressOfResult;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate and return the layout
        v = inflater.inflate(R.layout.fragment_parking_finder_map, container,
                false);

        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();
        // latitude and longitude
        double latitude = 17.385044;
        double longitude = 78.486671;

        // create marker
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(latitude, longitude)).title("Hello Maps");

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        // adding marker
        googleMap.addMarker(marker);
        LatLng indore = new LatLng(22.7000, 75.9000);

        final MarkerOptions marker2 = new MarkerOptions().position(indore).title("Indore");
        googleMap.addMarker(marker2);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(22.7000, 75.9000)).zoom(8).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        // Perform any camera updates here
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (!marker.equals(resultMarker)) {
                    PopupOnMap pop = new PopupOnMap(getActivity());
                    pop.show();
                    pop.setName("prafull", marker.getTitle(), "8269564260");
                }
                return false;
            }
        });



        searchText = (AutoCompleteTextView) v.findViewById(R.id.search_on_map);
        searchButton = (Button) v.findViewById(R.id.button_search);
        rangeIncrement = (Button) v.findViewById(R.id.range_increment);
        rangeDecrement = (Button) v.findViewById(R.id.range_decrement);
        rangeText = (TextView) v.findViewById(R.id.range_select);

        searchButton.setOnClickListener(this);
        rangeDecrement.setOnClickListener(this);
        rangeIncrement.setOnClickListener(this);

        googleMap.setMyLocationEnabled(true);


        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        actionId == EditorInfo.IME_ACTION_GO ||
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    // hide virtual keyboard
                    getResultForSearch();
                    return true;
                }


                return false;
            }
        });
        return v;


    }


    @Override
    public void onClick(View v) {

        int range = Integer.parseInt(rangeText.getText().toString());

        switch (v.getId()) {

            case R.id.button_search:

                getResultForSearch();
                break;

            case R.id.range_increment:

                if (range > 0 && range < 20) {
                    range++;
                }

                rangeText.setText(String.valueOf(range));
                break;

            case R.id.range_decrement:

                range = Integer.parseInt(rangeText.getText().toString());
                if (range >1 && range <=20) {
                    range--;
                }
                rangeText.setText(String.valueOf(range));
                break;
        }

    }

    void getResultForSearch(){

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);

        new SearchClicked(searchText.getText().toString()).execute();
        searchText.setText("", TextView.BufferType.EDITABLE);


    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



    private class SearchClicked extends AsyncTask<Void, Void, Boolean> {
        private String toSearch;



        public SearchClicked(String toSearch) {
            this.toSearch = toSearch;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);


            if(resultMarker!=null)
            resultMarker.remove();

            resultLatLng = new LatLng(addressOfResult.getLatitude() ,addressOfResult.getLongitude());
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(resultLatLng).zoom(15).build();
            googleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));


            resultMarkerOption = new MarkerOptions().position(resultLatLng).title(addressOfResult.getAddressLine(0));
           resultMarker=  googleMap.addMarker(resultMarkerOption);




        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            try {
                Geocoder geocoder = new Geocoder(getContext(), Locale.UK);
                List<Address> results = geocoder.getFromLocationName(toSearch, 1);

                if (results.size() == 0) {
                    return false;
                }

                addressOfResult = results.get(0);

                // Now do something with this GeoPoint:
       //         Barcode.GeoPoint p = new Barcode.GeoPoint(0,address.getLatitude() ,address.getLongitude() );



            } catch (Exception e) {
                Log.e("", "Something went wrong: ", e);
                return false;
            }
            return true;
        }


    }

}
