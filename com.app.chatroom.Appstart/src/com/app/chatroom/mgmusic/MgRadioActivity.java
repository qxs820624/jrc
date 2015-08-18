package com.app.chatroom.mgmusic;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.app.chatroom.adapter.AllZjListAdapter;
import com.app.chatroom.json.bean.ZjBean;
import com.app.chatroom.json.bean.ZjMusicBean;
import com.app.chatroom.ui.HttpBaseActivitytwo;
import com.app.chatroom.util.Commond;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jianrencun.chatroom.R;

public class MgRadioActivity extends HttpBaseActivitytwo {

	ArrayList<ZjBean> zjlist = new ArrayList<ZjBean>();// 专辑列表
	ListView zjlist_listview;
	ImageButton zjlist_close_btn;
	RelativeLayout zjlist_progressbar_RelativeLayout;
	AllZjListAdapter allZjListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zjlist_dialog1);
		initView();
		initListener();
		zjlist.clear();
		String url = "http://366music.com/jme/jme2.json";
		StringBuffer data = new StringBuffer();
		// 请求网络验证登陆
		HttpRequestTask request = new HttpRequestTask();
		request.execute(url, data.toString());

		zjlist_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String zjid = allZjListAdapter.list.get(position).getZJID();
				System.out.println("专辑ID：" + zjid);
				Intent intent = new Intent(getApplicationContext(),
						MusicZjListActivity.class);
				intent.putExtra("AlbumId", zjid);
				startActivity(intent);
			}
		});

	}

	void initView() {
		zjlist_close_btn = (ImageButton) findViewById(R.id.zjlist_close_btn);
		zjlist_listview = (ListView) findViewById(R.id.zjlist_listview);
		zjlist_progressbar_RelativeLayout = (RelativeLayout) findViewById(R.id.zjlist_progressbar_RelativeLayout);
	}

	void initListener() {
		zjlist_close_btn.setOnClickListener(listener);
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.zjlist_close_btn:
				finish();
				break;

			default:
				break;
			}
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
			allZjListAdapter = new AllZjListAdapter(MgRadioActivity.this,
					zjlist, zjlist_listview);
			zjlist_listview.setAdapter(allZjListAdapter);
			zjlist_listview.requestLayout();
		} else {
			Commond.showToast(getApplicationContext(), "网络连接异常");
		}
		zjlist_progressbar_RelativeLayout.setVisibility(View.GONE);
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
}
