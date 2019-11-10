package com.example.myapplicationsql.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.telephony.PhoneNumberUtils;

import com.example.myapplicationsql.Untils.Contants;
import com.example.myapplicationsql.db.UserDatabaseHelper;
import com.example.myapplicationsql.pojo.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpI implements IUserDao  {

	private final UserDatabaseHelper mUserDatabaseHelper;

	public UserDaoImpI(Context context){
		mUserDatabaseHelper = new UserDatabaseHelper(context);
	}

	@Override
	public long addUser(User user) {
		SQLiteDatabase db = mUserDatabaseHelper.getWritableDatabase();
		ContentValues value = userToValues(user);
		//判断输入是否正确
		long result = db.insert(Contants.DB_NAME,null,value);
		db.close();
		return result;
	}


	@Override
	public int delUserById(int id) {
		SQLiteDatabase db = mUserDatabaseHelper.getWritableDatabase();
		int deleteResult = db.delete(Contants.DB_NAME, Contants.LABLE_ID, new String[]{id + ""});
		db.close();
		return deleteResult;
	}

	@Override
	public int updateUser(User user) {
		SQLiteDatabase db = mUserDatabaseHelper.getWritableDatabase();
		//根据id来更新内容
		ContentValues value = userToValues(user);
		int updateResult = db.update(Contants.DB_NAME, value, Contants.LABLE_ID, new String[]{user.get_id() + ""});
		db.close();
		return updateResult;
	}

	@Override
	public User getUserById(int id) {
		SQLiteDatabase db = mUserDatabaseHelper.getReadableDatabase();
		String sqlValue = "select * from " + Contants.LABLE_NAME + " where " + Contants.LABLE_ID + "= ?";
		Cursor cursor = db.rawQuery(sqlValue, new String[]{id + ""});
		User user = null;
		if (cursor.moveToNext()) {
			user = cursorToUser(cursor);
		}
		db.close();
		return user;
	}


	@Override
	public List<User> listAllUser() {
		SQLiteDatabase db = mUserDatabaseHelper.getReadableDatabase();
		Cursor cursor = db.query(Contants.DB_NAME, null, null, null, null, null, null);
		//创建数据链表
		List<User> userList = new ArrayList<>();
		while (cursor.moveToNext()){
			User user = cursorToUser(cursor);
			userList.add(user);
		}
		db.close();
		return userList;
	}

	@NotNull
	private ContentValues userToValues(User user) {
		ContentValues value = new ContentValues();
		//添加对应的数据信息
		value.put(Contants.LABLE_NAME,user.getUserName());
		value.put(Contants.LABLE_PSAAWORD,user.getPassword());
		value.put(Contants.LABLE_AGE,user.getAge());
		value.put(Contants.LABLE_SEX,user.getSex());
		return value;
	}

	private User cursorToUser(Cursor cursor) {
		//从数据库中读取数据
		User user = new User();
		int userId = cursor.getInt(cursor.getColumnIndex(Contants.LABLE_ID));
		user.set_id(userId);
		String userName = cursor.getString(cursor.getColumnIndex(Contants.LABLE_USER_NAME));
		user.setUserName(userName);
		String userPassword = cursor.getString(cursor.getColumnIndex(Contants.LABLE_PSAAWORD));
		user.setPassword(userPassword);
		String userSex = cursor.getString(cursor.getColumnIndex(Contants.LABLE_SEX));
		user.setSex(userSex);
		int userAge = cursor.getInt(cursor.getColumnIndex(Contants.LABLE_AGE));
		user.setAge(userAge);
		return user;
	}
}
