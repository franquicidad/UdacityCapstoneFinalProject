package com.example.mac.udacitycapstonefinalproject;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabPagerAdapter  extends FragmentPagerAdapter {

    private static int tabCount=2;
    public  TabPagerAdapter(FragmentManager fm, int numberOfTabs){
        super(fm);
        this.tabCount=numberOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                CarroNuevoFragment tab1 = new CarroNuevoFragment();
                return tab1;
            case  1:
                CarroNuevoFragment tab2 = new CarroNuevoFragment();
                return tab2;
            default:
                return null;
//
        }
    }

    @Override
    public int getCount() {
        return  tabCount;
    }

}


