package com.duom.fjz.iteminfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.chatroom.util.SystemUtil;
import com.jianrencun.android.HttpBaseActivity;
import com.jianrencun.chatroom.R;

public class Tixing extends HttpBaseActivity{
	
	private ImageView iv ,iv2;
	private TextView tv ;
	private Button bnt ;
	int which , k= 0;
	private String tp ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tixing);
		
		Intent it = getIntent();
		which = it.getIntExtra("where", 0);
		tp = it.getStringExtra("tp");
		
		
		iv = (ImageView)findViewById(R.id.gexingtuw);
		iv2 = (ImageView)findViewById(R.id.gexingtuh);
		
		tv = (TextView)findViewById(R.id.wenzimiaoshu);
		bnt = (Button)findViewById(R.id.miaoshu_bnt);
		
        switch (which) {

		case 1:
			iv2.setVisibility(View.VISIBLE);
			iv2.setBackgroundResource(R.drawable.xiaoguaishou);
			break;
		case 2:
			iv.setVisibility(View.VISIBLE);
			iv.setBackgroundResource(R.drawable.huluxiongdi);
			break;
		case 3:
			iv.setVisibility(View.VISIBLE);
			iv.setBackgroundResource(R.drawable.xiaojianren);
			break;
		case 4:
			iv2.setVisibility(View.VISIBLE);
			iv2.setBackgroundResource(R.drawable.aotuman);
			break;
		case 5:
			iv2.setVisibility(View.VISIBLE);
			iv2.setBackgroundResource(R.drawable.baixuegongzhu);
			break;
		}
		tv.setText(tp);
		bnt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					finish();				
			}
		});		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			
		}
		return super.onKeyDown(keyCode, event);
	}


	@Override
	public void resultData(String url, String result) {
		// TODO Auto-generated method stub
		
	}
}
