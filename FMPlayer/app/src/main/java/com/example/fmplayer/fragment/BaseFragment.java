package com.example.fmplayer.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 界面的内容
 * 点击事件
 * 数据
 */
public abstract class BaseFragment extends Fragment {

	private final static String TAG = "BaseFragment";
    private View rootView = null;

    public BaseFragment(){

	}

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
         rootView = getSubView(inflater,container,false);
         //设置监听
        setSubListener();
		Log.i(TAG, "onCreateView: ");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected abstract void setSubListener();

    protected abstract View getSubView(LayoutInflater inflater, ViewGroup container, boolean b);
}
