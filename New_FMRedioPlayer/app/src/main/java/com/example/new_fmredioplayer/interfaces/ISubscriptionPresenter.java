package com.example.new_fmredioplayer.interfaces;

import com.example.new_fmredioplayer.base.BasePresenter;
import com.ximalaya.ting.android.opensdk.model.album.Album;

/**
 *订阅数量一般有上限
 */
public interface ISubscriptionPresenter extends BasePresenter<ISubscriptionCallback> {

	/**
	 * 添加订阅
	 *
	 * @param album
	 */
	void addSubscription(Album album);

	/**
	 * 删除订阅
	 *
	 * @param album
	 */
	void deleteSubscription(Album album);

	/**
	 * 获取订阅列表
	 */
	void getSubscriptionList();
}
