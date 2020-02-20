package com.example.december_2019.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.december_2019.R;
import com.example.december_2019.adapter.UserListAdapter;

import java.util.zip.Inflater;

public class FragmentFrist extends BaseFragment {

	private View mView;
	private UserListAdapter mUserListAdapter;
	private RecyclerView mListView;

	@Override
	public View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
		mView =  layoutInflater.inflate(R.layout.fragment_first, null);
		initView();
		return mView;
	}

	private void initView() {
		mListView = mView.findViewById(R.id.textView);
		mUserListAdapter = new UserListAdapter();
		mListView.setAdapter(mUserListAdapter);
	}

}
