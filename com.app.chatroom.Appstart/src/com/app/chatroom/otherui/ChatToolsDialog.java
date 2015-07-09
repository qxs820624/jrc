package com.app.chatroom.otherui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.app.chatroom.util.SystemUtil;
import com.jianrencun.chatroom.R;

public class ChatToolsDialog extends Activity {
	ImageButton userinfoBtn;
	ImageButton copyBtn;
	ImageButton friendBtn;
	String msg = "";
	String uid = "";
	String fuid = "";
	String type = "";
	LayoutInflater inflater;
	View view;
	TextView toastTextView;
	Toast toast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chatroom_tools_dialog);
		Intent intent = getIntent();
		msg = intent.getStringExtra("msg");
		uid = intent.getStringExtra("uid");
		fuid = intent.getStringExtra("fuid");
		type = intent.getStringExtra("type");
		userinfoBtn = (ImageButton) findViewById(R.id.chattool_userinfo_btn);
		copyBtn = (ImageButton) findViewById(R.id.chattool_copy_btn);
		friendBtn = (ImageButton) findViewById(R.id.chattool_friend_btn);
		// 自定义Toast
		inflater = this.getLayoutInflater();
		view = inflater.inflate(R.layout.toast, null);
		toastTextView = (TextView) view.findViewById(R.id.toast_textView);
		toast = new Toast(this);
		toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 80);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(view);

		if (!type.equals("1")) {
			copyBtn.setVisibility(View.GONE);
		}

		userinfoBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						VillageUserInfoDialog.class);
				// Log.i("ttt", uid);
				intent.putExtra("uid", uid);
				intent.putExtra("fuid", fuid);
				intent.putExtra("type", 1); // 判断是从聊天室打开，还是村委会
				startActivityForResult(intent, 1);
				finish();
			}
		});

		copyBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SystemUtil.copy(msg, getApplicationContext());
				toastTextView.setText("复制成功");
				toast.show();
				finish();
			}
		});
		friendBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				toastTextView.setText("举报成功");
				toast.show();
				finish();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == 1) {
			if (resultCode == 2020) {
				getIntent().putExtra("touid", data.getStringExtra("touid"));
				getIntent().putExtra("tonick", data.getStringExtra("tonick"));
				setResult(2020, getIntent());
				finish();
			}
			//判断从个人信息关闭，直接关闭Dialog
			if(resultCode == 31){
				finish();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}
}
