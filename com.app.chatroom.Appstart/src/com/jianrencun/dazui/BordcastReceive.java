package com.jianrencun.dazui;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

import com.tencent.mm.sdk.plugin.MMPluginOAuth.Receiver;

public class BordcastReceive extends Receiver {
    private static final String TAG = "HeadsetPlugReceiver"; 
    
    @Override 
    public void onReceive(Context context, Intent intent) { 
          if (intent.hasExtra("state")){ 
               if (intent.getIntExtra("state", 0) == 0){     

            	   AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
    				audioManager.setMode(AudioManager.MODE_IN_CALL);// 把模式调成听筒放音模式
    				audioManager.setSpeakerphoneOn(false);   		  		  			
               } 
               else if (intent.getIntExtra("state", 0) == 1){ 
            		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    				audioManager.setMode(AudioManager.MODE_NORMAL);// 把模式调成听筒放音模式
               } 
          } 
         
    } 
}
