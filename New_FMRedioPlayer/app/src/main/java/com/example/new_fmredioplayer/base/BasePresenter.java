package com.example.new_fmredioplayer.base;

import com.example.new_fmredioplayer.interfaces.IAlbumDetailViewCallBack;
import com.example.new_fmredioplayer.interfaces.ISearchCallback;

public interface BasePresenter<T> {

	/**
	 * 接口注册
	 * @param iCallback
	 */
	void registerViewCallback(T iCallback);

	/**
	 *取消接口注册
	 * @param iCallback
	 */
	void unRegisterViewCallback(T iCallback);
}

