package com.example.new_fmredioplayer.fragments;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.new_fmredioplayer.R;
import com.example.new_fmredioplayer.base.BaseFragment;
import com.example.new_fmredioplayer.utils.Constants;
import com.example.new_fmredioplayer.utils.LogUtils;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendFragment extends BaseFragment {

	private final static String TAG = "RecommendFragment";

	public View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container){
		View rootView = layoutInflater.inflate(R.layout.fragment_recommend,container,false);

		//数据获取
		getRecommendData();

		//返回View
		return rootView;
	}

	/**
	 * 获取推荐内容
	 */
	private void getRecommendData() {
		//封装参数
		Map<String, String> map = new HashMap<String, String>();
		//返回参数条数
		map.put(DTransferConstants.LIKE_COUNT, Constants.RECOMMAND_COUNT+"");
		CommonRequest.getGuessLikeAlbum(map, new IDataCallBack<GussLikeAlbumList>() {
			@Override
			public void onSuccess(@Nullable GussLikeAlbumList gussLikeAlbumList) {
				//数据获取成功
				if (gussLikeAlbumList != null) {
					List<Album> albumList = gussLikeAlbumList.getAlbumList();
					if (albumList != null) {
						LogUtils.d(TAG,"size -->" + albumList.size());
					}

				}
			}

			@Override
			public void onError(int i, String s) {
				//数据获取出错
				LogUtils.d(TAG," error -->" + i);
				LogUtils.d(TAG," errorMsg -->" + s);
			}
		});
	}
}
