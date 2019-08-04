package com.example.rcycleview.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rcycleview.R;
import com.example.rcycleview.beans.ItemBean;

import java.util.List;


public class ListViewAdapter extends RecycleViewBaseAdapter {


	public ListViewAdapter(List<ItemBean> data) {
		super(data);
	}

	@Override
	protected View getSubView(ViewGroup parent, int position) {

		View view = View.inflate(parent.getContext(),R.layout.item_list_view,null);
		return view;
	}
}
