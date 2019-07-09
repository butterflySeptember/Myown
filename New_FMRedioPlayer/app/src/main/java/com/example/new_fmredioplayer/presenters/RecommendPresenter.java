package com.example.new_fmredioplayer.presenters;

import android.support.annotation.Nullable;

import com.example.new_fmredioplayer.api.XimalayaFMApi;
import com.example.new_fmredioplayer.fragments.RecommendFragment;
import com.example.new_fmredioplayer.interfaces.IRecommendPresenter;
import com.example.new_fmredioplayer.interfaces.IRecommendViewCallback;
import com.example.new_fmredioplayer.utils.Constants;
import com.example.new_fmredioplayer.utils.LogUtils;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.callback.Callback;

public class RecommendPresenter implements IRecommendPresenter {

	private final static String TAG = "RecommendPresenter";

	private  List<IRecommendViewCallback> mCallBack = new ArrayList<>();

	private static RecommendPresenter sInstance = null;
	private List<Album> mCurrentRecommend = null;

	/**
	 *获取当前推荐专辑
	 *使用之前判空
	 * @return 推荐专辑
	 */
	public List<Album> getCurrentRecommend(){
		return mCurrentRecommend;
	}

	/**
	 * 获取单例对象
	 *
	 * @return
	 */
	public static RecommendPresenter getInstance(){
		if (sInstance == null) {
			synchronized (RecommendPresenter.class){
				if (sInstance == null) {
					sInstance = new RecommendPresenter();
				}
			}
		}
		return sInstance;
	}

	@Override
	public void getRecommendList() {
		//获取推荐内容
		//封装参数
		//显示加载界面
		updateLoading();
		XimalayaFMApi ximalayaFMApi = XimalayaFMApi.getXimalayaFMApi();

		ximalayaFMApi.getRecommendList(new IDataCallBack<GussLikeAlbumList>() {
			@Override
			public void onSuccess(@Nullable GussLikeAlbumList gussLikeAlbumList) {
				LogUtils.d(TAG,"thread name -->" + Thread.currentThread().getName());
				//数据获取成功
				if (gussLikeAlbumList != null) {
					List<Album> albumList = gussLikeAlbumList.getAlbumList();
					if (albumList != null) {
						LogUtils.d(TAG,"size -->" + albumList.size());
					}
					//更新UI
					//upRecommendUI(albumList);
					handlerRecommendResult(albumList);
				}
	}

			@Override
			public void onError(int i, String s) {
				//数据获取出错
				LogUtils.d(TAG," error -->" + i);
				LogUtils.d(TAG," errorMsg -->" + s);
				handlerError();
			}
		});
	}

	private void handlerError(){
		if (mCallBack != null) {
			for (IRecommendViewCallback callback : mCallBack) {
				callback.onNetworkError();
			}
		}
	}

	private void handlerRecommendResult(List<Album> albumList) {
		//通知ui更新
		if (albumList != null) {
			//数据为空
			if (albumList.size()==0) {
				for (IRecommendViewCallback callback : mCallBack) {
					callback.onEmpty();
				}
			}
		}else {
		//通知UI更新
			for (IRecommendViewCallback callback : mCallBack) {
				callback.onRecommendListloaded(albumList);
			}
			this.mCurrentRecommend = albumList;
		}
	}

	private  void updateLoading(){
		for (IRecommendViewCallback callback : mCallBack) {
			callback.onLoading();
		}
	}
	@Override
	public void pull2RefreshMore() {

	}

	@Override
	public void loadMore() {

	}

//	public void registerViewCallback(IRecommendViewCallback callback) {
//		if (mCallBack.contains(callback)) {
//			mCallBack.add(callback);
//		}
//	}
//
//	public void unregisterViewCallback(IRecommendViewCallback callback) {
//		if (mCallBack.contains(callback)) {
//			mCallBack.remove(mCallBack);
//		}
//	}


	public void registerViewCallback(Object o) {

	}

	@Override
	public void unRegisterViewCallback(Object o) {

	}

	public void unregisterViewCallback(RecommendFragment recommendFragment) {
		if (mCallBack.contains(recommendFragment)) {
			mCallBack.add(recommendFragment);
		}
	}
}
