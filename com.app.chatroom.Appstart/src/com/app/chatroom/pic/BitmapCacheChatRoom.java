package com.app.chatroom.pic;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Set;
import java.util.WeakHashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.text.TextUtils;

public class BitmapCacheChatRoom {
	public WeakHashMap<String, WeakReference<Bitmap>> bmpCacheMap = new WeakHashMap<String, WeakReference<Bitmap>>();

	static BitmapCacheChatRoom intance = null;

	public static BitmapCacheChatRoom getIntance() {
		if (intance == null)
			intance = new BitmapCacheChatRoom();
		return intance;
	}

	/**
	 * 
	 * @param url
	 */
	public void clearCacheBitmap() {
		Set<String> keys = bmpCacheMap.keySet();
		while (keys.iterator().hasNext()) {
			clearCacheBitmap(keys.iterator().next());
		}
	}

	/**
	 * 
	 * @param url
	 */
	public void clearCacheBitmap(String url) {
		WeakReference<Bitmap> bitmapRef = bmpCacheMap.get(url);
		if (bitmapRef != null) {
			Bitmap bitmap = bitmapRef.get();
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
				bitmap = null;
			}
		}
		bmpCacheMap.remove(url);
	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	public Bitmap getCacheBitmap(String filename, int width, int height) {
		Bitmap bmp = null;
		WeakReference<Bitmap> drawableRef = bmpCacheMap.get(filename);
		if (drawableRef != null) {
			bmp = drawableRef.get();
			if (bmp == null || bmp.isRecycled()) {
				bmpCacheMap.remove(filename);
				bmp = null;
			}
		}
		if (bmp == null)
			bmp = getBitmap(filename);
		if (height > 0 && width > 0) {
			// bmp = getBitmap(url, width, height);
			// sdk2.2之后才有
			// bmp = ThumbnailUtils.extractThumbnail(bmp, width, height);
			Bitmap tmpBmp = bmp;
			bmp = resizeImage(bmp, width, height);
			tmpBmp.recycle();
			tmpBmp = null;
		}
		if (bmp != null) {
			drawableRef = new WeakReference<Bitmap>(bmp);
			bmpCacheMap.put(filename, drawableRef);
		}
		return bmp;
	}

	/**
	 * 
	 * @param bitmap
	 * @param w
	 * @param h
	 * @return
	 */
	private static Bitmap resizeImage(Bitmap bitmap, int w, int h) {

		// load the origial Bitmap
		Bitmap BitmapOrg = bitmap;

		int width = BitmapOrg.getWidth();
		int height = BitmapOrg.getHeight();
		int newWidth = w == 0 ? width : w;
		int newHeight = h == 0 ? height : h;

		// calculate the scale
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		//
		float scale = scaleWidth;
		if (scaleWidth > scaleHeight) {
			scale = scaleHeight;
		}

		// create a matrix for the manipulation
		Matrix matrix = new Matrix();
		// resize the Bitmap
		matrix.postScale(scale, scale);
		// if you want to rotate the Bitmap
		// matrix.postRotate(45);

		// recreate the new Bitmap
		Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
				height, matrix, true);

		// make a Drawable from Bitmap to allow to set the Bitmap
		// to the ImageView, ImageButton or what ever
		return resizedBitmap;

	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	private Bitmap getBitmap(String filename) {
		// //@////@System.out.println("getCacheBitmap filename:" + filename);
		if (TextUtils.isEmpty(filename))
			return null;
		try {
			InputStream stream = new FileInputStream(filename);
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inPreferredConfig = Bitmap.Config.RGB_565;
			// @@1.5
			opts.inPurgeable = true;
			opts.inInputShareable = true;
			Bitmap bmp = BitmapFactory.decodeStream(stream, null, opts);
			stream.close();
			stream = null;
			//
			return bmp;
		} catch (OutOfMemoryError error) {
			error.printStackTrace();
			// Debug.d(error.getMessage());
			// Commond.showToast(this, "显示图片失败1!");
		} catch (Exception ex) {
			delFile(filename);
			ex.printStackTrace();
			// Debug.d(ex.getMessage());
			// Commond.showToast(this, "显示图片失败!");
		}
		return null;
	}

	/**
	 * 
	 * @param url
	 */
	public boolean delFile(String filename) {
		// //@////@System.out.println("delFile filename:" + filename);
		if (!TextUtils.isEmpty(filename)) {
			//
			File file = new File(filename);
			if (file.exists())
				file.delete();
			file = null;
			return true;
		}
		return false;
	}
}