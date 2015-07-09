package com.jianrencun.android;

import java.io.File;

import com.app.chatroom.Appstart;
import com.duom.fjz.iteminfo.Adapterwithpic;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.jianrencun.chatroom.R;

public class Exit extends Activity{
      private Button queding ,quxiao ;
      
      @Override
  	protected void onCreate(Bundle savedInstanceState) {
  		super.onCreate(savedInstanceState);
  		setContentView(R.layout.exit);
      queding = (Button)findViewById(R.id.queding);
      quxiao = (Button)findViewById(R.id.quxiao);
      
      
      queding.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent data = new Intent();
			data.putExtra("exit", 55);
			setResult(60, data);
			// 关闭掉这个Activity
			finish();	
			File picfile = new File(Appstart.jrcfile + "/" + "readlinks");
			String filename = picfile.getPath().toString();
			CacheData.save(filename, JianrencunActivity.number1.toString().getBytes(), Exit.this);
		}
	});
      quxiao.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
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
