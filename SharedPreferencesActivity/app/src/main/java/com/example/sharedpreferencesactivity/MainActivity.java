package com.example.sharedpreferencesactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

	private EditText mAccountNumber;
	private EditText mPassword;
	private Button mLoginBtn;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    //初始化
	private void initView() {
		mAccountNumber = this.findViewById(R.id.account_number);
		mPassword = this.findViewById(R.id.password);
		mLoginBtn = this.findViewById(R.id.login_btn);
	}
}
