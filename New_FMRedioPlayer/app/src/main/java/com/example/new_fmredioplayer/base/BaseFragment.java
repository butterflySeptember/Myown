package com.example.new_fmredioplayer.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

	private View mRootView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstar) {
		mRootView = onSubViewLoaded(inflater,container);
		return mRootView;
	}

	public abstract View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) ;
}
