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

public abstract class RecycleViewBaseAdapter extends RecyclerView.Adapter<RecycleViewBaseAdapter.InnerHolder> {

	private final List<ItemBean> mData;

	public RecycleViewBaseAdapter(List<ItemBean> data){
		this.mData = data;
	}

	@NonNull
	@Override
	public InnerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

		View view = getSubView(viewGroup,i);

		return new InnerHolder(view);
	}

	protected abstract View getSubView(ViewGroup parent, int position);

	/**
	 * 用于绑定holder，一般用于设置数据
	 * @param innerHolder
	 * @param position
	 */
	public void onBindViewHolder(ListViewAdapter.InnerHolder innerHolder, int position) {
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
			mIcon = itemView.findViewById(R.id.view_icon);
			mTitle = itemView.findViewById(R.id.view_title);
		}

		//设置数据
		public void setData(ItemBean itemBean) {
			mIcon.setImageResource(itemBean.icon);
			mTitle.setText(itemBean.title);
		}
	}
}
