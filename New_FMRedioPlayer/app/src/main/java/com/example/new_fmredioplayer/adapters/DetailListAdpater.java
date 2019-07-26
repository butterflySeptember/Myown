package com.example.new_fmredioplayer.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.new_fmredioplayer.R;

import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DetailListAdpater extends RecyclerView.Adapter<DetailListAdpater.InnerHolder> {

	private List<Track> mDetailData = new ArrayList<>();
	//格式化时间
	private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy_MM_dd");
	private ItemClickListener mItemClickListener = null;
	private ItemLongClickListener mItemLongClickListener = null;

	@NonNull
	@Override
	public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_layout,parent,false);
		return new InnerHolder(itemView);
	}

	@Override
	public void onBindViewHolder(@NonNull InnerHolder holder, final int position) {
		//找到控件，设置数据
		View itemView = holder.itemView;
		//顺序ID
		TextView orderTv = itemView.findViewById(R.id.order_text);
		//标题
		TextView titleTv = itemView.findViewById(R.id.detail_item_title);
		//播放次数
		TextView playCountTv = itemView.findViewById(R.id.album_play_count);
		//时长
		TextView durationTv = itemView.findViewById(R.id.detail_item_duration);
		//更新日期
		TextView updateDateTv = itemView.findViewById(R.id.detail_item_update_time);

		//设置数据
		final Track track = mDetailData.get(position);
		orderTv.setText((position + 1) +"");
		titleTv.setText(track.getTrackTitle());
		playCountTv.setText(track.getPlayCount()+ "");
		durationTv.setText(track.getSampleDuration()+"");
		String updateTimeText = (String) mSimpleDateFormat.format(track.getUpdatedAt());
		updateDateTv.setText(updateTimeText);

		//设置item的点击事件
		itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(v.getContext(),"you click " + position +" item ",Toast.LENGTH_SHORT).show();
				if (mItemClickListener != null) {
					//添加参数：列表位置
					mItemClickListener.onItemClick(mDetailData,position);
				}
			}
		});

		itemView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				if (mItemLongClickListener != null) {
					mItemLongClickListener.onItemLongClickListener(track);
				}
				return true;
			}
		});
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

	public void setItemClickListener (ItemClickListener listener){
		this.mItemClickListener = listener;
	}

	public interface ItemClickListener{
		void onItemClick(List<Track> detailData, int position);
	}

	public void setItemLongClickListener(ItemLongClickListener listener){
		this.mItemLongClickListener = listener;
	}

	public interface ItemLongClickListener{
		void onItemLongClickListener(Track track);
	}
}
