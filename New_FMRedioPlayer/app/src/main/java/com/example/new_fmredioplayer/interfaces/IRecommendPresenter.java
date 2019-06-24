package com.example.new_fmredioplayer.interfaces;

import android.view.View;

import com.example.new_fmredioplayer.base.BasePresenter;

public interface IRecommendPresenter extends BasePresenter {

	/**
	 * 获取推荐内容
	 */
	void getRecommendList();

	/**
	 *下拉刷新内容
	 */
	void  pull2RefreshMore();

	/**
	 * 下拉加载更多
	 */
	void loadMore();

}
