package com.app.chatroom.ui;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.SearchPeople;
import com.app.chatroom.adapter.VillageAdapter;
import com.app.chatroom.adapter.VillageCaifuAdapter;
import com.app.chatroom.adapter.VillageDayScoreAdapter;
import com.app.chatroom.adapter.VillagePeopleAdapter;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.http.HttpVillage;
import com.app.chatroom.json.MessageJson;
import com.app.chatroom.json.VillageJson;
import com.app.chatroom.json.VillageMsgJson;
import com.app.chatroom.json.VillagePeopleJson;
import com.app.chatroom.json.bean.MessageBean;
import com.app.chatroom.json.bean.VillageBean;
import com.app.chatroom.json.bean.VillageMsgBean;
import com.app.chatroom.json.bean.VillagePeopleBean;
import com.app.chatroom.otherui.SystemMsgWebDialog;
import com.app.chatroom.otherui.VillageUserInfoDialog;
import com.app.chatroom.pic.BitmapCacheVillage;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;
import com.app.chatroom.util.PhoneInfo;
import com.app.chatroom.view.NetImageView;
import com.duom.fjz.iteminfo.BitmapCache;
import com.duom.fjz.iteminfo.Tixing;
import com.jianrencun.android.Details;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Dzmysave;

public class VillageActivity extends HttpBaseActivitytwo {
	ImageButton villageBackBtn;

	private ViewPager mTabPager;
	private ImageView mTabImg, mTabImg2, mTabImg3 , mTabImg4;// 动画图片
	private ImageView mTab1, mTab2, mTab3 , mTab4;
	private int zero = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int one;// 单个水平动画位移
	public ProgressBar villagePeopleProgressBar;// 财富排行进度条
	public ProgressBar villageProgressBar;// 村委进度条
	public RelativeLayout villageprogerssBarRelativeLayout;
	public RelativeLayout villagePeopleProgerssBarRelativeLayout;
	public RelativeLayout villageScoreProgressBarRelativeLayout;
	public NetImageView villageLogoImageView;// 村委图片
	public TextView villageTitleTextView;// 村委名称
	public TextView villageMoneyTextView;// 村委金钱
	public TextView villageCountTextView;// 村委人数
	public TextView villageSystemTextView;// 村委公告
	public RelativeLayout villageTopRelative;//
	ListView rightListView , caifulv;
	ListView leftListView;
	ListView scoreListView;
	RelativeLayout village_left_relativelayout , caifurl;//
	public VillageAdapter villageAdapter;
	public VillagePeopleAdapter villagePeopleAdapter;
	public VillageDayScoreAdapter villageDayScoreAdapter;
	public VillageCaifuAdapter caifuadapter;
	public ArrayList<VillageBean> villageList = new ArrayList<VillageBean>();
	public ArrayList<MessageBean> msgList = new ArrayList<MessageBean>();
	public ArrayList<VillageMsgBean> villageMsgList = new ArrayList<VillageMsgBean>();
	VillageTopThread villageThread;
	/* 配置文件 */
	SharedPreferences sp;
	SystemSettingUtilSp su;
	// 是否允许多次加载标识
	boolean IsLoadPeople = true;
	boolean IsLoadData = true;
	boolean IsLoadScore = true;
	boolean IsLoadCaifu = true ;
	// 全新加载数据
	private View vFooter;
	private LinearLayout loading;
	boolean isPeopleFoot = false;
	int pagecount = 2;
	ArrayList<String> dlpl = new ArrayList<String>();
	public ArrayList<VillagePeopleBean> peoplelist = new ArrayList<VillagePeopleBean>();
	public ArrayList<VillagePeopleBean> peoplemorelist = new ArrayList<VillagePeopleBean>();
	public ArrayList<MessageBean> msgpeopleList = new ArrayList<MessageBean>();
	// ////////
	boolean isVillageFoot = false;
	public ArrayList<VillagePeopleBean> villagePeopleList = new ArrayList<VillagePeopleBean>();
	public ArrayList<VillagePeopleBean> villagePeopleMoreList = new ArrayList<VillagePeopleBean>();
	public ArrayList<MessageBean> msgvillagePeopleList = new ArrayList<MessageBean>();
	// /////
	boolean isVillageScoreFoot = false;
	public ArrayList<VillagePeopleBean> villageScoreList = new ArrayList<VillagePeopleBean>();
	public ArrayList<VillagePeopleBean> villageScoreMoreList = new ArrayList<VillagePeopleBean>();
	public ArrayList<MessageBean> msgvillageScoreList = new ArrayList<MessageBean>();
	//////
	public ArrayList<VillagePeopleBean> caifulist = new ArrayList<VillagePeopleBean>();
	public ArrayList<VillagePeopleBean> caifumorelist = new ArrayList<VillagePeopleBean>();
	public ArrayList<MessageBean> msgcaifuleList = new ArrayList<MessageBean>();
	private ImageButton ib;
	int anim_count = 0;
	private Details dt= new Details(); 
	// private ImageButton search ;
    private TextView tv ,tv2, tv_ck , tv_ck2 , tv3 , tv_ck3;
    private Button bnt , bnt2 ,bnt3;
    private View view , viewtwo , viewthree;
    private RelativeLayout rl1 , rl2 , rl3;
    Animation animation ;
    private int flg , sel , po1 , po2 , po3 ,where;
    private String tp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_village);
		
		Intent it = getIntent();
		flg = it.getIntExtra("flg", 0);
		sel = it.getIntExtra("sel", 0);
		where = it.getIntExtra("where", 0);
		tp = it.getStringExtra("tp");
		if(!TextUtils.isEmpty(tp)){
		Intent toit = new Intent() ;
		toit.setClass(this, Tixing.class);
		toit.putExtra("where", where);
		toit.putExtra("tp", tp);
		startActivity(toit);
		}
		
		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
	    animation = AnimationUtils.loadAnimation(this, R.anim.gradually);
		vFooter = LayoutInflater.from(this).inflate(R.layout.list_footer, null);
		loading = (LinearLayout) vFooter.findViewById(R.id.list_footer);
		loading.setVisibility(View.GONE);

		ib = (ImageButton) findViewById(R.id.village_right_btn);
//		ib.setVisibility(View.VISIBLE);\
		if(flg == 0){
			ib.setVisibility(View.GONE);
		}else if(flg == 1){
			ib.setVisibility(View.VISIBLE);
			yd_animhadler.sendEmptyMessageDelayed(1, 100);
		}
		
		ib.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String url = dt.appendNameValueint(ConstantsJrc.URLQD, "uid", Integer.parseInt(su.getUid()));
				url = dt.appendNameValue(url, "token", su.getToken());
				StringBuffer data = new StringBuffer();
				HttpRequestTask request = new HttpRequestTask();
				request.execute(url, data.toString());
			}
		});
			

		mTabPager = (ViewPager) findViewById(R.id.village_tabpager);
		mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());

		mTab1 = (ImageView) findViewById(R.id.village_farmer_radiobutton);
		mTab2 = (ImageView) findViewById(R.id.village_dayscore_radiobutton);
		mTab3 = (ImageView) findViewById(R.id.village_money_radiobutton);
		mTab4= (ImageView) findViewById(R.id.caifu_village_radiobutton);
		mTab1.setImageDrawable(getResources().getDrawable(R.drawable.village_farmerlist_btn_pressed));

		mTabImg = (ImageView) findViewById(R.id.img_tab_now);
		mTabImg2 = (ImageView) findViewById(R.id.img_tab_now2);
		mTabImg3 = (ImageView) findViewById(R.id.img_tab_now3);
		mTabImg4= (ImageView) findViewById(R.id.img_tab_now4);
		mTab1.setOnClickListener(new MyOnClickListener(0));
		mTab2.setOnClickListener(new MyOnClickListener(1));
		mTab3.setOnClickListener(new MyOnClickListener(2));
		mTab4.setOnClickListener(new MyOnClickListener(3));
		Display currDisplay = getWindowManager().getDefaultDisplay();// 获取屏幕当前分辨率
		int displayWidth = currDisplay.getWidth();

		one = (int) (displayWidth / 2); // 设置水平动画平移大小
		villageThread = new VillageTopThread();
		LayoutInflater mLi = LayoutInflater.from(this);
		View view1 = mLi.inflate(R.layout.village_tab_left, null);
		View view2 = mLi.inflate(R.layout.village_tab_dayscore, null);
		View view3 = mLi.inflate(R.layout.village_tab_right, null);
		View view4 = mLi.inflate(R.layout.village_tab_right, null);
		
		caifulv =  (ListView) view4
				.findViewById(R.id.village_right_listview);
		caifurl = (RelativeLayout) view4
				.findViewById(R.id.village_rightlist_progressbar_RelativeLayout);
		rightListView = (ListView) view3
				.findViewById(R.id.village_right_listview);
		scoreListView = (ListView) view2
				.findViewById(R.id.village_dayscore_listview);
		leftListView = (ListView) view1
				.findViewById(R.id.village_left_listview);
		village_left_relativelayout = (RelativeLayout) view1
				.findViewById(R.id.village_left_RelativeLayout);
		villagePeopleProgerssBarRelativeLayout = (RelativeLayout) view3
				.findViewById(R.id.village_rightlist_progressbar_RelativeLayout);
		villageScoreProgressBarRelativeLayout = (RelativeLayout) view2
				.findViewById(R.id.village_dayscore_progressbar_RelativeLayout);
		villageprogerssBarRelativeLayout = (RelativeLayout) view1
				.findViewById(R.id.village_leftlist_progressbar_RelativeLayout);

		LayoutInflater li = LayoutInflater.from(this);
		View headerview = li.inflate(R.layout.village_farmer_top_items, null);
		setListView(headerview);
		leftListView.addHeaderView(headerview, null, false);
		
		 view = new View(getApplicationContext());
		view = getLayoutInflater().inflate(R.layout.listheader_huodong, null);
		tv = (TextView) view.findViewById(R.id.listheader_tv);
		bnt = (Button) view.findViewById(R.id.listheader_chakanbnt);
		tv_ck = (TextView) view.findViewById(R.id.listheader_chakantv);
		rl1 = (RelativeLayout)view.findViewById(R.id.listheader_rl);
		
		viewtwo = new View(getApplicationContext());
		viewtwo = getLayoutInflater().inflate(R.layout.listheader_huodong, null);
		tv2 = (TextView) viewtwo.findViewById(R.id.listheader_tv);
		bnt2 = (Button) viewtwo.findViewById(R.id.listheader_chakanbnt);
		tv_ck2 = (TextView) viewtwo.findViewById(R.id.listheader_chakantv);
		rl2 = (RelativeLayout)viewtwo.findViewById(R.id.listheader_rl);
		
		viewthree = new View(getApplicationContext());
		viewthree = getLayoutInflater().inflate(R.layout.listheader_huodong, null);
		tv3 = (TextView) viewthree.findViewById(R.id.listheader_tv);
		bnt3 = (Button) viewthree.findViewById(R.id.listheader_chakanbnt);
		tv_ck3 = (TextView) viewthree.findViewById(R.id.listheader_chakantv);
		rl3 = (RelativeLayout)viewthree.findViewById(R.id.listheader_rl);
		
		rl1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(getApplicationContext(),
						SystemMsgWebDialog.class);
				it.putExtra(
						"link",
						ConstantsJrc.MAINMORE
								+ "?uid="
								+ su.getUid()
								+ "&flg=20&w="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getWidth(VillageActivity.this)
								+ "&pkg="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getPackage(VillageActivity.this)
								+ "&ver="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getVersionName(VillageActivity.this)
								+ "&token=" + su.getToken());
				it.putExtra("type", String.valueOf(20));
				startActivity(it);								
			}
		});
		tv_ck.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(getApplicationContext(),
						SystemMsgWebDialog.class);
				it.putExtra(
						"link",
						ConstantsJrc.MAINMORE
								+ "?uid="
								+ su.getUid()
								+ "&flg=20&w="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getWidth(VillageActivity.this)
								+ "&pkg="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getPackage(VillageActivity.this)
								+ "&ver="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getVersionName(VillageActivity.this)
								+ "&token=" + su.getToken());
				it.putExtra("type", String.valueOf(20));
				startActivity(it);
			}
		});
		
		bnt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				view.startAnimation(animation);
				view.setVisibility(View.GONE);
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						if(view.getVisibility() == View.GONE){
							scoreListView.removeHeaderView(view);
							po1 = 0 ;
						}						
					}
				}, 1000);						
			}
		});
		
		tv_ck2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(getApplicationContext(),
						SystemMsgWebDialog.class);
				it.putExtra(
						"link",
						ConstantsJrc.MAINMORE
								+ "?uid="
								+ su.getUid()
								+ "&flg=21&w="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getWidth(VillageActivity.this)
								+ "&pkg="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getPackage(VillageActivity.this)
								+ "&ver="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getVersionName(VillageActivity.this)
								+ "&token=" + su.getToken());
				it.putExtra("type", String.valueOf(21));
				startActivity(it);
			}
		});
		rl2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(getApplicationContext(),
						SystemMsgWebDialog.class);
				it.putExtra(
						"link",
						ConstantsJrc.MAINMORE
								+ "?uid="
								+ su.getUid()
								+ "&flg=21&w="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getWidth(VillageActivity.this)
								+ "&pkg="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getPackage(VillageActivity.this)
								+ "&ver="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getVersionName(VillageActivity.this)
								+ "&token=" + su.getToken());
				it.putExtra("type", String.valueOf(21));
				startActivity(it);
			}
		});
		
		bnt2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewtwo.startAnimation(animation);
				viewtwo.setVisibility(View.GONE);
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						if(viewtwo.getVisibility() == View.GONE){
							rightListView.removeHeaderView(viewtwo);
							po2 = 0 ;
						}						
					}
				}, 1000);						
			}
		});
		tv_ck3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(getApplicationContext(),
						SystemMsgWebDialog.class);
				it.putExtra(
						"link",
						ConstantsJrc.MAINMORE
								+ "?uid="
								+ su.getUid()
								+ "&flg=23&w="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getWidth(VillageActivity.this)
								+ "&pkg="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getPackage(VillageActivity.this)
								+ "&ver="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getVersionName(VillageActivity.this)
								+ "&token=" + su.getToken());
				it.putExtra("type", String.valueOf(21));
				startActivity(it);
			}
		});
		rl3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(getApplicationContext(),
						SystemMsgWebDialog.class);
				it.putExtra(
						"link",
						ConstantsJrc.MAINMORE
								+ "?uid="
								+ su.getUid()
								+ "&flg=23&w="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getWidth(VillageActivity.this)
								+ "&pkg="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getPackage(VillageActivity.this)
								+ "&ver="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getVersionName(VillageActivity.this)
								+ "&token=" + su.getToken());
				it.putExtra("type", String.valueOf(21));
				startActivity(it);
			}
		});
		
		bnt3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewthree.startAnimation(animation);
				viewthree.setVisibility(View.GONE);
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						if(viewthree.getVisibility() == View.GONE){
							caifulv.removeHeaderView(viewthree);
							po3 = 0 ;
						}						
					}
				}, 1000);						
			}
		});

		// 禁止多次加载
		// if (IsLoadData) {
		// villagePeopleList.clear();
		// loading.setVisibility(View.GONE);
		// String url = ConstantsJrc.VILLAGEURL + "?" + "uid=" + su.getUid()
		// + "&pd=";
		// if (!dlpl.contains(url)) {
		// StringBuffer data = new StringBuffer();
		// // 请求网络验证登陆
		// HttpRequestTask request = new HttpRequestTask();
		// request.execute(url, data.toString());
		// dlpl.add(url);
		// }
		// }
		// 每个页面的view数据
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);
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

			// @Override
			// public CharSequence getPageTitle(int position) {
			// return titles.get(position);
			// }

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(views.get(position));
				return views.get(position);
			}
		};
		mTabPager.setAdapter(mPagerAdapter);
		mTabPager.setCurrentItem(sel, false);

		villageBackBtn = (ImageButton) findViewById(R.id.village_left_btn);
		villageBackBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		// search =(ImageButton) findViewById(R.id.village_search);
		// search.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// Intent it = new Intent();
		// it.setClass(VillageActivity.this, SearchPeople.class);
		// startActivity(it);
		// }
		// });

		leftListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position > 0) {
					Intent intent = new Intent(getApplicationContext(),
							VillageUserInfoDialog.class);
					String uid = String
							.valueOf(villageAdapter.villagePeopleList.get(
									position - 1).getUid());
					String nick = villageAdapter.villagePeopleList.get(
							position - 1).getNick();
					// Log.i("ttt", uid + "," + nick);
					intent.putExtra("uid", uid);
					intent.putExtra("nick", nick);
					intent.putExtra("fuid", su.getUid());
					intent.putExtra("type", 2);
					intent.putExtra("src", 1);
					startActivity(intent);
				}
			}
		});

		rightListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position < villagePeopleAdapter.list.size()+po2) {
					Intent intent = new Intent(getApplicationContext(),
							VillageUserInfoDialog.class);
					String uid = String.valueOf(villagePeopleAdapter.list.get(
							position-po2).getUid());
					String nick = villagePeopleAdapter.list.get(position-po2)
							.getNick();
					// Log.i("ttt", uid + "," + nick);
					intent.putExtra("uid", uid);
					intent.putExtra("nick", nick);
					intent.putExtra("fuid", su.getUid());
					intent.putExtra("type", 2);
					startActivity(intent);
				}
			}
		});
		caifulv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position < caifuadapter.list.size()+po3) {
					Intent intent = new Intent(getApplicationContext(),
							VillageUserInfoDialog.class);
					String uid = String.valueOf(caifuadapter.list.get(
							position-po3).getUid());
					String nick = caifuadapter.list.get(position-po3)
							.getNick();
					// Log.i("ttt", uid + "," + nick);
					intent.putExtra("uid", uid);
					intent.putExtra("nick", nick);
					intent.putExtra("fuid", su.getUid());
					intent.putExtra("type", 2);
					startActivity(intent);
				}
			}
		});

		scoreListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position < villageDayScoreAdapter.list.size()+po1) {
					Intent intent = new Intent(getApplicationContext(),
							VillageUserInfoDialog.class);
					String uid = String.valueOf(villageDayScoreAdapter.list
							.get(position - po1).getUid());
					String nick = villageDayScoreAdapter.list.get(position - po1)
							.getNick();
					intent.putExtra("uid", uid);
					intent.putExtra("nick", nick);
					intent.putExtra("fuid", su.getUid());
					intent.putExtra("type", 2);
					startActivity(intent);
				}
			}
		});

	}

	// 公告点击
	OnClickListener listen = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			VillageMsgBean villageMsgBean = (VillageMsgBean) v.getTag();
			if (!villageMsgBean.getLink().toString().equals("")) {
				Intent intent = new Intent(getApplicationContext(),
						SystemMsgWebDialog.class);
				intent.putExtra("link", villageMsgBean.getLink());
				startActivity(intent);
			}
		}
	};

	private void setListView(View view) {
		villageTopRelative = (RelativeLayout) view
				.findViewById(R.id.village_top_relative);
		villageLogoImageView = (NetImageView) view
				.findViewById(R.id.village_logo);
		villageTitleTextView = (TextView) view
				.findViewById(R.id.village_top_name_textView);
		villageMoneyTextView = (TextView) view
				.findViewById(R.id.village_score_textView);
		villageCountTextView = (TextView) view
				.findViewById(R.id.village_totalpeople_textView);
		villageSystemTextView = (TextView) view
				.findViewById(R.id.village_system_textView);
		villageThread.start();
		villageSystemTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!"".equals(villageMsgList.get(0).getLink().toString())) {
					Intent intent = new Intent(getApplicationContext(),
							SystemMsgWebDialog.class);
					intent.putExtra("link", villageMsgList.get(0).getLink());
					intent.putExtra("type", "99");
					startActivity(intent);
				}
			}
		});
	}

	class VillageTopThread extends Thread {
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
				// dialog.setCancelable(true);
				// dialog.show();
				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				// dialog.cancel();
				villageThread.stopThread(false);
				villageAdapter = new VillageAdapter(VillageActivity.this,
						villagePeopleList, leftListView, listen);
				leftListView.setAdapter(villageAdapter);
				leftListView.requestLayout();
				villageprogerssBarRelativeLayout.setVisibility(View.GONE);
				leftListView.setVisibility(View.VISIBLE);
				break;
			}
		};
	};

	private void login() throws ClientProtocolException, IOException {
		handler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);

		final String result = HttpVillage.VillageGet(su.getUid(), "",
				su.getToken());
		// System.out.println("村委头：" + result);
		VillageJson villageJson = new VillageJson();
		VillageMsgJson villageMsgJson = new VillageMsgJson();
		VillagePeopleJson vilalgePeopleJson = new VillagePeopleJson();
		MessageJson mj = new MessageJson();
		if (!"".equals(result)) {

			msgList = mj.parseJsonPd(result);
			villageMsgList = villageMsgJson.parseJson(result);
			villageList = villageJson.parseJson(result);
			villagePeopleList = vilalgePeopleJson.parseJson(result);

			handler.post(new Runnable() {

				@Override
				public void run() {
					try {
						if (villageList.get(0).getRet() == 1) {
							//
							villageLogoImageView.setImageUrl(villageList.get(0)
									.getLogo(), R.drawable.photo,
									Environment.getExternalStorageDirectory()
											+ File.separator + getPackageName()
											+ ConstantsJrc.PHOTO_PATH,
									ConstantsJrc.PROJECT_PATH
											+ getPackageName()
											+ ConstantsJrc.PHOTO_PATH);
							villageTitleTextView.setText(villageList.get(0)
									.getTitle());
							villageMoneyTextView.setText(villageList.get(0)
									.getMoney());
							villageCountTextView.setText(String
									.valueOf(villageList.get(0).getCount()));
							if (!villageMsgList.get(0).getContent().toString()
									.equals("")) {
								villageSystemTextView
										.setVisibility(View.VISIBLE);
								villageSystemTextView.setText(villageMsgList
										.get(0).getContent());
							}

							// villageTopRelative.setVisibility(View.VISIBLE);
							handler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
						}
					} catch (Exception e) {

					}
				}
			});
		}
	}

	/**
	 * 数据加载Handler
	 */
	public Handler villagePeopleHandler = new Handler() {

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int num = msg.what;
			if (num == ConstantsJrc.HANDLER_SHOW_PROGRESS) {
				// villagePeopleProgressBar.setVisibility(View.VISIBLE);
				villagePeopleProgerssBarRelativeLayout
						.setVisibility(View.VISIBLE);
			} else if (num == ConstantsJrc.HANDLER_CANCEL_PROGRESS) {
				// villagePeopleProgressBar.setVisibility(View.GONE);
				villagePeopleProgerssBarRelativeLayout.setVisibility(View.GONE);
			} else if (num == ConstantsJrc.HANDLER_ADAPTER_REFRESH) {
				// rightListView.requestLayout();
				// villagePeopleAdapter.notifyDataSetChanged();
			}
		};
	};

	/**
	 * 数据加载Handler
	 */
	public Handler villageHandler = new Handler() {

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int num = msg.what;
			if (num == ConstantsJrc.HANDLER_SHOW_PROGRESS) {
				// villageProgressBar.setVisibility(View.VISIBLE);
				villageprogerssBarRelativeLayout.setVisibility(View.VISIBLE);
			} else if (num == ConstantsJrc.HANDLER_CANCEL_PROGRESS) {
				// villageProgressBar.setVisibility(View.GONE);
				villageprogerssBarRelativeLayout.setVisibility(View.GONE);
			} else if (num == ConstantsJrc.HANDLER_ADAPTER_REFRESH) {
				// leftListView.requestLayout();
				// villageAdapter.notifyDataSetChanged();
			}
		};
	};

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

	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				mTab1.setImageDrawable(getResources().getDrawable(
						R.drawable.village_farmerlist_btn_pressed));
				mTabImg.setVisibility(View.VISIBLE);
				mTabImg2.setVisibility(View.GONE);
				mTabImg3.setVisibility(View.GONE);
				mTabImg4.setVisibility(View.GONE);
				mTab2.setImageDrawable(getResources().getDrawable(
						R.drawable.village_dayscore_btn_normal));
				mTab3.setImageDrawable(getResources().getDrawable(
						R.drawable.village_moneylist_btn_normal));
				mTab4.setImageDrawable(getResources().getDrawable(
						R.drawable.caizhubang));
				// 禁止多次加载
				// if (IsLoadData) {
				// villagePeopleList.clear();
				// loading.setVisibility(View.GONE);
				// String url = ConstantsJrc.VILLAGEURL + "?" + "uid="
				// + su.getUid() + "&pd=";
				// if (!dlpl.contains(url)) {
				// StringBuffer data = new StringBuffer();
				// // 请求网络验证登陆
				// HttpRequestTask request = new HttpRequestTask();
				// request.execute(url, data.toString());
				// dlpl.add(url);
				// }
				// }
				break;
			case 1:
				mTab2.setImageDrawable(getResources().getDrawable(
						R.drawable.village_dayscore_btn_pressed));
				mTabImg.setVisibility(View.GONE);
				mTabImg2.setVisibility(View.VISIBLE);
				mTabImg3.setVisibility(View.GONE);
				mTabImg4.setVisibility(View.GONE);
				mTab1.setImageDrawable(getResources().getDrawable(
						R.drawable.village_farmerlist_btn_normal));
				mTab3.setImageDrawable(getResources().getDrawable(
						R.drawable.village_moneylist_btn_normal));
				mTab4.setImageDrawable(getResources().getDrawable(
						R.drawable.caizhubang));
				// 禁止多次加载
				if (IsLoadScore) {
					villageScoreList.clear();
					loading.setVisibility(View.GONE);
					String url = ConstantsJrc.VILLAGESCORE + "?" + "uid="
							+ su.getUid() + "&pd=";
					if (!dlpl.contains(url)) {
						StringBuffer data = new StringBuffer();
						// 请求网络验证登陆
						HttpRequestTask request = new HttpRequestTask();
						request.execute(url, data.toString());
						dlpl.add(url);
					}
				}
				break;
			case 2:
				mTab3.setImageDrawable(getResources().getDrawable(
						R.drawable.village_moneylist_btn_pressed));
				mTabImg.setVisibility(View.GONE);
				mTabImg2.setVisibility(View.GONE);
				mTabImg3.setVisibility(View.VISIBLE);
				mTabImg4.setVisibility(View.GONE);
				mTab1.setImageDrawable(getResources().getDrawable(
						R.drawable.village_farmerlist_btn_normal));
				mTab2.setImageDrawable(getResources().getDrawable(
						R.drawable.village_dayscore_btn_normal));
				mTab4.setImageDrawable(getResources().getDrawable(
						R.drawable.caizhubang));
				// 禁止多次加载
				if (IsLoadPeople) {
					peoplelist.clear();
					loading.setVisibility(View.GONE);
					String url = ConstantsJrc.VILLAGEPEOPLEURL + "?" + "uid="
							+ su.getUid() + "&pd="+ "&token="
									+ su.getToken();
					if (!dlpl.contains(url)) {
						StringBuffer data = new StringBuffer();
						// 请求网络验证登陆
						HttpRequestTask request = new HttpRequestTask();
						request.execute(url, data.toString());
						dlpl.add(url);
					}
				}

				break;
			case 3:
				mTab3.setImageDrawable(getResources().getDrawable(
						R.drawable.village_moneylist_btn_normal));
				mTabImg.setVisibility(View.GONE);
				mTabImg2.setVisibility(View.GONE);
				mTabImg3.setVisibility(View.GONE);
				mTabImg4.setVisibility(View.VISIBLE);
				mTab1.setImageDrawable(getResources().getDrawable(
						R.drawable.village_farmerlist_btn_normal));
				mTab2.setImageDrawable(getResources().getDrawable(
						R.drawable.village_dayscore_btn_normal));
				mTab4.setImageDrawable(getResources().getDrawable(
						R.drawable.caizhubangxuanzhong));
				// 禁止多次加载
				if (IsLoadCaifu) {
					caifulist.clear();
					loading.setVisibility(View.GONE);
					String url = ConstantsJrc.VILLAGECAIFUURL + "?" + "uid="
							+ su.getUid() + "&pd="+ "&token="
									+ su.getToken();
					if (!dlpl.contains(url)) {
						StringBuffer data = new StringBuffer();
						// 请求网络验证登陆
						HttpRequestTask request = new HttpRequestTask();
						request.execute(url, data.toString());
						dlpl.add(url);
					}
				}

				break;
			}

			currIndex = arg0;
			// animation.setFillAfter(true);// True:图片停在动画结束位置
			// animation.setDuration(150);
			// mTabImg.startAnimation(animation);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		villageThread.stopThread(false);
		BitmapCacheVillage.getIntance().clearCacheBitmap();
	}

	@Override
	public void resultData(String url, String result) {
		// TODO Auto-generated method stub
		try {
			if (url.contains(ConstantsJrc.VILLAGEURL + "?" + "uid=" + su.getUid()
					+ "&pd=")) {
				VillagePeopleJson vilalgePeopleJson = new VillagePeopleJson();
				villagePeopleMoreList = vilalgePeopleJson.parseJson(result);
				MessageJson mj = new MessageJson();
				msgvillagePeopleList = mj.parseJsonPd(result);
				if (msgvillagePeopleList.get(0).getPd() != null)
					su.setVillagePd(msgvillagePeopleList.get(0).getPd());
				if (villagePeopleMoreList.size() == 0) {
					leftListView.removeFooterView(vFooter);
				}
				for (VillagePeopleBean v : villagePeopleMoreList) {
					villagePeopleList.add(v);
				}
				villagePeopleMoreList.clear();
				if (isVillageFoot == false) {
					villageAdapter = new VillageAdapter(VillageActivity.this,
							villagePeopleList, leftListView, listen);
					leftListView.setAdapter(villageAdapter);
					villageprogerssBarRelativeLayout.setVisibility(View.GONE);
				} else {
					leftListView.requestLayout();
					villageAdapter.notifyDataSetChanged();
					villageprogerssBarRelativeLayout.setVisibility(View.GONE);
				}
				IsLoadData = false;
			}
			if (url.contains(ConstantsJrc.VILLAGESCORE + "?" + "uid="
					+ su.getUid() + "&pd=")) {
				VillagePeopleJson villagePeopleJson = new VillagePeopleJson();
				villageScoreMoreList = villagePeopleJson.parseJson(result);
				MessageJson mj = new MessageJson();
				msgvillageScoreList = mj.parseJson(result);
				if (msgvillageScoreList.get(0).getPd() != null) {
					su.setVillageScorePd(msgvillageScoreList.get(0).getPd());
				}
				if (villageScoreMoreList.size() == 0) {
					scoreListView.removeFooterView(vFooter);
				}
				for (VillagePeopleBean v : villageScoreMoreList) {
					villageScoreList.add(v);
				}
				if(!TextUtils.isEmpty(msgvillageScoreList.get(0).getMsg())){
					scoreListView.addHeaderView(view);
					tv.setText(msgvillageScoreList.get(0).getMsg());
					po1 = 1 ;
				}
				villageScoreMoreList.clear();
				if (isPeopleFoot == false) {
					villageDayScoreAdapter = new VillageDayScoreAdapter(
							VillageActivity.this, villageScoreList,
							scoreListView);
					scoreListView.setAdapter(villageDayScoreAdapter);
					villageScoreProgressBarRelativeLayout
							.setVisibility(View.GONE);
				} else {
					scoreListView.requestLayout();
					villageDayScoreAdapter.notifyDataSetChanged();
					villageScoreProgressBarRelativeLayout
							.setVisibility(View.GONE);
				}
				IsLoadScore = false;
			}
			if (url.contains(ConstantsJrc.VILLAGEPEOPLEURL + "?" + "uid="
					+ su.getUid() + "&pd=")) {
				VillagePeopleJson villagePeopleJson = new VillagePeopleJson();
				peoplemorelist = villagePeopleJson.parseJson(result);
				MessageJson mj = new MessageJson();
				msgpeopleList = mj.parseJsonPd(result);
				
				if(!TextUtils.isEmpty(msgpeopleList.get(0).getMsg())){
					rightListView.addHeaderView(viewtwo);
					tv2.setText(msgpeopleList.get(0).getMsg());	
					po2 = 1 ;
				}
				
				if (msgpeopleList.get(0).getPd() != null)
					su.setVillagePeoplePd(msgpeopleList.get(0).getPd());
				if (peoplemorelist.size() == 0) {
					rightListView.removeFooterView(vFooter);
				}
				for (VillagePeopleBean v : peoplemorelist) {
					peoplelist.add(v);
				}
				peoplemorelist.clear();
				if (isPeopleFoot == false) {
					villagePeopleAdapter = new VillagePeopleAdapter(
							VillageActivity.this, peoplelist, rightListView);
					rightListView.setAdapter(villagePeopleAdapter);
					villagePeopleProgerssBarRelativeLayout
							.setVisibility(View.GONE);
				} else {
					rightListView.requestLayout();
					villagePeopleAdapter.notifyDataSetChanged();
					villagePeopleProgerssBarRelativeLayout
							.setVisibility(View.GONE);
				}
				
				IsLoadPeople = false;
			}
			
			if (url.contains(ConstantsJrc.VILLAGECAIFUURL + "?" + "uid="
					+ su.getUid() + "&pd=")) {
				VillagePeopleJson villagePeopleJson = new VillagePeopleJson();
				caifumorelist = villagePeopleJson.parseJson(result);
				MessageJson mj = new MessageJson();
				msgcaifuleList = mj.parseJsonPd(result);
				
				if(!TextUtils.isEmpty(msgcaifuleList.get(0).getMsg())){
					caifulv.addHeaderView(viewthree);
					tv3.setText(msgcaifuleList.get(0).getMsg());	
					po3 = 1 ;
				}
				if (msgcaifuleList.get(0).getPd() != null)
					su.setVillagePeoplePd(msgcaifuleList.get(0).getPd());
				if (caifumorelist.size() == 0) {
					rightListView.removeFooterView(vFooter);
				}
				for (VillagePeopleBean v : caifumorelist) {
					caifulist.add(v);
				}
				caifumorelist.clear();
				if (isPeopleFoot == false) {
					caifuadapter = new VillageCaifuAdapter(
							VillageActivity.this, caifulist, caifulv);
					caifulv.setAdapter(caifuadapter);
					caifurl
							.setVisibility(View.GONE);
				} else {
					caifulv.requestLayout();
					caifuadapter.notifyDataSetChanged();
					caifurl
							.setVisibility(View.GONE);
				}
				IsLoadCaifu = false;
			}
			if(url.contains(ConstantsJrc.URLQD)){
				String tip = null;
				if (result == null) {
					// fresh.setVisibility(View.VISIBLE);
					Commond.showToast(VillageActivity.this, "小贱提醒 ：当前网络不稳定！");
					return;
				}
				JSONObject jsonChannel = new JSONObject(result);
				int ret = jsonChannel.optInt("ret");
				tip = URLDecoder.decode(jsonChannel.optString("tip"));
				if(!TextUtils.isEmpty(tip)){
				Commond.showToast(VillageActivity.this, tip);
				}
				if (ret == 1) {					
					anim_count = 1 ;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	Handler yd_animhadler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				if(anim_count != 0){
					yd_xiaoshi.sendEmptyMessageDelayed(1, 100);
				}else{
				ib.setBackgroundResource(R.drawable.jinbi1);
				yd_animhadler.sendEmptyMessageDelayed(2, 100);
				}
			} else if (msg.what == 2) {
				if(anim_count != 0){
					yd_xiaoshi.sendEmptyMessageDelayed(1, 100);
				}else{
				ib.setBackgroundResource(R.drawable.jinbi2);
				yd_animhadler.sendEmptyMessageDelayed(3, 100);
				}
			} else if (msg.what == 3) {
				if(anim_count != 0){
					yd_xiaoshi.sendEmptyMessageDelayed(1, 100);
				}else{
				ib.setBackgroundResource(R.drawable.jinbi3);
				yd_animhadler.sendEmptyMessageDelayed(4, 100);
				}
			} else if (msg.what == 4) {
				if(anim_count != 0){
					yd_xiaoshi.sendEmptyMessageDelayed(1, 100);
				}else{
				ib.setBackgroundResource(R.drawable.jinbi4);
				yd_animhadler.sendEmptyMessageDelayed(5, 100);
				}
			} else if (msg.what == 5) {
				if(anim_count != 0){
					yd_xiaoshi.sendEmptyMessageDelayed(1, 100);
				}else{
				ib.setBackgroundResource(R.drawable.jinbi5);
				yd_animhadler.sendEmptyMessageDelayed(6, 100);
				}
			} else if (msg.what == 6) {
				if(anim_count != 0){
					yd_xiaoshi.sendEmptyMessageDelayed(1, 100);
				}else{
				ib.setBackgroundResource(R.drawable.jinbi6);
				yd_animhadler.sendEmptyMessageDelayed(7, 100);
				}
			} else if (msg.what == 7) {
				if(anim_count != 0){
					yd_xiaoshi.sendEmptyMessageDelayed(1, 100);
				}else{
				ib.setBackgroundResource(R.drawable.jinbi7);
				yd_animhadler.sendEmptyMessageDelayed(8, 100);
				}
			} else if (msg.what == 8) {
				if(anim_count != 0){
					yd_xiaoshi.sendEmptyMessageDelayed(1, 100);
				}else{
				ib.setBackgroundResource(R.drawable.jinbi8);
				yd_animhadler.sendEmptyMessageDelayed(1, 100);
				}
			}
		};
	};

	Handler yd_xiaoshi = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				ib.setBackgroundResource(R.drawable.xiaoshi1);
				yd_xiaoshi.sendEmptyMessageDelayed(2, 100);
			} else if (msg.what == 2) {
				ib.setBackgroundResource(R.drawable.xiaoshi2);
				yd_xiaoshi.sendEmptyMessageDelayed(3, 100);
			} else if (msg.what == 3) {
				ib.setBackgroundResource(R.drawable.xiaoshi3);
				yd_xiaoshi.sendEmptyMessageDelayed(4, 100);
			} else if (msg.what == 4) {
				ib.setBackgroundResource(R.drawable.xiaoshi4);
				yd_xiaoshi.sendEmptyMessageDelayed(5, 100);
			} else if (msg.what == 5) {
				ib.setBackgroundResource(R.drawable.xiaoshi5);
				yd_xiaoshi.sendEmptyMessageDelayed(6, 100);
			} else if (msg.what == 6) {
				ib.setBackgroundResource(R.drawable.xiaoshi6);
				ib.setVisibility(View.GONE);
			} 
		};
	};
}
