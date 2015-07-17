package com.app.chatroom.mgmusic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.app.chatroom.util.Commond;
import com.jianrencun.chatroom.R;

public class MonthMenuActivity extends Activity {
	Button month_menu_cancel_btn;
	Button month_menu_ok_btn;
	Button month_menu_info_btn;
	String serverid = "";
	String msg = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.month_menu);
		Intent intent = getIntent();
		serverid = intent.getStringExtra("serverid");
		msg = intent.getStringExtra("msg");
		initView();
		initListener();
	}

	void initView() {
		month_menu_cancel_btn = (Button) findViewById(R.id.month_menu_cancel_btn);
		month_menu_ok_btn = (Button) findViewById(R.id.month_menu_ok_btn);
		month_menu_info_btn = (Button) findViewById(R.id.month_menu_info_btn);
	}

	void initListener() {
		month_menu_cancel_btn.setOnClickListener(listener);
		month_menu_ok_btn.setOnClickListener(listener);
		month_menu_info_btn.setOnClickListener(listener);
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.month_menu_cancel_btn:
				finish();
				break;
			case R.id.month_menu_ok_btn:
				Intent intent = new Intent(getApplicationContext(),
						MonthOrderDialog.class);
				intent.putExtra("serverid", serverid);
				intent.putExtra("msg", msg);
				startActivity(intent);
				finish();
				break;
			case R.id.month_menu_info_btn:
				Commond.showToast(getApplicationContext(), msg);
				break;
			default:
				break;
			}
		}
	};
}
