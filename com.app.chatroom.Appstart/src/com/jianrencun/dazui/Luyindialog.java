package com.jianrencun.dazui;

import java.net.URLDecoder;
import java.net.URLEncoder;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.updata.FileUpload;
import com.app.chatroom.util.Commond;
import com.jianrencun.chatroom.R;

public class Luyindialog extends Activity {
	private EditText title;
	private Button surescbnt, canclesc;
	private int alen;
	private String scurl;
	private StringBuffer data;
	private ProgressBar pb;
	private LinearLayout ll;
	private String tip;
	private boolean dazuisc = true;
	private String path;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.luyindialog);

		Intent it = getIntent();
		alen = it.getIntExtra("lylen", 0);
		path = it.getStringExtra("path");

		title = (EditText) findViewById(R.id.dazuisctitle);
		surescbnt = (Button) findViewById(R.id.dazuisurescbnt);
		canclesc = (Button) findViewById(R.id.dazuicanclebnt);
		pb = (ProgressBar) findViewById(R.id.dazui_scdia_pb);
		ll = (LinearLayout) findViewById(R.id.dazui_scdia_ll);

		surescbnt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(title.getText().toString())) {
					Commond.showToast(Luyindialog.this, "请给录音起个名字！");
					return;
				}
				dazuisc = true;
				pb.setVisibility(View.VISIBLE);
				ll.setVisibility(View.VISIBLE);
				scurl = "http://jrc.hutudan.com/music/postitem.php";
				data = new StringBuffer();
				data.append("&uid=");
				data.append(MainMenuActivity.uid);
				data.append("&title=");
				data.append(URLEncoder.encode(title.getText().toString()));
				data.append("&alen=");
				data.append(alen);
				new Thread(new myThread()).start();
                v.setClickable(false);
			}
		});
		canclesc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			surescbnt.setClickable(true);
			switch (msg.what) {			
			case 0:
				ll.setVisibility(View.GONE);
				pb.setVisibility(View.GONE);
				Commond.showToast(Luyindialog.this, tip);
				break;
			case 1:
				ll.setVisibility(View.GONE);
				pb.setVisibility(View.GONE);
				Intent in = new Intent();
				in.putExtra("tip", tip);
				setResult(14, in);
				finish();
				break;
			}
			super.handleMessage(msg);
		}
	};

	class myThread implements Runnable {
		public void run() {
			while (dazuisc) {
				byte[] bt = FileUpload.getInstance().upload(scurl,
						data.toString(), "afile", path);
				if (bt == null) {

					Message message = new Message();
					message.what = 0;
					myHandler.sendMessage(message);
					dazuisc = false;
				} else {
					String ss = new String(bt);
					try {
						JSONObject json = new JSONObject(ss);

						// pd.cancel();
						int ret = json.optInt("ret", -1);
						tip = URLDecoder.decode(json.optString("tip"));
						dazuisc = false;
						Message message = new Message();
						message.what = 1;
						myHandler.sendMessage(message);

					} catch (Exception ex) {
						ex.printStackTrace();
						Message message = new Message();
						message.what = 0;
						myHandler.sendMessage(message);
						dazuisc = false;
					}
				}
			}
		}
	}
}
