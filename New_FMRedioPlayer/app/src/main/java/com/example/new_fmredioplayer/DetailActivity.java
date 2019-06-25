package com.example.new_fmredioplayer;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.new_fmredioplayer.adapters.DetailListAdpater;
import com.example.new_fmredioplayer.base.BaseActivity;
import com.example.new_fmredioplayer.interfaces.IAlbumDetailViewCallBack;
import com.example.new_fmredioplayer.interfaces.IAlbumDetialPresenter;
import com.example.new_fmredioplayer.presenters.AlbumDetialPresenter;
import com.example.new_fmredioplayer.presenters.PlayPresenter;
import com.example.new_fmredioplayer.utils.ImageBlur;
import com.example.new_fmredioplayer.utils.LogUtils;
import com.example.new_fmredioplayer.views.UILoader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

public class DetailActivity extends BaseActivity implements IAlbumDetailViewCallBack, UILoader.OnRetryClickListener, DetailListAdpater.ItemClickListener {

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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_datial);
		//设置导航栏为透明
		getWindow().setStatusBarColor(Color.TRANSPARENT);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
		
		initView();

		mAlbumDetialPresenter = AlbumDetialPresenter.getInstance();
		mAlbumDetialPresenter.registerViewCallback(this);

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

	}

	private View createSuccessView(ViewGroup container) {
		View detailListView = LayoutInflater.from(this).inflate(R.layout.item_detail_list,container,false);
		mDetailList = detailListView.findViewById(R.id.album_detail_list);
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
		return detailListView;
	}

	@Override
	public void onDetailListLoaded(List<Track> tracks) {
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
}
