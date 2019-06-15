package com.example.looppager;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.looppager.Adapter.LoopPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

	private ViewPager mViewPager;
	private LoopPagerAdapter mLoopPagerAdapter;
	//private static List<Integer> sColors = new ArrayList<>();
	private static List<Integer> sPic = new ArrayList<>();

	static {
		sPic.add(R.mipmap.pic1);
		sPic.add(R.mipmap.pic2);
		sPic.add(R.mipmap.pic3);

	}

	private Handler mHandler;
	private LinearLayout mPointContainer;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
		/*Random random = new Random();
		//设置颜色数据
		for (int i = 0; i < 5 ; i++){
			sColors.add(Color.argb( random.nextInt(255),random.nextInt(255),random.nextInt(255),random.nextInt(255)));
		}
		//向适配器中添加数据
		mLoopPagerAdapter.setData(sColors);
		//后设置数据
		mLoopPagerAdapter.notifyDataSetChanged();*/
		mHandler = new Handler();
    }

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		//当界面绑定到窗口
		mHandler.post(mLooperTask);
	}

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		//当窗口解除绑定
		mHandler.removeCallbacks(mLooperTask);
	}

	private Runnable mLooperTask = new Runnable() {
		@Override
		public void run() {
			//切换图片
			int currentItem  = mViewPager.getCurrentItem();
			mViewPager.setCurrentItem(++currentItem,true);
			mHandler.postDelayed(this,3000);
		}
	};

	private void initView() {
		//1.初始化控件
		mViewPager = (ViewPager)this.findViewById(R.id.loop_pager);
		//设置适配器
		mLoopPagerAdapter = new LoopPagerAdapter();
		mLoopPagerAdapter.setData(sPic);
		mViewPager.setAdapter(mLoopPagerAdapter);
		//设置初始轮播位置
		mViewPager.addOnPageChangeListener(this);
		//根据图片的个数编辑点的个数
		mPointContainer = (LinearLayout)this.findViewById(R.id.points_container);
		insertPoint();
		mViewPager.setCurrentItem(mLoopPagerAdapter.getDataViewSize()*100,false);

	}

	private void insertPoint() {
		for ( int i = 0;i<sPic.size();i++){
			View point = new View(this);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(40,40);
			point.setBackground(getResources().getDrawable(R.drawable.shap_point_nomal,null));
			layoutParams.leftMargin = 20;
			point.setLayoutParams(layoutParams);
			mPointContainer.addView(point);
		}
	}

	@Override
	public void onPageScrolled(int i, float v, int i1) {

	}

	@Override
	public void onPageSelected(int position) {
		//Page停下时选中的位置
		int realposition;
		if(mLoopPagerAdapter.getDataViewSize() != 0) {
			realposition = position % mLoopPagerAdapter.getDataViewSize();
		}else{
			realposition = 0;
			return;
		}
		setSelectPoint(realposition);
	}

	private void setSelectPoint(int realposition) {
		for(int i=0;i < mPointContainer.getChildCount(); i++){
			View point = mPointContainer.getChildAt(i);
			if(i != realposition){
				//显示为白色
				point.setBackgroundResource(R.drawable.shap_point_nomal);
			}else{
				//选中的颜色
				point.setBackgroundResource(R.drawable.shap_point_select);
			}
		}
	}

	@Override
	public void onPageScrollStateChanged(int i) {

	}
}
