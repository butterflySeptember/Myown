package com.example.retrofitdemo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.retrofitdemo.R;
import com.example.retrofitdemo.domain.JsonResult;

import java.util.ArrayList;
import java.util.List;

public class itemAdapter extends RecyclerView.Adapter<itemAdapter.InnerHolder>{

	List<JsonResult.DataBean> mList = new ArrayList<>();
	private Context mContext;

	@NonNull
	@Override
	public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		mContext = parent.getContext();
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_list,parent,false);
		return new InnerHolder(itemView);
	}

	@Override
	public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
		ImageView coverIv = holder.itemView.findViewById(R.id.cover_image);
		TextView titleTv = holder.itemView.findViewById(R.id.title_text);
		TextView userName = holder.itemView.findViewById(R.id.user_name);
		TextView commentCount = holder.itemView.findViewById(R.id.comment_count);
		TextView viewCount = holder.itemView.findViewById(R.id.view_count);
		titleTv.setText(mList.get(position).getTitle());
		userName.setText(mList.get(position).getUserName());
		commentCount.setText(mList.get(position).getCommentCount());
		viewCount.setText(mList.get(position).getViewCount());

		Glide.with(mContext).load(mList.get(position).getCover())
				.into(coverIv);
	}

	@Override
	public int getItemCount() {
		return mList.size();
	}

	public void setData(JsonResult jsonResult) {
		//更新传入的数据
		mList.clear();
		mList.addAll(jsonResult.getData());
		notifyDataSetChanged();
	}

	public class InnerHolder extends RecyclerView.ViewHolder {

		public InnerHolder(@NonNull View itemView) {
			super(itemView);
		}
	}
}
