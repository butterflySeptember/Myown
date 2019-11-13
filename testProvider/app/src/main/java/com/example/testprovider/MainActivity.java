package com.example.testprovider;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	/**
	 * 点击时获取内容
	 *
	 * @param view
	 */
	public void getRemoteUser(View view){
		ContentResolver contentResolver = getContentResolver();
		//验证uri
		Uri uri = Uri.parse("content://com.example.myapplicationsql");
		//输入查询条件
		Cursor cursor = contentResolver.query(uri, null, null, null, null);
		String[] columnNames = cursor.getColumnNames();
		for (String columnName :columnNames){
			Log.d(TAG, "getColumnNames -- >"+ columnName);
		}
		while (cursor.moveToNext()) {
			for (String columnName :columnNames){
				String value = cursor.getString(cursor.getColumnIndex(columnName));
				Log.d(TAG, " ===========================  ");
				Log.d(TAG, columnName +" ----- "+ value);
				Log.d(TAG, " ===========================  ");
			}
		}
		cursor.close();
	}
}
