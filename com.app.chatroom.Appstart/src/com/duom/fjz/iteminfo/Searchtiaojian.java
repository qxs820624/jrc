package com.duom.fjz.iteminfo;

import com.jianrencun.chatroom.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Searchtiaojian extends Activity{
        private Button sexall , sexboy , sexgril , onehour , threehour , day , sousuo , age , city,
        ageall , ageone , agetwo , agethree;
        private int sexnum , hournum ,agenum;
       
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        	// TODO Auto-generated method stub
        	super.onCreate(savedInstanceState);
		setContentView(R.layout.searchtiaojian);
		
		sexall = (Button)findViewById(R.id.tj_xingbie_all);
		sexboy = (Button)findViewById(R.id.tj_xingbie_boy);
		sexgril = (Button)findViewById(R.id.tj_xingbie_gril);
		onehour = (Button)findViewById(R.id.tj_zaixian_one);
		threehour = (Button)findViewById(R.id.tj_zaixian_three);
		day = (Button)findViewById(R.id.tj_zaixian_day);
		sousuo = (Button)findViewById(R.id.tj_search_bnt);
		city = (Button)findViewById(R.id.tj_city);
		age = (Button)findViewById(R.id.tj_age);		
		ageall = (Button)findViewById(R.id.tj_age_all);
		ageone = (Button)findViewById(R.id.tj_age_one);
		agetwo = (Button)findViewById(R.id.tj_age_two);
		agethree = (Button)findViewById(R.id.tj_age_three);
		
		sexall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				if(v.getBackground()){
//					sexall.setBackgroundResource(R.drawable.tj_leftpre);
//				}else if(){
//					sexall.setBackgroundResource(R.drawable.tj_left);
//				}
				sexall.setBackgroundResource(R.drawable.tj_leftpre);
				sexall.setTextColor(Color.parseColor("#ffffff"));
				sexboy.setTextColor(Color.parseColor("#000000"));
				sexgril.setTextColor(Color.parseColor("#000000"));
				sexboy.setBackgroundResource(R.drawable.tj_center);
				sexgril.setBackgroundResource(R.drawable.tj_right);
				sexnum = 0 ;
			}
		});
		sexboy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sexall.setBackgroundResource(R.drawable.tj_left);
				sexboy.setBackgroundResource(R.drawable.tj_centerpre);
				sexboy.setTextColor(Color.parseColor("#ffffff"));
				sexall.setTextColor(Color.parseColor("#000000"));
				sexgril.setTextColor(Color.parseColor("#000000"));
				sexgril.setBackgroundResource(R.drawable.tj_right);
				sexnum = 1 ;
			}
		});
		sexgril.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sexall.setBackgroundResource(R.drawable.tj_left);
				sexboy.setBackgroundResource(R.drawable.tj_center);
				sexgril.setBackgroundResource(R.drawable.tj_rightpre);
				sexgril.setTextColor(Color.parseColor("#ffffff"));
				sexboy.setTextColor(Color.parseColor("#000000"));
				sexall.setTextColor(Color.parseColor("#000000"));
				sexnum = 2 ;
			}
		});
		onehour.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onehour.setBackgroundResource(R.drawable.tj_leftpre);
				onehour.setTextColor(Color.parseColor("#ffffff"));
				threehour.setTextColor(Color.parseColor("#000000"));
				day.setTextColor(Color.parseColor("#000000"));
				threehour.setBackgroundResource(R.drawable.tj_center);
				day.setBackgroundResource(R.drawable.tj_right);
				hournum = 0 ;
			}
		});
		threehour.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onehour.setBackgroundResource(R.drawable.tj_left);
				threehour.setBackgroundResource(R.drawable.tj_centerpre);
				threehour.setTextColor(Color.parseColor("#ffffff"));
				onehour.setTextColor(Color.parseColor("#000000"));
				day.setTextColor(Color.parseColor("#000000"));
				day.setBackgroundResource(R.drawable.tj_right);
				hournum = 1 ;
			}
		});
		day.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onehour.setBackgroundResource(R.drawable.tj_left);
				threehour.setBackgroundResource(R.drawable.tj_center);
				day.setBackgroundResource(R.drawable.tj_rightpre);
				day.setTextColor(Color.parseColor("#ffffff"));
				threehour.setTextColor(Color.parseColor("#000000"));
				onehour.setTextColor(Color.parseColor("#000000"));
				hournum = 2 ;
			}
		});
		age.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		city.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		ageall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ageall.setBackgroundResource(R.drawable.tj_leftpre);
				ageone.setBackgroundResource(R.drawable.tj_center);
				agetwo.setBackgroundResource(R.drawable.sf_three);
				agethree.setBackgroundResource(R.drawable.tj_right);
				ageall.setTextColor(Color.parseColor("#ffffff"));
				ageone.setTextColor(Color.parseColor("#000000"));
				agetwo.setTextColor(Color.parseColor("#000000"));
				agethree.setTextColor(Color.parseColor("#000000"));
				agenum = 0 ;
			}
		});
		ageone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ageall.setBackgroundResource(R.drawable.tj_left);
				ageone.setBackgroundResource(R.drawable.tj_centerpre);
				agetwo.setBackgroundResource(R.drawable.sf_three);
				agethree.setBackgroundResource(R.drawable.tj_right);
				ageone.setTextColor(Color.parseColor("#ffffff"));
				ageall.setTextColor(Color.parseColor("#000000"));
				agetwo.setTextColor(Color.parseColor("#000000"));
				agethree.setTextColor(Color.parseColor("#000000"));
				agenum = 1 ;
			}
		});
		agetwo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ageall.setBackgroundResource(R.drawable.tj_left);
				ageone.setBackgroundResource(R.drawable.tj_center);
				agetwo.setBackgroundResource(R.drawable.tj_centerpre);
				agethree.setBackgroundResource(R.drawable.tj_right);
				agetwo.setTextColor(Color.parseColor("#ffffff"));
				ageall.setTextColor(Color.parseColor("#000000"));
				ageone.setTextColor(Color.parseColor("#000000"));
				agethree.setTextColor(Color.parseColor("#000000"));
				agenum = 2 ;
			}
		});
		agethree.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ageall.setBackgroundResource(R.drawable.tj_left);
				ageone.setBackgroundResource(R.drawable.tj_center);
				agetwo.setBackgroundResource(R.drawable.sf_three);
				agethree.setBackgroundResource(R.drawable.tj_rightpre);
				agethree.setTextColor(Color.parseColor("#ffffff"));
				ageall.setTextColor(Color.parseColor("#000000"));
				agetwo.setTextColor(Color.parseColor("#000000"));
				ageone.setTextColor(Color.parseColor("#000000"));
				agenum = 3 ;
			}
		});
		sousuo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent data=new Intent();  
				data.putExtra("sexnum" , sexnum );
				data.putExtra("hournum", hournum);
				data.putExtra("agenum", agenum);
				 //请求代码可以自己设置，这里设置成20  
				setResult(20, data);  
				finish();
			}
		});
	}
}
