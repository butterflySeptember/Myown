package com.example.taobaounion.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class baseFragment extends Fragment {

	private Unbinder mBind;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = loadRootView(inflater, container, savedInstanceState);
		mBind = ButterKnife.bind(this, view);
		initView(view);
		initPresenter();
		loadData();
		return view;
	}

	protected void initView(View rootView){

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

	protected View loadRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		int resId = getRootId();
		return inflater.inflate(resId,container,false);
	}

	protected abstract int getRootId();
}
