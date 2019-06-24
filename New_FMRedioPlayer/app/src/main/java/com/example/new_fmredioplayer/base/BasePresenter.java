package com.example.new_fmredioplayer.base;

import com.example.new_fmredioplayer.interfaces.IAlbumDetailViewCallBack;

public interface BasePresenter<T> {

	/**
	 * 接口注册
	 * @param t
	 */
	void registerViewCallback(T t);

	/**
	 *取消接口注册
	 * @param m
	 */
	void unRegisterViewCallback(T t);
}

