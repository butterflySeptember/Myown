package com.example.fmplayer.Views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoSrollViewPager extends ViewPager {

    public NoSrollViewPager( Context context) {

    	super(context);
    }

    public NoSrollViewPager (Context context, AttributeSet attrs){
    	super(context,attrs);
	}


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
        /**
         * false 表示未处理
         * true 表示消费掉事件
         */
    }
}
