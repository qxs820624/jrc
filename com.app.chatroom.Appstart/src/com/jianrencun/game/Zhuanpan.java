package com.jianrencun.game;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.chatroom.Appstart;
import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.otherui.SystemMsgWebDialog;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;
import com.app.chatroom.util.SystemUtil;
import com.app.chatroom.view.HomeView;
import com.duom.fjz.iteminfo.BitmapCache;
import com.duom.fjz.iteminfo.BitmapCacheDzlb;
import com.jianrencun.android.Details;
import com.jianrencun.android.HttpBaseActivity;
import com.jianrencun.android.HttpBaseActivity.HttpRequestTask;
import com.jianrencun.android.JianrencunActivity;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Comment;
import com.jianrencun.dazui.PinglunAdapter;
import com.jianrencun.dynamic.Dynamic;
import com.jianrencun.game.GameAdapter.LoadImageRunnable;

public class Zhuanpan extends HttpBaseActivity {
	private ImageButton small, big, start, phb, pay, back, help;
	private TextView tvcontent, tv1, tv2;
	private ImageView zhuanpan, zhizhen;
	private LinearLayout ll, zhuanpan_ll;
	private Matrix matrix = new Matrix();
	private Timer mTimer;
	private BitmapDrawable bm;
	private Bitmap bitmap;
	private int progress = 0;
	private Animation rotateAnimation = null , anima = null;
	private float fromd = 0;
	private Timer tm;
	private boolean isstop = false;
	private String choujiangmsg;

	/* 配置文件 */
	SharedPreferences sp;
	SystemSettingUtilSp su;
	private Details dt = new Details();

	private int model = 0;
	private float lstod;

	/* 音频控制 */
	MediaPlayer myMediaZhongjiang; // zhongjiang
//	MediaPlayer myMediaZhuan; // zhuangpan
	int vunm;
    int x ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.zhuanpan);

		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);

		// 按钮声音
//		myMediaZhongjiang = MediaPlayer.create(getApplicationContext(),
//				R.raw.hahaha);
//		myMediaZhuan = MediaPlayer.create(getApplicationContext(),
//				R.raw.zhuanpanmusic);
		// TODO Auto-generated method stub
		small = (ImageButton) findViewById(R.id.zp_small);
		big = (ImageButton) findViewById(R.id.zp_big);
		start = (ImageButton) findViewById(R.id.zp_start);
		phb = (ImageButton) findViewById(R.id.zp_phb);
		pay = (ImageButton) findViewById(R.id.zp_pay);
		back = (ImageButton) findViewById(R.id.zp_back);
		help = (ImageButton) findViewById(R.id.zp_help);
		zhuanpan = (ImageView) findViewById(R.id.zp_pan);
		zhizhen = (ImageView) findViewById(R.id.zp_zhizhen);
		tvcontent = (TextView) findViewById(R.id.zp_content);
		tv1 = (TextView) findViewById(R.id.zp_tv1);
		tv2 = (TextView) findViewById(R.id.zp_tv2);
		ll = (LinearLayout) findViewById(R.id.zp_start_ll);
		bm = ((BitmapDrawable) getResources().getDrawable(
				R.drawable.zhuanpan_pan));
		
		///
		anima= new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible  
        anima.setDuration(100); // duration - half a second  
        anima.setRepeatCount(10); // Repeat animation infinitely  
        anima.setRepeatMode(Animation.REVERSE); //   
    
		
		zhuanpan_ll = (LinearLayout) findViewById(R.id.zhuanpan_ll);
		bitmap = bm.getBitmap();

		help.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(Zhuanpan.this, Zhuanpan_help.class);
				startActivity(it);
			}
		});

		phb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.putExtra("mode_flg", model);
				it.setClass(Zhuanpan.this, ZhuanpanPhb.class);
				startActivity(it);
			}
		});

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (zhuanpan.getVisibility() == View.VISIBLE) {
					small.setVisibility(View.VISIBLE);
					findViewById(R.id.zp_dg).clearAnimation();
					big.setVisibility(View.VISIBLE);
					tv1.setVisibility(View.VISIBLE);
					tv2.setVisibility(View.VISIBLE);
					zhizhen.setVisibility(View.GONE);
					zhuanpan.clearAnimation();
					zhuanpan.setVisibility(View.GONE);
					tvcontent.setVisibility(View.GONE);
					ll.setVisibility(View.GONE);
					lstod = 0;
					fromd = 0;
					if (myMediaZhongjiang.isPlaying()) {
						myMediaZhongjiang.stop();
						myMediaZhongjiang.release();
						myMediaZhongjiang = null;
					}
				} else {
					finish();
				}
			}
		});

		small.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				zhuanpan_ll.setVisibility(View.VISIBLE);
				String url = dt.appendNameValue(ConstantsJrc.ZHUANPANLOGIN,
						"uid", su.getUid());
				url = dt.appendNameValue(url, "token",
						URLEncoder.encode(su.getToken()));
				url = Details.appendNameValueint(url, "mode_flg", 2);

				StringBuffer data = new StringBuffer();
				HttpRequestTask request = new HttpRequestTask();
				request.execute(url, data.toString());
                if(myMediaZhongjiang == null){
				myMediaZhongjiang = MediaPlayer.create(getApplicationContext(),
						R.raw.hahaha);
                }
			}
		});

		big.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				zhuanpan_ll.setVisibility(View.VISIBLE);
				String url = dt.appendNameValue(ConstantsJrc.ZHUANPANLOGIN,
						"uid", su.getUid());
				url = dt.appendNameValue(url, "token",
						URLEncoder.encode(su.getToken()));
				url = Details.appendNameValueint(url, "mode_flg", 1);

				StringBuffer data = new StringBuffer();
				HttpRequestTask request = new HttpRequestTask();
				request.execute(url, data.toString());
				 if(myMediaZhongjiang == null){
				myMediaZhongjiang = MediaPlayer.create(getApplicationContext(),
						R.raw.hahaha);
				 }
			}
		});
		start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				findViewById(R.id.zp_dg).setVisibility(View.GONE);  
				String url = dt.appendNameValue(ConstantsJrc.ZHUANPANSTART,
						"uid", su.getUid());
				url = dt.appendNameValue(url, "token",
						URLEncoder.encode(su.getToken()));
				url = Details.appendNameValueint(url, "mode_flg", model);
				StringBuffer data = new StringBuffer();
				HttpRequestTask request = new HttpRequestTask();
				request.execute(url, data.toString());
				start.setClickable(false);
				start.setBackgroundResource(R.drawable.game_begin_no);
				isstop = true;
				findViewById(R.id.zp_dg).clearAnimation();
				// new Handler().postDelayed(new Runnable() {
				// @Override
				// public void run() {
				//
				//
				// }
				// }, 4000);
			}
		});
		pay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
					Commond.showToast(Zhuanpan.this, "请检查网络连接！");
					return;
				}
				Intent intentshop = new Intent(getApplicationContext(),
						SystemMsgWebDialog.class);
				intentshop.putExtra("help", su.getShop());
				intentshop.putExtra("uid", su.getUid());
				intentshop.putExtra("type", "2");
				startActivity(intentshop);
			}
		});

	}

	@Override
	public void resultData(String url, String result) {
		// TODO Auto-generated method stub
		String tip = null;
		if (result == null) {
			// fresh.setVisibility(View.VISIBLE);
			Commond.showToast(Zhuanpan.this, "小贱提醒 ：当前网络不稳定！");
			return;
		}

		try {
			// //////////////////////////////////////////////正文内容页面
			JSONObject jsonChannel = new JSONObject(result);
			int ret = jsonChannel.optInt("ret");
			tip = URLDecoder.decode(jsonChannel.optString("tip"));
			// tjd = URLDecoder.decode(jsonChannel.optString("tjd"));
			if (ret == 0) {
				Commond.showToast(Zhuanpan.this, tip);
				start.setClickable(true);
				start.setBackgroundResource(R.drawable.zp_start1);
				return;
			}
			if (url.contains(ConstantsJrc.ZHUANPANLOGIN)) {
				String pic = URLDecoder.decode(jsonChannel.optString("pic"));
				if (TextUtils.isEmpty(pic)) {
					zhuanpan_ll.setVisibility(View.GONE);
					zhuanpan.setBackgroundResource(R.drawable.zhuanpan_pan);
					small.setVisibility(View.GONE);
					big.setVisibility(View.GONE);
					tv1.setVisibility(View.GONE);
					tv2.setVisibility(View.GONE);
					zhizhen.setVisibility(View.VISIBLE);
					tvcontent.setVisibility(View.VISIBLE);
					zhuanpan.setVisibility(View.VISIBLE);
					ll.setVisibility(View.VISIBLE);
				} else {
					jiance(Zhuanpan.this, pic, zhuanpan, zphandler);
				}
				String msg = URLDecoder.decode(jsonChannel.optString("msg"));
				tvcontent.setText(msg);

				if (url.contains("mode_flg=1")) {
					model = 1;
				} else if (url.contains("mode_flg=2")) {
					model = 2;
				}
			} else if (url.contains(ConstantsJrc.ZHUANPANSTART)) {
				vunm = jsonChannel.optInt("vnum");
				float tod = 1080;
				switch (vunm) {
				case 0:
					tod += 112.5;
					break;
				case 1:
					tod += 22.5;
					break;
				case 2:
					tod += 292.5;
					break;
				case 3:
					tod += 202.5;
					break;
				case 4:
					tod += 67.5;
					break;
				case 5:
					tod += 157.5;
					break;
				case 6:
					tod += 247.5;
					break;
				case 7:
					tod += 292.5;
					break;
				}

				rotateAnimation = new RotateAnimation(fromd, tod,
						RotateAnimation.RELATIVE_TO_SELF, 0.5f,
						RotateAnimation.RELATIVE_TO_SELF, 0.5f);							
				rotateAnimation.setDuration(4000);
				rotateAnimation.setFillAfter(true);			
				choujiangmsg = URLDecoder.decode(jsonChannel.optString("msg"));
				zhuanpan.startAnimation(rotateAnimation);
			
				lstod = tod;
				if (myMediaZhongjiang != null && myMediaZhongjiang.isPlaying()) {
					myMediaZhongjiang.stop();
					myMediaZhongjiang.reset();
				}
//				if (myMediaZhuan != null) {
//					myMediaZhuan.start();
//				}
				anima.setAnimationListener(new AnimationListener(){

					@Override
					public void onAnimationEnd(Animation animation) {
						// TODO Auto-generated method stub
						findViewById(R.id.zp_dg).setVisibility(View.GONE);   
					}

					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
				});
				rotateAnimation.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						// TODO Auto-generated method stub

						tvcontent.setText(choujiangmsg);
						fromd = lstod;
						fromd = fromd % 360;
						start.setClickable(true);
						start.setBackgroundResource(R.drawable.zp_start1);
						
						if (vunm == 1 || vunm == 0) {
							myMediaZhongjiang = MediaPlayer.create(
									getApplicationContext(), R.raw.hahaha);
							myMediaZhongjiang.start();
						}
						
						if(vunm == 0 || vunm ==1 || vunm == 2 ){
							findViewById(R.id.zp_dg).setVisibility(View.VISIBLE);
							findViewById(R.id.zp_dg).clearAnimation();
							findViewById(R.id.zp_dg).startAnimation(anima);
						}
					}
				});
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Commond.showToast(Zhuanpan.this, "解析失败！");
		}
	}

	public void jiance(Context context, String header, ImageView iv,
			Handler mhandler) {
		File picfile = new File(Appstart.jrcfile + "/"
				+ Commond.getMd5Hash(header));
		String filename = picfile.getPath().toString();
		ArrayList<String> urls = new ArrayList<String>();
		Bitmap bmp = null;
		if (picfile.exists()) {
			bmp = BitmapCache.getIntance().getCacheBitmap(filename, 0, 0);
		}
		if (bmp == null) {
			if (!urls.contains(header)) {
				urls.add(header);
				new Thread(new LoadImageRunnable(context, mhandler, 0, header,
						filename)).start();
			}
		} else {
			BitmapDrawable bd = new BitmapDrawable(bmp);
			iv.setImageDrawable(bd);
			zhuanpan_ll.setVisibility(View.GONE);
			small.setVisibility(View.GONE);
			big.setVisibility(View.GONE);
			tv1.setVisibility(View.GONE);
			tv2.setVisibility(View.GONE);
			zhizhen.setVisibility(View.VISIBLE);
			tvcontent.setVisibility(View.VISIBLE);
			zhuanpan.setVisibility(View.VISIBLE);
			ll.setVisibility(View.VISIBLE);
		}
	}

	Handler zphandler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			if (data != null) {
				String url = data.getString("url");
				String filename = data.getString("filename");
				Bitmap bmp = BitmapCacheDzlb.getIntance().getCacheBitmap(
						filename, 0, 0);

				BitmapDrawable bd = new BitmapDrawable(bmp);
				zhuanpan.setBackgroundDrawable(bd);
				zhuanpan_ll.setVisibility(View.GONE);
				small.setVisibility(View.GONE);
				big.setVisibility(View.GONE);
				tv1.setVisibility(View.GONE);
				tv2.setVisibility(View.GONE);
				zhizhen.setVisibility(View.VISIBLE);
				tvcontent.setVisibility(View.VISIBLE);
				zhuanpan.setVisibility(View.VISIBLE);
				ll.setVisibility(View.VISIBLE);
			}
		}
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub

		if (myMediaZhongjiang != null && myMediaZhongjiang.isPlaying()) {
			myMediaZhongjiang.release();
			myMediaZhongjiang = null;
		}
//		if (myMediaZhuan != null && myMediaZhuan.isPlaying()) {
//			myMediaZhuan.release();
//			myMediaZhuan = null;
//		}

		super.onDestroy();
	}
}
