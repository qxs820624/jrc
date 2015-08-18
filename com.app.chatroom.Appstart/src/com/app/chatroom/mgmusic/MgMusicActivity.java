package com.app.chatroom.mgmusic;

import java.util.Hashtable;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.app.chatroom.util.Commond;
import com.cmsc.cmmusic.init.InitCmmInterface;
import com.jianrencun.chatroom.R;

/**
 * 咪咕音乐首页
 * 
 * @author Administrator
 * 
 */
public class MgMusicActivity extends Activity {
	ImageButton mgmusic_close_btn;
	LinearLayout group1;// 咪咕客户端下载
	LinearLayout group2;// 音乐榜单
	LinearLayout group3;// 贱人电台
	LinearLayout group4;// 贱曲包月
	LinearLayout group5;// 贱币兑换
	private UIHandler mUIHandler = new UIHandler();
	Hashtable<String, String> initResult = new Hashtable<String, String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mgmusic_dialog);
		initView();
		initListener();
		InitCmmInterface.initSDK(this);
		if (!InitCmmInterface.initCheck(getApplicationContext())) {
			new Thread(new T1()).start();
		} else {
			Commond.showToast(getApplicationContext(), "已经成功初始化数据");
		}

 
	}

	void initView() {
		mgmusic_close_btn = (ImageButton) findViewById(R.id.mgmusic_close_btn);
		group1 = (LinearLayout) findViewById(R.id.group1);
		group2 = (LinearLayout) findViewById(R.id.group2);
		group3 = (LinearLayout) findViewById(R.id.group3);
		group4 = (LinearLayout) findViewById(R.id.group4);
		group5 = (LinearLayout) findViewById(R.id.group5);
	}

	void initListener() {
		mgmusic_close_btn.setOnClickListener(listener);
		group1.setOnClickListener(listener);
		group2.setOnClickListener(listener);
		group3.setOnClickListener(listener);
		group4.setOnClickListener(listener);
		group5.setOnClickListener(listener);
	}

	private class UIHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case 0:
				if (msg.obj == null) {
					Commond.showToast(getApplicationContext(), "初始化失败");
					return;
				}
				System.out.println(initResult);
				if (null != initResult) {
					Commond.showToast(getApplicationContext(),
							initResult.get("desc").toString());
				}
				break;
			}
		}
	}

	class T1 extends Thread {
		@Override
		public void run() {
			super.run();
			Looper.prepare();

			initResult = InitCmmInterface.initCmmEnv(MgMusicActivity.this);
			Message m = new Message();
			m.what = 0;
			m.obj = initResult;
			mUIHandler.sendMessage(m);

			Looper.loop();
		}
	}

	OnClickListener listener = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.mgmusic_close_btn:
				finish();
				break;
			case R.id.group1:
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				Uri content_url = Uri
						.parse("http://wm.10086.cn/view/html5/download.do?cType=sst_client&cid=0147301&nodeId=7638&ucid=bjstsk&autodown=s");
				intent.setData(content_url);
				startActivity(intent);
				break;
			case R.id.group2:
				Intent intentgroup2 = new Intent(getApplicationContext(),
						AllListActivity.class);
				startActivity(intentgroup2);
				break;
			case R.id.group3:
				Intent intentgroup3 = new Intent(getApplicationContext(),
						MgRadioActivity.class);
				startActivity(intentgroup3);
				break;
			case R.id.group4:
				Intent intentgroup4 = new Intent(getApplicationContext(),
						MonthlyActivity.class);
				startActivity(intentgroup4);
				break;
			case R.id.group5:
				Intent intentgroup5 = new Intent(getApplicationContext(),
						PropActivity.class);
				startActivity(intentgroup5);
				break;
			default:
				break;
			}
		}
	};

}
