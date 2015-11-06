package com.neoresearch.bookmyspace;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Locations extends Fragment implements View.OnClickListener {
    public String mobile;
    GridView grid;
    ArrayAdapter<String> adapter;
    JSONArray  locations;
  //  String locations;
    SessionManager session;
    TextView loc;
    String [] langs;
    //String[] list;
    protected ProgressDialog pDialog;
    List<String> list;
    ImageButton redirect;
    JSONParser jsonParser = new JSONParser();
    private static final String LOCATION_URL = "http://prasheel-acropolis.rhcloud.com/get_locations.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_LOCATIONS ="locations";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.locations, container, false);
        session=new SessionManager(getActivity().getApplicationContext());
        redirect=(ImageButton)v.findViewById(R.id.imageButton3);
        loc=(TextView)v.findViewById(R.id.textView3);
        HashMap<String, String> user = session.getUserDetails();
        mobile = user.get(SessionManager.KEY_MOBILE);
        grid = (GridView)v.findViewById(R.id.gridView);
        new CheckLocations().execute();
        redirect.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.imageButton3 :
            {((ProvideParking)getActivity()).setCurrentItem(1, true);}

        }
    }

    class CheckLocations extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPreExecute()
        {

            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Searching for Added Locations...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }
        @Override
        protected String doInBackground(String... args) {
            int success;


            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("mobile", mobile));
                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(LOCATION_URL, "POST",params);
                // check your log for json response
                Log.d("Sending Mobile", json.toString());
                // json success tag
                success = json.getInt(TAG_SUCCESS);
                //json locations array
                locations=json.getJSONArray(TAG_LOCATIONS);
                //JSONArray arr = new JSONArray(TAG_LOCATIONS);
               list = new ArrayList<String>();

               for(int i = 0; i < locations.length(); i++){

                   list.add(locations.getString(i));
             }


                if (success == 1) {
                    Toast.makeText(getActivity().getApplicationContext(), "Locations Exist!!!", Toast.LENGTH_LONG).show();
                    Log.d("Locations Exist", json.toString());
                    //***CODE HERE TO SHOW LOCATIONS*****

                    return json.getString(TAG_SUCCESS);
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Locations Empty!!!", Toast.LENGTH_LONG).show();
                    Log.d("Locations Empty", json.getString(TAG_SUCCESS));
                    //*****CODE HERE TO SHOW EMPTY SCREEN********

                    return json.getString(TAG_SUCCESS);
                    }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String file_url)
        {
            pDialog.dismiss();
          //  langs= new String[]{"C", "C++", "JAVA", "Android", "HTML5", "CSS3", "iOS", "Bootstrap", "JSON"};
            adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list);
            grid.setAdapter(adapter);
            //grid.setAdapter(new ArrayAdapter<String>(adapter));
            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getActivity().getApplicationContext(), "You Clicked on : " + adapter.getItem(position), Toast.LENGTH_SHORT).show();
                }
            });
            if (file_url != null) {
                Toast.makeText(getActivity().getApplicationContext(), file_url, Toast.LENGTH_LONG).show();
            }



        }

    }

}
