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
	ImageButton month_ImageButton2;// 畅游包月
	ImageButton month_ImageButton3;// 畅游包月
	ImageButton month_ImageButton4;// 畅游包月
	ImageButton month_ImageButton5;// 畅游包月

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
		month_ImageButton2 = (ImageButton) findViewById(R.id.month_ImageButton2);
		month_ImageButton3 = (ImageButton) findViewById(R.id.month_ImageButton3);
		month_ImageButton4 = (ImageButton) findViewById(R.id.month_ImageButton4);
		month_ImageButton5 = (ImageButton) findViewById(R.id.month_ImageButton5);
	}

	void initListener() {
		mounth_close_btn.setOnClickListener(listener);
		month_ImageButton1.setOnClickListener(listener);
		month_ImageButton2.setOnClickListener(listener);
		month_ImageButton3.setOnClickListener(listener);
		month_ImageButton4.setOnClickListener(listener);
		month_ImageButton5.setOnClickListener(listener);
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
				IntentMenu("600967020000006608", "畅游4G包，6元/月",
						"http://www.366music.com/jme/jme4g6.json");
				break;
			case R.id.month_ImageButton2:
				IntentMenu("600967020000006040", "SuperWifi包，10元/月",
						"http://www.366music.com/jme/jmewifi10.json");
				break;
			case R.id.month_ImageButton3:
				IntentMenu("600967020000006029", "超会专享包，15元/月",
						"http://www.366music.com/jme/jmezx15.json");
				break;
			case R.id.month_ImageButton4:
				IntentMenu("600967020000006012", "5G空间包，20元/月",
						"http://www.366music.com/jme/jme5g20.json");
				break;
			case R.id.month_ImageButton5:
				IntentMenu("600967020000006625", "高速互联包，30元/月",
						"http://www.366music.com/jme/jmegshl30.json");
				break;

			default:
				break;
			}
		}
	};

	// 跳转到包月菜单
	public void IntentMenu(String serverid, String msg, String url) {
		Intent intent = new Intent(getApplicationContext(),
				MonthMenuActivity.class);
		intent.putExtra("serverid", serverid);
		intent.putExtra("msg", msg);
		intent.putExtra("url", url);
		startActivity(intent);
	}
}
