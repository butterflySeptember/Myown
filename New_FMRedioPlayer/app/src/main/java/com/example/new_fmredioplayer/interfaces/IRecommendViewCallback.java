package com.example.new_fmredioplayer.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.List;

public interface IRecommendViewCallback {

	/**
	 * 获取推荐内容的结果
	 * @param result
	 */
	void onRecommendListloaded(List<Album> result);

	/**
	 * 加载更多
	 *
	 * @param result
	 */
	void onLoaderMore(List<Album> result);

	/**
	 * 下拉加载更多的结果
	 *
	 * @param result
	 */
	void onRefreshMore(List<Album> result);
}
