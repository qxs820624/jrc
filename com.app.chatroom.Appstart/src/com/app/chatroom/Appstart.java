package com.app.chatroom;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.mgmusic.GetPublicKey;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.PhoneInfo;
import com.app.chatroom.util.SystemUtil;
import com.duom.fjz.iteminfo.BitmapCache2;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Comment;

public class Appstart extends Activity {
	Context mContext;
	TextView tvProgress;
	ProgressBar progressBar;
	RelativeLayout appStartRelativeLayout, rlbg; // 下载进度布局
	ImageView appStartIMText, iv; // 下载文字
	TextView dataTextView;// 数据包大小
	TextView verTextView;// 版本号
	RelativeLayout appBg;
	MyHandler handler;
	int sw;
	int sh;
	String filename;
	LayoutInflater inflater;
	View view;
	TextView toastTextView;
	Toast toast;
	public static File jrcfile, jrcsave;
	public static int swi;
	long totalSize = 0;
	InputStream connis;
	Bitmap bmp, bmp2;
	String ffg, fbg;

	SharedPreferences sp;
	SystemSettingUtilSp su;
	private int uid , ouid ;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		mContext = this;
		handler = new MyHandler(this);

		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.appstart);
		PhoneInfo siminfo = PhoneInfo.getInstance(Appstart.this);
		System.out.println(GetPublicKey.getSignInfo(getApplicationContext()));
		/** 创建基础文件 **/
		SystemUtil.makeDir(this);

		DisplayMetrics dm = new DisplayMetrics();
		dm = mContext.getApplicationContext().getResources()
				.getDisplayMetrics();

		ffg = su.getFfg();
		fbg = su.getFbg();

		sw = dm.widthPixels;
		sh = dm.heightPixels;
		
		////
		Intent it = getIntent();
		
		uid = it.getIntExtra("uid", 0);
		ouid = it.getIntExtra("ouid", 0);
		
		
		
		// 自定义Toast
		inflater = this.getLayoutInflater();
		view = inflater.inflate(R.layout.toast, null);
		toastTextView = (TextView) view.findViewById(R.id.toast_textView);
		toast = new Toast(this);
		toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 80);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(view);
		//
		rlbg = (RelativeLayout) this
				.findViewById(R.id.app_start_bg_RelativeLayout);
		iv = (ImageView) this.findViewById(R.id.imageView1);

		if (!TextUtils.isEmpty(ffg) || !TextUtils.isEmpty(fbg)) {
			File picfile = new File(Environment.getExternalStorageDirectory()
					+ File.separator + getPackageName()
					+ ConstantsJrc.IMAGE_PATH + "/" + Comment.getMd5Hash(fbg));
			filename = picfile.getPath().toString();
			if (picfile.exists()) {
				bmp = BitmapCache2.getIntance().getCacheBitmap(filename, 0, 0);

			}

			File picfile2 = new File(Environment.getExternalStorageDirectory()
					+ File.separator + getPackageName()
					+ ConstantsJrc.IMAGE_PATH + "/" + Comment.getMd5Hash(ffg));
			filename = picfile2.getPath().toString();
			if (picfile2.exists()) {
				bmp2 = BitmapCache2.getIntance().getCacheBitmap(filename, 0, 0);
				if (bmp2 != null && bmp != null) {
					iv.setImageBitmap(bmp2);
					BitmapDrawable bd2 = new BitmapDrawable(bmp);
					rlbg.setBackgroundDrawable(bd2);
				}
			}
		}

		tvProgress = (TextView) this.findViewById(R.id.tvProgress);
		progressBar = (ProgressBar) this.findViewById(R.id.progressBar);
		appStartRelativeLayout = (RelativeLayout) findViewById(R.id.app_start_relativeLayout);
		appStartIMText = (ImageView) findViewById(R.id.app_start_imageView_text);
		appBg = (RelativeLayout) findViewById(R.id.app_start_bg_RelativeLayout);
		dataTextView = (TextView) findViewById(R.id.datasize_textView);
		verTextView = (TextView) findViewById(R.id.app_start_vcode_TextView);
		verTextView.setText(siminfo.getVersionName(Appstart.this));
		progressBar.setMax(100);
		filename = sw + "-" + sh;
		appStartIMText.setVisibility(View.GONE);
		appStartRelativeLayout.setVisibility(View.GONE);
		dataTextView.setVisibility(View.GONE);
		progressBar.setVisibility(View.GONE);
	}

	public class ResThread implements Runnable {

		@Override
		public void run() {
			try {
				//
				downResfile(filename, sw, sh);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//
			if (canGotoHome()) {
				gotoHome();
			}
		}
	};

	class MyThread implements Runnable {
		public void run() {

		}
	}

	private boolean canGotoHome() {
		try {		
			getResources().getAssets().open(filename);
			return true;
		} catch (Exception e) {
			// e.printStackTrace();
		}
		try {
			File file = getFileStreamPath(filename);
			if (file != null && file.length() > 4) {
				FileInputStream fis = new FileInputStream(file);
				// 文件有效性验证
				totalSize = readInt(fis);
				int filelen = (int) file.length();
				if (filelen == totalSize + 4) {
					fis.close();
					return true;
				}
				//
				int progress = (int) (filelen * 100 / totalSize);
				Message msg = handler.obtainMessage();
				msg.what = progress;
				msg.sendToTarget();
				//
				fis.close();
			}
		} catch (FileNotFoundException ex) {
			Message msg = handler.obtainMessage();
			msg.what = -1;
			Bundle data = new Bundle();
			data.putString("msg", "FileNotFoundException:" + ex.getMessage());
			msg.setData(data);
			msg.sendToTarget();
		} catch (IOException ex) {
			Message msg = handler.obtainMessage();
			msg.what = -1;
			Bundle data = new Bundle();
			data.putString("msg", "IOException:" + ex.getMessage());
			msg.setData(data);
			msg.sendToTarget();
		}
		return false;
	}

	private void gotoHome() {
		Intent intent = new Intent(mContext, MainMenuActivity.class);
		intent.putExtra("uid", uid);
		intent.putExtra("ouid", ouid);
		startActivity(intent);
		Appstart.this.finish();
	}

	public void setDownProgress(int progress) {
		tvProgress.setText(progress + "%");
		progressBar.setProgress(progress);
	}

	class MyHandler extends Handler {
		WeakReference<Appstart> mActivity;

		MyHandler(Appstart activity) {
			mActivity = new WeakReference<Appstart>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == -1) {
				Bundle data = msg.getData();
				if (data.containsKey("ret")) {
					toastTextView.setText("网络响应错误：" + data.getInt("ret"));
					toast.show();
				} else if (data.containsKey("msg")) {
					toastTextView.setText(data.getString("msg"));
					toast.show();
				}
				return;
			}
			Appstart theActivity = mActivity.get();
			int progress = msg.what;

			if (progress <= 100) {
				progress = progress >= 100 ? 100 : progress;
				theActivity.setDownProgress(progress);
			}
			dataTextView.setText("数据包大小:" + SystemUtil.CountSize(totalSize));
		}
	};

	/**
	 * 
	 * @param filename
	 */
	private void downResfile(String filename, int w, int h) throws IOException {
		String urlStr = ConstantsJrc.LOADDATA + "?flg=2&w=" + w + "&h=" + h
				+ "&v=" + PhoneInfo.getInstance(this).getVersionName(this)
				+ "&c=" + PhoneInfo.getInstance(this).getVersionCode(this);
		// System.out.println("urlStr:" + urlStr);
		HttpURLConnection httpConnection = null;
		URL url = new URL(urlStr);
		httpConnection = (HttpURLConnection) url.openConnection();
		// httpConnection.setFixedLengthStreamingMode(1024);
		httpConnection.setConnectTimeout(200000);
		httpConnection.setReadTimeout(500000);
		httpConnection.setRequestProperty("Connection", "Keep-Alive");

		int currentSize = 0;
		File tmpFile = this.getFileStreamPath(filename);
		if (tmpFile.exists()) {
			currentSize = (int) tmpFile.length();
		} else {
			tmpFile.createNewFile();
		}
		//
		if (currentSize > 4) {
			String range = "bytes=" + (currentSize - 4) + "-" + totalSize;
			httpConnection.setRequestProperty("RANGE", range);
			// System.out.println("Range:" + range);
		}
		//
		int ret = httpConnection.getResponseCode();
		if (ret == HttpURLConnection.HTTP_OK
				|| ret == HttpURLConnection.HTTP_PARTIAL) {
			// getContentLength不准确
			int len = httpConnection.getContentLength();
			if (len > 0) {
				if (totalSize == 0)
					totalSize = currentSize + len;
				connis = httpConnection.getInputStream();
				// 206断点续传
				FileOutputStream fos = this.openFileOutput(filename,
						Context.MODE_APPEND);
				byte buffer[] = new byte[10240];
				int readsize = 0;
				while ((readsize = connis.read(buffer)) > 0) {
					fos.write(buffer, 0, readsize);
					currentSize += readsize;
					int progress = (int) (currentSize * 100 / totalSize);

					Message msg = handler.obtainMessage();
					msg.what = progress;
					msg.sendToTarget();
					// System.out.println("<<<<" + currentSize);
				}
				buffer = null;
				connis.close();
				fos.flush();
				fos.close();
			}
		} else {
			Message msg = handler.obtainMessage();
			msg.what = -1;
			Bundle data = new Bundle();
			data.putInt("ret", ret);
			msg.setData(data);
			msg.sendToTarget();
		}
		if (httpConnection != null) {
			httpConnection.disconnect();
		}
	}

	public void writeInt(long v, OutputStream os) throws IOException {
		os.write((byte) (0xff & (v >> 24)));
		os.write((byte) (0xff & (v >> 16)));
		os.write((byte) (0xff & (v >> 8)));
		os.write((byte) (0xff & v));
	}

	public int readInt(InputStream is) throws IOException {
		return is.read() * 256 * 256 * 256 + is.read() * 256 * 256 + is.read()
				* 256 + is.read();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(TextUtils.isEmpty(su.getLoading())){			
			deleteFilesByDirectory(getApplicationContext().getFilesDir());
			su.setLoading(ConstantsJrc.LOADDATA);
		}
		try {
			if (canGotoHome()) {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						gotoHome();
					}
				}, 1500);
				return;
			}
			appStartIMText.setVisibility(View.VISIBLE);
			appStartRelativeLayout.setVisibility(View.VISIBLE);
			dataTextView.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.VISIBLE);
			//
			if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
				toastTextView.setText("检测到网络网络异常或未开启");
				toast.show();
				appStartIMText.setVisibility(View.GONE);
				appStartRelativeLayout.setVisibility(View.GONE);
				dataTextView.setVisibility(View.GONE);
				return;
			}
			new Thread(new ResThread()).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if (connis != null)
			try {
				connis.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		// System.out.println("onPause");
		super.onPause();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			// 完全退出，无残留
			Intent startMain = new Intent(Intent.ACTION_MAIN);
			startMain.addCategory(Intent.CATEGORY_HOME);
			startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(startMain);
			android.os.Process.killProcess(android.os.Process.myPid());
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
    /** * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }
}