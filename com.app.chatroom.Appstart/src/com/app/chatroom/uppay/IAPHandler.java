//package com.app.chatroom.uppay;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.os.Handler;
//import android.os.Message;
//import android.widget.Toast;
//
//import com.app.chatroom.otherui.SystemMsgWebPayDialog;
//import com.app.chatroom.util.Commond;
//import com.jianrencun.chatroom.R;
//
//public class IAPHandler extends Handler {
//
//	public static final int INIT_FINISH = 10000;
//	public static final int BILL_FINISH = 10001;
//	public static final int QUERY_FINISH = 10002;
//	public static final int UNSUB_FINISH = 10003;
//
//	private SystemMsgWebPayDialog context;
//
//	public IAPHandler(Activity context) {
//		this.context = (SystemMsgWebPayDialog) context;
//	}
//
//	@Override
//	public void handleMessage(Message msg) {
//		super.handleMessage(msg);
//		int what = msg.what;
//		switch (what) {
//		case INIT_FINISH:
//			initShow((String) msg.obj);
//			context.dismissProgressDialog();
//			break;
//		default:
//			break;
//		}
//	}
//
//	private void initShow(String msg) {
//		//Toast.makeText(context, "初始化：" + msg, Toast.LENGTH_LONG).show();
//		//Commond.showToast(context, "手机话费支付" + msg);
//	}
//
//	public void showDialog(String title, String msg) {
//		AlertDialog.Builder builder = new AlertDialog.Builder(context);
//		builder.setTitle(title);
//		builder.setIcon(context.getResources().getDrawable(R.drawable.icon));
//		builder.setMessage((msg == null) ? "Undefined error" : msg);
//		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int whichButton) {
//				dialog.dismiss();
//			}
//		});
//		builder.create().show();
//	}
//
//}
