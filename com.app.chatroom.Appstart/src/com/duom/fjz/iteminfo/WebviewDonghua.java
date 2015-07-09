package com.duom.fjz.iteminfo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.otherui.SystemMsgWebDialog;
import com.app.chatroom.util.Commond;
import com.app.chatroom.util.SystemUtil;
import com.jianrencun.android.Details;
import com.jianrencun.android.HttpBaseActivity;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.DazuiActivity;
import com.jianrencun.dazui.Dztishidialog;
import com.jianrencun.dazui.Mydazui;

public class WebviewDonghua extends Activity {
	private WebView wv;
	private LinearLayout ll;
	private String path, link, newpath, uid;
	private File donghuadownload;
	private File zipFile;
	private int flg = 0;
	private String htmlname, foldername;
	private File htmlfile;
	GetAudioJSInterface getaudiojs;
	private MediaPlayer mp;
	public static String dhpath = "";
	private Details dt= new Details(); 
	private Commond commond = new Commond();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.webviewdonghua);
		
		ll = (LinearLayout)findViewById(R.id.webview_donghua_ll);

		Intent it = getIntent();
//		uid = it.getStringExtra("uid");
		path = it.getStringExtra("htmlfile");

		getaudiojs = new GetAudioJSInterface();

		// 跳转过来。。接受path ，如果不为空 拆分 为 html名字 和 要解压到文件夹的名字		

		// 判断 html文件存不存在



		wv = (WebView) findViewById(R.id.webview_donghua);
		WebSettings webSettings = wv.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		wv.setBackgroundColor(0); // 设置背景色 //
		wv.addJavascriptInterface(getaudiojs, "getaudiointerface");
		wv.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				if (!commond.preUrl(WebviewDonghua.this, url)) {
					view.clearView();
					// view.loadUrl(url);
					return;
				}
				view.stopLoading();
				// DO SOMETHING
			}
			@Override
			public boolean shouldOverrideUrlLoading(final WebView view,
					final String url) {

				if (!commond.preUrl(WebviewDonghua.this, url)) {
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
			}
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				view.loadUrl("javascript:" + "if(window.getaudiointerface){"
						+ "    window.getaudiointerface.getaudio(getaudio());"
						+ "}");
			}
		});
		wv.loadUrl("file:///" + path);
	}


	/**
	 * 解压缩一个文件
	 * 
	 * @param zipFile
	 *            要解压的压缩文件
	 * @param folderPath
	 *            解压缩的目标目录
	 * @throws IOException
	 *             当解压缩过程出错时抛出
	 */
	public static int upZipFile(File zipFile, String folderPath)
			throws ZipException, IOException {
		ZipFile zfile = new ZipFile(zipFile);
		Enumeration zList = zfile.entries();
		ZipEntry ze = null;
		byte[] buf = new byte[1024];
		while (zList.hasMoreElements()) {
			ze = (ZipEntry) zList.nextElement();
			if (ze.isDirectory()) {
				String dirstr = folderPath + ze.getName();
				// dirstr.trim();
				dirstr = new String(dirstr.getBytes("8859_1"), "GB2312");
				Log.d("upZipFile", "str = " + dirstr);
				File f = new File(dirstr);
				f.mkdir();
				continue;
			}
			OutputStream os = new BufferedOutputStream(new FileOutputStream(
					getRealFileName(folderPath, ze.getName())));

			InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
			int readLen = 0;
			while ((readLen = is.read(buf, 0, 1024)) != -1) {
				os.write(buf, 0, readLen);
			}
			is.close();
			os.close();
		}
		zfile.close();

		return 0;
	}

	/**
	 * 给定根目录，返回一个相对路径所对应的实际文件名.
	 * 
	 * @param baseDir
	 *            指定根目录
	 * @param absFileName
	 *            相对路径名，来自于ZipEntry中的name
	 * @return java.io.File 实际的文件
	 */
	public static File getRealFileName(String baseDir, String absFileName) {
		String[] dirs = absFileName.split("/");
		File ret = new File(baseDir);
		String substr = null;
		if (dirs.length == 1) {
			substr = dirs[0];
			try {
				// substr.trim();
				substr = new String(substr.getBytes("8859_1"), "GB2312");

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ret = new File(ret, substr);
			return ret;
		}
		if (dirs.length > 1) {
			for (int i = 0; i < dirs.length - 1; i++) {
				substr = dirs[i];
				try {
					// substr.trim();
					substr = new String(substr.getBytes("8859_1"), "GB2312");

				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ret = new File(ret, substr);

			}
			if (!ret.exists())
				ret.mkdirs();
			substr = dirs[dirs.length - 1];
			try {
				// substr.trim();
				substr = new String(substr.getBytes("8859_1"), "GB2312");			
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ret = new File(ret, substr);
			Log.d("upZipFile", "2ret = " + ret);
			return ret;
		}
		return ret;
	}

	public class GetAudioJSInterface {

		public void getaudio(String path) {
			if (!TextUtils.isEmpty(path)) {
				if (null == mp) {
					mp = new MediaPlayer();
				}
				File file1 = new File(zipFile + File.separator + path);
				String filename = file1.getAbsolutePath().toString();
				File file = new File(filename);
				FileInputStream fis;
				try {
					fis = new FileInputStream(file);
					mp.setDataSource(fis.getFD());
					// 设置要播放的文件
					// player.setDataSource(mFlilepath);
					mp.prepare();
					// 播放之
					mp.start();
					mp.setOnCompletionListener(new OnCompletionListener() {

						@Override
						public void onCompletion(MediaPlayer mp) {
							mp.release();
							mp = null;
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (mp != null) {
			mp.release();
			mp = null;
		}
		super.onDestroy();
	}
}
