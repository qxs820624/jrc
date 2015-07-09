package com.app.chatroom.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;
import com.app.chatroom.util.DateManager;
import com.app.chatroom.util.ImageUtils;
import com.app.chatroom.util.SystemUtil;
import com.jianrencun.chatroom.R;

public class PicHandleActivity extends Activity {
	ImageButton backBtn;
	ImageButton sendBtn;
	TextView picSizeTv;
	ImageView picImageView;
	String oldPicPath = "";
	String type = "";
	SharedPreferences sp;
	SystemSettingUtilSp su;
	LoadThread loadThread = new LoadThread();
	String newPicSize = "";
	int w;
	int h;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pic_preview);
		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
		backBtn = (ImageButton) findViewById(R.id.pic_handle_back_btn);
		sendBtn = (ImageButton) findViewById(R.id.pic_handle_send);
		picSizeTv = (TextView) findViewById(R.id.pic_handle_size_TextView);
		picImageView = (ImageView) findViewById(R.id.pic_handle_ImageView);
		Intent intent = getIntent();
		oldPicPath = intent.getStringExtra("url");
		type = intent.getStringExtra("type");
		// Log.i("ttt", "pic原图地址:" + oldPicPath);
		File file = new File(oldPicPath);
		picSizeTv.setText("原图片大小：" + SystemUtil.CountSize(file.length()));
		// 如果无SD卡则存储在APP文件夹
		if (SystemUtil.getSDCardMount()) {
			su.setNewPicPath(Environment.getExternalStorageDirectory()
					+ File.separator + getPackageName()
					+ ConstantsJrc.IMAGE_PATH + File.separator
					+ Commond.getMd5Hash(DateManager.getPhoneTime()) + ".png");
		} else {
			su.setNewPicPath(ConstantsJrc.PROJECT_PATH + getPackageName()
					+ ConstantsJrc.IMAGE_PATH + File.separator
					+ Commond.getMd5Hash(DateManager.getPhoneTime()) + ".png");
		}
		if (file.length() / 1024 <= 205) {
			Bitmap bmp = BitmapFactory.decodeFile(oldPicPath);
			picImageView.setImageBitmap(bmp);
		} else {
			Bitmap bmp = null;
			if ("pic".equals(type)) {
				bmp = ImageUtils.getBitmapByPath2(oldPicPath);
			} else if ("camera".equals(type)) {
				bmp = ImageUtils.getBitmapByPath(oldPicPath);
			}
			// if (bmp.getWidth() > 480) {
			// w = 480;
			// h = 800;
			// } else if (bmp.getWidth() > 800) {
			// w = 800;
			// h = 480;
			// } else {
			// w = bmp.getWidth();
			// h = bmp.getHeight();
			// }
			// Bitmap bmp2 = ImageUtils.extractThumbnail(bmp, w, h);
			picImageView.setImageBitmap(bmp);
		}

		// 判断是否为图片文件
		if (SystemUtil.checkIsPic(oldPicPath)) {
			loadThread.start();
		} else {
			picSizeTv.setText("文件大小<不是图片文件>："
					+ SystemUtil.CountSize(file.length()));
		}

		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setResult(ConstantsJrc.ERROR);
				finish();
			}
		});
		sendBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!SystemUtil.checkIsPic(oldPicPath)) {
					setResult(ConstantsJrc.ERROR);
					finish();
				} else {
					getIntent().putExtra("newurl", su.getNewPicPath());
					setResult(ConstantsJrc.PHOTORESOULT, getIntent());
					finish();
				}
			}
		});
	}

	class LoadThread extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				initData();
			}
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:
				// dialog.setCancelable(true);
				// dialog.show();
				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				// dialog.cancel();
				loadThread.stopThread(false);
				break;
			}
		};
	};

	private void initData() {
		handler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);

		handler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		handler.post(new Runnable() {

			@Override
			public void run() {
				File file = new File(oldPicPath);
				Bitmap bmp = null;
				Bitmap bmp1 = null;
				// if (file.length() / 1024 <= 205) {
				// bmp1 = BitmapFactory.decodeFile(oldPicPath);
				// } else {
				if ("pic".equals(type)) {
					bmp = ImageUtils.getBitmapByPath2(oldPicPath);
				} else if ("camera".equals(type)) {
					bmp = ImageUtils.getBitmapByPath(oldPicPath);
				}
				// }
				// bmp = ImageUtils.extractThumbnail(bmp1, w, h);
				File f = new File(su.getNewPicPath());

				try {
					f.createNewFile();
					FileOutputStream fOut = null;
					fOut = new FileOutputStream(f);
					bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
					fOut.flush();
					fOut.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				newPicSize = SystemUtil.CountSize(f.length());
				picSizeTv.setText("图片大小：" + newPicSize);
			}
		});
	}

}
