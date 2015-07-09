package com.jianrencun.android;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import m.framework.ui.SlidingMenu;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.telephony.TelephonyManager;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.app.chatroom.Appstart;
import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.imgzoom.ImageZoom;
import com.app.chatroom.otherui.SystemMsgWebDialog;
import com.app.chatroom.otherui.VillageUserInfoDialog;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;
import com.app.chatroom.util.PhoneInfo;
import com.duom.fjz.iteminfo.Adapterwithpic;
import com.duom.fjz.iteminfo.BitmapCache1;
import com.duom.fjz.iteminfo.Iteminfo;
import com.duom.fjz.iteminfo.Pinglunitem;
import com.duom.fjz.iteminfo.WebViewCacheAdapter;
import com.duom.fjz.iteminfo.WebviewDonghua;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Dazuidetatil;
import com.tencent.mm.sdk.platformtools.Util;
import com.umeng.analytics.MobclickAgent;
import com.umeng.common.Log;
import com.weibo.sina.AuthorizeActivity;

public class Details extends HttpBaseActivity implements OnScrollListener,
		Callback {
	public static Details instance = null;
	private Bundle bundle1, bundle2;
	private Intent intent1, intent2;
	private ViewPager mTabPager;
	private ImageView mTabImg;// 动画图片
	private ImageView Tab1, Tab2;
	private int zero = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int one;// 单个水平动画位移
	private ProgressBar pgd, pgshoucang;
	long dismissTime;
	private LinearLayout mianban;
	private WebView wv;
	public static String mtype;
	private String content, date, detailslink, ccount, title;
	private int po, which;
	private String lastdate, currentdate = "";
	private EditText edittext;
	private String linshicz = "";
	private Button share, shoucang, pinglun;
	private Button zhichi, fandui;
	private TextView num;
	boolean isRefreshFoot = false;
	private ProgressBar pb, pbcon, pbbody;
	private ListView list;
	private boolean istwice = false, pinglunfresh = false, issave = false;
	private int kaiguan = 0;
	private int dcount, ucount, lastdcount, lastucount;
	private String zfdurl;
	private String jubaolink;
	private String nlink;
	private String btime, btm;
	private String pd;
	private String pagelink;
	private Adaptercri adapter;
	private int uc, dc, fc;
	private int cc;
	private int twice = 0;
	private ImageView ivtest;
	private boolean okronot = true;
	private List<String> dlpl;
	private List<Iteminfo> currentlistviewiteminfo = new ArrayList<Iteminfo>();
	private LinearLayout rllt2, rllt3;
	private int visibleLastIndex = 0; // 最后的可视项索引
	private List<Pinglunitem> items = new ArrayList<Pinglunitem>();
	private LinearLayout loading;
	private View vFooter;
	int displayWidth2;
	private String flink, rlink, clink;
	int v1, v2, v3, et1, et2, et3, et4;
	boolean isclick = false;
	String details;
	private Button numofpinglun;
	private int fav;
	String url2, nextpage2, lastpage2, save2, zhichi2, fandui2, fenxiang2,
			save3, nlink2;
	String feedback, header;
	private String imageUrl = "/mnt/sdcard/abc.jpg";
	int m;
	View view1;
	String uurl;
	ArrayList<View> views;
	static String filename;
	int current = 0;
	boolean isread = false;
	int position;
	InputMethodManager inputMethodManager;
	boolean man;
	long bbtime;
	private String hdcontent;
	static boolean freshmysave;
	private ImageView shafa;

	private Handler handler;
	private SlidingMenu menu;
	private ProgressDialog progressd;
	private int id;
	
	private static final String FILE_NAME = "/pic.jpg";
	public static String TEST_IMAGE;
	private Commond commond = new Commond();
		
	
	/**
	 * 
	 * @param url
	 * @param name
	 * @param value
	 * @return
	 */
	public  String appendNameValue(String url, String name, String value) {
		if (!url.contains("&" + name + "=") && !url.contains("?" + name + "=")) {
			if (url.indexOf('?') > 0) {
				url += "&" + name + "=" + value;
			} else {
				url += "?" + name + "=" + value;
			}
		}
		if(!TextUtils.isEmpty(MainMenuActivity.vername)){
		if(!url.contains("&ver=") && !url.contains("?ver=")){
			url += "&" + "ver" + "=" + MainMenuActivity.vername ;			
		}
		}
		return url;
	}

	public static String appendNameValueint(String url, String name, int value) {
		if (!url.contains("&" + name + "=") && !url.contains("?" + name + "=")) {
			if (url.indexOf('?') > 0) {
				url += "&" + name + "=" + value;
			} else {
				url += "?" + name + "=" + value;
			}
		}
		return url;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.details);

		instance = this;
		JianrencunActivity.yesorno = true;
		progressd = new ProgressDialog(this);

		handler = new Handler(this);

		inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		dlpl = new ArrayList<String>();

		pgshoucang = (ProgressBar) findViewById(R.id.pgshoucang);

		numofpinglun = (Button) findViewById(R.id.numofpinglun);
		mTabPager = (ViewPager) findViewById(R.id.tabpager2);
		// mtp = (LinearLayout) findViewById(R.id.lltp);
		mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());
		Tab1 = (ImageView) findViewById(R.id.img_weixin2);
		Tab2 = (ImageView) findViewById(R.id.img_address2);

		vFooter = LayoutInflater.from(this).inflate(R.layout.list_footer, null);
		loading = (LinearLayout) vFooter.findViewById(R.id.list_footer);
		loading.setVisibility(View.GONE);
		mianban = (LinearLayout) findViewById(R.id.mianban);
		mTabImg = (ImageView) findViewById(R.id.img_tab_now2);
		Tab1.setOnClickListener(new MyOnClickListener(1));
		Tab2.setOnClickListener(new MyOnClickListener(2));

		Display currDisplay = getWindowManager().getDefaultDisplay();// 获取屏幕当前分辨率
		int displayWidth = (int) (currDisplay.getWidth() * 0.98);
		displayWidth2 = (int) (currDisplay.getWidth());
		one = displayWidth / 2; // 设置水平动画平移大小
		Intent it = getIntent();
		title = it.getStringExtra("content");
		date = it.getStringExtra("date");
		which = it.getIntExtra("which", 9);
		po = it.getIntExtra("po", 8);
		id = it.getIntExtra("id", 10);
		detailslink = it.getStringExtra("link");
		clink = it.getStringExtra("clink");
		flink = it.getStringExtra("flink");
		rlink = it.getStringExtra("rlink");
		ccount = it.getStringExtra("ccount");
		fav = it.getIntExtra("isfav", 10);
		header = it.getStringExtra("header");
		numofpinglun.setText(ccount);
		// 将要分页显示的View装入数组中
		LayoutInflater mLi = LayoutInflater.from(this);
		view1 = mLi.inflate(R.layout.contentbody, null);
		// wv = (WebView) view1.findViewById(R.id.webbody);
		pgd = (ProgressBar) view1.findViewById(R.id.pgd);
		share = (Button) findViewById(R.id.share);
		wv = (WebView) view1.findViewById(R.id.webbody);
		wv.requestFocus();
		rllt2 = (LinearLayout) view1
				.findViewById(R.id.village_leftlist_progressbar_RelativeLayout2);
		// lastpage = (Button) findViewById(R.id.lastpagebnt);
		// nextpage = (Button) findViewById(R.id.nextpagebnt);
		shoucang = (Button) findViewById(R.id.shoucang);
		pbbody = (ProgressBar) findViewById(R.id.pbbody);
		pinglun = (Button) findViewById(R.id.pinglun);
		edittext = (EditText) findViewById(R.id.edittext);

		if (which == 33) {
			if (Shangchuan.mysclist.size() == 0 || Shangchuan.mysclist == null) {
				finish();
			} else {
				currentlistviewiteminfo = Shangchuan.mysclist;
			}
		} else if (which == 44) {
			if (Mysave.listinfo.size() == 0 || Mysave.listinfo == null) {
				finish();
			} else {
				currentlistviewiteminfo = Mysave.listinfo;
			}
		} else if (which == 55) {
			if (Xjfabu.Xjfabulist.size() == 0 || Xjfabu.Xjfabulist == null) {
				finish();
			} else {
				currentlistviewiteminfo = Xjfabu.Xjfabulist;
			}
		} else {
			try {

			if ( MainMenuActivity.li == null || MainMenuActivity.li.size() == 0) {
				finish();
			} else {
				currentlistviewiteminfo = MainMenuActivity.li
						.get(JianrencunActivity.indx);
			}
			
		} catch (Exception exception) {
			// TODO: handle exception
			exception.printStackTrace();
	    	}
		}

		if (fav == 0) {
			shoucang.setBackgroundResource(R.drawable.notsave1);
		} else if (fav == 1) {
			shoucang.setBackgroundResource(R.drawable.savebnt1);
		}

		// rllt =
		// (RelativeLayout)findViewById(R.id.village_leftlist_progressbar_RelativeLayout1);

		details = appendNameValue(detailslink, "pkg", getPackageName());

		// wv.setHorizontalScrollBarEnabled(false);
		uurl = geturl(details);

		edittext.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					man = true;
					shoucang.setVisibility(View.GONE);
					share.setVisibility(View.GONE);
					pinglun.setVisibility(View.VISIBLE);
					InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					inputMethodManager.toggleSoftInput(0,
							InputMethodManager.HIDE_NOT_ALWAYS);
				} else {
					// 运行3000毫秒，然后隐藏
					Message msg = new Message();
					msg.what = 2;
					// 设置发送的值，和间隔启动的时间
					shoucang.setVisibility(View.VISIBLE);
					share.setVisibility(View.VISIBLE);
					pinglun.setVisibility(View.GONE);
					InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					inputMethodManager.hideSoftInputFromWindow(
							edittext.getWindowToken(), 0);

				}
			}
		});
		File picfile = new File(Appstart.jrcfile + "/"
				+ Adapterwithpic.getMd5Hash(geturl(details)));
		String filename = picfile.getPath().toString();
		// File f = new File(p + Adapterwithpic.getMd5Hash(geturl(details)));
		if (picfile.exists()) {
			String ss = CacheData.load(filename, this);
			JSONObject jc;
			try {
				jc = new JSONObject(ss);
				pd = URLDecoder.decode(jc.optString("pd"));
				currentdate = pd;

				content = URLDecoder.decode(jc.optString("content"));
				feedback = URLDecoder.decode(jc.optString("feedback"));

				header = currentlistviewiteminfo.get(po).getHeader();
				loadWebView(content);
				// System.out.println("content4:" + content);
				currentdate = pd;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			pgd.setVisibility(View.VISIBLE);
			rllt2.setVisibility(View.VISIBLE);
			post(geturl(details));
		}

		View v0 = mLi.inflate(R.layout.contentbody, null);
		View v3 = mLi.inflate(R.layout.contentbody, null);

		View view2 = mLi.inflate(R.layout.criticism, null);
		pb = (ProgressBar) view2.findViewById(R.id.progresscri);
		list = (ListView) view2.findViewById(R.id.pinglunlist);
		shafa = (ImageView) view2.findViewById(R.id.xiaojian_shafa);
		rllt3 = (LinearLayout) view2
				.findViewById(R.id.village_leftlist_progressbar_RelativeLayout3);
		TelephonyManager mTm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		mtype = android.os.Build.MODEL; // 手机型号
		// imsi = mTm.getSubscriberId();
		// imei = mTm.getDeviceId();
		// phone = mTm.getLine1Number();//

		list.setOnScrollListener(this);
		list.addFooterView(vFooter);
		list.setFooterDividersEnabled(false);
		View view = new View(getApplicationContext());
		view = getLayoutInflater().inflate(R.layout.listheader, null);
		zhichi = (Button) view.findViewById(R.id.zhichibnt);
		fandui = (Button) view.findViewById(R.id.fanduibnt);
		num = (TextView) view.findViewById(R.id.numofsave);

		list.addHeaderView(view);

		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
                try {			
				Intent intent = new Intent();
				intent.putExtra("content", items.get(position - 1).getContent()
						.toString());
				intent.putExtra("title", items.get(position - 1).getTitle()
						.toString());
				intent.putExtra("jubaolink", items.get(position - 1)
						.getJubaolink());
				intent.putExtra("from", 1);
				intent.setClass(Details.this, Chakanpinglun.class);
				startActivity(intent);				
				} catch (Exception e) {
					// TODO: handle exception
					finish();
				}
			}
		});

		zhichi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (okronot == true) {
					// TODO Auto-generated method stub
					okronot = false;
					pb.setVisibility(View.VISIBLE);
					rllt3.setVisibility(View.VISIBLE);
					StringBuffer data = new StringBuffer();
					String url = appendNameValue(zfdurl, "pkg",
							getPackageName());
					zhichi2 = geturl(url);
					data.append("type=");
					data.append(0);
					// 请求网络验证登陆
					HttpRequestTask request = new HttpRequestTask();
					request.execute(zhichi2, data.toString());
				} else {
					Commond.showToast(Details.this, "您已经评价过了哦，亲！");
				}
			}
		});
		fandui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (okronot == true) {
					// TODO Auto-generated method stub
					okronot = false;
					pb.setVisibility(View.VISIBLE);
					rllt3.setVisibility(View.VISIBLE);
					StringBuffer data = new StringBuffer();
					String url = appendNameValue(zfdurl, "pkg",
							getPackageName());
					zhichi2 = geturl(url);
					data.append("type=");
					data.append(1);
					// 请求网络验证登陆
					HttpRequestTask request = new HttpRequestTask();
					request.execute(zhichi2, data.toString());
				} else {
					Commond.showToast(Details.this, "您已经评价过了哦，亲！");
				}
			}
		});
		if (pgshoucang.getVisibility() == View.VISIBLE) {
			shoucang.setEnabled(false);
		} else {
			shoucang.setEnabled(true);
		}
		shoucang.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				issave = true;
				shoucang.setEnabled(false);
				pgshoucang.setVisibility(View.VISIBLE);
				// rllt.setVisibility(View.VISIBLE);
				String url = appendNameValue(flink, "pkg",
						getPackageName());
				save2 = geturl(url);
				if (fav == 0) {
					StringBuffer data = new StringBuffer();
					// data.append("type=");
					// data.append(0);
					save3 = appendNameValue(save2, "type", "0");
					// 请求网络验证登陆
					HttpRequestTask request = new HttpRequestTask();
					request.execute(save3, data.toString());

				} else if (fav == 1) {
					StringBuffer data = new StringBuffer();
					//
					// data.append("type=");
					// data.append(1);
					// 请求网络验证登陆
					save3 = appendNameValue(save2, "type", "1");
					HttpRequestTask request = new HttpRequestTask();
					request.execute(save3, data.toString());
				}
			}
		});

		share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// newLaunchThread(false).start();

				showShare(false, null);
			}
		});

		// 每个页面的view数据
		views = new ArrayList<View>();
		views.add(v0);
		views.add(view1);
		views.add(view2);
		views.add(v3);

		// 填充ViewPager的数据适配器
		PagerAdapter mPagerAdapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(views.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(views.get(position));
				return views.get(position);
			}
		};

		mTabPager.setAdapter(mPagerAdapter);
		mTabPager.setCurrentItem(1);

		pinglun.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// String url = rlink;
				if (TextUtils.isEmpty(edittext.getText().toString())) {
					Commond.showToast(Details.this, "小贱提醒 ：输入内容，再评论哦！");
					return;
				}

				edittext.setEnabled(false);
				linshicz = edittext.getText().toString();

				StringBuffer data = new StringBuffer();
				String url = appendNameValue(rlink, "pkg",
						getPackageName());
				String mtype2 = URLEncoder.encode(mtype);

				url2 = appendNameValue(url, "model", mtype2);
				pinglunfresh = true;
				data.append("&input=");
				String input1 = edittext.getText().toString();
				data.append(URLEncoder.encode(input1));
				// data.append("&model=");
				// data.append(URLEncoder.encode(mtype));
				// 请求网络验证登陆
				HttpRequestTask request = new HttpRequestTask();
				request.execute(geturl(url2), data.toString());
				edittext.clearFocus();
				// edittext.setText("亲，正在评论中...");
				// edittext.setTextColor(Color.parseColor("#c5934c"));
				edittext.setText("");
				edittext.setHint("亲，正在评论中...");
				// edittext.clearFocus();
				// edittext.setText("");
				pgd.setVisibility(View.VISIBLE);
				rllt2.setVisibility(View.VISIBLE);
				pb.setVisibility(View.VISIBLE);
				rllt3.setVisibility(View.VISIBLE);
			}
		});

	}

	/**
	 * 
	 * @param content
	 */
	private void loadWebView(String content) {
		LinearLayout webviewPanel = (LinearLayout) view1
				.findViewById(R.id.webview_panel);
		if (wv != null) {
			webviewPanel.removeView(wv);
			wv.clearView();
			wv.destroy();
		}

		wv = (WebView) LayoutInflater.from(this).inflate(
				R.layout.contentbody_webview, null);
        wv.requestFocus();
		android.widget.LinearLayout.LayoutParams lp = new android.widget.LinearLayout.LayoutParams(
				android.widget.LinearLayout.LayoutParams.FILL_PARENT,
				android.widget.LinearLayout.LayoutParams.FILL_PARENT);
		webviewPanel.addView(wv, lp);

		wv.addJavascriptInterface(new ImageJavaScriptInterface(Details.this),
				"droid");
		wv.getSettings().setJavaScriptEnabled(true);// 用于支持JavaScript
		wv.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int newProgress) {
				// activity的进度是0 to 10000 (both inclusive),所以要*100
				pbbody.setProgress(newProgress);
				if (newProgress == 100) {
					pbbody.setProgress(0);
				}
			}
		});

		// wv.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		wv.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				// pbbody.setVisibility(View.GONE);

				if (JianrencunActivity.number1.contains(details)) {
					return;
				} else {				
					JianrencunActivity.number1.add(details);
				}
			}
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				super.onReceivedError(view, errorCode, description, failingUrl);
				 wv.loadUrl("file:///android_asset/failed/failed.html");
			}
			@Override
			public boolean shouldOverrideUrlLoading(final WebView view,
					final String url) {
				if (!commond.preUrl(Details.this, url)) {
					// view.clearView();
					view.loadUrl(url);
				}
				return true;
			}
		});
		wv.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);
	}

	/**
	 * 头标点击监听
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mTabPager.setCurrentItem(index);
		}
	};

	/*
	 * 页卡切换监听(原作者:D.Winter)
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			m = arg0;

			if (arg0 == 0) {
				mTabPager.setCurrentItem(1, true);
				// pbbody.setVisibility(View.VISIBLE);
				--po;
				dlpl.clear();
				linshicz = "";
				if (po < 0) {
					finish();
					pgd.setVisibility(View.GONE);
					rllt2.setVisibility(View.GONE);
					return;
				}
				if(list.getFooterViewsCount() == 0){
				list.addFooterView(vFooter);
				list.setFooterDividersEnabled(false);
				}

				int h = currentlistviewiteminfo.get(po).getIsfav();
				favourite(h);
				numofpinglun.setText(currentlistviewiteminfo.get(po)
						.getCriticism());

				pagelink = currentlistviewiteminfo.get(po).getLink();
				clink = currentlistviewiteminfo.get(po).getClink();
				rlink = currentlistviewiteminfo.get(po).getRlink();
				flink = currentlistviewiteminfo.get(po).getFlink();
				fav = currentlistviewiteminfo.get(po).getIsfav();
				title = currentlistviewiteminfo.get(po).getContent();
				istwice = false;
				items.clear();
				pinglunfresh = true;
				content = "";
				wv.reload();
				wv.clearView();
				// currentdate = "";
				String page = appendNameValue(pagelink, "pkg",
						getPackageName());
				String lastpage2 = geturl(page);
				uurl = lastpage2;

				File picfile = new File(Appstart.jrcfile + "/"
						+ Adapterwithpic.getMd5Hash(geturl(uurl)));
				String filename = picfile.getPath().toString();
				if (picfile.exists()) {
					String ss = CacheData.load(filename, Details.this);
					JSONObject jc;
					try {
						jc = new JSONObject(ss);
						pd = URLDecoder.decode(jc.optString("pd"));
						currentdate = pd;
						content = URLDecoder.decode(jc.optString("content"));
						feedback = URLDecoder.decode(jc.optString("feedback"));
						header = currentlistviewiteminfo.get(po).getHeader();
						// System.out.println("content0:" + content);
						loadWebView(content);
						currentdate = pd;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					pgd.setVisibility(View.VISIBLE);
					rllt2.setVisibility(View.VISIBLE);
					post(lastpage2);
				}
				if (JianrencunActivity.number1.contains(page)) {
					return;
				} else {
					JianrencunActivity.number1.add(page);
				}
			} else if (arg0 == views.size() - 1) {
				mTabPager.setCurrentItem(1, false);
				// pbbody.setVisibility(View.VISIBLE);
				++po;
				dlpl.clear();
				if(list.getFooterViewsCount() == 0){
				list.addFooterView(vFooter);
				list.setFooterDividersEnabled(false);
				}
				linshicz = "";
				if (which == 33) {
					currentlistviewiteminfo = Shangchuan.mysclist;
					if (po >= Shangchuan.mysclist.size()) {
						Commond.showToast(Details.this, "小贱提醒你：已经最后一条了哦！");
						po--;
						pgd.setVisibility(View.GONE);
						rllt2.setVisibility(View.GONE);
						return;
					}
				} else if (which == 44) {
					currentlistviewiteminfo = Mysave.listinfo;
					if (po >= Mysave.listinfo.size()) {
						Commond.showToast(Details.this, "小贱提醒你：已经最后一条了哦！");
						po--;
						pgd.setVisibility(View.GONE);
						rllt2.setVisibility(View.GONE);
						return;
					}
				} else if (which == 55) {
					currentlistviewiteminfo = Xjfabu.Xjfabulist;
					if (po >= Xjfabu.Xjfabulist.size()) {
						Commond.showToast(Details.this, "小贱提醒你：已经最后一条了哦！");
						po--;
						pgd.setVisibility(View.GONE);
						rllt2.setVisibility(View.GONE);
						return;
					}
				} else {
					currentlistviewiteminfo = MainMenuActivity.li
							.get(JianrencunActivity.indx);
					if (po >= MainMenuActivity.li.get(JianrencunActivity.indx)
							.size()) {
						Commond.showToast(Details.this, "小贱提醒你：已经最后一条了哦！");
						po--;
						pgd.setVisibility(View.GONE);
						rllt2.setVisibility(View.GONE);
						return;
					}
				}

				wv.reload();
				wv.clearView();
				int h = currentlistviewiteminfo.get(po).getIsfav();
				favourite(h);
				numofpinglun.setText(currentlistviewiteminfo.get(po)
						.getCriticism());
				pagelink = currentlistviewiteminfo.get(po).getLink();
				clink = currentlistviewiteminfo.get(po).getClink();
				rlink = currentlistviewiteminfo.get(po).getRlink();
				flink = currentlistviewiteminfo.get(po).getFlink();
				fav = currentlistviewiteminfo.get(po).getIsfav();
				title = currentlistviewiteminfo.get(po).getContent();
				istwice = false;
				items.clear();
				pinglunfresh = true;
				// currentdate = "";
				content = "";
				String page = appendNameValue(pagelink, "pkg",
						getPackageName());
				String nextpage2 = geturl(page);
				uurl = nextpage2;
				File picfile = new File(Appstart.jrcfile + "/"
						+ Adapterwithpic.getMd5Hash(geturl(uurl)));
				String filename = picfile.getPath().toString();
				if (picfile.exists()) {
					String ss = CacheData.load(filename, Details.this);
					JSONObject jc;
					try {
						jc = new JSONObject(ss);
						pd = URLDecoder.decode(jc.optString("pd"));
						currentdate = pd;
						content = URLDecoder.decode(jc.optString("content"));
						feedback = URLDecoder.decode(jc.optString("feedback"));
						header = currentlistviewiteminfo.get(po).getHeader();
						loadWebView(content);

						// System.out.println("content1:" + content);
						currentdate = pd;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					pgd.setVisibility(View.VISIBLE);
					rllt2.setVisibility(View.VISIBLE);
					post(nextpage2);
				}
				if (JianrencunActivity.number1.contains(page)) {
					return;
				} else {
					JianrencunActivity.number1.add(page);
				}
			}

			switch (arg0) {
			case 1:
				num.setText("0");
				zhichi.setText("0");
				fandui.setText("0");
				int[] location = new int[2];
				Tab1.getLocationOnScreen(location);
				int x = location[0];
				int y = location[1];
				v1 = Tab1.getLeft();
				okronot = true;
				current = 1;
				RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mTabImg
						.getLayoutParams();
				params.setMargins(v1, 0, 0, 0);// 通过自定义坐标来放置你的控件
				mTabImg.setLayoutParams(params);

				kaiguan = 0;
				Tab1.setImageDrawable(getResources().getDrawable(
						R.drawable.contentsel));
				Tab2.setImageDrawable(getResources().getDrawable(
						R.drawable.criticism));
				if (currIndex == 2 || currIndex == 3) {
					animation = new TranslateAnimation(one, zero, 0, 0);
					animation.setFillAfter(true);// True:图片停在动画结束位置
					animation.setDuration(150);
					mTabImg.startAnimation(animation);
				} else {
					animation = null;
				}
				// nextpage.setVisibility(View.VISIBLE);
				// lastpage.setVisibility(View.VISIBLE);
				break;
			case 2:

				// v2 = Tab2.getLeft();
				//
				// RelativeLayout.LayoutParams params1 =
				// (RelativeLayout.LayoutParams) mTabImg
				// .getLayoutParams();
				// params1.setMargins(v2, 0, 0, 0);// 通过自定义坐标来放置你的控件
				// mTabImg.setLayoutParams(params1);

				Tab2.setImageDrawable(getResources().getDrawable(
						R.drawable.criticismsel));
				Tab1.setImageDrawable(getResources().getDrawable(
						R.drawable.content));
				current = 2;
				animation = new TranslateAnimation(zero, one, 0, 0);
				animation.setFillAfter(true);// True:图片停在动画结束位置
				animation.setDuration(150);
				mTabImg.startAnimation(animation);

				// nextpage.setVisibility(View.GONE);
				// lastpage.setVisibility(View.GONE);
				kaiguan = 1;
				if (istwice == false) {
					istwice = true;
					// currentdate = "";
					twice = 0;
					pb.setVisibility(View.VISIBLE);
					rllt3.setVisibility(View.VISIBLE);
					String ss = clink;
					items.clear();
					post(clink);
				}
				break;
			}
			currIndex = arg0;
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			if (view.getLastVisiblePosition() == view.getCount() - 1) {
				if (TextUtils.isEmpty(nlink)) {
					list.removeFooterView(vFooter);
					return;
				} else {
					twice = 3;
					if (!dlpl.contains(nlink)) {
						loading.setVisibility(View.VISIBLE);
						String url = nlink;
						StringBuffer data = new StringBuffer();

						data.append("pkg=");
						data.append(URLEncoder.encode(getPackageName()));
						//
						data.append("&pd=");
						lastdate = currentdate;
						if (lastdate == null) {
							lastdate = "";
						}
						data.append(URLEncoder.encode(lastdate));
						// 请求网络验证登陆
						nlink2 = geturl(url);
						HttpRequestTask request = new HttpRequestTask();
						request.execute(nlink2, data.toString());
						dlpl.add(nlink);
					}
				}
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
	}

	@Override
	public void resultData(String url, String result) {
		// TODO Auto-generated method stub
		String tip = null;
		if (result == null) {
			// fresh.setVisibility(View.VISIBLE);
			Commond.showToast(Details.this, "小贱提醒 ：当前网络不稳定！");
			pgd.setVisibility(View.GONE);
			rllt2.setVisibility(View.GONE);
			pb.setVisibility(View.GONE);
			rllt3.setVisibility(View.GONE);
			return;
		}

		try {
			// //////////////////////////////////////////////正文内容页面
			JSONObject jsonChannel = new JSONObject(result);
			int ret = jsonChannel.optInt("ret");
			tip = URLDecoder.decode(jsonChannel.optString("tip"));
			if (ret == 0) {
				Commond.showToast(Details.this, tip);
				if (tip.contains("评论成功！")) {
					if (kaiguan == 0) {
						istwice = false;
					} else if (kaiguan == 1) {
						items.clear();
						post(clink);
					}
					// pb.setVisibility(View.GONE);
					pgd.setVisibility(View.GONE);
					rllt2.setVisibility(View.GONE);
					edittext.setHint("亲，说点什么吧！");
					edittext.setEnabled(true);
					edittext.setText("");
					edittext.setGravity(Gravity.CENTER_VERTICAL);
					items.clear();
				} else {
					pgd.setVisibility(View.GONE);
					rllt2.setVisibility(View.GONE);
					pb.setVisibility(View.GONE);
					rllt3.setVisibility(View.GONE);
					edittext.setEnabled(true);
					edittext.setText(linshicz);
					edittext.setGravity(Gravity.CENTER_VERTICAL);
				}
				return;
			}
			btm = URLDecoder.decode(jsonChannel.optString("btime"));
			//
			if (!TextUtils.isEmpty(btm)) {
				tip = URLDecoder.decode(jsonChannel.optString("tip"));
				zfdurl = URLDecoder.decode(jsonChannel.optString("udurl"));
				String pd = URLDecoder.decode(jsonChannel.optString("pd"));
				nlink = URLDecoder.decode(jsonChannel.optString("nlink"));
				btime = URLDecoder.decode(jsonChannel.optString("btime"));
				uc = jsonChannel.optInt("uc");
				dc = jsonChannel.optInt("dc");
				cc = jsonChannel.optInt("cc");
				fc = jsonChannel.optInt("fc");
				num.setText(fc + "");
				zhichi.setText(uc + "");
				fandui.setText(dc + "");
				lastucount = uc;
				lastdcount = dc;
				// currentdate = pd;

				JSONArray jsonItems = jsonChannel.optJSONArray("items");
				for (int i = 0; i < jsonItems.length(); i++) {
					JSONObject jsonItem = jsonItems.getJSONObject(i);
					String title = URLDecoder.decode(jsonItem
							.optString("title"));
					String link = URLDecoder.decode(jsonItem.optString("link"));
					String date = URLDecoder.decode(jsonItem.optString("date"));
					String content = URLDecoder.decode(jsonItem
							.optString("desc"));
					String header = URLDecoder.decode(jsonItem
							.optString("uheader"));

					String name = URLDecoder
							.decode(jsonItem.optString("unick"));
					String chenghao = URLDecoder.decode(jsonItem
							.optString("utype"));
					String fcount = URLDecoder.decode(jsonItem
							.optString("fcount"));
					String clor = URLDecoder.decode(jsonItem
							.optString("desc_c"));
					String nameclor = URLDecoder.decode(jsonItem
							.optString("unick_c"));
					int autolink = jsonItem.optInt("desc_l");
					String uid = URLDecoder.decode(jsonItem.optString("uid"));

					int iscuti = jsonItem.optInt("desc_b");
					jubaolink = URLDecoder.decode(jsonItem.optString("link"));
					Pinglunitem item = new Pinglunitem(header, fcount,
							chenghao, content, date, name, title, link, clor,
							iscuti, uid, autolink, jubaolink, nameclor);
					items.add(item);
					pinglunfresh = false;
				}
			} else if (!TextUtils.isEmpty(uurl) &&url.contains(uurl)) {
				if (current == 1) {
					content = "";
					pd = URLDecoder.decode(jsonChannel.optString("pd"));
					currentdate = pd;
					content = URLDecoder.decode(jsonChannel
							.optString("content"));
					feedback = URLDecoder.decode(jsonChannel
							.optString("feedback"));
					header = currentlistviewiteminfo.get(po).getHeader();

					File f = new File(Appstart.jrcfile + "/"
							+ Adapterwithpic.getMd5Hash(geturl(url)));
					String sj = f.getPath().toString();
					CacheData.save(sj, result.getBytes(), this);
				}
			}
			if (!TextUtils.isEmpty(details) &&url.contains(geturl(details))) {
				// String feedback =
				// URLDecoder.decode(jsonChannel.optString("feedback"));
				pgd.setVisibility(View.GONE);
				rllt2.setVisibility(View.GONE);
				// pbbody.setVisibility(View.VISIBLE);
				// System.out.println("content2:" + content);
				loadWebView(content);
				currentdate = pd;
			} else if (!TextUtils.isEmpty(url2) && url.contains(geturl(url2))) {
				if (kaiguan == 0) {
					istwice = false;
				} else if (kaiguan == 1) {
					items.clear();
					dlpl.clear();
					post(clink);
				}
				// pb.setVisibility(View.GONE);
				pgd.setVisibility(View.GONE);
				rllt2.setVisibility(View.GONE);
				edittext.setHint("亲，说点什么吧！");
				edittext.setTextColor(Color.parseColor("#833a16"));
				edittext.setEnabled(true);
				edittext.setText("");
				edittext.setGravity(Gravity.CENTER_VERTICAL);
				items.clear();
				Commond.showToast(Details.this, tip);
			}

			else if (!TextUtils.isEmpty(save3) &&url.contains(geturl(save3))) {
				tip = URLDecoder.decode(jsonChannel.optString("tip"));
				issave = false;
				freshmysave = true;
				pgshoucang.setVisibility(View.GONE);
				// rllt.setVisibility(View.GONE);
				if (fav == 1) {
					shoucang.setBackgroundResource(R.drawable.notsave1);
					Commond.showToast(Details.this, tip);
					fav = 0;
					if (which == 44) {
						Mysave.listinfo.get(po).setIsfav(0);
					} else {
						MainMenuActivity.li.get(which).get(po).setIsfav(0);
					}
				} else if (fav == 0) {
					shoucang.setBackgroundResource(R.drawable.savebnt1);
					Commond.showToast(Details.this, tip);
					fav = 1;
					if (which == 44) {
						Mysave.listinfo.get(po).setIsfav(1);
					} else {
						MainMenuActivity.li.get(which).get(po).setIsfav(1);
					}
				}
				shoucang.setEnabled(true);
			}

			// //////////////////////////////////////////////////////////评论页面
			else if (!TextUtils.isEmpty(clink) &&url.contains(geturl(clink))) {
				pb.setVisibility(View.GONE);
				rllt3.setVisibility(View.GONE);

				adapter = new Adaptercri(Details.this, items);
				list.setAdapter(adapter);
				if (items.size() == 0) {
					shafa.setVisibility(View.VISIBLE);
				} else {
					shafa.setVisibility(View.GONE);
				}
				if (TextUtils.isEmpty(nlink)) {
					list.removeFooterView(vFooter);
				}
			}

			else if (!TextUtils.isEmpty(zhichi2) &&url.contains(geturl(zhichi2))) {
				pb.setVisibility(View.GONE);
				rllt3.setVisibility(View.GONE);
				ucount = jsonChannel.optInt("ucount");
				dcount = jsonChannel.optInt("dcount");
				if (ucount > lastucount) {
					// lastucount = ucount ;
					zhichi.setText(ucount + "");
				} else if (dcount > lastdcount) {
					// lastdcount = dcount ;
					fandui.setText(dcount + "");
				}
				Commond.showToast(Details.this, "投票成功！");
			} else if (!TextUtils.isEmpty(uurl) &&url.contains(uurl)) {
				if (kaiguan == 1) {
					if (istwice == true) {
						list.requestLayout();
//						adapter.notifyDataSetChanged();
						loading.setVisibility(View.GONE);
					} else {
						// currentdate = "";
						twice = 0;
						pb.setVisibility(View.VISIBLE);
						rllt3.setVisibility(View.VISIBLE);
						String ss = clink;
						post(clink);
					}
				} else if (kaiguan == 0) {
					pgd.setVisibility(View.GONE);
					rllt2.setVisibility(View.GONE);
					pbbody.setVisibility(View.VISIBLE);
					if (!TextUtils.isEmpty(content)) {
						// System.out.println("content3:" + content);
						loadWebView(content);
					}
					pb.setVisibility(View.GONE);
					rllt3.setVisibility(View.GONE);
					currentdate = pd;
				}
			} else {
				if (kaiguan == 1) {
					if (istwice == true) {
						list.requestLayout();
//						adapter.notifyDataSetChanged();
						loading.setVisibility(View.GONE);
					} else {
						// currentdate = "";
						twice = 0;
						pb.setVisibility(View.VISIBLE);
						rllt3.setVisibility(View.VISIBLE);
						String ss = clink;
						post(clink);
					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			// fresh.setVisibility(View.VISIBLE);
			pgd.setVisibility(View.GONE);
			rllt2.setVisibility(View.GONE);
			pb.setVisibility(View.GONE);
			rllt3.setVisibility(View.GONE);
			pgshoucang.setVisibility(View.GONE);
			if (!TextUtils.isEmpty(linshicz)) {
				edittext.setText(linshicz);
			} else {
				edittext.setHint("亲，说点什么吧！");
				edittext.setText("");
			}
			edittext.setEnabled(true);
			Commond.showToast(Details.this, "操作失败！请检查网络！");
			e.printStackTrace();
		}

	}

	public void detail_back(View v) { // 返回按钮
		finish();
	
	}

	public void detail_sx(View v) { // 刷新按钮
		if (kaiguan == 0) {
			post(uurl);
			pgd.setVisibility(View.VISIBLE);
			rllt2.setVisibility(View.VISIBLE);
		} else if (kaiguan == 1) {
			pb.setVisibility(View.VISIBLE);
			rllt3.setVisibility(View.VISIBLE);
			dlpl.clear();
			items.clear();
			post(clink);
			
		}
	}

	public void post(String urllink) {
		String result = "";
		// String url = appendNameValue(urllink, "pkg", "com.jianrencun.chatroom");
		StringBuffer data = new StringBuffer();
		// data.append("&uid=");
		// data.append(12);
		// currentdate = "";
		// data.append("&pd=");
		// lastdate = "";
		// data.append(URLEncoder.encode(lastdate));
		// 请求网络验证登陆
		HttpRequestTask request = new HttpRequestTask();
		request.execute(urllink, data.toString());
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		int[] location = new int[2];

		v1 = Tab1.getLeft();
		v2 = Tab2.getLeft();
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mTabImg
				.getLayoutParams();
		params.setMargins(v1, 0, 0, 0);// 通过自定义坐标来放置你的控件
		mTabImg.setLayoutParams(params);

		// RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams)
		// huadao2
		// .getLayoutParams();
		// params1.setMargins(v1, 0, v2, 0);// 通过自定义坐标来放置你的控件
		// huadao2.setLayoutParams(params1);
		super.onWindowFocusChanged(hasFocus);
	}

	class Adaptercri extends BaseAdapter {
		Context context;
		ViewHolder holder = null;;
		WindowManager wm;
		int height;
		File picfile;
		Pinglunitem item;
		List<Pinglunitem> it;
		private ArrayList<String> urls = new ArrayList<String>();

		public Adaptercri(Context context, List<Pinglunitem> items) {
			this.context = context;
			this.it = items;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return it.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			View itemView = convertView;
			ImageView imageView = null;
			Drawable cachedDrawable = null;
			String uid = "";
			int autolink = 0;

			if (itemView == null) {
				itemView = LayoutInflater.from(context).inflate(
						R.layout.pinglunitem, null);
				holder = new ViewHolder();
				holder.relativell = (RelativeLayout) itemView
						.findViewById(R.id.relativell);
				holder.pinglunll = (LinearLayout) itemView
						.findViewById(R.id.pinglunll);
				holder.content = (TextView) itemView
						.findViewById(R.id.contentofpinglun);
				holder.date = (TextView) itemView.findViewById(R.id.userdate);
				// holder.fcount = (TextView)
				// itemView.findViewById(R.id.numfcount);
				holder.title = (TextView) itemView.findViewById(R.id.usertitle);
				// holder.chenghao = (TextView)
				// itemView.findViewById(R.id.userchenghao);
				holder.imageheader = (ImageView) itemView
						.findViewById(R.id.pinglunheader);
				itemView.setTag(holder);
			} else {
				holder = (ViewHolder) itemView.getTag();
			}
			item = it.get(position);
			itemView.setTag(itemView.getId(), item);
			//
			holder.content.setText(item.getContent());
			autolink = it.get(position).getAutolink();
			if (autolink != 0) {
				// holder.content.setFocusable(false);
				holder.content.setAutoLinkMask(autolink);
			}
			holder.content.setAutoLinkMask(autolink);

			holder.imageheader.setTag(item.getHeader());
			holder.imageheader.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (v.getTag().equals(it.get(position).getHeader())) {
						if (TextUtils.isEmpty(it.get(position).getHeader())) {
							Commond.showToast(Details.this, "非本村村民！");
						} else {
							Intent intent = new Intent(getApplicationContext(),
									VillageUserInfoDialog.class);
							intent.putExtra("uid", it.get(position).getUid());
							intent.putExtra("nick", it.get(position).getTitle());
							intent.putExtra("nick", it.get(position).getTitle());
							intent.putExtra("fuid", MainMenuActivity.uid);
							intent.putExtra("type", 2);
							startActivityForResult(intent, 1);
						}
					}
				}
			});
			if (TextUtils.isEmpty(item.getHeader())) {
				holder.imageheader.setImageResource(R.drawable.defautheader);
			} else {
				holder.imageheader.setImageResource(R.drawable.photo);
				picfile = new File(Appstart.jrcfile + "/"
						+ Adapterwithpic.getMd5Hash(item.getHeader()));
				String filename = picfile.getPath().toString();
				Bitmap bmp = null ;
				if(picfile.exists()){
				  bmp = BitmapCache1.getIntance().getCacheBitmap(filename,
							0, 0);
				}
								
				if (bmp == null) {
					if (!urls.contains(item.getHeader())) {
						urls.add(item.getHeader());
						new Thread(new LoadImageRunnable(this.context,
								mHandler, 0, item.getHeader(), filename))
								.start();
					}
					holder.imageheader
							.setImageResource(R.drawable.defautheader);
				} else {
					holder.imageheader.setImageBitmap(bmp);
				}
			}

			if (!TextUtils.isEmpty(item.getClor())) {
				holder.content.setTextColor(Color.parseColor(item.getClor()));
			} else {
				holder.content.setTextColor(Color.parseColor("#482c3f"));
			}

			if (!TextUtils.isEmpty(item.getNameclor())) {
				holder.title.setTextColor(Color.parseColor(item.getNameclor()));
			} else {
				holder.title.setTextColor(Color.parseColor("#827a80"));
			}

			if (item.getIscuti() == 1) {
				TextPaint tpaint = holder.content.getPaint();
				tpaint.setFakeBoldText(true);
			} else {
				TextPaint tpaint = holder.content.getPaint();
				tpaint.setFakeBoldText(false);
			}

			holder.date.setText(comDate(btime, item.getDate()));
			// holder.fcount.setText(item.getFcount());
			holder.title.setText(item.getTitle());
			// holder.chenghao.setText(item.getChenghao());

			holder.relativell.setBackgroundResource(R.drawable.listitem1);

			return itemView;
		}

		Handler mHandler = new Handler() {
			public void handleMessage(Message msg) {
				Bundle data = msg.getData();
				if (data != null) {
					String url = data.getString("url");
					String filename = data.getString("filename");

					Bitmap bmp = BitmapCache1.getIntance().getCacheBitmap(
							filename, 0, 0);
					ImageView iv = (ImageView) list.findViewWithTag(url);
					if (iv != null) {
						iv.setImageBitmap(bmp);
					}
				}
			}
		};
	}

	class ViewHolder {
		public LinearLayout pinglunll;
		public ImageView imageheader;
		public TextView fcount;
		public RelativeLayout relativell;
		public TextView title;
		public TextView content;
		public TextView date;
		public String uid;
	}

	public void favourite(int favourite) {
		if (favourite == 0) {
			shoucang.setBackgroundResource(R.drawable.notsave1);
		} else if (favourite == 1) {
			shoucang.setBackgroundResource(R.drawable.savebnt1);
		}
	}

	// /////////////////////////////////////////////////////////////计算时间
	/**
	 * 
	 * @param dateStr
	 * @return
	 */
	static private long getDate(String dateStr, boolean isShort) {
		long millis = -1;
		Date date = null;
		if (TextUtils.isEmpty(dateStr))
			return millis;
		if (TextUtils.isEmpty(dateStr) || TextUtils.isDigitsOnly(dateStr)) {
			date = new Date(Long.parseLong(dateStr));
		} else {
			String formatStr = "yyyy-MM-dd HH:mm:ss";
			if (isShort) {
				formatStr = "yyyy-MM-dd";
			}
			DateFormat format = new SimpleDateFormat(formatStr);
			try {
				date = format.parse(dateStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			millis = calendar.getTimeInMillis();
		}
		return millis;
	}

	/**
	 * 
	 * @param pubdate
	 * @return
	 */
	static public String strDate(long baseMillis, long pubMillis) {
		long millis = baseMillis - pubMillis;
		millis = millis / 1000;
		// //@////@System.out.println("millis:" + millis);
		if (millis < 60)
			return millis + "秒前";
		else {
			if (millis < 60 * 60)
				return (int) (millis / 60) + "分钟前";
			else {
				if (millis < 24 * 60 * 60)
					return (int) (millis / (60 * 60)) + "小时前";
				else {
					if (millis < 7 * 24 * 60 * 60) {
						int days = (int) (millis / (24 * 60 * 60));
						if (days == 1)
							return "昨天";
						else if (days == 2)
							return "前天";
						Calendar calendar1 = Calendar.getInstance();
						calendar1.setTimeInMillis(baseMillis);
						int d1 = calendar1.get(Calendar.DAY_OF_WEEK);
						if (days >= d1) {
							Calendar calendar2 = Calendar.getInstance();
							calendar2.setTimeInMillis(pubMillis);
							int d2 = calendar2.get(Calendar.DAY_OF_WEEK);
							return "上周" + weekStr(d2);
						}
						return days + "天前";
					} else {
						if (millis < 4 * 7 * 24 * 60 * 60) {
							int weeks = (int) (millis / (7 * 24 * 60 * 60));
							if (weeks == 1) {
								String strWeek = "上周";
								Calendar calendar = Calendar.getInstance();
								calendar.setTimeInMillis(pubMillis);
								int d = calendar.get(Calendar.DAY_OF_WEEK);
								strWeek += weekStr(d);
								return strWeek;
							}
							return weeks + "周前";
						} else {
							if (millis < 29030400/* (12 * 4 * 7 * 24 * 60 * 60) */) {
								return (int) (millis / (4 * 7 * 24 * 60 * 60))
										+ "月前";
							} else {
								int y = (int) (millis / 29030400/*
																 * (12 * 4 * 7 *
																 * 24 * 60 * 60)
																 */);
								if (y <= 0)
									return null;
								return y + "年前";
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param pubdate
	 * @return
	 */
	static public String comDate(String baseDate, String pubDate) {
		long baseMillis = getDate(baseDate, false);
		if (baseMillis == -1)
			return pubDate;

		long pubMillis = getDate(pubDate, false);
		if (pubMillis == -1)
			return pubDate;

		//
		String str = strDate(baseMillis, pubMillis);
		return str == null ? pubDate : str;
	}

	static public String weekStr(int week) {
		switch (week) {
		case 1:
			return "一";
		case 2:
			return "二";
		case 3:
			return "三";
		case 4:
			return "四";
		case 5:
			return "五";
		case 6:
			return "六";
		case 7:
			return "日";
		}
		return week + "";
	}

	public static String geturl(String s) {
		if (TextUtils.isEmpty(s)) {
			return null;
		}
		if (!s.contains("uid=")) {
			if (s.contains("?")) {
				s += "&uid=" + MainMenuActivity.uid;
			} else {
				s += "?uid=" + MainMenuActivity.uid;
			}
		}
		return s;
	}

	private String getWebCacheLegth(String url) {
		wv.loadUrl("javascript:wave()");
		// Commond.showToast(mContext, "clickOnAndroid");
		WebViewCacheAdapter db = new WebViewCacheAdapter(Details.this);
		final String filepath = db.getUrlToFileName(url);
		if (!TextUtils.isEmpty(filepath)) {
			String srcFileName = Details.this.getCacheDir() + "/webviewCache/"
					+ filepath;
			return srcFileName;
		}
		return null;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		freshmysave = false;
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub

		super.onPause();
		MobclickAgent.onPause(this);
	}

	final static class ImageJavaScriptInterface {
		Context mContext;
		static String srcFileName;
		String path ;
		Details da = new Details();

		public ImageJavaScriptInterface(Context context) {
			mContext = context;
		}

		/**
		 * This is not called on the UI thread. Post a runnable to invoke
		 * loadUrl on the UI thread.
		 */
		public void clickOnAndroid(final String src) {
			// mWebView.loadUrl("javascript:wave()");
			// Commond.showToast(mContext, "clickOnAndroid");
			// if(man == false){
			WebViewCacheAdapter db = new WebViewCacheAdapter(mContext);
			final String filepath = db.getUrlToFileName(src);
			if (!TextUtils.isEmpty(filepath)) {
				srcFileName = mContext.getCacheDir() + "/webviewCache/"
						+ filepath;
			} else {
				srcFileName = src;
			}
			if(!TextUtils.isEmpty(Appstart.jrcsave.getPath())){
				path  = Appstart.jrcsave.getPath();
			}
			else{
				path = Environment
				.getExternalStorageDirectory().toString()
				+ "/"
				+ ConstantsJrc.SAVE_PATH;
			}
			Intent i = new Intent(mContext, ImageZoom.class);
			i.putExtra("imageurl", srcFileName);
			i.putExtra("savepath", path);
			i.putExtra("downpath", path);

			mContext.startActivity(i);
		}
	}

	/**
		 * 
		 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent e) {
		// if (e.getAction() == MotionEvent.ACTION_DOWN) {
		// //类型为Down才处理，还有Move/Up之类的
		Rect r = new Rect();
		mianban.getGlobalVisibleRect(r);
		if (r.contains((int) e.getX(), (int) e.getY())) {
			return super.dispatchTouchEvent(e);
		} else {
			try {						
			if (!edittext.hasFocus()) {
				return super.dispatchTouchEvent(e);
			} else {
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.hideSoftInputFromWindow(
						edittext.getWindowToken(), 0);
				edittext.clearFocus();
				return false;
			 }
			} catch (Exception e2) {
				// TODO: handle exception
				this.finish();
				return false ;	
			}
		}
	}

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

			Adapterwithpic.urlToFile(mContext, sUrl, mFilename);
			Message msg = new Message();
			Bundle data = new Bundle();
			data.putString("url", sUrl);
			data.putString("filename", mFilename);
			msg.setData(data);
			mHandler.sendMessage(msg);
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stu
		if(wv!=null){
		wv.clearHistory();
		}
		super.onDestroy();
	}

	// 使用快捷分享完成分享（请务必仔细阅读位于SDK解压目录下Docs文件夹中OnekeyShare类的JavaDoc）
		private void showShare(boolean silent, String platform) {
			
			File picfile;
			if (!TextUtils.isEmpty(header)) {
				picfile = new File(Appstart.jrcfile + "/"
						+ Adapterwithpic.getMd5Hash(header));
				filename = picfile.getPath().toString();
				Bitmap bmp = BitmapCache1.getIntance()
						.getCacheBitmap(filename, 0, 0);

				if (bmp == null) {
					progressd.show();
					new Thread(new LoadImageRunnable(Details.this, mHandler, 0,
							header, filename)).start();

					return;
				}
			}
			//[命运掌握在自己手里~]。点击查看：http://jianrencun.com/share.php?id=807791 @贱人村V',,http://jianrencun.com/share.php?id=807791 

			String link = "";
			if (!TextUtils.isEmpty(feedback)) {
				int a = feedback.lastIndexOf("http");
				int b;
				if (feedback.contains("@")) {
					b = feedback.lastIndexOf("@");
					link = (feedback.substring(a, b ));					
				}	
			}
//			android.util.Log.e("oooo", feedback+"',,"+link);
			OnekeyShare oks = new OnekeyShare();
			oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
			oks.setTitle("分享");
			oks.setTitleUrl(link);
			oks.setText(feedback);
			oks.setImagePath(filename);
			oks.setImageUrl(header);
			oks.setUrl(link);
			oks.setAppPath(Details.TEST_IMAGE);
			oks.setComment("分享");
			oks.setSite(this.getString(R.string.app_name));
			oks.setSiteUrl(link);
			oks.setWhich(1);
//			oks.setVenueName("Southeast in China");
//			oks.setVenueDescription("This is a beautiful place!");
//			oks.setLatitude(23.122619f);
//			oks.setLongitude(113.372338f);
			oks.setSilent(silent);
			if (platform != null) {
				oks.setPlatform(platform);
			}

			// 去除注释，则快捷分享的分享加过将听过OneKeyShareCallback回调
//			oks.setCallback(new OneKeyShareCallback());

			oks.show(this);
		}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			if (data != null) {
				progressd.cancel();
				File picfile = new File(Appstart.jrcfile + "/"
						+ Adapterwithpic.getMd5Hash(header));
				String filename = picfile.getPath().toString();
				
				String link = "";
				if (!TextUtils.isEmpty(feedback)) {
					int a = feedback.lastIndexOf("http");
					int b;
					if (feedback.contains("@")) {
						b = feedback.lastIndexOf("@");
						link = (feedback.substring(a, b ));					
					}	
//					if(feedback.length()>=14){
//						feedback = feedback.substring(0, 12);
//						}
				}

				OnekeyShare oks = new OnekeyShare();
				oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
				oks.setTitle("分享");
				oks.setTitleUrl(link);
				oks.setText(feedback);
				oks.setImagePath(filename);
				oks.setImageUrl(header);
				oks.setAppPath(Details.TEST_IMAGE);
				oks.setComment("分享");
				oks.setSite(Details.this.getString(R.string.app_name));
				oks.setSiteUrl(link);
				oks.setWhich(1);
//				oks.setVenueName("Southeast in China");
//				oks.setVenueDescription("This is a beautiful place!");
//				oks.setLatitude(23.122619f);
//				oks.setLongitude(113.372338f);
				oks.setSilent(false);

				// 去除注释，则快捷分享的分享加过将听过OneKeyShareCallback回调
//				oks.setCallback(new OneKeyShareCallback());
//				oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());

				oks.show(Details.this);
			}
		}
	};

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		return false;
	}
}
