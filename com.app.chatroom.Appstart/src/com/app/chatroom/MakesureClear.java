package com.app.chatroom;

import java.io.File;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.util.Commond;

import com.jianrencun.chatroom.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MakesureClear extends Activity {
	private Button msqueding , msquxiao ;
	private TextView tv ;
	private String ss ;
	private Handler mHandler = null;  
	private boolean run = true ;
	private LinearLayout ll ;
	private long yy , xx;
	private boolean flag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sureclear);
		msqueding = (Button)findViewById(R.id.clear_msqueding);
		msquxiao =  (Button)findViewById(R.id.clear_msquxiao);
		ll = (LinearLayout)findViewById(R.id.clear_ll);
		tv = (TextView)findViewById(R.id.clear_mstt);
		
		Intent it = getIntent();
		ss = it.getStringExtra("from");
		if(ss.equalsIgnoreCase("clear")){
			tv.setText("亲，确定要清理缓存吗？");
		}
		
		mHandler = new Handler() {  
            @Override  
            public void handleMessage(Message msg) {  
                if (msg.what == 1) {  
                	run = false ;
                Commond.showToast(MakesureClear.this, "清理完毕！");
            	ll.setVisibility(View.GONE);
                  finish();
                }  
                super.handleMessage(msg);  
            }  
        };        

		msqueding.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 将最后的选择结果 ，通过设置setresult 向回传递结果
			    yy =System.currentTimeMillis();
				new MyThread().start();  
				ll.setVisibility(View.VISIBLE);
			}
		});
		
		msquxiao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish() ;
			}
		});				
	}
	
	public void clear(){
		File file;
		File uidfile =  new File(Environment.getExternalStorageDirectory()
				+ File.separator + getPackageName() + "/donghuazip" + "/uid.js");
		
		File mfile = new File(Environment.getExternalStorageDirectory()
				+ File.separator + getPackageName() + ConstantsJrc.PHOTO_PATH);
		File mfileaudio = new File(Environment.getExternalStorageDirectory()
				+ File.separator + getPackageName() + ConstantsJrc.AUDIO_PATH);
		File mfilezip = new File(Environment.getExternalStorageDirectory()
				+ File.separator + getPackageName() + "/donghuazip");
		if(uidfile.exists()){
			uidfile.delete();
		}
		if(mfilezip.exists()){
			File[] files = mfilezip.listFiles();  
			// 将所有的文件存入ArrayList中,并过滤所有图片格式的文件 
			for (int i = 0; i < files.length; i++) {
				file = files[i]; 		
				deleteDirectory(file.getAbsolutePath())   ; 				
			} 
		}
		if (mfile.exists()) {
			File[] files = mfile.listFiles();  
			// 将所有的文件存入ArrayList中,并过滤所有图片格式的文件 
			for (int i = 0; i < files.length; i++) {
				file = files[i]; 				
				
			    xx = file.lastModified();
			    if(yy - xx > 5*24*60*60*1000){
			    	file.delete();
			      }			    
			} 
		}
		if(mfileaudio.exists()){
			File[] files = mfileaudio.listFiles();  
			// 将所有的文件存入ArrayList中,并过滤所有图片格式的文件 
			for (int i = 0; i < files.length; i++) {
				file = files[i]; 				
			    xx = file.lastModified();
			    if(yy - xx > 1*24*60*60*1000){
			    	file.delete();
			      }			    
			} 	
		}
	}
	  public  class MyThread extends Thread {  
	        public void run() {  
	            	clear();	            	
	                Message msg = new Message();  
	                msg.what = 1;  
	                mHandler.sendMessage(msg);  	             
	        }  
	    }
	  
		public boolean deleteDirectory(String dirPath) {// 删除目录（文件夹）以及目录下的文件
			// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
			if (!dirPath.endsWith(File.separator)) {
				dirPath = dirPath + File.separator;
			}
			File dirFile = new File(dirPath);
			// 如果dir对应的文件不存在，或者不是一个目录，则退出
			if (!dirFile.exists() || !dirFile.isDirectory()) {
				return false;
			}
			flag = true;
			File[] files = dirFile.listFiles();// 获得传入路径下的所有文件
			for (int i = 0; i < files.length; i++) {// 循环遍历删除文件夹下的所有文件(包括子目录)
				if (files[i].isFile()) {// 删除子文件
//					flag = deleteFile(files[i].getAbsolutePath());
					files[i].delete();
					System.out.println(files[i].getAbsolutePath() + " 删除成功");
					if (!flag)
						break;// 如果删除失败，则跳出
				} else {// 运用递归，删除子目录
//					flag = deleteDirectory(files[i].getAbsolutePath());
					files[i].delete();
					if (!flag)
						break;// 如果删除失败，则跳出
				}
			}
			if (!flag)
				return false;
			if (dirFile.delete()) {// 删除当前目录
				return true;
			} else {
				return false;
			}
		}
}
