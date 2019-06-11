package com.example.myapplication.ui;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.ui.fragment.HomeFragment;
import com.example.myapplication.ui.fragment.MineFragment;
import com.example.myapplication.R;

import java.util.ArrayList;

/**
 * 主页
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private ViewPager mVpTabs;
    private ArrayList<Fragment> mFragments;
    private FragmentPagerAdapter mAdapter;
    private RelativeLayout mRtlMine;
    private TextView mTvMine;
    private HomeFragment homeFragment;
    private MineFragment mineFragment;
    private RelativeLayout mRtlHome;
    private TextView mTvHome;
    private RelativeLayout mRtlType;
    private TextView mTvType;
    private Object Context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        initPage();
    }
    private void initPage() {
        mVpTabs.setOffscreenPageLimit(0);
        mFragments=new ArrayList<Fragment>();
        if(homeFragment==null){
            homeFragment=new HomeFragment();
        }
        if(mineFragment==null){
            mineFragment=new MineFragment();
        }
        mFragments.add(homeFragment);
        mFragments.add(mineFragment);
        mAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mFragments.size();
            }
            @Override
            public Fragment getItem(int arg0) {
                return mFragments.get(arg0);
            }
        };
        mVpTabs.setAdapter(mAdapter);
        mVpTabs.setCurrentItem(0);
        mVpTabs.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public int mCurentPageIndex;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                ResetTab();
                switch (position) {
                    case 0:
                        ChangeType(mTvHome, getResources().getDrawable(R.mipmap.home,null),R.color.blue);
                        homeFragment.initdate();
                        break;
                    case 1:
                        ChangeType(mTvMine, getResources().getDrawable(R.mipmap.mines,null), R.color.blue);
                        break;
                }
                mCurentPageIndex = position;
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    /**
     * 重置Tab
     */
    public  void  ResetTab(){
        ChangeType(mTvHome, getResources().getDrawable(R.mipmap.home,null),R.color.black);
        ChangeType(mTvMine, getResources().getDrawable(R.mipmap.mine,null),R.color.black);
    }
    public  void ChangeType(TextView mTv, Drawable drawable, int color){
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mTv.setCompoundDrawables(null, drawable, null, null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTv.setTextColor(getResources().getColor(color,null));
        }
    }
    private void initview() {
        mVpTabs = (ViewPager) findViewById(R.id.vPager);
        mRtlHome = (RelativeLayout) findViewById(R.id.rtl_home);
        mRtlMine = (RelativeLayout) findViewById(R.id.rtl_mime);
        mTvHome = (TextView) findViewById(R.id.tv_home);
        mTvMine = (TextView) findViewById(R.id.tv_mime);
        mRtlHome.setOnClickListener(this);
        mRtlMine.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rtl_home:
                mVpTabs.setCurrentItem(0);
                homeFragment.initdate();
                break;
            case R.id.rtl_mime:
                mVpTabs.setCurrentItem(1);
                break;
        }
    }
    private long firstTime=0;

    /**
     * 双击退出
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                long secondTime=System.currentTimeMillis();
                if(secondTime-firstTime>2000){
                    Toast.makeText(MainActivity.this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
                    firstTime=secondTime;
                    return true;
                }else{
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }
}
