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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.json.bean.AttentionUserBean;
import com.app.chatroom.json.bean.MessageBean;
import com.app.chatroom.otherui.VillageUserInfoDialog;
import com.app.chatroom.pic.AsynImageLoader;
import com.app.chatroom.pic.BitmapCacheBguanzhu;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.SystemUtil;
import com.app.chatroom.view.NetImageView;
import com.duom.fjz.iteminfo.BitmapCache;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Comment;

public class BguanzhuAdapter extends BaseAdapter {
	ImageView userHeaderImage;
	ImageView userSexImage;
	TextView userNickTextView;
	TextView userSignTextView;
	TextView userUidTextView;
	ImageView userLevelImage;
	LayoutInflater li = null;
	VillageUserInfoDialog context;
	private boolean isLoad = false;
	OnClickListener mListener;
	OnClickListener mInfoListener;
	public ArrayList<AttentionUserBean> list = new ArrayList<AttentionUserBean>();
	public ArrayList<AttentionUserBean> morelist = new ArrayList<AttentionUserBean>();
	public ArrayList<MessageBean> msgList = new ArrayList<MessageBean>();
	SharedPreferences sp;
	SystemSettingUtilSp su;
	AsynImageLoader asynImageLoader;
	int pagecount = 2;
	File picfile;
	private ArrayList<String> headerurls = new ArrayList<String>();
	ListView lv;

	public BguanzhuAdapter(VillageUserInfoDialog c,
			ArrayList<AttentionUserBean> mList, ListView listView,
			OnClickListener listener, OnClickListener infolisten) {
		// TODO Auto-generated constructor stub
		this.context = c;
		this.list = mList;
		this.lv = listView;
		this.mListener = listener;
		this.mInfoListener = infolisten;
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
		AttentionUserBean auBean = list.get(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = li.inflate(R.layout.village_userinfo_dialog_items,
					null);
			viewHolder.userHeaderImage = (ImageView) convertView
					.findViewById(R.id.village_dialog_item_header_image);
			viewHolder.userUidTextView = (TextView) convertView
					.findViewById(R.id.village_dialog_item_uid_textView);
			viewHolder.userNickTextView = (TextView) convertView
					.findViewById(R.id.village_dialog_item_name_textView);
			viewHolder.userSexImage = (ImageView) convertView
					.findViewById(R.id.village_dialog_item_sex_image);
			viewHolder.userEditBtn = (ImageView) convertView
					.findViewById(R.id.village_userinfo_dialog_edit_btn);
			viewHolder.ivClickImage = (ImageView) convertView
					.findViewById(R.id.village_dialog_item_facemask);
			viewHolder.time = (TextView) convertView
					.findViewById(R.id.village_userinfo_dialog_time);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

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

		imageView1.setImageResource(R.drawable.photo);

		String filename = picfile.getPath().toString();
		Bitmap bmp1 = null;
		if(picfile.exists()){
			 bmp1 = BitmapCacheBguanzhu.getIntance().getCacheBitmap(filename,
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
		switch (list.get(position).getFollowed()) {
		case 0:
			viewHolder.userEditBtn
					.setBackgroundResource(R.drawable.chatroom_userinfo_dialog_xiaoguanzhu_btn);
			break;
		case 1:
			viewHolder.userEditBtn
					.setBackgroundResource(R.drawable.chatroom_userinfo_dialog_cancelguanzhu_btn);

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
				.getUid()));
		viewHolder.time.setText(list.get(position).getTime());
		viewHolder.ivClickImage.setTag(auBean);
		viewHolder.ivClickImage.setOnClickListener(mInfoListener);
		viewHolder.userEditBtn.setTag(auBean);
		viewHolder.userEditBtn.setOnClickListener(mListener);
		return convertView;
	}

	static class ViewHolder {
		public TextView userUidTextView , time;
		public TextView userNickTextView;
		public ImageView userHeaderImage;
		public ImageView userSexImage;
		public ImageView userEditBtn;
		public ImageView ivClickImage;
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			if (data != null) {
				String url = data.getString("url");
				String filename = data.getString("filename");

				Bitmap bmp = BitmapCache.getIntance().getCacheBitmap(filename,
						0, 0);
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
