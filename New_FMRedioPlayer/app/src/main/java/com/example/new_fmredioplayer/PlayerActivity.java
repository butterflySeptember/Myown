package com.example.new_fmredioplayer;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.example.new_fmredioplayer.base.BaseActivity;
import com.example.new_fmredioplayer.interfaces.IPlayerCallback;
import com.example.new_fmredioplayer.presenters.PlayPresenter;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.util.List;

public class PlayerActivity extends BaseActivity implements IPlayerCallback {

	private ImageView mControlBtn;
	private PlayPresenter mPlayPresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
		//初始化控件
		mPlayPresenter = PlayPresenter.getPlayPresenter();
		//接口注册
		mPlayPresenter.registerViewCallback(this);
		initView();
		initEvent();
		startPlay();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mPlayPresenter != null) {
			mPlayPresenter.unRegisterViewCallback(this);
		}
	}

	private void startPlay() {
		if (mPlayPresenter != null) {
			mPlayPresenter.play();
		}
	}

	/**
	 * 设置相关的事件
	 */
	private void initEvent() {
		mControlBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//更改播放状态
				if (mPlayPresenter.isPlay()) {
					mPlayPresenter.pause();
				}else{
					mPlayPresenter.play();
				}
			}
		});
	}

	/**
	 * 找到各个控件
	 */
	private void initView() {
		mControlBtn = this.findViewById(R.id.play_or_stop_btn);
		
	}

	@Override
	public void onPlayStart() {
		//开始播放
		if (mControlBtn != null) {
			mControlBtn.setImageResource(R.mipmap.stop_info_icon);
		}
	}

	@Override
	public void onPlayPause() {
		//暂停播放
		if (mControlBtn != null) {
			mControlBtn.setImageResource(R.mipmap.play_icon);
		}
	}

	@Override
	public void onPlayStop() {
		if (mControlBtn != null) {
			mControlBtn.setImageResource(R.mipmap.play_icon);
		}
	}

	@Override
	public void onPlayerError() {

	}

	@Override
	public void nextPlay(Track track) {

	}

	@Override
	public void onPrePlayer(Track track) {

	}

	@Override
	public void onListLoaded(List<Track> list) {

	}

	@Override
	public void onPlayModeChange(XmPlayListControl.PlayMode playMode) {

	}

	@Override
	public void onProgramsChange(long current, long total) {

	}

	@Override
	public void onAdLoading() {

	}

	@Override
	public void onAdFinished() {

	}

	@Override
	public void registerViewCallback(Object o) {

	}

	@Override
	public void unRegisterViewCallback(Object o) {

	}
}
