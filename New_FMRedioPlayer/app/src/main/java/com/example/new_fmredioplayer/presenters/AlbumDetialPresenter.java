package com.example.new_fmredioplayer.presenters;

import android.support.annotation.Nullable;

import com.example.new_fmredioplayer.interfaces.IAlbumDetailViewCallBack;
import com.example.new_fmredioplayer.interfaces.IAlbumDetialPresenter;
import com.example.new_fmredioplayer.utils.Constants;
import com.example.new_fmredioplayer.utils.LogUtils;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlbumDetialPresenter implements IAlbumDetialPresenter {

	private Album sTargetAlbum = null;

	private final static String TAG = "AlbumDetialPresenter";

	private List<IAlbumDetailViewCallBack> mCallBacks = null;

	private AlbumDetialPresenter(){
	}

	private static AlbumDetialPresenter sInstance = null;

	public static AlbumDetialPresenter getInstance(){
		if (sInstance == null) {
			synchronized (AlbumDetialPresenter.class){
				if (sInstance == null) {
					sInstance = new AlbumDetialPresenter();
				}
			}
		}
		return sInstance;
	}

	@Override
	public void pull2RefreshMore() {

	}

	@Override
	public void loadMore() {

	}

	@Override
	public void getAlbumDetial(int albumId, int page) {
		//根据页码和id来实现内容
		Map<String,String> map = new HashMap<String, String>();
		map.put(DTransferConstants.ALBUM_ID,albumId + "");
		map.put(DTransferConstants.SORT,"asc");
		map.put(DTransferConstants.PAGE,page + "");
		map.put(DTransferConstants.PAGE_SIZE, Constants.COUNT_DEFAULT +"");
		CommonRequest.getTracks(map, new IDataCallBack<TrackList>() {
			@Override
			public void onSuccess(@Nullable TrackList trackList) {
				//是否在主线程，判断是否可以更新UI
				LogUtils.d(TAG,"current Thread -- > " + Thread.currentThread().getName());
				if (trackList != null) {
					List<Track> tracks = trackList.getTracks();
					LogUtils.d(TAG,"tracks -->" + tracks);
					handlerAlbumDetailResult(tracks);
				}
			}

			@Override
			public void onError(int errorCode, String errorMsg) {
				LogUtils.d(TAG,"errorCode -->" + errorCode);
				LogUtils.d(TAG,"errorMsg -->" + errorMsg);
			}
		});

	}

	private void handlerAlbumDetailResult(List<Track> tracks) {
		for (IAlbumDetailViewCallBack mCallBack : mCallBacks) {
			mCallBack.onDetailListLoaded(tracks);
		}
	}

	@Override
	public void registerViewCallback(IAlbumDetailViewCallBack detailViewCallBack) {
		if (mCallBacks.contains(detailViewCallBack)) {
			mCallBacks.add(detailViewCallBack);
			if (sTargetAlbum != null) {
				detailViewCallBack.onAlbunLoaded(sTargetAlbum);
			}
		}
	}

	@Override
	public void unRegisterViewCallback(IAlbumDetailViewCallBack detailViewCallBack) {
		mCallBacks.remove(detailViewCallBack);
	}

	public void setTargetAlbum(Album targetAlbum){

		this.sTargetAlbum = targetAlbum;
	}
}
