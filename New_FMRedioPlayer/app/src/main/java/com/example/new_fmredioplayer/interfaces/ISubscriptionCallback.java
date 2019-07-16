package com.example.new_fmredioplayer.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.List;

public interface ISubscriptionCallback {

	/**
	 * 返回添加专辑的结果
	 *
	 * @param isSuccess
	 */
	void onAddResult(boolean isSuccess);

	/**
	 * 返回删除专辑的结果
	 *
	 * @param isSuccess
	 */
	void onDeleteResult(boolean isSuccess);

	/**
	 * 返回获取订阅专辑的结果
	 *
	 * @param albumList
	 */
	void onSubscriptionLoaded(List<Album> albumList);
}
