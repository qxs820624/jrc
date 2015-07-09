package com.app.chatroom.uppay;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;

public class UPPayUtils {

	public static final int STARTE_UPPAY = 1;				//调用UPPay请求码
	public static final String PAY_RESULT = "pay_result";	//支付结果bundle中key值
	public static final String PACKAGE_NAME = "com.unionpay.uppay";
	public static final String ACTIVITY_NAME = "com.unionpay.uppay.PayActivity";
	
	public static final String PAY_RESULT_BROADCAST = "com.unionpay.uppay.payResult";//broadcast id
    public static final String KEY_PAY_RESULT = "PayResult"; // broadcast key
	
    //pay result
	public static final String TAG_SUCCESS = "success";
    public static final String TAG_FAIL = "fail";
    public static final String TAG_CANCEL = "cancel";

	public static final String APK_FILE_NAME = "UPPayPlugin.apk";

	public static boolean checkNeedUpdate(Context context) {
		try {
			context.getPackageManager().getApplicationInfo(PACKAGE_NAME,
					PackageManager.GET_UNINSTALLED_PACKAGES);
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(PACKAGE_NAME, 0);
			int versionCode = pi.versionCode;
			// check server version code
			return false;
		} catch (NameNotFoundException e) {
			return true;
		}
	}

	public static boolean downloadPlugin(Context context) {
		return true;
	}

	private static void startActivity(Activity activity, Bundle bundle) {
		Intent startIntent = new Intent();
		startIntent.putExtras(bundle);
		startIntent.setClassName(PACKAGE_NAME, ACTIVITY_NAME);
		activity.startActivity(startIntent);
	}
	

	public static boolean checkApkExist(Context context, String packageName) {
		if (packageName == null || "".equals(packageName))
			return false;
		try {
			context.getPackageManager().getApplicationInfo(packageName,
					PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}

	/**
	 * retrieve the UPPay.apk from assets folder
	 * 
	 * @param context
	 * @param srcfileName
	 * @param desFileName
	 * @return
	 */
	public static boolean retrieveApkFromAssets(Context context,
			String srcfileName, String desFileName) {
		boolean bRet = false;
		try {
			InputStream is = context.getAssets().open(srcfileName);
			FileOutputStream fos = context.openFileOutput(desFileName,
					Context.MODE_WORLD_READABLE);
			byte[] temp = new byte[1024];
			int i = 0;
			while ((i = is.read(temp)) > 0) {
				fos.write(temp, 0, i);
			}
			fos.close();
			is.close();
			bRet = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bRet;
	}

	/**
	 * install the UPPay.apk from the context's files path
	 * 
	 * @param context
	 * @param filePath
	 */
	public static void installApp(Context context, String filePath) {
		if (filePath == null) {
			return;
		}
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file:" + filePath),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}
}
