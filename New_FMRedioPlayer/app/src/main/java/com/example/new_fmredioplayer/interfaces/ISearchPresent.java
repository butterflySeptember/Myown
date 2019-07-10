package com.example.new_fmredioplayer.interfaces;

import com.example.new_fmredioplayer.base.BasePresenter;

public interface ISearchPresent extends BasePresenter<ISearchCallback>{

	/**
	 * 进行搜索
	 * @param keyword 关键字
	 */
	void doSearch(String keyword);

	/**
	 * 重新搜索
	 */
	void reSearch();

	/**
	 * 加载更多
	 */
	void loaderMore();

	/**
	 * 获取热词
	 */
	void getHotWord();

	/**
	 * 获取推荐的内容（相似关键字）
	 * @param keyword 关键字
	 */
	void getRecommendMore(String keyword);
}
