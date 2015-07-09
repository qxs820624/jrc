package com.jianrencun.game;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.otherui.VillageUserInfoDialog;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;
import com.app.chatroom.util.PhoneInfo;
import com.duom.fjz.iteminfo.WebviewDonghua;
import com.jianrencun.android.Details;
import com.jianrencun.android.HttpBaseActivity;
import com.jianrencun.android.HttpBaseActivity.HttpRequestTask;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.DazuiAdapter2;
import com.jianrencun.dazui.Mydazui;
import com.tencent.plus.DensityUtil;

public class Gameroom extends HttpBaseActivity {
	private Button back, bnt, left, right, call;
	private ListView lv;
	private View view;
	private TextView tv, tv_ck;
	private RelativeLayout rl;
	private String header, nick;
	private int gid;
	private LinearLayout ll;
	private List<Gameiteminfo> lists = new ArrayList<Gameiteminfo>();
	private Gameiteminfo gitem;
	private GameAdapter ga;
	private List<GameListinfo> lists2 = new ArrayList<GameListinfo>();
	private GameListinfo gitem2;
	private GameListAdapter ga2;
	private GridView gl;
	private TextView title;
	/* 配置文件 */
	SharedPreferences sp;
	SystemSettingUtilSp su;
	private Details dt = new Details();
	private OnClickListener onclick;
	private RelativeLayout gr_change_rl;
	private String btime;

	private GameListAdapter.ViewHolder holder;
	private int po;
	private String pd;

	private int guid;
	private String flg, dlink;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.gameroom);

		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);

		back = (Button) findViewById(R.id.gr_back);
		lv = (ListView) findViewById(R.id.gr_lv);
		gl = (GridView) findViewById(R.id.gr_gv);
		ll = (LinearLayout) findViewById(R.id.game_ll);
		left = (Button) findViewById(R.id.gr_left_bnt);
		right = (Button) findViewById(R.id.gr_right_bnt);
		title = (TextView) findViewById(R.id.gr_title);
		gr_change_rl = (RelativeLayout) findViewById(R.id.gr_change_rl);
		call = (Button) findViewById(R.id.call);

		gl.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gr_change_rl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ll.setVisibility(View.VISIBLE);
				String url = dt.appendNameValue(ConstantsJrc.GAMEROOM, "uid",
						su.getUid());
				url = dt.appendNameValue(url, "token",
						URLEncoder.encode(su.getToken()));
				url = dt.appendNameValue(url, "uid", su.getUid());
				url = dt.appendNameValue(url, "pd", su.getUid());
				url = dt.appendNameValue(
						url,
						"uid",
						URLEncoder.encode(PhoneInfo.getInstance(
								getApplicationContext()).getPackage(
								Gameroom.this)));

				StringBuffer data = new StringBuffer();
				HttpRequestTask request = new HttpRequestTask();
				request.execute(url, data.toString());
			}
		});
		gl.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(Gameroom.this, Paihangbang.class);
				it.putExtra("guid", lists.get(arg2).getGid());
				startActivity(it);
			}
		});
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(Gameroom.this, Paihangbang.class);
				it.putExtra("guid", lists2.get(arg2).getGid());
				startActivity(it);
			}
		});
		call.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(Gameroom.this, Call.class);
				startActivity(it);
			}
		});

		onclick = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				holder = (GameListAdapter.ViewHolder) v.getTag();
				po = holder.position;

				guid = lists2.get(holder.position).getGid();
				flg = lists2.get(holder.position).getFlg();
				dlink = lists2.get(holder.position).getDlink();

				String url = dt.appendNameValue(ConstantsJrc.TIAOZHANTA, "uid",
						su.getUid());
				url = dt.appendNameValue(url, "token",
						URLEncoder.encode(su.getToken()));
				url = dt.appendNameValue(url, "uid", su.getUid());
				url = dt.appendNameValueint(url, "ouid",
						lists2.get(holder.position).getUid());
				url = dt.appendNameValueint(url, "gid",
						lists2.get(holder.position).getGid());
				url = dt.appendNameValueint(url, "id",
						lists2.get(holder.position).getId());

				StringBuffer data = new StringBuffer();
				HttpRequestTask request = new HttpRequestTask();
				request.execute(url, data.toString());
			}
		};

		view = new View(getApplicationContext());
		view = getLayoutInflater().inflate(R.layout.listheader_huodong, null);
		tv = (TextView) view.findViewById(R.id.listheader_tv);
		bnt = (Button) view.findViewById(R.id.listheader_chakanbnt);
		tv_ck = (TextView) view.findViewById(R.id.listheader_chakantv);
		rl = (RelativeLayout) view.findViewById(R.id.listheader_rl);

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		String url = dt.appendNameValue(ConstantsJrc.GAMEROOM, "uid",
				su.getUid());
		url = dt.appendNameValue(url, "token", URLEncoder.encode(su.getToken()));
		url = dt.appendNameValue(url, "uid", su.getUid());
		url = dt.appendNameValue(url, "pkg", URLEncoder.encode(PhoneInfo
				.getInstance(getApplicationContext()).getPackage(this)));

		StringBuffer data = new StringBuffer();
		HttpRequestTask request = new HttpRequestTask();
		request.execute(url, data.toString());

	}

	@Override
	public void resultData(String url, String result) {
		// TODO Auto-generated method stub
		String tip = null;
		if (result == null) {
			// fresh.setVisibility(View.VISIBLE);
			Commond.showToast(Gameroom.this, "小贱提醒 ：当前网络不稳定！");
			return;
		}

		try {
			// //////////////////////////////////////////////正文内容页面
			JSONObject jsonChannel = new JSONObject(result);
			int ret = jsonChannel.optInt("ret");
			tip = URLDecoder.decode(jsonChannel.optString("tip"));
			btime = URLDecoder.decode(jsonChannel.optString("btime"));
			pd = URLDecoder.decode(jsonChannel.optString("pd"));
			// tjd = URLDecoder.decode(jsonChannel.optString("tjd"));
			// tjm =jsonChannel.optInt("tjm");
			if (ret == 0) {
				Commond.showToast(Gameroom.this, tip);
				return;
			}
			ll.setVisibility(View.GONE);
			if (url.contains(ConstantsJrc.GAMEROOM)) {
				String tt = URLDecoder.decode(jsonChannel.optString("title"));
				if (TextUtils.isEmpty(tt)) {
					title.setVisibility(View.GONE);
					gr_change_rl.setVisibility(View.GONE);
				} else {
					title.setVisibility(View.VISIBLE);
					title.setText(tt);
					gr_change_rl.setVisibility(View.VISIBLE);
				}

				JSONArray jsonItems = jsonChannel.optJSONArray("games");
				if (jsonItems != null) {
					lists.clear();
					// tip = "获取成功！";
					for (int i = 0; i < jsonItems.length(); i++) {
						JSONObject jsonItem = jsonItems.getJSONObject(i);
						nick = URLDecoder.decode(jsonItem.optString("name"));
						header = URLDecoder.decode(jsonItem.optString("icon"));
						gid = jsonItem.optInt("gid");
						gitem = new Gameiteminfo(header, nick, gid);
						lists.add(gitem);
					}
					if (lists.size() != 0) {
						ViewGroup.LayoutParams params = gl.getLayoutParams();
						params.width = Commond.dip2px(Gameroom.this, 81)
								* lists.size();
						gl.setLayoutParams(params);
						gl.setNumColumns(lists.size());
						ga = new GameAdapter(Gameroom.this, lists, gl);
						gl.setAdapter(ga);
						if (lists.size() >= 3) {
							left.setVisibility(View.VISIBLE);
							right.setVisibility(View.VISIBLE);
						}
						// gl.setNumColumns(lists.size());
						// gl.setStretchMode(GridView.NO_STRETCH);
						// ga = new GameAdapter(Gameroom.this, lists, gl);
						// gl.setAdapter(ga);
					}
				}
				JSONArray jsonItem1 = jsonChannel.optJSONArray("items");
				if (jsonItem1 != null) {
					lists2.clear();
					// tip = "获取成功！";
					for (int i = 0; i < jsonItem1.length(); i++) {
						JSONObject jsonItem = jsonItem1.getJSONObject(i);
						int id = jsonItem.optInt("id");
						int gid = jsonItem.optInt("gid");
						int uid = jsonItem.optInt("uid");
						String header = URLDecoder.decode(jsonItem
								.optString("header"));
						String nick = URLDecoder.decode(jsonItem
								.optString("nick"));
						String nick_c = URLDecoder.decode(jsonItem
								.optString("nick_c"));
						String desc = URLDecoder.decode(jsonItem
								.optString("desc"));
						String desc_c = URLDecoder.decode(jsonItem
								.optString("desc_c"));
						String flg = URLDecoder.decode(jsonItem
								.optString("flg"));
						String dlink = URLDecoder.decode(jsonItem
								.optString("dlink"));
						String date = URLDecoder.decode(jsonItem
								.optString("time"));
						gitem2 = new GameListinfo(gid, uid, header, nick,
								nick_c, desc, desc_c, flg, dlink, date, id, 0);
						lists2.add(gitem2);
					}
					if (lists2.size() != 0) {
						ga2 = new GameListAdapter(Gameroom.this, lists2, lv,
								onclick, btime);
						lv.setAdapter(ga2);
					}
				}
			} else if (url.contains(ConstantsJrc.TIAOZHANTA)) {

				if (guid == 2) {
					Intent it = new Intent();
					it.setClass(Gameroom.this, Zhuanpan.class);
					startActivity(it);
				} else {
					if (flg.equalsIgnoreCase("ceshi")) {
						
//						StringBuffer data = new StringBuffer();
//						String gameurl;
//						gameurl = Details.geturl(ConstantsJrc.WEB_DONGHUA);
//						help = URLEncoder.encode(help);
//						url = dt.appendNameValue(url, "path", help);
//						url = Details.appendNameValueint(url, "flg", flg);
//						url = dt.appendNameValue(url, "token", URLEncoder.encode(su.getToken()));
//						// 请求网络验证登陆
//						HttpRequestTask request = new HttpRequestTask();
//						request.execute(url, data.toString());   
						
					} else {
						final PackageManager pm = getPackageManager();
						Intent launch = pm.getLaunchIntentForPackage(flg);
						if (launch != null) {
							lists2.get(po).setZt(1);
							if (ga2 != null) {
								ga2.notifyDataSetChanged();
							}

							Bundle bundle = new Bundle();
							bundle.putInt("uid", Integer.parseInt(su.getUid()));
							bundle.putString("token", su.getToken());
							launch.putExtras(bundle);
							startActivity(launch);
						} else {
							Uri uri = Uri.parse(dlink);// id为包名
							Intent it = new Intent(Intent.ACTION_VIEW, uri);
							startActivity(it);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			// pb.setVisibility(View.GONE);
			// ll.setVisibility(View.GONE);
			Commond.showToast(Gameroom.this, "操作失败！请检查网络！");
		}
	}
	

}
