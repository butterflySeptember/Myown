package com.example.new_fmredioplayer.interfaces;

import com.example.new_fmredioplayer.base.BasePresenter;

public interface IAlbumDetialPresenter extends BasePresenter<IAlbumDetailViewCallBack> {

	/**
	 *下拉刷新内容
	 */
	void  pull2RefreshMore();

	/**
	 * 下拉加载更多
	 */
	void loadMore();

	/**
	 *获取专辑详情
	 */
	void getAlbumDetial(int albumId,int page);

}
