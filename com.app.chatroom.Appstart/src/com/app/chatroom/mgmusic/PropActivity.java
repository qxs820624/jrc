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
	ImageButton ImageButton2;// 2元
	ImageButton ImageButton3;// 3元
	ImageButton ImageButton4;// 4元
	ImageButton ImageButton5;// 5元
	ImageButton ImageButton6;// 6元
	ImageButton ImageButton7;// 8元
	ImageButton ImageButton8;// 10元
	ImageButton ImageButton9;// 12元
	ImageButton ImageButton10;// 15元
	ImageButton ImageButton11;// 20元
	ImageButton ImageButton12;// 30元

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
		ImageButton2 = (ImageButton) findViewById(R.id.ImageButton2);
		ImageButton3 = (ImageButton) findViewById(R.id.ImageButton3);
		ImageButton4 = (ImageButton) findViewById(R.id.ImageButton4);
		ImageButton5 = (ImageButton) findViewById(R.id.ImageButton5);
		ImageButton6 = (ImageButton) findViewById(R.id.ImageButton6);
		ImageButton7 = (ImageButton) findViewById(R.id.ImageButton7);
		ImageButton8 = (ImageButton) findViewById(R.id.ImageButton8);
		ImageButton9 = (ImageButton) findViewById(R.id.ImageButton9);
		ImageButton10 = (ImageButton) findViewById(R.id.ImageButton10);
		ImageButton11= (ImageButton) findViewById(R.id.ImageButton11);
		ImageButton12 = (ImageButton) findViewById(R.id.ImageButton12);
	}

	void initListener() {
		prop_close_btn.setOnClickListener(listener);
		ImageButton1.setOnClickListener(listener);
		ImageButton2.setOnClickListener(listener);
		ImageButton3.setOnClickListener(listener);
		ImageButton4.setOnClickListener(listener);
		ImageButton5.setOnClickListener(listener);
		ImageButton6.setOnClickListener(listener);
		ImageButton7.setOnClickListener(listener);
		ImageButton8.setOnClickListener(listener);
		ImageButton9.setOnClickListener(listener);
		ImageButton10.setOnClickListener(listener);
		ImageButton11.setOnClickListener(listener);
		ImageButton12.setOnClickListener(listener);
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
			case R.id.ImageButton2:
				prop("600893Z01000100001");
				break;
			case R.id.ImageButton3:
				prop("600893Z01000100001");
				break;
			case R.id.ImageButton4:
				prop("600893Z01000100001");
				break;
			case R.id.ImageButton5:
				prop("600893Z01000100001");
				break;
			case R.id.ImageButton6:
				prop("600893Z01000100001");
				break;
			case R.id.ImageButton7:
				prop("600893Z01000100001");
				break;

			case R.id.ImageButton8:
				prop("600893Z01000100001");
				break;
			case R.id.ImageButton9:
				prop("600893Z01000100001");
				break;

			case R.id.ImageButton10:
				prop("600893Z01000100001");
				break;
			case R.id.ImageButton11:
				prop("600893Z01000100001");
				break;
			case R.id.ImageButton12:
				prop("600893Z01000100001");
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 计次订购
	 * 
	 * @param serviceId
	 */
	public void prop(String serviceId) {
		ExclusiveManagerInterface.exclusiveOnceByNet(getApplicationContext(),
				serviceId, new CMMusicCallback<Result>() {
					@Override
					public void operationResult(Result result) {
						if (null != result) {
							Commond.showToast(getApplicationContext(),
									result.getResMsg());
						}

					}
				});
	}
}
