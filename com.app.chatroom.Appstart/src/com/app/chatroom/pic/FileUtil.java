package com.app.chatroom.pic;

import java.io.File;
import java.io.IOException;

import android.os.Environment;
import android.util.Log;

import com.app.chatroom.util.Commond;
import com.app.chatroom.util.SystemUtil;

public class FileUtil {
	private static final String TAG = "FileUtil";

	/**
	 * 保存图片
	 * 
	 * @param imageUri
	 *            图片链接
	 * @param savepath
	 *            SD卡路径
	 * @param progectpath
	 *            项目路径 "/data/data"+getPackageName()+"/images"
	 * @return
	 */
	public static File getCacheFile(String imageUri, String savepath,
			String progectpath) {
		File cacheFile = null;
		try {
			if (SystemUtil.getSDCardMount()) {
				File sdCardDir = Environment.getExternalStorageDirectory();
				String fileName = Commond.getMd5Hash(imageUri);
				File dir = new File(sdCardDir.getCanonicalPath()
						+ File.separator + savepath);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				cacheFile = new File(dir, fileName);
				// Log.i(TAG, "exists:" + cacheFile.exists() + ",dir:" + dir
				//		+ ",URL:" + getFileName(imageUri) + ",file:" + fileName);
			} else {

				String fileName = Commond.getMd5Hash(imageUri);
				File dir = new File(progectpath);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				cacheFile = new File(dir, fileName);
				// Log.i(TAG, "exists:" + cacheFile.exists() + ",dir:" + dir
				// + ",URL:" + getFileName(imageUri) + ",file:" + fileName);
			}

		} catch (IOException e) {
			e.printStackTrace();
			// Log.e(TAG, "getCacheFileError:" + e.getMessage());
		}

		return cacheFile;

	}

	public static String getFileName(String path) {
		int index = path.lastIndexOf("/");
		return path.substring(index + 1);
	}

}
