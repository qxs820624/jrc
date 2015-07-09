package com.app.chatroom.mgmusic;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

public class MusicPlayer implements OnBufferingUpdateListener,
		OnCompletionListener, OnPreparedListener {
	public MediaPlayer mediaPlayer; // 媒体播放器
	private ProgressBar progBar; // 进度
	private Timer mTimer = new Timer(); // 计时器

	// 初始化播放器
	public MusicPlayer(ProgressBar pBar) {
		super();
		this.progBar = pBar;
		try {
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
			mediaPlayer.setOnBufferingUpdateListener(this);
			mediaPlayer.setOnPreparedListener(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 每一秒触发一次
		mTimer.schedule(timerTask, 0, 1000);
	}

	// 计时器
	TimerTask timerTask = new TimerTask() {

		@Override
		public void run() {
			if (mediaPlayer == null)
				return;
			if (mediaPlayer.isPlaying() && progBar.isPressed() == false) {
				handler.sendEmptyMessage(0); // 发送消息
			}
		}
	};

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int position = mediaPlayer.getCurrentPosition();
			int duration = mediaPlayer.getDuration();
			if (duration > 0) {
				// 计算进度（获取进度条最大刻度*当前音乐播放位置 / 当前音乐时长）
				long pos = progBar.getMax() * position / duration;
				progBar.setProgress((int) pos);
			}
		};
	};

	public void play() {
		mediaPlayer.start();
	}

	/**
	 * 
	 * @param url
	 *            url地址
	 */
	public void playUrl(String url) {
		try {
			mediaPlayer.reset();
			mediaPlayer.setDataSource(url); // 设置数据源
			mediaPlayer.prepare(); // prepare自动播放
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 暂停
	public void pause() {
		mediaPlayer.pause();
	}

	// 停止
	public void stop() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	// 是否正在播放
	public boolean isPlay() {
		return mediaPlayer.isPlaying();
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		mp.start();
		Log.e("mediaPlayer", "onPrepared");
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		Log.e("mediaPlayer", "onCompletion");
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		// TODO Auto-generated method stub
		// progBar.setSecondaryProgress(percent);
		int currentProgress = progBar.getMax()
				* mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration();
		Log.e(currentProgress + "% play", percent + " buffer");
	}

}
