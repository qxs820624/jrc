package com.app.chatroom.otherui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.app.chatroom.download.APKDownload;
import com.jianrencun.chatroom.R;

public class MessageDownDialog extends Activity {
	Button okBtn;
	Button cancelBtn;
	TextView tipTextView;
	String tip = "";
	String ret = "";
	String vname = "";
	String vsize = "";
	String vurl = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_down_dialog);
		okBtn = (Button) findViewById(R.id.message_down_ok_btn);
		cancelBtn = (Button) findViewById(R.id.message_down_cancel_btn);
		tipTextView = (TextView) findViewById(R.id.message_down_context_TextView);
		Intent intent = getIntent();
		tip = intent.getStringExtra("tip");
		ret = intent.getStringExtra("ret");
		vname = intent.getStringExtra("vname");
		vsize = intent.getStringExtra("vsize");
		vurl = intent.getStringExtra("vurl");
		tipTextView.setText(tip);

		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (ret.equals("2")) {
					APKDownload.startDownThread(getApplicationContext(), vname,
							vurl, Integer.parseInt(vsize));
					finish();
				}
			}
		});

		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
