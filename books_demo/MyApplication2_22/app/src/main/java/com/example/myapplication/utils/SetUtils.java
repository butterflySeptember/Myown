package com.example.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SetUtils {

	public  static void saveId(Context context, String UserId) {
		SharedPreferences sp = context.getSharedPreferences("Id", Context.MODE_PRIVATE);
		sp.edit().putString("Id", UserId).commit();
	}
	public static String GetId(Context context) {
		SharedPreferences sp = context.getSharedPreferences("Id", Context.MODE_PRIVATE);
		return sp.getString("Id",null);
	}

	public  static void saveUserName(Context context, String UserName) {
		SharedPreferences sp = context.getSharedPreferences("UserName", Context.MODE_PRIVATE);
		sp.edit().putString("UserName", UserName).commit();
	}
	public static String GetUserName(Context context) {
		SharedPreferences sp = context.getSharedPreferences("UserName", Context.MODE_PRIVATE);
		return sp.getString("UserName",null);
	}

	/**
	 * @param context
	 * @param IsLogin
	 */
	public  static void saveIsLogin(Context context, boolean IsLogin) {
		SharedPreferences sp = context.getSharedPreferences("IsLogin", Context.MODE_PRIVATE);
		sp.edit().putBoolean("IsLogin", IsLogin).commit();
	}
	public static boolean IsLogin(Context context) {
		SharedPreferences sp = context.getSharedPreferences("IsLogin", Context.MODE_PRIVATE);
		boolean IsLogin = sp.getBoolean("IsLogin", false);
		return IsLogin;
	}

	/**
	 * @param context
	 * @param Manager
	 */
	public  static void saveIsManager(Context context, boolean Manager) {
		SharedPreferences sp = context.getSharedPreferences("Manager", Context.MODE_PRIVATE);
		sp.edit().putBoolean("Manager", Manager).commit();
	}
	public static boolean IsManager(Context context) {
		SharedPreferences sp = context.getSharedPreferences("Manager", Context.MODE_PRIVATE);
		boolean Manager = sp.getBoolean("Manager", false);
		return Manager;
	}
}
