package com.jianrencun.game;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;
import com.duom.fjz.iteminfo.BitmapCacheDzlb;
import com.jianrencun.android.Details;
import com.jianrencun.android.HttpBaseActivity;
import com.jianrencun.android.HttpBaseActivity.HttpRequestTask;
import com.jianrencun.chatroom.R;

public class ZhuanpanPhb extends HttpBaseActivity {
	private Button close ;
	private ListView lv ;
	private  LinearLayout ll ;
	
	/* 配置文件 */
	SharedPreferences sp;
	SystemSettingUtilSp su;
	private Details dt = new Details();
	
	GamebigInfo biginfo ; 
	List<GamebigInfo> biginfos = new ArrayList<GamebigInfo>();	
	GamePaihangbangAdapter  ga ;
	
	private int model ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {				
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.paihangbang);
		close = (Button)findViewById(R.id.zp_phb_close);
		lv = (ListView)findViewById(R.id.zp_paihangbang_lv);
		ll = (LinearLayout)findViewById(R.id.zp_phb_ll);
		
		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
		
		Intent it = getIntent();
		model = it.getIntExtra("mode_flg", 0);
		
		close.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish() ;
			}
		});
		
		String url = dt.appendNameValue(ConstantsJrc.ZHUANPANPHB, "uid",
				su.getUid());
		url = dt.appendNameValue(url, "token", URLEncoder.encode(su.getToken()));
		url = Details.appendNameValueint(url, "mode_flg", model);

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
			Commond.showToast(ZhuanpanPhb.this, "小贱提醒 ：当前网络不稳定！");
			return;
		}

		try {
			ll.setVisibility(View.GONE);
			// //////////////////////////////////////////////正文内容页面
			JSONObject jsonChannel = new JSONObject(result);
			int ret = jsonChannel.optInt("ret");
			tip = URLDecoder.decode(jsonChannel.optString("tip"));
			// tjd = URLDecoder.decode(jsonChannel.optString("tjd"));
			if (ret == 0) {
				Commond.showToast(ZhuanpanPhb.this, tip);
				return;
			}
			
			JSONArray jsonItems = jsonChannel.optJSONArray("tops");
			if (jsonItems != null) {
				// tip = "获取成功！";
				for (int i = 0; i < jsonItems.length(); i++) {
					JSONObject jsonItem = jsonItems.getJSONObject(i);
					int  phflg = jsonItem.optInt("ret");
					String title = URLDecoder.decode(jsonItem
							.optString("title"));	
					biginfo = new GamebigInfo(phflg, title, 0, "", "", "", "", "" , "" , 1 , 0);
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
									.optString("money"));
							String num = URLDecoder.decode(jsonpic
									.optString("num"));
							String ord = URLDecoder.decode(jsonpic
									.optString("ord"));

							biginfo = new GamebigInfo(phflg, title, uid, nick, nick_c, score, ord, header , num ,1 , 0);
							biginfos.add(biginfo);
						}
					}					
				}
			}		
			ga = new GamePaihangbangAdapter(ZhuanpanPhb.this, biginfos, lv , 1 , ZhuanpanPhb.this);
			lv.setAdapter(ga);		

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Commond.showToast(ZhuanpanPhb.this, "解析失败！");
		}
	}	
}
