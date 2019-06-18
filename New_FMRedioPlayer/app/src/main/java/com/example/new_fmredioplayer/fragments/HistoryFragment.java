package com.example.new_fmredioplayer.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.new_fmredioplayer.R;
import com.example.new_fmredioplayer.base.BaseFragment;

public class HistoryFragment extends BaseFragment {

	public View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container){
		View rootView = layoutInflater.inflate(R.layout.fragment_history, container, false);
		return rootView;
	}
}
