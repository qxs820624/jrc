package com.jianrencun.game;

import java.util.Calendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.jianrencun.android.HttpBaseActivity;
import com.jianrencun.chatroom.R;

public class Call extends HttpBaseActivity{
	
	Button boy ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.call);
	
	boy = (Button)findViewById(R.id.call_iamboy);
	
	boy.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

//			View timepickerview=LayoutInflater.from(getContext()).inflate(
//			R.layout.timepicker, null);
//			ScreenInfo screenInfo = new ScreenInfo(getContext());
//			wheelMain = new WheelMain(timepickerview,hasTime);
//			wheelMain.screenheight = screenInfo.getHeight();
//			String time = itemValue.getText().toString();
//			Calendar calendar = Calendar.getInstance();
//			if(JudgeDate.isDate(time, formatStr)){
//			try {
//			calendar.setTime(dateFormat.parse(time));
//			} catch (ParseException e) {
//			e.printStackTrace();
//			}
//			}
//			int year = calendar.get(Calendar.YEAR);
//			int month = calendar.get(Calendar.MONTH);
//			int day = calendar.get(Calendar.DAY_OF_MONTH);
//			int hour = calendar.get(Calendar.HOUR_OF_DAY);
//			int min = calendar.get(Calendar.MINUTE);
//			if(hasTime)
//			wheelMain.initDateTimePicker(year,month,day,hour,min);
//			else
//			wheelMain.initDateTimePicker(year,month,day);
//			new AlertDialog.Builder(context)
//			.setTitle("选择时间")
//			.setView(timepickerview)
//			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//			itemValue.setText(wheelMain.getTime());
//			}
//			}).setNegativeButton("取消", null)
//			.show();
//
//			WheelMain wheelMain = new WheelMain(timepickerview,hasTime);
//			if(hasTime)
//			wheelMain.initDateTimePicker(year,month,day,hour,min);
//			else
//			wheelMain.initDateTimePicker(year,month,day);
		}
	});
	
	Intent it = new Intent();
	it.setClass(this, Huadongye.class);
	startActivity(it);
	
	}

	@Override
	public void resultData(String url, String result) {
		// TODO Auto-generated method stub
		
	}
}