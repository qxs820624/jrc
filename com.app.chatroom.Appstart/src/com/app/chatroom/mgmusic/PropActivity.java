package com.app.chatroom.mgmusic;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.app.chatroom.util.Commond;
import com.cmsc.cmmusic.common.CMMusicCallback;
import com.cmsc.cmmusic.common.ExclusiveManagerInterface;
import com.cmsc.cmmusic.common.data.Result;
import com.jianrencun.chatroom.R;

/**
 * 商城道具
 * 
 * @author Administrator
 * 
 */
public class PropActivity extends Activity {
	ImageButton prop_close_btn;
	ImageButton ImageButton1;// 0.01元

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
		ImageButton1 = (ImageButton) findViewById(R.id.ImageButton1);
	}

	void initListener() {
		prop_close_btn.setOnClickListener(listener);
		ImageButton1.setOnClickListener(listener);
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.prop_close_btn:
				finish();
				break;
			case R.id.ImageButton1:
				prop("600893Z01000100001");
				break;

			default:
				break;
			}
		}
	};

	/**
	 * 计次订购
	 * @param serviceId
	 */
	public void prop(String serviceId) {
		ExclusiveManagerInterface.exclusiveOnceByNet(getApplicationContext(),
				serviceId, new CMMusicCallback<Result>() {
					@Override
					public void operationResult(Result result) {
						if (null != result) {
						 Commond.showToast(getApplicationContext(), result.getResMsg());
						}

					}
				});
	}
}
