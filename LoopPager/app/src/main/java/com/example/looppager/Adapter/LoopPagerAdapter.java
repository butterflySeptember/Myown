package com.example.looppager.Adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;
import java.util.Objects;

public class LoopPagerAdapter extends PagerAdapter {
	private List<Integer> sPics = null;

	@Override
	public int getCount() {
		if(sPics != null ){
			//获取数组长度
			//return sPics.size();
			//无限个数
			return Integer.MAX_VALUE;
		}
		return 0;
	}

	@Override
	public Object instantiateItem( ViewGroup container, int position) {
		int viewPosition = position % sPics.size();
		ImageView imageView = new ImageView(container.getContext());
		//设置颜色
		//imageView.setBackgroundColor(mColors.get(position));
		//设置图片
		imageView.setImageResource(sPics.get(viewPosition));
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

	public void setData(List<Integer> sPics) {
		this.sPics = sPics ;
	}

	public int getDataViewSize(){
		if(sPics !=null ){
			return sPics.size();
		}
		return 0;
	}

}
