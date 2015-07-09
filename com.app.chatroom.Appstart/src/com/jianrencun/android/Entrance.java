package com.jianrencun.android;

import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.otherui.SystemMsgWebDialog;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;
import com.app.chatroom.util.SystemUtil;
import com.jianrencun.chatroom.R;
public class Entrance extends Activity{
    private Button queding ,quxiao ;
    private TextView tishi ;
    private String tip;
    private int ret;
	SharedPreferences sp;
	SystemSettingUtilSp su;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entrance);
    queding = (Button)findViewById(R.id.entrance);
    quxiao = (Button)findViewById(R.id.cancleentrance);
    tishi = (TextView)findViewById(R.id.tishi);
    sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
			MODE_WORLD_WRITEABLE);
	su = new SystemSettingUtilSp(sp);
    
    Intent it = getIntent();
    tip = it.getStringExtra("tishi");
    ret = it.getIntExtra("ret", 0);
    tishi.setText(tip);
   if(ret == 3){
	   quxiao.setVisibility(View.GONE);	   
   }else if(ret == 0){
	   quxiao.setVisibility(View.GONE);	  
   }
   else{
	   quxiao.setVisibility(View.VISIBLE);
   }
    
    queding.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(ret == 2){
			Intent data = new Intent();
			data.putExtra("entrance", 88);
			setResult(89, data);
			// 关闭掉这个Activity
			finish();	
			}else if(ret == 3){
				finish();
			}else if(ret == 0){
				finish();
			}else if(ret == 4){
				if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
					Commond.showToast(Entrance.this,"检测到网络网络异常或未开启");
				
					return;
				}
				Intent intentshop = new Intent(getApplicationContext(),
						SystemMsgWebDialog.class);
				// intentshop.putExtra(
				// "link",
				// ConstantsJrc.MAINMORE
				// + "?uid="
				// + su.getUid()
				// + "&flg=2&w="
				// + PhoneInfo
				// .getInstance(getApplicationContext())
				// .getWidth(MainMenuActivity.this)
				// + "&pkg="
				// + PhoneInfo
				// .getInstance(getApplicationContext())
				// .getPackage(MainMenuActivity.this)
				// + "&ver="
				// + PhoneInfo
				// .getInstance(getApplicationContext())
				// .getVersionName(MainMenuActivity.this)
				// + "&token=" + su.getToken());
				intentshop.putExtra("help", su.getShop());
				intentshop.putExtra("uid", su.getUid());
				intentshop.putExtra("type", "2");
				startActivity(intentshop);
			}
		}
	});
    quxiao.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
	});
    }
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}
}

