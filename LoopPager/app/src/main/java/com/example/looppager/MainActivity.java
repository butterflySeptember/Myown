package com.example.looppager;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.looppager.Adapter.LoopPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

	private ViewPager mViewPager;
	private LoopPagerAdapter mLoopPagerAdapter;
	private static List<Integer> sColors = new ArrayList<>();
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

		Random random = new Random();
		//设置颜色数据
		for (int i = 0; i < 5 ; i++){
			sColors.add(Color.argb( random.nextInt(255),random.nextInt(255),random.nextInt(255),random.nextInt(255)));
		}

		//向适配器中添加数据
		mLoopPagerAdapter.setData(sColors);
		//后设置数据
		mLoopPagerAdapter.notifyDataSetChanged();

    }

    private void initView() {
		//1.初始化控件
		mViewPager = (ViewPager)this.findViewById(R.id.loop_pager);
		//设置适配器
		mLoopPagerAdapter = new LoopPagerAdapter();
		mViewPager.setAdapter(mLoopPagerAdapter);
    }
}
