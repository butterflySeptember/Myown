package com.example.new_fmredioplayer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.example.new_fmredioplayer.base.BaseActivity;
import com.example.new_fmredioplayer.interfaces.ISearchCallback;
import com.example.new_fmredioplayer.presenters.SearchPresenter;
import com.example.new_fmredioplayer.utils.LogUtils;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.word.HotWord;
import com.ximalaya.ting.android.opensdk.model.word.QueryResult;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity implements ISearchCallback {

	private final static String TAG = "SearchActivity";
	private View mBackBtn;
	private EditText mInputBox;
	private View mSearchBtn;
	private FrameLayout mResultContainer;
	private SearchPresenter mSearchPresenter;

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
				//todo：执行搜索操作

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

	}

	@Override
	public void onHotWordLoad(List<HotWord> hotWordList) {
		LogUtils.d(TAG,"hotWord size -- > " + hotWordList.size());
		List<String> hotwords = new ArrayList<>();
		for (HotWord hotWord : hotWordList) {
			String searchWord = hotWord.getSearchword();
			hotwords.add(searchWord);
		}
		//更新UI
	}

	@Override
	public void onLoadMoreResult(List<Album> result, boolean isOkay) {

	}

	@Override
	public void onRecommendLoaded(List<QueryResult> keywordList) {

	}

	@Override
	public void registerViewCallback(Object iCallback) {

	}

	@Override
	public void unRegisterViewCallback(Object iCallback) {

	}
}
