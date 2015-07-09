package com.app.chatroom;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

import com.app.chatroom.adapter.SearchAdapter;
import com.app.chatroom.adapter.Searchpeopleadapter;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;
import com.duom.fjz.iteminfo.Searchpeopleinfo;
import com.jianrencun.android.Details;
import com.jianrencun.android.HttpBaseActivity;
import com.jianrencun.android.HttpBaseActivity.HttpRequestTask;
import com.jianrencun.chatroom.R;

public class SearchPeople extends HttpBaseActivity implements OnScrollListener{
	private ImageButton back;
	private ListView lv ;
	private String url ;
	private Details dt = new Details();
	  private Button sexall , sexboy , sexgril , ageall , agedown , ageup;
		private int uid, sex;
		private String nick, header, nickclor, level;
		private LinearLayout ll ;
		SharedPreferences sp;
		SystemSettingUtilSp su;
		private Searchpeopleinfo sinfo;
		private Searchpeopleadapter sa;
		private List<Searchpeopleinfo> list ;
		private int sexnum ,agenum;
		private View vFooter;
		private LinearLayout loading;
		private String nlink ;
		private List<Integer> dlpl;
		private int page ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.searchpeople);
    	
    	sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
		dlpl = new ArrayList<Integer>();
    	back = (ImageButton)findViewById(R.id.searchpeople_left_btn);
    	lv = (ListView)findViewById(R.id.searchpeople_lv);
    	ll = (LinearLayout)findViewById(R.id.searchpeoplell);
    	list = new ArrayList<Searchpeopleinfo>();
    	
    	vFooter = LayoutInflater.from(this).inflate(R.layout.list_footer, null);
		loading = (LinearLayout) vFooter.findViewById(R.id.list_footer);
		loading.setVisibility(View.GONE);
    	
		lv.setOnScrollListener(this);
		lv.addFooterView(vFooter);
		lv.setFooterDividersEnabled(false);
		
    	View view = new View(getApplicationContext());
		view = getLayoutInflater().inflate(R.layout.searchpeople_head, null);
		sexall = (Button) view.findViewById(R.id.sh_head_all);
		sexboy = (Button) view.findViewById(R.id.sh_head_boy);
		sexgril = (Button) view.findViewById(R.id.sh_head_gril);
		sexall.setBackgroundResource(R.drawable.tj_leftpre);
		
		sexall.setTextColor(Color.parseColor("#ffffff"));
		sexboy.setTextColor(Color.parseColor("#000000"));
		sexgril.setTextColor(Color.parseColor("#000000"));

		
		sexall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				if(v.getBackground()){
//					sexall.setBackgroundResource(R.drawable.tj_leftpre);
//				}else if(){
//					sexall.setBackgroundResource(R.drawable.tj_left);
//				}
				if(sexnum!=0){
					sexnum = 0 ;
					list.clear();
					dlpl.clear();
					searhpeople(sexnum, agenum);					
				}
				sexall.setBackgroundResource(R.drawable.tj_leftpre);
				sexall.setTextColor(Color.parseColor("#ffffff"));
				sexboy.setTextColor(Color.parseColor("#000000"));
				sexgril.setTextColor(Color.parseColor("#000000"));
				sexboy.setBackgroundResource(R.drawable.tj_center);
				sexgril.setBackgroundResource(R.drawable.tj_right);								
				
			}
		});
		sexboy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(sexnum!=2){
					sexnum = 2 ;
					list.clear();
					dlpl.clear();
					searhpeople(sexnum, agenum);					
				}
				sexall.setBackgroundResource(R.drawable.tj_left);
				sexboy.setBackgroundResource(R.drawable.tj_centerpre);
				sexboy.setTextColor(Color.parseColor("#ffffff"));
				sexall.setTextColor(Color.parseColor("#000000"));
				sexgril.setTextColor(Color.parseColor("#000000"));
				sexgril.setBackgroundResource(R.drawable.tj_right);
			}
		});
		sexgril.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(sexnum!=1){
					sexnum = 1 ;
					dlpl.clear();
					list.clear();
					searhpeople(sexnum, agenum);					
				}
				sexall.setBackgroundResource(R.drawable.tj_left);
				sexboy.setBackgroundResource(R.drawable.tj_center);
				sexgril.setBackgroundResource(R.drawable.tj_rightpre);
				sexgril.setTextColor(Color.parseColor("#ffffff"));
				sexboy.setTextColor(Color.parseColor("#000000"));
				sexall.setTextColor(Color.parseColor("#000000"));				
			}
		});
		
		ageall = (Button)view.findViewById(R.id.sh_head_age_all);
    	agedown = (Button)view.findViewById(R.id.sh_head_xia);
    	ageup = (Button)view.findViewById(R.id.sh_head_shang);
    	
		ageall.setTextColor(Color.parseColor("#ffffff"));
		agedown.setTextColor(Color.parseColor("#000000"));
		ageup.setTextColor(Color.parseColor("#000000"));
    	ageall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				if(v.getBackground()){
//					sexall.setBackgroundResource(R.drawable.tj_leftpre);
//				}else if(){
//					sexall.setBackgroundResource(R.drawable.tj_left);
//				}
				if(agenum!=0){
					agenum = 0 ;
					list.clear();
					dlpl.clear();
					searhpeople(sexnum, agenum);					
				}
				ageall.setBackgroundResource(R.drawable.tj_leftpre);
				ageall.setTextColor(Color.parseColor("#ffffff"));
				agedown.setTextColor(Color.parseColor("#000000"));
				ageup.setTextColor(Color.parseColor("#000000"));
				agedown.setBackgroundResource(R.drawable.tj_center);
				ageup.setBackgroundResource(R.drawable.tj_right);
			}
		});
    	agedown.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(agenum!=1){
					agenum = 1 ;
					list.clear();
					dlpl.clear();
					searhpeople(sexnum, agenum);					
				}
				ageall.setBackgroundResource(R.drawable.tj_left);
				agedown.setBackgroundResource(R.drawable.tj_centerpre);
				agedown.setTextColor(Color.parseColor("#ffffff"));
				ageall.setTextColor(Color.parseColor("#000000"));
				ageup.setTextColor(Color.parseColor("#000000"));
				ageup.setBackgroundResource(R.drawable.tj_right);
			}
		});
    	ageup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(agenum!=2){
					agenum = 2 ;
					list.clear();
					dlpl.clear();
					searhpeople(sexnum, agenum);					
				}
				ageall.setBackgroundResource(R.drawable.tj_left);
				agedown.setBackgroundResource(R.drawable.tj_center);
				ageup.setBackgroundResource(R.drawable.tj_rightpre);
				ageup.setTextColor(Color.parseColor("#ffffff"));
				agedown.setTextColor(Color.parseColor("#000000"));
				ageall.setTextColor(Color.parseColor("#000000"));
			}
		});
    	
		lv.addHeaderView(view);
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
    	url = ConstantsJrc.SEARCHURL; 
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
		if (result == null) {
			// fresh.setVisibility(View.VISIBLE);
			Commond.showToast(SearchPeople.this, "小贱提醒 ：当前网络不稳定！");
			return;
		}

		try {		
			// //////////////////////////////////////////////正文内容页面
			JSONObject jsonChannel = new JSONObject(result);
			int ret = jsonChannel.optInt("ret");
			tip = URLDecoder.decode(jsonChannel.optString("tip"));
			if (ret == 0) {				
				Commond.showToast(SearchPeople.this, tip);
				return;
			}
			ll.setVisibility(View.GONE);
			JSONArray jsonItems = jsonChannel.optJSONArray("items");
			page = jsonChannel.optInt("page");			
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
				if(list.size() != 0 && !url.contains("page")){				
					sa = new Searchpeopleadapter(SearchPeople.this, list, lv , true);
					lv.setAdapter(sa);				
				}else{
					lv.requestLayout();
					sa.notifyDataSetChanged();
					loading.setVisibility(View.GONE);
				}							
			}		

		} catch (Exception e) {
			// TODO: handle exception
			// pb.setVisibility(View.GONE);
            // ll.setVisibility(View.GONE);
			Commond.showToast(SearchPeople.this, "操作失败！请检查网络！");
		}
	}
	
	private void searhpeople(int sexnum , int  agenum){
		url = ConstantsJrc.SEARCHURL; 
	    ll.setVisibility(View.VISIBLE);
		url = dt.appendNameValue(url, "uid", su.getUid());
		url = Details.appendNameValueint(url, "sex", sexnum);
		url = Details.appendNameValueint(url, "age", agenum);
		url = dt.appendNameValue(url, "token", URLEncoder.encode(su.getToken()));
		StringBuffer data1 = new StringBuffer();
		HttpRequestTask request = new HttpRequestTask();
		request.execute(url, data1.toString());
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
				if (page == 0 ) {
					lv.removeFooterView(vFooter);
					return;
				} else {					
					if (!dlpl.contains(page)) {
						loading.setVisibility(View.VISIBLE);
						String url = ConstantsJrc.SEARCHURL;
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
						url = Details.appendNameValueint(url, "sex", sexnum);
						url = Details.appendNameValueint(url, "age", agenum);
						url = dt.appendNameValue(url, "token", URLEncoder.encode(su.getToken()));
						HttpRequestTask request = new HttpRequestTask();
						request.execute(url, data.toString());
						dlpl.add(page);
					}
				}
			}
		}
	}
}
