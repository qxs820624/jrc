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
import android.widget.RelativeLayout;

import com.app.chatroom.adapter.AllListAdapter;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.util.Commond;
import com.cmsc.cmmusic.common.MusicQueryInterface;
import com.cmsc.cmmusic.common.data.ChartInfo;
import com.cmsc.cmmusic.common.data.ChartListRsp;
import com.jianrencun.chatroom.R;

/**
 * 榜单
 * 
 * @author Administrator
 * 
 */
public class AllListActivity extends Activity {

	ImageButton alllist_close_btn;
	RelativeLayout alllist_progressbar_RelativeLayout;
	public ArrayList<ChartInfo> chartInfoList = new ArrayList<ChartInfo>();// 榜单列表
	GetListThread getListThread;
	AllListAdapter allListAdapter;
	ListView alllist_listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alllist_dialog);
		initView();
		initListener();
		getListThread = new GetListThread();
		getListThread.start();

		alllist_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String ChartCode = allListAdapter.list.get(position)
						.getChartCode();
				Intent intent = new Intent(getApplicationContext(),
						MusicListActivity.class);
				intent.putExtra("ChartCode", ChartCode);
				startActivity(intent);
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
					alllist_progressbar_RelativeLayout
							.setVisibility(View.VISIBLE);
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				try {
					alllist_progressbar_RelativeLayout.setVisibility(View.GONE);
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			}
		};
	};

	private void getAllList() throws ClientProtocolException, IOException {
		listhandler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);

		ChartListRsp clist = MusicQueryInterface.getChartInfo(
				getApplicationContext(), 1, 30);
		System.out.println("榜单：" + clist.getResCode() + ","
				+ clist.getResCounter());
		chartInfoList = (ArrayList<ChartInfo>) clist.getChartInfos();
		listhandler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		listhandler.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (chartInfoList != null) {
						allListAdapter = new AllListAdapter(
								AllListActivity.this, chartInfoList,
								alllist_listview);
						alllist_listview.setAdapter(allListAdapter);
						alllist_listview.requestLayout();
					} else {
						Commond.showToast(getApplicationContext(), "网络连接异常");
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

	void initView() {
		alllist_close_btn = (ImageButton) findViewById(R.id.alllist_close_btn);
		alllist_progressbar_RelativeLayout = (RelativeLayout) findViewById(R.id.alllist_progressbar_RelativeLayout);
		alllist_listview = (ListView) findViewById(R.id.alllist_listview);
	}

	void initListener() {
		alllist_close_btn.setOnClickListener(listener);
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.alllist_close_btn:
				finish();
				break;

			default:
				break;
			}
		}
	};
}
