package com.example.new_fmredioplayer.data;

import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.List;

public interface ISubDaoCallback {

	/**
	 * 添加结果的回调
	 *
	 * @param isOkay
	 */
	void onAddResult(boolean isOkay);

	/**
	 * 删除结果的回调
	 *
	 * @param isOkay
	 */
	void onDelResult(boolean isOkay);

	/**
	 * 收藏专辑的返回结果
	 * @param albumList
	 */
	void onSubListLoaded(List<Album> albumList);
}
