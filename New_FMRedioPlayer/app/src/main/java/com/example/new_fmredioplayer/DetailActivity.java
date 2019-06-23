package com.example.new_fmredioplayer;


import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.new_fmredioplayer.adapters.DetailListAdpater;
import com.example.new_fmredioplayer.base.BaseActivity;
import com.example.new_fmredioplayer.interfaces.IAlbumDetailViewCallBack;
import com.example.new_fmredioplayer.interfaces.IAlbumDetialPresenter;
import com.example.new_fmredioplayer.presenters.AlbumDetialPresenter;
import com.example.new_fmredioplayer.utils.ImageBlur;
import com.example.new_fmredioplayer.utils.LogUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

public class DetailActivity extends BaseActivity implements IAlbumDetailViewCallBack {

	private ImageView mLargeCover;
	private ImageView mSmallCover;
	private TextView mAlbumTitle;
	private TextView mAlbumAuthor;
	private AlbumDetialPresenter mAlbumDetialPresenter;
	private int mCurrentPage = 1 ;

	private static final String TAG = "DetailActivity";
	private RecyclerView mDetailList;
	private DetailListAdpater mDetailListAdpater;

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
		mLargeCover = this.findViewById(R.id.iv_large_cover);
		mSmallCover = this.findViewById(R.id.viv_small_color);
		mAlbumTitle = this.findViewById(R.id.tv_album_title);
		mAlbumAuthor = this.findViewById(R.id.tv_album_author);
		mDetailList = this.findViewById(R.id.album_detail_list);
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
	}

	@Override
	public void onDetailListLoaded(List<Track> tracks) {
		//更新UI
		mDetailListAdpater.setData(tracks);

	}

	@Override
	public void onAlbunLoaded(Album album) {

		long id = album.getId();

		LogUtils.d(TAG,"get id" + id);
		//获取专辑详情内容
		mAlbumDetialPresenter.getAlbumDetial((int)id,mCurrentPage);

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
}
