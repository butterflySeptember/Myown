package com.example.new_fmredioplayer.data;

import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;

public interface IHistoryDaoCallback {

	/**
	 * 添加历史的结果
	 *
	 * @param isSuccess
	 */
	void onHistoryAdd(boolean isSuccess);

	/**
	 * 删除历史的结果
	 *
	 * @param isSuccess
	 */
	void onHistoryDel(boolean isSuccess);

	/**
	 * 加载历史的结果
	 *
	 * @param trackList
	 */
	void onHistoryLoaded(List<Track> trackList);

	/**
	 * 清除历史内容的结果
	 * @param isSuccess
	 */
	void onHistoryClear(boolean isSuccess);
}
