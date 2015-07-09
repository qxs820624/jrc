package com.app.chatroom.otherui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.app.chatroom.json.bean.ChatMessageBean;
import com.app.chatroom.ui.ChatRoomActivity;
import com.app.chatroom.ui.DeliverData;
import com.jianrencun.chatroom.R;

public class Message1Dialog extends Activity {
	Button okBtn;
	Button cancelBtn;
	TextView tipTextView;
	String tip = "";
	String ret = "";
	String auth = "";
	String msg_c = "";
	String nick_c = "";
	int msg_b = 0;
	int msg_l = 0;
	int roomId = 0;
	String header = "";
	int type = 0;
	int qp = 0;
	ArrayList<ChatMessageBean> list = new ArrayList<ChatMessageBean>();

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
		auth = intent.getStringExtra("auth");
		msg_c = intent.getStringExtra("msg_c");
		msg_b = intent.getIntExtra("msg_b", 0);
		msg_l = intent.getIntExtra("msg_l", 0);
		nick_c = intent.getStringExtra("nick_c");
		roomId = intent.getIntExtra("roomid", 0);
		header = intent.getStringExtra("header");
		type = intent.getIntExtra("roomtype", 0);
		qp = intent.getIntExtra("qp", 0);
		DeliverData deld = (DeliverData) intent.getExtras().getSerializable(
				KEYGUARD_SERVICE);
		list = deld.getMsglist();

		tipTextView.setText(tip);
		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (ret.equals("2")) {
					Intent intent = new Intent(getApplicationContext(),
							ChatRoomActivity.class);
					intent.putExtra("auth", auth);
					intent.putExtra("msg_c", msg_c);
					intent.putExtra("msg_b", msg_b);
					intent.putExtra("msg_l", msg_l);
					intent.putExtra("nick_c", nick_c);
					intent.putExtra("roomid", roomId);
					intent.putExtra("header", header);
					intent.putExtra("roomtype", type);
					intent.putExtra("qp", qp);
					DeliverData deliverData2 = new DeliverData();
					deliverData2.setPersonList(list);
					Bundle bundle2 = new Bundle();
					bundle2.putSerializable(KEYGUARD_SERVICE, deliverData2);
					intent.putExtras(bundle2);
					startActivity(intent);
					finish();
				}
			}
		});

		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getIntent().putExtra("isenterroom", true);
				setResult(22, getIntent());
				finish();
			}
		});
	}
}
