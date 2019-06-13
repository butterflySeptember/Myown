package com.example.looppager.Adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;
import java.util.Objects;

public class LoopPagerAdapter extends PagerAdapter {
	private List<Integer> mColors = null;

	@Override
	public int getCount() {
		if(mColors != null ){
			return mColors.size();
		}
		return 0;
	}

	@Override
	public Object instantiateItem( ViewGroup container, int position) {
		ImageView imageView = new ImageView(container.getContext());
		//设置颜色
		imageView.setBackgroundColor(mColors.get(position));
		//添加view
		container.addView(imageView);
		return imageView;
	}

	@Override
	public void destroyItem( ViewGroup container, int position,  Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject( View view,  Object object) {
		return view == object;
	}

	public void setData(List<Integer> colors) {
		this.mColors = colors ;
	}

}
