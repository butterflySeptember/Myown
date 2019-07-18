package com.example.new_fmredioplayer.presenters;

import android.support.annotation.Nullable;

import com.example.new_fmredioplayer.data.XimalayaFMApi;
import com.example.new_fmredioplayer.interfaces.ISearchCallback;
import com.example.new_fmredioplayer.interfaces.ISearchPresent;
import com.example.new_fmredioplayer.utils.Constants;
import com.example.new_fmredioplayer.utils.LogUtils;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.SearchAlbumList;
import com.ximalaya.ting.android.opensdk.model.word.HotWord;
import com.ximalaya.ting.android.opensdk.model.word.HotWordList;
import com.ximalaya.ting.android.opensdk.model.word.QueryResult;
import com.ximalaya.ting.android.opensdk.model.word.SuggestWords;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;

public class SearchPresenter implements ISearchPresent {

	private static final String TAG = "SearchPresenter";
	private static final int DEFAULT_PAGE = 1;
	private int mCurrentPage = DEFAULT_PAGE;
	//当前的搜索关键字
	private String mCurrentKeyword = null;
	private XimalayaFMApi mXimalayaFMApi;
	private List<Album> searchResult = new ArrayList<>();

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
		mCurrentPage = DEFAULT_PAGE;
		searchResult.clear();
		//用于网络状态不佳时点击重新搜索
		this.mCurrentKeyword = keyword ;
		search(keyword);
	}

	private void search(String keyword) {
		mXimalayaFMApi.searchByKeyword(keyword, mCurrentPage, new IDataCallBack<SearchAlbumList>() {
			@Override
			public void onSuccess(@Nullable SearchAlbumList searchAlbumList) {
				List<Album> albums = searchAlbumList.getAlbums();
				searchResult.addAll(albums);
				if (albums != null) {
					LogUtils.d(TAG,"album size :" + albums.size());
					if (mIsLoaderMore) {
						for (ISearchCallback callback : mCallbacks) {
							//处理搜索结果为0的情况
							if (albums.size()  == 0) {
								callback.onLoadMoreResult(searchResult,false);
							}else {
								callback.onLoadMoreResult(searchResult,true);
							}
						}
						mIsLoaderMore = false;
					}else {
						for (ISearchCallback callback : mCallbacks) {
							callback.onSearchResultLoad(searchResult);
						}
					}
				}else {
					LogUtils.d(TAG,"album is null ....");
				}
			}

			@Override
			public void onError(int errorCode, String errorMsg) {
				LogUtils.d(TAG,"search errorCode -- >" + errorCode);
				LogUtils.d(TAG,"search errorMsg -- > " + errorMsg);
				for (ISearchCallback callback : mCallbacks) {
					if (mIsLoaderMore) {
						mIsLoaderMore = false;
						mCurrentPage -- ;
						callback.onLoadMoreResult(searchResult,false);
					}else{
						callback.onError(errorCode,errorMsg);
					}
				}
			}
		});
	}

	@Override
	public void reSearch() {
		//重新搜索
		search(mCurrentKeyword);
	}

	private boolean mIsLoaderMore = false;

	@Override
	public void loaderMore() {
		//首先判断是否要加载更多
		if (searchResult.size() < Constants.COUNT_DEFAULT) {
			for (ISearchCallback callback : mCallbacks) {
				callback.onLoadMoreResult(searchResult,false);
			}
		}else {
			mIsLoaderMore = true;
			mCurrentPage ++;
			search(mCurrentKeyword);
		}
	}

	@Override
	public void getHotWord() {
		mXimalayaFMApi.getHotWord(new IDataCallBack<HotWordList>() {
			@Override
			public void onSuccess(HotWordList hotWordList) {
				if (hotWordList != null) {
					List<HotWord> hotWord = hotWordList.getHotWordList();
					LogUtils.d(TAG,"hotWord size -- >" + hotWord.size());
					for (ISearchCallback callback : mCallbacks) {
						callback.onHotWordLoad(hotWord);
					}
				}
			}

			@Override
			public void onError(int errorCode, String errorMsg) {
				LogUtils.d(TAG,"getHotWord errorCode -- > " + errorCode);
				LogUtils.d(TAG,"getHotWord errorMsg -- > " +errorMsg);
//				for (ISearchCallback callback : mCallbacks) {
//					callback.onError(errorCode,errorMsg);
//				}
			}
		});
	}

	@Override
	public void getRecommendWord(String keyword) {
		mXimalayaFMApi.getSuggestWord(keyword, new IDataCallBack<SuggestWords>() {
			@Override
			public void onSuccess(@Nullable SuggestWords suggestWords) {
				if (suggestWords != null) {
					List<QueryResult> keyWordList = suggestWords.getKeyWordList();
					LogUtils.d(TAG,"suggestWords keyWord size -- >" + keyWordList.size());
					for (ISearchCallback callback : mCallbacks) {
						callback.onRecommendLoaded(keyWordList);
					}
				}
			}

			@Override
			public void onError(int errorCode, String errorMsg) {
				LogUtils.d(TAG,"getRecommendWord errorCode -- > " + errorCode);
				LogUtils.d(TAG,"getRecommendWord errorMsg -- > " +errorMsg);
			}
		});
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
