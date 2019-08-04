package com.example.rcycleview.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.example.rcycleview.beans.ItemBean;
import com.example.rcycleview.R;
import java.util.List;

public class GridViewAdapter extends RecycleViewBaseAdapter{


	public GridViewAdapter(List<ItemBean> data) {
		super(data);
	}

	@Override
	protected View getSubView(ViewGroup parent, int position) {
		View view = View.inflate(parent.getContext(),R.layout.item_grid_view,null);
		return view;
	}
}
