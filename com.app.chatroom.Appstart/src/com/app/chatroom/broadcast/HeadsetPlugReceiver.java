package com.app.chatroom.broadcast;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;

public class HeadsetPlugReceiver extends BroadcastReceiver {
	SharedPreferences sp;
	SystemSettingUtilSp su;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		sp = context.getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				context.MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
		AudioManager audioManager1 = (AudioManager) context
		.getSystemService(Context.AUDIO_SERVICE);
		if (intent.hasExtra("state")) {
			if (intent.getIntExtra("state", 0) == 0) {
				// Toast.makeText(context, "headset not connected",
				// Toast.LENGTH_LONG).show();
				AudioManager audioManager = (AudioManager) context
						.getSystemService(Context.AUDIO_SERVICE);
				if (audioManager.isSpeakerphoneOn() == false) {
					audioManager.setMode(AudioManager.ROUTE_SPEAKER);// 把模式调成扬声器放音模式
					audioManager.setSpeakerphoneOn(true);
				}
				su.setAudioModel("1");
				// Commond.showToast(context, "扬声器模式开启");
			} else if (intent.getIntExtra("state", 0) == 1) {
				// Toast.makeText(context, "headset  connected",
				// Toast.LENGTH_LONG)
				// .show();
				AudioManager audioManager = (AudioManager) context
						.getSystemService(Context.AUDIO_SERVICE);
				if (audioManager.isSpeakerphoneOn()) {
					audioManager.setMode(AudioManager.MODE_IN_CALL);// 把模式调成听筒放音模式
					audioManager.setSpeakerphoneOn(false);
				}
			 
				su.setAudioModel("0");
				// /Commond.showToast(context, "耳机连接-听筒模式开启");
			}
		}
	}

}
