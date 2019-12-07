package com.example.december_2019.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.december_2019.R;

public class FragmentSecond extends BaseFragment {

	private View mView;

	@Override
	public View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
		mView = layoutInflater.inflate(R.layout.fragment_second, null);
		return mView;
	}
}
