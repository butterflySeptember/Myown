package com.example.december_2019.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.december_2019.R;
import com.example.december_2019.bean.UserFragment;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.InnerHolder>{

	private List<UserFragment> mData = new ArrayList<>();

	@NonNull
	@Override
	public InnerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		View listView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_view, null);
		return new InnerHolder(listView);
	}

	@Override
	public void onBindViewHolder(@NonNull InnerHolder innerHolder, int i) {
		innerHolder.itemView.getTag(i);
		innerHolder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//todo:设置点击事件
			}
		});
	}

	@Override
	public int getItemCount() {
		if (mData != null) {
			return mData.size();
		}
		return 0;
	}

	public class InnerHolder extends RecyclerView.ViewHolder {

		public InnerHolder(@NonNull View itemView) {
			super(itemView);
		}
	}
}
