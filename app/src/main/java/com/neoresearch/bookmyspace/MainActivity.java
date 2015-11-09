package com.neoresearch.bookmyspace;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.HashMap;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
ImageButton provide_parking,use_parking;
    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        provide_parking=(ImageButton)findViewById(R.id.provide_parking);
        use_parking=(ImageButton)findViewById(R.id.use_parking);

        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        session=new SessionManager(getApplicationContext());

        setTitle("Book or Provide Space");
        getSupportActionBar().setLogo(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);
        getSupportActionBar().setElevation((float) 3.2);
        getSupportActionBar().setIcon(R.drawable.logout);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        HashMap<String, String> user = session.getUserDetails();
        String mobile = user.get(SessionManager.KEY_MOBILE);
        provide_parking.setOnClickListener(this);
        use_parking.setOnClickListener(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       switch(item.getItemId())
       {
           case R.id.action_settings : return true;
           case R.id.action_logout :session.logoutUser();
               Intent i=new Intent(MainActivity.this,LoginRegisterActivity.class);
               startActivity(i);
               Toast.makeText(MainActivity.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
               return true;


       }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.provide_parking:
            {Intent i = new Intent(MainActivity.this,ProvideParking.class);
                startActivity(i);
                Log.d("Go", "Code Accessed");
                break;}

            case R.id.use_parking:
                Intent i = new Intent(MainActivity.this,UsersNavigationDrawer.class);
                startActivity(i);
                Log.d("Go User", "User's Code Accessed");

        }

        }

    }


