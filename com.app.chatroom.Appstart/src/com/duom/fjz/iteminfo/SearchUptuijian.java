package com.duom.fjz.iteminfo;

import java.net.URLDecoder;
import java.net.URLEncoder;

import org.json.JSONObject;

import com.jianrencun.chatroom.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.chatroom.Tuhaobang;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;
import com.jianrencun.android.Details;
import com.jianrencun.android.HttpBaseActivity;
import com.jianrencun.android.HttpBaseActivity.HttpRequestTask;

public class SearchUptuijian extends HttpBaseActivity{
       private TextView tvdesc , tvnumjinbi , tvnum2;
       private ImageButton jia , jian , sure ,cancle; 
       private int xx = 10;
       private String url ;
       private Details dt = new Details();
       private String tjd ;
       private int tjm ;
       private int kk ;
   	SharedPreferences sp;
   	SystemSettingUtilSp su;
   	private LinearLayout ll ;
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchuptuijian);
		
    	sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
		
		Intent it = getIntent() ; 
		tjd = it.getStringExtra("tjd");
		tjm = it.getIntExtra("tjm", 0);
		kk = tjm ;
		
		tvdesc = (TextView) findViewById(R.id.sf_tuijian_tv);
		tvnum2  = (TextView) findViewById(R.id.sf_tuijian_numjinbi2);
		tvnumjinbi = (TextView) findViewById(R.id.sf_tuijian_et);
		jia = (ImageButton) findViewById(R.id.sf_tuijian_jia);
	    jian = (ImageButton) findViewById(R.id.sf_tuijian_jian);
		sure = (ImageButton) findViewById(R.id.sf_tuijian_sure);
		cancle = (ImageButton)findViewById(R.id.sf_tuijian_cancle);
		ll = (LinearLayout) findViewById(R.id.uptuijianll);

        tvdesc.setText(tjd);
        tvnum2.setText(String.valueOf(tjm));
        tvnumjinbi.setText(String.valueOf(tjm));		
		
		jia.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			   tjm+=kk;
		        tvnumjinbi.setText(String.valueOf(tjm));
			}
		});
		
		jian.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(tjm<=kk){
					Commond.showToast(SearchUptuijian.this, "别点了！这么扣怎么上推荐榜？");
				}else{
					   tjm-=kk;
				       tvnumjinbi.setText(String.valueOf(tjm));
				}
			}
		});
		
		sure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				url = Details.appendNameValueint(ConstantsJrc.SEARCHUPTUIJIAN, "uid", Integer.parseInt(su.getUid()));
				url = Details.appendNameValueint(url, "num", Integer.parseInt(tvnumjinbi.getText().toString()));
				url = dt.appendNameValue(url, "token", URLEncoder.encode(su.getToken()));
//			    Commond.showToast(SearchUptuijian.this, Integer.parseInt(tvnumjinbi.getText().toString())+"");
				ll.setVisibility(View.VISIBLE);
				StringBuffer data = new StringBuffer();
				HttpRequestTask request = new HttpRequestTask();
				request.execute(url, data.toString());
			}
		});
		cancle.setOnClickListener(new OnClickListener() {
			
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
		ll.setVisibility(View.GONE);
		if (TextUtils.isEmpty(result)) {
			// fresh.setVisibility(View.VISIBLE);
			Commond.showToast(SearchUptuijian.this, "小贱提醒 ：当前网络不稳定！");
			return;
		}

		try {
			// //////////////////////////////////////////////正文内容页面
			JSONObject jsonChannel = new JSONObject(result);
			int ret = jsonChannel.optInt("ret");
			tip = URLDecoder.decode(jsonChannel.optString("tip"));
			if (ret == 0) {				
				Commond.showToast(SearchUptuijian.this, tip);
				return;
			}
			Commond.showToast(SearchUptuijian.this, tip);
			finish();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
}
