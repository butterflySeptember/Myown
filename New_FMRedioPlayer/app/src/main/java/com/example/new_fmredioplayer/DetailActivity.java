package com.example.new_fmredioplayer;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.new_fmredioplayer.adapters.DetailListAdpater;
import com.example.new_fmredioplayer.base.BaseActivity;
import com.example.new_fmredioplayer.base.baseApplication;
import com.example.new_fmredioplayer.interfaces.IAlbumDetailViewCallBack;
import com.example.new_fmredioplayer.interfaces.IPlayerCallback;
import com.example.new_fmredioplayer.presenters.AlbumDetialPresenter;
import com.example.new_fmredioplayer.presenters.PlayPresenter;
import com.example.new_fmredioplayer.utils.ImageBlur;
import com.example.new_fmredioplayer.utils.LogUtils;
import com.example.new_fmredioplayer.views.UILoader;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.bezierlayout.BezierLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

public class DetailActivity extends BaseActivity implements IAlbumDetailViewCallBack, UILoader.OnRetryClickListener, DetailListAdpater.ItemClickListener, IPlayerCallback {

	private ImageView mLargeCover;
	private ImageView mSmallCover;
	private TextView mAlbumTitle;
	private TextView mAlbumAuthor;
	private AlbumDetialPresenter mAlbumDetialPresenter;
	private int mCurrentPage = 1 ;

	private static final String TAG = "DetailActivity";
	private RecyclerView mDetailList;
	private DetailListAdpater mDetailListAdpater;
	private FrameLayout mDetailListContainer;
	private UILoader mUiLoader;

	private long mCurrentId = -1;
	private ImageView mPlayControlBtn;
	private TextView mPlayControlTips;
	private PlayPresenter mPlayPresenter;
	private List<Track> mCurrentTrack = null;
	private final static int DEFAULT_PLAY_INDEX = 0;
	private TwinklingRefreshLayout mRefreshLayout;
	private boolean mIsLoaderMore = false;
	private String mTrackTitle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_datial);
		//设置导航栏为透明
		getWindow().setStatusBarColor(Color.TRANSPARENT);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
		
		initView();
		//播放详情列表的Presenter
		mAlbumDetialPresenter = AlbumDetialPresenter.getInstance();
		mAlbumDetialPresenter.registerViewCallback(this);
		//播放控制列表的Presenter
		mPlayPresenter = PlayPresenter.getPlayPresenter();
		mPlayPresenter.registerViewCallback(this);
		updatePlayState(mPlayPresenter.isPlay());
		initEvent();
	}



	private void initEvent() {
		mPlayControlBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mPlayPresenter != null) {
					//判断播放器是否有播放列表
					boolean hasPlayList = mPlayPresenter.hasPlayList();
					if (hasPlayList) {
						handlePlayControl();
					}else {
						handleNoPlayListControl();
					}
				}
			}
		});
	}

	/**
	 * 播放器没有播放内容时，默认播放第一个
	 */
	private void handleNoPlayListControl() {
		mPlayPresenter.setPlayList(mCurrentTrack,DEFAULT_PLAY_INDEX);
		//mPlayPresenter.play();
	}

	private void handlePlayControl() {
		//控制播放器的状态
		if (mPlayPresenter.isPlay()) {
			mPlayPresenter.pause();
		}else{
			mPlayPresenter.play();
		}
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		mDetailListContainer = this.findViewById(R.id.detail_list_container);
		if (mUiLoader == null) {
			mUiLoader = new UILoader(this) {
				@Override
				protected View getSuccessView(ViewGroup container) {
					return createSuccessView(container);
				}
			};
			mDetailListContainer.removeAllViews();
			mDetailListContainer.addView(mUiLoader);
			mUiLoader.setOnRetryClickListener(DetailActivity.this);
		}


		mLargeCover = this.findViewById(R.id.iv_large_cover);
		mSmallCover = this.findViewById(R.id.viv_small_color);
		mAlbumTitle = this.findViewById(R.id.tv_album_title);
		mAlbumAuthor = this.findViewById(R.id.tv_album_author);
		//播放控制图标
		mPlayControlBtn = this.findViewById(R.id.detail_play_control);
		mPlayControlTips = this.findViewById(R.id.play_control_tv);
		mPlayControlTips.setSelected(true);

	}

	private View createSuccessView(ViewGroup container) {
		View detailListView = LayoutInflater.from(this).inflate(R.layout.item_detail_list,container,false);
		mDetailList = detailListView.findViewById(R.id.album_detail_list);
		//设置下拉刷新布局
		mRefreshLayout = detailListView.findViewById(R.id.refresh_layout);
		//RecyclerView的使用步骤
		//第一步，设置布局管理器
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		mDetailList.setLayoutManager(layoutManager);
		//第二步，设置适配器
		mDetailListAdpater = new DetailListAdpater();
		mDetailList.setAdapter(mDetailListAdpater);
		//设置item的间距
		mDetailList.addItemDecoration(new RecyclerView.ItemDecoration() {
			@Override
			public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
				//使用工具类 dp和px相互转换
				outRect.left = UIUtil.dip2px(view.getContext(),5);
				outRect.top = UIUtil.dip2px(view.getContext(),5);
				outRect.bottom = UIUtil.dip2px(view.getContext(),5);
			}
		});
		mDetailListAdpater.setItemClickListener(this);

		//设置刷新效果
		BezierLayout headerView = new BezierLayout(this);
		mRefreshLayout.setHeaderView(headerView);
		mRefreshLayout.setHeaderHeight(140);
		mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
			@Override
			public void onRefresh(TwinklingRefreshLayout refreshLayout) {
				super.onRefresh(refreshLayout);

				baseApplication.getHandler().postDelayed(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(DetailActivity.this,"下拉刷新成功 ...",Toast.LENGTH_SHORT).show();
						mRefreshLayout.finishRefreshing();
					}
				},2000);
			}

			@Override
			public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
				super.onLoadMore(refreshLayout);
				//加载更多内容
				if (mAlbumDetialPresenter != null) {
					mAlbumDetialPresenter.loadMore();
					mIsLoaderMore = true;
				}
			}
		});
		return detailListView;
	}

	@Override
	public void onDetailListLoaded(List<Track> tracks) {
		if (mIsLoaderMore && mRefreshLayout != null) {
			mRefreshLayout.finishLoadmore();
			mIsLoaderMore = false;
		}

		mCurrentTrack = tracks;
		//判断数据结果，根据结果显示ui控制
		if (tracks == null ||tracks.size() == 0) {
			if (mUiLoader != null) {
				mUiLoader.updateStatus(UILoader.UIStatus.EMPTY);
			}
		}

		if (mUiLoader != null) {
			mUiLoader.updateStatus(UILoader.UIStatus.SUCCESS);
		}
		//更新UI
		mDetailListAdpater.setData(tracks);

	}

	@Override
	public void onAlbunLoaded(Album album) {

		long id = album.getId();

		LogUtils.d(TAG,"get id" + id);
		mCurrentId = id;
		//获取专辑详情内容
		if (mAlbumDetialPresenter != null) {
			mAlbumDetialPresenter.getAlbumDetial((int)id,mCurrentPage);
		}
		//显示loading状态
		if (mUiLoader != null) {
			mUiLoader.updateStatus(UILoader.UIStatus.LOADING);
		}

		//
		if (mAlbumTitle != null) {
			mAlbumTitle.setText(album.getAlbumTitle());
		}

		if (mAlbumAuthor != null) {
			mAlbumAuthor.setText(album.getAnnouncer().getNickname());
		}

		//添加毛玻璃效果
		if (mLargeCover != null) {
			Picasso.with(this).load(album.getCoverUrlLarge()).into(mLargeCover, new Callback() {
				@Override
				public void onSuccess() {
					Drawable drawable = mLargeCover.getDrawable();
					if (drawable != null) {
						ImageBlur.makeBlur(mLargeCover, DetailActivity.this);
					}
				}

				@Override
				public void onError() {
					LogUtils.d(TAG,"onAlbunLoaded onError...");
				}
			});
		}

		if (mSmallCover != null) {
			Picasso.with(this).load(album.getCoverUrlSmall()).into(mSmallCover);
		}
	}

	@Override
	public void onNetworkError(int errorCode, String errorMsg) {
		//请求发生错误，显示网络异常状态
		mUiLoader.updateStatus(UILoader.UIStatus.NETWORK_ERROR);
	}

	@Override
	public void onLoaderMoreFinished(int size) {
		if (size > 0) {
			Toast.makeText(this,"成功加载" + size + "条数据",Toast.LENGTH_SHORT).show();
		}else {
			Toast.makeText(this,"没有更多节目",Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onRefreshFinished(int size) {

	}

	@Override
	public void OnRetryClick() {
		//表示网络不佳的时候，点击了重新加载
		if (mAlbumDetialPresenter != null) {
			mAlbumDetialPresenter.getAlbumDetial((int)mCurrentId,mCurrentPage);
		}
	}


	@Override
	public void onItemClick(List<Track> detailData, int position) {
		//设置播放器的数据
		PlayPresenter playPresenter = PlayPresenter.getPlayPresenter();
		playPresenter.setPlayList(detailData,position);
		//跳转到播放器界面
		Intent intent = new Intent(this,PlayerActivity.class);
		startActivity(intent);

	}


	@Override
	public void registerViewCallback(Object o) {

	}

	@Override
	public void unRegisterViewCallback(Object o) {

	}

	/**
	 * 根据播放状态修改文字和图标
	 * @param isPlaying
	 */
	private void updatePlayState(boolean isPlaying) {
		if (mPlayControlBtn != null) {
			mPlayControlBtn.setImageResource(isPlaying ? R.drawable.select_pause_black_bg : R.drawable.select_play_black_bg);
		}
		if (mPlayControlTips != null && TextUtils.isEmpty(mTrackTitle)) {
			mPlayControlTips.setText(isPlaying ? mTrackTitle : "已暂停");
		}
	}

	@Override
	public void onPlayStart() {
		//修改图标为暂停的状态，文字修改为正在播放
		updatePlayState(true);
	}

	@Override
	public void onPlayPause() {
		updatePlayState(false);
	}

	@Override
	public void onPlayStop() {
		updatePlayState(false);
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
			if (TextUtils.isEmpty(mTrackTitle) && mPlayControlTips != null) {
				mPlayControlTips.setText(mTrackTitle);
			}
		}
	}

	@Override
	public void updateListOrder(boolean isReverse) {

	}
}
