package com.app.chatroom.util;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.widget.ImageView;

public class ImageUtils {

	/**
	 * 将图片压缩成固定的大小
	 * 
	 * @param source
	 *            图片资源
	 * @param width
	 *            压缩高度
	 * @param height
	 *            压缩宽度
	 * @return
	 */
	public static Bitmap extractThumbnail(Bitmap source, int width, int height) {
		return ImageZoom.extractThumbnail(source, width, height);
	}

	private static class ImageZoom {

		private static final int OPTIONS_SCALE_UP = 0x1;
		public static final int OPTIONS_RECYCLE_INPUT = 0x2;
		private static final int OPTIONS_NONE = 0x0;

		public static Bitmap extractThumbnail(Bitmap source, int width,
				int height) {
			return extractThumbnail(source, width, height, OPTIONS_NONE);
		}

		private static Bitmap extractThumbnail(Bitmap source, int width,
				int height, int options) {
			if (source == null) {
				return null;
			}
			float scale;
			if (source.getWidth() < source.getHeight()) {
				scale = width / (float) source.getWidth();
			} else {
				scale = height / (float) source.getHeight();
			}
			Matrix matrix = new Matrix();
			matrix.setScale(scale, scale);
			Bitmap thumbnail = transform(matrix, source, width, height,
					OPTIONS_SCALE_UP | options);
			return thumbnail;
		}

		private static Bitmap transform(Matrix scaler, Bitmap source,
				int targetWidth, int targetHeight, int options) {
			boolean scaleUp = (options & OPTIONS_SCALE_UP) != 0;
			boolean recycle = (options & OPTIONS_RECYCLE_INPUT) != 0;

			int deltaX = source.getWidth() - targetWidth;
			int deltaY = source.getHeight() - targetHeight;
			if (!scaleUp && (deltaX < 0 || deltaY < 0)) {
				Bitmap b2 = Bitmap.createBitmap(targetWidth, targetHeight,
						Bitmap.Config.ARGB_8888);
				Canvas c = new Canvas(b2);
				int deltaXHalf = Math.max(0, deltaX / 2);
				int deltaYHalf = Math.max(0, deltaY / 2);
				Rect src = new Rect(deltaXHalf, deltaYHalf, deltaXHalf
						+ Math.min(targetWidth, source.getWidth()), deltaYHalf
						+ Math.min(targetHeight, source.getHeight()));
				int dstX = (targetWidth - src.width()) / 2;
				int dstY = (targetHeight - src.height()) / 2;
				Rect dst = new Rect(dstX, dstY, targetWidth - dstX,
						targetHeight - dstY);
				c.drawBitmap(source, src, dst, null);
				if (recycle) {
					source.recycle();
				}
				return b2;
			}
			float bitmapWidthF = source.getWidth();
			float bitmapHeightF = source.getHeight();

			float bitmapAspect = bitmapWidthF / bitmapHeightF;
			float viewAspect = (float) targetWidth / targetHeight;

			if (bitmapAspect > viewAspect) {
				float scale = targetHeight / bitmapHeightF;
				if (scale < .9F || scale > 1F) {
					scaler.setScale(scale, scale);
				} else {
					scaler = null;
				}
			} else {
				float scale = targetWidth / bitmapWidthF;
				if (scale < .9F || scale > 1F) {
					scaler.setScale(scale, scale);
				} else {
					scaler = null;
				}
			}
			Bitmap b1;
			if (scaler != null) {
				b1 = Bitmap.createBitmap(source, 0, 0, source.getWidth(),
						source.getHeight(), scaler, true);
			} else {
				b1 = source;
			}

			if (recycle && b1 != source) {
				source.recycle();
			}

			int dx1 = Math.max(0, b1.getWidth() - targetWidth);
			int dy1 = Math.max(0, b1.getHeight() - targetHeight);

			Bitmap b2 = Bitmap.createBitmap(b1, dx1 / 2, dy1 / 2, targetWidth,
					targetHeight);

			if (b2 != b1) {
				if (recycle || b1 != source) {
					b1.recycle();
				}
			}
			return b2;
		}
	}

	/**
	 * 计算压缩值
	 * 
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1; // 这是什么意思 ,这里应该写+=1吧！
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8; // 这里让它保持是8的整数倍，有什么意思在里面嘛？貌似8这个，像是内存分配的什么栈大小样
		}
		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));
		if (upperBound < lowerBound) {
			return lowerBound;
		}
		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	/**
	 * 将sd卡中的图片动态压缩，显示在imageview上
	 * 
	 * @param imageView
	 * @param imageFile
	 */
	public void sasuo(ImageView imageView, String imageFile) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(imageFile, opts);
		opts.inSampleSize = computeSampleSize(opts, -1, 128 * 128);
		opts.inJustDecodeBounds = false;
		try {
			Bitmap bmp = BitmapFactory.decodeFile(imageFile, opts);
			imageView.setImageBitmap(bmp);
		} catch (OutOfMemoryError err) {
		}

	}

	/**
	 * 按文件大小缩放
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap getBitmapByPath(String path) {
		File f = new File(path);
		Bitmap resizeBmp = null;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		if (f.length() < 50000) {
			opts.inSampleSize = 1;
		} else if (f.length() < 100000) {
			opts.inSampleSize = 1;
		} else if (f.length() < 200000) {
			opts.inSampleSize = 2;
		} else if (f.length() < 442500) {
			opts.inSampleSize = 3;
		} else if (f.length() < 885000) {
			opts.inSampleSize = 6;
		} else if (f.length() < 1770000) {
			opts.inSampleSize = 10;
		} else if (f.length() < 3540000) {
			opts.inSampleSize = 12;
		} else {
			// System.out.println("8  " + f.length() + " , " + f.length()
			// / (1024f * 1024f) * 1000f);
			opts.inSampleSize = 19;
		}
		resizeBmp = BitmapFactory.decodeFile(f.getPath(), opts);
		return resizeBmp;
	}

	/**
	 * 按文件大小缩放
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap getBitmapByPath2(String path) {
		File f = new File(path);
		Bitmap resizeBmp = null;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		if (f.length() < 50000) {
			opts.inSampleSize = 1;
		} else if (f.length() < 100000) {
			opts.inSampleSize = 1;
		} else if (f.length() < 200000) {
			opts.inSampleSize = 2;
		} else if (f.length() < 442500) {
			opts.inSampleSize = 2;
		} else if (f.length() < 885000) {
			opts.inSampleSize = 2;
		} else if (f.length() < 1300000) {
			opts.inSampleSize = 2;
		} else if (f.length() < 1770000) {
			opts.inSampleSize = 4;
		} else if (f.length() < 2000000) {
			opts.inSampleSize = 6;
		} else if (f.length() < 2400000) {
			opts.inSampleSize = 7;
		} else if (f.length() < 2800000) {
			opts.inSampleSize = 8;
		} else if (f.length() < 3000000) {
			opts.inSampleSize = 10;
		} else if (f.length() < 3540000) {
			opts.inSampleSize = 12;
		} else {
			// System.out.println("8  " + f.length() + " , " + f.length()
			// / (1024f * 1024f) * 1000f);
			opts.inSampleSize = 14;
		}
		resizeBmp = BitmapFactory.decodeFile(f.getPath(), opts);
		return resizeBmp;
	}

}
