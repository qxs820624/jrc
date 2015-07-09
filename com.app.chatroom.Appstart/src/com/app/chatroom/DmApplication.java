package com.app.chatroom;

import android.app.Application;

import com.app.chatroom.expressionutil.SmileyParser;

public class DmApplication extends Application {
	public static String VERSION = "1.0";

	private static DmApplication instance;

	public static DmApplication getInstance() {
		return instance;
	}

	public void onCreate() {
		super.onCreate();
		instance = this;
		// 初始化表情解析器（将文本解析为表情图标显示，如[微笑] 显示微笑图标）
		SmileyParser.init(getBaseContext());
	}

	public void onExit() {
		// ThemeManager.getInstance().release();
		// UserManager.getInstance().release();
	}
}
