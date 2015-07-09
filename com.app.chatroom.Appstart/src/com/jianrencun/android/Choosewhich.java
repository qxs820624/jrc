package com.jianrencun.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.jianrencun.chatroom.R;
import com.umeng.analytics.MobclickAgent;

public class Choosewhich extends Activity {
	private TextView tv1, tv2;
	private Button close;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choosewhich);
		tv1 = (TextView) findViewById(R.id.add);
		tv2 = (TextView) findViewById(R.id.photo);
		close = (Button) findViewById(R.id.close);
		
		Intent it = getIntent();
		String ss = it.getStringExtra("from");
		if(!TextUtils.isEmpty(ss) && ss.equalsIgnoreCase("jubao")){
			tv1.setBackgroundResource(R.drawable.savephoto1);
			tv2.setBackgroundResource(R.drawable.jubaophoto1);
		}
		tv1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 将最后的选择结果 ，通过设置setresult 向回传递结果
				Intent data = new Intent();
				data.putExtra("index", 33);
				setResult(50, data);
				// 关闭掉这个Activity
				finish();
			}
		});

		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		tv2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent data = new Intent();
				data.putExtra("index", 44);
				setResult(50, data);
				// 关闭掉这个Activity
				finish();
			}
		});

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
