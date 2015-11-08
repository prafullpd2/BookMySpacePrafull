package com.neoresearch.bookmyspace;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ConfirmationForBooking extends AppCompatActivity implements View.OnClickListener {

    TextView name, address, contact;
    EditText vehicleID, time;
    Button book, cancle;
    protected ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_for_booking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = (TextView) findViewById(R.id.name_confimation);
        address = (TextView) findViewById(R.id.address_confirmation);
        contact = (TextView) findViewById(R.id.contact_confirmation);

        Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        address.setText(intent.getStringExtra("address"));
        contact.setText(intent.getStringExtra("contact"));

        vehicleID = (EditText) findViewById(R.id.vehicle_confirmation);
        time = (EditText) findViewById(R.id.time_confirmation);

        book = (Button) findViewById(R.id.book_confirmation);
        cancle = (Button) findViewById(R.id.cancel_confirmation);

        book.setOnClickListener(this);
        cancle.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.book_confirmation:



                break;


            case R.id.cancel_confirmation:


                super.onBackPressed();
                break;


        }


    }


//    class BookParking extends AsyncTask<String, String, String> {
//
//
//        @Override
//        protected String doInBackground(String... params) {
//            return null;
//        }
//
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//
//            pDialog = new ProgressDialog(getActivity());
//            pDialog.setMessage("Attempting login...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
//
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//        }
//    }

}