package com.app.chatroom.util;

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
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.MakesureClear;
import com.app.chatroom.SearchPeople;
import com.app.chatroom.Tuhaobang;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.download.APKDownload;
import com.app.chatroom.otherui.SystemMsgWebDialog;
//import com.app.chatroom.otherui.SystemMsgWebPayDialog;
import com.app.chatroom.otherui.VillageUserInfoDialog;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.ui.MailContentActivity;
import com.duom.fjz.iteminfo.WebviewDonghua;
import com.jianrencun.android.Details;
import com.jianrencun.android.HttpBaseActivity;
import com.jianrencun.android.Xjfabu;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Dzmysave;

public class Commond{
	private String gamehtml, lsgh;
	private int downflg;
	private String newpath, link;
	private File donghuadownload, zipFile, htmlfile;
	private String htmlname, foldername;
	static List<String> urls = new ArrayList<String>();
	private static Activity context ;
	
	

	/**
	 * Md5 32仿or 16仿加密
	 * 
	 * @param plainText
	 * @return 32位加孿
	 */
	public static String getMd5Hash(String plainText) {
		StringBuffer buf = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			// Log.e("555","result: " + buf.toString());//32位的加密
			// Log.e("555","result: " + buf.toString().substring(8,24));//16位的加密
			return buf.toString().substring(8, 16);// 返回8仿

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将url返回的内容保存为文件
	 * 
	 * @param url
	 */
	public static String urlToFile(Context context, String url, String filename) {
		try {

			HttpGet httpRequest = new HttpGet(url);
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = (HttpResponse) httpclient
					.execute(httpRequest);
			HttpEntity entity = response.getEntity();
			BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(
					entity);
			InputStream s = bufferedHttpEntity.getContent();
			// bitmap = BitmapFactory.decodeStream(is);
			FileOutputStream fos = new FileOutputStream(filename);
			byte[] buffer = new byte[10240];
			int len = s.read(buffer);
			while (len > 0) {
				fos.write(buffer, 0, len);
				len = s.read(buffer);
			}
			fos.flush();
			fos.close();
			fos = null;
			s.close();
			// 使用HttpURLConnection加载网络图片的时候，
			// 偶尔会出现：“SkImageDecoder::Factory returned null”错误！

			// URL l = new URL(url);
			// HttpURLConnection c = (HttpURLConnection) l.openConnection();
			// if (HttpURLConnection.HTTP_OK == c.getResponseCode()) {
			// InputStream s = c.getInputStream();
			// FileOutputStream fos = new FileOutputStream(filename);
			// byte[] buffer = new byte[1024];
			// int len = s.read(buffer);
			// while (len > 0) {
			// fos.write(buffer, 0, len);
			// len = s.read(buffer);
			// }
			// fos.flush();
			// fos.close();
			// fos = null;
			// s.close();
			// }
			// if (c != null) {
			// c.disconnect();
			// }
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return "请求失败:" + ex.getMessage();
		}
	}

	// 判断格式
	public static String getFileType(FileInputStream fios) {
		String ex = "";
		try {
			int aa = fios.read();
			int bb = fios.read();
			if (aa == 66 && bb == 77) {
				ex = "bmp";
			} else if (aa == 255 && bb == 216) {
				ex = "jpg";
			} else if (aa == 137 && bb == 80) {
				ex = "png";
			} else if (aa == 71 && bb == 73) {
				ex = "gif";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ex;
	}

	/**
	 * 复制文件到新地址
	 * 
	 * @param srcFileName
	 * @param destFileName
	 * @return
	 * @throws Exception
	 */
	public static boolean copyFile(String srcFileName, String destFileName) {
		try {
			File srcfile = new File(srcFileName);
			if (!srcfile.exists())
				return false;
			File descfile = new File(destFileName);
			if (!descfile.exists())
				descfile.createNewFile();
			int length = 2097152;
			FileInputStream in = new FileInputStream(srcfile);
			FileOutputStream out = new FileOutputStream(descfile);
			FileChannel inC = in.getChannel();
			FileChannel outC = out.getChannel();
			while (true) {
				if (inC.position() == inC.size()) {
					inC.close();
					outC.close();
					return true;
				}
				if ((inC.size() - inC.position()) < 20971520)
					length = (int) (inC.size() - inC.position());
				else
					length = 20971520;
				inC.transferTo(inC.position(), length, outC);
				inC.position(inC.position() + length);
			}
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 显示提示消息
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showToast(final Context context, String msg) {
		if (context == null)
			return;
		LayoutInflater vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View toastRoot = vi.inflate(R.layout.toastzdy, null);
		Toast toast = new Toast(context.getApplicationContext());
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		TextView tv = (TextView) toastRoot.findViewById(R.id.TextViewInfo);
		if (tv != null)
			tv.setText(msg);
		if (!TextUtils.isEmpty(msg)) {
			toast.show();
		}
	}

	/**
	 * 拦截WebView跳转
	 * 
	 * @param activity
	 * @param url
	 * @return
	 */
	public  boolean preUrl(Activity activity, String url) {
		SharedPreferences sp;
		SystemSettingUtilSp su;
		context = activity ;
		sp = activity.getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				activity.MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
		String[] strUrl;
		String pre = "http://market:";
		String pre2 = "http://web:";
		String pre3 = "http://blank:";// 转到浏览器进行打开
		String pre4 = "http://upay:"; // 银联支付
		String pre5 = "http://chat:"; // 聊天室
		String pre6 = "http://jian-";// 小贱工作室
		String pre7 = "http://zui-";// 大嘴
		String pre8 = "http://cun:";// 村委会
		String pre9 = "http://xin:";// 私信
		String pre10 = "http://uid-";// 个人信息
		String pre11 = "http://xin-uid-";// 私信
		String pre12 = "http://mm:";
		String pre13 = "http://close";
		String pre14 = "http://dj-";
		String pre15 = "http://clear";
		String pre16 = "http://hd:";
		String pre17 = "http://shop:";
		String pre18 = "http://jt:";
		String pre19 = "http://th:";
		String pre20 = "http://help:";
		String pre21 = "http://jp:";

		String action = "android.intent.action.VIEW";
		try {
			if (url.startsWith(pre)) {
				Intent i = new Intent(action);
				i.setData(Uri.parse("market://search?q=pname:"
						+ url.substring(pre.length())));
				activity.startActivity(i);
				return true;
			} else if (url.startsWith(pre2)) {
				// ///////////////////////////
				String[] tmps = url.split("\\|");
				String newurl = null;
				String title = null;
				long size = 0;
				if (tmps.length >= 3) {
					newurl = tmps[0];
					title = URLDecoder.decode(tmps[2]);
					if (TextUtils.isDigitsOnly(tmps[1]))
						size = Long.parseLong(tmps[1]);
				} else {
					newurl = url;
				}
				// 请求下载
				newurl = "http://" + newurl.substring(pre2.length());
				APKDownload.startDownThread(activity, title, newurl, size);
				return true;
			} else if (url.startsWith(pre3)) {
				// ///////////////////////////
				Intent i = new Intent(action);
				i.setData(Uri.parse("http://" + url.substring(pre3.length())));
				activity.startActivity(i);
				return true;
			} else if (url.startsWith(pre4)) {
				// checkUPPay(activity);
			} else if (url.startsWith(pre5)) {
				// 聊天室
			} else if (url.startsWith(pre6)) {
				// 小贱工作室
				System.out.println(url);
				strUrl = url.split("-|:");
				Intent intent = new Intent(activity, Xjfabu.class);
				intent.putExtra("ouid", strUrl[2]);
				intent.putExtra("osc", ConstantsJrc.USERDZURL);
				intent.putExtra("type", 1);
				activity.startActivity(intent);
				return true;
			} else if (url.startsWith(pre7)) {
				// 大嘴
				strUrl = url.split("-|:");
				Intent intent = new Intent(activity, Dzmysave.class);
				intent.putExtra("ouid", strUrl[2]);
				intent.putExtra("osc", ConstantsJrc.USERDZURL);
				intent.putExtra("type", 1);
				activity.startActivity(intent);
				return true;
			} else if (url.startsWith(pre8)) {
				// 村委会
			} else if (url.startsWith(pre9)) {
				// 私信
			} else if (url.startsWith(pre10)) {
				strUrl = url.split("-|:");
				Intent intent = new Intent(activity,
						VillageUserInfoDialog.class);
				intent.putExtra("uid", strUrl[2]);
				intent.putExtra("nick", "");
				intent.putExtra("fuid", su.getUid());
				intent.putExtra("type", 2);
				activity.startActivity(intent);
				return true;
			} else if (url.startsWith(pre11)) {
				strUrl = url.split("-|:");
				if (strUrl[3].equals(su.getUid())) {
					Commond.showToast(activity, "和自己留言脑袋有问题");
				} else {
					Intent intent = new Intent(activity,
							MailContentActivity.class);
					intent.putExtra("fuid", strUrl[3]);
					intent.putExtra("fnick", "");
					activity.startActivity(intent);
				}
				return true;
			} else if (url.startsWith(pre12)) {
//				if (activity instanceof SystemMsgWebPayDialog) {
//					SystemMsgWebPayDialog sy = (SystemMsgWebPayDialog) activity;
//					strUrl = url.split(":|/");
//					String code = strUrl[4].toString();
//					sy.initPay(code);
//				}
				return true;
			} else if (url.startsWith(pre13)) {
				activity.finish();
				return true;
			}else if(url.startsWith(pre14)){
				if (SystemUtil.getSDCardMount()) {
					donghuadownload = new File(
							Environment.getExternalStorageDirectory() + File.separator
									+ activity.getPackageName() + "/donghuadownload");
					zipFile = new File(Environment.getExternalStorageDirectory()
							+ File.separator + activity.getPackageName() + "/donghuazip");
					if (!donghuadownload.exists()) {
						donghuadownload.mkdirs();
					}
					if (!zipFile.exists()) {
						zipFile.mkdirs();
					}
				} else {
					donghuadownload = new File(ConstantsJrc.PROJECT_PATH
							+ activity.getPackageName() + "/donghuadownload");
					zipFile = new File(ConstantsJrc.PROJECT_PATH + activity.getPackageName()
							+ "/donghuazip");
					if (!donghuadownload.exists()) {
						donghuadownload.mkdirs();
					}
					if (!zipFile.exists()) {
						zipFile.mkdirs();
					}
				}
				strUrl = url.split(":|-");
				Intent intent = new Intent(activity,
						WebviewDonghua.class);
				donghuapre(strUrl[2]);
				StringBuffer data = new StringBuffer();
				String url2 , path;
				url2 = Details.geturl(ConstantsJrc.WEB_DONGHUA);
				path = URLEncoder.encode(strUrl[2]);
				url2 = appendNameValue(url2, "path", path);
				url2 = Details.appendNameValueint(url2, "flg", downflg);
				// 请求网络验证登陆
				new Thread(new getDate(url2)).start();
				return true;
			}else if(url.startsWith(pre15)){
				Intent intent = new Intent(activity,
						MakesureClear.class);
				intent.putExtra("from", "clear");
				activity.startActivity(intent);
				return true ;
			}else if(url.startsWith(pre16)){
				Intent intenttask = new Intent(activity,
						SystemMsgWebDialog.class);
				intenttask.putExtra("help", su.getActivity());
				intenttask.putExtra("uid", su.getUid());
				intenttask.putExtra("type", "4");
				activity.startActivity(intenttask);
				return true ;
			}else if(url.startsWith(pre17)){
				Intent intentshop = new Intent(activity,
						SystemMsgWebDialog.class);
				intentshop.putExtra("help", su.getShop());
				intentshop.putExtra("uid", su.getUid());
				intentshop.putExtra("type", "2");
				activity.startActivity(intentshop);
				return true ;
			}else if(url.startsWith(pre18)){
				Intent itsou = new Intent(activity,
						SearchPeople.class);				
				activity.startActivity(itsou);
				return true ;
			}else if(url.startsWith(pre19)){
				Intent it = new Intent() ;
				it.setClass(activity, Tuhaobang.class);
				activity.startActivity(it);
				return true ;
			}else if(url.startsWith(pre20)){
				Intent intenthelp = new Intent(activity,
						SystemMsgWebDialog.class);
				intenthelp.putExtra("help", su.getHelp());
				intenthelp.putExtra("uid", su.getUid());
				intenthelp.putExtra("type", "1");
				activity.startActivity(intenthelp);
				return true ;
			}
			else if(url.startsWith(pre21)){
				Intent intentjp = new Intent(activity,
						SystemMsgWebDialog.class);
				intentjp.putExtra(
						"link",
						ConstantsJrc.MAINMORE
								+ "?uid="
								+ su.getUid()
								+ "&flg=3&w="
								+ PhoneInfo
										.getInstance(activity)
										.getWidth(activity)
								+ "&pkg="
								+ PhoneInfo
										.getInstance(activity)
										.getPackage(activity)
								+ "&ver="
								+ PhoneInfo
										.getInstance(activity)
										.getVersionName(activity)
								+ "&token=" + su.getToken());
				intentjp.putExtra("type", "3");
				activity.startActivity(intentjp);
				return true ;
			}
		} catch (Exception e) {
			showToast(activity, "未检测到浏览器");
		}

		return false;
	}
	/**  
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp  
     */    
    public static int px2dip(Context context, float pxValue) {    
        final float scale = context.getResources().getDisplayMetrics().density;    
        return (int) (pxValue / scale + 0.5f);    
    }    
    /**  
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)  
     */    
    public static int dip2px(Context context, float dpValue) {    
        final float scale = context.getResources().getDisplayMetrics().density;    
        return (int) (dpValue * scale + 0.5f);    
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
			File picfile1 = new File(zipFile + "/" + File.separator
					+ File.separator + foldername + File.separator + htmlname);
			String filename1 = picfile1.getPath().toString();
			Intent intent = new Intent(context,
					WebviewDonghua.class);
			intent.putExtra("htmlfile", filename1);
			context.startActivity(intent);
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
			Intent intent = new Intent(context.getApplicationContext(),
					WebviewDonghua.class);
			intent.putExtra("htmlfile", htmlfile.getPath());
			context.startActivity(intent);
			downflg = 1;
			return;
		}
	}

	class getDate implements Runnable {
		String path;

		public getDate(String path) {
			this.path = path;
		}

		public void run() {
			HttpGetData(path);
		}
	}

	public void HttpGetData(String uri) {
		try {
			org.apache.http.client.HttpClient httpclient = new DefaultHttpClient();
			HttpGet get = new HttpGet(uri);
			HttpResponse response;
			response = httpclient.execute(get);
			int code = response.getStatusLine().getStatusCode();
			// 检验状态码，如果成功接收数据
			if (code == 200) {
				// 返回json格式： {"id": "27JpL~j4vsL0LX00E00005","version": "abc"}
				String rev = EntityUtils.toString(response.getEntity());
				JSONObject jsonChannel;
				try {
					jsonChannel = new JSONObject(rev);
					int ret = jsonChannel.optInt("ret");

					link = URLDecoder.decode(jsonChannel.optString("link"));
					newpath = URLDecoder.decode(jsonChannel.optString("path"));

					File picfile = new File(donghuadownload + "/"
							+ File.separator + Commond.getMd5Hash(newpath)
							+ ".zip");
					String filename = picfile.getPath().toString();

					if (TextUtils.isEmpty(link) && TextUtils.isEmpty(newpath)) {
						// Intent intent = new Intent(getApplicationContext(),
						// WebviewDonghua.class);
						// intent.putExtra("htmlfile", htmlfile.getPath());
						// startActivity(intent);
					} else {
						if (!picfile.exists()) {
							urls.add(uri);
							new Thread(new myThread(link, newpath, uri,
									foldername, htmlname, donghuadownload,
									myHandler)).start();
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
		}
	}
	/**
	 * 
	 * @param url
	 * @param name
	 * @param value
	 * @return
	 */
	public  String appendNameValue(String url, String name, String value) {
		if (!url.contains("&" + name + "=") && !url.contains("?" + name + "=")) {
			if (url.indexOf('?') > 0) {
				url += "&" + name + "=" + value;
			} else {
				url += "?" + name + "=" + value;
			}
		}
		if(!TextUtils.isEmpty(MainMenuActivity.vername)){
		if(!url.contains("&ver=") && !url.contains("?ver=")){
			url += "&" + "ver" + "=" + MainMenuActivity.vername ;			
		}
		}
		return url;
	}
}
