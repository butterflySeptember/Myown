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

    public static BaseFragment getFragmentByPosition(int position) {
        BaseFragment baseFragment = scashe.get(position);
        if (baseFragment != null) {
            return baseFragment;
        }

        switch (position) {
            case Constants.PAGE_MAIN:
                baseFragment = new MainFragment();
                break;
            case Constants.PAGE_MY_LISTENER:
                baseFragment = new DistoryFragment();
                break;
            case Constants.PAGE_DISTORY:
                baseFragment = new MyListenerFagment();
                break;
            case Constants.PAGE_MINE:
                baseFragment = new MineFagment();
                break;

        }
        scashe.put(position,baseFragment);
        return baseFragment;
    }
}