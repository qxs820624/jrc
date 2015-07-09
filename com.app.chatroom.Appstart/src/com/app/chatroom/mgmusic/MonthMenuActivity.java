package com.app.chatroom.mgmusic;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.jianrencun.chatroom.R;

public class MonthMenuActivity extends Activity {
	Button month_menu_cancel_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.month_menu);
		initView();
		initListener();
	}

	void initView() {
		month_menu_cancel_btn = (Button) findViewById(R.id.month_menu_cancel_btn);
	}

	void initListener() {
		month_menu_cancel_btn.setOnClickListener(listener);
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.month_menu_cancel_btn:
				finish();
				break;

			default:
				break;
			}
		}
	};
}
