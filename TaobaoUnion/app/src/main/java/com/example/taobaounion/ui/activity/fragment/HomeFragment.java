package com.example.taobaounion.ui.activity.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.viewpager.widget.ViewPager;

import com.example.taobaounion.R;
import com.example.taobaounion.View.IHomeCallback;
import com.example.taobaounion.base.baseFragment;
import com.example.taobaounion.domain.Catgories;
import com.example.taobaounion.presenter.impl.impl.HomePresenterImpl;
import com.example.taobaounion.ui.activity.adapter.HomePagerViewPager;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;

public class HomeFragment extends baseFragment implements IHomeCallback {

	@BindView(R.id.home_indicator)
	public TabLayout mTabLayout;

	@BindView(R.id.home_pager)
	public ViewPager homeViewPager;

	private HomePresenterImpl mHomePresenter;
	private HomePagerViewPager mHomePagerViewPager;

	@Override
	protected int getRootId() {
		return R.layout.fragment_home;
	}

	@Override
	protected void initPresenter() {
		mHomePresenter = new HomePresenterImpl();
		mHomePresenter.registerViewCallback(this);
	}

	@Override
	protected void loadData() {
		mHomePresenter.getCategories();
	}

	@Override
	protected void initView(View rootView) {
		mTabLayout.setupWithViewPager(homeViewPager);
		//设置适配器
		mHomePagerViewPager = new HomePagerViewPager(getChildFragmentManager());


	}

	@Override
	public void onCategoriesLoad(Catgories category) {
		setUpStatus(Status.SUCCESS);
		//返回加载的数据
		if (mHomePagerViewPager != null) {
			mHomePagerViewPager.setCatgories(category);
		}
	}

	@Override
	protected View loadRootView(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.base_home_fragment_layout,container,false);
	}

	@Override
	public void onNetworkError() {
		setUpStatus(Status.ERROR);
	}

	@Override
	public void onLoading() {
		setUpStatus(Status.LOADING);
	}

	@Override
	public void onEmpty() {
		setUpStatus(Status.EMPTY);
	}

	@Override
	protected void release() {
		//取消注册
		if (mHomePresenter != null) {
			mHomePresenter.unregisterViewCallback(this);
		}
	}

	@Override
	protected void onRetryClick() {
		//网络错误点击，重新加载信息
		if (mHomePresenter != null) {
			mHomePresenter.getCategories();
		}
		super.onRetryClick();
	}
}
