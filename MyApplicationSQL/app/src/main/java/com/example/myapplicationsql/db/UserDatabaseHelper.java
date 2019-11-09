package com.example.myapplicationsql.db;

import android.content.Context;
import android.support.annotation.Nullable;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myapplicationsql.Untils.Contants;

public class UserDatabaseHelper extends SQLiteOpenHelper {


	private static final String TAG = "UserDatabaseHelper";

	public UserDatabaseHelper(@Nullable Context context) {
		super(context, Contants.DB_NAME,null,Contants.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG,"SQLiteDatabase onCreate ...");
		//创建数据库
		String createSql = "create table user(_id integer primary key autoincrement,userName varchar(30),/" +
				"password varchar(30),sex varchar(5),age integer";
		db.execSQL(createSql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//数据升级
	}
}
