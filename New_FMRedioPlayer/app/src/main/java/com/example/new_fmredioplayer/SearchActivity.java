package com.example.new_fmredioplayer;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.example.new_fmredioplayer.adapters.AlbumListAdapter;
import com.example.new_fmredioplayer.base.BaseActivity;
import com.example.new_fmredioplayer.interfaces.ISearchCallback;
import com.example.new_fmredioplayer.presenters.SearchPresenter;
import com.example.new_fmredioplayer.utils.LogUtils;
import com.example.new_fmredioplayer.views.UILoader;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.word.HotWord;
import com.ximalaya.ting.android.opensdk.model.word.QueryResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchActivity extends BaseActivity implements ISearchCallback {

	private final static String TAG = "SearchActivity";
	private View mBackBtn;
	private EditText mInputBox;
	private View mSearchBtn;
	private FrameLayout mResultContainer;
	private SearchPresenter mSearchPresenter;
	private UILoader mUILoader;
	private RecyclerView mResultListView;
	private AlbumListAdapter mAlbumListAdapter;
	//	private FlowTextLayout mFlowTextLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		initView();
		initEvent();
		initPresent();
	}

	private void initPresent() {
		//注册UI更新的接口
		mSearchPresenter = SearchPresenter.getSearchPresenter();
		mSearchPresenter.registerViewCallback(this);
		//拿到热词
		mSearchPresenter.getHotWord();
	}

	private void initView() {
		mBackBtn = this.findViewById(R.id.search_back);
		mInputBox = this.findViewById(R.id.search_input);
		mSearchBtn = this.findViewById(R.id.search_btn);
		mResultContainer = this.findViewById(R.id.search_container);
//		mFlowTextLayout = this.findViewById(R.id.flow_text_layout);
		if (mUILoader == null) {
			mUILoader = new UILoader(this) {
				@Override
				protected View getSuccessView(ViewGroup container) {
					return createSuccessView();
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
		mResultListView = resultView.findViewById(R.id.result_list_view);
		//设置布局管理器
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		mResultListView.setLayoutManager(layoutManager);
		//设置适配器
		mAlbumListAdapter = new AlbumListAdapter();
		mResultListView.setAdapter(mAlbumListAdapter);
		return resultView;
	}

	private void initEvent() {
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
				LogUtils.d(TAG,"content -- > " + s);
				LogUtils.d(TAG,"start -- > " + start);
				LogUtils.d(TAG,"before -- > " + before);
				LogUtils.d(TAG,"count -- > " + count);

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
		LogUtils.d(TAG,"hotWord size -- > " + hotWordList.size());
		List<String> hotWords = new ArrayList<>();
		hotWords.clear();
		for (HotWord hotWord : hotWordList) {
			String searchWord = hotWord.getSearchword();
			hotWords.add(searchWord);
		}
		Collections.sort(hotWords);
		//更新UI
//		mFlowTextLayout.setTextContents(hotWords);
	}

	@Override
	public void onLoadMoreResult(List<Album> result, boolean isOkay) {

	}

	@Override
	public void onRecommendLoaded(List<QueryResult> keywordList) {

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
}
