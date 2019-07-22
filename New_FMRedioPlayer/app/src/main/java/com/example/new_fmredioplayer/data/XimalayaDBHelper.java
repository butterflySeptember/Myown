package com.example.new_fmredioplayer.data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.new_fmredioplayer.utils.Constants;
import com.example.new_fmredioplayer.utils.LogUtils;

public class XimalayaDBHelper extends SQLiteOpenHelper {

	private final static String TAG = "XimalayaDBHelper";

	public XimalayaDBHelper(Context context) {
		//name 数据库的名字，factory 游标工厂 ，version 数据库版本号
		super(context, Constants.DB_NAME , null, Constants.DB_VERSION_CODE);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		LogUtils.d(TAG,"onCreate ...");
		//创建数据表
		//订阅的相关字段
		String subTbSql = "create table " + Constants.SUB_TB_NAME + "(" +
				Constants.SUB_TB_ID + " integer primary key autoincrement,"+
				Constants.SUB_TB_COVER_URL + " varchar," +
				Constants.SUB_TB_TITLE + " varchar," +
				Constants.SUB_TB_DESCRIPTION + " varchar," +
				Constants.SUB_TB_PLAY_COUNT + " integer," +
				Constants.SUB_TB_TRACK_COUNT + " integer," +
				Constants.SUB_TB_AUTHOR_NAME + " varchar," +
				Constants.SUB_TB_ALBUM + " integer" +
				")";
		db.execSQL(subTbSql);

		//创建历史纪录表
		String historyTbSql = "create table " + Constants.HISTORY_TB_NAME + "(" +
				Constants.HISTORY_ID + " integer primary key autoincrement,"+
				Constants.HISTORY_TRACK_ID + " integer," +
				Constants.HISTORY_TITLE + " varchar," +
				Constants.HISTORY_COVER + " varchar," +
				Constants.HISTORY_PLAY_COUNT + " integer," +
				Constants.HISTORY_UPDATE_TIME + " integer," +
				Constants.HISTORY_DURATION + " integer" +
				")";
		db.execSQL(historyTbSql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
