package com.jianrencun.game;

import java.net.URLDecoder;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;
import com.jianrencun.android.Details;
import com.jianrencun.android.HttpBaseActivity;
import com.jianrencun.android.HttpBaseActivity.HttpRequestTask;
import com.jianrencun.chatroom.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

public class Zhuanpan_help extends HttpBaseActivity{
	private WebView wv ;
	private ImageButton ib ;
	
	/* 配置文件 */
	SharedPreferences sp;
	SystemSettingUtilSp su;
	private Details dt = new Details();
      @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.zhuanpan_help);
    	
    	sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
    			MODE_WORLD_WRITEABLE);
    	su = new SystemSettingUtilSp(sp);
    	
    	wv = (WebView)findViewById(R.id.help_wv);
    	wv.setBackgroundColor(0); // 设置背景色  
    	ib = (ImageButton)findViewById(R.id.help_close);
    	
    	String url = dt.appendNameValue(ConstantsJrc.ZHUANPANHELP, "uid",su.getUid());
    	url = dt.appendNameValue(url, "token", URLEncoder.encode(su.getToken()));

    	StringBuffer data = new StringBuffer();
    	HttpRequestTask request = new HttpRequestTask();
    	request.execute(url, data.toString());
    	
    	ib.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			  finish();	
			}
		});
    }
	@Override
	public void resultData(String url, String result) {
		// TODO Auto-generated method stub
		String tip = null;
		if(result == null) {
			// fresh.setVisibility(View.VISIBLE);
			Commond.showToast(Zhuanpan_help.this, "小贱提醒 ：当前网络不稳定！");
			return;
		}

		try {
			// //////////////////////////////////////////////正文内容页面
			JSONObject jsonChannel = new JSONObject(result);
			int ret = jsonChannel.optInt("ret");
			tip = URLDecoder.decode(jsonChannel.optString("tip"));
			// tjd = URLDecoder.decode(jsonChannel.optString("tjd"));
			if (ret == 0) {
				Commond.showToast(Zhuanpan_help.this, tip);
				return;
			}		
			String desc  = URLDecoder.decode(jsonChannel.optString("desc"));
			wv.loadDataWithBaseURL(null, desc, "text/html", "UTF-8", null);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Commond.showToast(Zhuanpan_help.this, "解析失败！");
		}
	}
}
