package com.example.new_fmredioplayer.utils;

public class Constants {

	//获取的推荐列表专辑数量
	public static int RECOMMAND_COUNT = 20;

	//默认请求列表数量
	public static int COUNT_DEFAULT = 50;

	//热词的数量
	public static int COUNT_HOT_WORD = 10;

	//数据库相关的常量
	//数据库名称
	public final static String DB_NAME = "Ximalaya.db" ;
	//数据库的版本号
	public static final int DB_VERSION_CODE = 1;
	//订阅的表名
	public final static String SUB_TB_NAME = "subTB";
	public final static String SUB_TB_ID = "_id";
	public final static String SUB_TB_COVER_URL = "coverUrl";
	public final static String SUB_TB_TITLE = "title";
	public final static String SUB_TB_DESCRIPTION = "description";
	public final static String SUB_TB_PLAY_COUNT = "playCount";
	public final static String SUB_TB_TRACK_COUNT = "trackCount";
	public final static String SUB_TB_AUTHOR_NAME = "authorName";
	public final static String SUB_TB_ALBUM = "album";
	//订阅最多个数
	public static final int MAX_SUB_COUNT = 100;
	//历史纪录的表名
	public final static String HISTORY_TB_NAME = "historyTB";
	public final static String HISTORY_ID = "_id";
	public final static String HISTORY_TRACK_ID = "trackId";
	public final static String HISTORY_TITLE = "historyTitle";
	public final static String HISTORY_PLAY_COUNT = "playCount";
	public final static String HISTORY_DURATION = "historyDuration";
	public final static String HISTORY_UPDATE_TIME = "historyUpdateTime";
	public final static String HISTORY_COVER = "historyCover";

}
