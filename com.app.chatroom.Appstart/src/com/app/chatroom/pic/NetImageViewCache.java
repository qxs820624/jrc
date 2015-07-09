package com.app.chatroom.pic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Set;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

import com.app.chatroom.util.Commond;
import com.app.chatroom.util.SystemUtil;

public class NetImageViewCache extends HashMap<String, Bitmap> {

	private static NetImageViewCache mNetImageViewCache;

	private NetImageViewCache() {
	}

	public static NetImageViewCache getInstance() {
		if (mNetImageViewCache == null)
			synchronized (NetImageViewCache.class) {
				if (mNetImageViewCache == null)
					mNetImageViewCache = new NetImageViewCache();
			}
		return mNetImageViewCache;
	}

	public void clear() {
		Set<String> keys = this.keySet();
		for (String key : keys) {
			Bitmap bmp = this.get(key);
			if (bmp != null && bmp.isRecycled()) {
				bmp.recycle();
				bmp = null;
				this.remove(key);
			}
		}
	}

	/**
	 * 判断图片是否存在首先判断内存中是否存在然后判断本地是否存在
	 * 
	 * @param url
	 * @return
	 */
	public Bitmap getBitmap(String url, String savePath, String savePath2) {
		boolean isExit = containsKey(url);
		// boolean isExit = false;
		if (isExit) {
			return get(url);
		}
		/** 将本地图片缓存到内存中 **/
		String name = Commond.getMd5Hash(url);
		String filePath = isCacheFileIsExit(savePath, savePath2);
		File file = new File(filePath, name);
		// System.out.println(file.getPath());
		if (file.exists()) {
			// isExit = cacheBmpToMemory(file, url);
			Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
			if (bitmap != null) {
				// System.out.println("put url:" + url + " path:"
				// + file.getAbsolutePath());
				this.put(url, bitmap, false);
				return bitmap;
			}
		}
		return null;
	}

	/*
	 * 将本地图片缓存到内存中
	 */
	private boolean cacheBmpToMemory(File file, String url) {
		boolean sucessed = true;
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			sucessed = false;
			return false;
		}
		byte[] bs = getBytesFromStream(inputStream);
		Bitmap bitmap = BitmapFactory.decodeByteArray(bs, 0, bs.length);
		if (bitmap == null) {
			return false;
		}
		this.put(url, bitmap, false);
		return sucessed;
	}

	private byte[] getBytesFromStream(InputStream inputStream) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int len = 0;
		while (len != -1) {
			try {
				len = inputStream.read(b);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (len != -1) {
				baos.write(b, 0, len);
			}
		}

		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return baos.toByteArray();
	}

	/*
	 * 判断缓存文件夹是否存在如果存在怎返回文件夹路径，如果不存在新建文件夹并返回文件夹路径
	 */
	public String isCacheFileIsExit(String savePath, String savePath2) {
		String filePath = "";

		if (SystemUtil.getSDCardMount()) {
			filePath = savePath;
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
		} else {
			filePath = savePath2;
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
		}

		return filePath;
	}

	/*
	 * 将url变成图片的地址
	 */
	private String changeUrlToName(String url) {
		String name = url.replaceAll(":", "_");
		name = name.replaceAll("//", "_");
		name = name.replaceAll("/", "_");
		name = name.replaceAll("=", "_");
		name = name.replaceAll(",", "_");
		name = name.replaceAll("&", "_");
		return name;
	}

	private Bitmap put(String key, Bitmap value, String savePath,
			String savePath2) {
		String filePath = isCacheFileIsExit(savePath, savePath2);
		String name = Commond.getMd5Hash(key);
		File file = new File(filePath, name);
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		value.compress(CompressFormat.JPEG, 100, outputStream);
		try {
			outputStream.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (null != outputStream) {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			outputStream = null;
		}

		return super.put(key, value);
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @param isCacheToLocal
	 *            是否缓存到本地
	 * @return
	 */
	public Bitmap put(String key, Bitmap value, boolean isCacheToLocal) {
		// System.out.println("put key:" + key);
		if (isCacheToLocal) {
			return super.put(key, value);
		} else {
			return super.put(key, value);
		}
	}
}
