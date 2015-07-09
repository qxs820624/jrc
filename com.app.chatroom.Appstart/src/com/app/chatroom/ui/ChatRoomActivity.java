package com.app.chatroom.ui;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.client.ClientProtocolException;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.chatroom.adapter.ChatRoomAdapter2;
import com.app.chatroom.adapter.OnLineUserAdapter;
import com.app.chatroom.audio.VideoRecord;
import com.app.chatroom.broadcast.HeadsetPlugReceiver;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.expressionutil.SmileyParser;
import com.app.chatroom.http.HttpExitRoom;
import com.app.chatroom.http.HttpGetMessage;
import com.app.chatroom.http.HttpSendMsg;
import com.app.chatroom.imgzoom.ImageZoom;
import com.app.chatroom.interfaces.Expression;
import com.app.chatroom.json.GetMessageJson;
import com.app.chatroom.json.MessageJson;
import com.app.chatroom.json.OnlineUserListJson;
import com.app.chatroom.json.bean.ChatMessageBean;
import com.app.chatroom.json.bean.MessageBean;
import com.app.chatroom.json.bean.OnlineUserBean;
import com.app.chatroom.otherui.ChatToolsDialog;
import com.app.chatroom.otherui.MainTopRightDialog;
import com.app.chatroom.otherui.Message0Dialog;
import com.app.chatroom.otherui.MessageSystemDialog;
import com.app.chatroom.otherui.VillageUserInfoDialog;
import com.app.chatroom.pic.BitmapCacheBguanzhu;
import com.app.chatroom.pic.BitmapCacheBlack;
import com.app.chatroom.pic.BitmapCacheChatRoom;
import com.app.chatroom.pic.BitmapCacheOnline;
import com.app.chatroom.pic.BitmapCacheVillage;
import com.app.chatroom.pic.NetImageViewCache;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;
import com.app.chatroom.util.DateManager;
import com.app.chatroom.util.PhoneInfo;
import com.app.chatroom.util.SystemUtil;
import com.app.chatroom.view.ExpressionGridView;
import com.app.chatroom.view.MyScrollView;
import com.app.chatroom.view.NetImageView;
import com.duom.fjz.iteminfo.Tixing;
import com.jianrencun.chatroom.R;

/**
 * 聊天室主页面
 * 
 * @author J.King
 * 
 */
@SuppressWarnings("deprecation")
public class ChatRoomActivity extends HttpBaseActivitytwo implements Expression {

	public static ChatRoomActivity instance = null;
	private ViewPager mTabPager;

	private AudioManager audio;
	HeadsetPlugReceiver headsetPlugReceiver;// 耳机拔插监听广播
	/* 页面切换Button */
	private ImageButton mTab1, mTab2, mTab3;
	private ImageView mTabImg;// 动画图片
	private int zero = 0;// 动画图片偏移量
	private int one;// 单个水平动画位移
	private int two;

	/* 当前页卡编号 */
	private int currIndex = 0;
	/* 底部菜单键 */
	private ImageButton addBtn;
	/* 退出键 */
	private ImageButton backBtn;
	/* 表情按钮、拍照按钮、相册按钮 */
	private ImageButton expressionBtn;
	private ImageButton cameraBtn;
	private ImageButton photoBtn;
	/* 文字 语音 切换 */
	private CheckBox chooseBtn;
	/* 是否显示 底部菜单,底部表情 */
	boolean IsShow = true;
	boolean IsShowExpression = true;
	/* 底部菜单布局 */
	private RelativeLayout bottomRelative;
	/* 总页面布局 */
	private RelativeLayout ly;
	/* 文字输入框 */
	private EditText messageEt;
	/* 底部面板 */
	private LinearLayout bottomLayout;
	/* 录音切换按钮 */
	private Button speakBtn;
	/* 发送信息按钮 */
	private Button sendBtn;
	/* 表情功能界面 */
	private LinearLayout linearLayout;
	/* 显示表情总页数的view */
	private LinearLayout expressionCountLayout;
	/* 记录表情当前页数的view */
	private int expressionNum;
	/* 用户权限 */
	private String auth = "";
	private String msg_c = ""; // 文字颜色
	private String nick_c = "";// 昵称颜色
	private int msg_b = 0; // 文字粗体
	private int msg_l = 0; // 是否点击
	private int roomid = 0;// 房间编号
	private String header = "";// 顶部图片
	private int roomtype = 0; // 房间类型
	private int qp = 0;// 气泡类型
	/* 大厅LIST */
	private LinearLayout chattospeakLinearLayout;// 私聊布局
	private ListView leftListView;
	private ChatRoomAdapter2 chatRoomAdapter2;
	private LayoutInflater inflater;
	private TextView chatToUserTextView;
	private ImageButton chatRoomCloseButton;// 关闭对用户说按钮
	private NetImageView chatroom_top_title;
	private RelativeLayout speakRelativeLayout;// 录音背景框
	private ImageView recorder_volumeImageView;// 音频动画
	private ImageButton chatPrivateCloseButton; // 关闭私聊
	private ImageButton chatPrivateOpenButton; // 开启私聊
	private ImageButton chatLiwuImageButton;// 礼物按钮
	public boolean IsPrivate = true;// 是否为私聊
	public String toUid = "";// 聊天对象
	public String toNick = "";// 聊天对象昵称
	public int type = 0;// 大厅聊天类型
	/* 在线村民LIST */
	private LinearLayout onlineLinearLayout;// 人数布局
	private ListView rightListView;
	public OnLineUserAdapter onlineAdapter;
	public TextView onlineCountTextView;
	public ProgressBar onlineProgressBar;
	public RelativeLayout onlineProgerssBarRelativeLayout;
	/* 私聊LIST */
	private ListView midleListView;
	private ChatRoomAdapter2 privateChatRoomAdapter2;

	// 大厅总消息列表
	public ArrayList<ChatMessageBean> chatList = new ArrayList<ChatMessageBean>();
	// 网络接收消息列表
	private ArrayList<ChatMessageBean> receiveList = new ArrayList<ChatMessageBean>();
	// 自动发送队列消息列表
	public ArrayList<ChatMessageBean> queueList = new ArrayList<ChatMessageBean>();
	// 私聊大厅列表
	public ArrayList<ChatMessageBean> privateList = new ArrayList<ChatMessageBean>();
	// 私聊自动发送队列
	public ArrayList<ChatMessageBean> queuePrivateList = new ArrayList<ChatMessageBean>();
	// 系统消息队列
	public ArrayList<ChatMessageBean> systemList = new ArrayList<ChatMessageBean>();
	// 系统消息自动发送队列
	public ArrayList<ChatMessageBean> queueSystemList = new ArrayList<ChatMessageBean>();
	// 消息对话框列表
	public ArrayList<ChatMessageBean> msgDialogList = new ArrayList<ChatMessageBean>();
	// 消息对话框自发送队列
	public ArrayList<ChatMessageBean> queueMsgDialogList = new ArrayList<ChatMessageBean>();
	// 消息列表系统消息
	public ArrayList<ChatMessageBean> msgSystemList = new ArrayList<ChatMessageBean>();
	// 消息列表系统消息自动发送队列
	public ArrayList<ChatMessageBean> queueMsgSystemList = new ArrayList<ChatMessageBean>();
	// 消息预加载数组
	public ArrayList<ChatMessageBean> roomqueueList = new ArrayList<ChatMessageBean>();

	TextView systemTextView; // 系统消息显示
	ImageView systemCloseImageView;// 关闭系统消息
	RelativeLayout systemBar;// 系统消息条
	boolean IsSysBarShow = true;// 是否显示
	boolean IsExit = true;// 是否退出
	private ChatMessageBean chatMessageFromBean;
	public ArrayList<MessageBean> sendMessageList = new ArrayList<MessageBean>();
	public ArrayList<MessageBean> getMessageList = new ArrayList<MessageBean>();
	public ArrayList<MessageBean> exitMessageList = new ArrayList<MessageBean>();
	public ArrayList<String> pdlist = new ArrayList<String>();
	/* 表情 */
	LinearLayout buttomView;
	private int[] imageIds = new int[105];
	private View gridView; // 表情功能的view
	private View dialogView; // 按下选中的表情的状态
	private AbsoluteLayout absoluteLayout; // 将dialogView 现在是在absoluteLayout上面

	public boolean LongTouch = false;
	int temp = 0;
	int temp2 = 0;
	int temp3 = 0;
	/* 音频控制 */
	MediaPlayer myMediaStart; // 启动声音
	public static MediaPlayer myMediaSend; // 发送出声音
	MediaPlayer mediaPlayer; // 播放音频
	boolean isPlay = true;// 是否正在播放
	VideoRecord vr;
	int videoTime = 0;
	/* 配置文件 */
	SharedPreferences sp;
	SystemSettingUtilSp su;
	/* 发送数据，图片，音频地址 */
	String contString = ""; // 聊天内容
	String pfile = ""; // 图片本地URL
	String afile = ""; // 音频本地URL
	String filetype = "";// 聊天室发送文件类型
	SendMsgThread sendThread;// 发送线程
	MySendAudioTimerTask sendaudioThread = null;//
	String picPath = "";

	public Timer receiveTime;// 接收定时器
	public Timer addTime;// 添加定时器
	public Timer systemMsgTime;// 系统消息定时器
	public Timer sendAudio;// 延迟发送音频

	View footView = null;
	public LinearLayout footLinerLayout;
	LayoutInflater li = null;
	/** 自定义Toast **/
	LayoutInflater inflatertoast;
	View view;
	TextView toastTextView;
	Toast toast;
	ImageView receiveModelImageView;
	boolean startaudio = false; // 是否开始录音
	boolean reallyaudio = false;// 是否录音完毕，然后允许发送
	boolean isLastRow = true;

	boolean isBottom = false; // ListView 是否在底部
	boolean isBottom2 = false;//
	private int lastItem;
	private int countlistitem;
	boolean openvideo = false;

	private View vFooter;
	private LinearLayout loading;
	boolean isfoot = false;
	int pagecount = 2;
	ArrayList<String> dlpl = new ArrayList<String>();
	public ArrayList<OnlineUserBean> list = new ArrayList<OnlineUserBean>();
	public ArrayList<OnlineUserBean> morelist = new ArrayList<OnlineUserBean>();
	public ArrayList<MessageBean> msgList = new ArrayList<MessageBean>();

	public boolean isphonepause = true;
	public boolean otherui = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_edit3);
		// registerHeadsetPlugReceiver();
		instance = this;
		// 启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
		audio = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
		// 获取用户权限
		Intent intent = getIntent();
		auth = intent.getStringExtra("auth");
		msg_c = intent.getStringExtra("msg_c");
		nick_c = intent.getStringExtra("nick_c");
		msg_b = intent.getIntExtra("msg_b", 0);
		msg_l = intent.getIntExtra("msg_l", 0);
		roomid = intent.getIntExtra("roomid", 0);
		header = intent.getStringExtra("header");
		roomtype = intent.getIntExtra("roomtype", 0);
		qp = intent.getIntExtra("qp", 0); // 气泡类型
		/////////////////////////////////////////////////////////////////////
//		int where = intent.getIntExtra("where", 0);
//		String tp = intent.getStringExtra("tp");
//		if(!TextUtils.isEmpty(tp)){
//		Intent toit = new Intent() ;
//		toit.setClass(this, Tixing.class);
//		toit.putExtra("where", where);
//		toit.putExtra("tp", tp);
//		startActivity(toit);
//		}
		DeliverData deld = (DeliverData) intent.getExtras().getSerializable(
				KEYGUARD_SERVICE);
		roomqueueList = deld.getMsglist();
		for (ChatMessageBean c : roomqueueList) {
			chatMessageFromBean = new ChatMessageBean();
			chatMessageFromBean.setId(c.getId());
			chatMessageFromBean.setType(c.getType());
			chatMessageFromBean.setIcon(c.getIcon());
			chatMessageFromBean.setFuid(c.getFuid());
			chatMessageFromBean.setFnick(c.getFnick());
			chatMessageFromBean.setFheader(c.getFheader());
			chatMessageFromBean.setTuid(c.getTuid());
			chatMessageFromBean.setTnick(c.getTnick());
			chatMessageFromBean.setContent(c.getContent());
			chatMessageFromBean.setAfile(c.getAfile());
			chatMessageFromBean.setAlen(c.getAlen());
			chatMessageFromBean.setAsize(c.getAsize());
			chatMessageFromBean.setPfile(c.getPfile());
			chatMessageFromBean.setPd(c.getPd());
			chatMessageFromBean.setPw(c.getPw());
			chatMessageFromBean.setPh(c.getPh());
			chatMessageFromBean.setContent_c(c.getContent_c());
			chatMessageFromBean.setNick_c(c.getNick_c());
			chatMessageFromBean.setContent_b(c.getContent_b());
			chatMessageFromBean.setContent_l(c.getContent_l());
			chatMessageFromBean.setIsplay(false);
			chatMessageFromBean.setQp(c.getQp());

			if (su.getAudioAuto().equals("0")) {
				chatMessageFromBean.setAudioauto(false);
			} else if (su.getAudioAuto().equals("1")) {
				chatMessageFromBean.setAudioauto(true);
			} else {
				chatMessageFromBean.setAudioauto(true);
			}

			if (su.getPicAuto().equals("0")) {
				chatMessageFromBean.setIspicauto(false);
			} else if (su.getPicAuto().equals("1")) {
				chatMessageFromBean.setIspicauto(true);
			} else {
				chatMessageFromBean.setIspicauto(true);
			}

			// 所有数据消息显示,只接收0，1,不接收其他系统消息
			if (c.getType() == 1 || c.getType() == 0) {
				queueList.add(chatMessageFromBean);
			}

			if (c.getType() == 2) {
				queueSystemList.add(chatMessageFromBean);
			}
			if (c.getType() == 3) {
				queueMsgDialogList.add(chatMessageFromBean);
			}
			if (c.getType() == 4) {
				queueMsgSystemList.add(chatMessageFromBean);
			}

			if (c.getType() == 1) {
				queuePrivateList.add(chatMessageFromBean);
			} else if (c.getTuid().equals(su.getUid()) && c.getType() != 3
					&& c.getFuid() > 0) {
				queuePrivateList.add(chatMessageFromBean);
			} else if (c.getTuid().equals(su.getUid()) && c.getType() != 4) {
				queuePrivateList.add(chatMessageFromBean);
			} else if (c.getFuid() == Integer.parseInt(su.getUid())
					&& c.getType() != 3) {
				queuePrivateList.add(chatMessageFromBean);
			} else if (c.getFuid() == Integer.parseInt(su.getUid())
					&& c.getType() != 4) {
				queuePrivateList.add(chatMessageFromBean);
			}
		}
		// 实例化组件
		InitView();
		if (!"".equals(header)) {
			chatroom_top_title.setImageUrl(header, R.drawable.photo,
					Environment.getExternalStorageDirectory() + File.separator
							+ getPackageName() + ConstantsJrc.PHOTO_PATH,
					ConstantsJrc.PROJECT_PATH + getPackageName()
							+ ConstantsJrc.PHOTO_PATH);
		}

		// if (audio.isWiredHeadsetOn() == true) {
		// receiveModelImageView.setImageResource(R.drawable.receiver_icon);
		// AudioManager audioManager = (AudioManager)
		// getSystemService(Context.AUDIO_SERVICE);
		// audioManager.setMode(AudioManager.MODE_IN_CALL);// 把模式调成听筒放音模式
		// audioManager.setSpeakerphoneOn(false);
		// su.setAudioModel("0");
		// } else if (audio.isWiredHeadsetOn() == false) {
		// receiveModelImageView.setImageResource(R.drawable.speaker_icon);
		// AudioManager audioManager = (AudioManager)
		// getSystemService(Context.AUDIO_SERVICE);
		// audioManager.setMode(AudioManager.MODE_IN_CALL);// 把模式调成听筒放音模式
		// audioManager.setSpeakerphoneOn(true);
		// } else {
		// receiveModelImageView.setImageResource(R.drawable.speaker_icon);
		// AudioManager audioManager = (AudioManager)
		// getSystemService(Context.AUDIO_SERVICE);
		// audioManager.setMode(AudioManager.ROUTE_SPEAKER);// 把模式调成扬声器放音模式
		// audioManager.setSpeakerphoneOn(true);
		// }
		li = LayoutInflater.from(this);
		footView = li.inflate(R.layout.main_lv_foot, null);
		footLinerLayout = (LinearLayout) footView
				.findViewById(R.id.chatroom_foot_LinearLayout);
		footLinerLayout.setVisibility(View.GONE);

		vFooter = LayoutInflater.from(this).inflate(R.layout.list_footer, null);
		loading = (LinearLayout) vFooter.findViewById(R.id.list_footer);
		loading.setVisibility(View.GONE);

		// 自定义Toast
		inflatertoast = this.getLayoutInflater();
		view = inflatertoast.inflate(R.layout.toast, null);
		toastTextView = (TextView) view.findViewById(R.id.toast_textView);
		toast = new Toast(this);
		toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 80);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(view);

		// 按钮声音
		myMediaStart = MediaPlayer.create(getApplicationContext(),
				R.raw.ptt_startrecord);
		myMediaSend = MediaPlayer.create(getApplicationContext(),
				R.raw.sent_message);

		// 实例化录音
		vr = new VideoRecord(ChatRoomActivity.this, recorder_volumeImageView);
		vr.init();

		receiveTime = new Timer();
		addTime = new Timer();
		systemMsgTime = new Timer();
		try {
			receiveTime.schedule(new MyTimerTask(), 50, 5000);// 50毫秒后启动任务
			addTime.schedule(new MyTimerTaskRecevie(), 1000, 500);// 1000 毫秒发送消息
			systemMsgTime.schedule(new MyTimerTaskSystem(), 1000, 3000);// 1000毫秒启动发送系统消息
		} catch (Exception e) {
			// TODO: handle exception
		}

		inflater = getLayoutInflater();
		buttomView = (LinearLayout) findViewById(R.id.bottomView);
		absoluteLayout = (AbsoluteLayout) findViewById(R.id.abs);
		createExpressionDialog();
		// ##########################################################

		mTabPager = (ViewPager) findViewById(R.id.tabpager);
		mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());

		mTab1 = (ImageButton) findViewById(R.id.chatroom_radiobutton);
		mTab2 = (ImageButton) findViewById(R.id.mychatroom_radiobutton);
		mTab3 = (ImageButton) findViewById(R.id.farmer_radiobutton);
		// 家族显示
		if (roomtype == 2) {
			mTab3.setBackgroundResource(R.drawable.b_housepeople);
		}
		mTabImg = (ImageView) findViewById(R.id.img_tab_now);

		mTab1.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.chatroom_btn_pressed));
		mTab1.setOnClickListener(new MyOnClickListener(0));
		mTab2.setOnClickListener(new MyOnClickListener(1));
		mTab3.setOnClickListener(new MyOnClickListener(2));
		Display currDisplay = getWindowManager().getDefaultDisplay();// 获取屏幕当前分辨率
		int displayWidth = currDisplay.getWidth();
		one = displayWidth / 3; // 设置水平动画平移大小
		two = one * 2;

		LayoutInflater mLi = LayoutInflater.from(this);
		View view1 = mLi.inflate(R.layout.main_tab_left, null);
		View view2 = mLi.inflate(R.layout.main_tab_middle, null);
		View view3 = mLi.inflate(R.layout.main_tab_right, null);

		// 聊天大厅List
		leftListView = (ListView) view1
				.findViewById(R.id.chatroom_left_listview);

		// 在线村民List
		rightListView = (ListView) view3
				.findViewById(R.id.chatroom_right_listview);
		// rightListView.setDivider(null);
		onlineProgressBar = (ProgressBar) view3
				.findViewById(R.id.online_progressbar);
		onlineProgerssBarRelativeLayout = (RelativeLayout) view3
				.findViewById(R.id.online_progerssbar_RelativeLayout);
		// 私聊List
		midleListView = (ListView) view2
				.findViewById(R.id.chatroom_middle_listview);

		// onlineAdapter = new OnLineUserAdapter(this);

		// countlistitem = onlineAdapter.list.size();
		// System.out.println("countlistitem:" + countlistitem);
		// rightListView.addFooterView(footView);
		// rightListView.setAdapter(onlineAdapter);
		// onlineAdapter.loadData(1, URLEncoder.encode(su.getOnlinePd()), 1);

		leftListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (visibleItemCount + firstVisibleItem == totalItemCount) {
					isBottom = true;
					// 在ListView底部
				} else {
					isBottom = false;
					// 没有在ListView底部
				}
				// TODO Auto-generated method stub
			}
		});
		midleListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if (visibleItemCount + firstVisibleItem == totalItemCount) {
					isBottom2 = true;
					// 在ListView底部
				} else {
					isBottom2 = false;
					// 没有在ListView底部
				}
			}
		});

		rightListView.addFooterView(vFooter);
		rightListView.setFooterDividersEnabled(false);

		rightListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					if (view.getLastVisiblePosition() == view.getCount() - 1) {

						isfoot = true;
						String url = ConstantsJrc.ONLINEUSER + "?" + "roomid="
								+ roomid + "&pd=" + su.getOnlinePd()
								+ "&token=" + su.getToken() + "&page="
								+ pagecount++;
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
			}
		});

		// 每个页面的view数据
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);

		// 填充ViewPager的数据适配器
		PagerAdapter mPagerAdapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(views.get(position));
			}

			// @Override
			// public CharSequence getPageTitle(int position) {
			// return titles.get(position);
			// }

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(views.get(position));
				return views.get(position);
			}
		};

		mTabPager.setAdapter(mPagerAdapter);

		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// receiveTime.cancel();
				// addTime.cancel();
				if (IsExit) {
					IsExit = false;
					finish();
					// Intent intent = new Intent(getApplicationContext(),
					// ExitDialog.class);
					// startActivityForResult(intent, 2);
				}
			}
		});

		ly.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// buttomView.setVisibility(View.GONE);
				// 自动弹回键盘
				InputMethodManager m = (InputMethodManager) messageEt
						.getContext().getSystemService(
								Context.INPUT_METHOD_SERVICE);
				m.hideSoftInputFromWindow(messageEt.getWindowToken(), 0);
				IsShow = true;
			}
		});

		addBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (bottomRelative.getVisibility() == View.VISIBLE
						|| buttomView.getVisibility() == View.VISIBLE) {
					bottomRelative.setVisibility(View.GONE);
					buttomView.setVisibility(View.GONE);
				}

				if (IsShow) {
					bottomRelative.setVisibility(View.VISIBLE);
					IsShow = false;
				} else {
					bottomRelative.setVisibility(View.GONE);
					IsShow = true;
				}
			}
		});

		messageEt.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus == true) {
					buttomView.setVisibility(View.GONE);
				} else {
					buttomView.setVisibility(View.GONE);
					// 自动弹回键盘
					InputMethodManager m = (InputMethodManager) messageEt
							.getContext().getSystemService(
									Context.INPUT_METHOD_SERVICE);
					m.hideSoftInputFromWindow(messageEt.getWindowToken(), 0);

				}

			}
		});

		chooseBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					chooseBtn.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.change_message));
					speakBtn.setVisibility(View.VISIBLE);
					messageEt.setVisibility(View.GONE);
					sendBtn.setVisibility(View.GONE);
					if (bottomRelative.getVisibility() == View.VISIBLE
							|| buttomView.getVisibility() == View.VISIBLE) {
						leftListView.setFocusable(true);
						bottomRelative.clearFocus();
						buttomView.clearFocus();
						bottomRelative.setVisibility(View.GONE);
						buttomView.setVisibility(View.GONE);
					}
					IsShow = true;
				} else {
					chooseBtn.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.change_video));
					speakBtn.setVisibility(View.GONE);
					messageEt.setVisibility(View.VISIBLE);
					sendBtn.setVisibility(View.VISIBLE);
				}
			}
		});

		expressionBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bottomRelative.setVisibility(View.GONE);
				buttomView.setVisibility(View.VISIBLE);
			}
		});

		cameraBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (auth.equals("2") || auth.equals("4") || auth.equals("5")) {
					Intent intent = new Intent(getApplicationContext(),
							MessageSystemDialog.class);
					intent.putExtra("tip", "您没有发图片的权限");
					intent.putExtra("ret", "0");
					startActivity(intent);

				} else {
					File sdCardDir = Environment.getExternalStorageDirectory();
					File dir;
					try {
						dir = new File(sdCardDir.getCanonicalPath()
								+ File.separator + getPackageName()
								+ ConstantsJrc.IMAGE_PATH);
						if (!dir.exists()) {
							dir.mkdirs();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (SystemUtil.getSDCardMount()) {
						su.setOldPicPath(Environment
								.getExternalStorageDirectory()
								+ File.separator
								+ getPackageName()
								+ ConstantsJrc.IMAGE_PATH
								+ File.separator
								+ Commond.getMd5Hash(DateManager.getPhoneTime())
								+ ".png");
					} else {
						su.setOldPicPath(ConstantsJrc.PROJECT_PATH
								+ getPackageName()
								+ ConstantsJrc.IMAGE_PATH
								+ File.separator
								+ Commond.getMd5Hash(DateManager.getPhoneTime())
								+ ".png");
					}
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					intent.putExtra(MediaStore.EXTRA_OUTPUT,
							Uri.fromFile(new File(su.getOldPicPath())));
					// 拍照前设置好宽高
					intent.putExtra("outputX", 480);
					intent.putExtra("outputY", 800);
					startActivityForResult(intent, ConstantsJrc.PHOTOHRAPH);
					if (bottomRelative.getVisibility() == View.VISIBLE
							|| buttomView.getVisibility() == View.VISIBLE) {
						bottomRelative.setVisibility(View.GONE);
						buttomView.setVisibility(View.GONE);
					}
					IsShow = true;
				}
			}
		});

		photoBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (auth.equals("2") || auth.equals("4") || auth.equals("5")) {
					Intent intent = new Intent(getApplicationContext(),
							MessageSystemDialog.class);
					intent.putExtra("tip", "您没有发图片的权限");
					intent.putExtra("ret", "0");
					startActivity(intent);

				} else {
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
					intent.setDataAndType(
							MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
							ConstantsJrc.IMAGE_UNSPECIFIED);
					startActivityForResult(intent, ConstantsJrc.PHOTOZOOM);
					if (bottomRelative.getVisibility() == View.VISIBLE
							|| buttomView.getVisibility() == View.VISIBLE) {
						bottomRelative.setVisibility(View.GONE);
						buttomView.setVisibility(View.GONE);
					}
					IsShow = true;
				}
			}
		});

		speakBtn.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
					toastTextView.setText("检测到网络网络异常或未开启");
					toast.show();
					return false;
				}
				if (auth.equals("3") || auth.equals("6") || auth.equals("5")) {
					Intent intent = new Intent(getApplicationContext(),
							MessageSystemDialog.class);
					intent.putExtra("tip", "您没有发语音的权限");
					intent.putExtra("ret", "0");
					startActivity(intent);
				} else {
					if (startaudio == false) {
						startaudio = true;
						reallyaudio = false;
						speakRelativeLayout.setVisibility(View.VISIBLE);
						// System.out.println("长按....录音...");
						filetype = "afile";
						LongTouch = true;
						chatRoomAdapter2.clear();
						myMediaStart.start();
						vr.start();
					}
				}
				return true;

			}
		});
		speakBtn.setOnTouchListener(new MyClickListener());

		chatPrivateCloseButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (IsPrivate == false) {
					chatPrivateCloseButton
							.setBackgroundDrawable(getResources()
									.getDrawable(
											R.drawable.chatroom_private_close_btn_pressed));
					chatPrivateOpenButton
							.setBackgroundDrawable(getResources()
									.getDrawable(
											R.drawable.chatroom_private_open_btn_normal));
					type = 0;
					IsPrivate = true;
					// Log.i("type", type + "");
				}
			}
		});
		chatPrivateOpenButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (toUid.equals("")) {
					toastTextView.setText("不能对大家说悄悄话");
					toast.show();
					return;
				}
				if (IsPrivate) {
					chatPrivateCloseButton
							.setBackgroundDrawable(getResources()
									.getDrawable(
											R.drawable.chatroom_private_close_btn_normal));
					chatPrivateOpenButton
							.setBackgroundDrawable(getResources()
									.getDrawable(
											R.drawable.chatroom_private_open_btn_pressed));
					type = 1;
					IsPrivate = false;
					// Log.i("type", type + "");
				}
			}
		});

		sendBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
					toastTextView.setText("检测到网络网络异常或未开启");
					toast.show();
					return;
				}

				if (auth.equals("1") || auth.equals("6") || auth.equals("4")) {
					Intent intent = new Intent(getApplicationContext(),
							MessageSystemDialog.class);
					intent.putExtra("tip", "您没有发文字的权限");
					intent.putExtra("ret", "0");
					startActivity(intent);

				} else {

					sendThread = new SendMsgThread();
					contString = messageEt.getText().toString();
					filetype = "";
					afile = "";
					pfile = "";
					// 判断表情和选择框显示，发送的时候就自动隐藏
					if (currIndex == 2)
						mTabPager.setCurrentItem(0);
					if (contString.length() > 0) {
						ChatMessageBean chatBean = new ChatMessageBean();
						chatBean.setRoomid(roomid);
						chatBean.setContent(contString);
						chatBean.setFuid(Integer.parseInt(su.getUid()));
						chatBean.setFheader(su.getHeader());
						chatBean.setTuid(toUid);
						chatBean.setType(type);
						chatBean.setTnick(toNick);
						chatBean.setMsgtype(3);
						chatBean.setIsOK(0);
						chatBean.setIscome(false);
						chatBean.setIssend(false);// 设置未发送状态
						String date = DateManager.getPhoneTime();
						chatBean.setPd(date);
						chatBean.setDatetime(DateManager.strDate(DateManager
								.getDate(DateManager.getPhoneTime(), false),
								DateManager.getDate(chatBean.getPd(), false)));
						chatBean.setContent_c(msg_c);
						chatBean.setContent_b(msg_b);
						chatBean.setContent_l(msg_l);
						chatBean.setNick_c(nick_c);
						chatBean.setQp(qp);
						// sendThread.start();
						// if (type == 1 || !toUid.equals("")) {
						for (ChatMessageBean bean : privateChatRoomAdapter2.list) {
							bean.setDatetime(DateManager.strDate(
									DateManager.getDate(
											DateManager.getPhoneTime(), false),
									DateManager.getDate(chatBean.getPd(), false)));
						}
						for (ChatMessageBean bean : chatRoomAdapter2.list) {
							bean.setDatetime(DateManager.strDate(
									DateManager.getDate(
											DateManager.getPhoneTime(), false),
									DateManager.getDate(chatBean.getPd(), false)));
						}

						privateList.add(0, chatBean);
						midleListView.requestLayout();
						privateChatRoomAdapter2.notifyDataSetChanged();
						if (midleListView.getCount() > 0) {
							midleListView.setSelection(midleListView.getCount() - 1);
						}
						// }
						chatList.add(0, chatBean);
						leftListView.requestLayout();
						chatRoomAdapter2.notifyDataSetChanged();

						messageEt.setText("");
						// System.out.println(leftListView.getCount() + "个");
						if (leftListView.getCount() > 0) {
							leftListView.setSelection(leftListView.getCount() - 1);
						}

						leftListView.setFocusable(true);
						if (bottomRelative.getVisibility() == View.VISIBLE
								|| buttomView.getVisibility() == View.VISIBLE) {
							bottomRelative.clearFocus();
							buttomView.clearFocus();
							leftListView.setFocusable(true);
							leftListView.requestFocus();
							bottomRelative.setVisibility(View.GONE);
							buttomView.setVisibility(View.GONE);
						}
					}
					focus();
				}
			}
		});

		chatRoomAdapter2 = new ChatRoomAdapter2(ChatRoomActivity.this,
				ChatRoomActivity.this, chatList, leftListView, listen, listen2,
				listen3, longlisten, longlisten2, longlisten3);
		leftListView.setAdapter(chatRoomAdapter2);

		privateChatRoomAdapter2 = new ChatRoomAdapter2(ChatRoomActivity.this,
				ChatRoomActivity.this, privateList, midleListView, listen,
				listen2, listen3, longlisten, longlisten2, longlisten3);
		midleListView.setAdapter(privateChatRoomAdapter2);

		chatRoomCloseButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				chatToUserTextView.setText("大家");
				toUid = "";
				IsPrivate = true;
				type = 0;
				toNick = "";
				onlineLinearLayout.setVisibility(View.VISIBLE);
				chattospeakLinearLayout.setVisibility(View.GONE);
				// mTabPager.setCurrentItem(0);
//				chatLiwuImageButton.setVisibility(View.GONE);
				messageEt.setHint(R.string.xml_send_message);
			}
		});

		chatLiwuImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(TextUtils.isEmpty(toUid)){
					Commond.showToast(ChatRoomActivity.this, "先点击Ta人头像，再送礼哦~");
					return ;
				}
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						VillageUserInfoDialog.class);
				// Log.i("ttt", uid);
				intent.putExtra("uid", toUid);
				intent.putExtra("fuid", su.getUid());
//				intent.putExtra("type", 1); // 判断是从聊天室打开，还是村委会
				intent.putExtra("chatroom", "1");
				intent.putExtra("type", 5);
				intent.putExtra("src", 2);
				startActivityForResult(intent, 1);
			}
		});
		rightListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position < onlineAdapter.list.size()) {
					Intent intent = new Intent(getApplicationContext(),
							VillageUserInfoDialog.class);
					String uid = String.valueOf(onlineAdapter.list
							.get(position).getId());
					// Log.i("ttt", uid);
					intent.putExtra("uid", uid);
					intent.putExtra("fuid", su.getUid());
					intent.putExtra("type", 1);
					intent.putExtra("src", 1);
					startActivityForResult(intent, 1);
				}
			}
		});

		chatToUserTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						VillageUserInfoDialog.class);
				intent.putExtra("uid", toUid);
				intent.putExtra("fuid", su.getUid());
				intent.putExtra("type", 1);
				intent.putExtra("src", 1);
				startActivity(intent);
			}
		});

	}

	// 监听头像点击
	OnClickListener listen = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ChatMessageBean chatBean = (ChatMessageBean) v.getTag();
			if (chatBean.getFuid() == Integer.parseInt(su.getUid())) {
				toastTextView.setText("自言自语脑袋会有问题");
				toast.show();
			} else {
				IsPrivate = true;
				chatPrivateCloseButton.setBackgroundDrawable(getResources()
						.getDrawable(
								R.drawable.chatroom_private_close_btn_pressed));
				chatPrivateOpenButton.setBackgroundDrawable(getResources()
						.getDrawable(
								R.drawable.chatroom_private_open_btn_normal));
				messageEt.setHint("对" + chatBean.getFnick().toString() + "说:");
				type = 0;
				toUid = String.valueOf(chatBean.getFuid());
				toNick = chatBean.getFnick().toString();
				chatToUserTextView.setText(toNick);
				chattospeakLinearLayout.setVisibility(View.VISIBLE);
				chatLiwuImageButton.setVisibility(View.VISIBLE);
				onlineLinearLayout.setVisibility(View.GONE);
				// Log.i("ttt", "聊天对象UID：" + toUid);
			}
		}

	};

	// 图片边框点击
	OnClickListener listen2 = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			String imgurl = "";
			String downpath = "";
			String savepath = "";
			ChatMessageBean chatBean = (ChatMessageBean) v.getTag();
			// Log.i("text", chatBean.getPfile());
			if (chatBean.isIscome()) {
				imgurl = ConstantsJrc.GETBIGIMAGE
						+ "?url="
						+ URLEncoder.encode(chatBean.getPfile())
						+ "&uid="
						+ chatBean.getFuid()
						+ "&sw="
						+ PhoneInfo.getInstance(getApplicationContext())
								.getWidth(ChatRoomActivity.this)
						+ "&sh="
						+ PhoneInfo.getInstance(getApplicationContext())
								.getHight(ChatRoomActivity.this);
			} else {
				imgurl = chatBean.getPlocalfile();
			}

			// Log.i("img", "img:::" + chatBean.isIscome() + "," + imgurl);
			if (SystemUtil.getSDCardMount()) {
				downpath = Environment.getExternalStorageDirectory()
						+ File.separator + getPackageName()
						+ ConstantsJrc.IMAGE_PATH;
				savepath = Environment.getExternalStorageDirectory()
						+ ConstantsJrc.SAVE_PATH;
			} else {
				downpath = ConstantsJrc.PROJECT_PATH + getPackageName()
						+ ConstantsJrc.IMAGE_PATH;
				savepath = ConstantsJrc.PROJECT_PATH + getPackageName()
						+ ConstantsJrc.SAVE_PATH;
			}

			Intent intent = new Intent(getApplicationContext(), ImageZoom.class);
			// 需要请求图片的url(也可以是本地文件)
			intent.putExtra("imageurl", imgurl);
			// 网路图片下载后保存的文件加路径
			intent.putExtra("downpath", downpath);
			// 图片需要保存的文件夹路径
			intent.putExtra("savepath", savepath);
			startActivity(intent);
			int version = Integer.valueOf(android.os.Build.VERSION.SDK);
			if (version > 5) {
				overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
			}
		}

	};
	/**
	 * 
	 */
	OnClickListener listen3 = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};

	/***
	 * 文字框长按
	 */
	OnLongClickListener longlisten = new OnLongClickListener() {

		@Override
		public boolean onLongClick(View v) {
			// TODO Auto-generated method stub
			ChatMessageBean chatBean = (ChatMessageBean) v.getTag();

			Intent intent = new Intent(getApplicationContext(),
					ChatToolsDialog.class);
			intent.putExtra("msg", chatBean.getContent());
			intent.putExtra("uid", String.valueOf(chatBean.getFuid()));
			intent.putExtra("fuid", su.getUid());
			intent.putExtra("type", "1");
			startActivityForResult(intent, 1);
			return false;
		}
	};

	/**
	 * 语音框长按
	 */
	OnLongClickListener longlisten2 = new OnLongClickListener() {

		@Override
		public boolean onLongClick(View v) {
			// TODO Auto-generated method stub
			ChatMessageBean chatBean = (ChatMessageBean) v.getTag();
			Intent intent = new Intent(getApplicationContext(),
					ChatToolsDialog.class);
			intent.putExtra("msg", chatBean.getContent());
			intent.putExtra("uid", String.valueOf(chatBean.getFuid()));
			intent.putExtra("fuid", su.getUid());
			intent.putExtra("type", "2");
			startActivityForResult(intent, 1);
			return false;
		}
	};
	/**
	 * 图片框长按
	 */
	OnLongClickListener longlisten3 = new OnLongClickListener() {

		@Override
		public boolean onLongClick(View v) {
			// TODO Auto-generated method stub
			ChatMessageBean chatBean = (ChatMessageBean) v.getTag();
			Intent intent = new Intent(getApplicationContext(),
					ChatToolsDialog.class);
			intent.putExtra("msg", chatBean.getContent());
			intent.putExtra("uid", String.valueOf(chatBean.getFuid()));
			intent.putExtra("fuid", su.getUid());
			intent.putExtra("type", "3");
			startActivityForResult(intent, 1);
			return false;
		}
	};

	/**
	 * 延迟2秒停止录音
	 * 
	 * @author J.King
	 * 
	 */
	public class MySendAudioTimerTask extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (_run) {
				try {
					Thread.sleep(1000);
					vr.stop();
					startaudio = false;
					reallyaudio = true;
					sendAudio();
					// sendAudio.cancel();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Handler sendaudiohandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:
				// dialog.setCancelable(true);
				// dialog.show();
				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				// dialog.cancel();
				// leftListView.requestLayout();
				// midleListView.requestLayout();
				// chatRoomAdapter2.notifyDataSetChanged();
				// privateChatRoomAdapter2.notifyDataSetChanged();
				sendaudioThread.stopThread(false);
				break;
			}
		};
	};

	private void sendAudio() {
		sendaudiohandler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		sendaudiohandler.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (videoTime > 1) {
						filetype = "afile";
						contString = "";
						pfile = "";
						File file = new File(su.getAudioPath());
						afile = file.getPath();
						sendThread = new SendMsgThread();
						if (currIndex == 2)
							mTabPager.setCurrentItem(0);
						if (file.exists()) {
							ChatMessageBean chatBean = new ChatMessageBean();
							chatBean.setFuid(Integer.parseInt(su.getUid()));
							chatBean.setFheader(su.getHeader());
							chatBean.setType(type);
							chatBean.setTuid(toUid);
							chatBean.setTnick(toNick);
							chatBean.setRoomid(roomid);
							chatBean.setMsgtype(4);
							chatBean.setPlocalfile(su.getAudioPath());
							chatBean.setIsOK(0);
							chatBean.setIscome(false);
							chatBean.setAlen(String.valueOf(videoTime));
							chatBean.setAfile(su.getAudioPath());
							chatBean.setAlocalfile(su.getAudioPath());
							chatBean.setPd(DateManager.getPhoneTime());
							chatBean.setNick_c(nick_c);
							chatBean.setQp(qp);
							// sendThread.start();
							privateList.add(0, chatBean);
							// privateChatRoomAdapter2.notifyDataSetChanged();
							if (midleListView.getCount() > 0) {
								midleListView.setSelection(midleListView
										.getCount() - 1);
							}

							chatList.add(0, chatBean);
							// chatRoomAdapter2.notifyDataSetChanged();
							// System.out.println(leftListView.getCount() +
							// "个");
							if (leftListView.getCount() > 0) {
								leftListView.setSelection(leftListView
										.getCount() - 1);
							}
						}
					} else {
						toastTextView.setText("录音时间太短");
						toast.show();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

	public class MyClickListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
					toastTextView.setText("检测到网络网络异常或未开启");
					toast.show();
					return false;
				}
				if (LongTouch) {
					// System.out.println("抬起....发送...");
					speakRelativeLayout.setVisibility(View.GONE);
					// sendAudio = new Timer();
					// sendAudio.schedule(new MySendAudioTimerTask(), 1000,
					// 1000);
					LongTouch = false;
					sendaudioThread = new MySendAudioTimerTask();
					sendaudioThread.start();
				}
				break;

			}
			return false;
		}

	}

	/**
	 * 定时器取数据，5秒获取一次网络数据
	 * 
	 * @author J.King
	 * 
	 */
	public class MyTimerTask extends TimerTask {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (_run) {
				try {
					String result = HttpGetMessage.GetMessage(roomid, Integer
							.parseInt(su.getUid()), su.getMsgPid(), PhoneInfo
							.getInstance(getApplicationContext())
							.getVersionName(ChatRoomActivity.this), su
							.getToken());

					// System.out.println(result);

					MessageJson mj = new MessageJson();
					if (!"".equals(result) || result != null) {
						getMessageList = mj.parseJsonCountPid(result);
						su.setMsgPid(getMessageList.get(0).getPid());
						su.setBTime(getMessageList.get(0).getBtime());
						Message msg = new Message();
						Bundle b = new Bundle();

						if (!"".equals(getMessageList.get(0).getCount()
								.toString())
								|| getMessageList.get(0).getCount() != null) {
							b.putString("count", getMessageList.get(0)
									.getCount());
						} else {
							b.putString("count", "0");
						}
						if ("0".equals(getMessageList.get(0).getRet())) {
							if ("".equals(getMessageList.get(0).getTip())) {
								Intent intent = new Intent(
										getApplicationContext(),
										MessageSystemDialog.class);
								intent.putExtra("ret", getMessageList.get(0)
										.getRet());
								intent.putExtra("tip", getMessageList.get(0)
										.getTip());
								startActivity(intent);
							}
							return;
						}
						b.putString("ret", getMessageList.get(0).getRet());
						b.putString("tip", getMessageList.get(0).getTip());
						if (b != null) {
							msg.setData(b);
							myTimerHandler.sendMessage(msg);
						}
					}

					GetMessageJson gmJson = new GetMessageJson();
					receiveList = gmJson.parseJson(result);
					// System.out.println("ID:" + chatList.get(0).getId());
					// if (chatList.get(chatList.size()).getId() < receiveList
					// .get(0).getId()) {
					if (!pdlist.contains(getMessageList.get(0).getPid())) {
						for (int i = 0; i < receiveList.size(); i++) {
							chatMessageFromBean = new ChatMessageBean();
							chatMessageFromBean.setId(receiveList.get(i)
									.getId());
							chatMessageFromBean.setType(receiveList.get(i)
									.getType());
							chatMessageFromBean.setIcon(receiveList.get(i)
									.getIcon());
							chatMessageFromBean.setFuid(receiveList.get(i)
									.getFuid());
							chatMessageFromBean.setFnick(receiveList.get(i)
									.getFnick());
							chatMessageFromBean.setFheader(receiveList.get(i)
									.getFheader());
							chatMessageFromBean.setTuid(receiveList.get(i)
									.getTuid());
							chatMessageFromBean.setTnick(receiveList.get(i)
									.getTnick());
							chatMessageFromBean.setContent(receiveList.get(i)
									.getContent());
							chatMessageFromBean.setAfile(receiveList.get(i)
									.getAfile());
							chatMessageFromBean.setAlen(receiveList.get(i)
									.getAlen());
							chatMessageFromBean.setAsize(receiveList.get(i)
									.getAsize());
							chatMessageFromBean.setPfile(receiveList.get(i)
									.getPfile());
							chatMessageFromBean.setPd(receiveList.get(i)
									.getPd());
							chatMessageFromBean.setPw(receiveList.get(i)
									.getPw());
							chatMessageFromBean.setPh(receiveList.get(i)
									.getPh());
							chatMessageFromBean.setContent_c(receiveList.get(i)
									.getContent_c());
							chatMessageFromBean.setNick_c(receiveList.get(i)
									.getNick_c());
							chatMessageFromBean.setContent_b(receiveList.get(i)
									.getContent_b());
							chatMessageFromBean.setContent_l(receiveList.get(i)
									.getContent_l());
							chatMessageFromBean.setQp(receiveList.get(i)
									.getQp());
							chatMessageFromBean.setIsplay(false);
							chatMessageFromBean.setAflg(receiveList.get(i)
									.getAflg());
							chatMessageFromBean.setIsOK(receiveList.get(i)
									.getIsOK());
							if (su.getAudioAuto().equals("0")) {
								chatMessageFromBean.setAudioauto(false);
							} else if (su.getAudioAuto().equals("1")) {
								chatMessageFromBean.setAudioauto(true);
							} else {
								chatMessageFromBean.setAudioauto(true);
							}

							if (su.getPicAuto().equals("0")) {
								chatMessageFromBean.setIspicauto(false);
							} else if (su.getPicAuto().equals("1")) {
								chatMessageFromBean.setIspicauto(true);
							} else {
								chatMessageFromBean.setIspicauto(true);
							}

							// 所有数据消息显示,只接收0，1,不接收其他系统消息
							if (receiveList.get(i).getType() == 1
									|| receiveList.get(i).getType() == 0) {
								queueList.add(chatMessageFromBean);
							}

							if (receiveList.get(i).getType() == 2) {
								queueSystemList.add(chatMessageFromBean);
							}
							if (receiveList.get(i).getType() == 3) {
								queueMsgDialogList.add(chatMessageFromBean);
							}
							if (receiveList.get(i).getType() == 4) {
								queueMsgSystemList.add(chatMessageFromBean);
							}
							// Log.i("sys", queueSystemList.toString());
							// 类型为私聊或者跟自己有关的信息加入缓存
							// if (receiveList.get(i).getType() == 1
							// || (receiveList.get(i).getTuid() == Integer
							// .parseInt(su.getUid()) && receiveList
							// .get(i).getFuid() == Integer
							// .parseInt(su.getUid()))
							// || receiveList.get(i).getType() != 3
							// || receiveList.get(i).getType() != 4) {
							//
							// queuePrivateList.add(chatMessageFromBean);
							// }

							if (receiveList.get(i).getType() == 1) {
								queuePrivateList.add(chatMessageFromBean);
							} else if (receiveList.get(i).getTuid()
									.equals(su.getUid())
									&& receiveList.get(i).getType() != 3
									&& receiveList.get(i).getFuid() > 0) {
								queuePrivateList.add(chatMessageFromBean);
							} else if (receiveList.get(i).getTuid()
									.equals(su.getUid())
									&& receiveList.get(i).getType() != 4) {
								queuePrivateList.add(chatMessageFromBean);
							} else if (receiveList.get(i).getFuid() == Integer
									.parseInt(su.getUid())
									&& receiveList.get(i).getType() != 3) {
								queuePrivateList.add(chatMessageFromBean);
							} else if (receiveList.get(i).getFuid() == Integer
									.parseInt(su.getUid())
									&& receiveList.get(i).getType() != 4) {
								queuePrivateList.add(chatMessageFromBean);
							}
						}
						pdlist.add(getMessageList.get(0).getPid());
					}

					// Log.i("private", queuePrivateList.toString() + "");

					receiveList.clear();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
			}

		}
	}

	Handler myTimerHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Bundle b = msg.getData();
			String num = b.getString("count");
			String ret = b.getString("ret");
			String tip = b.getString("tip");
			onlineCountTextView.setText(num);
			if (ret.equals("2")) {
				Intent intent = new Intent(getApplicationContext(),
						Message0Dialog.class);
				intent.putExtra("ret", ret);
				intent.putExtra("tip", tip);
				startActivity(intent);
				receiveTime.cancel();
			} else if (ret.equals("1") && !tip.equals("")) {
				Intent intent = new Intent(getApplicationContext(),
						MessageSystemDialog.class);
				intent.putExtra("ret", ret);
				intent.putExtra("tip", tip);
				startActivity(intent);
			}

		}
	};

	/**
	 * 500 毫秒发送消息数据
	 * 
	 * @author J.King
	 * 
	 */
	public class MyTimerTaskRecevie extends TimerTask {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (_run) {
				addMsghandler.sendEmptyMessage(1);
			}
		}
	}

	Handler addMsghandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			ChatMessageBean chatBean = new ChatMessageBean();
			ChatMessageBean chatPrivateBean = new ChatMessageBean();
			ChatMessageBean chatMsgSysBean = new ChatMessageBean();
			try {
				/**
				 * 倒序删除聊天内容
				 */
				if (chatList.size() > 80) {
					for (int i = 0; i < 40; i++) {
						chatList.remove(79 - i);
					}
					if (leftListView.getCount() > 0) {
						leftListView.setSelection(leftListView.getCount() - 1);
					}
				}
				if (privateList.size() > 80) {
					for (int i = 0; i < 40; i++) {
						privateList.remove(79 - i);
					}
					if (midleListView.getCount() > 0) {
						midleListView
								.setSelection(midleListView.getCount() - 1);
					}
				}

				if (queueMsgDialogList.size() > 0) {
					for (int i = 0; i < queueMsgDialogList.size(); i++) {
						if (queueMsgDialogList.get(i).getType() == 3) {
							Intent intent = new Intent(getApplicationContext(),
									MessageSystemDialog.class);
							intent.putExtra("ret", "0");
							intent.putExtra("tip", queueMsgDialogList.get(i)
									.getContent());
							startActivity(intent);
							queueMsgDialogList.remove(i);
						}
					}
				}

				if (queueMsgSystemList.size() > 0) {
					for (int i = 0; i < queueMsgSystemList.size(); i++) {
						chatMsgSysBean.setContent(queueMsgSystemList.get(i)
								.getContent());
						chatMsgSysBean.setIcon(queueMsgSystemList.get(i)
								.getIcon());
						chatMsgSysBean.setMsgtype(6);
						chatList.add(0, chatMsgSysBean);
						leftListView.requestLayout();
						chatRoomAdapter2.notifyDataSetChanged();

						if (isBottom) {
							if (leftListView.getCount() > 0) {
								leftListView.setSelection(leftListView
										.getCount() - 1);
							}
						}
						queueMsgSystemList.remove(i);
					}
				}

				if (queueList.size() > 0) {
					for (int i = 0; i < 1; i++) {
						chatBean.setContent(queueList.get(i).getContent());
						if (!queueList.get(i).getContent().toString()
								.equals("")
								&& queueList.get(i).getType() != 3
								&& queueList.get(i).getType() != 4) {
							if (queueList.get(i).getFuid() == Integer
									.parseInt(su.getUid())) {
								chatBean.setMsgtype(3);
								chatBean.setIscome(true);
							} else {
								chatBean.setMsgtype(0);
								chatBean.setIscome(true);
							}
						} else if (!queueList.get(i).getPfile().toString()
								.equals("")) {
							if (queueList.get(i).getFuid() == Integer
									.parseInt(su.getUid())) {
								chatBean.setMsgtype(5);
								chatBean.setIscome(true);
								chatBean.setPw(queueList.get(i).getPw());
								chatBean.setPh(queueList.get(i).getPh());
								chatBean.setPfile(queueList.get(i).getPfile());
							} else {
								chatBean.setMsgtype(2);
								chatBean.setIscome(true);
								chatBean.setPw(queueList.get(i).getPw());
								chatBean.setPh(queueList.get(i).getPh());
								chatBean.setPfile(queueList.get(i).getPfile());
							}
						} else if (!queueList.get(i).getAfile().toString()
								.equals("")) {
							if (queueList.get(i).getFuid() == Integer
									.parseInt(su.getUid())) {
								chatBean.setMsgtype(4);
								chatBean.setAlen(queueList.get(i).getAlen());
								chatBean.setAsize(queueList.get(i).getAsize());
								chatBean.setAfile(queueList.get(i).getAfile());
								chatBean.setIscome(true);
							} else {
								chatBean.setMsgtype(1);
								chatBean.setAlen(queueList.get(i).getAlen());
								chatBean.setAsize(queueList.get(i).getAsize());
								chatBean.setAfile(queueList.get(i).getAfile());
								chatBean.setIscome(true);
							}
						}
						chatBean.setAflg(queueList.get(i).getAflg());
						chatBean.setType(queueList.get(i).getType());
						chatBean.setFuid(queueList.get(i).getFuid());
						chatBean.setPd(queueList.get(i).getPd());
						chatBean.setFnick(queueList.get(i).getFnick());
						chatBean.setFheader(queueList.get(i).getFheader());
						if (queueList.get(i).getTuid().equals(su.getUid())) {
							chatBean.setTnick("我");
						} else {
							chatBean.setTnick(queueList.get(i).getTnick());
						}
						chatBean.setTuid(queueList.get(i).getTuid());
						chatBean.setIsOK(queueList.get(i).getIsOK());
						chatBean.setIsplay(queueList.get(i).getIsplay());
						chatBean.setIspicauto(queueList.get(i).getIspicauto());
						chatBean.setAudioauto(queueList.get(i).getAudioauto());
						chatBean.setContent_c(queueList.get(i).getContent_c());
						chatBean.setNick_c(queueList.get(i).getNick_c());
						chatBean.setContent_b(queueList.get(i).getContent_b());
						chatBean.setContent_l(queueList.get(i).getContent_l());
						chatBean.setQp(queueList.get(i).getQp());
						chatList.add(0, chatBean);
						leftListView.requestLayout();
						chatRoomAdapter2.notifyDataSetChanged();

						if (isBottom) {
							if (leftListView.getCount() > 0) {
								leftListView.setSelection(leftListView
										.getCount() - 1);
							}
						}
						if (isBottom2) {
							if (midleListView.getCount() > 0) {
								midleListView.setSelection(midleListView
										.getCount() - 1);
							}
						}
						queueList.remove(i);
					}
				}

				if (queuePrivateList.size() > 0) {
					for (int i = 0; i < 1; i++) {
						chatPrivateBean.setContent(queuePrivateList.get(i)
								.getContent());

						if (!queuePrivateList.get(i).getContent().toString()
								.equals("")) {
							if (queuePrivateList.get(i).getFuid() == Integer
									.parseInt(su.getUid())) {
								chatPrivateBean.setMsgtype(3);
								chatPrivateBean.setIscome(true);
							} else {
								chatPrivateBean.setMsgtype(0);
								chatPrivateBean.setIscome(true);
							}
						} else if (!queuePrivateList.get(i).getPfile()
								.toString().equals("")) {
							if (queuePrivateList.get(i).getFuid() == Integer
									.parseInt(su.getUid())) {
								chatPrivateBean.setMsgtype(5);
								chatPrivateBean.setIscome(true);
								chatPrivateBean.setPw(queuePrivateList.get(i)
										.getPw());
								chatPrivateBean.setPh(queuePrivateList.get(i)
										.getPh());
								chatPrivateBean.setPfile(queuePrivateList
										.get(i).getPfile());
							} else {
								chatPrivateBean.setMsgtype(2);
								chatPrivateBean.setIscome(true);
								chatPrivateBean.setPw(queuePrivateList.get(i)
										.getPw());
								chatPrivateBean.setPh(queuePrivateList.get(i)
										.getPh());
								chatPrivateBean.setPfile(queuePrivateList
										.get(i).getPfile());
							}
						} else if (!queuePrivateList.get(i).getAfile()
								.toString().equals("")) {
							if (queuePrivateList.get(i).getFuid() == Integer
									.parseInt(su.getUid())) {
								chatPrivateBean.setMsgtype(4);
								chatPrivateBean.setIscome(true);
								chatPrivateBean.setAlen(queuePrivateList.get(i)
										.getAlen());
								chatPrivateBean.setAsize(queuePrivateList
										.get(i).getAsize());
								chatPrivateBean.setAfile(queuePrivateList
										.get(i).getAfile());
							} else {
								chatPrivateBean.setMsgtype(1);
								chatPrivateBean.setIscome(true);
								chatPrivateBean.setAlen(queuePrivateList.get(i)
										.getAlen());
								chatPrivateBean.setAsize(queuePrivateList
										.get(i).getAsize());
								chatPrivateBean.setAfile(queuePrivateList
										.get(i).getAfile());
							}
						}

						chatPrivateBean.setAflg(queuePrivateList.get(i)
								.getAflg());
						chatPrivateBean.setType(queuePrivateList.get(i)
								.getType());
						chatPrivateBean.setFuid(queuePrivateList.get(i)
								.getFuid());
						chatPrivateBean.setPd(queuePrivateList.get(i).getPd());
						chatPrivateBean.setFnick(queuePrivateList.get(i)
								.getFnick());
						chatPrivateBean.setFheader(queuePrivateList.get(i)
								.getFheader());
						if (queuePrivateList.get(i).getTuid()
								.equals(su.getUid())) {
							chatPrivateBean.setTnick("我");
						} else {
							chatPrivateBean.setTnick(queuePrivateList.get(i)
									.getTnick());
						}

						chatPrivateBean.setTuid(queuePrivateList.get(i)
								.getTuid());
						chatPrivateBean.setIsOK(queuePrivateList.get(i)
								.getIsOK());
						chatPrivateBean.setIsplay(queuePrivateList.get(i)
								.getIsplay());
						chatPrivateBean.setIspicauto(queuePrivateList.get(i)
								.getIspicauto());
						chatPrivateBean.setAudioauto(queuePrivateList.get(i)
								.getAudioauto());
						chatPrivateBean.setContent_c(queuePrivateList.get(i)
								.getContent_c());
						chatPrivateBean.setNick_c(queuePrivateList.get(i)
								.getNick_c());
						chatPrivateBean.setContent_b(queuePrivateList.get(i)
								.getContent_b());
						chatPrivateBean.setContent_l(queuePrivateList.get(i)
								.getContent_l());
						chatPrivateBean.setQp(queuePrivateList.get(i).getQp());
						privateList.add(0, chatPrivateBean);
						midleListView.requestLayout();
						privateChatRoomAdapter2.notifyDataSetChanged();
						// System.out.println("私聊信息：" + midleListView.getCount()
						// + "条");
						if (isBottom2) {
							if (midleListView.getCount() > 0) {
								midleListView.setSelection(midleListView
										.getCount() - 1);
							}
						}
						queuePrivateList.remove(i);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	};

	/**
	 * 1500 毫秒发送系统消息数据
	 * 
	 * @author J.King
	 * 
	 */
	public class MyTimerTaskSystem extends TimerTask {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (_run) {
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				addSystemMsghandler.sendEmptyMessage(1);
			}
		}
	}

	Handler addSystemMsghandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			systemBar.setVisibility(View.VISIBLE);
			try {
				if (queueSystemList.size() > 0) {
					if (queueSystemList.size() == 1) {
						systemBar.setVisibility(View.VISIBLE);
						systemTextView.setText("系统消息："
								+ queueSystemList.get(0).getContent());
						queueSystemList.remove(0);
					} else {
						systemBar.setVisibility(View.VISIBLE);
						for (int i = 0; i < 1; i++) {
							systemTextView.setText("系统消息："
									+ queueSystemList.get(i).getContent());
							queueSystemList.remove(i);
						}
					}
				} else if (queueSystemList.size() == 0) {
					systemBar.setVisibility(View.GONE);
					IsSysBarShow = true;
					new SystemBarThread().start();
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	};

	public Handler rHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			systemBar.setVisibility(View.GONE);
			IsSysBarShow = false;
		};
	};

	class SystemBarThread extends Thread {
		@Override
		public void run() {

			if (IsSysBarShow) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				rHandler.sendEmptyMessage(1);
			}
		}
	}

	/**
	 * 
	 * 发送消息
	 * 
	 * @author J.King
	 * 
	 */
	class SendMsgThread extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					sendMessage();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	private Handler msghandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:
				// dialog.setCancelable(true);
				// dialog.show();
				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				// dialog.cancel();
				sendThread.stopThread(false);
				break;
			}
		};
	};

	private void sendMessage() throws ClientProtocolException, IOException {
		msghandler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);
		final String result = HttpSendMsg.SendMsgPost(roomid, type,
				su.getUid(), toUid, contString, afile, pfile, filetype,
				videoTime, su.getToken());

		// System.out.println(result);
		MessageJson mj = new MessageJson();
		sendMessageList = mj.parseJson(result);
		// System.out.println("消息返回：" + sendMessageList.toString());
		msghandler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		msghandler.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (!sendMessageList.get(0).getTip().equals("")) {
						toastTextView.setText(sendMessageList.get(0).getTip());
						toast.show();
					}
					if (sendMessageList.get(0).getRet().equals("1")) {

						sendThread.stopThread(false);
						if (filetype.equals("afile"))
							myMediaSend.start();
					} else if (sendMessageList.get(0).getRet().equals("0")) {
						sendThread.stopThread(false);

					} else if (sendMessageList.get(0).getRet().equals("")
							|| sendMessageList.get(0).getRet() == null) {
						sendThread.stopThread(false);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

	/**
	 * 在线村民数据加载Handler
	 */
	public Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			// int num = msg.what;
			// if (num == ConstantsJrc.HANDLER_SHOW_PROGRESS) {
			// onlineProgressBar.setVisibility(View.VISIBLE);
			// onlineProgerssBarRelativeLayout.setVisibility(View.VISIBLE);
			// } else if (num == ConstantsJrc.HANDLER_CANCEL_PROGRESS) {
			// onlineProgressBar.setVisibility(View.GONE);
			// onlineProgerssBarRelativeLayout.setVisibility(View.GONE);
			// } else if (num == ConstantsJrc.HANDLER_ADAPTER_REFRESH) {
			// rightListView.requestLayout();
			// // onlineAdapter.notifyDataSetChanged();
			// rightListView.setDivider(getResources().getDrawable(
			// R.drawable.list_line));
			// footLinerLayout.setVisibility(View.GONE);
			// }
		};
	};
	/**
 * 
 */
	public Handler videohandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Bundle b = msg.getData();
			videoTime = b.getInt("time");
			if (videoTime > 50) {
				toastTextView.setText("录音剩余时间" + (60 - videoTime) + "秒");
				toast.show();
			}
			// System.out.println("录音时间：" + b.getInt("time"));
		}

	};

	/**
	 * 退出房间
	 * 
	 * @author J.King
	 * 
	 */
	class ExitRoomThread extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					exitRoom();
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
				// dialog.setCancelable(true);
				// dialog.show();
				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				// dialog.cancel();
				break;
			}
		};
	};

	private void exitRoom() throws ClientProtocolException, IOException {
		roomhandler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);

		final String result = HttpExitRoom.ExitRoomGet(roomid, su.getUid(),
				su.getToken());
		MessageJson mj = new MessageJson();
		if (!"".equals(result)) {
			exitMessageList = mj.parseJson(result);
			roomhandler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
			roomhandler.post(new Runnable() {

				@Override
				public void run() {
                    try {
					if ("1".equals(exitMessageList.get(0).getRet().toString())) {
						su.setMsgPid("");
					}					
				} catch (Exception e) {
					// TODO: handle exception
				}
				}
			});
		} else {
			Commond.showToast(getApplicationContext(), "网络连接出错");
		}

	}

	/**
	 * 焦点切换
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// focus();
		return super.onTouchEvent(event);
	}

	public void focus() {
		ly.setFocusable(true);
		ly.requestFocus();
		ly.setFocusableInTouchMode(true);
		IsShow = true;
	}

	/**
	 * 头标点击监听
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mTabPager.setCurrentItem(index);
		}
	};

	/**
	 * 页面切换监听
	 * 
	 * @author J.King
	 * 
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				list.clear();
				rightListView.setVisibility(View.GONE);
				if (toUid.equals("")) {
					onlineLinearLayout.setVisibility(View.VISIBLE);
					chattospeakLinearLayout.setVisibility(View.GONE);
				} else {
					onlineLinearLayout.setVisibility(View.GONE);
					chattospeakLinearLayout.setVisibility(View.VISIBLE);
				}

				mTab1.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.chatroom_btn_pressed));
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
					mTab2.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.my_chat_btn_normal));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
					if (roomtype == 2) {

						mTab3.setBackgroundResource(R.drawable.online_house_btn_normal);
					} else {
						mTab3.setBackgroundDrawable(getResources().getDrawable(
								R.drawable.online_farmer_btn_normal));
					}
				}
				break;
			case 1:
				list.clear();
				rightListView.setVisibility(View.GONE);
				if (toUid.equals("")) {
					onlineLinearLayout.setVisibility(View.VISIBLE);
					chattospeakLinearLayout.setVisibility(View.GONE);
				} else {
					onlineLinearLayout.setVisibility(View.GONE);
					chattospeakLinearLayout.setVisibility(View.VISIBLE);
				}

				mTab2.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.my_chat_btn_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, one, 0, 0);
					mTab1.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.chatroom_btn_normal));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
					if (roomtype == 2) {
						mTab3.setBackgroundResource(R.drawable.online_house_btn_normal);
					} else {
						mTab3.setBackgroundDrawable(getResources().getDrawable(
								R.drawable.online_farmer_btn_normal));
					}
				}
				break;
			case 2:
				rightListView.setVisibility(View.VISIBLE);
				if (toUid.equals("")) {
					onlineLinearLayout.setVisibility(View.VISIBLE);
					chattospeakLinearLayout.setVisibility(View.GONE);
				} else {
					onlineLinearLayout.setVisibility(View.GONE);
					chattospeakLinearLayout.setVisibility(View.VISIBLE);
				}
				if (roomtype == 2) {
					mTab3.setBackgroundResource(R.drawable.online_house_btn_pressed);
				} else {
					mTab3.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.online_farmer_btn_pressed));
				}
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, two, 0, 0);
					mTab1.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.chatroom_btn_normal));
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
					mTab2.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.my_chat_btn_normal));
				}

				list.clear();
				loading.setVisibility(View.GONE);
				String url = ConstantsJrc.ONLINEUSER + "?" + "roomid=" + roomid
						+ "&pd=" + su.getOnlinePd() + "&page=";
				StringBuffer data = new StringBuffer();
				// 请求网络验证登陆
				HttpRequestTask request = new HttpRequestTask();
				request.execute(url, data.toString());
				break;
			}

			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			// animation.setDuration(50);
			mTabImg.startAnimation(animation);

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	/**
	 * 设置标题栏右侧按钮的作用
	 * 
	 * @param v
	 */
	public void btnmainright(View v) {
		Intent intent = new Intent(ChatRoomActivity.this,
				MainTopRightDialog.class);
		startActivityForResult(intent, ConstantsJrc.RIGHTTOP);

		// startActivity(intent);
	}

	private void createExpressionDialog() {
		int count = 0;

		linearLayout = (LinearLayout) inflater.inflate(R.layout.expression,
				null);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, 300);
		buttomView.addView(linearLayout, params);

		// mViewPager = (ViewPager) findViewById(R.id.viewpager);

		MyScrollView scrollView = (MyScrollView) linearLayout
				.findViewById(R.id.scrollerView1);
		// final ArrayList<View> views = new ArrayList<View>();
		for (int i = 0; i < imageIds.length; i++) {
			if ((i + 1) % 21 == 0 && i < imageIds.length - 1) {
				count++;

				gridView = new ExpressionGridView(this, (count - 1) * 21,
						21 * count);

				// views.add(gridView);
				scrollView.addView(gridView);

				gridView.setOnTouchListener(new OnTouchListener() {

					public boolean onTouch(View v, MotionEvent event) {
						if (event.getAction() == MotionEvent.ACTION_UP) {
							deteleDialog();

						}
						return false;
					}
				});

			} else if (i == imageIds.length - 1) {
				count++;
				gridView = new ExpressionGridView(this, (count - 1) * 21,
						imageIds.length);

				// views.add(gridView);
				scrollView.addView(gridView);

				gridView.setOnTouchListener(new OnTouchListener() {

					public boolean onTouch(View v, MotionEvent event) {
						if (event.getAction() == MotionEvent.ACTION_UP) {
							deteleDialog();
						}
						return false;
					}
				});

			}
		}

		expressionCountLayout = (LinearLayout) linearLayout
				.findViewById(R.id.scrollCount);
		for (int i = 1; i <= count; i++) {
			ImageView imageView;
			// if (i != 1)
			// {
			// imageView = new ImageView(this);
			// params = new LayoutParams(10, LayoutParams.WRAP_CONTENT);
			// expressionCountLayout.addView(imageView, params);
			// }

			imageView = new ImageView(this);
			LinearLayout.LayoutParams par = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			par.setMargins(5, 0, 5, 0);
			if (i == 1) {
				imageView.setBackgroundResource(R.drawable.page_icon_selected);
			} else {
				imageView.setBackgroundResource(R.drawable.page_icon_normal);
			}
			// imageView.setPadding(10, 0, 10, 0);
			expressionCountLayout.addView(imageView, par);
		}

	}

	@Override
	public void deteleDialog() {
		// TODO Auto-generated method stub
		if (dialogView != null) {
			absoluteLayout.removeAllViews();
			dialogView = null;
		}
	}

	@Override
	public void addDialog(int i, int w, int h, int[] x_y) {
		// TODO Auto-generated method stub
		i = expressionNum * 21 + i;
		dialogView = inflater.inflate(R.layout.biaoqing_dialog, null);
		AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(w,
				h, x_y[0] - 10, x_y[1] - 160);
		absoluteLayout.addView(dialogView, params);

		ImageView imageView = (ImageView) dialogView
				.findViewById(R.id.image_biaoqing);

		try {

			Field field = R.drawable.class.getDeclaredField("smiley_" + i);
			int resourceId = Integer.parseInt(field.get(null).toString());
			// imageView.setImageResource(resourceId);

			// 插入表情
			this.messageEt.append(ConstantsJrc.name[i]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addMessage(int i) {
		i = expressionNum * 21 + i;
		try {
			CharSequence convertContent = SmileyParser.getInstance()
					.addSmileySpans(SmileyParser.mSmileyTexts[i]);
			if (speakBtn.getVisibility() == View.VISIBLE) {
				chooseBtn.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.change_video));
				speakBtn.setVisibility(View.GONE);
				messageEt.setVisibility(View.VISIBLE);
				sendBtn.setVisibility(View.VISIBLE);
				chooseBtn.setChecked(false);
			}
			this.messageEt.append(convertContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void changeExpressionCount(int age) {
		expressionNum = age;
		for (int i = 0; i < expressionCountLayout.getChildCount(); i++) {
			expressionCountLayout.getChildAt(i).setBackgroundResource(
					R.drawable.page_icon_normal);
		}
		expressionCountLayout.getChildAt(age).setBackgroundResource(
				R.drawable.page_icon_selected);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (bottomRelative.getVisibility() == View.VISIBLE
					|| buttomView.getVisibility() == View.VISIBLE) {
				bottomRelative.setVisibility(View.GONE);
				buttomView.setVisibility(View.GONE);
				IsShow = true;
			} else {
				finish();
				// if (IsExit) {
				// Intent intent = new Intent(getApplicationContext(),
				// ExitDialog.class);
				// startActivityForResult(intent, 2);
				// }
			}
		}

		return false;

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		if (resultCode == ConstantsJrc.NONE)
			return;
		if (requestCode == ConstantsJrc.RIGHTTOP) {
			otherui = true;
			if (resultCode == ConstantsJrc.RIGHTSOUND) {
				if (su.getAudioModel().equals("0")) {
					receiveModelImageView
							.setImageResource(R.drawable.receiver_icon);
				} else {
					receiveModelImageView
							.setImageResource(R.drawable.speaker_icon);

				}
			}
			if (resultCode == ConstantsJrc.RIGHTPIC) {
				if (su.getPicAuto().equals("1")) {
					leftListView.requestLayout();
					chatRoomAdapter2.notifyDataSetChanged();
				} else {
					leftListView.requestLayout();
					chatRoomAdapter2.notifyDataSetChanged();
				}
			}
			if (resultCode == ConstantsJrc.RIGHTAUDIO) {
				if (su.getAudioAuto().equals("1")) {
					leftListView.requestLayout();
					chatRoomAdapter2.notifyDataSetChanged();
				} else {
					leftListView.requestLayout();
					chatRoomAdapter2.notifyDataSetChanged();
				}
			}
		}
		if (requestCode == 1) {
			otherui = true;
			if (resultCode == 2020) {
				toUid = data.getStringExtra("touid");
				toNick = data.getStringExtra("tonick");
				if (!toUid.equals(su.getUid())) {
					chatPrivateCloseButton
							.setBackgroundDrawable(getResources()
									.getDrawable(
											R.drawable.chatroom_private_close_btn_pressed));
					chatPrivateOpenButton
							.setBackgroundDrawable(getResources()
									.getDrawable(
											R.drawable.chatroom_private_open_btn_normal));
					messageEt.setHint("对" + toNick + "说:");
					type = 0;
					mTabPager.setCurrentItem(0);
					chatToUserTextView.setText(toNick);
					chattospeakLinearLayout.setVisibility(View.VISIBLE);
					onlineLinearLayout.setVisibility(View.GONE);
				} else {
					toastTextView.setText("自己和自己聊没意思");
					toast.show();
				}
			}
		}
		if (requestCode == 2) {
			if (data != null) {
				if (resultCode == 404) {
					IsExit = data.getBooleanExtra("isexit", true);
				}
			} else {
				IsExit = true;
			}

		}
		if (requestCode == ConstantsJrc.ERROR) {
			if (bottomRelative.getVisibility() == View.VISIBLE
					|| buttomView.getVisibility() == View.VISIBLE) {
				bottomRelative.setVisibility(View.GONE);
				buttomView.setVisibility(View.GONE);
			}
		}
		// 图片选择
		if (requestCode == ConstantsJrc.PHOTOZOOM) {
			IsShow = true;
			if (data == null) {
				return;
			}

			Uri uri = data.getData();
			if (uri.toString().substring(0, 7).equals("file://")) {
				// picPath = uri.toString().substring(7,
				// uri.toString().length());
				picPath = uri.getPath();
				su.setOldPicPath(picPath);
			} else {
				Cursor cursor = this.getContentResolver().query(uri, null,
						null, null, null);
				cursor.moveToFirst();
				picPath = cursor.getString(cursor.getColumnIndex("_data"));
				su.setOldPicPath(picPath);
			}
			startPhotoZoom(picPath, "pic");
		}

		// 拍照
		if (requestCode == ConstantsJrc.PHOTOHRAPH) {
			IsShow = true;
			// 设置文件保存路径这里放在指定目录下
			File picture = new File(su.getOldPicPath());
			// System.out.println("------------------------" +
			// picture.getPath());
			picPath = picture.getPath();
			startPhotoZoom(picPath, "camera");
		}

		if (requestCode == ConstantsJrc.PHOTORESOULT) {
			if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
				toastTextView.setText("检测到网络网络异常或未开启");
				toast.show();
				return;
			}

			if (data == null)
				return;
			String newurl = data.getStringExtra("newurl");
			// Log.i("newurl", ">>>:" + newurl);
			filetype = "pfile";
			contString = "";
			afile = "";
			File file = new File(newurl);
			Bitmap bmp = BitmapFactory.decodeFile(su.getNewPicPath());
			// Log.i("newurl", ">>>:" + bmp.getWidth() + "," + bmp.getHeight());
			pfile = file.getPath();
			sendThread = new SendMsgThread();
			if (currIndex == 2)
				mTabPager.setCurrentItem(0);
			if (file.exists()) {
				ChatMessageBean chatBean = new ChatMessageBean();
				chatBean.setFuid(Integer.parseInt(su.getUid()));
				chatBean.setFheader(su.getHeader());
				chatBean.setRoomid(roomid);
				chatBean.setType(type);
				chatBean.setTnick(toNick);
				chatBean.setTuid(toUid);
				chatBean.setMsgtype(5);
				chatBean.setPw(bmp.getWidth());
				chatBean.setPh(bmp.getHeight());
				chatBean.setPlocalfile(picPath);
				chatBean.setQp(qp);
				chatBean.setIsOK(0);
				chatBean.setIscome(false);
				// Log.i("newurl", "su:" + su.getNewPicPath());
				chatBean.setPfile(su.getNewPicPath());
				chatBean.setPd(DateManager.getPhoneTime());

				// sendThread.start();

				privateList.add(0, chatBean);
				midleListView.requestLayout();
				privateChatRoomAdapter2.notifyDataSetChanged();
				if (midleListView.getCount() > 0) {
					midleListView.setSelection(midleListView.getCount() - 1);
				}

				chatList.add(0, chatBean);
				leftListView.requestLayout();
				chatRoomAdapter2.notifyDataSetChanged();
				// System.out.println(leftListView.getCount() + "个");
				if (leftListView.getCount() > 0) {
					leftListView.setSelection(leftListView.getCount() - 1);
				}
				IsShow = true;
			}

		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	public void startPhotoZoom(String url, String type) {
		Intent intent = new Intent(ChatRoomActivity.this,
				PicHandleActivity.class);
		intent.putExtra("type", type);
		intent.putExtra("url", url);
		// Log.i("ttt", "Chatroom:" + url);
		startActivityForResult(intent, ConstantsJrc.PHOTORESOULT);
	}

	// private void registerHeadsetPlugReceiver() {
	// headsetPlugReceiver = new HeadsetPlugReceiver();
	// IntentFilter filter = new IntentFilter();
	// filter.addAction("android.intent.action.HEADSET_PLUG");
	// registerReceiver(headsetPlugReceiver, filter);
	// }
	//
	// private void unregisterReceiver() {
	// this.unregisterReceiver(headsetPlugReceiver);
	// }

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		new ExitRoomThread().start();
		if (myMediaStart != null) {
			myMediaStart.release();
			myMediaStart = null;
		}
		if (myMediaSend != null) {
			myMediaSend.release();
			myMediaSend = null;
		}
		if (mediaPlayer != null) {
			mediaPlayer.release();
			mediaPlayer = null;
		}
		chatRoomAdapter2.clear();
		this.receiveTime.cancel();
		this.addTime.cancel();
		this.systemMsgTime.cancel();
		queueList.clear();
		queuePrivateList.clear();
		queueSystemList.clear();
		su.setMsgPid("");
		NetImageViewCache.getInstance().clear();
		BitmapCacheChatRoom.getIntance().clearCacheBitmap();
		BitmapCacheOnline.getIntance().clearCacheBitmap();
		BitmapCacheBguanzhu.getIntance().clearCacheBitmap();
		BitmapCacheBlack.getIntance().clearCacheBitmap();
		BitmapCacheVillage.getIntance().clearCacheBitmap();
		// if (audio.isWiredHeadsetOn() == true) {
		// AudioManager audioManager = (AudioManager)
		// getSystemService(Context.AUDIO_SERVICE);
		// audioManager.setMode(AudioManager.MODE_IN_CALL);// 把模式调成听筒放音模式
		// audioManager.setSpeakerphoneOn(false);
		// }
		su.setAudioModel("0");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		chatRoomAdapter2.clear();
		// chatRoomAdapter2.stop();
		// System.out.println("最小化了");
		// if (isphonepause) {
		// AudioManager audioManager = (AudioManager)
		// getSystemService(Context.AUDIO_SERVICE);
		// audioManager.setMode(AudioManager.MODE_IN_CALL);// 把模式调成听筒放音模式
		// audioManager.setSpeakerphoneOn(false);
		// }
		// if (audio.isWiredHeadsetOn() == true) {
		// AudioManager audioManager = (AudioManager)
		// getSystemService(Context.AUDIO_SERVICE);
		// audioManager.setMode(AudioManager.MODE_IN_CALL);// 把模式调成听筒放音模式
		// audioManager.setSpeakerphoneOn(false);
		// }

		if (chatRoomAdapter2.mediaPlayer != null) {
			chatRoomAdapter2.mediaPlayer.pause();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (otherui == true) {
			isphonepause = false;
		} else {
			isphonepause = true;
		}

		if (isphonepause) {
			// if (audio.isWiredHeadsetOn() == true) {
			// AudioManager audioManager = (AudioManager)
			// getSystemService(Context.AUDIO_SERVICE);
			// audioManager.setMode(AudioManager.MODE_IN_CALL);// 把模式调成听筒放音模式
			// audioManager.setSpeakerphoneOn(false);
			// } else {
			// AudioManager audioManager = (AudioManager)
			// getSystemService(Context.AUDIO_SERVICE);
			// audioManager.setMode(AudioManager.ROUTE_SPEAKER);// 把模式调扬声器模式
			// audioManager.setSpeakerphoneOn(true);
			// }
		}
		super.onResume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		// myMediaStart.stop();
		vr.Recycle();
		super.onStop();
	}

	/**
	 * 初始化组件
	 */
	public void InitView() {
		backBtn = (ImageButton) findViewById(R.id.left_btn);
		addBtn = (ImageButton) findViewById(R.id.add_btn);
		bottomRelative = (RelativeLayout) findViewById(R.id.main_bottom_menu);
		ly = (RelativeLayout) findViewById(R.id.focus_ly);
		messageEt = (EditText) findViewById(R.id.message_editText);
		bottomLayout = (LinearLayout) findViewById(R.id.LayoutButtonDetailBottom);
		chooseBtn = (CheckBox) findViewById(R.id.choose_btn);
		speakBtn = (Button) findViewById(R.id.speak_btn);
		sendBtn = (Button) findViewById(R.id.send_btn);
		onlineCountTextView = (TextView) findViewById(R.id.online_farmer_count_TextView);
		expressionBtn = (ImageButton) findViewById(R.id.expression_btn);
		cameraBtn = (ImageButton) findViewById(R.id.camera_btn);
		photoBtn = (ImageButton) findViewById(R.id.photo_btn);
		chattospeakLinearLayout = (LinearLayout) findViewById(R.id.chattospeakLinearLayout);
		chatToUserTextView = (TextView) findViewById(R.id.chatroom_touser_TextView);
		onlineLinearLayout = (LinearLayout) findViewById(R.id.onlineLinearLayout);
		chatRoomCloseButton = (ImageButton) findViewById(R.id.chatroom_close_private_Button);
		chatPrivateCloseButton = (ImageButton) findViewById(R.id.chatroom_private_close_ImageButton);
		chatPrivateOpenButton = (ImageButton) findViewById(R.id.chatroom_private_open_ImageButton);
		systemBar = (RelativeLayout) findViewById(R.id.systemBar);
		systemTextView = (TextView) findViewById(R.id.system_msg_TextView);
		systemCloseImageView = (ImageView) findViewById(R.id.system_close_ImageView);
		receiveModelImageView = (ImageView) findViewById(R.id.receiver_model_imageview);
		speakRelativeLayout = (RelativeLayout) findViewById(R.id.chatroom_speak_RelativeLayout);
		recorder_volumeImageView = (ImageView) findViewById(R.id.chatroom_speakvolume_ImageView);
		chatroom_top_title = (NetImageView) findViewById(R.id.chatroom_top_title);
		chatLiwuImageButton = (ImageButton) findViewById(R.id.chat_liwu_btn);
	}

	// 点击任意地方取消软键盘
	@Override
	public boolean dispatchTouchEvent(MotionEvent e) {
		// if (e.getAction() == MotionEvent.ACTION_DOWN) {
		// //类型为Down才处理，还有Move/Up之类的
		Rect r = new Rect();
		bottomLayout.getGlobalVisibleRect(r);
		if (r.contains((int) e.getX(), (int) e.getY())) {
			return super.dispatchTouchEvent(e);
		} else {
			if (!messageEt.hasFocus()) {
				return super.dispatchTouchEvent(e);
			} else {
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.hideSoftInputFromWindow(
						messageEt.getWindowToken(), 0);
				messageEt.clearFocus();
				if (bottomRelative.getVisibility() == View.VISIBLE
						|| buttomView.getVisibility() == View.VISIBLE) {
					bottomRelative.setVisibility(View.GONE);
					buttomView.setVisibility(View.GONE);
				}
				IsShow = true;
				return false;
			}
		}
	}

	@Override
	public void resultData(String url, String result) {
		// TODO Auto-generated method stub

		try {
			// System.out.println("url:" + url);
			OnlineUserListJson oujson = new OnlineUserListJson();
			morelist = oujson.parseJson(result);
			MessageJson mj = new MessageJson();
			msgList = mj.parseJsonPd(result);
			su.setOnlinePd(msgList.get(0).getPd());
			if (morelist.size() == 0) {
				rightListView.removeFooterView(vFooter);
				// vFooter.setVisibility(View.GONE);
				// loading.setVisibility(View.GONE);
			}
			for (OnlineUserBean u : morelist) {
				list.add(u);
			}
			morelist.clear();
			if (isfoot == false) {
				onlineAdapter = new OnLineUserAdapter(ChatRoomActivity.this,
						list, rightListView);
				rightListView.setAdapter(onlineAdapter);
				onlineProgerssBarRelativeLayout.setVisibility(View.GONE);
			} else {
				rightListView.requestLayout();
				onlineAdapter.notifyDataSetChanged();
				onlineProgerssBarRelativeLayout.setVisibility(View.GONE);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
