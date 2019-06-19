package com.example.new_fmredioplayer;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.new_fmredioplayer.adapters.MainContentAdpater;
import com.example.new_fmredioplayer.adapters.indicatorAdpater;
import com.example.new_fmredioplayer.utils.LogUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;


public class MainActivity extends FragmentActivity {
	private final static String TAG = "MainActivity";
	private MagicIndicator mMagicIndicator;
	private ViewPager mContentPager;
	private indicatorAdpater mAdpater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initview();
		initEvent();
	}

	private void initEvent() {
		mAdpater.setOnIndicatorTapClickListener(new indicatorAdpater.onIndicatorTapClickListener() {
			@Override
			public void onTabClick(int index) {
				LogUtils.d(TAG,"click index  -- >" + index);
				if (mContentPager != null) {
					mContentPager.setCurrentItem(index);
				}
			}
		});
	}

	@SuppressLint("NewApi")
	private void initview() {
		mMagicIndicator = this.findViewById(R.id.magic_indicator3);
		mMagicIndicator.setBackgroundColor(this.getResources().getColor(R.color.main_color,null));

		//创建适配器
		mAdpater = new indicatorAdpater(this);
		CommonNavigator commonNavigator = new CommonNavigator(this);
		commonNavigator.setAdjustMode(true); //设置平分位置
		commonNavigator.setAdapter(mAdpater);

		//viewpager
		mContentPager = this.findViewById(R.id.content_pager);

		//创建内容适配器
		FragmentManager supportFragmentManger = getSupportFragmentManager();
		MainContentAdpater mainContentAdpater = new MainContentAdpater(supportFragmentManger);
		mContentPager.setAdapter(mainContentAdpater);

		//把View Pager和Indicator绑定到一起
		mMagicIndicator.setNavigator(commonNavigator);
		ViewPagerHelper.bind(mMagicIndicator,mContentPager);

	}

}
