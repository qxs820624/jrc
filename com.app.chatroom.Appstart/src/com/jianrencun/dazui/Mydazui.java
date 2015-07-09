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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import cn.sharesdk.onekeyshare.EditPage;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.audio.VideoRecordDz2;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.otherui.VillageUserInfoDialog;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;
import com.duom.fjz.iteminfo.BitmapCache;
import com.jianrencun.android.Details;
import com.jianrencun.chatroom.R;

public class Mydazui extends HttpBaseActivity implements OnScrollListener,
		OnClickListener, Callback {

	private Button luzhibnt, mybnt, dazuifresh2, bfmbnt, dazuiscbnt,
			shoucangyuyin, dazuiclbnt;
	private ViewPager mTabPager;
	private ImageButton dazuiluyin, ztbf;
	private ProgressBar pb;
	private TextView begintv, endtv, timelong;
	private View vFooter;
	private ArrayList<View> views;
	private SeekBar seekbar;
	private LinearLayout loading;
	private boolean istwice = false, istwice2 = false;
	private ListView mydazuisc, mydazuisave, lslv;
	VideoRecord vr;
	private Handler hd;
	private final int message_what_position = 101;
	String kk;
	private AudioManager audio;
	private LinearLayout seekll, scandclll;
	/* 音频控制 */
	MediaPlayer myMediaStart; // 启动声音
	MediaPlayer myMediaSend; // 发送出声音
	MediaPlayer mediaPlayer; // 播放音频
	boolean isPlay = false;// 是否正在播放
	boolean isRecord = false;// 是否正在录音
	TimerTask task;// 定时器
	Timer timer;
	int jishiqi = 0;
	private boolean run;
	/* 配置文件 */
	public Timer receiveTime;// 接收定时器
	public Timer addTime;// 添加定时器
	public Timer systemMsgTime;// 系统消息定时器
	public Timer sendAudio;// 延迟发送音频
	private View view1, view2, view3;
	LayoutInflater mLi;
	private boolean isplay;
	// 开始时间 ， 结束时间
	private long begintime, endtime;
	// 播放开关
	boolean bfswitch;
	static boolean jixu;
	// 动画
	public static final int ANIMATION_TIME = 1000;
	AlphaAnimation aa;
	int tagfromdz;
	private TextView time2;
	private int whichpage = 0;
	private Thread th, th2;

	// ,y dazui yiyin shangchuan
	private List<DazuiIteminfo> mylist, savelist, lslist;
	private ProgressBar yuyinpro, savepro;
	private LinearLayout yuyinll, savell;
	private ListView lv, savelv;
	private DazuiAdapter2 ad, savead;
	OnClickListener listener, listener2, listener_osc, listener_liwu,
			listener_save, listener_share;
	private DazuiAdapter2.ViewHolder holder;
	private String fileurl;
	private int position, bfposition;
	public static MediaPlayer player;
	private List<String> lsfileurl;
	private List<String> bofangurl, bofangurl1, bofangurl2, lsbofangurl;;
	private List<Integer> po, po1, po2, lspo;

	// 大嘴删除上传
	private Button bton, delete;
	private boolean scswitch = false;
	private int twice = 0;
	public static boolean scisnoty;
	private ImageView danosc, danosave;
	private VideoRecordDz2 vrdz;
	private String mynlink, savenlink;
	private List<String> dllinks;
	/* 配置文件 */
	SharedPreferences sp;
	SystemSettingUtilSp su;
	int videoTime = 0;
	int length;
	private Handler handler1;
	int ceshi = 1;
	int lastceshi = 1;
	boolean issing;
	int ik = 1;
	int fistpage;
	private String lasturl;
	private List<Boolean> bls = null;
	private boolean thswitch = true;
	private boolean isthisactivity = true;
	public static boolean mdispause;

	private String currentdate = "", lastdate;

	/** 记录需要合成的几段amr语音文件 **/
	private ArrayList<String> list;
	/** 是否暂停标志位 **/
	public static boolean isPause;

	/** 在暂停状态中 **/
	private boolean inThePause;
	private final String SUFFIX = ".amr";
	// private int twice;
	File file1;
String filepath;
private Details dt= new Details(); 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mydazui);

		// 初始化
		init();
		// 暂停标志位 为false
		isPause = false;
		// 暂停状态标志位
		inThePause = false;

		handler1 = new Handler(this);

		Intent it = getIntent();
		tagfromdz = it.getIntExtra("tag", 0);
		// 录音按钮监听
		dazuiluyin.setOnClickListener(this);
		// 播放按钮监听
		bfmbnt.setOnClickListener(this);
		// 上传语音
		dazuiscbnt.setOnClickListener(this);
		// 重录语音
		dazuiclbnt.setOnClickListener(this);
		ztbf.setOnClickListener(this);
		//

		// 顶
		listener_osc = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stopPlay();
				try {				
				if (mylist.size() != 0 || savelist.size() != 0) {
					if (lastceshi == 1) {
						mylist.get(bfposition).setStartorstop(0);
						mylist.get(bfposition).setJindubg(0);
						lv.requestLayout();
						ad.notifyDataSetChanged();
					} else if (lastceshi == 2) {
						savelv.requestLayout();
						savelist.get(bfposition).setStartorstop(0);
						savelist.get(bfposition).setJindubg(0);
						savead.notifyDataSetChanged();
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
				it.setClass(Mydazui.this, Dzmysave.class);
				startActivity(it);
				
				} catch (Exception e) {
					// TODO: handle exception
					Commond.showToast(Mydazui.this, "超时了！请重新进入大嘴！");
				}
			}			
		};
		// 踩
		listener_liwu = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				holder = (DazuiAdapter2.ViewHolder) v.getTag();
				mdispause = true;
				Intent it = new Intent();
				it.putExtra("fuid", MainMenuActivity.uid);
				it.putExtra("uid", String.valueOf(holder.ouid));
				it.putExtra("chatroom", "1");
				it.putExtra("src", 3);
				if (whichpage == 1) {
					it.putExtra("oid", mylist.get(holder.position).getId());
				} else if (whichpage == 2) {
					it.putExtra("oid", savelist.get(holder.position).getId());
				}
				it.setClass(Mydazui.this, VillageUserInfoDialog.class);
				startActivity(it);
			}
		};
		// 收藏
		listener_save = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				holder = (DazuiAdapter2.ViewHolder) v.getTag();
				if (whichpage == 1) {
					if (mylist.get(holder.position).getIsfav() == 0) {
						listener_save(mylist, lv, 0, holder.position);
					} else if (mylist.get(holder.position).getIsfav() == 1) {
						listener_save(mylist, lv, 1, holder.position);
					}
				} else if (whichpage == 2) {
					if (savelist.get(holder.position).getIsfav() == 0) {
						listener_save(savelist, savelv, 0, holder.position);
					} else if (savelist.get(holder.position).getIsfav() == 1) {
						listener_save(savelist, savelv, 1, holder.position);
					}
				}
			}
		};
		listener_share = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				holder = (DazuiAdapter2.ViewHolder) v.getTag();
				if (whichpage == 1) {
					showShare(false, mylist, holder.position , null);
				} else if (whichpage == 2) {
					showShare(false, savelist, holder.position , null);
				}
			}
		};

		// 每项按钮事件
		listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				holder = (DazuiAdapter2.ViewHolder) v.getTag();
				if (whichpage == 1) {
					bflisener(mylist, ad, po1, bofangurl1, whichpage, lv);
					ceshi = 1;
				} else if (whichpage == 2) {
					bflisener(savelist, savead, po2, bofangurl2, whichpage,
							savelv);
					ceshi = 2;
				}
			}
		};

		// 每项删除按钮事件
		listener2 = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				DazuiAdapter2.ViewHolder holder = (DazuiAdapter2.ViewHolder) v
						.getTag();
				yuyinll.setVisibility(View.VISIBLE);
				yuyinpro.setVisibility(View.VISIBLE);
				String url = "http://jrc.hutudan.com/music/cancel_item.php";
				scswitch = true;
				String urltwo = dt.appendNameValue(url, "uid",
						MainMenuActivity.uid);
				urltwo = Details.appendNameValueint(urltwo, "mid",
						mylist.get(holder.position).getId());
				StringBuffer data = new StringBuffer();
				// 请求网络验证登陆
				HttpRequestTask request = new HttpRequestTask();
				request.execute(urltwo, data.toString());
			}
		};
		// 播放拖动条。。。
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				getseek(seekBar.getProgress() * 1000);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// puse();
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				begintv.setText(String.format("%02d:%02d", progress / 60,
						progress % 60));

			}
		});

		hd = new Handler() {
			public void handleMessage(android.os.Message mes) {
				if (mes.what == message_what_position) {
					seekbar.setProgress(mes.arg1);
				}
			}
		};

		// 每个页面的view数据
		views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);

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
		if (tagfromdz == 2) {

			mTabPager.setCurrentItem(0, true);
		} else if (tagfromdz == 1) {
			mTabPager.setCurrentItem(1, true);
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
	 * 页卡切换监听(原作者:D.Winter)
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				whichpage = 0;
				luzhibnt.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.luzhiyuyinsel));
				mybnt.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.myyuyin1));
				shoucangyuyin.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.mydazui_shoucang1));
				delete.setVisibility(View.GONE);
				if (scswitch == true) {
					scswitch = false;
					bton.setBackgroundResource(R.drawable.garbage1s);
					lv.setAdapter(new DazuiAdapter2(Mydazui.this, mylist, lv,
							null, listener2, scswitch, listener_save,
							listener_share, listener_osc, listener_liwu));
				}
				break;
			case 1:
				whichpage = 1;
				luzhibnt.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.luzhiyuyin1));
				mybnt.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.mydz_myyuyin_select));
				shoucangyuyin.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.mydazui_shoucang1));
				delete.setVisibility(View.VISIBLE);
				if (istwice == false) {
					istwice = true;
					mylist.clear();
					String url = "http://jrc.hutudan.com/music/list.php?flg=2";
					url = Details.geturl(url);
					StringBuffer data = new StringBuffer();
					HttpRequestTask request = new HttpRequestTask();
					request.execute(url, data.toString());
				}
				break;
			case 2:
				whichpage = 2;
				luzhibnt.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.luzhiyuyin1));
				mybnt.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.myyuyin1));
				shoucangyuyin.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.mydz_save_select));
				delete.setVisibility(View.GONE);
				if (istwice2 == false) {
					istwice2 = true;
					savelist.clear();
					String url = "http://jrc.hutudan.com/music/list.php?flg=3";
					url = Details.geturl(url);
					StringBuffer data = new StringBuffer();
					HttpRequestTask request = new HttpRequestTask();
					request.execute(url, data.toString());
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

	// 返回按钮监听
	public void dazui_back(View v) { // 返回按钮
		if (isRecord == true) {
			Intent it = new Intent();
			it.putExtra("dzwhich", 0);
			it.putExtra("dzts", "正在录音中是否要退出？");
			it.setClass(Mydazui.this, Dztishidialog.class);
			startActivityForResult(it, 7);
		} else if (scandclll.getVisibility() == View.VISIBLE) {
			Intent it = new Intent();
			it.putExtra("dzwhich", 1);
			it.putExtra("dzts", "确定放弃上传？");
			it.setClass(Mydazui.this, Dztishidialog.class);
			startActivityForResult(it, 8);

		} else {
			finish();
			mdispause = false;
			isplay = false;
			stopPlay();
		}

	}

	// shan chu ...
	public void yysc_garbage(View v) {
		bton = (Button) v;
		if (scswitch == false) {
			scswitch = true;
			v.setBackgroundResource(R.drawable.canclegarbage1);
			lv.setAdapter(new DazuiAdapter2(Mydazui.this, mylist, lv, null,
					listener2, scswitch, listener_save, listener_share,
					listener_osc, listener_liwu));
		} else {
			scswitch = false;
			v.setBackgroundResource(R.drawable.garbage1s);
			lv.setAdapter(new DazuiAdapter2(Mydazui.this, mylist, lv, null,
					listener2, scswitch, listener_save, listener_share,
					listener_osc, listener_liwu));
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			// if(whichpage == 1){
			if (view.getLastVisiblePosition() == view.getCount() - 1) {
				String nn = "";

				if (whichpage == 1) {
					if (TextUtils.isEmpty(mynlink)) {
						lv.removeFooterView(vFooter);
						return;
					}
				}

				if (whichpage == 2) {
					if (TextUtils.isEmpty(savenlink)) {
						savelv.removeFooterView(vFooter);
						return;
					}
				}

				if (whichpage == 1) {
					nn = mynlink;
				} else if (whichpage == 2) {
					nn = savenlink;
				}
				if (!dllinks.contains(nn)) {
					twice = 3;
					loading.setVisibility(View.VISIBLE);
					String url = nn;
					StringBuffer data = new StringBuffer();
					//
					data.append("&pd=");
					lastdate = currentdate;
					data.append(URLEncoder.encode(lastdate));
					// 请求网络验证登陆
					// nlink2 = geturl(url);
					nn = Details.geturl(nn);
					HttpRequestTask request = new HttpRequestTask();
					request.execute(nn, data.toString());
					dllinks.add(nn);
				}
			}

			// }
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		/** 调节媒体音量 **/
		switch (keyCode) {
		// case KeyEvent.KEYCODE_VOLUME_UP:
		// audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
		// AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND
		// | AudioManager.FLAG_SHOW_UI);
		// return true;
		// case KeyEvent.KEYCODE_VOLUME_DOWN:
		// audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
		// AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND
		// | AudioManager.FLAG_SHOW_UI);
		// return true;
		case KeyEvent.KEYCODE_BACK:
			if (isRecord == true) {
				Intent it = new Intent();
				it.putExtra("dzwhich", 0);
				it.putExtra("dzts", "正在录音中是否要退出？");
				it.setClass(Mydazui.this, Dztishidialog.class);
				startActivityForResult(it, 7);
			} else if (scandclll.getVisibility() == View.VISIBLE) {
				Intent it = new Intent();
				it.putExtra("dzwhich", 1);
				it.putExtra("dzts", "确定放弃上传？");
				it.setClass(Mydazui.this, Dztishidialog.class);
				startActivityForResult(it, 8);

			} else {
				finish();
				mdispause = false;
				isplay = false;
				stopPlay();
			}
			return true;
		default:
			break;
		}
		return false;
	}

	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				jishiqi++;
				timelong.setText(jishiqi + "'");
				break;
			}
			super.handleMessage(msg);
		}
	};

	private void init() {
		task = new TimerTask() {
			public void run() {
				if (run) {
					Message message = new Message();
					message.what = 1;
					handler.sendMessage(message);
				}
			}
		};

		list = new ArrayList<String>();
		dllinks = new ArrayList<String>();
		mylist = new ArrayList<DazuiIteminfo>();
		savelist = new ArrayList<DazuiIteminfo>();
		lsfileurl = new ArrayList<String>();
		bofangurl = new ArrayList<String>();
		bls = new ArrayList<Boolean>();
		po = new ArrayList<Integer>();
		po1 = new ArrayList<Integer>();
		po2 = new ArrayList<Integer>();
		bofangurl1 = new ArrayList<String>();
		bofangurl2 = new ArrayList<String>();
		lsbofangurl = new ArrayList<String>();
		lspo = new ArrayList<Integer>();
		lslist = new ArrayList<DazuiIteminfo>();

		delete = (Button) findViewById(R.id.dazuidelete);
		delete.setVisibility(View.GONE);
		mLi = LayoutInflater.from(this);
		audio = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
		luzhibnt = (Button) findViewById(R.id.luzhiyuyin);
		shoucangyuyin = (Button) findViewById(R.id.wodeshoucang);

		mybnt = (Button) findViewById(R.id.jingcaiyuyin);
		mTabPager = (ViewPager) findViewById(R.id.dazuitabpager2);
		mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());

		// 按钮声音
		myMediaStart = MediaPlayer.create(getApplicationContext(),
				R.raw.ptt_startrecord);
		myMediaSend = MediaPlayer.create(getApplicationContext(),
				R.raw.sent_message);
		// 实例化录音
		vr = new VideoRecord();
		vrdz = new VideoRecordDz2(Mydazui.this, null);
		vrdz.init();
		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);

		luzhibnt.setOnClickListener(new MyOnClickListener(0));
		mybnt.setOnClickListener(new MyOnClickListener(1));
		shoucangyuyin.setOnClickListener(new MyOnClickListener(2));

		vFooter = LayoutInflater.from(this).inflate(R.layout.list_footer, null);
		loading = (LinearLayout) vFooter.findViewById(R.id.list_footer);
		loading.setVisibility(View.GONE);

		view1 = mLi.inflate(R.layout.luzhiyuyin, null);
		seekbar = (SeekBar) view1.findViewById(R.id.seekbar);
		dazuifresh2 = (Button) view1.findViewById(R.id.dazuifresh);
		begintv = (TextView) view1.findViewById(R.id.begintm);
		endtv = (TextView) view1.findViewById(R.id.endtm);
		bfmbnt = (Button) view1.findViewById(R.id.bfmbnt);
		ztbf = (ImageButton) view1.findViewById(R.id.zantingorbofang);
		dazuiscbnt = (Button) view1.findViewById(R.id.dazuiscbnt);
		dazuiclbnt = (Button) view1.findViewById(R.id.dazuiclbnt);
		seekll = (LinearLayout) view1.findViewById(R.id.dazuill);
		scandclll = (LinearLayout) view1.findViewById(R.id.scandcl);
		timelong = (TextView) view1.findViewById(R.id.timelong);
		time2 = (TextView) view1.findViewById(R.id.time2);
		// bfmbnt.setText("bofang");
		dazuiluyin = (ImageButton) view1.findViewById(R.id.bigbnt);
		ztbf.setVisibility(View.GONE);

		view2 = mLi.inflate(R.layout.myyuyin, null);
		mydazuisc = (ListView) view2.findViewById(R.id.dazuimyyuyinlv);
		yuyinll = (LinearLayout) view2.findViewById(R.id.dazui_ll3);
		yuyinpro = (ProgressBar) view2.findViewById(R.id.dazui_myyuyin);
		lv = (ListView) view2.findViewById(R.id.dazuimyyuyinlv);
		danosc = (ImageView) view2.findViewById(R.id.dznosc);

		lv.addFooterView(vFooter);
		lv.setOnScrollListener(this);

		view3 = mLi.inflate(R.layout.mysavedz, null);
		mydazuisc = (ListView) view3.findViewById(R.id.mysavelvdz);
		savell = (LinearLayout) view3
				.findViewById(R.id.village_leftlist_progressbar_RelativeLayout6dz);
		savepro = (ProgressBar) view3.findViewById(R.id.mysavepbdz);
		savelv = (ListView) view3.findViewById(R.id.mysavelvdz);
		danosave = (ImageView) view3.findViewById(R.id.nosavedz);

		savelv.addFooterView(vFooter);
		savelv.setOnScrollListener(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == 13) {
			cxlz();
			begainluzhi();
		}
		if (resultCode == 14) {
			String tip = data.getStringExtra("tip");
			Commond.showToast(Mydazui.this, tip);
			istwice = false;
			cxlz();
		}
		if (resultCode == 16) {
			if(vrdz.mMediaRecorder01 != null){
			vrdz.stop();
			}
			aa.reset();
			dazuiluyin.clearAnimation();
			// aa.reset();
			run = false;
			timer.cancel();
			finish();
		} else if (resultCode == 17) {
			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	class update implements Runnable {

		@Override
		public void run() {
			while (hd != null && vr.mPlayer != null
					&& seekbar.getProgress() < vr.mPlayer.getDuration() / 1000) {
				if (isplay) {
					Message message = hd.obtainMessage();
					message.arg1 = vr.mPlayer.getCurrentPosition() / 1000;
					message.what = message_what_position;
					hd.sendMessage(message);
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	// 重新录制、、、、、、、、、、/////////////////
	private void cxlz() {
		seekll.setVisibility(View.GONE);
		timelong.setVisibility(View.VISIBLE);
		time2.setVisibility(View.VISIBLE);
		bfmbnt.setVisibility(View.GONE);
		scandclll.setVisibility(View.GONE);
		ztbf.setVisibility(View.VISIBLE);
		ztbf.setBackgroundResource(R.drawable.zantingbnt1);
		file1 = null ;
		// aa.cancel();
		aa.reset();
		// 暂停标志位 为false
		isPause = false;
		list.clear();
		// 暂停状态标志位
		inThePause = false;
		dazuiluyin.clearAnimation();
		jishiqi = 0;
		time2.setBackgroundResource(R.drawable.luyintv);
		run = false;
		timer.cancel();
		timelong.setText("");
		begintv.setText("00:00");
		task = new TimerTask() {
			public void run() {
				if (run) {
					Message message = new Message();
					message.what = 1;
					handler.sendMessage(message);
				}
			}
		};
	}

	private void begainluzhi() {

		timelong.setText("0'");
		if (myMediaStart == null) {
			// 按钮声音
			myMediaStart = MediaPlayer.create(
					getApplicationContext(), R.raw.ptt_startrecord);
			myMediaSend = MediaPlayer.create(
					getApplicationContext(), R.raw.sent_message);
		}
		try {
			myMediaStart.start();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		begintime = System.currentTimeMillis();
		dazuiluyin.setImageResource(R.drawable.luing);
		vrdz.start();
		run = true;
		timer = new Timer(true);
		timer.schedule(task, 1000, 1000);
		// 图片渐变模糊度始终
		aa = new AlphaAnimation(0.9f, 1.0f);
		// 渐变时间
		aa.setDuration(ANIMATION_TIME);
		aa.setRepeatCount(Animation.INFINITE);
		aa.setRepeatMode(Animation.RESTART);
		// 展示图片渐变动画
		dazuiluyin.startAnimation(aa);
		isRecord = true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == dazuiluyin) {
			if (seekll.getVisibility() == View.GONE) {				
				if (isRecord == false) {
					if (list.size() != 0) {
						list.clear();
					}
					ztbf.setVisibility(View.VISIBLE);
					ztbf.setBackgroundResource(R.drawable.zantingbnt1);
					time2.setBackgroundResource(R.drawable.luyintv);
					if (myMediaStart == null) {
						// 按钮声音
						myMediaStart = MediaPlayer.create(
								getApplicationContext(), R.raw.ptt_startrecord);
						myMediaSend = MediaPlayer.create(
								getApplicationContext(), R.raw.sent_message);
					}
					try {
						myMediaStart.start();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					
					begintime = System.currentTimeMillis();
					dazuiluyin.setImageResource(R.drawable.luing);
					vrdz.start();
					run = true;
					timer = new Timer(true);
					timer.schedule(task, 1000, 1000);
					// 图片渐变模糊度始终
					aa = new AlphaAnimation(0.9f, 1.0f);
					// 渐变时间
					aa.setDuration(ANIMATION_TIME);
					aa.setRepeatCount(Animation.INFINITE);
					aa.setRepeatMode(Animation.RESTART);
					// 展示图片渐变动画
					dazuiluyin.startAnimation(aa);
					isRecord = true;

				}
				// 结束录音
				else if (isRecord == true) {
					if (myMediaSend == null) {
						// 按钮声音
						myMediaStart = MediaPlayer.create(
								getApplicationContext(), R.raw.ptt_startrecord);
						myMediaSend = MediaPlayer.create(
								getApplicationContext(), R.raw.sent_message);
					}
					if (myMediaSend != null) {
						myMediaSend.start();
					}
					ztbf.setVisibility(View.GONE);

					if (isPause) {

						// 在暂停状态按下结束键,处理list就可以了
						if (inThePause) {
							getInputCollection(list, false);
						}
						// 在正在录音时，处理list里面的和正在录音的语音
						else {
							list.add(su.getAudioPath());
							getInputCollection(list, true);
						}

						// 还原标志位
						isPause = false;
						inThePause = false;
						ztbf.setBackgroundResource(R.drawable.zantingbnt1);
						aa.reset();
						if(vrdz.mMediaRecorder01 != null){
						vrdz.stop();
						}
						timer.cancel();
						dazuiluyin.clearAnimation();
						run = false;
						endtime = System.currentTimeMillis();
						dazuiluyin
								.setImageResource(android.R.color.transparent);
						// adapter.add(myRecAudioFile.getName());
						isRecord = false;
						timelong.setVisibility(View.GONE);
						try {
							vr.stopandper(file1.getAbsolutePath());
						} catch (Exception e) {
							// TODO: handle exception
							finish();
						}

						length = vr.mPlayer.getDuration() / 1000;

						if (length < 3) {
							Commond.showToast(Mydazui.this, "不够3秒,不可以上传！");
							cxlz();
							return;
						}

						seekll.setVisibility(View.VISIBLE);
					} else {
						if(vrdz.mMediaRecorder01 != null){
						vrdz.stop();
						}
						// aa.cancel();
						aa.reset();
						dazuiluyin.clearAnimation();
						run = false;
						timer.cancel();
						endtime = System.currentTimeMillis();
						dazuiluyin
								.setImageResource(android.R.color.transparent);
						isRecord = false;
						timelong.setVisibility(View.GONE);
						try {
							vr.stopandper(su.getAudioPath());
						} catch (Exception e) {
							// TODO: handle exception
							finish();
						}

						length = vr.mPlayer.getDuration() / 1000;

						if (length < 3) {
							Commond.showToast(Mydazui.this, "不够3秒,不可以上传！");
							cxlz();
							return;
						}

						seekll.setVisibility(View.VISIBLE);
					}

					time2.setVisibility(View.GONE);
					bfmbnt.setVisibility(View.VISIBLE);
					scandclll.setVisibility(View.VISIBLE);

					// kk = Comment.calculatTime( (int)(endtime - begintime) );
					// endtv.setText(kk);

					endtv.setText(String.format("%02d:%02d", length / 60,
							length % 60));
					seekbar.setMax(length);
					// 还原标志位
					isPause = false;
					inThePause = false;
					ztbf.setBackgroundResource(R.drawable.zantingbnt1);
				}
			} else {
				Commond.showToast(Mydazui.this, "请先上传！或点击重录！");

			}
		}
		if (v == ztbf) {
			isPause = true;

			// 已经暂停过了，再次点击按钮 开始录音，录音状态在录音中
			if (inThePause) {
				ztbf.setVisibility(View.VISIBLE);
				ztbf.setBackgroundResource(R.drawable.zantingbnt1);
				myMediaStart.start();
				begintime = System.currentTimeMillis();
				dazuiluyin.setImageResource(R.drawable.luing);
				vrdz.start();
				run = true;
				// timer = new Timer(true);
				// timer.schedule(task, 1000, 1000);
				// 图片渐变模糊度始终
				aa = new AlphaAnimation(0.9f, 1.0f);
				// 渐变时间
				aa.setDuration(ANIMATION_TIME);
				aa.setRepeatCount(Animation.INFINITE);
				aa.setRepeatMode(Animation.RESTART);
				// 展示图片渐变动画
				dazuiluyin.startAnimation(aa);
				isRecord = true;
				inThePause = false;

			}
			// 正在录音，点击暂停,现在录音状态为暂停
			else {
				if(isRecord == false){
					Commond.showToast(this, "还木有开始录音哦！");
					return ;
				}
				try {
				// 当前正在录音的文件名，全程
				list.add(su.getAudioPath());
				inThePause = true;
				if(vrdz.mMediaRecorder01 != null){
				vrdz.stop();
				}
				// start();
				ztbf.setVisibility(View.VISIBLE);
				ztbf.setBackgroundResource(R.drawable.jixubofangbnt1);
				// aa.cancel();
				aa.reset();
				dazuiluyin.clearAnimation();
				run = false;
				// timer.cancel();
				endtime = System.currentTimeMillis();
				dazuiluyin.setImageResource(android.R.color.transparent);
				isRecord = true;
				// timelong.setVisibility(View.GONE);				
					vr.stopandper(su.getAudioPath());
				} catch (Exception e) {
					// TODO: handle exception
					finish();
				}
			}
		}
		if (v == bfmbnt) {
			if (bfswitch == false) {
				if (jixu == true) {
					if (!vr.mPlayer.isPlaying()) {
						vr.mPlayer.start();
						v.setBackgroundResource(R.drawable.mybofang);
					}
				} else {
					if (th2 == null) {
						th2 = new Thread(new update());
						th2.start();
					}
					vr.startPlaying();

				}
				isplay = true;
				v.setBackgroundResource(R.drawable.myzanting);
				bfswitch = true;
				vr.mPlayer.setOnCompletionListener(new OnCompletionListener() {

					@Override
					public void onCompletion(MediaPlayer mp) {
						// vr.stopPlaying(su.getAudioPath());
						timer.cancel();
						jishiqi = 0;
						bfmbnt.setBackgroundResource(R.drawable.mybofang);
						bfswitch = false;
						jixu = false;
						seekbar.setProgress(0);
					}
				});
			} else if (bfswitch == true) {
				isplay = false;
				if (null != vr.mPlayer) {
					if (vr.mPlayer.isPlaying()) {
						vr.mPlayer.pause();
						v.setBackgroundResource(R.drawable.mybofang);
					}
				}

				bfswitch = false;
			}
		}
		if (v == dazuiscbnt) {
			mdispause = true;
			Intent it = new Intent();
			it.setClass(Mydazui.this, Luyindialog.class);
			it.putExtra("lylen", length);
			if(file1 == null){
				filepath = su.getAudioPath();
				Log.e("su.get", filepath);
			}else{
				filepath = file1.getAbsolutePath();
				Log.e("file1.get", filepath);
			}
			it.putExtra("path", filepath);
			startActivityForResult(it, 5);
		}
		if (v == dazuiclbnt) {
			mdispause = true;
			Intent it = new Intent();
			it.setClass(Mydazui.this, Makesure.class);
			startActivityForResult(it, 4);

		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		jixu = false;
		mdispause = false;
		try {
		if (scisnoty == true) {
			if (ad != null) {
				ad.notifyDataSetChanged();
				scisnoty = false;
			}
		}
		if (EditPage.isshare == true) {
			if (whichpage == 1) {
				if (EditPage.isshare == true) {
					mylist.get(holder.position).setSc(
							mylist.get(holder.position).getSc() + 1);
					lv.requestLayout();
					ad.notifyDataSetChanged();
				}
			} else if (whichpage == 2) {
				if (EditPage.isshare == true) {
					savelist.get(holder.position).setSc(
							savelist.get(holder.position).getSc() + 1);
					savelv.requestLayout();
					savead.notifyDataSetChanged();
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
		
		if ((mylist.size() != 0 || savelist.size() != 0) && player == null) {
			if (lastceshi == 1) {
				mylist.get(bfposition).setStartorstop(0);
				mylist.get(bfposition).setJindubg(0);
				lv.requestLayout();
				ad.notifyDataSetChanged();
			} else if (lastceshi == 2) {
				savelv.requestLayout();
				savelist.get(bfposition).setStartorstop(0);
				savelist.get(bfposition).setJindubg(0);
				savead.notifyDataSetChanged();
			}
			if (player != null) {
				player.release();
				player = null;
			}
		}
		
		if (player != null) {
			if (MainMenuActivity.borz == true) {
				player.start();
			}
		}
		
	} catch (Exception e) {
		// TODO: handle exception
		Commond.showToast(Mydazui.this, "遇到问题了，请返回 然后重新进入！");
	}
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (player != null) {
			player.release();
			player = null;
		}
		if (vr.mPlayer != null) {
			vr.mPlayer = null;
		}
		hd = null;
		scisnoty = false;
		mdispause = false;
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
//		if (isRecord == true) {
//			isPause = true;
//			// 已经暂停过了，再次点击按钮 开始录音，录音状态在录音中
//
//			// 正在录音，点击暂停,现在录音状态为暂停
//			if (!inThePause) {
//				// 当前正在录音的文件名，全程
//				list.add(su.getAudioPath());
//				inThePause = true;
//				if(vrdz.mMediaRecorder01 != null){
//				vrdz.stop();
//				}
//				// start();
//				ztbf.setVisibility(View.VISIBLE);
//				ztbf.setBackgroundResource(R.drawable.jixubofangbnt1);
//				// aa.cancel();
//				aa.reset();
//				dazuiluyin.clearAnimation();
//				run = false;
//				// timer.cancel();
//				endtime = System.currentTimeMillis();
//				dazuiluyin.setImageResource(android.R.color.transparent);
//				isRecord = true;
//				// timelong.setVisibility(View.GONE);
//				try {
//					vr.stopandper(su.getAudioPath());
//				} catch (Exception e) {
//					// TODO: handle exception
//					finish();
//				}
//			}
//		}

		if (player != null) {
			if (mdispause == false) {
				if (player.isPlaying()) {
					player.pause();
				}
			}
		}
		super.onPause();
	}

	public void getseek(int pos) {
		if (vr.mPlayer != null) {
			vr.mPlayer.seekTo(pos);
		}
		if (jixu == true) {
			if (!vr.mPlayer.isPlaying()) {
				vr.mPlayer.start();
			}
		}
	}

	@Override
	void resultData(String url, String result) {
		// TODO Auto-generated method stub
		String tip = null;

		if (result == null) {
			// fresh.setVisibility(View.VISIBLE);
			Commond.showToast(Mydazui.this, "小贱提醒 ：当前网络不稳定！");
			return;
		}

		try {
			// //////////////////////////////////////////////正文内容页面
			JSONObject jsonChannel = new JSONObject(result);
			int ret = jsonChannel.optInt("ret");
			tip = URLDecoder.decode(jsonChannel.optString("tip"));
			String nlink = URLDecoder.decode(jsonChannel.optString("nlink"));
			if (ret == 0) {
				Commond.showToast(Mydazui.this, tip);
				return;
			}
			lastdate = URLDecoder.decode(jsonChannel.optString("pd"));
			JSONArray jsonItems = jsonChannel.optJSONArray("items");
			if (jsonItems != null) {
				// tip = "获取成功！";
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
					String nameclor = URLDecoder.decode(jsonItem
							.optString("unick_c"));
					int ccount = jsonItem.optInt("ccount");
					int status = jsonItem.optInt("status");
					int fc = jsonItem.optInt("fc");
					int sc = jsonItem.optInt("sc");
					int uc = jsonItem.optInt("uc");
					int dc = jsonItem.optInt("dc");
					int tc = jsonItem.optInt("tc");
					int gc = jsonItem.optInt("gc");
					int isfav = jsonItem.optInt("isfav");
					int type = jsonItem.optInt("bg");

					if (url.contains("flg=2")) {
						DazuiIteminfo item = new DazuiIteminfo(title, size,
								date, id, afile, uid, uhead, unick, len,
								ccount, status, 0, isfav, nameclor, fc, sc, uc,
								dc, 0, flg_pic, tc, gc , type);
						mylist.add(item);
						mynlink = nlink;
					} else if (url.contains("flg=3")) {
						DazuiIteminfo item = new DazuiIteminfo(title, size,
								date, id, afile, uid, uhead, unick, len,
								ccount, -1, 0, isfav, nameclor, fc, sc, uc, dc,
								0, flg_pic, tc, gc , type);
						savenlink = nlink;
						savelist.add(item);
					}
					Boolean bl = false;
					bls.add(bl);
				}
			}
			if (url.contains("http://jrc.hutudan.com/music/list.php")) {
				if (url.contains("flg=2")) {
					if (twice == 0) {
						ad = new DazuiAdapter2(Mydazui.this, mylist, lv,
								listener, listener2, scswitch, listener_save,
								listener_share, listener_osc, listener_liwu);
						yuyinll.setVisibility(View.GONE);
						yuyinpro.setVisibility(View.GONE);
						if (mylist.size() == 0) {
							danosc.setVisibility(View.VISIBLE);
						} else {
							danosc.setVisibility(View.GONE);
						}
						lv.setAdapter(ad);
					} else if (twice == 3) {
						lv.requestLayout();
						ad.notifyDataSetChanged();
					}
				} else if (url.contains("flg=3")) {
					savead = new DazuiAdapter2(Mydazui.this, savelist, savelv,
							listener, listener2, false, listener_save,
							listener_share, listener_osc, listener_liwu);
					savell.setVisibility(View.GONE);
					savepro.setVisibility(View.GONE);
					if (savelist.size() == 0) {
						danosave.setVisibility(View.VISIBLE);
					} else {
						danosave.setVisibility(View.GONE);
					}
					if (savelv.getAdapter() == null) {
						savelv.setAdapter(savead);
					} else {
						savelv.requestLayout();
//						 ad1.notifyDataSetChanged(); // 数据集变化后,通知adapter
					}
				}
			}
			if (url.contains("http://jrc.hutudan.com/music/support.php")) {
				if (url.contains("flg=0")) {
					if (whichpage == 1) {
						if (ad != null) {
							lv.requestLayout();
							mylist.get(holder.position).setUc(
									mylist.get(holder.position).getUc() + 1);
							ad.notifyDataSetChanged();
						}
					} else if (whichpage == 2) {
						if (savead != null) {
							savelv.requestLayout();
							savelist.get(holder.position).setUc(
									savelist.get(holder.position).getUc() + 1);
							savead.notifyDataSetChanged();
						}
					}
				} else if (url.contains("flg=1")) {
					if (whichpage == 1) {
						if (ad != null) {
							lv.requestLayout();
							mylist.get(holder.position).setDc(
									mylist.get(holder.position).getDc() + 1);
							ad.notifyDataSetChanged();
						}
					} else if (whichpage == 2) {
						if (savead != null) {
							savelv.requestLayout();
							savelist.get(holder.position).setDc(
									savelist.get(holder.position).getDc() + 1);
							savead.notifyDataSetChanged();
						}
					}
				}

			}

			if (url.contains("http://jrc.hutudan.com/music/fav.php")) {
				if (url.contains("type=0")) {
					saveorquxiao(1);
					if (whichpage == 1) {
						mylist.get(holder.position).setIsfav(1);
					} else if (whichpage == 2) {
						savelist.get(holder.position).setIsfav(1);
					}
				} else if (url.contains("type=1")) {
					saveorquxiao(-1);
					if (whichpage == 1) {
						mylist.get(holder.position).setIsfav(0);
					} else if (whichpage == 2) {
						savelist.get(holder.position).setIsfav(0);
					}
				}
			}
			if (url.contains("http://jrc.hutudan.com/music/cancel_item.php")) {
				Commond.showToast(Mydazui.this, tip);
				mylist.clear();
				scswitch = true;
				yuyinll.setVisibility(View.VISIBLE);
				yuyinpro.setVisibility(View.VISIBLE);
				String urlagain = "http://jrc.hutudan.com/music/list.php?flg=2";
				urlagain = Details.geturl(urlagain);
				StringBuffer data = new StringBuffer();
				HttpRequestTask request = new HttpRequestTask();
				request.execute(urlagain, data.toString());
			}

		} catch (Exception e) {
			// TODO: handle exception
			yuyinll.setVisibility(View.GONE);
			lv.setVisibility(View.GONE);
			Commond.showToast(Mydazui.this, "操作失败！请检查网络！");
		}
	}

	// 播放按钮监听方法
	// ////////////////////////////
	// ////////////
	private void bflisener(List<DazuiIteminfo> list, DazuiAdapter2 da1,
			List<Integer> pos, List<String> bofangurls, int which, ListView lv1) {
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

					File picfile = new File(MainMenuActivity.dazuisdown
							+ File.separator + Comment.getMd5Hash(fileurl)
							+ ".amr");
					String filename = picfile.getPath().toString();
					if (picfile.exists()) {
						if (player != null) {
							if (DazuiActivity.player != null) {
								DazuiActivity.player.release();
								DazuiActivity.player = null;
							}
							player.release();
							player = null;
						}
						if (DazuiAdapter2.th != null) {
							DazuiAdapter2.th = null;
						}

						list.get(position).setStartorstop(1);
						list.get(position).setJindubg(1);
						lv1.requestLayout();
						da1.notifyDataSetChanged();
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
				File picfile = new File(MainMenuActivity.dazuisdown
						+ File.separator + Comment.getMd5Hash(fileurl) + ".amr");
				String filename = picfile.getPath().toString();
				if (picfile.exists()) {
					if (player != null) {
						if (DazuiActivity.player != null) {
							DazuiActivity.player.release();
							DazuiActivity.player = null;
						}
						player.release();
						player = null;
					}
					if (DazuiAdapter2.th != null) {
						DazuiAdapter2.th = null;
					}

					list.get(position).setStartorstop(1);
					list.get(position).setJindubg(1);
					lv1.requestLayout();
					da1.notifyDataSetChanged();
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
				case 1:
					lslist = mylist;
					lspo = po1;
					ik = 1;
					lslv = lv;
					lsbofangurl = bofangurl1;
					break;
				case 2:
					lslist = savelist;
					lspo = po2;
					ik = 2;
					lslv = savelv;
					lsbofangurl = bofangurl2;
					break;
				}
				if (lslist.size() == 0) {
					return;
				} else {
					if (lslist.size() == 0 || lslist == null) {
						finish();
						return;
					}
					lslist.get(position).setStartorstop(0);
					lslist.get(position).setJindubg(0);
					lslv.requestLayout();
					if (ik == 1) {
						ad.notifyDataSetChanged();
					} else if (ik == 2) {
						savead.notifyDataSetChanged();
					}
				}
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
					lv1.requestLayout();
					da1.notifyDataSetChanged();
					startPlaying(filename, position, which);
					bfposition = position;
					issing = true;
					fistpage = which;
				}
				// 在另一个页面播放
				else {
					if (player != null) {
						if (DazuiActivity.player != null) {
							DazuiActivity.player.release();
							DazuiActivity.player = null;
						}
						player.release();
						player = null;
					}
					if (DazuiAdapter2.th != null) {
						DazuiAdapter2.th = null;
					}

					list.get(position).setStartorstop(1);
					list.get(position).setJindubg(1);
					DazuiIteminfo it = list.get(position);
					lv1.requestLayout();
					da1.notifyDataSetChanged();
					startPlaying(filename, position, which);
					issing = true;

					switch (lastceshi) {
					case 1:
						lslist = mylist;
						lspo = po1;
						ik = 1;
						lslv = lv;
						lsbofangurl = bofangurl1;
						break;
					case 2:
						lslist = savelist;
						lspo = po2;
						ik = 2;
						lslv = savelv;
						lsbofangurl = bofangurl2;
						break;
					}
					if (lslist.size() == 0) {
						return;
					} else {
						lslist.get(bfposition).setStartorstop(0);
						lslist.get(bfposition).setJindubg(0);
						lslv.requestLayout();
						if (ik == 1) {
							ad.notifyDataSetChanged();
						} else if (ik == 2) {
							savead.notifyDataSetChanged();
						}
					}
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

	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			String str2 = msg.getData().getString("fileurl");// 接受msg传递过来的参数
			int whichlist = msg.getData().getInt("whichlist");// 接受msg传递过来的参数
			switch (msg.what) {
			case 0:
				// lslist.clear();
				switch (whichlist) {
				case 1:
					lslist = mylist;
					lspo = po1;
					ik = 1;
					lsbofangurl = bofangurl1;
					lslv = lv;
					break;
				case 2:
					lslist = savelist;
					lspo = po2;
					ik = 2;
					lsbofangurl = bofangurl2;
					lslv = savelv;
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
						if (ik == 1) {
							ad.notifyDataSetChanged();
						} else if (ik == 2) {
							savead.notifyDataSetChanged();
						}
					}
				}

				if (ceshi == whichlist) {
					switch (ceshi) {
					case 1:
						lslist = mylist;
						lspo = po1;
						ik = 1;
						lsbofangurl = bofangurl1;
						lslv = lv;
						break;
					case 2:
						lslist = savelist;
						lspo = po2;
						ik = 2;
						lsbofangurl = bofangurl2;
						lslv = savelv;
						break;
					}
					if (lslist.size() == 0 || lslist == null) {
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
							if (ik == 1) {
								ad.notifyDataSetChanged();
							} else if (ik == 2) {
								savead.notifyDataSetChanged();
							}
						}
						if (lslist.size() == 0 || lslist == null) {
							finish();
							return;
						}
						lslist.get(lspo.get(lspo.size() - 1)).setStartorstop(1);
						lslist.get(lspo.get(lspo.size() - 1)).setJindubg(1);
						lslv.requestLayout();
						if (ik == 1) {
							ad.notifyDataSetChanged();
						} else if (ik == 2) {
							savead.notifyDataSetChanged();
						}

						if (player != null) {
							if (DazuiActivity.player != null) {
								DazuiActivity.player.release();
								DazuiActivity.player = null;
							}
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
						case 1:
							lslist = mylist;
							lspo = po1;
							ik = 1;
							lsbofangurl = bofangurl1;
							lslv = lv;
							break;
						case 2:
							lslist = savelist;
							lspo = po2;
							ik = 2;
							lsbofangurl = bofangurl2;
							lslv = savelv;
							break;
						}
						if (lslist.size() == 0 || lslist == null) {
							finish();
							return;
						}
						lslist.get(bfposition).setStartorstop(0);
						lslist.get(bfposition).setJindubg(0);
						lslv.requestLayout();
						if (ik == 1) {
							ad.notifyDataSetChanged();
						} else if (ik == 2) {
							savead.notifyDataSetChanged();
						}
					}
					lastceshi = whichlist;
					bfposition = position;
				}
				break;
			case 1:
				Commond.showToast(Mydazui.this, "网络不给力啊！！");
				getik(whichpage);
				getls(whichpage);
				if (lslist.size() == 0 || lslist == null) {
					finish();
					return;
				}
				lslist.get(position).setStartorstop(0);
				lslist.get(position).setJindubg(0);
				if (ik == 1) {
					if (ad != null) {
						lslv.requestLayout();
						ad.notifyDataSetChanged();
					}
				} else if (ik == 2) {
					lslv.requestLayout();
					savead.notifyDataSetChanged();
				}

				break;
			}
			super.handleMessage(msg);
		}
	};

	private void stopPlay() {
		if (null != player) {
			if (player.isPlaying()) {
				player.stop();
			}
			player.release();
			player = null;
		}
	}

	// 录音计时Handler
	public Handler videohandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Bundle b = msg.getData();
			videoTime = b.getInt("time");

			// System.out.println("录音时间：" + b.getInt("time"));
		}

	};

	private void saveorquxiao(int num) {
		if (whichpage == 1) {
			if (ad != null) {
				lv.requestLayout();
				mylist.get(holder.position).setFc(
						mylist.get(holder.position).getFc() + num);
				ad.notifyDataSetChanged();
			}
		} else if (whichpage == 2) {
			if (savead != null) {
				savelv.requestLayout();
				savelist.get(holder.position).setFc(
						savelist.get(holder.position).getFc() + num);
				savead.notifyDataSetChanged();
			}
		}
	}

	// 使用快捷分享完成图文分享
	// 使用快捷分享完成图文分享
	private void showShare(boolean silent, final List<DazuiIteminfo> it,final int position,String platform) {
		
		String url = "http://jrc.hutudan.com/music/shareitem.php?" + "id="
				+ it.get(position).getId() + "&uid=" + MainMenuActivity.uid;
		mdispause = true;
		OnekeyShare oks = new OnekeyShare();
		oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
		oks.setTitle("分享");
		oks.setTitleUrl(url);
		oks.setText(it.get(position).getTitle() + "    下载地址：" + url);
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
					lsfileurl.add(fileurl);
					Message message = new Message();
					message.what = 0;
					Bundle bundle = message.getData();
					bundle.putString("fileurl", furl); // 往Bundle中存放数
					bundle.putInt("whichlist", whichlist);
					message.setData(bundle);
					myHandler.sendMessage(message);
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

	@Override
	/** 处理操作结果 */
	public boolean handleMessage(Message msg) {
		// AbstractWeibo weibo = (AbstractWeibo) msg.obj;
		// String text = AbstractWeibo.actionToString(msg.arg2);
		// switch (msg.arg1) {
		// case 1: { // 成功
		// text = weibo.getName() + " completed at " + text;
		// }
		// break;
		// case 2: { // 失败
		// text = weibo.getName() + " caught error at " + text;
		// }
		// break;
		// case 3: { // 取消
		// text = weibo.getName() + " canceled at " + text;
		// }
		// break;
		// }
		//
		// Toast.makeText(Mydazui.this, text, Toast.LENGTH_SHORT).show();
		return false;
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

	public void startPlaying(String mFlilepath, final int po, final int which) {
		try {
			if (isthisactivity == true) {
				if (null == player) {
					player = new MediaPlayer();
				}
				player.reset();
				File file = new File(mFlilepath);
				FileInputStream fis = new FileInputStream(file);
				player.setDataSource(fis.getFD());
				// 设置要播放的文件
				// player.setDataSource(mFlilepath);
				player.prepare();
				DazuiActivity.lenth = player.getDuration() / 1000;
				// 播放之
				player.start();
				MainMenuActivity.borz = true;
				player.setOnCompletionListener(new OnCompletionListener() {

					@Override
					public void onCompletion(MediaPlayer mp) {
						if (DazuiActivity.player != null) {
							DazuiActivity.player.release();
							DazuiActivity.player = null;
						}
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
						if (ik == 1) {
							ad.notifyDataSetChanged();
						} else if (ik == 2) {
							savead.notifyDataSetChanged();
						}
						bls.set(position, true);
					}
				});
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<DazuiIteminfo> getls(int which) {
		// lslist.clear();
		switch (which) {
		case 1:
			lslist = mylist;
			lspo = po1;
			ik = 1;
			lsbofangurl = bofangurl1;
			break;
		case 2:
			lslist = savelist;
			lspo = po2;
			ik = 2;
			lsbofangurl = bofangurl2;
			break;

		}
		return lslist;
	}

	public int getik(int which) {
		// lslist.clear();
		switch (which) {
		case 1:
			lslist = mylist;
			lspo = po1;
			ik = 1;
			lsbofangurl = bofangurl1;
			break;
		case 2:
			lslist = savelist;
			lspo = po2;
			ik = 2;
			lsbofangurl = bofangurl2;
			break;
		}
		return ik;
	}

	/**
	 * @param isAddLastRecord
	 *            是否需要添加list之外的最新录音，一起合并
	 * @return 将合并的流用字符保存
	 */
	public void getInputCollection(List list, boolean isAddLastRecord) {

		String mMinute1 = vrdz.getTime();

		// 创建音频文件,合并的文件放这里
		file1 = new File(vrdz.myRecAudioDir, mMinute1 + SUFFIX);
		FileOutputStream fileOutputStream = null;

		if (!file1.exists()) {
			try {
				file1.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			fileOutputStream = new FileOutputStream(file1);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// list里面为暂停录音 所产生的 几段录音文件的名字，中间几段文件的减去前面的6个字节头文件

		for (int i = 0; i < list.size(); i++) {
			File file = new File((String) list.get(i));
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				byte[] myByte = new byte[fileInputStream.available()];
				// 文件长度
				int length = myByte.length;

				// 头文件
				if (i == 0) {
					while (fileInputStream.read(myByte) != -1) {
						fileOutputStream.write(myByte, 0, length);
					}
				}

				// 之后的文件，去掉头文件就可以了
				else {
					while (fileInputStream.read(myByte) != -1) {

						fileOutputStream.write(myByte, 6, length - 6);
					}
				}

				fileOutputStream.flush();
				fileInputStream.close();
				System.out.println("合成文件长度：" + file1.length());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		// 结束后关闭流
		try {
			fileOutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 加上当前正在录音的这一段
		// if(isAddLastRecord){
		//
		//
		// //刚刚录音的
		// try {
		// FileInputStream fileInputStream=new FileInputStream(myRecAudioFile);
		// byte []myByte=new byte[fileInputStream.available()];
		// System.out.println(fileInputStream.available()+"");
		// while(fileInputStream.read(myByte)!=-1){
		// //outputStream.
		// fileOutputStream.write(myByte, 6, (fileInputStream.available()-6));
		// }
		//
		// fileOutputStream.flush();
		// fileInputStream.close();
		// fileOutputStream.close();
		// System.out.println("合成文件长度："+file1.length());
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// }

		// 合成一个文件后，删除之前暂停录音所保存的零碎合成文件
		deleteListRecord(isAddLastRecord);
		//

	}

	private void deleteListRecord(boolean isAddLastRecord) {
		for (int i = 0; i < list.size(); i++) {
			File file = new File((String) list.get(i));
			if (file.exists()) {
				file.delete();
			}
		}
		// 正在暂停后，继续录音的这一段音频文件
		if (isAddLastRecord) {
			vrdz.myRecAudioDir.delete();
		}
	}

}
