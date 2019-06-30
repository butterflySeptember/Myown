package com.example.sharedpreferencesactivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class SDCardActivity extends Activity implements View.OnClickListener {

	private Button mWriteBtn;
	private Button mCheckBtn;
	private final static String TAG = "SDCardActivity";
	private Button mMSDFreeBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置布局
		setContentView(R.layout.activity_sd_card);
		initView();
		initEvent();
	}

	private void initEvent() {
		mWriteBtn.setOnClickListener(this);
		mCheckBtn.setOnClickListener(this);
		mMSDFreeBtn.setOnClickListener(this);
	}


	private void initView() {
		mWriteBtn = this.findViewById(R.id.se_write_btn);
		mCheckBtn = this.findViewById(R.id.sd_check_btn);
		mMSDFreeBtn = this.findViewById(R.id.sd_free_btn);
	}


	@Override
	public void onClick(View view) {
		final File exFile = Environment.getExternalStorageDirectory();
		Log.d(TAG, "Ex file path == " + exFile);
		if (view == mWriteBtn) {
			File file = new File(exFile,"info.txt");
			try {
				FileOutputStream fos = new FileOutputStream(file);
				fos.write("这是保存的内容".getBytes());
				fos.close();
				} catch (Exception e) {
				e.printStackTrace();
				}
		}else if (view == mCheckBtn){
			//判断SD卡是否已经挂载
			String state = Environment.getExternalStorageState();
			if (state.equals(Environment.MEDIA_MOUNTED)) {
				Log.d(TAG, "SD卡已挂载 ...");
			}else if(state.equals(Environment.MEDIA_REMOVED)){
				Log.d(TAG, "SD卡已删除 ...");
			}
		}else if(view == mMSDFreeBtn){
			long freeSpace = exFile.getFreeSpace();
			//把long类型进行转换
			String sizeText = Formatter.formatFileSize(this, freeSpace);
			Log.d(TAG, "free size == " + sizeText);
		}
	}
}
