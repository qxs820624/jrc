package com.app.chatroom.mgmusic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.jianrencun.chatroom.R;

/**
 * 包月
 * 
 * @author Administrator
 * 
 */
public class MonthlyActivity extends Activity {
	ImageButton mounth_close_btn;
	ImageButton month_ImageButton1;// 畅游包月

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mounth_dialog);
		initView();
		initListener();
	}

	void initView() {
		mounth_close_btn = (ImageButton) findViewById(R.id.mounth_close_btn);
		month_ImageButton1 = (ImageButton) findViewById(R.id.month_ImageButton1);
	}

	void initListener() {
		mounth_close_btn.setOnClickListener(listener);
		month_ImageButton1.setOnClickListener(listener);
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.mounth_close_btn:
				finish();
				break;
			case R.id.month_ImageButton1:
				IntentMenu();
				break;
			default:
				break;
			}
		}
	};

	// 跳转到包月菜单
	public void IntentMenu() {
		Intent intent = new Intent(getApplicationContext(),
				MonthMenuActivity.class);
		startActivity(intent);
	}
}
