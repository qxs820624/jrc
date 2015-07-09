package com.jianrencun.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.otherui.SystemMsgWebDialog;
import com.app.chatroom.util.PhoneInfo;
import com.jianrencun.chatroom.R;

public class TopRight extends Activity {
	ImageButton myshoucang;
	ImageButton myshangchuan;
	ImageButton bjshangchuan;
	ImageButton ishenhe;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		/** 全屏设置，隐藏窗口所有装饰 */
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.topright);
		myshoucang = (ImageButton) findViewById(R.id.myshoucangbtn);
		myshangchuan = (ImageButton) findViewById(R.id.myshangchuanbtn);
		bjshangchuan = (ImageButton) findViewById(R.id.bjshangchuanbtn);
		ishenhe =  (ImageButton) findViewById(R.id.shenhebtn);
		myshoucang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(TopRight.this, Mysave.class);
				startActivity(it);
				finish();
			}
		});
		
		ishenhe.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentshop = new Intent(getApplicationContext(),
						SystemMsgWebDialog.class);
				intentshop.putExtra(
						"link",
						ConstantsJrc.MAINMORE
								+ "?uid="
								+ MainMenuActivity.uid
								+ "&flg=15&w="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getWidth(TopRight.this)
								+ "&pkg="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getPackage(TopRight.this)
								+ "&ver="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getVersionName(TopRight.this));
				intentshop.putExtra("type", "15");
				startActivity(intentshop);
				finish();
			}
		});
		

		myshangchuan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(TopRight.this, Shangchuan.class);
				it.putExtra("tag", 1);
				startActivity(it);
				finish();
			}
		});

		bjshangchuan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(TopRight.this, Shangchuan.class);
				it.putExtra("tag", 2);
				startActivity(it);
				finish();
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}
}
