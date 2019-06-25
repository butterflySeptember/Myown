package com.example.new_fmredioplayer.interfaces;

import com.example.new_fmredioplayer.base.BasePresenter;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.util.List;

public interface IPlayerCallback extends BasePresenter {

	/**
	 * 开始播放
	 */
	void onPlayStart();

	/**
	 * 播放暂停
	 */
	void onPlayPause();

	/**
	 * 播放停止
	 */
	void onPlayStop();

	/**
	 * 播放错误
	 */
	void onPlayerError();

	/**
	 * 下一首播放
	 */
	void nextPlay(Track track);

	/**
	 * 上一首播放
	 */
	void onPrePlayer(Track track);

	/**
	 * 播放列表数据加载
	 *
	 * @param list 播放列表数据
	 */
	void onListLoaded(List<Track> list);

	/**
	 * 播放器模式改变
	 *
	 * @param playMode
	 */
	void onPlayModeChange(XmPlayListControl.PlayMode playMode);

	/**
	 * 进度条的改变
	 *
	 * @param current
	 * @param total
	 */
	void onProgramsChange(long current,long total);

	/**
	 * 广告加载完成
	 */
	void onAdLoading();

	/**
	 * 广告结束
	 */
	void onAdFinished();
}
