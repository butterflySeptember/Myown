package com.example.taobaounion.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class baseFragment extends Fragment {

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = loadRootView(inflater, container, savedInstanceState);
		return view;
	}

	protected View loadRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		int resId = getRootId();
		return inflater.inflate(resId,container,false);
	}

	protected abstract int getRootId();
}
