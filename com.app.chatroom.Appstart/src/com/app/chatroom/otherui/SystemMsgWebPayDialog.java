//package com.app.chatroom.otherui;
//
//import mm.purchasesdk.OnPurchaseListener;
//import mm.purchasesdk.Purchase;
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.Window;
//import android.webkit.WebChromeClient;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
//
//import com.app.chatroom.uppay.IAPHandler;
//import com.app.chatroom.uppay.IAPListener;
//import com.app.chatroom.uppay.PayResultReceiver;
//import com.app.chatroom.util.Commond;
//
//import com.jianrencun.chatroom.R;
//
//public class SystemMsgWebPayDialog extends Activity {
//	ImageButton closeBtn;
//	WebView wv;
//	String link = "";
//	String type = "";
//	int roomtype = 0;
//	ProgressBar progressBar;
//	RelativeLayout progressBarRelativeLayout;
//	private PayResultReceiver receiver;
//	private static boolean registerReceiver = true;
//	GotoBackJSInterface gotobackjs;
//	ImageView system_msg_dialog_title;
//	boolean isload = false;
//
//	// /////////////////////////////////////////////////////
//	public static final int ITEM0 = Menu.FIRST;// 系统值
//	private final String TAG = "Demo";
//
//	public static Purchase purchase;
//	public Context context;
//	public ProgressDialog mProgressDialog;
//
//	public IAPListener mListener;
//	public IAPListener mListener2;
//
//	// 计费信息
//	private static final String APPID = "300002826067";
//	private static final String APPKEY = "09E83CF9EB12D86A";
//	// 计费点信息
//	public static String LEASE_PAYCODE = "";
//
//	public static String mPaycode;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		/** 全屏设置，隐藏窗口所有装饰 */
//		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		setContentView(R.layout.system_msg_dialog);
//		Intent intent = getIntent();
//		link = intent.getStringExtra("link");
//		type = intent.getStringExtra("type");
//		roomtype = intent.getIntExtra("roomtype", 0);
//		closeBtn = (ImageButton) findViewById(R.id.system_msg_close_btn);
//		wv = (WebView) findViewById(R.id.system_msg_webView);
//		system_msg_dialog_title = (ImageView) findViewById(R.id.system_msg_dialog_title);
//
//		gotobackjs = new GotoBackJSInterface();
//		wv.addJavascriptInterface(gotobackjs, "gotobackinterface");
//		progressBar = (ProgressBar) findViewById(R.id.system_msg_progressbar);
//		progressBarRelativeLayout = (RelativeLayout) findViewById(R.id.system_msg_progressbar_RelativeLayout);
//
//		mProgressDialog = new ProgressDialog(SystemMsgWebPayDialog.this);
//		mProgressDialog.setIndeterminate(true);
//		mProgressDialog.setMessage("请稍候...");
//		context = SystemMsgWebPayDialog.this;
//		IAPHandler iapHandler = new IAPHandler(SystemMsgWebPayDialog.this);
//		mListener = new IAPListener(SystemMsgWebPayDialog.this, iapHandler);
//		// this.mListener2 = mListener;
//		/**
//		 * IAP组件初始化.包括下面3步。
//		 */
//		/**
//		 * step1.实例化PurchaseListener。实例化传入的参数与您实现PurchaseListener接口的对象有关。
//		 * 例如，此Demo代码中使用IAPListener继承PurchaseListener，其构造函数需要Context实例。
//		 */
//
//		/**
//		 * step2.获取Purchase实例。
//		 */
//		purchase = Purchase.getInstance();
//		/**
//		 * step3.向Purhase传入应用信息。APPID，APPKEY。 需要传入参数APPID，APPKEY。 APPID，见开发者文档
//		 * APPKEY，见开发者文档
//		 */
//		try {
//			purchase.setAppInfo(APPID, APPKEY);
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		/**
//		 * step4. IAP组件初始化开始， 参数PurchaseListener，初始化函数需传入step1时实例化的
//		 * PurchaseListener。
//		 */
//		try {
//			purchase.init(context, mListener);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		// 进度条实例化，为了返回销毁
//		switch (Integer.parseInt(type)) {
//		case 1:
//			system_msg_dialog_title
//					.setBackgroundResource(R.drawable.help_title);
//			break;
//
//		case 2:
//			system_msg_dialog_title
//					.setBackgroundResource(R.drawable.shop_title);
//			break;
//		case 3:
//			system_msg_dialog_title
//					.setBackgroundResource(R.drawable.other_title);
//			break;
//		case 4:
//			system_msg_dialog_title
//					.setBackgroundResource(R.drawable.huodong_title);
//			break;
//		case 5:
//			system_msg_dialog_title
//					.setBackgroundResource(R.drawable.xunzhang_title);
//			break;
//		case 6:
//			system_msg_dialog_title
//					.setBackgroundResource(R.drawable.userpwd_title);
//			break;
//		case 7:
//			if (roomtype == 1) {
//				system_msg_dialog_title
//						.setBackgroundResource(R.drawable.dating_title);
//			} else {
//				system_msg_dialog_title
//						.setBackgroundResource(R.drawable.house_title);
//			}
//			break;
//		case 13:
//			system_msg_dialog_title
//					.setBackgroundResource(R.drawable.userscore_title);
//			break;
//		case 14:
//			system_msg_dialog_title
//					.setBackgroundResource(R.drawable.usersign_title);
//			break;
//		case 99:
//			system_msg_dialog_title
//					.setBackgroundResource(R.drawable.village_sys_title);
//			break;
//		}
//		WebSettings webSettings = wv.getSettings();
//		webSettings.setJavaScriptEnabled(true);
//		wv.setBackgroundColor(0);
//		wv.loadUrl(link);
//		// wv.setFocusable(false);
//
//		wv.requestFocus();
//		wv.setWebChromeClient(new WebChromeClient() {
//
//			@Override
//			public void onProgressChanged(WebView view, int newProgress) {
//				// TODO Auto-generated method stub
//				if (newProgress == 100) {
//					progressBar.setVisibility(View.GONE);
//					progressBarRelativeLayout.setVisibility(View.GONE);
//					isload = true;
//				} else {
//					progressBar.setVisibility(View.VISIBLE);
//					progressBarRelativeLayout.setVisibility(View.VISIBLE);
//					isload = false;
//				}
//				super.onProgressChanged(view, newProgress);
//			}
//		});
//
//		wv.setWebViewClient(new WebViewClient() {
//
//			@Override
//			public boolean shouldOverrideUrlLoading(final WebView view,
//					final String url) {
//
//				if (!Commond.preUrl(SystemMsgWebPayDialog.this, url)) {
//					view.clearView();
//					view.loadUrl(url);
//				}
//				return true;
//			}
//			@Override
//			public void onReceivedError(WebView view, int errorCode,
//					String description, String failingUrl) {
//				// TODO Auto-generated method stub
//				super.onReceivedError(view, errorCode, description, failingUrl);
//				 wv.loadUrl("file:///android_asset/failed/failed.html");
//			}
//			// @Override
//			// public void onPageStarted(WebView view, String url, Bitmap
//			// favicon) {
//			// // TODO Auto-generated method stub
//			// if (!Commond.preUrl(SystemMsgWebPayDialog.this, url)) {
//			// view.clearView();
//			// return;
//			// }
//			// view.stopLoading();
//			// }
//		});
//
//		closeBtn.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if (!isload) {
//					finish();
//				} else {
//					wv.loadUrl("javascript:" + "if(window.gotobackinterface){"
//							+ "    window.gotobackinterface.gotoback(offset);"
//							+ "}gotoBack();");
//				}
//			}
//		});
//	}
//
//	public class GotoBackJSInterface {
//
//		public void gotoback(String offset) {
//			// System.out.println("offset:" + offset);
//			if ("0".equals(offset))
//				SystemMsgWebPayDialog.this.finish();
//		}
//	}
//
//	public void initPay(String paycode) {
//		LEASE_PAYCODE = paycode;
//		System.out.println("支付渠道：" + LEASE_PAYCODE);
//		mPaycode = LEASE_PAYCODE;
//		//
//		System.out.println(mListener + ",,,");
//		order(this, mListener);
//
//	}
//
//	public static void order(Context context, OnPurchaseListener listener) {
//		try {
//			purchase.order(context, mPaycode, listener);
//			// purchase.order(context, mPaycode, 10, listener);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void showProgressDialog() {
//		if (mProgressDialog == null) {
//			mProgressDialog = new ProgressDialog(SystemMsgWebPayDialog.this);
//			mProgressDialog.setIndeterminate(true);
//			mProgressDialog.setMessage("请稍候.....");
//		}
//		if (!mProgressDialog.isShowing()) {
//			mProgressDialog.show();
//		}
//	}
//
//	public void dismissProgressDialog() {
//		if (mProgressDialog != null && mProgressDialog.isShowing()) {
//			mProgressDialog.dismiss();
//		}
//	}
//}
