package com.example.rcycleview.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.rcycleview.R;
import com.example.rcycleview.beans.ItemBean;

import java.util.List;


public class ListViewAdapter extends RecycleViewBaseAdapter {

	//普通条目类型
	private final static int NORMAL_TYPE = 0;
	//加载更多条目类型
	private final static int LOADER_MORE_TYPE = 1;
	private OnRefreshListener mOnRefreshListener;

	public ListViewAdapter(List<ItemBean> data) {
		super(data);
	}

	/**
	 * 根据ViewType的类型来设置内容
	 *
	 * @param viewGroup
	 * @return
	 */
	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
		View view = getSubView(viewGroup, viewType);
		if (viewType == NORMAL_TYPE) {
			return new InnerHolder(view);
		} else {
			return new LoaderMoreHolder(view);
		}
	}

	public void onBindViewHolder(RecyclerView.ViewHolder innerHolder, int position) {

		if (getItemViewType(position) == NORMAL_TYPE && innerHolder instanceof InnerHolder) {
			//设置数据
			((InnerHolder) innerHolder).setData(mData.get(position), position);
		} else if (getItemViewType(position) == LOADER_MORE_TYPE && innerHolder instanceof LoaderMoreHolder) {
			((LoaderMoreHolder) innerHolder).update(LoaderMoreHolder.LOADER_STATE_LOADING);
		}

	}

	@Override
	protected View getSubView(ViewGroup parent, int viewType) {
		View view;
		//根据类型获取View
		if (viewType == NORMAL_TYPE) {
			view = View.inflate(parent.getContext(), R.layout.item_list_view, null);
		} else {
			view = View.inflate(parent.getContext(), R.layout.item_list_loader_more, null);
		}
		return view;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == getItemCount() - 1) {
			//最后一个数据时返回加载更多
			return LOADER_MORE_TYPE;
		}
		return NORMAL_TYPE;
	}

	public void setOnRefreshListener(OnRefreshListener listener) {
		this.mOnRefreshListener = listener;
	}
	public interface OnRefreshListener{
		void onUpPullRefresh(LoaderMoreHolder loaderMoreHolder);
	}

	public class LoaderMoreHolder extends RecyclerView.ViewHolder {

		//设置选中的类型
		public static final int LOADER_STATE_LOADING = 0;
		public static final int LOADER_STATE_RELOAD = 1;
		public static final int LOADER_STATUS_NORMAL = 2;

		private final LinearLayout mLoading;
		private final RelativeLayout mReload;

		public LoaderMoreHolder(@NonNull View itemView) {
			super(itemView);
			//找到控件
			mLoading = itemView.findViewById(R.id.loading);
			mReload = itemView.findViewById(R.id.reload);

			mReload.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//设置加载数据
					startLoaderMore();
				}
			});
		}

		public void update(int status) {
			switch (status) {
				case LOADER_STATE_LOADING:
					mReload.setVisibility(View.GONE);
					mLoading.setVisibility(View.VISIBLE);
					//设置加载数据
					startLoaderMore();
					break;
				case LOADER_STATE_RELOAD:
					mLoading.setVisibility(View.GONE);
					mReload.setVisibility(View.VISIBLE);
					break;
				case LOADER_STATUS_NORMAL:
					mReload.setVisibility(View.GONE);
					mLoading.setVisibility(View.GONE);
					break;
			}
		}

		private void startLoaderMore() {
			if (mOnRefreshListener != null) {
				mOnRefreshListener.onUpPullRefresh(this);
			}
		}
	}
}
