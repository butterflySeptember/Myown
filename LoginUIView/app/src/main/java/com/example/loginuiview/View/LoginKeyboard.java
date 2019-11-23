package com.example.loginuiview.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.loginuiview.R;

public class LoginKeyboard extends LinearLayout implements View.OnClickListener {

	private ImageView mDelBtn;
	private KeyPadActionListener mKeyPadActionListener = null;

	public LoginKeyboard(Context context) {
		this(context, null);
	}

	public LoginKeyboard(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LoginKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		View.inflate(context, R.layout.include_login_keyboard, this);
		initView();
		initListener();
	}

	/**
	 * 找到各个控件
	 */
	private void initView() {
		this.findViewById(R.id.key_pad_number_1).setOnClickListener(this);
		this.findViewById(R.id.key_pad_number_2).setOnClickListener(this);
		this.findViewById(R.id.key_pad_number_3).setOnClickListener(this);
		this.findViewById(R.id.key_pad_number_4).setOnClickListener(this);
		this.findViewById(R.id.key_pad_number_5).setOnClickListener(this);
		this.findViewById(R.id.key_pad_number_6).setOnClickListener(this);
		this.findViewById(R.id.key_pad_number_7).setOnClickListener(this);
		this.findViewById(R.id.key_pad_number_8).setOnClickListener(this);
		this.findViewById(R.id.key_pad_number_9).setOnClickListener(this);
		this.findViewById(R.id.key_pad_number_0).setOnClickListener(this);
		mDelBtn = (ImageView) this.findViewById(R.id.key_pad_delete);
	}

	private void initListener() {
		mDelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				//把这个回调出去
				if (mKeyPadActionListener != null) {
					//处理删除按钮被点击了
					mKeyPadActionListener.onDelClick(view);
				}
			}
		});

		mDelBtn.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				//这个是删除按钮被长按了
				if (mKeyPadActionListener != null) {
					//处理删除按钮被点击了
					return mKeyPadActionListener.onDelLongPress(view);
				}
				return false;
			}
		});
	}

	@Override
	public void onClick(View view) {
		//数字按钮被点击了,把所点击的内容传到外面去
		if (mKeyPadActionListener != null) {
			String number = ((TextView) view).getText().toString();
			mKeyPadActionListener.onNumberClick(view, number);
		}

	}

	/**
	 * 设置按钮的点击事件
	 *
	 * @param listener
	 */
	public void setKeyPadActionListener(KeyPadActionListener listener) {
		this.mKeyPadActionListener = listener;
	}


	/**
	 * 这个接口用于通知点击事件
	 * 包括数字键盘被点击了，删除键盘被点击了
	 * 删除按钮被长按了。
	 */
	public interface KeyPadActionListener {
		void onNumberClick(View view, String number);

		void onDelClick(View view);

		boolean onDelLongPress(View view);
	}
}