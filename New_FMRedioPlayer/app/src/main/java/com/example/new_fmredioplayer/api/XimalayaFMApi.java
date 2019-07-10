package com.example.new_fmredioplayer.api;

import com.example.new_fmredioplayer.utils.Constants;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;
import com.ximalaya.ting.android.opensdk.model.album.SearchAlbumList;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

import java.util.HashMap;
import java.util.Map;

public class XimalayaFMApi {

	private XimalayaFMApi(){

	}

	public static XimalayaFMApi sXimalayaFMApi;

	public static XimalayaFMApi getXimalayaFMApi(){
		if (sXimalayaFMApi == null) {
			synchronized (XimalayaFMApi.class){
				if (sXimalayaFMApi == null) {
					sXimalayaFMApi = new XimalayaFMApi();
				}
			}
		}
		return sXimalayaFMApi;
	}
	/**
	 * 获取推荐内容
	 *
	 * @param callBack 请求结果的接口
	 */
	public void getRecommendList(IDataCallBack<GussLikeAlbumList> callBack){
		Map<String, String> map = new HashMap<String, String>();
		//返回参数条数
		map.put(DTransferConstants.LIKE_COUNT, Constants.RECOMMAND_COUNT+"");
		CommonRequest.getGuessLikeAlbum(map,callBack);
	}

	/**
	 * 根据专辑id获取专辑详情列表
	 *
	 * @param callBack 专辑详情列表
	 * @param mCurrentAlbumId 专辑id
	 * @param mCurrentPageIndex 相关页码
	 */
	public void getAlbumDetail(IDataCallBack<TrackList> callBack,int mCurrentAlbumId,int mCurrentPageIndex){
		Map<String,String> map = new HashMap<String, String>();
		//表示专辑的相关信息
		map.put(DTransferConstants.ALBUM_ID,mCurrentAlbumId + "");
		map.put(DTransferConstants.SORT,"asc");
		map.put(DTransferConstants.PAGE,mCurrentPageIndex + "");
		map.put(DTransferConstants.PAGE_SIZE, Constants.COUNT_DEFAULT +"");
		CommonRequest.getTracks(map,callBack);
	}

	/**
	 * 根据关键词进行搜索
	 *
	 * @param keyword
	 */
	public void searchByKeyword(String keyword, int page, IDataCallBack<SearchAlbumList> callBack) {
		Map<String,String> map =new HashMap<String, String>();
		map.put(DTransferConstants.SEARCH_KEY, keyword);
		map.put(DTransferConstants.PAGE , page + "");
		map.put(DTransferConstants.PAGE_SIZE,Constants.COUNT_DEFAULT + "");
		CommonRequest.getSearchedAlbums(map,callBack);
	}
}
