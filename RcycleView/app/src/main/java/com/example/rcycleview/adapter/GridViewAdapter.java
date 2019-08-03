package com.example.rcycleview.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rcycleview.beans.ItemBean;
import com.example.rcycleview.R;
import java.util.List;

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.InnerHolder> {


	private final List<ItemBean> mData;

	public GridViewAdapter(List<ItemBean> data){
		this.mData = data;
	}

	@NonNull
	@Override
	public InnerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

		View view = View.inflate(viewGroup.getContext(),R.layout.item_grid_view,null);
		return new InnerHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull InnerHolder innerHolder, int position) {
		innerHolder.setData(mData.get(position));
	}

	@Override
	public int getItemCount() {
		if (mData != null) {
			return mData.size();
		}
		return 0;
	}

	public class InnerHolder extends RecyclerView.ViewHolder {

		private final ImageView mIconView;
		private final TextView mTitle;

		public InnerHolder(@NonNull View itemView) {
			super(itemView);
			//初始化控件
			mIconView = itemView.findViewById(R.id.grid_view_icon);
			mTitle = itemView.findViewById(R.id.gird_view_title);
		}

		public void setData(ItemBean itemBean) {
			mIconView.setImageResource(itemBean.icon);
			mTitle.setText(itemBean.title);
		}
	}
}
