package com.example.new_fmredioplayer.utils;

import com.example.new_fmredioplayer.base.BaseFragment;
import com.example.new_fmredioplayer.fragments.HistoryFragment;
import com.example.new_fmredioplayer.fragments.RecommendFragment;
import com.example.new_fmredioplayer.fragments.SubscriptionFragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentCreater {

	public final static int INDEX_RECOMMEND = 0;
	public final static int INDEX_SUBSCRIPTION = 1;
	public final static int INDEX_HISTORY = 2;

	public final static int PAGER_COUNT = 3;

	private static Map<Integer, BaseFragment> sCache = new HashMap<>();

	public static BaseFragment getFragment(int index){
		BaseFragment baseFragment = sCache.get(index);
		if (baseFragment != null) {
			return baseFragment;
		}
		switch (index){
			case INDEX_RECOMMEND:
				baseFragment = new RecommendFragment();
				break;
			case INDEX_SUBSCRIPTION:
				baseFragment = new SubscriptionFragment();
				break;
			case INDEX_HISTORY:
				baseFragment = new HistoryFragment();
				break;
		}
		sCache.put(index,baseFragment);
		return baseFragment;
	}
}
