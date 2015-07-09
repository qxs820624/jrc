package com.app.chatroom;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.jianrencun.chatroom.R;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.app.chatroom.adapter.SearchAdapter;
import com.app.chatroom.adapter.VillageAdapter;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.otherui.VillageUserInfoDialog;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;
import com.duom.fjz.iteminfo.SearchUptuijian;
import com.duom.fjz.iteminfo.Searchpeopleinfo;
import com.duom.fjz.iteminfo.Searchtiaojian;
import com.jianrencun.android.Details;
import com.jianrencun.android.HttpBaseActivity;
import com.jianrencun.dazui.Dazuidetatil;

public class Tuhaobang extends HttpBaseActivity{
	private ImageButton back ,dibusousuo  ;
	private ListView lv ;
	private ImageView iv ;
	private SearchAdapter sa;
	private Searchpeopleinfo sinfo;
	private Details dt = new Details();
	private String url ;
	private List<Searchpeopleinfo> list ;
	private int uid, sex;
	private String nick, header, nickclor, level;
	private LinearLayout ll ;
	private String tjd ;
	private int tjm ;
	private boolean xianshi = true;
	private String ss;
	private ImageView iv1 , iv2, iv3 , sexnum1 , sexnum2 , sexnum3 ;
	SharedPreferences sp;
	SystemSettingUtilSp su;
	private TextView tv1 ,tv2 ,tv3 ;
       @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.searchfriend2);
    	
    	sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
    	
    	back = (ImageButton)findViewById(R.id.sf_close_btn);
    	dibusousuo = (ImageButton)findViewById(R.id.sf_dibubnt);
    	ll = (LinearLayout)findViewById(R.id.searchpeoplell);
    	
    	lv = (ListView)findViewById(R.id.sf_gv);
    	iv = (ImageView)findViewById(R.id.sf_tuijian_tv);
    	      	
//    	lv.setSelector(new ColorDrawable(Color.TRANSPARENT));
    	list = new ArrayList<Searchpeopleinfo>();
    	
    	View view = new View(getApplicationContext());
		view = getLayoutInflater().inflate(R.layout.tuijian_head, null);
		iv1 = (ImageView) view.findViewById(R.id.tuijian_iv1);
		iv2 = (ImageView) view.findViewById(R.id.tuijian_iv2);
		iv3 = (ImageView) view.findViewById(R.id.tuijian_iv3);
		
		tv1 = (TextView)view.findViewById(R.id.tv_num1);
    	tv2 = (TextView)view.findViewById(R.id.tv_num2);
    	tv3 = (TextView)view.findViewById(R.id.tv_num3);
    	sexnum1= (ImageView)view.findViewById(R.id.tuijian_sex_num1);
    	sexnum2= (ImageView)view.findViewById(R.id.tuijian_sex_num2);
    	sexnum3= (ImageView)view.findViewById(R.id.tuijian_sex_num3);
    	
		lv.addHeaderView(view);
//    	lv.setAdapter(sa);
    	
    	back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
//    	searchbnt.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//		    	url = ConstantsJrc.SEARCHURL; 
//				xianshi = false;
//				ss = et.getText().toString();	
//				if(TextUtils.isEmpty(ss)){
//					Commond.showToast(Searchfriend.this, "请输入内容，在搜索！谢谢合作！");
//					return ;
//				}
//				ss = URLEncoder.encode(ss);
//				ll.setVisibility(View.VISIBLE);
//				
//				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//				inputMethodManager.hideSoftInputFromWindow(
//						et.getWindowToken(), 0);
//				
//				url = dt.appendNameValue(url, "uid",su.getUid());
//				url =  dt.appendNameValue(url, "key", ss);
//				iv.setImageResource(R.drawable.sousuojieguo);
//				StringBuffer data = new StringBuffer();
//				HttpRequestTask request = new HttpRequestTask();
//				request.execute(url, data.toString());
//			}
//		});
    	dibusousuo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(Tuhaobang.this, SearchUptuijian.class);
				it.putExtra("tjd", tjd);
				it.putExtra("tjm", tjm);
				startActivityForResult(it, 1);
			}
		});
//    	tuiyijian.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent it = new Intent();
//				it.setClass(Searchfriend.this, SearchUptuijian.class);
//				it.putExtra("tjd", tjd);
//				it.putExtra("tjm", tjm);
//				startActivityForResult(it, 1);
//			}
//		});
//    	change.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
////		    	url = ConstantsJrc.SEARCHURL; 
////				url = dt.appendNameValue(url, "uid", su.getUid());
////				
////				StringBuffer data = new StringBuffer();
////				HttpRequestTask request = new HttpRequestTask();
////				request.execute(url, data.toString());
//			}
//		});	
//    	gv.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(getApplicationContext(),
//						VillageUserInfoDialog.class);
//
//				intent.putExtra("uid",
//						String.valueOf(list.get(arg2).getUid()));
//				intent.putExtra("nick", list.get(arg2).getNick());
//				intent.putExtra("fuid", MainMenuActivity.uid);
//				intent.putExtra("type", 2);
//				startActivity(intent);		
//			}
//		});
    	url = ConstantsJrc.MONEYUSER; 
		url = dt.appendNameValue(url, "uid", su.getUid());
		url = dt.appendNameValue(url, "token", URLEncoder.encode(su.getToken()));
		StringBuffer data = new StringBuffer();
		HttpRequestTask request = new HttpRequestTask();
		request.execute(url, data.toString());
    }

	@Override
	public void resultData(String url, String result) {
		// TODO Auto-generated method stub
		String tip = null;
		list.clear();
		if (result == null) {
			// fresh.setVisibility(View.VISIBLE);
			Commond.showToast(Tuhaobang.this, "小贱提醒 ：当前网络不稳定！");
			return;
		}

		try {		
			// //////////////////////////////////////////////正文内容页面
			JSONObject jsonChannel = new JSONObject(result);
			int ret = jsonChannel.optInt("ret");
			tip = URLDecoder.decode(jsonChannel.optString("tip"));
			tjd = URLDecoder.decode(jsonChannel.optString("tjd"));
			tjm =jsonChannel.optInt("tjm");
			if (ret == 0) {				
				Commond.showToast(Tuhaobang.this, tip);
				return;
			}
			ll.setVisibility(View.GONE);
			JSONArray jsonItems = jsonChannel.optJSONArray("items");
			if (jsonItems != null) {
				// tip = "获取成功！";
				for (int i = 0; i < jsonItems.length(); i++) {
					JSONObject jsonItem = jsonItems.getJSONObject(i);
					 uid = jsonItem.optInt("uid");
					 sex = jsonItem.optInt("sex");				
					 nick = URLDecoder.decode(jsonItem
							.optString("nick"));
					 header = URLDecoder.decode(jsonItem
								.optString("header"));
					 nickclor = URLDecoder.decode(jsonItem
								.optString("nick_c"));
					 
					 level = URLDecoder.decode(jsonItem
								.optString("level"));			
					sinfo = new Searchpeopleinfo(uid, sex, nick, header, nickclor, level);
					list.add(sinfo);	
				}
				if(list.size() != 0){				

					sa = new SearchAdapter(Tuhaobang.this, list, lv , xianshi,iv1 ,iv2 ,iv3 ,tv1 , tv2 , tv3 , sexnum1 , sexnum2 , sexnum3);
					lv.setAdapter(sa);				
				}
			}		

		} catch (Exception e) {
			// TODO: handle exception
			// pb.setVisibility(View.GONE);
            // ll.setVisibility(View.GONE);
			Commond.showToast(Tuhaobang.this, "操作失败！请检查网络！");
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		 if(resultCode == 20){
		    	url = ConstantsJrc.MONEYUSER; 
		    	iv.setImageResource(R.drawable.sousuojieguo);
			 xianshi = false;
			 ll.setVisibility(View.VISIBLE);
			 int sexnum = data.getIntExtra("sexnum", 0);
			 int hournum = data.getIntExtra("hournum", 0);
			 int agenum = data.getIntExtra("agenum", 0);
				url = dt.appendNameValue(url, "uid", su.getUid());
				url = Details.appendNameValueint(url, "sex", sexnum);
				url = Details.appendNameValueint(url, "online", hournum);
				url = Details.appendNameValueint(url, "age", agenum);
				url = dt.appendNameValue(url, "token", URLEncoder.encode(su.getToken()));
				StringBuffer data1 = new StringBuffer();
				HttpRequestTask request = new HttpRequestTask();
				request.execute(url, data1.toString());
	         }	
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		list.clear();
		super.onDestroy();
	}
}
