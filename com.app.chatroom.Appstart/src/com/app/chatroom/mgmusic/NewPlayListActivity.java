package com.app.chatroom.mgmusic;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

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

import com.app.chatroom.adapter.AllNewPlayAdapter;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.json.bean.ZjBean;
import com.app.chatroom.json.bean.ZjMusicBean;
import com.app.chatroom.ui.HttpBaseActivitytwo;
import com.app.chatroom.util.Commond;
import com.cmsc.cmmusic.common.MusicQueryInterface;
import com.cmsc.cmmusic.common.data.MusicInfo;
import com.cmsc.cmmusic.common.data.MusicInfoResult;
import com.duom.fjz.iteminfo.MarqueeTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jianrencun.chatroom.R;

public class NewPlayListActivity extends HttpBaseActivitytwo {
	ArrayList<ZjBean> zjlist = new ArrayList<ZjBean>();// 专辑列表
	ListView newplaylist_listview;
	ImageButton newplaylist_close_btn;
	RelativeLayout newplaylist_progressbar_RelativeLayout;
	AllNewPlayAdapter allNewListAdapter;
	String url = "";
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
	GetMusicInfoThread getMusicInfoThread;// 列表获取歌曲信息
	GetMusicInfoThread2 getMusicInfoThread2;// 更多按钮获取歌曲信息
	MusicInfo mMusicInfo;
	ZjBean zjBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_playlist_dialog);
		initView();
		initListener();
		Intent intent = getIntent();
		url = intent.getStringExtra("url");
		zjlist.clear();
		if (!"".equals(url)) {
			StringBuffer data = new StringBuffer();
			// 请求网络验证登陆
			HttpRequestTask request = new HttpRequestTask();
			request.execute(url, data.toString());
		} else {
			Commond.showToast(getApplicationContext(), "请求链接异常");
		}
		musicPlayer = new MusicPlayer(music_list_progressBar);
		newplaylist_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				musicSelectPosition = position;
				// TODO Auto-generated method stub
				zjBean = allNewListAdapter.list.get(position);
				System.out.println(zjBean);
				if (music_play_RelativeLayout.getVisibility() == View.GONE) {
					music_play_RelativeLayout.setVisibility(View.VISIBLE);
				}
				getMusicInfoThread = new GetMusicInfoThread();
				getMusicInfoThread.start();
			}
		});

	}

	void initView() {
		newplaylist_listview = (ListView) findViewById(R.id.newplaylist_listview);
		newplaylist_close_btn = (ImageButton) findViewById(R.id.newplaylist_close_btn);
		newplaylist_progressbar_RelativeLayout = (RelativeLayout) findViewById(R.id.newplaylist_progressbar_RelativeLayout);
		music_list_progressBar = (ProgressBar) findViewById(R.id.music_list_progressBar);
		music_list_next_ImageView = (ImageButton) findViewById(R.id.music_list_next_ImageView);
		music_list_play_ImageView = (ImageButton) findViewById(R.id.music_list_play_ImageView);
		music_play_RelativeLayout = (RelativeLayout) findViewById(R.id.music_play_RelativeLayout);
		music_list_musicname_textView = (MarqueeTextView) findViewById(R.id.music_list_musicname_textView);
		music_list_singername_textView = (MarqueeTextView) findViewById(R.id.music_list_singername_textView);
		music_list_audio_progressbar = (ProgressBar) findViewById(R.id.music_list_audio_progressbar);
	}

	void initListener() {
		newplaylist_close_btn.setOnClickListener(listener);
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.newplaylist_close_btn:
				finish();
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

			ZjBean zjb = (ZjBean) v.getTag();
			getMusicInfoThread2 = new GetMusicInfoThread2(zjb);
			getMusicInfoThread2.start();
		}
	};

	@Override
	public void resultData(String url, String result) {
		// TODO Auto-generated method stub
		System.out.println(result);
		ZjMusicBean zjmusicBean = new ZjMusicBean();
		if (!"".equals(result) && result != null) {
			Gson gson = new Gson();
			zjmusicBean = gson.fromJson(result, new TypeToken<ZjMusicBean>() {
			}.getType());
			zjlist = zjmusicBean.getMusic();
			System.out.println(zjlist.size() + "个");
			allNewListAdapter = new AllNewPlayAdapter(NewPlayListActivity.this,
					zjlist, newplaylist_listview, mlistener);
			newplaylist_listview.setAdapter(allNewListAdapter);
			newplaylist_listview.requestLayout();
		} else {
			Commond.showToast(getApplicationContext(), "网络连接异常");
		}
		newplaylist_progressbar_RelativeLayout.setVisibility(View.GONE);
	}

	/**
	 * 解析数据
	 * 
	 * @param <T>
	 * @param jsonString
	 * @param objectClass
	 * @return
	 */
	public static <T> ArrayList<T> parseStringArrayList(String jsonString,
			Class<T> objectClass) {
		ArrayList<T> list = new ArrayList<T>();
		try {
			Gson gson = new Gson();
			list = gson.fromJson(jsonString,
					new TypeToken<ArrayList<ZjBean>>() {
					}.getType());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	private class PlayMusicThread extends Thread {
		private boolean _run = true;
		private MusicInfo mu = new MusicInfo();

		public void stopThread(boolean run) {
			this._run = !run;
		}

		public PlayMusicThread(MusicInfo m) {
			// TODO Auto-generated constructor stub
			this.mu = m;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					playmucisfun(mu);
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

	private void playmucisfun(final MusicInfo minfo)
			throws ClientProtocolException, IOException {
		musichandler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);
		System.out.println("歌曲试听地址：" + minfo.getSongListenDir());
		musicPlayer.playUrl(minfo.getSongListenDir());
		musichandler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		musichandler.post(new Runnable() {

			@Override
			public void run() {
				try {

					music_list_play_ImageView
							.setBackgroundResource(R.drawable.music_pause_btn_bg);
					music_list_singername_textView.setText(minfo
							.getSingerName());
					music_list_musicname_textView.setText(minfo.getSongName());
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

	private class GetMusicInfoThread extends Thread {
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
					newplaylist_progressbar_RelativeLayout
							.setVisibility(View.VISIBLE);
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				try {
					newplaylist_progressbar_RelativeLayout
							.setVisibility(View.GONE);
					getMusicInfoThread.stopThread(true);
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			}
		};
	};

	private void getAllList() throws ClientProtocolException, IOException {
		listhandler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);

		MusicInfoResult result = MusicQueryInterface.getMusicInfoByMusicId(
				NewPlayListActivity.this, zjBean.getZJID());
		mMusicInfo = result.getMusicInfo();
		listhandler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		listhandler.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (mMusicInfo != null) {
						plyaMusicThread = new PlayMusicThread(mMusicInfo);
						plyaMusicThread.start();
					} else {
						Commond.showToast(getApplicationContext(), "网络出错");
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

	private class GetMusicInfoThread2 extends Thread {
		private boolean _run = true;
		private ZjBean mzj = new ZjBean();

		public void stopThread(boolean run) {
			this._run = !run;
		}

		public GetMusicInfoThread2(ZjBean zzz) {
			// TODO Auto-generated constructor stub
			this.mzj = zzz;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					getAllList2(mzj);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Handler listhandler2 = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:
				try {
					newplaylist_progressbar_RelativeLayout
							.setVisibility(View.VISIBLE);
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				try {
					newplaylist_progressbar_RelativeLayout
							.setVisibility(View.GONE);
					getMusicInfoThread.stopThread(true);
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			}
		};
	};

	private void getAllList2(ZjBean zj) throws ClientProtocolException,
			IOException {
		listhandler2.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);

		MusicInfoResult result = MusicQueryInterface.getMusicInfoByMusicId(
				NewPlayListActivity.this, zj.getZJID());
		mMusicInfo = result.getMusicInfo();
		listhandler2.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		listhandler2.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (mMusicInfo != null) {
						Intent intent = new Intent(getApplicationContext(),
								MusicMenuActivity.class);
						intent.putExtra("musicid", mMusicInfo.getMusicId());
						intent.putExtra("songname", mMusicInfo.getSongName());
						startActivity(intent);
					} else {
						Commond.showToast(getApplicationContext(), "网络出错");
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (musicPlayer != null) {
			musicPlayer.stop();
			musicPlayer = null;
		}
	}
}
