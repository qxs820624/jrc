package com.jianrencun.dazui;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.EditPage;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.app.chatroom.Appstart;
import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.broadcast.HeadsetPlugReceiver;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.otherui.VillageUserInfoDialog;
import com.app.chatroom.pic.BitmapCacheBguanzhu;
import com.app.chatroom.pic.BitmapCacheBlack;
import com.app.chatroom.pic.BitmapCacheVillage;
import com.app.chatroom.util.Commond;
import com.duom.fjz.iteminfo.Adapterwithpic;
import com.duom.fjz.iteminfo.BitmapCache1;
import com.duom.fjz.iteminfo.BitmapCacheDzlb;
import com.duom.fjz.iteminfo.BitmapCacheDzpl;
import com.duom.fjz.iteminfo.Tixing;
import com.jianrencun.android.Details;
import com.jianrencun.android.Details.LoadImageRunnable;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.HttpBaseActivity.HttpRequestTask;

public class DazuiActivity extends HttpBaseActivity implements
		OnScrollListener {

	private Button dazuifresh, dazuifresh2, dazuifresh3, dz_sy_zhutis;
	private Button dazuione, dazuitwo, dazuithree,dazuifour,dazuifive;
	private ViewPager mTabPager;
	private ProgressBar pb;
	ArrayList<View> views;
	private View vFooter;
	private final int message_what_position = 101;
	private LinearLayout loading;
	private boolean istwice = false, istwice2 = false , istwice3 = false , istwice4 = false ;
	private ListView dazuinewlv, dazuijclv, dazuiphblv, lslv;

	private String nLink, jingcainLink, phbnLink;
	private List<String> dllinks;
	private boolean thswitch = true;

	public static MediaPlayer player;
	private String currentdate = "", lastdate;
	public static List<DazuiIteminfo> dazuilst, jingcailist, phblist, lslist;
	OnClickListener listener, listener_osc, listener_liwu, listener_save,
			listener_share;
	private int twice, twice1, twice2 , twice3 ,twice4;
	String nlink;
	private int whichpage = 1;
	private String fileurl;
	private List<String> lsfileurl;
	private List<Boolean> bls = null;
	private List<ProgressBar> pbs = null;
	private int position, bfposition;
	private View view1, view2, view3 , view4 ,view5;
	private LinearLayout ll0, ll1, ll2 , ll3, ll4;
	private ProgressBar pb_zuixin, pb_jingcai, pb_phb;
	private String lasturl;
	private DazuiAdapter2.ViewHolder holder;
	private List<DazuiAdapter2> adapters;
	private List<Integer> po, po1, po2, lspo;
	private List<String> bofangurl, bofangurl1, bofangurl2, lsbofangurl;
	private boolean num;
	int ik = 0;
	int ceshi = 0;
	int lastceshi = 0;
	boolean issing;
	int fistpage = 1;
	private AudioManager audio;
	private boolean isthisactivity = true;
	private Thread th;
	HeadsetPlugReceiver headsetPlugReceiver;// 耳机拔插监听广播
	int v1, v2;
	public static int lenth;

	private WebView mWebView;
	public static boolean ispause = false;

	private static final int MENU_ITEM_COUNTER = Menu.FIRST;
	
	
	///////////
	private ListView lv1, lv2, lv3;
	private ProgressBar pb_1, pb_2, pb_3;
	private List<ZhutisIteminfo> ztlist, phlist, drlist;
	private String ztlink, phlink, drlink;

	private int sel ;
	private ArrayList<String> dztabname = new ArrayList<String>();
	// boolean isnum1 = true;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dazuiactivity);
		// registerHeadsetPlugReceiver();
			
		init();
		
		Intent it = getIntent();
		sel = it.getIntExtra("sel", 0);
		dztabname = it.getStringArrayListExtra("dztabname");	
		int where = it.getIntExtra("where", 0);
		String tp = it.getStringExtra("tp");
		if(!TextUtils.isEmpty(tp)){
		Intent toit = new Intent() ;
		toit.setClass(this, Tixing.class);
		toit.putExtra("where", where);
		toit.putExtra("tp", tp);
		startActivity(toit);
		}
		
        if(dztabname != null && dztabname.size()>0){
		dazuione.setText(dztabname.get(0));
		dazuitwo.setText(dztabname.get(1));
		dazuithree.setText(dztabname.get(2));
		dazuifour.setText(dztabname.get(3));
		dazuifive.setText(dztabname.get(4));
        }
        lastceshi = sel;
        ceshi = sel;
		registerHeadsetPlugReceiver();
		
		lv1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(DazuiActivity.this, Dzmysave.class);
				it.putExtra("osc", ztlist.get(arg2).getLink());
				startActivity(it);
			}
		});
		lv2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(DazuiActivity.this, Dzmysave.class);
				it.putExtra("osc", phlist.get(arg2).getLink());
				it.putExtra("which", 1);
				startActivity(it);
			}
		});
		lv3.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(DazuiActivity.this, Dzmysave.class);
				it.putExtra("osc", drlist.get(arg2).getLink());
				it.putExtra("which", 2);
				startActivity(it);
			}
		});
		// //////////////////////////////////////////////////
		/*
		 * mWebView = (WebView) findViewById(R.id.ceshiwv);
		 * mWebView.getSettings().setJavaScriptEnabled(true);
		 * mWebView.getSettings().setDefaultTextEncodingName("utf-8");
		 * mWebView.setBackgroundColor(0); // 设置背景色 //
		 * mWebView.getBackground().setAlpha(0); // 设置填充透明度 范围：0-255
		 * mWebView.loadDataWithBaseURL(null, "加载中。。", "text/html", "utf-8",
		 * null); //
		 * mWebView.loadDataWithBaseURL(mGetDetail.data.get("hostsUrl"), //
		 * mGetDetail.data.get("description"), "text/html", "utf-8", null);
		 * mWebView.setVisibility(View.VISIBLE); // 加载完之后进行设置显示，以免加载时初始化效果不好看
		 */
		// //////////////////////////////////////////////////////////
		// if (audio.isWiredHeadsetOn() == true) {
		// AudioManager audioManager = (AudioManager)
		// getSystemService(Context.AUDIO_SERVICE);
		// audioManager.setMode(AudioManager.MODE_IN_CALL);// 把模式调成听筒放音模式
		// audioManager.setSpeakerphoneOn(false);
		//
		// } else if (audio.isWiredHeadsetOn() == false) {
		//
		// AudioManager audioManager = (AudioManager)
		// getSystemService(Context.AUDIO_SERVICE);
		// audioManager.setMode(AudioManager.ROUTE_SPEAKER);// 把模式调成听筒放音模式
		// audioManager.setSpeakerphoneOn(true);
		// }

		// 每个页面的view数据
		views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);
		views.add(view5);
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
		// 顶
		listener_osc = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				stopPlay();
				if (dazuilst.size() != 0 || jingcailist.size() != 0
						|| phblist.size() != 0) {
					if (lastceshi == 0) {
						dazuilst.get(bfposition).setStartorstop(0);
						dazuilst.get(bfposition).setJindubg(0);
						adapters.get(0).notifyDataSetChanged();
					} else if (lastceshi == 1) {
						jingcailist.get(bfposition).setStartorstop(0);
						jingcailist.get(bfposition).setJindubg(0);
						adapters.get(1).notifyDataSetChanged();
					} else if (lastceshi == 2) {
						phblist.get(bfposition).setStartorstop(0);
						phblist.get(bfposition).setJindubg(0);
						adapters.get(2).notifyDataSetChanged();
					}
					if (player != null) {
						player.release();
						player = null;
					}
				}
				holder = (DazuiAdapter2.ViewHolder) v.getTag();
				Intent it = new Intent();
				it.putExtra("osc", ConstantsJrc.USERDZURL);
				it.putExtra("ouid", String.valueOf(holder.ouid));
				it.setClass(DazuiActivity.this, Dzmysave.class);
				startActivity(it);
			}
		};
		// 踩
		listener_liwu = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				holder = (DazuiAdapter2.ViewHolder) v.getTag();

				ispause = true;
				Intent it = new Intent();
				it.putExtra("fuid", MainMenuActivity.uid);
				it.putExtra("uid", String.valueOf(holder.ouid));
				it.putExtra("chatroom", "1");
				it.putExtra("type", 5);
				it.putExtra("src", 3);
				if (whichpage == 0) {
					it.putExtra("oid", dazuilst.get(holder.position).getId());
				} else if (whichpage == 1) {
					it.putExtra("oid", jingcailist.get(holder.position).getId());
				} else if (whichpage == 2) {
					it.putExtra("oid", phblist.get(holder.position).getId());
				}
				it.setClass(DazuiActivity.this, VillageUserInfoDialog.class);
				startActivity(it);
			}
		};
		// 收藏
		listener_save = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				holder = (DazuiAdapter2.ViewHolder) v.getTag();
				if (whichpage == 0) {
					if (dazuilst.get(holder.position).getIsfav() == 0) {
						listener_save(dazuilst, dazuinewlv, 0, holder.position);
					} else if (dazuilst.get(holder.position).getIsfav() == 1) {
						listener_save(dazuilst, dazuinewlv, 1, holder.position);
					}

				} else if (whichpage == 1) {
					if (jingcailist.get(holder.position).getIsfav() == 0) {
						listener_save(jingcailist, dazuijclv, 0,
								holder.position);
					} else if (jingcailist.get(holder.position).getIsfav() == 1) {
						listener_save(jingcailist, dazuijclv, 1,
								holder.position);
					}
				} else if (whichpage == 2) {
					if (phblist.get(holder.position).getIsfav() == 0) {
						listener_save(phblist, dazuiphblv, 0, holder.position);
					} else if (phblist.get(holder.position).getIsfav() == 1) {
						listener_save(phblist, dazuiphblv, 1, holder.position);
					}
				}
			}
		};
		listener_share = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				holder = (DazuiAdapter2.ViewHolder) v.getTag();
				if (whichpage == 0) {
					// newLaunchThread(false, dazuilst,
					// holder.position).start();
					showShare(false, dazuilst, holder.position , null);
				} else if (whichpage == 1) {

					showShare(false, jingcailist, holder.position , null);

				} else if (whichpage == 2) {
					// newLaunchThread(false, phblist, holder.position).start();
					showShare(false, phblist, holder.position , null);
				}
			}
		};

		// 每项按钮事件
		listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				holder = (DazuiAdapter2.ViewHolder) v.getTag();

				int[] location = new int[2];
				holder.dz_ding.getLocationOnScreen(location);
				int x = location[0];
				int y = location[1];
				v1 = x;

				if (whichpage == 0) {
					bflisener(dazuilst, adapters.get(0), po, bofangurl,
							whichpage, dazuinewlv);
					ceshi = 0;
				} else if (whichpage == 1) {
					bflisener(jingcailist, adapters.get(1), po1, bofangurl1,
							whichpage, dazuijclv);
					ceshi = 1;
				} else if (whichpage == 2) {
					bflisener(phblist, adapters.get(2), po2, bofangurl2,
							whichpage, dazuiphblv);
					ceshi = 2;
				}
			}
		};

		dz_sy_zhutis.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(DazuiActivity.this, Dzzhutis.class);
				startActivity(it);
			}
		});

		mTabPager.setAdapter(mPagerAdapter);
		mTabPager.setCurrentItem(sel, false);
		switch (sel) {
		case 0:
			dazuione.setTextColor(Color.parseColor("#ffe555"));
			dazuitwo.setTextColor(Color.parseColor("#544029"));
			dazuithree.setTextColor(Color.parseColor("#544029"));
			dazuifour.setTextColor(Color.parseColor("#544029"));
			dazuifive.setTextColor(Color.parseColor("#544029"));
			dazuione.setBackgroundResource(R.drawable.zuo3);
			break;
		case 1:
			dazuione.setTextColor(Color.parseColor("#544029"));
			dazuitwo.setTextColor(Color.parseColor("#ffe555"));
			dazuithree.setTextColor(Color.parseColor("#544029"));
			dazuifour.setTextColor(Color.parseColor("#544029"));
			dazuifive.setTextColor(Color.parseColor("#544029"));
			dazuitwo.setBackgroundResource(R.drawable.zhong3);
			break;
		case 2:
			dazuione.setTextColor(Color.parseColor("#544029"));
			dazuitwo.setTextColor(Color.parseColor("#544029"));
			dazuithree.setTextColor(Color.parseColor("#ffe555"));
			dazuifour.setTextColor(Color.parseColor("#544029"));
			dazuifive.setTextColor(Color.parseColor("#544029"));
			dazuithree.setBackgroundResource(R.drawable.zhong3);
			break;			
		case 3:
			dazuione.setTextColor(Color.parseColor("#544029"));
			dazuitwo.setTextColor(Color.parseColor("#544029"));
			dazuithree.setTextColor(Color.parseColor("#544029"));
			dazuifour.setTextColor(Color.parseColor("#ffe555"));
			dazuifive.setTextColor(Color.parseColor("#544029"));
			dazuifour.setBackgroundResource(R.drawable.zhong3);
			break;
		case 4:
			dazuione.setTextColor(Color.parseColor("#544029"));
			dazuitwo.setTextColor(Color.parseColor("#544029"));
			dazuithree.setTextColor(Color.parseColor("#544029"));
			dazuifour.setTextColor(Color.parseColor("#544029"));
			dazuifive.setTextColor(Color.parseColor("#ffe555"));
			dazuifive.setBackgroundResource(R.drawable.you3);
			break;
		}
		String url;
		url = "http://jrc.hutudan.com/music/list.php?flg=0";
		url = Details.geturl(url);
		// "http://jrc.hutudan.com/music/enter.phppkg=com.jianrencun.chatroom&uid=193&nick=+++df&header=http%3A%2F%2Fq.qlogo.cn%2Fqqapp%2F222222%2FEB5F6F79D1AA31C3F3D91E7AD958B74B%2F100";
		StringBuffer data = new StringBuffer();
		// 请求网络验证登陆
		HttpRequestTask request = new HttpRequestTask();
		request.execute(url, data.toString());
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
			switch (arg0) {
			case 0:
				whichpage = 0;
				// isnum1 = true;
				dazuione.setBackgroundResource(R.drawable.zuo3);
				dazuitwo.setBackgroundResource(R.drawable.dz_zhong1);
				dazuithree.setBackgroundResource(R.drawable.dz_zhong1);
				dazuifour.setBackgroundResource(R.drawable.dz_zhong1);
				dazuifive.setBackgroundResource(R.drawable.dz_you1);
				dazuione.setTextColor(Color.parseColor("#ffe555"));
				dazuitwo.setTextColor(Color.parseColor("#544029"));
				dazuithree.setTextColor(Color.parseColor("#544029"));
				dazuifour.setTextColor(Color.parseColor("#544029"));
				dazuifive.setTextColor(Color.parseColor("#544029"));
				// lastceshi = 0;
				break;
			case 1:
				whichpage = 1;
				// isnum1 = true;
				dazuione.setBackgroundResource(R.drawable.dz_zuo1);
				dazuitwo.setBackgroundResource(R.drawable.zhong3);
				dazuithree.setBackgroundResource(R.drawable.dz_zhong1);
				dazuifour.setBackgroundResource(R.drawable.dz_zhong1);
				dazuifive.setBackgroundResource(R.drawable.dz_you1);
				dazuione.setTextColor(Color.parseColor("#544029"));
				dazuitwo.setTextColor(Color.parseColor("#ffe555"));
				dazuithree.setTextColor(Color.parseColor("#544029"));
				dazuifour.setTextColor(Color.parseColor("#544029"));
				dazuifive.setTextColor(Color.parseColor("#544029"));
				// lastceshi = 1;
				if (istwice == false) {
					istwice = true;
					// isnum1 = true;
					currentdate = "";
					// pb.setVisibility(View.VISIBLE);
					// String ss = clink;
					String url = "http://jrc.hutudan.com/music/list.php?flg=1";
					url = Details.geturl(url);
					post(url);
				}
				break;
			case 2:
				whichpage = 2;
				dazuione.setBackgroundResource(R.drawable.dz_zuo1);
				dazuitwo.setBackgroundResource(R.drawable.dz_zhong1);
				dazuithree.setBackgroundResource(R.drawable.zhong3);
				dazuifour.setBackgroundResource(R.drawable.dz_zhong1);
				dazuifive.setBackgroundResource(R.drawable.dz_you1);
				dazuione.setTextColor(Color.parseColor("#544029"));
				dazuitwo.setTextColor(Color.parseColor("#544029"));
				dazuithree.setTextColor(Color.parseColor("#ffe555"));
				dazuifour.setTextColor(Color.parseColor("#544029"));
				dazuifive.setTextColor(Color.parseColor("#544029"));
				// lastceshi = 2;
				if (istwice2 == false) {
					istwice2 = true;			
					currentdate = "";
					// pb.setVisibility(View.VISIBLE);
					// String ss = clink;
					String url = "http://jrc.hutudan.com/music/top.php?flg=0";
					url = Details.geturl(url);
					post(url);
				}
				break;
			case 3:
				whichpage = 3;
				dazuione.setBackgroundResource(R.drawable.dz_zuo1);
				dazuitwo.setBackgroundResource(R.drawable.dz_zhong1);
				dazuithree.setBackgroundResource(R.drawable.dz_zhong1);
				dazuifour.setBackgroundResource(R.drawable.zhong3);
				dazuifive.setBackgroundResource(R.drawable.dz_you1);
				dazuione.setTextColor(Color.parseColor("#544029"));
				dazuitwo.setTextColor(Color.parseColor("#544029"));
				dazuithree.setTextColor(Color.parseColor("#544029"));
				dazuifour.setTextColor(Color.parseColor("#ffe555"));
				dazuifive.setTextColor(Color.parseColor("#544029"));
				// lastceshi = 2;
				if (istwice3 == false) {
						istwice3 = true;
						// isnum1 = true;
						currentdate = "";
						// pb.setVisibility(View.VISIBLE);
						// String ss = clink;
						String url = "http://jrc.hutudan.com/music/top.php?flg=1";
						url = Details.geturl(url);
						post(url);					
				}
				break;
			case 4:
				whichpage = 4;
				dazuione.setBackgroundResource(R.drawable.dz_zuo1);
				dazuitwo.setBackgroundResource(R.drawable.dz_zhong1);
				dazuithree.setBackgroundResource(R.drawable.dz_zhong1);
				dazuifour.setBackgroundResource(R.drawable.dz_zhong1);
				dazuifive.setBackgroundResource(R.drawable.you3);
				dazuione.setTextColor(Color.parseColor("#544029"));
				dazuitwo.setTextColor(Color.parseColor("#544029"));
				dazuithree.setTextColor(Color.parseColor("#544029"));
				dazuifour.setTextColor(Color.parseColor("#544029"));
				dazuifive.setTextColor(Color.parseColor("#ffe555"));
				// lastceshi = 2;
				if (istwice4 == false) {
					istwice4 = true;	
					currentdate = "";
					// pb.setVisibility(View.VISIBLE);
					// String ss = clink;
					String url = "http://jrc.hutudan.com/music/top.php?flg=2";
					url = Details.geturl(url);
					post(url);
				}
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	public void dazui_back(View v) { // 返回按钮
		stopPlay();
	    finish();
	}

	public void dazui_sort(View v) { // 返回按钮
		stopPlay();
		if (dazuilst.size() != 0 || jingcailist.size() != 0
				|| phblist.size() != 0) {
			if (lastceshi == 0) {
				if (dazuilst.size() == 0) {
					finish();
					return;
				}
				dazuilst.get(bfposition).setStartorstop(0);
				dazuilst.get(bfposition).setJindubg(0);
				adapters.get(0).notifyDataSetChanged();
			} else if (lastceshi == 1) {
				if (jingcailist.size() == 0) {
					finish();
					return;
				}
				jingcailist.get(bfposition).setStartorstop(0);
				jingcailist.get(bfposition).setJindubg(0);
				adapters.get(1).notifyDataSetChanged();
			} else if (lastceshi == 2) {
				if (phblist.size() == 0) {
					finish();
					return;
				}
				phblist.get(bfposition).setStartorstop(0);
				phblist.get(bfposition).setJindubg(0);
				adapters.get(2).notifyDataSetChanged();
			}
			if (player != null) {
				player.release();
				player = null;
			}
		}
		Intent it = new Intent();
		ispause = false;
		it.setClass(DazuiActivity.this, Mydazui.class);
		startActivity(it);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			if (view.getLastVisiblePosition() == view.getCount() - 1) {
				if (whichpage == 0) {
					whichScroll(nLink, dazuinewlv);
					twice = 3;
				} else if (whichpage == 1) {
					whichScroll(jingcainLink, dazuijclv);
					twice1 = 3;
				} else if (whichpage == 2) {
					whichScroll(phbnLink, dazuiphblv);
					twice2 = 3;
				}
			}
		}
	}

	private void init() {
		
		ztlist = new ArrayList<ZhutisIteminfo>();
		phlist = new ArrayList<ZhutisIteminfo>();
		drlist = new ArrayList<ZhutisIteminfo>();
		dazuione = (Button) findViewById(R.id.dazuione);
		dazuitwo = (Button) findViewById(R.id.dazuitwo);
		dazuithree = (Button) findViewById(R.id.dazuithree);
		dazuifour = (Button) findViewById(R.id.dazuifour);
		dazuifive = (Button) findViewById(R.id.dazuifive);
		
		mTabPager = (ViewPager) findViewById(R.id.dazuitabpager);
		dz_sy_zhutis = (Button) findViewById(R.id.dz_sy_zhutis);
		// dz_sy_zhutis.setVisibility(View.GONE);
		mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());

		audio = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
		dazuilst = new ArrayList<DazuiIteminfo>();
		lslist = new ArrayList<DazuiIteminfo>();
		jingcailist = new ArrayList<DazuiIteminfo>();
		dllinks = new ArrayList<String>();
		phblist = new ArrayList<DazuiIteminfo>();
		bls = new ArrayList<Boolean>();
		pbs = new ArrayList<ProgressBar>();
		lsfileurl = new ArrayList<String>();
		po = new ArrayList<Integer>();
		po1 = new ArrayList<Integer>();
		po2 = new ArrayList<Integer>();
		bofangurl = new ArrayList<String>();
		bofangurl1 = new ArrayList<String>();
		bofangurl2 = new ArrayList<String>();
		lsbofangurl = new ArrayList<String>();
		lspo = new ArrayList<Integer>();
		adapters = new ArrayList<DazuiAdapter2>();
		for (int i = 0; i < 3; i++) {
			DazuiAdapter2 adapt = null;
			adapters.add(adapt);
		}

		dazuione.setOnClickListener(new MyOnClickListener(0));
		dazuitwo.setOnClickListener(new MyOnClickListener(1));
		dazuithree.setOnClickListener(new MyOnClickListener(2));
		dazuifour.setOnClickListener(new MyOnClickListener(3));
		dazuifive.setOnClickListener(new MyOnClickListener(4));

		vFooter = LayoutInflater.from(this).inflate(R.layout.list_footer, null);
		loading = (LinearLayout) vFooter.findViewById(R.id.list_footer);
		loading.setVisibility(View.GONE);

		LayoutInflater mLi = LayoutInflater.from(this);
		view1 = mLi.inflate(R.layout.dazuicontent, null);

		dazuifresh = (Button) view1.findViewById(R.id.dazuifresh);
		dazuinewlv = (ListView) view1.findViewById(R.id.dazuinewlv);

		ll0 = (LinearLayout) view1.findViewById(R.id.dazui_ll0);
		pb_zuixin = (ProgressBar) view1
				.findViewById(R.id.dazui_zuixinshangchuan);

		dazuinewlv.setOnScrollListener(this);
		dazuinewlv.addFooterView(vFooter);
		dazuinewlv.setFooterDividersEnabled(false);

		view2 = mLi.inflate(R.layout.jingcaiyuyin, null);
		ll1 = (LinearLayout) view2.findViewById(R.id.dazui_ll1);
		pb_jingcai = (ProgressBar) view2.findViewById(R.id.dazui_jingcai);

		dazuifresh2 = (Button) view2.findViewById(R.id.dazuifresh2);
		dazuijclv = (ListView) view2.findViewById(R.id.dazuijingcailv);

		dazuijclv.setOnScrollListener(this);
		dazuijclv.addFooterView(vFooter);
		dazuijclv.setFooterDividersEnabled(false);

//		view3 = mLi.inflate(R.layout.dazuiremenphb, null);
//		ll2 = (LinearLayout) view3.findViewById(R.id.dazui_phb_ll);
//		pb_phb = (ProgressBar) view3.findViewById(R.id.dazui_phb_pb);
//
//		dazuifresh3 = (Button) view3.findViewById(R.id.dazui_phb_fresh);
//		dazuiphblv = (ListView) view3.findViewById(R.id.dazuiphblv);
//
//		dazuiphblv.setOnScrollListener(this);
//		dazuiphblv.addFooterView(vFooter);
//		dazuiphblv.setFooterDividersEnabled(false);
		
		view3 = mLi.inflate(R.layout.zhutis_phb, null);
		ll2 = (LinearLayout) view3.findViewById(R.id.zhutis_phbll0);
		pb_2 = (ProgressBar) view3.findViewById(R.id.zhutis_phbpg);

		lv1 = (ListView) view3.findViewById(R.id.zhutis_phblv);
		
		view4 = mLi.inflate(R.layout.zhutis_zhuti, null);

		lv2 = (ListView) view4.findViewById(R.id.zhutis_zhutilv);
		ll3 = (LinearLayout) view4.findViewById(R.id.zhutis_zhtutill0);
		pb_1 = (ProgressBar) view4.findViewById(R.id.zhutis_zhtutipg);

		view5 = mLi.inflate(R.layout.zhutis_daren, null);
		ll4 = (LinearLayout) view5.findViewById(R.id.zhutis_darenll0);
		pb_3 = (ProgressBar) view5.findViewById(R.id.zhutis_darenpg);

		lv3 = (ListView) view5.findViewById(R.id.zhutis_darenlv);
	}

	public void whichScroll(String nlink, ListView lv) {
		if (TextUtils.isEmpty(nlink)) {
			lv.removeFooterView(vFooter);
			return;
		} else {
			if (!dllinks.contains(nlink)) {
				loading.setVisibility(View.VISIBLE);
				String url = nlink;
				StringBuffer data = new StringBuffer();
				//
				data.append("&pd=");
				lastdate = currentdate;
				data.append(URLEncoder.encode(lastdate));
				// 请求网络验证登陆
				// nlink2 = geturl(url);
				nlink = Details.geturl(nlink);
				HttpRequestTask request = new HttpRequestTask();
				request.execute(nlink, data.toString());
				dllinks.add(nlink);
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
	}

	public void post(String urllink) {
		String result = "";
		// String url = appendNameValue(urllink, "pkg", "com.jianrencun.chatroom");
		StringBuffer data = new StringBuffer();
		// 请求网络验证登陆
		HttpRequestTask request = new HttpRequestTask();
		request.execute(urllink, data.toString());
	}

	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			String str2 = msg.getData().getString("fileurl");// 接受msg传递过来的参数
			int whichlist = msg.getData().getInt("whichlist");// 接受msg传递过来的参数
			switch (msg.what) {
			case 0:
				// lslist.clear();
				switch (whichlist) {
				case 0:
					lslist = dazuilst;
					lspo = po;
					ik = 0;
					lsbofangurl = bofangurl;
					lslv = dazuinewlv;

					break;
				case 1:
					lslist = jingcailist;
					lspo = po1;
					ik = 1;
					lsbofangurl = bofangurl1;
					lslv = dazuijclv;
					break;
				case 2:
					lslist = phblist;
					lspo = po2;
					ik = 2;
					lsbofangurl = bofangurl2;
					lslv = dazuiphblv;
					break;
				}
				if (lslist.size() == 0 || lslist == null) {
					finish();
					return;
				}
				for (int i = 0; i <= lsbofangurl.size() - 1; i++) {
					String str3 = lsbofangurl.get(i);
					if (str2.equalsIgnoreCase(lsbofangurl.get(i))) {
						// Commond.showToast(DazuiActivity.this, "下载完成！");
						if (lslist.size() == 0 || lslist == null) {
							finish();
							return;
						}
						lslist.get(lspo.get(i)).setStartorstop(0);
						lslist.get(lspo.get(i)).setJindubg(0);
						lslv.requestLayout();
						adapters.get(ik).notifyDataSetChanged();
					}
				}

				if (ceshi == whichlist) {
					switch (ceshi) {
					case 0:
						lslist = dazuilst;
						lspo = po;
						ik = 0;
						lsbofangurl = bofangurl;
						lslv = dazuinewlv;
						break;
					case 1:
						lslist = jingcailist;
						lspo = po1;
						ik = 1;
						lsbofangurl = bofangurl1;
						lslv = dazuijclv;
						break;
					case 2:
						lslist = phblist;
						lspo = po2;
						ik = 2;
						lslv = dazuiphblv;
						lsbofangurl = bofangurl2;
						break;
					}
					if (lslist.size() == 0 || lslist == null
							|| lsbofangurl.size() == 0 || lsbofangurl == null) {
						finish();
						return;
					}
					if (str2.equalsIgnoreCase(lsbofangurl.get(lsbofangurl
							.size() - 1))) {
						String filename = MainMenuActivity.dazuisdown
								.getAbsolutePath()
								+ File.separator
								+ Comment.getMd5Hash(lsbofangurl
										.get(lsbofangurl.size() - 1)) + ".amr";
						if (lastceshi == whichlist) {
							if (lslist.size() == 0 || lslist == null) {
								finish();
								return;
							}
							lslist.get(bfposition).setStartorstop(0);
							lslist.get(bfposition).setJindubg(0);
							adapters.get(ik).notifyDataSetChanged();
						}
						if (lslist.size() == 0 || lslist == null) {
							finish();
							return;
						}
						lslist.get(lspo.get(lspo.size() - 1)).setStartorstop(1);
						lslist.get(lspo.get(lspo.size() - 1)).setJindubg(1);
						lslv.requestLayout();
						adapters.get(ik).notifyDataSetChanged();
						if (player != null) {
							player.release();
							player = null;
						}
						if (DazuiAdapter2.th != null) {
							DazuiAdapter2.th = null;
						}
						startPlaying(filename, lspo.get(lspo.size() - 1),
								whichlist);
					}
					if (lastceshi != whichlist) {
						switch (lastceshi) {
						case 0:
							lslist = dazuilst;
							lspo = po;
							ik = 0;
							lsbofangurl = bofangurl;
							lslv = dazuinewlv;
							break;
						case 1:
							lslist = jingcailist;
							lspo = po1;
							ik = 1;
							lsbofangurl = bofangurl1;
							lslv = dazuijclv;
							break;
						case 2:
							lslist = phblist;
							lspo = po2;
							ik = 2;
							lsbofangurl = bofangurl2;
							lslv = dazuiphblv;
							break;
						}
						if (lslist.size() == 0 || lslist == null) {
							finish();
							return;
						}
						lslist.get(bfposition).setStartorstop(0);
						lslist.get(bfposition).setJindubg(0);
						lslv.requestLayout();
						adapters.get(ik).notifyDataSetChanged();
					}
					lastceshi = whichlist;
					bfposition = position;
				}
				break;
			case 1:
				Commond.showToast(DazuiActivity.this, "网络不给力啊！！");
				try {
					getik(whichpage);
					getls(whichpage);
				} catch (Exception e) {
					// TODO: handle exception
					finish();
				}

				if (lslist.size() == 0 || lslist == null) {
					finish();
					return;
				}
				lslist.get(position).setStartorstop(0);
				lslist.get(position).setJindubg(0);
				if (adapters.get(ik) != null) {
					lslv.requestLayout();
					adapters.get(ik).notifyDataSetChanged();
				}
				break;
			}
			super.handleMessage(msg);
		}
	};

	class myThread implements Runnable {
		public String furl;
		public int whichlist;

		public myThread(String url, int whichlist) {
			this.furl = url;
			this.whichlist = whichlist;
		}

		public void run() {
			if (thswitch) {
				int count;
				try {
					// for(int i = 0 ; i<bofangurl.size()-1 ;i++){
					URL url = new URL(furl);
					URLConnection conexion = url.openConnection();
					conexion.connect();

					int lenghtOfFile = conexion.getContentLength();
					InputStream input = new BufferedInputStream(
							url.openStream());
					String filename = MainMenuActivity.dazuisdown.getPath()
							.toString();
					OutputStream output = new FileOutputStream(filename
							+ File.separator + Comment.getMd5Hash(furl)
							+ ".amr");
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
					bundle.putInt("whichlist", whichlist);
					message.setData(bundle);
					myHandler.sendMessage(message);
					lsfileurl.add(fileurl);
					// }

				} catch (Exception e) {
					// Commond.showToast(DazuiActivity.this, "网络很不给力啊！");
					Message message = new Message();
					message.what = 1;
					myHandler.sendMessage(message);

				}
			}
		}
	}

	public void startPlaying(String mFlilepath, final int po, final int which) {
		try {
			if (isthisactivity == true) {
				if (null == player) {
					player = new MediaPlayer();
				}
				// d
				player.reset();
				File file = new File(mFlilepath);
				FileInputStream fis = new FileInputStream(file);
				player.setDataSource(fis.getFD());
				// 设置要播放的文件
				// player.setDataSource(mFlilepath);
				player.prepare();
				lenth = player.getDuration() / 1000;
				// 播放之
				player.start();
				// new Thread(new update()).start();
				MainMenuActivity.borz = true;
				player.setOnCompletionListener(new OnCompletionListener() {

					@Override
					public void onCompletion(MediaPlayer mp) {
						player.release();
						player = null;
						getls(which);
						getik(which);
						if (lslist.size() == 0 || lslist == null) {
							finish();
							return;
						}
						if (DazuiAdapter2.th != null) {
							DazuiAdapter2.th = null;
						}
						lslist.get(po).setStartorstop(0);
						lslist.get(po).setJindubg(0);
						lslv.requestLayout();
						adapters.get(ik).notifyDataSetChanged();
						bls.set(position, true);
					}
				});
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void stopPlay() {
		if (null != player) {
			if (player.isPlaying()) {
				player.stop();
			}
			player.release();
			player = null;
		}
	}

	// private void registerHeadsetPlugReceiver() {
	// headsetPlugReceiver = new HeadsetPlugReceiver();
	// IntentFilter filter = new IntentFilter();
	// filter.addAction("android.intent.action.HEADSET_PLUG");
	// registerReceiver(headsetPlugReceiver, filter);
	// }
	//
	// private void unregisterReceiver() {
	// this.unregisterReceiver(headsetPlugReceiver);
	// }

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (th != null) {
			th.interrupt();
			th = null;
		}
		if (BitmapCacheDzlb.getIntance().bmpCacheMap != null
				&& BitmapCacheDzlb.getIntance().bmpCacheMap.size() != 0) {

			BitmapCacheDzlb.getIntance().clearCacheBitmap();
		}
		if (BitmapCacheDzpl.getIntance().bmpCacheMap != null
				&& BitmapCacheDzpl.getIntance().bmpCacheMap.size() != 0) {

			BitmapCacheDzpl.getIntance().clearCacheBitmap();
		}
		BitmapCacheBguanzhu.getIntance().clearCacheBitmap();
		BitmapCacheBlack.getIntance().clearCacheBitmap();
		BitmapCacheVillage.getIntance().clearCacheBitmap();

		isthisactivity = false;
		dllinks.clear();
		dazuilst.clear();
		jingcailist.clear();
		phblist.clear();
		lslist.clear();
		lsfileurl.clear();
		bls.clear();
		pbs.clear();
		adapters.clear();
		po.clear();
		po1.clear();
		po2.clear();
		lspo.clear();
		bofangurl.clear();
		bofangurl1.clear();
		bofangurl2.clear();
		lsbofangurl.clear();
		Dazuidetatil.context = null;
		player = null;
		lenth = 0;
		DazuiAdapter2.th = null;
		MainMenuActivity.borz = false;
		ispause = false;

		unregisterReceiver(headsetPlugReceiver);

			AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
			audioManager.setMode(AudioManager.MODE_NORMAL);	
		super.onDestroy();
	}

	@Override
	void resultData(String url, String result) {
		// TODO Auto-generated method stub
		String tip = null;
		try {
           
			JSONObject jsonChannel = new JSONObject(result);
			tip = URLDecoder.decode(jsonChannel.optString("tip"));
			lastdate = URLDecoder.decode(jsonChannel.optString("pd"));
			nlink = URLDecoder.decode(jsonChannel.optString("nlink"));
			String bttime = URLDecoder.decode(jsonChannel.optString("bttime"));
			int ret = jsonChannel.optInt("ret");
			Commond.showToast(this, tip);
			if (ret == 0) {
				return;
			}

			JSONArray jsonItems = jsonChannel.optJSONArray("items");
			if (jsonItems != null) {
				// tip = "获取成功！";
				if(url.contains("list.php")){
				for (int i = 0; i < jsonItems.length(); i++) {
					JSONObject jsonItem = jsonItems.getJSONObject(i);
					int id = jsonItem.optInt("id");
					int uid = jsonItem.optInt("uid");
					String title = URLDecoder.decode(jsonItem
							.optString("title"));
					String afile = URLDecoder.decode(jsonItem
							.optString("afile"));
					String uhead = URLDecoder.decode(jsonItem
							.optString("uhead"));
					String unick = URLDecoder.decode(jsonItem
							.optString("unick"));
					String date = URLDecoder.decode(jsonItem.optString("date"));
					String size = URLDecoder.decode(jsonItem.optString("size"));
					String len = URLDecoder.decode(jsonItem.optString("len"));
					String flg_pic = URLDecoder.decode(jsonItem
							.optString("flg_pic"));
					len = "时长: " + len + "'";
					int fc = jsonItem.optInt("fc");
					int sc = jsonItem.optInt("sc");
					int uc = jsonItem.optInt("uc");
					int dc = jsonItem.optInt("dc");
					int tc = jsonItem.optInt("tc");
					int gc = jsonItem.optInt("gc");
					String pd = URLDecoder.decode(jsonItem.optString("pd"));
					String nameclor = URLDecoder.decode(jsonItem
							.optString("unick_c"));
					int ccount = jsonItem.optInt("ccount");
					int status = jsonItem.optInt("status");
					int isfav = jsonItem.optInt("isfav");
					int type = jsonItem.optInt("bg");

					DazuiIteminfo item = new DazuiIteminfo(title, size, date,
							id, afile, uid, uhead, unick, len, ccount, 3, 0,
							isfav, nameclor, fc, sc, uc, dc, 0, flg_pic, tc, gc , type);
					if (url.contains("list.php?flg=0")) {
						// itemchoose1.add(item);
						dazuilst.add(item);
						nLink = nlink;
					} else if (url.contains("list.php?flg=1")) {
						jingcailist.add(item);
						jingcainLink = nlink;
					} else if (url.contains("list.php?flg=4")) {
						phblist.add(item);
						phbnLink = nlink;
					}
					Boolean bl = false;
					bls.add(bl);
					ProgressBar pb = null;
					pbs.add(pb);
				}
				}else if(url.contains("top.php")){
					for (int i = 0; i < jsonItems.length(); i++) {
						JSONObject jsonItem = jsonItems.getJSONObject(i);
						String title = URLDecoder.decode(jsonItem
								.optString("title"));
						String title_c = URLDecoder.decode(jsonItem
								.optString("title_c"));
						String pic = URLDecoder.decode(jsonItem.optString("pic"));
						String desc1 = URLDecoder.decode(jsonItem
								.optString("desc1"));
						String desc2 = URLDecoder.decode(jsonItem
								.optString("desc2"));
						String link = URLDecoder.decode(jsonItem.optString("link"));

						ZhutisIteminfo item = new ZhutisIteminfo(pic, title, desc1,
								link, title_c, desc2);
						if (url.contains("flg=0")) {
							// itemchoose1.add(item);
							ztlist.add(item);
							ztlink = nlink;
						} else if (url.contains("flg=1")) {
							phlist.add(item);
							phlink = nlink;
						} else if (url.contains("flg=2")) {
							drlist.add(item);
							drlink = nlink;
						}
					}
				}
			}
			if(url.contains("top.php?flg=0")){
				ZhutisAdapter ad;
//				pb_zuixin.setVisibility(View.GONE);
				if (lv1.getAdapter() == null) {
					ad = new ZhutisAdapter(DazuiActivity.this, ztlist, lv1);
					ll2.setVisibility(View.GONE);
					lv1.setAdapter(ad);				
				} else if (twice == 3) {
					lv1.requestLayout();
//					 ad.notifyDataSetChanged(); // 数据集变化后,通知adapter
					loading.setVisibility(View.GONE);
				}
			}else if(url.contains("top.php?flg=1")){
				ZhutisAdapter ad;
				if (lv2.getAdapter() == null) {
					ad = new ZhutisAdapter(DazuiActivity.this, phlist, lv2);
					ll3.setVisibility(View.GONE);
					lv2.setAdapter(ad);				
				} else if (twice == 3) {
					lv2.requestLayout();
//					 ad.notifyDataSetChanged(); // 数据集变化后,通知adapter
					loading.setVisibility(View.GONE);
				}
				
			}else if(url.contains("top.php?flg=2")){
				ZhutisAdapter ad;
				if (lv3.getAdapter() == null) {
					ad = new ZhutisAdapter(DazuiActivity.this, drlist, lv3);
					ll4.setVisibility(View.GONE);
					lv3.setAdapter(ad);				
				} else if (twice == 3) {
					lv3.requestLayout();
//					 ad.notifyDataSetChanged(); // 数据集变化后,通知adapter
					loading.setVisibility(View.GONE);					
				}
			}
			if (url.contains("http://jrc.hutudan.com/music/fav.php")) {
				if (url.contains("type=0")) {
					saveorquxiao(1);
					if (whichpage == 0) {
						dazuilst.get(holder.position).setIsfav(1);
					} else if (whichpage == 1) {
						jingcailist.get(holder.position).setIsfav(1);
					} else if (whichpage == 2) {
						phblist.get(holder.position).setIsfav(1);
					}
				} else if (url.contains("type=1")) {
					saveorquxiao(-1);
					if (whichpage == 0) {
						dazuilst.get(holder.position).setIsfav(0);
					} else if (whichpage == 1) {
						jingcailist.get(holder.position).setIsfav(0);
					} else if (whichpage == 2) {
						phblist.get(holder.position).setIsfav(0);
					}
				}
			}

			if (url.contains("http://jrc.hutudan.com/music/list.php?flg=0")) {
				DazuiAdapter2 ad;
				pb_zuixin.setVisibility(View.GONE);
				if (dazuinewlv.getAdapter() == null) {
					ad = new DazuiAdapter2(DazuiActivity.this, dazuilst,
							dazuinewlv, listener, null, false, listener_save,
							listener_share, listener_osc, listener_liwu);
					ll0.setVisibility(View.GONE);
					dazuinewlv.setAdapter(ad);
					adapters.set(0, ad);
				} else if (twice == 3) {
					dazuinewlv.requestLayout();
					// ad.notifyDataSetChanged(); // 数据集变化后,通知adapter
					loading.setVisibility(View.GONE);
				}
				// adapt = ad ;
			} else if (url
					.contains("http://jrc.hutudan.com/music/list.php?flg=1")) {
				DazuiAdapter2 ad1;

				pb_jingcai.setVisibility(View.GONE);
				if (dazuijclv.getAdapter() == null) {
					ad1 = new DazuiAdapter2(DazuiActivity.this, jingcailist,
							dazuijclv, listener, null, false, listener_save,
							listener_share, listener_osc, listener_liwu);
					ll1.setVisibility(View.GONE);
					dazuijclv.setAdapter(ad1);
					adapters.set(1, ad1);
				} else if (twice1 == 3) {
					dazuijclv.requestLayout();
					// ad1.notifyDataSetChanged(); // 数据集变化后,通知adapter
					loading.setVisibility(View.GONE);
				}
			} else if (url
					.contains("http://jrc.hutudan.com/music/list.php?flg=4")) {
				DazuiAdapter2 ad2;

				pb_phb.setVisibility(View.GONE);
				if (dazuiphblv.getAdapter() == null) {
					ad2 = new DazuiAdapter2(DazuiActivity.this, phblist,
							dazuiphblv, listener, null, false, listener_save,
							listener_share, listener_osc, listener_liwu);
					ll2.setVisibility(View.GONE);
					dazuiphblv.setAdapter(ad2);
					adapters.set(2, ad2);
				} else if (twice2 == 3) {
					dazuiphblv.requestLayout();
					// ad2.notifyDataSetChanged(); // 数据集变化后,通知adapter
					loading.setVisibility(View.GONE);
				}
			}

			// currentdate = pd;
			// th.interrupt();
		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return;
		}
		if (!TextUtils.isEmpty(tip)) {
			Commond.showToast(this, tip);
		}
	}

	// 播放按钮监听方法
	// ////////////////////////////
	// ////////////
	private void bflisener(List<DazuiIteminfo> list, DazuiAdapter2 da,
			List<Integer> pos, List<String> bofangurls, int which, ListView lv) {
		fileurl = list.get(holder.position).getAfile();

		position = holder.position;
		pos.add(position);
		bofangurls.add(fileurl);
		// 判断俩次播放位置是否相同

		if (bfposition == position) {
			// 在一个页面的listview
			if (lastceshi == which) {
				if (player != null) {
					if (list.get(position).getStartorstop() == 1) {
						player.pause();
						MainMenuActivity.borz = false;
						File picfile = new File(MainMenuActivity.dazuisdown
								+ File.separator + Comment.getMd5Hash(fileurl)
								+ ".amr");
						if (picfile.exists()) {
							holder.dz_sybf
									.setBackgroundResource(R.drawable.dz_sy_zhengwenbf);
						} else {
							holder.dz_sybf
									.setBackgroundResource(R.drawable.dazuidetatil_bf_weixiazai);
						}
						list.get(position).setStartorstop(0);
						issing = true;
					} else if (list.get(position).getStartorstop() == 0) {
						player.start();
						MainMenuActivity.borz = true;
						holder.dz_sybf
								.setBackgroundResource(R.drawable.dazuidetailzt1);
						list.get(position).setStartorstop(1);
						list.get(position).setJindubg(1);
					}
				}
				// 在播放另一个页面的
				else {
					if (player != null) {
						player.release();
						player = null;
					}
					if (DazuiAdapter2.th != null) {
						DazuiAdapter2.th = null;
					}
					File picfile = new File(MainMenuActivity.dazuisdown
							+ File.separator + Comment.getMd5Hash(fileurl)
							+ ".amr");
					String filename = picfile.getPath().toString();
					if (picfile.exists()) {

						list.get(position).setStartorstop(1);
						list.get(position).setJindubg(1);
						lv.requestLayout();
						da.notifyDataSetChanged();
						startPlaying(filename, position, which);
						issing = true;
					} else {
						if (!lsfileurl.contains(fileurl)) {
							holder.dazuiliebiao_pb.setVisibility(View.VISIBLE);
							holder.dz_sybf.setEnabled(false);
							th = new Thread(new myThread(fileurl, whichpage));
							th.start();
							list.get(position).setStartorstop(2);

						}
					}
				}
			} else {

				if (player != null) {
					player.release();
					player = null;
				}
				if (DazuiAdapter2.th != null) {
					DazuiAdapter2.th = null;
				}
				File picfile = new File(MainMenuActivity.dazuisdown
						+ File.separator + Comment.getMd5Hash(fileurl) + ".amr");
				String filename = picfile.getPath().toString();
				if (picfile.exists()) {

					list.get(position).setStartorstop(1);
					list.get(position).setJindubg(1);
					lv.requestLayout();
					da.notifyDataSetChanged();
					startPlaying(filename, position, which);
					issing = true;
				} else {
					if (!lsfileurl.contains(fileurl)) {
						holder.dazuiliebiao_pb.setVisibility(View.VISIBLE);
						holder.dz_sybf.setEnabled(false);
						th = new Thread(new myThread(fileurl, whichpage));
						th.start();
						list.get(position).setStartorstop(2);
					}
				}

				switch (lastceshi) {
				case 0:
					lslist = dazuilst;
					lspo = po;
					ik = 0;
					lsbofangurl = bofangurl;
					lslv = dazuinewlv;
					break;
				case 1:
					lslist = jingcailist;
					lspo = po1;
					ik = 1;
					lslv = dazuijclv;
					lsbofangurl = bofangurl1;
					break;
				case 2:
					lslist = phblist;
					lspo = po2;
					ik = 2;
					lslv = dazuiphblv;
					lsbofangurl = bofangurl2;
					break;
				}
				if (lslist.size() == 0) {
					finish();
					return;
				}
				if (lslist.size() == 0 || lslist == null) {
					finish();
					return;
				}
				lslist.get(position).setStartorstop(0);
				lslist.get(position).setJindubg(0);
				lslv.requestLayout();
				adapters.get(ik).notifyDataSetChanged();
			}
			lastceshi = which;
			issing = true;
			fistpage = which;
		}
		// }
		// 不是播放的同一个位置音乐
		else {
			lasturl = fileurl;
			File picfile = new File(MainMenuActivity.dazuisdown
					+ File.separator + Comment.getMd5Hash(fileurl) + ".amr");
			String filename = picfile.getPath().toString();

			if (picfile.exists()) {
				// 当在一个页面中播放
				if (lastceshi == which) {

					list.get(position).setStartorstop(1);
					list.get(position).setJindubg(1);
					list.get(bfposition).setStartorstop(0);
					list.get(bfposition).setJindubg(0);
					int xx = position;
					int yy = bfposition;
					DazuiIteminfo it = list.get(position);
					lv.requestLayout();
					da.notifyDataSetChanged();
					startPlaying(filename, position, which);
					bfposition = position;
					issing = true;
					fistpage = which;
				}
				// 在另一个页面播放
				else {
					if (player != null) {
						player.release();
						player = null;
					}
					if (DazuiAdapter2.th != null) {
						DazuiAdapter2.th = null;
					}
					list.get(position).setStartorstop(1);
					list.get(position).setJindubg(1);
					DazuiIteminfo it = list.get(position);
					lv.requestLayout();
					da.notifyDataSetChanged();
					startPlaying(filename, position, which);
					issing = true;
					switch (lastceshi) {
					case 0:
						lslist = dazuilst;
						lspo = po;
						ik = 0;
						lsbofangurl = bofangurl;
						break;
					case 1:
						lslist = jingcailist;
						lspo = po1;
						ik = 1;
						lsbofangurl = bofangurl1;
						break;
					case 2:
						lslist = phblist;
						lspo = po2;
						ik = 2;
						lsbofangurl = bofangurl2;
						break;
					}
					if (lslist.size() == 0 || lslist == null) {
						finish();
						return;
					}
					lslist.get(bfposition).setStartorstop(0);
					lslist.get(bfposition).setJindubg(0);
					lv.requestLayout();
					adapters.get(ik).notifyDataSetChanged();
					bfposition = position;
				}
				// isnum1 = false ;
				lastceshi = which;
			} else {
				if (!lsfileurl.contains(fileurl)) {
					holder.dazuiliebiao_pb.setVisibility(View.VISIBLE);
					holder.dz_sybf.setEnabled(false);
					th = new Thread(new myThread(fileurl, whichpage));
					th.start();
					list.get(position).setStartorstop(2);

				}
			}
			bls.set(position, true);
		}

	}

	public List<DazuiIteminfo> getls(int which) {
		// lslist.clear();
		switch (which) {
		case 0:
			lslist = dazuilst;
			break;
		case 1:
			lslist = jingcailist;
			break;
		case 2:
			lslist = phblist;
			break;
		}
		return lslist;
	}

	public int getik(int which) {
		// lslist.clear();
		switch (which) {
		case 0:
			ik = 0;
			lslv = dazuinewlv;
			break;
		case 1:
			ik = 1;
			lslv = dazuijclv;
			break;
		case 2:
			ik = 2;
			lslv = dazuiphblv;
			break;
		}
		return ik;
	}

	public List<Integer> getpo(int which) {
		// lslist.clear();
		switch (which) {
		case 0:
			lspo = po;
			break;
		case 1:
			lspo = po1;
			break;
		case 2:
			lspo = po2;
			break;
		}
		return null;
	}

	public List<Integer> getbofangurl(int which) {
		// lslist.clear();
		switch (which) {
		case 0:
			lsbofangurl = bofangurl;
			break;
		case 1:
			lsbofangurl = bofangurl1;
			break;
		case 2:
			lsbofangurl = bofangurl2;
			break;
		}
		return null;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			stopPlay();
			finish();		
			return true;
		default:
			break;
		}
		return false;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		ispause = false;
		try {					
		if (EditPage.isshare == true) {
			if (whichpage == 0) {
				if (EditPage.isshare == true) {
					dazuilst.get(holder.position).setSc(
							dazuilst.get(holder.position).getSc() + 1);
					dazuinewlv.requestLayout();
					adapters.get(0).notifyDataSetChanged();
				}
			} else if (whichpage == 1) {
				if (EditPage.isshare == true) {
					jingcailist.get(holder.position).setSc(
							jingcailist.get(holder.position).getSc() + 1);
					dazuijclv.requestLayout();
					adapters.get(1).notifyDataSetChanged();
				}
			} else if (whichpage == 2) {
				if (EditPage.isshare == true) {
					phblist.get(holder.position).setSc(
							phblist.get(holder.position).getSc() + 1);
					dazuiphblv.requestLayout();
					adapters.get(2).notifyDataSetChanged();
				}
			}
			EditPage.isshare = false;
		}
		// if (audio.isWiredHeadsetOn() == true) {
		// AudioManager audioManager = (AudioManager)
		// getSystemService(Context.AUDIO_SERVICE);
		// audioManager.setMode(AudioManager.MODE_IN_CALL);// 把模式调成听筒放音模式
		// audioManager.setSpeakerphoneOn(false);
		// } else if (audio.isWiredHeadsetOn() == false) {
		// AudioManager audioManager = (AudioManager)
		// getSystemService(Context.AUDIO_SERVICE);
		// audioManager.setMode(AudioManager.ROUTE_SPEAKER);// 把模式调成听筒放音模式
		// audioManager.setSpeakerphoneOn(true);
		// }
		if (player != null) {
			if (MainMenuActivity.borz == true) {
				player.start();
			}
		}
		if ((dazuilst.size() != 0 || jingcailist.size() != 0
				|| phblist.size() != 0) && player == null) {
			if (lastceshi == 0) {
				dazuilst.get(bfposition).setStartorstop(0);
				dazuilst.get(bfposition).setJindubg(0);
				adapters.get(0).notifyDataSetChanged();
			} else if (lastceshi == 1) {
				jingcailist.get(bfposition).setStartorstop(0);
				jingcailist.get(bfposition).setJindubg(0);
				adapters.get(1).notifyDataSetChanged();
			} else if (lastceshi == 2) {
				phblist.get(bfposition).setStartorstop(0);
				phblist.get(bfposition).setJindubg(0);
				adapters.get(2).notifyDataSetChanged();
			}
			if (player != null) {
				player.release();
				player = null;
			}
		}
		} catch (Exception e) {
			// TODO: handle exception
		}
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if (ispause == false) {
			// if (audio.isWiredHeadsetOn() == true) {
			// AudioManager audioManager = (AudioManager)
			// getSystemService(Context.AUDIO_SERVICE);
			// audioManager.setMode(AudioManager.MODE_IN_CALL);// 把模式调成听筒放音模式
			// audioManager.setSpeakerphoneOn(false);
			// } else if (audio.isWiredHeadsetOn() == false) {
			// AudioManager audioManager = (AudioManager)
			// getSystemService(Context.AUDIO_SERVICE);
			// audioManager.setMode(AudioManager.MODE_NORMAL);// 把模式调成听筒放音模式
			//
			// }
		}
		if (player != null) {
			if (ispause == false) {
				if (player.isPlaying()) {
					player.pause();
				}
			}
		}
		super.onPause();
	}

	private void saveorquxiao(int num) {
		if (whichpage == 0) {
			if (adapters.get(0) != null) {
				dazuinewlv.requestLayout();
				dazuilst.get(holder.position).setFc(
						dazuilst.get(holder.position).getFc() + num);
				adapters.get(0).notifyDataSetChanged();
			}
		} else if (whichpage == 1) {
			if (adapters.get(1) != null) {
				dazuijclv.requestLayout();
				jingcailist.get(holder.position).setFc(
						jingcailist.get(holder.position).getFc() + num);
				adapters.get(1).notifyDataSetChanged();
			}
		} else if (whichpage == 2) {
			if (adapters.get(2) != null) {
				dazuiphblv.requestLayout();
				phblist.get(holder.position).setFc(
						phblist.get(holder.position).getFc() + num);
				adapters.get(2).notifyDataSetChanged();
			}
		}
	}

	// 使用快捷分享完成图文分享
//	public Thread newLaunchThread(final boolean silent,
//			final List<DazuiIteminfo> it, final int position) {
//		return new Thread() {
//			public void run() {
//
//				handler.post(new Runnable() {
//					public void run() {
//						String url = "http://jrc.hutudan.com/music/shareitem.php"
//								+ "id="
//								+ it.get(position).getId()
//								+ "&uid="
//								+ MainMenuActivity.uid;
//						ispause = true;
//						Intent i = new Intent(DazuiActivity.this,
//								ShareAllGird.class);
//						i.putExtra("notif_icon", R.drawable.ic_launcher);
//						i.putExtra("notif_title",
//								DazuiActivity.this.getString(R.string.app_name));
//
//						i.putExtra("address", "13800123456");
//						i.putExtra("title", "图文分享");
//						i.putExtra("text", it.get(position).getTitle()
//								+ "    下载地址：" + url);
//						i.putExtra("dzurl", url);
//						i.putExtra("image", "");
//						i.putExtra("which", 2);
//
//						i.putExtra("silent", silent);
//						startActivity(i);
//					}
//				});
//			}
//		};
//
//	}

	// 使用快捷分享完成直接分享
//	private void showGrid(boolean silent, final List<DazuiIteminfo> it,
//			final int position) {
//
//		Intent i = new Intent(this, ShareAllGird.class);
//		String url = "http://jrc.hutudan.com/music/shareitem.php" + "id="
//				+ it.get(position).getId() + "&uid=" + MainMenuActivity.uid;
//		ispause = true;
//		// 分享时Notification的图标
//		i.putExtra("notif_icon", R.drawable.ic_launcher);
//		// 分享时Notification的标题
//		i.putExtra("notif_title", this.getString(R.string.app_name));
//
//		// title标题，在印象笔记、邮箱、信息、微信（包括好友和朋友圈）、人人网和QQ空间使用，否则可以不提供
//		i.putExtra("title", "分享");
//		// titleUrl是标题的网络链接，仅在QQ空间使用，否则可以不提供
//		i.putExtra("titleUrl", "http://www.jianrencun.com/");
//		i.putExtra("title", "语音分享");
//		i.putExtra("text", it.get(position).getTitle() + "    下载地址：" + url);
//		i.putExtra("dzurl", url);
//		i.putExtra("image", "");
//		i.putExtra("which", 2);
//		// 是平台名称
//		i.putExtra("silent", silent);
//		this.startActivity(i);
//	}
	// 使用快捷分享完成分享（请务必仔细阅读位于SDK解压目录下Docs文件夹中OnekeyShare类的JavaDoc）
	private void showShare(boolean silent, final List<DazuiIteminfo> it,final int position,String platform) {
		
		String url = "http://jrc.hutudan.com/music/shareitem.php?" + "id="
				+ it.get(position).getId() + "&uid=" + MainMenuActivity.uid;
		ispause = true;
		OnekeyShare oks = new OnekeyShare();
		oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
		oks.setTitle("分享");
		oks.setTitleUrl(url);
		oks.setText(it.get(position).getTitle() +"    收听地址："+ url);
		oks.setAppPath(Details.TEST_IMAGE);
		oks.setComment("分享");
		oks.setUrl(url);
		oks.setSite(this.getString(R.string.app_name));
		oks.setSiteUrl(url);
		oks.setDzUrl(url);
		oks.setWhich(2);
//		oks.setVenueName("Southeast in China");
//		oks.setVenueDescription("This is a beautiful place!");
//		oks.setLatitude(23.122619f);
//		oks.setLongitude(113.372338f);
		oks.setSilent(silent);
		if (platform != null) {
			oks.setPlatform(platform);
		}

		// 去除注释，则快捷分享的分享加过将听过OneKeyShareCallback回调
//		oks.setCallback(new OneKeyShareCallback());

		oks.show(this);
	}


	// /////dingddddddddddddddddd
	private void listener_dingorcai(List<DazuiIteminfo> list, ListView lv,
			int flg, int position) {
		String url;
		int mid = list.get(position).getId();
		url = "http://jrc.hutudan.com/music/support.php";
		url = Details.appendNameValueint(url, "flg", flg);
		url = Details.appendNameValueint(url, "uid",
				Integer.parseInt(MainMenuActivity.uid));
		url = Details.appendNameValueint(url, "mid", mid);
		StringBuffer data = new StringBuffer();
		// 请求网络验证登陆
		HttpRequestTask request = new HttpRequestTask();
		request.execute(url, data.toString());
	}

	// /////dingddddddddddddddddd
	private void listener_save(List<DazuiIteminfo> list, ListView lv, int type,
			int position) {
		String url;
		int mid = list.get(position).getId();
		url = "http://jrc.hutudan.com/music/fav.php";
		url = Details.appendNameValueint(url, "type", type);
		url = Details.appendNameValueint(url, "uid",
				Integer.parseInt(MainMenuActivity.uid));
		url = Details.appendNameValueint(url, "id", mid);
		StringBuffer data = new StringBuffer();
		// 请求网络验证登陆
		HttpRequestTask request = new HttpRequestTask();
		request.execute(url, data.toString());
	}

	// ////////////////////////////////////////////////////////
	// //////监听耳机插入
	private void registerHeadsetPlugReceiver() {
		headsetPlugReceiver = new HeadsetPlugReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.intent.action.HEADSET_PLUG");
		registerReceiver(headsetPlugReceiver, intentFilter);
	}

	/*
	 * @Override public boolean dispatchTouchEvent(MotionEvent ev) { // TODO
	 * Auto-generated method stub if(mWebView.getVisibility() == View.VISIBLE){
	 * mWebView.setVisibility(View.GONE); return false ; }else{ return
	 * super.dispatchTouchEvent(ev); } }
	 */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_ITEM_COUNTER, 0, "听筒");
		menu.add(0, MENU_ITEM_COUNTER + 1, 0, "外放");

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_ITEM_COUNTER:
			if (audio.isWiredHeadsetOn() == false) {
				AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
				audioManager.setMode(AudioManager.MODE_IN_CALL);// 把模式调成听筒放音模式           
			}
			break;
		case MENU_ITEM_COUNTER + 1:
			if (audio.isWiredHeadsetOn() == false) {
				AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
				audioManager.setMode(AudioManager.MODE_NORMAL);
			}
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}


}