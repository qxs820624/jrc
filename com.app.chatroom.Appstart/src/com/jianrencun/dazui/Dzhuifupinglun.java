package com.jianrencun.dazui;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.audio.VideoRecordDz;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.download.DownFileRight;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.updata.FileUpload;
import com.app.chatroom.util.Commond;
import com.app.chatroom.util.SystemUtil;
import com.jianrencun.android.Chakanpinglun;
import com.jianrencun.android.Details;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Dazuidetatil.MyClickListener;
import com.jianrencun.dazui.Dazuidetatil.myThread2;
import com.jianrencun.dazui.HttpBaseActivity.HttpRequestTask;
import com.jianrencun.dynamic.Dynamic;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class Dzhuifupinglun extends HttpBaseActivity implements
		OnScrollListener, OnClickListener {
	private ListView lv;
	// 面板
	private Button wenzi, yuyin, fabiao, anxiabnt;
	private EditText et;
	private View vFooter;
	private LinearLayout loading;
	// Intent 传值
	private int id;
	private Button back;
	String urlpl, urlqq, urlsave, nlink;
	private List<Pinglunitem> items;
	private ProgressBar pb;
	private boolean thswitch = true;
	private DownFileRight df;
	private AudioManager audio;
	private LinearLayout ll;
	private RelativeLayout dazuimianban;
	private ProgressBar llpb;
	private int twice;
	private List<String> dlpl;
	private List<Integer> dlplint;
	private PinglunAdapter adapter;
	public boolean LongTouch = false;
	private PinglunAdapter.ViewHolder holder;

	private boolean borz;
	public static Dzhuifupinglun context = null;
	private boolean isplay;

	private Handler hd;
	private final int message_what_position = 101;
	private ImageView shafa;
	private TextView logo;

	String filename;
	private int po;

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
	private boolean isdetatilactivity = true;

	/* 配置文件 */
	SharedPreferences sp;
	SystemSettingUtilSp su;
	int videoTime = 0;
	private ImageView recorder_volumeImageView;// 音频动画
	private RelativeLayout speakRelativeLayout;// 录音背景框
	private int dracation;
	int length;
	int pid;
	private boolean plgx = false;
	private String isdy;
	private int flg, page;
	private Details dt= new Details(); 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		try {
			setContentView(R.layout.dazuidetatil);
		} catch (Exception e) {
			// TODO: handle exception
			Commond.showToast(Dzhuifupinglun.this, "贱人村矫情了，请您重新登录一下！");
			finish();
		}

		init();
		context = Dzhuifupinglun.this;
		// 请求评论
		if (!TextUtils.isEmpty(isdy) && isdy.equalsIgnoreCase("yes")) {
			urlqq = ConstantsJrc.DTDETATIL;
			urlqq = Details.appendNameValueint(urlqq, "flg", flg);
		} else {
			urlqq = "http://jrc.hutudan.com/music/comments.php";
		}
		urlqq = Details.geturl(urlqq);
		urlqq = Details.appendNameValueint(urlqq, "id", id);
		urlqq = Details.appendNameValueint(urlqq, "pid", pid);
		post(urlqq);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

			}
		});

		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("content", items.get(position).getDesc()
						.toString());
				intent.putExtra("title", items.get(position).getUnick()
						.toString());
				intent.putExtra("from", 0);
				intent.putExtra("mid", items.get(position).getId());
				intent.setClass(Dzhuifupinglun.this, Chakanpinglun.class);
				startActivity(intent);
				return false;
			}
		});

		// View view = new View(getApplicationContext());
		// view = getLayoutInflater().inflate(R.layout.dazui_huifu_listheader,
		// null);
		// ImageView header = (ImageView)
		// view.findViewById(R.id.dz_huifu_touxiang);
		// TextView title = (TextView) view.findViewById(R.id.dz_huifu_title);
		// TextView nick = (TextView) view.findViewById(R.id.dz_huifu_nick);
		// lv.addHeaderView(view);

		anxiabnt.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
					Commond.showToast(Dzhuifupinglun.this, "检测到网络网络异常或未开启");
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
	}

	// 初始化
	private void init() {

		Intent it = getIntent();
		id = it.getIntExtra("id", 0);
		pid = it.getIntExtra("pid", 1);
		isdy = it.getStringExtra("isdy");
		flg = it.getIntExtra("flg", 0);

		// 按钮声音
		myMediaStart = MediaPlayer.create(getApplicationContext(),
				R.raw.ptt_startrecord);
		myMediaSend = MediaPlayer.create(getApplicationContext(),
				R.raw.sent_message);
		// 实例化录音
		vr = new VideoRecordDz(Dzhuifupinglun.this, recorder_volumeImageView);
		vr.init();
		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);

		df = new DownFileRight();
		audio = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
		items = new ArrayList<Pinglunitem>();
		dlpl = new ArrayList<String>();
		dlplint = new ArrayList<Integer>();
		vFooter = LayoutInflater.from(this).inflate(R.layout.list_footer, null);
		loading = (LinearLayout) vFooter.findViewById(R.id.list_footer);
		loading.setVisibility(View.GONE);
		lv = (ListView) findViewById(R.id.dazuidetatil_lv);
		wenzi = (Button) findViewById(R.id.dazuidetatil_wenzi);
		ll = (LinearLayout) findViewById(R.id.dazui_detatil_ll);
		dazuimianban = (RelativeLayout) findViewById(R.id.dazuimianban);
		llpb = (ProgressBar) findViewById(R.id.dazui_detatil_pb);
		yuyin = (Button) findViewById(R.id.dazuidetatil_yuyin);
		back = (Button) findViewById(R.id.dazuidetatil_back);
		fabiao = (Button) findViewById(R.id.dazuidetatil_fabiao);
		anxiabnt = (Button) findViewById(R.id.dazuidetatil_anzhushuohua);
		et = (EditText) findViewById(R.id.dazuidetatil_et);
		shafa = (ImageView) findViewById(R.id.dazuidetatil_shafa);
		recorder_volumeImageView = (ImageView) findViewById(R.id.dazui_speakvolume_ImageView);
		speakRelativeLayout = (RelativeLayout) findViewById(R.id.dazui_speak_RelativeLayout);

		logo = (TextView) findViewById(R.id.dz_pinglun_huifu);
		logo.setBackgroundResource(R.drawable.pinglunhuifu);

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

		wenzi.setOnClickListener(this);
		yuyin.setOnClickListener(this);
		back.setOnClickListener(this);
		fabiao.setOnClickListener(this);
		anxiabnt.setOnClickListener(this);

		lv.setOnScrollListener(this);
		lv.addFooterView(vFooter);
		lv.setFooterDividersEnabled(false);

	}

	public void post(String urllink) {
		String result = "";
		// String url = appendNameValue(urllink, "pkg",
		// "com.jianrencun.chatroom");
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == back) {
			if (adapter != null) {
				if (adapter.mediaPlayer != null) {
					if (adapter.mediaPlayer.isPlaying()) {
						adapter.mediaPlayer.stop();
						adapter.mediaPlayer = null;
					}
				}
			}

			if (plgx == true) {
				Intent data = new Intent();
				// 请求代码可以自己设置，这里设置成20
				setResult(10, data);
			}
			finish();
		}
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
				Commond.showToast(Dzhuifupinglun.this, "输入内容，再评论哦！");
				et.requestFocus();
				return;
			}
			v.setEnabled(false);
			ll.setVisibility(View.VISIBLE);
			if (!TextUtils.isEmpty(isdy) && isdy.equalsIgnoreCase("yes")) {
				urlpl = ConstantsJrc.DYPINGLUN;
				urlpl = Details.appendNameValueint(urlpl, "flg", flg);
			} else {
				urlpl = "http://jrc.hutudan.com/music/sendreply.php";
			}
			urlpl = Details.geturl(urlpl);
			urlpl = Details.appendNameValueint(urlpl, "id", id);
			urlpl = Details.appendNameValueint(urlpl, "pid", pid);
			StringBuffer data = new StringBuffer();
			data.append("&input=");
			data.append(URLEncoder.encode(et.getText().toString()));
			data.append("&id=");
			data.append(id);
			// 请求网络验证登陆
			HttpRequestTask request = new HttpRequestTask();
			request.execute(urlpl, data.toString());
		}
	}

	public class MyClickListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
					Commond.showToast(Dzhuifupinglun.this, "检测到网络异常或未开启");
					return false;
				}
				yypl = true;
				if (LongTouch) {
					System.out.println("抬起....发送...");
					speakRelativeLayout.setVisibility(View.GONE);
					LongTouch = false;
					vr.stop();

					endtm = System.currentTimeMillis();
					milltime = (int) (endtm - bgtm);
					int hour = milltime / (60 * 60 * 1000);
					int minute = (milltime - hour * 60 * 60 * 1000)
							/ (60 * 1000);
					int seconds = (milltime - hour * 60 * 60 * 1000 - minute * 60 * 1000) / 1000;
					if (seconds <= 1) {
						Commond.showToast(Dzhuifupinglun.this, "想挣银币？多说俩句！！");
						return false;
					}
					if (!TextUtils.isEmpty(isdy)
							&& isdy.equalsIgnoreCase("yes")) {
						urlpl = ConstantsJrc.DYPINGLUN;
						urlpl = Details.appendNameValueint(urlpl, "flg", flg);
					} else {
						urlpl = "http://jrc.hutudan.com/music/sendreply.php";
					}
					urlpl = Details.geturl(urlpl);
					urlpl = Details.appendNameValueint(urlpl, "id", id);
					urlpl = Details.appendNameValueint(urlpl, "pid", pid);
					ll.setVisibility(View.VISIBLE);
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
				ll.setVisibility(View.GONE);
				// pb.setVisibility(View.GONE);
				items.clear();

				StringBuffer data = new StringBuffer();
				HttpRequestTask request = new HttpRequestTask();
				request.execute(urlqq, data.toString());
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
					Commond.showToast(Dzhuifupinglun.this, tip);
					plgx = true;
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
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			if (view.getLastVisiblePosition() == view.getCount() - 1) {
				if(!TextUtils.isEmpty(isdy) &&isdy.equalsIgnoreCase("yes")){
					if (page == 0 ) {
						lv.removeFooterView(vFooter);
						return;
					} else {			
						twice = 3;
						if (!dlplint.contains(page)) {
							loading.setVisibility(View.VISIBLE);
							String url = ConstantsJrc.DTDETATIL;
							StringBuffer data = new StringBuffer();

							data.append("pkg=");
							data.append(URLEncoder.encode(getPackageName()));
							//
							// data.append("&pd=");
							// lastdate = currentdate;
							// data.append(URLEncoder.encode(lastdate));
							// 请求网络验证登陆
							url = Details.geturl(url);
							url = Details.appendNameValueint(url, "page", page);
							url = Details.appendNameValueint(url, "flg", flg);
							url = dt.appendNameValue(url, "uid", su.getUid());
							url = Details.appendNameValueint(url, "id", id);
							url = Details.appendNameValueint(url, "pid", pid);
							url = dt.appendNameValue(url, "token",URLEncoder.encode(su.getToken()));
//							url = dt.appendNameValueint(url, "pd", pd);
							HttpRequestTask request = new HttpRequestTask();
							request.execute(url, data.toString());
							dlplint.add(page);
						}
					}
				}else{			
				if (TextUtils.isEmpty(nlink)) {
					lv.removeFooterView(vFooter);
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
						// data.append("&pd=");
						// lastdate = currentdate;
						// data.append(URLEncoder.encode(lastdate));
						// 请求网络验证登陆
						nlink = Details.geturl(url);
						HttpRequestTask request = new HttpRequestTask();
						request.execute(nlink, data.toString());
						dlpl.add(nlink);
					}
				}				
			}
			}
		}
	}

	@Override
	void resultData(String url, String result) {
		// TODO Auto-generated method stub
		try {
			Log.e("dfwefsefwa", url + ",," + result + "");
			JSONObject jsonChannel = new JSONObject(result);
			int ret = jsonChannel.optInt("ret");
			if (ret == 0) {
				Commond.showToast(Dzhuifupinglun.this, "贱人村矫情了,请重新试一次...");
			}
			String tip = URLDecoder.decode(jsonChannel.optString("tip"));
			String pd = URLDecoder.decode(jsonChannel.optString("pd"));
			if (!TextUtils.isEmpty(isdy) && isdy.equalsIgnoreCase("yes")) {
				page = jsonChannel.optInt("page");
			} else {
				nlink = URLDecoder.decode(jsonChannel.optString("nlink"));
			}
			String btm = URLDecoder.decode(jsonChannel.optString("btime"));

			if (urlpl != null && url.contains(urlpl)) {
				et.clearFocus();
				et.setHint("亲，说点什么吧！");
				et.setEnabled(true);
				fabiao.setEnabled(true);
				et.setText("");
				et.setGravity(Gravity.CENTER_VERTICAL);
				items.clear();
				post(urlqq);
				plgx = true;
				return;
			}
			//
			// if (!TextUtils.isEmpty(btm)) {

			// currentdate = pd;

			JSONArray jsonItems = jsonChannel.optJSONArray("items");
			for (int i = 0; i < jsonItems.length(); i++) {
				JSONObject jsonItem = jsonItems.getJSONObject(i);
				int id = jsonItem.optInt("id");
				int uid = jsonItem.optInt("uid");
				String unick;
				if (!TextUtils.isEmpty(isdy) && isdy.equalsIgnoreCase("yes")) {
					unick = URLDecoder.decode(jsonItem.optString("nick"));
				} else {
					unick = URLDecoder.decode(jsonItem.optString("unick"));
				}
				String uhead;
				if (!TextUtils.isEmpty(isdy) && isdy.equalsIgnoreCase("yes")) {
					uhead = URLDecoder.decode(jsonItem.optString("header"));
				} else {
					uhead = URLDecoder.decode(jsonItem.optString("uheader"));
				}
				String date;
				if (!TextUtils.isEmpty(isdy) && isdy.equalsIgnoreCase("yes")) {
					date = URLDecoder.decode(jsonItem.optString("desc1"));
				} else {
					date = URLDecoder.decode(jsonItem.optString("date"));
				}
				String afile = URLDecoder.decode(jsonItem.optString("afile"));
				String pfile = URLDecoder.decode(jsonItem.optString("pfile"));
				String desc = URLDecoder.decode(jsonItem.optString("desc"));
				String desc_c = URLDecoder.decode(jsonItem.optString("desc_c"));
				String nameclor;
				if (!TextUtils.isEmpty(isdy) && isdy.equalsIgnoreCase("yes")) {
					nameclor = URLDecoder.decode(jsonItem.optString("nick_c"));
				} else {
					nameclor = URLDecoder.decode(jsonItem.optString("unick_c"));
				}
				String numofdiscuss = URLDecoder.decode(jsonItem.optString(""));
				int aflg = jsonItem.optInt("aflg");
				int alen = jsonItem.optInt("alen");
				int pid = jsonItem.optInt("pid");
				int bj = jsonItem.optInt("uhflg");
				if (TextUtils.isEmpty(desc) && !TextUtils.isEmpty(afile)) {
					tp = 1;
				} else {
					tp = 0;
				}
				Pinglunitem item = new Pinglunitem(id, uid, unick, uhead, date,
						afile, pfile, desc, desc_c, tp, alen, false, nameclor,
						aflg, pid, numofdiscuss, bj);
				items.add(item);
			}

			// }
			if (urlqq != null && url.contains(urlqq)) {
				ll.setVisibility(View.GONE);
				adapter = new PinglunAdapter(Dzhuifupinglun.this, items, btm,
						lv, false);
				if (items.size() == 0) {
					shafa.setVisibility(View.VISIBLE);
				} else {
					shafa.setVisibility(View.GONE);
				}
				lv.setAdapter(adapter);

			} else if (nlink != null) {
				if (twice == 3) {
					lv.requestLayout();
					adapter.notifyDataSetChanged();
					loading.setVisibility(View.GONE);
				}
			}else {
				if (twice == 3) {
					lv.requestLayout();
					adapter.notifyDataSetChanged();
					loading.setVisibility(View.GONE);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			// fresh.setVisibility(View.VISIBLE);
			// pgd.setVisibility(View.GONE);
			// rllt2.setVisibility(View.GONE);
			// pb.setVisibility(View.GONE);
			// rllt3.setVisibility(View.GONE);
			// pgshoucang.setVisibility(View.GONE);
			// edittext.setHint("亲，说点什么吧！");
			// edittext.setEnabled(true);
			// edittext.setText("");
			// Commond.showToast(Details.this, "操作失败！请检查网络！");
			e.printStackTrace();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (plgx == true) {
				Intent data = new Intent();
				// 请求代码可以自己设置，这里设置成20
				setResult(10, data);
			}
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
		plgx = false;
		Dazuidetatil.detatilispause = false;
		if (DazuiActivity.player != null) {
			if (MainMenuActivity.borz == true) {
				DazuiActivity.player.start();
			}
		}
		if (Dzmysave.player != null) {
			if (MainMenuActivity.borz == true) {
				Dzmysave.player.start();
			}
		}
		if (Mydazui.player != null) {
			if (MainMenuActivity.borz == true) {
				Mydazui.player.start();
			}
		}
		if(Dynamic.dyplayer != null){
			if (MainMenuActivity.borz == true) {
				Dynamic.dyplayer.start();
			}
		}
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if (DazuiActivity.player != null && DazuiActivity.player.isPlaying()) {
			if (Dazuidetatil.detatilispause == false) {
				DazuiActivity.player.pause();
			}
		}
		if (Dzmysave.player != null && Dzmysave.player.isPlaying()) {
			if (Dazuidetatil.detatilispause == false) {
				Dzmysave.player.pause();
			}
		}
		if (Mydazui.player != null && Mydazui.player.isPlaying()) {
			if (Dazuidetatil.detatilispause == false) {
				Mydazui.player.pause();
			}
		}
		super.onPause();
	}
}
