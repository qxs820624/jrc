package com.jianrencun.dazui;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.Environment;


public class Comment {
	//将毫秒转化为 时：分：秒 格式 ，例如  00:05:23
	 public  static String calculatTime(int milliSecondTime) {
	  
	  int hour = milliSecondTime /(60*60*1000);
	  int minute = (milliSecondTime - hour*60*60*1000)/(60*1000);
	  int seconds = (milliSecondTime - hour*60*60*1000 - minute*60*1000)/1000;
	  
	  if(seconds >= 60 )
	  {
	   seconds = seconds % 60;
	      minute+=seconds/60;
	  }
	  if(minute >= 60)
	  {
	    minute = minute % 60;
	    hour  += minute/60;
	  }
	  
	  String sh = "";
	  String sm ="";
	  String ss = "";
	  if(hour <10) {
	     sh = "0" + String.valueOf(hour);
	  }else {
	     sh = String.valueOf(hour);
	  }
	  if(minute <10) {
	     sm = "0" + String.valueOf(minute);
	  }else {
	     sm = String.valueOf(minute);
	  }
	  if(seconds <10) {
	     ss = "0" + String.valueOf(seconds);
	  }else {
	     ss = String.valueOf(seconds);
	  }
	  
	  return sm+":"+ ss;
	 }
		/**
		 * 检测SD卡是否可用
		 * 
		 * @return
		 */
		public static boolean getSDCardMount() {
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				return true;
			} else {
				return false;
			}
		}
		public static String getMd5Hash(String plainText) {
			StringBuffer buf = null;
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(plainText.getBytes());
				byte b[] = md.digest();
				int i;
				buf = new StringBuffer("");
				for (int offset = 0; offset < b.length; offset++) {
					i = b[offset];
					if (i < 0)
						i += 256;
					if (i < 16)
						buf.append("0");
					buf.append(Integer.toHexString(i));
				}
				return buf.toString().substring(8, 16);// 返回8仿

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		/**
		 * 将url返回的内容保存为文件
		 * 
		 * @param url
		 */
		public static void urlToFile(Context context, String url, String filename) {
			try {
				url= url.replaceAll(" ", "%20");
				HttpGet httpRequest = new HttpGet(url);
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = (HttpResponse) httpclient
						.execute(httpRequest);
				HttpEntity entity = response.getEntity();
				BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(
						entity);
				InputStream s = bufferedHttpEntity.getContent();
				// bitmap = BitmapFactory.decodeStream(is);
				FileOutputStream fos = new FileOutputStream(filename);
				byte[] buffer = new byte[1024];
				int len = s.read(buffer);
				while (len > 0) {
					fos.write(buffer, 0, len);
					len = s.read(buffer);
				}
				fos.flush();
				fos.close();
				fos = null;
				s.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		/////////////////////
		///转换 字节转换为kb
		/////////////////
		
//		public static String CountSize(long size)
//		 {
//		double baseKB = 1024.00, baseMB = 1024 * 1024.00;
//		 String strSize = "";
//		 if(size < baseMB)
//		 {
//		 strSize = String.format("{0:0.##} KB",(size/baseKB));
//		 }
//		 else if(size > baseMB)
//		 {
//		 strSize = String.format("{0:0.##} MB",(size/baseMB));
//		 }
//		 return strSize;
//		 }
}
