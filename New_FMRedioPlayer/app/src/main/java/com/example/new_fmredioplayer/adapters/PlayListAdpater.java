package com.example.new_fmredioplayer.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.new_fmredioplayer.R;
import com.example.new_fmredioplayer.base.baseApplication;

import com.example.new_fmredioplayer.views.SubPopWindow;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.ArrayList;
import java.util.List;

public class PlayListAdpater extends RecyclerView.Adapter<PlayListAdpater.InnerHolder> {

	private List<Track> mData = new ArrayList<>();
	private TextView mTrackTitleTv;
	private View mPlayingIconView;
	private int playingIndex = 0;
	private SubPopWindow.PlayListItemClickListener mItemClickListener = null;

	@Override
	public PlayListAdpater.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_play_list, parent, false);
		return new InnerHolder(itemView);
	}

	@Override
	public void onBindViewHolder(@NonNull PlayListAdpater.InnerHolder holder, final int position) {

		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mItemClickListener != null) {
					mItemClickListener.onItemClick(position);
				}
			}
		});
		//设置数据
		Track track = mData.get(position);
		//设置title内容
		mTrackTitleTv = holder.itemView.findViewById(R.id.track_title_tv);
		mTrackTitleTv.setText(track.getTrackTitle());
		//设置字体颜色
		mTrackTitleTv.setTextColor(playingIndex == position ?
				baseApplication.getAppContext().getResources().getColor(R.color.main_color):
				baseApplication.getAppContext().getResources().getColor(R.color.play_list_text_color));
		//找到对应的播放状态的图标
		mPlayingIconView = holder.itemView.findViewById(R.id.play_icon_iv);
		mPlayingIconView.setVisibility(playingIndex  == position ? View.VISIBLE : View.GONE);
	}

	@Override
	public int getItemCount() {
		return mData.size();
	}

	public void setData(List<Track> list) {
		//设置数据更新列表
		mData.clear();
		mData.addAll(list);
		notifyDataSetChanged();
	}

	public void setCurrentPlayPosition(int position) {
		playingIndex = position;
		notifyDataSetChanged();
	}

	public void setOnItemClickListener(SubPopWindow.PlayListItemClickListener listener) {
		this.mItemClickListener = listener;
	}

	public class InnerHolder extends RecyclerView.ViewHolder{

		public InnerHolder(@NonNull View itemView) {
			super(itemView);
		}
	}
}
