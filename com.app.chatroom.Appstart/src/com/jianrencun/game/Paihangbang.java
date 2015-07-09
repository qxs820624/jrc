package com.jianrencun.game;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.otherui.SystemMsgWebDialog;
import com.app.chatroom.otherui.VillageUserInfoDialog;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.ui.RoomListActivity;
import com.app.chatroom.util.Commond;
import com.app.chatroom.util.PhoneInfo;
import com.app.chatroom.util.SystemUtil;
import com.duom.fjz.iteminfo.BitmapCacheDzlb;
import com.duom.fjz.iteminfo.WebviewDonghua;
import com.jianrencun.android.Details;
import com.jianrencun.android.HttpBaseActivity;
import com.jianrencun.chatroom.R;

public class Paihangbang extends HttpBaseActivity {
	private Button back, begin;
	private ListView lv;
	private ImageView header;
	private TextView gname, desc1;
	/* 配置文件 */
	SharedPreferences sp;
	SystemSettingUtilSp su;
	private Details dt = new Details();

	GamebigInfo biginfo;
	List<GamebigInfo> biginfos = new ArrayList<GamebigInfo>();
	GamePaihangbangAdapter ga;
	private LinearLayout ll;
	View view1;
	String dlink, flg;
	private int guid;
	private String gamehtml , lsgh;
	private int downflg;
	private String newpath, link;
	private File donghuadownload, zipFile , htmlfile;
	private String htmlname, foldername;
	static List<String> urls = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.game_paihangbang);

		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);

		Intent it = getIntent();
		guid = it.getIntExtra("guid", 0);

		back = (Button) findViewById(R.id.gr_paihangbang_back);
		begin = (Button) findViewById(R.id.game_paihangbang_begin);
		lv = (ListView) findViewById(R.id.gr_paihangbang_lv);
		ll = (LinearLayout) findViewById(R.id.game_paihangbang_ll);

		if (SystemUtil.getSDCardMount()) {
			donghuadownload = new File(
					Environment.getExternalStorageDirectory() + File.separator
							+ getPackageName() + "/donghuadownload");
			zipFile = new File(Environment.getExternalStorageDirectory()
					+ File.separator + getPackageName() + "/donghuazip");
			if (!donghuadownload.exists()) {
				donghuadownload.mkdirs();
			}
			if (!zipFile.exists()) {
				zipFile.mkdirs();
			}

		} else {
			donghuadownload = new File(ConstantsJrc.PROJECT_PATH
					+ getPackageName() + "/donghuadownload");
			zipFile = new File(ConstantsJrc.PROJECT_PATH + getPackageName()
					+ "/donghuazip");
			if (!donghuadownload.exists()) {
				donghuadownload.mkdirs();
			}
			if (!zipFile.exists()) {
				zipFile.mkdirs();
			}
		}

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (arg2 == 0) {
					Intent it = new Intent();
					it.setClass(Paihangbang.this, Gamedetatil.class);
					it.putExtra("guid", guid);
					startActivity(it);
				} else {
					if (biginfos.get(arg2 - 1).getJiazubj() == 0) {
						Intent intent = new Intent(Paihangbang.this,
								VillageUserInfoDialog.class);
						intent.putExtra("uid",
								String.valueOf(biginfos.get(arg2 - 1).getUid()));
						intent.putExtra("nick", biginfos.get(arg2 - 1)
								.getNick());
						intent.putExtra("fuid", MainMenuActivity.uid);
						intent.putExtra("type", 2);
						startActivity(intent);
					} else if (biginfos.get(arg2 - 1).getJiazubj() == 1) {
						Intent intent = new Intent(getApplicationContext(),
								SystemMsgWebDialog.class);
						intent.putExtra("link", ConstantsJrc.MAINMORE
								+ "?uid="
								+ su.getUid()
								+ "&flg=7&w="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getWidth(Paihangbang.this)
								+ "&pkg="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getPackage(Paihangbang.this)
								+ "&ver="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getVersionName(Paihangbang.this)
								+ "&rid=" + biginfos.get(arg2 - 1).getUid());

						// intent.putExtra("roomtype", roombean.getType());
						intent.putExtra("type", "7");
						startActivity(intent);
					}
				}
			}
		});

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		begin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (guid == 2) {
					Intent it = new Intent();
					it.setClass(Paihangbang.this, Zhuanpan.class);
					startActivity(it);
				} else {
					// //////////////////////////////////
					// //////gamehtml 下载/////////////////
					// ///////////////////////////////////
					if (flg.contains("dj.")) {						
						donghuapre(lsgh);
						StringBuffer data = new StringBuffer();
						String gameurl;
						gameurl = Details.geturl(ConstantsJrc.WEB_DONGHUA);
						gamehtml = URLEncoder.encode(gamehtml);
						gameurl = dt.appendNameValue(gameurl, "path", gamehtml);
						gameurl = Details.appendNameValueint(gameurl, "flg",
								downflg);
						gameurl = dt.appendNameValue(gameurl, "token",
								URLEncoder.encode(su.getToken()));
						// 请求网络验证登陆
						HttpRequestTask request = new HttpRequestTask();
						request.execute(gameurl, data.toString());

					} else {
						final PackageManager pm = getPackageManager();
						Intent launch = pm.getLaunchIntentForPackage(flg);
						if (launch != null) {
							Bundle bundle = new Bundle();
							bundle.putInt("uid", Integer.parseInt(su.getUid()));
							bundle.putString("token", su.getToken());
							launch.putExtras(bundle);
							startActivity(launch);
						} else {
							Uri uri = Uri.parse(dlink);// id为包名
							Intent it = new Intent(Intent.ACTION_VIEW, uri);
							startActivity(it);
						}
					}
				}
			}
		});

		LayoutInflater mLi = LayoutInflater.from(this);
		view1 = mLi.inflate(R.layout.game_header, null);
		header = (ImageView) view1.findViewById(R.id.gr_paihang_header_header);
		gname = (TextView) view1.findViewById(R.id.gr_paihang_header_gname);
		desc1 = (TextView) view1.findViewById(R.id.gr_paihang_header_desc1);
		// desc2 = (TextView)view1.findViewById(R.id.gr_paihang_header_desc2);

		header.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		String url = dt.appendNameValue(ConstantsJrc.GAMEPAIHANG, "uid",
				su.getUid());
		url = dt.appendNameValue(url, "token", URLEncoder.encode(su.getToken()));
		url = Details.appendNameValueint(url, "gid", guid);
		url = dt.appendNameValue(url, "pkg", URLEncoder.encode(PhoneInfo
				.getInstance(getApplicationContext()).getPackage(this)));
		StringBuffer data = new StringBuffer();
		HttpRequestTask request = new HttpRequestTask();
		request.execute(url, data.toString());

	}

	@Override
	public void resultData(String url, String result) {
		// TODO Auto-generated method stub
		String tip = null;
		ll.setVisibility(View.GONE);
		if (result == null) {
			// fresh.setVisibility(View.VISIBLE);
			Commond.showToast(Paihangbang.this, "小贱提醒 ：当前网络不稳定！");
			return;
		}

		try {
			ll.setVisibility(View.GONE);
			// //////////////////////////////////////////////正文内容页面
			JSONObject jsonChannel = new JSONObject(result);
			int ret = jsonChannel.optInt("ret");
			tip = URLDecoder.decode(jsonChannel.optString("tip"));
			// ////////////////////
			// ////网页游戏，下载动画
			// //////////////////////////
			// tjd = URLDecoder.decode(jsonChannel.optString("tjd"));
			if (ret == 0) {
				Commond.showToast(Paihangbang.this, tip);
				return;
			}
			if (url.contains(ConstantsJrc.WEB_DONGHUA)) {				               
				tip = URLDecoder.decode(jsonChannel.optString("tip"));
				link = URLDecoder.decode(jsonChannel.optString("link"));
				newpath = URLDecoder.decode(jsonChannel.optString("path"));

				File picfile = new File(donghuadownload + "/" + File.separator
						+ Commond.getMd5Hash(newpath) + ".zip");
				String filename = picfile.getPath().toString();
				
				if (TextUtils.isEmpty(link) && TextUtils.isEmpty(newpath)) {
					// wv.loadUrl("file:///" + htmlfile);
				} else {
					if (!TextUtils.isEmpty(link) || !TextUtils.isEmpty(newpath)) {
						if (!urls.contains(url)) {
							ll.setVisibility(View.VISIBLE);
							urls.add(url);
							new Thread(new myThread(link, newpath, url,
									foldername, htmlname, donghuadownload,
									myHandler)).start();
						}
					}
				}
			} else {
				String icon = URLDecoder.decode(jsonChannel.optString("icon"));
				String name = URLDecoder.decode(jsonChannel.optString("name"));
				String msg = URLDecoder.decode(jsonChannel.optString("msg"));
				String gdesc = URLDecoder
						.decode(jsonChannel.optString("gdesc"));
				dlink = URLDecoder.decode(jsonChannel.optString("dlink"));
				flg = URLDecoder.decode(jsonChannel.optString("flg"));
				if(!TextUtils.isEmpty(flg) && flg.contains("dj.")){
				String[] strs = flg.split("dj.");
				gamehtml = strs[1]; 
				lsgh = strs[1];
				}

				JSONArray jsonItems = jsonChannel.optJSONArray("tops");
				if (jsonItems != null) {
					// tip = "获取成功！";
					for (int i = 0; i < jsonItems.length(); i++) {
						JSONObject jsonItem = jsonItems.getJSONObject(i);
						int phflg = jsonItem.optInt("flg");
						String title = URLDecoder.decode(jsonItem
								.optString("title"));
						biginfo = new GamebigInfo(phflg, title, 0, "", "", "",
								"", "", "", 2, 2);
						biginfos.add(biginfo);
						JSONObject jsonChannel2 = new JSONObject(
								jsonItem.toString());
						JSONArray jsonpics = jsonChannel2.optJSONArray("items");
						if (jsonpics != null) {
							for (int j = 0; j < jsonpics.length(); j++) {

								JSONObject jsonpic = jsonpics.getJSONObject(j);
								int uid = jsonpic.optInt("uid");
								String header = URLDecoder.decode(jsonpic
										.optString("header"));
								String nick = URLDecoder.decode(jsonpic
										.optString("nick"));
								String nick_c = URLDecoder.decode(jsonpic
										.optString("nick_c"));
								String score = URLDecoder.decode(jsonpic
										.optString("score"));
								String ord = URLDecoder.decode(jsonpic
										.optString("ord"));
								int flg2 = jsonpic.optInt("flg");
								biginfo = new GamebigInfo(phflg, title, uid,
										nick, nick_c, score, ord, header, "",
										2, flg2);
								biginfos.add(biginfo);
							}
						}
					}
				}
				// GameAdapter.jiance(Gamedetatil.this, icon, header, mHandler);
				// for (int i = 0; i < lists.size(); i++) {
				// GameAdapter.jiance(Gamedetatil.this, lists.get(i),
				// list_iv.get(i), Ivhandler);
				// }
				ga = new GamePaihangbangAdapter(Paihangbang.this, biginfos, lv,
						2, Paihangbang.this);
				lv.addHeaderView(view1);
				lv.setAdapter(ga);
				// //////////////////headerview
				gname.setText(name);
				desc1.setText(msg);
				GameAdapter.jiance(Paihangbang.this, icon, header, mHandler);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Commond.showToast(Paihangbang.this, "解析失败！");
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

				BitmapDrawable bd = new BitmapDrawable(bmp);
				header.setImageDrawable(bd);
			}
		}
	};

	private boolean isAvilible(Context context, String packageName) {
		final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
		List<String> pName = new ArrayList<String>();// 用于存储所有已安装程序的包名
		// 从pinfo中将包名字逐一取出，压入pName list中
		if (pinfo != null) {
			for (int i = 0; i < pinfo.size(); i++) {
				String pn = pinfo.get(i).packageName;
				pName.add(pn);
			}
		}
		return pName.contains(packageName);// 判断pName中是否有目标程序的包名，有TRUE，没有FALSE
	}

	// //下载动画游戏
	static class myThread implements Runnable {
		public String furl, path, qingqiuurl, foldername, htmlname;
		public File donghuadownload;
		public Handler hd;

		public myThread(String url, String path, String url2,
				String foldername, String htmlname, File donghuadownload,
				Handler hd) {
			this.furl = url;
			this.path = path;
			this.qingqiuurl = url2;
			this.foldername = foldername;
			this.htmlname = htmlname;
			this.donghuadownload = donghuadownload;
			this.hd = hd;
		}

		public void run() {
			int count;
			try {
				String[] strs = path.split("[/]");
				foldername = strs[0];
				htmlname = strs[1];
				// for(int i = 0 ; i<bofangurl.size()-1 ;i++){
				URL url = new URL(furl);
				URLConnection conexion = url.openConnection();
				conexion.connect();

				int lenghtOfFile = conexion.getContentLength();
				InputStream input = new BufferedInputStream(url.openStream());

				File picfile = new File(donghuadownload + "/" + File.separator
						+ Commond.getMd5Hash(path) + ".zip");
				String filename = picfile.getPath().toString();

				OutputStream output = new FileOutputStream(picfile
						.getAbsolutePath().toString());
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
				bundle.putString("fileurl", qingqiuurl); // 往Bundle中存放数
				bundle.putString("path", path); // 往Bundle中存放数
				message.setData(bundle);
				hd.sendMessage(message);
				// }
			} catch (Exception e) {
				// Commond.showToast(DazuiActivity.this, "网络很不给力啊！");
				Message message = new Message();
				message.what = 1;
				hd.sendMessage(message);
				e.printStackTrace();
			}
		}
	}

	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			String str2 = msg.getData().getString("fileurl");// 接受msg传递过来的参数
			String path = msg.getData().getString("path");// 接受msg传递过来的参数

			File picfile = new File(donghuadownload + "/" + File.separator
					+ Commond.getMd5Hash(newpath) + ".zip");
			String filename = picfile.getPath().toString();
			if (picfile.exists()) {
				try {
					File picfile1 = new File(zipFile + "/" + File.separator
							+ File.separator + foldername + File.separator
							+ htmlname);
					String filename1 = picfile1.getPath().toString();
					if (!picfile1.exists()) {
						new Thread(new jieyaThread(picfile, str2, zipFile,
								jieyaHandler)).start();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			super.handleMessage(msg);
		}
	};

	static class jieyaThread implements Runnable {
		File file;
		String str2;
		File zipFile;
		public Handler hd;

		public jieyaThread(File picfile, String str2, File zipFile, Handler hd) {
			this.file = picfile;
			this.str2 = str2;
			this.zipFile = zipFile;
			this.hd = hd;
		}

		public void run() {

			try {
				WebviewDonghua.upZipFile(file, zipFile.getAbsolutePath()
						.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message message = new Message();
			message.what = 1;
			Bundle bundle = message.getData();
			bundle.putString("str2", str2); // 往Bundle中存放数
			message.setData(bundle);
			if (file.exists()) {
				file.delete();
			}
			hd.sendMessage(message);
		}
	}

	Handler jieyaHandler = new Handler() {
		public void handleMessage(Message msg) {
			ll.setVisibility(View.GONE);
			File picfile1 = new File(zipFile + "/" + File.separator
					+ File.separator + foldername + File.separator + htmlname);
			String filename1 = picfile1.getPath().toString();
			Intent intent = new Intent(getApplicationContext(),
					WebviewDonghua.class);
			intent.putExtra("htmlfile", filename1);
			startActivity(intent);
			urls.clear();
			super.handleMessage(msg);
		}
	};

	public void donghuapre(String path) {
		// 跳转过来。。接受path ，如果不为空 拆分 为 html名字 和 要解压到文件夹的名字

		// 判断 html文件存不存在
		String[] strs = path.split("[/]");
		foldername = strs[0];
		htmlname = strs[1];
		htmlfile = new File(zipFile + File.separator + foldername
				+ File.separator + htmlname);
		downflg = 0;
		if (htmlfile.exists()) {
			Intent intent = new Intent(getApplicationContext(),
					WebviewDonghua.class);
			intent.putExtra("htmlfile", htmlfile.getPath());
			startActivity(intent);
			downflg = 1;
			return;
		}
	}
}
