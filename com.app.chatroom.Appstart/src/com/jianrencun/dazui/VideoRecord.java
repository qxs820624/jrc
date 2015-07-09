package com.jianrencun.dazui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;


/**
 * 音频录制
 * 
 * @author J.King
 * 
 */

public class VideoRecord {
    public MediaPlayer   mPlayer = null; 
    private MediaRecorder mRecorder = null; 
    public void startPlaying() { 
  //播放之  
            mPlayer.start(); 
            Mydazui.jixu = true ;
    } 
     
    //停止播放  
    public void stopPlaying(String mFileName) { 
//        mPlayer.release(); 
        mPlayer = null; 
        mPlayer = new MediaPlayer(); 
        try { 	       	
//			File file = new File(mFileName);
//			FileInputStream fis = new FileInputStream(file);
			mPlayer.setDataSource(mFileName);       
			  mPlayer.prepare(); 
//			  fis.reset();
//			  fis.close();
        } catch (IOException e) { 
        } 
    } 

 
//    public void startRecording() { 
//    	mFileName = DazuiActivity.dazuisRecord.getAbsolutePath();
//    	String haomiao = String.valueOf(System.currentTimeMillis());
//        mFileName += "/"+haomiao+".amr";
//        mRecorder = new MediaRecorder(); 
//        //设置音源为Micphone  
//        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); 
//        //设置封装格式  
//        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP); 
//        mRecorder.setOutputFile(mFileName); 
//        //设置编码格式  
//        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); 
// 
//        try { 
//            mRecorder.prepare(); 
//        } catch (IOException e) { 
//        } 
// 
//        mRecorder.start(); 
//    } 
 
    public void stopandper(String mFileName) { 
        mPlayer = new MediaPlayer(); 
        try { 
        	       	
			File file = new File(mFileName);
			FileInputStream fis = new FileInputStream(file);
			mPlayer.setDataSource(fis.getFD());       
			  mPlayer.prepare(); 
			  fis.reset();
			  fis.close();
        } catch (IOException e) { 
        } 
    } 
}
