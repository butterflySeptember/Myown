package com.example.new_fmredioplayer.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.new_fmredioplayer.base.BaseApplication;
import com.example.new_fmredioplayer.utils.Constants;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.ArrayList;
import java.util.List;

public class HistoryDao implements IHistoryDao{

	private final XimalayaDBHelper mHelper;
	private IHistoryDaoCallback mCallback = null;

	public HistoryDao(){
		mHelper = new XimalayaDBHelper(BaseApplication.getAppContext());
	}

	@Override
	public void setCallback(IHistoryDaoCallback callback) {
		this.mCallback = callback;
	}

	@Override
	public void addHistory(Track track) {
		SQLiteDatabase db = null;
		boolean isSuccess = false;
		try {
			db = mHelper.getWritableDatabase();
			db.beginTransaction();
			ContentValues values = new ContentValues();

			//封装数据
			values.put(Constants.HISTORY_TRACK_ID,track.getDataId());
			values.put(Constants.HISTORY_TITLE,track.getTrackTitle());
			values.put(Constants.HISTORY_PLAY_COUNT ,track.getPlayCount());
			values.put(Constants.HISTORY_DURATION ,track.getDuration());
			values.put(Constants.HISTORY_UPDATE_TIME,track.getUpdatedAt());
			values.put(Constants.HISTORY_COVER,track.getCoverUrlLarge());

			//插入数据
			db.insert(Constants.HISTORY_TB_NAME,null,values);
			db.setTransactionSuccessful();
			isSuccess = true;
		}catch (Exception e){
			e.printStackTrace();
			isSuccess = false;
		}finally {
			if (db != null) {
				db.endTransaction();
				db.close();
			}
			if (mCallback != null) {
				mCallback.onHistoryAdd(isSuccess);
			}
		}
	}

	@Override
	public void detailHistory(Track track) {
		SQLiteDatabase db = null;
		boolean isSuccess = false;
		try{
			db = mHelper.getWritableDatabase();
			db.beginTransaction();
			//删除数据
			int delete =  db.delete(Constants.HISTORY_TB_NAME, Constants.HISTORY_ID + "=?", new String[]{track.getDataId() + ""});
			db.setTransactionSuccessful();
			isSuccess = true;
		}catch (Exception e){
			e.printStackTrace();
			isSuccess = false;
		}finally {
			if (db != null) {
				db.endTransaction();
				db.close();
			}
			if (mCallback != null) {
				mCallback.onHistoryDel(isSuccess);
			}
		}
	}

	@Override
	public void clearDistory() {
		SQLiteDatabase db = null;
		boolean isSuccess = false;
		try{
			db = mHelper.getWritableDatabase();
			db.beginTransaction();
			db.delete(Constants.HISTORY_TB_NAME,null,null);
			db.setTransactionSuccessful();
			isSuccess = true;
		}catch (Exception e){
			e.printStackTrace();
			isSuccess = false;
		}finally {
			if (db != null) {
				db.endTransaction();
				db.close();
			}
			if (mCallback != null) {
				mCallback.onHistoryClear(isSuccess);
			}
		}
	}

	@Override
	public void listHistories() {
		SQLiteDatabase db = null;
		List<Track> trackList = new ArrayList<>();
		try{
			db = mHelper.getReadableDatabase();
			db.beginTransaction();
			Cursor query = db.query(Constants.HISTORY_TB_NAME, null, null, null, null, null, "trackId");
			while (query.moveToNext()) {
				Track track = new Track();
				long trackId = query.getLong(query.getColumnIndex(Constants.HISTORY_TRACK_ID));
				track.setDataId(trackId);
				//获取标题
				String title = query.getString(query.getColumnIndex(Constants.HISTORY_TITLE));
				track.setTrackTitle(title);
				//获取播放数量
				int playCount = query.getInt(query.getColumnIndex(Constants.HISTORY_PLAY_COUNT));
				track.setPlayCount(playCount);
				//获取时长
				int duration = query.getInt(query.getColumnIndex(Constants.HISTORY_DURATION));
				track.setDuration(duration);
				//获取更新事件
				long updateTime = query.getLong(query.getColumnIndex(Constants.HISTORY_UPDATE_TIME));
				track.setUpdatedAt(updateTime);
				//获取封面
				String cover = query.getString(query.getColumnIndex(Constants.HISTORY_COVER));
				track.setCoverUrlLarge(cover);
				//添加内容
				trackList.add(track);
			}
			db.close();
			db.setTransactionSuccessful();
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			if (db != null) {
				db.endTransaction();
				db.close();
			}
			if (mCallback != null) {
				mCallback.onHistoryLoaded(trackList);
			}
		}
	}
}
