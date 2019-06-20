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
import com.example.new_fmredioplayer.interfaces.IRecommendViewCallback;
import com.example.new_fmredioplayer.presenters.RecommendPresenter;
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

public class RecommendFragment extends BaseFragment implements IRecommendViewCallback {

	private final static String TAG = "RecommendFragment";
	private View mRootView;
	private RecyclerView mRecommendBy;
	private RecommendListAdapter mRecommendListAdapter;
	private RecommendPresenter mRecommendPresenter;

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

		//获取到逻辑上的对象
		mRecommendPresenter = RecommendPresenter.getInstance();
		//首先设置接口注册通知
		mRecommendPresenter.registerViewCallback(this);
		//获取推荐列表
		mRecommendPresenter.getRecommendList();

		//返回View
		return mRootView;
	}





	@Override
	public void onRecommendListloaded(List<Album> result) {
		//获取到推荐内容，方法被调用（成功）
		//获取到数据，更新UI
		mRecommendListAdapter.setData(result);
	}

	@Override
	public void onLoaderMore(List<Album> result) {

	}

	@Override
	public void onRefreshMore(List<Album> result) {

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		//取消接口注册，避免内存泄露
		if (mRecommendPresenter != null) {
			mRecommendPresenter.unregisterViewCallback(this);
		}
	}
}
