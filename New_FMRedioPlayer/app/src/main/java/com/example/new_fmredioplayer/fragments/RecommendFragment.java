package com.example.new_fmredioplayer.fragments;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.new_fmredioplayer.R;
import com.example.new_fmredioplayer.adapters.RecommendListAdapter;
import com.example.new_fmredioplayer.base.BaseFragment;
import com.example.new_fmredioplayer.utils.Constants;
import com.example.new_fmredioplayer.utils.LogUtils;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendFragment extends BaseFragment {

	private final static String TAG = "RecommendFragment";
	private View mRootView;
	private RecyclerView mRecommendBy;
	private RecommendListAdapter mRecommendListAdapter;

	public View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container){
		mRootView = layoutInflater.inflate(R.layout.fragment_recommend,container,false);

		//初始化
		//1，找到对应的控件
		mRecommendBy = (RecyclerView) mRootView.findViewById(R.id.recommend_list);
		//2，设置布局管理器
		LinearLayoutManager linearLayoutManager =	new LinearLayoutManager(getContext());
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecommendBy.setLayoutManager(linearLayoutManager);
		//3，设置适配器
		mRecommendListAdapter = new RecommendListAdapter();
		mRecommendBy.setAdapter(mRecommendListAdapter);
		mRecommendBy.addItemDecoration(new RecyclerView.ItemDecoration() {
			@Override
			public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
				//使用工具类 dp和px相互转换
				outRect.left = UIUtil.dip2px(view.getContext(),5);
				outRect.top = UIUtil.dip2px(view.getContext(),5);
				outRect.bottom = UIUtil.dip2px(view.getContext(),5);
			}
		});

		//数据获取
		getRecommendData();

		//返回View
		return mRootView;
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
				LogUtils.d(TAG,"thread name -->" + Thread.currentThread().getName());
				//数据获取成功
				if (gussLikeAlbumList != null) {
					List<Album> albumList = gussLikeAlbumList.getAlbumList();
					if (albumList != null) {
						LogUtils.d(TAG,"size -->" + albumList.size());
					}
					//更新UI
					upRecommendUI(albumList);

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

	private void upRecommendUI(List<Album> albumList){
		//把数据设置给adapter
		mRecommendListAdapter.setData(albumList);
	}
}
