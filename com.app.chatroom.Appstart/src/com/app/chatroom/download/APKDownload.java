package com.app.chatroom.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.jianrencun.chatroom.R;

public class APKDownload {
	public static ArrayList<String> appList = new ArrayList<String>();
	public static NotificationManager updateNotificationManager = null;
	public static boolean isAutoInstall = true;

	/**
	 * 
	 * @param title
	 * @param key
	 * @param size
	 * @param pkg
	 * @param thumburl
	 * @param filepath
	 */
	public static boolean startDownThread(Context context, String title,
			String url, long len) {
		if (TextUtils.isEmpty(url))
			return false;
		//
		if (updateNotificationManager == null)
			updateNotificationManager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
		// 获取传值
		// //@////@System.out.println("size:" + size);
		// //@////@System.out.println("url:" + key);
		if (appList.contains(url)) {
			Toast.makeText(context, title + ":已在下载中！", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		Toast.makeText(context, title + ":开始下载！", Toast.LENGTH_SHORT).show();
		//
		Notification updateNotification = new Notification();
		// entity.updateNotification.flags |= Notification.FLAG_ONGOING_EVENT;
		// 设置下载过程中，点击通知栏，回到主界面
		// 通知栏跳转Intent
		// 设置通知栏显示内容
		RemoteViews contentView = new RemoteViews(context.getPackageName(),
				R.layout.app_market_notify);
		contentView.setTextViewText(R.id.notificationTitle, title + ":开始下载");
		contentView.setTextViewText(R.id.notificationPercent, "1%");
		contentView.setProgressBar(R.id.notificationProgress, 100, 1, false);
		updateNotification.contentView = contentView;

		updateNotification.icon = android.R.drawable.stat_sys_download;
		updateNotification.tickerText = title + ":开始下载";
		// ////////////////////////
		Intent updateIntent = new Intent();
		// updateIntent.setAction("cn.duome.marketmanager");
		// updateIntent = new Intent(context, BaseActivityGroup.class);
		updateIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
				| Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
		updateNotification.contentIntent = PendingIntent.getActivity(context,
				0, updateIntent, 0);
		// entity.updateNotification.setLatestEventInfo(UpdateService.this,
		// entity.title, "0%", entity.updatePendingIntent);
		//
		appList.add(url);

		// 发出通知
		int id = appList.size();
		updateNotificationManager.notify(id, updateNotification);
		DownRunnable runnable = new DownRunnable(context, url,
				updateNotification, id, len);
		Thread thread = new Thread(runnable);
		thread.start();
		return true;
	}

	/**
	 * 
	 * @author sunxml
	 * 
	 */
	public static class DownRunnable implements Runnable {
		String mUrl = null;
		Notification mUpdateNotification;
		int mUpdateNotificationId;
		Context mContext = null;
		long totalSize = 0;

		public DownRunnable(Context context, String url,
				Notification updateNotification, int notificationId, long len) {
			mUrl = url;
			mContext = context;
			mUpdateNotification = updateNotification;
			mUpdateNotificationId = notificationId;
			totalSize = len;
		}

		/**
		 * 
		 */
		public void run() {
			long currentSize = 0;
			HttpURLConnection httpConnection = null;
			InputStream is = null;
			FileOutputStream fos = null;
			try {
				//

				// ///////////////////////////////////////////////
				// /获取下载文件大小-开始
				// //////////////////////////////////////////////
				if (totalSize == 0) {
					String fileSizeUrl = getMeataStringData(mContext,
							"fileSizeUrl");
					String urlStr = fileSizeUrl
							+ URLEncoder.encode(mUrl, "utf-8");
					URL l = new URL(urlStr);
					HttpURLConnection c = (HttpURLConnection) l
							.openConnection();
					if (HttpURLConnection.HTTP_OK == c.getResponseCode()) {
						InputStream s = c.getInputStream();
						byte[] b = new byte[20];
						int len = s.read(b);
						s.close();
						String ll = new String(b, 0, len);
						totalSize = Long.parseLong(ll);
					}
					if (c != null) {
						c.disconnect();
					}
				}
				// ///////////////////////////////////////////////
				// /获取下载文件大小-结束
				// //////////////////////////////////////////////
				URL url = new URL(mUrl);
				httpConnection = (HttpURLConnection) url.openConnection();
				httpConnection.setConnectTimeout(200000);
				httpConnection.setReadTimeout(200000);
				httpConnection.setRequestProperty("Connection", "Keep-Alive");
				//
				int ret = httpConnection.getResponseCode();
				// Map<String, List<String>> headers = c.getHeaderFields();
				// for (String name : headers.keySet()) {
				// if (name != null)
				// //@////@System.out.println("name:" + name);
				// }
				String ap0FileName = getCachePath(mContext, null)
						+ getMd5Hash(mUrl) + ".ap0";
				if (ret == HttpURLConnection.HTTP_OK
						|| ret == HttpURLConnection.HTTP_PARTIAL) {
					is = httpConnection.getInputStream();
					// 206断点续传
					fos = new FileOutputStream(ap0FileName);
					int bufflen = (int) totalSize / 10;
					byte buffer[] = new byte[bufflen];
					int readsize = 0;
					// ///////////////////////////////////////
					// 初始化消息栏的百分比
					// ///////////////////////////////////////
					int progress = (int) (currentSize * 100 / totalSize);
					progress = progress >= 100 ? 100 : progress;
					if (progress > 0) {
						mUpdateNotification.contentView.setTextViewText(
								R.id.notificationPercent, progress + "%");
						mUpdateNotification.contentView
								.setProgressBar(R.id.notificationProgress, 100,
										progress, false);
						updateNotificationManager.notify(mUpdateNotificationId,
								mUpdateNotification);
					}
					// ///////////////////////////////////////
					// ///////////////////////////////////////
					// ///////////////////////////////////////
					while ((readsize = is.read(buffer)) > 0) {
						fos.write(buffer, 0, readsize);
						currentSize += readsize;

						progress = (int) (currentSize * 100 / totalSize);
						if (progress <= 100) {
							progress = progress >= 100 ? 100 : progress;
							// 为了防止频繁的通知导致应用吃紧，百分比增加10才通知一次
							if ((progress % 5) == 0) {
								mUpdateNotification.contentView
										.setTextViewText(
												R.id.notificationPercent,
												progress + "%");
								mUpdateNotification.contentView.setProgressBar(
										R.id.notificationProgress, 100,
										progress, false);
								updateNotificationManager.notify(
										mUpdateNotificationId,
										mUpdateNotification);
							}
						}
					}
					// 重命名已经下载完成的文件
					String destFileName = ap0FileName.replace(".ap0", ".apk");
					if (renameFile(ap0FileName, destFileName)) {
						installApk(mContext, destFileName);
					}
					buffer = null;
				} else {
				}
			} catch (Exception ex) {
				if (ex != null)
					ex.printStackTrace();
			} finally {
				updateNotificationManager.cancel(mUpdateNotificationId);
				if (httpConnection != null) {
					httpConnection.disconnect();
				}

				try {
					if (is != null)
						is.close();
					if (fos != null)
						fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		/**
	 * 
	 */
		public static boolean installApk(final Context context, String filename) {
			if (TextUtils.isEmpty(filename))
				return false;
			int result = Settings.Secure.getInt(context.getContentResolver(),
					Settings.Secure.INSTALL_NON_MARKET_APPS, 0);
			if (result == 0) {
				//
				Toast.makeText(context, "请选中“允许安装非电子市场的应用程序选线”再试！",
						Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
				intent.setAction(Settings.ACTION_APPLICATION_SETTINGS);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
				//
				// CheckInstallDialog cidg = new CheckInstallDialog(context,
				// "提示",
				// "请选中“允许安装非电子市场的应用程序选线”再试！");
				// cidg.show();
			} else {
				if (!filename.contains("/sdcard")) {
					String oldname = filename;
					filename = "sharetemp_" + System.currentTimeMillis();
					try {
						FileInputStream fis = new FileInputStream(oldname);
						FileOutputStream fos = context.openFileOutput(filename,
								Context.MODE_WORLD_READABLE);
						filename = context.getFilesDir() + "/" + filename;
						byte[] buffer = new byte[1024];
						int len = 0;
						int count = 0;
						while ((len = fis.read(buffer)) != -1) {
							count += len;
							fos.write(buffer, 0, len);
						}
						buffer = null;
						fis.close();
						fis = null;
						fos.flush();
						fos.close();
						fos = null;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return false;
					}
				}
				// //@////@System.out.println("filename:" + filename);
				//
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.parse("file://" + filename),
						"application/vnd.android.package-archive");
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
				//
				// intent.setDataAndType(Uri.fromFile(f),
				// "application/vnd.android.package-archive");
				context.startActivity(intent);
				return true;
			}
			return false;
		}
	}

	/**
	 * 重命名文件名。
	 * 
	 * @param srcFilePath
	 *            旧文件名
	 * @param destFilePath
	 *            新文件名
	 * @return 成功或失败
	 * @since 0.4
	 */
	public static boolean renameFile(String srcFilePath, String destFilePath) {
		if (copyFile(srcFilePath, destFilePath) != null) {
			File srcfile = new File(srcFilePath);
			srcfile.delete();
			return true;
		}
		return false;
	}

	/**
	 * 复制文件到新地址
	 * 
	 * @param srcFileName
	 * @param destFileName
	 * @return
	 * @throws Exception
	 */
	public static File copyFile(String srcFileName, String destFileName) {
		try {
			File srcfile = new File(srcFileName);
			if (!srcfile.exists())
				return null;
			File descfile = new File(destFileName);
			if (!descfile.exists())
				descfile.createNewFile();
			int length = 2097152;
			FileInputStream in = new FileInputStream(srcfile);
			FileOutputStream out = new FileOutputStream(descfile);
			FileChannel inC = in.getChannel();
			FileChannel outC = out.getChannel();
			while (true) {
				if (inC.position() == inC.size()) {
					inC.close();
					outC.close();
					return descfile;
				}
				if ((inC.size() - inC.position()) < 20971520)
					length = (int) (inC.size() - inC.position());
				else
					length = 20971520;
				inC.transferTo(inC.position(), length, outC);
				inC.position(inC.position() + length);
			}
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	public static String getMeataStringData(Context context, String name) {
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			Bundle bundle = ai.metaData;
			return bundle.getString(name);
		} catch (Exception e) {

		}
		return "";
	}

	/**
	 * 
	 * @param context
	 * @param dir
	 * @return
	 */
	public static String getCachePath(Context context, String dir) {
		String path = null;
		if (TextUtils.isEmpty(dir))
			dir = context.getPackageName();
		String tmp = android.os.Environment.getExternalStorageState();
		if (android.os.Environment.MEDIA_MOUNTED.equals(tmp)/*
															 * || android.os.
															 * Environment
															 * .MEDIA_SHARED
															 * .equals(tmp)
															 */) {
			// path = "/sdcard/" + dir + "/";
			path = Environment.getExternalStorageDirectory().getPath() + "/"
					+ dir + "/";
			File file = new File(path);
			if (!file.exists()) {
				if (!file.mkdir())
					path = null;
			}
			file = null;
			//
		}
		if (TextUtils.isEmpty(path)) {
			path = context.getCacheDir().getAbsolutePath() + File.separator;
		}
		// @////@System.out.println("getCachePath:" + path);
		return path;
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	public static String getMd5Hash(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String md5 = number.toString(16);
			//
			// while (md5.length() < 32)
			// md5 = "0" + md5;
			return md5;
		} catch (NoSuchAlgorithmException e) {
			// Log.e("MD5", e.getMessage());
			return null;
		}
	}
}