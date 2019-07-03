package com.example.new_fmredioplayer.views;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.example.new_fmredioplayer.R;
import com.example.new_fmredioplayer.adapters.PlayListAdpater;
import com.example.new_fmredioplayer.base.baseApplication;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;

public class SubPopWindow extends PopupWindow {

	private final View mPopView;
	private View mCloseBtn;
	private RecyclerView mTrackList;
	private PlayListAdpater mPlayListAdpater;

	public SubPopWindow(){
		//设置宽高
		super(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

		//设置外部可点击
		//外部点击关闭：先使用setBackgroundDrawable，再设置setOutsideTouchable
		setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		setOutsideTouchable(true);
		//载入View
		mPopView = LayoutInflater.from(baseApplication.getAppContext()).inflate(R.layout.player_pop_window, null);
		//设置内容
		setContentView(mPopView);
		//设置窗口动画
		setAnimationStyle(R.style.pop_animation);
		initView();
		initEvent();
	}

	private void initEvent() {
		mCloseBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//关闭窗口
				SubPopWindow.this.dismiss();
			}
		});
	}

	private void initView() {
		mCloseBtn = mPopView.findViewById(R.id.close_list_btn);
		mTrackList = mPopView.findViewById(R.id.play_list_rv);
		//设置布局管理器
		LinearLayoutManager layoutManager = new LinearLayoutManager(baseApplication.getAppContext());
		mTrackList.setLayoutManager(layoutManager);
		//设置适配器
		mPlayListAdpater = new PlayListAdpater();
		mTrackList.setAdapter(mPlayListAdpater);
	}

	/**
	 * 给适配器设置数据
	 * @param data
	 */
	public void setListData (List<Track> data){
		if (mPlayListAdpater != null) {
			mPlayListAdpater.setData(data);
		}
	}
}
