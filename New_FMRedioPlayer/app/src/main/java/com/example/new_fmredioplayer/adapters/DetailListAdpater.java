package com.example.new_fmredioplayer.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.new_fmredioplayer.R;

import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.ArrayList;
import java.util.List;

public class DetailListAdpater extends RecyclerView.Adapter<DetailListAdpater.InnerHolder> {

	private List<Track> mDetailData = new ArrayList<>();

	@NonNull
	@Override
	public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_layout,parent,false);
		return new InnerHolder(itemView);
	}

	@Override
	public void onBindViewHolder(@NonNull DetailListAdpater.InnerHolder innerHolder, int i) {

	}

	@Override
	public int getItemCount() {
		return mDetailData.size();
	}

	public void setData(List<Track> tracks) {
		//清除原来的数据
		mDetailData.clear();
		//添加新的数据
		mDetailData.addAll(tracks);
		//更新数据，更新UI
		notifyDataSetChanged();
	}

	public class InnerHolder extends RecyclerView.ViewHolder {
		public InnerHolder(@NonNull View itemView) {
			super(itemView);
		}
	}
}
