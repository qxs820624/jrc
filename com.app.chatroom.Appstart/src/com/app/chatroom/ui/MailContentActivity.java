package com.app.chatroom.ui;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.chatroom.adapter.MailContentAdapter;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.db.DbUtil;
import com.app.chatroom.http.HttpSendMsg;
import com.app.chatroom.json.MessageJson;
import com.app.chatroom.json.bean.MailBean;
import com.app.chatroom.json.bean.MessageBean;
import com.app.chatroom.otherui.VillageUserInfoDialog;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.DateManager;
import com.jianrencun.chatroom.R;

public class MailContentActivity extends Activity {
	ImageButton backBtn;
	ListView mailContentListView;
	Button sendBtn;
	EditText messageEditText;
	ProgressBar mailContentProgressBar;
	RelativeLayout focusly;
	RelativeLayout bottomRelative;
	String fuid = "";
	String fnick = "";
	/* 留言列表List */
	public ArrayList<MailBean> contentlist = new ArrayList<MailBean>();
	/* 数据库留言List */
	public ArrayList<MailBean> dblist = new ArrayList<MailBean>();
	public ArrayList<MessageBean> sendMessageList = new ArrayList<MessageBean>();
	private MailContentAdapter mailContentAdapter;
	MailBean mailBean;
	Cursor cursor;
	DbUtil db = new DbUtil(this);
	LoadDbThread loadThread;
	String contentString = "";

	SharedPreferences sp;
	SystemSettingUtilSp su;
	SendMailThread sendMailThread;
	LayoutInflater inflater;
	View view;
	TextView toastTextView;
	Toast toast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mail_content);
		// 启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);

		Intent intent = getIntent();
		fuid = intent.getStringExtra("fuid");
		fnick = intent.getStringExtra("fnick");
		db.Open();

		// 自定义Toast
		inflater = this.getLayoutInflater();
		view = inflater.inflate(R.layout.toast, null);
		toastTextView = (TextView) view.findViewById(R.id.toast_textView);
		toast = new Toast(this);
		toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 80);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(view);

		loadThread = new LoadDbThread();
		loadThread.start();
		backBtn = (ImageButton) findViewById(R.id.mail_content_acitivty_back_Button);
		sendBtn = (Button) findViewById(R.id.mail_send_btn);
		messageEditText = (EditText) findViewById(R.id.mail_message_editText);
		mailContentListView = (ListView) findViewById(R.id.mail_content_activity_listview);
		mailContentProgressBar = (ProgressBar) findViewById(R.id.mail_content_progressbar);
		focusly = (RelativeLayout) findViewById(R.id.focus_mail_content_ly);
		bottomRelative = (RelativeLayout) findViewById(R.id.mail_content_bottom_relative);

		messageEditText.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus == true) {

				} else {
					InputMethodManager m = (InputMethodManager) messageEditText
							.getContext().getSystemService(
									Context.INPUT_METHOD_SERVICE);
					m.hideSoftInputFromWindow(messageEditText.getWindowToken(),
							0);
				}
			}
		});
		if (fnick.equals("")) {
			messageEditText.setHint("");
		} else {
			messageEditText.setHint("对" + fnick + "留言:");
		}
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		sendBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendMailThread = new SendMailThread();
				contentString = messageEditText.getText().toString();
				if (contentString.length() > 0) {

					MailBean mMailBean = new MailBean();
					mMailBean.setTuid(fuid);
					mMailBean.setFuid(su.getUid());
					mMailBean.setFnick(su.getNick());
					mMailBean.setFheader(su.getHeader());
					mMailBean.setContent(contentString);
					mMailBean.setPtime(DateManager.getPhoneTime());
					mMailBean.setIscometype(3);
					sendMailThread.start();
					contentlist.add(mMailBean);
					mailContentListView.requestLayout();
					mailContentAdapter.notifyDataSetChanged();
					messageEditText.setText("");
					// System.out.println(mailContentListView.getCount() + "个");

					if (mailContentListView.getCount() > 0) {
						mailContentListView.setSelection(mailContentListView
								.getCount() - 1);
					}
				}

			}
		});

		mailContentAdapter = new MailContentAdapter(this, contentlist,
				infolisten);
		mailContentListView.setAdapter(mailContentAdapter);

	}

	// 查看他人资料
	OnClickListener infolisten = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			MailBean mBean = (MailBean) v.getTag();
			Intent intent = new Intent(getApplicationContext(),
					VillageUserInfoDialog.class);
			String uid = String.valueOf(mBean.getFuid());
			intent.putExtra("uid", uid);
			intent.putExtra("fuid", su.getUid());
			intent.putExtra("type", 2);
			intent.putExtra("src", 1);
			startActivityForResult(intent, 1);
		}
	};
	
	// dianji item
	OnClickListener itemclick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
//			MailBean mBean = (MailBean) v.getTag();
//			Intent intent = new Intent(getApplicationContext(),
//					VillageUserInfoDialog.class);
//			String uid = String.valueOf(mBean.getFuid());
//			intent.putExtra("uid", uid);
//			intent.putExtra("fuid", su.getUid());
//			intent.putExtra("type", 2);
//			intent.putExtra("src", 1);
//			startActivityForResult(intent, 1);
		}
	};

	/**
	 * 焦点切换
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		focus();
		return super.onTouchEvent(event);
	}

	public void focus() {
		focusly.setFocusable(true);
		focusly.requestFocus();
		focusly.setFocusableInTouchMode(true);
	}

	class LoadDbThread extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					loadDbData();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Handler loadDbhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:
				mailContentProgressBar.setVisibility(View.VISIBLE);
				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				mailContentProgressBar.setVisibility(View.GONE);
				mailContentListView.requestLayout();
				mailContentAdapter.notifyDataSetChanged();
				loadThread.stopThread(false);
				break;
			}
		};
	};

	private void loadDbData() throws ClientProtocolException, IOException {
		loadDbhandler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);
		cursor = db.getFuid2(Integer.parseInt(fuid),
				Integer.parseInt(su.getUid()));
		loadDbhandler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		loadDbhandler.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (cursor.getCount() > 0) {
						// System.out.println(cursor.getCount() + "");
						for (int i = 0; i < cursor.getCount(); i++) {
							cursor.moveToPosition(i);
							mailBean = new MailBean();
							mailBean.setFuid(cursor.getString(cursor
									.getColumnIndex("fuid")));
							mailBean.setFnick(cursor.getString(cursor
									.getColumnIndex("fnick")));
							mailBean.setFheader(cursor.getString(cursor
									.getColumnIndex("fheader")));
							mailBean.setContent(cursor.getString(cursor
									.getColumnIndex("content")));
							mailBean.setPtime(cursor.getString(cursor
									.getColumnIndex("ptime")));
							if (cursor.getString(2).equals(su.getUid()))
								mailBean.setIscometype(3);
							else
								mailBean.setIscometype(0);
							contentlist.add(mailBean);
							if (mailContentListView.getCount() > 0) {
								mailContentListView
										.setSelection(mailContentListView
												.getCount() - 1);
							}
						}
						cursor.close();
					} else {

					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

	}

	/**
	 * 
	 * 发送消息
	 * 
	 * @author J.King
	 * 
	 */
	class SendMailThread extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					sendMessage();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	private Handler msghandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:
				// dialog.setCancelable(true);
				// dialog.show();
				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				// dialog.cancel();
				sendMailThread.stopThread(false);
				break;
			}
		};
	};

	private void sendMessage() throws ClientProtocolException, IOException {
		msghandler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);
		final String result = HttpSendMsg.SendMailPost(su.getUid(), fuid,
				contentString, "", "", su.getToken());

		// System.out.println(result);
		MessageJson mj = new MessageJson();
		sendMessageList = mj.parseJson(result);
		// System.out.println("消息返回：" + sendMessageList.toString());
		msghandler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		msghandler.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (sendMessageList.get(0).getRet().equals("1")) {
						sendMailThread.stopThread(false);
						if (!sendMessageList.get(0).getTip().equals("")) {
							toastTextView.setText(sendMessageList.get(0)
									.getTip().toString());
							toast.show();
						}
					} else if (sendMessageList.get(0).getRet().equals("0")) {
						sendMailThread.stopThread(false);
						if (!sendMessageList.get(0).getTip().equals("")) {
							toastTextView.setText(sendMessageList.get(0)
									.getTip().toString());
							toast.show();
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		// db.Close();
		super.onDestroy();
	}

	// 点击任意地方取消软键盘
	@Override
	public boolean dispatchTouchEvent(MotionEvent e) {
		// if (e.getAction() == MotionEvent.ACTION_DOWN) {
		// //类型为Down才处理，还有Move/Up之类的
		Rect r = new Rect();
		bottomRelative.getGlobalVisibleRect(r);
		if (r.contains((int) e.getX(), (int) e.getY())) {
			return super.dispatchTouchEvent(e);
		} else {
			if (!messageEditText.hasFocus()) {
				return super.dispatchTouchEvent(e);
			} else {
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.hideSoftInputFromWindow(
						messageEditText.getWindowToken(), 0);
				messageEditText.clearFocus();
				return false;
			}
		}
	}
}
