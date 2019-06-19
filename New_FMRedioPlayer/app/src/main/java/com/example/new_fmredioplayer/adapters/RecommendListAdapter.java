package com.example.new_fmredioplayer.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.new_fmredioplayer.R;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;

import java.util.ArrayList;
import java.util.List;

public class RecommendListAdapter extends RecyclerView.Adapter<RecommendListAdapter.InnerHolder> {

	private List<Album> mData = new ArrayList<>();

	@NonNull
	@Override
	public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend,parent,false);
		return null;
	}

	@Override
	public void onBindViewHolder(@NonNull InnerHolder innerHolder, int i) {

	}

	@Override
	public int getItemCount() {
		//返回要显示的个数
		if (mData != null) {
			return mData.size();
		}
		return 0;
	}

	public void setData(List<Album> albumList) {
		if (mData != null) {
			mData.clear();
			mData.addAll(albumList);
		}
		//更新UI
		notifyDataSetChanged();
	}

	public class InnerHolder extends RecyclerView.ViewHolder{

		public InnerHolder(View itemView) {
			super(itemView);
		}
	}
}
