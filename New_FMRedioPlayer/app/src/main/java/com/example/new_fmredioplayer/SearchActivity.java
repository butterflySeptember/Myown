package com.example.new_fmredioplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.new_fmredioplayer.adapters.AlbumListAdapter;
import com.example.new_fmredioplayer.adapters.SearchRecommendAdapter;
import com.example.new_fmredioplayer.base.BaseActivity;
import com.example.new_fmredioplayer.interfaces.ISearchCallback;
import com.example.new_fmredioplayer.presenters.AlbumDetialPresenter;
import com.example.new_fmredioplayer.presenters.SearchPresenter;
import com.example.new_fmredioplayer.utils.Constants;
import com.example.new_fmredioplayer.utils.LogUtils;
import com.example.new_fmredioplayer.views.FlowTextLayout;
import com.example.new_fmredioplayer.views.UILoader;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.word.HotWord;
import com.ximalaya.ting.android.opensdk.model.word.QueryResult;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchActivity extends BaseActivity implements ISearchCallback, AlbumListAdapter.onRecommendItemClickListener {

	private final static String TAG = "SearchActivity";
	private View mBackBtn;
	private EditText mInputBox;
	private View mSearchBtn;
	private FrameLayout mResultContainer;
	private SearchPresenter mSearchPresenter;
	private UILoader mUILoader;
	private RecyclerView mResultListView;
	private AlbumListAdapter mAlbumListAdapter;
	private FlowTextLayout mFlowTextLayout;
	private InputMethodManager mInputMethodManager;
	private View mDelBtn;
	public final static int TIME_SHOW_IMM = 500;
	private RecyclerView mSearchRecommendList;
	private SearchRecommendAdapter mRecommendAdapter;
	private TwinklingRefreshLayout mRefreshLayout;
	//	private FlowTextLayout mFlowTextLayout;
	private boolean mNeedSuggest = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		initView();
		initEvent();
		initPresent();
	}

	private void initPresent() {
		mInputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);

		//注册UI更新的接口
		mSearchPresenter = SearchPresenter.getSearchPresenter();
		mSearchPresenter.registerViewCallback(this);
		//拿到热词
		mSearchPresenter.getHotWord();
	}

	private void initView() {
		mBackBtn = this.findViewById(R.id.search_back);
		mInputBox = this.findViewById(R.id.search_input);
		mDelBtn = this.findViewById(R.id.search_input_delect_icon);
		mDelBtn.setVisibility(View.GONE);
		mInputBox.postDelayed(new Runnable() {
			@Override
			public void run() {
				mInputBox.requestFocus();
				mInputMethodManager.showSoftInput(mInputBox,InputMethodManager.SHOW_IMPLICIT);
			}
		},TIME_SHOW_IMM);
		mSearchBtn = this.findViewById(R.id.search_btn);
		mResultContainer = this.findViewById(R.id.search_container);
//		mFlowTextLayout = this.findViewById(R.id.flow_text_layout);
		if (mUILoader == null) {
			mUILoader = new UILoader(this) {
				@Override
				protected View getSuccessView(ViewGroup container) {
					return createSuccessView();
				}

				//覆写返回内容为空的方法
				@Override
				protected View getEmptyView() {
					View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_empty_view, this,false);
					TextView tipsView = emptyView.findViewById(R.id.empty_tips_tv);
					tipsView.setText(R.string.search_no_content_tip_text);
					return emptyView;
				}
			};
			if (mUILoader.getParent() instanceof ViewGroup) {
				((ViewGroup) mUILoader.getParent()).removeView(mUILoader);
			}
			mResultContainer.addView(mUILoader);
		}
	}

	/**
	 *创建网络请求成功的view
	 * @return
	 */
	private View createSuccessView() {
		View resultView = LayoutInflater.from(this).inflate(R.layout.search_result_layout, null);
		//刷新控件
		mRefreshLayout = resultView.findViewById(R.id.search_result_refresh_layout);
		mRefreshLayout.setOverScrollBottomShow(true);
		mRefreshLayout.setEnableRefresh(false);
		//热词显示
		mFlowTextLayout = resultView.findViewById(R.id.recommend_hot_word_list);

		mResultListView = resultView.findViewById(R.id.result_list_view);
		//设置布局管理器
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		mResultListView.setLayoutManager(layoutManager);
		//设置适配器
		mAlbumListAdapter = new AlbumListAdapter();
		mResultListView.setAdapter(mAlbumListAdapter);
		mResultListView.addItemDecoration(new RecyclerView.ItemDecoration() {
			@Override
			public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
				//使用工具类 dp和px相互转换
				outRect.left = UIUtil.dip2px(view.getContext(),5);
				outRect.top = UIUtil.dip2px(view.getContext(),5);
				outRect.bottom = UIUtil.dip2px(view.getContext(),5);
			}
		});
		//搜索推荐界面显示
		mSearchRecommendList = resultView.findViewById(R.id.search_recommend_list);
		//设置布局管理器
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		mSearchRecommendList.setLayoutManager(linearLayoutManager);
		mSearchRecommendList.addItemDecoration(new RecyclerView.ItemDecoration() {
			@Override
			public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
				//使用工具类 dp和px相互转换
				outRect.left = UIUtil.dip2px(view.getContext(),2);
				outRect.top = UIUtil.dip2px(view.getContext(),2);
				outRect.bottom = UIUtil.dip2px(view.getContext(),5);
			}
		});
		//设置适配器
		mRecommendAdapter = new SearchRecommendAdapter();
		mSearchRecommendList.setAdapter(mRecommendAdapter);
		return resultView;
	}

	private void initEvent() {

		if (mAlbumListAdapter != null) {
			mAlbumListAdapter.setAlbumItemClickListener(this);
		}

		mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
			@Override
			public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
				LogUtils.d(TAG,"Load more ...");
				//加载更多内容
					 if (mSearchPresenter != null) {
						 mSearchPresenter.loaderMore();
				 }
			}
		});

		if (mRecommendAdapter != null) {
			mRecommendAdapter.setItemClickListener(new SearchRecommendAdapter.itemClickListener() {
				@Override
				public void onItemClick(String keyword) {
					//不需要相关的联想词
					mNeedSuggest = false;
					switchSearch(keyword);
				}
			});
		}

		mDelBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mInputBox.setText("");
			}
		});

		mBackBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mSearchBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//执行搜索操作
				String keyword = mInputBox.getText().toString().trim();
				if (mSearchPresenter != null) {
					if (TextUtils.isEmpty(keyword)) {
						Toast.makeText(SearchActivity.this,"请输入搜索内容",Toast.LENGTH_LONG).show();
						return;
					}
					mSearchPresenter.doSearch(keyword);
					mUILoader.updateStatus(UILoader.UIStatus.LOADING);
				}
			}
		});

		mInputBox.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (TextUtils.isEmpty(s)) {
					mSearchPresenter.getHotWord();
					mDelBtn.setVisibility(View.VISIBLE);

				}else{
					mDelBtn.setVisibility(View.VISIBLE);
					if (mNeedSuggest) {
						//触发联想查询
						getSuggestWord(s.toString());
					}else {
						mNeedSuggest = true;
					}

				}
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		mUILoader.setOnRetryClickListener(new UILoader.OnRetryClickListener() {
			@Override
			public void OnRetryClick() {
				if (mSearchPresenter != null) {
					mSearchPresenter.reSearch();
					mUILoader.updateStatus(UILoader.UIStatus.LOADING);
				}
			}
		});

		mFlowTextLayout.setClickListener(new FlowTextLayout.ItemClickListener() {
			@Override
			public void onItemClick(String text) {
				//不需要相关的联想词
				mNeedSuggest = false;
				switchSearch(text);
			}
		});
	}

	private void switchSearch(String text ) {
		//当搜索内容为空时，弹出提示框
		if (TextUtils.isEmpty(text)) {
			Toast.makeText(this,"请输入搜索内容",Toast.LENGTH_LONG).show();
			return;
		}else{
			//把热词放入输入框
			mInputBox.setText(text);
			//把输入光标移到最后
			mInputBox.setSelection(text.length());
			//进行搜索
			if (mSearchPresenter != null) {
				mSearchPresenter.doSearch(text);
			}
			//改变状态
			if (mUILoader != null) {
				mUILoader.updateStatus(UILoader.UIStatus.LOADING);
			}
		}
	}

	/**
	 * 获取联想的关键词
	 * @param keyWord 关键词
	 */
	private void getSuggestWord(String keyWord) {
		if (mSearchPresenter != null) {
			mSearchPresenter.getRecommendWord(keyWord);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mSearchPresenter != null) {
			mSearchPresenter.unRegisterViewCallback(this);
			mSearchPresenter = null ;
		}
	}

	@Override
	public void onSearchResultLoad(List<Album> result) {
		handleSearchResult(result);
		//隐藏键盘
		mInputMethodManager.hideSoftInputFromWindow(mInputBox.getWindowToken(), mInputMethodManager.HIDE_NOT_ALWAYS);
	}

	private void handleSearchResult(List<Album> result) {
		hideSuccessView();
		mRefreshLayout.setVisibility(View.VISIBLE);

		if (result != null) {
			if (result.size() == 0) {
				//数据为空
				if (mUILoader != null) {
					mUILoader.updateStatus(UILoader.UIStatus.EMPTY);
				}else{
					//如果数据不为空则设置数据
					mAlbumListAdapter.setData(result);
					mUILoader.updateStatus(UILoader.UIStatus.SUCCESS);
				}
			}
		}
	}

	@Override
	public void onHotWordLoad(List<HotWord> hotWordList) {
		hideSuccessView();
		mFlowTextLayout.setVisibility(View.VISIBLE);
		if (mUILoader != null) {
			mUILoader.updateStatus(UILoader.UIStatus.SUCCESS);
		}
		LogUtils.d(TAG,"hotWord size -- > " + hotWordList.size());
		List<String> hotWords = new ArrayList<>();
		hotWords.clear();
		for (HotWord hotWord : hotWordList) {
			String searchWord = hotWord.getSearchword();
			hotWords.add(searchWord);
		}
		Collections.sort(hotWords);
		//更新UI
		mFlowTextLayout.setTextContents(hotWords);
	}

	@Override
	public void onLoadMoreResult(List<Album> result, boolean isOkay) {
		//处理加载更多的结果
		if (mRefreshLayout != null) {
			mRefreshLayout.finishLoadmore();
		}
		if ( ! isOkay) {
			Toast.makeText(SearchActivity.this,"没有更多内容",Toast.LENGTH_SHORT).show();
		}else {
			handleSearchResult(result);
		}
	}

	@Override
	public void onRecommendLoaded(List<QueryResult> keywordList) {
		//联想相关的关键字
		LogUtils.d(TAG,"keyWord list -- > " + keywordList.size());
		if (mRecommendAdapter != null) {
			mRecommendAdapter.setData(keywordList);
		}
		//控制UI的状态
		if (mUILoader != null) {
			mUILoader.updateStatus(UILoader.UIStatus.SUCCESS);
		}
		//控制显示和隐藏
		hideSuccessView();
		mRefreshLayout.setVisibility(View.VISIBLE);
	}

	private void hideSuccessView(){
		mRefreshLayout.setVisibility(View.GONE);
		mSearchRecommendList.setVisibility(View.GONE);
		mFlowTextLayout.setVisibility(View.GONE);
	}

	@Override
	public void onError(int errorCode, String errorMsg) {
		if (mUILoader != null) {
			mUILoader.updateStatus(UILoader.UIStatus.NETWORK_ERROR);
		}
	}

	@Override
	public void registerViewCallback(Object iCallback) {

	}

	@Override
	public void unRegisterViewCallback(Object iCallback) {

	}

	@Override
	public void onItemClick(int position, Album album) {
		//获取被点击的位置
		AlbumDetialPresenter.getInstance().setTargetAlbum(album);
		//item被点击,跳转到详情界面
		Intent intent = new Intent(this, DetailActivity.class);
		startActivity(intent);
	}
}
