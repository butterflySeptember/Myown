package com.example.sharedpreferencesactivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class PreferenceActivity extends Activity implements CompoundButton.OnCheckedChangeListener {

	private Switch mUnknowSwitchBtn;
	private SharedPreferences mSettingInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preference);
		init();
		initEvent();
		//获得SharedPreferences
		mSettingInfo = this.getSharedPreferences("setting_info", MODE_PRIVATE);
	}

	@Override
	protected void onResume() {
		super.onResume();
		//回显保存的结果
		boolean state = mSettingInfo.getBoolean("State",false);
		mUnknowSwitchBtn.setChecked(state);
	}

	private void initEvent() {
		mUnknowSwitchBtn.setOnCheckedChangeListener(this);
	}

	private void init() {
		mUnknowSwitchBtn = this.findViewById(R.id.unknow_switch_btn);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		//对改变的数据进行保存
		//进入编辑模式，获得编辑器
		SharedPreferences.Editor editor = mSettingInfo.edit();
		//保存数据（可以选择类型）
		editor.putBoolean("State",isChecked);
		//提交编辑器
		editor.commit();
	}
}
