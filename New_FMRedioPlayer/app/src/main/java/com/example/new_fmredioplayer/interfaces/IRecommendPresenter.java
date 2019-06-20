package com.example.new_fmredioplayer.interfaces;

import android.view.View;

public interface IRecommendPresenter {

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

	/**
	 *这个方法用于注册UI的回调
	 */
	void registerViewCallback(IRecommendViewCallback callback);

	/**
	 * 取消UI的回调注册
	 * @param callback
	 */
	void unregisterViewCallback(IRecommendViewCallback callback);
}
