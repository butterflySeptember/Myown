package com.example.myapplicationsql.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplicationsql.DAO.IUserDao;
import com.example.myapplicationsql.R;
import com.example.myapplicationsql.pojo.User;

import java.util.List;

public class RegistActivity extends AppCompatActivity implements IUserDao {

	private EditText mUserId;
	private EditText mUserPassword;
	private EditText mUserSex;
	private EditText mUserAge;
	private CheckBox mIsCheckBox;
	private static boolean sIsChecked = true;
	private Button mSaveBtn;
	User mUser = null;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_new_user);
		initView();
		initEvent();
	}

	private void initEvent() {
		mSaveBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (sIsChecked) {
					saveUser();
				}else {
					Toast.makeText(getApplicationContext(), "请勾选用户协议", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void saveUser(){
		if (mUserId.getText() == null) {
			Toast.makeText(getApplicationContext(), "用户名不能为空", Toast.LENGTH_SHORT).show();
		} else if (mUserPassword.getText() == null) {
			Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
		} else {
			mUser.setUserName(mUserId.getText()+"");
			mUser.setPassword(mUserPassword.getText()+"");
			mUser.setAge(mUserAge.getText());
			mUser.setSex(mUserSex.getText());
			addUser(mUser);
		}
	}

	private void initView() {
		mUserId = this.findViewById(R.id.new_user_id);
		mUserPassword = this.findViewById(R.id.new_user_password);
		mUserSex = this.findViewById(R.id.new_user_sex);
		mUserAge = this.findViewById(R.id.new_user_age);
		mSaveBtn = this.findViewById(R.id.new_save_btn);
		mIsCheckBox = this.findViewById(R.id.checkbox_new_view);
		sIsChecked = mIsCheckBox.isChecked();
	}

	@Override
	public long addUser(User user) {
		return 0;
	}

	@Override
	public int delUserById(int id) {
		return 0;
	}

	@Override
	public int updateUser(User user) {
		return 0;
	}

	@Override
	public User getUserById(int id) {
		return null;
	}

	@Override
	public List<User> listAllUser() {
		return null;
	}
}
