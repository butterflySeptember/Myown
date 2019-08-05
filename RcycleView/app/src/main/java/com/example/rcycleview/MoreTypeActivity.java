package com.example.rcycleview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.rcycleview.adapter.MoreTypeAdapter;
import com.example.rcycleview.beans.Datas;
import com.example.rcycleview.beans.MoreTypeBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoreTypeActivity extends AppCompatActivity {

	private RecyclerView mRecyclerView;
	private List<MoreTypeBean> mData;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_type);
		initView();
		initData();
		//创建布局管理器
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(linearLayoutManager);
		//创建适配器
		MoreTypeAdapter moreTypeAdapter = new MoreTypeAdapter(mData);
		mRecyclerView.setAdapter(moreTypeAdapter);

	}


	private void initData() {
		//准备数据
		mData = new ArrayList<>();
		Random random = new Random();
		for (int i = 0; i < Datas.icons.length ; i++){
			MoreTypeBean data = new MoreTypeBean();
			data.pic = Datas.icons[i];
			//设置随机条目类型
			data.type = random.nextInt(3);
			mData.add(data);
		}
	}

	private void initView() {
		//初始化控件
		mRecyclerView = this.findViewById(R.id.more_type_list);
	}


}
