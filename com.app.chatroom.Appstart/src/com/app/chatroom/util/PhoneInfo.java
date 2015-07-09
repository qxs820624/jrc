package com.app.chatroom.util;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

public class PhoneInfo {
	public static PhoneInfo instance;

	public static PhoneInfo getInstance(Context context) {
		if (instance == null)
			instance = new PhoneInfo(context);
		return instance;
	}

	/**
	 * 17 TelephonyManager提供设备上获取通讯服务信息的入口。 应用程序可以使用这个类方法确定的电信服务商和国家
	 * 以及某些类型的用户访问信息。 18 应用程序也可以注册一个监听器到电话收状态的变化。不需要直接实例化这个类 19
	 * 使用Context.getSystemService(Context.TELEPHONY_SERVICE)来获取这个类的实例。 20
	 */

	private TelephonyManager telephonyManager;
	private DisplayMetrics dm;
	/**
	 * 
	 * 国际移动用户识别码
	 */

	private String IMSI;

	private PhoneInfo(Context context) {
		telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
	}

	/**
	 * Role:获取当前设置的电话号码 <BR>
	 * Date:2012-3-12 <BR>
	 * 
	 * @return
	 */

	public String getNativePhoneNumber() {
		String NativePhoneNumber = "";
		NativePhoneNumber = telephonyManager.getLine1Number();
		return NativePhoneNumber;
	}

	/**
	 * 获取手机IMSI号
	 * 
	 * @return
	 */
	public String getIMSINumber() {
		if (telephonyManager.getSubscriberId() == null)
			return "";
		else
			return telephonyManager.getSubscriberId();
	}

	/**
	 * 获取手机IMEI号
	 * 
	 * @return
	 */
	public String getIMEINumber() {
		if (telephonyManager.getDeviceId() == null)
			return "";
		else
			return telephonyManager.getDeviceId();
	}

	/**
	 * 获取手机型号
	 * 
	 * @return
	 */
	public String getPhoneType() {
		return Build.MANUFACTURER + Build.MODEL;
	}

	/**
	 * 获取手机系统版本
	 * 
	 * @return
	 */
	public String getOS() {
		return Build.VERSION.SDK;
	}

	/**
	 * SIM卡的序列号：<br/>
	 * 需要权限：READ_PHONE_STATE
	 * 
	 * @return
	 */
	public String getSimNumber() {
		return telephonyManager.getSimSerialNumber();
	}

	/**
	 * 获取手机语言
	 * 
	 * @return
	 */
	public String getLanguage() {
		return Locale.getDefault().getLanguage();
	}

	/**
	 * 获取屏幕高
	 * 
	 * @param a
	 * @return
	 */
	public String getHight(Activity a) {
		dm = new DisplayMetrics();
		a.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return String.valueOf(dm.heightPixels);
	}

	/**
	 * 获取屏幕宽
	 * 
	 * @param a
	 * @return
	 */
	public String getWidth(Activity a) {
		dm = new DisplayMetrics();
		a.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return String.valueOf(dm.widthPixels);
	}

	/**
	 * 获取程序包名
	 * 
	 * @param a
	 * @return
	 */
	public String getPackage(Activity a) {
		PackageInfo info = null;
		try {
			info = a.getPackageManager().getPackageInfo(a.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info.packageName;
	}

	/**
	 * 获取程序版本编码
	 * 
	 * @param a
	 * @return
	 */
	public String getVersionCode(Activity a) {
		PackageInfo info = null;
		try {
			info = a.getPackageManager().getPackageInfo(a.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return String.valueOf(info.versionCode);
	}

	/**
	 * 获取程序版本号
	 * 
	 * @param a
	 * @return
	 */
	public String getVersionName(Activity a) {
		PackageInfo info = null;
		try {
			info = a.getPackageManager().getPackageInfo(a.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return String.valueOf(info.versionName);
	}

	/***
	 * 获取手机MAC地址
	 * 
	 * @param a
	 * @return
	 */
	public String getLocalMacAddress(Activity a) {
		WifiManager wifi = (WifiManager) a
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		if (info.getMacAddress() == null)
			return "";
		else
			return info.getMacAddress();
	}

	/***
	 * 获得Manifest渠道编号
	 * 
	 * @param a
	 * @return
	 */
	public String getCid(Activity a) {
		ApplicationInfo appInfo;
		String cid = "";
		try {
			appInfo = a.getPackageManager().getApplicationInfo(
					a.getPackageName(), PackageManager.GET_META_DATA);
			cid = appInfo.metaData.getString("UMENG_CHANNEL");
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (cid.equals(""))
			return "";
		else
			return cid;
	}

	/**
	 * 
	 * Role:Telecom service providers获取手机服务商信息 <BR>
	 * 需要加入权限<uses-permission
	 * android:name="android.permission.READ_PHONE_STATE"/> <BR>
	 * Date:2012-3-12 <BR>
	 */

	public String getProvidersName() {
		String ProvidersName = null;
		// 返回唯一的用户ID;就是这张卡的编号神马的
		IMSI = telephonyManager.getSubscriberId();
		// IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
		// System.out.println(IMSI);
		if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
			ProvidersName = "中国移动";
		} else if (IMSI.startsWith("46001")) {
			ProvidersName = "中国联通";
		} else if (IMSI.startsWith("46003")) {
			ProvidersName = "中国电信";
		}
		return ProvidersName; 
	}

}
