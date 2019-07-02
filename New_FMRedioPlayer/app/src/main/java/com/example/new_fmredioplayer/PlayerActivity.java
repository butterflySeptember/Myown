package com.example.new_fmredioplayer;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;


import com.example.new_fmredioplayer.adapters.PlayerTrackPagerAdapter;
import com.example.new_fmredioplayer.base.BaseActivity;
import com.example.new_fmredioplayer.interfaces.IPlayerCallback;
import com.example.new_fmredioplayer.presenters.PlayPresenter;
import com.example.new_fmredioplayer.utils.LogUtils;
import com.example.new_fmredioplayer.views.SubPopWindow;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_LIST;
import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_LIST_LOOP;
import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_RANDOM;
import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_SINGLE_LOOP;

public class PlayerActivity extends BaseActivity implements IPlayerCallback, ViewPager.OnPageChangeListener {

	private ImageView mControlBtn;
	private PlayPresenter mPlayPresenter;
	private SimpleDateFormat mMinFormat = new SimpleDateFormat("mm:ss");
	private SimpleDateFormat mHourFormat = new SimpleDateFormat("hh:mm:ss");
	private TextView mTotalDuration;
	private TextView mCurrentPosition;
	private final static String TAG = "PlayerActivity";
	private SeekBar mDurationBar;
	private int mCurrentProgress = 0;
	private boolean mIsUserTouchProgressBar = false;
	private ImageView mPlayNextBtn;
	private ImageView mPlayPreBtn;
	private TextView mTrackTitle;
	private String mTrackTitleText;
	private ViewPager mTrackPagerView;
	private PlayerTrackPagerAdapter mTrackPagerAdapter;
	private boolean mIsUserSlidePage = false;
	private ImageView mPlayModeSwitchBtn;

	private static Map<XmPlayListControl.PlayMode,XmPlayListControl.PlayMode> splayModeRule = new HashMap<>();
	private XmPlayListControl.PlayMode mCurrentMode = XmPlayListControl.PlayMode.PLAY_MODEL_LIST;
	//1、列表播放：PLAY_MODEL_LIST
	//2、列表循环：PLAY_MODEL_LIST_LOOP
	//3、随机播放：PLAY_MODEL_RANDOM
	//4、单曲循环：PLAY_MODEL_SINGLE_LOOP
	static {
		splayModeRule.put(PLAY_MODEL_LIST, PLAY_MODEL_LIST_LOOP);
		splayModeRule.put(PLAY_MODEL_LIST_LOOP,PLAY_MODEL_RANDOM);
		splayModeRule.put(PLAY_MODEL_RANDOM,PLAY_MODEL_SINGLE_LOOP);
		splayModeRule.put(PLAY_MODEL_SINGLE_LOOP,PLAY_MODEL_LIST);
	}

	private ImageView mPlayerListBtn;
	private SubPopWindow mSubPopWindow;
	private ValueAnimator mEnterBgAnimator;
	private ValueAnimator mOutBgAnimator;
	private final int BG_ANIMATION_DURATION = 300;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
		//初始化控件
		mPlayPresenter = PlayPresenter.getPlayPresenter();
		//接口注册
		mPlayPresenter.registerViewCallback(this);
		initView();
		//界面初始化后获取数据
		mPlayPresenter.getPlayList();
		initEvent();
		//startPlay();
		initBgAnimation();
	}

	private void initBgAnimation() {
		//popWindow弹出时的动画
		mEnterBgAnimator = ValueAnimator.ofFloat(1.0f,0.8f);
		mEnterBgAnimator.setDuration(BG_ANIMATION_DURATION);
		mEnterBgAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float value = (float)animation.getAnimatedValue();
				LogUtils.d(TAG,"value -- > " + value);
				//更改透明度
				updateBgAlpha(value);
			}
		});
		//popWindow消失时的动画
		mOutBgAnimator = ValueAnimator.ofFloat(0.8f, 1.0f);
		mOutBgAnimator.setDuration(BG_ANIMATION_DURATION);
		mOutBgAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float value = (float)animation.getAnimatedValue();
				updateBgAlpha(value);
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mPlayPresenter != null) {
			mPlayPresenter.unRegisterViewCallback(this);
		}
	}


	/**
	 * 设置相关的事件
	 */
	@SuppressLint("ClickableViewAccessibility")
	private void initEvent() {
		mControlBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//更改播放状态
				if (mPlayPresenter.isPlay()) {
					mPlayPresenter.pause();
				}else{
					mPlayPresenter.play();
				}
			}
		});

		//设置进度条拖动更新
		mDurationBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean isFromUser) {
				if (isFromUser) {
					mCurrentProgress = progress ;
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				mIsUserTouchProgressBar = true;
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				//拖动进度条结束更新
				mIsUserTouchProgressBar = false;
				mPlayPresenter.seekTo(mCurrentProgress);
			}
		});

		mPlayNextBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//播放下一个节目
				if (mPlayPresenter != null) {
					mPlayPresenter.playNext();
				}
			}
		});

		mPlayPreBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//播放上一个节目
				if (mPlayPresenter != null) {
					mPlayPresenter.playPre();
				}
			}
		});

		mTrackPagerView.addOnPageChangeListener(this);

		mTrackPagerView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			//判断是否为用户触摸
			public boolean onTouch(View view, MotionEvent motionEvent) {
				int action = motionEvent.getAction();
				switch (action){
					case  MotionEvent.ACTION_DOWN:
						mIsUserSlidePage = true;
						break;
				}
				return false;
			}
		});

		mPlayModeSwitchBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//根据当前的Mode获取到下一个Mode
				XmPlayListControl.PlayMode playMode = splayModeRule.get(mCurrentMode);
				//处理播放模式的切换
				if (mPlayPresenter != null) {
					mPlayPresenter.switchPlayMode(mCurrentMode);
				}
			}
		});

		mPlayerListBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//展示播放列表
				mSubPopWindow.showAtLocation(v, Gravity.BOTTOM,0,0);
				//设置背景透明度
				updateBgAlpha(0.8f);
				//改变背景透明度改为渐变过程
			}
		});
		mSubPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				//弹窗消失后，恢复正常透明度
				//updateBgAlpha(1.0f);
			}
		});
	}

	private void updateBgAlpha(float alpha) {
		Window window = getWindow();
		WindowManager.LayoutParams attributes = window.getAttributes();
		attributes.alpha = alpha;
		window.setAttributes(attributes);
	}

	/**
	 * 根据当前的播放模式更新播放图标
	 */
	private void updatePlayModeBtnImage() {
		int resId = R.drawable.select_play_mode_list_order;
		switch (mCurrentMode){
			case PLAY_MODEL_LIST:
				resId = R.drawable.select_play_mode_list_order;
				break;
			case PLAY_MODEL_LIST_LOOP:
				resId = R.drawable.select_play_mode_loop;
				break;
			case PLAY_MODEL_RANDOM:
				resId = R.drawable.select_play_mode_random;
				break;
			case PLAY_MODEL_SINGLE_LOOP:
				resId = R.drawable.select_play_mode_single_loop;
				break;
		}
		mPlayModeSwitchBtn.setImageResource(resId);
	}

	/**
	 * 找到各个控件
	 */
	private void initView() {
		mControlBtn = this.findViewById(R.id.play_or_stop_btn);
		mTotalDuration = this.findViewById(R.id.track_duraction);
		mCurrentPosition = this.findViewById(R.id.current_position);
		mDurationBar = this.findViewById(R.id.track_seek_bar);
		mPlayNextBtn = this.findViewById(R.id.play_next);
		mPlayPreBtn = this.findViewById(R.id.play_pre);
		mTrackTitle = this.findViewById(R.id.track_title);
		if (!TextUtils.isEmpty(mTrackTitleText)) {
			mTrackTitle.setText(mTrackTitleText);
		}
		mTrackPagerView = this.findViewById(R.id.track_pager_view);
		//创建适配器
		mTrackPagerAdapter = new PlayerTrackPagerAdapter();
		//设置适配器
		mTrackPagerView.setAdapter(mTrackPagerAdapter);
		mPlayModeSwitchBtn = this.findViewById(R.id.player_mode_switch_btn);
		//播放列表按钮
		mPlayerListBtn = this.findViewById(R.id.player_list_btn);
		//创建popWindow
		mSubPopWindow = new SubPopWindow();
	}

	@Override
	public void onPlayStart() {
		//开始播放
		if (mControlBtn != null) {
			mControlBtn.setImageResource(R.drawable.select_player_stop);
		}
	}

	@Override
	public void onPlayPause() {
		//暂停播放
		if (mControlBtn != null) {
			mControlBtn.setImageResource(R.drawable.select_player_play_or_not);
		}
	}

	@Override
	public void onPlayStop() {
		if (mControlBtn != null) {
			mControlBtn.setImageResource(R.drawable.select_player_play_or_not);
		}
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
		//把数据设置入适配器
		if (mTrackPagerAdapter != null) {
			mTrackPagerAdapter.setData(list);
		}
	}

	@Override
	public void onPlayModeChange(XmPlayListControl.PlayMode playMode) {
		//更新播放模式，修改UI
		mCurrentMode = playMode;
		updatePlayModeBtnImage();

	}

	@Override
	public void onProgramsChange(int currentProgress, int total) {
		//设置bar的最大值
		mDurationBar.setMax(total);
		String totalDuration;
		String currentPosititon;
		//更新播放进度，更新进度条
		if (total>1000*60*60) {
			totalDuration = mHourFormat.format(total);
			currentPosititon = mHourFormat.format(currentProgress);
		}else{
			totalDuration = mMinFormat.format(total);
			currentPosititon = mMinFormat.format(currentProgress);
		}
		if (mTotalDuration != null) {
			mTotalDuration.setText(totalDuration);
		}
		//更新当前时间
		if (mCurrentPosition != null) {
			mCurrentPosition.setText(currentPosititon);
		}
		//更新进度
		//计算当前进度
		if (!mIsUserTouchProgressBar){
			//设置当前值
			mDurationBar.setProgress(currentProgress);
		}
	}

	@Override
	public void onAdLoading() {

	}

	@Override
	public void onAdFinished() {

	}

	@Override
	public void onTrackUpdate(Track track, int playIndex) {
		//更新标题
		this.mTrackTitleText = track.getTrackTitle();
		if (mTrackTitle != null) {
			mTrackTitle.setText(mTrackTitleText);
		}
		//内容改变时，获取当前的位置
		//修改当前节目改变后的图片
		if (mTrackPagerView != null) {
			mTrackPagerView.setCurrentItem(playIndex,true);
		}
	}

	@Override
	public void registerViewCallback(Object o) {

	}

	@Override
	public void unRegisterViewCallback(Object o) {

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		LogUtils.d(TAG,"position -- > " + position);
		//当页面选中的时候，切换播放的内容
		if (mPlayPresenter != null && mIsUserSlidePage == true) {
			mPlayPresenter.playByIndex(position);
		}
		mIsUserSlidePage = false;
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}
}
