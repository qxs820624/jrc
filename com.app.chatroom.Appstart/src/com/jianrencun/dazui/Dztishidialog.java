package com.jianrencun.dazui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.jianrencun.chatroom.R;

public class Dztishidialog extends Activity{
      private Button queren , quxiao ;
      private TextView tv ;
      private int wh ;
      
      @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.dztishidialog);
    	
    	Intent it = getIntent() ;
    	String text = it.getStringExtra("dzts");
    	 wh = it.getIntExtra("dzwhich", 11);
    	
    	queren = (Button)findViewById(R.id.dztssure);
    	quxiao = (Button)findViewById(R.id.dztsguanbi);
    	tv = (TextView)findViewById(R.id.dztscontent);
    	tv.setText(text);

    	quxiao.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			finish() ;
    		}
    	});   	
    	
    	queren.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			
    			Intent in = new Intent();  		
    			if(wh == 0){
	 			setResult(16, in);
    			}else if(wh == 1){
    			setResult(17, in);	
    			}
    			finish() ;
    		}
    	});
    }
}
