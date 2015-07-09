package com.app.chatroom.broadcast;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;

public class HeadsetPlugReceiver2 extends BroadcastReceiver {
	SharedPreferences sp;
	SystemSettingUtilSp su;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		sp = context.getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				context.MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
		
		if (intent.hasExtra("state")) {
			if (intent.getIntExtra("state", 0) == 0) {

				AudioManager audioManager = (AudioManager) context
						.getSystemService(Context.AUDIO_SERVICE);
				// audioManager.setMode(AudioManager.ROUTE_SPEAKER);//
				// 把模式调成扬声器放音模式
				// audioManager.setSpeakerphoneOn(true);
				audioManager.setMode(AudioManager.MODE_NORMAL);
				su.setAudioModel("1");
			} else if (intent.getIntExtra("state", 0) == 1) {

				AudioManager audioManager = (AudioManager) context
						.getSystemService(Context.AUDIO_SERVICE);
				// audioManager.setMode(AudioManager.MODE_IN_CALL);//
				// 把模式调成听筒放音模式
				// audioManager.setSpeakerphoneOn(false);
				audioManager.setMode(AudioManager.MODE_NORMAL);
				su.setAudioModel("0");
			}
		}
	}

}
