package com.app.chatroom.audio;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnInfoListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.SystemUtil;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Dazuidetatil;

/**
 * 音频录制
 * 
 * @author J.King
 * 
 */

public class VideoRecordDz {

	/** 录音保存路径 **/
	private File myRecAudioDir;
	private MediaRecorder mMediaRecorder01;
	private File myRecAudioFile;
	/** 录音时间 **/
	int second = 0;
	int minute = 0;
	/** 计时器 **/
	Timer timer;
	/** 声音格式 **/
	private final String SUFFIX = ".amr";
	/** 是否停止录音 **/
	private boolean isStopRecord;
	private String length1 = null;
	public Context context;
	SharedPreferences sp;
	SystemSettingUtilSp su;
	private int BASE = 600;
	private int SPACE = 300;// 间隔取样时间
	private ImageView view;

	public VideoRecordDz(Context c, ImageView view) {
		// TODO Auto-generated constructor stub
		this.context = c;
		sp = context.getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				Context.MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
		this.view = view;
	}

	public void init() {
		// 取得sd card路径作为录音文件的位置
		if (SystemUtil.getSDCardMount()) {
			String pathStr = Environment.getExternalStorageDirectory()
					.getPath()
					+ "/"
					+ context.getPackageName()
					+ ConstantsJrc.AUDIO_PATH;
			myRecAudioDir = new File(pathStr);
			if (!myRecAudioDir.exists()) {
				myRecAudioDir.mkdirs();
				// Log.v("录音", "创建录音文件！" + myRecAudioDir.exists());
			}
		} else {
			String pathStr = ConstantsJrc.PROJECT_PATH + context.getPackageName()
					+ ConstantsJrc.AUDIO_PATH;
			myRecAudioDir = new File(pathStr);
			if (!myRecAudioDir.exists()) {
				myRecAudioDir.mkdirs();
				// Log.v("录音", "创建录音文件！" + myRecAudioDir.exists());
			}
		}
	}

	public void start() {
//		second = 0;
//		minute = 0;
//		TimerTask timerTask = new TimerTask() {
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
////				second++;
////				if (second >= 60) {
////					// second = 0;
////					// minute++;
////					stop();
////				}
//
//				Message msg = new Message();
//				Bundle b = new Bundle();
//				b.putInt("time", second);
//				msg.setData(b);
//				// handler.sendMessage(msg);
//				context.videohandler.sendMessage(msg);
//			}
//
//		};
//		timer = new Timer();
//		timer.schedule(timerTask, 50, 1000); // 50毫秒启动计时器
		try {
			String mMinute1 = getTime();
			//System.out.println("当前时间：" + mMinute1);

			myRecAudioFile = new File(myRecAudioDir, mMinute1 + SUFFIX);
			// Log.i("ttt", "path:" + myRecAudioFile.getPath());
			su.setAudioPath(myRecAudioFile.getPath());
			mMediaRecorder01 = new MediaRecorder();

			// 设置录音为麦克风
			mMediaRecorder01.setAudioSource(MediaRecorder.AudioSource.MIC);
			mMediaRecorder01
					.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
			mMediaRecorder01.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

			// 录音文件保存这里
			mMediaRecorder01.setOutputFile(myRecAudioFile.getAbsolutePath());
			mMediaRecorder01.prepare();
			mMediaRecorder01.start();
			updateMicStatus();// 录音动画
			mMediaRecorder01.setOnInfoListener(new OnInfoListener() {

				@Override
				public void onInfo(MediaRecorder mr, int what, int extra) {
					// TODO Auto-generated method stub
					// int a = mr.getMaxAmplitude();
					// Log.i("video", "a:" + a);
				}
			});
			// Log.i("video", "录音中......");
			isStopRecord = false;
		} catch (Exception e) {

		}

	}

	public void stop() {
//		timer.cancel();
		if (myRecAudioFile != null) {
			// 停止录音
			mMediaRecorder01.stop();
			mMediaRecorder01.reset();
			mMediaRecorder01.release();
			mMediaRecorder01 = null;
			// 将录音频文件给Adapter
			// adapter.add(myRecAudioFile.getName());
			DecimalFormat df = new DecimalFormat("#.000");
			if (myRecAudioFile.length() <= 1024 * 1024) {
				length1 = df.format(myRecAudioFile.length() / 1024.0) + "K";
			} else {
				length1 = df.format(myRecAudioFile.length() / 1024.0 / 1024)
						+ "M";
			}
			// System.out.println(length1);
			// System.out.println("停  止" + myRecAudioFile.getName() + "文件大小为："
			//		+ length1);
			su.setAudioLength(String.valueOf(myRecAudioFile.length()));
		}
		isStopRecord = true;
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Bundle b = msg.getData();
			// System.out.println("录音时间：" + minute + ":" + second);
			//System.out.println("录音时间：" + b.getString("time"));
		}

	};

	public void Recycle() {
		if (mMediaRecorder01 != null && !isStopRecord) {
			// 停止录音
			mMediaRecorder01.stop();
			mMediaRecorder01.release();
			mMediaRecorder01 = null;
		}
	}

	private String getTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH：mm：ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String time = formatter.format(curDate);
		//System.out.println("当前时间");
		return time;
	}

	private final Handler mHandler = new Handler();
	private Runnable mUpdateMicStatusTimer = new Runnable() {
		public void run() {
			updateMicStatus();
		}
	};

	private void updateMicStatus() {
		if (mMediaRecorder01 != null && view != null) {
			// int vuSize = 10 * mMediaRecorder.getMaxAmplitude() / 32768;
			try {
				int ratio = mMediaRecorder01.getMaxAmplitude() / BASE;
				int db = 0;// 分贝
				if (ratio > 1)
					db = (int) (20 * Math.log10(ratio));
				switch (db / 4) {
				case 0:
					view.setImageResource(R.drawable.audio_recorder_volume_1);
					break;
				case 1:
					view.setImageResource(R.drawable.audio_recorder_volume_1);
					break;
				case 2:
					view.setImageResource(R.drawable.audio_recorder_volume_2);
					break;
				case 3:
					view.setImageResource(R.drawable.audio_recorder_volume_3);
					break;
				case 4:
					view.setImageResource(R.drawable.audio_recorder_volume_4);
					break;
				case 5:
					view.setImageResource(R.drawable.audio_recorder_volume_5);
					break;
				case 6:
					view.setImageResource(R.drawable.audio_recorder_volume_6);
					break;
				case 7:
					view.setImageResource(R.drawable.audio_recorder_volume_7);
					break;
				case 8:
					view.setImageResource(R.drawable.audio_recorder_volume_8);
					break;
				default:
					view.setImageResource(R.drawable.audio_recorder_volume_8);
					break;
				}
				mHandler.postDelayed(mUpdateMicStatusTimer, SPACE);
			} catch (Exception e) {
			}
			/*
			 * if (db > 1) { vuSize = (int) (20 * Math.log10(db)); Log.i("mic_",
			 * "麦克风的音量的大小：" + vuSize); } else Log.i("mic_", "麦克风的音量的大小：" + 0);
			 */
		}
	}
}
