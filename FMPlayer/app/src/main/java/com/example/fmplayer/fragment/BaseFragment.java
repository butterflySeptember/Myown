package com.example.fmplayer.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 界面的内容
 * 点击事件
 * 数据
 */
public abstract class BaseFragment extends Fragment {


    private View rootView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         rootView = getSubView();
         //设置监听
        setSubListener();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected abstract void setSubListener();

    protected abstract View getSubView();
}
