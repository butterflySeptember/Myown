package com.example.new_fmredioplayer.interfaces;

import com.example.new_fmredioplayer.base.BasePresenter;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

public interface IPlayerPresenter extends BasePresenter <IPlayerCallback>{

	/**
	 * 播放
	 */
	void play();

	/**
	 * 暂停
	 */
	void pause();

	/**
	 * 停止
	 */
	void stop();

	/**
	 * 上一首
	 */
	void playPre();

	/**
	 * 下一首
	 */
	void playNext();

	/**
	 * 切换播放模式
	 * @param mode
	 */
	void switchPlayMode(XmPlayListControl.PlayMode mode);

	/**
	 * 获取播放列表
	 */
	void getPlayList();

	/**
	 * 根据节目的位置进行播放
	 *
	 * @param index 节目在列表中的位置
	 */
	void playByIndex(int index);

	/**
	 * 切换播放进度
	 *
	 * @param progress B
	 */
	void  seekTo(int progress);
}