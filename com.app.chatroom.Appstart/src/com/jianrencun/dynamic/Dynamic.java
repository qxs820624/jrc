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

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.SearchPeople;
import com.app.chatroom.adapter.Searchpeopleadapter;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;
import com.jianrencun.android.Details;
import com.jianrencun.android.HttpBaseActivity;
import com.jianrencun.android.HttpBaseActivity.HttpRequestTask;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Comment;
import com.jianrencun.dazui.DazuiActivity;
import com.jianrencun.dazui.DazuiAdapter2;
import com.jianrencun.dazui.DazuiIteminfo;
import com.jianrencun.dazui.Dzmysave;
import com.umeng.common.Log;

public class Dynamic extends HttpBaseActivity implements OnScrollListener {
	private ListView lv;
	private DyAdapter da;
	/* 配置文件 */
	SharedPreferences sp;
	SystemSettingUtilSp su;
	private Details dt = new Details();
	private int page, pd;
	private Dyinfoitem dyitem;
	private Dyphotoitem dyphotoitem;
	private List<Dyinfoitem> dylists;
	// private List<Dyphotoitem> dyphotolists;
	private Button back;
	private View vFooter;
	private LinearLayout loading, dy_pb_ll;
	private List<Integer> dlpl;
	OnClickListener listener;
	private DyAdapter.ViewHolder holder;

	private List<String> lsfileurl;
	private List<String> bofangurl;
	private List<Integer> po;
	public static boolean saveisnoty;
	private Handler handler;
	private int twice;
	private List<String> dllinks;
	private String currentdate = "", lastdate;
	private String nlink;
	private String ouid, url;
	private String fileurl;
	private int position, bfposition;
	public static MediaPlayer dyplayer;
	public static int dylenth;
	private View view1 ;
	private TextView header_tv ;
	private int po2 ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dynamic);
		lv = (ListView) findViewById(R.id.dy_lv);
		back = (Button) findViewById(R.id.dy_back);
		dylists = new ArrayList<Dyinfoitem>();
		dlpl = new ArrayList<Integer>();
		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);

		lsfileurl = new ArrayList<String>();
		bofangurl = new ArrayList<String>();
		po = new ArrayList<Integer>();
		dllinks = new ArrayList<String>();

		vFooter = LayoutInflater.from(this).inflate(R.layout.list_footer, null);
		loading = (LinearLayout) vFooter.findViewById(R.id.list_footer);
		loading.setVisibility(View.GONE);
		dy_pb_ll = (LinearLayout) findViewById(R.id.dy_pb_ll);

		// 将要分页显示的View装入数组中
				LayoutInflater mLi = LayoutInflater.from(this);
				view1 = mLi.inflate(R.layout.header_dongtai, null);
				// wv = (WebView) view1.findViewById(R.id.webbody);
				header_tv = (TextView) view1.findViewById(R.id.header_dongtai_tv);
				
				view1.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent it = new Intent();
						it.setClass(Dynamic.this, Aboutme.class);
						startActivity(it);
						lv.removeHeaderView(view1);						
						po2 = 0 ;
					}
				});
				
		if (DyAdapter.th != null) {
			DyAdapter.th = null;
		}

		lv.setOnScrollListener(this);
		lv.addFooterView(vFooter);
		lv.setFooterDividersEnabled(false);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		// 每项按钮事件
		listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				holder = (DyAdapter.ViewHolder) v.getTag();
				bflisener(dylists, da);
			}
		};

		// ////////
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(lv.getHeaderViewsCount() != 0){
					po2 = 1 ;
				}else{
					po2 = 0 ;
				}
				if (position < dylists.size()+po2) {
				Intent it = new Intent();
				it.setClass(Dynamic.this, Dydetatil.class);
				it.putExtra("id", dylists.get(arg2-po2).getId());
				it.putExtra("flg", dylists.get(arg2-po2).getFlg());
				it.putExtra("afile", dylists.get(arg2-po2).getAfile());
				startActivity(it);
				}
			}
		});

		String url = dt.appendNameValue(ConstantsJrc.DONGTAI, "uid",
				su.getUid());
		url = dt.appendNameValue(url, "token", URLEncoder.encode(su.getToken()));
		url = dt.appendNameValueint(url, "pd", pd);
		StringBuffer data = new StringBuffer();
		HttpRequestTask request = new HttpRequestTask();
		request.execute(url, data.toString());
	}

	@Override
	public void resultData(String url, String result) {
		// TODO Auto-generated method stub
		String tip = null;
		if (result == null) {
			// fresh.setVisibility(View.VISIBLE);
			Commond.showToast(Dynamic.this, "小贱提醒 ：当前网络不稳定！");
			return;
		}

		try {
			// //////////////////////////////////////////////正文内容页面
			JSONObject jsonChannel = new JSONObject(result);
			int ret = jsonChannel.optInt("ret");
			if (ret == 0) {
				tip = URLDecoder.decode(jsonChannel.optString("tip"));
				Commond.showToast(Dynamic.this, tip);
				return;
			}
			String tip1 = URLDecoder.decode(jsonChannel.optString("tip1"));

			page = jsonChannel.optInt("page");
			pd = jsonChannel.optInt("pd");
			JSONArray jsonItems = jsonChannel.optJSONArray("items");
			if (jsonItems != null) {
				// tip = "获取成功！";
				for (int i = 0; i < jsonItems.length(); i++) {
					JSONObject jsonItem = jsonItems.getJSONObject(i);
					List<Dyphotoitem> dyphotolists = null;
					ArrayList<String> dyphotolurl = null;
					int id = jsonItem.optInt("id");
					int flg = jsonItem.optInt("flg");
					int uid = jsonItem.optInt("uid");
					String nick = URLDecoder.decode(jsonItem.optString("nick"));
					String nick_c = URLDecoder.decode(jsonItem
							.optString("nick_c"));
					String header = URLDecoder.decode(jsonItem
							.optString("header"));
					int headerbj = jsonItem.optInt("uhflg");
					int sex = jsonItem.optInt("sex");
					String title = URLDecoder.decode(jsonItem
							.optString("title"));
					String desc = URLDecoder.decode(jsonItem.optString("desc"));
					String desc_c = URLDecoder.decode(jsonItem
							.optString("desc_c"));
					JSONObject jsonChannel2 = new JSONObject(
							jsonItem.toString());
					JSONArray jsonpics = jsonChannel2.optJSONArray("pics");
					if (jsonpics != null) {
						dyphotolists = new ArrayList<Dyphotoitem>();
						dyphotolurl = new ArrayList<String>();
						for (int j = 0; j < jsonpics.length(); j++) {
							JSONObject jsonpic = jsonpics.getJSONObject(j);
							String photourl = URLDecoder.decode(jsonpic
									.optString("pic"));
							String burl = URLDecoder.decode(jsonpic
									.optString("bpic"));
							int w = jsonpic.optInt("w");
							int h = jsonpic.optInt("h");
							dyphotoitem = new Dyphotoitem(photourl, w, h, burl);
							if (!TextUtils.isEmpty(burl)) {
								dyphotolurl.add(burl);
							} else {
								dyphotolurl.add(photourl);
							}
							dyphotolists.add(dyphotoitem);
						}
					}
					String afile = URLDecoder.decode(jsonItem
							.optString("afile"));

					int alen = jsonItem.optInt("alen");
					String uptime = URLDecoder.decode(jsonItem
							.optString("desc1"));
					String numdis = URLDecoder.decode(jsonItem
							.optString("desc2"));
					if (dyphotolists != null) {
						dyitem = new Dyinfoitem(id, flg, uid, headerbj, sex,
								nick, nick_c, header, title, desc, desc_c,
								afile, alen, uptime, numdis, dyphotolists, 0,
								dyphotolurl);
					} else {
						dyitem = new Dyinfoitem(id, flg, uid, headerbj, sex,
								nick, nick_c, header, title, desc, desc_c,
								afile, alen, uptime, numdis, 0);
					}
					dylists.add(dyitem);
				}

				if(!TextUtils.isEmpty(tip1)){
					if(lv.getHeaderViewsCount() == 0){
					header_tv.setText(tip1);
					lv.addHeaderView(view1);
					}
				}
				
				if (dylists.size() != 0 && !url.contains("page")) {
					da = new DyAdapter(Dynamic.this, dylists, lv, listener);
					lv.setAdapter(da);
					dy_pb_ll.setVisibility(View.GONE);
				} else {
					lv.requestLayout();
					da.notifyDataSetChanged();
					loading.setVisibility(View.GONE);
				}

				// if (list.size()!= 0) {
				// // pb_zuixin.setVisibility(View.GONE);
				// if (lv.getAdapter() == null) {
				// ad = new DazuiAdapter2(Dzmysave.this, list, lv,
				// listener, null, false, listener_save,
				// listener_share, listener_osc, listener_liwu);
				// ll.setVisibility(View.GONE);
				// lv.setAdapter(ad);
				// } else if (twice == 3) {
				// lv.requestLayout();
				// // ad.notifyDataSetChanged(); // 数据集变化后,通知adapter
				// loading.setVisibility(View.GONE);
				// }
				// // adapt = ad ;
				// }
			}
		} catch (Exception e) {
			// TODO: handle exception
			// pb.setVisibility(View.GONE);
			Commond.showToast(Dynamic.this, "操作失败！请检查网络！");
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
				if (page == 0) {
					lv.removeFooterView(vFooter);
					return;
				} else {
					if (!dlpl.contains(page)) {
						loading.setVisibility(View.VISIBLE);
						String url = ConstantsJrc.DONGTAI;
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
						url = dt.appendNameValue(url, "uid", su.getUid());
						url = dt.appendNameValue(url, "token",
								URLEncoder.encode(su.getToken()));
						url = dt.appendNameValueint(url, "pd", pd);
						HttpRequestTask request = new HttpRequestTask();
						request.execute(url, data.toString());
						dlpl.add(page);
					}
				}
			}
		}
	}

	// 播放按钮监听方法
	// ////////////
	private void bflisener(List<Dyinfoitem> list, DyAdapter da) {

		fileurl = list.get(holder.position).getAfile();
		position = holder.position;
		bofangurl.add(fileurl);
		po.add(position);
		if (bfposition == position) {

			if (dyplayer != null) {
				if (list.get(position).getStartorstop() == 1) {
					dyplayer.pause();
					holder.dy_seekbar.setEnabled(false);
					MainMenuActivity.borz = false;
					File picfile = new File(MainMenuActivity.dazuisdown
							+ File.separator + Comment.getMd5Hash(fileurl)
							+ ".amr");
					if (picfile.exists()) {
						holder.dy_bofang.setBackgroundResource(R.drawable.havebf1);
					} else {
						holder.dy_bofang.setBackgroundResource(R.drawable.dy_bofang1);
					}

					list.get(position).setStartorstop(0);
				} else if (list.get(position).getStartorstop() == 0) {
					dyplayer.start();
					holder.dy_seekbar.setEnabled(true);
					MainMenuActivity.borz = true;
					holder.dy_bofang
							.setBackgroundResource(R.drawable.dy_zanting1);
					list.get(position).setStartorstop(1);
					// list.get(position).setJindubg(1);
				}
			} else {
				File picfile = new File(MainMenuActivity.dazuisdown
						+ File.separator + Comment.getMd5Hash(fileurl) + ".amr");
				String filename = picfile.getPath().toString();
				if (picfile.exists()) {
					startPlaying(filename, position);
					list.get(position).setStartorstop(1);
					// list.get(position).setJindubg(1);
					lv.requestLayout();
					da.notifyDataSetChanged();
				} else {
					if (!lsfileurl.contains(fileurl)) {
						 holder.dy_down_pb.setVisibility(View.VISIBLE);
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
				// list.get(position).setJindubg(1);

				list.get(bfposition).setStartorstop(0);
				// list.get(bfposition).setJindubg(0);

				int xx = position;
				int yy = bfposition;
				Dyinfoitem it = dylists.get(position);
				da.notifyDataSetChanged();
				bfposition = position;
			} else {
				if (!lsfileurl.contains(fileurl)) {
					 holder.dy_down_pb.setVisibility(View.VISIBLE);
					new Thread(new myThread(fileurl)).start();
					list.get(position).setStartorstop(2);
				}
			}
			// bls.set(position, true);
		}
	}

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
			if (null == dyplayer) {
				dyplayer = new MediaPlayer();
			}
			dyplayer.reset();
			holder.dy_seekbar.setEnabled(true);
			MainMenuActivity.borz = true;
			File file = new File(mFlilepath);
			FileInputStream fis = new FileInputStream(file);
			dyplayer.setDataSource(fis.getFD());
			// 设置要播放的文件
			// player.setDataSource(mFlilepath);
			dyplayer.prepare();
			dylenth = dyplayer.getDuration() / 1000;
			// 播放之
			dyplayer.start();
			holder.dy_seekbar.setEnabled(true);

			dyplayer.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					dyplayer.release();
					dyplayer = null;
					lv.requestLayout();
					dylists.get(po).setStartorstop(0);
					da.notifyDataSetChanged();
					if (DyAdapter.th != null) {
						DyAdapter.th = null;
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
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
						Commond.showToast(Dynamic.this, "下载完成！");
						dylists.get(po.get(i)).setStartorstop(0);
						lv.requestLayout();
						da.notifyDataSetChanged();
					}
				}
				if (str2.equalsIgnoreCase(bofangurl.get(bofangurl.size() - 1))) {
					String filename = MainMenuActivity.dazuisdown
							.getAbsolutePath()
							+ File.separator
							+ Comment
									.getMd5Hash(bofangurl.get(bofangurl.size() - 1))
							+ ".amr";
					dylists.get(po.get(po.size() - 1)).setStartorstop(1);
					if (po.get(po.size() - 1) != bfposition) {
						dylists.get(bfposition).setStartorstop(0);
						bfposition = position;
					}

					da.notifyDataSetChanged();
					startPlaying(filename, po.get(po.size() - 1));
				}
				break;
			case 1:
				Commond.showToast(Dynamic.this, "网络不给力啊！！");

				if (dylists.size() == 0 || dylists == null) {
					finish();
					return;
				}
				dylists.get(position).setStartorstop(0);
				if (da != null) {
					lv.requestLayout();
					da.notifyDataSetChanged();
				}
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (dyplayer != null) {
			dyplayer.release();
			dyplayer = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (dyplayer != null && !dyplayer.isPlaying()) {
			dylists.get(bfposition).setStartorstop(0);
			da.notifyDataSetChanged();
		}
		super.onResume();
	}
}
