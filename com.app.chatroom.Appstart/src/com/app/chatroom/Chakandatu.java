package com.app.chatroom;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.imgzoom.ImageZoom;
import com.app.chatroom.imgzoom.view.TouchImageView;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;
import com.app.chatroom.util.SystemUtil;
import com.duom.fjz.iteminfo.BitmapCacheDzlb;
import com.jianrencun.android.Choosewhich;
import com.jianrencun.android.Details;
import com.jianrencun.android.HttpBaseActivity;
import com.jianrencun.android.HttpBaseActivity.HttpRequestTask;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Comment;

public class Chakandatu extends HttpBaseActivity {
	public ViewPager mTabPager;
	private int tabpage;
	private TouchImageView iv;
	private LinearLayout ll;
	private List<TouchImageView> ivs;
	private List<LinearLayout> lls;
	private ArrayList<String> photos = new ArrayList<String>();
	private List<Boolean> bl;
	private String dazuidown;
	private File ImageFile;
	private ArrayList<String> urls = new ArrayList<String>();
	private TextView tv1, tv2;
	private int po;
	private ImageButton back, save;
	private String url;
	private String savepath;
	String filename;
	int[] resids = new int[1];
	public GestureDetector gestureScanner;
	DisplayMetrics dm = new DisplayMetrics();
	private List<Integer> ints = new ArrayList<Integer>();

	private List<Integer> picids = new ArrayList<Integer>();
	private Details dt = new Details();
	/* 配置文件 */
	SharedPreferences sp;
	SystemSettingUtilSp su;
	String ouid;
	int picid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chakandatu);

		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);

		if (!TextUtils.isEmpty(Appstart.jrcsave.getPath())) {
			savepath = Appstart.jrcsave.getPath();
		} else {
			savepath = Environment.getExternalStorageDirectory().toString()
					+ "/" + ConstantsJrc.SAVE_PATH;
		}

		if (SystemUtil.getSDCardMount()) {
			ImageFile = new File(Environment.getExternalStorageDirectory()
					+ File.separator + getPackageName()
					+ ConstantsJrc.IMAGE_PATH);
			if (!ImageFile.exists()) {
				ImageFile.mkdirs();
			}
		} else {
			ImageFile = new File(ConstantsJrc.PROJECT_PATH + getPackageName()
					+ ConstantsJrc.IMAGE_PATH);
			if (!ImageFile.exists()) {
				ImageFile.mkdirs();
			}
		}

		back = (ImageButton) findViewById(R.id.img_back);
		save = (ImageButton) findViewById(R.id.img_save);

		resids[0] = R.id.ll_panel;
		setGestureDetector(resids);
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		dazuidown = ImageFile.getAbsolutePath().toString();
		Intent it = getIntent();
		photos = it.getStringArrayListExtra("photos");
		po = it.getIntExtra("po", 0);
		ouid = it.getStringExtra("uid");
		picid = it.getIntExtra("picid", 0);
		if (!ouid.equals(su.getUid())) {
			picids = it.getIntegerArrayListExtra("picids");
		} else {
			save.setBackgroundResource(R.drawable.title_seve_btn_right);
		}

		// android.util.Log.e("eeeeeeee222", photos.toString());

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!ouid.equals(su.getUid())) {
					Intent it = new Intent();
					it.setClass(Chakandatu.this, Choosewhich.class);
					it.putExtra("from", "jubao");
					startActivityForResult(it, 2);
				} else {
					savePhoto();
				}
			}
		});

		tv1 = (TextView) findViewById(R.id.chakandatu_dangqian);
		tv2 = (TextView) findViewById(R.id.chakandatu_total);
		tv2.setText(String.valueOf(photos.size()));
		tv1.setText(String.valueOf(po + 1));

		// 直接调用创建bitmap，会出现显示不正确的问题

		mTabPager = (ViewPager) findViewById(R.id.tabpager_datu);
		mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());

		ivs = new ArrayList<TouchImageView>();
		lls = new ArrayList<LinearLayout>();
		bl = new ArrayList<Boolean>();
		// 将要分页显示的View装入数组中
		LayoutInflater mLi = LayoutInflater.from(this);
		// 每个页面的view数据
		final ArrayList<View> views = new ArrayList<View>();
		for (int i = 0; i < photos.size(); i++) {

			Boolean boo = false;
			bl.add(boo);

			int j = 0;
			ints.add(j);

			View view = mLi.inflate(R.layout.morephotos, null);
			iv = (TouchImageView) view.findViewById(R.id.morephotoiv);
			// 因此需要设置zoomView的高宽
			iv.layout(0, 0, dm.widthPixels, dm.heightPixels);
			iv.setGestureScanner(gestureScanner);

			ivs.add(iv);
			ivs.get(i).setId(i);

			ll = (LinearLayout) view.findViewById(R.id.morephoto_pb_ll);
			lls.add(ll);

			views.add(view);
		}

		// 填充ViewPager的数据适配器
		PagerAdapter mPagerAdapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(views.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(views.get(position));
				return views.get(position);
			}
		};

		mTabPager.setAdapter(mPagerAdapter);
		mTabPager.setCurrentItem(po, false);

		if (po == 0) {
			lls.get(po).setVisibility(View.VISIBLE);
			File picfile = new File(dazuidown + "/"
					+ Comment.getMd5Hash(photos.get(po)));
			filename = picfile.getPath().toString();
			url = photos.get(po);

			Bitmap bmp = null;
			if (picfile.exists()) {
				bmp = BitmapCacheDzlb.getIntance().getCacheBitmap(filename, 0,
						0);
			}

			if (bmp == null) {
				if (!urls.contains(photos.get(po))) {

					urls.add(photos.get(po));
					new Thread(new LoadImageRunnable(Chakandatu.this, mHandler,
							0, photos.get(po), filename, po)).start();
				}
			} else {

				lls.get(po).setVisibility(View.GONE);
				// BitmapDrawable bd = new BitmapDrawable(bmp);
				ivs.get(po).layout(0, 0, dm.widthPixels, dm.heightPixels);
				ivs.get(po).setGestureScanner(gestureScanner);
				ivs.get(po).setImage(bmp, ints.get(po));
				ints.set(po, 1);
			}
		}
	}

	/**
	 * 头标点击监听
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mTabPager.setCurrentItem(index);
		}
	};

	/*
	 * 页卡切换监听()
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			File picfile = null;
			try {
				if (picids != null && picids.size() != 0) {
					picid = picids.get(arg0);
				}
				tv1.setText(String.valueOf(arg0 + 1));
				url = photos.get(arg0);
				lls.get(arg0).setVisibility(View.VISIBLE);
				picfile = new File(dazuidown + "/"
						+ Comment.getMd5Hash(photos.get(arg0)));
				filename = picfile.getPath().toString();

				Bitmap bmp = null;
				if (picfile.exists()) {
					bmp = BitmapCacheDzlb.getIntance().getCacheBitmap(filename,
							0, 0);
				}

				if (bmp == null) {
					if (!urls.contains(photos.get(arg0))) {
						urls.add(photos.get(arg0));
						new Thread(new LoadImageRunnable(Chakandatu.this,
								mHandler, 0, photos.get(arg0), filename, arg0))
								.start();
					}
				} else {
					lls.get(arg0).setVisibility(View.GONE);
					BitmapDrawable bd = new BitmapDrawable(bmp);

					ivs.get(arg0).layout(0, 0, dm.widthPixels, dm.heightPixels);
					ivs.get(arg0).setGestureScanner(gestureScanner);

					ivs.get(arg0).setImage(bmp, ints.get(arg0));
					ints.set(arg0, 1);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				Commond.showToast(Chakandatu.this, "登录超时，请重新登录！");
				finish();
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// lastview = arg0;
		}
	}

	public class LoadImageRunnable implements Runnable {
		private Context mContext;
		private int mThreadId;
		private Handler mHandler;
		private String sUrl;
		private String mFilename;
		private int arg0;

		public LoadImageRunnable(Context context, Handler h, int id,
				String str, String filename, int arg0) {
			mHandler = h;
			mContext = context;
			mThreadId = id;
			sUrl = str;
			mFilename = filename;
			this.arg0 = arg0;
		}

		@Override
		public void run() {

			Comment.urlToFile(mContext, sUrl, mFilename);
			Message msg = new Message();
			msg.what = arg0;
			Bundle data = new Bundle();
			data.putString("url", sUrl);
			data.putString("filename", mFilename);
			msg.setData(data);
			mHandler.sendMessage(msg);
		}
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			int i = msg.what;
			if (data != null) {
				String url = data.getString("url");
				String filename = data.getString("filename");

				Bitmap bmp = BitmapCacheDzlb.getIntance().getCacheBitmap(
						filename, 0, 0);
				TouchImageView iv = ivs.get(i);
				lls.get(i).setVisibility(View.GONE);
				if (iv != null) {
					BitmapDrawable bd = new BitmapDrawable(bmp);
					iv.layout(0, 0, dm.widthPixels, dm.heightPixels);
					iv.setGestureScanner(gestureScanner);
					iv.setImage(bmp, ints.get(i));
					ints.set(i, 1);
					;
				}
			}
		}
	};

	/**
	 * 
	 * @param resids
	 */
	public void setGestureDetector(final int[] resids) {
		//
		gestureScanner = new GestureDetector(Chakandatu.this,
				new OnGestureListener() {

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
								/*
								 * && Math.abs(swipeXDistance) >
								 * Math.abs(swipeYDistance)
								 */
								&& Math.abs(swipeYDistance) < 100
								&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
							// viewFlipper.setInAnimation(slideLeftIn);
							// viewFlipper.setOutAnimation(slideLeftOut);
							// viewFlipper.showNext();
							rightInLeftOut();
							return true;
						} else if (-swipeXDistance > SWIPE_X_MIN_DISTANCE
								/*
								 * && Math.abs(swipeXDistance) >
								 * Math.abs(swipeYDistance)
								 */
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
				savePhoto();
			} else if (index == 44) {
				String url = ConstantsJrc.JUBAOPHOTO;
				url = Details.appendNameValueint(url, "uid",
						Integer.parseInt(su.getUid()));
				url = Details.appendNameValueint(url, "ouid",
						Integer.parseInt(ouid));
				url = Details.appendNameValueint(url, "picid", picid);
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
			Commond.showToast(Chakandatu.this, "小贱提醒 ：当前网络不稳定！");
			return;
		}

		try {
			// //////////////////////////////////////////////正文内容页面
			JSONObject jsonChannel = new JSONObject(result);
			int ret = jsonChannel.optInt("ret");
			if (ret == 0) {
				tip = URLDecoder.decode(jsonChannel.optString("tip"));
				Commond.showToast(Chakandatu.this, tip);
				return;
			}
			tip = URLDecoder.decode(jsonChannel.optString("tip"));
			Commond.showToast(Chakandatu.this, tip);
		} catch (Exception e) {
			// TODO: handle exception
			// pb.setVisibility(View.GONE);
			Commond.showToast(Chakandatu.this, "操作失败！");
		}
	}

	public void savePhoto() {
		File file = new File(savepath);
		if (!file.exists())
			file.mkdirs();
		if (!savepath.endsWith("/") && !savepath.endsWith("\\"))
			savepath += "/";
		savepath += Commond.getMd5Hash(url);
		// TODO Auto-generated method stub
		String ex = "";
		// 判断是否是本地文件
		if (url.startsWith("http://")) {
			try {
				FileInputStream fios = new FileInputStream(filename);
				ex = "." + Commond.getFileType(fios);
				fios.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				FileInputStream fios = new FileInputStream(filename);
				ex = "." + Commond.getFileType(fios);
				fios.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//
		if (Commond.copyFile(filename, savepath + ex)) {
			Toast.makeText(Chakandatu.this, "保存成功！" + savepath + ex,
					Toast.LENGTH_SHORT).show();
			if (!TextUtils.isEmpty(Appstart.jrcsave.getPath())) {
				savepath = Appstart.jrcsave.getPath();
			} else {
				savepath = Environment.getExternalStorageDirectory().toString()
						+ "/" + ConstantsJrc.SAVE_PATH;

			}
		} else
			Toast.makeText(Chakandatu.this, "保存失败！", Toast.LENGTH_SHORT).show();
	}
}
