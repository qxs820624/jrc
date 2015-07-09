package com.app.chatroom.otherui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.app.chatroom.ui.MailActivity;
import com.jianrencun.chatroom.R;

public class DeleteMailDialog extends Activity {
	Button okBtn;
	Button cancelBtn;
	TextView tipTextView;
	String tip = "";
	String ret = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message1_dialog);
		okBtn = (Button) findViewById(R.id.message1_ok_btn);
		cancelBtn = (Button) findViewById(R.id.message1_cancel_btn);
		tipTextView = (TextView) findViewById(R.id.message1_context_TextView);
		Intent intent = getIntent();
		tip = intent.getStringExtra("tip");
		ret = intent.getStringExtra("ret");
		tipTextView.setText(tip);

		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					if (ret.equals("0")) {
						// finish();
						MailActivity.instance.DeleteAllMail();
						finish();
					} else if (ret.equals("1")) {
						finish();
					} else if (ret.equals("2")) {
						finish();
					}
				} catch (Exception e) {

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
