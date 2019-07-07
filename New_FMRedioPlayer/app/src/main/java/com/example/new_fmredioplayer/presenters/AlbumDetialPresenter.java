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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.callback.Callback;

public class AlbumDetialPresenter implements IAlbumDetialPresenter {

	private Album sTargetAlbum = null;

	private final static String TAG = "AlbumDetialPresenter";

	private List<IAlbumDetailViewCallBack> mCallBacks = null;
	private List<Track> mTracks = new ArrayList<>();

	private Album mTargetAlbum;
	//当前的专辑ID
	private int mCurrentAlbumId = 1;
	//当前页码
	private int mCurrentPageIndex = 0;

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
		//加载更多内容
		mCurrentPageIndex ++;
		//结果加载在内容后方
		doLoaded(true);
	}

	/**
	 * 加载页面内容
	 * @param isLoaderMore
	 */
	private void doLoaded(final boolean isLoaderMore){
		Map<String,String> map = new HashMap<>();
		map.put(DTransferConstants.ALBUM_ID,mCurrentAlbumId + "");
		map.put(DTransferConstants.SORT,"asc");
		map.put(DTransferConstants.PAGE,mCurrentPageIndex + "");
		map.put(DTransferConstants.PAGE_SIZE, Constants.COUNT_DEFAULT +"");
		CommonRequest.getTracks(map, new IDataCallBack<TrackList>() {
			@Override
			public void onSuccess(@Nullable TrackList trackList) {
				//是否在主线程，判断是否可以更新UI
				LogUtils.d(TAG,"current Thread -- > " + Thread.currentThread().getName());
				if (trackList != null) {
					List<Track> tracks = trackList.getTracks();
					LogUtils.d(TAG,"tracks -->" + tracks);
					if (isLoaderMore) {
						//上拉加载，结果添加在后面
						mTracks.addAll(mTracks.size() - 1,tracks);
						int size = mTracks.size();
						handlerLoaderMoreResult(size);
					}else {
						//下拉加载，结果在头部更新
						mTracks.addAll(0 , tracks);
					}
					handlerAlbumDetailResult(mTracks);
				}
			}

			@Override
			public void onError(int errorCode, String errorMsg) {
				if (isLoaderMore) {
					mCurrentPageIndex -- ;
				}
				LogUtils.d(TAG,"errorCode -->" + errorCode);
				LogUtils.d(TAG,"errorMsg -->" + errorMsg);
				handlerError(errorCode,errorMsg);
			}
		});
	}

	/**
	 * 处理加载更多的结果
	 * @param size
	 */
	private void handlerLoaderMoreResult(int size) {
		for (IAlbumDetailViewCallBack callBack : mCallBacks) {
			callBack.onLoaderMoreFinished(size);
		}
	}

	@Override
	public void getAlbumDetial(int albumId, int page) {
		mTracks.clear();
		this.mCurrentAlbumId = albumId;
		this.mCurrentPageIndex = page;
		doLoaded(false);
	}

	/**
	 * 如果发生错误，就通知ui
	 * @param errorCode
	 * @param errorMsg
	 */
	private void handlerError(int errorCode, String errorMsg) {
		for (IAlbumDetailViewCallBack callBack : mCallBacks) {
			callBack.onNetworkError(errorCode,errorMsg);
		}
	}

	private void handlerAlbumDetailResult(List<Track> tracks) {
		for (IAlbumDetailViewCallBack mCallBack : mCallBacks) {
			mCallBack.onDetailListLoaded(tracks);
		}
	}

	public void registerViewCallback(IAlbumDetailViewCallBack detailViewCallBack) {
		if (mCallBacks.contains(detailViewCallBack)) {
			mCallBacks.add(detailViewCallBack);
			if (sTargetAlbum != null) {
				detailViewCallBack.onAlbunLoaded(sTargetAlbum);
			}
		}
	}

//	public void unRegisterViewCallback(IAlbumDetailViewCallBack detailViewCallBack) {
//		mCallBacks.remove(detailViewCallBack);
//	}
//
//	public void setTargetAlbum(Album targetAlbum){
//
//		this.sTargetAlbum = targetAlbum;
//	}

	@Override
	public void registerViewCallback(Object o) {

	}

	@Override
	public void unRegisterViewCallback(Object o) {

	}

	public void setTargetAlbum(Album targetAlbum) {
		mTargetAlbum = targetAlbum;
	}

	public Album getTargetAlbum() {
		return mTargetAlbum;
	}
}
