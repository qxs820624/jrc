package com.app.chatroom.otherui;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.http.HttpExitRoom;
import com.app.chatroom.json.MessageJson;
import com.app.chatroom.json.bean.MessageBean;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.ui.ChatRoomActivity;
import com.jianrencun.chatroom.R;

public class ExitDialog extends Activity {
	Button okBtn;
	Button cancelBtn;
	TextView tipTextView;
	private Dialog dialog = null;
	public ArrayList<MessageBean> exitMessageList = new ArrayList<MessageBean>();
	/* 配置文件 */
	SharedPreferences sp;
	SystemSettingUtilSp su;
	ExitRoomThread extiRoomThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exit_dialog);
		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
		// 进度条实例化，为了返回销毁
		dialog = new Dialog(this, R.style.theme_dialog_alert);
		dialog.setContentView(R.layout.dialog_layout);
		okBtn = (Button) findViewById(R.id.exit_ok_btn);
		cancelBtn = (Button) findViewById(R.id.exit_cancel_btn);
		tipTextView = (TextView) findViewById(R.id.exit_context_TextView);
		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getIntent().putExtra("isexit", true);
				setResult(404, getIntent());
				finish();
				ChatRoomActivity.instance.receiveTime.cancel();
				ChatRoomActivity.instance.addTime.cancel();
				ChatRoomActivity.instance.systemMsgTime.cancel();
				su.setMsgPid("");
				ChatRoomActivity.instance.finish();
			}
		});
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getIntent().putExtra("isexit", true);
				setResult(404, getIntent());
				finish();
			}
		});
	}

	class ExitRoomThread extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					exitRoom();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Handler roomhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:
				// dialog.setCancelable(true);
				// dialog.show();
				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				// dialog.cancel();
				break;
			}
		};
	};

	private void exitRoom() throws ClientProtocolException, IOException {
		roomhandler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);

		final String result = HttpExitRoom.ExitRoomGet(1, su.getUid(),su.getToken());

		System.out.println(result);
		MessageJson mj = new MessageJson();
		exitMessageList = mj.parseJson(result);
		System.out.println("退出房间：" + exitMessageList.toString());

		roomhandler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		roomhandler.post(new Runnable() {

			@Override
			public void run() {
				if (exitMessageList.get(0).getRet().equals("1")) {
					finish();
					ChatRoomActivity.instance.receiveTime.cancel();
					ChatRoomActivity.instance.addTime.cancel();
					su.setMsgPid("");
					ChatRoomActivity.instance.finish();
					extiRoomThread.stopThread(false);
				} else {

				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		getIntent().putExtra("isexit", true);
		setResult(404, getIntent());
		finish();
		super.onDestroy();
	}
}
