package com.example.new_fmredioplayer.interfaces;

import com.example.new_fmredioplayer.base.BasePresenter;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;

public interface IAlbumDetailViewCallBack extends BasePresenter {

	/**
	 * 专辑内容的加载
	 * @param tracks
	 */
	void onDetailListLoaded(List<Track> tracks);

	/**
	 * 把album传给ui
	 * @param album
	 */
	void onAlbunLoaded(Album album);

	/**
	 * 网络错误
	 * @param errorCode
	 * @param errorMsg
	 */
	void onNetworkError(int errorCode, String errorMsg);

	/**
	 * 加载更多的结果
	 * true表示加载成功
	 * false表示无更多内容
	 * @param size
	 */
	void onLoaderMoreFinished(int size);

	/**
	 * 下拉加载更多的结果
	 * @param size
	 */
	void onRefreshFinished(int size);
}
