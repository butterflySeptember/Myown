package com.example.sharedpreferencesactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MainActivity";
	private EditText mAccountNumber;
	private EditText mPassword;
	private Button mLoginBtn;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //设置点击事件
		initEvent();
    }

	private void initEvent() {
		mLoginBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.d(TAG, "点击了登陆按钮 ...");
				handlerLoginEvent(view);
			}
		});
	}

	/**
	 *处理登陆事件
	 * @param view
	 */
	private void handlerLoginEvent(View view) {
		//获取数据
		///账号
		 String accountNumberText = mAccountNumber.getText().toString();
		 //密码
		 String passwordText = mPassword.getText().toString();
		 //对账号和密码进行判空

		if (TextUtils.isEmpty(accountNumberText)) {
			Toast.makeText(this,"输入的账号为空",Toast.LENGTH_LONG).show();
			return;
		}

		if (TextUtils.isEmpty(passwordText)) {
			Toast.makeText(this,"输入密码为空",Toast.LENGTH_LONG).show();
		}

		 //保存账号和密码
		saveUserInfo(accountNumberText,passwordText);
	}

	/**
	 * 保存输入的账号和密码
	 * @param accountNumberText
	 * @param passwordText
	 */
	/**
	 * 保存数据存储路径 :
	 * cache dir == /data/data/com.example.sharedpreferencesactivity/cache
	 * 缓存路径，这个路径下面的文件会由系统根据内存进行清理
	 * file dir == /data/data/com.example.sharedpreferencesactivity/file
	 * 这个路径下面的文件可以用代码进行清理或者手动清理
	 * @param accountNumberText
	 * @param passwordText
	 */
	//使用文件保存数据
	private void saveUserInfo(String accountNumberText, String passwordText) {
		Log.d(TAG, "保存用户信息 ...");
		//获得文件保存的路径
		File filesDir = this.getFilesDir();
		//创建文件
		File saveFile = new File(filesDir,"info.text");
		Log.d(TAG, "file dir == " + filesDir.toString());
		try {
			if (!saveFile.exists()) {
				saveFile.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(saveFile);
			//以特定的格式来存储数据
			fos.write((accountNumberText + "***" + passwordText).getBytes());
			fos.close();
			Toast.makeText(this,"数据保存成功",Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			//打开目标文件
			FileInputStream fileInputStream = this.openFileInput("info.text");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
			//读取目标数据
			String info = bufferedReader.readLine();
			//转换字符串为目标格式
			String[] splits = info.split("\\*\\*\\*");
			String account = splits[0];
			String password = splits[1];
			//设置字符串
			mAccountNumber.setText(account);
			mPassword.setText(password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//初始化
	private void initView() {
		mAccountNumber = this.findViewById(R.id.account_number);
		mPassword = this.findViewById(R.id.password);
		mLoginBtn = this.findViewById(R.id.login_btn);
	}
}
