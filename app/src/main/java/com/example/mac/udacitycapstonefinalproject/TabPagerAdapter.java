package com.example.mac.udacitycapstonefinalproject;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabPagerAdapter  extends FragmentPagerAdapter {

    private static int tabCount=2;
    public  TabPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return CarroNuevoFragment.newInstance(0, "");
            case  1:
                return CarroUsadoFragment.newInstance2(0, "Used");
            default:
                return null;
//
        }
    }

    @Override
    public int getCount() {
        return  tabCount;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "New";
            case 1:
                return "Used";



        }
        return null;
    }
}


