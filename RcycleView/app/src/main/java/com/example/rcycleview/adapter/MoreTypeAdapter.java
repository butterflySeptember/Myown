package com.example.rcycleview.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.rcycleview.beans.MoreTypeBean;
import com.example.rcycleview.R;

import java.util.List;

public class MoreTypeAdapter extends RecyclerView.Adapter {

	private final List<MoreTypeBean> mData;

	//定义三个常量，因为有三种类型
	public static final int TYPE_FULL_IMAGE = 0;
	public static final int TYPE_LEFT_IMAGE = 1;
	public static final int TYPE_THREE_IMAGES = 2;

	public MoreTypeAdapter (List<MoreTypeBean> data){
		this.mData = data;
	}
	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
		//创建ViewHolder
		View view;
		if (viewType == TYPE_FULL_IMAGE){
			view = View.inflate(viewGroup.getContext(),R.layout.item_type_full_image,null);
			return new FullImageHolder(view);
		}else if (viewType == TYPE_LEFT_IMAGE){
			view = View.inflate(viewGroup.getContext(),R.layout.item_type_left_image,null);
			return  new LeftImageHolder(view);
		}else if (viewType == TYPE_THREE_IMAGES){
			view = View.inflate(viewGroup.getContext(),R.layout.item_type_three_images,null);
			return new ThreeImageHolder(view);
		}

		return null;
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
		//数据
	}

	@Override
	public int getItemCount() {
		if (mData != null) {
			return mData.size();
		}
		return 0;
	}

	@Override
	public int getItemViewType(int position) {
		MoreTypeBean moreTypeBean = mData.get(position);
		switch (moreTypeBean.type){
			case TYPE_FULL_IMAGE:
				return TYPE_FULL_IMAGE;
			case TYPE_LEFT_IMAGE:
				return TYPE_LEFT_IMAGE;
			case TYPE_THREE_IMAGES:
				return TYPE_THREE_IMAGES;
		}
		return super.getItemViewType(position);
	}
	private class FullImageHolder extends RecyclerView.ViewHolder{

		public FullImageHolder(@NonNull View itemView) {
			super(itemView);
		}
	}

	private class LeftImageHolder extends RecyclerView.ViewHolder{

		public LeftImageHolder(@NonNull View itemView) {
			super(itemView);
		}
	}

	private class ThreeImageHolder extends RecyclerView.ViewHolder{

		public ThreeImageHolder(@NonNull View itemView) {
			super(itemView);
		}
	}
}
