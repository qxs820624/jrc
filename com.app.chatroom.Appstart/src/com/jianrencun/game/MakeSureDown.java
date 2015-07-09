package com.jianrencun.game;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.chatroom.MakesureClear;
import com.app.chatroom.MakesureClear.MyThread;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.util.Commond;
import com.jianrencun.chatroom.R;

public class MakeSureDown extends Activity {
	private Button msqueding , msquxiao ;
	private TextView tv ;
	private String ss ;
	private Handler mHandler = null;  
	private boolean run = true ;
	private LinearLayout ll ;
	private long yy , xx;
	private boolean flag;
	String dlink ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sureclear);
		msqueding = (Button)findViewById(R.id.clear_msqueding);
		msquxiao =  (Button)findViewById(R.id.clear_msquxiao);
		ll = (LinearLayout)findViewById(R.id.clear_ll);
		tv = (TextView)findViewById(R.id.clear_mstt);
        Intent it = getIntent() ;
        dlink = it.getStringExtra("dlink");
		tv.setText("亲，挑战他？现在下载消流弊？");				        
		msqueding.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 将最后的选择结果 ，通过设置setresult 向回传递结果
				Uri uri = Uri.parse(dlink);//id为包名 
                Intent it = new Intent(Intent.ACTION_VIEW, uri); 
                startActivity(it); 
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
