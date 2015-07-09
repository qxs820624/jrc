//package com.jianrencun.dazui;
//
//import com.bofangqi.R;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.media.MediaPlayer;
//import android.widget.Toast;
//
//public class Mediaplayer {
//	private  MediaPlayer mp;
//	
//	public void initplay(){
//		if(mp == null){
//			mp = new MediaPlayer();
//		}
//	}
//
//	public void prepar(String path){
//		if(null == mp){
//			mp = new MediaPlayer();
//		}
//		mp.reset();
//		try {
//			mp.setDataSource(path);
//			mp.prepare();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//		
//	}
//
//	
//	public void play(){
//		if(mp == null ){
//			prepar();
//		}		
//		mp.start();
//		
//		isplay = true;
//		playbnt.setImageResource(R.drawable.m_pause);
//		
//	}
//	public void puse(){
//		if(null != mp){
//			if(mp.isPlaying()){
//				mp.pause();
//			}
//		}
//	}
//	public void sys(){
//		countindex--;
//		if(countindex == -1){
//			countindex =list.size()-1;
//		}
//		
//		prepar();
//		info();
//		play();
//	}
//	public void getintent(){
//		Intent it = getIntent();
//        int id = it.getIntExtra("id", 1);
//        Toast.makeText(this, id+"hjkh", 2000).show();
//        countindex=id;
//    	if(null == mp){
//			mp = new MediaPlayer();
//		}
//		mp.reset();
////		try {
////			mp.setDataSource(list.get(countindex%list.size()).getPath());
////			mp.prepare();
////		} catch (Exception e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		} 
////        info();
//       
//        
//   
//		
//	}
//	public void getseek(int pos){
//		if(mp!= null){
//			mp.seekTo(pos);
//		}
//		play();
//	}	
//	private void release() {
//		if(null != mp) {
//			if(mp.isPlaying()) {
//				mp.stop();
//			}
//			mp.release();
//			mp = null;
//		}
//	}
//}
