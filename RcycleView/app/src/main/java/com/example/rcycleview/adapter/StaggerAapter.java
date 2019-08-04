package com.example.rcycleview.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.example.rcycleview.R;
import com.example.rcycleview.beans.ItemBean;

import java.util.List;

public class StaggerAapter extends RecycleViewBaseAdapter {

	public StaggerAapter(List<ItemBean> data) {
		super(data);
	}

	@Override
	protected View getSubView(ViewGroup parent, int position) {
		View view = View.inflate(parent.getContext(), R.layout.item_stagger_view,null);
		return view;
	}
}
