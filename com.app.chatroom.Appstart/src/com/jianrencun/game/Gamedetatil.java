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
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.chatroom.Appstart;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;
import com.app.chatroom.util.PhoneInfo;
import com.app.chatroom.util.SystemUtil;
import com.duom.fjz.iteminfo.BitmapCache;
import com.duom.fjz.iteminfo.BitmapCacheDzlb;
import com.duom.fjz.iteminfo.WebviewDonghua;
import com.jianrencun.android.Details;
import com.jianrencun.android.HttpBaseActivity;
import com.jianrencun.android.HttpBaseActivity.HttpRequestTask;
import com.jianrencun.chatroom.R;
import com.jianrencun.game.GameAdapter.LoadImageRunnable;
import com.jianrencun.game.Paihangbang.jieyaThread;
import com.jianrencun.game.Paihangbang.myThread;

public class Gamedetatil extends HttpBaseActivity {
	private Button back, gamebegin;
	private ImageView header, iv1, iv2, iv3;
	private TextView gname, desc1, desc3;
	/* 配置文件 */
	SharedPreferences sp;
	SystemSettingUtilSp su;
	private Details dt = new Details();
	private int guid;
	private String pic;
	private List<String> lists = new ArrayList<String>();
	private List<ImageView> list_iv = new ArrayList<ImageView>();
	private String dlink, flg;

	private String gamehtml, lsgh;
	private int downflg;
	private String newpath, link;
	private File donghuadownload, zipFile, htmlfile;
	private String htmlname, foldername;
	static List<String> urls = new ArrayList<String>();
    private LinearLayout ll ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.gamedetatil);

		Intent it = getIntent();
		guid = it.getIntExtra("guid", 0);
		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);

		back = (Button) findViewById(R.id.game_detatil_back);
		gamebegin = (Button) findViewById(R.id.game_detatil_begin);
		header = (ImageView) findViewById(R.id.gr_detatil_header);
		iv1 = (ImageView) findViewById(R.id.gr_detatil_iv1);
		iv2 = (ImageView) findViewById(R.id.gr_detatil_iv2);
		iv3 = (ImageView) findViewById(R.id.gr_detatil_iv3);
		ll = (LinearLayout)findViewById(R.id.game_detatil_proll);
		ll.setVisibility(View.GONE);
		list_iv.add(iv1);
		list_iv.add(iv2);
		list_iv.add(iv3);

		gname = (TextView) findViewById(R.id.gr_detatil_gname);
		desc1 = (TextView) findViewById(R.id.gr_detatil_desc1);
		desc3 = (TextView) findViewById(R.id.game_detatil_desc3);
		
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

		gamebegin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (guid == 2) {
					Intent it = new Intent();
					it.setClass(Gamedetatil.this, Zhuanpan.class);
					startActivity(it);
				} else {
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

		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		String url = dt.appendNameValue(ConstantsJrc.GAMEDETATIL, "uid",
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
			Commond.showToast(Gamedetatil.this, "小贱提醒 ：当前网络不稳定！");
			return;
		}

		try {
			// //////////////////////////////////////////////正文内容页面		
			JSONObject jsonChannel = new JSONObject(result);
			int ret = jsonChannel.optInt("ret");
			tip = URLDecoder.decode(jsonChannel.optString("tip"));
			// tjd = URLDecoder.decode(jsonChannel.optString("tjd"));
			if (ret == 0) {
				Commond.showToast(Gamedetatil.this, tip);
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
				
				desc1.setText(msg);
				gname.setText(name);
				desc3.setText(gdesc);

				JSONArray jsonItems = jsonChannel.optJSONArray("pics");
				if (jsonItems != null) {
					// tip = "获取成功！";
					for (int i = 0; i < jsonItems.length(); i++) {
						JSONObject jsonItem = jsonItems.getJSONObject(i);
						pic = URLDecoder.decode(jsonItem.optString("pic"));
						lists.add(pic);
					}
				}
				GameAdapter.jiance(Gamedetatil.this, icon, header, mHandler);
				for (int i = 0; i < lists.size(); i++) {
					jiance(Gamedetatil.this, lists.get(i), list_iv.get(i),
							Ivhandler);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Commond.showToast(Gamedetatil.this, "解析失败！");
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

	public static void jiance(Context context, String header, ImageView iv,
			Handler mhandler) {
		File picfile = new File(Appstart.jrcfile + "/"
				+ Commond.getMd5Hash(header));
		String filename = picfile.getPath().toString();
		ArrayList<String> urls = new ArrayList<String>();
		Bitmap bmp = null;
		if (picfile.exists()) {
			bmp = BitmapCache.getIntance().getCacheBitmap(filename, 0, 0);
		}
		if (bmp == null) {
			if (!urls.contains(header)) {
				urls.add(header);
				new Thread(new LoadImageRunnable(context, mhandler, 0, header,
						filename)).start();
			}
			iv.setImageResource(R.drawable.gamedef_bg);
		} else {
			BitmapDrawable bd = new BitmapDrawable(bmp);
			iv.setImageDrawable(bd);
		}
	}

	Handler Ivhandler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			if (data != null) {
				String url = data.getString("url");
				String filename = data.getString("filename");

				Bitmap bmp = BitmapCacheDzlb.getIntance().getCacheBitmap(
						filename, 0, 0);

				BitmapDrawable bd = new BitmapDrawable(bmp);
				list_iv.get(lists.indexOf(url)).setImageDrawable(bd);
			}
		}
	};

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
