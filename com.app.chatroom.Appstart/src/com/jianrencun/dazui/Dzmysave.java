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
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.sharesdk.onekeyshare.EditPage;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.otherui.VillageUserInfoDialog;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;
import com.jianrencun.android.Details;
import com.jianrencun.chatroom.R;
import com.weibo.joechan.util.TextUtil;

public class Dzmysave extends HttpBaseActivity implements Callback,
		OnScrollListener {
	private Button back, freshbnt;
	private ProgressBar pb;
	private LinearLayout ll;
	private ListView lv;
	private List<DazuiIteminfo> list;
	OnClickListener listener, listener_osc, listener_liwu, listener_save,
			listener_share;
	private DazuiAdapter2.ViewHolder holder;
	DazuiAdapter2 ad;
	private String fileurl;
	private int position, bfposition;
	public static MediaPlayer player;
	private List<String> lsfileurl;
	private List<String> bofangurl;
	private List<Integer> po;
	public static boolean saveisnoty;
	private Handler handler;
	private int twice;
	private View vFooter;
	private LinearLayout loading;
	private List<String> dllinks;
	private String currentdate = "", lastdate;
	private String nlink;
	private String ouid, url;
	/* 配置文件 */
	SharedPreferences sp;
	SystemSettingUtilSp su;
	public static boolean dsispause;
	private AudioManager audio;
	private TextView tv;
	private int which ;
	private int type ;
	private ImageView nofabiao ;
	private Details dt= new Details(); 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dzmysave);
		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
		Intent it = getIntent();
		ouid = it.getStringExtra("ouid");
		url = it.getStringExtra("osc");
		which = it.getIntExtra("which", 0);
		type = it.getIntExtra("type", 2);

		audio = (AudioManager) getSystemService(Service.AUDIO_SERVICE);

		nofabiao = (ImageView) findViewById(R.id.dznofabiao);
		back = (Button) findViewById(R.id.dazui_save_back);
		pb = (ProgressBar) findViewById(R.id.dazui_save_pb);
		ll = (LinearLayout) findViewById(R.id.dazui_save_ll);
		lv = (ListView) findViewById(R.id.dazui_save_lv);
		tv = (TextView) findViewById(R.id.dslogo);
		list = new ArrayList<DazuiIteminfo>();
		lsfileurl = new ArrayList<String>();
		bofangurl = new ArrayList<String>();
		po = new ArrayList<Integer>();
		dllinks = new ArrayList<String>();

		if (DazuiAdapter2.th != null) {
			DazuiAdapter2.th = null;
		}
		if(!TextUtil.isEmpty(ouid)){
		if (ouid.equalsIgnoreCase(su.getUid())) {
			tv.setBackgroundResource(R.drawable.wodefabiao);
		} else {
			if(type == 1){
				tv.setBackgroundResource(R.drawable.jiazufabiaodz);
			}else{
			tv.setBackgroundResource(R.drawable.tadefabiao);
			}
		}
		}else{
			if(which == 1){
				tv.setBackgroundResource(R.drawable.dazuidaren);
			}else if(which == 2){
				tv.setBackgroundResource(R.drawable.remenpaihang);
			}else if(which == 0){
			tv.setBackgroundResource(R.drawable.jingcaizhuanti_logo);
	    	}
		}

		vFooter = LayoutInflater.from(this).inflate(R.layout.list_footer, null);
		loading = (LinearLayout) vFooter.findViewById(R.id.list_footer);
		loading.setVisibility(View.GONE);

		lv.setOnScrollListener(this);
		lv.addFooterView(vFooter);
		lv.setFooterDividersEnabled(false);

		handler = new Handler(this);

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				stopPlay();
				saveisnoty = true;
				list.get(bfposition).setStartorstop(0);
				list.get(bfposition).setJindubg(0);
				Intent intent = new Intent();
				intent.putExtra("len", list.get(position).getLen().toString());
				intent.putExtra("url", list.get(position).getAfile().toString());
				intent.putExtra("title", list.get(position).getTitle()
						.toString());
				intent.putExtra("ypid", list.get(position).getId());
				intent.putExtra("isfav", list.get(position).getIsfav());
				intent.putExtra("po", position);
				intent.setClass(Dzmysave.this, Dazuidetatil.class);
				startActivity(intent);
			}
		});

		// 每项按钮事件
		listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				holder = (DazuiAdapter2.ViewHolder) v.getTag();
				bflisener(list, ad);
			}
		};
		// 顶
		listener_osc = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
           Commond.showToast(Dzmysave.this, "不要点了，你已经在查看他（她）的发布了！");
			}
		};
		// 踩
		listener_liwu = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				holder = (DazuiAdapter2.ViewHolder) v.getTag();
				dsispause = true;
				Intent it = new Intent();
				it.putExtra("fuid", MainMenuActivity.uid);
				it.putExtra("uid", String.valueOf(holder.ouid));
				it.putExtra("chatroom", "1");
				it.putExtra("src", 3);
				it.putExtra("oid", list.get(holder.position).getId());	
				it.setClass(Dzmysave.this, VillageUserInfoDialog.class);
				startActivity(it);
			}
		};
		// 收藏
		listener_save = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				holder = (DazuiAdapter2.ViewHolder) v.getTag();
				dsispause = true;
				if (list.get(holder.position).getIsfav() == 0) {
					listener_save(list, lv, 0, holder.position);
				} else if (list.get(holder.position).getIsfav() == 1) {
					listener_save(list, lv, 1, holder.position);
				}
			}
		};
		listener_share = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				holder = (DazuiAdapter2.ViewHolder) v.getTag();
				dsispause = true ;
				showShare(false, list, holder.position , null);
			}
		};
		url = dt.appendNameValue(url, "uid", su.getUid());
		url = dt.appendNameValue(url, "ouid", ouid);
		StringBuffer data = new StringBuffer();
		HttpRequestTask request = new HttpRequestTask();
		request.execute(url, data.toString());
	}

	// 播放按钮监听方法
	// ////////////////////////////
	// ////////////
	private void bflisener(List<DazuiIteminfo> list, DazuiAdapter2 da) {

		fileurl = list.get(holder.position).getAfile();
		position = holder.position;
		bofangurl.add(fileurl);
		po.add(position);
		if (bfposition == position) {

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
				} else if (list.get(position).getStartorstop() == 0) {
					player.start();
					MainMenuActivity.borz = true;
					holder.dz_sybf
							.setBackgroundResource(R.drawable.dazuidetailzt1);
					list.get(position).setStartorstop(1);
					list.get(position).setJindubg(1);
				}
			} else {
				File picfile = new File(MainMenuActivity.dazuisdown
						+ File.separator + Comment.getMd5Hash(fileurl) + ".amr");
				String filename = picfile.getPath().toString();
				if (picfile.exists()) {
					startPlaying(filename, position);
					list.get(position).setStartorstop(1);
					list.get(position).setJindubg(1);
					lv.requestLayout();
					da.notifyDataSetChanged();
				} else {
					if (!lsfileurl.contains(fileurl)) {
						holder.dazuiliebiao_pb.setVisibility(View.VISIBLE);
						new Thread(new myThread(fileurl)).start();
						list.get(position).setStartorstop(2);

					}
				}
			}
		} else {
			File picfile = new File(MainMenuActivity.dazuisdown
					+ File.separator + Comment.getMd5Hash(fileurl) + ".amr");
			String filename = picfile.getPath().toString();
			if (picfile.exists()) {
				startPlaying(filename, position);
				list.get(position).setStartorstop(1);
				list.get(position).setJindubg(1);

				list.get(bfposition).setStartorstop(0);
				list.get(bfposition).setJindubg(0);

				int xx = position;
				int yy = bfposition;
				DazuiIteminfo it = list.get(position);
				da.notifyDataSetChanged();
				bfposition = position;
			} else {
				if (!lsfileurl.contains(fileurl)) {
					holder.dazuiliebiao_pb.setVisibility(View.VISIBLE);
					new Thread(new myThread(fileurl)).start();
					list.get(position).setStartorstop(2);

				}
			}
			// bls.set(position, true);
		}
	}

	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				String str2 = msg.getData().getString("fileurl");// 接受msg传递过来的参数

				for (int i = 0; i < bofangurl.size() - 1; i++) {
					String str3 = bofangurl.get(i);
					if (str2.equalsIgnoreCase(bofangurl.get(i))) {
						Commond.showToast(Dzmysave.this, "下载完成！");
						list.get(po.get(i)).setStartorstop(0);
						list.get(po.get(i)).setJindubg(0);
						lv.requestLayout();
						ad.notifyDataSetChanged();
					}
				}
				if (str2.equalsIgnoreCase(bofangurl.get(bofangurl.size() - 1))) {
					String filename = MainMenuActivity.dazuisdown
							.getAbsolutePath()
							+ File.separator
							+ Comment
									.getMd5Hash(bofangurl.get(bofangurl.size() - 1))
							+ ".amr";
					list.get(po.get(po.size() - 1)).setStartorstop(1);
					list.get(po.get(po.size() - 1)).setJindubg(1);
					if (po.get(po.size() - 1) != bfposition) {
						list.get(bfposition).setStartorstop(0);
						list.get(bfposition).setJindubg(0);
						bfposition = position;
					}

					ad.notifyDataSetChanged();
					startPlaying(filename, po.get(po.size() - 1));
				}
				break;
			case 1:
				Commond.showToast(Dzmysave.this, "网络不给力啊！！");				
				
				if (list.size() == 0 ||list == null) {
					finish();
					return;
				}
				list.get(position).setStartorstop(0);
				list.get(position).setJindubg(0);
				if (ad != null) {
					lv.requestLayout();
					ad.notifyDataSetChanged();
				}
				break;
			}
			super.handleMessage(msg);
		}
	};

	class myThread implements Runnable {
		public String furl;

		public myThread(String url) {
			this.furl = url;
		}

		public void run() {
			int count;
			try {
				// for(int i = 0 ; i<bofangurl.size()-1 ;i++){
				URL url = new URL(furl);
				URLConnection conexion = url.openConnection();
				conexion.connect();

				int lenghtOfFile = conexion.getContentLength();
				InputStream input = new BufferedInputStream(url.openStream());
				// Log.i("audio", filePath + "/" + fileUrl);
				String filename = MainMenuActivity.dazuisdown.getPath()
						.toString();
				OutputStream output = new FileOutputStream(filename
						+ File.separator + Comment.getMd5Hash(furl) + ".amr");
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
				Bundle bundle = new Bundle();
				bundle.putString("fileurl", furl); // 往Bundle中存放数
				message.setData(bundle);
				myHandler.sendMessage(message);
				// }

			} catch (Exception e) {
				e.printStackTrace();
				Message message = new Message();
				message.what = 1;
				myHandler.sendMessage(message);
			}
		}
	}

	public void startPlaying(String mFlilepath, final int po) {
		try {
			if (null == player) {
				player = new MediaPlayer();
			}
			player.reset();
			MainMenuActivity.borz = true;
			File file = new File(mFlilepath);
			FileInputStream fis = new FileInputStream(file);
			player.setDataSource(fis.getFD());
			// 设置要播放的文件
			// player.setDataSource(mFlilepath);
			player.prepare();
			DazuiActivity.lenth = player.getDuration() / 1000;
			// 播放之
			player.start();
			player.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					player.release();
					player = null;
					lv.requestLayout();
					list.get(po).setStartorstop(0);
					list.get(po).setJindubg(0);
					ad.notifyDataSetChanged();
				}
			});
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

	@Override
	void resultData(String url, String result) {
		// TODO Auto-generated method stub
		String tip = null;
		if (result == null) {
			// fresh.setVisibility(View.VISIBLE);
			Commond.showToast(Dzmysave.this, "小贱提醒 ：当前网络不稳定！");
			return;
		}

		try {
			// //////////////////////////////////////////////正文内容页面
			JSONObject jsonChannel = new JSONObject(result);
			int ret = jsonChannel.optInt("ret");
			if (ret == 0) {
				tip = URLDecoder.decode(jsonChannel.optString("tip"));
				Commond.showToast(Dzmysave.this, tip);
				return;
			}
			// pb.setVisibility(View.GONE);
			ll.setVisibility(View.GONE);
			lastdate = URLDecoder.decode(jsonChannel.optString("pd"));
			nlink = URLDecoder.decode(jsonChannel.optString("nlink"));
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
					list.add(item);

					ll.setVisibility(View.GONE);
				}

				if (list.size()!= 0) {
					// pb_zuixin.setVisibility(View.GONE);
					if (lv.getAdapter() == null) {
						ad = new DazuiAdapter2(Dzmysave.this, list, lv,
								listener, null, false, listener_save,
								listener_share, listener_osc, listener_liwu);
						ll.setVisibility(View.GONE);
						lv.setAdapter(ad);
					} else if (twice == 3) {
						lv.requestLayout();
						// ad.notifyDataSetChanged(); // 数据集变化后,通知adapter
						loading.setVisibility(View.GONE);
					}
					// adapt = ad ;
				}
//				else if(url.contains("http://jrc.hutudan.com/music/list.php")){
//					if (lv.getAdapter() == null) {
//						ad = new DazuiAdapter2(Dzmysave.this, list, lv,
//								listener, null, false, listener_save,
//								listener_share, listener_osc, listener_liwu);
//						ll.setVisibility(View.GONE);
//						lv.setAdapter(ad);
//					} else if (twice == 3) {
//						lv.requestLayout();
//						// ad.notifyDataSetChanged(); // 数据集变化后,通知adapter
//						loading.setVisibility(View.GONE);
//					}
//				}
				
			}
				
			if(list.size() == 0){
					if(!TextUtil.isEmpty(ouid)){
						if (ouid.equalsIgnoreCase(su.getUid())) {
							nofabiao.setBackgroundResource(R.drawable.nohave);
							nofabiao.setVisibility(View.VISIBLE);
						} else {
							nofabiao.setBackgroundResource(R.drawable.nohave);
							nofabiao.setVisibility(View.VISIBLE);
						}
						}
				}
			
			if (url.contains("http://jrc.hutudan.com/music/support.php")) {
				if (ad != null) {
					lv.requestLayout();
					list.get(holder.position).setUc(
							list.get(holder.position).getUc() + 1);
					ad.notifyDataSetChanged();
				}

			}

			if (url.contains("http://jrc.hutudan.com/music/fav.php")) {
				if (url.contains("type=0")) {
					saveorquxiao(1);

					list.get(holder.position).setIsfav(1);

				} else if (url.contains("type=1")) {
					saveorquxiao(-1);

					list.get(holder.position).setIsfav(0);

				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			// pb.setVisibility(View.GONE);
			ll.setVisibility(View.GONE);
			Commond.showToast(Dzmysave.this, "操作失败！请检查网络！");
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub 
		dsispause = false;
		if (EditPage.isshare == true) {
			if (EditPage.isshare == true) {
				if(list != null && list.size()!=0){
				list.get(holder.position).setSc(
						list.get(holder.position).getSc() + 1);
				lv.requestLayout();
				ad.notifyDataSetChanged();
				}else{
					finish();
				}
			}
			EditPage.isshare = false;
		}
//		if (audio.isWiredHeadsetOn() == true) {
//			AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//			audioManager.setMode(AudioManager.MODE_IN_CALL);// 把模式调成听筒放音模式
//			audioManager.setSpeakerphoneOn(false);
//		} else if (audio.isWiredHeadsetOn() == false) {
//			AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//			audioManager.setMode(AudioManager.ROUTE_SPEAKER);// 把模式调成听筒放音模式
//			audioManager.setSpeakerphoneOn(true);
//		}
		if (player != null) {
			if (MainMenuActivity.borz == true) {
				player.start();
			}
		}
		if (DazuiActivity.player != null) {
				DazuiActivity.player.release();
				DazuiActivity.player = null ;
		}
		if (Mydazui.player != null) {
			Mydazui.player.release();
			Mydazui.player = null ;
		}
		super.onResume();
	}

	private void saveorquxiao(int num) {
		if (ad != null) {
			lv.requestLayout();
			list.get(holder.position).setFc(
					list.get(holder.position).getFc() + num);
			ad.notifyDataSetChanged();
		}
	}

	// 使用快捷分享完成图文分享
	// 使用快捷分享完成图文分享
	private void showShare(boolean silent, final List<DazuiIteminfo> it,final int position,String platform) {
		
		String url = "http://jrc.hutudan.com/music/shareitem.php?" + "id="
				+ it.get(position).getId() + "&uid=" + MainMenuActivity.uid;
		dsispause = true;
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
	@Override
	/** 处理操作结果 */
	public boolean handleMessage(Message msg) {
//		AbstractWeibo weibo = (AbstractWeibo) msg.obj;
//		String text = AbstractWeibo.actionToString(msg.arg2);
//		switch (msg.arg1) {
//		case 1: { // 成功
//			text = weibo.getName() + " completed at " + text;
//		}
//			break;
//		case 2: { // 失败
//			text = weibo.getName() + " caught error at " + text;
//		}
//			break;
//		case 3: { // 取消
//			text = weibo.getName() + " canceled at " + text;
//		}
//			break;
//		}
//
//		Toast.makeText(Dzmysave.this, text, Toast.LENGTH_SHORT).show();
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
				twice = 3;
				whichScroll(nlink, lv);

			}
		}
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
	protected void onPause() {
		// TODO Auto-generated method stub
		if (dsispause == false) {
//			if (audio.isWiredHeadsetOn() == true) {
//				AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//				audioManager.setMode(AudioManager.MODE_IN_CALL);// 把模式调成听筒放音模式
//				audioManager.setSpeakerphoneOn(false);
//			} else if (audio.isWiredHeadsetOn() == false) {
//				AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//				audioManager.setMode(AudioManager.MODE_NORMAL);// 把模式调成听筒放音模式
//			}
		}
		if (player != null) {
			if (dsispause == false) {
				if (player.isPlaying()) {
					player.pause();
				}
			}
		}
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (player != null) {
			player.release();
			player = null;
		}		
		
		dsispause = false;
		super.onDestroy();
	}

}
