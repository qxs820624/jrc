package com.jianrencun.dynamic;

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

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import cn.sharesdk.onekeyshare.OnekeyShare;

import com.app.chatroom.Chakandatu;
import com.app.chatroom.DeleteOrBorwser;
import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.audio.VideoRecordDz;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.otherui.VillageUserInfoDialog;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.updata.FileUpload;
import com.app.chatroom.util.Commond;
import com.app.chatroom.util.SystemUtil;
import com.duom.fjz.iteminfo.BitmapCacheDzlb;
import com.duom.fjz.iteminfo.BitmapCacheDzpl;
import com.jianrencun.android.Chakanpinglun;
import com.jianrencun.android.Details;
import com.jianrencun.android.HttpBaseActivity;
import com.jianrencun.android.Shangchuan;
import com.jianrencun.android.HttpBaseActivity.HttpRequestTask;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Comment;
import com.jianrencun.dazui.Dazuidetatil;
import com.jianrencun.dazui.Dzhuifupinglun;
import com.jianrencun.dazui.Mydazui;
import com.jianrencun.dazui.PinglunAdapter;
import com.jianrencun.dazui.Pinglunitem;
import com.jianrencun.dazui.Dazuidetatil.MyClickListener;
import com.jianrencun.dynamic.DyAdapter.LoadImageRunnable;

public class Dydetatil extends HttpBaseActivity implements OnScrollListener,
		OnClickListener {

	private LinearLayout pb_ll;
	private Button back;
	private ListView lv;
	private View view;
	public TextView dy_name, dy_title, dy_numdis, dy_long, dy_uptime;
	public ImageView dy_header;
	public ImageView dy_dzsex;
	public Button dy_share;
	public ImageButton dy_bofang;
	public SeekBar dy_seekbar;
	 public ImageView dy_iv1, dy_iv2, dy_iv3, dy_iv4, dy_iv5;
	public TextView dy_content_text;
	// public ImageView dy_content_iv;
	public RelativeLayout dy_bf_rl;
	public LinearLayout dy_photo_ll;
	private View vFooter;
	private LinearLayout loading, dy_pb_ll;
	private Details dt = new Details();
	/* 配置文件 */
	SharedPreferences sp;
	SystemSettingUtilSp su;
	private int page, pd, id, flg;
	ArrayList<Dyphotoitem> dyphotolists = null;
	ArrayList<String> dyphotobigs = null;
	private Dyphotoitem dyphotoitem;
	private List<Pinglunitem> items = new ArrayList<Pinglunitem>();
	private File picfile, photopf;
	int po, po2;
	private String dazuidown;
	private ArrayList<String> urls = new ArrayList<String>();
	private int uid;
	private String nick;
	// 面板
	private Button wenzi, yuyin, fabiao, anxiabnt, shuaxin;
	private EditText et;
	private RelativeLayout dymianban , rl1 , rl2 ,rl3 , rl4 ,rl5;

	// //
	private String urlpl, afile;
	private PinglunAdapter da;
	public static MediaPlayer player = null;
	private Handler hd;
	private boolean isplay;

	public static boolean borz;
	private final int message_what_position = 101;
	private String filename = "";
	private boolean isdetatilactivity = true;
	private LinearLayout dy_long_ll, dy_bigphoto_ll;
	private List<ImageView> iv1s = new ArrayList<ImageView>();
	DisplayMetrics dm;
	String detatilurl;

	/* 音频控制 */
	MediaPlayer myMediaStart; // 启动声音
	MediaPlayer myMediaSend; // 发送出声音
	boolean isPlay = false;// 是否正在播放
	VideoRecordDz vr;
	private StringBuffer data;
	private long bgtm, endtm;
	private int milltime;
	private boolean yypl = true;
	private int tp;
	OnClickListener listener;
	private int fromwhich;
	public static boolean freshmysave;
	private ImageView recorder_volumeImageView;// 音频动画
	private RelativeLayout speakRelativeLayout;// 录音背景框
	private int dracation;
	public boolean LongTouch = false;
	private List<Integer> dlpl;
    private String title ;
    public ProgressBar dy_down_pb;		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dydetatil);

		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
		
		// 按钮声音
		myMediaStart = MediaPlayer.create(getApplicationContext(),
				R.raw.ptt_startrecord);
		myMediaSend = MediaPlayer.create(getApplicationContext(),
				R.raw.sent_message);
		// 实例化录音
		vr = new VideoRecordDz(Dydetatil.this, recorder_volumeImageView);
		vr.init();
		// ///////////////////传值
		Intent it = getIntent();
		id = it.getIntExtra("id", 0);
		flg = it.getIntExtra("flg", 0);
		afile = it.getStringExtra("afile");
		dazuidown = MainMenuActivity.dazuisdown.toString();
		dlpl = new ArrayList<Integer>();
		// ////////////初始化控件
		pb_ll = (LinearLayout) findViewById(R.id.dy_detatil_pb_ll);
		back = (Button) findViewById(R.id.dy_detatil_back);
		lv = (ListView) findViewById(R.id.dy_detatil_lv);
		wenzi = (Button) findViewById(R.id.dydetatil_wenzi);
		yuyin = (Button) findViewById(R.id.dydetatil_yuyin);
		// shuaxin = (Button) findViewById(R.id.dydetatil_shuaxin);
		fabiao = (Button) findViewById(R.id.dydetatil_fabiao);
		anxiabnt = (Button) findViewById(R.id.dydetatil_anzhushuohua);
		et = (EditText) findViewById(R.id.dydetatil_et);
		dymianban = (RelativeLayout) findViewById(R.id.dymianban);
		dyphotobigs = new ArrayList<String>();
		////////////////
		if(flg != 8){
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long idd) {
					// TODO Auto-generated method stub
					if (items.get(position-1).getPid() == 0) {
						Intent it = new Intent();
						it.setClass(Dydetatil.this, Dzhuifupinglun.class);
						it.putExtra("pid", items.get(position-1).getId());
						it.putExtra("id", id);
						it.putExtra("flg", flg);
						it.putExtra("isdy", "yes");
						startActivityForResult(it, 2);
					} else {
						Intent it = new Intent();
						it.setClass(Dydetatil.this, Dzhuifupinglun.class);
						it.putExtra("pid", items.get(position-1).getPid());
						it.putExtra("flg", flg);
						it.putExtra("id", id);
						it.putExtra("isdy", "yes");
						startActivityForResult(it, 2);
					}
				}
			});
			
			lv.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub

					Intent intent = new Intent();
					intent.putExtra("content", items.get(position-1).getDesc()
							.toString());
					intent.putExtra("title", items.get(position-1).getUnick()
							.toString());
					intent.putExtra("from", 0);
					intent.putExtra("mid", items.get(position-1).getId());
					intent.setClass(Dydetatil.this, Chakanpinglun.class);
					startActivity(intent);
					return false;
				}
			});
		}else{
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long idd) {
			Intent intent = new Intent();
			intent.putExtra("content", items.get(position - 1).getDesc()
					.toString());
			intent.putExtra("title", items.get(position - 1).getUnick()
					.toString());
//			intent.putExtra("jubaolink", items.get(position - 1)
//					.getJubaolink());
			intent.putExtra("from", 1);
			intent.setClass(Dydetatil.this, Chakanpinglun.class);
			startActivity(intent);	
				}
			});
		}
		// /////
		vFooter = LayoutInflater.from(this).inflate(R.layout.list_footer, null);
		loading = (LinearLayout) vFooter.findViewById(R.id.list_footer);
		loading.setVisibility(View.GONE);
		dy_pb_ll = (LinearLayout) findViewById(R.id.dy_pb_ll);
		speakRelativeLayout = (RelativeLayout) findViewById(R.id.dy_speak_RelativeLayout);
		 dm = new DisplayMetrics();   
	        (Dydetatil.this).getWindowManager().getDefaultDisplay().getMetrics(dm);  

		if (flg == 8) {
			wenzi.setVisibility(View.GONE);
			yuyin.setVisibility(View.GONE);
		} else {
			wenzi.setVisibility(View.VISIBLE);
			yuyin.setVisibility(View.VISIBLE);
		}
		// /////////////////////////////////
		et.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					inputMethodManager.toggleSoftInput(0,
							InputMethodManager.HIDE_NOT_ALWAYS);
				} else {
					InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					inputMethodManager.hideSoftInputFromWindow(
							et.getWindowToken(), 0);

				}
			}
		});
		
		anxiabnt.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
					Commond.showToast(Dydetatil.this, "检测到网络网络异常或未开启");
					return false;
				}
				myMediaStart.start();
				bgtm = System.currentTimeMillis();
				System.out.println("长按....录音...");
				speakRelativeLayout.setVisibility(View.VISIBLE);
				LongTouch = true;
				vr.start();
				// }
				return true;
			}
		});
		anxiabnt.setOnTouchListener(new MyClickListener());
		
		view = new View(getApplicationContext());
		view = getLayoutInflater().inflate(R.layout.dy_xiaojian, null);
		dy_name = (TextView) view.findViewById(R.id.dy_username);
		dy_title = (TextView) view.findViewById(R.id.dy_biaoti);
		dy_uptime = (TextView) view.findViewById(R.id.dy_uptime);
		dy_long = (TextView) view.findViewById(R.id.dy_long);
		dy_numdis = (TextView) view.findViewById(R.id.dy_numdis);
		dy_header = (ImageView) view.findViewById(R.id.dy_header);
		dy_dzsex = (ImageView) view.findViewById(R.id.dy_sex);
		dy_share = (Button) view.findViewById(R.id.dy_share);
		dy_bofang = (ImageButton) view.findViewById(R.id.dy_bofang);
		dy_seekbar = (SeekBar) view.findViewById(R.id.dy_seekbar);
		dy_iv1 = (ImageView) view.findViewById(R.id.dy_photo1);
		dy_iv2 = (ImageView) view.findViewById(R.id.dy_photo2);
		dy_iv3 = (ImageView) view.findViewById(R.id.dy_photo3);
		dy_iv4 = (ImageView) view.findViewById(R.id.dy_photo4);
		dy_iv5 = (ImageView) view.findViewById(R.id.dy_photo5);
		dy_long_ll = (LinearLayout)view.findViewById(R.id.dy_long_ll);
		dy_bigphoto_ll = (LinearLayout)view.findViewById(R.id.dy_bigphoto_ll);
		dy_down_pb = (ProgressBar)view.findViewById(R.id.dy_down_pb);
		dy_seekbar.setEnabled(false);
		
		if(TextUtils.isEmpty(afile)){
			dy_share.setVisibility(View.GONE);			
		}else{
			dy_share.setVisibility(View.VISIBLE);	
		}
		//分享
		dy_share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String url = "http://jrc.hutudan.com/music/shareitem.php?" + "id="
						+ id + "&uid=" + MainMenuActivity.uid;
				
				OnekeyShare oks = new OnekeyShare();
				oks.setNotification(R.drawable.ic_launcher, Dydetatil.this.getString(R.string.app_name));
				oks.setTitle("分享");
				oks.setTitleUrl(url);
				oks.setText( title + "    下载地址：" + url);
				oks.setAppPath(Details.TEST_IMAGE);
				oks.setComment("分享");
				oks.setUrl(url);
				oks.setSite(Dydetatil.this.getString(R.string.app_name));
				oks.setSiteUrl(url);
				oks.setDzUrl(url);
				oks.setWhich(2);
//				oks.setVenueName("Southeast in China");
//				oks.setVenueDescription("This is a beautiful place!");
//				oks.setLatitude(23.122619f);
//				oks.setLongitude(113.372338f);
				oks.setSilent(false);
				// 去除注释，则快捷分享的分享加过将听过OneKeyShareCallback回调
//				oks.setCallback(new OneKeyShareCallback());

				oks.show(Dydetatil.this);
			}
		});
		// 播放拖动条。。。
		dy_seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				getseek(seekBar.getProgress() * 1000);
				dy_bofang.setBackgroundResource(R.drawable.dy_zanting1);
				borz = true;
				isplay = true;
				new Thread(new update()).start();
				player.setOnCompletionListener(new OnCompletionListener() {

					@Override
					public void onCompletion(MediaPlayer mp) {
						// player.release();
						player = null;
						// player.stop();
						dy_bofang.setBackgroundResource(R.drawable.havebf1);
						borz = false;
						// mpprepar(filename);
						// int length = player.getDuration() / 1000;
						// sk.setMax(length);
						dy_seekbar.setProgress(0);
						isplay = false;
						dy_seekbar.setEnabled(false);
					}
				});
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// puse();

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// bgtv.setText(String.format("%02d:%02d", progress / 60,
				// progress % 60));

			}
		});

		hd = new Handler() {
			public void handleMessage(android.os.Message mes) {
				if (mes.what == message_what_position) {
					dy_seekbar.setProgress(mes.arg1);
				}
			}
		};

		lv.setOnScrollListener(this);
		lv.addFooterView(vFooter);
		lv.setFooterDividersEnabled(false);

		

		dy_bofang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// if (pb.getVisibility() == View.VISIBLE) {
				// Commond.showToast(Dydetatil.this, "稍安勿躁,正在下载中！");
				// return;
				// }
				MainMenuActivity.borz = false ;
				if (player != null) {
					if (borz == false) {
						player.start();
						if(Dynamic.dyplayer != null && Dynamic.dyplayer.isPlaying()){
							Dynamic.dyplayer.pause() ;
						}
						borz = true;
						isplay = true;
						dy_bofang.setBackgroundResource(R.drawable.dy_zanting1);
						new Thread(new update()).start();
						player.setOnCompletionListener(new OnCompletionListener() {
							@Override
							public void onCompletion(MediaPlayer mp) {
								// player.release();
								player = null;
								// player.stop();
								dy_seekbar.setProgress(0);
								dy_bofang
										.setBackgroundResource(R.drawable.havebf1);
								borz = false;
								// mpprepar(filename);
								// int length = player.getDuration() / 1000;
								// sk.setMax(length);
								isplay = false;
								dy_seekbar.setEnabled(false);
							}
						});
					} else if (borz == true) {	
						borz = false;
						player.pause();
						dy_bofang
								.setBackgroundResource(R.drawable.havebf1);
						isplay = false;
					}
				} else {
					File picfile = new File(dazuidown + File.separator
							+ Comment.getMd5Hash(afile) + ".amr");
					filename = picfile.getPath().toString();
					// File f = new File(p +
					// Adapterwithpic.getMd5Hash(geturl(details)));
					if (picfile.exists()) {
						dy_bofang.setBackgroundResource(R.drawable.dy_zanting1);
						startPlaying(filename);
						borz = true;
						isplay = true;
						new Thread(new update()).start();
					} else {
						dy_down_pb.setVisibility(View.VISIBLE);
						new Thread(new myThread(afile, myHandler)).start();
					}
				}
			}
		});

		dy_header.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Dydetatil.this,
						VillageUserInfoDialog.class);

				intent.putExtra("uid", String.valueOf(uid));
				intent.putExtra("nick", nick);
				intent.putExtra("fuid", MainMenuActivity.uid);
				// intent.putExtra("type", 9);
				startActivity(intent);
			}
		});

		dy_content_text = (TextView) view.findViewById(R.id.dy_content_text);
		dy_bf_rl = (RelativeLayout) view.findViewById(R.id.dy_bf_rl);
		dy_photo_ll = (LinearLayout) view.findViewById(R.id.dy_photo_ll);
		rl1 = (RelativeLayout) view.findViewById(R.id.dy_rl1);
		rl2 = (RelativeLayout) view.findViewById(R.id.dy_rl2);
		rl3 = (RelativeLayout) view.findViewById(R.id.dy_rl3);
		rl4 = (RelativeLayout) view.findViewById(R.id.dy_rl4);
		rl5 = (RelativeLayout) view.findViewById(R.id.dy_rl5);
        view.setClickable(false);
		lv.addHeaderView(view, null, false);

		detatilurl = dt.appendNameValue(ConstantsJrc.DTDETATIL, "uid",
				su.getUid());
		detatilurl = dt.appendNameValue(detatilurl, "token", URLEncoder.encode(su.getToken()));
		detatilurl = dt.appendNameValueint(detatilurl, "pd", pd);
		detatilurl = dt.appendNameValueint(detatilurl, "id", id);
		detatilurl = dt.appendNameValueint(detatilurl, "flg", flg);

		StringBuffer data = new StringBuffer();
		HttpRequestTask request = new HttpRequestTask();
		request.execute(detatilurl, data.toString());

		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		dy_iv1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


						Intent intent = new Intent();
						intent.setClass(Dydetatil.this, Chakandatu.class);
						intent.putStringArrayListExtra("photos", dyphotobigs);
						intent.putExtra("po", 0);
						startActivity(intent);									
			}
		});
		dy_iv2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


						Intent intent = new Intent();
						intent.setClass(Dydetatil.this, Chakandatu.class);
						intent.putStringArrayListExtra("photos", dyphotobigs);
						intent.putExtra("po", 1);
						startActivity(intent);									
			}
		});
		dy_iv3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


						Intent intent = new Intent();
						intent.setClass(Dydetatil.this, Chakandatu.class);
						intent.putStringArrayListExtra("photos", dyphotobigs);
						intent.putExtra("po", 2);
						startActivity(intent);									
			}
		});
		dy_iv4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


						Intent intent = new Intent();
						intent.setClass(Dydetatil.this, Chakandatu.class);
						intent.putStringArrayListExtra("photos", dyphotobigs);
						intent.putExtra("po", 3);
						startActivity(intent);									
			}
		});
		dy_iv5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


						Intent intent = new Intent();
						intent.setClass(Dydetatil.this, Chakandatu.class);
						intent.putStringArrayListExtra("photos", dyphotobigs);
						intent.putExtra("po", 4);
						startActivity(intent);									
			}
		});

		
		wenzi.setOnClickListener(this);
		yuyin.setOnClickListener(this);
		// back.setOnClickListener(this);
		fabiao.setOnClickListener(this);
		anxiabnt.setOnClickListener(this);
		// shuaxin.setOnClickListener(this);
	}

	@Override
	public void resultData(String url, String result) {
		// TODO Auto-generated method stub	
		pb_ll.setVisibility(View.GONE);
		String tip = null;
		if (result == null) {
			// fresh.setVisibility(View.VISIBLE);
			Commond.showToast(Dydetatil.this, "小贱提醒 ：当前网络不稳定！");
			return;
		}
		try {
			// //////////////////////////////////////////////正文内容页面
			JSONObject jsonChannel = new JSONObject(result);
			tip = URLDecoder.decode(jsonChannel.optString("tip"));
			int ret = jsonChannel.optInt("ret");
			if (ret == 0) {
				Commond.showToast(Dydetatil.this, tip);
				return;
			}

			String tip1 = URLDecoder.decode(jsonChannel.optString("tip1"));
			page = jsonChannel.optInt("page");
			pd = jsonChannel.optInt("pd");

			uid = jsonChannel.optInt("uid");
			nick = URLDecoder.decode(jsonChannel.optString("nick"));
			String nick_c = URLDecoder.decode(jsonChannel.optString("nick_c"));
			String header = URLDecoder.decode(jsonChannel.optString("header"));
			int headerbj = jsonChannel.optInt("uhflg");
			int sex = jsonChannel.optInt("sex");
			title = URLDecoder.decode(jsonChannel.optString("title"));
			String desc = URLDecoder.decode(jsonChannel.optString("desc"));
			String desc_c = URLDecoder.decode(jsonChannel.optString("desc_c"));

			String afile = URLDecoder.decode(jsonChannel.optString("afile"));

			int alen = jsonChannel.optInt("alen");
			String uptime = URLDecoder.decode(jsonChannel.optString("desc1"));
			String numdis = URLDecoder.decode(jsonChannel.optString("desc2"));
			JSONArray jsonpics = jsonChannel.optJSONArray("pics");
			if (jsonpics != null) {
				dyphotolists = new ArrayList<Dyphotoitem>();
				for (int j = 0; j < jsonpics.length(); j++) {
					JSONObject jsonpic = jsonpics.getJSONObject(j);
					String photourl = URLDecoder.decode(jsonpic
							.optString("pic"));
					String bphotourl = URLDecoder.decode(jsonpic
							.optString("bpic"));

					String burl = URLDecoder.decode(jsonpic.optString("burl"));
					int w = jsonpic.optInt("uhflg");
					int h = jsonpic.optInt("sex");
					if (TextUtils.isEmpty(bphotourl)) {
						dyphotoitem = new Dyphotoitem(photourl, w, h, burl);
					} else {
						dyphotoitem = new Dyphotoitem(bphotourl, w, h, burl);
					}
					dyphotolists.add(dyphotoitem);
				}
			}

			JSONArray jsonItems = jsonChannel.optJSONArray("items");
			if (jsonItems != null) {
				// tip = "获取成功！";
				for (int i = 0; i < jsonItems.length(); i++) {
					JSONObject jsonItem = jsonItems.getJSONObject(i);
					List<Dyphotoitem> dyphotolists = null;
					int id = jsonItem.optInt("id");
					int flg = jsonItem.optInt("flg");
					int uid1 = jsonItem.optInt("uid");
					int pid1 = jsonItem.optInt("pid");
					String nick1 = URLDecoder
							.decode(jsonItem.optString("nick"));
					String nick_c1 = URLDecoder.decode(jsonItem
							.optString("nick_c"));
					String header1 = URLDecoder.decode(jsonItem
							.optString("header"));
					int headerbj1 = jsonItem.optInt("uhflg");
					int sex1 = jsonItem.optInt("sex");
					String desc1 = URLDecoder
							.decode(jsonItem.optString("desc"));
					String desc_c1 = URLDecoder.decode(jsonItem
							.optString("desc_c"));
					String afile1 = URLDecoder.decode(jsonItem
							.optString("afile"));

					int alen1 = jsonItem.optInt("alen");
					String uptime1 = URLDecoder.decode(jsonItem
							.optString("desc1"));
					if (TextUtils.isEmpty(desc1) && !TextUtils.isEmpty(afile1)) {
						tp = 1;
					} else {
						tp = 0;
					}
					Pinglunitem item = new Pinglunitem(id, uid1, nick1,
							header1, uptime1, afile1, "", desc1, desc_c1, tp,
							alen1, false, nick_c1, flg, pid1, "", headerbj1);
					items.add(item);
				}
				// //
				if (items.size() != 0 && !url.contains("page")) {
					da = new PinglunAdapter(Dydetatil.this, items, "", lv,
							false);
					lv.setAdapter(da);
				} else if (items.size() != 0 && url.contains("page")) {
					lv.requestLayout();
					da.notifyDataSetChanged();
					loading.setVisibility(View.GONE);
				} else {
					da = new PinglunAdapter(Dydetatil.this, items, "", lv,
							false);
					lv.setAdapter(da);
				}
			} else {
				da = new PinglunAdapter(Dydetatil.this, items, "", lv, false);
				lv.setAdapter(da);
			}
			if (url.contains(ConstantsJrc.DYPINGLUN)) {
				items.clear();
				
//				dyphotolists.clear();
				post(detatilurl);
				et.clearFocus();
				pb_ll.setVisibility(View.VISIBLE);
				et.setHint("亲，说点什么吧！");
				et.setTextColor(Color.parseColor("#833a16"));
				et.setEnabled(true);
				et.setText("");
				et.setGravity(Gravity.CENTER_VERTICAL);
				items.clear();
				Commond.showToast(Dydetatil.this, tip);
			}
			// //////////图片
			if (dyphotolists != null) {
				if (dyphotolists.size() == 0) {
					// viewHolder.dy_content_iv.setVisibility(View.GONE);
					dy_photo_ll.setVisibility(View.GONE);
				} else {				
					if(flg == 8){
						dy_photo_ll.setVisibility(View.GONE);
					for (int i = 0; i < dyphotolists.size(); i++) {
						if (!TextUtils.isEmpty(dyphotolists.get(i).getUrl())) {
							Bitmap bmp = null;
							picfile = new File(dazuidown
									+ "/"
									+ Comment.getMd5Hash(dyphotolists.get(i)
											.getUrl()));
							String filename = picfile.getPath().toString();
							if (picfile.exists()) {
								if (dyphotolists.get(i).getW() > dm.widthPixels / 4 * 3) {
									bmp = Shangchuan.getbp(filename,
											dm.widthPixels / 4 * 3);
								} else {
									bmp = BitmapCacheDzlb.getIntance()
											.getCacheBitmap(filename, 0, 0);
								}
							}

							if (bmp == null) {
								if (!urls
										.contains(dyphotolists.get(i).getUrl())) {
									ImageView iv = new ImageView(Dydetatil.this);
									iv.setTag(dyphotolists.get(i).getUrl());
									dy_bigphoto_ll.addView(iv);
									iv1s.add(iv);

									urls.add(dyphotolists.get(i).getUrl());
									dyphotobigs.add(dyphotolists.get(i)
											.getUrl());
									new Thread(new LoadImageRunnable2(
											Dydetatil.this, mHandler,
											dyphotolists.get(i).getUrl(),
											filename, iv)).start();
									// iv.setImageResource(R.drawable.photoviewdef);
								}
							} else {
								dyphotobigs.add(dyphotolists.get(i).getUrl());
								dy_bigphoto_ll.removeAllViews();
								ImageView iv = new ImageView(Dydetatil.this);
								iv.setTag(dyphotolists.get(i).getUrl());
								dy_bigphoto_ll.addView(iv);
								iv1s.add(iv);
								iv.setImageBitmap(bmp);
							}
						}
					}
					}else{
						 dy_photo_ll.setVisibility(View.VISIBLE);
						 switch (dyphotolists.size()) {
						 case 1:
						 rl1.setVisibility(View.VISIBLE);
						 rl2.setVisibility(View.GONE);
						 rl3.setVisibility(View.GONE);
						 rl4.setVisibility(View.GONE);
						 rl5.setVisibility(View.GONE);
						 break;
						 case 2:
						 rl1.setVisibility(View.VISIBLE);
						 rl2.setVisibility(View.VISIBLE);
						 rl3.setVisibility(View.GONE);
						 rl4.setVisibility(View.GONE);
						 rl5.setVisibility(View.GONE);
						 break;
						 case 3:
						 rl1.setVisibility(View.VISIBLE);
						 rl2.setVisibility(View.VISIBLE);
						 rl3.setVisibility(View.VISIBLE);
						 rl4.setVisibility(View.GONE);
						 rl5.setVisibility(View.GONE);
						 break;
						 case 4:
						 rl1.setVisibility(View.VISIBLE);
						 rl2.setVisibility(View.VISIBLE);
						 rl3.setVisibility(View.VISIBLE);
						 rl4.setVisibility(View.VISIBLE);
						 rl5.setVisibility(View.GONE);
						 break;
						 case 5:
						 rl1.setVisibility(View.VISIBLE);
						 rl2.setVisibility(View.VISIBLE);
						 rl3.setVisibility(View.VISIBLE);
						 rl4.setVisibility(View.VISIBLE);
						 rl5.setVisibility(View.VISIBLE);
						 break;
						 }
					for (int i = 0; i < dyphotolists.size(); i++) {

						if (!TextUtils.isEmpty(dyphotolists.get(i).getUrl())) {
							dyphotobigs.add(dyphotolists.get(i).getUrl());
						} else {
							dyphotobigs.add(dyphotolists.get(i).getUrl());
						}
						switch (i) {
						case 0:
							if (dyphotolists.size() == 1
									&& dyphotolists.get(0).getW() != 0) {

								dy_iv1.setTag(dyphotolists.get(0).getUrl());
							} else {
								dy_iv1.setTag(dyphotolists
										.get(0).getUrl());
							}
							break;
						case 1:
							dy_iv2.setTag(dyphotolists.get(1)
									.getUrl());
							break;
						case 2:
							dy_iv3.setTag(dyphotolists.get(2)
									.getUrl());
							break;
						case 3:
							dy_iv4.setTag(dyphotolists.get(3)
									.getUrl());
							break;
						case 4:
							dy_iv5.setTag(dyphotolists.get(4)
									.getUrl());
							break;

						default:

							break;
						}
						photopf = new File(dazuidown
								+ "/"
								+ Comment.getMd5Hash(dyphotolists.get(i)
										.getUrl()));
						String filename = photopf.getPath().toString();
						Bitmap bmp = null;
						if (photopf.exists()) {
							bmp = BitmapCacheDzlb.getIntance().getCacheBitmap(
									filename, 0, 0);
						}

						if (bmp == null) {

							if (!urls.contains(dyphotolists.get(i).getUrl())) {
								urls.add(dyphotolists.get(i).getUrl());
								new Thread(new LoadImageRunnable(
										Dydetatil.this, mHandler2, 0,
										dyphotolists.get(i).getUrl(), filename,
										0, 0)).start();
							}
							 switch (i) {
							 case 0:
							 dy_iv1.setImageResource(R.drawable.defaultpic);
							 break;
							 case 1:
							 dy_iv2.setImageResource(R.drawable.defaultpic);
							 break;
							 case 2:
							 dy_iv3.setImageResource(R.drawable.defaultpic);
							 break;
							 case 3:
							 dy_iv4.setImageResource(R.drawable.defaultpic);
							 break;
							 case 4:
							 dy_iv5.setImageResource(R.drawable.defaultpic);
							 break;
							
							 default:
							
							 break;
							 }
						} else {
							BitmapDrawable bd = new BitmapDrawable(bmp);
							 switch (i) {
							 case 0:
							 dy_iv1.setImageDrawable(bd);
							 break;
							 case 1:
							 dy_iv2.setImageDrawable(bd);
							 break;
							 case 2:
							 dy_iv3.setImageDrawable(bd);
							 break;
							 case 3:
							 dy_iv4.setImageDrawable(bd);
							 break;
							 case 4:
							 dy_iv5.setImageDrawable(bd);
							 break;
							
							 default:
							
							 break;
							 }
						}
					}
					}
				}
			} else {
				// viewHolder.dy_content_iv.setVisibility(View.GONE);
				dy_photo_ll.setVisibility(View.GONE);
			}

			// ///sex
			if (sex == 0) {
				dy_dzsex.setBackgroundResource(R.drawable.what);
			} else if (sex == 1) {
				dy_dzsex.setBackgroundResource(R.drawable.woman);
			} else if (sex == 2) {
				dy_dzsex.setBackgroundResource(R.drawable.man);
			}

			// ////语音面板
			if (!TextUtils.isEmpty(afile)) {
				dy_bf_rl.setVisibility(View.VISIBLE);
				dy_long.setVisibility(View.VISIBLE);
				dy_long_ll.setVisibility(View.VISIBLE);
				File picfile1 = new File(MainMenuActivity.dazuisdown + File.separator
						+ Comment.getMd5Hash(afile) + ".amr");
				if (picfile1.exists()) {
					dy_bofang.setBackgroundResource(R.drawable.havebf1);
				}else{
					dy_bofang.setBackgroundResource(R.drawable.dy_bofang1);
				}
				
			} else {
				dy_bf_rl.setVisibility(View.GONE);
				dy_long.setVisibility(View.GONE);
				dy_long_ll.setVisibility(View.GONE);
			}
			// ////标题
			dy_title.setText(title);
			// 昵称
			dy_name.setText(nick);			
			 if(!TextUtils.isEmpty(nick_c)){
			 dy_name.setTextColor(Color.parseColor(nick_c));
			 } else {
			 dy_name.setTextColor(Color.parseColor("#716d4f"));
			 }
			// ////正文内容
			if (!TextUtils.isEmpty(desc)) {
				dy_content_text.setText(desc);
				dy_content_text.setVisibility(View.VISIBLE);
				if (!TextUtils.isEmpty(desc_c)) {
					dy_content_text.setTextColor(Color.parseColor(desc_c));
				} else {
					dy_content_text.setTextColor(Color.parseColor("#716d4f"));
				}
			} else {
				dy_content_text.setVisibility(View.GONE);
			}
			// ////下面时间。评论数，
			dy_uptime.setText(uptime);
			dy_numdis.setText(numdis);
			dy_long.setText(String.valueOf(alen));

			if (TextUtils.isEmpty(header)) {
				dy_header.setImageResource(R.drawable.defautheader);
			} else {
				dy_header.setImageResource(R.drawable.photo);
				picfile = new File(dazuidown + "/" + Comment.getMd5Hash(header));
				String filename = picfile.getPath().toString();

				Bitmap bmp = null;
				if (picfile.exists()) {
					bmp = BitmapCacheDzpl.getIntance().getCacheBitmap(filename,
							0, 0);
				}

				if (bmp == null) {
					if (!urls.contains(header)) {
						urls.add(header);
						new Thread(new LoadImageRunnable(Dydetatil.this,
								mHandler, 0, header, filename, 0, 0)).start();
					}
					dy_header.setImageResource(R.drawable.photo);
				} else {
					dy_header.setImageBitmap(bmp);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			// pb.setVisibility(View.GONE);
			e.printStackTrace();
			Commond.showToast(Dydetatil.this, "操作失败！请检查网络！");
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			if (view.getLastVisiblePosition() == view.getCount() - 1) {
				if (page == 0 ) {
					lv.removeFooterView(vFooter);
					return;
				} else {					
					if (!dlpl.contains(page)) {
						loading.setVisibility(View.VISIBLE);
						StringBuffer data = new StringBuffer();

						data.append("pkg=");
						data.append(URLEncoder.encode(getPackageName()));
						//
						// data.append("&pd=");
						// lastdate = currentdate;
						// data.append(URLEncoder.encode(lastdate));
						// 请求网络验证登陆						
						detatilurl = dt.appendNameValue(ConstantsJrc.DTDETATIL, "uid",
								su.getUid());
						detatilurl = Details.appendNameValueint(detatilurl, "page", page);
						detatilurl = dt.appendNameValue(detatilurl, "token", URLEncoder.encode(su.getToken()));
						detatilurl = dt.appendNameValueint(detatilurl, "pd", pd);
						detatilurl = dt.appendNameValueint(detatilurl, "id", id);
						detatilurl = dt.appendNameValueint(detatilurl, "flg", flg);
						HttpRequestTask request = new HttpRequestTask();
						request.execute(detatilurl, data.toString());
						dlpl.add(page);
					}
				}
			}
		}
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			if (data != null) {
				String url = data.getString("url");
				String filename = data.getString("filename");

				Bitmap bmp = BitmapCacheDzlb.getIntance().getCacheBitmap(
						filename, 0, 0);
				ImageView iv = (ImageView) lv.findViewWithTag(url);
				if (iv != null) {
					BitmapDrawable bd = new BitmapDrawable(bmp);
					iv.setImageDrawable(bd);
					;
				}
			}
		}
	};
	Handler mHandler2 = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			if (data != null) {
				String url = data.getString("url");
				String filename = data.getString("filename");

				Bitmap bmp = BitmapCacheDzlb.getIntance().getCacheBitmap(
						filename, 0, 0);
				ImageView iv = (ImageView) lv.findViewWithTag(url);
				if (iv != null) {
					BitmapDrawable bd = new BitmapDrawable(bmp);
					iv.setBackgroundDrawable(bd);
					;
				}
			}
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == wenzi) {
			v.setVisibility(View.GONE);
			yuyin.setVisibility(View.VISIBLE);
			et.setVisibility(View.VISIBLE);
			anxiabnt.setVisibility(View.GONE);
		}
		if (v == yuyin) {
			v.setVisibility(View.GONE);
			wenzi.setVisibility(View.VISIBLE);
			et.setVisibility(View.GONE);
			anxiabnt.setVisibility(View.VISIBLE);
		}
		if (v == fabiao) {
			if (TextUtils.isEmpty(et.getText().toString())) {
				Commond.showToast(Dydetatil.this, "输入内容，再评论哦！");
				et.requestFocus();
				return;
			}
			v.setEnabled(false);
			pb_ll.setVisibility(View.VISIBLE);
			urlpl = ConstantsJrc.DYPINGLUN;
			urlpl = Details.geturl(urlpl);
			urlpl = Details.appendNameValueint(urlpl, "id", id);
			urlpl = Details.appendNameValueint(urlpl, "flg", flg);
			urlpl = dt.appendNameValue(urlpl, "token", su.getToken());
			urlpl = dt.appendNameValue(urlpl, "input",
					URLEncoder.encode(et.getText().toString()));
			StringBuffer data = new StringBuffer();
			// data.append("&input=");
			// data.append(URLEncoder.encode(et.getText().toString()));
			// data.append("&id=");
			// data.append(id);
			// 请求网络验证登陆
			HttpRequestTask request = new HttpRequestTask();
			request.execute(urlpl, data.toString());
		}

		// 这个是刷新
		// if (v == shuaxin) {
		// items.clear();
		// dlpl.clear();
		// ll.setVisibility(View.VISIBLE);
		// post(urlqq);
		// }
	}

	/**
	 * 
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent e) {
		// if (e.getAction() == MotionEvent.ACTION_DOWN) {
		// //类型为Down才处理，还有Move/Up之类的
		Rect r = new Rect();
		dymianban.getGlobalVisibleRect(r);

		if (r.contains((int) e.getX(), (int) e.getY())) {
			return super.dispatchTouchEvent(e);
		} else {
			try {
				if (!et.hasFocus()) {
					return super.dispatchTouchEvent(e);
				} else {
					InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					inputMethodManager.hideSoftInputFromWindow(
							et.getWindowToken(), 0);
					et.clearFocus();
					return false;
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
				return false;
			}
		}
	}

	class update implements Runnable {

		@Override
		public void run() {
			while (hd != null && player != null
					&& dy_seekbar.getProgress() < player.getDuration() / 1000) {
				if (isplay) {
					Message message = hd.obtainMessage();
					message.arg1 = player.getCurrentPosition() / 1000;
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

	public void mpprepar(String mFlilepath) {
		if (player != null) {
			player.stop();
			player = null;
		}
		player = new MediaPlayer();
		try {
			File file = new File(mFlilepath);
			FileInputStream fis = new FileInputStream(file);
			player.setDataSource(fis.getFD());
			dy_seekbar.setEnabled(true);
			player.prepare();
			// fis.reset();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startPlaying(String mFlilepath) {
		if (isdetatilactivity == true) {
			mpprepar(mFlilepath);
			// 设置要播放的文件
			// player.setDataSource(mFlilepath);
			int length = player.getDuration() / 1000;
			dy_seekbar.setMax(length);
			if (Dynamic.dyplayer != null && Dynamic.dyplayer.isPlaying()) {
				Dynamic.dyplayer.pause();
			}
			// 播放之
			player.start();
			player.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					// player.release();

					player.stop();
					player = null;
					dy_bofang.setBackgroundResource(R.drawable.havebf1);
					borz = false;
					// mpprepar(filename);
					// int length = player.getDuration() / 1000;
					// sk.setMax(length);
					dy_seekbar.setProgress(0);
					isplay = false;
					dy_seekbar.setEnabled(false);
				}
			});
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		isdetatilactivity = false;
		if (player != null) {
			player.release();
			player = null;
		}
		super.onDestroy();
	}

	public void getseek(int pos) {
		if (player != null) {
			player.seekTo(pos);
		}
		if (!player.isPlaying()) {
			player.start();
		}
	}

	class myThread implements Runnable {
		public String flurl;
		public Handler hd;

		public myThread(String flurl, Handler hd) {
			this.flurl = flurl;
			this.hd = hd;
		}

		public void run() {
			int count;
			try {
				URL url = new URL(flurl);
				URLConnection conexion = url.openConnection();
				conexion.connect();

				int lenghtOfFile = conexion.getContentLength();
				InputStream input = new BufferedInputStream(url.openStream());
				String filename = dazuidown.toString();
				OutputStream output = new FileOutputStream(filename
						+ File.separator + Comment.getMd5Hash(flurl) + ".amr");
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
				hd.sendMessage(message);

			} catch (Exception e) {
				// Toast.makeText(Dazuidetatil.this, "下载失败！", 1000).show();
			}
		}
	}

	Handler loadphotohd = new Handler() {
		public void handleMessage(Message msg) {

			ImageView iv = (ImageView) msg.obj;
			Bundle data = msg.getData();
			if (data != null) {
				String url = data.getString("url");
				String filename = data.getString("filename");
				Bitmap bmp = null;
				int w = data.getInt("w");
				int h = data.getInt("h");
				if (w > dm.widthPixels / 5 * 4) {
					bmp = Shangchuan.getbp(filename, dm.widthPixels / 5 * 4);
				} else {
					bmp = BitmapCacheDzlb.getIntance().getCacheBitmap(filename,
							0, 0);
				}
				// Bitmap bmp =
				// BitmapCacheDzlb.getIntance().getCacheBitmap(filename,
				// 0, 0);
				iv.setImageBitmap(bmp);

			}
		}
	};

	public class LoadImageRunnable2 implements Runnable {
		private Context mContext;
		private Handler mHandler;
		private String sUrl;
		private String mFilename;
		private ImageView iv;

		public LoadImageRunnable2(Context context, Handler h, String str,
				String filename, ImageView iv) {
			mHandler = h;
			mContext = context;
			sUrl = str;
			mFilename = filename;
			this.iv = iv;

		}

		@Override
		public void run() {
			Comment.urlToFile(mContext, sUrl, mFilename);
			Message msg = new Message();
			msg.obj = iv;
			Bundle data = new Bundle();
			data.putString("url", sUrl);
			data.putString("filename", mFilename);
			msg.setData(data);
			loadphotohd.sendMessage(msg);
		}
	}

	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				// Dazuidetatil.pb.setVisibility(View.GONE);
				dy_down_pb.setVisibility(View.GONE);
				// Commond.showToast(Dazuidetatil.this, "下载完成！");
				dy_bofang.setBackgroundResource(R.drawable.dy_zanting1);
				String filename = dazuidown + File.separator
						+ Comment.getMd5Hash(afile) + ".amr";
				startPlaying(filename);
				borz = true;
				isplay = true;
				Mydazui.scisnoty = true;
				new Thread(new update()).start();
				break;
			}
			super.handleMessage(msg);
		}
	};

	public void post(String urllink) {
		String result = "";
		// String url = appendNameValue(urllink, "pkg",
		// "com.jianrencun.chatroom");
		StringBuffer data = new StringBuffer();
		HttpRequestTask request = new HttpRequestTask();
		request.execute(urllink, data.toString());
	}

	public class MyClickListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
					Commond.showToast(Dydetatil.this, "检测到网络异常或未开启");
					return false;
				}
				yypl = true;
				if (LongTouch) {
					System.out.println("抬起....发送...");
					speakRelativeLayout.setVisibility(View.GONE);
					LongTouch = false;

					endtm = System.currentTimeMillis();
					milltime = (int) (endtm - bgtm);
					int hour = milltime / (60 * 60 * 1000);
					int minute = (milltime - hour * 60 * 60 * 1000)
							/ (60 * 1000);
					int seconds = (milltime - hour * 60 * 60 * 1000 - minute * 60 * 1000) / 1000;
					if (seconds <= 1) {
						Commond.showToast(Dydetatil.this, "想挣银币？多说俩句！！");
						vr.stop();
						return false;
					}

					try {
						vr.stop();
					} catch (Exception e) {
						// TODO: handle exception
						finish();
					}
					pb_ll.setVisibility(View.VISIBLE);
					urlpl = ConstantsJrc.DYPINGLUN;
					urlpl = Details.geturl(urlpl);
					urlpl = Details.appendNameValueint(urlpl, "flg", flg);
					urlpl = dt.appendNameValue(urlpl, "token", su.getToken());
					urlpl = Details.appendNameValueint(urlpl, "id", id);
					data = new StringBuffer();
					data.append("&uid=");
					data.append(MainMenuActivity.uid);
					data.append("id=");
					data.append(id);
					data.append("&alen=");
					data.append(seconds);
					myMediaSend.start();
					new Thread(new myThread2()).start();
				}
				break;

			}
			return false;
		}
	}

	Handler myHandler2 = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				pb_ll.setVisibility(View.GONE);
				// pb.setVisibility(View.GONE);
				items.clear();

				StringBuffer data = new StringBuffer();
				HttpRequestTask request = new HttpRequestTask();
				request.execute(detatilurl, data.toString());
				break;
			}
			super.handleMessage(msg);
		}
	};

	class myThread2 implements Runnable {
		public void run() {
			while (yypl) {
				byte[] bt = FileUpload.getInstance().upload(urlpl,
						data.toString(), "afile", su.getAudioPath());
				String sss = su.getAudioPath();
				String ss = new String(bt);
				try {
					JSONObject json = new JSONObject(ss);
					String tip;
					// pd.cancel();
					tip = URLDecoder.decode(json.optString("tip"));
					Commond.showToast(Dydetatil.this, tip);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				yypl = false;
				Message message = new Message();
				message.what = 1;
				myHandler2.sendMessage(message);

			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		// URL ur = new URL(header);
		if (resultCode == 0) {
			return;
		}
		// if (resultCode == 100) {
		// d
		// int position = data.getIntExtra("savephoto", 100);
		// if (position == 99) {
		//
		// String ss = ImageJavaScriptInterface.srcFileName ;
		// Bitmap bt = getPictureFromCache(ss);
		// saveMyBitmap(ss, bt);
		// }
		if (resultCode == 10) {
			detatilurl = dt.appendNameValue(ConstantsJrc.DTDETATIL, "uid",
					su.getUid());
			detatilurl = dt.appendNameValue(detatilurl, "token", URLEncoder.encode(su.getToken()));
			detatilurl = dt.appendNameValueint(detatilurl, "pd", 0);
			detatilurl = dt.appendNameValueint(detatilurl, "id", id);
			detatilurl = dt.appendNameValueint(detatilurl, "flg", flg);
            items.clear();
            pb_ll.setVisibility(View.VISIBLE);
			StringBuffer data1 = new StringBuffer();
			HttpRequestTask request = new HttpRequestTask();
			request.execute(detatilurl, data1.toString());
		}
	}

}
