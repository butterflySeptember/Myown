package com.example.new_fmredioplayer.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.example.new_fmredioplayer.R;
import com.example.new_fmredioplayer.base.BaseApplication;
import com.example.new_fmredioplayer.base.BaseFragment;
import com.example.new_fmredioplayer.base.BaseApplication;

public abstract class UILoader extends FrameLayout {

	private View mLoadingView;
	private View mSuccessView;
	private View mNetworkErrorView;
	private View mEmptyView;
	private OnRetryClickListener mOnClickRetryListener =null;

	public enum UIStatus{
		LOADING,SUCCESS,NETWORK_ERROR,EMPTY,NONE
	}

	public UIStatus mCurrentStatus = UIStatus.NONE;

	public UILoader(Context context) {
		this(context,null);
	}

	public UILoader(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public UILoader(Context context,AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		init();
	}

	public void updateStatus(UIStatus status){
		mCurrentStatus = status;
		//更新UI一定要在主线程中
		BaseApplication.getHandler().post(new Runnable() {
			@Override
			public void run() {
				switchUIByCurrentStatus();
			}
		});
	}

	/**
	 * 初始化UI
	 */
	private void init() {
		switchUIByCurrentStatus();
	}

	private void switchUIByCurrentStatus() {
		//加载中
		if (mLoadingView == null) {
			mLoadingView = getLoadingView();
			addView(mLoadingView);
		}
		//设置是否可见
		mLoadingView.setVisibility(mCurrentStatus==UIStatus.LOADING?VISIBLE:GONE);

		//成功
		if (mSuccessView == null) {
			mSuccessView = getSuccessView(this);
			addView(mSuccessView);
		}
		mSuccessView.setVisibility(mCurrentStatus==UIStatus.SUCCESS?VISIBLE:GONE);

		//网络错误
		if (mNetworkErrorView == null) {
			mNetworkErrorView = getNetworkErrorView();
			addView(mNetworkErrorView);
		}
		mNetworkErrorView.setVisibility(mCurrentStatus==UIStatus.NETWORK_ERROR?VISIBLE:GONE);

		//数据为空界面
		if (mEmptyView == null) {
			mEmptyView = getEmptyView();
			addView(mEmptyView);
		}
		mEmptyView.setVisibility(mCurrentStatus==UIStatus.EMPTY?VISIBLE:GONE);
	}

	protected View getEmptyView(){
		View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_empty_view, this, false);
		return emptyView;
	}

	protected View getNetworkErrorView(){
		View networkErrorView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_error_view,this,false);
		networkErrorView.findViewById(R.id.network_error_icon).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//重新获取数据
				if (mOnClickRetryListener != null) {
					mOnClickRetryListener.OnRetryClick();
				}
			}
		});
		return networkErrorView;
	}

	protected abstract View getSuccessView(ViewGroup container);

	private View getLoadingView() {
		return LayoutInflater.from(getContext()).inflate(R.layout.fragment_loading_view,this,false);
	}

	public void setOnRetryClickListener(OnRetryClickListener listener){
		this.mOnClickRetryListener = listener;
	}

	public interface OnRetryClickListener{
		void OnRetryClick();
	}
}
