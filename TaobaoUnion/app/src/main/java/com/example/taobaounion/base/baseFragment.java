package com.example.taobaounion.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.taobaounion.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class baseFragment extends Fragment {

	private Status currentStatus = Status.NONE;
	private View mSuccessView;
	private View mLoadingView;
	private View mErrorView;
	private View mEmptyView;

	public enum Status {
		NONE, LOADING, SUCCESS, EMPTY, ERROR
	}

	private Unbinder mBind;
	private FrameLayout mBaseContainer;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.base_fragment_layout, container, false);
		mBaseContainer = rootView.findViewById(R.id.base_container);
		loadStatusView(inflater, container);
		mBind = ButterKnife.bind(this, rootView);
		initView(rootView);
		initPresenter();
		loadData();
		return rootView;
	}

	/**
	 * 加载各种状态的View
	 *
	 * @param inflater
	 * @param container
	 */
	private void loadStatusView(LayoutInflater inflater, ViewGroup container) {

		//成功的View
		mSuccessView = loadSuccessView(inflater, container);
		mBaseContainer.addView(mSuccessView);

		//加载中的View
		mLoadingView = loadLoadingView(inflater, container);
		mBaseContainer.addView(mLoadingView);

		//错误页面
		mErrorView = loadErrorView(inflater, container);
		mBaseContainer.addView(mErrorView);

		mEmptyView = loadEmptyView(inflater, container);
		mBaseContainer.addView(mEmptyView);
		setUpStatus(Status.NONE);
	}

	private View loadErrorView(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.fragment_error, container, false);
	}

	private View loadEmptyView(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.fragment_empty, container, false);
	}

	public void setUpStatus(Status status) {
		this.currentStatus = status;
		mSuccessView.setVisibility(currentStatus == Status.SUCCESS ? View.VISIBLE : View.GONE);
		mLoadingView.setVisibility(currentStatus == Status.LOADING ? View.VISIBLE : View.GONE);
		mErrorView.setVisibility(currentStatus == Status.ERROR ? View.VISIBLE : View.GONE);
		mEmptyView.setVisibility(currentStatus == Status.EMPTY ? View.VISIBLE : View.GONE);
	}


	/**
	 * 加载中的view
	 *
	 * @param inflater
	 * @param container
	 * @return
	 */
	protected View loadLoadingView(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.fragment_loading, container, false);
	}

	protected void initView(View rootView) {

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mBind != null) {
			mBind.unbind();
		}
		release();
	}

	protected void release() {
		//释放资源
	}

	protected void initPresenter() {
		//创建Presenter
	}

	protected void loadData() {
		//加载数据
	}

	protected View loadSuccessView(LayoutInflater inflater, ViewGroup container) {
		int resId = getRootId();
		return inflater.inflate(resId, container, false);
	}

	protected abstract int getRootId();
}
