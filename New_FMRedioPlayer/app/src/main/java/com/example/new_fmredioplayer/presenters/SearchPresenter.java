package com.example.new_fmredioplayer.presenters;

import android.support.annotation.Nullable;

import com.example.new_fmredioplayer.api.XimalayaFMApi;
import com.example.new_fmredioplayer.interfaces.ISearchCallback;
import com.example.new_fmredioplayer.interfaces.ISearchPresent;
import com.example.new_fmredioplayer.utils.LogUtils;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.SearchAlbumList;

import java.util.ArrayList;
import java.util.List;

public class SearchPresenter implements ISearchPresent {

	private static final String TAG = "SearchPresenter";
	private static final int DEFAULT_PAGE = 1;
	private int mCurrentPage = DEFAULT_PAGE;
	//当前的搜索关键字
	private String mCurrentKeyword = null;
	private XimalayaFMApi mXimalayaFMApi;

	private SearchPresenter(){
		mXimalayaFMApi = XimalayaFMApi.getXimalayaFMApi();
	}
	private static SearchPresenter sSearchPresenter = null;
	public static SearchPresenter getSearchPresenter(){
		if (sSearchPresenter == null) {
			synchronized (SearchPresenter.class){
				if (sSearchPresenter == null) {
					sSearchPresenter = new SearchPresenter();
				}
			}
		}
		return sSearchPresenter;
	}

	List<ISearchCallback> mCallbacks = new ArrayList<>();

	@Override
	public void doSearch(String keyword) {
		//用于网络状态不佳时点击重新搜索
		this.mCurrentKeyword = keyword ;
		mXimalayaFMApi.searchByKeyword(keyword, mCurrentPage, new IDataCallBack<SearchAlbumList>() {
			@Override
			public void onSuccess(@Nullable SearchAlbumList searchAlbumList) {
				List<Album> albums = searchAlbumList.getAlbums();
				if (albums != null) {
					LogUtils.d(TAG,"album size :" + albums.size());
				}else {
					LogUtils.d(TAG,"album is null ....");
				}
			}

			@Override
			public void onError(int errorCode, String errorMsg) {
				LogUtils.d(TAG,"errorCode -- >" + errorCode);
				LogUtils.d(TAG,"errorMsg -- > " + errorMsg);
			}
		});
	}

	@Override
	public void reSearch() {

	}

	@Override
	public void loaderMore() {

	}

	@Override
	public void getHotWord() {

	}

	@Override
	public void getRecommendMore(String keyword) {

	}

	@Override
	public void registerViewCallback(ISearchCallback iSearchCallback) {
		if ( ! mCallbacks.contains(iSearchCallback)) {
			mCallbacks.add(iSearchCallback);
		}
	}

	@Override
	public void unRegisterViewCallback(ISearchCallback iSearchCallback) {
		mCallbacks.remove(iSearchCallback);
	}
}
