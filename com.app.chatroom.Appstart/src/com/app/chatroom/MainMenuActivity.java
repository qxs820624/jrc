package com.app.chatroom;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.db.DbUtil;
import com.app.chatroom.http.HttpCheck;
import com.app.chatroom.http.HttpLoginServer;
import com.app.chatroom.json.GetMailJson;
import com.app.chatroom.json.MessageJson;
import com.app.chatroom.json.UserInfoJson;
import com.app.chatroom.json.bean.MailBean;
import com.app.chatroom.json.bean.MessageBean;
import com.app.chatroom.json.bean.UserBean;
import com.app.chatroom.mgmusic.MgMusicActivity;
import com.app.chatroom.otherui.MessageDownDialog;
import com.app.chatroom.otherui.MessageSystemDialog;
import com.app.chatroom.otherui.SystemMsgWebDialog;
import com.app.chatroom.otherui.VillageUserInfoDialog;
import com.app.chatroom.pic.AsynImageLoader;
import com.app.chatroom.pic.BitmapCacheRoomList;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.ui.GameActivity;
import com.app.chatroom.ui.MailActivity;
import com.app.chatroom.ui.RoomListActivity;
import com.app.chatroom.ui.VillageActivity;
import com.app.chatroom.util.Commond;
import com.app.chatroom.util.PhoneInfo;
import com.app.chatroom.util.SystemUtil;
import com.app.chatroom.view.HomeView;
import com.cmsc.cmmusic.init.InitCmmInterface;
import com.duom.fjz.iteminfo.BitmapCache2;
import com.duom.fjz.iteminfo.Iteminfo;
import com.duom.fjz.iteminfo.Tixing;
import com.duom.fjz.iteminfo.WebviewDonghua;
import com.jianrencun.android.CacheData;
import com.jianrencun.android.CategoryEntity;
import com.jianrencun.android.Details;
import com.jianrencun.android.Entrance;
import com.jianrencun.android.JianrencunActivity;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Comment;
import com.jianrencun.dynamic.Dynamic;
import com.jianrencun.game.Gameroom;
import com.jianrencun.game.Zhuanpan;
import com.umeng.analytics.MobclickAgent;
import com.weibo.sina.AuthorizeActivity;

public class MainMenuActivity extends Activity {
	Button chatRoomBtn;// 聊天室进入按钮
	Button villageBtn;// 村委会进入按钮
	ImageButton mailBtn, dongtaiBtn;// 信箱按钮
	TextView messageCount;// 未读信息
	TextView taskCount;// 任务数量
	// ImageButton mainMenuBtn;// 主页菜单按钮
	ImageButton mainShopBtn; // 商城按钮
	ImageButton mainHelpBtn; // 帮助按钮
	ImageButton mainTaskBtn;// 任务按钮
	ImageButton mainGameBtn;// 游戏按钮
	ImageButton mainJPBtn; // 精品按钮
	ImageButton mainMgBtn;// 咪咕音乐按钮
	ImageButton mainYuepi;// sousuo
	ImageButton sousuo;
	TextView mainMdTextView;// 钻石
	TextView mainMgTextView;// 金币
	TextView mainMsTextView;// 银币
	RelativeLayout systemRelative;// 公告卷轴布局
	ImageButton systemCloseButton;// 关闭公告
	public WebView sysWebView; // 公告WebView
	public static String pakName;
	private Dialog dialog = null;
	ArrayList<UserBean> userList = new ArrayList<UserBean>();
	ArrayList<MessageBean> messageList = new ArrayList<MessageBean>();
	ArrayList<MailBean> mailBeanlist = new ArrayList<MailBean>();
	MailBean mMailBean;
	RelativeLayout userInfo;
	SharedPreferences sp;
	SystemSettingUtilSp su;
	boolean IsEnterRoom = true;
	/** 用户头像 **/
	ImageView userImage;
	/** 用户昵称 **/
	TextView userName;
	/** 用户等级 **/
	TextView userLevel;
	CheckInfoThread checkThread;
	LoginThread loginThread;
	ReLoadPhtotThread reloadThread;
	DbUtil db = new DbUtil(this);
	int IsReadCount = 0;
	public Timer loadingDataTime;
	AsynImageLoader asynImageLoader;
	LayoutInflater inflater;
	View view;
	TextView toastTextView;
	Toast toast;
	public static String catePd = "";
	public static String shoucanglb, shangchuanyh, myshangchuanlb;
	public static ArrayList<ArrayList<Iteminfo>> li;
	public static String uid, nick, header, token;
	public static String p;
	public static String sppath;
	public static int k = 0;
	public static String vername;

	public int whichto;
	private int istj = 0;
	private int isshop = 0;
	private long exitTime = 0;

	String Mainpd = "";
	File picfile;
	private ArrayList<String> headerurls = new ArrayList<String>();
	public static File dazuisdown;
	private File donghuadownload, zipFile;
	String help = "";// 帮助道具路径
	String shop = "";// 商店路径
	String hd = ""; // 活动路径
	String jspath = "";// JS脚本路径
	String jsname = "uid.js";// 脚本名字
	private boolean gointochat = false, gointoxiaojian = false,
			gointodazui = false;

	private Thread xiaojianth, dazuith;
	Details dt = new Details();
	Bitmap bmp, bmp2;
	String filename, filename2;
	private String htmlname, foldername;
	public static String dhpath = "";
	private File htmlfile;
	private int flg;
	private String path, link, newpath;
	private String imei, imsi, model, osver, width, height, vercode, ver, sim,
			lang, mac, pkg, cid;
	private ImageView pro_iv, pro_tiaodonghua;
	private RelativeLayout pro_rl, pro_zuorl;
	private int jinduwidth, dangqianzhi, xuyaozhi;
	int zuo;
	private TextView dengji, jiecaodangqian, jiecaoxuyao;
	private TextView maohao, xiegang;
	private int numtime;
	private RelativeLayout sy_jiecao_rl, relativerl2;
	private ImageView iv1, iv2, iv3, iv4;
	private boolean hasMeasured = false;
	DisplayMetrics dm;
	int bjwidth;
	RelativeLayout.LayoutParams linearParams;
	RelativeLayout.LayoutParams linearParams2;
	RelativeLayout.LayoutParams linearParams3;
	RelativeLayout.LayoutParams linearParams4;
	RelativeLayout.LayoutParams linearParams5;
	private String tip1, tip2, tip3, tip4, tip5, tip0;
	private ImageView xiaoshoudh;
	private ArrayList<String> x = new ArrayList<String>();
	private int def = 6;
	private boolean isfristtime = true;
	public static boolean borz;
	private int oid, ouid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// registerHeadsetPlugReceiver();
		ShareSDK.initSDK(this);
		MobclickAgent.onError(this);
		p = "data/data/" + getPackageName() + "/files/";
		sppath = "/data/data/" + getPackageName() + "/shared_prefs/";
		/** 全屏设置，隐藏窗口所有装饰 */
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		/** 标题是属于View的，所以窗口所有的修饰部分被隐藏后标题依然有效 */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_menu);

		Intent it = getIntent();
		oid = it.getIntExtra("uid", 0);
		ouid = it.getIntExtra("ouid", 0);
		if (oid != 0 && ouid != 0) {
			Intent intent = new Intent(this, VillageUserInfoDialog.class);
			intent.putExtra("uid", String.valueOf(ouid));
			intent.putExtra("nick", "");
			intent.putExtra("fuid", String.valueOf(oid));
			intent.putExtra("type", 2);
			startActivity(intent);
		}

		dm = new DisplayMetrics();
		((Activity) this).getWindowManager().getDefaultDisplay().getMetrics(dm);
		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);

		yd_animhadler.sendEmptyMessageDelayed(1, 150);
		tiao_animhadler.sendEmptyMessageDelayed(1, 100);
		relativerl2 = (RelativeLayout) findViewById(R.id.relativeLayout2);
		sy_jiecao_rl = (RelativeLayout) findViewById(R.id.sy_jiecao_rl);
		dengji = (TextView) findViewById(R.id.sy_dengji_num);
		jiecaodangqian = (TextView) findViewById(R.id.sy_jiecao_tv_dangqian);
		jiecaoxuyao = (TextView) findViewById(R.id.sy_jiecao_tv_xuyao);
		maohao = (TextView) findViewById(R.id.sy_maohao);
		xiegang = (TextView) findViewById(R.id.sy_xiegang);

		xiaoshoudh = (ImageView) findViewById(R.id.xiaoshoudh);
		xiaoshoudh.setVisibility(View.GONE);

		pro_iv = (ImageView) findViewById(R.id.pro_iv);
		pro_tiaodonghua = (ImageView) findViewById(R.id.pro_tiaodonghua);
		pro_tiaodonghua.setVisibility(View.GONE);
		iv1 = (ImageView) findViewById(R.id.iv1);
		iv2 = (ImageView) findViewById(R.id.iv2);
		iv3 = (ImageView) findViewById(R.id.iv3);
		iv4 = (ImageView) findViewById(R.id.iv4);
		// ///////////////////////////////////////////获取控件的宽度
		ViewTreeObserver vto = iv1.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			public boolean onPreDraw() {
				if (hasMeasured == false) {
					bjwidth = iv1.getMeasuredWidth();
					// 获取到宽度和高度后，可用于计算
					linearParams = (RelativeLayout.LayoutParams) iv1
							.getLayoutParams();
					linearParams.setMargins(HomeView.points.get(0).x - bjwidth
							/ 2, HomeView.points.get(0).y, 0, 0);
					iv1.setLayoutParams(linearParams);
					iv1.setVisibility(View.GONE);
					linearParams2 = (RelativeLayout.LayoutParams) iv2
							.getLayoutParams();
					linearParams2.setMargins(HomeView.points.get(1).x - bjwidth
							/ 2, HomeView.points.get(1).y, 0, 0);
					iv2.setLayoutParams(linearParams2);
					iv2.setVisibility(View.GONE);
					linearParams3 = (RelativeLayout.LayoutParams) iv3
							.getLayoutParams();
					linearParams3.setMargins(HomeView.points.get(2).x
							- dm.widthPixels / 50, HomeView.points.get(2).y, 0,
							0);
					iv3.setLayoutParams(linearParams3);
					iv3.setVisibility(View.GONE);
					linearParams4 = (RelativeLayout.LayoutParams) iv4
							.getLayoutParams();
					linearParams4.setMargins(HomeView.points.get(3).x - bjwidth
							/ 3 * 2, HomeView.points.get(3).y, 0, 0);
					iv4.setLayoutParams(linearParams4);
					iv4.setVisibility(View.GONE);
					hasMeasured = true;
				}
				return true;
			}
		});

		pro_rl = (RelativeLayout) findViewById(R.id.pro_rl);
		pro_zuorl = (RelativeLayout) findViewById(R.id.sy_rl_zuo);

		imei = PhoneInfo.getInstance(getApplicationContext()).getIMEINumber();
		imsi = PhoneInfo.getInstance(getApplicationContext()).getIMSINumber();
		model = PhoneInfo.getInstance(getApplicationContext()).getPhoneType();
		osver = PhoneInfo.getInstance(getApplicationContext()).getOS();
		width = PhoneInfo.getInstance(getApplicationContext()).getWidth(this);
		height = PhoneInfo.getInstance(getApplicationContext()).getHight(this);
		vercode = PhoneInfo.getInstance(getApplicationContext())
				.getVersionCode(this);
		ver = PhoneInfo.getInstance(getApplicationContext()).getVersionName(
				this);
		sim = PhoneInfo.getInstance(getApplicationContext()).getSimNumber();
		lang = PhoneInfo.getInstance(getApplicationContext()).getLanguage();
		pkg = PhoneInfo.getInstance(getApplicationContext()).getPackage(this);
		mac = PhoneInfo.getInstance(getApplicationContext())
				.getLocalMacAddress(this);
		cid = PhoneInfo.getInstance(getApplicationContext()).getCid(this);

		uid = su.getUid();
		nick = su.getNick();
		header = su.getHeader();
		token = su.getToken();
		vername = PhoneInfo.getInstance(getApplicationContext())
				.getVersionName(MainMenuActivity.this);
		if (SystemUtil.getSDCardMount()) {
			donghuadownload = new File(
					Environment.getExternalStorageDirectory() + File.separator
							+ getPackageName() + "/donghuadownload");
			zipFile = new File(Environment.getExternalStorageDirectory()
					+ File.separator + getPackageName() + "/donghuazip");
			if (!donghuadownload.exists()) {
				donghuadownload.mkdirs();
			}
			if (!zipFile.exists()) {
				zipFile.mkdirs();
			}

		} else {
			donghuadownload = new File(ConstantsJrc.PROJECT_PATH
					+ getPackageName() + "/donghuadownload");
			zipFile = new File(ConstantsJrc.PROJECT_PATH + getPackageName()
					+ "/donghuazip");
			if (!donghuadownload.exists()) {
				donghuadownload.mkdirs();
			}
			if (!zipFile.exists()) {
				zipFile.mkdirs();
			}
		}
		if (SystemUtil.getSDCardMount()) {
			dazuisdown = new File(Environment.getExternalStorageDirectory()
					+ File.separator + getPackageName() + "/dazuisdown");
			if (!dazuisdown.exists()) {
				dazuisdown.mkdirs();
			}
			jspath = Environment.getExternalStorageDirectory() + File.separator
					+ getPackageName() + File.separator + "donghuazip";
		} else {
			dazuisdown = new File(ConstantsJrc.PROJECT_PATH + getPackageName()
					+ "/dazuisdown");
			if (!dazuisdown.exists()) {
				dazuisdown.mkdirs();
			}
			jspath = ConstantsJrc.PROJECT_PATH + getPackageName()
					+ File.separator + "donghuazip";
		}

		pakName = getPackageName();
		su.setMsgPid("");
		db.Open();

		// 自定义Toast
		inflater = this.getLayoutInflater();
		view = inflater.inflate(R.layout.toast, null);
		toastTextView = (TextView) view.findViewById(R.id.toast_textView);
		toast = new Toast(this);
		toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 80);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(view);
		asynImageLoader = new AsynImageLoader(this);

		userInfo = (RelativeLayout) findViewById(R.id.main_linearLayout2);
		userImage = (ImageView) findViewById(R.id.user_main_photo);
		userName = (TextView) findViewById(R.id.user_name);
		userLevel = (TextView) findViewById(R.id.user_level);
		mailBtn = (ImageButton) findViewById(R.id.main_mail_imageButton);
		dongtaiBtn = (ImageButton) findViewById(R.id.main_dongtai_imageButton);
		messageCount = (TextView) findViewById(R.id.main_mail_num_TextView);
		taskCount = (TextView) findViewById(R.id.main_task_num_TextView);
		// mainMenuBtn = (ImageButton) findViewById(R.id.main_menu_imageButton);
		mainShopBtn = (ImageButton) findViewById(R.id.main_menu_shop_imageButton);
		sousuo = (ImageButton) findViewById(R.id.main_menu_sousuo_imageButton);
		mainJPBtn = (ImageButton) findViewById(R.id.main_menu_jp_imageButton);
		mainHelpBtn = (ImageButton) findViewById(R.id.main_menu_help_imageButton);
		mainTaskBtn = (ImageButton) findViewById(R.id.main_menu_task_imageButton);
		mainYuepi = (ImageButton) findViewById(R.id.main_menu_yuepi_imageButton);
		mainGameBtn = (ImageButton) findViewById(R.id.main_menu_game_imageButton);
		mainMgBtn = (ImageButton) findViewById(R.id.main_menu_mg_imageButton);
		mainMdTextView = (TextView) findViewById(R.id.main_score_md_textView);
		mainMgTextView = (TextView) findViewById(R.id.main_score_mg_textView);
		mainMsTextView = (TextView) findViewById(R.id.main_score_ms_textView);
		sysWebView = (WebView) findViewById(R.id.main_menu_system_webview);
		systemCloseButton = (ImageButton) findViewById(R.id.main_menu_iknow_imageButton);
		systemRelative = (RelativeLayout) findViewById(R.id.main_system_relativelayout);

		// 进度条实例化，为了返回销毁
		dialog = new Dialog(this, R.style.theme_dialog_alert);
		dialog.setContentView(R.layout.dialog_layout);
		loadingDataTime = new Timer();
		loginThread = new LoginThread();
		checkThread = new CheckInfoThread();
		checkThread.start();

		sysWebView.getSettings().setJavaScriptEnabled(true);
		sysWebView.getSettings().setDefaultTextEncodingName("utf-8");
		sysWebView.setBackgroundColor(0); // 设置背景色 //
		// mWebView.getBackground().setAlpha(0); // 设置填充透明度 范围：0-255
		sysWebView.loadUrl("file:///android_asset/zhijuan.html");

		// ////////////////////////////////////////////////////////
		sy_jiecao_rl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						SystemMsgWebDialog.class);
				intent.putExtra(
						"link",
						ConstantsJrc.MAINMORE
								+ "?uid="
								+ su.getUid()
								+ "&flg=22&w="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getWidth(MainMenuActivity.this)
								+ "&pkg="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getPackage(MainMenuActivity.this)
								+ "&ver="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getVersionName(MainMenuActivity.this)
								+ "&token=" + su.getToken());
				intent.putExtra("type", "22");
				startActivity(intent);
			}
		});

		relativerl2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						SystemMsgWebDialog.class);
				intent.putExtra(
						"link",
						ConstantsJrc.MAINMORE
								+ "?uid="
								+ su.getUid()
								+ "&flg=22&w="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getWidth(MainMenuActivity.this)
								+ "&pkg="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getPackage(MainMenuActivity.this)
								+ "&ver="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getVersionName(MainMenuActivity.this)
								+ "&token=" + su.getToken());
				intent.putExtra("type", "22");
				startActivity(intent);
			}
		});
		// 判断UID是否为空
		if (su.getUid().equals("")) {
			// try {
			// SystemUtil.writeUidJs(
			// jspath,
			// jsname,
			// "var uid="
			// + su.getUid()
			// + ";\r\n"
			// + "var pkg='"
			// + PhoneInfo
			// .getInstance(getApplicationContext())
			// .getPackage(this)
			// + "';"
			// + "\r\n"
			// + "var ver='"
			// + PhoneInfo
			// .getInstance(getApplicationContext())
			// .getVersionName(MainMenuActivity.this)
			// + "';" + "\r\n" + "var hack=0;", false);
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			loginThread.start();
		} else {
			if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
				toastTextView.setText("检测到网络网络异常或未开启");
				toast.show();
			}
			userName.setText(su.getNick());
			if (su.getLevel() == null) {
				userLevel.setText("村民");
			} else {
				userLevel.setText(su.getLevel());
			}
			loadingDataTime.schedule(new MyTimerTask(), 100, 15000);
		}

		/** 首页模块 跳转 **/
		findViewById(R.id.homeview).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gointodazui = false;
				gointoxiaojian = false;
				gointochat = false;
				int index = (Integer) v.getTag();
				switch (index) {
				case 0:// 进入村委会
					if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
						toastTextView.setText("检测到网络网络异常或未开启");
						toast.show();
						return;
					} else {

						uid = su.getUid();
						nick = su.getNick();
						header = su.getHeader();
						token = su.getToken();
						String url = "http://jrc.hutudan.com/usercenter/enter_cun.php";
						url = geturl(url);
						dialog = new Dialog(MainMenuActivity.this,
								R.style.theme_dialog_alert);
						dialog.setContentView(R.layout.dialog_layout);
						dialog.show();
						new Thread(new uThread(MainMenuActivity.this,
								mHandler3, url)).start();
					}
					break;
				case 1:// 进入聊天室内
					if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
						toastTextView.setText("检测到网络网络异常或未开启");
						toast.show();
						IsEnterRoom = true;
						return;
					} else {
						// if (IsEnterRoom) {
						// IsEnterRoom = false;
						// enterRoomThread = new EnterRoomThread();
						// enterRoomThread.start();
						// } else {
						// dialog.setCancelable(true);
						// dialog.show();
						// }
						Intent intent = new Intent(getApplicationContext(),
								RoomListActivity.class);
						intent.putExtra("tp", tip2);
						intent.putExtra("where", 2);
						iv2.setVisibility(View.GONE);
						x.remove("2");
						startActivity(intent);
						tip2 = "";
					}
					break;
				case 2:// 进入小贱工作室
					if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
						toastTextView.setText("检测到网络网络异常或未开启");
						toast.show();
						IsEnterRoom = true;
						return;
					} else {
						if (xiaojianth != null && xiaojianth.isAlive()) {
							Commond.showToast(MainMenuActivity.this,
									"正在进入中。。。稍后");
							return;
						}
						gointodazui = false;
						gointoxiaojian = true;
						gointochat = false;
						uid = su.getUid();
						nick = su.getNick();
						header = su.getHeader();
						token = su.getToken();
						String url1 = "http://news.hutudan.com/3g/category_json.php";
						String url = dt.appendNameValue(url1, "pkg", pakName);
						String url2 = geturl(url);
						String url3 = dt.appendNameValue(url2, "pd", catePd);
						dialog = new Dialog(MainMenuActivity.this,
								R.style.theme_dialog_alert);
						dialog.setContentView(R.layout.dialog_layout);
						dialog.show();
						xiaojianth = new Thread(new uThread(
								MainMenuActivity.this, mHandler, url3));
						xiaojianth.start();
						if (dazuith != null && dazuith.isAlive()) {
							mHandler2.removeCallbacks(dazuith);
						}
					}
					break;
				case 3:// 进入大嘴活动
					if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
						toastTextView.setText("检测到网络网络异常或未开启");
						toast.show();
						IsEnterRoom = true;
						return;
					} else {
						if (dazuith != null && dazuith.isAlive()) {
							Commond.showToast(MainMenuActivity.this,
									"正在进入中。。。稍后");
							return;
						}
						gointodazui = true;
						gointoxiaojian = false;
						gointochat = false;
						uid = su.getUid();
						nick = su.getNick();
						header = su.getHeader();
						token = su.getToken();
						String url = "http://jrc.hutudan.com/music/enter.php";
						url = dt.appendNameValue(url, "uid", su.getUid());
						dialog = new Dialog(MainMenuActivity.this,
								R.style.theme_dialog_alert);
						dialog.setContentView(R.layout.dialog_layout);
						dialog.show();
						dazuith = new Thread(new uThread(MainMenuActivity.this,
								mHandler2, url));
						dazuith.start();
						if (xiaojianth != null && xiaojianth.isAlive()) {
							mHandler.removeCallbacks(xiaojianth);
						}
					}
					break;
				case 4:// 进入game
					Intent intenttask = new Intent(getApplicationContext(),
							SystemMsgWebDialog.class);
					// intenttask.putExtra(
					// "link",
					// ConstantsJrc.MAINMORE
					// + "?uid="
					// + su.getUid()
					// + "&flg=4&w="
					// + PhoneInfo
					// .getInstance(getApplicationContext())
					// .getWidth(MainMenuActivity.this)
					// + "&pkg="
					// + PhoneInfo
					// .getInstance(getApplicationContext())
					// .getPackage(MainMenuActivity.this)
					// + "&ver="
					// + PhoneInfo
					// .getInstance(getApplicationContext())
					// .getVersionName(MainMenuActivity.this)
					// + "&token=" + su.getToken());

					Intent it = new Intent();
					it.setClass(MainMenuActivity.this, Gameroom.class);
					startActivity(it);
					break;
				case 5:// 进入biaoji
						// if
						// (SystemUtil.isNetworkConnected(getApplicationContext())
						// == false) {
						// toastTextView.setText("检测到网络网络异常或未开启");
						// toast.show();
						// IsEnterRoom = true;
						// return;
						// } else {
						// Commond.showToast(MainMenuActivity.this,
						// "正在建设中。。。。");
						// }
					break;
				case 6:// 进入diaosu
						// if
						// (SystemUtil.isNetworkConnected(getApplicationContext())
						// == false) {
						// toastTextView.setText("检测到网络网络异常或未开启");
						// toast.show();
						// IsEnterRoom = true;
						// return;
						// } else {
						// Commond.showToast(MainMenuActivity.this,
						// "正在建设中。。。。");
						// }
					break;
				case 7:// 进入laba
						// if
						// (SystemUtil.isNetworkConnected(getApplicationContext())
						// == false) {
						// toastTextView.setText("检测到网络网络异常或未开启");
						// toast.show();
						// IsEnterRoom = true;
						// return;
						// } else {
						// Commond.showToast(MainMenuActivity.this,
						// "正在建设中。。。。");
						// }
					break;
				}
			}
		});

		mailBtn.setOnClickListener(listener);
		dongtaiBtn.setOnClickListener(listener);
		userInfo.setOnClickListener(listener);
		mainShopBtn.setOnClickListener(listener);
		sousuo.setOnClickListener(listener);
		mainJPBtn.setOnClickListener(listener);
		mainHelpBtn.setOnClickListener(listener);
		mainTaskBtn.setOnClickListener(listener);
		mainGameBtn.setOnClickListener(listener);
		mainMgBtn.setOnClickListener(listener);
		systemCloseButton.setOnClickListener(listener);
		mainYuepi.setOnClickListener(listener);
	}

	/**
	 * 清理本地缓存的数据文件
	 */
	public void clearCacheData() {
		try {
			double size = 0;
			if (SystemUtil.getSDCardMount()) {
				File sdCardDir = Environment.getExternalStorageDirectory();
				File dir = new File(sdCardDir.getCanonicalPath()
						+ File.separator + getPackageName());
				size = SystemUtil.getFolderSize(dir) / 1024.00 / 1024.00;
				// 大于50MB自动清理数据
				if (size > 50) {
					SystemUtil.RecursionDeleteFile(dir);
					SystemUtil.makeDir(MainMenuActivity.this);
				}
			} else {

				File dir = new File(ConstantsJrc.PROJECT_PATH
						+ getPackageName());
				size = SystemUtil.getFolderSize(dir) / 1024.00 / 1024.00;
				// 大于30MB自动清理数据
				if (size > 30) {
					SystemUtil.RecursionDeleteFile(dir);
					SystemUtil.makeDir(MainMenuActivity.this);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			switch (v.getId()) {
			case R.id.main_linearLayout2:
				Intent intent = new Intent(getApplicationContext(),
						VillageUserInfoDialog.class);
				intent.putExtra("uid", su.getUid());
				intent.putExtra("fuid", su.getUid());
				intent.putExtra("nick", su.getNick());
				intent.putExtra("type", 2);
				intent.putExtra("src", 1);
				intent.putExtra("tp", tip5);
				intent.putExtra("where", 5);
				xiaoshoudh.setVisibility(View.GONE);
				x.remove("0");
				startActivityForResult(intent, 1);
				tip5 = "";
				break;
			case R.id.main_mail_imageButton:
				Intent intent2 = new Intent(getApplicationContext(),
						MailActivity.class);
				startActivity(intent2);
				su.setMailCount(0);
				messageCount.setVisibility(View.GONE);
				break;
			case R.id.main_dongtai_imageButton:
				Intent its = new Intent();
				its.setClass(MainMenuActivity.this, Dynamic.class);
				startActivity(its);
				break;
			case R.id.main_menu_shop_imageButton:
				if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
					toastTextView.setText("检测到网络网络异常或未开启");
					toast.show();
					return;
				}
				Intent intentshop = new Intent(getApplicationContext(),
						SystemMsgWebDialog.class);
				// intentshop.putExtra(
				// "link",
				// ConstantsJrc.MAINMORE
				// + "?uid="
				// + su.getUid()
				// + "&flg=2&w="
				// + PhoneInfo
				// .getInstance(getApplicationContext())
				// .getWidth(MainMenuActivity.this)
				// + "&pkg="
				// + PhoneInfo
				// .getInstance(getApplicationContext())
				// .getPackage(MainMenuActivity.this)
				// + "&ver="
				// + PhoneInfo
				// .getInstance(getApplicationContext())
				// .getVersionName(MainMenuActivity.this)
				// + "&token=" + su.getToken());
				intentshop.putExtra("help", su.getShop());
				intentshop.putExtra("uid", su.getUid());
				intentshop.putExtra("type", "2");
				startActivity(intentshop);
				break;
			case R.id.main_menu_yuepi_imageButton:
				if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
					toastTextView.setText("检测到网络网络异常或未开启");
					toast.show();
					return;
				}
				Intent itsou = new Intent(getApplicationContext(),
						SearchPeople.class);
				startActivity(itsou);
				break;
			case R.id.main_menu_help_imageButton:
				if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
					toastTextView.setText("检测到网络网络异常或未开启");
					toast.show();
					return;
				}
				Intent intenthelp = new Intent(getApplicationContext(),
						SystemMsgWebDialog.class);
				intenthelp.putExtra("help", su.getHelp());
				intenthelp.putExtra("uid", su.getUid());
				intenthelp.putExtra("type", "1");
				startActivity(intenthelp);
				break;
			case R.id.main_menu_task_imageButton:
				if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
					toastTextView.setText("检测到网络网络异常或未开启");
					toast.show();
					return;
				}
				Intent intenttask = new Intent(getApplicationContext(),
						SystemMsgWebDialog.class);
				// intenttask.putExtra(
				// "link",
				// ConstantsJrc.MAINMORE
				// + "?uid="
				// + su.getUid()
				// + "&flg=4&w="
				// + PhoneInfo
				// .getInstance(getApplicationContext())
				// .getWidth(MainMenuActivity.this)
				// + "&pkg="
				// + PhoneInfo
				// .getInstance(getApplicationContext())
				// .getPackage(MainMenuActivity.this)
				// + "&ver="
				// + PhoneInfo
				// .getInstance(getApplicationContext())
				// .getVersionName(MainMenuActivity.this)
				// + "&token=" + su.getToken());
				intenttask.putExtra("help", su.getActivity());
				intenttask.putExtra("uid", su.getUid());
				intenttask.putExtra("type", "4");
				startActivity(intenttask);
				break;
			case R.id.main_menu_game_imageButton:
				if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
					toastTextView.setText("检测到网络网络异常或未开启");
					toast.show();
					return;
				}

				Intent intentgame = new Intent(getApplicationContext(),
						GameActivity.class);
				startActivity(intentgame);
				break;
			case R.id.main_menu_iknow_imageButton:
				systemRelative.setVisibility(View.GONE);
				sysWebView.setVisibility(View.GONE);
				systemCloseButton.setVisibility(View.GONE);
				break;
			case R.id.main_menu_sousuo_imageButton:
				Intent it = new Intent();
				it.setClass(MainMenuActivity.this, Tuhaobang.class);
				startActivity(it);
				break;
			case R.id.main_menu_jp_imageButton:
				if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
					toastTextView.setText("检测到网络网络异常或未开启");
					toast.show();
					return;
				}
				Intent intentjp = new Intent(getApplicationContext(),
						SystemMsgWebDialog.class);
				intentjp.putExtra(
						"link",
						ConstantsJrc.MAINMORE
								+ "?uid="
								+ su.getUid()
								+ "&flg=3&w="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getWidth(MainMenuActivity.this)
								+ "&pkg="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getPackage(MainMenuActivity.this)
								+ "&ver="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getVersionName(MainMenuActivity.this)
								+ "&token=" + su.getToken());
				intentjp.putExtra("type", "3");
				startActivity(intentjp);
				break;
			case R.id.main_menu_mg_imageButton:
				if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
					toastTextView.setText("检测到网络网络异常或未开启");
					toast.show();
					return;
				}
				Intent intentmg = new Intent(getApplicationContext(),
						MgMusicActivity.class);
				startActivity(intentmg);
				break;
			}
		}
	};

	class LoginThread extends Thread {
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
				loginThread.stopThread(false);
				if (!su.getUid().equals("")) {
					loadingDataTime.schedule(new MyTimerTask(), 100, 15000);
				}
				break;
			}
		};
	};

	private void login() throws ClientProtocolException, IOException {
		handler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);

		final String result = HttpLoginServer.LoginPost(su.getUid(),
				su.getMainPd(), imei, imsi, URLEncoder.encode(model), osver,
				width, height, vercode, ver, "", sim, lang, pkg,
				URLEncoder.encode(mac), URLEncoder.encode(cid), su.getToken());

		// System.out.println("返回结果：" + result);
		UserInfoJson uj = new UserInfoJson();
		userList = uj.parseJson(result);
		// System.out.println("用户：" + userList.toString());
		GetMailJson mMaliJson = new GetMailJson();
		mailBeanlist = mMaliJson.parseJson(result);

		handler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		handler.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (userList.get(0).getRet() == 0) {
						if (!TextUtils.isEmpty(userList.get(0).getTip())) {
							Intent intent = new Intent(getApplicationContext(),
									MessageSystemDialog.class);
							intent.putExtra("ret", "0");
							intent.putExtra("tip", userList.get(0).getTip());
							startActivity(intent);
						}
					} else if (userList.get(0).getRet() == 1) {
						if (!su.getUid().equals(
								String.valueOf(userList.get(0).getUid()))) {
							try {
								SystemUtil
										.writeUidJs(
												jspath,
												jsname,
												"var uid="
														+ userList.get(0)
																.getUid()
														+ ";\r\n"
														+ "var pkg='"
														+ PhoneInfo
																.getInstance(
																		getApplicationContext())
																.getPackage(
																		MainMenuActivity.this)
														+ "';"
														+ "\r\n"
														+ "var ver='"
														+ PhoneInfo
																.getInstance(
																		getApplicationContext())
																.getVersionName(
																		MainMenuActivity.this)
														+ "';" + "\r\n"
														+ "var hack=0;", false);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						su.setUid(String.valueOf(userList.get(0).getUid()));
						su.setNick(userList.get(0).getNick());
						su.setHeader(userList.get(0).getHeader());
						su.setSign(userList.get(0).getSign());
						su.setScore(String.valueOf(userList.get(0).getScore()));
						su.setLevel(userList.get(0).getLevel());
						su.setBirthday(userList.get(0).getBirthday());
						su.setCity(userList.get(0).getCity());
						su.setSex(userList.get(0).getSex());
						su.setMainPd(userList.get(0).getPd());

						su.setToken(userList.get(0).getToken());
						istj = userList.get(0).getIstj();
						isshop = userList.get(0).getIsshop();
						if (isshop == 0) {
							mainShopBtn.setVisibility(View.VISIBLE);
						} else if (isshop == 1) {
							mainShopBtn.setVisibility(View.GONE);
						}
						if (istj == 0) {
							mainJPBtn.setVisibility(View.VISIBLE);
						} else if (istj == 1) {
							mainJPBtn.setVisibility(View.GONE);
						}
						userName.setText(userList.get(0).getNick());

						if (!TextUtils.isEmpty(userList.get(0).getTip0())) {
							Intent toit = new Intent();
							toit.setClass(MainMenuActivity.this, Tixing.class);
							toit.putExtra("where", 5);
							toit.putExtra("tp", userList.get(0).getTip0());
							startActivity(toit);
						}

						if (!TextUtils.isEmpty(userList.get(0).getTip1())) {
							iv1.setVisibility(View.VISIBLE);
							tiaodong.sendEmptyMessageDelayed(1, 100);
							tip1 = userList.get(0).getTip1();
							x.add("1");
						}
						if (!TextUtils.isEmpty(userList.get(0).getTip2())) {
							iv2.setVisibility(View.VISIBLE);
							tiaodong2.sendEmptyMessageDelayed(1, 100);
							tip2 = userList.get(0).getTip2();
							x.add("2");
						}
						if (!TextUtils.isEmpty(userList.get(0).getTip3())) {
							iv3.setVisibility(View.VISIBLE);
							tiaodong3.sendEmptyMessageDelayed(1, 100);
							tip3 = userList.get(0).getTip3();
							x.add("3");
						}
						if (!TextUtils.isEmpty(userList.get(0).getTip4())) {
							iv4.setVisibility(View.VISIBLE);
							tiaodong4.sendEmptyMessageDelayed(1, 100);
							tip4 = userList.get(0).getTip4();
							x.add("4");
						}
						if (!TextUtils.isEmpty(userList.get(0).getTip5())) {
							xiaoshoudh.setVisibility(View.VISIBLE);
							tiaodong5.sendEmptyMessageDelayed(1, 100);
							tip5 = userList.get(0).getTip5();
							x.add("0");
						}
						if (isfristtime) {
							for (int i = 0; i < x.size(); i++) {
								if (Integer.parseInt(x.get(i)) < def) {
									def = Integer.parseInt(x.get(i));
								}
							}
							switch (def) {
							case 0:
								xiaoshoudh.setVisibility(View.VISIBLE);
								iv1.setVisibility(View.GONE);
								iv2.setVisibility(View.GONE);
								iv3.setVisibility(View.GONE);
								iv4.setVisibility(View.GONE);

								break;

							case 1:
								xiaoshoudh.setVisibility(View.GONE);
								iv1.setVisibility(View.VISIBLE);
								iv2.setVisibility(View.GONE);
								iv3.setVisibility(View.GONE);
								iv4.setVisibility(View.GONE);
								break;

							case 2:
								xiaoshoudh.setVisibility(View.GONE);
								iv1.setVisibility(View.GONE);
								iv2.setVisibility(View.VISIBLE);
								iv3.setVisibility(View.GONE);
								iv4.setVisibility(View.GONE);
								break;

							case 3:
								xiaoshoudh.setVisibility(View.GONE);
								iv1.setVisibility(View.GONE);
								iv2.setVisibility(View.GONE);
								iv3.setVisibility(View.VISIBLE);
								iv4.setVisibility(View.GONE);
								break;
							case 4:
								xiaoshoudh.setVisibility(View.GONE);
								iv1.setVisibility(View.GONE);
								iv2.setVisibility(View.GONE);
								iv3.setVisibility(View.GONE);
								iv4.setVisibility(View.VISIBLE);
								break;
							}
							def = 6;
							isfristtime = false;
						}
						if (userList.get(0) != null && userList.size() != 0) {
							pro_iv.setVisibility(View.VISIBLE);
							RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) pro_iv
									.getLayoutParams();
							if (userList.get(0).getJc() > 2) {
								llParams.width = (int) ((double) (jinduwidth + zuo) * ((double) userList
										.get(0).getJc() / (double) userList
										.get(0).getJct()));
							} else {
								// llParams.width = 2 ;
							}
							pro_iv.setLayoutParams(llParams);
							// Log.e("commm on 111", jinduwidth+zuo
							// +" ,"+userList.get(0).getJc()+","+userList.get(0).getJct()+","+llParams.width);
							RelativeLayout.LayoutParams llParam2 = (RelativeLayout.LayoutParams) pro_tiaodonghua
									.getLayoutParams();
							if (userList.get(0).getJc() > 2) {
								// pro_tiaodonghua.setVisibility(View.VISIBLE);
								llParam2.width = (int) ((double) (jinduwidth + zuo) * ((double) userList
										.get(0).getJc() / (double) userList
										.get(0).getJct()));
							} else {
								// pro_tiaodonghua.setVisibility(View.GONE);
							}
							pro_tiaodonghua.setLayoutParams(llParam2);
						}

						uid = su.getUid();
						nick = su.getNick();
						header = su.getHeader();
						token = su.getToken();
						if (userList.get(0).getLevel() == null) {
							userLevel.setText("村民");
						} else {
							userLevel.setText(userList.get(0).getLevel());
						}

						mainMdTextView.setText(String.valueOf(userList.get(0)
								.getMd()));
						mainMgTextView.setText(String.valueOf(userList.get(0)
								.getMg()));
						mainMsTextView.setText(String.valueOf(userList.get(0)
								.getMs()));

						if (userList.get(0).getTcount() == 0) {
							taskCount.setVisibility(View.GONE);
						} else {
							taskCount.setVisibility(View.VISIBLE);
							taskCount.setText(String.valueOf(userList.get(0)
									.getTcount()));
						}
						xiegang.setVisibility(View.VISIBLE);
						dengji.setText(userList.get(0).getJcl());
						jiecaodangqian.setText(String.valueOf(userList.get(0)
								.getJc()));
						jiecaoxuyao.setText(String.valueOf(userList.get(0)
								.getJct()));

						userImage.setTag(userList.get(0).getHeader());
						if (SystemUtil.getSDCardMount()) {
							picfile = new File(Environment
									.getExternalStorageDirectory()
									+ File.separator
									+ getPackageName()
									+ ConstantsJrc.PHOTO_PATH
									+ "/"
									+ Comment.getMd5Hash(userList.get(0)
											.getHeader()));
						} else {
							picfile = new File(ConstantsJrc.PROJECT_PATH
									+ getPackageName()
									+ ConstantsJrc.PHOTO_PATH
									+ "/"
									+ Comment.getMd5Hash(userList.get(0)
											.getHeader()));
						}

						userImage.setImageResource(R.drawable.photo);

						String filename = picfile.getPath().toString();
						Bitmap bmp1 = BitmapCacheRoomList.getIntance()
								.getCacheBitmap(filename, 0, 0);

						if (bmp1 == null) {
							userImage.setImageResource(R.drawable.photo);
							if (!headerurls.contains(userList.get(0)
									.getHeader())) {
								headerurls.add(userList.get(0).getHeader());
								new Thread(new LoadImageRunnable(
										MainMenuActivity.this, mPhotoHandler,
										0, userList.get(0).getHeader(),
										filename)).start();
							}

						} else {
							userImage.setImageBitmap(bmp1);
						}

						for (MailBean bean : mailBeanlist) {
							mMailBean = new MailBean();
							mMailBean.setId(bean.getId());
							mMailBean.setType(bean.getType());
							mMailBean.setFuid(bean.getFuid());
							mMailBean.setFnick(bean.getFnick());
							mMailBean.setFheader(bean.getFheader());
							mMailBean.setFsex(bean.getFsex());
							mMailBean.setTuid(bean.getTuid());
							mMailBean.setTnick(bean.getTnick());
							mMailBean.setTheader(bean.getTheader());
							mMailBean.setTsex(bean.getTsex());
							mMailBean.setContent(bean.getContent());
							mMailBean.setAfile(bean.getAfile());
							mMailBean.setPfile(bean.getPfile());
							mMailBean.setPtime(bean.getPtime());
							if (bean.getFuid().equals(su.getUid())) {
								mMailBean.setIsread("0");
							} else {
								mMailBean.setIsread(bean.getIsread());
								IsReadCount++;
							}
							db.Insert(mMailBean);
							addNotify(bean.getId(), bean.getFnick() + ":"
									+ bean.getContent());
						}

						// IsReadCount = db.getNoReadMail(1, su.getUid());
						// IsReadCount = mailBeanlist.size();
						su.setMailCount(IsReadCount);

						// Log.i("mail", IsReadCount + "个");
						if (su.getMailCount() > 0) {
							if (IsReadCount > 99) {
								messageCount.setVisibility(View.VISIBLE);
								messageCount.setText(String.valueOf(99));
							} else {
								messageCount.setVisibility(View.VISIBLE);
								messageCount.setText(String.valueOf(su
										.getMailCount()));
							}
						} else {
							messageCount.setVisibility(View.GONE);
						}

						if (!TextUtils.isEmpty(userList.get(0).getTip())) {
							Intent intent = new Intent(getApplicationContext(),
									MessageSystemDialog.class);
							intent.putExtra("ret", "0");
							intent.putExtra("tip", userList.get(0).getTip());
							startActivity(intent);
						}
						if (!TextUtils.isEmpty(userList.get(0).getDj())) {
							// Intent intent = new
							// Intent(getApplicationContext(),
							// WebviewDonghua.class);
							// intent.putExtra("uid", su.getUid());
							// intent.putExtra("path", userList.get(0).getDj());
							// startActivity(intent);
							donghuapre(userList.get(0).getDj());
							StringBuffer data = new StringBuffer();
							String url;
							url = Details.geturl(ConstantsJrc.WEB_DONGHUA);
							path = URLEncoder.encode(userList.get(0).getDj());
							url = dt.appendNameValue(url, "path", path);
							url = Details.appendNameValueint(url, "flg", flg);
							// 请求网络验证登陆
							// HttpGetData(url);
							new Thread(new getDate(url)).start();
						}
						if (!TextUtils.isEmpty(userList.get(0).getHelp())) {
							help = userList.get(0).getHelp();
							su.setHelp(help);
						}

						if (!TextUtils.isEmpty(userList.get(0).getShop())) {
							shop = userList.get(0).getShop();
							su.setShop(shop);
						}
						if (!TextUtils.isEmpty(userList.get(0).getHd())) {
							hd = userList.get(0).getHd();
							su.setActivity(hd);
						}
						if (!TextUtils.isEmpty(userList.get(0).getFfg())) {
							picfile = new File(Environment
									.getExternalStorageDirectory()
									+ File.separator
									+ getPackageName()
									+ ConstantsJrc.IMAGE_PATH
									+ "/"
									+ Comment.getMd5Hash(userList.get(0)
											.getFfg()));

							filename = picfile.getPath().toString();
							if (bmp == null) {
								new Thread(new LoadImageRunnable(
										MainMenuActivity.this, null, 0,
										userList.get(0).getFfg(), filename))
										.start();
							}
						}
						if (!TextUtils.isEmpty(userList.get(0).getFbg())) {
							picfile = new File(Environment
									.getExternalStorageDirectory()
									+ File.separator
									+ getPackageName()
									+ ConstantsJrc.IMAGE_PATH
									+ "/"
									+ Comment.getMd5Hash(userList.get(0)
											.getFbg()));
							filename2 = picfile.getPath().toString();

							if (picfile.exists()) {
								bmp2 = BitmapCache2.getIntance()
										.getCacheBitmap(filename2, 0, 0);
							}
							if (bmp2 == null) {
								new Thread(new LoadImageRunnable(
										MainMenuActivity.this, null, 0,
										userList.get(0).getFbg(), filename2))
										.start();
							}
						}

					} else if (userList.get(0).getRet() == 2) {
						su.setUid(String.valueOf(userList.get(0).getUid()));
						su.setNick(userList.get(0).getNick());
						su.setHeader(userList.get(0).getHeader());
						su.setSign(userList.get(0).getSign());
						su.setScore(String.valueOf(userList.get(0).getScore()));
						su.setLevel(userList.get(0).getLevel());
						su.setBirthday(userList.get(0).getBirthday());
						su.setCity(userList.get(0).getCity());
						su.setSex(userList.get(0).getSex());
						su.setMainPd(userList.get(0).getPd());
						su.setToken(userList.get(0).getToken());
						// su.setQQ(String.valueOf(userList.get(0).getQq()));
						// su.setWeixin(userList.get(0).getWeixin());
						userName.setText(userList.get(0).getNick());
						uid = su.getUid();
						nick = su.getNick();
						header = su.getHeader();
						token = su.getToken();

						if (!TextUtils.isEmpty(userList.get(0).getTip0())) {
							Intent toit = new Intent();
							toit.setClass(MainMenuActivity.this, Tixing.class);
							toit.putExtra("where", 5);
							toit.putExtra("tp", userList.get(0).getTip0());
							startActivity(toit);
						}

						if (!TextUtils.isEmpty(userList.get(0).getTip1())) {
							iv1.setVisibility(View.VISIBLE);
							tiaodong.sendEmptyMessageDelayed(1, 100);
							tip1 = userList.get(0).getTip1();
							x.add("1");
						}
						if (!TextUtils.isEmpty(userList.get(0).getTip2())) {
							iv2.setVisibility(View.VISIBLE);
							tiaodong2.sendEmptyMessageDelayed(1, 100);
							tip2 = userList.get(0).getTip2();
							x.add("2");
						}
						if (!TextUtils.isEmpty(userList.get(0).getTip3())) {
							iv3.setVisibility(View.VISIBLE);
							tiaodong3.sendEmptyMessageDelayed(1, 100);
							tip3 = userList.get(0).getTip3();
							x.add("3");
						}
						if (!TextUtils.isEmpty(userList.get(0).getTip4())) {
							iv4.setVisibility(View.VISIBLE);
							tiaodong4.sendEmptyMessageDelayed(1, 100);
							tip4 = userList.get(0).getTip4();
							x.add("4");
						}
						if (!TextUtils.isEmpty(userList.get(0).getTip5())) {
							xiaoshoudh.setVisibility(View.VISIBLE);
							tiaodong5.sendEmptyMessageDelayed(1, 100);
							tip5 = userList.get(0).getTip5();
							x.add("0");
						}
						if (userList.get(0).getLevel() == null) {
							userLevel.setText("村民");
						} else {
							userLevel.setText(userList.get(0).getLevel());
						}
						if (isfristtime) {
							for (int i = 0; i < x.size(); i++) {
								if (Integer.parseInt(x.get(i)) < def) {
									def = Integer.parseInt(x.get(i));
								}
							}
							switch (def) {
							case 0:
								xiaoshoudh.setVisibility(View.VISIBLE);
								iv1.setVisibility(View.GONE);
								iv2.setVisibility(View.GONE);
								iv3.setVisibility(View.GONE);
								iv4.setVisibility(View.GONE);

								break;

							case 1:
								xiaoshoudh.setVisibility(View.GONE);
								iv1.setVisibility(View.VISIBLE);
								iv2.setVisibility(View.GONE);
								iv3.setVisibility(View.GONE);
								iv4.setVisibility(View.GONE);
								break;

							case 2:
								xiaoshoudh.setVisibility(View.GONE);
								iv1.setVisibility(View.GONE);
								iv2.setVisibility(View.VISIBLE);
								iv3.setVisibility(View.GONE);
								iv4.setVisibility(View.GONE);
								break;

							case 3:
								xiaoshoudh.setVisibility(View.GONE);
								iv1.setVisibility(View.GONE);
								iv2.setVisibility(View.GONE);
								iv3.setVisibility(View.VISIBLE);
								iv4.setVisibility(View.GONE);
								break;
							case 4:
								xiaoshoudh.setVisibility(View.GONE);
								iv1.setVisibility(View.GONE);
								iv2.setVisibility(View.GONE);
								iv3.setVisibility(View.GONE);
								iv4.setVisibility(View.VISIBLE);
								break;
							}
							def = 6;
							isfristtime = false;
						}
						mainMdTextView.setText(String.valueOf(userList.get(0)
								.getMd()));
						mainMgTextView.setText(String.valueOf(userList.get(0)
								.getMg()));
						mainMsTextView.setText(String.valueOf(userList.get(0)
								.getMs()));
						if (userList.get(0).getTcount() == 0) {
							taskCount.setVisibility(View.GONE);
						} else {
							taskCount.setVisibility(View.VISIBLE);
							taskCount.setText(String.valueOf(userList.get(0)
									.getTcount()));
						}
						xiegang.setVisibility(View.VISIBLE);
						dengji.setText(userList.get(0).getJcl());
						jiecaodangqian.setText(String.valueOf(userList.get(0)
								.getJc()));
						jiecaoxuyao.setText(String.valueOf(userList.get(0)
								.getJct()));
						if (userList.get(0) != null && userList.size() != 0) {
							pro_iv.setVisibility(View.VISIBLE);
							RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) pro_iv
									.getLayoutParams();
							if (userList.get(0).getJc() > 2) {
								llParams.width = (int) ((double) (jinduwidth + zuo) * ((double) userList
										.get(0).getJc() / (double) userList
										.get(0).getJct()));
							} else {
								// llParams.width = 2 ;
							}

							pro_iv.setLayoutParams(llParams);
							RelativeLayout.LayoutParams llParam2 = (RelativeLayout.LayoutParams) pro_tiaodonghua
									.getLayoutParams();
							if (userList.get(0).getJc() > 2) {
								// pro_tiaodonghua.setVisibility(View.VISIBLE);
								llParam2.width = (int) ((double) (jinduwidth + zuo) * ((double) userList
										.get(0).getJc() / (double) userList
										.get(0).getJct()));
							} else {
								// pro_tiaodonghua.setVisibility(View.GONE);
							}
							pro_tiaodonghua.setLayoutParams(llParam2);
						}
						// Log.e("commm on 222", jinduwidth+zuo
						// +" ,"+userList.get(0).getJc()+","+userList.get(0).getJct()+","+llParams.width);
						userImage.setTag(userList.get(0).getHeader());
						if (SystemUtil.getSDCardMount()) {
							picfile = new File(Environment
									.getExternalStorageDirectory()
									+ File.separator
									+ getPackageName()
									+ ConstantsJrc.PHOTO_PATH
									+ "/"
									+ Comment.getMd5Hash(userList.get(0)
											.getHeader()));
						} else {
							picfile = new File(ConstantsJrc.PROJECT_PATH
									+ getPackageName()
									+ ConstantsJrc.PHOTO_PATH
									+ "/"
									+ Comment.getMd5Hash(userList.get(0)
											.getHeader()));
						}

						userImage.setImageResource(R.drawable.photo);

						String filename = picfile.getPath().toString();
						Bitmap bmp1 = BitmapCacheRoomList.getIntance()
								.getCacheBitmap(filename, 0, 0);

						if (bmp1 == null) {
							userImage.setImageResource(R.drawable.photo);
							if (!headerurls.contains(userList.get(0)
									.getHeader())) {
								headerurls.add(userList.get(0).getHeader());
								new Thread(new LoadImageRunnable(
										MainMenuActivity.this, mPhotoHandler,
										0, userList.get(0).getHeader(),
										filename)).start();
							}

						} else {
							userImage.setImageBitmap(bmp1);
						}
						for (MailBean bean : mailBeanlist) {
							mMailBean = new MailBean();
							mMailBean.setId(bean.getId());
							mMailBean.setType(bean.getType());
							mMailBean.setFuid(bean.getFuid());
							mMailBean.setFnick(bean.getFnick());
							mMailBean.setFheader(bean.getFheader());
							mMailBean.setFsex(bean.getFsex());
							mMailBean.setTuid(bean.getTuid());
							mMailBean.setTnick(bean.getTnick());
							mMailBean.setTheader(bean.getTheader());
							mMailBean.setTsex(bean.getTsex());
							mMailBean.setContent(bean.getContent());
							mMailBean.setAfile(bean.getAfile());
							mMailBean.setPfile(bean.getPfile());
							mMailBean.setPtime(bean.getPtime());
							if (bean.getFuid().equals(su.getUid())) {
								mMailBean.setIsread("0");

							} else {
								mMailBean.setIsread(bean.getIsread());
								IsReadCount++;
							}
							db.Insert(mMailBean);
						}

						// IsReadCount = db.getNoReadMail(1, su.getUid());
						// IsReadCount = mailBeanlist.size();
						su.setMailCount(IsReadCount);

						// Log.i("mail", IsReadCount + "个");
						if (su.getMailCount() > 0) {
							if (IsReadCount > 99) {
								messageCount.setVisibility(View.VISIBLE);
								messageCount.setText(String.valueOf(99));
							} else {
								messageCount.setVisibility(View.VISIBLE);
								messageCount.setText(String.valueOf(su
										.getMailCount()));
							}
						} else {
							messageCount.setVisibility(View.GONE);
						}
						if (!TextUtils.isEmpty(userList.get(0).getDj())) {
							// Intent intent = new
							// Intent(getApplicationContext(),
							// WebviewDonghua.class);
							// intent.putExtra("uid", su.getUid());
							// intent.putExtra("path", userList.get(0).getDj());
							// startActivity(intent);
							donghuapre(userList.get(0).getDj());
							StringBuffer data = new StringBuffer();
							String url;
							url = Details.geturl(ConstantsJrc.WEB_DONGHUA);
							path = URLEncoder.encode(userList.get(0).getDj());
							url = dt.appendNameValue(url, "path", path);
							url = Details.appendNameValueint(url, "flg", flg);
							// 请求网络验证登陆
							new Thread(new getDate(url)).start();
						}
						if (!TextUtils.isEmpty(userList.get(0).getHelp())) {
							help = userList.get(0).getHelp();
							su.setHelp(help);
						}
						if (!TextUtils.isEmpty(userList.get(0).getShop())) {
							shop = userList.get(0).getShop();
							su.setShop(shop);
						}
						if (!TextUtils.isEmpty(userList.get(0).getHd())) {
							hd = userList.get(0).getHd();
							su.setActivity(hd);
						}
						if (!TextUtils.isEmpty(userList.get(0).getFfg())) {
							picfile = new File(Environment
									.getExternalStorageDirectory()
									+ File.separator
									+ getPackageName()
									+ ConstantsJrc.IMAGE_PATH
									+ "/"
									+ Comment.getMd5Hash(userList.get(0)
											.getFfg()));
							filename = picfile.getPath().toString();

							if (picfile.exists()) {
								bmp = BitmapCache2.getIntance().getCacheBitmap(
										filename, 0, 0);
							}
							if (bmp == null) {
								new Thread(new LoadImageRunnable(
										MainMenuActivity.this, null, 0,
										userList.get(0).getFfg(), filename))
										.start();
							}
						}
						if (!TextUtils.isEmpty(userList.get(0).getFbg())) {
							picfile = new File(Environment
									.getExternalStorageDirectory()
									+ File.separator
									+ getPackageName()
									+ ConstantsJrc.IMAGE_PATH
									+ "/"
									+ Comment.getMd5Hash(userList.get(0)
											.getFbg()));
							filename2 = picfile.getPath().toString();

							if (picfile.exists()) {
								bmp2 = BitmapCache2.getIntance()
										.getCacheBitmap(filename2, 0, 0);
							}
							if (bmp2 == null) {
								new Thread(new LoadImageRunnable(
										MainMenuActivity.this, null, 0,
										userList.get(0).getFbg(), filename2))
										.start();
							}
						}
					}
					loadingDataTime.schedule(new MyTimerTask(), 100, 15000);
					IsReadCount = 0;
				} catch (Exception e) {

				}
			}
		});
	}

	/**
	 * 定时器取数据，15秒获取一次网络数据
	 * 
	 * @author J.King
	 * 
	 */
	public class MyTimerTask extends TimerTask {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (_run) {
				try {
					loadingData();
					Message msg = new Message();
					Bundle b = new Bundle();
					b.putInt("count", su.getMailCount());
					b.putString("nick", su.getNick());
					b.putString("level", su.getLevel());
					b.putString("header", su.getHeader());
					b.putString("md", su.getMd());
					b.putString("mg", su.getMg());
					b.putString("ms", su.getMs());
					b.putString("tcount", su.getTcount());
					b.putString("jcl", su.getJcl());
					b.putInt("jc", su.getJc());
					b.putInt("jct", su.getJct());
					msg.setData(b);
					myTimerHandler.sendMessage(msg);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	private Handler myTimerHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Bundle b = msg.getData();
			int num = b.getInt("count");
			String nick = b.getString("nick");
			String level = b.getString("level");
			String header = b.getString("header");
			String md = b.getString("md");
			String mg = b.getString("mg");
			String ms = b.getString("ms");
			String tcount = b.getString("tcount");
			String jcl = b.getString("jcl");
			int jc = b.getInt("jc");
			int jct = b.getInt("jct");
			try {
				if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
					return;
				}
				if (userList.size() == 0 || userList.get(0).getTip0() == null) {
					return;
				}

				if (!TextUtils.isEmpty(userList.get(0).getTip0())) {
					Intent toit = new Intent();
					toit.setClass(MainMenuActivity.this, Tixing.class);
					toit.putExtra("where", 5);
					toit.putExtra("tp", userList.get(0).getTip0());
					startActivity(toit);
				}

				if (!TextUtils.isEmpty(userList.get(0).getTip1())) {
					iv1.setVisibility(View.VISIBLE);
					tiaodong.sendEmptyMessageDelayed(1, 100);
					tip1 = userList.get(0).getTip1();
					x.add("1");
				}
				if (!TextUtils.isEmpty(userList.get(0).getTip2())) {
					iv2.setVisibility(View.VISIBLE);
					tiaodong2.sendEmptyMessageDelayed(1, 100);
					tip2 = userList.get(0).getTip2();
					x.add("2");
				}
				if (!TextUtils.isEmpty(userList.get(0).getTip3())) {
					iv3.setVisibility(View.VISIBLE);
					tiaodong3.sendEmptyMessageDelayed(1, 100);
					tip3 = userList.get(0).getTip3();
					x.add("3");
				}
				if (!TextUtils.isEmpty(userList.get(0).getTip4())) {
					iv4.setVisibility(View.VISIBLE);
					tiaodong4.sendEmptyMessageDelayed(1, 100);
					tip4 = userList.get(0).getTip4();
					x.add("4");
				}
				if (!TextUtils.isEmpty(userList.get(0).getTip5())) {
					xiaoshoudh.setVisibility(View.VISIBLE);
					tiaodong5.sendEmptyMessageDelayed(1, 100);
					tip5 = userList.get(0).getTip5();
					x.add("0");
				}
				if (isfristtime) {
					for (int i = 0; i < x.size(); i++) {
						if (Integer.parseInt(x.get(i)) < def) {
							def = Integer.parseInt(x.get(i));
						}
					}
					switch (def) {
					case 0:
						xiaoshoudh.setVisibility(View.VISIBLE);
						iv1.setVisibility(View.GONE);
						iv2.setVisibility(View.GONE);
						iv3.setVisibility(View.GONE);
						iv4.setVisibility(View.GONE);

						break;

					case 1:
						xiaoshoudh.setVisibility(View.GONE);
						iv1.setVisibility(View.VISIBLE);
						iv2.setVisibility(View.GONE);
						iv3.setVisibility(View.GONE);
						iv4.setVisibility(View.GONE);
						break;

					case 2:
						xiaoshoudh.setVisibility(View.GONE);
						iv1.setVisibility(View.GONE);
						iv2.setVisibility(View.VISIBLE);
						iv3.setVisibility(View.GONE);
						iv4.setVisibility(View.GONE);
						break;

					case 3:
						xiaoshoudh.setVisibility(View.GONE);
						iv1.setVisibility(View.GONE);
						iv2.setVisibility(View.GONE);
						iv3.setVisibility(View.VISIBLE);
						iv4.setVisibility(View.GONE);
						break;
					case 4:
						xiaoshoudh.setVisibility(View.GONE);
						iv1.setVisibility(View.GONE);
						iv2.setVisibility(View.GONE);
						iv3.setVisibility(View.GONE);
						iv4.setVisibility(View.VISIBLE);
						break;
					}
					def = 6;
					isfristtime = false;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();

			}

			if (num > 0) {
				// System.out.println("获得：" + num);
				if (num > 99) {
					messageCount.setVisibility(View.VISIBLE);
					messageCount.setText(String.valueOf(99));
				} else {
					messageCount.setVisibility(View.VISIBLE);
					messageCount.setText(String.valueOf(num));
				}
			} else {
				messageCount.setVisibility(View.GONE);
			}
			if (userList.get(0) != null && userList.size() != 0) {
				xiegang.setVisibility(View.VISIBLE);
				dengji.setText(userList.get(0).getJcl());
				jiecaodangqian.setText(String.valueOf(userList.get(0).getJc()));
				jiecaoxuyao.setText(String.valueOf(userList.get(0).getJct()));
			}
			userName.setText(nick);
			if (level == null) {
				userLevel.setText("村民");
			} else {
				userLevel.setText(level);
			}
			if (istj == 0) {
				mainJPBtn.setVisibility(View.VISIBLE);
			} else if (istj == 1) {
				mainJPBtn.setVisibility(View.GONE);
			}
			if (isshop == 0) {
				mainShopBtn.setVisibility(View.VISIBLE);
			} else if (isshop == 1) {
				mainShopBtn.setVisibility(View.GONE);
			}
			mainMdTextView.setText(md);
			mainMgTextView.setText(mg);
			mainMsTextView.setText(ms);
			if (TextUtils.isEmpty(tcount) || "0".equals(tcount)) {
				taskCount.setVisibility(View.GONE);
			} else {
				taskCount.setVisibility(View.VISIBLE);
				taskCount.setText(tcount);
			}

			userImage.setTag(header);
			if (SystemUtil.getSDCardMount()) {
				picfile = new File(Environment.getExternalStorageDirectory()
						+ File.separator + getPackageName()
						+ ConstantsJrc.PHOTO_PATH + "/"
						+ Comment.getMd5Hash(header));
			} else {
				picfile = new File(ConstantsJrc.PROJECT_PATH + getPackageName()
						+ ConstantsJrc.PHOTO_PATH + "/"
						+ Comment.getMd5Hash(header));
			}

			userImage.setImageResource(R.drawable.photo);

			String filename = picfile.getPath().toString();
			Bitmap bmp1 = BitmapCacheRoomList.getIntance().getCacheBitmap(
					filename, 0, 0);

			if (bmp1 == null) {
				userImage.setImageResource(R.drawable.photo);
				if (!headerurls.contains(header)) {
					headerurls.add(header);
					new Thread(new LoadImageRunnable(MainMenuActivity.this,
							mPhotoHandler, 0, header, filename)).start();
				}
			} else {
				userImage.setImageBitmap(bmp1);
			}
			if (userList.get(0) != null && userList.size() != 0) {
				pro_iv.setVisibility(View.VISIBLE);
				RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) pro_iv
						.getLayoutParams();
				if (userList.get(0).getJc() > 2) {
					llParams.width = (int) ((double) (jinduwidth + zuo) * ((double) userList
							.get(0).getJc() / (double) userList.get(0).getJct()));
				} else {
					// llParams.width = 2 ;
				}
				pro_iv.setLayoutParams(llParams);
				RelativeLayout.LayoutParams llParam2 = (RelativeLayout.LayoutParams) pro_tiaodonghua
						.getLayoutParams();
				if (userList.get(0).getJc() > 2) {
					// pro_tiaodonghua.setVisibility(View.VISIBLE);
					llParam2.width = (int) ((double) (jinduwidth + zuo) * ((double) userList
							.get(0).getJc() / (double) userList.get(0).getJct()));
				} else {
					// pro_tiaodonghua.setVisibility(View.GONE);
				}
				pro_tiaodonghua.setLayoutParams(llParam2);
			}
		}

	};

	private void loadingData() throws ClientProtocolException, IOException {
		db.Open();

		final String result = HttpLoginServer.LoginPost(su.getUid(),
				su.getMainPd(), imei, imsi, URLEncoder.encode(model), osver,
				width, height, vercode, ver, "", sim, lang, pkg,
				URLEncoder.encode(mac), URLEncoder.encode(cid), su.getToken());
		// System.out.println(result);
		UserInfoJson uj = new UserInfoJson();
		userList = uj.parseJson(result);
		GetMailJson mMaliJson = new GetMailJson();
		mailBeanlist = mMaliJson.parseJson(result);
		File fw = new File(jspath + File.separator + jsname);
		if (!fw.exists()) {
			if (!su.getUid().equals(String.valueOf(userList.get(0).getUid()))) {
				su.setUid(String.valueOf(userList.get(0).getUid()));
			}
			SystemUtil.writeUidJs(
					jspath,
					jsname,
					"var uid="
							+ su.getUid()
							+ ";\r\n"
							+ "var pkg='"
							+ PhoneInfo.getInstance(getApplicationContext())
									.getPackage(this)
							+ "';"
							+ "\r\n"
							+ "var ver='"
							+ PhoneInfo.getInstance(getApplicationContext())
									.getVersionName(MainMenuActivity.this)
							+ "';" + "\r\n" + "var hack=0;", false);
		} else {
			String ss = su.getUid();
			if (!SystemUtil.readFileCompareText(jspath, jsname, "pkg")
					|| !su.getUid().equals(
							String.valueOf(userList.get(0).getUid()))
					|| !SystemUtil.readFileCompareText(jspath, jsname, "ver")
					|| !SystemUtil.readFileCompareText(jspath, jsname, "uid")) {

				su.setUid(String.valueOf(userList.get(0).getUid()));
				SystemUtil.writeUidJs(
						jspath,
						jsname,
						"var uid="
								+ su.getUid()
								+ ";\r\n"
								+ "var pkg='"
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getPackage(this)
								+ "';"
								+ "\r\n"
								+ "var ver='"
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getVersionName(MainMenuActivity.this)
								+ "';" + "\r\n" + "var hack=0;", false);
			}
		}
		if (userList.get(0).getRet() == 0) {
			if (!TextUtils.isEmpty(userList.get(0).getTip())) {
				Intent intent = new Intent(getApplicationContext(),
						MessageSystemDialog.class);
				intent.putExtra("ret", "0");
				intent.putExtra("tip", userList.get(0).getTip());
				startActivity(intent);
			}
		} else if (userList.get(0).getRet() == 1) {
			if (!su.getUid().equals(String.valueOf(userList.get(0).getUid()))) {
				try {
					SystemUtil.writeUidJs(
							jspath,
							jsname,
							"var uid="
									+ su.getUid()
									+ ";\r\n"
									+ "var pkg='"
									+ PhoneInfo.getInstance(
											getApplicationContext())
											.getPackage(MainMenuActivity.this)
									+ "';"
									+ "\r\n"
									+ "var ver='"
									+ PhoneInfo.getInstance(
											getApplicationContext())
											.getVersionName(
													MainMenuActivity.this)
									+ "';" + "\r\n" + "var hack=0;", false);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			su.setUid(String.valueOf(userList.get(0).getUid()));
			istj = userList.get(0).getIstj();

			isshop = userList.get(0).getIsshop();
			if (!userList.get(0).getPd().equals(""))
				su.setMainPd(userList.get(0).getPd());
			if (!userList.get(0).getNick().equals(""))
				su.setNick(userList.get(0).getNick());
			if (!userList.get(0).getHeader().equals(""))
				su.setHeader(userList.get(0).getHeader());
			if (!userList.get(0).getSign().equals(""))
				su.setSign(userList.get(0).getSign());
			if (!userList.get(0).getScore().equals(""))
				su.setScore(userList.get(0).getScore());
			if (!userList.get(0).getLevel().equals(""))
				su.setLevel(userList.get(0).getLevel());
			if (!userList.get(0).getBirthday().equals(""))
				su.setBirthday(userList.get(0).getBirthday());
			if (!userList.get(0).getCity().equals(""))
				su.setCity(userList.get(0).getCity());
			if (!userList.get(0).getSex().equals(""))
				su.setSex(userList.get(0).getSex());
			if (!String.valueOf(userList.get(0).getMd()).equals(""))
				su.setMd(String.valueOf(userList.get(0).getMd()));
			if (!String.valueOf(userList.get(0).getMg()).equals(""))
				su.setMg(String.valueOf(userList.get(0).getMg()));
			if (!String.valueOf(userList.get(0).getMs()).equals(""))
				su.setMs(String.valueOf(userList.get(0).getMs()));
			if (!String.valueOf(userList.get(0).getTcount()).equals(""))
				su.setTcount(String.valueOf(userList.get(0).getTcount()));
			if (!userList.get(0).getToken().equals(""))
				su.setToken(userList.get(0).getToken());

			for (MailBean bean : mailBeanlist) {
				mMailBean = new MailBean();
				mMailBean.setId(bean.getId());
				mMailBean.setType(bean.getType());
				mMailBean.setFuid(bean.getFuid());
				mMailBean.setFnick(bean.getFnick());
				mMailBean.setFheader(bean.getFheader());
				mMailBean.setFsex(bean.getFsex());
				mMailBean.setTuid(bean.getTuid());
				mMailBean.setTnick(bean.getTnick());
				mMailBean.setTheader(bean.getTheader());
				mMailBean.setTsex(bean.getTsex());
				mMailBean.setContent(bean.getContent());
				mMailBean.setAfile(bean.getAfile());
				mMailBean.setPfile(bean.getPfile());
				mMailBean.setPtime(bean.getPtime());
				if (bean.getFuid().equals(su.getUid())) {
					mMailBean.setIsread("0");
				} else {
					mMailBean.setIsread(bean.getIsread());
					IsReadCount++;
				}
				db.Insert(mMailBean);
				if ("1".equals(bean.getType())) {
					addNotify(bean.getId(),
							bean.getFnick() + ":" + bean.getContent());
				}
			}

			if (!TextUtils.isEmpty(userList.get(0).getTip())) {
				Intent intent = new Intent(getApplicationContext(),
						MessageSystemDialog.class);
				intent.putExtra("ret", "0");
				intent.putExtra("tip", userList.get(0).getTip());
				startActivity(intent);
			}

			if (!TextUtils.isEmpty(userList.get(0).getDj())) {
				// Intent intent = new Intent(getApplicationContext(),
				// WebviewDonghua.class);
				// intent.putExtra("uid", su.getUid());
				// intent.putExtra("path", userList.get(0).getDj());
				// startActivity(intent);
				donghuapre(userList.get(0).getDj());
				StringBuffer data = new StringBuffer();
				String url;
				url = Details.geturl(ConstantsJrc.WEB_DONGHUA);
				path = URLEncoder.encode(userList.get(0).getDj());
				url = dt.appendNameValue(url, "path", path);
				url = Details.appendNameValueint(url, "flg", flg);
				// 请求网络验证登陆
				new Thread(new getDate(url)).start();
			}
			if (!TextUtils.isEmpty(userList.get(0).getHelp())) {
				help = userList.get(0).getHelp();
				su.setHelp(help);
			}

			if (!TextUtils.isEmpty(userList.get(0).getFfg())) {
				picfile = new File(Environment.getExternalStorageDirectory()
						+ File.separator + getPackageName()
						+ ConstantsJrc.IMAGE_PATH + "/"
						+ Comment.getMd5Hash(userList.get(0).getFfg()));
				filename = picfile.getPath().toString();
				su.setFfg(userList.get(0).getFfg());
				if (picfile.exists()) {
					bmp = BitmapCache2.getIntance().getCacheBitmap(filename, 0,
							0);
				}
				if (bmp == null) {
					new Thread(new LoadImageRunnable(MainMenuActivity.this,
							null, 0, userList.get(0).getFfg(), filename))
							.start();
				}
			} else {
				su.setFfg("");
			}
			if (!TextUtils.isEmpty(userList.get(0).getFbg())) {
				picfile = new File(Environment.getExternalStorageDirectory()
						+ File.separator + getPackageName()
						+ ConstantsJrc.IMAGE_PATH + "/"
						+ Comment.getMd5Hash(userList.get(0).getFbg()));
				filename2 = picfile.getPath().toString();
				su.setFbg(userList.get(0).getFbg());
				if (picfile.exists()) {
					bmp2 = BitmapCache2.getIntance().getCacheBitmap(filename2,
							0, 0);
				}
				if (bmp2 == null) {
					new Thread(new LoadImageRunnable(MainMenuActivity.this,
							null, 0, userList.get(0).getFbg(), filename2))
							.start();
				}
			} else {
				su.setFbg("");
			}
			if (!TextUtils.isEmpty(userList.get(0).getShop())) {
				shop = userList.get(0).getShop();
				su.setShop(shop);
			}
			if (!TextUtils.isEmpty(userList.get(0).getHd())) {
				hd = userList.get(0).getHd();
				su.setActivity(hd);
			}

		} else if (userList.get(0).getRet() == 2) {
			if (!su.getUid().equals(String.valueOf(userList.get(0).getUid()))) {
				try {
					SystemUtil.writeUidJs(
							jspath,
							jsname,
							"var uid="
									+ su.getUid()
									+ ";\r\n"
									+ "var pkg='"
									+ PhoneInfo.getInstance(
											getApplicationContext())
											.getPackage(MainMenuActivity.this)
									+ "';"
									+ "\r\n"
									+ "var ver='"
									+ PhoneInfo.getInstance(
											getApplicationContext())
											.getVersionName(
													MainMenuActivity.this)
									+ "';" + "\r\n" + "var hack=0;", false);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			su.setUid(String.valueOf(userList.get(0).getUid()));
			istj = userList.get(0).getIstj();
			isshop = userList.get(0).getIsshop();
			if (!userList.get(0).getPd().equals(""))
				su.setMainPd(userList.get(0).getPd());
			if (!userList.get(0).getNick().equals(""))
				su.setNick(userList.get(0).getNick());
			if (!userList.get(0).getHeader().equals(""))
				su.setHeader(userList.get(0).getHeader());
			if (!userList.get(0).getSign().equals(""))
				su.setSign(userList.get(0).getSign());
			if (!userList.get(0).getScore().equals(""))
				su.setScore(userList.get(0).getScore());
			if (!userList.get(0).getLevel().equals(""))
				su.setLevel(userList.get(0).getLevel());
			if (!userList.get(0).getBirthday().equals(""))
				su.setBirthday(userList.get(0).getBirthday());
			if (!userList.get(0).getCity().equals(""))
				su.setCity(userList.get(0).getCity());
			if (!userList.get(0).getSex().equals(""))
				su.setSex(userList.get(0).getSex());
			if (!String.valueOf(userList.get(0).getMd()).equals(""))
				su.setMd(String.valueOf(userList.get(0).getMd()));
			if (!String.valueOf(userList.get(0).getMg()).equals(""))
				su.setMg(String.valueOf(userList.get(0).getMg()));
			if (!String.valueOf(userList.get(0).getMs()).equals(""))
				su.setMs(String.valueOf(userList.get(0).getMs()));
			if (!String.valueOf(userList.get(0).getTcount()).equals(""))
				su.setTcount(String.valueOf(userList.get(0).getTcount()));
			if (!userList.get(0).getToken().equals(""))
				su.setToken(userList.get(0).getToken());
			for (MailBean bean : mailBeanlist) {
				mMailBean = new MailBean();
				mMailBean.setId(bean.getId());
				mMailBean.setType(bean.getType());
				mMailBean.setFuid(bean.getFuid());
				mMailBean.setFnick(bean.getFnick());
				mMailBean.setFheader(bean.getFheader());
				mMailBean.setFsex(bean.getFsex());
				mMailBean.setTuid(bean.getTuid());
				mMailBean.setTnick(bean.getTnick());
				mMailBean.setTheader(bean.getTheader());
				mMailBean.setTsex(bean.getTsex());
				mMailBean.setContent(bean.getContent());
				mMailBean.setAfile(bean.getAfile());
				mMailBean.setPfile(bean.getPfile());
				mMailBean.setPtime(bean.getPtime());
				if (bean.getFuid().equals(su.getUid())) {
					mMailBean.setIsread("0");
				} else {
					mMailBean.setIsread(bean.getIsread());
					IsReadCount++;
				}

				db.Insert(mMailBean);

				if ("1".equals(bean.getType()))
					addNotify(bean.getId(),
							bean.getFnick() + ":" + bean.getContent());

			}
			// 关闭更新
			// Intent intent = new Intent(getApplicationContext(),
			// MessageDownDialog.class);
			// intent.putExtra("ret", String.valueOf(userList.get(0).getRet()));
			// intent.putExtra("tip", userList.get(0).getTip().toString());
			// intent.putExtra("vname", userList.get(0).getVname().toString());
			// intent.putExtra("vsize",
			// String.valueOf(userList.get(0).getVsize()));
			// intent.putExtra("vurl", userList.get(0).getVurl().toString());
			// startActivity(intent);

			if (!TextUtils.isEmpty(userList.get(0).getDj())) {
				// Intent intent1 = new Intent(getApplicationContext(),
				// WebviewDonghua.class);
				// intent1.putExtra("uid", su.getUid());
				// intent1.putExtra("path", userList.get(0).getDj());
				// startActivity(intent1);
				donghuapre(userList.get(0).getDj());
				StringBuffer data = new StringBuffer();
				String url;
				url = Details.geturl(ConstantsJrc.WEB_DONGHUA);
				path = URLEncoder.encode(userList.get(0).getDj());
				url = dt.appendNameValue(url, "path", path);
				url = Details.appendNameValueint(url, "flg", flg);
				// 请求网络验证登陆
				new Thread(new getDate(url)).start();
			}
			if (!TextUtils.isEmpty(userList.get(0).getHelp())) {
				help = userList.get(0).getHelp();
				su.setHelp(help);
			}
			if (!TextUtils.isEmpty(userList.get(0).getShop())) {
				shop = userList.get(0).getShop();
				su.setShop(shop);
			}
			if (!TextUtils.isEmpty(userList.get(0).getHd())) {
				hd = userList.get(0).getHd();
				su.setActivity(hd);
			}

			if (!TextUtils.isEmpty(userList.get(0).getFfg())) {
				picfile = new File(Environment.getExternalStorageDirectory()
						+ File.separator + getPackageName()
						+ ConstantsJrc.IMAGE_PATH + "/"
						+ Comment.getMd5Hash(userList.get(0).getFfg()));
				filename = picfile.getPath().toString();

				if (picfile.exists()) {
					bmp = BitmapCache2.getIntance().getCacheBitmap(filename, 0,
							0);
				}
				if (bmp == null) {
					new Thread(new LoadImageRunnable(MainMenuActivity.this,
							null, 0, userList.get(0).getFfg(), filename))
							.start();
				}
			}
			if (!TextUtils.isEmpty(userList.get(0).getFbg())) {
				picfile = new File(Environment.getExternalStorageDirectory()
						+ File.separator + getPackageName()
						+ ConstantsJrc.IMAGE_PATH + "/"
						+ Comment.getMd5Hash(userList.get(0).getFbg()));
				filename2 = picfile.getPath().toString();

				if (picfile.exists()) {
					bmp2 = BitmapCache2.getIntance().getCacheBitmap(filename2,
							0, 0);
				}
				if (bmp2 == null) {
					new Thread(new LoadImageRunnable(MainMenuActivity.this,
							null, 0, userList.get(0).getFbg(), filename2))
							.start();
				}
			}
		}
		// IsReadCount = db.getNoReadMail(1, su.getUid());
		db.Close();
		// IsReadCount = mailBeanlist.size();
		if (IsReadCount > 0) {
			if (su.getMailCount() > 0) {
				su.setMailCount(su.getMailCount() + IsReadCount);
			} else {
				su.setMailCount(IsReadCount);
			}
		}
		// System.out.println("定时获取私信：" + IsReadCount + "个");
		// Log.i("mail", "定时获取私信：" + IsReadCount + "个");
		IsReadCount = 0;
	}

	private class CheckInfoThread extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					check();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Handler checkhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:
				// dialog.setCancelable(true);
				// dialog.show();
				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				// dialog.cancel();
				checkThread.stopThread(false);
				break;
			}
		};
	};

	private void check() throws ClientProtocolException, IOException {
		checkhandler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);

		final String result = HttpCheck
				.CheckInfoGet(su.getUid().toString(),
						PhoneInfo.getInstance(getApplicationContext())
								.getVersionCode(this),
						PhoneInfo.getInstance(getApplicationContext())
								.getVersionName(this),
						PhoneInfo.getInstance(getApplicationContext())
								.getPackage(this), su.getToken());
		// System.out.println(result);
		MessageJson mj = new MessageJson();
		messageList = mj.parseJsonAuth(result);

		checkhandler.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (messageList.get(0).getRet().equals("1")) {
						systemCloseButton.setVisibility(View.VISIBLE);
						systemRelative.setVisibility(View.VISIBLE);
						sysWebView.setVisibility(View.VISIBLE);
						checkhandler
								.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
					}

				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

	class ReLoadPhtotThread extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					ReloadPhoto();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Handler reloadhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:
				// dialog.setCancelable(true);
				// dialog.show();
				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				// dialog.cancel();
				reloadThread.stopThread(false);
				break;
			}
		};
	};

	private void ReloadPhoto() throws ClientProtocolException, IOException {
		reloadhandler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);

		reloadhandler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		reloadhandler.post(new Runnable() {

			@Override
			public void run() {
				try {

					userImage.setTag(su.getHeader());
					if (SystemUtil.getSDCardMount()) {
						picfile = new File(Environment
								.getExternalStorageDirectory()
								+ File.separator
								+ getPackageName()
								+ ConstantsJrc.PHOTO_PATH
								+ "/" + Comment.getMd5Hash(su.getHeader()));
					} else {
						picfile = new File(ConstantsJrc.PROJECT_PATH
								+ getPackageName() + ConstantsJrc.PHOTO_PATH
								+ "/" + Comment.getMd5Hash(su.getHeader()));
					}

					userImage.setImageResource(R.drawable.photo);

					String filename = picfile.getPath().toString();
					Bitmap bmp1 = BitmapCacheRoomList.getIntance()
							.getCacheBitmap(filename, 0, 0);

					if (bmp1 == null) {
						if (!headerurls.contains(su.getHeader())) {
							headerurls.add(su.getHeader());
							new Thread(new LoadImageRunnable(
									MainMenuActivity.this, mPhotoHandler, 0, su
											.getHeader(), filename)).start();
						}
						userImage.setImageResource(R.drawable.photo);
					} else {
						userImage.setImageBitmap(bmp1);
					}
				} catch (Exception e) {

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
				userLevel.setText(level);
				userName.setText(nick);
				reloadThread = new ReLoadPhtotThread();
				reloadThread.start();
			}
		}
		if (requestCode == 2) {
			if (resultCode == 22) {
				IsEnterRoom = data.getBooleanExtra("isenterroom", true);
			}
		}
		if (resultCode == 89) {
			int entrance = data.getIntExtra("entrance", 89);
			if (entrance == 88) {
				// pb.setVisibility(View.VISIBLE);
				Intent it = new Intent();
				it.setClass(MainMenuActivity.this, JianrencunActivity.class);
				startActivity(it);

			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	void addNotify(int id, String msg) {

		// 获得通知管理器
		NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// 构建一个通知对象
		Notification notification = new Notification(R.drawable.ic_launcher,
				msg, System.currentTimeMillis());

		PendingIntent pendingIntent = PendingIntent.getActivity(
				getApplicationContext(), 0, new Intent(MainMenuActivity.this,
						MainMenuActivity.class), 0);

		notification.setLatestEventInfo(getApplicationContext(), "", "",
				pendingIntent);

		notification.flags |= Notification.FLAG_AUTO_CANCEL; // 自动终止
		// notification.defaults |= Notification.DEFAULT_SOUND; // 默认声音
		manager.notify(id, notification);// 发起通知
		manager.cancel(id);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		reloadThread = new ReLoadPhtotThread();
		reloadThread.start();
		k = 0;

		for (int i = 0; i < x.size(); i++) {
			if (Integer.parseInt(x.get(i)) < def) {
				def = Integer.parseInt(x.get(i));
			}
		}
		switch (def) {
		case 0:
			xiaoshoudh.setVisibility(View.VISIBLE);
			iv1.setVisibility(View.GONE);
			iv2.setVisibility(View.GONE);
			iv3.setVisibility(View.GONE);
			iv4.setVisibility(View.GONE);

			break;

		case 1:
			xiaoshoudh.setVisibility(View.GONE);
			iv1.setVisibility(View.VISIBLE);
			iv2.setVisibility(View.GONE);
			iv3.setVisibility(View.GONE);
			iv4.setVisibility(View.GONE);
			break;

		case 2:
			xiaoshoudh.setVisibility(View.GONE);
			iv1.setVisibility(View.GONE);
			iv2.setVisibility(View.VISIBLE);
			iv3.setVisibility(View.GONE);
			iv4.setVisibility(View.GONE);
			break;

		case 3:
			xiaoshoudh.setVisibility(View.GONE);
			iv1.setVisibility(View.GONE);
			iv2.setVisibility(View.GONE);
			iv3.setVisibility(View.VISIBLE);
			iv4.setVisibility(View.GONE);
			break;
		case 4:
			xiaoshoudh.setVisibility(View.GONE);
			iv1.setVisibility(View.GONE);
			iv2.setVisibility(View.GONE);
			iv3.setVisibility(View.GONE);
			iv4.setVisibility(View.VISIBLE);
			break;
		}
		def = 6;
		/** 创建基础文件 **/
		SystemUtil.makeDir(this);
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		dialog.dismiss();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		this.loadingDataTime.cancel();
		su.setMainPd("");
		vername = "";
		// clearCacheData(); // 退出检测清理
		// unregisterReceiver(); // 注销监听
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				// Commond.showToast(getApplicationContext(), "再按一次退出贱人村");
				toastTextView.setText("再按一次退出贱人村");
				toast.show();
				exitTime = System.currentTimeMillis();
			} else {
				toast.cancel();
				su.setMainPd("");
				SystemUtil.params.clear();
				SystemUtil.params = null;
				InitCmmInterface.exitApp();
				// 完全退出，无残留
				Intent startMain = new Intent(Intent.ACTION_MAIN);
				startMain.addCategory(Intent.CATEGORY_HOME);
				startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(startMain);
				this.loadingDataTime.cancel();
				// clearCacheData();

				android.os.Process.killProcess(android.os.Process.myPid());
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public String geturl(String s) {
		if (!s.contains("uid=")) {
			if (s.contains("?")) {
				s += "&uid=" + uid;
			} else {
				s += "?uid=" + uid;
			}
		}
		// if (!s.contains("nick=")) {
		// String ni = URLEncoder.encode(nick);
		// if (s.contains("?")) {
		// s += "&nick=" + ni;
		// } else {
		// s += "?nick=" + ni;
		// }
		// }
		// if (!s.contains("header=")) {
		// String hea = URLEncoder.encode(MainMenuActivity.header);
		// if (s.contains("?")) {
		// s += "&header=" + hea;
		// } else {
		// s += "?header=" + hea;
		// }
		// }
		return s;
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			String ssda;
			if (data.getString("over1").equalsIgnoreCase("yes")) {
				try {
					whichto = 0;
					dialog.cancel();
					ssda = data.getString("over2");
					iv3.setVisibility(View.GONE);
					x.remove("3");
					try {
						AuthorizeActivity.get2json(MainMenuActivity.this, ssda,
								whichto, gointoxiaojian, tip3);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					tip3 = "";
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	};

	Handler mHandler2 = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			String ssda;
			if (data.getString("over1").equalsIgnoreCase("yes")) {
				try {
					whichto = 1;
					dialog.cancel();
					ssda = data.getString("over2");
					iv4.setVisibility(View.GONE);
					x.remove("4");
					try {
						AuthorizeActivity.get2json(MainMenuActivity.this, ssda,
								whichto, gointodazui, tip4);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					tip4 = "";
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	};
	Handler mHandler3 = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			String ssda;
			if (data.getString("over1").equalsIgnoreCase("yes")) {
				try {
					whichto = 2;
					dialog.cancel();
					ssda = data.getString("over2");
					iv1.setVisibility(View.GONE);
					x.remove("1");
					try {
						AuthorizeActivity.get2json(MainMenuActivity.this, ssda,
								whichto, true, tip1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					tip1 = "";
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	};

	class uThread implements Runnable {
		public Handler mHandler;
		public String murl;
		String ex;

		public uThread(Context context, Handler h, String url) {
			mHandler = h;
			murl = url;
		}

		@Override
		public void run() {

			String ssdata = AuthorizeActivity.Gethttptonet(murl);

			// process incoming messages here
			Message msg = new Message();
			Bundle da = new Bundle();
			da.putString("over1", "yes");
			da.putString("over2", ssdata);
			msg.setData(da);
			mHandler.sendMessage(msg);
		};
	}

	public static void get2json(Activity context, String ss)
			throws JSONException {
		JSONObject jsonChannel = new JSONObject(ss);
		int ret = jsonChannel.optInt("ret");
		if (ret == 0) {
			Commond.showToast(context, "解析失败！");
			return;
		}
		String pd = URLDecoder.decode(jsonChannel.optString("pd"));
		catePd = pd;
		String tip = URLDecoder.decode(jsonChannel.optString("tip"));
		JSONArray jsonItems = jsonChannel.optJSONArray("items");
		if (jsonItems != null && jsonItems.length() > 0) {
			// tip = "获取成功！";
			for (int i = 0; i < jsonItems.length(); i++) {
				CategoryEntity entity = new CategoryEntity();
				JSONObject jsonItem = jsonItems.getJSONObject(i);
				entity.setName(URLDecoder.decode(jsonItem.optString("title")));
				String link = URLDecoder.decode(jsonItem.optString("link"));
				entity.setLink(link);
				CacheData.cateList.add(entity);
			}
			CacheData.setCategoryList(context);
		} else {
			CacheData.getCategoryList(context);

		}

		if (ret == 2) {
			tip = URLDecoder.decode(jsonChannel.optString("tip"));
		}
		//
		if (tip.equalsIgnoreCase("") || tip == null) {
			Intent it = new Intent();
			it.setClass(context, JianrencunActivity.class);
			context.startActivity(it);
			context.finish();
		} else {
			Intent it = new Intent();
			it.putExtra("tishi", tip);
			it.setClass(context, Entrance.class);
			it.putExtra("ret", ret);
			context.startActivityForResult(it, 89);
		}
	}

	Handler mPhotoHandler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			if (data != null) {
				String url = data.getString("url");
				String filename = data.getString("filename");

				Bitmap bmp = BitmapCacheRoomList.getIntance().getCacheBitmap(
						filename, 0, 0);
				ImageView iv = (ImageView) userImage.findViewWithTag(url);
				if (iv != null) {
					iv.setImageBitmap(bmp);
				}
			}
		}
	};

	public class LoadImageRunnable implements Runnable {
		private Context mContext;
		private int mThreadId;
		private Handler mHandler;
		private String sUrl;
		private String mFilename;

		public LoadImageRunnable(Context context, Handler h, int id,
				String str, String filename) {
			mHandler = h;
			mContext = context;
			mThreadId = id;
			sUrl = str;
			mFilename = filename;
		}

		@Override
		public void run() {

			Comment.urlToFile(mContext, sUrl, mFilename);
			Message msg = new Message();
			Bundle data = new Bundle();
			data.putString("url", sUrl);
			data.putString("filename", mFilename);
			msg.setData(data);
			if (mHandler != null) {
				mHandler.sendMessage(msg);
			}
		}
	}

	public void donghuapre(String path) {
		// 跳转过来。。接受path ，如果不为空 拆分 为 html名字 和 要解压到文件夹的名字

		// 判断 html文件存不存在
		String[] strs = path.split("[/]");
		foldername = strs[0];
		htmlname = strs[1];
		htmlfile = new File(zipFile + File.separator + foldername
				+ File.separator + htmlname);
		flg = 0;
		if (htmlfile.exists()) {
			Intent intent = new Intent(getApplicationContext(),
					WebviewDonghua.class);
			intent.putExtra("htmlfile", htmlfile.getPath());
			startActivity(intent);
			flg = 1;
			return;
		}
	}

	class myThread implements Runnable {
		public String furl, path;

		public myThread(String url, String path) {
			this.furl = url;
			this.path = path;
		}

		public void run() {
			int count;
			try {

				String[] strs = newpath.split("[/]");
				foldername = strs[0];
				htmlname = strs[1];
				// for(int i = 0 ; i<bofangurl.size()-1 ;i++){
				URL url = new URL(furl);
				URLConnection conexion = url.openConnection();
				conexion.connect();

				int lenghtOfFile = conexion.getContentLength();
				InputStream input = new BufferedInputStream(url.openStream());

				File picfile = new File(donghuadownload + "/" + File.separator
						+ Commond.getMd5Hash(newpath) + ".zip");
				String filename = picfile.getPath().toString();

				OutputStream output = new FileOutputStream(picfile
						.getAbsolutePath().toString());
				byte data[] = new byte[10240];
				long total = 0;
				while ((count = input.read(data)) != -1) {
					total += count;
					// publishProgress("" + (int) ((total * 100) /
					// lenghtOfFile));
					output.write(data, 0, count);
				}
				output.flush();
				output.close();
				input.close();
				Message message = new Message();
				message.what = 0;
				Bundle bundle = message.getData();
				bundle.putString("fileurl", furl); // 往Bundle中存放数
				bundle.putString("path", path); // 往Bundle中存放数
				message.setData(bundle);
				myHandler.sendMessage(message);
				// }

			} catch (Exception e) {
				// Commond.showToast(DazuiActivity.this, "网络很不给力啊！");
				Message message = new Message();
				message.what = 1;
				myHandler.sendMessage(message);
				e.printStackTrace();
			}
		}
	}

	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			String str2 = msg.getData().getString("fileurl");// 接受msg传递过来的参数
			String path = msg.getData().getString("path");// 接受msg传递过来的参数

			File picfile = new File(donghuadownload + "/" + File.separator
					+ Commond.getMd5Hash(newpath) + ".zip");
			String filename = picfile.getPath().toString();

			if (picfile.exists()) {
				try {
					File picfile1 = new File(zipFile + "/" + File.separator
							+ File.separator + foldername + File.separator
							+ htmlname);
					String filename1 = picfile1.getPath().toString();
					if (!picfile1.exists()) {
						new Thread(new jieyaThread2(picfile)).start();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			super.handleMessage(msg);
		}
	};

	class jieyaThread2 implements Runnable {
		File file;

		public jieyaThread2(File picfile) {
			this.file = picfile;
		}

		public void run() {

			try {
				WebviewDonghua.upZipFile(file, zipFile.getAbsolutePath()
						.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message message = new Message();
			message.what = 1;
			if (file.exists()) {
				file.delete();
			}
			jieyaHandler2.sendMessage(message);
		}
	}

	Handler jieyaHandler2 = new Handler() {
		public void handleMessage(Message msg) {
			File picfile1 = new File(zipFile + "/" + File.separator
					+ File.separator + foldername + File.separator + htmlname);
			String filename1 = picfile1.getPath().toString();
			Intent intent = new Intent(getApplicationContext(),
					WebviewDonghua.class);
			intent.putExtra("htmlfile", filename1);
			startActivity(intent);
			super.handleMessage(msg);
		}
	};

	class getDate implements Runnable {
		String path;

		public getDate(String path) {
			this.path = path;
		}

		public void run() {
			HttpGetData(path);
		}
	}

	public void HttpGetData(String uri) {
		try {
			org.apache.http.client.HttpClient httpclient = new DefaultHttpClient();
			HttpGet get = new HttpGet(uri);
			HttpResponse response;
			response = httpclient.execute(get);
			int code = response.getStatusLine().getStatusCode();
			// 检验状态码，如果成功接收数据
			if (code == 200) {
				// 返回json格式： {"id": "27JpL~j4vsL0LX00E00005","version": "abc"}
				String rev = EntityUtils.toString(response.getEntity());
				JSONObject jsonChannel;
				try {
					jsonChannel = new JSONObject(rev);
					int ret = jsonChannel.optInt("ret");

					link = URLDecoder.decode(jsonChannel.optString("link"));
					newpath = URLDecoder.decode(jsonChannel.optString("path"));

					File picfile = new File(donghuadownload + "/"
							+ File.separator + Commond.getMd5Hash(newpath)
							+ ".zip");
					String filename = picfile.getPath().toString();

					if (TextUtils.isEmpty(link) && TextUtils.isEmpty(newpath)) {
						// Intent intent = new Intent(getApplicationContext(),
						// WebviewDonghua.class);
						// intent.putExtra("htmlfile", htmlfile.getPath());
						// startActivity(intent);
					} else {
						if (!picfile.exists()) {
							new Thread(new myThread(link, newpath)).start();
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
		}
	}

	int anim_count = 0;
	Handler yd_animhadler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				anim_count += 1;
				findViewById(R.id.main_menu_yuepi_imageButton)
						.setBackgroundResource(R.drawable.donghua1);
				yd_animhadler.sendEmptyMessageDelayed(2, 150);
			} else if (msg.what == 2) {
				findViewById(R.id.main_menu_yuepi_imageButton)
						.setBackgroundResource(R.drawable.donghua2);
				yd_animhadler.sendEmptyMessageDelayed(3, 150);
			} else if (msg.what == 3) {
				findViewById(R.id.main_menu_yuepi_imageButton)
						.setBackgroundResource(R.drawable.donghua3);
				yd_animhadler.sendEmptyMessageDelayed(1, 150);
			} else if (msg.what == 4) {
				findViewById(R.id.main_menu_yuepi_imageButton)
						.setBackgroundResource(R.drawable.donghua4);
			}
		};
	};

	Handler tiao_animhadler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				anim_count += 1;
				pro_tiaodonghua.setBackgroundResource(R.drawable.tiao_donghua1);
				tiao_animhadler.sendEmptyMessageDelayed(2, 150);
			} else if (msg.what == 2) {
				pro_tiaodonghua.setBackgroundResource(R.drawable.tiao_donghua2);
				tiao_animhadler.sendEmptyMessageDelayed(3, 150);
			} else if (msg.what == 3) {
				pro_tiaodonghua.setBackgroundResource(R.drawable.tiao_donghua3);
				tiao_animhadler.sendEmptyMessageDelayed(4, 150);
			} else if (msg.what == 4) {
				pro_tiaodonghua.setBackgroundResource(R.drawable.tiao_donghua4);
				tiao_animhadler.sendEmptyMessageDelayed(1, 150);
			}
		};
	};

	int count = 0;
	Handler tiaodong2 = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				anim_count += 1;
				linearParams2.setMargins(
						HomeView.points.get(1).x - bjwidth / 2,
						HomeView.points.get(1).y + 1, 0, 0);
				iv2.setLayoutParams(linearParams2);
				tiaodong2.sendEmptyMessageDelayed(2, 150);
			} else if (msg.what == 2) {
				linearParams2.setMargins(
						HomeView.points.get(1).x - bjwidth / 2,
						HomeView.points.get(1).y + 1 + 1, 0, 0);
				iv2.setLayoutParams(linearParams2);
				tiaodong2.sendEmptyMessageDelayed(3, 150);
			} else if (msg.what == 3) {
				linearParams2.setMargins(
						HomeView.points.get(1).x - bjwidth / 2,
						HomeView.points.get(1).y - 1, 0, 0);
				iv2.setLayoutParams(linearParams2);
				tiaodong2.sendEmptyMessageDelayed(4, 150);
			} else if (msg.what == 4) {
				linearParams2.setMargins(
						HomeView.points.get(1).x - bjwidth / 2,
						HomeView.points.get(1).y - 1 - 1, 0, 0);
				iv2.setLayoutParams(linearParams2);
				tiaodong2.sendEmptyMessageDelayed(1, 150);
			}
		};
	};
	Handler tiaodong = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				linearParams.setMargins(HomeView.points.get(0).x - bjwidth / 2,
						HomeView.points.get(0).y + 1, 0, 0);
				iv1.setLayoutParams(linearParams);
				tiaodong.sendEmptyMessageDelayed(2, 150);
			} else if (msg.what == 2) {
				linearParams.setMargins(HomeView.points.get(0).x - bjwidth / 2,
						HomeView.points.get(0).y + 2, 0, 0);
				iv1.setLayoutParams(linearParams);
				tiaodong.sendEmptyMessageDelayed(3, 150);
			} else if (msg.what == 3) {
				linearParams.setMargins(HomeView.points.get(0).x - bjwidth / 2,
						HomeView.points.get(0).y - 1, 0, 0);
				iv1.setLayoutParams(linearParams);
				tiaodong.sendEmptyMessageDelayed(4, 150);
			} else if (msg.what == 4) {
				linearParams.setMargins(HomeView.points.get(0).x - bjwidth / 2,
						HomeView.points.get(0).y - 2, 0, 0);
				iv1.setLayoutParams(linearParams);
				tiaodong.sendEmptyMessageDelayed(1, 150);
			}
		};
	};
	Handler tiaodong3 = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				anim_count += 1;
				linearParams3.setMargins(HomeView.points.get(2).x
						- dm.widthPixels / 50, HomeView.points.get(2).y + 1, 0,
						0);
				iv3.setLayoutParams(linearParams3);
				tiaodong3.sendEmptyMessageDelayed(2, 150);
			} else if (msg.what == 2) {
				linearParams3.setMargins(HomeView.points.get(2).x
						- dm.widthPixels / 50, HomeView.points.get(2).y + 2, 0,
						0);
				iv3.setLayoutParams(linearParams3);
				tiaodong3.sendEmptyMessageDelayed(3, 150);
			} else if (msg.what == 3) {
				linearParams3.setMargins(HomeView.points.get(2).x
						- dm.widthPixels / 50, HomeView.points.get(2).y - 1, 0,
						0);
				iv3.setLayoutParams(linearParams3);
				tiaodong3.sendEmptyMessageDelayed(4, 150);
			} else if (msg.what == 4) {
				linearParams3.setMargins(HomeView.points.get(2).x
						- dm.widthPixels / 50, HomeView.points.get(2).y - 2, 0,
						0);
				iv3.setLayoutParams(linearParams3);
				tiaodong3.sendEmptyMessageDelayed(1, 150);
			}
		};
	};
	Handler tiaodong4 = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				anim_count += 1;
				linearParams4.setMargins(HomeView.points.get(3).x - bjwidth / 3
						* 2, HomeView.points.get(3).y + 1, 0, 0);
				iv4.setLayoutParams(linearParams4);
				tiaodong4.sendEmptyMessageDelayed(2, 150);
			} else if (msg.what == 2) {
				linearParams4.setMargins(HomeView.points.get(3).x - bjwidth / 3
						* 2, HomeView.points.get(3).y + 2, 0, 0);
				iv4.setLayoutParams(linearParams4);
				tiaodong4.sendEmptyMessageDelayed(3, 150);
			} else if (msg.what == 3) {
				linearParams4.setMargins(HomeView.points.get(3).x - bjwidth / 3
						* 2, HomeView.points.get(3).y - 1, 0, 0);
				iv4.setLayoutParams(linearParams4);
				tiaodong4.sendEmptyMessageDelayed(4, 150);
			} else if (msg.what == 4) {
				linearParams4.setMargins(HomeView.points.get(3).x - bjwidth / 3
						* 2, HomeView.points.get(3).y - 2, 0, 0);
				iv4.setLayoutParams(linearParams4);
				tiaodong4.sendEmptyMessageDelayed(1, 150);
			}
		};
	};
	Handler tiaodong5 = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				xiaoshoudh.setBackgroundResource(R.drawable.xiaoshou1);
				tiaodong5.sendEmptyMessageDelayed(2, 150);
			} else if (msg.what == 2) {
				xiaoshoudh.setBackgroundResource(R.drawable.xiaoshou2);
				tiaodong5.sendEmptyMessageDelayed(3, 150);
			} else if (msg.what == 3) {
				xiaoshoudh.setBackgroundResource(R.drawable.xiaoshou3);
				tiaodong5.sendEmptyMessageDelayed(4, 150);
			} else if (msg.what == 4) {
				xiaoshoudh.setBackgroundResource(R.drawable.xiaoshou4);
				tiaodong5.sendEmptyMessageDelayed(5, 150);
			} else if (msg.what == 5) {
				xiaoshoudh.setBackgroundResource(R.drawable.xiaoshou5);
				tiaodong5.sendEmptyMessageDelayed(1, 150);
			}
		};
	};

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		if (numtime == 0) {
			jinduwidth = pro_rl.getWidth();
			zuo = pro_zuorl.getWidth() - 5;
			// Log.e("commm on 000", jinduwidth+zuo +" ,");
			numtime++;
		}
		// RelativeLayout.LayoutParams llParams =
		// (RelativeLayout.LayoutParams)pro_iv.getLayoutParams();
		// llParams.width = jinduwidth+zuo;
		// pro_iv.setLayoutParams(llParams);
		//
		// RelativeLayout.LayoutParams llParam2 =
		// (RelativeLayout.LayoutParams)pro_tiaodonghua.getLayoutParams();
		// llParam2.width = jinduwidth+zuo;
		// pro_tiaodonghua.setLayoutParams(llParam2);
		super.onWindowFocusChanged(hasFocus);
	}
}
