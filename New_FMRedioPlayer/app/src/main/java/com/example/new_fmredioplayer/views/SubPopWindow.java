package com.example.new_fmredioplayer.views;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.example.new_fmredioplayer.R;
import com.example.new_fmredioplayer.base.baseApplication;

public class SubPopWindow extends PopupWindow {

	public SubPopWindow(){
		//设置宽高
		super(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

		//设置外部可点击
		//外部点击关闭：先使用setBackgroundDrawable，再设置setOutsideTouchable
		setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		setOutsideTouchable(true);
		//载入View
		View popView = LayoutInflater.from(baseApplication.getAppContext()).inflate(R.layout.player_pop_window, null);
		//设置内容
		setContentView(popView);
		//设置窗口动画
		setAnimationStyle(R.style.pop_animation);

	}
}
