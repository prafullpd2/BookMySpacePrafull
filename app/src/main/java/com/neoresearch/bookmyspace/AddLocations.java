    package com.neoresearch.bookmyspace;

    import android.app.Activity;
    import android.app.ProgressDialog;
    import android.content.Context;
    import android.location.Criteria;
    import android.location.Location;
    import android.location.LocationListener;
    import android.location.LocationManager;
    import android.os.AsyncTask;
    import android.os.Bundle;
    import android.support.v4.app.Fragment;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageButton;
    import android.widget.Toast;

    import org.apache.http.NameValuePair;
    import org.apache.http.message.BasicNameValuePair;
    import org.json.JSONObject;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;

    public class AddLocations extends Fragment implements View.OnClickListener,LocationListener {

        EditText location_name, h_no, street, area, space;
        protected ProgressDialog pDialog;
        ImageButton redirect;
        String mobile, loc_name, house, street_n, area_n, space_n, longitude, latitude,provider;
        SessionManager session;
        Double lati,longi;
        JSONParser jsonParser = new JSONParser();
        Button add;

        private static final String LOCATION_URL = "http://prasheel-acropolis.rhcloud.com/add_locations.php";
        private static final String TAG_SUCCESS = "success";
        LocationManager locationManager;


        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            Context c = getActivity().getApplicationContext();
            locationManager =(LocationManager)activity.getSystemService(Context.LOCATION_SERVICE);

        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.addlocations, container, false);
            location_name = (EditText) v.findViewById(R.id.location_name);
            h_no = (EditText) v.findViewById(R.id.home_number);
            space = (EditText) v.findViewById(R.id.space);
            street = (EditText) v.findViewById(R.id.street);
            area = (EditText) v.findViewById(R.id.area);
            add = (Button) v.findViewById(R.id.add_locations);
            session = new SessionManager(getActivity().getApplicationContext());
            HashMap<String, String> user = session.getUserDetails();
            redirect = (ImageButton) v.findViewById(R.id.imageButton4);
            redirect.setOnClickListener(this);
            add.setOnClickListener(this);
            mobile = user.get(SessionManager.KEY_MOBILE);

            Criteria criteria = new Criteria();
            provider = locationManager.getBestProvider(criteria, false);
            Location location = null;
            Toast.makeText(getActivity(), "Getting your GPS Location", Toast.LENGTH_LONG).show();

            if (location != null) {
                System.out.println("Provider " + provider + " has been selected.");
                onLocationChanged(location);
            } else {

            }

            return v;

        }
        @Override
        public void onResume() {
            super.onResume();
            locationManager.requestLocationUpdates(provider, 400, 1, this);
        }

        @Override
        public void onPause() {
            super.onPause();
            locationManager.removeUpdates(this);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.add_locations:
                    house = h_no.getText().toString();
                    space_n = space.getText().toString();
                    area_n = area.getText().toString();
                    street_n = street.getText().toString();
                    loc_name = location_name.getText().toString();
                    new AddLocation().execute();

                case R.id.imageButton4:
                    ((ProvideParking) getActivity()).setCurrentItem(0, true);

            }
        }

        @Override
        public void onLocationChanged(Location location) {

            lati =  location.getLatitude();
             longi = location.getLongitude();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }


        class AddLocation extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(getActivity());
                pDialog.setMessage("Adding Location...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }
        @Override
        protected String doInBackground(String... args) {
            int success;
           latitude=lati.toString();
            longitude=longi.toString();
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("mobile", mobile));
                params.add(new BasicNameValuePair("location_name", loc_name));
                params.add(new BasicNameValuePair("house_no", house));
                params.add(new BasicNameValuePair("street", street_n));
                params.add(new BasicNameValuePair("area", area_n));
                params.add(new BasicNameValuePair("space", space_n));
                params.add(new BasicNameValuePair("long",longitude));
                params.add(new BasicNameValuePair("lat",latitude));


                Log.d("request!", "starting");

                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(LOCATION_URL, "POST", params);

                // check your log for json response
                Log.d("Sending Mobile", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);


                if (success == 1) {
                    Toast.makeText(getActivity().getApplicationContext(), "Locations Added!!!", Toast.LENGTH_LONG).show();
                    Log.d("Locations Added", json.toString());


                    return json.getString(TAG_SUCCESS);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Error Occured!!!", Toast.LENGTH_LONG).show();
                    Log.d("Error Occured", json.getString(TAG_SUCCESS));


                    return json.getString(TAG_SUCCESS);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();


            if (file_url != null) {
                Toast.makeText(getActivity().getApplicationContext(), file_url, Toast.LENGTH_LONG).show();
            }


        }

    }





    }




