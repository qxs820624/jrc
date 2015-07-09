package com.app.chatroom.otherui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.http.HttpFollow;
import com.app.chatroom.http.HttpOnlineUserInfo;
import com.app.chatroom.json.MessageJson;
import com.app.chatroom.json.OnlineUserInfoJson;
import com.app.chatroom.json.bean.MessageBean;
import com.app.chatroom.json.bean.UserBean;
import com.app.chatroom.pic.AsynImageLoader;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.ui.UserInfoActivity;
import com.app.chatroom.util.Commond;
import com.app.chatroom.view.NetImageView;
import com.jianrencun.chatroom.R;

public class UserInfoDialog extends Activity {
	String uid = "";
	String fuid = "";
	String nick = "";
	int flg = 0;
	ArrayList<UserBean> onlineUserInfoList = new ArrayList<UserBean>();
	LoadDataThread loadThread;
	NetImageView chatroom_userinfo_photo;
	TextView chatroom_userinfoid_TextView;
	TextView chatroom_userinfoscore_TextView;
	TextView chatroom_userinfolevel_TextView;
	TextView chatroom_userinfonick_TextView;
	TextView chatroom_userinfosign_TextView;
	ImageView chatroom_userinfosex_ImageView;
	TextView chatroom_userinfocity_TextView;
	TextView chatroom_userinfobirthday_TextView;
	ImageButton userinfo_speakButton;
	ImageButton userinfo_closeButton;
	ProgressBar onlineuserinfo_progressbar;
	RelativeLayout onlineuserinfo_progressbarRelativeLayout;
	ImageButton chatroom_userinfo_edit_btn , infoedit;
	TextView userinfo_guanzhu_number_TextView;
	TextView userinfo_fensi_number_TextView;
	TextView userinfo_shangchuan_number_TextView;
	TextView userinfo_liwu_number_TextView;
	/* 配置文件 */
	SharedPreferences sp;
	SystemSettingUtilSp su;
	AsynImageLoader asynImageLoader;

	/** 自定义Toast **/
	LayoutInflater inflater;
	View view;
	TextView toastTextView;
	Toast toast;
	ArrayList<MessageBean> messageList = new ArrayList<MessageBean>();
	ArrayList<MessageBean> messageList2 = new ArrayList<MessageBean>();
	FollowThread followThread;
	FollowThread2 followThread2;
	boolean isfollow = true;

	private LinearLayout ll ;
	private ImageView iv ;
	 DisplayMetrics dm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userinfo_dialog_new);
		initView();
		asynImageLoader = new AsynImageLoader(this);
		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
		
		// 自定义Toast
		inflater = this.getLayoutInflater();
		view = inflater.inflate(R.layout.toast, null);
		toastTextView = (TextView) view.findViewById(R.id.toast_textView);
		toast = new Toast(this);
		toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 80);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(view);

		Intent intent = this.getIntent();
		uid = intent.getStringExtra("uid");
		fuid = intent.getStringExtra("fuid");
		
		if (!uid.equals(fuid)) {
			chatroom_userinfo_edit_btn
					.setBackgroundResource(R.drawable.chatroom_userinfo_dialog_xiaoguanzhu_btn);
		}

		loadThread = new LoadDataThread();
		loadThread.start();
		userinfo_closeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				nick = "";
				finish();
			}
		});

		userinfo_speakButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (uid.equals(su.getUid())) {
					toastTextView.setText("和自己说话脑袋有问题");
					toast.show();
					return;
				} else {
					getIntent().putExtra("touid", uid);
					getIntent().putExtra("tonick", nick);
					setResult(2020, getIntent());
					finish();
				}

			}
		});

		chatroom_userinfo_edit_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (uid.equals(fuid)) {
					Intent intent = new Intent(getApplicationContext(),
							UserInfoActivity.class);
					startActivityForResult(intent, 1);
				} else {
					// System.out.println("isf:"
					//		+ onlineUserInfoList.get(0).getIsf());
					if (onlineUserInfoList.get(0).getIsf() == 0) {
						if (isfollow) {
							flg = 1;
							followThread = new FollowThread();
							followThread.start();
							isfollow = false;
						} else {
							flg = 2;
							followThread2 = new FollowThread2();
							followThread2.start();
							isfollow = true;
						}
					} else {
						if (isfollow) {
							flg = 2;
							followThread2 = new FollowThread2();
							followThread2.start();
							isfollow = false;
						} else {
							flg = 1;
							followThread = new FollowThread();
							followThread.start();
							isfollow = true;
						}
					}
				}
			}
		});
	       iv.setOnClickListener(new OnClickListener() {
	   		
	   		@Override
	   		public void onClick(View v) {
	   			// TODO Auto-generated method stub
	   			
//	   			Intent intent = new Intent(Intent.ACTION_PICK, null);
//	   			intent.setClass(UserInfoDialog.this, Choosewhich.class);
//	   			startActivityForResult(intent, 5);
//	   			haomiao = String.valueOf(System.currentTimeMillis());
	   			
//	   			iv2= new ImageView(v.getContext());
//	   			iv2.setTag(i);
//	   	        iv2.setBackgroundResource(R.drawable.makesure_bk);
//	   	        ll.addView(iv2);
//	   	        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams)iv2.getLayoutParams();
//	   	        linearParams.width=dm.widthPixels*1/6;
//	   	        linearParams.height=linearParams.width;
//	   	        linearParams.leftMargin = dm.widthPixels*1/6/6;
//	   	        iv2.setLayoutParams(linearParams);
//	   	        i++;
//	   	        iv2.setOnClickListener(new OnClickListener() {
//	   	    		
//	   	    		@Override
//	   	    		public void onClick(View v) {
//	   	    			// TODO Auto-generated method stub
//	   	    			if(v.getTag().equals(0)){
//	   	    				Toast.makeText(MainActivity.this, "0", 2000).show();
//	   	    			}
//	   	    			if(v.getTag().equals(1)){
//	   	    				Toast.makeText(MainActivity.this, "1", 2000).show();
//	   	    			}
//	   	    			if(v.getTag().equals(2)){
//	   	    				Toast.makeText(MainActivity.this, "2", 2000).show();
//	   	    			}
//	   	    		}
//	   	    	});
	   		}
	   	});
	}

	void initView() {
		infoedit =  (ImageButton) findViewById(R.id.info_edit);
		chatroom_userinfo_photo = (NetImageView) findViewById(R.id.chatroom_userinfo_photo);
		chatroom_userinfoid_TextView = (TextView) findViewById(R.id.chatroom_userinfoid_TextView);
		chatroom_userinfoscore_TextView = (TextView) findViewById(R.id.chatroom_userinfoscore_TextView);
		chatroom_userinfolevel_TextView = (TextView) findViewById(R.id.chatroom_userinfolevel_TextView);
		chatroom_userinfonick_TextView = (TextView) findViewById(R.id.chatroom_userinfonick_TextView);
		chatroom_userinfosign_TextView = (TextView) findViewById(R.id.chatroom_userinfosign_TextView);
		chatroom_userinfosex_ImageView = (ImageView) findViewById(R.id.chatroom_userinfosex_ImageView);
		chatroom_userinfocity_TextView = (TextView) findViewById(R.id.chatroom_userinfocity_TextView);
		chatroom_userinfobirthday_TextView = (TextView) findViewById(R.id.chatroom_userinfobirthday_TextView);
		userinfo_speakButton = (ImageButton) findViewById(R.id.chatroom_userinfo_speak_btn);
		userinfo_closeButton = (ImageButton) findViewById(R.id.chatroom_userinfo_close_btn);
		onlineuserinfo_progressbar = (ProgressBar) findViewById(R.id.onlineuserinfo_progressbar);
		onlineuserinfo_progressbarRelativeLayout = (RelativeLayout) findViewById(R.id.onlineuserinfo_progressbar_RelativeLayout);
		chatroom_userinfosign_TextView
				.setMovementMethod(ScrollingMovementMethod.getInstance()); // TextView滚动
		chatroom_userinfo_edit_btn = (ImageButton) findViewById(R.id.chatroom_userinfo_edit_btn);
		userinfo_guanzhu_number_TextView = (TextView) findViewById(R.id.userinfo_guanzhu_number_TextView);
		userinfo_fensi_number_TextView = (TextView) findViewById(R.id.userinfo_fensi_number_TextView);
		//userinfo_shangchuan_number_TextView = (TextView) findViewById(R.id.userinfo_shangchuan_number_TextView);
		userinfo_liwu_number_TextView = (TextView) findViewById(R.id.userinfo_liwu_number_TextView);
	
//		ll = (LinearLayout) findViewById(R.id.ll);
		iv = (ImageView)findViewById(R.id.add);
		int screenWidth  = getWindowManager().getDefaultDisplay().getWidth();  
		 dm = new DisplayMetrics();   
	     getWindowManager().getDefaultDisplay().getMetrics(dm);   	  
				
	     RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams)iv.getLayoutParams();
        linearParams.width=dm.widthPixels*1/6;
        linearParams.height=linearParams.width;
        linearParams.leftMargin = dm.widthPixels*1/6/6;
        iv.setLayoutParams(linearParams);
	}

	class LoadDataThread extends Thread {
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
				onlineuserinfo_progressbar.setVisibility(View.VISIBLE);
				onlineuserinfo_progressbarRelativeLayout
						.setVisibility(View.VISIBLE);
				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				onlineuserinfo_progressbar.setVisibility(View.GONE);
				onlineuserinfo_progressbarRelativeLayout
						.setVisibility(View.GONE);
				loadThread.stopThread(false);
				break;
			}
		};
	};

	private void login() throws ClientProtocolException, IOException {
		handler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);

		final String result = HttpOnlineUserInfo.LoadDataGet(uid, fuid,su.getToken());

		// System.out.println(result);
		OnlineUserInfoJson ouj = new OnlineUserInfoJson();
		onlineUserInfoList = ouj.parseJson(result);
		// System.out.println("用户：" + onlineUserInfoList.toString());

		handler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		handler.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (onlineUserInfoList.get(0).getRet() == 1) {
						chatroom_userinfoid_TextView.setText(String
								.valueOf(onlineUserInfoList.get(0).getUid()));
						chatroom_userinfonick_TextView
								.setText(onlineUserInfoList.get(0).getNick());
						nick = onlineUserInfoList.get(0).getNick();
						chatroom_userinfosign_TextView
								.setText(onlineUserInfoList.get(0).getSign());
						chatroom_userinfoscore_TextView.setText(String
								.valueOf(onlineUserInfoList.get(0).getScore()));
						chatroom_userinfolevel_TextView
								.setText(onlineUserInfoList.get(0).getLevel());
						chatroom_userinfobirthday_TextView
								.setText(onlineUserInfoList.get(0)
										.getBirthday());
						chatroom_userinfocity_TextView
								.setText(onlineUserInfoList.get(0).getCity());
						userinfo_guanzhu_number_TextView.setText(String
								.valueOf(onlineUserInfoList.get(0).getFc()));
						userinfo_fensi_number_TextView.setText(String
								.valueOf(onlineUserInfoList.get(0).getFc1()));
//						userinfo_shangchuan_number_TextView.setText(String
//								.valueOf(onlineUserInfoList.get(0).getPc()));
						userinfo_liwu_number_TextView.setText(String
								.valueOf(onlineUserInfoList.get(0).getGc()));

						if (!uid.equals(fuid)) {
							switch (onlineUserInfoList.get(0).getIsf()) {
							case 0:
								chatroom_userinfo_edit_btn
										.setBackgroundResource(R.drawable.chatroom_userinfo_dialog_xiaoguanzhu_btn);
								break;
							case 1:
								chatroom_userinfo_edit_btn
										.setBackgroundResource(R.drawable.chatroom_userinfo_dialog_cancelguanzhu_btn);
							}

						}
						switch (Integer.parseInt(onlineUserInfoList.get(0)
								.getSex())) {
						case 0:
							chatroom_userinfosex_ImageView
									.setImageDrawable(getResources()
											.getDrawable(R.drawable.what));
							break;
						case 1:
							chatroom_userinfosex_ImageView
									.setImageDrawable(getResources()
											.getDrawable(R.drawable.woman));
							break;
						case 2:
							chatroom_userinfosex_ImageView
									.setImageDrawable(getResources()
											.getDrawable(R.drawable.man));
							break;
						}

						chatroom_userinfo_photo.setImageUrl(onlineUserInfoList
								.get(0).getHeader(), R.drawable.photo,
								Environment.getExternalStorageDirectory()
										+ File.separator + getPackageName()
										+ ConstantsJrc.PHOTO_PATH,
								ConstantsJrc.PROJECT_PATH + getPackageName()
										+ ConstantsJrc.PHOTO_PATH);
					}
				} catch (Exception e) {
					// TODO: handle exception

				}
			}
		});
	}

	private class FollowThread extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					follow();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Handler followhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:

				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				followThread.stopThread(false);
				break;
			}
		};
	};

	private void follow() throws ClientProtocolException, IOException {
		followhandler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);

		final String result = HttpFollow.FollowGet(Integer.parseInt(fuid),
				Integer.parseInt(uid), flg,su.getToken());

		// System.out.println(result);
		MessageJson mj = new MessageJson();
		messageList = mj.parseJson(result);

		followhandler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		followhandler.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (messageList.get(0).getRet().equals("0")) {
						Commond.showToast(getApplicationContext(), messageList
								.get(0).getTip());
						chatroom_userinfo_edit_btn
								.setBackgroundResource(R.drawable.chatroom_userinfo_dialog_xiaoguanzhu_btn);
					} else if (messageList.get(0).getRet().equals("1")) {
						Commond.showToast(getApplicationContext(), messageList
								.get(0).getTip());
						chatroom_userinfo_edit_btn
								.setBackgroundResource(R.drawable.chatroom_userinfo_dialog_cancelguanzhu_btn);
						//onlineUserInfoList.get(0).setIsf(1);
					} else {
						Commond.showToast(getApplicationContext(), "请求出错");
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

	private class FollowThread2 extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					follow2();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Handler followhandler2 = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:

				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				followThread2.stopThread(false);
				break;
			}
		};
	};

	private void follow2() throws ClientProtocolException, IOException {
		followhandler2.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);

		final String result = HttpFollow.FollowGet(Integer.parseInt(fuid),
				Integer.parseInt(uid), flg,su.getToken());

		System.out.println(result);
		MessageJson mj = new MessageJson();
		messageList2 = mj.parseJson(result);

		followhandler2.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		followhandler2.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (messageList2.get(0).getRet().equals("0")) {
						Commond.showToast(getApplicationContext(), messageList2
								.get(0).getTip());
						chatroom_userinfo_edit_btn
								.setBackgroundResource(R.drawable.chatroom_userinfo_dialog_cancelguanzhu_btn);
					} else if (messageList2.get(0).getRet().equals("1")) {
						Commond.showToast(getApplicationContext(), messageList2
								.get(0).getTip());
						chatroom_userinfo_edit_btn
								.setBackgroundResource(R.drawable.chatroom_userinfo_dialog_xiaoguanzhu_btn);
						//onlineUserInfoList.get(0).setIsf(0);
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == 1) {
			if (resultCode == 20) {
				String nick = data.getStringExtra("nick");
				String level = data.getStringExtra("level");
				getIntent().putExtra("nick", nick);
				getIntent().putExtra("level", level);
				setResult(20, getIntent());
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
