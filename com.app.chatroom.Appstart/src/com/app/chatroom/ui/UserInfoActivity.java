package com.app.chatroom.ui;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.chatroom.Appstart;
import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.http.HttpUserInfo;
import com.app.chatroom.json.MessageJson;
import com.app.chatroom.json.bean.MessageBean;
import com.app.chatroom.otherui.MessageSystemDialog;
import com.app.chatroom.otherui.UpPhotoDialog;
import com.app.chatroom.pic.AsynImageLoader;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;
import com.app.chatroom.util.EditTextUtil;
import com.app.chatroom.util.SystemUtil;
import com.app.chatroom.view.NetImageView;
import com.jianrencun.chatroom.R;
import com.tencent.open.HttpStatusException;
import com.tencent.open.NetworkUnavailableException;
import com.tencent.tauth.Constants;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.weibo.sina.AuthorizeActivity;

public class UserInfoActivity extends HttpBaseActivitytwo {
	ImageButton chooseIDBtn;
	ImageButton backBtn;

	/* 个人信息 */
	ImageButton chooseHeaderImageButton;
	NetImageView userHeaderImageView;
	TextView userIDTextView;
	TextView userScoreTextView;
	TextView userLevelTextView;
	EditText userNickEditText;
	EditText userSignEditText;
	RadioGroup sexRadioGroup;
	RadioButton userBoyRadioButton, userGirlRadioButton;
	EditText userCityEditText;
	EditText userBirthdayEditText;
	EditText userQQEdtiText;
	EditText userWeixinEditText;
	EditText userPhoneEditText;
	EditText userEmailEditText;
	EditText editYear, editMonth, editDay;

	ImageButton saveBtn, qqbd, sinabd;

	SharedPreferences sp;
	public static SystemSettingUtilSp su;
	private Dialog dialog = null;
	private Dialog dialog2 = null;
	LoadThread loadThread;
	UpLoadPhotoThread upLoadThread;
	ArrayList<MessageBean> messageList = new ArrayList<MessageBean>();
	ModifyUserInfo modifyThread;
	String sex = "0";
	String photoPath = "";
	String photoUrl = "";
	AsynImageLoader asynImageLoader;
	public static String sunick;

	//
	private static final String TAG = "TAuthDemoActivity";
	public static final int REQUEST_PICK_PICTURE = 1001;
	private static final String CALLBACK = "auth://tauth.qq.com/";

	public String mAppid = "100387196";// 申请时分配的appid
	private String scope = "get_simple_userinfo ,add_topic";// 授权范围
	// private AuthReceiver receiver;
	String mat, mad, mod, json;
	public String mAccessToken, mOpenId;
	int loopCount;
	public static int k = 0;
	private static String uid, unick, header;
	private SharedPreferences prefs;
	LayoutInflater inflater;
	View view;
	TextView toastTextView;
	Toast toast;

	private Handler mHandler;
	private Tencent mTencent;
	private static final String SCOPE = "get_simple_userinfo ,add_topic";

	@SuppressLint("WorldWriteableFiles")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info);
		initView();
		asynImageLoader = new AsynImageLoader(this);
		EditTextUtil.lengthFilter(this, editYear, 4, "不要恶搞，最多四位");
		EditTextUtil.lengthFilter(this, editMonth, 2, "不要恶搞，最多二位");
		EditTextUtil.lengthFilter(this, editDay, 2, "不要恶搞，最多二位");
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
		// 进度条实例化，为了返回销毁
		dialog = new Dialog(this, R.style.theme_dialog_alert);
		dialog.setContentView(R.layout.dialog_layout);
		dialog2 = new Dialog(this, R.style.theme_dialog_alert);
		dialog2.setContentView(R.layout.dialog_layout2);
		loadThread = new LoadThread();
		upLoadThread = new UpLoadPhotoThread();
		loadThread.start();

		mHandler = new Handler();

		mTencent = Tencent.createInstance(mAppid, this.getApplicationContext());

		userHeaderImageView.setImageUrl(su.getHeader(), R.drawable.photo,
				Environment.getExternalStorageDirectory() + File.separator
						+ getPackageName() + ConstantsJrc.PHOTO_PATH,
				ConstantsJrc.PROJECT_PATH + getPackageName()
						+ ConstantsJrc.PHOTO_PATH);
		// 启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		chooseIDBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						ChooseIDActivity.class);
				startActivity(intent);
			}
		});

		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();

			}
		});

		saveBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
					toastTextView.setText("检测到网络网络异常或未开启");
					toast.show();
					return;
				} else {
					modifyThread = new ModifyUserInfo();
					if (userNickEditText.getText().toString().equals("")) {
						Intent intent = new Intent(getApplicationContext(),
								MessageSystemDialog.class);
						intent.putExtra("ret", "0");
						intent.putExtra("tip", "昵称不能空");
						startActivity(intent);
						return;
					}
					if (!userNickEditText.getText().toString().equals("")) {
						if (EditTextUtil.replaceBlank(
								userNickEditText.getText().toString()).equals(
								"")) {
							Intent intent = new Intent(getApplicationContext(),
									MessageSystemDialog.class);
							intent.putExtra("ret", "0");
							intent.putExtra("tip", "昵称不能为空格");
							startActivity(intent);
						} else {
							modifyThread.start();
						}
					} else if (userEmailEditText.getText().toString()
							.equals("")
							&& userPhoneEditText.getText().toString()
									.equals("")) {
						modifyThread.start();
					} else if (!userEmailEditText.getText().toString()
							.equals("")) {
						if (EditTextUtil.VerificationEmail(userEmailEditText
								.getText().toString())) {
							modifyThread.start();
						} else {
							Intent intent = new Intent(getApplicationContext(),
									MessageSystemDialog.class);
							intent.putExtra("ret", "0");
							intent.putExtra("tip", "请正确输入邮箱地址");
							startActivity(intent);
						}
					} else if (!userPhoneEditText.getText().toString()
							.equals("")) {
						if (EditTextUtil.VerificationPhone(userPhoneEditText
								.getText().toString())) {
							modifyThread.start();
						} else {
							Intent intent = new Intent(getApplicationContext(),
									MessageSystemDialog.class);
							intent.putExtra("ret", "0");
							intent.putExtra("tip", "请正确输入手机号码");
							startActivity(intent);
						}
					}

				}
			}
		});

		qqbd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				File file = new File(MainMenuActivity.sppath + "TokenDate.xml");

				onClickLogin();
			}
		});

		sinabd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//
				Intent it = new Intent();
				it.setClass(UserInfoActivity.this, AuthorizeActivity.class);
				startActivity(it);
			}
		});

		// 注册广播
		// registerIntentReceivers();

		chooseHeaderImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						UpPhotoDialog.class);
				startActivityForResult(intent, 1);

			}
		});
		userBoyRadioButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				userBoyRadioButton.setChecked(true);
				sex = "2";
				userGirlRadioButton.setChecked(false);
			}
		});

		userGirlRadioButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				userBoyRadioButton.setChecked(false);
				sex = "1";
				userGirlRadioButton.setChecked(true);
			}
		});
		// sexRadioGroup.setOnCheckedChangeListener(new
		// OnCheckedChangeListener() {
		//
		// @Override
		// public void onCheckedChanged(RadioGroup group, int checkedId) {
		// // TODO Auto-generated method stub
		// switch (checkedId) {
		// case R.id.usersex_girl_radio:
		// sex = "1";
		// break;
		//
		// case R.id.usersex_boy_radio:
		// sex = "2";
		// break;
		// }
		// }
		// });
	}

	private void initView() {
		chooseIDBtn = (ImageButton) findViewById(R.id.userchooseid_Button);
		backBtn = (ImageButton) findViewById(R.id.userinfo_left_btn);
		chooseHeaderImageButton = (ImageButton) findViewById(R.id.info_photo_bg_ImageView);
		userHeaderImageView = (NetImageView) findViewById(R.id.user_infophoto);
		userIDTextView = (TextView) findViewById(R.id.userid_TextView);
		userScoreTextView = (TextView) findViewById(R.id.userscore_TextView);
		userLevelTextView = (TextView) findViewById(R.id.userlevel_TextView);
		userNickEditText = (EditText) findViewById(R.id.usernick_EditText);
		userSignEditText = (EditText) findViewById(R.id.usersign_EditText);
		userBoyRadioButton = (RadioButton) findViewById(R.id.usersex_boy_radio);
		userGirlRadioButton = (RadioButton) findViewById(R.id.usersex_girl_radio);
		userCityEditText = (EditText) findViewById(R.id.usercity_EditText);
		// userBirthdayEditText = (EditText)
		// findViewById(R.id.userbirthday_EditText);
		editYear = (EditText) findViewById(R.id.edit_year);
		editMonth = (EditText) findViewById(R.id.edit_month);
		editDay = (EditText) findViewById(R.id.edit_day);
		// userQQEdtiText = (EditText) findViewById(R.id.userqq_EditText);
		// userWeixinEditText = (EditText)
		// findViewById(R.id.userweixin_EditText);
		userPhoneEditText = (EditText) findViewById(R.id.userphone_EditText);
		userEmailEditText = (EditText) findViewById(R.id.useremail_EditText);

		saveBtn = (ImageButton) findViewById(R.id.userinfo_right_btn);
		qqbd = (ImageButton) findViewById(R.id.userqq);
		sinabd = (ImageButton) findViewById(R.id.usersina);
		sexRadioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
	}

	class LoadThread extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				initData();
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
				loadThread.stopThread(false);
				break;
			}
		};
	};

	private void initData() {
		handler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);

		handler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		handler.post(new Runnable() {

			@Override
			public void run() {
				try {

					userIDTextView.setText(su.getUid());
					userScoreTextView.setText(su.getScore());
					userLevelTextView.setText(su.getLevel());
					userNickEditText.setText(su.getNick());

					userSignEditText.setText(su.getSign());
					// System.out.println("本地性别：" + su.getSex());
					if (su.getSex().equals("1")) {
						userGirlRadioButton.setChecked(true);
						sex = "1";
					} else if (su.getSex().equals("2")) {
						userBoyRadioButton.setChecked(true);
						sex = "2";
					}
					userCityEditText.setText(su.getCity());
					String[] str = su.getBirthday().split("\\-");
					editYear.setText(str[0].toString());
					editMonth.setText(str[1].toString());
					editDay.setText(str[2].toString());
					// userBirthdayEditText.setText(su.getBirthday());
					// userQQEdtiText.setText(su.getQQ());
					// userWeixinEditText.setText(su.getWeixin());
					userPhoneEditText.setText(su.getPhone());
					userEmailEditText.setText(su.getEmail());
					// InputStream imgis = null;
					// imgis = GetInternetData.getInputStream(su.getHeader());
					// if (imgis != null)
					// userHeaderImageView.setImageBitmap(BitmapFactory
					// .decodeStream(imgis));
					// asynImageLoader.showImageAsyn(userHeaderImageView,
					// su.getHeader(), R.drawable.photo);
				} catch (Exception e) {
					// TODO: handle exception
					// Toast.makeText(getApplicationContext(), "网络状况不佳",
					// 0).show();
				}
			}
		});
	}

	class ModifyUserInfo extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					ModifyData();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Handler modifyHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:
				dialog.setCancelable(true);
				dialog.show();
				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				dialog.cancel();
				modifyThread.stopThread(false);
				break;
			}
		};
	};

	private void ModifyData() throws ClientProtocolException, IOException {
		modifyHandler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);
		// System.out.println(sex);
		final String result = HttpUserInfo.ModifyDataPost(userIDTextView
				.getText().toString(), "", URLEncoder.encode(userNickEditText
				.getText().toString()), URLEncoder.encode(userSignEditText
				.getText().toString()), sex, userPhoneEditText.getText()
				.toString(), URLEncoder.encode(userEmailEditText.getText()
				.toString()), URLEncoder.encode(editYear.getText().toString()
				+ "-" + editMonth.getText().toString() + "-"
				+ editDay.getText().toString()), URLEncoder
				.encode(userCityEditText.getText().toString()),su.getToken());
		// System.out.println(result);
		MessageJson mj = new MessageJson();
		messageList = mj.parseJsonHeader(result);
		modifyHandler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		modifyHandler.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (messageList.get(0).getRet().equals("1")) {

						// Intent intent = new Intent(getApplicationContext(),
						// MessageSystemDialog.class);
						// intent.putExtra("ret",
						// String.valueOf(messageList.get(0).getRet()));
						// intent.putExtra("tip", messageList.get(0).getTip()
						// .toString());
						// startActivity(intent);
						toastTextView.setText(messageList.get(0).getTip());
						toast.show();
						modifyThread.stopThread(false);
						su.setNick(userNickEditText.getText().toString());
						su.setSign(userSignEditText.getText().toString());
						su.setLevel(userLevelTextView.getText().toString());
						if (userGirlRadioButton.isChecked() == true) {
							su.setSex("1");
						} else if (userBoyRadioButton.isChecked() == true) {
							su.setSex("2");
						} else {
							su.setSex("0");
						}
						if (!messageList.get(0).getHeader().toString()
								.equals("")) {
							su.setHeader(messageList.get(0).getHeader());
						}
						su.setCity(userCityEditText.getText().toString());
						su.setBirthday(editYear.getText().toString() + "-"
								+ editMonth.getText().toString() + "-"
								+ editDay.getText().toString());
						su.setPhone(userPhoneEditText.getText().toString());
						su.setEmail(userEmailEditText.getText().toString());
						getIntent().putExtra("nick",
								userNickEditText.getText().toString());
						getIntent().putExtra("level",
								userLevelTextView.getText().toString());
						// Log.e("11111111111",
						// userLevelTextView.getText().toString()+"");
						setResult(20, getIntent());
						// finish();
					} else {
						// Intent intent = new Intent(getApplicationContext(),
						// MessageSystemDialog.class);
						// intent.putExtra("ret",
						// String.valueOf(messageList.get(0).getRet()));
						// intent.putExtra("tip", messageList.get(0).getTip()
						// .toString());
						// startActivity(intent);
						toastTextView.setText(messageList.get(0).getTip());
						toast.show();
					}
				} catch (Exception e) {
					// TODO: handle exception
					toastTextView.setText("检测到网络网络异常或未开启");
					toast.show();
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		if (requestCode == 1) {
			if (resultCode == 20) {
				photoPath = data.getStringExtra("path");
				// System.out.println(photoPath);
				if (!photoPath.equals("")) {
					Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
					userHeaderImageView.setImageBitmap(bitmap);
					upLoadThread = new UpLoadPhotoThread();
					upLoadThread.start();
					// userNickEditText.setText(sunick);
					File file = new File(MainMenuActivity.sppath
							+ "TokenDate.xml");
					if (file.exists()) {
						deletefile(file);
					}

					File sfile = new File(MainMenuActivity.sppath
							+ "AuthoSharePreference.xml");
					if (sfile.exists()) {
						deletefile(sfile);
						Appstart.swi = 0;
					}
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	class UpLoadPhotoThread extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					UpLoadPhotoData();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Handler upLoadHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:
				dialog2.setCancelable(true);
				dialog2.show();
				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				dialog2.cancel();
				upLoadThread.stopThread(false);
				break;
			}
		};
	};

	private void UpLoadPhotoData() throws ClientProtocolException, IOException {
		upLoadHandler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);
		// System.out.println(sex);
		final String result = HttpUserInfo.ModifyPhotoPost(su.getUid(),
				photoPath,su.getToken());
		// System.out.println(result);
		MessageJson mj = new MessageJson();
		messageList = mj.parseJsonHeader(result);
		upLoadHandler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		upLoadHandler.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (messageList.get(0).getRet().equals("1")) {

						// Intent intent = new Intent(getApplicationContext(),
						// MessageSystemDialog.class);
						// intent.putExtra("ret",
						// String.valueOf(messageList.get(0).getRet()));
						// intent.putExtra("tip", messageList.get(0).getTip()
						// .toString());
						// startActivity(intent);
						toastTextView.setText(messageList.get(0).getTip()
								.toString());
						toast.show();

						if (!messageList.get(0).getHeader().toString()
								.equals("")) {
							su.setHeader(messageList.get(0).getHeader());
							MainMenuActivity.header = messageList.get(0)
									.getHeader();
							photoUrl = messageList.get(0).getHeader()
									.toString();
						}

					} else {
						// Intent intent = new Intent(getApplicationContext(),
						// MessageSystemDialog.class);
						// intent.putExtra("ret",
						// String.valueOf(messageList.get(0).getRet()));
						// intent.putExtra("tip", messageList.get(0).getTip()
						// .toString());
						// startActivity(intent);
						toastTextView.setText(messageList.get(0).getTip()
								.toString());
						toast.show();
					}
				} catch (Exception e) {

				}
			}
		});
	}

	public void getjson(String ss) {
		try {
			JSONObject jsonChannel2;
			jsonChannel2 = new JSONObject(ss);

			unick = URLDecoder.decode(jsonChannel2.optString("nickname"));
			header = URLDecoder
					.decode(jsonChannel2.optString("figureurl_qq_2"));
			if (unick != null && !unick.equalsIgnoreCase("") && header != null
					&& !header.equalsIgnoreCase("")) {
				sharesaveDate();
				userNickEditText.setText(unick);
				userHeaderImageView.setImageUrl(header, R.drawable.photo,
						Environment.getExternalStorageDirectory()
								+ File.separator + getPackageName()
								+ ConstantsJrc.PHOTO_PATH,
						ConstantsJrc.PROJECT_PATH + getPackageName()
								+ ConstantsJrc.PHOTO_PATH);
				dialog.cancel();
				su.setNick(unick);
				su.setHeader(header);
				MainMenuActivity.header = header;
				MainMenuActivity.nick = unick;
				File sfile1 = new File(MainMenuActivity.sppath
						+ "AuthoSharePreference.xml");
				// Commond.showToast(UserInfoActivity.this, "绑定QQ成功！");
				// qqsendMess();
				if (sfile1.exists()) {
					deletefile(sfile1);
					Appstart.swi = 0;
				}
				dialog.show();
				/*
				 * 绑定QQ后上传头像
				 */
				String url = "http://jrc.hutudan.com/usercenter/open_bind.php"+ "?token="
						+ su.getToken();
				StringBuffer data = new StringBuffer();
				data.append("&uid=");
				data.append(MainMenuActivity.uid);
				data.append("&type=");
				data.append(2);
				data.append("&header=");
				data.append(URLEncoder.encode(header));
				data.append("&nick=");
				data.append(URLEncoder.encode(unick));
				HttpRequestTask request = new HttpRequestTask();
				request.execute(url, data.toString());

				getIntent().putExtra("nick", unick);
				setResult(20, getIntent());

			} else {
				Commond.showToast(UserInfoActivity.this, "绑定QQ失败！");
			}
			// AuthorizeActivity.sinaname="";
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		File sfile = new File(MainMenuActivity.sppath
				+ "AuthoSharePreference.xml");
		if (sfile.exists()) {
			if (Appstart.swi == 0) {
				if (AuthorizeActivity.sinaname != null
						&& !AuthorizeActivity.sinaname.equalsIgnoreCase("")) {
					userNickEditText.setText(AuthorizeActivity.sinaname);
					userHeaderImageView.setImageUrl(
							AuthorizeActivity.sinaheader, R.drawable.photo,
							Environment.getExternalStorageDirectory()
									+ File.separator + getPackageName()
									+ ConstantsJrc.PHOTO_PATH,
							ConstantsJrc.PROJECT_PATH + getPackageName()
									+ ConstantsJrc.PHOTO_PATH);
					su.setNick(AuthorizeActivity.sinaname);
					su.setHeader(AuthorizeActivity.sinaheader);
					MainMenuActivity.header = AuthorizeActivity.sinaheader;
					MainMenuActivity.nick = AuthorizeActivity.sinaname;
					getIntent().putExtra("nick", AuthorizeActivity.sinaname);

					/*
					 * 绑定xinlang后上传头像
					 */
					dialog.show();
					bdsc(1, AuthorizeActivity.sinaheader,
							AuthorizeActivity.sinaname);

					File file = new File(MainMenuActivity.sppath
							+ "TokenDate.xml");
					if (file.exists()) {
						deletefile(file);
					}
					Appstart.swi = 1;
					// Commond.showToast(UserInfoActivity.this, "绑定新浪微博成功！");
				}
			}
		}
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		// modifyThread.stopThread(false);
		upLoadThread.stopThread(false);
		loadThread.stopThread(false);
		super.onDestroy();
	}

	private void sharesaveDate() {
		// String ss= paCalendar.getInstance();
		prefs = getSharedPreferences("TokenDate", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("token", mAccessToken);
		editor.putString("openid", mOpenId);
		editor.commit(); // 注意一定要写此函数（语句）
	}

	private SharedPreferences sharereadDate() {
		SharedPreferences prefs = getSharedPreferences("TokenDate",
				Context.MODE_PRIVATE);
		return prefs;
	}

	public boolean satisfyConditions() {
		return mAccessToken != null && mAppid != null && mOpenId != null
				&& !mAccessToken.equals("") && !mAppid.equals("")
				&& !mOpenId.equals("");
	}

	public static final int PROGRESS = 0;

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case PROGRESS:
			dialog = new ProgressDialog(this);
			((ProgressDialog) dialog).setMessage("请求中,请稍等...");
			break;
		}

		return dialog;
	}

	public void deletefile(File file) {
		// 执行删除，或者什么。。。操作
		File delFile = new File(file.getAbsolutePath());
		if (delFile.exists()) {
			// Log.i("PATH", delFile.getAbsolutePath());
			delFile.delete();
		}

	}

	private void bdsc(int type, String header, String nick) {
		String url = "http://jrc.hutudan.com/usercenter/open_bind.php"+ "?token="
				+ su.getToken();
		StringBuffer data = new StringBuffer();
		data.append("&uid=");
		data.append(MainMenuActivity.uid);
		data.append("&type=");
		data.append(type);
		data.append("&header=");
		data.append(URLEncoder.encode(header));
		data.append("&nick=");
		data.append(URLEncoder.encode(nick));
		HttpRequestTask request = new HttpRequestTask();
		request.execute(url, data.toString());
	}

	@Override
	public void resultData(String url, String result) {
		// TODO Auto-generated method stub
		String tip = "";
		dialog.cancel();
		if (result == null) {
			// fresh.setVisibility(View.VISIBLE);
			Commond.showToast(UserInfoActivity.this, "小贱提醒 ：当前网络不稳定！");
			return;
		}

		try {
			// //////////////////////////////////////////////正文内容页面
			JSONObject jsonChannel = new JSONObject(result);
			tip = URLDecoder.decode(jsonChannel.optString("tip"));
			Commond.showToast(UserInfoActivity.this, tip);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// fresh.setVisibility(View.VISIBLE);
			e.printStackTrace();
		}
	}

	// //////////////////////////////////////QQ登录
	private void onClickUserInfo() {
		if (ready()) {

			mTencent.requestAsync(Constants.GRAPH_SIMPLE_USER_INFO, null,
					Constants.HTTP_GET, new BaseApiListener(), null);
			dialog.show();
		}

	}

	private boolean ready() {
		boolean ready = mTencent.isSessionValid()
				&& mTencent.getOpenId() != null;
		return ready;
	}

	private class BaseApiListener implements IRequestListener {

		@Override
		public void onComplete(final JSONObject response, Object state) {
			showResult("IRequestListener.onComplete:", response.toString());
			doComplete(response, state);
		}

		protected void doComplete(JSONObject response, Object state) {
		}

		@Override
		public void onIOException(final IOException e, Object state) {
			showResult("IRequestListener.onIOException:", e.getMessage());
		}

		@Override
		public void onMalformedURLException(final MalformedURLException e,
				Object state) {
			showResult("IRequestListener.onMalformedURLException", e.toString());
		}

		@Override
		public void onJSONException(final JSONException e, Object state) {
			showResult("IRequestListener.onJSONException:", e.getMessage());
		}

		@Override
		public void onConnectTimeoutException(ConnectTimeoutException arg0,
				Object arg1) {
			showResult("IRequestListener.onConnectTimeoutException:",
					arg0.getMessage());

		}

		@Override
		public void onSocketTimeoutException(SocketTimeoutException arg0,
				Object arg1) {
			showResult("IRequestListener.SocketTimeoutException:",
					arg0.getMessage());
		}

		@Override
		public void onUnknowException(Exception arg0, Object arg1) {
			showResult("IRequestListener.onUnknowException:", arg0.getMessage());
		}

		@Override
		public void onHttpStatusException(HttpStatusException arg0, Object arg1) {
			showResult("IRequestListener.HttpStatusException:",
					arg0.getMessage());
		}

		@Override
		public void onNetworkUnavailableException(
				NetworkUnavailableException arg0, Object arg1) {
			showResult("IRequestListener.onNetworkUnavailableException:",
					arg0.getMessage());
		}
	}

	private void showResult(final String base, final String msg) {
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				if (dialog.isShowing())
					dialog.dismiss();
				getjson(msg);
			}
		});
	}

	private void onClickLogin() {
		if (!mTencent.isSessionValid()) {
			IUiListener listener = new BaseUiListener() {
				@Override
				protected void doComplete(JSONObject values) {

				}
			};
			mTencent.login(this, SCOPE, listener);
		} else {
			mTencent.logout(this);
			onClickUserInfo();
		}
	}

	private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(JSONObject response) {
			String ssk = response.toString();
			doComplete(response);
			onClickUserInfo();
		}

		protected void doComplete(JSONObject values) {

		}

		@Override
		public void onError(UiError e) {
			showResult("onError:", "code:" + e.errorCode + ", msg:"
					+ e.errorMessage + ", detail:" + e.errorDetail);
		}

		@Override
		public void onCancel() {
			showResult("onCancel", "");
		}
	}

}
