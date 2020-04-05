package com.example.taobaounion.ui.activity.fragment;

import android.view.View;

import com.example.taobaounion.R;
import com.example.taobaounion.base.baseFragment;

public class RedPacketFragment extends baseFragment {
	@Override
	protected int getRootId() {
		return R.layout.fragment_red_packet;
	}

	@Override
	protected void initView(View rootView) {
		setUpStatus(Status.SUCCESS);
	}
}
