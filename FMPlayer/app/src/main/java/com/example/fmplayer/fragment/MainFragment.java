package com.example.fmplayer.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fmplayer.R;

public class MainFragment extends BaseFragment {
    @Override
    protected void setSubListener() {

    }

    @Override
    protected View getSubView(LayoutInflater inflater, ViewGroup container, boolean b) {
        View rootView = inflater.inflate(R.layout.fragment_main,container,false);
        return rootView;
    }
}
