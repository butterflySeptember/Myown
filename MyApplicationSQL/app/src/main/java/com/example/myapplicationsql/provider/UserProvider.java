package com.example.myapplicationsql.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;

import com.example.myapplicationsql.Untils.Contants;
import com.example.myapplicationsql.db.UserDatabaseHelper;

public class UserProvider extends ContentProvider {

	private UserDatabaseHelper mUserDatabaseHelper = null;

	private static final  int USER_MATCH_CODE = 1;
	//判断是否有查询权限
	private static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

	static {
		sUriMatcher.addURI("com.example.myapplicationsql","user",USER_MATCH_CODE);
	}

	@Override
	public boolean onCreate() {
		//初始化
		mUserDatabaseHelper = new UserDatabaseHelper(getContext());
		return false;
	}

	@Nullable
	@Override
	public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
		int match = sUriMatcher.match(uri);
		if (match == USER_MATCH_CODE){
			//获取查询权限
			SQLiteDatabase db = mUserDatabaseHelper.getReadableDatabase();
			//查询内容
			Cursor cursor = db.query(Contants.DB_NAME,projection,selection,selectionArgs,null,null,sortOrder);
			db.close();
			return cursor;
		}else {
			try {
				throw new IllegalAccessException("参数错误");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Nullable
	@Override
	public String getType(@NonNull Uri uri) {
		return null;
	}

	@Nullable
	@Override
	public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
		int result = sUriMatcher.match(uri);
		if (result == USER_MATCH_CODE){
			SQLiteDatabase db = mUserDatabaseHelper.getWritableDatabase();
			long id = db.insert(Contants.DB_NAME, null, values);
			Uri resultUri = Uri.parse("content://com.example.myapplicationsql/user/id"+id);
			//插入数据成功，数据已经变化，通知其他位置（监听
			getContext().getContentResolver().notifyChange(resultUri,null);
			return resultUri;
		}else{
			//规则不匹配
			try {
				throw new IllegalAccessException("参数错误");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
		return 0;
	}

	@Override
	public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
		return 0;
	}
}
