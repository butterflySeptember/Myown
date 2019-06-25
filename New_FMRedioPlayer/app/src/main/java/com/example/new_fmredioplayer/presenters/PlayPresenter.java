package com.example.new_fmredioplayer.presenters;

import android.app.job.JobInfo;

import com.example.new_fmredioplayer.base.baseApplication;
import com.example.new_fmredioplayer.interfaces.IPlayerCallback;
import com.example.new_fmredioplayer.interfaces.IPlayerPresenter;
import com.example.new_fmredioplayer.utils.LogUtils;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.util.List;

public class PlayPresenter implements IPlayerPresenter {

	private final XmPlayerManager mPlayerManager;
	private final static String TAG = "PlayPresenter";

	private  PlayPresenter(){
		mPlayerManager = XmPlayerManager.getInstance(baseApplication.getAppContext());
	}

	private static PlayPresenter sPlayPresenter;

	public static PlayPresenter getPlayPresenter(){
		if (sPlayPresenter == null) {
			synchronized (sPlayPresenter){
				if (sPlayPresenter == null) {
					sPlayPresenter = new PlayPresenter();
				}
			}
		}
		return sPlayPresenter;
	}

	private boolean isPlayListSet = false;

	public void setPlayList(List<Track> list,int playIndex){
		if (mPlayerManager != null) {
			mPlayerManager.setPlayList(list,playIndex);
			isPlayListSet = true;
		}else{
			LogUtils.d(TAG,"play list is null ...");
		}
	}

	@Override
	public void play() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void stop() {

	}

	@Override
	public void playPre() {

	}

	@Override
	public void playNext() {

	}

	@Override
	public void switchPlayMode(XmPlayListControl.PlayMode mode) {

	}

	@Override
	public void getPlayList() {

	}

	@Override
	public void playByIndex(int index) {

	}

	@Override
	public void seekTo(int progress) {

	}

	@Override
	public void registerViewCallback(IPlayerCallback iPlayerCallback) {

	}

	@Override
	public void unRegisterViewCallback(IPlayerCallback iPlayerCallback) {

	}
}
