package com.app.chatroom.imgzoom.view;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.app.chatroom.imgzoom.gif.GIFFrameManager;

public class GifView extends View implements Runnable {
	private GIFFrameManager mGIFFrameManager = null;
	private Paint mPaint = null;

	/**
	 * Constructor
	 */
	public GifView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (mPaint == null)
			mPaint = new Paint();
	}

	public GifView(Context context) {
		super(context);
		if (mPaint == null)
			mPaint = new Paint();
	}

	/**
	 * 
	 * @param in
	 */
	public void setGif(InputStream in) {
		byte[] data = this.fileConnect(in);
		mGIFFrameManager = GIFFrameManager.CreateGifImage(data);
		new Thread(this).start();
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (mGIFFrameManager == null)
			return;
		mGIFFrameManager.nextFrame();
		Bitmap bitmap = mGIFFrameManager.getImage();
		if (bitmap != null) {
			int x = (this.getWidth()-bitmap.getWidth())/2;
			int y = (this.getHeight()-bitmap.getHeight())/2;
			canvas.drawBitmap(bitmap, x, y, mPaint);
		}
	}

	public byte[] fileConnect(InputStream in) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int ch = 0;
		try {
			while ((ch = in.read()) != -1) {
				out.write(ch);
			}
			byte[] b = out.toByteArray();
			out.close();
			out = null;
			in.close();
			in = null;
			return b;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void run() {
		while (!Thread.interrupted()) {
			try {
				Thread.sleep(100);
				this.postInvalidate();
			} catch (Exception ex) {
				ex.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}
}