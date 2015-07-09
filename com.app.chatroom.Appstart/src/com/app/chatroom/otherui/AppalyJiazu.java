package com.app.chatroom.otherui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jianrencun.android.HttpBaseActivity;
import com.jianrencun.chatroom.R;

public class AppalyJiazu extends HttpBaseActivity{
	private Button back , apply ;
	private TextView tv_desc ;
	private EditText tv_jzname , tv_roomname;
	private ImageView change;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.applayjiazu);
		
		back = (Button)findViewById(R.id.apply_close_btn);
		apply = (Button)findViewById(R.id.jz_makesure);
		tv_desc= (TextView)findViewById(R.id.tv_des);
		tv_jzname = (EditText)findViewById(R.id.et_jiazuname);
		tv_roomname = (EditText)findViewById(R.id.et_roomname);
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		change.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});		
	}

	@Override
	public void resultData(String url, String result) {
		// TODO Auto-generated method stub
		
	}
}
