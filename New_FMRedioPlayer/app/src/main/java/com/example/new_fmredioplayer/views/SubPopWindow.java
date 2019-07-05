package com.example.new_fmredioplayer.views;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.new_fmredioplayer.R;
import com.example.new_fmredioplayer.adapters.PlayListAdpater;
import com.example.new_fmredioplayer.base.baseApplication;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.util.List;

public class SubPopWindow extends PopupWindow {

	private final View mPopView;
	private View mCloseBtn;
	private RecyclerView mTrackList;
	private PlayListAdpater mPlayListAdpater;
	private TextView mPlayModeTv;
	private ImageView mPlayModeIv;
	private View mPlayModeContainer;
	private PlayListPlayModeClickListener mPlayModeClickListener = null;
	private View mOrderBtnContainer;
	private ImageView mOrderIcon;
	private TextView mOrderText;

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

		mPlayModeContainer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//切换播放模式
				if (mPlayModeClickListener != null) {
					mPlayModeClickListener.onPlayModeClick();
				}
			}
		});

		mOrderBtnContainer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//切换播放顺序为正序或者逆序
				mPlayModeClickListener.onPlayModeClick();
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

		//播放模式相关
		mPlayModeContainer = mPopView.findViewById(R.id.play_list_play_container);
		mPlayModeTv = mPopView.findViewById(R.id.play_list_play_mode_tv);
		mPlayModeIv = mPopView.findViewById(R.id.play_list_play_mode_iv);

		mOrderBtnContainer = mPopView.findViewById(R.id.play_list_order_container);
		mOrderIcon = mPopView.findViewById(R.id.play_list_order_iv);
		mOrderText = mPopView.findViewById(R.id.play_list_order_tv);
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

	public void setCurrentPlayPosition(int position){
		if (mPlayListAdpater != null) {
			mPlayListAdpater.setCurrentPlayPosition(position);
			mTrackList.scrollToPosition(position);
		}
	}

	public void setPlayListItemClickListener(PlayListItemClickListener listener){
		mPlayListAdpater.setOnItemClickListener(listener);
	}

	/**
	 * 更新播放列表的播放模式
	 * @param currentMode
	 */
	public void updatePlayMode(XmPlayListControl.PlayMode currentMode) {
		updatePlayModeBtnImage(currentMode);
	}

	/**
	 * 更新播放顺序的同时更新图标和文字显示
	 * @param isOrder
	 */
	public void updateOrderIcon(boolean isOrder) {
		mOrderIcon.setImageResource(isOrder ? R.mipmap.sort_alpha_asc : R.mipmap.sort_alpha_up);
		mOrderText.setText(isOrder ? R.string.play_order_text_order : R.string.play_order_text_up);
	}

	/**
	 * 根据当前的播放模式更新播放图标
	 */
	private void updatePlayModeBtnImage(XmPlayListControl.PlayMode playMode) {
		int resId = R.drawable.select_play_mode_list_order;
		int textId = R.string.play_mode_order_text;
		switch (playMode){
			case PLAY_MODEL_LIST:
				resId = R.drawable.select_play_mode_list_order;
				textId = R.string.play_mode_order_text;
				break;
			case PLAY_MODEL_LIST_LOOP:
				resId = R.drawable.select_play_mode_loop;
				textId = R.string.play_mode_list_play_text;
				break;
			case PLAY_MODEL_RANDOM:
				resId = R.drawable.select_play_mode_random;
				textId = R.string.play_mode_random_text;
				break;
			case PLAY_MODEL_SINGLE_LOOP:
				resId = R.drawable.select_play_mode_single_loop;
				textId = R.string.play_mode_single_play_text;
				break;
		}
		mPlayModeIv.setImageResource(resId);
		mPlayModeTv.setText(textId);
	}

	public interface PlayListItemClickListener{
		void onItemClick(int position);
	}

	public void setPlayListPlayModeClickListener(PlayListPlayModeClickListener playModeListener){
		mPlayModeClickListener = playModeListener;
	}

	public interface PlayListPlayModeClickListener{
		void onPlayModeClick();
	}

	public interface PlayListActionListener{

		//播放模式被电击
		void onPlayModeClick();

		//播放模式切换：顺序或者逆序
		void onOrderClick();
	}
}
