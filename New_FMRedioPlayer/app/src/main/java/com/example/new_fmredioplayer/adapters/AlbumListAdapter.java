package com.example.new_fmredioplayer.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.new_fmredioplayer.R;
import com.example.new_fmredioplayer.utils.LogUtils;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.InnerHolder> {

	private List<Album> mData = new ArrayList<>();
	private final static String TAG = "RecommendListAdapter";
	private onRecommendItemClickListener mItemClickListener = null;
	private onAlbumItemLongClickListener mLongClickListener = null;

	@NonNull
	@Override
	public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		//找到View
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend,parent,false);
		return new InnerHolder(itemView);
	}

	@Override
	public void onBindViewHolder(@NonNull InnerHolder holder, final int position) {
		//封装数据
		holder.itemView.setTag(position);
		//设置点击事件
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mItemClickListener != null) {
					int clickPosition = (int)v.getTag();
					mItemClickListener.onItemClick(clickPosition,mData.get(position));
				}
				LogUtils.d(TAG,"holder.itemView.onClick -- >" + v.getTag());

			}
		});
		holder.setData(mData.get(position));

		holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				if (mLongClickListener != null) {
					int clickPosition =(int) v.getTag();
					mLongClickListener.onAlbumItemLongClick(mData.get(clickPosition));
				}
				//true表示消费掉该事件
				return false;
			}
		});
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

	public int getDataSize() {
		return mData.size();
	}

	public static class InnerHolder extends RecyclerView.ViewHolder{



		public InnerHolder(View itemView) {
			super(itemView);
		}

		public void setData(Album album) {
			//找到控件
			ImageView albumcover = (ImageView) itemView.findViewById(R.id.album_cover);
			TextView albumTitleTv  = (TextView) itemView.findViewById(R.id.album_title_tv);
			TextView desrcDesrcTv = (TextView) itemView.findViewById(R.id.album_description_tv);
			TextView albumPlayCountTv = (TextView) itemView.findViewById(R.id.album_play_count);
			TextView albumContentCountTv = (TextView) itemView.findViewById(R.id.album_content_size);

			//设置数据
			albumTitleTv.setText(album.getAlbumTitle());
			desrcDesrcTv.setText(album.getAlbumIntro());
			albumPlayCountTv.setText(album.getPlayCount() + " ");
			albumContentCountTv.setText(album.getIncludeTrackCount() +" ");

			String coverUrlLarge = album.getCoverUrlLarge();
			if ( ! TextUtils.isEmpty(coverUrlLarge)) {
				Picasso.with(itemView.getContext()).load(coverUrlLarge).into(albumcover);
			}else{
				albumcover.setImageResource(R.mipmap.logo);
			}
		}
	}
	public void setAlbumItemClickListener(onRecommendItemClickListener listener){
		this.mItemClickListener = listener;
	}

	public interface onRecommendItemClickListener{
		void onItemClick(int position, Album album);
	}

	/**
	 * item长按的接口
	 */
	public interface onAlbumItemLongClickListener {
		void onAlbumItemLongClick(Album album);
	}

	public void setOnAlbumItemLongClick(onAlbumItemLongClickListener listener){
		this.mLongClickListener = listener;
	}
}
