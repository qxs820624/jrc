package com.duom.fjz.iteminfo;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class WebViewCacheAdapter {

	//
	private static final String DATABASE_NAME = "webviewCache.db";
	private static final String DATABASE_TABLE_CACHE = "cache";
	private static final int DATABASE_VERSION = 5;// 5:版本是htc手机，1其它手机都可以

	//
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public WebViewCacheAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}
	}

	/**
	 * 打开数据库
	 * 
	 * @return
	 * @throws SQLException
	 */

	public boolean open() {
		try {
			db = DBHelper.getReadableDatabase();
			if (db.isDbLockedByOtherThreads())
				System.out.println("isDbLockedByOtherThreads");
			if (db.isDbLockedByCurrentThread())
				System.out.println("isDbLockedByCurrentThread");
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 关闭数据库
	 */

	public void close() {
		DBHelper.close();
	}

	/**
	 * 根据url获取到webview缓存的图片路径
	 * 
	 * @return
	 */
	public String getUrlToFileName(String src) {
		String filepath = "";
		try {
			if (!open())
				return "";
			Cursor c = db.query(true, DATABASE_TABLE_CACHE,
					new String[] { "filepath" }, "url='" + src + "'", null,
					null, null, null, null);
			if (c != null) {
				if (c.moveToFirst())
					filepath = c.getString(0);
				c.close();
			}
			close();
		} catch (SQLiteException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return filepath;
	}

}
