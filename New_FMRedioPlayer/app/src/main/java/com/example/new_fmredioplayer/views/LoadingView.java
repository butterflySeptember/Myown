package com.example.new_fmredioplayer.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.support.annotation.Nullable;

import com.example.new_fmredioplayer.R;

@SuppressLint("AppCompatCustomView")
public class LoadingView extends ImageView {

	//要旋转的角度
	private int rotatDegree = 0;

	private boolean mNeedRotate = false ;

	public LoadingView(Context context) {
		this(context,null);
	}

	public LoadingView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs,0);
	}

	public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		//设置图片
		setImageResource(R.mipmap.loading);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		mNeedRotate = true ;
		//绑定到window
		post(new Runnable() {
			@Override
			public void run() {
				rotatDegree += 30;
				rotatDegree =  rotatDegree <= 360 ? rotatDegree: 0;
				invalidate();
				//判断是否进行旋转
				if (mNeedRotate) {
					postDelayed(this,100);
				}
			}
		});
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		mNeedRotate = false;
		//和window解绑
	}

	protected void onDraw(Canvas canvas){
		/**
		 *第一个参数为旋转角度
		 * 第二个第三个参数为xy的坐标
		 */
		canvas.rotate(rotatDegree,getWidth()/2,getHeight()/2);
		super.onDraw(canvas);
	}
}
