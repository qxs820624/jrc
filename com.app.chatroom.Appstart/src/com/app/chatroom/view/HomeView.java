package com.app.chatroom.view;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Vector;

import com.app.chatroom.Appstart;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class HomeView extends View implements GestureDetector.OnGestureListener {
	Context mContext;
	private GestureDetector mGestureDetector;
	OnClickListener mListener;

	Paint mPaint = new Paint();
	//
	int mBgW;
	int mBgH;
	Bitmap mBgBmp;
	ArrayList<IconEntity> icons = new ArrayList<IconEntity>(8);
	ArrayList<Bitmap> mBgBmps = null;
	int bh;
	public static Vector<Point> points = new Vector<Point>(8);

	//
	/**
	 * 
	 * */
	public HomeView(Context context) {
		super(context);
		init(context);
	}

	/**
	 * 
	 * @param context
	 * @param attr
	 */
	public HomeView(Context context, AttributeSet attr) {
		super(context, attr);
		init(context);
	}

	public HomeView(Context context, AttributeSet attrs, int defStyle) {
		// TODO Auto-generated constructor stub
		super(context, attrs, defStyle);
	}

	@Override
	public void setOnClickListener(OnClickListener listener) {
		mListener = listener;
	}

	/**
	 * 
	 */
	private void init(Context context) {
		mContext = context;
		mGestureDetector = new GestureDetector(getContext(), this);
		DisplayMetrics dm = new DisplayMetrics();
		dm = mContext.getApplicationContext().getResources()
				.getDisplayMetrics();
		int sw = dm.widthPixels;
		int sh = dm.heightPixels;
		float density = dm.density;
		float xdpi = dm.xdpi;
		float ydpi = dm.ydpi;
		init(sw + "-" + sh);
	}

	/**
	 * 
	 * @param fileName
	 */
	public void init(String fileName) {
		InputStream in = null;
		//
		try {
			in = getResources().getAssets().open(fileName);
		} catch (Exception e) {
			try {
				in = mContext.openFileInput(fileName);
				// 读取文件大小
				readInt(in);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (in == null) {
			try {
				in = getResources().getAssets().open("480-800");
			} catch (Exception e) {

			}
		}
		try {
			byte[] buff = null;
			mBgW = readInt(in);
			mBgH = readInt(in);
			int len = readInt(in);
			if (len > 0) {
				buff = new byte[len];
				len = in.read(buff);
				mBgBmp = BitmapFactory.decodeByteArray(buff, 0, len);
				buff = null;
			}
			//
			int count = 8;
			for (int i = 0; i < count; i++) {
				IconEntity icon = new IconEntity();
				icon.rect.left = readInt(in);
				icon.rect.top = readInt(in);
				len = readInt(in);
				buff = new byte[len];
				len = in.read(buff);
				icon.iconBmp = BitmapFactory.decodeByteArray(buff, 0, len);
				icon.rect.right = icon.rect.left + icon.iconBmp.getWidth();
				icon.rect.bottom = icon.rect.top - bh
						+ icon.iconBmp.getHeight();
				len = readInt(in);
				buff = null;
				buff = new byte[len];
				len = in.read(buff);
				icon.icon1Bmp = BitmapFactory.decodeByteArray(buff, 0, len);
				buff = null;
				//
				Point p = new Point(icon.rect.left + icon.iconBmp.getWidth()
						/ 2, icon.rect.top - bh);
				points.add(p);
				icons.add(icon);
			}
			//
			count = readInt(in);
			if (count > 0) {
				mBgBmps = new ArrayList<Bitmap>(count);
				for (int i = 0; i < count; i++) {
					len = readInt(in);
					if (len <= 0)
						break;
					buff = new byte[len];
					len = in.read(buff);
					if (len <= 0)
						break;
					mBgBmps.add(BitmapFactory.decodeByteArray(buff, 0, len));
					buff = null;
				}
			}
			//
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int readInt(InputStream is) throws IOException {
		return is.read() * 256 * 256 * 256 + is.read() * 256 * 256 + is.read()
				* 256 + is.read();
	}

	/**
	 * 缁樺埗View
	 * */
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		bh = getStatusHeight((Activity) mContext);
		// 背景
		if (mBgBmp != null)
			canvas.drawBitmap(mBgBmp, 0, 0, mPaint);
		else if (mBgBmps != null) {
			int offX = 0;
			int offY = -bh;
			for (Bitmap bgbmp : mBgBmps) {
				if (bgbmp != null) {
					canvas.drawBitmap(bgbmp, offX, offY, mPaint);
					offY += bgbmp.getHeight();
				}
			}
		}
		// 图标Icon
		for (IconEntity icon : icons) {   
			int i = icons.indexOf(icon);
            	  if (icon.isSelected) {
            		  if((i== 0 || i== 1 ||i== 2 ||i== 3 ||i== 4 )&&(i != 5 && i != 6 && i != 7 )){
  					canvas.drawBitmap(icon.icon1Bmp, icon.rect.left,
  							(icon.rect.top - bh), mPaint);
            		  }
  				} else {
  				  if((i== 0 || i== 1 ||i== 2 ||i== 3 ||i== 4 )&&(i != 5 && i != 6 && i != 7 )){
  					canvas.drawBitmap(icon.iconBmp, icon.rect.left,
  							(icon.rect.top - bh), mPaint);
  				  }
  				}
              						
		}
	}

	/**
	 * 
	 */
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY() + bh;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;

		case MotionEvent.ACTION_UP:
			int i = 0;
			for (IconEntity icon : icons) {
				if (icon.rect.contains(x, y)) {
					this.setTag(i);
					mListener.onClick(this);
				}
				i++;
				icon.isSelected = false;
			}
			invalidate();
			break;
		}
		return mGestureDetector.onTouchEvent(event);
	}

	/**
	 * 
	 * @author sunxml
	 * 
	 */
	public class IconEntity {
		boolean isSelected = false;
		Rect rect = new Rect();
		Bitmap iconBmp;
		Bitmap icon1Bmp;
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		int x = (int) arg0.getX();
		int y = (int) arg0.getY() + bh;
		boolean isPreceded = false;
		for (IconEntity icon : icons) {
			if (icon.rect.contains(x, y)) {
				isPreceded = true;
				icon.isSelected = true;
			} else
				icon.isSelected = false;
		}
		invalidate();
		return isPreceded;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 
	 * @param activity
	 * @return > 0 success; <= 0 fail
	 */
	public static int getStatusHeight(Activity activity) {
		int statusHeight = 0;
		Rect localRect = new Rect();
		activity.getWindow().getDecorView()
				.getWindowVisibleDisplayFrame(localRect);
		statusHeight = localRect.top;
		if (0 == statusHeight) {
			Class<?> localClass;
			try {
				localClass = Class.forName("com.android.internal.R$dimen");
				Object localObject = localClass.newInstance();
				int i5 = Integer.parseInt(localClass
						.getField("status_bar_height").get(localObject)
						.toString());
				statusHeight = activity.getResources()
						.getDimensionPixelSize(i5);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		return statusHeight;
	}
}