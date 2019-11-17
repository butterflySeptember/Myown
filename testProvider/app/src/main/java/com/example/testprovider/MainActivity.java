package com.example.testprovider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MainActivity";

	public static final String LABLE_USER_NAME = "userName";
	public static final String LABLE_PSAAWORD = "password";
	public static final String LABLE_SEX = "sex";
	public static final String LABLE_AGE = "age";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Uri uri = Uri.parse("content://com.example.myapplicationsql/user");
		ContentResolver contentResolver = getContentResolver();
		contentResolver.registerContentObserver(uri, true, new ContentObserver(new Handler()) {
			@Override
			public void onChange(boolean selfChange) {
				super.onChange(selfChange);
				Log.d(TAG, "用户数据发生变化... ");
				//todo：获取内容之后的处理
			}
		});
	}

	/**
	 * 点击时获取内容
	 *
	 * @param view
	 */
	public void getRemoteUser(View view){
		ContentResolver contentResolver = getContentResolver();
		//验证uri
		Uri uri = Uri.parse("content://com.example.myapplicationsql/user");
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


	public void addUser(View view) {
		ContentResolver contentResolver = getContentResolver();
		Uri uri	= Uri.parse("content://com.example.myapplicationsql/user");
		ContentValues values = new ContentValues();
		values.put(LABLE_USER_NAME,"zhangsan");
		values.put(LABLE_PSAAWORD,"123456");
		values.put(LABLE_SEX,"male");
		values.put(LABLE_AGE,15);
		contentResolver.insert(uri,values);
	}
}
