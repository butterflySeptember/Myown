package com.example.new_fmredioplayer.fragments;

import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.new_fmredioplayer.PlayerActivity;
import com.example.new_fmredioplayer.R;
import com.example.new_fmredioplayer.adapters.DetailListAdpater;
import com.example.new_fmredioplayer.base.BaseApplication;
import com.example.new_fmredioplayer.base.BaseFragment;
import com.example.new_fmredioplayer.interfaces.IHistoryCallback;
import com.example.new_fmredioplayer.presenters.HistoryPresenter;
import com.example.new_fmredioplayer.presenters.PlayPresenter;
import com.example.new_fmredioplayer.views.ConfirmCheckBoxDialog;
import com.example.new_fmredioplayer.views.UILoader;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

public class HistoryFragment extends BaseFragment implements IHistoryCallback, DetailListAdpater.ItemClickListener, DetailListAdpater.ItemLongClickListener, ConfirmCheckBoxDialog.onDialogActionClickListener {

	private UILoader mUiLoader;
	private DetailListAdpater mDetailListAdpater;
	private HistoryPresenter mHistoryPresenter;
	private Track mCurrentHistory = null;

	public View onSubViewLoaded(final LayoutInflater layoutInflater, ViewGroup container){
		FrameLayout rootView = (FrameLayout) layoutInflater.inflate(R.layout.fragment_history, container, false);
		if (mUiLoader == null) {
			mUiLoader = new UILoader(BaseApplication.getAppContext()) {
				@Override
				protected View getSuccessView(ViewGroup container) {
					return createSuccessView(container);
				}

				@Override
				protected View getEmptyView() {
					View emptyView = layoutInflater.from(getContext()).inflate(R.layout.fragment_empty_view, this, false);
					TextView emptyTipsTv = emptyView.findViewById(R.id.empty_tips_tv);
					emptyTipsTv.setText("没有历史内容");
					return emptyView;
				}
			};
		}else {
			if (mUiLoader.getParent() instanceof ViewGroup) {
				((ViewGroup) mUiLoader.getParent()).removeView(mUiLoader);
			}
		}
		mUiLoader.updateStatus(UILoader.UIStatus.LOADING);
		//HistoryPresenter
		mHistoryPresenter = HistoryPresenter.getHistoryPresenter();
		mHistoryPresenter.registerViewCallback(this);
		mHistoryPresenter.listHistories();
		rootView.addView(mUiLoader);
		return rootView;
	}

	private View createSuccessView(ViewGroup container) {
		View successView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_history, container, false);
		TwinklingRefreshLayout refreshLayout = successView.findViewById(R.id.over_scroll_view);
		refreshLayout.setEnableOverScroll(false);
		refreshLayout.setEnableLoadmore(false);
		refreshLayout.setEnableRefresh(false);
		//RecyclerView
		RecyclerView historyList = successView.findViewById(R.id.history_list);
		historyList.setLayoutManager(new LinearLayoutManager(container.getContext()));
		//设置item的间距
		historyList.addItemDecoration(new RecyclerView.ItemDecoration() {
			@Override
			public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
				//使用工具类 dp和px相互转换
				outRect.left = UIUtil.dip2px(view.getContext(),5);
				outRect.top = UIUtil.dip2px(view.getContext(),5);
				outRect.bottom = UIUtil.dip2px(view.getContext(),5);
			}
		});
		//设置适配器
		mDetailListAdpater = new DetailListAdpater();
		mDetailListAdpater.setItemClickListener(this);
		mDetailListAdpater.setItemLongClickListener(this);
		historyList.setAdapter(mDetailListAdpater);
		return successView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (mHistoryPresenter != null) {
			mHistoryPresenter.unRegisterViewCallback(this);
		}
	}

	@Override
	public void onHistoriesLoaded(List<Track> tracks) {
		if (tracks == null && tracks.size() == 0) {
			mUiLoader.updateStatus(UILoader.UIStatus.EMPTY);
		}else {
			//更新数据
			if (mDetailListAdpater != null) {
				mDetailListAdpater.setData(tracks);
			}
			mUiLoader.updateStatus(UILoader.UIStatus.SUCCESS);
		}
	}

	@Override
	public void onItemClick(List<Track> detailData, int position) {
		//设置播放器的数据
		PlayPresenter playPresenter = PlayPresenter.getPlayPresenter();
		playPresenter.setPlayList(detailData,position);
		//跳转到播放器界面
		Intent intent = new Intent(getActivity(), PlayerActivity.class);
		startActivity(intent);
	}

	@Override
	public void onItemLongClickListener(Track track) {
		this.mCurrentHistory = track;
		//长按删除历史
		ConfirmCheckBoxDialog dialog = new ConfirmCheckBoxDialog(getActivity());
		//设置弹出框点击事件
		dialog.setOnDialogActionClickListener(this);
		dialog.show();
	}

	@Override
	public void onCancelSubClick() {
		//取消
	}

	@Override
	public void onConfirmClick(boolean checked) {
		//确定
		if (checked) {
			//删除全部历史
			if (mHistoryPresenter != null) {
				mHistoryPresenter.cleanHistories();
			}
		}else {
			//删除选中该专辑
			if (mHistoryPresenter != null && mCurrentHistory != null) {
				mHistoryPresenter.delHistory(mCurrentHistory);
			}
		}
	}


}
