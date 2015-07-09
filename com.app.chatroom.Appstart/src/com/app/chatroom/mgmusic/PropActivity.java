package com.app.chatroom.mgmusic;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.jianrencun.chatroom.R;

/**
 * 商城道具
 * @author Administrator
 *
 */
public class PropActivity extends Activity{
	ImageButton prop_close_btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prop_dialog);
		initView();
		initListener();
	}
	
	void initView() {
		prop_close_btn = (ImageButton) findViewById(R.id.prop_close_btn);

	}

	void initListener() {
		prop_close_btn.setOnClickListener(listener);
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.prop_close_btn:
				finish();
				break;

			default:
				break;
			}
		}
	};
}
