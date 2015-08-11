package com.app.chatroom.mgmusic;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.app.chatroom.adapter.MusicZjListAdapter;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.util.Commond;
import com.cmsc.cmmusic.common.MusicQueryInterface;
import com.cmsc.cmmusic.common.data.MusicInfo;
import com.cmsc.cmmusic.common.data.MusicListRsp;
import com.duom.fjz.iteminfo.MarqueeTextView;
import com.jianrencun.chatroom.R;

/**
 * 音乐列表
 * 
 * @author Administrator
 * 
 */
public class MusicZjListActivity extends Activity {
	String AlbumId = "";
	ImageButton musiclist_close_btn;
	ListView musiclist_listview;
	RelativeLayout musiclist_progressbar_RelativeLayout;
	public ArrayList<MusicInfo> musicInfoList = new ArrayList<MusicInfo>();// 歌曲列表
	MusicZjListAdapter musiczjListAdapter;
	GetListThread getListThread;
	RelativeLayout music_play_RelativeLayout;
	ProgressBar music_list_progressBar;
	ImageButton music_list_play_ImageView;
	ImageButton music_list_next_ImageView;
	MusicPlayer musicPlayer;
	MarqueeTextView music_list_musicname_textView;
	MarqueeTextView music_list_singername_textView;
	int musicSelectPosition;
	ProgressBar music_list_audio_progressbar;
	PlayMusicThread plyaMusicThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_musiclist_dialog);
		initView();
		initListener();
		Intent intent = getIntent();
		AlbumId = intent.getStringExtra("AlbumId");
		System.out.println("专辑ID:" + AlbumId);
		getListThread = new GetListThread();
		getListThread.start();
		musicPlayer = new MusicPlayer(music_list_progressBar);
		musiclist_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				musicSelectPosition = position;
				MusicInfo m = musiczjListAdapter.list.get(position);
				System.out.println(m);
				if (music_play_RelativeLayout.getVisibility() == View.GONE) {
					music_play_RelativeLayout.setVisibility(View.VISIBLE);
				}
				plyaMusicThread = new PlayMusicThread(musicInfoList);
				plyaMusicThread.start();

			}
		});

	}

	private class GetListThread extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					getAllList();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Handler listhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:
				try {
					musiclist_progressbar_RelativeLayout
							.setVisibility(View.VISIBLE);
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				try {
					musiclist_progressbar_RelativeLayout
							.setVisibility(View.GONE);
					getListThread.stopThread(true);
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			}
		};
	};

	private void getAllList() throws ClientProtocolException, IOException {
		listhandler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);

		MusicListRsp mlist = MusicQueryInterface.getMusicsByAlbumId(
				MusicZjListActivity.this, AlbumId, 1, 30);
		musicInfoList = (ArrayList<MusicInfo>) mlist.getMusics();
		listhandler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		listhandler.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (musicInfoList != null) {
						musiczjListAdapter = new MusicZjListAdapter(
								MusicZjListActivity.this, musicInfoList,
								musiclist_listview, mlistener);
						musiclist_listview.setAdapter(musiczjListAdapter);
						musiclist_listview.requestLayout();
					} else {
						Commond.showToast(getApplicationContext(), "网络出错");
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

	private class PlayMusicThread extends Thread {
		private boolean _run = true;
		private ArrayList<MusicInfo> list = new ArrayList<MusicInfo>();

		public void stopThread(boolean run) {
			this._run = !run;
		}

		public PlayMusicThread(ArrayList<MusicInfo> ls) {
			// TODO Auto-generated constructor stub
			this.list = ls;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					playmucisfun(list);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Handler musichandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:
				try {
					music_list_audio_progressbar.setVisibility(View.VISIBLE);
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				try {
					music_list_audio_progressbar.setVisibility(View.GONE);
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			}
		};
	};

	private void playmucisfun(final ArrayList<MusicInfo> ls)
			throws ClientProtocolException, IOException {
		musichandler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);

		musicPlayer.playUrl(ls.get(musicSelectPosition).getSongListenDir());
		musichandler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		musichandler.post(new Runnable() {

			@Override
			public void run() {
				try {

					music_list_play_ImageView
							.setBackgroundResource(R.drawable.music_pause_btn_bg);
					music_list_singername_textView.setText(ls.get(
							musicSelectPosition).getSingerName());
					music_list_musicname_textView.setText(ls.get(
							musicSelectPosition).getSongName());
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

	void initView() {
		musiclist_close_btn = (ImageButton) findViewById(R.id.musiclist_close_btn);
		musiclist_progressbar_RelativeLayout = (RelativeLayout) findViewById(R.id.musiclist_progressbar_RelativeLayout);
		musiclist_listview = (ListView) findViewById(R.id.musiclist_listview);
		music_list_progressBar = (ProgressBar) findViewById(R.id.music_list_progressBar);
		music_list_next_ImageView = (ImageButton) findViewById(R.id.music_list_next_ImageView);
		music_list_play_ImageView = (ImageButton) findViewById(R.id.music_list_play_ImageView);
		music_play_RelativeLayout = (RelativeLayout) findViewById(R.id.music_play_RelativeLayout);
		music_list_musicname_textView = (MarqueeTextView) findViewById(R.id.music_list_musicname_textView);
		music_list_singername_textView = (MarqueeTextView) findViewById(R.id.music_list_singername_textView);
		music_list_audio_progressbar = (ProgressBar) findViewById(R.id.music_list_audio_progressbar);
	}

	void initListener() {
		musiclist_close_btn.setOnClickListener(listener);
		music_list_play_ImageView.setOnClickListener(listener);
		music_list_next_ImageView.setOnClickListener(listener);
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.musiclist_close_btn:
				finish();
				break;
			case R.id.music_list_play_ImageView:
				if (musicPlayer.isPlay()) {
					musicPlayer.pause();
					music_list_play_ImageView
							.setBackgroundResource(R.drawable.music_play_btn_bg);
				} else {
					musicPlayer.play();
					music_list_play_ImageView
							.setBackgroundResource(R.drawable.music_pause_btn_bg);
				}
				break;
			case R.id.music_list_next_ImageView:
				musicSelectPosition++;
				System.out.println("第" + musicSelectPosition + "个");
				if (musicSelectPosition >= musicInfoList.size()) {
					Commond.showToast(getApplicationContext(), "已经是最后一首了");
					return;
				} else {
					plyaMusicThread = new PlayMusicThread(musicInfoList);
					plyaMusicThread.start();
				}
				break;
			default:
				break;
			}
		}
	};

	OnClickListener mlistener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			MusicInfo mInfo = (MusicInfo) v.getTag();
			Intent intent = new Intent(getApplicationContext(),
					MusicMenuActivity.class);
			intent.putExtra("musicid", mInfo.getMusicId());
			intent.putExtra("songname", mInfo.getSongName());
			startActivity(intent);
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (musicPlayer != null) {
			musicPlayer.stop();
			musicPlayer = null;
		}
	}

}
