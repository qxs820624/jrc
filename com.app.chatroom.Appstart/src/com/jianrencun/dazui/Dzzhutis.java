package com.jianrencun.dazui;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.app.chatroom.pic.BitmapCacheBguanzhu;
import com.app.chatroom.pic.BitmapCacheBlack;
import com.app.chatroom.pic.BitmapCacheVillage;
import com.app.chatroom.util.Commond;
import com.app.chatroom.viewdate.WheelScroller.ScrollingListener;
import com.duom.fjz.iteminfo.BitmapCache2;
import com.jianrencun.android.Details;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.DazuiActivity.MyOnClickListener;
import com.jianrencun.dazui.HttpBaseActivity.HttpRequestTask;
import com.jianrencun.dazui.Mydazui.MyOnPageChangeListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class Dzzhutis extends HttpBaseActivity implements OnScrollListener {
	private ViewPager mTabPager;
	private View view1, view2, view3;
	ArrayList<View> views;
	private ImageButton zhutis1, zhutis2, zhutis3;
	private int whichpage;
	private boolean istwice, istwice2;
	private String currentdate;
	private ListView lv1, lv2, lv3;
	private LinearLayout ll0, ll1, ll2;
	private ProgressBar pb_1, pb_2, pb_3;
	private LinearLayout mTabImg;// 动画图片
	private int zero = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int one, two;// 单个水平动画位移
	Display currDisplay;
	private int lastview , twice;
	private String pd;
	private String dnlink;
	private List<ZhutisIteminfo> ztlist, phlist, drlist;
	private String ztlink, phlink, drlink;
	private View vFooter;
	private LinearLayout loading;
	private List<String> dllinks = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dzzhutis);

		init();
		
		lv1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(Dzzhutis.this, Dzmysave.class);
				it.putExtra("osc", ztlist.get(arg2).getLink());
				startActivity(it);
			}
		});
		lv2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(Dzzhutis.this, Dzmysave.class);
				it.putExtra("osc", phlist.get(arg2).getLink());
				it.putExtra("which", 1);
				startActivity(it);
			}
		});
		lv3.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(Dzzhutis.this, Dzmysave.class);
				it.putExtra("osc", drlist.get(arg2).getLink());
				it.putExtra("which", 2);
				startActivity(it);
			}
		});

		// 每个页面的view数据
		views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		// 填充ViewPager的数据适配器
		PagerAdapter mPagerAdapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(views.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(views.get(position));
				return views.get(position);
			}
		};

		mTabPager.setAdapter(mPagerAdapter);
		mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());

		String url;
		url = "http://jrc.hutudan.com/music/top.phpflg=0";
		url = Details.geturl(url);
		// "http://jrc.hutudan.com/music/enter.phppkg=com.jianrencun.chatroom&uid=193&nick=+++df&header=http%3A%2F%2Fq.qlogo.cn%2Fqqapp%2F222222%2FEB5F6F79D1AA31C3F3D91E7AD958B74B%2F100";
		StringBuffer data = new StringBuffer();
		// 请求网络验证登陆
		HttpRequestTask request = new HttpRequestTask();
		request.execute(url, data.toString());
	}

	/**
	 * 头标点击监听
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mTabPager.setCurrentItem(index);
		}
	};

	/*
	 * 页卡切换监听(原作者:D.Winter)
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				whichpage = 0;
				if (lastview == 1) {
					animation = new TranslateAnimation(one, zero, 0, 0);
				} else if (lastview == 2) {
					animation = new TranslateAnimation(two, zero, 0, 0);
				}

				// isnum1 = true;
				zhutis1.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.zhuantisel));
				zhutis2.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.daren));
				zhutis3.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.paihang));
				// lastceshi = 0;

				break;
			case 1:
				whichpage = 1;
				if (lastview == 0) {
					animation = new TranslateAnimation(zero, one, 0, 0);
				} else if (lastview == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
				}

				// isnum1 = true;
				zhutis1.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.zhuanti));
				zhutis2.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.darensel));
				zhutis3.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.paihang));

				if (istwice == false) {
					istwice = true;
					// isnum1 = true;
					currentdate = "";
					// pb.setVisibility(View.VISIBLE);
					// String ss = clink;
					String url = "http://jrc.hutudan.com/music/top.phpflg=1";
					url = Details.geturl(url);
					post(url);
				}

				break;
			case 2:
				whichpage = 2;
				if (lastview == 0) {
					animation = new TranslateAnimation(zero, two, 0, 0);
				} else if (lastview == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
				}
				zhutis1.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.zhuanti));
				zhutis2.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.daren));
				zhutis3.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.paihangsel));

				if (istwice2 == false) {
					istwice2 = true;
					currentdate = "";
					// pb.setVisibility(View.VISIBLE);
					// String ss = clink;
					String url = "http://jrc.hutudan.com/music/top.phpflg=2";
					url = Details.geturl(url);
					post(url);
				}

				break;
			}
			if (animation != null) {
				animation.setFillAfter(true);// True:图片停在动画结束位置
				animation.setDuration(150);
				mTabImg.startAnimation(animation);
			}
			lastview = arg0;
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	public void da_zhutis_back(View v) { // 返回按钮
		finish();
	}

	private void init() {

		ztlist = new ArrayList<ZhutisIteminfo>();
		phlist = new ArrayList<ZhutisIteminfo>();
		drlist = new ArrayList<ZhutisIteminfo>();
		mTabPager = (ViewPager) findViewById(R.id.dz_zhutispager);
		zhutis1 = (ImageButton) findViewById(R.id.dz_zhutis1);
		zhutis2 = (ImageButton) findViewById(R.id.dz_zhutis2);
		zhutis3 = (ImageButton) findViewById(R.id.dz_zhutis3);
		mTabImg = (LinearLayout) findViewById(R.id.dz_img_tab_now);

		zhutis1.setOnClickListener(new MyOnClickListener(0));
		zhutis2.setOnClickListener(new MyOnClickListener(1));
		zhutis3.setOnClickListener(new MyOnClickListener(2));

		currDisplay = getWindowManager().getDefaultDisplay();// 获取屏幕当前分辨率
		int displayWidth = (int) (currDisplay.getWidth() * 0.93);
		int displayHeight = currDisplay.getHeight();
		one = displayWidth / 3; // 设置水平动画平移大小
		two = one * 2;
		
		vFooter = LayoutInflater.from(this).inflate(R.layout.list_footer, null);
		loading = (LinearLayout) vFooter.findViewById(R.id.list_footer);
		loading.setVisibility(View.GONE);

		RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) mTabImg
				.getLayoutParams();
		llParams.width = (int) (one * 0.87);

		LayoutInflater mLi = LayoutInflater.from(this);
		view1 = mLi.inflate(R.layout.zhutis_zhuti, null);

		lv1 = (ListView) view1.findViewById(R.id.zhutis_zhutilv);
		ll0 = (LinearLayout) view1.findViewById(R.id.zhutis_zhtutill0);
		pb_1 = (ProgressBar) view1.findViewById(R.id.zhutis_zhtutipg);

		view2 = mLi.inflate(R.layout.zhutis_phb, null);
		ll1 = (LinearLayout) view2.findViewById(R.id.zhutis_phbll0);
		pb_2 = (ProgressBar) view2.findViewById(R.id.zhutis_phbpg);

		lv2 = (ListView) view2.findViewById(R.id.zhutis_phblv);

		view3 = mLi.inflate(R.layout.zhutis_daren, null);
		ll2 = (LinearLayout) view3.findViewById(R.id.zhutis_darenll0);
		pb_3 = (ProgressBar) view3.findViewById(R.id.zhutis_darenpg);

		lv3 = (ListView) view3.findViewById(R.id.zhutis_darenlv);
	}

	@Override
	void resultData(String url, String result) {
		// TODO Auto-generated method stub
		String tip = null;
		try {
			//Log.e("ooommmy", result+","+"\n"+url);
			JSONObject jsonChannel = new JSONObject(result);
			tip = URLDecoder.decode(jsonChannel.optString("tip"));
			pd = URLDecoder.decode(jsonChannel.optString("pd"));
			String nlink = URLDecoder.decode(jsonChannel.optString("nlink"));

			int ret = jsonChannel.optInt("ret");
			Commond.showToast(this, tip);
			if (ret == 0) {
				return;
			}

			JSONArray jsonItems = jsonChannel.optJSONArray("items");
			if (jsonItems != null) {
				// tip = "获取成功！";
				for (int i = 0; i < jsonItems.length(); i++) {
					JSONObject jsonItem = jsonItems.getJSONObject(i);
					String title = URLDecoder.decode(jsonItem
							.optString("title"));
					String title_c = URLDecoder.decode(jsonItem
							.optString("title_c"));
					String pic = URLDecoder.decode(jsonItem.optString("pic"));
					String desc1 = URLDecoder.decode(jsonItem
							.optString("desc1"));
					String desc2 = URLDecoder.decode(jsonItem
							.optString("desc2"));
					String link = URLDecoder.decode(jsonItem.optString("link"));

					ZhutisIteminfo item = new ZhutisIteminfo(pic, title, desc1,
							link, title_c, desc2);
					if (url.contains("flg=0")) {
						// itemchoose1.add(item);
						ztlist.add(item);
						ztlink = nlink;
					} else if (url.contains("flg=1")) {
						phlist.add(item);
						phlink = nlink;
					} else if (url.contains("flg=2")) {
						drlist.add(item);
						drlink = nlink;
					}
				}
			}
			if(url.contains("flg=0")){
				ZhutisAdapter ad;
//				pb_zuixin.setVisibility(View.GONE);
				if (lv1.getAdapter() == null) {
					ad = new ZhutisAdapter(Dzzhutis.this, ztlist, lv1);
					ll0.setVisibility(View.GONE);
					lv1.setAdapter(ad);				
				} else if (twice == 3) {
					lv1.requestLayout();
//					 ad.notifyDataSetChanged(); // 数据集变化后,通知adapter
					loading.setVisibility(View.GONE);
				}
			}else if(url.contains("flg=1")){
				ZhutisAdapter ad;
				if (lv2.getAdapter() == null) {
					ad = new ZhutisAdapter(Dzzhutis.this, phlist, lv2);
					ll1.setVisibility(View.GONE);
					lv2.setAdapter(ad);				
				} else if (twice == 3) {
					lv2.requestLayout();
//					 ad.notifyDataSetChanged(); // 数据集变化后,通知adapter
					loading.setVisibility(View.GONE);
				}
				
			}else if(url.contains("flg=2")){
				ZhutisAdapter ad;
				if (lv3.getAdapter() == null) {
					ad = new ZhutisAdapter(Dzzhutis.this, drlist, lv3);
					ll2.setVisibility(View.GONE);
					lv3.setAdapter(ad);				
				} else if (twice == 3) {
					lv3.requestLayout();
//					 ad.notifyDataSetChanged(); // 数据集变化后,通知adapter
					loading.setVisibility(View.GONE);					
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return;
		}
		if (!TextUtils.isEmpty(tip)) {
			Commond.showToast(this, tip);
		}
	}

	public void post(String urllink) {
		String result = "";
		// String url = appendNameValue(urllink, "pkg", "com.jianrencun.chatroom");
		StringBuffer data = new StringBuffer();
		// 请求网络验证登陆
		HttpRequestTask request = new HttpRequestTask();
		request.execute(urllink, data.toString());
	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			// if(whichpage == 1){
			if (view.getLastVisiblePosition() == view.getCount() - 1) {
				String nn = "";
				
				if(whichpage == 0){
					if (TextUtils.isEmpty(ztlink)) {
						lv1.removeFooterView(vFooter);
						return;
					}
				}

				if (whichpage == 1) {
					if (TextUtils.isEmpty(phlink)) {
						lv2.removeFooterView(vFooter);
						return;
					}
				}

				if (whichpage == 2) {
					if (TextUtils.isEmpty(drlink)) {
					lv3.removeFooterView(vFooter);					
					return;
					}
				}

				if (whichpage == 0) {
					nn = ztlink;
				} else if (whichpage == 1) {
					nn = phlink;
				} else if (whichpage == 2) {
					nn = drlink;
				}
				if (!dllinks.contains(nn)) {
					twice = 3;
					loading.setVisibility(View.VISIBLE);
					String url = nn;
					StringBuffer data = new StringBuffer();
					//
					data.append("&pd=");
					data.append(URLEncoder.encode(pd));
					// 请求网络验证登陆
					// nlink2 = geturl(url);
					nn = Details.geturl(nn);
					HttpRequestTask request = new HttpRequestTask();
					request.execute(nn, data.toString());
					dllinks.add(nn);
				}
			}

			// }
		}
	}
   @Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	   if(BitmapCache2.getIntance().bmpCacheMap != null && BitmapCache2.getIntance().bmpCacheMap.size() != 0){
		   BitmapCache2.getIntance().clearCacheBitmap();		   
	   }
		BitmapCacheBguanzhu.getIntance().clearCacheBitmap();
		BitmapCacheBlack.getIntance().clearCacheBitmap();
		BitmapCacheVillage.getIntance().clearCacheBitmap();
	super.onDestroy();
}

}
