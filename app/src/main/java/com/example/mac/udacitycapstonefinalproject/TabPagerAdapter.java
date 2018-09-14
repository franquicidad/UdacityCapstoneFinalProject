package com.example.mac.udacitycapstonefinalproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabPagerAdapter  extends FragmentPagerAdapter {

    int tabCount;
    public  TabPagerAdapter(FragmentManager fm, int numberOfTabs){
        super(fm);
        this.tabCount=numberOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
//        switch (position){
//            case 0:
//                CarroNuevoFragment tab1 = new CarroNuevoFragment();
//                return tab1;
//            case  1:
//                CarroUsadoFragment tab2 = new CarroUsadoFragment();
//                return tab2;
//            default:
//                return null;
//
//        }
            return null;
    }

    @Override
    public int getCount() {
        return  tabCount;
    }
}


