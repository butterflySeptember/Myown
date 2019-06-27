package com.example.new_fmredioplayer.adapters;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.new_fmredioplayer.R;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.ArrayList;
import java.util.List;

public class PlayerTrackPagerAdapter extends PagerAdapter {

	private List<Track> mData = new ArrayList<>();

	@NonNull
	@Override
	public Object instantiateItem(@NonNull ViewGroup container, int position) {

		//设置对应布局
		View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_track_pager,container,false);
		container.addView(itemView);
		//设置数据
		//找到控件
		ImageView trackPageItem = itemView.findViewById(R.id.track_pager_view);
		//设置图片
		Track track = mData.get(position);
		String coverUrlLarge = track.getCoverUrlLarge();
		Picasso.with(container.getContext()).load(coverUrlLarge).into(trackPageItem);
		return itemView;
	}

	@Override
	public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
		container.removeView((View) object);
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
		return view == object;
	}

	public void setData(List<Track> list) {
		//清空数据内容
		mData.clear();
		//设置数据
		mData.addAll(list);
		notifyDataSetChanged();
	}

}
