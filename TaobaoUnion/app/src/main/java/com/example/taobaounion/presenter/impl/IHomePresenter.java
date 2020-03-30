package com.example.taobaounion.presenter.impl;

import com.example.taobaounion.View.IHomeCallback;

public interface IHomePresenter {

	/**
	 * 获取商品分类
	 */
	void getCategories();

	/**
	 * 注册UI通知接口
	 *
	 * @param callback
	 */
	void registerViewCallback(IHomeCallback callback);

	/**
	 * 取消UI通知的接口
	 *
	 * @param callback
	 */
	void unregisterViewCallback(IHomeCallback callback);
}
