package com.example.taobaounion.ui.activity.fragment;

import android.os.Bundle;
import android.view.View;

import com.example.taobaounion.R;
import com.example.taobaounion.base.baseFragment;
import com.example.taobaounion.domain.Catgories;
import com.example.taobaounion.utils.Constants;
import com.example.taobaounion.utils.LogUtils;

public class HomePagerFragment extends baseFragment {

	public static HomePagerFragment newInstance(Catgories.DataBean category){
		HomePagerFragment homePagerFragment = new HomePagerFragment();
		Bundle bundle = new Bundle();
		bundle.putString(Constants.KEY_HOME_PAGER_TITLE,category.getTitle());
		bundle.putInt(Constants.KEY_HOME_PAGER_MATERIAL_ID,category.getId());
		homePagerFragment.setArguments(bundle);
		return homePagerFragment;
	}

	@Override
	protected int getRootId() {
		return R.layout.fragment_home_pager;
	}

	@Override
	protected void initView(View rootView) {
		setUpStatus(Status.SUCCESS);
	}

	@Override
	protected void loadData() {
		Bundle arguments = getArguments();
		String title = arguments.getString(Constants.KEY_HOME_PAGER_TITLE);
		int materialId = arguments.getInt(Constants.KEY_HOME_PAGER_MATERIAL_ID);
		//加载数据
		LogUtils.d(HomePagerFragment.class,"title -- > " + title);
		LogUtils.d(HomePagerFragment.class,"material ID -- > " + materialId);
	}
}
