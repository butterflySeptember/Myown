package com.example.rcycleview;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.rcycleview.adapter.GridViewAdapter;
import com.example.rcycleview.adapter.ListViewAdapter;
import com.example.rcycleview.adapter.RecycleViewBaseAdapter;
import com.example.rcycleview.adapter.StaggerAapter;
import com.example.rcycleview.beans.Datas;
import com.example.rcycleview.beans.ItemBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private List<ItemBean> mData;
	private RecyclerView mListView;
	private RecycleViewBaseAdapter mAdapter;
	private SwipeRefreshLayout mSwipeRefreshLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView = this.findViewById(R.id.recycle_view);
		mSwipeRefreshLayout = this.findViewById(R.id.refresh_layout);
		//模拟数据
		initData();
		//默认显示为ListView效果
		showList(true,false);

		//处理下拉刷新
		handlerDownPullUpdate();

	}

	private void handlerDownPullUpdate() {
		//设置开始刷新
		mSwipeRefreshLayout.setEnabled(true);
		mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				//添加刷新数据
				ItemBean itemBean = new ItemBean();
				itemBean.title = "我是新添加的数据";
				itemBean.icon = R.mipmap.pic_09;
				//确定数据添加的位置和添加的数据
				mData.add(0,itemBean);
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						mAdapter.notifyDataSetChanged();
						mSwipeRefreshLayout.setEnabled(false);
					}
				},2000);
			}
		});
	}

	/**
	 *
	 * 下拉加载更多数据
	 * @param loaderMoreHolder
	 */
	private void handlerUpPullRefresh(final ListViewAdapter.LoaderMoreHolder loaderMoreHolder) {
		//添加刷新数据
		ItemBean itemBean = new ItemBean();
		itemBean.title = "我是新添加的数据";
		itemBean.icon = R.mipmap.pic_10;
		//确定数据添加的位置和添加的数据
		mData.add(itemBean);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				mAdapter.notifyDataSetChanged();
				loaderMoreHolder.update(ListViewAdapter.LoaderMoreHolder.LOADER_STATUS_NORMAL);
			}
		},2000);
	}

	private void initEvent() {

		mAdapter.setOnItemClickListener(new RecycleViewBaseAdapter.OnItemClickListener() {
			@Override
			public void OnItemClick(int position) {
				Toast.makeText(MainActivity.this,"你点击了第" + position +"个条目", Toast.LENGTH_SHORT).show();
			}
		});

		if (mAdapter instanceof ListViewAdapter){
			((ListViewAdapter)mAdapter).setOnRefreshListener(new ListViewAdapter.OnRefreshListener() {
				@Override
				public void onUpPullRefresh(ListViewAdapter.LoaderMoreHolder loaderMoreHolder) {
					handlerUpPullRefresh(loaderMoreHolder);
				}
			});

		}

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
			int j = i+1;
			data.title = "我是第" + j  + "个条目";
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
				showGrid(true,false);
				break;
			case R.id.gird_view_vertical_reverse:
				showGrid(true,true);
				break;
			case R.id.gird_view_horizontal_stander:
				showGrid(false,false);
				break;
			case R.id.gird_view_horizontal_reverse:
				showGrid(false,true);
				break;

			//瀑布流效果
			case R.id.stagger_view_vertical_stander:
				showStagger(true,false);
				break;
			case R.id.stagger_view_vertical_reverse:
				showStagger(true,true);
				break;
			case R.id.stagger_view_horizontal_stander:
				showStagger(false,false);
				break;
			case R.id.stagger_view_horizontal_reverse:
				showStagger(false,true);
				break;

				//多种条目类型
			case R.id.mot_type:
				//打开新的Activity
				Intent intent = new Intent(this,MoreTypeActivity.class);
				startActivity(intent);
				break;

		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * 此方法用于实现瀑布流效果
	 */
	private void showStagger(boolean isVertical,boolean isReverse) {
		//准备布局管理器
		StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, isVertical ? StaggeredGridLayoutManager.VERTICAL :
				StaggeredGridLayoutManager.HORIZONTAL);
		//设置布局管理器方向
		staggeredGridLayoutManager.setReverseLayout(isReverse);
		mListView.setLayoutManager(staggeredGridLayoutManager);
		//设置适配器
		mAdapter = new StaggerAapter(mData);
		mListView.setAdapter(mAdapter);
		//设置点击事件
		initEvent();
	}

	/**
	 * 这个方法用于实现GridView效果
	 */
	private void showGrid(boolean isVertical,boolean isReverse) {
		//设置布局管理器
		GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
		//设置布局管理器属性
		gridLayoutManager.setOrientation(isVertical ? GridLayoutManager.VERTICAL : GridLayoutManager.HORIZONTAL);
		gridLayoutManager.setReverseLayout(isReverse);
		mListView.setLayoutManager(gridLayoutManager);
		//设置适配器
		mAdapter = new GridViewAdapter(mData);
		mListView.setAdapter(mAdapter);
		//设置点击事件
		initEvent();
	}

	/**
	 * 这个方法用于实现listView效果
	 */
	private void showList(boolean isVertical,boolean isReverse) {
		//设置布局管理器
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		//设置布局管理器的属性，控制显示方式
		linearLayoutManager.setOrientation(isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL );
		linearLayoutManager.setReverseLayout(isReverse);

		mListView.setLayoutManager(linearLayoutManager);
		//设置适配器
		mAdapter = new ListViewAdapter(mData);
		mListView.setAdapter(mAdapter);
		//设置点击事件
		initEvent();
	}
}
