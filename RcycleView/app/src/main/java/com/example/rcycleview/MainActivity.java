package com.example.rcycleview;

import android.content.ClipData;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.rcycleview.adapter.ListViewAdapter;
import com.example.rcycleview.beans.Datas;
import com.example.rcycleview.beans.ItemBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private List<ItemBean> mData;
	private RecyclerView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView = this.findViewById(R.id.recycle_view);
		//模拟数据
		initData();
		//默认显示为ListView效果
		showList(true,false);
	}

	/**
	 * 这个方法用于模拟数据
	 */
	private void initData() {

		mData = new ArrayList<>();
		//创建模拟数据
		for(int i=0 ; i< Datas.icons.length ; i++){
			//创建数据对象
			ItemBean data = new ItemBean();
			data.icon = Datas.icons[i];
			data.title = "我是第" + i+1  + "个条目";
			//添加到集合
			mData.add(data);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//显示菜单
		getMenuInflater().inflate(R.menu.recycle_view_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int itemId = item.getItemId();

		switch (itemId) {

			//listView效果
			case R.id.list_view_vertical_stander:
				showList(true,false);
				break;
			case R.id.list_view_vertical_reverse:
				showList(true,true);
				break;
			case R.id.list_view_horizontal_stander:
				showList(false,false);
				break;
			case R.id.list_view_horizontal_reverse:
				showList(false,true);
				break;

			//girdView效果
			case R.id.gird_view_vertical_stander:
				break;
			case R.id.gird_view_vertical_reverse:
				break;
			case R.id.gird_view_horizontal_stander:
				break;
			case R.id.gird_view_horizontal_reverse:
				break;

			//瀑布流效果
			case R.id.stagger_view_vertical_stander:
				break;
			case R.id.stagger_view_vertical_reverse:
				break;
			case R.id.stagger_view_horizontal_stander:
				break;
			case R.id.stagger_view_horizontal_reverse:
				break;

		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * 这个方法用于实现listView效果
	 */
	private void showList(boolean isVertical,boolean isReverse) {
		//设置布局管理器
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		mListView.setLayoutManager(linearLayoutManager);
		//设置布局管理器的属性，控制显示方式
		linearLayoutManager.setOrientation(isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL );
		linearLayoutManager.setReverseLayout(isReverse);
		//设置适配器
		ListViewAdapter listViewAdapter = new ListViewAdapter(mData);
		mListView.setAdapter(listViewAdapter);
	}
}
