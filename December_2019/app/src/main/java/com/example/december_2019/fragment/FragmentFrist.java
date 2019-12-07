package com.example.december_2019.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.december_2019.R;

import java.util.zip.Inflater;

public class FragmentFrist extends BaseFragment {

	private View mView;

	@Override
	public View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
		mView =  layoutInflater.inflate(R.layout.fragment_first, null);
		return mView;
	}

}
