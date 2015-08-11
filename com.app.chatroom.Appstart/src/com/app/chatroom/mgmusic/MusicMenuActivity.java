package com.app.chatroom.mgmusic;

import java.io.File;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.download.DownFileForMigu;
import com.app.chatroom.util.Commond;
import com.cmsc.cmmusic.common.CMMusicCallback;
import com.cmsc.cmmusic.common.VibrateRingManagerInterface;
import com.cmsc.cmmusic.common.data.DownloadResult;
import com.jianrencun.chatroom.R;

public class MusicMenuActivity extends Activity {
	String musicId;
	String songName;
	Button music_list_menu_cl_btn;// 设置成彩铃
	Button music_list_menu_sj_btn;// 设置成手机铃声
	Button music_list_menu_cancel_btn;
	DownFileForMigu downFileRight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.music_list_menu);

		Intent intent = getIntent();
		if (null != intent) {
			musicId = intent.getStringExtra("musicid");
			songName = intent.getStringExtra("songname");
		}
		System.out.println("musicid:" + musicId);
		initView();
		initListener();
		downFileRight = new DownFileForMigu(getApplicationContext()) {

			@Override
			public void resultData(String result) {
				// TODO Auto-generated method stub
				if ("true".equals(result)) {
					Commond.showToast(
							getApplicationContext(),
							"下载成功！" + "\n文件路径:"
									+ Environment.getExternalStorageDirectory()
									+ File.separator + "咪咕音乐" + File.separator
									+ songName + ".mp3");
					setMyRingtone(Environment.getExternalStorageDirectory()
							+ File.separator + "咪咕音乐" + File.separator
							+ songName + ".mp3");
				}
			}
		};
	}

	void initView() {
		music_list_menu_cl_btn = (Button) findViewById(R.id.music_list_menu_cl_btn);
		music_list_menu_sj_btn = (Button) findViewById(R.id.music_list_menu_sj_btn);
		music_list_menu_cancel_btn = (Button) findViewById(R.id.music_list_menu_cancel_btn);
	}

	void initListener() {
		music_list_menu_cl_btn.setOnClickListener(listener);
		music_list_menu_sj_btn.setOnClickListener(listener);
		music_list_menu_cancel_btn.setOnClickListener(listener);
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.music_list_menu_cl_btn:
				Intent intent = new Intent(getApplicationContext(),
						MusicPayDialog.class);
				intent.putExtra("musicId", musicId);
				intent.putExtra("type", 0);
				startActivity(intent);
				break;
			case R.id.music_list_menu_sj_btn:
				VibrateRingManagerInterface.queryVibrateRingDownloadUrlByNet(
						MusicMenuActivity.this, musicId, false,
						new CMMusicCallback<DownloadResult>() {
							@Override
							public void operationResult(
									final DownloadResult downloadResult) {
								if (null != downloadResult) {
									Commond.showToast(getApplicationContext(),
											downloadResult.getResMsg());
									System.out.println(downloadResult
											.getDownUrl());
									Commond.showToast(getApplicationContext(),
											"开始下载");
									downFileRight.down(
											downloadResult.getDownUrl(),
											songName,
											Environment
													.getExternalStorageDirectory()
													+ File.separator + "咪咕音乐",
											ConstantsJrc.PROJECT_PATH + "咪咕音乐",
											null);
								}

							}
						});
				finish();
				break;
			case R.id.music_list_menu_cancel_btn:
				finish();
				break;
			default:
				break;
			}
		}
	};

	// 设置--铃声的具体方法
	public void setMyRingtone(String path) {
		File sdfile = new File(path);
		ContentValues values = new ContentValues();
		values.put(MediaStore.MediaColumns.DATA, sdfile.getAbsolutePath());
		values.put(MediaStore.MediaColumns.TITLE, sdfile.getName());
		values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/*");
		values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
		values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
		values.put(MediaStore.Audio.Media.IS_ALARM, false);
		values.put(MediaStore.Audio.Media.IS_MUSIC, false);

		Uri uri = MediaStore.Audio.Media.getContentUriForPath(sdfile
				.getAbsolutePath());
		Uri newUri = this.getContentResolver().insert(uri, values);
		RingtoneManager.setActualDefaultRingtoneUri(this,
				RingtoneManager.TYPE_RINGTONE, newUri);
		Commond.showToast(getApplicationContext(), "设置来电铃声成功！");
		System.out.println("setMyRingtone()-----铃声");
	}

}
