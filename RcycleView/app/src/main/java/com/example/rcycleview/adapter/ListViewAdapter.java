package com.example.rcycleview.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rcycleview.R;
import com.example.rcycleview.beans.ItemBean;

import java.util.List;


public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.InnerHolder> {

	private final List<ItemBean> mData;

	public ListViewAdapter(List<ItemBean> data){
		this.mData = data;
	}

	/**
	 * 这个方法用于创建条目的View
	 * @param viewGroup
	 * @param i
	 * @return
	 */
	@Override
	public InnerHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		//拿到View，创建inflate
		View view = View.inflate(viewGroup.getContext(), R.layout.item_list_view, null);
		//传入view
		return new InnerHolder(view);
	}

	/**
	 * 用于绑定holder，一般用于设置数据
	 * @param innerHolder
	 * @param position
	 */
	@Override
	public void onBindViewHolder(InnerHolder innerHolder, int position) {
		//设置数据
		innerHolder.setData(mData.get(position));
	}

	/**
	 * 返回条目个数
	 * @return
	 */
	@Override
	public int getItemCount() {
		if (mData != null) {
			return mData.size();
		}
		return 0;
	}

	public class InnerHolder extends RecyclerView.ViewHolder {

		private final ImageView mIcon;
		private final TextView mTitle;

		public InnerHolder(@NonNull View itemView) {
			super(itemView);
			//初始化控件
			mIcon = itemView.findViewById(R.id.list_view_icon);
			mTitle = itemView.findViewById(R.id.list_view_title);
		}

		//设置数据
		public void setData(ItemBean itemBean) {
			mIcon.setImageResource(itemBean.icon);
			mTitle.setText(itemBean.title);
		}
	}
}
