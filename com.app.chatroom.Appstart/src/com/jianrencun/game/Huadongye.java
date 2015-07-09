/**
 * 
 */
package com.jianrencun.game;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.app.chatroom.Chakandatu;
import com.app.chatroom.Chakandatu.LoadImageRunnable;
import com.app.chatroom.Chakandatu.MyOnPageChangeListener;
import com.app.chatroom.imgzoom.view.TouchImageView;
import com.app.chatroom.util.Commond;
import com.duom.fjz.iteminfo.BitmapCacheDzlb;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Comment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * @author Administrator
 *
 */
public class Huadongye extends Activity{
	private ViewPager mTabPager ;
	private ImageView iv ;	
	private List<Integer>photos = new ArrayList<Integer>();
	private List<ImageView> ivs = new ArrayList<ImageView>();
	private LinearLayout ll ;
	private Button join ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.huadongye);	
	
	photos.add(R.drawable.description1);
	photos.add(R.drawable.description2);
	photos.add(R.drawable.description3);
	
	// 直接调用创建bitmap，会出现显示不正确的问题
	mTabPager = (ViewPager) findViewById(R.id.huadongye);
	mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());
	// 将要分页显示的View装入数组中
	LayoutInflater mLi = LayoutInflater.from(this);
	// 每个页面的view数据
	final ArrayList<View> views = new ArrayList<View>();
	for (int i = 0; i < photos.size(); i++) {
		View view = mLi.inflate(R.layout.morephotos, null);
		iv = (TouchImageView) view.findViewById(R.id.morephotoiv);	
		ll = (LinearLayout)view.findViewById(R.id.morephoto_pb_ll);
		join = (Button)view.findViewById(R.id.join);
		ll.setVisibility(View.GONE);
		ivs.add(iv);
		views.add(view);
	}
	ivs.get(0).setImageResource(photos.get(0));	
	join.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
	});

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
		
	}
	/*
	 * 页卡切换监听()
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			File picfile = null;
			try {
				ivs.get(arg0).setImageResource(photos.get(arg0));		
				if(arg0 == ivs.size()-1){
					join.setVisibility(View.VISIBLE);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				Commond.showToast(Huadongye.this, "登录超时，请重新登录！");
				finish();
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// lastview = arg0;
		}
	}
}
