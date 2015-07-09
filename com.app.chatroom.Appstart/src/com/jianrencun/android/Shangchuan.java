package com.jianrencun.android;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.chatroom.Appstart;
import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.imgzoom.ImageZoom;
import com.app.chatroom.util.Commond;
import com.duom.fjz.iteminfo.Adapterwithpic;
import com.duom.fjz.iteminfo.Iteminfo;
import com.jianrencun.chatroom.R;
import com.umeng.analytics.MobclickAgent;

public class Shangchuan extends HttpBaseActivity implements OnScrollListener {
	public static Shangchuan instance = null;
	private ViewPager mTabPager;
	private LinearLayout mTabImg;// 动画图片
	private ImageView Tab1, Tab2;
	private int zero = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int one;// 单个水平动画位移
	private LinearLayout loading, sctabnow;
	private View vFooter;
	int displayWidth2;
	private TextView huadao2;
	private ListView mysc;
	private List<BasicNameValuePair> vpdata = new ArrayList<BasicNameValuePair>();
	private int isfav;
	private EditText biaoti, neirong;
	private Button bnt, delete;
	private boolean scswitch = false;
	private Gallery gallery;
	private List<Bitmap> bits , smallbits;
	private LinearLayout rllt4, rllt5;
	public static final String IMAGE_UNSPECIFIED = "image/*";
	// / 用来判断 返回的状态。
	public static final int NONE = 0;
	public static final int PHOTOHRAPH = 1;// 拍照
	public static final int PHOTORESOULT = 3;// 结果
	String haomiao;
	private ProgressBar pbmysc;
	Bitmap  smallbmp;
	int id;
	int v1, v2;
	private String lsurl = "";
	OnClickListener listener;
	boolean isclick = false;
	private int kaiguan = 0;
	private int twice = 0;
	private boolean istwice = false;
	private Button bton;
	List<String> filenames = new ArrayList<String>();
	int n;
	private List<String> dlsc = new ArrayList<String>();
	String ss;
	private String flink, rlink, clink;
	int status;
	public static List<Iteminfo> mysclist = new ArrayList<Iteminfo>();
	private String lastdate, currentdate = "";
	public static String scnlink;
	Adapterwithpic ad;
	public static String sclbpd = "";
	List<String> scpicpath = new ArrayList<String>();
	public String murl;
	private ImageView ivnohave;
	// private UserGallery ugallery ;
	private ProgressBar scpb;
	int j;
	int posc;
	Bitmap bmp ,mBitmap;
	private boolean yon = false;
	int kk;
	private Thread th ;
	private String path;
	private Details dt= new Details(); 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.scgl);

		instance = this;
		huadao2 = (TextView) findViewById(R.id.huadao3);
		mTabPager = (ViewPager) findViewById(R.id.tabpager3);

		mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());
		Tab1 = (ImageView) findViewById(R.id.img_weixin3);

		Tab2 = (ImageView) findViewById(R.id.img_address3);
		delete = (Button) findViewById(R.id.scdelete);
		
		vFooter = LayoutInflater.from(this).inflate(R.layout.list_footer, null);
		loading = (LinearLayout) vFooter.findViewById(R.id.list_footer);
		sctabnow = (LinearLayout) findViewById(R.id.sctabnow);
		loading.setVisibility(View.GONE);
		
	
		mTabImg = (LinearLayout) findViewById(R.id.img_tab_now3);
		Tab1.setOnClickListener(new MyOnClickListener(0));
		Tab2.setOnClickListener(new MyOnClickListener(1));

		delete.setVisibility(View.GONE);

		Display currDisplay = getWindowManager().getDefaultDisplay();// 获取屏幕当前分辨率
		int displayWidth = (int) (currDisplay.getWidth() * 0.99);
		int displayHeight = currDisplay.getHeight();
		displayWidth2 = (int) (currDisplay.getWidth());
		one = displayWidth / 2; // 设置水平动画平移大小

		// 将要分页显示的View装入数组中
		LayoutInflater mLi = LayoutInflater.from(this);
		View view1 = mLi.inflate(R.layout.shangchuan, null);
		rllt5 = (LinearLayout) view1
				.findViewById(R.id.village_leftlist_progressbar_RelativeLayout5);
		scpb = (ProgressBar) view1.findViewById(R.id.scpb);
		bnt = (Button) view1.findViewById(R.id.scbnt);
		biaoti = (EditText) view1.findViewById(R.id.scbiaoti);
		neirong = (EditText) view1.findViewById(R.id.scneirong);
		gallery = (Gallery) view1.findViewById(R.id.scgallery);
		bits = new ArrayList<Bitmap>();
		smallbits = new ArrayList<Bitmap>();
		try {
			InputStream is = getResources().openRawResource(R.drawable.icon);
			mBitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		bits.add(mBitmap);
		smallbits.add(mBitmap);
		
		gallery.setAdapter(new ImageAdapter(Shangchuan.this, bits));

		biaoti.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					v.setBackgroundResource(R.drawable.scshurukpre);
					InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					inputMethodManager.toggleSoftInput(0,
							InputMethodManager.HIDE_NOT_ALWAYS);
				} else {
					v.setBackgroundResource(R.drawable.scshuruk1);
					InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					inputMethodManager.hideSoftInputFromWindow(
							biaoti.getWindowToken(), 0);
				}
			}
		});
		neirong.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					v.setBackgroundResource(R.drawable.scshurukpre);
					InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					inputMethodManager.toggleSoftInput(0,
							InputMethodManager.HIDE_NOT_ALWAYS);
				} else {
					v.setBackgroundResource(R.drawable.scshuruk1);
					InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					inputMethodManager.hideSoftInputFromWindow(
							biaoti.getWindowToken(), 0);
				}
			}
		});
		LayoutInflater mLi1 = LayoutInflater.from(this);
		View view2 = mLi1.inflate(R.layout.mysc, null);
		rllt4 = (LinearLayout) view2
				.findViewById(R.id.village_leftlist_progressbar_RelativeLayout4);
		mysc = (ListView) view2.findViewById(R.id.mysclist);
		pbmysc = (ProgressBar) view2.findViewById(R.id.progressmysc);
		ivnohave = (ImageView) view2.findViewById(R.id.myscpic);
		bnt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String result = "";
				if (TextUtils.isEmpty(biaoti.getText().toString())) {
					Commond.showToast(Shangchuan.this, "小贱提醒 ：亲，先输入标题哦！");
					biaoti.requestFocus();
					return;
				}				
				if (TextUtils.isEmpty(neirong.getText().toString())
						&& bits.size() == 1) {
					Commond.showToast(Shangchuan.this, "小贱提醒 ：内容 和 图片不能同时为空哦！");
					return;
				}
				bnt.setEnabled(false);
				scpb.setVisibility(View.VISIBLE);
				rllt5.setVisibility(View.VISIBLE);
				th = new Thread(new uThread(Shangchuan.this, mHandler)); th.start();
				sclbpd = "";
				yon = true;
			}
		});
		mysc.addFooterView(vFooter);
		mysc.setOnScrollListener(this);
		mysc.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				posc = position;
				if(mysclist == null || mysclist.size() == 0){
					Commond.showToast(Shangchuan.this, "登录超时！请重新登录！");
					return ;
				}
				Intent it = new Intent();
				it.putExtra("content", mysclist.get(position).getContent());
				it.putExtra("link", mysclist.get(position).getLink());
				it.putExtra("date", mysclist.get(position).getDate());
				it.putExtra("ccount", mysclist.get(position).getCriticism());
				it.putExtra("clink", mysclist.get(position).getClink());
				it.putExtra("rlink", mysclist.get(position).getRlink());
				it.putExtra("flink", mysclist.get(position).getFlink());
				it.putExtra("isfav", mysclist.get(position).getIsfav());
				it.putExtra("header", mysclist.get(position).getHeader());
				it.putExtra("po", position);
				it.putExtra("which", 33);
				Adapterwithpic.ViewHolder holder = (Adapterwithpic.ViewHolder) view
						.getTag();
				holder.content.setTextColor(Color.parseColor("#9d8f98"));
				holder.date.setTextColor(Color.parseColor("#b9b5b8"));
				it.setClass(Shangchuan.this, Details.class);
				startActivity(it);
			}
		});
		//
		Intent it = getIntent();
		kk = it.getIntExtra("tag", 0);

		// 每项删除按钮事件
		listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Adapterwithpic.ViewHolder holder = (Adapterwithpic.ViewHolder) v
						.getTag();
				String url = "";
				pbmysc.setVisibility(View.VISIBLE);
				rllt4.setVisibility(View.VISIBLE);
				if(mysclist.size() != 0 || mysclist != null){					
				 url = mysclist.get(holder.positiondele).getDlink();
				}else{
					finish();
				}
				lsurl = url;
				String urltwo = dt.appendNameValue(url, "pkg",
						getPackageName());
				urltwo = Details.geturl(urltwo);
				StringBuffer data = new StringBuffer();
				data.append("id=");
				data.append(mysclist.get(holder.positiondele).getId());
				// 请求网络验证登陆
				HttpRequestTask request = new HttpRequestTask();
				request.execute(urltwo, data.toString());
			}
		};

		// TODO Auto-generated method stub
		// pbmysc.setVisibility(View.VISIBLE);
		// String url
		// ="http://news.hutudan.com/3g/tool/item_client_del_json.php";
		// String urltwo = Details.appendNameValue(url, "pkg", "com.jianrencun.chatroom");
		// urltwo = Details.geturl(urltwo);
		// StringBuffer data = new StringBuffer();
		// data.append("id=");
		// data.append(v.getTag());
		// // 请求网络验证登陆
		// HttpRequestTask request = new HttpRequestTask();
		// request.execute(urltwo, data.toString());

		// // gallery 实现监听
		gallery.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == bits.size() - 1) {
					if (bits.size() >= 6) {
						Commond.showToast(Shangchuan.this, "亲，最多只能上传5张哦！");
						return;
					}
					Intent intent = new Intent(Intent.ACTION_PICK, null);
					intent.setClass(Shangchuan.this, Choosewhich.class);
					startActivityForResult(intent, 5);
					haomiao = String.valueOf(System.currentTimeMillis());

				} else if (position != bits.size() - 1) {
					try {
						bits.remove(position + 1);
						smallbits.remove(position + 1);
						scpicpath.remove(bits.size() - 1);
						gallery.setAdapter(new ImageAdapter(Shangchuan.this, smallbits));
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						finish();
					}
					

				}
			}
		});

		// 每个页面的view数据
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);

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

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(views.get(position));
				return views.get(position);
			}
		};

		mTabPager.setAdapter(mPagerAdapter);
		if (kk == 2) {

			mTabPager.setCurrentItem(0, true);
		} else if (kk == 1) {
			mTabImg.setVisibility(View.INVISIBLE);
			sctabnow.setVisibility(View.VISIBLE);
			mTabPager.setCurrentItem(1, true);
		}

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

	/*
	 * 页卡切换监听(原作者:D.Winter)
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				mTabImg.setVisibility(View.VISIBLE);
				sctabnow.setVisibility(View.INVISIBLE);
				bnt.setEnabled(true);
				delete.setVisibility(View.GONE);
				int[] location = new int[2];
				Tab1.getLocationOnScreen(location);
				int x = location[0];
				int y = location[1];
				v1 = Tab1.getLeft();
				if (scswitch == true) {
					scswitch = false;
					bton.setBackgroundResource(R.drawable.garbage1s);
					mysc.setAdapter(new Adapterwithpic(Shangchuan.this,
							mysclist, JianrencunActivity.number1, mysc,
							scswitch, listener));
				}
				// RelativeLayout.LayoutParams params =
				// (RelativeLayout.LayoutParams) mTabImg
				// .getLayoutParams();
				// params.setMargins(v1, 0, 0, 0);// 通过自定义坐标来放置你的控件
				// mTabImg.setLayoutParams(params);

				kaiguan = 0;
				Tab1.setImageDrawable(getResources().getDrawable(
						R.drawable.bjscsel));
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
					Tab2.setImageDrawable(getResources().getDrawable(
							R.drawable.mysc));
				}
				currIndex = arg0;
				// animation.setFillAfter(true);// True:图片停在动画结束位置
				// animation.setDuration(150);
				// mTabImg.startAnimation(animation);
				break;
			case 1:
				// if(kk == 3){
				mTabImg.setVisibility(View.INVISIBLE);
				sctabnow.setVisibility(View.VISIBLE);
				// }
				bnt.setEnabled(false);
				delete.setVisibility(View.VISIBLE);
				v2 = Tab2.getLeft();
				biaoti.clearFocus();
				neirong.clearFocus();

				Tab2.setImageDrawable(getResources().getDrawable(
						R.drawable.myscsel));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, one, 0, 0);
					Tab1.setImageDrawable(getResources().getDrawable(
							R.drawable.bjsc));
				}
				String url = MainMenuActivity.myshangchuanlb;
				kaiguan = 1;

				if (istwice == false) {
					mysclist.clear();
					ivnohave.setVisibility(View.GONE);
					istwice = true;
					pbmysc.setVisibility(View.VISIBLE);
					rllt4.setVisibility(View.VISIBLE);
					String url2 = dt.appendNameValue(url, "pd", sclbpd);
					post(url2);
				}
				currIndex = arg0;
				// animation.setFillAfter(true);// True:图片停在动画结束位置
				// animation.setDuration(150);
				// mTabImg.startAnimation(animation);
				// RelativeLayout.LayoutParams params1 =
				// (RelativeLayout.LayoutParams) mTabImg
				// .getLayoutParams();
				// params1.setMargins(v2, 0, 0, 0);// 通过自定义坐标来放置你的控件
				// mTabImg.setLayoutParams(params1);
				break;
			}

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	// /返回的结果处理
	@SuppressLint("NewApi")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == NONE)
			return;
		if (resultCode == 50) {
			int index = data.getIntExtra("index", 50);
			if (index == 33) {
				// Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
				// getImage.addCategory(Intent.CATEGORY_OPENABLE);
				// getImage.setType("image/*");
				// startActivityForResult(getImage, 2);

				Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
				intent.setDataAndType(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						ConstantsJrc.IMAGE_UNSPECIFIED);
				startActivityForResult(intent, 2);
				

			} else if (index == 44) {
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
			scpicpath.add(sspath);
			bmp = getbp(sspath, 200);
			smallbmp = getbp(sspath, 100 / 3 * 2);
			bits.add(bmp);
			smallbits.add(smallbmp);
			// 拍完后，重新查询数据库，加载适配器。将照的相片显示出来
			gallery.setAdapter(new ImageAdapter(this, smallbits));
			gallery.setSelection(smallbits.size() - 2);
		}
		if (data == null)
			return;

		if (requestCode == 2) {
			
			// 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
//			ContentResolver resolver = getContentResolver();
//			try {
//				Uri originalUri = data.getData(); // 获得图片的uri
//				// bm = MediaStore.Images.Media.getBitmap(resolver,
//				// originalUri); // 显得到bitmap图片
//				String[] proj = { MediaStore.Images.Media.DATA };
//				CursorLoader loader = new CursorLoader(Shangchuan.this, originalUri, proj, null, null, null);
//			    Cursor cursor = loader.loadInBackground();
//			    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//			    cursor.moveToFirst();

				// 将光标移至开头
//				cursor.moveToFirst();
				
				ContentResolver resolver = getContentResolver();
				Uri uri = data.getData();
				try
				{
				String[] filePathColumn = {MediaStore.Images.Media.DATA};
				Cursor cursor = resolver.query(uri, filePathColumn, null, null, null);
				if (null != cursor)
				{
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//				String picturePath = cursor.getString(columnIndex);
				
				// 最后根据索引值获取图片路径
			    path = cursor.getString(columnIndex);
			    cursor.close();
			    System.out.println("图片的路径和名字–>" + path);
				}else{
					path = uri.getPath().toString();
				}
				
				if (scpicpath.contains(path)) {
					Commond.showToast(Shangchuan.this, "亲，该图片已经存在，请重新选择！");
					return;
				}
				///////////////////////////////////////////////////////////////////////////////
				Intent i = new Intent(this, ImageZoom.class);
				i.putExtra("imageurl", path);
				i.putExtra("izhl", 2);			
				i.putExtra("savepath", Appstart.jrcsave.getPath());
				i.putExtra("downpath", Appstart.jrcsave.getPath());
				startActivityForResult(i, 7); 
			} catch (Exception e) {
				e.printStackTrace();
				Commond.showToast(Shangchuan.this, "添加失败，请重新添加！");
			}
			super.onActivityResult(requestCode, resultCode, data);
		}
		if(resultCode == 77){
			Bitmap bm = null;
			scpicpath.add(path);
			// suo fang tupian
			// if (bm.getWidth() >= bm.getHeight() &&
			// (bm.getWidth()/3*2)>320) {
			// bm = getbp(path, 300/3*2);
			// } else if (bm.getWidth() < bm.getHeight() &&
			// (bm.getWidth()/3*2)>320) {
			// bm = getbp(path, 300/3*2);
			// }
			bm = getbp(path,200);
			smallbmp = getbp(path, 100 / 3 * 2);
			bits.add(bm);
			smallbits.add(smallbmp);
			gallery.setAdapter(new ImageAdapter(this, smallbits));
			gallery.setSelection(smallbits.size() - 2);
		}
	}

	public void scgl_back(View v) { // 小黑 对话界面
		finish();
	}

	public void mysc_garbage(View v) {
		bton = (Button) v;
		if (scswitch == false) {
			scswitch = true;
			v.setBackgroundResource(R.drawable.canclegarbage1);
			mysc.setAdapter(new Adapterwithpic(Shangchuan.this, mysclist,
					JianrencunActivity.number1, mysc, scswitch, listener));
		} else {
			scswitch = false;
			v.setBackgroundResource(R.drawable.garbage1s);
			mysc.setAdapter(new Adapterwithpic(Shangchuan.this, mysclist,
					JianrencunActivity.number1, mysc, scswitch, listener));
		}
	}

	// /适配器
	private final class ImageAdapter extends BaseAdapter {
		private Context mContext;
		private List<Bitmap> mBitmap;

		public ImageAdapter(Context context, List<Bitmap> mBitmap) {
			this.mContext = context;
			this.mBitmap = mBitmap;
		}

		public int getCount() {
			return mBitmap.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView, iv2;
			View view = null;
			// 自定义的View ， 来实现特殊的相册显示效果
			if (position != bits.size() - 1) {
				if(mBitmap.size() != 1){
				view = getLayoutInflater().inflate(R.layout.tjxiangpian, null);
				imageView = (ImageView) view.findViewById(R.id.sciv);				
				Drawable draw = new BitmapDrawable(mBitmap.get(position + 1));
				imageView.setImageDrawable(draw);
				}else{
					Commond.showToast(Shangchuan.this, "出问题了！请联系我们，谢谢!");
				}
			} else {
				view = getLayoutInflater().inflate(R.layout.kong, null);
			}
			// TextView tv =(TextView)view.findViewById(R.id.xiangcetv);
			return view;
		}
	}

	// ////////处理图片的方法，，按照图片的一定比例来缩小，解决内存溢出的办法。
	public static Bitmap getBitmap(String path, float height) {
		try {
			Bitmap bmp = null ;
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			bmp = BitmapFactory.decodeFile(path, options); // 此时返回bm为空
			options.inJustDecodeBounds = false;
			// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
			int be = (int) (options.outHeight / (float) height);
			if (be <= 0)
				be = 1;
			options.inSampleSize = be;
			// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
			bmp = BitmapFactory.decodeFile(path, options);

			return bmp;

		} catch (OutOfMemoryError error) {
			error.printStackTrace();
		}
		return null;
	}

	// ////////处理图片的方法，，按照图片的一定比例来缩小，解决内存溢出的办法。
	public static Bitmap getbp(String path, float weight) {
		try {
			Bitmap bmp = null ;
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			bmp = BitmapFactory.decodeFile(path, options); // 此时返回bm为空
			options.inJustDecodeBounds = false;
			// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
			int be = (int) (options.outWidth / (float) weight);
			if (be <= 0)
				be = 1;
			options.inSampleSize = be;
			// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
			bmp = BitmapFactory.decodeFile(path, options);
			return bmp;
		} catch (OutOfMemoryError error) {
			error.printStackTrace();
		}
		return null;
	}

	// 缩放图片
	public static Bitmap getSmallBitmap(Bitmap bitmap) {
		int nHeight = 154;
		int nWidth = 154;
		int width = bitmap.getWidth();
		int heightofpicture = bitmap.getHeight();
		float scaleWidth = ((float) nWidth) / width;
		float scaleHeight = ((float) nHeight) / heightofpicture;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap temp = Bitmap.createBitmap(bitmap, 0, 0, width, heightofpicture,
				matrix, true);
		return temp;
	}

	public File byteToFile(byte[] bytes) {
		File file = null;
		n++;
		String path = Environment.getExternalStorageDirectory().toString()
				+ "/" + n + "sc.jpg";
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

	public static String doPostUpload(String url,
			List<BasicNameValuePair> datas, List<String> files) {
		try {
			// 组装提交信息
			MultipartEntity reqEntity = new MultipartEntity();
			for (BasicNameValuePair data : datas) {
				reqEntity.addPart(
						data.getName(),
						new StringBody(data.getValue(), "text/plain", Charset
								.forName("UTF-8")));
			}
			int i = 0;
			for (String file : files) {
				reqEntity.addPart("file" + i, new FileBody(new File(file)));
				i++;
			}
			// 设置提交信息
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(reqEntity);
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httppost);

			// 若状态码为200 ok
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 取出回应字串
				String strResult = EntityUtils.toString(httpResponse
						.getEntity());
				System.out.println("doPostJson response[" + url + "]: \n"
						+ strResult);
				return strResult;
			} else {
				System.out.println("doPost Error Response[" + url + "]: \n"
						+ httpResponse.getStatusLine().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			if (data.getString("over").equalsIgnoreCase("yes")) {
				try {
					JSONObject json = new JSONObject(ss);
					int ret = json.optInt("ret", -1);
					String tip = URLDecoder.decode(json.optString("tip"));
					if (ret == 1) {
						// tip = "上传成功!";
					} else {
						// tip = "上传请求失败,请重试!";
					}
					n = 0;
			           if(filenames == null){
			        	   finish();
			        	   return ;
			           }
					filenames.clear();
					bits.clear();
					dlsc.clear();
					bits.add(mBitmap);
					smallbits.clear();
					smallbits.add(mBitmap);
					gallery.setAdapter(new ImageAdapter(Shangchuan.this, bits));
					biaoti.setText("");
					biaoti.clearFocus();
					neirong.setText("");
					neirong.clearFocus();
					istwice = false;
					Commond.showToast(Shangchuan.this, tip);
					scpb.setVisibility(View.GONE);
					rllt5.setVisibility(View.GONE);
					scpicpath.clear();
					bnt.setEnabled(true);
				} catch (Exception ex) {
					Commond.showToast(Shangchuan.this, "上传失败！请重新上传！");
					scpb.setVisibility(View.GONE);
					rllt5.setVisibility(View.GONE);
					bnt.setEnabled(true);
			           if(filenames == null){
			        	   finish();
			        	   return ;
			           }
					filenames.clear();
					ex.printStackTrace();
				}
			}
		}
	};

	class uThread implements Runnable {
		public Handler mHandler;
		String ex;

		public uThread(Context context, Handler h) {
			mHandler = h;
		}

		@Override
		public void run() {

			String url = MainMenuActivity.shangchuanyh;
			url = dt.appendNameValue(url, "pkg",getPackageName());
			url = Details.geturl(url);
           if(filenames == null){
        	   finish();
        	   return ;
           }
			for (int i = 1; i < bits.size(); i++) {
				FileInputStream fios;
				try {
					fios = new FileInputStream(scpicpath.get(i - 1));
					ex = "." + Commond.getFileType(fios);
					fios.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (ex.equalsIgnoreCase(".gif")) {
			           if(filenames == null){
			        	   finish();
			        	   return ;
			           }
					filenames.add(scpicpath.get(i - 1));
				} else {
					ByteArrayOutputStream os = new ByteArrayOutputStream();
					if(bits.get(i) == null ){
						 finish();
						return ;
					}
					bits.get(i).compress(CompressFormat.JPEG, 100, os);
					byte[] bytes = os.toByteArray();
					File file = byteToFile(bytes);		
			           if(filenames == null){
			        	   finish();
			        	   return ;
			           }
					filenames.add(file.getAbsolutePath().toString());
					}
				
			}
			bits.clear();
			String bt = URLEncoder.encode(biaoti.getText().toString());
			String nr = URLEncoder.encode(neirong.getText().toString());
			vpdata.add(new BasicNameValuePair("title", bt));
			vpdata.add(new BasicNameValuePair("content", nr));
			ss = doPostUpload(url, vpdata, filenames);
			// process incoming messages here
			Message msg = new Message();
			Bundle data = new Bundle();
			data.putString("over", "yes");
			msg.setData(data);
			mHandler.sendMessage(msg);
		};
	}

	/**
	 * 
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent e) {
		if (e.getAction() != MotionEvent.ACTION_MOVE) {
			return super.dispatchTouchEvent(e);
		}
		Rect r = new Rect();
		gallery.getGlobalVisibleRect(r);
		if (r.contains((int) e.getX(), (int) e.getY())) {
			// 注意 ：下面这行代码 只能实现横向 滑动 ，但有点 手机 比如moto手机 不能点击 图片看大图
			// return adview.dispatchTouchEvent(e);
			mTabPager.requestDisallowInterceptTouchEvent(true);
		} else
			mTabPager.requestDisallowInterceptTouchEvent(false);
		return super.dispatchTouchEvent(e);
	}

	public void post(String urllink) {
		String result = "";
		mysclist.clear();
		String url = dt.appendNameValue(urllink, "pkg",
				getPackageName());
		url = Details.geturl(url);
		murl = url;
		StringBuffer data = new StringBuffer();
		// 请求网络验证登陆
		HttpRequestTask request = new HttpRequestTask();
		request.execute(url, data.toString());
	}

	@Override
	public void resultData(String url, String result) {
		// TODO Auto-generated method stub
		String tip = null;
                  
		if (result == null) {
			// fresh.setVisibility(View.VISIBLE);
			Commond.showToast(Shangchuan.this, "小贱提醒 ：当前网络不稳定！");
			return;
		}
		try {
			JSONObject jsonChannel = new JSONObject(result);
			tip = URLDecoder.decode(jsonChannel.optString("tip"));
			String pd = URLDecoder.decode(jsonChannel.optString("pd"));

			scnlink = URLDecoder.decode(jsonChannel.optString("nlink"));

			int ret = jsonChannel.optInt("ret");
			if (ret == 0) {
				Commond.showToast(Shangchuan.this, tip);
				return;
			}

			JSONArray jsonItems = jsonChannel.optJSONArray("items");
			if (jsonItems != null && jsonItems.length() > 0) {
				save(Adapterwithpic.getMd5Hash(url), result.toString()
						.getBytes(), this);
//				sclbpd = pd;
				// tip = "获取成功！";
				for (int i = 0; i < jsonItems.length(); i++) {
					JSONObject jsonItem = jsonItems.getJSONObject(i);
					String title = URLDecoder.decode(jsonItem
							.optString("title"));
					String link = URLDecoder.decode(jsonItem.optString("link"));

					String fcount = URLDecoder.decode(jsonItem
							.optString("fcount"));
					String date = URLDecoder.decode(jsonItem.optString("date"));
					String ccount = URLDecoder.decode(jsonItem
							.optString("ccount"));
					String dcount = URLDecoder.decode(jsonItem
							.optString("dcount"));
					String ucount = URLDecoder.decode(jsonItem
							.optString("ucount"));
					String thumbnail = URLDecoder.decode(jsonItem
							.optString("thumbnail"));
					String dlink = URLDecoder.decode(jsonItem
							.optString("dlink"));
					status = jsonItem.optInt("status");
					isfav = jsonItem.optInt("isfav");
					id = jsonItem.optInt("id");
					flink = URLDecoder.decode(jsonItem.optString("flink"));
					rlink = URLDecoder.decode(jsonItem.optString("rlink"));
					clink = URLDecoder.decode(jsonItem.optString("clink"));
					Iteminfo item = new Iteminfo(title, date, thumbnail,
							ccount, dcount, ucount, fcount, link, flink, rlink,
							clink, isfav, status, id, dlink);
					mysclist.add(item);
				}
				      
				
				if (twice == 0) {      
						ad = new Adapterwithpic(Shangchuan.this, mysclist,
								JianrencunActivity.number1, mysc, scswitch, listener);
						mysc.setAdapter(ad);										
					pbmysc.setVisibility(View.GONE);
					rllt4.setVisibility(View.GONE);
					ivnohave.setVisibility(View.GONE);
					loading.setVisibility(View.GONE);
					// fresh.setVisibility(View.GONE);
				} else if (twice == 3) {
					mysc.requestLayout();
					ad.notifyDataSetChanged();	
					rllt4.setVisibility(View.GONE);
					loading.setVisibility(View.GONE);
				}
			} else {
				if ( TextUtils.isEmpty(sclbpd)) {
					if(mysclist.size() == 0 || mysclist == null){
					pbmysc.setVisibility(View.GONE);
					rllt4.setVisibility(View.GONE);
					// Commond.showToast(Shangchuan.this, "亲，您还没有上传哦！");
					ad = new Adapterwithpic(Shangchuan.this, mysclist,
							JianrencunActivity.number1, mysc, scswitch, listener);
					mysc.setAdapter(ad);
					try {											
					if(vFooter != null){
						mysc.removeFooterView(vFooter);
					}
					} catch (Exception e) {
						// TODO: handle exception
					}
					ivnohave.setVisibility(View.VISIBLE);
					return;
					}
				}
				sclbpd = pd;
				String ssdfe = lsurl;
				if (url.contains(lsurl) && !TextUtils.isEmpty(lsurl)) {
					String u = MainMenuActivity.myshangchuanlb;
					String url1 = dt.appendNameValue(u, "pd", "");
					mysclist.clear();
					post(url1);
					twice = 0 ;
					
					Commond.showToast(Shangchuan.this, tip);
				} 
				if(mysc!= null && vFooter != null){
//					mysc.removeFooterView(vFooter);
					loading.setVisibility(View.GONE);
				}
//				else {
//					String url1 = MainMenuActivity.myshangchuanlb;
//					String url2 = Details.appendNameValue(url1, "pd", "");
//					url2 = Details.appendNameValue(url2, "pkg",
//							MainMenuActivity.pakName);
//					url2 = Details.geturl(url2);
//					File f = new File(MainMenuActivity.p
//							+ Adapterwithpic.getMd5Hash(url2));
//					if (f.exists() && yon == false) {
//						String ss = load(Adapterwithpic.getMd5Hash(url2),
//								Shangchuan.this);
//						if (TextUtils.isEmpty(ss)) {
//							pbmysc.setVisibility(View.GONE);
//							rllt4.setVisibility(View.GONE);
//							// Commond.showToast(Shangchuan.this, "亲，您还没有上传哦！");
//							ivnohave.setVisibility(View.VISIBLE);
//						}
//						getjson(ss);
//					}
//				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			if (view.getLastVisiblePosition() == view.getCount() - 1) {
				if (TextUtils.isEmpty(scnlink)) {
					mysc.removeFooterView(vFooter);
					return;
				} else {
					twice = 3;
					if (!dlsc.contains(scnlink)) {
						loading.setVisibility(View.VISIBLE);
						String url = scnlink;
						StringBuffer data = new StringBuffer();

						data.append("pkg=");
						data.append(URLEncoder.encode(getPackageName()));
						//
						data.append("&pd=");
//						lastdate = currentdate;
						data.append(URLEncoder.encode(currentdate));
						String nlink2 = Details.geturl(url);
//						File f = new File(MainMenuActivity.p
//								+ Adapterwithpic.getMd5Hash(nlink2));
//						if (f.exists() && yon == false) {
//							String ss = load(Adapterwithpic.getMd5Hash(nlink2),
//									Shangchuan.this);
//							getjson(ss);
//						} else {
							// 请求网络验证登陆
							HttpRequestTask request = new HttpRequestTask();
							request.execute(nlink2, data.toString());
							dlsc.add(scnlink);
//						}
					}
				}
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (JianrencunActivity.yesorno == true) {
			JianrencunActivity.yesorno = false;
			if(ad !=null){
				mysc.requestLayout();
			ad.notifyDataSetChanged();
			}
		}
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	public void save(String filename, byte[] buffer, Context context) {
		try {
			FileOutputStream outStream = context.openFileOutput(filename,
					Context.MODE_WORLD_READABLE);
			outStream.write(buffer);
			outStream.close();
		} catch (FileNotFoundException e) {
			return;
		} catch (IOException e) {
			return;
		}
	}

	public String load(String filename, Context context) {
		try {
			FileInputStream inStream = context.openFileInput(filename);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length = -1;
			while ((length = inStream.read(buffer)) != -1) {
				stream.write(buffer, 0, length);
			}
			stream.close();
			inStream.close();
			// text.setText(stream.toString());
			return stream.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	public void getjson(String ss) {
		try {
			JSONObject jsonChannel2;
			jsonChannel2 = new JSONObject(ss);
			String tip = URLDecoder.decode(jsonChannel2.optString("tip"));
			String pd2 = URLDecoder.decode(jsonChannel2.optString("pd"));
			// sclbpd = pd ;
			scnlink = URLDecoder.decode(jsonChannel2.optString("nlink"));

			JSONArray jsonItems2 = jsonChannel2.optJSONArray("items");
			if (jsonItems2 != null && jsonItems2.length() > 0) {
				// tip = "获取成功！";
				for (int i = 0; i < jsonItems2.length(); i++) {
					JSONObject jsonItem = jsonItems2.getJSONObject(i);
					String title = URLDecoder.decode(jsonItem
							.optString("title"));
					String link = URLDecoder.decode(jsonItem.optString("link"));

					String fcount = URLDecoder.decode(jsonItem
							.optString("fcount"));
					String date = URLDecoder.decode(jsonItem.optString("date"));
					String ccount = URLDecoder.decode(jsonItem
							.optString("ccount"));
					String dcount = URLDecoder.decode(jsonItem
							.optString("dcount"));
					String ucount = URLDecoder.decode(jsonItem
							.optString("ucount"));
					String thumbnail = URLDecoder.decode(jsonItem
							.optString("thumbnail"));
					status = jsonItem.optInt("status");

					isfav = jsonItem.optInt("isfav");
					id = jsonItem.optInt("id");
					flink = URLDecoder.decode(jsonItem.optString("flink"));
					rlink = URLDecoder.decode(jsonItem.optString("rlink"));
					clink = URLDecoder.decode(jsonItem.optString("clink"));
					String dlink = URLDecoder.decode(jsonItem
							.optString("dlink"));
					Iteminfo item = new Iteminfo(title, date, thumbnail,
							ccount, dcount, ucount, fcount, link, flink, rlink,
							clink, isfav, status, id, dlink);
					mysclist.add(item);
				}
				ad = new Adapterwithpic(Shangchuan.this, mysclist,
						JianrencunActivity.number1, mysc, scswitch, listener);
				if (twice == 0) {
					mysc.setAdapter(ad);
					pbmysc.setVisibility(View.GONE);
					rllt4.setVisibility(View.GONE);
					// fresh.setVisibility(View.GONE);
				} else if (twice == 3) {
					pbmysc.setVisibility(View.GONE);
					rllt4.setVisibility(View.GONE);
					mysc.requestLayout();
					ad.notifyDataSetChanged(); // 数据集变化后,通知adapter
					loading.setVisibility(View.GONE);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			pbmysc.setVisibility(View.GONE);
			rllt4.setVisibility(View.GONE);
			loading.setVisibility(View.GONE);
			Commond.showToast(Shangchuan.this, "操作失败！请检查网络!");
			e.printStackTrace();
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(th!=null){
			th.interrupt();
			th = null ;
		}
		vpdata.clear();
		vpdata = null ;
		bits.clear() ; bits = null ;
		smallbits.clear(); smallbits = null ;
		 filenames.clear();	filenames = null;
	   dlsc.clear();		dlsc = null ;
	
   scpicpath.clear(); scpicpath = null ;
		super.onDestroy();
	}
}
