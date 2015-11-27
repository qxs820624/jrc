package com.app.chatroom.mgmusic;

import java.util.ArrayList;
import java.util.Random;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.json.MessageJson;
import com.app.chatroom.json.bean.MessageBean;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.ui.HttpBaseActivitytwo;
import com.app.chatroom.util.Commond;
import com.app.chatroom.util.PhoneInfo;
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
public class PropActivity extends HttpBaseActivitytwo {
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
	SharedPreferences sp;
	SystemSettingUtilSp su;
	int coins = 0;
	float paymoney = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prop_dialog);
		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
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
		ImageButton11 = (ImageButton) findViewById(R.id.ImageButton11);
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
				coins = 100;
				paymoney = 0.1f;
				prop("600855Z01000100001");
				break;
			case R.id.ImageButton2:
				coins = 1000;
				paymoney = 2.0f;
				prop("633539Z01000100001");
				break;
			case R.id.ImageButton3:
				coins = 1500;
				paymoney = 3.0f;
				prop("635736Z01000100001");
				break;
			case R.id.ImageButton4:
				coins = 2000;
				paymoney = 4.0f;
				prop("633886Z01000100001");
				break;
			case R.id.ImageButton5:
				coins = 2500;
				paymoney = 5.0f;
				prop("600813Z01000100001");
				break;
			case R.id.ImageButton6:
				coins = 3000;
				paymoney = 6.0f;
				prop("635736Z01000100002");
				break;
			case R.id.ImageButton7:
				coins = 4000;
				paymoney = 8.0f;
				prop("633539Z01000100002");
				break;

			case R.id.ImageButton8:
				coins = 5000;
				paymoney = 10.0f;
				prop("635736Z01000100003");
				break;
			case R.id.ImageButton9:
				coins = 6000;
				paymoney = 12.0f;
				prop("600855Z01000100002");
				break;

			case R.id.ImageButton10:
				coins = 7500;
				paymoney = 15.0f;
				prop("633886Z01000100002");
				break;
			case R.id.ImageButton11:
				coins = 10000;
				paymoney = 20.0f;
				prop("600813Z01000100002");
				break;
			case R.id.ImageButton12:
				coins = 15000;
				paymoney = 30.0f;
				prop("633886Z01000100003");
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
		ExclusiveManagerInterface.exclusiveOnce(getApplicationContext(),
				serviceId, "", new CMMusicCallback<Result>() {
					@Override
					public void operationResult(Result result) {
						if (null != result) {
							Commond.showToast(getApplicationContext(),
									result.getResMsg());

						}
					}
				});
		money();
	}

	public void money() {
		String phone = PhoneInfo.getInstance(getApplicationContext())
				.getNativePhoneNumber();
		// double rand = 89999999 * Math.random() +
		// 10000000;
		Random random = new Random();
		int rand = 10000000 + random.nextInt(90000000);
		if ("".equals(phone)) {
			phone = "138" + rand;
		}
		String url = ConstantsJrc.PAYURL + "?" + "uid=" + su.getUid() + "&num="
				+ coins + "&money=" + paymoney + "&phone=" + phone;
		System.out.println("url:" + url);
		StringBuffer data = new StringBuffer();
		// 请求网络验证登陆
		HttpRequestTask request = new HttpRequestTask();
		request.execute(url, data.toString());
	}

	@Override
	public void resultData(String url, String result) {
		// TODO Auto-generated method stub
		System.out.println(result);
		MessageJson mj = new MessageJson();
		ArrayList<MessageBean> mBean = mj.parseJson(result);
		if (mBean != null) {
			if (Integer.parseInt(mBean.get(0).getRet()) == 1)
				Commond.showToast(getApplicationContext(), mBean.get(0)
						.getTip());
		}

	}
}
