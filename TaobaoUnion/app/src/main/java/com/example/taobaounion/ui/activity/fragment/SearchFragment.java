package com.example.taobaounion.ui.activity.fragment;

import android.view.View;

import com.example.taobaounion.R;
import com.example.taobaounion.base.baseFragment;

public class SearchFragment extends baseFragment {
	@Override
	protected int getRootId() {
		return R.layout.fragment_search;
	}

	@Override
	protected void initView(View rootView) {
		setUpStatus(Status.SUCCESS);
	}
}
