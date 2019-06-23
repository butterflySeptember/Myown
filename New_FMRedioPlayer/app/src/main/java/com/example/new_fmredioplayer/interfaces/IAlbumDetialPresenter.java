package com.example.new_fmredioplayer.interfaces;

public interface IAlbumDetialPresenter {

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

	/**
	 *注册UI通知的接口
	 * @param detialPresenter
	 */
	void registerViewCallback(IAlbumDetailViewCallBack detailViewCallBack);

	/**
	 * 取消注册UI通知的接口
	 * @param detialPresenter
	 */
	void unRegisterViewCallback(IAlbumDetailViewCallBack detailViewCallBack);
}
