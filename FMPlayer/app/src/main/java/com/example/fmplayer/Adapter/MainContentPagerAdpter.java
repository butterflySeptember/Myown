package com.example.fmplayer.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.example.fmplayer.utils.Constants;

public class MainContentPagerAdpter extends FragmentPagerAdapter {

    public MainContentPagerAdpter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int i) {
        return null;
    }

    @Override
    public int getCount() {
        return Constants.TABS_COUNT;
    }
}
