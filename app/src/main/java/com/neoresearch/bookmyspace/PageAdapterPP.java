package com.neoresearch.bookmyspace;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class PageAdapterPP extends FragmentPagerAdapter {


        final int PAGE_COUNTPP = 2;

        public PageAdapterPP(FragmentManager fm) {

            super(fm);

        }
    @Override

    public int getCount() {

        return PAGE_COUNTPP;

    }


    @Override

        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Locations loc = new Locations();
                     return loc;
                  //  Login log=new Login();
                  //  return log;

                case 1:
                    AddLocations ad=new AddLocations();
                    return ad;
                   // Register r=new Register();
                   // return r;


            }
            return null;
        }


    }


