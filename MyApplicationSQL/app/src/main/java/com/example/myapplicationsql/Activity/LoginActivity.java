package com.example.myapplicationsql.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplicationsql.R;

public class LoginActivity extends AppCompatActivity  {

	private EditText mLoginText;
	private EditText mPasswordText;
	private CheckedTextView mCheckedTextViewForget;
	private CheckBox mLoginCheckBox;
	private boolean mIsCheckBox;
	private Button mLoginBtn;
	private CheckedTextView mLoginNewBox;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity_main);
		initView();
		initEvent();
	}

	private void initEvent() {
		mLoginCheckBox.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mIsCheckBox) {
					mLoginCheckBox.setChecked(false);
				} else {
					mLoginCheckBox.setChecked(true);
				}
			}
		});
		mLoginBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mIsCheckBox) {
					if (mLoginText.getText() == null) {
						Toast.makeText(getApplicationContext(), "请输入用户名", Toast.LENGTH_SHORT).show();
					} else if (mPasswordText.getText() == null) {
						Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
					} else {
						//todo：处理登录事件
					}
				} else {
					Toast.makeText(getApplicationContext(), "请勾选用户协议", Toast.LENGTH_SHORT).show();
				}
			}
		});

		mLoginNewBox.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//打开创建新账号界面
				startActivity(new Intent(getApplicationContext(),RegistActivity.class));
			}
		});
	}

	private void initView() {
		mLoginText = this.findViewById(R.id.main_login_edit_text);
		mPasswordText = this.findViewById(R.id.main_login_password);
		mCheckedTextViewForget = this.findViewById(R.id.checkbox_forget_view);
		mLoginCheckBox = this.findViewById(R.id.login_checkbox);
		mLoginBtn = this.findViewById(R.id.login_btn);
		mLoginNewBox = this.findViewById(R.id.checkbox_new_view);
		mIsCheckBox = mLoginCheckBox.isChecked();
	}


}
