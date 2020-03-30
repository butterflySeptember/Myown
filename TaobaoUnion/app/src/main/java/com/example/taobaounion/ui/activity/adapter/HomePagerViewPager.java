package com.example.taobaounion.ui.activity.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.taobaounion.domain.Catgories;
import com.example.taobaounion.ui.activity.fragment.HomePagerFragment;
import com.example.taobaounion.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class HomePagerViewPager extends FragmentPagerAdapter {

	private List<Catgories.DataBean> categories = new ArrayList<>();

	public HomePagerViewPager(@NonNull FragmentManager fm) {
		super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
	}

	@Nullable
	@Override
	public CharSequence getPageTitle(int position) {
		return categories.get(position).getTitle();
	}

	@NonNull
	@Override
	public Fragment getItem(int position) {
		HomePagerFragment homePagerFragment = new HomePagerFragment();
		return homePagerFragment;
	}

	@Override
	public int getCount() {
		return categories.size();
	}

	public void setCatgories(Catgories category) {
		categories.clear();
		LogUtils.d(HomePagerViewPager.class,"category size -- > " + category.getData().size());
		List<Catgories.DataBean> data = category.getData();
		categories.addAll(data);
		notifyDataSetChanged();
	}
}
