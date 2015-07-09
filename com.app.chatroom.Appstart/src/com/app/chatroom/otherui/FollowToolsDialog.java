package com.app.chatroom.otherui;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.http.HttpFollowMove;
import com.app.chatroom.json.MessageJson;
import com.app.chatroom.json.bean.MessageBean;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;
import com.jianrencun.chatroom.R;

public class FollowToolsDialog extends Activity {

	ImageButton followTopButton;
	ImageButton followBottomButton;
	ImageButton followUpButton;
	ImageButton followDownButton;
	String uid = "";
	String tuid = "";
	int flg = 0;
	SharedPreferences sp;
	SystemSettingUtilSp su;
	ArrayList<MessageBean> messageList = new ArrayList<MessageBean>();
	FollowMoveThread followMoveThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.follow_tools_dialog);
		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
		Intent intent = getIntent();
		uid = intent.getStringExtra("uid");
		tuid = intent.getStringExtra("tuid");
		followTopButton = (ImageButton) findViewById(R.id.follow_movetop_btn);
		followBottomButton = (ImageButton) findViewById(R.id.follow_movebottom_btn);
		followUpButton = (ImageButton) findViewById(R.id.follow_moveup_btn);
		followDownButton = (ImageButton) findViewById(R.id.follow_movedown_btn);

		followTopButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				followMoveThread = new FollowMoveThread();
				flg = 1;
				followMoveThread.start();
			}
		});
		followUpButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				followMoveThread = new FollowMoveThread();
				flg = 2;
				followMoveThread.start();

			}
		});
		followDownButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				followMoveThread = new FollowMoveThread();
				flg = 3;
				followMoveThread.start();

			}
		});
		followBottomButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				followMoveThread = new FollowMoveThread();
				flg = 4;
				followMoveThread.start();

			}
		});

	}

	private class FollowMoveThread extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					followmove();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Handler followmovehandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:

				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				followMoveThread.stopThread(false);
				break;
			}
		};
	};

	private void followmove() throws ClientProtocolException, IOException {
		followmovehandler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);

		final String result = HttpFollowMove.FollowMoveGet(
				Integer.parseInt(uid), Integer.parseInt(tuid), flg,
				su.getToken());

		// System.out.println(result);
		MessageJson mj = new MessageJson();
		messageList = mj.parseJson(result);
		System.out.println(messageList.get(0).toString());
		followmovehandler
				.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		followmovehandler.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (messageList.get(0).getRet().equals("0")) {
						Commond.showToast(getApplicationContext(), messageList
								.get(0).getTip());

						followmovehandler
								.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
						finish();
					} else if (messageList.get(0).getRet().equals("1")) {
						Commond.showToast(getApplicationContext(), messageList
								.get(0).getTip());
						getIntent().putExtra("move", 1);
						setResult(21, getIntent());
						followmovehandler
								.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
						finish();

					} else {
						Commond.showToast(getApplicationContext(), "请求出错");
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
