package com.example.new_fmredioplayer.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.example.new_fmredioplayer.R;

import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;


public class indicatorAdpater extends CommonNavigatorAdapter {

	private final String[] mTitles;
	private onIndicatorTapClickListener mOnTabClickListener;

	public indicatorAdpater (Context context){
		mTitles = context.getResources().getStringArray(R.array.indicator_title);
	}
	@Override
	public int getCount() {
		if (mTitles != null) {
			return mTitles.length;
		}
		return 0;
	}

	@Override
	public IPagerTitleView getTitleView(Context context, final int index) {
		//创建View
		SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
		simplePagerTitleView.setText(mTitles[index]);
		simplePagerTitleView.setNormalColor(Color.parseColor("#c8e6c9"));
		simplePagerTitleView.setSelectedColor(Color.WHITE);
		simplePagerTitleView.setTextSize(12);
		simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//mViewPager.setCurrentItem(index);
				if (mOnTabClickListener != null) {
					mOnTabClickListener.onTabClick(index);
				}
			}
		});
		return simplePagerTitleView;
	}

	@Override
	public IPagerIndicator getIndicator(Context context) {
		LinePagerIndicator indicator = new LinePagerIndicator(context);
		indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
		indicator.setYOffset(UIUtil.dip2px(context, 3));
		indicator.setColors(Color.parseColor("#ffffff"));
		return indicator;
	}

	public void setOnIndicatorTapClickListener(onIndicatorTapClickListener listener){
		this.mOnTabClickListener = listener;
	}

	public interface onIndicatorTapClickListener{
		void onTabClick(int index);
	}
}
