package com.app.chatroom.otherui;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
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
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.app.chatroom.Appstart;
import com.app.chatroom.Chakandatu;
import com.app.chatroom.DeleteOrBorwser;
import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.adapter.BguanzhuAdapter;
import com.app.chatroom.adapter.BlackListAdapter;
import com.app.chatroom.adapter.LiwuListAdapter;
import com.app.chatroom.adapter.LiwuTypeListAdapter;
import com.app.chatroom.adapter.MyLiWuAdapter;
import com.app.chatroom.adapter.PitemsAdapter;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.http.HttpBlakcUser;
import com.app.chatroom.http.HttpFollow;
import com.app.chatroom.http.HttpGetCode;
import com.app.chatroom.http.HttpOnlineUserInfo;
import com.app.chatroom.http.HttpUserInfo;
import com.app.chatroom.imgzoom.ImageZoom;
import com.app.chatroom.json.AttentionUserListJson;
import com.app.chatroom.json.BlackListJson;
import com.app.chatroom.json.LiwuListJson;
import com.app.chatroom.json.LiwuTypeListJson;
import com.app.chatroom.json.MessageJson;
import com.app.chatroom.json.MyLiwuItemJson;
import com.app.chatroom.json.OnlineUserInfoJson;
import com.app.chatroom.json.PicphotoJson;
import com.app.chatroom.json.PitemsJson;
import com.app.chatroom.json.UserInfoJson;
import com.app.chatroom.json.bean.AttentionUserBean;
import com.app.chatroom.json.bean.BlackListBean;
import com.app.chatroom.json.bean.ChatMessageBean;
import com.app.chatroom.json.bean.LiwuItemsBean;
import com.app.chatroom.json.bean.LiwuTypeBean;
import com.app.chatroom.json.bean.MessageBean;
import com.app.chatroom.json.bean.MyLiwuItemsBean;
import com.app.chatroom.json.bean.PicphotoBean;
import com.app.chatroom.json.bean.PitemsBean;
import com.app.chatroom.json.bean.UserBean;
import com.app.chatroom.pic.AsynImageLoader;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.ui.DeliverData;
import com.app.chatroom.ui.HttpBaseActivitytwo;
import com.app.chatroom.ui.MailContentActivity;
import com.app.chatroom.updata.FileUpload;
import com.app.chatroom.util.Commond;
import com.app.chatroom.util.EditTextUtil;
import com.app.chatroom.util.PhoneInfo;
import com.app.chatroom.util.SystemUtil;
import com.app.chatroom.view.NetImageView;
import com.app.chatroom.viewdate.OnWheelChangedListener;
import com.app.chatroom.viewdate.WheelView;
import com.app.chatroom.viewdate.adapter.ArrayWheelAdapter;
import com.app.chatroom.viewdate.adapter.NumericWheelAdapter;
import com.duom.fjz.iteminfo.Backpacks;
import com.duom.fjz.iteminfo.BitmapCacheDzlb;
import com.duom.fjz.iteminfo.TestUpload;
import com.duom.fjz.iteminfo.Tixing;
import com.jianrencun.android.Choosewhich;
import com.jianrencun.android.Shangchuan;
import com.jianrencun.android.Xjfabu;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Comment;
import com.jianrencun.dazui.DazuiActivity;
import com.jianrencun.dazui.Dzmysave;
import com.jianrencun.dazui.Mydazui;
import com.jianrencun.dynamic.Dynamic;
import com.tencent.open.HttpStatusException;
import com.tencent.open.NetworkUnavailableException;
import com.tencent.tauth.Constants;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.weibo.sina.AuthorizeActivity;

public class VillageUserInfoDialog extends HttpBaseActivitytwo implements
		OnScrollListener {
	private ViewPager mTabPager;
	private ImageView mTabImg, mTabImg2;// 动画图片
	private ImageView mTab1, mTab2;
	private int zero = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int one;// 单个水平动画位移
	// 每个页面的view数据
	public ArrayList<View> views = new ArrayList<View>();
	PagerAdapter mPagerAdapter = null;
	View view1;
	View view2;
	View view3;
	View view4;
	View view5;
	View view6;
	View view7;
	View view8;
	String uid = "";
	String nick = "";
	String fuid = "";
	int src = 0;
	int oid = 0;
	String buid = "";
	String bfuid = "";
	String blackuid = "";
	String blackfuid = "";
	String chatroom = "";
	int type = 0;
	int flg = 0;
	int blackflg = 0;
	ArrayList<UserBean> villageUserInfoList = new ArrayList<UserBean>();
	LoadDataThread loadThread;

	// 个人资料布局
	NetImageView village_userinfo_photo;
	ImageView village_userinfobg_ImageView;
	TextView village_userinfoid_TextView;
	TextView village_userinfoscore_TextView;
	TextView village_userinfolevel_TextView;
	TextView village_userinfonick_TextView;
	TextView village_userinfosign_TextView, infotv;
	ImageView village_userinfosex_ImageView;
	TextView village_userinfocity_TextView;
	TextView village_userinfobirthday_TextView;
	// ImageButton village_userinfo_speakButton;
	// ImageButton village_userinfo_speakButton2;
	ImageButton village_userinfo_closeButton;
	ImageButton village_userinfo_save_btn;
	ProgressBar villageuserinfo_progressbar;
	RelativeLayout villageuserinfo_progressbarRelativeLayout;
	TextView village_userinfo_guanzhu_number_TextView;
	TextView village_userinfo_fensi_number_TextView;
	TextView village_userinfo_liwu_number_TextView;
	TextView village_userinfo_dazui_number_TextView;
	TextView village_userinfo_xiaojian_number_TextView;
	TextView village_userinfo_beibao_number_TextView;
	TextView village_userinfo_time;
	// ImageButton village_userinfo_edit_btn;// 编辑按钮
	// ImageButton village_userinfo_xiaoliwuButton;// 送礼按钮
	ImageButton village_userinfo_blacklistButton;// 黑名单按钮
	ImageView village_userinfo_dialog_title;
	RelativeLayout village_userinfo_fensi_rl_btn;// 被关注
	RelativeLayout village_userinfo_guanzhu_rl_btn;// 关注
	RelativeLayout village_userinfo_liwu_rl_btn;// 礼物
	RelativeLayout village_userinfo_xiaojian_rl_btn;// 小贱
	RelativeLayout village_userinfo_dazui_rl_btn;// 大嘴
	RelativeLayout village_userinfo_beibao_rl_btn;// 背包
	ImageView village_userscorewhat_ImageView;// 财富问号按钮

	PitemsAdapter pitAdapter;
	GridView village_userinfo_icon_gridView;
	ArrayList<PitemsBean> pitList = new ArrayList<PitemsBean>();
	ArrayList<PicphotoBean> picphotos = new ArrayList<PicphotoBean>();
	FrameLayout HorizontalScrollView1;
	/* 个人信息 */
	ImageButton chooseIDBtn;
	RelativeLayout chooseHeaderImageButton;
	ImageButton userpassword_Button, infoedit;
	NetImageView userHeaderImageView;
	TextView userIDTextView;
	TextView userScoreTextView;
	TextView userLevelTextView;
	EditText userNickEditText;
	EditText userSignEditText;
	RadioGroup sexRadioGroup;
	RadioButton userBoyRadioButton, userGirlRadioButton;
	EditText userCityEditText;
	EditText userBirthdayEditText;
	EditText userQQEdtiText;
	EditText userWeixinEditText;
	// EditText userPhoneEditText;
	// EditText userEmailEditText;
	EditText editYear, editMonth, editDay;
	LinearLayout linear_WheelView;
	WheelView useryear_WheelView;
	WheelView usermonth_WheelView;
	WheelView userday_WheelView;
	Calendar calendar = Calendar.getInstance();
	TextView userbirthday_EditText;
	ImageButton qqbd, sinabd;
	ScrollView user_scrollView;
	// 切换账号
	EditText choosephone_EditText;
	ImageButton phone_code_btn;
	EditText chooseemail_EditText;
	ImageButton email_code_btn;
	EditText input_code_EditText;
	ImageButton user_login_Button;
	ArrayList<UserBean> userList = new ArrayList<UserBean>();
	ChooseIdThread chooseThread;
	ReLoginThread reLoginThread;
	ScrollView chooseid_scrollView;
	String phone = "";
	String email = "";

	// 每页的布局控件
	ListView beiguanzhuListView;
	public ProgressBar villagebeiguanzhuProgressBar;//
	public RelativeLayout villagevbeiguanzhuRelativeLayout;
	ListView guanzhuListView;
	public ProgressBar villageguanzhuProgressBar;//
	public RelativeLayout villagevguanzhuRelativeLayout;
	GridView liwuGridView;
	public ProgressBar villageliwuListProgressBar;//
	public RelativeLayout villageliwuListRelativeLayout;
	ListView myLiwuListView;
	public ProgressBar villagemyliwuListProgressBar;//
	public RelativeLayout villagemyliwuListRelativeLayout;
	ListView blackListView;
	public ProgressBar villageblackListProgressBar;//
	public RelativeLayout villageblackListRelativeLayout;
	/* 配置文件 */
	SharedPreferences sp;
	SystemSettingUtilSp su;
	AsynImageLoader asynImageLoader;

	private Dialog dialog = null;
	private Dialog dialog2 = null;
	LoadUserDataThread loaduserDataThread;
	UpLoadPhotoThread upLoadThread;
	ModifyUserInfo modifyThread;
	String sex = "0";
	String photoPath = "";
	String photoUrl = "";
	public static String sunick;
	//
	private static final String TAG = "TAuthDemoActivity";
	public static final int REQUEST_PICK_PICTURE = 1001;
	private static final String CALLBACK = "auth://tauth.qq.com/";

	public String mAppid = "100387196";// 申请时分配的appid
	private String scope = "get_simple_userinfo ,add_topic";// 授权范围
	// private AuthReceiver receiver;
	String mat, mad, mod, json;
	public String mAccessToken, mOpenId;
	int loopCount;
	public static int k = 0;
	public static String unick, header;
	private SharedPreferences prefs;

	private Handler mHandler;
	private Tencent mTencent;
	private static final String SCOPE = "get_simple_userinfo ,add_topic";
	// 勾搭、拉黑
	ArrayList<MessageBean> messageList = new ArrayList<MessageBean>();
	ArrayList<MessageBean> messageList2 = new ArrayList<MessageBean>();
	ArrayList<MessageBean> messageblakcList = new ArrayList<MessageBean>();
	ArrayList<MessageBean> messageblakcList2 = new ArrayList<MessageBean>();
	FollowThread followThread;
	FollowThread2 followThread2;
	boolean isfollow = true;
	BlackThread blackThread;
	BlackThread2 blackThread2;
	boolean isblack = true;

	// 各个页面数据加载
	public BguanzhuAdapter bguanzhuAdapter;
	public BguanzhuAdapter bguanzhuAdapter2;
	private View vFooter;
	private LinearLayout loading;
	boolean isfoot = false;
	boolean isfoot2 = false;
	int pagebeicount = 1;
	int pageguancount = 1;
	ArrayList<String> dlpl = new ArrayList<String>();
	ArrayList<Integer> dlint = new ArrayList<Integer>();
	public ArrayList<AttentionUserBean> attentionList = new ArrayList<AttentionUserBean>();
	public ArrayList<AttentionUserBean> attentionList2 = new ArrayList<AttentionUserBean>();
	public ArrayList<AttentionUserBean> attentionmoreList = new ArrayList<AttentionUserBean>();
	public ArrayList<MessageBean> msgList = new ArrayList<MessageBean>();
	AttentionUserBean aubean;
	// 礼物列表
	public LiwuListAdapter liwuAdapter;
	boolean isgridfoot = false;
	int pagegridcount = 2;
	public ArrayList<LiwuItemsBean> liwuitemList = new ArrayList<LiwuItemsBean>();
	public ArrayList<LiwuItemsBean> liwumoreitemList = new ArrayList<LiwuItemsBean>();
	// 我的礼物
	public MyLiWuAdapter myLiwuAdapter;
	boolean isMyLiWuFoot = false;
	int pagemyliwucount = 2;
	public ArrayList<MyLiwuItemsBean> myliwuitemlist = new ArrayList<MyLiwuItemsBean>();
	public ArrayList<MyLiwuItemsBean> myliwumoreitemlist = new ArrayList<MyLiwuItemsBean>();
	boolean IsLoadShop = true;
	boolean IsLoadUserInfo = true;
	public int pageselect = 0;
	boolean isShowChooseID = true;
	//
	BlackListAdapter blackListAdapter;
	boolean isBlackFoot = false;
	int pageblacklist = 1;
	public ArrayList<BlackListBean> myblackList = new ArrayList<BlackListBean>();
	public ArrayList<BlackListBean> myblackmoreList = new ArrayList<BlackListBean>();
	BlackListBean blackBean;
	ArrayList<ChatMessageBean> chatmsglist = new ArrayList<ChatMessageBean>();
	// 礼物分类GIFTLIST
	LiwuTypeListAdapter liwuTypeListAdapter;
	ListView liwuTypeListView;
	RelativeLayout gridRelative;
	RelativeLayout liwuTypeRelative;
	public ProgressBar villageliwutypeListProgressBar;//
	public RelativeLayout villageliwutypeListRelativeLayout;
	boolean isliwutypefoot = false;
	int pageliwutypecount = 2;
	boolean IsLoadShopType = true;
	public ArrayList<LiwuTypeBean> liwutypeitemList = new ArrayList<LiwuTypeBean>();
	public ArrayList<LiwuTypeBean> liwutypemoreitemList = new ArrayList<LiwuTypeBean>();
	private Bitmap bitmap;
	String jspath = "";// JS脚本路径
	String jsname = "uid.js";// 脚本名字
	private ImageView infojiantou;

	private LinearLayout ll, ll_chat, liwull;
	private ImageView iv;
	DisplayMetrics dm;
	String haomiao;
	private ImageButton info_sixin, info_songi, info_chat, info_gouda;
	private ScrollView sv;
	int i = 0;
	RelativeLayout rl;
	// / 用来判断 返回的状态。
	public static final int NONE = 0;
	public static final int PHOTOHRAPH = 1;// 拍照
	public static final int PHOTORESOULT = 3;// 结果

	private String path;
	List<String> scpicpath = new ArrayList<String>();
	Bitmap bmp, smallbmp, bitinfo;
	TestUpload tul;
	private TextView jiecaolev, jiecaodangqian, jiecaoxuyao;
	private ProgressBar jiecaobg;
	private File picfile;
	private String dazuidown = MainMenuActivity.dazuisdown.toString();
	private ArrayList<String> urls = new ArrayList<String>();
	private int picdeleteid;
	private ArrayList<String> photopaths;
	private ArrayList<Integer> picids = new ArrayList<Integer>();
	PicphotoBean pit;
	private TextView maohao, xiegang;
	private List<ImageView> iv1s = new ArrayList<ImageView>();
	private List<ImageView> ivks = new ArrayList<ImageView>();
	int changepo, picid;
	private LinearLayout xinxi_caifu_ll;
	private RelativeLayout info_layout;

	private int page, po;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.villageuserinfo_dialog_new);
		// 启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		asynImageLoader = new AsynImageLoader(this);
		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
		tul = new TestUpload(false);
		photopaths = new ArrayList<String>();
		initView();

		dm = new DisplayMetrics();
		((Activity) this).getWindowManager().getDefaultDisplay().getMetrics(dm);

		Intent intent = this.getIntent();
		uid = intent.getStringExtra("uid"); // 对方ID
		nick = intent.getStringExtra("nick");
		fuid = intent.getStringExtra("fuid"); // 自己ID
		chatroom = intent.getStringExtra("chatroom");// 发送方向
		src = intent.getIntExtra("src", 0);// 区分礼物跳转
		oid = intent.getIntExtra("oid", 0);// 其他类型

		int where = intent.getIntExtra("where", 0);
		String tp = intent.getStringExtra("tp");
		if (!TextUtils.isEmpty(tp)) {
			Intent toit = new Intent();
			toit.setClass(this, Tixing.class);
			toit.putExtra("where", where);
			toit.putExtra("tp", tp);
			startActivity(toit);
		}
		// 区分关注，以防混乱
		buid = uid;
		bfuid = fuid;
		blackuid = uid;
		blackfuid = fuid;

		// 判断是从聊天室打开，还是村委会
		type = intent.getIntExtra("type", 0);// 0=不显示， 1=聊天室，2=村委会

		if (SystemUtil.getSDCardMount()) {
			jspath = Environment.getExternalStorageDirectory() + File.separator
					+ getPackageName() + File.separator + "donghuazip";
		} else {
			jspath = ConstantsJrc.PROJECT_PATH + getPackageName()
					+ File.separator + "donghuazip";
		}
		// 底部加载进度
		vFooter = LayoutInflater.from(this).inflate(R.layout.list_footer, null);
		loading = (LinearLayout) vFooter.findViewById(R.id.list_footer);
		loading.setVisibility(View.GONE);
		// 进度条实例化，为了返回销毁
		dialog = new Dialog(this, R.style.theme_dialog_alert);
		dialog.setContentView(R.layout.dialog_layout);
		dialog2 = new Dialog(this, R.style.theme_dialog_alert);
		dialog2.setContentView(R.layout.dialog_layout2);

		info_layout = (RelativeLayout) findViewById(R.id.info_layout);

		mTabPager = (ViewPager) findViewById(R.id.village_dialog_tabpager);
		mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());

		LayoutInflater mLi = LayoutInflater.from(this);
		view1 = mLi.inflate(R.layout.village_userinfo_dialog_xinxi, null);
		view2 = mLi.inflate(R.layout.village_userinfo_dialog_beiguanzhu, null);
		view3 = mLi.inflate(R.layout.village_userinfo_dialog_guanzhu, null);
		// view4 = mLi.inflate(R.layout.village_userinfo_dialog_liwulist, null);
		view4 = mLi.inflate(R.layout.village_userinfo_dialog_liwu_type, null);
		view5 = mLi.inflate(R.layout.village_userinfo_dialog_myliwu, null);
		view6 = mLi.inflate(R.layout.village_userinfo_dialog_usercenter, null);
		view7 = mLi.inflate(R.layout.village_userinfo_dialog_chooseid, null);
		view8 = mLi.inflate(R.layout.village_userinfo_dialog_blacklist, null);
		initView1(view1);
		initView2(view2);
		initView3(view3);
		initView4(view4);
		initView5(view5);
		initView6(view6);
		// initView7(view7);
		initView8(view8);

		views.add(view1);

		// views.add(null);
		// views.add(view2);
		// views.add(view3);
		// views.add(view4);
		// views.add(view5);
		// views.add(view6);
		// views.add(view7);

		// 填充ViewPager的数据适配器
		mPagerAdapter = new PagerAdapter() {

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

			public int getItemPosition(Object object) {
				return POSITION_NONE;
			};

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(views.get(position));
				return views.get(position);
			}

		};

		mTabPager.setAdapter(mPagerAdapter);
		if ("1".equals(chatroom)) {
			villageuserinfo_progressbarRelativeLayout.setVisibility(View.GONE);
			views.add(view4);
			pageselect = 3;
			mPagerAdapter.notifyDataSetChanged();
			mTabPager.setCurrentItem(1);
		}
		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setClass(VillageUserInfoDialog.this, Choosewhich.class);
				startActivityForResult(intent, 5);
				haomiao = String.valueOf(System.currentTimeMillis());

				// View itemView =
				// LayoutInflater.from(VillageUserInfoDialog.this).inflate(
				// R.layout.photoview, null);
				// ImageView iv =
				// (ImageView)itemView.findViewById(R.id.photoview_iv);
				// rl =
				// (RelativeLayout)itemView.findViewById(R.id.photoview_rl);
				// rl.setTag(i);
				// ll.addView(itemView);
				// LinearLayout.LayoutParams linearParams =
				// (LinearLayout.LayoutParams)rl.getLayoutParams();
				// linearParams.width=dm.widthPixels*1/7;
				// linearParams.height=linearParams.width;
				// linearParams.leftMargin = dm.widthPixels*1/8/8*2;
				// itemView.setLayoutParams(linearParams);
				// i++;
				//
				// itemView.setOnClickListener(new OnClickListener() {
				//
				// @Override
				// public void onClick(View v) {
				// // TODO Auto-generated method stub
				// if(v.getTag().equals(0)){
				// ll.removeView(v);
				// }
				// }
				// });
			}
		});

		// 判断可显示功能
		if (!uid.equals(fuid)) {
			village_userinfo_blacklistButton
					.setBackgroundResource(R.drawable.chatroom_userinfo_dialog_addblack_btn);
			// village_userinfo_edit_btn
			// .setBackgroundResource(R.drawable.chatroom_userinfo_dialog_xiaoguanzhu_btn);
			infoedit.setVisibility(View.GONE);
			village_userinfo_guanzhu_rl_btn.setVisibility(View.GONE);
			village_userinfo_fensi_rl_btn
					.setBackgroundResource(R.drawable.beigouda_yuan1);
		} else {
			village_userinfo_xiaojian_rl_btn
					.setBackgroundResource(R.drawable.chatroom_userinfo_dialog_xiaojian_btn2);
			village_userinfo_dazui_rl_btn
					.setBackgroundResource(R.drawable.chatroom_userinfo_dialog_dazui_btn2);
			village_userinfo_beibao_rl_btn.setVisibility(View.VISIBLE);
			village_userinfo_beibao_rl_btn
					.setBackgroundResource(R.drawable.chatroom_userinfo_dialog_beibao_btn2);
			infoedit.setVisibility(View.VISIBLE);
			village_userinfo_guanzhu_rl_btn.setVisibility(View.VISIBLE);
		}

		switch (type) {
		case 1:

			ll_chat.setVisibility(View.VISIBLE);
			break;

		case 2:
			ll_chat.setVisibility(View.GONE);
			break;
		}

		loadThread = new LoadDataThread();
		loadThread.start();

		infotv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(getApplicationContext(),
						SystemMsgWebDialog.class);
				intent.putExtra(
						"link",
						ConstantsJrc.MAINMORE
								+ "?uid="
								+ su.getUid()
								+ "&flg=7&w="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getWidth(VillageUserInfoDialog.this)
								+ "&pkg="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getPackage(VillageUserInfoDialog.this)
								+ "&ver="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getVersionName(
												VillageUserInfoDialog.this)
								+ "&rid="
								+ villageUserInfoList.get(0).getJzid());
				intent.putExtra("type", "7");
				startActivity(intent);
			}
		});

		info_sixin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (uid.equals(su.getUid())) {
					Commond.showToast(getApplicationContext(), "和自己发私信脑袋有问题");
					return;
				} else {
					Intent intent = new Intent(getApplicationContext(),
							MailContentActivity.class);
					intent.putExtra("fuid", uid);
					intent.putExtra("fnick", nick);
					startActivity(intent);
				}
			}
		});

		info_chat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (type == 1) {
					if (uid.equals(su.getUid())) {
						Commond.showToast(getApplicationContext(), "和自己说话脑袋有问题");

						return;
					} else {
						getIntent().putExtra("touid", uid);
						getIntent().putExtra("tonick", nick);
						setResult(2020, getIntent());
						finish();
					}

				}
			}
		});

		info_gouda.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (villageUserInfoList.get(0).getIsf() == 0) {
					if (isfollow) {
						flg = 1;
						followThread = new FollowThread();
						followThread.start();
						isfollow = false;
					}
				}
				// else {
				// if (isfollow) {
				// flg = 2;
				// followThread2 = new FollowThread2();
				// followThread2.start();
				// isfollow = false;
				// } else {
				// flg = 1;
				// followThread = new FollowThread();
				// followThread.start();
				// isfollow = true;
				// }
				// }
			}
		});

		infoedit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
					Commond.showToast(VillageUserInfoDialog.this,
							"检测到网络网络异常或未开启");
					return;
				}
				// TODO Auto-generated method stub
				if (villageUserInfoList.size() == 0) {
					return;
				}
				if (uid.equals(fuid)) {
					// Intent intent = new Intent(getApplicationContext(),
					// UserInfoActivity.class);
					// startActivityForResult(intent, 1);
					views.add(view6);
					pageselect = 5;
					mPagerAdapter.notifyDataSetChanged();
					mTabPager.setCurrentItem(1);
				}
				// /////gouda 勾搭
				// else {
				// if (villageUserInfoList.get(0).getIsf() == 0) {
				// if (isfollow) {
				// flg = 1;
				// followThread = new FollowThread();
				// followThread.start();
				// isfollow = false;
				// } else {
				// flg = 2;
				// followThread2 = new FollowThread2();
				// followThread2.start();
				// isfollow = true;
				// }
				// } else {
				// if (isfollow) {
				// flg = 2;
				// followThread2 = new FollowThread2();
				// followThread2.start();
				// isfollow = false;
				// } else {
				// flg = 1;
				// followThread = new FollowThread();
				// followThread.start();
				// isfollow = true;
				// }
				// }
				// }
			}
		});

		village_userinfo_blacklistButton
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (villageUserInfoList.size() == 0) {
							return;
						}
						if (uid.equals(fuid)) {
							views.add(view8);
							pageselect = 8;
							mPagerAdapter.notifyDataSetChanged();
							mTabPager.setCurrentItem(1);
						} else {
							if (villageUserInfoList.get(0).getIsb() == 0) {
								if (isblack) {
									blackflg = 1;
									blackThread = new BlackThread();
									blackThread.start();
									isblack = false;
								} else {
									blackflg = 2;
									blackThread2 = new BlackThread2();
									blackThread2.start();
									isblack = true;
								}
							} else {
								if (isblack) {
									blackflg = 2;
									blackThread2 = new BlackThread2();
									blackThread2.start();
									isblack = false;
								} else {
									blackflg = 1;
									blackThread = new BlackThread();
									blackThread.start();
									isblack = true;
								}
							}
						}
					}
				});

		village_userinfobg_ImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String imgurl = "";
				String downpath = "";
				String savepath = "";
				// if (villageUserInfoList.size() > 0) {
				// imgurl = villageUserInfoList.get(0).getHeader_b();
				// } else {
				// return;
				// }
				// System.out.println("user:" + villageUserInfoList);
				try {

					if (villageUserInfoList.size() > 0) {
						if ("".equals(villageUserInfoList.get(0).getHeader_b())
								|| villageUserInfoList.get(0).getHeader_b() != null) {
							imgurl = villageUserInfoList.get(0).getHeader_b();
						} else {
							return;
						}
					} else {
						System.out.println("user:" + villageUserInfoList);
					}
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
					Intent intent = new Intent(getApplicationContext(),
							ImageZoom.class);
					// 需要请求图片的url(也可以是本地文件)
					intent.putExtra("imageurl", imgurl);
					intent.putExtra("ouid", uid);
					// 网路图片下载后保存的文件加路径
					intent.putExtra("downpath", downpath);
					// 图片需要保存的文件夹路径
					intent.putExtra("savepath", savepath);
					startActivity(intent);
					int version = Integer.valueOf(android.os.Build.VERSION.SDK);
					if (version > 5) {
						overridePendingTransition(R.anim.zoom_enter,
								R.anim.zoom_exit);
					}

				} catch (Exception e) {
					// TODO: handle exception
					Commond.showToast(VillageUserInfoDialog.this, "亲，网络不给力哦！");
				}
			}

		});

		village_userinfo_closeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if ("1".equals(chatroom)) {
					finish();
				}
				if (currIndex == 0) {
					finish();
				} else if (gridRelative.getVisibility() == View.VISIBLE) {
					gridRelative.setVisibility(View.GONE);
					liwuTypeRelative.setVisibility(View.VISIBLE);
					dlint.clear();
					liwumoreitemList.clear();
				} else {
					mTabPager.setCurrentItem(0);
				}

			}
		});

		village_userinfo_fensi_rl_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (villageUserInfoList.size() > 0) {
					views.add(view2);
					pageselect = 1;
					mPagerAdapter.notifyDataSetChanged();
					mTabPager.setCurrentItem(1);
				}
			}
		});
		village_userinfo_guanzhu_rl_btn
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (villageUserInfoList.size() > 0) {
							views.add(view3);
							pageselect = 2;
							mPagerAdapter.notifyDataSetChanged();
							mTabPager.setCurrentItem(1);
						}
					}
				});
		info_songi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (villageUserInfoList.size() > 0) {
					views.add(view4);
					pageselect = 3;
					mPagerAdapter.notifyDataSetChanged();
					mTabPager.setCurrentItem(1);
				}
			}
		});
		village_userinfo_liwu_rl_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (villageUserInfoList.size() > 0) {
					views.add(view5);
					pageselect = 4;
					mPagerAdapter.notifyDataSetChanged();
					mTabPager.setCurrentItem(1);
				}
			}
		});

		village_userinfo_xiaojian_rl_btn
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (villageUserInfoList.size() == 0) {
							return;
						}
						try {
							Intent intent = new Intent(getApplicationContext(),
									Xjfabu.class);
							intent.putExtra("ouid", String
									.valueOf(villageUserInfoList.get(0)
											.getUid()));
							intent.putExtra("osc", ConstantsJrc.USERDZURL);
							startActivity(intent);
						} catch (Exception e) {
							// TODO: handle exception
							Commond.showToast(VillageUserInfoDialog.this,
									"亲，网络不给力哦！");
						}
					}
				});

		village_userinfo_dazui_rl_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (villageUserInfoList.size() > 0) {
					try {
						Intent intent = new Intent(getApplicationContext(),
								Dzmysave.class);
						intent.putExtra("ouid", String
								.valueOf(villageUserInfoList.get(0).getUid()));
						intent.putExtra("osc", ConstantsJrc.USERDZURL);
						startActivity(intent);
					} catch (Exception e) {
						// TODO: handle exception
						Commond.showToast(VillageUserInfoDialog.this,
								"亲，网络不给力哦！");
					}
				}
			}
		});

		village_userinfo_beibao_rl_btn
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (villageUserInfoList.size() > 0) {
							Intent intent = new Intent(getApplicationContext(),
									Backpacks.class);
							startActivity(intent);
						}
					}
				});
		chooseIDBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// views.add(view7);
				// mPagerAdapter.notifyDataSetChanged();
				// mTabPager.setCurrentItem(2);
				if (isShowChooseID) {
					chooseid_scrollView.setVisibility(View.VISIBLE);
					user_scrollView.setVisibility(View.GONE);
					village_userinfo_save_btn.setVisibility(View.GONE);
					village_userinfo_dialog_title
							.setBackgroundDrawable(getResources()
									.getDrawable(
											R.drawable.chatroom_userinfo_chooseid_title));
					isShowChooseID = false;
				} else {
					chooseid_scrollView.setVisibility(View.GONE);
					user_scrollView.setVisibility(View.VISIBLE);
					village_userinfo_save_btn.setVisibility(View.VISIBLE);
					village_userinfo_dialog_title
							.setBackgroundDrawable(getResources()
									.getDrawable(
											R.drawable.chatroom_userinfo_usercenter_title));
					isShowChooseID = true;
				}
			}
		});

		qqbd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				File file = new File(MainMenuActivity.sppath + "TokenDate.xml");

				onClickLogin();
			}
		});

		sinabd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//
				Intent it = new Intent();
				it.setClass(getApplicationContext(), AuthorizeActivity.class);
				startActivity(it);
			}
		});

		chooseHeaderImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						UpPhotoDialog.class);
				startActivityForResult(intent, 1);

			}
		});

		userpassword_Button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						SystemMsgWebDialog.class);
				intent.putExtra(
						"link",
						ConstantsJrc.MAINMORE
								+ "?uid="
								+ su.getUid()
								+ "&flg=6&w="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getWidth(VillageUserInfoDialog.this)
								+ "&pkg="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getPackage(VillageUserInfoDialog.this)
								+ "&ver="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getVersionName(
												VillageUserInfoDialog.this)
								+ "&token=" + su.getToken() + "&token="
								+ su.getToken());
				intent.putExtra("type", "6");
				startActivity(intent);
			}
		});

		userBoyRadioButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				userBoyRadioButton.setChecked(true);
				sex = "2";
				userGirlRadioButton.setChecked(false);
			}
		});

		userGirlRadioButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				userBoyRadioButton.setChecked(false);
				sex = "1";
				userGirlRadioButton.setChecked(true);
			}
		});
		village_userinfo_save_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
					Commond.showToast(getApplicationContext(), "检测到网络网络异常或未开启");
					return;
				} else {
					modifyThread = new ModifyUserInfo();
					if (userNickEditText.getText().toString().equals("")) {
						Intent intent = new Intent(getApplicationContext(),
								MessageSystemDialog.class);
						intent.putExtra("ret", "0");
						intent.putExtra("tip", "昵称不能空");
						startActivity(intent);
						return;
					}
					if (!userNickEditText.getText().toString().equals("")) {
						if (EditTextUtil.replaceBlank(
								userNickEditText.getText().toString()).equals(
								"")) {
							Intent intent = new Intent(getApplicationContext(),
									MessageSystemDialog.class);
							intent.putExtra("ret", "0");
							intent.putExtra("tip", "昵称不能为空格");
							startActivity(intent);
						} else {
							if (linear_WheelView.getVisibility() == View.VISIBLE) {
								linear_WheelView.setVisibility(View.GONE);
							}
							modifyThread.start();
						}
					}

				}
			}
		});

		phone_code_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				phone = choosephone_EditText.getText().toString();
				email = "";
				if (EditTextUtil.VerificationPhone(choosephone_EditText
						.getText().toString())) {
					chooseThread = new ChooseIdThread();
					chooseThread.start();
				} else {
					Intent intent = new Intent(getApplicationContext(),
							MessageSystemDialog.class);
					intent.putExtra("ret", "0");
					intent.putExtra("tip", "请正确输入手机号码");
					startActivity(intent);
				}
			}
		});
		email_code_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				phone = "";
				email = chooseemail_EditText.getText().toString();

				if (EditTextUtil.VerificationEmail(chooseemail_EditText
						.getText().toString())) {
					chooseThread = new ChooseIdThread();
					chooseThread.start();
				} else {
					Intent intent = new Intent(getApplicationContext(),
							MessageSystemDialog.class);
					intent.putExtra("ret", "0");
					intent.putExtra("tip", "请正确输入邮箱地址");
					startActivity(intent);
				}
			}
		});

		user_login_Button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!input_code_EditText.getText().toString().equals("")) {
					reLoginThread = new ReLoginThread();
					reLoginThread.start();
				} else {
					Intent intent = new Intent(getApplicationContext(),
							MessageSystemDialog.class);
					intent.putExtra("ret", "0");
					intent.putExtra("tip", "请填入验证码");
					startActivity(intent);
				}
			}
		});

		village_userinfo_icon_gridView.setSelector(new ColorDrawable(
				Color.TRANSPARENT));
		village_userinfo_icon_gridView
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(getApplicationContext(),
								SystemMsgWebDialog.class);
						intent.putExtra(
								"link",
								ConstantsJrc.MAINMORE
										+ "?uid="
										+ su.getUid()
										+ "&ouid="
										+ uid
										+ "&flg=5&w="
										+ PhoneInfo
												.getInstance(
														getApplicationContext())
												.getWidth(
														VillageUserInfoDialog.this)
										+ "&pkg="
										+ PhoneInfo
												.getInstance(
														getApplicationContext())
												.getPackage(
														VillageUserInfoDialog.this)
										+ "&ver="
										+ PhoneInfo
												.getInstance(
														getApplicationContext())
												.getVersionName(
														VillageUserInfoDialog.this)
										+ "&token=" + su.getToken());
						intent.putExtra("type", "5");
						startActivity(intent);
					}
				});
		HorizontalScrollView1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
					Commond.showToast(getApplicationContext(), "检测到网络网络异常或未开启");
					return;
				}
				Intent intent = new Intent(getApplicationContext(),
						SystemMsgWebDialog.class);
				intent.putExtra(
						"link",
						ConstantsJrc.MAINMORE
								+ "?uid="
								+ su.getUid()
								+ "&flg=5&w="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getWidth(VillageUserInfoDialog.this)
								+ "&pkg="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getPackage(VillageUserInfoDialog.this)
								+ "&ver="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getVersionName(
												VillageUserInfoDialog.this)
								+ "&token=" + su.getToken());
				intent.putExtra("type", "5");
				startActivity(intent);
			}
		});
		village_userscorewhat_ImageView
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (SystemUtil
								.isNetworkConnected(getApplicationContext()) == false) {
							Commond.showToast(getApplicationContext(),
									"检测到网络网络异常或未开启");
							return;
						}
						Intent intent = new Intent(getApplicationContext(),
								SystemMsgWebDialog.class);
						intent.putExtra(
								"link",
								ConstantsJrc.MAINMORE
										+ "?uid="
										+ su.getUid()
										+ "&ouid="
										+ uid
										+ "&flg=13&w="
										+ PhoneInfo
												.getInstance(
														getApplicationContext())
												.getWidth(
														VillageUserInfoDialog.this)
										+ "&pkg="
										+ PhoneInfo
												.getInstance(
														getApplicationContext())
												.getPackage(
														VillageUserInfoDialog.this)
										+ "&ver="
										+ PhoneInfo
												.getInstance(
														getApplicationContext())
												.getVersionName(
														VillageUserInfoDialog.this)
										+ "&token=" + su.getToken());
						intent.putExtra("type", "13");
						startActivity(intent);
					}
				});

		village_userinfosign_TextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (SystemUtil.isNetworkConnected(getApplicationContext()) == false) {
					Commond.showToast(getApplicationContext(), "检测到网络网络异常或未开启");
					return;
				}
				Intent intent = new Intent(getApplicationContext(),
						SystemMsgWebDialog.class);
				intent.putExtra(
						"link",
						ConstantsJrc.MAINMORE
								+ "?uid="
								+ su.getUid()
								+ "&ouid="
								+ uid
								+ "&flg=14&w="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getWidth(VillageUserInfoDialog.this)
								+ "&pkg="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getPackage(VillageUserInfoDialog.this)
								+ "&ver="
								+ PhoneInfo
										.getInstance(getApplicationContext())
										.getVersionName(
												VillageUserInfoDialog.this)
								+ "&token=" + su.getToken());
				intent.putExtra("type", "14");
				startActivity(intent);
			}
		});

		// 被关注ListView
		beiguanzhuListView.addFooterView(vFooter);
		beiguanzhuListView.setFooterDividersEnabled(false);
		beiguanzhuListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					if (view.getLastVisiblePosition() == view.getCount() - 1) {
						isfoot = true;
						String url = ConstantsJrc.FOLLOWS + "?" + "uid=" + fuid
								+ "&ouid=" + uid + "&flg=2" + "&token="
								+ su.getToken() + "&page=" + pagebeicount++;
						if (!dlpl.contains(url)) {
							loading.setVisibility(View.VISIBLE);
							StringBuffer data = new StringBuffer();
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

		// 关注ListView
		guanzhuListView.addFooterView(vFooter);
		guanzhuListView.setFooterDividersEnabled(false);
		guanzhuListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					if (view.getLastVisiblePosition() == view.getCount() - 1) {
						isfoot2 = true;
						String url = ConstantsJrc.FOLLOWS + "?" + "uid=" + fuid
								+ "&ouid=" + uid + "&flg=1" + "&token="
								+ su.getToken() + "&page=" + pageguancount++;
						if (!dlpl.contains(url)) {
							loading.setVisibility(View.VISIBLE);
							StringBuffer data = new StringBuffer();
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

		guanzhuListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (uid.equals(fuid)) {
					if (position < bguanzhuAdapter.list.size()) {
						Intent intent = new Intent(getApplicationContext(),
								FollowToolsDialog.class);
						String uid = String.valueOf(bguanzhuAdapter.list.get(
								position).getUid());

						intent.putExtra("uid", su.getUid());
						intent.putExtra("tuid", uid);
						startActivityForResult(intent, 1);
					}
				}
			}
		});

		// 收礼ListView
		myLiwuListView.addFooterView(vFooter);
		myLiwuListView.setFooterDividersEnabled(false);
		myLiwuListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					if (view.getLastVisiblePosition() == view.getCount() - 1) {
						isMyLiWuFoot = true;
						String url = ConstantsJrc.GIFTS + "?" + "uid=" + uid
								+ "&token=" + su.getToken() + "&ouid="
								+ Integer.parseInt(su.getUid()) + "&page="
								+ pagemyliwucount++;
						if (!dlpl.contains(url)) {
							loading.setVisibility(View.VISIBLE);
							StringBuffer data = new StringBuffer();
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

		// 黑名单ListView
		blackListView.addFooterView(vFooter);
		blackListView.setFooterDividersEnabled(false);
		blackListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					if (view.getLastVisiblePosition() == view.getCount() - 1) {
						isBlackFoot = true;
						String url = ConstantsJrc.BLACKS + "?" + "uid=" + fuid
								+ "&ouid=" + uid + "&token=" + su.getToken()
								+ "&page=" + pageblacklist++;
						if (!dlpl.contains(url)) {
							loading.setVisibility(View.VISIBLE);
							StringBuffer data = new StringBuffer();
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

		userbirthday_EditText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (linear_WheelView.getVisibility() == View.GONE) {
					linear_WheelView.setVisibility(View.VISIBLE);
					linear_WheelView.setFocusable(true);
					userbirthday_EditText.setFocusable(true);
					userNickEditText.setFocusable(false);
					userSignEditText.setFocusable(false);
					userCityEditText.setFocusable(false);
					// ScrollView 自动滚动底部
					user_scrollView.post(new Runnable() {
						public void run() {
							user_scrollView.fullScroll(ScrollView.FOCUS_DOWN);
						}
					});
				} else {
					linear_WheelView.setVisibility(View.GONE);
					userNickEditText.setFocusable(true);
					userSignEditText.setFocusable(true);
					userCityEditText.setFocusable(true);
					userNickEditText.setFocusableInTouchMode(true);
					userCityEditText.setFocusableInTouchMode(true);
					userSignEditText.setFocusableInTouchMode(true);
				}
			}
		});

		liwuTypeListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// TODO Auto-generated method stub

				pageselect = 10;
				po = position;
				if (position < liwuTypeListAdapter.list.size()) {
					gridRelative.setVisibility(View.VISIBLE);
					liwuTypeRelative.setVisibility(View.GONE);
					liwuGridView.setVisibility(View.VISIBLE);
					villageliwuListRelativeLayout.setVisibility(View.VISIBLE);
					liwuGridView.setVisibility(View.VISIBLE);
					liwuitemList.clear();
					loading.setVisibility(View.GONE);
					String url3 = ConstantsJrc.GIFTLIST + "?" + "uid=" + fuid
							+ "&cid="
							+ liwuTypeListAdapter.list.get(position).getId()
							+ "&page=" + "&src=" + src + "&token="
							+ su.getToken();
					StringBuffer data3 = new StringBuffer();
					// 请求网络验证登陆
					HttpRequestTask request3 = new HttpRequestTask();
					request3.execute(url3, data3.toString());
				}
			}
		});
	}

	// 关注监听
	OnClickListener listen = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			aubean = (AttentionUserBean) v.getTag();
			buid = String.valueOf(aubean.getUid());
			bfuid = su.getUid();
			// System.out.println("follow:" + aubean.getFollowed());
			if (aubean.getFollowed() == 0) {
				if (aubean.getIsfollow()) {
					flg = 1;
					followThread = new FollowThread();
					followThread.start();
					aubean.setIsfollow(true);
				} else {
					flg = 2;
					followThread2 = new FollowThread2();
					followThread2.start();
					aubean.setIsfollow(false);
				}
			} else {
				if (aubean.getIsfollow()) {
					flg = 2;
					followThread2 = new FollowThread2();
					followThread2.start();
					aubean.setIsfollow(true);
				} else {
					flg = 1;
					followThread = new FollowThread();
					followThread.start();
					aubean.setIsfollow(false);
				}
			}
			if (fuid.equals(uid)) {
				if (pageselect == 1) {
					attentionList.clear();
					loading.setVisibility(View.GONE);
					String url = ConstantsJrc.FOLLOWS + "?" + "uid=" + fuid
							+ "&ouid=" + uid + "&flg=2" + "&page=" + "&token="
							+ su.getToken();
					StringBuffer data = new StringBuffer();
					// 请求网络验证登陆
					HttpRequestTask request = new HttpRequestTask();
					request.execute(url, data.toString());
				}
				if (pageselect == 2) {
					attentionList2.clear();
					loading.setVisibility(View.GONE);
					String url2 = ConstantsJrc.FOLLOWS + "?" + "uid=" + fuid
							+ "&ouid=" + uid + "&flg=1" + "&page=" + "&token="
							+ su.getToken();
					StringBuffer data2 = new StringBuffer();
					// 请求网络验证登陆
					HttpRequestTask request2 = new HttpRequestTask();
					request2.execute(url2, data2.toString());
				}
			}

			// bguanzhuAdapter.notifyDataSetChanged();
		}
	};
	// 拉黑
	OnClickListener blacklisten = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			blackBean = (BlackListBean) v.getTag();
			blackuid = String.valueOf(blackBean.getUid());
			blackfuid = su.getUid();

			blackflg = 2;
			blackThread2 = new BlackThread2();
			blackThread2.start();

			// blackListView.setVisibility(View.VISIBLE);
			// villageblackListRelativeLayout.setVisibility(View.VISIBLE);
			// myblackList.clear();
			// loading.setVisibility(View.GONE);
			// String url8 = ConstantsJrc.BLACKS + "?" + "uid=" + fuid +
			// "&ouid="
			// + uid + "&page=" + "&token=" + su.getToken();
			// StringBuffer data8 = new StringBuffer();
			// // 请求网络验证登陆
			// HttpRequestTask request8 = new HttpRequestTask();
			// request8.execute(url8, data8.toString());
		}
	};

	// 赠送礼物监听
	OnClickListener liwulisten = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			LiwuItemsBean liwuBean = (LiwuItemsBean) v.getTag();
			Intent intent = new Intent(getApplicationContext(),
					LiwuDialog.class);
			intent.putExtra("uid", Integer.parseInt(fuid));
			intent.putExtra("touid", Integer.parseInt(uid));
			intent.putExtra("logo", liwuBean.getLogo());
			intent.putExtra("gid", liwuBean.getId());
			intent.putExtra("src", src);
			intent.putExtra("oid", oid);
			intent.putExtra("mark", liwuBean.getMark());
			startActivityForResult(intent, 5);
		}
	};

	// 查看他人资料
	OnClickListener infolisten = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			AttentionUserBean aub = (AttentionUserBean) v.getTag();
			Intent intent = new Intent(getApplicationContext(),
					VillageUserInfoDialog.class);
			String uid = String.valueOf(aub.getUid());
			intent.putExtra("uid", uid);
			intent.putExtra("fuid", su.getUid());
			intent.putExtra("type", 2);
			intent.putExtra("src", 1);
			startActivityForResult(intent, 1);
		}
	};
	// 礼物页面查看他人资料
	OnClickListener infolisten2 = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			MyLiwuItemsBean myb = (MyLiwuItemsBean) v.getTag();
			if (myb.getUid() == 0) {
				Commond.showToast(getApplicationContext(), "神秘人无法查看信息");
			} else {
				Intent intent = new Intent(getApplicationContext(),
						VillageUserInfoDialog.class);
				String uid = String.valueOf(myb.getUid());
				intent.putExtra("uid", uid);
				intent.putExtra("fuid", su.getUid());
				intent.putExtra("type", 2);
				intent.putExtra("src", 1);
				startActivityForResult(intent, 1);
			}
		}
	};

	// 黑名单查看他人资料
	OnClickListener blackinfolisten = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			BlackListBean blb = (BlackListBean) v.getTag();
			Intent intent = new Intent(getApplicationContext(),
					VillageUserInfoDialog.class);
			String uid = String.valueOf(blb.getUid());
			intent.putExtra("uid", uid);
			intent.putExtra("fuid", su.getUid());
			intent.putExtra("type", 2);
			startActivityForResult(intent, 1);
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

			switch (arg0) {
			case 0:
				views.remove(1);
				infoedit.setVisibility(View.VISIBLE);
				// mTabPager.removeView(view2);
				// mTabPager.removeView(view3);
				// mTabPager.removeView(view4);
				// mTabPager.removeView(view5);
				// mTabPager.removeView(view6);
				// mTabPager.removeView(view7);
				isShowChooseID = true;
				village_userinfo_dialog_title
						.setBackgroundDrawable(getResources().getDrawable(
								R.drawable.chatroom_userinfo_title));
				beiguanzhuListView.setVisibility(View.GONE);
				guanzhuListView.setVisibility(View.GONE);
				blackListView.setVisibility(View.GONE);
				village_userinfo_save_btn.setVisibility(View.GONE);
				villageliwuListRelativeLayout.setVisibility(View.GONE);
				villageliwutypeListRelativeLayout.setVisibility(View.GONE);
				villagevbeiguanzhuRelativeLayout.setVisibility(View.GONE);
				villagevbeiguanzhuRelativeLayout.setVisibility(View.GONE);
				villageblackListRelativeLayout.setVisibility(View.GONE);
				myLiwuListView.setVisibility(View.GONE);
				liwuGridView.setVisibility(View.GONE);
				liwuTypeListView.setVisibility(View.GONE);
				user_scrollView.setVisibility(View.GONE);
				chooseid_scrollView.setVisibility(View.GONE);
				gridRelative.setVisibility(View.GONE);
				liwuTypeRelative.setVisibility(View.GONE);
				if (uid.equals(fuid)) {
					village_userinfo_blacklistButton
							.setBackgroundResource(R.drawable.chatroom_userinfo_dialog_blacklist_btn);
				}
				break;
			case 1:
				infoedit.setVisibility(View.GONE);
				switch (pageselect) {
				case 1:
					if (villageuserinfo_progressbarRelativeLayout
							.getVisibility() == View.VISIBLE) {
						villageuserinfo_progressbarRelativeLayout
								.setVisibility(View.GONE);
					}
					village_userinfo_save_btn.setVisibility(View.GONE);
					if (uid.equals(fuid)) {
						village_userinfo_dialog_title
								.setBackgroundDrawable(getResources()
										.getDrawable(
												R.drawable.chatroom_userinfo_goudawo_title));
					} else {
						village_userinfo_dialog_title
								.setBackgroundDrawable(getResources()
										.getDrawable(
												R.drawable.chatroom_userinfo_goudata_title));
					}
					guanzhuListView.setVisibility(View.GONE);
					beiguanzhuListView.setVisibility(View.VISIBLE);
					villagevbeiguanzhuRelativeLayout
							.setVisibility(View.VISIBLE);
					attentionList.clear();
					loading.setVisibility(View.GONE);
					String url = ConstantsJrc.FOLLOWS + "?" + "uid=" + fuid
							+ "&ouid=" + uid + "&flg=2" + "&page=" + "&token="
							+ su.getToken();
					StringBuffer data = new StringBuffer();
					// 请求网络验证登陆
					HttpRequestTask request = new HttpRequestTask();
					request.execute(url, data.toString());

					break;
				case 2:
					infoedit.setVisibility(View.GONE);
					if (villageuserinfo_progressbarRelativeLayout
							.getVisibility() == View.VISIBLE) {
						villageuserinfo_progressbarRelativeLayout
								.setVisibility(View.GONE);
					}
					village_userinfo_save_btn.setVisibility(View.GONE);
					if (uid.equals(fuid)) {
						village_userinfo_dialog_title
								.setBackgroundDrawable(getResources()
										.getDrawable(
												R.drawable.chatroom_userinfo_wodouda_title));
					} else {
						village_userinfo_dialog_title
								.setBackgroundDrawable(getResources()
										.getDrawable(
												R.drawable.chatroom_userinfo_tagouda_title));
					}
					beiguanzhuListView.setVisibility(View.GONE);
					guanzhuListView.setVisibility(View.VISIBLE);
					villagevbeiguanzhuRelativeLayout
							.setVisibility(View.VISIBLE);
					attentionList2.clear();
					loading.setVisibility(View.GONE);
					String url2 = ConstantsJrc.FOLLOWS + "?" + "uid=" + fuid
							+ "&ouid=" + uid + "&flg=1" + "&page=" + "&token="
							+ su.getToken();
					StringBuffer data2 = new StringBuffer();
					// 请求网络验证登陆
					HttpRequestTask request2 = new HttpRequestTask();
					request2.execute(url2, data2.toString());
					break;
				case 3:
					infoedit.setVisibility(View.GONE);
					villageuserinfo_progressbarRelativeLayout
							.setVisibility(View.GONE);

					village_userinfo_save_btn.setVisibility(View.GONE);
					village_userinfo_dialog_title
							.setBackgroundDrawable(getResources().getDrawable(
									R.drawable.chatroom_userinfo_songli_title));
					gridRelative.setVisibility(View.GONE);
					liwuTypeRelative.setVisibility(View.VISIBLE);
					// liwuGridView.setVisibility(View.VISIBLE);
					villageliwuListRelativeLayout.setVisibility(View.GONE);
					villageliwutypeListRelativeLayout.setVisibility(View.GONE);
					// liwuGridView.setVisibility(View.VISIBLE);
					// if (IsLoadShop) {
					// liwuitemList.clear();
					// loading.setVisibility(View.GONE);
					// String url3 = ConstantsJrc.GIFTLIST + "?" + "uid="
					// + fuid + "&page=" + "&src=" + src + "&token="
					// + su.getToken();
					// StringBuffer data3 = new StringBuffer();
					// // 请求网络验证登陆
					// HttpRequestTask request3 = new HttpRequestTask();
					// request3.execute(url3, data3.toString());
					// }

					liwuTypeListView.setVisibility(View.VISIBLE);

					liwuTypeListView.setVisibility(View.VISIBLE);
					if (IsLoadShopType) {
						villageliwutypeListRelativeLayout
								.setVisibility(View.VISIBLE);
						liwutypeitemList.clear();
						loading.setVisibility(View.GONE);
						String url3 = ConstantsJrc.GIFTTYPE + "?" + "uid="
								+ fuid + "&token=" + su.getToken();

						StringBuffer data3 = new StringBuffer();
						// 请求网络验证登陆
						HttpRequestTask request3 = new HttpRequestTask();
						request3.execute(url3, data3.toString());
					}

					break;
				case 4:
					infoedit.setVisibility(View.GONE);
					if (villageuserinfo_progressbarRelativeLayout
							.getVisibility() == View.VISIBLE) {
						villageuserinfo_progressbarRelativeLayout
								.setVisibility(View.GONE);
					}
					village_userinfo_save_btn.setVisibility(View.GONE);
					if (uid.equals(fuid)) {
						village_userinfo_dialog_title
								.setBackgroundDrawable(getResources()
										.getDrawable(
												R.drawable.chatroom_userinfo_woliwu_title));
					} else {
						village_userinfo_dialog_title
								.setBackgroundDrawable(getResources()
										.getDrawable(
												R.drawable.chatroom_userinfo_taliwu_title));
					}
					myLiwuListView.setVisibility(View.VISIBLE);
					villagemyliwuListRelativeLayout.setVisibility(View.VISIBLE);
					myliwuitemlist.clear();
					loading.setVisibility(View.GONE);
					String url4 = ConstantsJrc.GIFTS + "?" + "uid=" + uid
							+ "&ouid=" + Integer.parseInt(su.getUid())
							+ "&page=" + "&token=" + su.getToken();
					StringBuffer data4 = new StringBuffer();
					// 请求网络验证登陆
					HttpRequestTask request4 = new HttpRequestTask();
					request4.execute(url4, data4.toString());
					break;
				case 5:
					infoedit.setVisibility(View.GONE);
					user_scrollView.setVisibility(View.VISIBLE);
					village_userinfo_save_btn.setVisibility(View.VISIBLE);
					village_userinfo_dialog_title
							.setBackgroundDrawable(getResources()
									.getDrawable(
											R.drawable.chatroom_userinfo_usercenter_title));
					// loaduserDataThread = new LoadUserDataThread();
					upLoadThread = new UpLoadPhotoThread();
					if (IsLoadUserInfo) {
						// loaduserDataThread.start();
						mHandler = new Handler();
						mTencent = Tencent.createInstance(mAppid,
								getApplicationContext());

						userHeaderImageView.setImageUrl(su.getHeader(),
								R.drawable.photo,
								Environment.getExternalStorageDirectory()
										+ File.separator + getPackageName()
										+ ConstantsJrc.PHOTO_PATH,
								ConstantsJrc.PROJECT_PATH + getPackageName()
										+ ConstantsJrc.PHOTO_PATH);

						userIDTextView.setText(su.getUid());
						userScoreTextView.setText(su.getScore());
						userLevelTextView.setText(su.getLevel());
						userNickEditText.setText(su.getNick());
						userSignEditText.setText(su.getSign());

						if (su.getSex().equals("1")) {
							userGirlRadioButton.setChecked(true);
							sex = "1";
						} else if (su.getSex().equals("2")) {
							userBoyRadioButton.setChecked(true);
							sex = "2";
						}
						userCityEditText.setText(su.getCity());
						userbirthday_EditText.setText(su.getBirthday());
						if (!"".equals(su.getBirthday())) {
							String[] str = su.getBirthday().split("\\-");
							// editYear.setText(str[0].toString());
							// editMonth.setText(str[1].toString());
							// editDay.setText(str[2].toString());
							setDate(useryear_WheelView, usermonth_WheelView,
									userday_WheelView,
									Integer.parseInt(str[0]),
									Integer.parseInt(str[1]),
									Integer.parseInt(str[2]));
						}// userPhoneEditText.setText(su.getPhone());
							// userEmailEditText.setText(su.getEmail());
						IsLoadUserInfo = false;
					}
					break;
				case 8:
					infoedit.setVisibility(View.GONE);
					if (villageuserinfo_progressbarRelativeLayout
							.getVisibility() == View.VISIBLE) {
						villageuserinfo_progressbarRelativeLayout
								.setVisibility(View.GONE);
					}
					village_userinfo_save_btn.setVisibility(View.GONE);

					village_userinfo_dialog_title
							.setBackgroundDrawable(getResources().getDrawable(
									R.drawable.chatroom_userinfo_black_title));

					blackListView.setVisibility(View.VISIBLE);
					villageblackListRelativeLayout.setVisibility(View.VISIBLE);
					myblackList.clear();
					loading.setVisibility(View.GONE);
					String url8 = ConstantsJrc.BLACKS + "?" + "uid=" + fuid
							+ "&ouid=" + uid + "&page=" + "&token="
							+ su.getToken();
					StringBuffer data8 = new StringBuffer();
					// 请求网络验证登陆
					HttpRequestTask request8 = new HttpRequestTask();
					request8.execute(url8, data8.toString());
					break;
				}
				break;
			case 2:
				infoedit.setVisibility(View.GONE);
				village_userinfo_save_btn.setVisibility(View.GONE);
				village_userinfo_dialog_title
						.setBackgroundDrawable(getResources().getDrawable(
								R.drawable.chatroom_userinfo_chooseid_title));
				break;
			}

			currIndex = arg0;
			// animation.setFillAfter(true);// True:图片停在动画结束位置
			// animation.setDuration(150);
			// mTabImg.startAnimation(animation);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	}

	void initView() {
		infoedit = (ImageButton) findViewById(R.id.info_edit);
		village_userinfo_closeButton = (ImageButton) findViewById(R.id.village_userinfo_close_btn);
		villageuserinfo_progressbar = (ProgressBar) findViewById(R.id.villageuserinfo_progressbar);
		villageuserinfo_progressbarRelativeLayout = (RelativeLayout) findViewById(R.id.villageuserinfo_progressbar_RelativeLayout);
		village_userinfo_dialog_title = (ImageView) findViewById(R.id.village_userinfo_dialog_title);
		village_userinfo_save_btn = (ImageButton) findViewById(R.id.village_userinfo_save_btn);
	}

	// 页面1
	void initView1(View view) {

		infojiantou = (ImageView) view.findViewById(R.id.infojiantou);
		infotv = (TextView) view.findViewById(R.id.villageinfotv);
		jiecaolev = (TextView) view.findViewById(R.id.jiecao_tv_dengji);
		jiecaodangqian = (TextView) view.findViewById(R.id.jiecao_tv_dangqian);
		jiecaoxuyao = (TextView) view.findViewById(R.id.jiecao_tv_xuyao);
		village_userinfo_time = (TextView) view
				.findViewById(R.id.village_userinfo_time);

		maohao = (TextView) view.findViewById(R.id.maohao);
		xiegang = (TextView) view.findViewById(R.id.xiegang);
		jiecaobg = (ProgressBar) view.findViewById(R.id.jiecao_bg);
		xinxi_caifu_ll = (LinearLayout) view.findViewById(R.id.xinxi_caifu_ll);
		village_userinfo_photo = (NetImageView) view
				.findViewById(R.id.village_userinfo_photo);
		village_userinfobg_ImageView = (ImageView) view
				.findViewById(R.id.village_userinfobg_ImageView);
		village_userinfoid_TextView = (TextView) view
				.findViewById(R.id.village_userinfoid_TextView);
		village_userinfoscore_TextView = (TextView) view
				.findViewById(R.id.village_userinfoscore_TextView);
		village_userinfolevel_TextView = (TextView) view
				.findViewById(R.id.village_userinfolevel_TextView);
		village_userinfonick_TextView = (TextView) view
				.findViewById(R.id.village_userinfonick_TextView);
		village_userinfosign_TextView = (TextView) view
				.findViewById(R.id.village_userinfosign_TextView);
		village_userinfosex_ImageView = (ImageView) view
				.findViewById(R.id.village_userinfosex_ImageView);
		village_userinfocity_TextView = (TextView) view
				.findViewById(R.id.village_userinfocity_TextView);
		village_userinfobirthday_TextView = (TextView) view
				.findViewById(R.id.village_userinfobirthday_TextView);

		village_userinfo_guanzhu_number_TextView = (TextView) view
				.findViewById(R.id.village_userinfo_guanzhu_number_TextView);
		village_userinfo_fensi_number_TextView = (TextView) view
				.findViewById(R.id.village_userinfo_fensi_number_TextView);
		village_userinfo_liwu_number_TextView = (TextView) view
				.findViewById(R.id.village_userinfo_liwu_number_TextView);

		village_userinfo_fensi_rl_btn = (RelativeLayout) view
				.findViewById(R.id.village_userinfo_fensi_rl_btn);
		village_userinfo_guanzhu_rl_btn = (RelativeLayout) view
				.findViewById(R.id.village_userinfo_guanzhu_rl_btn);
		village_userinfo_liwu_rl_btn = (RelativeLayout) view
				.findViewById(R.id.village_userinfo_liwu_rl_btn);
		village_userinfosign_TextView.setMovementMethod(ScrollingMovementMethod
				.getInstance());
		village_userinfo_icon_gridView = (GridView) view
				.findViewById(R.id.village_userinfo_icon_gridView);
		HorizontalScrollView1 = (FrameLayout) view
				.findViewById(R.id.framelayouticon);
		village_userinfo_dazui_number_TextView = (TextView) view
				.findViewById(R.id.village_userinfo_dazui_number_TextView);
		village_userinfo_xiaojian_number_TextView = (TextView) view
				.findViewById(R.id.village_userinfo_xiaojian_number_TextView);
		village_userinfo_beibao_number_TextView = (TextView) view
				.findViewById(R.id.village_userinfo_beibao_number_TextView);
		village_userinfo_blacklistButton = (ImageButton) view
				.findViewById(R.id.village_userinfo_blacklistButton);
		village_userscorewhat_ImageView = (ImageView) view
				.findViewById(R.id.village_userscorewhat_ImageView);
		village_userinfo_xiaojian_rl_btn = (RelativeLayout) view
				.findViewById(R.id.village_userinfo_xiaojian_rl_btn);
		village_userinfo_dazui_rl_btn = (RelativeLayout) view
				.findViewById(R.id.village_userinfo_dazui_rl_btn);
		village_userinfo_beibao_rl_btn = (RelativeLayout) view
				.findViewById(R.id.village_userinfo_beibao_rl_btn);
		info_sixin = (ImageButton) view.findViewById(R.id.info_sixin);
		info_songi = (ImageButton) view.findViewById(R.id.info_songli);
		info_chat = (ImageButton) view.findViewById(R.id.info_chat);
		info_gouda = (ImageButton) view.findViewById(R.id.info_gouda);
		ll_chat = (LinearLayout) view.findViewById(R.id.ll_chat);

		sv = (ScrollView) view.findViewById(R.id.scrollView1);

		ll = (LinearLayout) view.findViewById(R.id.llphotos);
		iv = (ImageView) view.findViewById(R.id.addphoto);
		int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) iv
				.getLayoutParams();
		linearParams.width = dm.widthPixels * 1 / 6;
		linearParams.height = linearParams.width;
		linearParams.leftMargin = linearParams.width / 2 / 7;
		iv.setLayoutParams(linearParams);

		RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) ll
				.getLayoutParams();
		llParams.height = dm.widthPixels * 1 / 6;
		ll.setLayoutParams(llParams);

	}

	void initView2(View view) {
		beiguanzhuListView = (ListView) view
				.findViewById(R.id.village_dialog_beiguanzhu_listview);
		villagebeiguanzhuProgressBar = (ProgressBar) view
				.findViewById(R.id.village_dialog_beiguanzhu_progressbar);
		villagevbeiguanzhuRelativeLayout = (RelativeLayout) view
				.findViewById(R.id.village_dialog_beiguanzhu_progerssbar_RelativeLayout);
	}

	void initView3(View view) {
		guanzhuListView = (ListView) view
				.findViewById(R.id.village_dialog_guanzhu_listview);
		villageguanzhuProgressBar = (ProgressBar) view
				.findViewById(R.id.village_dialog_guanzhu_progressbar);
		villagevguanzhuRelativeLayout = (RelativeLayout) view
				.findViewById(R.id.village_dialog_guanzhu_progerssbar_RelativeLayout);

	}

	void initView4(View view) {
		liwuGridView = (GridView) view
				.findViewById(R.id.village_dialog_liwu_grieview2);
		villageliwuListProgressBar = (ProgressBar) view
				.findViewById(R.id.village_dialog_liwu_progressbar);
		villageliwuListRelativeLayout = (RelativeLayout) view
				.findViewById(R.id.village_dialog_liwu_progerssbar_RelativeLayout);
		liwuTypeListView = (ListView) view
				.findViewById(R.id.village_dialog_liwutype_listview);
		villageliwutypeListProgressBar = (ProgressBar) view
				.findViewById(R.id.village_dialog_liwutype_progressbar);
		villageliwutypeListRelativeLayout = (RelativeLayout) view
				.findViewById(R.id.village_dialog_liwutype_progerssbar_RelativeLayout);
		gridRelative = (RelativeLayout) view
				.findViewById(R.id.liwu_grid_RelativeLayout);
		liwuTypeRelative = (RelativeLayout) view
				.findViewById(R.id.liwu_type_RelativeLayout);
		liwull = (LinearLayout) view.findViewById(R.id.liwu_ll);

		liwuGridView.setOnScrollListener(this);

	}

	void initView5(View view) {
		myLiwuListView = (ListView) view
				.findViewById(R.id.village_dialog_myliwu_listview);
		villagemyliwuListProgressBar = (ProgressBar) view
				.findViewById(R.id.village_dialog_myliwu_progressbar);
		villagemyliwuListRelativeLayout = (RelativeLayout) view
				.findViewById(R.id.village_dialog_myliwu_progerssbar_RelativeLayout);

	}

	void initView6(View view) {
		chooseIDBtn = (ImageButton) view.findViewById(R.id.userchooseid_Button);
		chooseHeaderImageButton = (RelativeLayout) view
				.findViewById(R.id.info_photo_bg_ImageView);
		userpassword_Button = (ImageButton) view
				.findViewById(R.id.userpassword_Button);
		userHeaderImageView = (NetImageView) view
				.findViewById(R.id.user_infophoto);
		userIDTextView = (TextView) view.findViewById(R.id.userid_TextView);
		userScoreTextView = (TextView) view
				.findViewById(R.id.userscore_TextView);
		userLevelTextView = (TextView) view
				.findViewById(R.id.userlevel_TextView);
		userNickEditText = (EditText) view.findViewById(R.id.usernick_EditText);
		userSignEditText = (EditText) view.findViewById(R.id.usersign_EditText);
		userBoyRadioButton = (RadioButton) view
				.findViewById(R.id.usersex_boy_radio);
		userGirlRadioButton = (RadioButton) view
				.findViewById(R.id.usersex_girl_radio);
		userCityEditText = (EditText) view.findViewById(R.id.usercity_EditText);
		// editYear = (EditText) view.findViewById(R.id.edit_year);
		// editMonth = (EditText) view.findViewById(R.id.edit_month);
		// editDay = (EditText) view.findViewById(R.id.edit_day);
		linear_WheelView = (LinearLayout) view
				.findViewById(R.id.linear_WheelView);
		useryear_WheelView = (WheelView) view
				.findViewById(R.id.useryear_WheelView);
		usermonth_WheelView = (WheelView) view
				.findViewById(R.id.usermonth_WheelView);
		userday_WheelView = (WheelView) view
				.findViewById(R.id.userday_WheelView);
		userbirthday_EditText = (TextView) view
				.findViewById(R.id.userbirthday_EditText);
		userbirthday_EditText.setFocusable(false);
		// userPhoneEditText = (EditText) view
		// .findViewById(R.id.userphone_EditText);
		// userEmailEditText = (EditText) view
		// .findViewById(R.id.useremail_EditText);

		OnWheelChangedListener listener = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updateDays(useryear_WheelView, usermonth_WheelView,
						userday_WheelView);
			}
		};

		// month
		int curMonth = calendar.get(Calendar.MONTH);
		String months[] = new String[] { "1月", "2月", "3月", "4月", "5月", "6月",
				"7月", "8月", "9月", "10月", "11月", "12月" };
		usermonth_WheelView.setViewAdapter(new DateArrayAdapter(this, months,
				curMonth));
		usermonth_WheelView.setCurrentItem(curMonth);
		usermonth_WheelView.addChangingListener(listener);

		// year
		int curYear = calendar.get(Calendar.YEAR);
		useryear_WheelView.setViewAdapter(new DateNumericAdapter(this,
				curYear - 100, curYear, 100));
		useryear_WheelView.setCurrentItem(100);
		useryear_WheelView.addChangingListener(listener);

		// day
		updateDays(useryear_WheelView, usermonth_WheelView, userday_WheelView);
		// day.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);

		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		userday_WheelView.setViewAdapter(new DateNumericAdapter(this, 1,
				maxDays, calendar.get(Calendar.DAY_OF_MONTH) - 1));
		userday_WheelView
				.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);
		userday_WheelView.addChangingListener(listener);
		updateDays(useryear_WheelView, usermonth_WheelView, userday_WheelView);

		qqbd = (ImageButton) view.findViewById(R.id.userqq);
		sinabd = (ImageButton) view.findViewById(R.id.usersina);
		sexRadioGroup = (RadioGroup) view.findViewById(R.id.radioGroup1);
		user_scrollView = (ScrollView) view.findViewById(R.id.user_scrollView);

		choosephone_EditText = (EditText) view
				.findViewById(R.id.userinfo_choosephone_EditText);
		phone_code_btn = (ImageButton) view
				.findViewById(R.id.userinfo_phone_code_btn);
		chooseemail_EditText = (EditText) view
				.findViewById(R.id.userinfo_chooseemail_EditText);
		email_code_btn = (ImageButton) view
				.findViewById(R.id.userinfo_email_code_btn);
		input_code_EditText = (EditText) view
				.findViewById(R.id.userinfo_input_code_EditText);
		user_login_Button = (ImageButton) view
				.findViewById(R.id.userinfo_user_login_Button);
		chooseid_scrollView = (ScrollView) view
				.findViewById(R.id.userinfo_chooseid_scrollView);
	}

	void initView7(View view) {
		// choosephone_EditText = (EditText) view
		// .findViewById(R.id.choosephone_EditText);
		// phone_code_btn = (ImageButton)
		// view.findViewById(R.id.phone_code_btn);
		// chooseemail_EditText = (EditText) view
		// .findViewById(R.id.chooseemail_EditText);
		// email_code_btn = (ImageButton)
		// view.findViewById(R.id.email_code_btn);
		// input_code_EditText = (EditText) view
		// .findViewById(R.id.input_code_EditText);
		// user_login_Button = (ImageButton) view
		// .findViewById(R.id.user_login_Button);
		// chooseid_scrollView = (ScrollView) view
		// .findViewById(R.id.chooseid_scrollView);
	}

	void initView8(View view) {
		blackListView = (ListView) view
				.findViewById(R.id.village_dialog_blacklist_listview);
		villageblackListProgressBar = (ProgressBar) view
				.findViewById(R.id.village_dialog_blacklist_progressbar);
		villageblackListRelativeLayout = (RelativeLayout) view
				.findViewById(R.id.village_dialog_blacklist_progerssbar_RelativeLayout);
	}

	class LoadDataThread extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					login();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:
				// villageuserinfo_progressbar.setVisibility(View.VISIBLE);
				if ("1".equals(chatroom)) {
					villageuserinfo_progressbarRelativeLayout
							.setVisibility(View.GONE);
				} else {
					villageuserinfo_progressbarRelativeLayout
							.setVisibility(View.VISIBLE);
				}

				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				// villageuserinfo_progressbar.setVisibility(View.GONE);
				villageuserinfo_progressbarRelativeLayout
						.setVisibility(View.GONE);
				loadThread.stopThread(false);
				break;
			}
		};
	};

	private void login() throws ClientProtocolException, IOException {
		handler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);

		final String result = HttpOnlineUserInfo.LoadDataGet(uid, fuid,
				su.getToken());

		// System.out.println(result);
		OnlineUserInfoJson ouj = new OnlineUserInfoJson();
		villageUserInfoList = ouj.parseJson(result);
		PitemsJson pitjson = new PitemsJson();
		pitList = pitjson.parseJson(result);
		PicphotoJson picjson = new PicphotoJson();
		picphotos = picjson.parseJson(result);
		// System.out.println("用户：" + villageUserInfoList.toString());
		// System.out.println(pitList.size() + "个");
		handler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		handler.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (villageUserInfoList.get(0).getRet() == 1) {
						if (!TextUtils.isEmpty(villageUserInfoList.get(0)
								.getJz())) {
							infotv.setClickable(true);
							infotv.setText(String.valueOf(villageUserInfoList
									.get(0).getJz()));
							if (!TextUtils.isEmpty(villageUserInfoList.get(0)
									.getJzc())) {
								infotv.setTextColor(Color
										.parseColor(villageUserInfoList.get(0)
												.getJzc()));
							}
							infojiantou.setVisibility(View.VISIBLE);
						} else {
							infotv.setClickable(false);
							infotv.setText("无");
							infojiantou.setVisibility(View.GONE);
						}
						if (villageUserInfoList.get(0).getBg() == 0) {
							info_layout
									.setBackgroundResource(R.drawable.chatroom_userinfo_bg);
						} else {
							info_layout
									.setBackgroundResource(R.drawable.wan_chatroom_userinfo_bg);
						}
						maohao.setVisibility(View.VISIBLE);
						xiegang.setVisibility(View.VISIBLE);
						// /////////////////////////////////////////
						jiecaobg.setMax(100);
						double x = ((double) (villageUserInfoList.get(0)
								.getJc()) / (double) (villageUserInfoList
								.get(0).getJct())) * 100;
						jiecaobg.setProgress((int) x);
						// /////////////////////
						village_userinfoid_TextView.setText(String
								.valueOf(villageUserInfoList.get(0).getUid()));
						village_userinfonick_TextView
								.setText(villageUserInfoList.get(0).getNick());
						nick = villageUserInfoList.get(0).getNick();
						village_userinfosign_TextView
								.setText(villageUserInfoList.get(0).getSign());
						village_userinfoscore_TextView.setText(String
								.valueOf(villageUserInfoList.get(0).getScore()));
						village_userinfolevel_TextView
								.setText(villageUserInfoList.get(0).getLevel());
						village_userinfobirthday_TextView
								.setText(villageUserInfoList.get(0)
										.getBirthday());
						village_userinfocity_TextView
								.setText(villageUserInfoList.get(0).getCity());
						village_userinfo_guanzhu_number_TextView.setText(String
								.valueOf(villageUserInfoList.get(0).getFc()));
						village_userinfo_fensi_number_TextView.setText(String
								.valueOf(villageUserInfoList.get(0).getFc1()));
						village_userinfo_liwu_number_TextView.setText(String
								.valueOf(villageUserInfoList.get(0).getGc()));
						village_userinfo_dazui_number_TextView.setText(String
								.valueOf(villageUserInfoList.get(0).getDzc()));
						village_userinfo_xiaojian_number_TextView
								.setText(String.valueOf(villageUserInfoList
										.get(0).getXjc()));
						village_userinfo_beibao_number_TextView.setText(String
								.valueOf(villageUserInfoList.get(0).getBbc()));
						jiecaolev.setText(villageUserInfoList.get(0).getJcl());
						jiecaodangqian.setText(String
								.valueOf(villageUserInfoList.get(0).getJc()));
						jiecaoxuyao.setText(String.valueOf(villageUserInfoList
								.get(0).getJct()));

						village_userinfo_time.setText(String
								.valueOf(villageUserInfoList.get(0).getTime()));

						if (pitList.size() == 0) {
							village_userinfo_icon_gridView
									.setVisibility(View.GONE);
						} else {
							village_userinfo_icon_gridView
									.setVisibility(View.VISIBLE);
						}

						for (int i = 0; i < picphotos.size(); i++) {
							if (!TextUtils.isEmpty(picphotos.get(i)
									.getPicsmall())) {
								Bitmap bmp = null;
								picfile = new File(dazuidown
										+ "/"
										+ Comment.getMd5Hash(picphotos.get(i)
												.getPicsmall()));
								String filename = picfile.getPath().toString();
								if (picfile.exists()) {
									bmp = BitmapCacheDzlb.getIntance()
											.getCacheBitmap(filename, 0, 0);
								}

								if (bmp == null) {
									if (!urls.contains(picphotos.get(i)
											.getPicsmall())) {
										View itemView = LayoutInflater.from(
												VillageUserInfoDialog.this)
												.inflate(R.layout.photoview,
														null);
										ImageView iv = (ImageView) itemView
												.findViewById(R.id.photoview_iv);
										ImageView ivk = (ImageView) itemView
												.findViewById(R.id.photoview_k);
										ivk.setTag(picphotos.get(i)
												.getPicsmall());
										iv.setTag(picphotos.get(i)
												.getPicsmall());
										rl = (RelativeLayout) itemView
												.findViewById(R.id.photoview_rl);
										itemView.setTag(picphotos.get(i)
												.getPicsmall());
										ll.addView(itemView);
										iv1s.add(iv);
										ivks.add(ivk);

										LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) rl
												.getLayoutParams();
										linearParams.width = dm.widthPixels * 1 / 6;
										linearParams.height = linearParams.width;
										linearParams.weight = 1;
										linearParams.leftMargin = linearParams.width / 2 / 7;
										itemView.setLayoutParams(linearParams);

										urls.add(picphotos.get(i).getPicsmall());
										photopaths.add(picphotos.get(i)
												.getPicbig());
										picids.add(picphotos.get(i).getPicid());
										new Thread(new LoadImageRunnable(
												VillageUserInfoDialog.this,
												mHandler, picphotos.get(i)
														.getPicsmall(),
												filename, iv, ivk)).start();
										iv.setBackgroundResource(R.drawable.photoviewdef);
										ivk.setOnClickListener(new OnClickListener() {

											@Override
											public void onClick(View v) {
												// TODO Auto-generated method
												// stub
												for (int i = 0; i < picphotos
														.size(); i++) {
													if (v.getTag()
															.equals(picphotos
																	.get(i)
																	.getPicsmall())) {
														if (uid.equals(fuid)) {
															Intent it = new Intent();
															it.setClass(
																	VillageUserInfoDialog.this,
																	DeleteOrBorwser.class);
															it.putExtra(
																	"tag",
																	v.getTag()
																			.toString());
															it.putExtra("po", i);
															it.putExtra(
																	"picid",
																	picphotos
																			.get(i)
																			.getPicid());
															it.putExtra("uid",
																	uid);
															it.putExtra("fuid",
																	fuid);
															startActivityForResult(
																	it, 3);
														} else {
															Intent intent = new Intent();
															intent.setClass(
																	VillageUserInfoDialog.this,
																	Chakandatu.class);
															intent.putStringArrayListExtra(
																	"photos",
																	photopaths);
															intent.putIntegerArrayListExtra(
																	"picids",
																	picids);
															intent.putExtra(
																	"uid", uid);
															intent.putExtra(
																	"picid",
																	picphotos
																			.get(i)
																			.getPicid());
															intent.putExtra(
																	"po", i);
															startActivity(intent);
														}
													}
												}
											}
										});
									}
								} else {
									photopaths
											.add(picphotos.get(i).getPicbig());
									picids.add(picphotos.get(i).getPicid());

									View itemView = LayoutInflater.from(
											VillageUserInfoDialog.this)
											.inflate(R.layout.photoview, null);
									ImageView iv = (ImageView) itemView
											.findViewById(R.id.photoview_iv);
									ImageView ivk = (ImageView) itemView
											.findViewById(R.id.photoview_k);
									rl = (RelativeLayout) itemView
											.findViewById(R.id.photoview_rl);
									ivk.setTag(picphotos.get(i).getPicsmall());
									itemView.setTag(picphotos.get(i)
											.getPicsmall());
									iv.setTag(picphotos.get(i).getPicsmall());
									ll.addView(itemView);
									iv1s.add(iv);
									ivks.add(ivk);
									LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) rl
											.getLayoutParams();
									linearParams.width = dm.widthPixels * 1 / 6;
									linearParams.height = linearParams.width;
									linearParams.weight = 1;
									if (i != 0) {
										linearParams.leftMargin = linearParams.width / 2 / 7;
									}
									if (i == 4) {
										RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) ll
												.getLayoutParams();
										llParams.height = dm.widthPixels * 1 / 6;
										llParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

										ll.setLayoutParams(llParams);
									}

									itemView.setLayoutParams(linearParams);
									iv.setImageBitmap(bmp);

									ivk.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub
											for (int i = 0; i < picphotos
													.size(); i++) {
												if (v.getTag().equals(
														picphotos.get(i)
																.getPicsmall())) {
													if (uid.equals(fuid)) {
														Intent it = new Intent();
														it.setClass(
																VillageUserInfoDialog.this,
																DeleteOrBorwser.class);
														it.putExtra("tag", v
																.getTag()
																.toString());
														it.putExtra("po", i);
														it.putExtra(
																"picid",
																picphotos
																		.get(i)
																		.getPicid());
														it.putExtra("uid", uid);
														it.putExtra("fuid",
																fuid);
														startActivityForResult(
																it, 3);
													} else {
														Intent intent = new Intent();
														intent.setClass(
																VillageUserInfoDialog.this,
																Chakandatu.class);
														intent.putStringArrayListExtra(
																"photos",
																photopaths);
														intent.putIntegerArrayListExtra(
																"picids",
																picids);
														intent.putExtra("po", i);
														intent.putExtra("uid",
																uid);
														intent.putExtra(
																"picid",
																picphotos
																		.get(i)
																		.getPicid());
														intent.putExtra("po", i);
														startActivity(intent);
													}
												}
											}
										}
									});
								}
							}
						}
						switch (villageUserInfoList.get(0).getIsf()) {
						case 0:
							info_gouda
									.setBackgroundResource(R.drawable.info_gouda1);
							break;
						case 1:
							info_gouda
									.setBackgroundResource(R.drawable.info_fenshou);
						}
						if (!uid.equals(fuid)) {
							if (picphotos.size() == 0) {
								ll.setVisibility(View.GONE);
							} else {
								ll.setVisibility(View.VISIBLE);
							}
							iv.setVisibility(View.GONE);

						} else {
							xinxi_caifu_ll.setVisibility(View.VISIBLE);
							ll.setVisibility(View.VISIBLE);
							if (picphotos.size() >= 5) {
								iv.setVisibility(View.GONE);
							} else {
								iv.setVisibility(View.VISIBLE);
							}
						}
						if (!uid.equals(fuid)) {
							switch (villageUserInfoList.get(0).getIsb()) {
							case 0:
								village_userinfo_blacklistButton
										.setBackgroundResource(R.drawable.chatroom_userinfo_dialog_addblack_btn);
								break;

							case 1:
								village_userinfo_blacklistButton
										.setBackgroundResource(R.drawable.chatroom_userinfo_dialog_cancelblack_btn);
								break;
							}
						}
						switch (Integer.parseInt(villageUserInfoList.get(0)
								.getSex())) {
						case 0:
							village_userinfosex_ImageView
									.setImageDrawable(getResources()
											.getDrawable(R.drawable.what));
							break;
						case 1:
							village_userinfosex_ImageView
									.setImageDrawable(getResources()
											.getDrawable(R.drawable.woman));
							break;
						case 2:
							village_userinfosex_ImageView
									.setImageDrawable(getResources()
											.getDrawable(R.drawable.man));
							break;
						}
						pitAdapter = new PitemsAdapter(
								VillageUserInfoDialog.this, pitList,
								village_userinfo_icon_gridView);
						if (pitList.size() > 8 && pitList.size() < 16) {
							LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) village_userinfo_icon_gridView
									.getLayoutParams();
							linearParams.height = dm.widthPixels * 1 / 6;
							village_userinfo_icon_gridView
									.setLayoutParams(linearParams);
						} else if (pitList.size() <= 8) {
							LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) village_userinfo_icon_gridView
									.getLayoutParams();
							linearParams.height = dm.widthPixels * 1 / 12;
							village_userinfo_icon_gridView
									.setLayoutParams(linearParams);
						} else {
							LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) village_userinfo_icon_gridView
									.getLayoutParams();
							linearParams.height = dm.widthPixels * 1 / 4;
							village_userinfo_icon_gridView
									.setLayoutParams(linearParams);
						}
						village_userinfo_icon_gridView.setAdapter(pitAdapter);
						village_userinfo_photo.setImageUrl(villageUserInfoList
								.get(0).getHeader(), R.drawable.photo,
								Environment.getExternalStorageDirectory()
										+ File.separator + getPackageName()
										+ ConstantsJrc.PHOTO_PATH,
								ConstantsJrc.PROJECT_PATH + getPackageName()
										+ ConstantsJrc.PHOTO_PATH);
					}

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 勾搭、被勾搭线程
	 * 
	 * @author J.King
	 * 
	 */
	private class FollowThread extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					follow();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Handler followhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:

				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				followThread.stopThread(false);
				if (currIndex == 1) {
					beiguanzhuListView.requestLayout();
					bguanzhuAdapter.notifyDataSetChanged();
				}
				break;
			}
		};
	};

	private void follow() throws ClientProtocolException, IOException {
		followhandler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);

		final String result = HttpFollow.FollowGet(Integer.parseInt(bfuid),
				Integer.parseInt(buid), flg, su.getToken());

		// System.out.println(result);
		MessageJson mj = new MessageJson();
		messageList = mj.parseJson(result);
		// System.out.println(messageList.get(0).toString());
		followhandler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		followhandler.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (messageList.get(0).getRet().equals("0")) {
						Commond.showToast(getApplicationContext(), messageList
								.get(0).getTip());

						info_gouda
								.setBackgroundResource(R.drawable.info_gouda1);

					} else if (messageList.get(0).getRet().equals("1")) {
						Commond.showToast(getApplicationContext(), messageList
								.get(0).getTip());

						info_gouda
								.setBackgroundResource(R.drawable.info_fenshou);

						aubean.setFollowed(1);
						followhandler
								.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
						// onlineUserInfoList.get(0).setIsf(1);
					} else {
						Commond.showToast(getApplicationContext(), "请求出错");
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

	private class FollowThread2 extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					follow2();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Handler followhandler2 = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:

				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				followThread2.stopThread(false);
				if (currIndex == 1) {
					beiguanzhuListView.requestLayout();
					bguanzhuAdapter.notifyDataSetChanged();
				}
				break;
			}
		};
	};

	private void follow2() throws ClientProtocolException, IOException {
		followhandler2.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);

		final String result = HttpFollow.FollowGet(Integer.parseInt(bfuid),
				Integer.parseInt(buid), flg, su.getToken());

		MessageJson mj = new MessageJson();
		messageList2 = mj.parseJson(result);
		// System.out.println(messageList2.get(0).toString());
		followhandler2.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		followhandler2.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (messageList2.get(0).getRet().equals("0")) {
						Commond.showToast(getApplicationContext(), messageList2
								.get(0).getTip());

						info_gouda
								.setBackgroundResource(R.drawable.info_fenshou);

					} else if (messageList2.get(0).getRet().equals("1")) {
						Commond.showToast(getApplicationContext(), messageList2
								.get(0).getTip());

						info_gouda
								.setBackgroundResource(R.drawable.info_gouda1);

						// onlineUserInfoList.get(0).setIsf(0);
						aubean.setFollowed(0);
						followhandler2
								.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
					} else {
						Commond.showToast(getApplicationContext(), "请求出错");
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

	/**
	 * 拉黑被拉黑线程
	 * 
	 * @author J.King
	 * 
	 */
	private class BlackThread extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					blackuser();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Handler blackhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:

				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				blackThread.stopThread(false);
				if (currIndex == 1) {
					// beiguanzhuListView.requestLayout();
					// bguanzhuAdapter.notifyDataSetChanged();
				}
				break;
			}
		};
	};

	private void blackuser() throws ClientProtocolException, IOException {
		blackhandler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);

		final String result = HttpBlakcUser.BlackGet(
				Integer.parseInt(blackfuid), Integer.parseInt(blackuid),
				blackflg, su.getToken());

		// System.out.println(result);
		MessageJson mj = new MessageJson();
		messageblakcList = mj.parseJson(result);
		// System.out.println(messageblakcList.get(0).toString());
		blackhandler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		blackhandler.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (currIndex == 1) {
						village_userinfo_blacklistButton
								.setBackgroundResource(R.drawable.chatroom_userinfo_dialog_blacklist_btn);
					}
					if (messageblakcList.get(0).getRet().equals("0")) {
						Commond.showToast(getApplicationContext(),
								messageblakcList.get(0).getTip());
						village_userinfo_blacklistButton
								.setBackgroundResource(R.drawable.chatroom_userinfo_dialog_addblack_btn);
					} else if (messageblakcList.get(0).getRet().equals("1")) {
						Commond.showToast(getApplicationContext(),
								messageblakcList.get(0).getTip());
						village_userinfo_blacklistButton
								.setBackgroundResource(R.drawable.chatroom_userinfo_dialog_cancelblack_btn);
						// aubean.setFollowed(1);
						blackhandler
								.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);

					} else {
						Commond.showToast(getApplicationContext(), "请求出错");
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

	private class BlackThread2 extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					blackuser2();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Handler blackhandler2 = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:

				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				blackThread2.stopThread(false);
				if (currIndex == 1) {
					blackListView.requestLayout();
					blackListAdapter.notifyDataSetChanged();
				}
				break;
			}
		};
	};

	private void blackuser2() throws ClientProtocolException, IOException {
		blackhandler2.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);

		final String result = HttpBlakcUser.BlackGet(
				Integer.parseInt(blackfuid), Integer.parseInt(blackuid),
				blackflg, su.getToken());

		MessageJson mj = new MessageJson();
		messageblakcList2 = mj.parseJson(result);

		blackhandler2.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		blackhandler2.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (currIndex == 1) {
						village_userinfo_blacklistButton
								.setBackgroundResource(R.drawable.chatroom_userinfo_dialog_blacklist_btn);
					}
					if (messageblakcList2.get(0).getRet().equals("0")) {
						Commond.showToast(getApplicationContext(),
								messageblakcList2.get(0).getTip());
						village_userinfo_blacklistButton
								.setBackgroundResource(R.drawable.chatroom_userinfo_dialog_cancelblack_btn);
					} else if (messageblakcList2.get(0).getRet().equals("1")) {
						Commond.showToast(getApplicationContext(),
								messageblakcList2.get(0).getTip());
						village_userinfo_blacklistButton
								.setBackgroundResource(R.drawable.chatroom_userinfo_dialog_addblack_btn);
						blackhandler2
								.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
						if (currIndex == 1) {
							myblackList.remove(blackBean);
						}
					} else {
						Commond.showToast(getApplicationContext(), "请求出错");
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
			// 勾搭
			if (pageselect == 1) {
				AttentionUserListJson atjson = new AttentionUserListJson();
				attentionmoreList = atjson.parseJson(result);
				MessageJson mj = new MessageJson();
				msgList = mj.parseJsonPd(result);

				if (attentionmoreList.size() == 0) {
					villagevbeiguanzhuRelativeLayout.setVisibility(View.GONE);
					beiguanzhuListView.removeFooterView(vFooter);

				}
				for (AttentionUserBean a : attentionmoreList) {
					attentionList.add(a);
				}
				attentionmoreList.clear();
				if (isfoot == false) {
					bguanzhuAdapter = new BguanzhuAdapter(
							VillageUserInfoDialog.this, attentionList,
							beiguanzhuListView, listen, infolisten);
					beiguanzhuListView.setAdapter(bguanzhuAdapter);
					villagevbeiguanzhuRelativeLayout.setVisibility(View.GONE);
				} else {
					beiguanzhuListView.requestLayout();
					bguanzhuAdapter.notifyDataSetChanged();
					villagevbeiguanzhuRelativeLayout.setVisibility(View.GONE);
				}
			}
			// 被勾搭
			if (pageselect == 2) {

				AttentionUserListJson atjson = new AttentionUserListJson();
				attentionmoreList = atjson.parseJson(result);
				MessageJson mj = new MessageJson();
				msgList = mj.parseJsonPd(result);

				if (attentionmoreList.size() == 0) {
					villagevguanzhuRelativeLayout.setVisibility(View.GONE);
					guanzhuListView.removeFooterView(vFooter);
					// System.out.println("执行。。。。");
				}
				for (AttentionUserBean a : attentionmoreList) {
					attentionList2.add(a);
				}
				attentionmoreList.clear();
				if (isfoot2 == false) {
					bguanzhuAdapter = new BguanzhuAdapter(
							VillageUserInfoDialog.this, attentionList2,
							guanzhuListView, listen, infolisten);
					guanzhuListView.setAdapter(bguanzhuAdapter);
					villagevguanzhuRelativeLayout.setVisibility(View.GONE);
				} else {
					guanzhuListView.requestLayout();
					bguanzhuAdapter.notifyDataSetChanged();
					villagevguanzhuRelativeLayout.setVisibility(View.GONE);
				}
			}
			// 礼物
			if (pageselect == 10) {
				LiwuListJson liwujson = new LiwuListJson();
				liwumoreitemList = liwujson.parseJson(result);
				MessageJson mj = new MessageJson();
				msgList = mj.parseJsonPd(result);
				page = msgList.get(0).getPage();
				liwull.setVisibility(View.GONE);
				if (liwumoreitemList.size() == 0) {
					villageliwuListRelativeLayout.setVisibility(View.GONE);
				}
				for (LiwuItemsBean l : liwumoreitemList) {
					liwuitemList.add(l);
				}
				if (isgridfoot == false) {
					liwuAdapter = new LiwuListAdapter(
							VillageUserInfoDialog.this, liwuitemList,
							liwuGridView, liwulisten);
					liwuGridView.setAdapter(liwuAdapter);
					villageliwuListRelativeLayout.setVisibility(View.GONE);
				} else {
					liwuGridView.requestLayout();
					liwuAdapter.notifyDataSetChanged();
					villageliwuListRelativeLayout.setVisibility(View.GONE);
				}
				IsLoadShop = false;
			}

			// 礼物分类
			if (pageselect == 3) {

				LiwuTypeListJson liwujson = new LiwuTypeListJson();
				liwutypemoreitemList = liwujson.parseJson(result);
				MessageJson mj = new MessageJson();
				msgList = mj.parseJsonPd(result);

				if (liwutypemoreitemList.size() == 0) {
					villageliwutypeListRelativeLayout.setVisibility(View.GONE);

				}
				for (LiwuTypeBean l : liwutypemoreitemList) {
					liwutypeitemList.add(l);
				}
				liwutypemoreitemList.clear();
				if (isliwutypefoot == false) {
					liwuTypeListAdapter = new LiwuTypeListAdapter(
							VillageUserInfoDialog.this, liwutypeitemList,
							liwuTypeListView, liwulisten);
					liwuTypeListView.setAdapter(liwuTypeListAdapter);
					villageliwutypeListRelativeLayout.setVisibility(View.GONE);

				} else {
					liwuTypeListView.requestLayout();
					liwuTypeListAdapter.notifyDataSetChanged();
					villageliwutypeListRelativeLayout.setVisibility(View.GONE);

				}
				IsLoadShopType = false;
			}
			// 我的礼物
			if (pageselect == 4) {
				MyLiwuItemJson myliwujson = new MyLiwuItemJson();
				myliwumoreitemlist = myliwujson.parseJson(result);
				MessageJson mj = new MessageJson();
				msgList = mj.parseJsonPd(result);
				if (myliwumoreitemlist.size() == 0) {
					villagemyliwuListRelativeLayout.setVisibility(View.GONE);
					myLiwuListView.removeFooterView(vFooter);

				}
				for (MyLiwuItemsBean m : myliwumoreitemlist) {
					myliwuitemlist.add(m);
				}
				myliwumoreitemlist.clear();
				if (isMyLiWuFoot == false) {
					myLiwuAdapter = new MyLiWuAdapter(
							VillageUserInfoDialog.this, myliwuitemlist,
							myLiwuListView, infolisten2);
					myLiwuListView.setAdapter(myLiwuAdapter);
					villagemyliwuListRelativeLayout.setVisibility(View.GONE);
				} else {
					myLiwuListView.requestLayout();
					myLiwuAdapter.notifyDataSetChanged();
					villagemyliwuListRelativeLayout.setVisibility(View.GONE);
				}
			}

			if (pageselect == 5) {
				String tip = "";
				dialog.cancel();
				if (result == null) {
					// fresh.setVisibility(View.VISIBLE);
					Commond.showToast(getApplicationContext(), "小贱提醒 ：当前网络不稳定！");
					return;
				}

				try {
					// //////////////////////////////////////////////正文内容页面
					JSONObject jsonChannel = new JSONObject(result);
					tip = URLDecoder.decode(jsonChannel.optString("tip"));
					Commond.showToast(getApplicationContext(), tip);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// fresh.setVisibility(View.VISIBLE);
					e.printStackTrace();
				}
			}
			// 黑名单
			if (pageselect == 8) {
				BlackListJson bljson = new BlackListJson();
				myblackmoreList = bljson.parseJson(result);
				MessageJson mj = new MessageJson();
				msgList = mj.parseJsonPd(result);

				if (myblackmoreList.size() == 0) {
					villageblackListRelativeLayout.setVisibility(View.GONE);
					blackListView.removeFooterView(vFooter);
				}
				for (BlackListBean a : myblackmoreList) {
					myblackList.add(a);
				}
				myblackmoreList.clear();
				if (isBlackFoot == false) {
					blackListAdapter = new BlackListAdapter(
							VillageUserInfoDialog.this, myblackList,
							blackListView, blacklisten, blackinfolisten);
					blackListView.setAdapter(blackListAdapter);
					villageblackListRelativeLayout.setVisibility(View.GONE);
				} else {
					blackListView.requestLayout();
					blackListAdapter.notifyDataSetChanged();
					villageblackListRelativeLayout.setVisibility(View.GONE);
				}
			}
			if (url.contains(ConstantsJrc.QIANGDELETE)) {
				String tip = "";
				int ret;
				try {
					villageuserinfo_progressbarRelativeLayout
							.setVisibility(View.GONE);
					// //////////////////////////////////////////////正文内容页面
					JSONObject jsonChannel = new JSONObject(result);
					tip = URLDecoder.decode(jsonChannel.optString("tip"));
					ret = jsonChannel.optInt("ret");
					if (ret == 1) {
						for (int j = 0; j < picphotos.size(); j++) {
							if (picphotos.get(j).getPicid() == picdeleteid) {
								ll.removeViewAt(j);
								picphotos.remove(j);
								photopaths.remove(j);
								picids.remove(j);

								iv1s.remove(j);
								ivks.remove(j);
								if (picphotos.size() < 5) {
									iv.setVisibility(View.VISIBLE);
									RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) ll
											.getLayoutParams();
									llParams.height = dm.widthPixels * 1 / 6;
									llParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
									ll.setLayoutParams(llParams);
								}
							}
						}
					}
					Commond.showToast(getApplicationContext(), tip);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					// fresh.setVisibility(View.VISIBLE);
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class LoadUserDataThread extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				initData();
			}
		}
	}

	private Handler loadhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:
				dialog.setCancelable(true);
				dialog.show();
				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				dialog.cancel();
				loaduserDataThread.stopThread(false);
				break;
			}
		};
	};

	private void initData() {
		loadhandler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);

		loadhandler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		loadhandler.post(new Runnable() {

			@Override
			public void run() {
				try {

					userIDTextView.setText(su.getUid());
					userScoreTextView.setText(su.getScore());
					userLevelTextView.setText(su.getLevel());
					userNickEditText.setText(su.getNick());

					userSignEditText.setText(su.getSign());
					// System.out.println("本地性别：" + su.getSex());
					if (su.getSex().equals("1")) {
						userGirlRadioButton.setChecked(true);
						sex = "1";
					} else if (su.getSex().equals("2")) {
						userBoyRadioButton.setChecked(true);
						sex = "2";
					}
					userCityEditText.setText(su.getCity());
					// String[] str = su.getBirthday().split("\\-");
					// editYear.setText(str[0].toString());
					// editMonth.setText(str[1].toString());
					// editDay.setText(str[2].toString());

					// userPhoneEditText.setText(su.getPhone());
					// userEmailEditText.setText(su.getEmail());

				} catch (Exception e) {
					// TODO: handle exception
					// Toast.makeText(getApplicationContext(), "网络状况不佳",
					// 0).show();
				}
			}
		});
	}

	class ModifyUserInfo extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					ModifyData();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Handler modifyHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:
				dialog.setCancelable(true);
				dialog.show();
				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				dialog.cancel();
				modifyThread.stopThread(false);
				break;
			}
		};
	};

	private void ModifyData() throws ClientProtocolException, IOException {
		modifyHandler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);
		// System.out.println(sex);
		final String result = HttpUserInfo.ModifyDataPost(userIDTextView
				.getText().toString(), "", URLEncoder.encode(userNickEditText
				.getText().toString()), URLEncoder.encode(userSignEditText
				.getText().toString()), sex, "", "", URLEncoder
				.encode(userbirthday_EditText.getText().toString()), URLEncoder
				.encode(userCityEditText.getText().toString()), su.getToken());
		// System.out.println(result);
		MessageJson mj = new MessageJson();
		messageList = mj.parseJsonHeader(result);
		modifyHandler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		modifyHandler.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (messageList.get(0).getRet().equals("1")) {

						// Intent intent = new Intent(getApplicationContext(),
						// MessageSystemDialog.class);
						// intent.putExtra("ret",
						// String.valueOf(messageList.get(0).getRet()));
						// intent.putExtra("tip", messageList.get(0).getTip()
						// .toString());
						// startActivity(intent);
						Commond.showToast(getApplicationContext(), messageList
								.get(0).getTip());

						modifyThread.stopThread(false);
						su.setNick(userNickEditText.getText().toString());
						su.setSign(userSignEditText.getText().toString());
						su.setLevel(userLevelTextView.getText().toString());
						if (userGirlRadioButton.isChecked() == true) {
							su.setSex("1");
						} else if (userBoyRadioButton.isChecked() == true) {
							su.setSex("2");
						} else {
							su.setSex("0");
						}
						if (!messageList.get(0).getHeader().toString()
								.equals("")) {
							su.setHeader(messageList.get(0).getHeader());
						}
						su.setCity(userCityEditText.getText().toString());
						su.setBirthday(userbirthday_EditText.getText()
								.toString());
						// su.setPhone(userPhoneEditText.getText().toString());
						// su.setEmail(userEmailEditText.getText().toString());
						getIntent().putExtra("nick",
								userNickEditText.getText().toString());
						getIntent().putExtra("level",
								userLevelTextView.getText().toString());
						// Log.e("11111111111",
						// userLevelTextView.getText().toString()+"");
						setResult(20, getIntent());
						// finish();
					} else {
						// Intent intent = new Intent(getApplicationContext(),
						// MessageSystemDialog.class);
						// intent.putExtra("ret",
						// String.valueOf(messageList.get(0).getRet()));
						// intent.putExtra("tip", messageList.get(0).getTip()
						// .toString());
						// startActivity(intent);
						Commond.showToast(getApplicationContext(), messageList
								.get(0).getTip());
					}
				} catch (Exception e) {
					// TODO: handle exception
					Commond.showToast(getApplicationContext(), "检测到网络网络异常或未开启");

				}
			}
		});
	}

	class UpLoadPhotoThread extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					UpLoadPhotoData();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Handler upLoadHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:
				dialog2.setCancelable(true);
				dialog2.show();
				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				dialog2.cancel();
				upLoadThread.stopThread(false);
				break;
			}
		};
	};

	private void UpLoadPhotoData() throws ClientProtocolException, IOException {
		upLoadHandler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);
		// System.out.println(sex);
		final String result = HttpUserInfo.ModifyPhotoPost(su.getUid(),
				photoPath, su.getToken());
		// System.out.println(result);
		MessageJson mj = new MessageJson();
		messageList = mj.parseJsonHeader(result);
		upLoadHandler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		upLoadHandler.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (messageList.get(0).getRet().equals("1")) {

						// Intent intent = new Intent(getApplicationContext(),
						// MessageSystemDialog.class);
						// intent.putExtra("ret",
						// String.valueOf(messageList.get(0).getRet()));
						// intent.putExtra("tip", messageList.get(0).getTip()
						// .toString());
						// startActivity(intent);
						Commond.showToast(getApplicationContext(), messageList
								.get(0).getTip().toString());

						if (!messageList.get(0).getHeader().toString()
								.equals("")) {
							su.setHeader(messageList.get(0).getHeader());
							MainMenuActivity.header = messageList.get(0)
									.getHeader();
							photoUrl = messageList.get(0).getHeader()
									.toString();
						}

					} else {
						// Intent intent = new Intent(getApplicationContext(),
						// MessageSystemDialog.class);
						// intent.putExtra("ret",
						// String.valueOf(messageList.get(0).getRet()));
						// intent.putExtra("tip", messageList.get(0).getTip()
						// .toString());
						// startActivity(intent);
						Commond.showToast(getApplicationContext(), messageList
								.get(0).getTip().toString());

					}
				} catch (Exception e) {

				}
			}
		});
	}

	class ChooseIdThread extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					ChooseId();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Handler chooseHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:
				dialog.setCancelable(true);
				dialog.show();
				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				dialog.cancel();
				chooseThread.stopThread(false);
				break;
			}
		};
	};

	private void ChooseId() throws ClientProtocolException, IOException {
		chooseHandler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);
		final String result = HttpGetCode.GetCodePost(su.getUid(), phone,
				email, su.getToken());
		// System.out.println(result);
		MessageJson mj = new MessageJson();
		messageList = mj.parseJson(result);
		chooseHandler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		chooseHandler.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (messageList.get(0).getRet().equals("1")) {

						Intent intent = new Intent(getApplicationContext(),
								MessageSystemDialog.class);
						intent.putExtra("ret",
								String.valueOf(messageList.get(0).getRet()));
						intent.putExtra("tip", messageList.get(0).getTip()
								.toString());
						startActivity(intent);
						chooseThread.stopThread(false);
						if (!chooseemail_EditText.getText().toString()
								.equals(""))
							su.setPhone(choosephone_EditText.getText()
									.toString());
						if (!chooseemail_EditText.getText().toString()
								.equals(""))
							su.setEmail(chooseemail_EditText.getText()
									.toString());
					} else {
						Intent intent = new Intent(getApplicationContext(),
								MessageSystemDialog.class);
						intent.putExtra("ret",
								String.valueOf(messageList.get(0).getRet()));
						intent.putExtra("tip", messageList.get(0).getTip()
								.toString());
						startActivity(intent);
					}
				} catch (Exception e) {
					// TODO: handle exception
					// Toast.makeText(getApplicationContext(), "网络状况不佳",
					// 0).show();
				}
			}
		});
	}

	class ReLoginThread extends Thread {
		private boolean _run = true;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		@Override
		public void run() {
			if (_run) {
				try {
					relogin();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Handler reloginhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:
				dialog.setCancelable(true);
				dialog.show();
				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				dialog.cancel();
				reLoginThread.stopThread(false);
				break;
			}
		};
	};

	private void relogin() throws ClientProtocolException, IOException {
		reloginhandler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);

		final String result = HttpGetCode.ReLoadPost(su.getUid(),
				choosephone_EditText.getText().toString(), chooseemail_EditText
						.getText().toString(), input_code_EditText.getText()
						.toString());

		// System.out.println(result);
		UserInfoJson uj = new UserInfoJson();
		userList = uj.parseJson(result);
		// System.out.println("用户：" + userList.toString());

		reloginhandler.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
		reloginhandler.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (userList.get(0).getRet() == 1) {
						try {
							SystemUtil
									.writeUidJs(
											jspath,
											jsname,
											"var uid="
													+ userList.get(0).getUid()
													+ ";\r\n"
													+ "var pkg='"
													+ PhoneInfo
															.getInstance(
																	getApplicationContext())
															.getPackage(
																	VillageUserInfoDialog.this)
													+ "';"
													+ "\r\n"
													+ "var ver='"
													+ PhoneInfo
															.getInstance(
																	getApplicationContext())
															.getVersionName(
																	VillageUserInfoDialog.this)
													+ "';" + "\r\n"
													+ "var hack=0;", false);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						su.setUid(String.valueOf(userList.get(0).getUid()));
						su.setNick(userList.get(0).getNick());
						su.setHeader(userList.get(0).getHeader());
						su.setSign(userList.get(0).getSign());
						su.setScore(String.valueOf(userList.get(0).getScore()));
						su.setLevel(userList.get(0).getLevel());
						su.setBirthday(userList.get(0).getBirthday());
						su.setCity(userList.get(0).getCity());
						// su.setQQ(String.valueOf(userList.get(0).getQq()));
						// su.setWeixin(userList.get(0).getWeixin());
						su.setSex(String.valueOf(userList.get(0).getSex()));
						reLoginThread.stopThread(false);
						Intent intent = new Intent(getApplicationContext(),
								MessageSystemDialog.class);
						intent.putExtra("ret",
								String.valueOf(userList.get(0).getRet()));
						intent.putExtra("tip", userList.get(0).getTip()
								.toString());
						startActivity(intent);
					} else {
						reLoginThread.stopThread(false);
						Intent intent = new Intent(getApplicationContext(),
								Message0Dialog.class);
						intent.putExtra("ret",
								String.valueOf(userList.get(0).getRet()));
						intent.putExtra("tip", userList.get(0).getTip()
								.toString());

						DeliverData deliverData = new DeliverData();
						deliverData.setPersonList(chatmsglist);
						Bundle bundle1 = new Bundle();
						bundle1.putSerializable(KEYGUARD_SERVICE, deliverData);
						intent.putExtras(bundle1);
						startActivity(intent);
					}
				} catch (Exception e) {
					// TODO: handle exception
					Intent intent = new Intent(getApplicationContext(),
							MessageSystemDialog.class);
					intent.putExtra("ret", "0");
					intent.putExtra("tip", "网络状况不佳");
					startActivity(intent);
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (mTencent != null) {
			mTencent.onActivityResult(requestCode, resultCode, data);
		}
		if (requestCode == 1) {
			if (resultCode == 20) {
				// String nick = data.getStringExtra("nick");
				// String level = data.getStringExtra("level");
				// getIntent().putExtra("nick", nick);
				// getIntent().putExtra("level", level);
				// setResult(20, getIntent());

				photoPath = data.getStringExtra("path");
				// System.out.println(photoPath);
				if (!photoPath.equals("")) {
					bitmap = BitmapFactory.decodeFile(photoPath);
					userHeaderImageView.setImageBitmap(bitmap);
					upLoadThread = new UpLoadPhotoThread();
					upLoadThread.start();
					// userNickEditText.setText(sunick);
					File file = new File(MainMenuActivity.sppath
							+ "TokenDate.xml");
					if (file.exists()) {
						deletefile(file);
					}

					File sfile = new File(MainMenuActivity.sppath
							+ "AuthoSharePreference.xml");
					if (sfile.exists()) {
						deletefile(sfile);
						Appstart.swi = 0;
					}
				}
			}
			if (resultCode == 21) {
				if (pageselect == 2) {
					guanzhuListView.setVisibility(View.VISIBLE);
					villagevbeiguanzhuRelativeLayout
							.setVisibility(View.VISIBLE);
					attentionList2.clear();
					loading.setVisibility(View.GONE);
					String url2 = ConstantsJrc.FOLLOWS + "?" + "uid=" + fuid
							+ "&ouid=" + uid + "&flg=1" + "&page=" + "&token="
							+ su.getToken();
					StringBuffer data2 = new StringBuffer();
					// 请求网络验证登陆
					HttpRequestTask request2 = new HttpRequestTask();
					request2.execute(url2, data2.toString());
				}

			}
		}
		if (resultCode == 50) {
			int index = data.getIntExtra("index", 50);
			if (index == 33) {
				// Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
				// getImage.addCategory(Intent.CATEGORY_OPENABLE);
				// getImage.setType("image/*");
				// startActivityForResult(getImage, 2);
				changepo = data.getIntExtra("po", 6);
				picid = data.getIntExtra("picid", 6);
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
				intent.setDataAndType(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						ConstantsJrc.IMAGE_UNSPECIFIED);
				startActivityForResult(intent, 2);

			} else if (index == 44) {
				changepo = data.getIntExtra("po", 6);
				picid = data.getIntExtra("picid", 6);
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(
						MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(new File(Environment
								.getExternalStorageDirectory().getPath()
								.toString(), haomiao + ".jpg")));
				// 启动activity并等待返回结果
				startActivityForResult(intent, PHOTOHRAPH);
			}
		}
		// 拍照
		if (requestCode == PHOTOHRAPH) {
			String sspath = Environment.getExternalStorageDirectory().getPath()
					.toString()
					+ "/" + haomiao + ".jpg";
			File file = new File(sspath);
			if (!file.exists()) {
				return;
			}
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			bmp = Shangchuan.getbp(sspath, 300);
			smallbmp = Shangchuan.getbp(sspath, 100);
			bmp.compress(CompressFormat.JPEG, 100, os);
			byte[] bytes = os.toByteArray();
			File filesmall = byteToFile(bytes);
			sspath = filesmall.getAbsolutePath().toString();

			scpicpath.add(sspath);
			villageuserinfo_progressbarRelativeLayout
					.setVisibility(View.VISIBLE);
			StringBuffer databuff = new StringBuffer();
			databuff.append("&uid=");
			databuff.append(su.getUid());

			databuff.append("&pos=");
			databuff.append(scpicpath.indexOf(sspath));

			new Thread(new uphoto(bmp, sspath, databuff)).start();

			// 拍完后，重新查询数据库，加载适配器。将照的相片显示出来
		}
		if (data == null)
			return;

		if (requestCode == 2) {
			ContentResolver resolver = getContentResolver();
			Uri uri = data.getData();
			try {
				String[] filePathColumn = { MediaStore.Images.Media.DATA };
				Cursor cursor = resolver.query(uri, filePathColumn, null, null,
						null);
				if (null != cursor) {
					cursor.moveToFirst();
					int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
					// String picturePath = cursor.getString(columnIndex);

					// 最后根据索引值获取图片路径
					path = cursor.getString(columnIndex);
					cursor.close();
				} else {
					path = uri.getPath().toString();
				}

				if (scpicpath.contains(path)) {
					Commond.showToast(VillageUserInfoDialog.this,
							"亲，该图片已经存在，请重新选择！");
					return;
				}
				// /////////////////////////////////////////////////////////////////////////////
				Intent i = new Intent(this, ImageZoom.class);
				i.putExtra("imageurl", path);
				i.putExtra("izhl", 2);
				i.putExtra("savepath", Appstart.jrcsave.getPath());
				i.putExtra("downpath", Appstart.jrcsave.getPath());
				startActivityForResult(i, 7);
			} catch (Exception e) {
				e.printStackTrace();
				Commond.showToast(VillageUserInfoDialog.this, "添加失败，请重新添加！");
			}
			super.onActivityResult(requestCode, resultCode, data);
		}
		if (resultCode == 77) {
			Bitmap bm = null;
			if (TextUtils.isEmpty(path)) {
				return;
			}
			// suo fang tupian
			// if (bm.getWidth() >= bm.getHeight() &&
			// (bm.getWidth()/3*2)>320) {
			// bm = getbp(path, 300/3*2);
			// } else if (bm.getWidth() < bm.getHeight() &&
			// (bm.getWidth()/3*2)>320) {
			// bm = getbp(path, 300/3*2);
			// }
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			bm = Shangchuan.getbp(path, 480);
			smallbmp = Shangchuan.getbp(path, 100);
			bm.compress(CompressFormat.JPEG, 100, os);
			byte[] bytes = os.toByteArray();
			File filesmall = byteToFile(bytes);
			path = filesmall.getAbsolutePath().toString();

			scpicpath.add(path);
			villageuserinfo_progressbarRelativeLayout
					.setVisibility(View.VISIBLE);
			StringBuffer databuff = new StringBuffer();
			databuff.append("&uid=");
			databuff.append(su.getUid());

			databuff.append("&pos=");
			databuff.append(scpicpath.indexOf(path));

			new Thread(new uphoto(bm, path, databuff)).start();
		}
		if (resultCode == 51) {
			int index = data.getIntExtra("dorb", 51);
			String tag = data.getStringExtra("tag");
			int po = data.getIntExtra("po", 0);
			if (index == 33) {
				Intent intent = new Intent();
				intent.setClass(this, Chakandatu.class);
				intent.putStringArrayListExtra("photos", photopaths);
				intent.putExtra("po", po);
				intent.putExtra("uid", uid);
				intent.putExtra("picid", picphotos.get(i).getPicid());
				startActivity(intent);

			} else if (index == 44) {
				for (int i = 0; i < picphotos.size(); i++) {
					if (tag.equals(picphotos.get(i).getPicsmall())) {
						villageuserinfo_progressbarRelativeLayout
								.setVisibility(View.VISIBLE);
						picdeleteid = picphotos.get(i).getPicid();
						deletePhoto(picphotos.get(i).getPicid());
						// scpicpath.remove(i);
					}
				}
			}
		}
		if (resultCode == 30) {
			if (type == 1) {
				Intent it = getIntent();
				setResult(31, it);
				finish();
			} else if (type == 5) {
				finish();
			} else {
				mTabPager.setCurrentItem(0);
			}
		}
	}

	public void getjson(String ss) {
		try {
			JSONObject jsonChannel2;
			jsonChannel2 = new JSONObject(ss);

			unick = URLDecoder.decode(jsonChannel2.optString("nickname"));
			header = URLDecoder
					.decode(jsonChannel2.optString("figureurl_qq_2"));
			if (unick != null && !unick.equalsIgnoreCase("") && header != null
					&& !header.equalsIgnoreCase("")) {
				sharesaveDate();
				userNickEditText.setText(unick);
				userHeaderImageView.setImageUrl(header, R.drawable.photo,
						Environment.getExternalStorageDirectory()
								+ File.separator + getPackageName()
								+ ConstantsJrc.PHOTO_PATH,
						ConstantsJrc.PROJECT_PATH + getPackageName()
								+ ConstantsJrc.PHOTO_PATH);
				dialog.cancel();
				su.setNick(unick);
				su.setHeader(header);
				MainMenuActivity.header = header;
				MainMenuActivity.nick = unick;
				File sfile1 = new File(MainMenuActivity.sppath
						+ "AuthoSharePreference.xml");
				// Commond.showToast(UserInfoActivity.this, "绑定QQ成功！");
				// qqsendMess();
				if (sfile1.exists()) {
					deletefile(sfile1);
					Appstart.swi = 0;
				}
				dialog.show();
				/*
				 * 绑定QQ后上传头像
				 */
				String url = "http://jrc.hutudan.com/usercenter/open_bind.php"
						+ "?token=" + su.getToken();
				StringBuffer data = new StringBuffer();
				data.append("&uid=");
				data.append(MainMenuActivity.uid);
				data.append("&type=");
				data.append(2);
				data.append("&header=");
				data.append(URLEncoder.encode(header));
				data.append("&nick=");
				data.append(URLEncoder.encode(unick));
				HttpRequestTask request = new HttpRequestTask();
				request.execute(url, data.toString());

				getIntent().putExtra("nick", unick);
				setResult(20, getIntent());

			} else {
				Commond.showToast(getApplicationContext(), "绑定QQ失败！");
			}
			// AuthorizeActivity.sinaname="";
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		File sfile = new File(MainMenuActivity.sppath
				+ "AuthoSharePreference.xml");
		if (sfile.exists()) {
			if (Appstart.swi == 0) {
				if (AuthorizeActivity.sinaname != null
						&& !AuthorizeActivity.sinaname.equalsIgnoreCase("")) {
					userNickEditText.setText(AuthorizeActivity.sinaname);
					userHeaderImageView.setImageUrl(
							AuthorizeActivity.sinaheader, R.drawable.photo,
							Environment.getExternalStorageDirectory()
									+ File.separator + getPackageName()
									+ ConstantsJrc.PHOTO_PATH,
							ConstantsJrc.PROJECT_PATH + getPackageName()
									+ ConstantsJrc.PHOTO_PATH);
					su.setNick(AuthorizeActivity.sinaname);
					su.setHeader(AuthorizeActivity.sinaheader);
					MainMenuActivity.header = AuthorizeActivity.sinaheader;
					MainMenuActivity.nick = AuthorizeActivity.sinaname;
					getIntent().putExtra("nick", AuthorizeActivity.sinaname);

					/*
					 * 绑定xinlang后上传头像
					 */
					dialog.show();
					bdsc(1, AuthorizeActivity.sinaheader,
							AuthorizeActivity.sinaname);

					File file = new File(MainMenuActivity.sppath
							+ "TokenDate.xml");
					if (file.exists()) {
						deletefile(file);
					}
					Appstart.swi = 1;
					// Commond.showToast(UserInfoActivity.this, "绑定新浪微博成功！");
				}
			}
		}
		if (DazuiActivity.player != null) {
			if (MainMenuActivity.borz == true) {
				DazuiActivity.player.start();
			}
		}
		if (Dzmysave.player != null) {
			if (MainMenuActivity.borz == true) {
				Dzmysave.player.start();
			}
		}
		if (Mydazui.player != null) {
			if (MainMenuActivity.borz == true) {
				Mydazui.player.start();
			}
		}
		if (Dynamic.dyplayer != null) {
			if (MainMenuActivity.borz == true) {
				Dynamic.dyplayer.start();
			}
		}
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if (DazuiActivity.player != null && DazuiActivity.player.isPlaying()) {
			// if (detatilispause == false) {
			DazuiActivity.player.pause();
			// }
		}
		if (Dzmysave.player != null && Dzmysave.player.isPlaying()) {
			// if (detatilispause == false) {
			Dzmysave.player.pause();
			// }
		}
		if (Mydazui.player != null && Mydazui.player.isPlaying()) {
			// if (detatilispause == false) {
			Mydazui.player.pause();
			// }
		}
		super.onPause();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ("1".equals(chatroom)) {

				// 关闭对话框！
				if (type == 1) {
					Intent it = getIntent();
					setResult(31, it);
				}
				finish();
			}
			if (currIndex == 0) {

				// 关闭对话框！
				if (type == 1) {
					Intent it = getIntent();
					setResult(31, it);
				}
				finish();
			} else if (gridRelative.getVisibility() == View.VISIBLE) {
				gridRelative.setVisibility(View.GONE);
				liwuTypeRelative.setVisibility(View.VISIBLE);
			} else {
				mTabPager.setCurrentItem(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		// modifyThread.stopThread(false);
		if (currIndex == 5) {
			upLoadThread.stopThread(false);
			loaduserDataThread.stopThread(false);
		}
		picphotos.clear();
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
			bitmap = null;
		}
		if (smallbmp != null && !smallbmp.isRecycled()) {
			smallbmp.recycle();
			smallbmp = null;
		}
		super.onDestroy();
	}

	private void sharesaveDate() {
		// String ss= paCalendar.getInstance();
		prefs = getSharedPreferences("TokenDate", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("token", mAccessToken);
		editor.putString("openid", mOpenId);
		editor.commit(); // 注意一定要写此函数（语句）
	}

	private SharedPreferences sharereadDate() {
		SharedPreferences prefs = getSharedPreferences("TokenDate",
				Context.MODE_PRIVATE);
		return prefs;
	}

	public boolean satisfyConditions() {
		return mAccessToken != null && mAppid != null && mOpenId != null
				&& !mAccessToken.equals("") && !mAppid.equals("")
				&& !mOpenId.equals("");
	}

	public static final int PROGRESS = 0;

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case PROGRESS:
			dialog = new ProgressDialog(this);
			((ProgressDialog) dialog).setMessage("请求中,请稍等...");
			break;
		}

		return dialog;
	}

	public void deletefile(File file) {
		// 执行删除，或者什么。。。操作
		File delFile = new File(file.getAbsolutePath());
		if (delFile.exists()) {
			// Log.i("PATH", delFile.getAbsolutePath());
			delFile.delete();
		}

	}

	private void bdsc(int type, String header, String nick) {
		String url = "http://jrc.hutudan.com/usercenter/open_bind.php?"
				+ "token=" + su.getToken();
		StringBuffer data = new StringBuffer();
		data.append("&uid=");
		data.append(MainMenuActivity.uid);
		data.append("&type=");
		data.append(type);
		data.append("&header=");
		data.append(URLEncoder.encode(header));
		data.append("&nick=");
		data.append(URLEncoder.encode(nick));
		HttpRequestTask request = new HttpRequestTask();
		request.execute(url, data.toString());
	}

	// //////////////////////////////////////QQ登录
	private void onClickUserInfo() {
		if (ready()) {

			mTencent.requestAsync(Constants.GRAPH_SIMPLE_USER_INFO, null,
					Constants.HTTP_GET, new BaseApiListener(), null);
			dialog.show();
		}

	}

	private boolean ready() {
		boolean ready = mTencent.isSessionValid()
				&& mTencent.getOpenId() != null;
		return ready;
	}

	private class BaseApiListener implements IRequestListener {

		@Override
		public void onComplete(final JSONObject response, Object state) {
			showResult("IRequestListener.onComplete:", response.toString());
			doComplete(response, state);
		}

		protected void doComplete(JSONObject response, Object state) {
		}

		@Override
		public void onIOException(final IOException e, Object state) {
			showResult("IRequestListener.onIOException:", e.getMessage());
		}

		@Override
		public void onMalformedURLException(final MalformedURLException e,
				Object state) {
			showResult("IRequestListener.onMalformedURLException", e.toString());
		}

		@Override
		public void onJSONException(final JSONException e, Object state) {
			showResult("IRequestListener.onJSONException:", e.getMessage());
		}

		@Override
		public void onConnectTimeoutException(ConnectTimeoutException arg0,
				Object arg1) {
			showResult("IRequestListener.onConnectTimeoutException:",
					arg0.getMessage());

		}

		@Override
		public void onSocketTimeoutException(SocketTimeoutException arg0,
				Object arg1) {
			showResult("IRequestListener.SocketTimeoutException:",
					arg0.getMessage());
		}

		@Override
		public void onUnknowException(Exception arg0, Object arg1) {
			showResult("IRequestListener.onUnknowException:", arg0.getMessage());
		}

		@Override
		public void onHttpStatusException(HttpStatusException arg0, Object arg1) {
			showResult("IRequestListener.HttpStatusException:",
					arg0.getMessage());
		}

		@Override
		public void onNetworkUnavailableException(
				NetworkUnavailableException arg0, Object arg1) {
			showResult("IRequestListener.onNetworkUnavailableException:",
					arg0.getMessage());
		}
	}

	private void showResult(final String base, final String msg) {
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				if (dialog.isShowing())
					dialog.dismiss();
				getjson(msg);
			}
		});
	}

	private void onClickLogin() {
		if (!mTencent.isSessionValid()) {
			IUiListener listener = new BaseUiListener() {
				@Override
				protected void doComplete(JSONObject values) {

				}
			};
			mTencent.login(this, SCOPE, listener);
		} else {
			mTencent.logout(this);
			onClickUserInfo();
		}
	}

	private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(JSONObject response) {
			String ssk = response.toString();
			doComplete(response);
			onClickUserInfo();
		}

		protected void doComplete(JSONObject values) {

		}

		@Override
		public void onError(UiError e) {
			showResult("onError:", "code:" + e.errorCode + ", msg:"
					+ e.errorMessage + ", detail:" + e.errorDetail);
		}

		@Override
		public void onCancel() {
			showResult("onCancel", "");
		}
	}

	/**
	 * Updates day wheel. Sets max days according to selected month and year
	 */
	void updateDays(WheelView year, WheelView month, WheelView day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR,
				calendar.get(Calendar.YEAR) + year.getCurrentItem());
		calendar.set(Calendar.MONTH, month.getCurrentItem());

		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		day.setViewAdapter(new DateNumericAdapter(this, 1, maxDays, calendar
				.get(Calendar.DAY_OF_MONTH) - 1));
		int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
		// day.setCurrentItem(curDay - 1, true);

		userbirthday_EditText.setText((calendar.get(Calendar.YEAR) - 100) + "-"
				+ (month.getCurrentItem() + 1) + "-" + (curDay));
	}

	/**
	 * 自定义设置选择日期
	 * 
	 * @param year
	 *            布局控件
	 * @param month
	 *            布局控件
	 * @param day
	 *            布局控件
	 * @param y
	 *            年
	 * @param m
	 *            月
	 * @param d
	 *            日
	 */
	void setDate(WheelView year, WheelView month, WheelView day, int y, int m,
			int d) {

		year.setCurrentItem(100 - (calendar.get(Calendar.YEAR) - y));
		month.setCurrentItem(m - 1);
		day.setCurrentItem(d - 1);
	}

	/**
	 * Adapter for numeric wheels. Highlights the current value.
	 */
	private class DateNumericAdapter extends NumericWheelAdapter {
		// Index of current item
		int currentItem;
		// Index of item to be highlighted
		int currentValue;

		/**
		 * Constructor
		 */
		public DateNumericAdapter(Context context, int minValue, int maxValue,
				int current) {
			super(context, minValue, maxValue);
			this.currentValue = current;
			setTextSize(20);
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			if (currentItem == currentValue) {
				// view.setTextColor(0xFF0000F0);// 文字颜色
			}
			view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
	}

	/**
	 * Adapter for string based wheel. Highlights the current value.
	 */
	private class DateArrayAdapter extends ArrayWheelAdapter<String> {
		// Index of current item
		int currentItem;
		// Index of item to be highlighted
		int currentValue;

		/**
		 * Constructor
		 */
		public DateArrayAdapter(Context context, String[] items, int current) {
			super(context, items);
			this.currentValue = current;
			setTextSize(20);
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			if (currentItem == currentValue) {
				// view.setTextColor(0xFF0000F0); // 文字颜色
			}
			view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
	}

	public File byteToFile(byte[] bytes) {
		File file = null;
		String path = Environment.getExternalStorageDirectory().getPath()
				+ File.separator + "header.PNG";
		BufferedOutputStream stream = null;
		try {
			file = new File(path);
			FileOutputStream fstream = new FileOutputStream(file);
			stream = new BufferedOutputStream(fstream);
			stream.write(bytes);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return file;
	}

	class uphoto implements Runnable {
		Bitmap bit;
		String path;
		StringBuffer data;

		public uphoto(Bitmap bit, String path, StringBuffer data) {
			this.bit = bit;
			this.path = path;
			this.data = data;
		}

		public void run() {
			// ByteArrayOutputStream os = new ByteArrayOutputStream();
			// bit.compress(CompressFormat.PNG, 100, os);
			// byte[] bytes = os.toByteArray();
			Message message = new Message();
			// File file = byteToFile(bytes);
			// byte[] bt = tul.upload(ConstantsJrc.PICUPQIANG, data.toString(),
			// file.getAbsolutePath().toString());
			// String ss = new String(bt);
			// String tip = null;
			String ss = "", tip = "";
			String strUrl = "uid=" + uid;
			if (changepo != 6) {
				strUrl += "&picid=" + picid;
			}
			String smallpic = "", bigpic = "";
			int picid;

			File file = new File(path);
			if (file.exists()) {
				byte[] by = FileUpload.getInstance().upload(
						ConstantsJrc.PICUPQIANG, strUrl, "header", path);
				// Log.i("ttt", by.toString());
				ss = new String(by);
			}

			try {
				JSONObject json = new JSONObject(ss);
				int ret = json.optInt("ret", -1);
				tip = URLDecoder.decode(json.optString("tip"));
				smallpic = URLDecoder.decode(json.optString("pic1"));
				bigpic = URLDecoder.decode(json.optString("pic2"));
				picid = json.optInt("picid", -1);

				if (ret == 1) {
					message.what = 1;
					Bundle bd = new Bundle();
					bd.putString("tip", tip);
					bd.putString("smallpic", smallpic);
					bd.putString("bigpic", bigpic);
					bd.putInt("picid", picid);
					message.setData(bd);
					// message.obj = smallpic;
					upphoto.sendMessage(message);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				message.what = 2;
				message.obj = smallpic;
				upphoto.sendMessage(message);
			}

		}
	}

	Handler upphoto = new Handler() {
		public void handleMessage(Message msg) {
			// String smallpic = msg.obj.toString();
			Bundle bd = msg.getData();
			ImageView ivk;
			String tip = bd.getString("tip");
			String smallpic = bd.getString("smallpic");
			String bigpic = bd.getString("bigpic");
			int picid = bd.getInt("picid");
			if (changepo != 6) {
				photopaths.set(changepo, bigpic);
			} else {
				photopaths.add(bigpic);
			}
			if (msg.what == 1) {
				if (changepo == 6) {
					View itemView = LayoutInflater.from(
							VillageUserInfoDialog.this).inflate(
							R.layout.photoview, null);
					ImageView iv1 = (ImageView) itemView
							.findViewById(R.id.photoview_iv);
					ivk = (ImageView) itemView.findViewById(R.id.photoview_k);
					rl = (RelativeLayout) itemView
							.findViewById(R.id.photoview_rl);
					itemView.setTag(smallpic);
					ivk.setTag(smallpic);
					iv1.setTag(smallpic);
					ll.addView(itemView);
					iv1s.add(iv1);
					ivks.add(ivk);
					LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) rl
							.getLayoutParams();
					linearParams.width = dm.widthPixels * 1 / 6;
					linearParams.height = linearParams.width;
					linearParams.weight = 1;
					linearParams.leftMargin = linearParams.width / 2 / 7;
					if (photopaths.size() == 5) {
						RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) ll
								.getLayoutParams();
						llParams.height = dm.widthPixels * 1 / 6;
						llParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

						ll.setLayoutParams(llParams);
					} else {
						RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) ll
								.getLayoutParams();
						llParams.height = dm.widthPixels * 1 / 6;
						ll.setLayoutParams(llParams);
					}
					itemView.setLayoutParams(linearParams);
					iv1.setImageBitmap(smallbmp);
				} else {
					iv1s.get(changepo).setImageBitmap(smallbmp);
					ivk = ivks.get(changepo);
					ivk.setTag(smallpic);
				}

				if (changepo != 6) {
					pit = new PicphotoBean(picid, smallpic, bigpic);
					picphotos.set(changepo, pit);
				} else {
					pit = new PicphotoBean(picid, smallpic, bigpic);
					picphotos.add(pit);
				}

				if (picphotos.size() >= 5) {
					iv.setVisibility(View.GONE);
				}

				villageuserinfo_progressbarRelativeLayout
						.setVisibility(View.GONE);

				ivk.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						for (int i = 0; i < picphotos.size(); i++) {
							if (v.getTag().equals(
									picphotos.get(i).getPicsmall())) {
								if (uid.equals(fuid)) {
									Intent it = new Intent();
									it.setClass(VillageUserInfoDialog.this,
											DeleteOrBorwser.class);
									it.putExtra("tag", v.getTag().toString());
									it.putExtra("picid", picphotos.get(i)
											.getPicid());
									it.putExtra("po", i);
									it.putExtra("uid", uid);
									it.putExtra("fuid", fuid);
									startActivityForResult(it, 3);
								} else {
									Intent intent = new Intent();
									intent.setClass(VillageUserInfoDialog.this,
											Chakandatu.class);
									intent.putStringArrayListExtra("photos",
											photopaths);
									intent.putIntegerArrayListExtra("picids",
											picids);
									intent.putExtra("po", i);
									intent.putExtra("uid", uid);
									intent.putExtra("picid", picphotos.get(i)
											.getPicid());
									intent.putExtra("po", i);
									startActivity(intent);
								}
							}
						}
					}
				});
				Commond.showToast(VillageUserInfoDialog.this, tip);
			} else if (msg.what == 2) {
				villageuserinfo_progressbarRelativeLayout
						.setVisibility(View.GONE);
				Commond.showToast(VillageUserInfoDialog.this, "添加失败！");
				scpicpath.remove(smallpic);
			}
			super.handleMessage(msg);
		}
	};
	Handler loadphotohd = new Handler() {
		public void handleMessage(Message msg) {

			ImageView iv = (ImageView) msg.obj;
			Bundle data = msg.getData();
			if (data != null) {
				String url = data.getString("url");
				String filename = data.getString("filename");

				if (picphotos.size() == 5) {
					RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) ll
							.getLayoutParams();
					llParams.height = dm.widthPixels * 1 / 6;
					llParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

					ll.setLayoutParams(llParams);
				} else {
					RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) ll
							.getLayoutParams();
					llParams.height = dm.widthPixels * 1 / 6;
					ll.setLayoutParams(llParams);
				}
				Bitmap bmp = BitmapCacheDzlb.getIntance().getCacheBitmap(
						filename, 0, 0);
				iv.setImageBitmap(bmp);

			}
		}
	};

	public class LoadImageRunnable implements Runnable {
		private Context mContext;
		private Handler mHandler;
		private String sUrl;
		private String mFilename;
		private ImageView iv, ivk;

		public LoadImageRunnable(Context context, Handler h, String str,
				String filename, ImageView iv, ImageView ivk) {
			mHandler = h;
			mContext = context;
			sUrl = str;
			mFilename = filename;
			this.iv = iv;
			this.ivk = ivk;
		}

		@Override
		public void run() {
			Comment.urlToFile(mContext, sUrl, mFilename);
			Message msg = new Message();
			msg.obj = iv;
			Bundle data = new Bundle();
			data.putString("url", sUrl);
			data.putString("filename", mFilename);
			msg.setData(data);
			loadphotohd.sendMessage(msg);
		}
	}

	public void deletePhoto(int picid) {
		String url = ConstantsJrc.QIANGDELETE + "?" + "uid=" + su.getUid()
				+ "&picid=" + picid + "&token=" + su.getToken();
		if (!dlpl.contains(url)) {
			loading.setVisibility(View.VISIBLE);
			StringBuffer data = new StringBuffer();
			HttpRequestTask request = new HttpRequestTask();
			request.execute(url, data.toString());
			dlpl.add(url);
		}
	}

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, ConstantsJrc.IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 255);
		intent.putExtra("outputY", 255);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, ConstantsJrc.PHOTORESOULT);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			if (view.getLastVisiblePosition() == view.getCount() - 1) {
				// Commond.showToast(VillageUserInfoDialog.this, "eeee");
				if (page == 0) {
					liwull.setVisibility(View.GONE);
					return;
				} else {
					if (!dlint.contains(page)) {
						liwull.setVisibility(View.VISIBLE);
						StringBuffer data = new StringBuffer();

						data.append("pkg=");
						data.append(URLEncoder.encode(getPackageName()));
						//
						// data.append("&pd=");
						// lastdate = currentdate;
						// data.append(URLEncoder.encode(lastdate));
						// 请求网络验证登陆
						String url3 = ConstantsJrc.GIFTLIST + "?" + "uid="
								+ fuid + "&cid="
								+ liwuTypeListAdapter.list.get(po).getId()
								+ "&page=" + page + "&src=" + src + "&token="
								+ su.getToken();
						StringBuffer data3 = new StringBuffer();
						// 请求网络验证登陆
						HttpRequestTask request3 = new HttpRequestTask();
						request3.execute(url3, data3.toString());
						isgridfoot = true;
						dlint.add(page);
					}
				}
			}
		}
	}
}
