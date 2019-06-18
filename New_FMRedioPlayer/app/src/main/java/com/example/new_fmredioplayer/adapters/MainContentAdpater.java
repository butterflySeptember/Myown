package com.example.new_fmredioplayer.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentContainer;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.new_fmredioplayer.utils.FragmentCreater;

public class MainContentAdpater extends FragmentPagerAdapter {
	public MainContentAdpater(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {

		return FragmentCreater.getFragment(position);
	}

	@Override
	public int getCount() {

		return FragmentCreater.PAGER_COUNT;
	}
}
