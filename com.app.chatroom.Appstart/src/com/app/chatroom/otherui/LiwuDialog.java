package com.app.chatroom.otherui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.http.HttpSongLiwu;
import com.app.chatroom.json.MessageJson;
import com.app.chatroom.json.bean.MessageBean;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;
import com.app.chatroom.view.NetImageView;
import com.jianrencun.android.Entrance;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.DazuiActivity;
import com.jianrencun.dazui.Dzmysave;
import com.jianrencun.dazui.Mydazui;
import com.jianrencun.dynamic.Dynamic;

public class LiwuDialog extends Activity {
	NetImageView logoImageView;// 图片
	EditText numEditText;// 数量
	ImageButton cancelBtn;// 取消按钮
	ImageButton okBtn;// 确定按钮
	ImageButton nullBtn;// 匿名按钮
	TextView markTextView;//
	int uid = 0;
	int touid = 0;
	int gid = 0;
	int flg = 0;// 匿名
	int src = 0;// 礼物路径
	int oid = 0;// 其他编号
	String mark = "";//
	boolean isnull = true;
	String num = "";
	String logo = "";
	SongLiwuThread songLiwuThread;
	ArrayList<MessageBean> messageList = new ArrayList<MessageBean>();
	SharedPreferences sp;
	SystemSettingUtilSp su;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.liwu_dialog);
		logoImageView = (NetImageView) findViewById(R.id.liwu_dialog_logo_ImageView);
		numEditText = (EditText) findViewById(R.id.liwu_dialog_editText);
		cancelBtn = (ImageButton) findViewById(R.id.liwu_dialog_cancel_btn);
		okBtn = (ImageButton) findViewById(R.id.liwu_dialog_ok_btn);
		nullBtn = (ImageButton) findViewById(R.id.liwu_dialog_null_ImageButton);
		markTextView = (TextView) findViewById(R.id.liwu_dialog_mark_TextView);
		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
		Intent intent = getIntent();
		uid = intent.getIntExtra("uid", 0);
		touid = intent.getIntExtra("touid", 0);
		gid = intent.getIntExtra("gid", 0);
		logo = intent.getStringExtra("logo");
		src = intent.getIntExtra("src", 0);
		oid = intent.getIntExtra("oid", 0);
		mark = intent.getStringExtra("mark");

		markTextView.setText(mark);
		logoImageView.setDrawingCacheEnabled(false);
		logoImageView.setImageUrl(logo, R.drawable.photo,
				Environment.getExternalStorageDirectory() + File.separator
						+ getPackageName() + ConstantsJrc.PHOTO_PATH,
				ConstantsJrc.PROJECT_PATH + getPackageName()
						+ ConstantsJrc.PHOTO_PATH);
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!numEditText.getText().toString().equals("")) {
					if (numEditText.getText().toString().equals("0")) {
						Commond.showToast(getApplicationContext(),
								"填个0你还送，你扣不扣");
					} else {
						if (numEditText.getText().toString().replace("0", "")
								.equals("")) {
							Commond.showToast(getApplicationContext(),
									"都是0，扣到家了");
							return;
						}
						num = numEditText.getText().toString();
						songLiwuThread = new SongLiwuThread();
						songLiwuThread.start();		
						Intent it = getIntent();
						setResult(30, it);
						finish();
					}
				} else {
					Commond.showToast(getApplicationContext(), "送礼数量不能为空");
				}

			}
		});

		nullBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isnull) {
					nullBtn.setBackgroundResource(R.drawable.liwu_null_select_btn);
					flg = 1;
					isnull = false;
				} else {
					nullBtn.setBackgroundResource(R.drawable.liwu_null_btn);
					flg = 0;
					isnull = true;
				}
			}
		});

	}

	private class SongLiwuThread extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					songliwu();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Handler liwuhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:

				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				songLiwuThread.stopThread(false);
				break;
			}
		};
	};

	private void songliwu() throws ClientProtocolException, IOException {
		liwuhandler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);

		final String result = HttpSongLiwu.LiwuGet(uid, touid, gid, num, flg,
				src, oid, su.getToken());

		// System.out.println(result);
		MessageJson mj = new MessageJson();
		messageList = mj.parseJson(result);

		liwuhandler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		liwuhandler.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (messageList.get(0).getRet().equals("0")) {
						if (!"".equals(messageList.get(0).getTip())) {
							Commond.showToast(getApplicationContext(),
									messageList.get(0).getTip());
						}
						finish();
					} else if (messageList.get(0).getRet().equals("1")) {
						if (!"".equals(messageList.get(0).getTip())) {
							Commond.showToast(getApplicationContext(),
									messageList.get(0).getTip());
						}
						finish();
					}else if (messageList.get(0).getRet().equals("3")) {
						if (!"".equals(messageList.get(0).getTip())) {
							Intent it = new Intent();
							it.putExtra("tishi", messageList.get(0).getTip());
							it.setClass(LiwuDialog.this, Entrance.class);
							it.putExtra("ret", 4);
							it.putExtra("whichto", 4);
							startActivityForResult(it, 90);
						}
						finish();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (DazuiActivity.player != null) {
			if (MainMenuActivity.borz == true) {
				DazuiActivity.player.start();
			}
		}
		if (Dzmysave.player != null) {
			if (MainMenuActivity.borz == true) {
				Dzmysave.player.start();
			}
		}
		if (Mydazui.player != null) {
			if (MainMenuActivity.borz == true) {
				Mydazui.player.start();
			}
		}
		if(Dynamic.dyplayer != null){
			if (MainMenuActivity.borz == true) {
				Dynamic.dyplayer.start();
			}
		}
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if (DazuiActivity.player != null && DazuiActivity.player.isPlaying()) {
//			if (detatilispause == false) {
				DazuiActivity.player.pause();
//			}
		}
		if (Dzmysave.player != null && Dzmysave.player.isPlaying()) {
//			if (detatilispause == false) {
				Dzmysave.player.pause();
//			}
		}
		if (Mydazui.player != null && Mydazui.player.isPlaying()) {
//			if (detatilispause == false) {
				Mydazui.player.pause();
//			}
		}
		super.onPause();
	}
}
