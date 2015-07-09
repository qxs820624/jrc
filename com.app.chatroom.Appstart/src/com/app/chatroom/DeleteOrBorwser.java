package com.app.chatroom;

import com.jianrencun.chatroom.R;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DeleteOrBorwser extends Activity{
	private TextView tv1, tv2 ,tv3 ,tv4;
	private Button close;
	private String tag ;
	private int po ;
	private String uid , fuid;
	private int picid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deleteorborwser);
		tv1 = (TextView) findViewById(R.id.dorb_delete);
		tv2 = (TextView) findViewById(R.id.dorb_borwser);
		tv4 = (TextView) findViewById(R.id.dorb_add);
		tv3 = (TextView) findViewById(R.id.dorb_photo);
		close = (Button) findViewById(R.id.dorb_close);
		
		Intent it = getIntent();
		tag = it.getStringExtra("tag");
		po = it.getIntExtra("po", 0);
		uid = it.getStringExtra("uid");
		fuid = it.getStringExtra("fuid");
		picid = it.getIntExtra("picid", 0);
		if(!uid.equals(fuid)){
			tv2.setVisibility(View.GONE);
		}
		
		tv1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 将最后的选择结果 ，通过设置setresult 向回传递结果
				Intent data = new Intent();
				data.putExtra("dorb", 33);
				data.putExtra("po", po);				
				setResult(51, data);
				// 关闭掉这个Activity
				finish();
			}
		});
		tv4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 将最后的选择结果 ，通过设置setresult 向回传递结果
				Intent data = new Intent();
				data.putExtra("index", 33);
				data.putExtra("po", po);
				data.putExtra("picid", picid);
				setResult(50, data);
				// 关闭掉这个Activity
				finish();
			}
		});
		tv3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 将最后的选择结果 ，通过设置setresult 向回传递结果
				Intent data = new Intent();
				data.putExtra("index", 44);
				data.putExtra("po", po);
				data.putExtra("picid", picid);
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
				data.putExtra("dorb", 44);
				data.putExtra("tag", tag);
				setResult(51, data);
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
