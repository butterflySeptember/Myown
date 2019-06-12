package com.example.fmplayer.utils;

import com.example.fmplayer.R;
import com.example.fmplayer.fragment.BaseFragment;
import com.example.fmplayer.fragment.DistoryFragment;
import com.example.fmplayer.fragment.MainFragment;
import com.example.fmplayer.fragment.MineFagment;
import com.example.fmplayer.fragment.MyListenerFagment;

import java.util.HashMap;
import java.util.Map;

public class FragmentCreate {

    public static Map<Integer, BaseFragment> scashe = new HashMap<>();

    public static BaseFragment getFragmentByTabID(int tabId) {
        BaseFragment baseFragment = scashe.get(tabId);
        if (baseFragment != null) {
            return baseFragment;
        }

        switch (tabId) {
            case R.id.tab_index:
                baseFragment = new MainFragment();
                break;
            case R.id.tab_discovery:
                baseFragment = new DistoryFragment();
                break;
            case R.id.tab_mylinster:
                baseFragment = new MyListenerFagment();
                break;
            case R.id.tab_mine:
                baseFragment = new MineFagment();
                break;

        }
        return baseFragment;
    }
}