package com.jianrencun.dazui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.jianrencun.chatroom.R;

public class Makesure extends Activity{
	private Button msqueding , msquxiao ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.makesure);
		msqueding = (Button)findViewById(R.id.msqueding);
		msquxiao =  (Button)findViewById(R.id.msquxiao);
		msqueding.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 将最后的选择结果 ，通过设置setresult 向回传递结果
				Intent in = new Intent();
	 			setResult(13, in);
	 			Mydazui.jixu = false;
				// 关闭掉这个Activity
				finish();
			}
		});
		
		msquxiao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish() ;
			}
		});		
	}
}
