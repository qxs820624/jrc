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

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;



import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.audio.VideoRecord;
import com.app.chatroom.audio.VideoRecordDz;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.download.DownFileRight;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.updata.FileUpload;
import com.app.chatroom.util.Commond;
import com.app.chatroom.util.SystemUtil;
import com.duom.fjz.iteminfo.BitmapCache1;
import com.jianrencun.android.Chakanpinglun;
import com.jianrencun.android.Details;
import com.jianrencun.chatroom.R;
import com.jianrencun.dynamic.Dynamic;
import com.umeng.common.Log;

public class Dazuidetatil extends HttpBaseActivity implements OnScrollListener,
		OnClickListener {
	private ListView lv;
	// 面板
	private Button wenzi, yuyin, fabiao, anxiabnt, shuaxin;
	private EditText et;
	private View vFooter;
	private LinearLayout loading;
	// Intent 传值
	private int id;
	private String fileurl, title, len;
	private Button back;
	String urlpl, urlqq, urlsave, nlink;
	private List<Pinglunitem> items;
	private int type;
	private ProgressBar pb;
	private boolean thswitch = true;
	private DownFileRight df;
	private AudioManager audio;
	private LinearLayout ll;
	private RelativeLayout dazuimianban;
	private ProgressBar llpb;
	private int twice;
	private List<String> dlpl;
	private PinglunAdapter adapter;
	public boolean LongTouch = false;
	private PinglunAdapter.ViewHolder holder;

	private boolean borz;
	public static Dazuidetatil context = null;
	private boolean isplay;

	private Handler hd;
	private final int message_what_position = 101;
	private ImageView shafa;

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
	private TextView logo;

	public static boolean detatilispause = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dazuidetatil);

		init();
		context = this;
		// 请求评论
		urlqq = "http://jrc.hutudan.com/music/comments.php";
		urlqq = Details.geturl(urlqq);
		urlqq = Details.appendNameValueint(urlqq, "id", id);
		post(urlqq);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long idd) {
				// TODO Auto-generated method stub
				Dazuidetatil.detatilispause = true;
				if (items.get(position).getPid() == 0) {
					Intent it = new Intent();
					it.setClass(Dazuidetatil.this, Dzhuifupinglun.class);
					it.putExtra("pid", items.get(position).getId());
					it.putExtra("id", id);
					startActivityForResult(it, 2);
				} else {
					Intent it = new Intent();
					it.setClass(Dazuidetatil.this, Dzhuifupinglun.class);
					it.putExtra("pid", items.get(position).getPid());
					it.putExtra("id", id);
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
				intent.putExtra("content", items.get(position).getDesc()
						.toString());
				intent.putExtra("title", items.get(position).getUnick()
						.toString());
				intent.putExtra("from", 0);
				intent.putExtra("mid", items.get(position).getId());
				intent.setClass(Dazuidetatil.this, Chakanpinglun.class);
				startActivity(intent);
				return false;
			}
		});

		anxiabnt.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
					Commond.showToast(Dazuidetatil.this, "检测到网络网络异常或未开启");
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
		//
		// // 每项删除按钮事件
		// listener = new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// holder = (PinglunAdapter.ViewHolder) v
		// .getTag();
		//
		// }
		// };
	}

	public class MyClickListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
					Commond.showToast(Dazuidetatil.this, "检测到网络异常或未开启");
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
						Commond.showToast(Dazuidetatil.this, "想挣银币？多说俩句！！");
						vr.stop();
						return false;
					}

					try {
						vr.stop();
					} catch (Exception e) {
						// TODO: handle exception
						finish();
					}
					urlpl = "http://jrc.hutudan.com/music/sendreply.php";
					urlpl = Details.geturl(urlpl);
					urlpl = Details.appendNameValueint(urlpl, "id", id);
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

	// 初始化
	private void init() {

		Intent it = getIntent();
		id = it.getIntExtra("ypid", 0);
		title = it.getStringExtra("title");
		fileurl = it.getStringExtra("url");
		len = it.getStringExtra("len");
		type = it.getIntExtra("isfav", 1);
		po = it.getIntExtra("po", 2);
		fromwhich = it.getIntExtra("which", 3);

		// 按钮声音
		myMediaStart = MediaPlayer.create(getApplicationContext(),
				R.raw.ptt_startrecord);
		myMediaSend = MediaPlayer.create(getApplicationContext(),
				R.raw.sent_message);
		// 实例化录音
		vr = new VideoRecordDz(Dazuidetatil.this, recorder_volumeImageView);
		vr.init();
		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);

		df = new DownFileRight();
		audio = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
		items = new ArrayList<Pinglunitem>();
		dlpl = new ArrayList<String>();
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
		shuaxin = (Button) findViewById(R.id.dazuidetatil_shuaxin);
		fabiao = (Button) findViewById(R.id.dazuidetatil_fabiao);
		anxiabnt = (Button) findViewById(R.id.dazuidetatil_anzhushuohua);
		et = (EditText) findViewById(R.id.dazuidetatil_et);
		shafa = (ImageView) findViewById(R.id.dazuidetatil_shafa);
		recorder_volumeImageView = (ImageView) findViewById(R.id.dazui_speakvolume_ImageView);
		speakRelativeLayout = (RelativeLayout) findViewById(R.id.dazui_speak_RelativeLayout);
		logo = (TextView) findViewById(R.id.dz_pinglun_huifu);
		logo.setBackgroundResource(R.drawable.dazuipinglun);
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
		shuaxin.setOnClickListener(this);

		lv.setOnScrollListener(this);
		lv.addFooterView(vFooter);
		lv.setFooterDividersEnabled(false);

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
	void resultData(String url, String result) {
		// TODO Auto-generated method stub
		String tip = "";
		try {
			JSONObject jsonChannel = new JSONObject(result);
			int ret = jsonChannel.optInt("ret");
			tip = URLDecoder.decode(jsonChannel.optString("tip"));
			String pd = URLDecoder.decode(jsonChannel.optString("pd"));
			nlink = URLDecoder.decode(jsonChannel.optString("nlink"));
			String btm = URLDecoder.decode(jsonChannel.optString("btime"));
			//
			if (!TextUtils.isEmpty(btm)) {

				// currentdate = pd;

				JSONArray jsonItems = jsonChannel.optJSONArray("items");
				for (int i = 0; i < jsonItems.length(); i++) {
					JSONObject jsonItem = jsonItems.getJSONObject(i);
					int id = jsonItem.optInt("id");
					int uid = jsonItem.optInt("uid");
					String unick = URLDecoder.decode(jsonItem
							.optString("unick"));
					String uhead = URLDecoder.decode(jsonItem
							.optString("uheader"));
					String date = URLDecoder.decode(jsonItem.optString("date"));
					String afile = URLDecoder.decode(jsonItem
							.optString("afile"));
					String pfile = URLDecoder.decode(jsonItem
							.optString("pfile"));
					String desc = URLDecoder.decode(jsonItem.optString("desc"));
					String desc_c = URLDecoder.decode(jsonItem
							.optString("desc_c"));
					String nameclor = URLDecoder.decode(jsonItem
							.optString("unick_c"));
					String numofdiscuss = URLDecoder.decode(jsonItem
							.optString("rnum"));
					int aflg = jsonItem.optInt("aflg");
					int alen = jsonItem.optInt("alen");
					int pid = jsonItem.optInt("pid");
					int bj = jsonItem.optInt("uhflg");

					if (TextUtils.isEmpty(desc) && !TextUtils.isEmpty(afile)) {
						tp = 1;
					} else {
						tp = 0;
					}
					Pinglunitem item = new Pinglunitem(id, uid, unick, uhead,
							date, afile, pfile, desc, desc_c, tp, alen, false,
							nameclor, aflg, pid, numofdiscuss,bj);
					items.add(item);
				}

			}
			if (urlpl != null && url.contains(urlpl)) {
				et.clearFocus();
				et.setHint("亲，说点什么吧！");
				et.setEnabled(true);
				fabiao.setEnabled(true);
				et.setText("");
				et.setGravity(Gravity.CENTER_VERTICAL);
				items.clear();
				post(urlqq);
			} else if (urlqq != null && url.contains(urlqq)) {
				ll.setVisibility(View.GONE);
				adapter = new PinglunAdapter(Dazuidetatil.this, items, btm, lv,
						false);
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
		if (!TextUtils.isEmpty(tip)) {
			Commond.showToast(Dazuidetatil.this, tip);
		}
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
				Commond.showToast(Dazuidetatil.this, "输入内容，再评论哦！");
				et.requestFocus();
				return;
			}
			v.setEnabled(false);
			ll.setVisibility(View.VISIBLE);
			urlpl = "http://jrc.hutudan.com/music/sendreply.php";
			urlpl = Details.geturl(urlpl);
			urlpl = Details.appendNameValueint(urlpl, "id", id);
			StringBuffer data = new StringBuffer();
			data.append("&input=");
			data.append(URLEncoder.encode(et.getText().toString()));
			data.append("&id=");
			data.append(id);
			// 请求网络验证登陆
			HttpRequestTask request = new HttpRequestTask();
			request.execute(urlpl, data.toString());
		}
		// 这个是刷新
		if (v == shuaxin) {
			items.clear();
			dlpl.clear();
			ll.setVisibility(View.VISIBLE);
			post(urlqq);
		}
	}

	// Handler myHandler = new Handler() {
	// public void handleMessage(Message msg) {
	// switch (msg.what) {
	// case 0:
	// pb.setVisibility(View.GONE);
	// // Commond.showToast(Dazuidetatil.this, "下载完成！");
	// String filename = DazuiActivity.dazuisdown.getAbsolutePath()
	// + File.separator + Comment.getMd5Hash(fileurl) + ".amr";
	// borz = true;
	// isplay = true;
	// Mydazui.scisnoty = true;
	// DazuiActivity.isnotify = true;
	// new Thread(new update()).start();
	// break;
	// }
	// super.handleMessage(msg);
	// }
	// };

	class myThread implements Runnable {
		public String flurl;
		public Handler hd;

		public myThread(String flurl, Handler hd) {
			this.flurl = flurl;
			this.hd = hd;
		}

		public void run() {
			if (thswitch) {
				int count;
				try {
					URL url = new URL(flurl);
					URLConnection conexion = url.openConnection();
					conexion.connect();

					int lenghtOfFile = conexion.getContentLength();
					InputStream input = new BufferedInputStream(
							url.openStream());
					String filename = MainMenuActivity.dazuisdown.getPath()
							.toString();
					OutputStream output = new FileOutputStream(filename
							+ File.separator + Comment.getMd5Hash(flurl)
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
					hd.sendMessage(message);

				} catch (Exception e) {
					// Toast.makeText(Dazuidetatil.this, "下载失败！", 1000).show();
				}
			}
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
				String oo = su.getAudioPath().toString();
				byte[] bt = FileUpload.getInstance().upload(urlpl,
						data.toString(), "afile", su.getAudioPath());
				String sss = su.getAudioPath();
				String ss = new String(bt);
				try {
					JSONObject json = new JSONObject(ss);
					String tip;
					// pd.cancel();
					tip = URLDecoder.decode(json.optString("tip"));
					Commond.showToast(Dazuidetatil.this, tip);
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

	public void mpprepar(String mFlilepath) {

		try {
			File file = new File(mFlilepath);
			FileInputStream fis = new FileInputStream(file);
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
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
			ll.setVisibility(View.VISIBLE);
			urlqq = "http://jrc.hutudan.com/music/comments.php";
			urlqq = Details.geturl(urlqq);
			urlqq = Details.appendNameValueint(urlqq, "id", id);
			items.clear();
			dlpl.clear();
			post(urlqq);
		}
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
			finish();
			if (adapter != null) {
				if (adapter.mediaPlayer != null) {
					if (adapter.mediaPlayer.isPlaying()) {
						adapter.mediaPlayer.stop();
						adapter.mediaPlayer = null;
					}
				}
			}
			return true;
		default:
			break;
		}
		return false;
	}

	/**
		 * 
		 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent e) {
		// if (e.getAction() == MotionEvent.ACTION_DOWN) {
		// //类型为Down才处理，还有Move/Up之类的
		Rect r = new Rect();
		dazuimianban.getGlobalVisibleRect(r);

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

	// class update implements Runnable {
	//
	// @Override
	// public void run() {
	// while (hd != null && player != null && sk.getProgress() < length) {
	// if (isplay) {
	// Message message = hd.obtainMessage();
	// if (null != player) {
	// if (!player.isPlaying()) {// 如果不在播放状态，则停止更新//播放器进度条，防止界面报错
	//
	// break;
	// }
	// }
	// message.arg1 = player.getCurrentPosition() / 1000;
	// message.what = message_what_position;
	// hd.sendMessage(message);
	// }
	// try {
	// Thread.sleep(1000);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }
	// }

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			if (view.getLastVisiblePosition() == view.getCount() - 1) {
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

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		isdetatilactivity = false;
		if (PinglunAdapter.mediaPlayer != null) {
			PinglunAdapter.mediaPlayer.release();
			PinglunAdapter.mediaPlayer = null;
		}

		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		freshmysave = false;
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
		if (DazuiActivity.player != null && DazuiActivity.player.isPlaying()) {
			if (detatilispause == false) {
				DazuiActivity.player.pause();
			}
		}
		if (Dzmysave.player != null && Dzmysave.player.isPlaying()) {
			if (detatilispause == false) {
				Dzmysave.player.pause();
			}
		}
		if (Mydazui.player != null && Mydazui.player.isPlaying()) {
			if (detatilispause == false) {
				Mydazui.player.pause();
			}
		}
		super.onPause();
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub

	}

	/** 处理操作结果 */
	// public boolean handleMessage(Message msg) {
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
	// Toast.makeText(Dazuidetatil.this, text, Toast.LENGTH_SHORT).show();
	// return false;
	// }

}
