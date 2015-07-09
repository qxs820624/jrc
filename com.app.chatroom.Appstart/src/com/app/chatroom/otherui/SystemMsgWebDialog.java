package com.app.chatroom.otherui;

import java.io.BufferedInputStream;
import java.io.File;
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
import java.util.zip.ZipException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
 

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.uppay.PayResultReceiver;
import com.app.chatroom.util.Commond;
import com.app.chatroom.util.SystemUtil;
import com.duom.fjz.iteminfo.WebviewDonghua;
import com.jianrencun.android.Details;
import com.jianrencun.android.HttpBaseActivity;
import com.jianrencun.chatroom.R;
import com.umeng.common.Log;
import com.umeng.common.net.s;

public class SystemMsgWebDialog extends HttpBaseActivity {
	ImageButton closeBtn;
	WebView wv;
	String link = "";
	String type = "";
	int roomtype = 0;
	ProgressBar progressBar;
	RelativeLayout progressBarRelativeLayout;
	private PayResultReceiver receiver;
	private static boolean registerReceiver = true;
	GotoBackJSInterface gotobackjs;
	ImageView system_msg_dialog_title;
	boolean isload = false;
	//
	private String help, uid;
	private File donghuadownload;
	private File zipFile;
	private String htmlname, foldername, newpath;
	private File htmlfile;
	private int flg = 0;
	public static String path = "";
	public static List<String> urls;
	SharedPreferences sp;
	SystemSettingUtilSp su;
	private Details dt= new Details(); 
	private Commond commond = new Commond();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		/** 全屏设置，隐藏窗口所有装饰 */
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		AdManager.init(SystemMsgWebDialog.this, "N8SUWE3INS", "Z7JL3YXOQR", 60,
//				2, 2, 2);
		
		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
		
		setContentView(R.layout.system_msg_dialog);
		Intent intent = getIntent();
		link = intent.getStringExtra("link");
		type = intent.getStringExtra("type");

		urls = new ArrayList<String>();
//		AdFloatViewManager.getInstance().showAdFloatView(this,
//				new AdListener() {
//					public void onReceiveSuccess() {
//					}
//
//					public void onReceiveFail(int code) {
//					}
//
//					public void onPresentScreen() {
//					}
//
//					public void onDismissScreen() {
//					}
//
//					public void onAdSwitch() {
//					}
//				}, InterstitialSize.SIZE_300X250);

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
		wv = (WebView) findViewById(R.id.system_msg_webView);

		roomtype = intent.getIntExtra("roomtype", 0);
		closeBtn = (ImageButton) findViewById(R.id.system_msg_close_btn);

		system_msg_dialog_title = (ImageView) findViewById(R.id.system_msg_dialog_title);

		gotobackjs = new GotoBackJSInterface();
		WebSettings webSettings = wv.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		wv.addJavascriptInterface(gotobackjs, "gotobackinterface");
		wv.setBackgroundColor(0);
		wv.clearCache(true);
		
		uid = intent.getStringExtra("uid");
		help = intent.getStringExtra("help");
		// 跳转过来。。接受path ，如果不为空 拆分 为 html名字 和 要解压到文件夹的名字
		if (!TextUtils.isEmpty(help)) {
			String[] strs = help.split("[/]");
			foldername = strs[0];
			htmlname = strs[1];
			path = help;
			// 判断 html文件存不存在
			htmlfile = new File(zipFile + File.separator + foldername
					+ File.separator + htmlname);
			String filename = htmlfile.getPath().toString();
			if (htmlfile.exists()) {
				flg = 1;
				wv.loadUrl("file:///" + htmlfile);
			}
		}
		progressBar = (ProgressBar) findViewById(R.id.system_msg_progressbar);
		progressBarRelativeLayout = (RelativeLayout) findViewById(R.id.system_msg_progressbar_RelativeLayout);
		progressBar.setVisibility(View.VISIBLE);
		progressBarRelativeLayout.setVisibility(View.VISIBLE);
		// 进度条实例化，为了返回销毁
		switch (Integer.parseInt(type)) {
		case 1:
			system_msg_dialog_title
					.setBackgroundResource(R.drawable.help_title);
			break;

		case 2:
			system_msg_dialog_title
					.setBackgroundResource(R.drawable.shop_title);
			break;
		case 3:
			system_msg_dialog_title
					.setBackgroundResource(R.drawable.other_title);
			break;
		case 4:
			system_msg_dialog_title
					.setBackgroundResource(R.drawable.huodong_title);
			break;
		case 5:
			system_msg_dialog_title
					.setBackgroundResource(R.drawable.xunzhang_title);
			break;
		case 6:
			system_msg_dialog_title
					.setBackgroundResource(R.drawable.userpwd_title);
			break;
		case 7:
			if (roomtype == 1) {
				system_msg_dialog_title
						.setBackgroundResource(R.drawable.dating_title);
			} else {
				system_msg_dialog_title
						.setBackgroundResource(R.drawable.house_title);
			}
			break;
		case 13:
			system_msg_dialog_title
					.setBackgroundResource(R.drawable.jiecao_caifu);
			break;
		case 14:
			system_msg_dialog_title
					.setBackgroundResource(R.drawable.usersign_title);
			break;
		case 15:
			system_msg_dialog_title
					.setBackgroundResource(R.drawable.shenhe_title);
			break;
		case 99:
			system_msg_dialog_title
					.setBackgroundResource(R.drawable.village_sys_title);
			break;
		case 20:
			system_msg_dialog_title
			.setBackgroundResource(R.drawable.shangbangjieshao);
			break;
		case 21:
			system_msg_dialog_title
			.setBackgroundResource(R.drawable.shangbangjieshao);
			break;
		case 22:
			system_msg_dialog_title
					.setBackgroundResource(R.drawable.jiecao_caifu);
			break;
		}
		
		wv.requestFocus();
		wv.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				if (newProgress == 100) {
					isload = true;
					progressBar.setVisibility(View.GONE);
					progressBarRelativeLayout.setVisibility(View.GONE);
//					wv.loadUrl("file:///" + htmlfile);
				} else {
					isload = false;
				}
				super.onProgressChanged(view, newProgress);
			}
		});

		wv.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(final WebView view,
					final String url) {

				if (!commond.preUrl(SystemMsgWebDialog.this, url)) {
					view.clearView();
					view.loadUrl(url);
				}
				return true;
			}
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				super.onReceivedError(view, errorCode, description, failingUrl);
				 wv.loadUrl("file:///android_asset/failed/failed.html");
				 File file;
					File mfile = new File(Environment.getExternalStorageDirectory()
							+ File.separator + getPackageName() + "donghuazip");
					if (mfile.exists()) {
						File[] files = mfile.listFiles();  
						// 将所有的文件存入ArrayList中,并过滤所有图片格式的文件 
						for (int i = 0; i < files.length; i++) {
                             if(files[i].getName().contains(foldername)){
                            	files[i].delete(); 
                             }
						} 
					}
			}			
		});

		if (TextUtils.isEmpty(link)) {
			uid = intent.getStringExtra("uid");
//			help = intent.getStringExtra("help");
//			// 跳转过来。。接受path ，如果不为空 拆分 为 html名字 和 要解压到文件夹的名字
			if (!TextUtils.isEmpty(help)) {
//				String[] strs = help.split("[/]");
//				foldername = strs[0];
//				htmlname = strs[1];
//				path = help;
//				// 判断 html文件存不存在
//				htmlfile = new File(zipFile + File.separator + foldername
//						+ File.separator + htmlname);
//				String filename = htmlfile.getPath().toString();
//				if (htmlfile.exists()) {
//					flg = 1;
//					wv.loadUrl("file:///" + htmlfile);
//				}
//				
				StringBuffer data = new StringBuffer();
				String url;
				url = Details.geturl(ConstantsJrc.WEB_DONGHUA);
				help = URLEncoder.encode(help);
				url = dt.appendNameValue(url, "path", help);
				url = Details.appendNameValueint(url, "flg", flg);
				url = dt.appendNameValue(url, "token", URLEncoder.encode(su.getToken()));
				// 请求网络验证登陆
				HttpRequestTask request = new HttpRequestTask();
				request.execute(url, data.toString());
			}
//			else {
//					// 判断 html文件存不存在
//					progressBar.setVisibility(View.GONE);
//					progressBarRelativeLayout.setVisibility(View.GONE);
//					String[] strs = path.split("[/]");
//					foldername = strs[0];
//					htmlname = strs[1];
//					htmlfile = new File(zipFile + File.separator + foldername
//							+ File.separator + htmlname);
//					wv.loadUrl("file:///" + htmlfile);
//					return;				
//			}

		
			
		} else {
			progressBar.setVisibility(View.VISIBLE);
			progressBarRelativeLayout.setVisibility(View.VISIBLE);
			wv.loadUrl(link);
		}

		// wv.setFocusable(false);


		closeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!isload) {
					finish();
				} else {
					wv.loadUrl("javascript:" + "if(window.gotobackinterface){"
							+ "    window.gotobackinterface.gotoback(offset);"
							+ "}gotoBack();");
				}
			}
		});
	}

	public class GotoBackJSInterface {

		public void gotoback(String offset) {
			// System.out.println("offset:" + offset);
			if ("0".equals(offset))
				SystemMsgWebDialog.this.finish();

		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (wv != null) {
			wv.clearHistory();
		}
		super.onDestroy();
	}

	public void finish() {
		super.finish();
//		try {
//			AdFloatViewManager.getInstance().removeAdFloatView(
//					getApplicationContext());
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}

	}

	@Override
	public void resultData(String url, String result) {
		// TODO Auto-generated method stub
		String tip = null;
		if (result == null) {
			// fresh.setVisibility(View.VISIBLE);
			Commond.showToast(SystemMsgWebDialog.this, "小贱提醒 ：当前网络不稳定！");
			// ll.setVisibility(View.GONE);
			return;
		}
		JSONObject jsonChannel;
		try {
			if(TextUtils.isEmpty(result)){
				if (TextUtils.isEmpty(link) && TextUtils.isEmpty(newpath)) {
					wv.loadUrl("file:///" + htmlfile);
				}
				return ;
			}		
			jsonChannel = new JSONObject(result);
			int ret = jsonChannel.optInt("ret");
			tip = URLDecoder.decode(jsonChannel.optString("tip"));
			link = URLDecoder.decode(jsonChannel.optString("link"));
			newpath = URLDecoder.decode(jsonChannel.optString("path"));

			File picfile = new File(donghuadownload + "/" + File.separator
					+ Commond.getMd5Hash(newpath) + ".zip");
			String filename = picfile.getPath().toString();

			if (TextUtils.isEmpty(link) && TextUtils.isEmpty(newpath)) {
//				wv.loadUrl("file:///" + htmlfile);
			} else {
				if (!TextUtils.isEmpty(link) || !TextUtils.isEmpty(newpath)) {				
					if(!urls.contains(url)){
						urls.add(url);
					new Thread(new myThread(link, newpath , url)).start();
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}

	class myThread implements Runnable {
		public String furl, path ,qingqiuurl;

		public myThread(String url, String path ,String url2) {
			this.furl = url;
			this.path = path;
			this.qingqiuurl = url2 ;
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
				myHandler.sendMessage(message);
				// }

			} catch (Exception e) {
				// Commond.showToast(DazuiActivity.this, "网络很不给力啊！");
				Message message = new Message();
				message.what = 1;
				myHandler.sendMessage(message);
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
						new Thread(new jieyaThread(picfile,str2)).start();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			super.handleMessage(msg);
		}
	};
	
	 class jieyaThread implements Runnable {   
		 File file ;
		 String str2 ;
		 public jieyaThread(File picfile, String str2) {
				this.file = picfile;
				this.str2 = str2 ;
			}
         public void run() {  
                  
            	  try {
					WebviewDonghua.upZipFile(file, zipFile
								.getAbsolutePath().toString());	
            	  } catch (Exception e) {
  					// TODO Auto-generated catch block
  					e.printStackTrace();
  				} 
                   Message message = new Message();   
                   message.what = 1;   
                   Bundle bundle = message.getData();
   				bundle.putString("str2", str2); // 往Bundle中存放数
   				message.setData(bundle);
   				if(file.exists()){
					file.delete();
				}
                  jieyaHandler.sendMessage(message);    
              }            
    } 
	 
	 Handler jieyaHandler = new Handler() {  
         public void handleMessage(Message msg) {   
 			progressBar.setVisibility(View.GONE);
 			String str2 = msg.getData().getString("str2");// 接受msg传递过来的参数
 			progressBarRelativeLayout.setVisibility(View.GONE);;
        	 File picfile1 = new File(zipFile + "/" + File.separator
						+ File.separator + foldername + File.separator
						+ htmlname);
				String filename1 = picfile1.getPath().toString();
				wv.loadUrl("file:///" + filename1);
				  urls.remove(str2);
              super.handleMessage(msg);   
         }   
    }; 
}
