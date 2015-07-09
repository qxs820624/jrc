package com.app.chatroom.ui;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.http.HttpGetCode;
import com.app.chatroom.json.MessageJson;
import com.app.chatroom.json.UserInfoJson;
import com.app.chatroom.json.bean.MessageBean;
import com.app.chatroom.json.bean.UserBean;
import com.app.chatroom.otherui.Message0Dialog;
import com.app.chatroom.otherui.MessageSystemDialog;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;
import com.app.chatroom.util.EditTextUtil;
import com.jianrencun.chatroom.R;

public class ChooseIDActivity extends Activity {
	ImageButton backBtn;
	EditText choosephone_EditText;
	Button phone_code_btn;
	EditText chooseemail_EditText;
	Button email_code_btn;
	EditText input_code_EditText;
	Button user_login_Button;
	private Dialog dialog = null;
	ArrayList<UserBean> userList = new ArrayList<UserBean>();
	ArrayList<MessageBean> messageList = new ArrayList<MessageBean>();
	SharedPreferences sp;
	SystemSettingUtilSp su;
	ChooseIdThread chooseThread;
	ReLoginThread reLoginThread;
	String phone = "";
	String email = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_id);
		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
		dialog = new Dialog(this, R.style.theme_dialog_alert);
		dialog.setContentView(R.layout.dialog_layout);
		initView();

		// 启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		phone_code_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				phone = choosephone_EditText.getText().toString();
				email = "";
				if (EditTextUtil.VerificationPhone(choosephone_EditText
						.getText().toString())) {
					chooseThread = new ChooseIdThread();
					chooseThread.start();
				} else {
					Intent intent = new Intent(getApplicationContext(),
							MessageSystemDialog.class);
					intent.putExtra("ret", "0");
					intent.putExtra("tip", "请正确输入手机号码");
					startActivity(intent);
				}
			}
		});
		email_code_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				phone = "";
				email = chooseemail_EditText.getText().toString();

				if (EditTextUtil.VerificationEmail(chooseemail_EditText
						.getText().toString())) {
					chooseThread = new ChooseIdThread();
					chooseThread.start();
				} else {
					Intent intent = new Intent(getApplicationContext(),
							MessageSystemDialog.class);
					intent.putExtra("ret", "0");
					intent.putExtra("tip", "请正确输入邮箱地址");
					startActivity(intent);
				}
			}
		});

		user_login_Button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!input_code_EditText.getText().toString().equals("")) {
					reLoginThread = new ReLoginThread();
					reLoginThread.start();
				} else {
					Intent intent = new Intent(getApplicationContext(),
							MessageSystemDialog.class);
					intent.putExtra("ret", "0");
					intent.putExtra("tip", "请填入验证码");
					startActivity(intent);
				}
			}
		});
	}

	void initView() {
		backBtn = (ImageButton) findViewById(R.id.chooseid_left_btn);
		choosephone_EditText = (EditText) findViewById(R.id.choosephone_EditText);
		phone_code_btn = (Button) findViewById(R.id.phone_code_btn);
		chooseemail_EditText = (EditText) findViewById(R.id.chooseemail_EditText);
		email_code_btn = (Button) findViewById(R.id.email_code_btn);
		input_code_EditText = (EditText) findViewById(R.id.input_code_EditText);
		user_login_Button = (Button) findViewById(R.id.user_login_Button);
		// chooseemail_EditText.setText(su.getEmail());
		// choosephone_EditText.setText(su.getPhone());
	}

	class ChooseIdThread extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					ChooseId();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Handler chooseHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:
				dialog.setCancelable(true);
				dialog.show();
				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				dialog.cancel();
				chooseThread.stopThread(false);
				break;
			}
		};
	};

	private void ChooseId() throws ClientProtocolException, IOException {
		chooseHandler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);
		final String result = HttpGetCode
				.GetCodePost(su.getUid(), phone, email,su.getToken());
		// System.out.println(result);
		MessageJson mj = new MessageJson();
		messageList = mj.parseJson(result);
		chooseHandler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		chooseHandler.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (messageList.get(0).getRet().equals("1")) {

						Intent intent = new Intent(getApplicationContext(),
								MessageSystemDialog.class);
						intent.putExtra("ret",
								String.valueOf(messageList.get(0).getRet()));
						intent.putExtra("tip", messageList.get(0).getTip()
								.toString());
						startActivity(intent);
						chooseThread.stopThread(false);
						if (!chooseemail_EditText.getText().toString()
								.equals(""))
							su.setPhone(choosephone_EditText.getText()
									.toString());
						if (!chooseemail_EditText.getText().toString()
								.equals(""))
							su.setEmail(chooseemail_EditText.getText()
									.toString());
					} else {
						Intent intent = new Intent(getApplicationContext(),
								MessageSystemDialog.class);
						intent.putExtra("ret",
								String.valueOf(messageList.get(0).getRet()));
						intent.putExtra("tip", messageList.get(0).getTip()
								.toString());
						startActivity(intent);
					}
				} catch (Exception e) {
					// TODO: handle exception
					// Toast.makeText(getApplicationContext(), "网络状况不佳",
					// 0).show();
				}
			}
		});
	}

	class ReLoginThread extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					login();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:
				dialog.setCancelable(true);
				dialog.show();
				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				dialog.cancel();
				reLoginThread.stopThread(false);
				break;
			}
		};
	};

	private void login() throws ClientProtocolException, IOException {
		handler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);

		final String result = HttpGetCode.ReLoadPost(su.getUid(),
				choosephone_EditText.getText().toString(), chooseemail_EditText
						.getText().toString(), input_code_EditText.getText()
						.toString());

		// System.out.println(result);
		UserInfoJson uj = new UserInfoJson();
		userList = uj.parseJson(result);
		// System.out.println("用户：" + userList.toString());

		handler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		handler.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (userList.get(0).getRet() == 1) {
						su.setUid(String.valueOf(userList.get(0).getUid()));
						su.setNick(userList.get(0).getNick());
						su.setHeader(userList.get(0).getHeader());
						su.setSign(userList.get(0).getSign());
						su.setScore(String.valueOf(userList.get(0).getScore()));
						su.setLevel(userList.get(0).getLevel());
						su.setBirthday(userList.get(0).getBirthday());
						su.setCity(userList.get(0).getCity());
						//su.setQQ(String.valueOf(userList.get(0).getQq()));
						//su.setWeixin(userList.get(0).getWeixin());
						su.setSex(String.valueOf(userList.get(0).getSex()));
						reLoginThread.stopThread(false);
						Intent intent = new Intent(getApplicationContext(),
								MessageSystemDialog.class);
						intent.putExtra("ret",
								String.valueOf(userList.get(0).getRet()));
						intent.putExtra("tip", userList.get(0).getTip()
								.toString());
						startActivity(intent);
					} else {
						reLoginThread.stopThread(false);
						Commond.showToast(getApplicationContext(), userList.get(0).getTip()
								.toString());
						// Intent intent = new Intent(getApplicationContext(),
						// Message0Dialog.class);
						// intent.putExtra("ret",
						// String.valueOf(userList.get(0).getRet()));
						// intent.putExtra("tip", userList.get(0).getTip()
						// .toString());
						// startActivity(intent);
					}
				} catch (Exception e) {
					// TODO: handle exception
					Commond.showToast(getApplicationContext(), "网络状况不佳");
					// Intent intent = new Intent(getApplicationContext(),
					// MessageSystemDialog.class);
					// intent.putExtra("ret", "0");
					// intent.putExtra("tip", "网络状况不佳");
					// startActivity(intent);
				}
			}
		});
	}

}
