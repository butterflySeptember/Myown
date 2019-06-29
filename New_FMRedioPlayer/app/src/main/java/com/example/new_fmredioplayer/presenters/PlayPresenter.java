package com.example.new_fmredioplayer.presenters;

import android.app.job.JobInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.new_fmredioplayer.base.baseApplication;
import com.example.new_fmredioplayer.interfaces.IPlayerCallback;
import com.example.new_fmredioplayer.interfaces.IPlayerPresenter;
import com.example.new_fmredioplayer.utils.LogUtils;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.advertis.Advertis;
import com.ximalaya.ting.android.opensdk.model.advertis.AdvertisList;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.player.advertis.IXmAdsStatusListener;
import com.ximalaya.ting.android.opensdk.player.constants.PlayerConstants;
import com.ximalaya.ting.android.opensdk.player.service.IXmPlayerStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayerException;

import java.util.ArrayList;
import java.util.List;

import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_LIST;
import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_LIST_LOOP;
import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_RANDOM;
import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_SINGLE_LOOP;

public class PlayPresenter implements IPlayerPresenter, IXmAdsStatusListener, IXmPlayerStatusListener {

	private List<IPlayerCallback> mIPlayerCallbacks = new ArrayList<>();
	private final XmPlayerManager mPlayerManager;
	private final static String TAG = "PlayPresenter";
	private Track mCurrentTrack;
	private int mCurrentIndex = 0;
	private final SharedPreferences mPlayModeSp;
	/**
	 * PLAY_MODEL_LIST
	 * PLAY_MODEL_LIST_LOOP
	 * PLAY_MODEL_RANDOM
	 * PLAY_MODEL_SINGLE_LOOP
	 */
	public static final int PLAY_MODEL_LIST_INT = 0;
	public static final int PLAY_MODEL_LIST_LOOP_INT = 1;
	public static final int PLAY_MODEL_RANDOM_INT = 2;
	public static final int PLAY_MODEL_SINGLE_LOOP_INT = 3;
	//sp * s key and name
	public static final String PLAY_MODE_SP_NAME = "PlayMode";
	public static final String PLAY_MODE_SP_KEY = "CurrentPlayMode";

	private  PlayPresenter(){
		mPlayerManager = XmPlayerManager.getInstance(baseApplication.getAppContext());
		//广告相关的物料接口注册
		mPlayerManager.addAdsStatusListener(this);
		//播放器状态相关的接口注册
		mPlayerManager.addPlayerStatusListener(this);
		//记录当前的播放模式
		mPlayModeSp = baseApplication.getAppContext().getSharedPreferences(PLAY_MODE_SP_NAME, Context.MODE_PRIVATE);
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
			mCurrentTrack = list.get(playIndex);
			mCurrentIndex = playIndex;
		}else{
			LogUtils.d(TAG,"play list is null ...");
		}
	}

	@Override
	public void play() {
		if (isPlayListSet) {
			//播放状态值
			//mPlayerManager.getPlayerStatus();
			LogUtils.d(TAG,"play -- > play status -- > " + mPlayerManager.getPlayerStatus());
			mPlayerManager.play();
		}
	}

	@Override
	public void pause() {
		if (mPlayerManager != null) {
			mPlayerManager.stop();
		}
	}

	@Override
	public void stop() {

	}

	@Override
	public void playPre() {
		//播放上一首
		if (mPlayerManager != null) {
			mPlayerManager.playPre();
		}
	}

	@Override
	public void playNext() {
		//播放下一首
		if (mPlayerManager != null) {
			mPlayerManager.playNext();
		}
	}

	@Override
	public void switchPlayMode(XmPlayListControl.PlayMode mode) {
		if (mPlayerManager != null) {
			mPlayerManager.setPlayMode(mode);
			//通知UI层播放状态的改变
			for (IPlayerCallback iPlayerCallback : mIPlayerCallbacks) {
				iPlayerCallback.onPlayModeChange(mode);
			}
			//将播放状态保存入SP中
			SharedPreferences.Editor editor = mPlayModeSp.edit();
			editor.putInt(PLAY_MODE_SP_KEY,getIntByPlatMode(mode));
			editor.commit();
		}
	}

	private int getIntByPlatMode (XmPlayListControl.PlayMode mode){
		switch (mode){
			case PLAY_MODEL_SINGLE_LOOP:
				return PLAY_MODEL_SINGLE_LOOP_INT;
			case PLAY_MODEL_LIST:
				return PLAY_MODEL_LIST_INT;
			case PLAY_MODEL_RANDOM:
				return PLAY_MODEL_RANDOM_INT;
			case PLAY_MODEL_LIST_LOOP:
				return PLAY_MODEL_LIST_LOOP_INT;
		}
		return PLAY_MODEL_LIST_INT;
	}

	private XmPlayListControl.PlayMode getModeIntByPlay(int index){
		switch (index){
			case PLAY_MODEL_SINGLE_LOOP_INT:
				return PLAY_MODEL_SINGLE_LOOP;
			case PLAY_MODEL_LIST_INT:
				return PLAY_MODEL_LIST;
			case PLAY_MODEL_RANDOM_INT:
				return PLAY_MODEL_RANDOM;
			case PLAY_MODEL_LIST_LOOP_INT:
				return PLAY_MODEL_LIST_LOOP;
		}
		return PLAY_MODEL_LIST;
	}

	@Override
	public void getPlayList() {
		if (mPlayerManager != null) {
			List<Track> playList = mPlayerManager.getPlayList();
			for (IPlayerCallback iPlayerCallback : mIPlayerCallbacks) {
				iPlayerCallback.onListLoaded(playList);
			}
		}
	}

	@Override
	public void playByIndex(int index) {
		//切换播放器到index位置
		if (mPlayerManager != null) {
			mPlayerManager.play(index);
		}
	}

	@Override
	public void seekTo(int progress) {
		//更新进度条的进度
		mPlayerManager.seekTo(progress);
	}

	@Override
	public boolean isPlay() {
		//返回当前是否正在播放
		return mPlayerManager.isPlaying();
	}

	@Override
	public void registerViewCallback(IPlayerCallback iPlayerCallback) {
		iPlayerCallback.onTrackUpdate(mCurrentTrack , mCurrentIndex);
		//从SP中获得播放状态
		int modeIndex = mPlayModeSp.getInt(PLAY_MODE_SP_KEY, PLAY_MODEL_LIST_INT);
		iPlayerCallback.onPlayModeChange(getModeIntByPlay(modeIndex));
		if (mIPlayerCallbacks.contains(iPlayerCallback)) {
			mIPlayerCallbacks.add(iPlayerCallback);
		}
	}

	@Override
	public void unRegisterViewCallback(IPlayerCallback iPlayerCallback) {
		mIPlayerCallbacks.remove(iPlayerCallback);
	}

	//==========================广告相关的数据接口  start========================
	@Override
	public void onStartGetAdsInfo() {
		LogUtils.d(TAG,"onStartGetAdsInfo ...");
	}

	@Override
	public void onGetAdsInfo(AdvertisList advertisList) {
		LogUtils.d(TAG,"onGetAdsInfo ...");
	}

	@Override
	public void onAdsStartBuffering() {
		LogUtils.d(TAG,"onAdsStartBuffering ...");
	}

	@Override
	public void onAdsStopBuffering() {
		LogUtils.d(TAG,"onAdsStopBuffering ...");
	}

	@Override
	public void onStartPlayAds(Advertis advertis, int i) {
		LogUtils.d(TAG,"onStartPlayAds ...");
	}

	@Override
	public void onCompletePlayAds() {
		LogUtils.d(TAG,"onCompletePlayAds ...");
	}

	@Override
	public void onError(int what, int extra) {
		LogUtils.d(TAG,"onError what --> " + what + "onError extra  -- > " + extra);
	}
	//==========================广告相关的数据接口  end=============================
	//
	//==========================播放器相关的数据接口 start=============================
	@Override
	public void onPlayStart() {
		LogUtils.d(TAG,"onPlayStart ...");
		for (IPlayerCallback iPlayerCallback : mIPlayerCallbacks) {
			iPlayerCallback.onPlayStart();
		}
	}

	@Override
	public void onPlayPause() {
		LogUtils.d(TAG,"onPlayPause ...");
		for (IPlayerCallback iPlayerCallback : mIPlayerCallbacks) {
			iPlayerCallback.onPlayPause();
		}
	}

	@Override
	public void onPlayStop() {
		LogUtils.d(TAG,"onPlayStop ...");
		for (IPlayerCallback iPlayerCallback : mIPlayerCallbacks) {
			iPlayerCallback.onPlayStop();
		}
	}

	@Override
	public void onSoundPlayComplete() {
		LogUtils.d(TAG,"onSoundPlayComplete ...");
	}

	@Override
	public void onSoundPrepared() {
		LogUtils.d(TAG,"onSoundPrepared ...");
		if (mPlayerManager.getPlayerStatus() == PlayerConstants.STATE_PREPARED) {
			//播放器初始化结束，可以播放
			mPlayerManager.play();
		}
	}

	@Override
	public void onSoundSwitch(PlayableModel lastMode, PlayableModel curMode) {
		LogUtils.d(TAG,"onSoundSwitch ...");
		LogUtils.d(TAG,"lastMode -- > " + lastMode.getKind());
		mCurrentIndex = mPlayerManager.getCurrentIndex();
		//curMode为当前播放的内容
		//通过getKind（）方法获得为track类型
		if (curMode instanceof Track) {
			Track currentTrack = (Track) curMode ;
			mCurrentTrack = currentTrack;
			//LogUtils.d(TAG,"title -- > " + ((Track) curMode).getTrackTitle());
			for (IPlayerCallback iPlayerCallback : mIPlayerCallbacks) {
				iPlayerCallback.onTrackUpdate(mCurrentTrack , mCurrentIndex);
			}
		}
	}

	@Override
	public void onBufferingStart() {
		LogUtils.d(TAG,"onBufferingStart ...");
	}

	@Override
	public void onBufferingStop() {
		LogUtils.d(TAG,"onBufferingStop ...");
	}

	@Override
	public void onBufferProgress(int progress) {
		LogUtils.d(TAG,"缓冲进度 -- >" + progress);
	}

	@Override
	public void onPlayProgress(int currPos, int duration) {
		//单位是毫秒
		for (IPlayerCallback iPlayerCallback : mIPlayerCallbacks) {
			iPlayerCallback.onProgramsChange(currPos,duration);
		}
		LogUtils.d(TAG,"当前播放进度 -- >" + currPos + "总时长 -- > " + duration);
	}

	@Override
	public boolean onError(XmPlayerException e) {
		LogUtils.d(TAG,"onError --> " + e);
		return false;
	}
	//==========================播放器相关的数据接口 end=============================

}
