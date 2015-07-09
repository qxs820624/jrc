package com.weibo.joechan.util;

import android.app.Activity;
import android.view.WindowManager;
/**
 * 设置输入法状态
 @author Joe Chan
 *@date 2012-10-16
 */
public class DisplayUtil {

	public static void initWindows(Activity activity) {

		activity.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}
}
