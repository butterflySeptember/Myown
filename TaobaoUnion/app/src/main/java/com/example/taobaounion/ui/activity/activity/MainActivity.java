
package com.example.taobaounion.ui.activity.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.transition.Transition;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.taobaounion.R;
import com.example.taobaounion.ui.activity.fragment.HomeFragment;
import com.example.taobaounion.ui.activity.fragment.RedPacketFragment;
import com.example.taobaounion.ui.activity.fragment.SearchFragment;
import com.example.taobaounion.ui.activity.fragment.SelectedFragment;
import com.example.taobaounion.utils.LogUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

	@BindView(R.id.main_navigation_bar)
	public BottomNavigationView mNavigationView;

	private final static String TAG = "MainActivity";
	private FragmentTransaction mTransaction;
	private HomeFragment mHomeFragment;
	private SelectedFragment mSelectedFragment;
	private RedPacketFragment mRedPacketFragment;
	private SearchFragment mSearchFragment;
	private FragmentManager mFm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		initFragment();
		initEvent();
	}

	private void initFragment() {
		mFm = getSupportFragmentManager();
		mHomeFragment = new HomeFragment();
		mSelectedFragment = new SelectedFragment();
		mRedPacketFragment = new RedPacketFragment();
		mSearchFragment = new SearchFragment();
		transitionHideAndShowFragment(mHomeFragment);
	}

	private void initEvent() {
		mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				LogUtils.d(MainActivity.class,"NavigationView item -- > " + item.getTitle());
				LogUtils.d(MainActivity.class,"NavigationView id -- > " + item.getItemId());
				switch (item.getItemId()){
					case R.id.home:
						LogUtils.d(MainActivity.class,"首页");
						transitionHideAndShowFragment(mHomeFragment);
						break;
					case R.id.search:
						transitionHideAndShowFragment(mSearchFragment);
						break;
					case R.id.selected:
						transitionHideAndShowFragment(mSelectedFragment);
						break;
					case R.id.red_packet:
						transitionHideAndShowFragment(mRedPacketFragment);
						break;
				}
				return true;
			}
		});
	}

	private void transitionHideAndShowFragment(Fragment fragment) {
		LogUtils.d(MainActivity.class, "transitionHideAndShowFragment ... ");
		mTransaction = mFm.beginTransaction();
		mTransaction.replace(R.id.main_page_container, fragment);
		mTransaction.commit();
	}


}
