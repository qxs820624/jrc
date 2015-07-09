package com.jianrencun.android;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import com.app.chatroom.Appstart;
import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;
import com.duom.fjz.iteminfo.Adapterwithpic;
import com.duom.fjz.iteminfo.BitmapCache;
import com.duom.fjz.iteminfo.BitmapCache1;
import com.duom.fjz.iteminfo.Iteminfo;
import com.jianrencun.chatroom.R;

public class Xjfabu extends HttpBaseActivity implements OnScrollListener{
	Button back, shuaxin;
	private ListView lv;
	ProgressBar pb;
	public static List<Iteminfo> Xjfabulist;
	Adapterwithpic ad;
	private int twice;
	private View vFooter;
	private LinearLayout loading;
	private LinearLayout rllt6;
	private List<String> dllinks;
	String nlink;
	private String currentdate = "";
	private ImageView nosave;
    private String ouid , url ;
    private TextView xjlogo ;
    private int type ;
	/* 配置文件 */
	SharedPreferences sp;
	SystemSettingUtilSp su;
	private Details dt= new Details(); 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mysave);

		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
		
		Intent it = getIntent();
		ouid = it.getStringExtra("ouid");
		url = it.getStringExtra("osc");
		type = it.getIntExtra("type", 0);
		
		
		xjlogo = (TextView)findViewById(R.id.xjlogo);
		back = (Button) findViewById(R.id.mysaveback);
		rllt6 = (LinearLayout) findViewById(R.id.village_leftlist_progressbar_RelativeLayout6);
		// shuaxin = (Button)findViewById(R.id.mysavefresh);
		lv = (ListView) findViewById(R.id.mysavelv);
		pb = (ProgressBar) findViewById(R.id.mysavepb);
		nosave = (ImageView) findViewById(R.id.nosave);
		
		if(ouid.equalsIgnoreCase(String.valueOf(su.getUid()))){
			xjlogo.setBackgroundResource(R.drawable.wodefabiao);
		}else{
			if(type == 1){
				xjlogo.setBackgroundResource(R.drawable.jiazufabiaoxj);
			}else{
			xjlogo.setBackgroundResource(R.drawable.tadefabiao);
			}
		}
		
		vFooter = LayoutInflater.from(this).inflate(R.layout.list_footer, null);
		loading = (LinearLayout) vFooter.findViewById(R.id.list_footer);
		loading.setVisibility(View.GONE);
		
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

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// posc = position ;
				Intent it = new Intent();
				if(Xjfabulist.size() !=0 && Xjfabulist != null){
				it.putExtra("content", Xjfabulist.get(position).getContent());
				it.putExtra("link", Xjfabulist.get(position).getLink());
				it.putExtra("date", Xjfabulist.get(position).getDate());
				it.putExtra("ccount", Xjfabulist.get(position).getCriticism());
				it.putExtra("clink", Xjfabulist.get(position).getClink());
				it.putExtra("rlink", Xjfabulist.get(position).getRlink());
				it.putExtra("flink", Xjfabulist.get(position).getFlink());
				it.putExtra("isfav", Xjfabulist.get(position).getIsfav());
				it.putExtra("header", Xjfabulist.get(position).getHeader());
				it.putExtra("po", position);
				it.putExtra("which", 55);
				Adapterwithpic.ViewHolder holder = (Adapterwithpic.ViewHolder) view
						.getTag();
				holder.content.setTextColor(Color.parseColor("#9d8f98"));
				holder.date.setTextColor(Color.parseColor("#b9b5b8"));
				it.setClass(Xjfabu.this, Details.class);
				startActivity(it);                                                       
				}else{             
					Commond.showToast(Xjfabu.this, "请求超时！请重新登录！");
				}
			}
		});

		Xjfabulist = new ArrayList<Iteminfo>();
		dllinks = new ArrayList<String>();
		// ////////////

		pb.setVisibility(View.VISIBLE);
		rllt6.setVisibility(View.VISIBLE);
//		String url = MainMenuActivity.shoucanglb;
//		String url2 = Details.geturl(url);
				
		String url2 = "http://jrc.hutudan.com/usercenter/user_posts.php";
		url2 = dt.appendNameValue(url2, "uid", String.valueOf(su.getUid()));
		url2 = dt.appendNameValue(url2, "ouid", ouid);
		StringBuffer data = new StringBuffer();
		HttpRequestTask request = new HttpRequestTask();
		request.execute(url2, data.toString());

	}

	@Override
	public void resultData(String url, String result) {
		// TODO Auto-generated method stub
		String tip = null;
		if (result == null) {
			// fresh.setVisibility(View.VISIBLE);
			Commond.showToast(Xjfabu.this, "小贱提醒 ：当前网络不稳定！");
			return;
		}

		try {
			// //////////////////////////////////////////////正文内容页面
			JSONObject jsonChannel = new JSONObject(result);
			int ret = jsonChannel.optInt("ret");
			if (ret == 0) {
				tip = URLDecoder.decode(jsonChannel.optString("tip"));
				Commond.showToast(Xjfabu.this, tip);
				return;
			}


			String pd = URLDecoder.decode(jsonChannel.optString("pd"));
			File picfile = new File(Appstart.jrcfile + "/"
					+ Adapterwithpic.getMd5Hash(url));
			String filename = picfile.getPath().toString();
			CacheData.save(filename, result.toString().getBytes(), this);
			nlink = URLDecoder.decode(jsonChannel.optString("nlink"));
			JSONArray jsonItems = jsonChannel.optJSONArray("items");
			if (jsonItems != null && jsonItems.length() > 0) {
//				listinfo.clear();
				currentdate = pd;
				// tip = "获取成功！";
				for (int i = 0; i < jsonItems.length(); i++) {

					JSONObject jsonItem = jsonItems.getJSONObject(i);
					String title = URLDecoder.decode(jsonItem
							.optString("title"));
					String link = URLDecoder.decode(jsonItem.optString("link"));

					String fcount = URLDecoder.decode(jsonItem
							.optString("fcount"));
					String date = URLDecoder.decode(jsonItem.optString("date"));
					String ccount = URLDecoder.decode(jsonItem
							.optString("ccount"));
					String dcount = URLDecoder.decode(jsonItem
							.optString("dcount"));
					String ucount = URLDecoder.decode(jsonItem
							.optString("ucount"));
					String thumbnail = URLDecoder.decode(jsonItem
							.optString("thumbnail"));

					int isfav = jsonItem.optInt("isfav");
					String flink = URLDecoder.decode(jsonItem
							.optString("flink"));
					String rlink = URLDecoder.decode(jsonItem
							.optString("rlink"));
					String clink = URLDecoder.decode(jsonItem
							.optString("clink"));
					Iteminfo item = new Iteminfo(title, date, thumbnail,
							ccount, dcount, ucount, fcount, link, flink, rlink,
							clink, isfav, -1, -1, null);
					// itemchoose1.add(item);
					Xjfabulist.add(item);
				}

				if(twice == 0){
					pb.setVisibility(View.GONE);
					rllt6.setVisibility(View.GONE);
						ad = new Adapterwithpic(Xjfabu.this, Xjfabulist,
								JianrencunActivity.number1, lv, false);
						lv.setAdapter(ad);
						
					
				}else if (twice == 3) {
					lv.requestFocus();
					ad.notifyDataSetChanged(); // 数据集变化后,通知adapter
				}
			} else {
				if (TextUtils.isEmpty(currentdate)) {
					pb.setVisibility(View.GONE);
					rllt6.setVisibility(View.GONE);
					if(Xjfabulist.size() == 0){
						if(ouid.equalsIgnoreCase(String.valueOf(su.getUid()))){
							nosave.setBackgroundResource(R.drawable.nohave);
							nosave.setVisibility(View.VISIBLE);
						}else{
							nosave.setBackgroundResource(R.drawable.nohave);
							nosave.setVisibility(View.VISIBLE);
						}
					}
					if(lv!=null  && vFooter != null && Xjfabulist.size()!=0){
						lv.removeFooterView(vFooter);
						}
					// Commond.showToast(Shangchuan.this, "亲，您还没有上传哦！");

					return;
				}

				currentdate = pd;
				String url1 = MainMenuActivity.shoucanglb;
				String url2 = dt.appendNameValue(url1, "pd", "");
				url2 = dt.appendNameValue(url2, "pkg",
						getPackageName());
				url2 = Details.geturl(url2);
				File picfile1 = new File(Appstart.jrcfile + "/"
						+ Adapterwithpic.getMd5Hash(url));
				String f = picfile1.getPath().toString();
				if (picfile1.exists()) {
					String ss = CacheData.load(f, Xjfabu.this);
					getjson(ss);
				}
				//
			}
		} catch (Exception e) {
			// TODO: handle exception
			pb.setVisibility(View.GONE);
			rllt6.setVisibility(View.GONE);
//			Commond.showToast(Xjfabu.this, "操作失败！请检查网络！");
			if(Xjfabulist!= null && Xjfabulist.size() != 0){
			if(lv!=null  && vFooter != null){
				loading.setVisibility(View.GONE);
				}
			}
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
//		if (Details.freshmysave == true) {
//			pb.setVisibility(View.VISIBLE);
//			rllt6.setVisibility(View.VISIBLE);  
//			String url = MainMenuActivity.shoucanglb;
//			String url2 = Details.geturl(url);
//			url2 = Details.appendNameValue(url2, "pd", currentdate);
//			StringBuffer data = new StringBuffer();
//			HttpRequestTask request = new HttpRequestTask();
//			request.execute(url2, data.toString());
//			Details.freshmysave = false;
//		}
		if(ad!= null){
			lv.requestLayout(); 
			ad.notifyDataSetChanged();
		}
		super.onResume();
	};

	public void post(String urllink) {
		String result = "";
		String url = urllink;
		StringBuffer data = new StringBuffer();
		// mostnewlv.setAdapter(new Adapterwithpic(Mostnew.this, items));
		data.append("pkg=");
		data.append(URLEncoder.encode(getPackageName()));
		//
		// data.append("&pd=");
		// lastdate = currentdate;
		// data.append(URLEncoder.encode(lastdate));
		// 请求网络验证登陆
		HttpRequestTask request = new HttpRequestTask();
		request.execute(url, data.toString());
	}

	public void getjson(String ss) {
		try {
			JSONObject jsonChannel2;
			pb.setVisibility(View.VISIBLE);
			rllt6.setVisibility(View.VISIBLE);
			jsonChannel2 = new JSONObject(ss);
			String tip = URLDecoder.decode(jsonChannel2.optString("tip"));
			String pd2 = URLDecoder.decode(jsonChannel2.optString("pd"));
			// sclbpd = pd ;
			String pd = URLDecoder.decode(jsonChannel2.optString("pd"));

			nlink = URLDecoder.decode(jsonChannel2.optString("nlink"));
			JSONArray jsonItems2 = jsonChannel2.optJSONArray("items");
			if (jsonItems2 != null && jsonItems2.length() > 0) {
				Xjfabulist.clear();
				currentdate = pd;
				// tip = "获取成功！";
				for (int i = 0; i < jsonItems2.length(); i++) {
					JSONObject jsonItem = jsonItems2.getJSONObject(i);
					String title = URLDecoder.decode(jsonItem
							.optString("title"));
					String link = URLDecoder.decode(jsonItem.optString("link"));

					String fcount = URLDecoder.decode(jsonItem
							.optString("fcount"));
					String date = URLDecoder.decode(jsonItem.optString("date"));
					String ccount = URLDecoder.decode(jsonItem
							.optString("ccount"));
					String dcount = URLDecoder.decode(jsonItem
							.optString("dcount"));
					String ucount = URLDecoder.decode(jsonItem
							.optString("ucount"));
					String thumbnail = URLDecoder.decode(jsonItem
							.optString("thumbnail"));

					int isfav = jsonItem.optInt("isfav");
					String flink = URLDecoder.decode(jsonItem
							.optString("flink"));
					String rlink = URLDecoder.decode(jsonItem
							.optString("rlink"));
					String clink = URLDecoder.decode(jsonItem
							.optString("clink"));
					Iteminfo item = new Iteminfo(title, date, thumbnail,
							ccount, dcount, ucount, fcount, link, flink, rlink,
							clink, isfav, -1, -1, null);
					// itemchoose1.add(item);
					Xjfabulist.add(item);
				}
				ad = new Adapterwithpic(Xjfabu.this, Xjfabulist,
						JianrencunActivity.number1, lv, false);
				if (twice == 0) {
					lv.setAdapter(ad);
					pb.setVisibility(View.GONE);
					rllt6.setVisibility(View.GONE);
					// fresh.setVisibility(View.GONE);
				} else if (twice == 3) {
					lv.requestLayout();
					ad.notifyDataSetChanged(); // 数据集变化后,通知adapte
				}
			} else {
				lv.requestLayout();
				ad.notifyDataSetChanged();
				pb.setVisibility(View.GONE);
				rllt6.setVisibility(View.GONE);
				if(lv!=null  && vFooter != null){
					lv.removeFooterView(vFooter);
					}
				// Commond.showToast(Shangchuan.this, "亲，您还没有上传哦！");
				if(Xjfabulist.size() == 0){
				nosave.setVisibility(View.VISIBLE);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			if (view.getLastVisiblePosition() == view.getCount() - 1) {
				twice = 3;
				loading.setVisibility(View.VISIBLE);			
				if (TextUtils.isEmpty(nlink)) {
					if(lv!=null  && vFooter != null){
						lv.removeFooterView(vFooter);
						return;
						}else{
							finish();
						}			
				}
				if (!dllinks.contains(nlink)) {
					File picfile = new File(Appstart.jrcfile + "/"
							+ Adapterwithpic.getMd5Hash(nlink));
					String filename = picfile.getPath().toString();

					post(nlink);
					dllinks.add(nlink);
				}
			}
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(Xjfabulist.size() != 0 || Xjfabulist != null){
		Xjfabulist.clear();
		Xjfabulist = null;
		}
		if(dllinks.size() != 0 || dllinks != null){
		dllinks.clear();
		dllinks = null ;
		}
		
		super.onDestroy();
	}
}
