package com.example.new_fmredioplayer.fragments;

import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.new_fmredioplayer.DetailActivity;
import com.example.new_fmredioplayer.R;
import com.example.new_fmredioplayer.adapters.AlbumListAdapter;
import com.example.new_fmredioplayer.base.BaseFragment;
import com.example.new_fmredioplayer.interfaces.ISubscriptionCallback;
import com.example.new_fmredioplayer.presenters.AlbumDetialPresenter;
import com.example.new_fmredioplayer.presenters.SubscriptionPresenter;
import com.example.new_fmredioplayer.utils.Constants;
import com.example.new_fmredioplayer.views.ConfirmDialog;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.Collections;
import java.util.List;

public class SubscriptionFragment extends BaseFragment implements ISubscriptionCallback, AlbumListAdapter.onRecommendItemClickListener, AlbumListAdapter.onAlbumItemLongClickListener, ConfirmDialog.onDialogActionClickListener {

	private SubscriptionPresenter mSubscriptionPresenter;
	private RecyclerView mSubListView;
	private AlbumListAdapter mAlbumListAdapter;
	private TwinklingRefreshLayout mRefreshLayout;
	private View mRootView;

	public View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container){
		mRootView = layoutInflater.inflate(R.layout.fragment_subscription,container,false);
		//初始化控件
		mRefreshLayout = mRootView.findViewById(R.id.over_scroll_view);
		mSubListView = mRootView.findViewById(R.id.sub_list);
		mSubListView.setLayoutManager(new LinearLayoutManager(container.getContext()));

		initEvent();
		//设置适配器
		mAlbumListAdapter = new AlbumListAdapter();
		mAlbumListAdapter.setAlbumItemClickListener(this);
		mAlbumListAdapter.setOnAlbumItemLongClick(this);
		mSubListView.setAdapter(mAlbumListAdapter);
		//创建Presenter
		mSubscriptionPresenter = SubscriptionPresenter.getInstance();
		mSubscriptionPresenter.registerViewCallback(this);
		mSubscriptionPresenter.getSubscriptionList();
		return mRootView;
	}

	private void initEvent() {
		mRefreshLayout.setEnableOverScroll(false);
		mRefreshLayout.setEnableRefresh(false);
		mSubListView.addItemDecoration(new RecyclerView.ItemDecoration() {
			@Override
			public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
				//使用工具类 dp和px相互转换
				outRect.left = UIUtil.dip2px(view.getContext(),5);
				outRect.top = UIUtil.dip2px(view.getContext(),5);
				outRect.bottom = UIUtil.dip2px(view.getContext(),5);
			}
		});
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		//取消接口注册
		if (mSubscriptionPresenter != null) {
			mSubscriptionPresenter.unRegisterViewCallback(this);
		}
		//取消注册
		mAlbumListAdapter.setAlbumItemClickListener(null);
	}

	@Override
	public void onAddResult(boolean isSuccess) {

	}

	@Override
	public void onDeleteResult(boolean isSuccess) {

	}

	@Override
	public void onSubscriptionLoaded(List<Album> albumList) {
		//更新UI
		if (mAlbumListAdapter != null) {
			//将列表反向
			Collections.reverse(albumList);
			mAlbumListAdapter.setData(albumList);
		}
	}

	@Override
	public void onSubFull() {
		Toast.makeText(getActivity(),"订阅数量不能超过" + Constants.MAX_SUB_COUNT,Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onItemClick(int position, Album album) {
		AlbumDetialPresenter.getInstance().setTargetAlbum(album);
		//跳转到详情界面
		Intent intent = new Intent(getContext(), DetailActivity.class);
		startActivity(intent);
	}

	@Override
	public void onAlbumItemLongClick(Album album) {
		//长按item，弹出提示框
		ConfirmDialog confirmDialog = new ConfirmDialog(getActivity());
		//设置弹出框的点击事件
		confirmDialog.setOnDialogActionClickListener(this);
		confirmDialog.show();
	}

	@Override
	public void onCancelSubClick() {
		//点击删除按钮，取消订阅
	}

	@Override
	public void onGiveUpClick() {
		//点击放弃按钮
	}
}
