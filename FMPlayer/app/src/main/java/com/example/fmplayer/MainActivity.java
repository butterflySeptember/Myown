 package com.example.fmplayer;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.fmplayer.Adapter.MainContentPagerAdpter;

 public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {

     private RadioGroup mTabGroup;
     private ViewPager mviwePager;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();

    }

    private void initview() {
        FragmentManager supportFragmentManger =  this.getSupportFragmentManager();
         //创建适配器
        MainContentPagerAdpter fragmentAdapter = new MainContentPagerAdpter(supportFragmentManger);
        mviwePager = (ViewPager)this.findViewById(R.id.Viewpager_main_activity);
        mviwePager.setAdapter(fragmentAdapter);
        //注册类型
        mTabGroup = (RadioGroup)this.findViewById(R.id.tab_group);

        //设置监听
        mTabGroup.setOnCheckedChangeListener(this);

        //默认选项
        /**
         *  mTabGroup.check(R.id.tab_index);
         *    第二种实现
         */
        RadioButton indexTab = (RadioButton)this.findViewById(R.id.tab_index);
        indexTab.setChecked(true);

    }

     @Override
     public void onCheckedChanged(RadioGroup group, int checkedId) {
         //底部点击状态
         switch (checkedId){
             case R.id.tab_index:
                 break;
             case R.id.tab_discovery:
                 break;
             case R.id.tab_mylinster:
                 break;
             case R.id.tab_mine:
                 break;

         }
     }
 }
