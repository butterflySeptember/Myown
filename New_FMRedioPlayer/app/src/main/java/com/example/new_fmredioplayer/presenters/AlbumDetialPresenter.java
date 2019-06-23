package com.example.new_fmredioplayer.presenters;

import com.example.new_fmredioplayer.interfaces.IAlbumDetailViewCallBack;
import com.example.new_fmredioplayer.interfaces.IAlbumDetialPresenter;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.List;

public class AlbumDetialPresenter implements IAlbumDetialPresenter {

	private Album sTargetAlbum = null;

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
