package com.example.rcycleview.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.rcycleview.R;
import com.example.rcycleview.beans.ItemBean;

import java.util.List;


public class ListViewAdapter extends RecycleViewBaseAdapter {

	//普通条目类型
	private final static int NORMAL_TYPE = 0;
	//加载更多条目类型
	private final static int LOADER_MORE_TYPE = 1;

	public ListViewAdapter(List<ItemBean> data) {
		super(data);
	}

	/**
	 * 根据ViewType的类型来设置内容
	 * @param viewGroup
	 * @return
	 */
	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
		View view = getSubView(viewGroup,viewType);
		if (viewType == NORMAL_TYPE){
			return new InnerHolder(view);
		}else {
			return new LoaderMoreHolder(view);
		}
	}

	@Override
	protected View getSubView(ViewGroup parent, int viewType) {
		View view;
		//根据类型获取View
		if (viewType == NORMAL_TYPE){
			view = View.inflate(parent.getContext(),R.layout.item_list_view,null);
		}else {
			view = View.inflate(parent.getContext(),R.layout.item_list_view_more,null);
		}
		return view;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == getItemCount()-1){
			//最后一个数据时返回加载更多
			return LOADER_MORE_TYPE;
		}
		return NORMAL_TYPE;
	}

	public class LoaderMoreHolder extends RecyclerView.ViewHolder{

		public LoaderMoreHolder(@NonNull View itemView) {
			super(itemView);
		}
	}
}
