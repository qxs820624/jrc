package com.jianrencun.android;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import cn.sharesdk.onekeyshare.SharePage;

import com.app.chatroom.Appstart;
import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.otherui.MainTopRightDialog;
import com.app.chatroom.pic.BitmapCacheBguanzhu;
import com.app.chatroom.pic.BitmapCacheBlack;
import com.app.chatroom.pic.BitmapCacheVillage;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.ui.ChatRoomActivity;
import com.app.chatroom.ui.UserInfoActivity;
import com.app.chatroom.util.Commond;
import com.app.chatroom.util.SystemUtil;
import com.duom.fjz.iteminfo.Adapterwithpic;
import com.duom.fjz.iteminfo.BackpackAdapter;
import com.duom.fjz.iteminfo.Backpacks;
import com.duom.fjz.iteminfo.BitmapCache;
import com.duom.fjz.iteminfo.BitmapCache1;
import com.duom.fjz.iteminfo.CateDetailEntity;
import com.duom.fjz.iteminfo.Iteminfo;
import com.duom.fjz.iteminfo.Tixing;
import com.duom.fjz.iteminfo.WebviewDonghua;
import com.jianrencun.chatroom.R;
import com.umeng.analytics.MobclickAgent;

public class JianrencunActivity extends HttpBaseActivity implements
		OnScrollListener, OnItemClickListener, OnClickListener {

	public static JianrencunActivity instance = null;
	static boolean yesorno = false;
	public ViewPager mTabPager;
	private String lastdate;
	boolean istwice = true;

	private int twice = 0;
	private String currentdate = "";
	private LinearLayout mTabImg;// 动画图片
	private TextView mTab1, mTab2, mTab3;
	private int zero = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int one;// 单个水平动画位移
	private int two;
	private int isfav;
	private int tabpage;
	private ListView hotestlv;
	private List<String> dllinks;
	// static List<String> links;
	int lastview;
	public static List<String> number1 = new ArrayList<String>();;
	private Button fresh;
	boolean flag = true;
	String currenturl;
	private String flink, rlink, clink;
	Thread th;
	int index = 0;
	static int indx;
	public Adapterwithpic adapt1;
	List<Iteminfo> itemchoose1, huancunitems1;
	Button zuo, you;
	ArrayList<String> qjUrl;
	ProgressBar pb;
	String lsnextlink = "", judgelink = "";
	private int visibleLastIndex = 0; // 最后的可视项索引
	private int visibleItemCount; // 当前窗口可见项总数
	boolean isRefreshFoot = false, isnew = false;
	boolean kaiguan = true, kaiguan1 = false, kaiguan2 = false;
	static RelativeLayout relativbar, mainbottom;
	static ListView morelv;
	static TextView titletar;
	static ProgressBar pbmore;
	Bitmap bitmap;
	private LinearLayout loading;
	private View vFooter;
	int v1, v2, v3, hd;
	private TextView huadao;
	ConnectivityManager cwjManager;
	NetworkInfo info;
	private int m = 0;
	// static final String p = "/data/data/com.jianrencun.android/files/";
	public String path;
	public ListView lv1;
	private List<ListView> lvs;
	private List<String> nextlinks;
	private List<Button> freshs;
	private List<ProgressBar> pbs;
	private LinearLayout rllt;
	private List<LinearLayout> rllts;
	private List<Adapterwithpic> adapters;
	private List<Boolean> bl;
	boolean how = false;
	String str, str_temp, remourl;
	Display currDisplay;
	Adapterwithpic adapt;

	SharedPreferences sp;
	SystemSettingUtilSp su;

	// private List<Iteminfo>[] listsinfo = {items1, items2};
	int loc, yesnlink;
	boolean x = false, y = false, z = false;
	
	Button bnt ;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		try{
		setContentView(R.layout.main);
		}catch (Exception e) {
			// TODO: handle exception
			Commond.showToast(this, "小贱出问题了！请重新登录！三克油（thank u）！");
		}
		
//		Intent it = new Intent() ;
//		it.setClass(JianrencunActivity.this, WebviewDonghua.class);
//		startActivity(it);
		Intent it = getIntent();		
		int where = it.getIntExtra("where", 0);
		String tp = it.getStringExtra("tp");
		if(!TextUtils.isEmpty(tp)){
		Intent toit = new Intent() ;
		toit.setClass(this, Tixing.class);
		toit.putExtra("where", where);
		toit.putExtra("tp", tp);
		startActivity(toit);
		}
		
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		info = cwjManager.getActiveNetworkInfo();

		path = getSDPath();
		instance = this;
		
		 InputStream is = this.getResources().openRawResource(R.drawable.liebiaotranslet); 
	     BitmapFactory.Options options=new BitmapFactory.Options(); 
	     options.inJustDecodeBounds = false; 
	     bitmap =BitmapFactory.decodeStream(is,null,options);
	     

		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);

		/** 创建基础文件 **/
		SystemUtil.makeDir(this);

		MainMenuActivity.li = new ArrayList<ArrayList<Iteminfo>>();
		dllinks = new ArrayList<String>();
		CacheData.detailcontent = new ArrayList<CateDetailEntity>();
		CacheData.sddetail = new File(Appstart.jrcfile + "/"
				+ "detailgorys.txt");
		CacheData.detailgorys = CacheData.sddetail.getPath().toString();
		CacheData.sdsc = new File(Appstart.jrcfile + "/"
				+ "shangchuangorys.txt");
		CacheData.myscgorys = CacheData.sdsc.getPath().toString();
		lvs = new ArrayList<ListView>();
		qjUrl = new ArrayList<String>();
		nextlinks = new ArrayList<String>();
		freshs = new ArrayList<Button>();
		adapters = new ArrayList<Adapterwithpic>();
		rllts = new ArrayList<LinearLayout>();
		pbs = new ArrayList<ProgressBar>();
		// listsinfo = new ArrayList<Iteminfo>[]{items1, items2};
		bl = new ArrayList<Boolean>();
		itemchoose1 = new ArrayList<Iteminfo>();
		huancunitems1 = new ArrayList<Iteminfo>();

		// bnt = (Button)findViewById(R.id.tobackpacks);
		// bnt.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// Intent it = new Intent();
		// it.setClass(JianrencunActivity.this, Backpacks.class);
		// startActivity(it);
		// }
		// });
		
		huadao = (TextView) findViewById(R.id.huadao);

		pbmore = (ProgressBar) findViewById(R.id.progressmore);
		relativbar = (RelativeLayout) findViewById(R.id.relatbar);
		mainbottom = (RelativeLayout) findViewById(R.id.main_bottom);
		morelv = (ListView) findViewById(R.id.morelv);
		titletar = (TextView) findViewById(R.id.titletar);
		// /////////////
		vFooter = LayoutInflater.from(this).inflate(R.layout.list_footer, null);
		loading = (LinearLayout) vFooter.findViewById(R.id.list_footer);
		loading.setVisibility(View.GONE);

		zuo = (Button) findViewById(R.id.zuo);
		you = (Button) findViewById(R.id.you);

		mTabPager = (ViewPager) findViewById(R.id.tabpager);
		mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());

		mTab1 = (TextView) findViewById(R.id.img_weixin);
		mTab2 = (TextView) findViewById(R.id.img_address);
		mTab3 = (TextView) findViewById(R.id.img_friends);
		mTabImg = (LinearLayout) findViewById(R.id.img_tab_now);
		try {
			mTab1.setText(CacheData.cateList.get(0).getName().toString());
			mTab2.setText(CacheData.cateList.get(1).getName().toString());
			mTab3.setText(CacheData.cateList.get(2).getName().toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Commond.showToast(this, "登录超时！请重新登录软件！");
		}

		mTab1.setOnClickListener(new MyOnClickListener(0));
		mTab2.setOnClickListener(new MyOnClickListener(1));
		mTab3.setOnClickListener(new MyOnClickListener(2));

		currDisplay = getWindowManager().getDefaultDisplay();// 获取屏幕当前分辨率
		int displayWidth = (int) (currDisplay.getWidth() * 0.974);
		int displayHeight = currDisplay.getHeight();
		one = displayWidth / 3; // 设置水平动画平移大小
		two = one * 2;

		RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) mTabImg
				.getLayoutParams();
		llParams.width = (int) (one * 0.9);

		File picfile = new File(Appstart.jrcfile + "/" + "readlinks");
		String filename = picfile.getPath().toString();
		if (picfile.exists()) {
			String ss = CacheData.load(filename, JianrencunActivity.this);
			if (!TextUtils.isEmpty(ss)) {
				Pattern pt = Pattern.compile("\\s*");
				Matcher m = pt.matcher(ss);
				ss = m.replaceAll("");
				ss = ss.replaceAll("(\\])", "");
				ss = ss.replaceAll("(\\[)", "");

				String sz[] = ss.split(",");
				JianrencunActivity.number1.clear();
				for (int i = 0; i < sz.length; i++) {
					JianrencunActivity.number1.add(sz[i]);
				}
			}
		}

		// 将要分页显示的View装入数组中
		LayoutInflater mLi = LayoutInflater.from(this);
		if (info == null) {
			// do nothing
			pb.setVisibility(View.GONE);
			fresh.setVisibility(View.VISIBLE);
			Commond.showToast(this, "网络链接失败！");
			return;
		}

		// 每个页面的view数据
		final ArrayList<View> views = new ArrayList<View>();
		for (int i = 0; i < CacheData.cateList.size(); i++) {
			loc = i;
			Boolean boo = false;
			bl.add(boo);

			Adapterwithpic adt = null;
			adapters.add(adt);

			ArrayList<Iteminfo> items = new ArrayList<Iteminfo>();
			MainMenuActivity.li.add(items);

			String nextlink = null;
			nextlinks.add(nextlink);

			String qj = null;
			qjUrl.add(qj);

			View view = mLi.inflate(R.layout.moreview, null);
			hotestlv = (ListView) view.findViewById(R.id.moreview);
			lvs.add(hotestlv);
			lvs.get(i).setOnScrollListener(this);
			lvs.get(i).setId(i);
			lvs.get(i).addFooterView(vFooter);
			lvs.get(i).setFooterDividersEnabled(false);
			lvs.get(i).setOnItemClickListener(this);
			pb = (ProgressBar) view.findViewById(R.id.progressmoreview);
			fresh = (Button) view.findViewById(R.id.fresh);
			pbs.add(pb);
			rllt = (LinearLayout) view
					.findViewById(R.id.village_leftlist_progressbar_RelativeLayout0);
			rllts.add(rllt);
			freshs.add(fresh);
			freshs.get(i).setOnClickListener(this);
			views.add(view);
		}

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
		try{
		currenturl = geturl(CacheData.cateList.get(0).getLink()).replace(
				"pkg=" + getPackageName() + "&", "");	
		int n_pos;
		pbs.get(0).setVisibility(View.VISIBLE);
		n_pos = currenturl.indexOf("&");
		currenturl = currenturl.substring(0, n_pos);

		String url = CacheData.cateList.get(0).getLink();
		url = addver(url);
		String url2 = geturl(url);

		StringBuffer data = new StringBuffer();
		data.append("pkg=");
		data.append(URLEncoder.encode(getPackageName()));
		//
		data.append("&pd=");
		lastdate = currentdate;
		data.append(URLEncoder.encode(lastdate));

		data.append("&token=");
		data.append(URLEncoder.encode(su.getToken()));

		// 请求网络验证登陆
		HttpRequestTask request = new HttpRequestTask();
		request.execute(url2, data.toString());
		bl.set(0, true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Commond.showToast(this, "登录超时！请重新登录软件！");
			return ;
		}
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
	 * 页卡切换监听()
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			indx = arg0;
			m = arg0;
			// if(lastview <= 2 ){
			// mTab1.setOnClickListener(new MyOnClickListener(0));
			// mTab2.setOnClickListener(new MyOnClickListener(1));
			// mTab3.setOnClickListener(new MyOnClickListener(2));
			// }
			try {
			if (arg0 - lastview > 0) {
				if (tabpage < 2) {
					tabpage++;
				}
				int xx = tabpage;
				if (arg0 >= 2) {

					if (tabpage == 0) {
						tabpage++;
						mTab1.setTextColor(Color.parseColor("#ff8400"));
						mTab2.setTextColor(Color.parseColor("#833a16"));
						mTab3.setTextColor(Color.parseColor("#833a16"));
						mTab1.setText(CacheData.cateList.get(arg0).getName()
								.toString());
						mTab2.setText(CacheData.cateList.get(arg0 + 1)
								.getName().toString());
						mTab3.setText(CacheData.cateList.get(arg0 + 2)
								.getName().toString());
						mTab1.setOnClickListener(new MyOnClickListener(arg0));
						mTab2.setOnClickListener(new MyOnClickListener(arg0 + 1));
						mTab3.setOnClickListener(new MyOnClickListener(arg0 + 2));
					} else if (tabpage == 1) {
						mTab1.setTextColor(Color.parseColor("#833a16"));
						mTab2.setTextColor(Color.parseColor("#ff8400"));
						mTab3.setTextColor(Color.parseColor("#833a16"));
						animation = new TranslateAnimation(zero, one, 0, 0);
					} else {
						mTab1.setTextColor(Color.parseColor("#833a16"));
						mTab2.setTextColor(Color.parseColor("#833a16"));
						mTab3.setTextColor(Color.parseColor("#ff8400"));
						mTab1.setText(CacheData.cateList.get(arg0 - 2)
								.getName().toString());
						mTab2.setText(CacheData.cateList.get(arg0 - 1)
								.getName().toString());
						mTab3.setText(CacheData.cateList.get(arg0).getName()
								.toString());
						mTab1.setOnClickListener(new MyOnClickListener(arg0 - 2));
						mTab2.setOnClickListener(new MyOnClickListener(arg0 - 1));
						mTab3.setOnClickListener(new MyOnClickListener(arg0));
						if (arg0 >= 2) {
							animation = new TranslateAnimation(one, two, 0, 0);
						}
					}

				} else if (arg0 < 2) {
					if (indx % 3 == 0) {
						mTab1.setText(CacheData.cateList.get(indx).toString());
						mTab1.setTextColor(Color.parseColor("#ff8400"));
						mTab2.setTextColor(Color.parseColor("#833a16"));
						mTab3.setTextColor(Color.parseColor("#833a16"));
						mTab2.setText(CacheData.cateList.get(indx + 1)
								.getName().toString());
						mTab3.setText(CacheData.cateList.get(indx + 2)
								.getName().toString());
						mTab1.setOnClickListener(new MyOnClickListener(indx));
						mTab2.setOnClickListener(new MyOnClickListener(indx + 1));
						mTab3.setOnClickListener(new MyOnClickListener(indx + 2));

					} else if (indx % 3 == 1) {
						if (CacheData.cateList.size() == 0
								|| CacheData.cateList == null) {
							finish();
						}
						
							mTab1.setText(CacheData.cateList.get(indx - 1)
									.getName().toString());
							mTab2.setText(CacheData.cateList.get(indx)
									.getName().toString());
							mTab2.setTextColor(Color.parseColor("#ff8400"));
							mTab1.setTextColor(Color.parseColor("#833a16"));
							mTab3.setText(CacheData.cateList.get(indx + 1)
									.getName().toString());
							mTab3.setTextColor(Color.parseColor("#833a16"));
							mTab1.setOnClickListener(new MyOnClickListener(
									indx - 1));
							mTab2.setOnClickListener(new MyOnClickListener(indx));
							mTab3.setOnClickListener(new MyOnClickListener(
									indx + 1));
							animation = new TranslateAnimation(zero, one, 0, 0);
						
					} else if (indx % 3 == 2) {
						mTab1.setText(CacheData.cateList.get(indx - 2)
								.getName().toString());
						mTab2.setText(CacheData.cateList.get(indx - 1)
								.getName().toString());
						mTab3.setTextColor(Color.parseColor("#ff8400"));
						mTab2.setTextColor(Color.parseColor("#833a16"));
						mTab1.setTextColor(Color.parseColor("#833a16"));
						mTab3.setText(CacheData.cateList.get(indx).getName()
								.toString());
						mTab1.setOnClickListener(new MyOnClickListener(indx - 2));
						mTab2.setOnClickListener(new MyOnClickListener(indx - 1));
						mTab3.setOnClickListener(new MyOnClickListener(indx));
						animation = new TranslateAnimation(one, two, 0, 0);
					}
				}
				if (arg0 - lastview > 1) {
					animation = new TranslateAnimation(zero, two, 0, 0);
					mTab1.setTextColor(Color.parseColor("#833a16"));
					mTab2.setTextColor(Color.parseColor("#833a16"));
					mTab3.setTextColor(Color.parseColor("#ff8400"));
				}
				// 左右提醒箭头、、、
				zuo.setVisibility(View.GONE);
				you.setVisibility(View.VISIBLE);
				if (arg0 >= CacheData.cateList.size() - 1) {
					zuo.setVisibility(View.VISIBLE);
					you.setVisibility(View.GONE);
				}
			} else if (arg0 - lastview < 0) {
				int xxd = tabpage;
				if (tabpage == 2) {
					tabpage--;
					mTab1.setTextColor(Color.parseColor("#833a16"));
					mTab2.setTextColor(Color.parseColor("#ff8400"));
					mTab3.setTextColor(Color.parseColor("#833a16"));
					animation = new TranslateAnimation(two, one, 0, 0);
				} else if (tabpage == 1) {
					tabpage--;
					mTab1.setTextColor(Color.parseColor("#ff8400"));
					mTab2.setTextColor(Color.parseColor("#833a16"));
					mTab3.setTextColor(Color.parseColor("#833a16"));
					animation = new TranslateAnimation(one, zero, 0, 0);
				} else if (tabpage == 0) {
					mTab1.setTextColor(Color.parseColor("#ff8400"));
					mTab2.setTextColor(Color.parseColor("#833a16"));
					mTab3.setTextColor(Color.parseColor("#833a16"));
					mTab1.setText(CacheData.cateList.get(arg0).getName()
							.toString());
					mTab2.setText(CacheData.cateList.get(arg0 + 1).getName()
							.toString());
					mTab3.setText(CacheData.cateList.get(arg0 + 2).getName()
							.toString());
					mTab1.setOnClickListener(new MyOnClickListener(arg0));
					mTab2.setOnClickListener(new MyOnClickListener(arg0 + 1));
					mTab3.setOnClickListener(new MyOnClickListener(indx + 2));
				}
				// 左右提醒箭头、、、
				zuo.setVisibility(View.VISIBLE);
				you.setVisibility(View.GONE);
				if (arg0 <= 0) {
					zuo.setVisibility(View.GONE);
					you.setVisibility(View.VISIBLE);
				}
				if (lastview - arg0 > 1) {
					animation = new TranslateAnimation(two, zero, 0, 0);
					mTab1.setTextColor(Color.parseColor("#ff8400"));
					mTab2.setTextColor(Color.parseColor("#833a16"));
					mTab3.setTextColor(Color.parseColor("#833a16"));
				}
			}		
			currenturl = geturl(CacheData.cateList.get(indx).getLink())
					.replace("pkg=" + getPackageName() + "&", "");
			int n_pos;
			n_pos = currenturl.indexOf("&");
			currenturl = currenturl.substring(0, n_pos);
			if (bl.get(indx) == false) {
				twice = 0;
				bl.set(indx, true);
				if (CacheData.cateList.size() == 0
						|| CacheData.cateList == null) {
					bl.set(indx, false);
					return;
				}
				pbs.get(indx).setVisibility(View.VISIBLE);
				rllts.get(indx).setVisibility(View.VISIBLE);
				yesnlink = 0;
				String url = CacheData.cateList.get(indx).getLink();
				url = addver(url);
				String url2 = geturl(url);

				StringBuffer data = new StringBuffer();
				data.append("pkg=");
				data.append(URLEncoder.encode(getPackageName()));
				//
				data.append("&pd=");
				lastdate = currentdate;
				data.append(URLEncoder.encode(lastdate));

				data.append("&token=");
				data.append(URLEncoder.encode(su.getToken()));
				// 请求网络验证登陆
				HttpRequestTask request = new HttpRequestTask();
				request.execute(url2, data.toString());
			}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				Commond.showToast(JianrencunActivity.this, "登录超时，请重新登录！");
				finish();
			}

			lastview = arg0;
			if (animation != null) {
				animation.setFillAfter(true);// True:图片停在动画结束位置
				animation.setDuration(150);
				mTabImg.startAnimation(animation);
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// lastview = arg0;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (yesorno == true) {
			yesorno = false;
			if (lvs.get(m).getAdapter() != null) {
				lvs.get(m).requestLayout();
				adapters.get(m).notifyDataSetChanged();
			}

		}
//		SharePage.isshare = false;
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	public void main_back(View v) {
		finish();
	}

	public void main_sort(View v) {
		Intent intent = new Intent();
		intent.setClass(JianrencunActivity.this, TopRight.class);
		startActivity(intent);
		// Intent intent = new Intent(JianrencunActivity.this,
		// TopRight.class);
		// startActivity(intent);
	}

	@Override
	public void resultData(String url, String result) {
		// TODO Auto-generated method stub
		String tip = null;
		// pb.setVisibility(View.GONE);
		if (result == null) {
			judgelink = url;
			if (url.contains("&start=")) {
				lsnextlink = url;
			} else {
				qjUrl.set(m, url);
			}
			freshs.get(m).setVisibility(View.VISIBLE);
			pbs.get(m).setVisibility(View.GONE);
			rllts.get(m).setVisibility(View.GONE);
			return;
		}
		try {
			pbs.get(0).setVisibility(View.GONE);
			rllts.get(0).setVisibility(View.GONE);

			JSONObject jsonChannel = new JSONObject(result);
			tip = URLDecoder.decode(jsonChannel.optString("tip"));
			String pd = URLDecoder.decode(jsonChannel.optString("pd"));
			String nlink = URLDecoder.decode(jsonChannel.optString("nlink"));
			int ret = jsonChannel.optInt("ret");
			if (ret == 0) {
				freshs.get(m).setVisibility(View.VISIBLE);
				pbs.get(m).setVisibility(View.GONE);
				rllts.get(m).setVisibility(View.GONE);
				Commond.showToast(this, tip);
				return;
			}

			for (int i = 0; i <= CacheData.cateList.size() - 1; i++) {
				// str = CacheData.cateList.get(i).getLink();
				if (CacheData.cateList.size() != 0
						&& CacheData.cateList != null) {
					remourl = CacheData.cateList.get(i).getLink();
					if (url.contains(remourl)) {
						nextlinks.set(i, nlink);
						how = true;
					}
				}
			}
			JSONArray jsonItems = jsonChannel.optJSONArray("items");
			if (jsonItems != null) {
				// tip = "获取成功！";
				for (int i = 0; i < jsonItems.length(); i++) {
					JSONObject jsonItem = jsonItems.getJSONObject(i);
					int id = jsonItem.optInt("id");
					String title = URLDecoder.decode(jsonItem
							.optString("title"));
					String link = URLDecoder.decode(jsonItem.optString("link"));

					String fcount = URLDecoder.decode(jsonItem
							.optString("fcount"));
					String date = URLDecoder.decode(jsonItem.optString("date"));
					String ccount = URLDecoder.decode(jsonItem
							.optString("ccount"));
					String dcount = URLDecoder.decode(jsonItem
							.optString("dcount"));
					String ucount = URLDecoder.decode(jsonItem
							.optString("ucount"));
					String thumbnail = URLDecoder.decode(jsonItem
							.optString("thumbnail"));

					isfav = jsonItem.optInt("isfav");
					flink = URLDecoder.decode(jsonItem.optString("flink"));
					rlink = URLDecoder.decode(jsonItem.optString("rlink"));
					clink = URLDecoder.decode(jsonItem.optString("clink"));
					Iteminfo item = new Iteminfo(title, date, thumbnail,
							ccount, dcount, ucount, fcount, link, flink, rlink,
							clink, isfav, -1, id, null);
					flag = false;
					List<Iteminfo> im;
					for (int j = 0; j <= CacheData.cateList.size() - 1; j++) {
						im = MainMenuActivity.li.get(j);
						str = CacheData.cateList.get(j).getLink();
						if (url.contains(str)) {
							// itemchoose1.add(item);
							im.add(item);
							yesnlink = 1;
						}
					}
				}
			}
			// else{
			// File picfile = new File(Appstart.jrcfile + "/"
			// + Adapterwithpic.getMd5Hash(url2));
			// String filename = picfile.getPath().toString();
			// if (picfile.exists()) {
			// String ss = CacheData.load(filename, this);
			// getLbjson(url2, ss);
			// }
			// }
			String ss = geturl(CacheData.cateList.get(0).getLink());
			for (int i = 0; i <= CacheData.cateList.size() - 1; i++) {
				str = CacheData.cateList.get(i).getLink();
				if (url.contains(str)) {
					Adapterwithpic ad;
					// if (twice == 0) {
					if (lvs.get(i).getAdapter() == null) {
						ad = new Adapterwithpic(JianrencunActivity.this,
								MainMenuActivity.li.get(i), number1,
								lvs.get(i), false);

						lvs.get(i).setAdapter(ad);
						adapters.set(i, ad);
						pbs.get(i).setVisibility(View.GONE);
						rllts.get(i).setVisibility(View.GONE);
						freshs.get(i).setVisibility(View.GONE);
					} else if (twice == 3) {
						lvs.get(i).requestLayout();
						// ad.notifyDataSetChanged(); // 数据集变化后,通知adapter
						// lvs.get(i).getAdapter().notify();
						loading.setVisibility(View.GONE);
					}
					// adapt = ad ;
				}
			}

			currentdate = pd;
			// th.interrupt();
			th = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			th = null;
			judgelink = url;
			if (url.contains("&start=")) {
				lsnextlink = url;
			} else {
				qjUrl.set(m, url);
			}
			freshs.get(m).setVisibility(View.VISIBLE);
			pbs.get(m).setVisibility(View.GONE);
			rllts.get(m).setVisibility(View.GONE);
			// lvs.get(m).removeFooterView(vFooter);
			e.printStackTrace();
			return;
		}
		if (!TextUtils.isEmpty(tip)) {
			Commond.showToast(this, tip);
		}
	}

	// /////////////////滑倒底部监听
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			if (view.getLastVisiblePosition() == view.getCount() - 1) {
				String url;
				twice = 3;
				yesnlink = 0;
				loading.setVisibility(View.VISIBLE);
				url = nextlinks.get(m);
                url = addver(url);
				try {
					if (TextUtils.isEmpty(url)) {
						if (lvs != null && lvs.get(m) != null
								&& vFooter != null) {
							lvs.get(m).removeFooterView(vFooter);
						} else {
							finish();
						}
					}
					if (!dllinks.contains(url)) {
						File picfile = new File(Appstart.jrcfile + "/"
								+ Adapterwithpic.getMd5Hash(url));
						String filename = picfile.getPath().toString();

						post(url);
						dllinks.add(url);
					}
				} catch (Exception e) {
					// TODO: handle exception
					Commond.showToast(JianrencunActivity.this, "等待超时！请重新进入！");
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
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		File picfile = new File(Appstart.jrcfile + "/" + "readlinks");
		String filename = picfile.getPath().toString();
		CacheData.save(filename, JianrencunActivity.number1.toString()
				.getBytes(), JianrencunActivity.this);
		number1.clear();
		indx = 0;
		if (BitmapCache.getIntance().bmpCacheMap != null
				&& BitmapCache.getIntance().bmpCacheMap.size() != 0) {
			BitmapCache.getIntance().clearCacheBitmap();
		}
		if (BitmapCache1.getIntance().bmpCacheMap != null
				&& BitmapCache1.getIntance().bmpCacheMap.size() != 0) {

			BitmapCache1.getIntance().clearCacheBitmap();
		}

		BitmapCacheBguanzhu.getIntance().clearCacheBitmap();
		BitmapCacheBlack.getIntance().clearCacheBitmap();
		BitmapCacheVillage.getIntance().clearCacheBitmap();
		MainMenuActivity.li.clear();
		// CacheData.cateList.clear();
		instance = null;
		yesorno = false;
		indx = 0;
		relativbar = null;
		mainbottom = null;
		morelv = null;
		titletar = null;
		pbmore = null;
		CacheData.cateList.clear();
		CacheData.detailcontent.clear();
		CacheData.sddetail = null;
		CacheData.detailgorys = null;
		CacheData.sdsc = null;
		CacheData.myscgorys = null;

		Details.filename = null;
		Shangchuan.mysclist.clear();
		Shangchuan.scnlink = null;
		Details.freshmysave = false;
		Shangchuan.sclbpd = "";
		
		 if(!bitmap.isRecycled() ){ 
			 bitmap.recycle()  ; //回收图片所占的内存 
	}
		super.onDestroy();
	};

	public void post(String urllink) {
		String result = "";
		String url = urllink;
		StringBuffer data = new StringBuffer();
		// mostnewlv.setAdapter(new Adapterwithpic(Mostnew.this, items));
		data.append("pkg=");
		data.append(URLEncoder.encode(getPackageName()));
		//
		data.append("&pd=");
		lastdate = currentdate;
		data.append(URLEncoder.encode(lastdate));

		data.append("&token=");
		data.append(URLEncoder.encode(su.getToken()));
		// 请求网络验证登陆
		HttpRequestTask request = new HttpRequestTask();
		request.execute(url, data.toString());

	}

	public String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
			return sdDir.toString();
		} else {
			return "/data/data/com.duom.jianrencun/files/";
		}

	}

	public String geturl(String s) {
		if (!s.contains("uid=")) {
			if (s.contains("?")) {
				s += "&uid=" + MainMenuActivity.uid;
			} else {
				s += "?uid=" + MainMenuActivity.uid;
			}
		}
		return s;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		// for (int i = 0; i < MainMenuActivity.li.size(); i++) {
		// if (lvs.get(m).getId() == i) {
		Intent it = new Intent();
		try {

			it.putExtra("content", MainMenuActivity.li.get(m).get(position)
					.getContent());
			it.putExtra("link", MainMenuActivity.li.get(m).get(position)
					.getLink());
			it.putExtra("date", MainMenuActivity.li.get(m).get(position)
					.getDate());
			it.putExtra("ccount", MainMenuActivity.li.get(m).get(position)
					.getCriticism());
			it.putExtra("clink", MainMenuActivity.li.get(m).get(position)
					.getClink());
			it.putExtra("rlink", MainMenuActivity.li.get(m).get(position)
					.getRlink());
			it.putExtra("flink", MainMenuActivity.li.get(m).get(position)
					.getFlink());
			it.putExtra("isfav", MainMenuActivity.li.get(m).get(position)
					.getIsfav());
			it.putExtra("header", MainMenuActivity.li.get(m).get(position)
					.getHeader());
			it.putExtra("id", MainMenuActivity.li.get(m).get(position).getId());
			it.putExtra("po", position);
			it.putExtra("which", m);
			Adapterwithpic.ViewHolder holder = (Adapterwithpic.ViewHolder) view
					.getTag();
			holder.content.setTextColor(Color.parseColor("#9d8f98"));
			holder.date.setTextColor(Color.parseColor("#b9b5b8"));
			it.setClass(JianrencunActivity.this, Details.class);
			startActivity(it);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Commond.showToast(JianrencunActivity.this, "登录超时！请重新登录！");
		}
		// break;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		for (int n = 0; n < freshs.size(); n++) {
			if (lvs.get(m).getId() == n) {
				// TODO Auto-generated method stub
				v.setVisibility(View.GONE);
				pbs.get(n).setVisibility(View.VISIBLE);
				rllts.get(n).setVisibility(View.VISIBLE);

				if (!judgelink.contains("&start=")) {
					post(qjUrl.get(m));
				} else {
					post(lsnextlink);
					lsnextlink = "";
				}
			}
		}
	}
   private String addver(String url){
		if(!url.contains("&ver=") && !url.contains("?ver=")){
			url += "&" + "ver" + "=" + MainMenuActivity.vername ;			
		}
		return url ;
   }
}