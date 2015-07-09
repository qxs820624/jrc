package com.jianrencun.android;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.app.chatroom.Appstart;
import com.app.chatroom.util.Commond;
import com.jianrencun.chatroom.R;
import com.umeng.analytics.MobclickAgent;

public class Savephoto extends Activity{
    private Button queding ,quxiao ;
    private String tip;
    String strFilename , filename ,geshi ,src;
	int i = 0;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.savephoto);
    queding = (Button)findViewById(R.id.savephoto);
    quxiao = (Button)findViewById(R.id.canclesave);
    Intent it = getIntent();
    strFilename = it.getStringExtra("filenamepath");
    filename = it.getStringExtra("filename");
    geshi = it.getStringExtra("geshi");
    src = it.getStringExtra("srcwanzheng");
    
    queding.setOnClickListener(new OnClickListener() {		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Bitmap bt = getPictureFromCache(strFilename);
			// 关闭掉这个Activity
			try {
				saveMyBitmap(filename, bt , geshi);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finish();	
		}
	});
    quxiao.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
	});
    }
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}
	/**
	 * * 从缓存获取图片 * * @return
	 */
	private Bitmap getPictureFromCache(String filename) {
		Bitmap bitmap = null;
		try {
			File file = new File(filename);
			FileInputStream inStream = new FileInputStream(file);
			bitmap = BitmapFactory.decodeStream(inStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	public void saveMyBitmap(String bitName, Bitmap mBitmap ,String geshi) throws Exception {
		File f = null;
		if (mBitmap == null) {
            getbitmap(src, i+"", geshi);
            i++;
            return;
		}
		if (ExistSDCard() == true) {
			f = new File(Appstart.jrcsave + "/" + bitName + geshi);
		} else {
			f = new File(bitName + geshi);
		}
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Commond.showToast(this, "在保存图片时出错：" + e.toString());
		}
		BufferedOutputStream bos = null;
		try {
			 bos = new BufferedOutputStream(new FileOutputStream(f));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (mBitmap != null) {
			mBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
			
		} 
//		else {
//			Picture pic = wv.capturePicture();
//			if (pic.getWidth() > 0 && pic.getHeight() > 0) {
//				mBitmap = Bitmap.createBitmap(pic.getWidth(), pic.getHeight(),
//						Bitmap.Config.ARGB_8888);
//				mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
//			}
//		}

		try {
			bos.flush();
			Commond.showToast(this, "保存成功！" + f.getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void getbitmap(String url ,String name , String geshi) throws Exception{
		 URL urls = new URL(url);
		 File f = null;
		
		 HttpURLConnection conn = (HttpURLConnection) urls.openConnection();
		 conn.setRequestMethod("GET");
		 conn.setConnectTimeout(5 * 1000);
		 InputStream inputStream = conn.getInputStream();
		 byte[] bytes = ReadInputStream(inputStream);
		 if (ExistSDCard() == true) {
				f = new File(Appstart.jrcsave + "/" + name + geshi);
			} else {
				f = new File(name + geshi);
			}
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Commond.showToast(this, "在保存图片时出错：" + e.toString());
			}
		 FileOutputStream fileOutputStream = new FileOutputStream(f);
		 fileOutputStream.write(bytes);
		 fileOutputStream.close();
				if(conn !=null){
					conn.disconnect();
					conn = null ;
				}			
		 }
		 
		public static byte[] ReadInputStream(InputStream inputStream) throws Exception
		 {
		 ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		 byte[] buffer = new byte[1024];
		 int len = 0;
		 while ((len=inputStream.read(buffer)) != -1)
		 {
		 outstream.write(buffer, 0, len);
		 }
		 inputStream.close();
		 return outstream.toByteArray();
		 }
		
		
		private boolean ExistSDCard() {
			if (android.os.Environment.getExternalStorageState().equals(
					android.os.Environment.MEDIA_MOUNTED)) {
				return true;
			} else
				return false;
		}
}
