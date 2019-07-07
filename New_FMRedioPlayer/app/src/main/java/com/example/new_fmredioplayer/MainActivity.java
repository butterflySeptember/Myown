package com.example.new_fmredioplayer;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.new_fmredioplayer.adapters.MainContentAdpater;
import com.example.new_fmredioplayer.adapters.indicatorAdpater;
import com.example.new_fmredioplayer.interfaces.IPlayerCallback;
import com.example.new_fmredioplayer.presenters.PlayPresenter;
import com.example.new_fmredioplayer.utils.LogUtils;
import com.example.new_fmredioplayer.views.RoundRectImageView;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Announcer;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.List;


public class MainActivity extends FragmentActivity implements IPlayerCallback {
	private final static String TAG = "MainActivity";
	private MagicIndicator mMagicIndicator;
	private ViewPager mContentPager;
	private indicatorAdpater mAdpater;
	private RoundRectImageView mRoundRectImageView;
	private TextView mHeaderTitle;
	private TextView mSubTitle;
	private ImageView mPlayControl;
	private PlayPresenter mPlayPresenter;
	private String mTrackTitle;
	private String mAnnouncer;
	private String mCoverUrlSmall;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initEvent();
		initPresenter();
	}

	private void initPresenter() {
		mPlayPresenter = PlayPresenter.getPlayPresenter();
		mPlayPresenter.registerViewCallback(this);
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
		mPlayControl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//控制播放状态
				if (mPlayPresenter != null) {
					if (mPlayPresenter.isPlay()) {
						mPlayPresenter.pause();
					}else {
						mPlayPresenter.play();
					}
				}
			}
		});
	}

	@SuppressLint("NewApi")
	private void initView() {
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

		//找到下方播放栏的控件
		mRoundRectImageView = this.findViewById(R.id.main_track_cover);
		mHeaderTitle = this.findViewById(R.id.main_head_title);
		//设置滚动
		mHeaderTitle.setSelected(true);
		mSubTitle = this.findViewById(R.id.main_sub_title);
		mPlayControl = this.findViewById(R.id.main_play_control);

	}

	@Override
	public void onPlayStart() {
		updatePlayControl(true);
	}

	private void updatePlayControl(boolean isPlaying) {
		if (mPlayControl != null) {
			mPlayControl.setImageResource(isPlaying ? R.drawable.select_player_stop : R.drawable.select_player_play_or_not);
		}
	}

	@Override
	public void onPlayPause() {
		updatePlayControl(false);
	}

	@Override
	public void onPlayStop() {
		updatePlayControl(false);
	}

	@Override
	public void onPlayerError() {

	}

	@Override
	public void nextPlay(Track track) {

	}

	@Override
	public void onPrePlayer(Track track) {

	}

	@Override
	public void onListLoaded(List<Track> list) {

	}

	@Override
	public void onPlayModeChange(XmPlayListControl.PlayMode playMode) {

	}

	@Override
	public void onProgramsChange(int current, int total) {

	}

	@Override
	public void onAdLoading() {

	}

	@Override
	public void onAdFinished() {

	}

	@Override
	public void onTrackUpdate(Track track, int playIndex) {
		if (track != null) {
			mTrackTitle = track.getTrackTitle();
			mAnnouncer = track.getAnnouncer().getNickname();
			mCoverUrlSmall = track.getCoverUrlSmall();
			//设置显示内容
			LogUtils.d(TAG,"标题 -- >" + mTrackTitle);
			if (mHeaderTitle != null) {
				mHeaderTitle.setText(mTrackTitle);
			}
			LogUtils.d(TAG,"作者 --> " + mAnnouncer );
			if (mSubTitle != null) {
				mSubTitle.setText(mAnnouncer +"");
			}
			if (mRoundRectImageView != null) {
				Picasso.with(this).load(mCoverUrlSmall).into(mRoundRectImageView);
			}
		}
	}

	@Override
	public void updateListOrder(boolean isReverse) {

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mPlayPresenter != null) {
			//解绑presenter
			mPlayPresenter.unRegisterViewCallback(this);
		}
	}

	@Override
	public void registerViewCallback(Object o) {

	}

	@Override
	public void unRegisterViewCallback(Object o) {

	}
}
