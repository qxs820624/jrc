package com.jianrencun.android;

import java.net.URLDecoder;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.util.Commond;
import com.jianrencun.chatroom.R;
import com.umeng.analytics.MobclickAgent;
import com.weibo.joechan.util.TextUtil;

public class Chakanpinglun extends HttpBaseActivity{
	private  TextView chakantv , chakantitle; 
	private  Button jubao , guanbi ;
	String tip ;
	int from ;
	private String jubaolink;
	int mid ;
	private Details dt= new Details(); 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chakanpinglun);
		
		chakantv = (TextView)findViewById(R.id.chakancontent);
		chakantv.setMovementMethod(ScrollingMovementMethod.getInstance());  
		jubao = (Button)findViewById(R.id.chakanjubao);
		guanbi = (Button)findViewById(R.id.chakanguanbi);
		chakantitle = (TextView)findViewById(R.id.chakantitle);
		
		Intent it = getIntent() ; 
		String content = it.getStringExtra("content");
		String title = it.getStringExtra("title");
		 from = it.getIntExtra("from", 1);
		 mid = it.getIntExtra("mid" , 3);

		 jubaolink = it.getStringExtra("jubaolink");		
		chakantv.setText(content); 
		chakantitle.setText(title);
		guanbi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				// TODO Auto-generated method stub
				finish() ;
			}
		});
		
		jubao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(from == 1){
				// TODO Auto-generated method stub   
				StringBuffer data = new StringBuffer();
				String url = dt.appendNameValue(jubaolink, "pkg", getPackageName());
				url = dt.appendNameValue(url, "uid", MainMenuActivity.uid);
				// 请求网络验证登陆
				HttpRequestTask request = new HttpRequestTask();
				request.execute(url, data.toString());
				}else if(from == 0){
				String url = "http://jrc.hutudan.com/music/report.php";
				StringBuffer data = new StringBuffer();
				 url = dt.appendNameValue(url, "uid", MainMenuActivity.uid);
				 url = Details.appendNameValueint(url, "cid", mid);				 
				// 请求网络验证登陆
				HttpRequestTask request = new HttpRequestTask();
				request.execute(url, data.toString());
				}
			}
		});
	}
	@Override
	public void resultData(String url,String result) {
		// TODO Auto-generated method stub

		if (result == null) {
			Commond.showToast(this, "获取数据失败！"+tip);
			return;
		}
    
		try {
			JSONObject jsonChannel = new JSONObject(result);
			int ret = jsonChannel.optInt("ret");
			tip = URLDecoder.decode(jsonChannel.optString("tip"),"UTF-8");
				Commond.showToast(this, tip);			
				finish() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
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
}
