package com.app.chatroom.otherui;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.jianrencun.chatroom.R;

public class MainTopRightDialog extends Activity {
	CheckBox receiverBtn;
	CheckBox soundBtn;
	CheckBox loadImageBtn;

	LayoutInflater inflater;
	View view;
	TextView toastTextView;
	Toast toast;
	SharedPreferences sp;
	SystemSettingUtilSp su;
	private AudioManager audio;
	int currVolume = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		/** 全屏设置，隐藏窗口所有装饰 */
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main_top_right_dialog);
		audio = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
		receiverBtn = (CheckBox) findViewById(R.id.receiver_auto_btn);
		soundBtn = (CheckBox) findViewById(R.id.sound_auto_btn);
		loadImageBtn = (CheckBox) findViewById(R.id.loadimage_auto_btn);

		// 自定义Toast
		inflater = this.getLayoutInflater();
		view = inflater.inflate(R.layout.toast, null);
		toastTextView = (TextView) view.findViewById(R.id.toast_textView);
		toast = new Toast(this);
		toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 80);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(view);
		// if (audio.isWiredHeadsetOn() == true) {
		// receiverBtn.setBackgroundDrawable(getResources().getDrawable(
		// R.drawable.receiver_close_btn));
		// // AudioManager audioManager = (AudioManager)
		// // getSystemService(Context.AUDIO_SERVICE);
		// // audioManager.setMode(AudioManager.MODE_IN_CALL);// 把模式调成听筒放音模式
		// // audioManager.setSpeakerphoneOn(false);
		//
		// try {
		// AudioManager audioManager = (AudioManager)
		// getSystemService(Context.AUDIO_SERVICE);
		// if (audioManager != null) {
		// if (audioManager.isSpeakerphoneOn()) {
		// audioManager.setSpeakerphoneOn(false);
		// audioManager.setStreamVolume(
		// AudioManager.STREAM_VOICE_CALL, currVolume,
		// AudioManager.STREAM_VOICE_CALL);
		// }
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// } else if (su.getAudioModel().equals("0")) {
		// receiverBtn.setBackgroundDrawable(getResources().getDrawable(
		// R.drawable.receiver_close_btn));
		// // AudioManager audioManager = (AudioManager)
		// // getSystemService(Context.AUDIO_SERVICE);
		// // audioManager.setMode(AudioManager.MODE_IN_CALL);// 把模式调成听筒放音模式
		// // System.out.println("audio:" + audioManager.getMode());
		// // audioManager.setSpeakerphoneOn(false);
		// try {
		// AudioManager audioManager = (AudioManager)
		// getSystemService(Context.AUDIO_SERVICE);
		// if (audioManager != null) {
		// if (audioManager.isSpeakerphoneOn()) {
		// audioManager.setSpeakerphoneOn(false);
		// audioManager.setStreamVolume(
		// AudioManager.STREAM_VOICE_CALL, currVolume,
		// AudioManager.STREAM_VOICE_CALL);
		// }
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// } else {
		// receiverBtn.setBackgroundDrawable(getResources().getDrawable(
		// R.drawable.receiver_open_btn));
		// // AudioManager audioManager = (AudioManager)
		// // getSystemService(Context.AUDIO_SERVICE);
		// // audioManager.setMode(AudioManager.ROUTE_SPEAKER);// 把模式调成扬声器放音模式
		// // System.out.println("audio:" + audioManager.getMode());
		// // audioManager.setSpeakerphoneOn(true);
		//
		// try {
		// AudioManager audioManager = (AudioManager)
		// getSystemService(Context.AUDIO_SERVICE);
		// audioManager.setMode(AudioManager.ROUTE_SPEAKER);
		// currVolume = audioManager
		// .getStreamVolume(AudioManager.STREAM_VOICE_CALL);
		//
		// if (!audioManager.isSpeakerphoneOn()) {
		// audioManager.setSpeakerphoneOn(true);
		//
		// audioManager
		// .setStreamVolume(
		// AudioManager.STREAM_VOICE_CALL,
		// audioManager
		// .getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL),
		// AudioManager.STREAM_VOICE_CALL);
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }

		if (su.getAudioAuto().equals("0")) {
			soundBtn.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.sound_auto_btn));
		} else if (su.getAudioAuto().equals("1")) {
			soundBtn.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.sound_btn));
		} else {
			soundBtn.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.sound_btn));
		}

		if (su.getPicAuto().equals("0")) {
			loadImageBtn.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.loadimage_auto_btn));
		} else if (su.getPicAuto().equals("1")) {
			loadImageBtn.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.loadimage_btn));
		} else {
			loadImageBtn.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.loadimage_btn));
		}

		receiverBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				// if (audio.isWiredHeadsetOn() == true) {
				// AudioManager audioManager = (AudioManager)
				// getSystemService(Context.AUDIO_SERVICE);
				// audioManager.setMode(AudioManager.MODE_NORMAL);
				// audioManager.setMode(AudioManager.MODE_IN_CALL);//
				// 把模式调成听筒放音模式
				// audioManager.setSpeakerphoneOn(false);
				// setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
				// toastTextView.setText("耳机连接-听筒模式");
				// toast.show();
				// } else if (su.getAudioModel().equals("0")) {
				// su.setAudioModel("1");
				// AudioManager audioManager = (AudioManager)
				// getSystemService(Context.AUDIO_SERVICE);
				// audioManager.setMode(AudioManager.ROUTE_SPEAKER);//
				// 把模式调成扬声器放音模式
				// audioManager.setSpeakerphoneOn(true);
				// toastTextView.setText("开启扬声器模式");
				// toast.show();
				// } else {
				// su.setAudioModel("0");
				// AudioManager audioManager = (AudioManager)
				// getSystemService(Context.AUDIO_SERVICE);
				// audioManager.setMode(AudioManager.MODE_NORMAL);
				// audioManager.setSpeakerphoneOn(false);
				// setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
				// audioManager.setMode(AudioManager.MODE_IN_CALL);//
				// 把模式调成听筒放音模式
				// toastTextView.setText("开启听筒模式");
				// toast.show();
				// }
				// setResult(ConstantsJrc.RIGHTSOUND);
				finish();
			}
		});

		soundBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (su.getAudioAuto().equals("0")) {
					su.setAudioAuto("1");
					soundBtn.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.sound_auto_btn));
					toastTextView.setText("自动加载音频");
					toast.show();

				} else {
					su.setAudioAuto("0");
					soundBtn.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.sound_btn));
					toastTextView.setText("手动加载音频");
					toast.show();
				}
				setResult(ConstantsJrc.RIGHTAUDIO);
				finish();
			}
		});

		loadImageBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (su.getPicAuto().equals("0")) {
					su.setPicAuto("1");
					loadImageBtn.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.loadimage_auto_btn));
					toastTextView.setText("自动加载图片");
					toast.show();
				} else {
					su.setPicAuto("0");
					loadImageBtn.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.loadimage_btn));
					toastTextView.setText("手动加载图片");
					toast.show();
				}
				setResult(ConstantsJrc.RIGHTPIC);
				finish();
			}
		});

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}
}
