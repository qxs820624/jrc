package com.app.chatroom.mgmusic;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.util.Commond;
import com.cmsc.cmmusic.common.CMMusicCallback;
import com.cmsc.cmmusic.common.MusicQueryInterface;
import com.cmsc.cmmusic.common.RingbackManagerInterface;
import com.cmsc.cmmusic.common.data.MusicInfo;
import com.cmsc.cmmusic.common.data.MusicInfoResult;
import com.cmsc.cmmusic.common.data.Result;
import com.jianrencun.chatroom.R;

public class MusicPayDialog extends Activity {

	String musicId;
	int type; // 当前显示是彩铃0，手机铃声1
	MusicInfo mInfo;
	TextView message1_title_TextView;
	TextView message1_context_TextView;
	Button message1_ok_btn;
	Button message1_cancel_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message1_dialog);
		Intent intent = getIntent();
		musicId = intent.getStringExtra("musicId");
		type = intent.getIntExtra("type", 0);
		initView();
		initListener();
		new GetDateThread().start();
	}

	void initView() {
		message1_title_TextView = (TextView) findViewById(R.id.message1_title_TextView);
		message1_context_TextView = (TextView) findViewById(R.id.message1_context_TextView);
		message1_ok_btn = (Button) findViewById(R.id.message1_ok_btn);
		message1_cancel_btn = (Button) findViewById(R.id.message1_cancel_btn);
	}

	void initListener() {
		message1_ok_btn.setOnClickListener(listener);
		message1_cancel_btn.setOnClickListener(listener);
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.message1_cancel_btn:
				finish();
				break;
			case R.id.message1_ok_btn:
				RingbackManagerInterface.buyRingBack(MusicPayDialog.this,
						musicId, new CMMusicCallback<Result>() {
							@Override
							public void operationResult(Result ret) {
								if (null != ret) {
									Commond.showToast(getApplicationContext(),
											ret.getResMsg());
								}
							}
						});
				finish();
				break;
			default:
				break;
			}
		}
	};

	private class GetDateThread extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					getAllList();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Handler listhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:

				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:

				break;
			}
		};
	};

	private void getAllList() throws ClientProtocolException, IOException {
		listhandler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);

		MusicInfoResult result = MusicQueryInterface.getMusicInfoByMusicId(
				MusicPayDialog.this, musicId);
		mInfo = result.getMusicInfo();
		listhandler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		listhandler.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (mInfo != null) {
						switch (type) {
						case 0:
							message1_title_TextView.setText("订购彩铃");
							message1_context_TextView
									.setText("资费：2元/首，咪咕特级会员专享1.4元\n" + "有效期："
											+ mInfo.getRingValidity());
							break;
						case 1:
							break;
						default:
							break;
						}
					} else {
						Commond.showToast(getApplicationContext(), "网络出错");
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

}
