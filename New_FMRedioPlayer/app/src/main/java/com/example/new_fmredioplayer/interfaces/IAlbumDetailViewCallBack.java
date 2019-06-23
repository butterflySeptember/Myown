package com.example.new_fmredioplayer.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;

public interface IAlbumDetailViewCallBack {

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

}
