package com.example.new_fmredioplayer.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.new_fmredioplayer.base.baseApplication;
import com.example.new_fmredioplayer.utils.Constants;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.Announcer;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionData implements ISubDao {

	private static final SubscriptionData ourInstance = new SubscriptionData();
	private final XimalayaDBHelper mXimalayaDBHelper;

	public static SubscriptionData getInstance() {
		return ourInstance;
	}

	private SubscriptionData() {
		mXimalayaDBHelper = new XimalayaDBHelper(baseApplication.getAppContext());

	}

	@Override
	public void addAlbum(Album album) {
		SQLiteDatabase db = null;
		try{
			db = mXimalayaDBHelper.getWritableDatabase();
			db.beginTransaction();
			ContentValues contentValues = new ContentValues();
			//封装数据
			contentValues.put(Constants.SUB_TB_COVER_URL,album.getCoverUrlLarge());
			contentValues.put(Constants.SUB_TB_TITLE,album.getAlbumTitle());
			contentValues.put(Constants.SUB_TB_DESCRIPTION,album.getAlbumIntro());
			contentValues.put(Constants.SUB_TB_TRACK_COUNT,album.getIncludeTrackCount());
			contentValues.put(Constants.SUB_TB_PLAY_COUNT,album.getPlayCount());
			contentValues.put(Constants.SUB_TB_AUTHOR_NAME,album.getAnnouncer().getNickname());
			contentValues.put(Constants.SUB_TB_ID,album.getId());
			//插入数据
			db.insert(Constants.SUB_TB_NAME,null,contentValues);
			db.setTransactionSuccessful();
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			if (db != null) {
				db.endTransaction();
				db.close();
			}
		}

	}

	@Override
	public void delAlbum(Album album) {
		SQLiteDatabase db = null;
		try{
			db = mXimalayaDBHelper.getWritableDatabase();
			db.beginTransaction();
			int delete =  db.delete(Constants.SUB_TB_NAME, Constants.SUB_TB_ID + "=?", new String[]{album.getId() + ""});
			db.setTransactionSuccessful();
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			if (db != null) {
				db.endTransaction();
				db.close();
			}
		}
	}

	@Override
	public void listAlbum() {
		SQLiteDatabase db = null;
		List<Album> result = new ArrayList<>();
		try{
			db = mXimalayaDBHelper.getReadableDatabase();
			Cursor query = db.query(Constants.DB_NAME, null, null, null, null, null, null);
			//封装数据
			while (query.moveToNext()) {
				Album album = new Album();
				//获取图片封面
				String coverUrl = query.getString(query.getColumnIndex(Constants.SUB_TB_COVER_URL));
				album.setCoverUrlLarge(coverUrl);
				//获取标题
				String title = query.getString(query.getColumnIndex(Constants.SUB_TB_TITLE));
				album.setAlbumTitle(title);
				//获取内容
				String description = query.getString(query.getColumnIndex(Constants.SUB_TB_DESCRIPTION));
				album.setAlbumIntro(description);
				//获取播放和节目数量
				long trackCount = query.getLong(query.getColumnIndex(Constants.SUB_TB_TRACK_COUNT));
				album.setIncludeTrackCount(trackCount);
				long playCount = query.getLong(query.getColumnIndex(Constants.SUB_TB_PLAY_COUNT));
				album.setPlayCount(playCount);
				//获取作者名
				String name = query.getString(query.getColumnIndex(Constants.SUB_TB_NAME));
				Announcer announcer = new Announcer();
				announcer.setNickname(name);
				album.setAnnouncer(announcer);
				//获取id
				long albumId = query.getLong(query.getColumnIndex(Constants.SUB_TB_ID));
				album.setId(albumId);
				result.add(album);
			}
			//todo：把数据返回给ui
			query.close();
			db.setTransactionSuccessful();
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			if (db != null) {
				db.endTransaction();
				db.close();
			}
		}
	}
}
