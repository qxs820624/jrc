package com.app.chatroom.imgzoom;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;

import org.json.JSONArray;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.imgzoom.view.GifView;
import com.app.chatroom.imgzoom.view.TouchImageView;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;
import com.jianrencun.android.Choosewhich;
import com.jianrencun.android.Details;
import com.jianrencun.android.HttpBaseActivity;
import com.jianrencun.android.HttpBaseActivity.HttpRequestTask;
import com.jianrencun.chatroom.R;
import com.jianrencun.dynamic.Aboutme;
import com.jianrencun.dynamic.AboutmeAdapter;
import com.jianrencun.dynamic.Aboutmeinfo;

public class ImageZoom extends HttpBaseActivity {
	Context mContext;
	private TouchImageView zoomView;
	private GifView gifView;
	Bitmap mBitmap;
	String imageUrl = null;
	String savepath = null;
	String downpath = null;
	int[] resids = new int[1];
	LayoutInflater inflater;
	View view;
	TextView toastTextView;
	Toast toast;
	LinearLayout ll , lladd ;
	Button sure , cancle ;
	private Details dt = new Details();
	/* 配置文件 */
	SharedPreferences sp;
	SystemSettingUtilSp su;
	String ouid ;

	// private ZoomState mZoomState;
	// private SimpleZoomListener mZoomListener;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		//
		setContentView(R.layout.image_zoom);
		
		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
		//
		sure = (Button)this.findViewById(R.id.sure_add);
		cancle = (Button)this.findViewById(R.id.cancle_add);
		lladd = (LinearLayout)this.findViewById(R.id.ll_add);
		ll = (LinearLayout)this.findViewById(R.id.ll_panel);
		zoomView = (TouchImageView) this.findViewById(R.id.zoom_view);
		gifView = (GifView) this.findViewById(R.id.gif_view);
		// 自定义Toast
		inflater = this.getLayoutInflater();
		view = inflater.inflate(R.layout.toast, null);
		toastTextView = (TextView) view.findViewById(R.id.toast_textView);
		toast = new Toast(this);
		toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 80);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(view);
		//
		findViewById(R.id.img_zoom_btn_back).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();
					}
				});
		//
		resids[0] = R.id.ll_panel;
		setGestureDetector(resids);
		zoomView.setGestureScanner(gestureScanner);
		zoomView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();

				int version = Integer.valueOf(android.os.Build.VERSION.SDK);
				if (version > 5) {
					overridePendingTransition(R.anim.zoom_exit2,
							R.anim.zoom_exit2);
				}
			}
		});
		
		sure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent data = new Intent();
				setResult(77, data);
				finish();
			}
		});
		
		cancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		//
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// 直接调用创建bitmap，会出现显示不正确的问题
		// 因此需要设置zoomView的高宽
		zoomView.layout(0, 0, dm.widthPixels, dm.heightPixels);

		//
		Intent i = this.getIntent();
		if (i != null) {
			imageUrl = i.getStringExtra("imageurl");
			ouid = i.getStringExtra("ouid");
			// Log.i("tttt", imageUrl);
			savepath = i.getStringExtra("savepath");
			File file = new File(savepath);
			if (!file.exists())
				file.mkdirs();
			if (!savepath.endsWith("/") && !savepath.endsWith("\\"))
				savepath += "/";
			savepath += Commond.getMd5Hash(imageUrl);
			//
			downpath = i.getStringExtra("downpath");
			
			int k = i.getIntExtra("izhl", 3);
			if(k == 2){
				ll.setVisibility(View.GONE);
				lladd.setVisibility(View.VISIBLE);
			}
		}

		//
		if (!TextUtils.isEmpty(imageUrl)) {
			if (imageUrl.startsWith("http://")) {
				if (!downpath.endsWith("/") && !downpath.endsWith("\\"))
					downpath += "/";
				downpath += Commond.getMd5Hash(imageUrl);
				// 首先判断本地是否存在,存在则直接显示
				File file = new File(downpath);
				if (file.exists()) {
					loadImage(downpath);
				} else {
					findViewById(R.id.progressImage)
							.setVisibility(View.VISIBLE);
					RequestImageTask dTask = new RequestImageTask();
					dTask.execute(imageUrl);
				}
			} else {
				downpath = imageUrl;// 本地文件
				loadImage(downpath);
			}
		}

	}

	/**
	 * 
	 */
	private void loadButtons() {
		findViewById(R.id.img_zoom_image_save).setVisibility(View.VISIBLE);
		findViewById(R.id.img_zoom_image_save).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						Intent it = new Intent();
						it.setClass(ImageZoom.this, Choosewhich.class);
						it.putExtra("from", "jubao");
						startActivityForResult(it, 2);
						

					}
				});
	}

	private void loadImage(String path) {
		try {
			String ex = "jpg";
			// 暂时屏蔽掉gif的显示
			// FileInputStream fios = new FileInputStream(srcFilePath);
			// ex = getFileType(fios);
			// fios.close();
			if ("gif".equals(ex)) {
				zoomView.setVisibility(View.GONE);
				FileInputStream fis = new FileInputStream(path);
				gifView.setGif(fis);
				loadButtons();
			} else {
				// BitmapFactory.Options opts = new BitmapFactory.Options();
				// opts.inJustDecodeBounds = true;
				// BitmapFactory.decodeFile(path, opts);
				//
				// opts.inSampleSize = computeSampleSize(opts, -1, 128 * 128);
				// opts.inJustDecodeBounds = false;
				mBitmap = BitmapFactory.decodeFile(path);
				zoomView.setImage(mBitmap , 0);
				gifView.setVisibility(View.GONE);
				loadButtons();

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Commond.showToast(ImageZoom.this, "大图出问题了！");
			finish();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mBitmap != null && !mBitmap.isRecycled()) {
			mBitmap.recycle();
			mBitmap = null;
		}
	}

	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
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
			// return the larger one when there is no overlapping zone.
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

	// ////////////////////////////////
	// ////////////////////////////////
	// ////////////////////////////////
	class RequestImageTask extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPostExecute(String result) {
			if (!TextUtils.isEmpty(result) && result.startsWith("http://"))
				loadImage(downpath);
			else {
				// Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
				toastTextView.setText("检测到网络网络异常或未开启");
				toast.show();
			}
			findViewById(R.id.progressImage).setVisibility(View.GONE);
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url = params[0];
			String result = Commond.urlToFile(mContext, url, downpath);
			if (TextUtils.isEmpty(result))
				return url;
			else
				return result;
		}
	}

	public GestureDetector gestureScanner;

	/**
	 * 
	 * @param resids
	 */
	public void setGestureDetector(final int[] resids) {
		//
		gestureScanner = new GestureDetector(mContext, new OnGestureListener() {

			@Override
			public boolean onDown(MotionEvent arg0) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float arg3) {
				if (e1 == null || e2 == null)
					return false;
				// TODO Auto-generated method stub
				int SWIPE_X_MIN_DISTANCE = 200;
				int SWIPE_MAX_OFF_PATH = 250;
				int SWIPE_THRESHOLD_VELOCITY = 200;
				int swipeXDistance = (int) (e1.getX() - e2.getX());
				int swipeYDistance = (int) (e1.getY() - e2.getY());
				if (swipeXDistance > SWIPE_X_MIN_DISTANCE
				/* && Math.abs(swipeXDistance) > Math.abs(swipeYDistance) */
				&& Math.abs(swipeYDistance) < 100
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					// viewFlipper.setInAnimation(slideLeftIn);
					// viewFlipper.setOutAnimation(slideLeftOut);
					// viewFlipper.showNext();
					rightInLeftOut();
					return true;
				} else if (-swipeXDistance > SWIPE_X_MIN_DISTANCE
				/* && Math.abs(swipeXDistance) > Math.abs(swipeYDistance) */
				&& Math.abs(swipeYDistance) < 100
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					// viewFlipper.setInAnimation(slideRightIn);
					// viewFlipper.setOutAnimation(slideRightOut);
					// viewFlipper.showPrevious();
					rightOutLeftIn();
					return true;
				}
				if (swipeXDistance > SWIPE_X_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

					// viewFlipper.setInAnimation(slideLeftIn);
					// viewFlipper.setOutAnimation(slideLeftOut);
					// viewFlipper.showNext();
				} else if (-swipeXDistance > SWIPE_X_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

					// viewFlipper.setInAnimation(slideRightIn);
					// viewFlipper.setOutAnimation(slideRightOut);
					// viewFlipper.showPrevious();
				}
				return false;
			}

			@Override
			public void onLongPress(MotionEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean onScroll(MotionEvent arg0, MotionEvent arg1,
					float arg2, float arg3) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onShowPress(MotionEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean onSingleTapUp(MotionEvent arg0) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		gestureScanner
				.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
					@Override
					public boolean onDoubleTap(MotionEvent e) {
						/*
						 * // for (int i = 0; i < resids.length; i++) { View v =
						 * findViewById(resids[i]); hideViewAnim(v); }
						 * Commond.isHidePanel = !Commond.isHidePanel; //
						 * switchFullScreen();
						 */
						return true;
					}

					@Override
					public boolean onDoubleTapEvent(MotionEvent e) {
						return true;
					}

					@Override
					public boolean onSingleTapConfirmed(MotionEvent e) {
						return true;
					}
				});
	}

	public void rightInLeftOut() {
	}

	public void rightOutLeftIn() {

	}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
		if (resultCode == 50) {
			int index = data.getIntExtra("index", 50);
			if (index == 33) {
				String ex = "";
				// 判断是否是本地文件
				if (imageUrl.startsWith("http://")) {
					try {
						FileInputStream fios = new FileInputStream(
								downpath);
						ex = "." + Commond.getFileType(fios);
						fios.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						FileInputStream fios = new FileInputStream(
								downpath);
						ex = "." + Commond.getFileType(fios);
						fios.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//
				if (Commond.copyFile(downpath, savepath + ex)) {
					Toast.makeText(mContext, "保存成功！" + savepath + ex,
							Toast.LENGTH_SHORT).show();
				} else
					Toast.makeText(mContext, "保存失败！",
							Toast.LENGTH_SHORT).show();
            
			} else if (index == 44) {
				String url = ConstantsJrc.JUBAOHEADER;
				url = Details.appendNameValueint(url, "uid", Integer.parseInt(su.getUid()));
				url = Details.appendNameValueint(url, "ouid", Integer.parseInt(ouid));
				url = dt.appendNameValue(url, "token", su.getToken());
				StringBuffer data1 = new StringBuffer();
				HttpRequestTask request = new HttpRequestTask();
				request.execute(url, data1.toString());
			}
		}
    }

	public void resultData(String url, String result) {
		// TODO Auto-generated method stub
		String tip = null;
		if (result == null) {
			// fresh.setVisibility(View.VISIBLE);
			Commond.showToast(ImageZoom.this, "小贱提醒 ：当前网络不稳定！");
			return;
		}

		try {
			// //////////////////////////////////////////////正文内容页面
			JSONObject jsonChannel = new JSONObject(result);
			int ret = jsonChannel.optInt("ret");
			if (ret == 0) {
				tip = URLDecoder.decode(jsonChannel.optString("tip"));
				Commond.showToast(ImageZoom.this, tip);
				return;
			}	
			tip = URLDecoder.decode(jsonChannel.optString("tip"));
			Commond.showToast(ImageZoom.this, tip);
		} catch (Exception e) {
			// TODO: handle exception
			// pb.setVisibility(View.GONE);
			Commond.showToast(ImageZoom.this, "操作失败！");
		}
	}
}
