package com.neoresearch.bookmyspace;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {


    final int PAGE_COUNT = 2;


    Context context;

    public ViewPagerAdapter(FragmentManager fm) {
            super(fm);

    }

    public int getCount() {

        return PAGE_COUNT;
    }

    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Login login = new Login();
                return login;

            case 1:
                Register register = new Register();
                return register;


        }
        return null;
    }




}
