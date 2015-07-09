package com.app.chatroom.ui;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.app.chatroom.adapter.RoomListAdapter;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.http.HttpEnterRoom;
import com.app.chatroom.json.GetMessageJson;
import com.app.chatroom.json.MessageJson;
import com.app.chatroom.json.RoomListJson;
import com.app.chatroom.json.bean.ChatMessageBean;
import com.app.chatroom.json.bean.MessageBean;
import com.app.chatroom.json.bean.RoomListBean;
import com.app.chatroom.otherui.Message0Dialog;
import com.app.chatroom.otherui.Message1Dialog;
import com.app.chatroom.otherui.MessageSystemDialog;
import com.app.chatroom.otherui.SystemMsgWebDialog;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;
import com.app.chatroom.util.PhoneInfo;
import com.app.chatroom.util.SystemUtil;
import com.duom.fjz.iteminfo.Tixing;
import com.jianrencun.chatroom.R;

public class RoomListActivity extends HttpBaseActivitytwo {
	int roomID = 0;
	String header = "";
	int type = 0;
	int flg = 0;
	public RelativeLayout enterroom_progressbar_RelativeLayout;
	public ImageButton enterroom_close_btn;
	public ListView roomListView;
	public ArrayList<RoomListBean> roomlist = new ArrayList<RoomListBean>();
	public ArrayList<RoomListBean> roommorelist = new ArrayList<RoomListBean>();
	public ArrayList<MessageBean> msgList = new ArrayList<MessageBean>();
	RoomListAdapter roomListAdapter;
	SharedPreferences sp;
	SystemSettingUtilSp su;
	private Dialog dialog = null;
	boolean IsEnterRoom = true;
	EnterRoomThread enterRoomThread;
	ArrayList<MessageBean> messageList = new ArrayList<MessageBean>();
	// 全新加载数据
	private View vFooter;
	private LinearLayout loading;
	boolean isFoot = false;
	int pagecount = 2;
	boolean isfirst = true;
	ArrayList<String> dlpl = new ArrayList<String>();
	ArrayList<ChatMessageBean> chatmsglist = new ArrayList<ChatMessageBean>();
	public static boolean gointochat = false ;
	private int where ;
	private String tp ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enterroom_dialog);
		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
		
		Intent it = getIntent();
		tp = it.getStringExtra("tp");
		where = it.getIntExtra("where", 0);
		if(!TextUtils.isEmpty(tp)){
			Intent toit = new Intent() ;
			toit.setClass(this, Tixing.class);
			toit.putExtra("where", where);
			toit.putExtra("tp", tp);
			startActivity(toit);
			}
		
		enterroom_progressbar_RelativeLayout = (RelativeLayout) findViewById(R.id.enterroom_progressbar_RelativeLayout);
		enterroom_close_btn = (ImageButton) findViewById(R.id.enterroom_close_btn);
		roomListView = (ListView) findViewById(R.id.enterroom_listview);
		// 进度条实例化，为了返回销毁
		dialog = new Dialog(this, R.style.theme_dialog_alert);
		dialog.setContentView(R.layout.dialog_layout);

		vFooter = LayoutInflater.from(this).inflate(R.layout.list_footer, null);
		loading = (LinearLayout) vFooter.findViewById(R.id.list_footer);
		loading.setVisibility(View.GONE);
		if (isfirst) {
			roomlist.clear();
			loading.setVisibility(View.GONE);
			String url = ConstantsJrc.ROOMS + "?" + "uid=" + su.getUid()
					+ "&pd=";
			StringBuffer data = new StringBuffer();
			// 请求网络验证登陆
			HttpRequestTask request = new HttpRequestTask();
			request.execute(url, data.toString());
		}
		roomListView.addFooterView(vFooter);
		roomListView.setFooterDividersEnabled(false);
		roomListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					if (view.getLastVisiblePosition() == view.getCount() - 1) {

						isFoot = true;
						String url = ConstantsJrc.ROOMS + "?" + "uid="
								+ su.getUid() + "&page=" + pagecount++;
						if (!dlpl.contains(url)) {
							loading.setVisibility(View.VISIBLE);
							StringBuffer data = new StringBuffer();
							// 请求网络验证登陆
							HttpRequestTask request = new HttpRequestTask();
							request.execute(url, data.toString());
							dlpl.add(url);
						}
					}
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub

			}
		});

		roomListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position >= 0) {
					roomID = roomlist.get(position).getId();
					header = roomlist.get(position).getHeader_1();
					type = roomlist.get(position).getType();
					flg = roomlist.get(position).getFlg();

					if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
						Commond.showToast(getApplicationContext(),
								"检测到网络网络异常或未开启");
						IsEnterRoom = true;
						return;
					} else {
						if (flg == 1) {
							gointochat = true ;
							if(gointochat = true){
							Intent intent = new Intent(getApplicationContext(),
									SystemMsgWebDialog.class);
							intent.putExtra(
									"link",
									ConstantsJrc.MAINMORE
											+ "?uid="
											+ su.getUid()
											+ "&flg=7&w="
											+ PhoneInfo
													.getInstance(
															getApplicationContext())
													.getWidth(
															RoomListActivity.this)
											+ "&pkg="
											+ PhoneInfo
													.getInstance(
															getApplicationContext())
													.getPackage(
															RoomListActivity.this)
											+ "&ver="
											+ PhoneInfo
													.getInstance(
															getApplicationContext())
													.getVersionName(
															RoomListActivity.this)
											+ "&rid=" + roomID);
							intent.putExtra("type", "7");
							startActivity(intent);							
						}
						} else {
							if (IsEnterRoom) {
								IsEnterRoom = false;
								enterRoomThread = new EnterRoomThread();
								enterRoomThread.start();
							} else {
								dialog.setCancelable(true);
								dialog.show();
							}
						}

					}
				}
			}
		});
		enterroom_close_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			RoomListBean roombean = (RoomListBean) v.getTag();
			Intent intent = new Intent(getApplicationContext(),
					SystemMsgWebDialog.class);
			intent.putExtra(
					"link",
					ConstantsJrc.MAINMORE
							+ "?uid="
							+ su.getUid()
							+ "&flg=7&w="
							+ PhoneInfo.getInstance(getApplicationContext())
									.getWidth(RoomListActivity.this)
							+ "&pkg="
							+ PhoneInfo.getInstance(getApplicationContext())
									.getPackage(RoomListActivity.this)
							+ "&ver="
							+ PhoneInfo.getInstance(getApplicationContext())
									.getVersionName(RoomListActivity.this)
							+ "&rid=" + roombean.getId());

			intent.putExtra("roomtype", roombean.getType());
			intent.putExtra("type", "7");
			startActivity(intent);
		}
	};

	private class EnterRoomThread extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					enterRoom();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Handler roomhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:
				try {
			
				dialog.setCancelable(true);
				dialog.show();
				
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				try {
				dialog.cancel();
				enterRoomThread.stopThread(false);
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			}
		};
	};

	private void enterRoom() throws ClientProtocolException, IOException {
		roomhandler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);

		final String result = HttpEnterRoom
				.EnterRoomGet(roomID, su.getUid().toString(), su.getSex(),
						PhoneInfo.getInstance(getApplicationContext())
								.getIMEINumber(),
						PhoneInfo.getInstance(getApplicationContext())
								.getIMSINumber(), URLEncoder.encode(PhoneInfo
								.getInstance(getApplicationContext())
								.getPhoneType()),
						PhoneInfo.getInstance(getApplicationContext()).getOS(),
						PhoneInfo.getInstance(getApplicationContext())
								.getWidth(this),
						PhoneInfo.getInstance(getApplicationContext())
								.getHight(this),
						PhoneInfo.getInstance(getApplicationContext())
								.getVersionCode(this),
						PhoneInfo.getInstance(getApplicationContext())
								.getVersionName(this), "", PhoneInfo
								.getInstance(getApplicationContext())
								.getSimNumber(),
						PhoneInfo.getInstance(getApplicationContext())
								.getLanguage(),
						PhoneInfo.getInstance(getApplicationContext())
								.getPackage(this), su.getToken());

		// System.out.println(result);
		GetMessageJson gmJson = new GetMessageJson();
		chatmsglist = gmJson.parseJson(result);
		MessageJson mj = new MessageJson();
		messageList = mj.parseJsonAuth(result);

		// System.out.println("进入房间：" + messageList.toString());

		roomhandler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		roomhandler.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (messageList.get(0).getRet().equals("0")) {
						Intent intent0 = new Intent(getApplicationContext(),
								Message0Dialog.class);
						intent0.putExtra("tip", messageList.get(0).getTip()
								.toString());
						intent0.putExtra("ret", messageList.get(0).getRet()
								.toString());
						intent0.putExtra("tp", tp);
						intent0.putExtra("where", where);
						DeliverData deliverData = new DeliverData();
						deliverData.setPersonList(chatmsglist);
						Bundle bundle1 = new Bundle();
						bundle1.putSerializable(KEYGUARD_SERVICE, deliverData);
						intent0.putExtras(bundle1);
						startActivityForResult(intent0, 2);
						enterRoomThread.stopThread(false);

					} else if (messageList.get(0).getRet().equals("1")) {
						Intent intent1 = new Intent(getApplicationContext(),
								Message0Dialog.class);
						intent1.putExtra("tip", messageList.get(0).getTip()
								.toString());
						intent1.putExtra("ret", messageList.get(0).getRet()
								.toString());
						intent1.putExtra("tp", tp);
						intent1.putExtra("where", where);
						if (!messageList.get(0).getAuth().toString().equals("")
								&& messageList.get(0).getAuth() != null) {
							intent1.putExtra("auth", messageList.get(0)
									.getAuth().toString());
						} else {
							intent1.putExtra("auth", "");
						}
						if (!messageList.get(0).getMsg_c().toString()
								.equals("")
								&& messageList.get(0).getMsg_c() != null) {
							intent1.putExtra("msg_c", messageList.get(0)
									.getMsg_c().toString());
						} else {
							intent1.putExtra("msg_c", "");
						}
						if (!messageList.get(0).getNick_c().toString()
								.equals("")
								&& messageList.get(0).getNick_c() != null) {
							intent1.putExtra("nick_c", messageList.get(0)
									.getNick_c().toString());
						} else {
							intent1.putExtra("nick_c", "");
						}

						DeliverData deliverData = new DeliverData();
						deliverData.setPersonList(chatmsglist);
						Bundle bundle1 = new Bundle();
						bundle1.putSerializable(KEYGUARD_SERVICE, deliverData);
						intent1.putExtras(bundle1);
						intent1.putExtra("msg_b", messageList.get(0).getMsg_b()); 
						intent1.putExtra("msg_l", messageList.get(0).getMsg_l());
						intent1.putExtra("qp", messageList.get(0).getQp());
						intent1.putExtra("roomid", roomID);
						intent1.putExtra("header", header);
						intent1.putExtra("roomtype", type);
						startActivityForResult(intent1, 2);
						enterRoomThread.stopThread(false);

					} else if (messageList.get(0).getRet().equals("2")) {
						Intent intent2 = new Intent(getApplicationContext(),
								Message1Dialog.class);
						intent2.putExtra("tip", messageList.get(0).getTip()
								.toString());
						intent2.putExtra("ret", messageList.get(0).getRet()
								.toString());
						if (!messageList.get(0).getAuth().toString().equals("")
								&& messageList.get(0).getAuth() != null) {
							intent2.putExtra("auth", messageList.get(0)
									.getAuth().toString());
						} else {
							intent2.putExtra("auth", "");
						}
						if (!messageList.get(0).getMsg_c().toString()
								.equals("")
								&& messageList.get(0).getMsg_c() != null) {
							intent2.putExtra("msg_c", messageList.get(0)
									.getMsg_c().toString());
						} else {
							intent2.putExtra("msg_c", "");
						}

						if (!messageList.get(0).getNick_c().toString()
								.equals("")
								&& messageList.get(0).getNick_c() != null) {
							intent2.putExtra("nick_c", messageList.get(0)
									.getNick_c().toString());
						} else {
							intent2.putExtra("nick_c", "");
						}
						DeliverData deliverData2 = new DeliverData();
						deliverData2.setPersonList(chatmsglist);
						Bundle bundle2 = new Bundle();
						bundle2.putSerializable(KEYGUARD_SERVICE, deliverData2);
						intent2.putExtras(bundle2);
						intent2.putExtra("msg_b", messageList.get(0).getMsg_b());
						intent2.putExtra("msg_l", messageList.get(0).getMsg_l());
						intent2.putExtra("qp", messageList.get(0).getQp());
						intent2.putExtra("roomid", roomID);
						intent2.putExtra("header", header);
						intent2.putExtra("roomtype", type);
						startActivityForResult(intent2, 2);
						enterRoomThread.stopThread(false);
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
	public void resultData(String url, String result) {
		// TODO Auto-generated method stub
		try {
			// System.out.println("url:" + url);
			// System.out.println("result:" + result);
			RoomListJson roomjson = new RoomListJson();
			roommorelist = roomjson.parseJson(result);
			MessageJson mj = new MessageJson();
			msgList = mj.parseJsonPd(result);
			if (msgList.get(0).getRet().equals("0")) {
				Intent intent = new Intent(getApplicationContext(),
						MessageSystemDialog.class);
				intent.putExtra("ret", msgList.get(0).getRet());
				intent.putExtra("tip", msgList.get(0).getTip());
				startActivity(intent);
			}
			if (roommorelist.size() == 0) {
				roomListView.removeFooterView(vFooter);
				// vFooter.setVisibility(View.GONE);
				// loading.setVisibility(View.GONE);
			}
			for (RoomListBean r : roommorelist) {
				roomlist.add(r);
			}
			roommorelist.clear();
			if (isFoot == false) {
				roomListAdapter = new RoomListAdapter(RoomListActivity.this,
						roomlist, roomListView, listener);
				roomListView.setAdapter(roomListAdapter);
				enterroom_progressbar_RelativeLayout.setVisibility(View.GONE);

			} else {
				roomListView.requestLayout();
				roomListAdapter.notifyDataSetChanged();
				enterroom_progressbar_RelativeLayout.setVisibility(View.GONE);
			}
			isfirst = false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == 2) {
			if (resultCode == 22) {
				IsEnterRoom = data.getBooleanExtra("isenterroom", true);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		gointochat = false ;
		super.onDestroy();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		gointochat = false ;
		super.onResume();
	}
}
