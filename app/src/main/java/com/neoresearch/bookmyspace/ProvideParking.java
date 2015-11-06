package com.neoresearch.bookmyspace;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
//ping database for previously added locations
//if locations exist, list them
//if not , then show a textview- no locations provided yet

public class ProvideParking extends ActionBarActivity {
    ViewPager pageAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provide_parking);
        ActionBar actionBar=getActionBar();
        setTitle("Book My Space");
        getSupportActionBar().setLogo(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);
        pageAdapter=(ViewPager)findViewById(R.id.viewpager);
        pageAdapter.setAdapter(new PageAdapterPP(getSupportFragmentManager()));
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_provide_parking, menu);
        return true;

    }
    public void setCurrentItem (int item, boolean smoothScroll) {
        pageAdapter.setCurrentItem(item, smoothScroll);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
