package com.example.androidnetwork.adapters;

import android.support.v7.view.menu.MenuView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidnetwork.R;
import com.example.androidnetwork.domain.GetTextItem;

import java.util.ArrayList;
import java.util.List;

public class GetResultListAdapter extends RecyclerView.Adapter<GetResultListAdapter.InnerHolder> {

	private List<GetTextItem.DataBean> mList = new ArrayList<>();

	@NonNull
	@Override
	public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_get_text, parent, false);
		return new InnerHolder(itemView);
	}

	@Override
	public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
		View itemView = holder.itemView;
		ImageView cover = itemView.findViewById(R.id.item_image);
		TextView titleTv = itemView.findViewById(R.id.item_title);
		TextView contentTv = itemView.findViewById(R.id.item_content);
		GetTextItem.DataBean dataBean = mList.get(position);
		titleTv.setText(dataBean.getTitle());
		contentTv.setText(dataBean.getCover());
		Glide.with(itemView.getContext()).load("http://10.0.2.2:9102" + mList.get(position).getCover()).into(cover);
	}

	@Override
	public int getItemCount() {
		return mList.size();
	}

	public void setData(GetTextItem getTextItem) {
		mList.clear();
		mList.addAll(getTextItem.getData());
		notifyDataSetChanged();
	}

	public class InnerHolder extends RecyclerView.ViewHolder {

		public InnerHolder(@NonNull View itemView) {
			super(itemView);
		}
	}
}
