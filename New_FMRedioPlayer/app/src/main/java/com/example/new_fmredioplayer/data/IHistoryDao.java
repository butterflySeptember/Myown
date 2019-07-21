package com.example.new_fmredioplayer.data;

import com.ximalaya.ting.android.opensdk.model.track.Track;

public interface IHistoryDao {

	/**
	 * 设置回调的网络接口
	 *
	 * @param callback
	 */
	void setCallback(IHistoryDaoCallback callback);

	/**
	 * 添加历史
	 * @param track
	 */
	void addHistory(Track track);

	/**
	 * 删除历史
	 * @param track
	 */
	void detailHistory(Track track);

	/**
	 * 清除历史内容
	 */
	void clearDistory();

	/**
	 * 获取历史内容
	 */
	void listHistories();
}
