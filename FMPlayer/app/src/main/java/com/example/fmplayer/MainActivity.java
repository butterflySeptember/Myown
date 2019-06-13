 package com.example.fmplayer;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.fmplayer.Adapter.MainContentPagerAdpter;
import com.example.fmplayer.utils.Constants;

 public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {

     private RadioGroup mTabGroup;
     private ViewPager mviwePager;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        initlistener();

    }

     private void initlistener() {
         mviwePager.addOnPageChangeListener(this);
     }

     private void initview() {
        FragmentManager supportFragmentManger =  this.getSupportFragmentManager();
         //创建适配器
        MainContentPagerAdpter fragmentAdapter = new MainContentPagerAdpter(supportFragmentManger);
        mviwePager = (ViewPager)this.findViewById(R.id.Viewpager_main_activity);
        mviwePager.setAdapter(fragmentAdapter);
        //注册类型
        mTabGroup = (RadioGroup)this.findViewById(R.id.tab_group);

        mTabGroup.setOnCheckedChangeListener(this);
        //默认选中第一个
        mTabGroup.check(R.id.tab_index);

        //默认选项
        /**
         *  mTabGroup.check(R.id.tab_index);
         *    第二种实现
         */
        //RadioButton indexTab = (RadioButton)this.findViewById(R.id.tab_index);
        //indexTab.setChecked(true);

    }

     @Override
     public void onCheckedChanged(RadioGroup group, int checkedId) {
         //底部点击状态
         int index = Constants.PAGE_MAIN;
         switch (checkedId){
             case R.id.tab_index:
                 index = Constants.PAGE_MAIN;
                 break;
             case R.id.tab_discovery:
                 index = Constants.PAGE_MY_LISTENER;
                 break;
             case R.id.tab_mylinster:
                 index = Constants.PAGE_DISTORY;
                 break;
             case R.id.tab_mine:
                 index = Constants.PAGE_MINE;
                 break;

         }
         mviwePager.setCurrentItem(index,false);
     }

     @Override
     public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

     }

     @Override
     public void onPageSelected(int position) {
         //处理tab点击事件
         switch (position){
             case Constants.PAGE_MAIN:
                 mTabGroup.check(R.id.tab_index);
                 break;
             case Constants.PAGE_MY_LISTENER:
                 mTabGroup.check(R.id.tab_mylinster);
                 break;
             case Constants.PAGE_DISTORY:
                 mTabGroup.check(R.id.tab_discovery);
                 break;
             case Constants.PAGE_MINE:
                 mTabGroup.check(R.id.tab_mine);
                 break;
         }
     }

     @Override
     public void onPageScrollStateChanged(int i) {

     }
 }
