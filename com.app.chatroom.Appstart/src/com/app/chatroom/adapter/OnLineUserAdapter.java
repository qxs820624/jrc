package com.app.chatroom.adapter;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.json.bean.MessageBean;
import com.app.chatroom.json.bean.OnlineUserBean;
import com.app.chatroom.pic.AsynImageLoader;
import com.app.chatroom.pic.BitmapCacheOnline;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.ui.ChatRoomActivity;
import com.app.chatroom.util.SystemUtil;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Comment;

public class OnLineUserAdapter extends BaseAdapter {
	ImageView userHeaderImage;
	ImageView userSexImage;
	TextView userNickTextView;
	TextView userSignTextView;
	TextView userUidTextView;
	ImageView userLevelImage;
	LayoutInflater li = null;
	ChatRoomActivity context;
	private boolean isLoad = false;
	public ArrayList<OnlineUserBean> list = new ArrayList<OnlineUserBean>();
	public ArrayList<OnlineUserBean> morelist = new ArrayList<OnlineUserBean>();
	public ArrayList<MessageBean> msgList = new ArrayList<MessageBean>();
	SharedPreferences sp;
	SystemSettingUtilSp su;
	int pagecount = 2;
	File picfile;
	private ArrayList<String> headerurls = new ArrayList<String>();
	ListView lv;

	public OnLineUserAdapter(ChatRoomActivity c,
			ArrayList<OnlineUserBean> mList, ListView listivew) {
		// TODO Auto-generated constructor stub
		this.context = c;
		this.list = mList;
		this.lv = listivew;
		li = LayoutInflater.from(context);
		sp = context.getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				Context.MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();

	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressWarnings("null")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = li.inflate(R.layout.chatting_online_items, null);
			viewHolder.userHeaderImage = (ImageView) convertView
					.findViewById(R.id.online_header_image);
			viewHolder.userUidTextView = (TextView) convertView
					.findViewById(R.id.online_uid_textView);
			viewHolder.userNickTextView = (TextView) convertView
					.findViewById(R.id.online_name_textView);
			viewHolder.userSexImage = (ImageView) convertView
					.findViewById(R.id.online_sex_image);
			viewHolder.userLevelImage = (RelativeLayout) convertView
					.findViewById(R.id.online_level_imageView);
			viewHolder.userLevelText = (TextView) convertView
					.findViewById(R.id.online_level_textView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		// // 异步加载图片，缓存下载
		// asynImageLoader.showImageAsyn(imageView,
		// list.get(position).getHeader(), R.drawable.photo);
		// viewHolder.userHeaderImage.setDrawingCacheEnabled(false);
		// viewHolder.userHeaderImage.setImageUrl(list.get(position).getHeader(),
		// R.drawable.photo, Environment.getExternalStorageDirectory()
		// + File.separator + context.getPackageName()
		// + ConstantsJrc.PHOTO_PATH, ConstantsJrc.PROJECT_PATH
		// + context.getPackageName() + ConstantsJrc.PHOTO_PATH);

		ImageView imageView1 = viewHolder.userHeaderImage;
		viewHolder.userHeaderImage.setTag(list.get(position).getHeader());
		if (SystemUtil.getSDCardMount()) {
			picfile = new File(Environment.getExternalStorageDirectory()
					+ File.separator + context.getPackageName()
					+ ConstantsJrc.PHOTO_PATH + "/"
					+ Comment.getMd5Hash(list.get(position).getHeader()));
		} else {
			picfile = new File(ConstantsJrc.PROJECT_PATH
					+ context.getPackageName() + ConstantsJrc.PHOTO_PATH + "/"
					+ Comment.getMd5Hash(list.get(position).getHeader()));
		}

		// imageView1.setImageResource(R.drawable.photo);

		String filename = picfile.getPath().toString();
		Bitmap bmp1 = null;
		
		if(picfile.exists()){
			 bmp1 = BitmapCacheOnline.getIntance().getCacheBitmap(filename,
						0, 0);
		}

		if (bmp1 == null) {
			if (!headerurls.contains(list.get(position).getHeader())) {
				headerurls.add(list.get(position).getHeader());
				new Thread(new LoadImageRunnable(context, mHandler, 0, list
						.get(position).getHeader(), filename)).start();
			}
			imageView1.setImageResource(R.drawable.photo);
		} else {
			imageView1.setImageBitmap(bmp1);
		}

		// 设置男女图片
		switch (list.get(position).getSex()) {
		case 0:
			viewHolder.userSexImage.setImageDrawable(context.getResources()
					.getDrawable(R.drawable.what));
			break;
		case 1:
			viewHolder.userSexImage.setImageDrawable(context.getResources()
					.getDrawable(R.drawable.woman));
			break;
		case 2:
			viewHolder.userSexImage.setImageDrawable(context.getResources()
					.getDrawable(R.drawable.man));
			break;
		}
		switch (list.get(position).getType()) {
		case 1:
			viewHolder.userLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_1));
			viewHolder.userLevelText.setText(list.get(position).getLevel());
			break;
		case 2:
			viewHolder.userLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_2));
			viewHolder.userLevelText.setText(list.get(position).getLevel());
			break;
		case 3:
			viewHolder.userLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_3));
			viewHolder.userLevelText.setText(list.get(position).getLevel());
			break;
		case 4:
			viewHolder.userLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_4));
			viewHolder.userLevelText.setText(list.get(position).getLevel());
			break;
		case 5:
			viewHolder.userLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_5));
			viewHolder.userLevelText.setText(list.get(position).getLevel());
			break;
		case 6:
			viewHolder.userLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_6));
			viewHolder.userLevelText.setText(list.get(position).getLevel());
			break;
		case 7:
			viewHolder.userLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_7));
			viewHolder.userLevelText.setText(list.get(position).getLevel());
			break;

		}

		String ncolor = "";
		if (!TextUtils.isEmpty(list.get(position).getNick_c())) {
			ncolor = list.get(position).getNick_c().replace("#ff", "#");
		} else {
			ncolor = "#000000";
		}
//		viewHolder.userNickTextView.setText(Html.fromHtml("<font color=\""
//				+ ncolor + "\">" + list.get(position).getNick() + "</font>"));
		viewHolder.userNickTextView.setText(list.get(position).getNick());
		viewHolder.userNickTextView.setTextColor(Color.parseColor(ncolor));
		viewHolder.userUidTextView.setText(String.valueOf(list.get(position)
				.getId()));

		return convertView;
	}

	static class ViewHolder {
		public TextView userUidTextView;
		public TextView userNickTextView;
		public ImageView userHeaderImage;
		public ImageView userSexImage;
		public RelativeLayout userLevelImage;
		public TextView userLevelText;
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			if (data != null) {
				String url = data.getString("url");
				String filename = data.getString("filename");

				Bitmap bmp = BitmapCacheOnline.getIntance().getCacheBitmap(
						filename, 0, 0);
				ImageView iv = (ImageView) lv.findViewWithTag(url);
				if (iv != null) {
					iv.setImageBitmap(bmp);
				}
			}
		}
	};

	public class LoadImageRunnable implements Runnable {
		private Context mContext;
		private int mThreadId;
		private Handler mHandler;
		private String sUrl;
		private String mFilename;

		public LoadImageRunnable(Context context, Handler h, int id,
				String str, String filename) {
			mHandler = h;
			mContext = context;
			mThreadId = id;
			sUrl = str;
			mFilename = filename;
		}

		@Override
		public void run() {

			Comment.urlToFile(mContext, sUrl, mFilename);
			Message msg = new Message();
			Bundle data = new Bundle();
			data.putString("url", sUrl);
			data.putString("filename", mFilename);
			msg.setData(data);
			mHandler.sendMessage(msg);
		}
	}

}
