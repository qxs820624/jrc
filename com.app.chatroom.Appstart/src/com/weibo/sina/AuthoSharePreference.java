package com.weibo.sina;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 自定义SharedPreference类，用来存取Token、secret。。。
 * 
 * @author Joe Chan
 */
public class AuthoSharePreference {

	private final static String SHAREPREFERENCE_NAME = "AuthoSharePreference";

	private final static String KEY_TOKEN = "token";

	private final static String KEY_EXPIRES = "expires_in";

	private final static String KEY_REMIND = "remind_in";

	private final static String KEY_UID = "uid";
	
	private final static String KEY_SECRET ="secret";

	public AuthoSharePreference() {

	}
	
	public static boolean putToken(Context context, String token) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				SHAREPREFERENCE_NAME, 0);
		Editor editor = sharedPreferences.edit();
		editor.putString(KEY_TOKEN, token);

		return editor.commit();
	}

	public static String getToken(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				SHAREPREFERENCE_NAME, 0);
		return sharedPreferences.getString(KEY_TOKEN, "");
	}

	public static boolean putExpires(Context context, String expires) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				SHAREPREFERENCE_NAME, 0);
		Editor editor = sharedPreferences.edit();
		editor.putString(KEY_EXPIRES, expires);

		return editor.commit();
	}

	public static String getExpires(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				SHAREPREFERENCE_NAME, 0);
		return sharedPreferences.getString(KEY_EXPIRES, "");
	}

	public static boolean putRemind(Context context, String remind) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				SHAREPREFERENCE_NAME, 0);
		Editor editor = sharedPreferences.edit();
		editor.putString(KEY_REMIND, remind);

		return editor.commit();
	}

	public static String getRemind(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				SHAREPREFERENCE_NAME, 0);
		return sharedPreferences.getString(KEY_REMIND, "");
	}

	public static boolean putUid(Context context, String uid) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				SHAREPREFERENCE_NAME, 0);
		Editor editor = sharedPreferences.edit();
		editor.putString(KEY_UID, uid);

		return editor.commit();
	}

	public static String getUid(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				SHAREPREFERENCE_NAME, 0);
		return sharedPreferences.getString(KEY_UID, "");
	}
	
	public static boolean putSecret(Context context, String secret) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				SHAREPREFERENCE_NAME, 0);
		Editor editor = sharedPreferences.edit();
		editor.putString(KEY_SECRET, secret);

		return editor.commit();
	}

	public static String getSecret(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				SHAREPREFERENCE_NAME, 0);
		return sharedPreferences.getString(KEY_SECRET, "");
	}
	
}
