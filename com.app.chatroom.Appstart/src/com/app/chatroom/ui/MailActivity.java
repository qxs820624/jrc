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
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.app.chatroom.adapter.MailListAdapter;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.db.DbUtil;
import com.app.chatroom.json.bean.MailBean;
import com.app.chatroom.json.bean.MessageBean;
import com.app.chatroom.otherui.DeleteMailDialog;
import com.app.chatroom.otherui.MessageSystemDialog;
import com.app.chatroom.otherui.VillageUserInfoDialog;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;
import com.jianrencun.chatroom.R;

public class MailActivity extends Activity {
	ImageButton mailBcakBtn;// 后退按钮
	ImageButton mailDelBtn;// 删除按钮
	ImageView mailNoImage;// 无私信图
	ImageView mail_acitivty_del_all_Button;// 删除全部邮件
	ListView mailListView;
	private Dialog dialog = null;
	SharedPreferences sp;
	SystemSettingUtilSp su;
	ArrayList<MailBean> mailBeanlist = new ArrayList<MailBean>();
	ArrayList<MessageBean> messageList = new ArrayList<MessageBean>();
	MailBean mMailBean;
	DbUtil db = new DbUtil(this);
	LoadMailThread loadMailThread;
	MailListAdapter mailListAdapter;
	boolean IsDel = true;
	int countMail = 0;
	public static MailActivity instance = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mail);
		instance = this;
		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
		db.Open();

		// 进度条实例化，为了返回销毁
		dialog = new Dialog(this, R.style.theme_dialog_alert);
		dialog.setContentView(R.layout.dialog_layout);
		loadMailThread = new LoadMailThread();
		loadMailThread.start();
		mailBcakBtn = (ImageButton) findViewById(R.id.mail_acitivty_back_Button);
		mailDelBtn = (ImageButton) findViewById(R.id.mail_acitivty_del_Button);
		mailNoImage = (ImageView) findViewById(R.id.mail_no_mail_ImageView);
		mailListView = (ListView) findViewById(R.id.mail_activity_listview);
		mail_acitivty_del_all_Button = (ImageView) findViewById(R.id.mail_acitivty_del_all_Button);

		mailBcakBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();

			}
		});

		mail_acitivty_del_all_Button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(getApplicationContext(),
						DeleteMailDialog.class);
				intent.putExtra("ret", "0");
				intent.putExtra("tip", "是否删除所有信件？");
				startActivity(intent);
			}
		});

		mailDelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (db.getAllData().getCount() > 0) {
					if (IsDel) {
						mailDelBtn.setImageDrawable(getResources().getDrawable(
								R.drawable.mail_del_btn_select));
						mailListAdapter.showDeleteButton(true);
						IsDel = false;
						mail_acitivty_del_all_Button
								.setVisibility(View.VISIBLE);
						mailListView.requestLayout();
						mailListAdapter.notifyDataSetChanged();
					} else {
						mailDelBtn.setImageDrawable(getResources().getDrawable(
								R.drawable.mail_del_btn));
						mailListAdapter.showDeleteButton(false);
						mail_acitivty_del_all_Button.setVisibility(View.GONE);
						IsDel = true;
						mailListView.requestLayout();
						mailListAdapter.notifyDataSetChanged();

					}
				} else {
					Intent intent = new Intent(getApplicationContext(),
							MessageSystemDialog.class);
					intent.putExtra("ret", "0");
					intent.putExtra("tip", "没有可删除信件");
					startActivity(intent);
				}

			}
		});

	}

	public void DeleteAllMail() {
		db.deleteAll();
		mailListAdapter = new MailListAdapter(getApplicationContext(),
				mailListView, listen, listen2, infolisten);
		mailListView.setAdapter(mailListAdapter);
		mailNoImage.setVisibility(View.VISIBLE);
		mail_acitivty_del_all_Button.setVisibility(View.GONE);
		mailDelBtn.setImageDrawable(getResources().getDrawable(
				R.drawable.mail_del_btn));
	}

	// 查看他人资料
	OnClickListener infolisten = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String uid = "";
			MailBean mBean = (MailBean) v.getTag();
			Intent intent = new Intent(getApplicationContext(),
					VillageUserInfoDialog.class);
			if (mBean.getFuid().equals(su.getUid())) {
				uid = String.valueOf(mBean.getTuid());
			} else {
				uid = String.valueOf(mBean.getFuid());
			}

			intent.putExtra("uid", uid);
			intent.putExtra("fuid", su.getUid());
			intent.putExtra("type", 2);
			intent.putExtra("src", 1);
			startActivityForResult(intent, 1);
		}
	};

	// 每项点击跳转
	OnClickListener listen = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
           try {				
			MailBean mailBean = (MailBean) v.getTag();
			Intent intent = new Intent(getApplicationContext(),
					MailContentActivity.class);
			if (mailBean.getFuid().equals(su.getUid())) {
				intent.putExtra("fuid", mailBean.getTuid());
				intent.putExtra("fnick", mailBean.getTnick());
			} else {
				intent.putExtra("fuid", mailBean.getFuid());
				intent.putExtra("fnick", mailBean.getFnick());
			}
			if (mailBean.getFuid().equals(su.getUid())) {
				db.updateIsread2(Integer.parseInt(mailBean.getTuid()), "0");
			} else {
				db.updateIsread(Integer.parseInt(mailBean.getFuid()), "0");
			}
			startActivity(intent);
       	} catch (Exception e) {
			// TODO: handle exception
       		Commond.showToast(MailActivity.this, "sorry,出错了！");
       		finish();
		  }
		}
	};

	// 每项删除按钮事件
	OnClickListener listen2 = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			MailBean mailBean = (MailBean) v.getTag();
			// Log.i("text", mailBean.toString());
			if (mailBean.getFuid().equals(su.getUid())) {
				db.delete2(Integer.parseInt(mailBean.getTuid()));
			} else {
				db.delete(Integer.parseInt(mailBean.getFuid()));
			}

			if (db.getAllData().getCount() == 0) {
				mailNoImage.setVisibility(View.VISIBLE);
				IsDel = true;
				mailDelBtn.setImageDrawable(getResources().getDrawable(
						R.drawable.mail_del_btn));
				mail_acitivty_del_all_Button.setVisibility(View.GONE);
			} else {
				mailNoImage.setVisibility(View.GONE);
			}
			mailListView.requestLayout();
			mailListAdapter.notifyDataSetChanged();
		}
	};

	class LoadMailThread extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					LoadingData();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Handler mailhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:
				dialog.setCancelable(true);
				dialog.show();
				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				dialog.cancel();
				loadMailThread.stopThread(false);
				// System.out.println(countMail + "条");
				if (countMail > 0) {
					mailNoImage.setVisibility(View.GONE);
					mailListAdapter = new MailListAdapter(
							getApplicationContext(), mailListView, listen,
							listen2, infolisten);
					mailListView.setAdapter(mailListAdapter);
				} else {
					mailNoImage.setVisibility(View.VISIBLE);
				}
				break;
			}
		};
	};

	private void LoadingData() throws ClientProtocolException, IOException {
		mailhandler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);

		mailhandler.post(new Runnable() {

			@Override
			public void run() {
				try {
					countMail = db.getAllData().getCount();
				} catch (Exception e) {

				}
			}
		});

		mailhandler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		mailListView.requestLayout();
		mailListAdapter.notifyDataSetChanged();
		super.onRestart();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		// db.Close();
		if(mailListAdapter !=null ){
			if(mailListAdapter.cursor!= null){
			mailListAdapter.cursor.close();
			mailListAdapter.cursor = null ;
			}
		}
		super.onDestroy();
	}
}
