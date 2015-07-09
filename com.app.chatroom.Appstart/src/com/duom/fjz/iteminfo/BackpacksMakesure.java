package com.duom.fjz.iteminfo;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.chatroom.Appstart;
import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;
import com.jianrencun.android.Details;
import com.jianrencun.android.HttpBaseActivity;
import com.jianrencun.android.HttpBaseActivity.HttpRequestTask;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.DazuiActivity;
import com.jianrencun.dazui.Dzmysave;
import com.jianrencun.dazui.Mydazui;
import com.jianrencun.dynamic.Dynamic;

public class BackpacksMakesure extends HttpBaseActivity {

	private ImageView iv;
	private EditText shuliang;
	private ImageButton sure, cancle;
	private TextView tv1, tv2;
	private String logo, mark;
	private int num, which, id;
	private File picfile;
	private Bitmap bmp;
	private TextView desc_tv;
	private String url = ConstantsJrc.BACKPACKS_SELL;
	private Details dt= new Details(); 
	/* 配置文件 */
	SharedPreferences sp;
	SystemSettingUtilSp su;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.backpacksmakesure);

		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);

		Intent it = getIntent();
		logo = it.getStringExtra("url");
		num = it.getIntExtra("num", 0);
		which = it.getIntExtra("chuordiu", 0);
		id = it.getIntExtra("did", 0);
		mark = it.getStringExtra("mark");


		iv = (ImageView) findViewById(R.id.makesure_iv);
		desc_tv = (TextView) findViewById(R.id.makesure_et);
		shuliang = (EditText) findViewById(R.id.makesure_num);
		sure = (ImageButton) findViewById(R.id.makesure_sure);
		cancle = (ImageButton) findViewById(R.id.makesure_cancle);
		tv1 = (TextView)findViewById(R.id.makesure_diuqi);

		if(which == 0){
			tv1.setText("出售");
		}else if(which == 1){
			tv1.setText("丢弃");
		}
		
		picfile = new File(Appstart.jrcfile + "/" + Commond.getMd5Hash(logo));
		String filename = picfile.getPath().toString();
		if (picfile.exists()) {
			bmp = BitmapCache.getIntance().getCacheBitmap(filename, 0, 0);
		}
		if (bmp == null) {

		} else {
			iv.setImageBitmap(bmp);
		}

		desc_tv.setText(mark);

		sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				url = dt.appendNameValue(url, "uid", su.getUid());
				url = Details.appendNameValueint(url, "did", id);
				url = Details.appendNameValueint(url, "flg", which);
				url = Details.appendNameValueint(url, "num", Integer.parseInt(shuliang.getText().toString()));
				url = dt.appendNameValue(url, "token", URLEncoder.encode(su.getToken()));
				StringBuffer data = new StringBuffer();
				HttpRequestTask request = new HttpRequestTask();
				request.execute(url, data.toString());
			}
		});

		cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	@Override
	public void resultData(String url, String result) {
		// TODO Auto-generated method stub
		String tip = "";
		JSONObject jsonChannel;
		try {
			jsonChannel = new JSONObject(result);

			int ret = jsonChannel.optInt("ret");
			tip = URLDecoder.decode(jsonChannel.optString("tip"));
			if (ret == 0) {
				Commond.showToast(BackpacksMakesure.this, tip);
				return;
			}
			Commond.showToast(BackpacksMakesure.this, tip);	
			Intent data=new Intent();  
			data.putExtra("num", shuliang.getText().toString());
			 //请求代码可以自己设置，这里设置成20  
			setResult(20, data);  
			finish();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (DazuiActivity.player != null) {
			if (MainMenuActivity.borz == true) {
				DazuiActivity.player.start();
			}
		}
		if (Dzmysave.player != null) {
			if (MainMenuActivity.borz == true) {
				Dzmysave.player.start();
			}
		}
		if (Mydazui.player != null) {
			if (MainMenuActivity.borz == true) {
				Mydazui.player.start();
			}
		}
		if(Dynamic.dyplayer != null){
			if (MainMenuActivity.borz == true) {
				Dynamic.dyplayer.start();
			}
		}
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if (DazuiActivity.player != null && DazuiActivity.player.isPlaying()) {
//			if (detatilispause == false) {
				DazuiActivity.player.pause();
//			}
		}
		if (Dzmysave.player != null && Dzmysave.player.isPlaying()) {
//			if (detatilispause == false) {
				Dzmysave.player.pause();
//			}
		}
		if (Mydazui.player != null && Mydazui.player.isPlaying()) {
//			if (detatilispause == false) {
				Mydazui.player.pause();
//			}
		}
		super.onPause();
	}
}
