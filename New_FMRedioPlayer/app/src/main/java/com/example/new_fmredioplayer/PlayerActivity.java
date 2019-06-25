package com.example.new_fmredioplayer;

import android.os.Bundle;


import com.example.new_fmredioplayer.base.BaseActivity;
import com.example.new_fmredioplayer.presenters.PlayPresenter;

public class PlayerActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
		//TODO:测试播放
		PlayPresenter playPresenter = PlayPresenter.getPlayPresenter();
		playPresenter.play();
	}
}
