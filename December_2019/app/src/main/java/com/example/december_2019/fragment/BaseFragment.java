package com.example.december_2019.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseFragment extends Fragment {

	private View mInflate;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mInflate = onSubViewLoaded(inflater, container);
		return mInflate;
	}

	public abstract View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) ;

}
