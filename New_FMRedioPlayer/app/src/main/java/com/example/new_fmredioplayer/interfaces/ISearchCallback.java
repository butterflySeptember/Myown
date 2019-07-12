package com.example.new_fmredioplayer.interfaces;

import com.example.new_fmredioplayer.base.BasePresenter;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.word.HotWord;
import com.ximalaya.ting.android.opensdk.model.word.QueryResult;

import java.util.List;

public interface ISearchCallback extends BasePresenter {

	/**
	 * 搜索结果的回调方法
	 *
	 * @param result
	 */
	void onSearchResultLoad(List<Album> result);

	/**
	 * 获取推荐热词的回调结果
	 *
	 * @param hotWordList
	 */
	void onHotWordLoad(List<HotWord> hotWordList);

	/**
	 * 加载更多的返回结果
	 *
	 * @param result 结果
	 * @param isOkay true加载更多成功，false表示没有更多
	 */
	void onLoadMoreResult (List<Album> result ,boolean isOkay);

	/**
	 * 获取热词列表
	 * @param keywordList
	 */
	void onRecommendLoaded(List<QueryResult> keywordList);

	/**
	 * 错误通知
	 * @param errorCode
	 * @param errorMsg
	 */
	void onError(int errorCode,String errorMsg);
}
