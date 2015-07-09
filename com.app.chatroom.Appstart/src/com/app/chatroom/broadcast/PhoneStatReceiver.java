package com.app.chatroom.broadcast;

import com.jianrencun.dazui.DazuiActivity;
import com.jianrencun.dazui.Dazuidetatil;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneStatReceiver extends BroadcastReceiver {
	private static final String TAG = "PhoneStatReceiver";

	private static boolean incomingFlag = false;

	private static String incoming_number = null;


	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		// 如果是拨打电话
		if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
			incomingFlag = false;
			String phoneNumber = intent
					.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
			//Log.i(TAG, "call OUT:" + phoneNumber);
		} else {
			// 如果是来电
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Service.TELEPHONY_SERVICE);
			switch (tm.getCallState()) {
			case TelephonyManager.CALL_STATE_RINGING:
				incomingFlag = true;// 标识当前是来电
				incoming_number = intent.getStringExtra("incoming_number");
				AudioManager audioManager = (AudioManager) context
						.getSystemService(Context.AUDIO_SERVICE);
				audioManager.setMode(AudioManager.MODE_IN_CALL);// 把模式调成听筒放音模式
				audioManager.setSpeakerphoneOn(false);
				// Log.i(TAG, "RINGING :" + incoming_number);
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:// 接听
				if (incomingFlag) {
					// Log.i(TAG, "incoming ACCEPT :" + incoming_number);

				}
				break;

			case TelephonyManager.CALL_STATE_IDLE:// 挂机
				if (incomingFlag) {
					// Log.i(TAG, "incoming IDLE");
				}
				break;
			}
		}
	}

}
