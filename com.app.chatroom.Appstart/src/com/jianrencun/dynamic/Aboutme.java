package com.jianrencun.dynamic;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;
import com.jianrencun.android.Details;
import com.jianrencun.android.HttpBaseActivity;
import com.jianrencun.android.HttpBaseActivity.HttpRequestTask;
import com.jianrencun.chatroom.R;

public class Aboutme extends HttpBaseActivity implements OnScrollListener{

	private Details dt = new Details();
	/* 配置文件 */
	SharedPreferences sp;
	SystemSettingUtilSp su;
	private int pd, page;
	private LinearLayout ll;
	private Button back;
	private ListView lv;
	private Aboutmeinfo dyitem;
	 List<Aboutmeinfo> dylists = new ArrayList<Aboutmeinfo>() ;
	 List<String> dyphotolurl = new ArrayList<String>() ;
	 AboutmeAdapter ad ;
	 
		private View vFooter;
		private LinearLayout loading, dy_pb_ll;
		private List<Integer> dlpl = new ArrayList<Integer>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.aboutme);
		
		ll = (LinearLayout)findViewById(R.id.aboutme_pb_ll);
		back = (Button)findViewById(R.id.about_back);
		lv = (ListView)findViewById(R.id.about_lv);
		
		vFooter = LayoutInflater.from(this).inflate(R.layout.list_footer, null);
		loading = (LinearLayout) vFooter.findViewById(R.id.list_footer);
		loading.setVisibility(View.GONE);
		dy_pb_ll = (LinearLayout) findViewById(R.id.dy_pb_ll);
		
		lv.setOnScrollListener(this);
		lv.addFooterView(vFooter);
		lv.setFooterDividersEnabled(false);
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(Aboutme.this, Dydetatil.class);
				it.putExtra("id", dylists.get(arg2).getId());
				it.putExtra("flg", dylists.get(arg2).getAflg());
				it.putExtra("afile", dylists.get(arg2).getAfile());
				startActivity(it);
			}
		});

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);

		String url = dt.appendNameValue(ConstantsJrc.ABOUTME, "uid",
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
			Commond.showToast(Aboutme.this, "小贱提醒 ：当前网络不稳定！");
			return;
		}

		try {
			// //////////////////////////////////////////////正文内容页面
			JSONObject jsonChannel = new JSONObject(result);
			int ret = jsonChannel.optInt("ret");
			if (ret == 0) {
				tip = URLDecoder.decode(jsonChannel.optString("tip"));
				Commond.showToast(Aboutme.this, tip);
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
			
			 int id = jsonItem.optInt("id");
			 int flg = jsonItem.optInt("flg");
			 int uid = jsonItem.optInt("uid");
			 String nick = URLDecoder.decode(jsonItem
			 .optString("nick"));
			 String nick_c = URLDecoder.decode(jsonItem
			 .optString("nick_c"));
			 String header = URLDecoder.decode(jsonItem
			 .optString("header"));
			 int headerbj = jsonItem.optInt("uhflg");
			 int sex = jsonItem.optInt("sex");
			 String title = URLDecoder.decode(jsonItem
			 .optString("title"));
			 String desc = URLDecoder.decode(jsonItem
			 .optString("desc"));
			 String desc_c = URLDecoder.decode(jsonItem
			 .optString("desc_c"));
			 String afile = URLDecoder.decode(jsonItem
			 .optString("afile"));
			 String desc1 = URLDecoder.decode(jsonItem
			 .optString("desc1"));
			 int alen =jsonItem.optInt("alen");
			 String uptime = URLDecoder.decode(jsonItem.optString("desc1"));
			 String numdis = URLDecoder.decode(jsonItem.optString("desc2"));

			 dyitem = new Aboutmeinfo(id, uid, nick, header, desc1, afile, "", desc, desc_c, 0, alen, false, nick_c, flg, 0, "", 0 , title)		;	
			 dylists.add(dyitem);
			 }
			 if(dylists.size() != 0 && !url.contains("page")){
			 ad = new AboutmeAdapter(Aboutme.this, dylists, lv);
			 lv.setAdapter(ad);
			 ll.setVisibility(View.GONE);
			 }else{
			 lv.requestLayout();
			 ad.notifyDataSetChanged();
//			 loading.setVisibility(View.GONE);
			 }
			 }
		} catch (Exception e) {
			// TODO: handle exception
			// pb.setVisibility(View.GONE);
			Commond.showToast(Aboutme.this, "操作失败！请检查网络！");
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
						StringBuffer data = new StringBuffer();

						data.append("pkg=");
						data.append(URLEncoder.encode(getPackageName()));
						//
						// data.append("&pd=");
						// lastdate = currentdate;
						// data.append(URLEncoder.encode(lastdate));
						// 请求网络验证登陆
						String url = dt.appendNameValue(ConstantsJrc.ABOUTME, "uid",
								su.getUid());
						url = dt.appendNameValue(url, "token", URLEncoder.encode(su.getToken()));
						url = dt.appendNameValueint(url, "pd", pd);
						url = Details.appendNameValueint(url, "page", page);						
						HttpRequestTask request = new HttpRequestTask();
						request.execute(url, data.toString());
						dlpl.add(page);
					}
				}
			}
		}
	}
}
