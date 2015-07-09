package com.weibo.sina;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.ui.VillageActivity;
import com.app.chatroom.util.Commond;
import com.jianrencun.android.CacheData;
import com.jianrencun.android.CategoryEntity;
import com.jianrencun.android.Entrance;
import com.jianrencun.android.JianrencunActivity;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.DazuiActivity;
import com.umeng.analytics.MobclickAgent;
import com.weibo.joechan.util.DisplayUtil;

/**
 * 新浪微博认证
 * 
 * @author Administrator
 * 
 */
public class AuthorizeActivity extends Activity {

	private ImageView SharePic;
	private String str, imageUrl;
	private Bundle bundle, bundle2;
	public static String sinaname, sinaheader, city;

	// Bitmap bitmap1;
	private static final String URL_ACTIVITY_CALLBACK = "weiboandroidsdk://TimeLineActivity";
	private static final String FROM = "xweibo";

	// 设置appkey及appsecret，如何获取新浪微博appkey和appsecret请另外查询相关信息，此处不作介绍
	// public static final String CONSUMER_KEY = "664316105";//
	// 替换为开发者的appkey，例如"1646212960";
	// private static final String CONSUMER_SECRET =
	// "9281f5eb4bed91c2881fb17d0541328f";//
	// 替换为开发者的appkey，例如"94098772160b6f8ffc1315374d8861f9";

	public static final String CONSUMER_KEY = "2608313580";// 替换为开发者的appkey，例如"1646212960";
	private static final String CONSUMER_SECRET = "7092cfd4cdfdfd8e60948012b35bfd73";// 替换为开发者的appkey，例如"94098772160b6f8ffc1315374d8861f9";

	private String username = "";
	private String password = "";
	Weibo weibo;
	private String uid;
	private AccessToken accessToken;
	public static Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (MainMenuActivity.k == 1) {
			bundle = getIntent().getExtras();
			str = bundle.getString("msg1");
			imageUrl = bundle.getString("imageUrl");

		}
		setContentView(R.layout.top);
		DisplayUtil.initWindows(this);
		File sfile = new File(MainMenuActivity.sppath
				+ "AuthoSharePreference.xml");
		if (sfile.exists()) {
			if (!(TextUtils.isEmpty(AuthoSharePreference
					.getToken(AuthorizeActivity.this)) && TextUtils
					.isEmpty(AuthoSharePreference
							.getSecret(AuthorizeActivity.this)))) {
				login();

			}
		} else {
			weibo = Weibo.getInstance();
			weibo.setupConsumerConfig(CONSUMER_KEY, CONSUMER_SECRET);
			// Oauth2.0隐式授权认证方式
			weibo.setRedirectUrl("http://jianrencun.com/wb_callback_url.php");// 此处回调页内容应该替换为与appkey对应的应用回调页
			weibo.authorize(AuthorizeActivity.this, new AuthDialogListener());
			dialog.setCancelable(true);
			dialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					// TODO Auto-generated method stub
					finish();
				}
			});
		}
	}

	public void login() {

		Utility.setAuthorization(new Oauth2AccessTokenHeader());
		String token = AuthoSharePreference.getToken(this);
		uid = AuthoSharePreference.getUid(this);
		// String kk = uid;
		accessToken = new AccessToken(token, CONSUMER_SECRET);
		// AccessToken accessToken22 = accessToken;
		Weibo.getInstance().setAccessToken(accessToken);
		if (MainMenuActivity.k == 0) {
			try {
				Weibo weibo = Weibo.getInstance();
				weibo.setAccessToken(accessToken);
				String ss = getCounts(weibo);
				getjson(ss);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finish();
		} else {
//			goShareActivity();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		AuthorizeActivity.this.finish();
		return true;
	}

//	public void goShareActivity() {
//		Intent intent = new Intent();
//		intent.setClass(AuthorizeActivity.this, ShareActivity.class);
//		/*
//		 * this, weibo.getAccessToken().getToken(), weibo
//		 * .getAccessToken().getSecret(), content, picPath
//		 */
//		intent.putExtra(ShareActivity.EXTRA_ACCESS_TOKEN,
//				AuthoSharePreference.getToken(AuthorizeActivity.this));
//		intent.putExtra(ShareActivity.EXTRA_TOKEN_SECRET,
//				AuthoSharePreference.getSecret(AuthorizeActivity.this));
//		intent.putExtra(ShareActivity.EXTRA_WEIBO_CONTENT, str);
//		intent.putExtra(ShareActivity.EXTRA_PIC_URI, imageUrl);
//		startActivity(intent);
//		finish();
//	}

	class AuthDialogListener implements WeiboDialogListener {

		@Override
		public void onComplete(Bundle values) {

			String token = values.getString("access_token");
			String expires_in = values.getString("expires_in");
			String remind_in = values.getString("remind_in");
			String content = "access_token : " + token + "  expires_in: "
					+ expires_in;
			uid = values.getString("uid");
			System.out.println("token---@>" + token);

			accessToken = new AccessToken(token, CONSUMER_SECRET);

			accessToken.setExpiresIn(expires_in);
			Weibo.getInstance().setAccessToken(accessToken);

			AuthoSharePreference.putToken(AuthorizeActivity.this, token);
			AuthoSharePreference.putExpires(AuthorizeActivity.this, expires_in);
			AuthoSharePreference.putRemind(AuthorizeActivity.this, remind_in);
			AuthoSharePreference.putUid(AuthorizeActivity.this, uid);
			AuthoSharePreference.putSecret(AuthorizeActivity.this, Weibo
					.getInstance().getAccessToken().getSecret());

			if (MainMenuActivity.k == 1) {
				try {
					share2weibo(str, imageUrl);
					AuthorizeActivity.this.finish();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {

				}
			} else if (MainMenuActivity.k == 0) {
				try {
					String ss = getCounts(weibo);
					getjson(ss);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		@Override
		public void onError(DialogError e) {
			Toast.makeText(getApplicationContext(),
					"Auth error : " + e.getMessage(), Toast.LENGTH_LONG).show();
		}

		@Override
		public void onCancel() {
			AuthorizeActivity.this.finish();
			Toast.makeText(getApplicationContext(), "取消验证", Toast.LENGTH_LONG)
					.show();
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(getApplicationContext(),
					"Auth exception : " + e.getMessage(), Toast.LENGTH_LONG)
					.show();
		}

	}

	// 分享微博
	private void share2weibo(String content, String picPath)
			throws WeiboException {
		Weibo weibo = Weibo.getInstance();
		weibo.share2weibo(this, weibo.getAccessToken().getToken(), weibo
				.getAccessToken().getSecret(), content, picPath);
	}

	// 获取用户资料
	public String getCounts(Weibo weibo) throws MalformedURLException,
			IOException, WeiboException {
		String url = Weibo.SERVER + "users/show.json";
		WeiboParameters bundle = new WeiboParameters();
		bundle.add("source", Weibo.getAppKey());
		bundle.add("uid", uid);
		String rlt = weibo.request(this, url, bundle, "GET",
				weibo.getAccessToken());
		System.out.println("rlt--->" + rlt);
		return rlt;
	}

	// 返回最新的公共微博
	private String getPublicTimeline(Weibo weibo) throws MalformedURLException,
			IOException, WeiboException {
		String url = Weibo.SERVER + "statuses/public_timeline.json";
		WeiboParameters bundle = new WeiboParameters();
		bundle.add("source", Weibo.getAppKey());
		String rlt = weibo.request(this, url, bundle, "GET",
				weibo.getAccessToken());
		return rlt;
	}

	// 上传图片并发布一条新微博
	private String upload(Weibo weibo, String source, String file,
			String status, String lon, String lat) throws WeiboException {
		WeiboParameters bundle = new WeiboParameters();
		bundle.add("source", source);
		bundle.add("pic", file);
		bundle.add("status", status);
		if (!TextUtils.isEmpty(lon)) {
			bundle.add("lon", lon);
		}
		if (!TextUtils.isEmpty(lat)) {
			bundle.add("lat", lat);
		}
		String rlt = "";
		String url = Weibo.SERVER + "statuses/upload.json";
		try {
			rlt = weibo.request(this, url, bundle, Utility.HTTPMETHOD_POST,
					weibo.getAccessToken());
		} catch (WeiboException e) {
			throw new WeiboException(e);
		}
		return rlt;
	}

	// 发布一条微博信息
	private String update(Weibo weibo, String source, String status,
			String lon, String lat) throws WeiboException {
		WeiboParameters bundle = new WeiboParameters();
		bundle.add("source", source);
		bundle.add("status", status);
		if (!TextUtils.isEmpty(lon)) {
			bundle.add("lon", lon);
		}
		if (!TextUtils.isEmpty(lat)) {
			bundle.add("lat", lat);
		}
		String rlt = "";
		String url = Weibo.SERVER + "statuses/update.json";
		rlt = weibo.request(this, url, bundle, Utility.HTTPMETHOD_POST,
				weibo.getAccessToken());
		return rlt;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	public void getjson(String ss) {
		try {
			JSONObject jsonChannel2;
			jsonChannel2 = new JSONObject(ss);
			sinaheader = URLDecoder.decode(jsonChannel2
					.optString("profile_image_url"));
			sinaname = URLDecoder.decode(jsonChannel2.optString("screen_name"));
			city = URLDecoder.decode(jsonChannel2.optString("location"));

			try {
				update(weibo, CONSUMER_KEY,
						getResources().getString(R.string.qqsend), "", "");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String geturl(String s) {
		if (!s.contains("uid=")) {
			if (s.contains("?")) {
				s += "&uid=162";
			} else {
				s += "?uid=162";
			}
		}
		if (!s.contains("nick=")) {
			if (s.contains("?")) {
				s += "&nick=" + sinaname;
			} else {
				s += "?nick=" + sinaheader;
			}
		}
		return s;
	}

	public static String Gethttptonet(String url) {
		String strResult = "";

		/* 建立HTTP Get对象 */
		HttpGet httpRequest = new HttpGet(url);
		try {

			/* 发送请求并等待响应 */
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);
			/* 若状态码为200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 读 */
				strResult = EntityUtils.toString(httpResponse.getEntity());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strResult;
	}

	public static void get2json(Activity context, String ss , int whichto , boolean gotowhere ,String tp)
			throws JSONException {
		ArrayList<String> dztabname = null ;
		JSONObject jsonChannel = new JSONObject(ss);
		String tip = URLDecoder.decode(jsonChannel.optString("tip"));
		int ret = jsonChannel.optInt("ret");
		if (ret == 0) {
//			Commond.showToast(context, tip);
			if(whichto == 0){
			Intent it = new Intent();
			it.putExtra("tishi", tip);
			it.setClass(context, Entrance.class);
			it.putExtra("ret", ret);
			it.putExtra("whichto", whichto);
			context.startActivityForResult(it, 89);
			}else if(whichto == 1){
				Intent it = new Intent();
				it.putExtra("tishi", tip);
				it.setClass(context, Entrance.class);
				it.putExtra("ret", ret);
				it.putExtra("whichto", whichto);
				context.startActivityForResult(it, 90);
			}else if(whichto == 2){
				Commond.showToast(context, "进入失败！");
			}
			return;
		}  
		String pd = URLDecoder.decode(jsonChannel.optString("pd"));
		MainMenuActivity.shoucanglb = URLDecoder.decode(jsonChannel
				.optString("flink"));
		MainMenuActivity.shangchuanyh = URLDecoder.decode(jsonChannel
				.optString("plink"));
		MainMenuActivity.myshangchuanlb = URLDecoder.decode(jsonChannel
				.optString("ulink"));
		CacheData.catePd = pd;		


		if (ret == 2) {
			tip = URLDecoder.decode(jsonChannel.optString("tip"));
		}
		//
		if (tip.equalsIgnoreCase("") || tip == null) {
			if(whichto == 0){
				JSONArray jsonItems = jsonChannel.optJSONArray("items");
				if (jsonItems != null && jsonItems.length() > 0) {
					// tip = "获取成功！";
					for (int i = 0; i < jsonItems.length(); i++) {
						CategoryEntity entity = new CategoryEntity();
						JSONObject jsonItem = jsonItems.getJSONObject(i);
						entity.setName(URLDecoder.decode(jsonItem.optString("title")));
						String link = URLDecoder.decode(jsonItem.optString("link"));
						entity.setLink(link);
						CacheData.cateList.add(entity);
					}		
					CacheData.setCategoryList(context);
				} else {
					CacheData.getCategoryList(context);
					for (int i = 0; i < CacheData.cateList.size() - 1; i++) {
					}
				}
			
			Intent it = new Intent();
			it.setClass(context, JianrencunActivity.class);
			it.putExtra("tp", tp);
			it.putExtra("where", 3);
			context.startActivity(it);
			}else if(whichto == 1){			
				int sel = jsonChannel.optInt("sel");
				JSONArray jsonItems = jsonChannel.optJSONArray("items");
				if (jsonItems != null && jsonItems.length() > 0) {
					 dztabname = new ArrayList<String>();
					// tip = "获取成功！";
					for (int i = 0; i < jsonItems.length(); i++) {					
						JSONObject jsonItem = jsonItems.getJSONObject(i);
						String link = URLDecoder.decode(jsonItem.optString("title"));
						dztabname.add(link);
					}		
				}
				Intent it = new Intent();
				it.putExtra("sel", sel);
				it.putExtra("tp", tp);
				it.putExtra("where", 4);
				it.putStringArrayListExtra("dztabname", dztabname);
				it.setClass(context, DazuiActivity.class);
				context.startActivity(it);
			}else if(whichto == 2){		
				int flg = jsonChannel.optInt("flg");
				int sel = jsonChannel.optInt("sel");
				Intent it = new Intent(context,
						VillageActivity.class);
				it.putExtra("tp", tp);
				it.putExtra("where", 1);
				it.putExtra("flg", flg);
				it.putExtra("sel", sel);				
				context.startActivity(it);
			}
		} else {
			if(whichto == 0){
				if(gotowhere = true){
			Intent it = new Intent();
			it.putExtra("tishi", tip);
			it.setClass(context, Entrance.class);
			it.putExtra("ret", ret);
			it.putExtra("whichto", whichto);			
			context.startActivityForResult(it, 89);
				}
			}else if(whichto == 1){
				if(gotowhere = true){
				Intent it = new Intent();
				it.putExtra("tishi", tip);
				it.setClass(context, Entrance.class);
				it.putExtra("ret", ret);
				it.putExtra("whichto", whichto);
				context.startActivityForResult(it, 90);
				}
			}
		}
	}

}