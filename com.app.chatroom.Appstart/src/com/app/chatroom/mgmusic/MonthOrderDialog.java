package com.app.chatroom.mgmusic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.app.chatroom.util.Commond;
import com.cmsc.cmmusic.common.CMMusicCallback;
import com.cmsc.cmmusic.common.CPManagerInterface;
import com.cmsc.cmmusic.common.data.Result;
import com.jianrencun.chatroom.R;

public class MonthOrderDialog extends Activity {
	TextView message1_title_TextView;
	TextView message1_context_TextView;
	Button message1_ok_btn;
	Button message1_cancel_btn;
	String serverid = "";
	String msg = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message1_dialog);
		initView();
		initListener();
		Intent intent = getIntent();
		serverid = intent.getStringExtra("serverid");
		msg = intent.getStringExtra("msg");
		message1_context_TextView.setText(msg);
	}

	void initView() {
		message1_title_TextView = (TextView) findViewById(R.id.message1_title_TextView);
		message1_context_TextView = (TextView) findViewById(R.id.message1_context_TextView);
		message1_ok_btn = (Button) findViewById(R.id.message1_ok_btn);
		message1_cancel_btn = (Button) findViewById(R.id.message1_cancel_btn);
	}

	void initListener() {
		message1_ok_btn.setOnClickListener(listener);
		message1_cancel_btn.setOnClickListener(listener);
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.message1_cancel_btn:
				finish();
				break;
			case R.id.message1_ok_btn:

				CPManagerInterface.openCPMonthByNet(getApplicationContext(),
						serverid, false, new CMMusicCallback<Result>() {

							@Override
							public void operationResult(Result arg0) {
								// TODO Auto-generated method stub
								if (null != arg0) {
									Commond.showToast(getApplicationContext(),
											arg0.getResMsg());
								}
							}
						});
				finish();
				break;
			default:
				break;
			}
		}
	};
}
