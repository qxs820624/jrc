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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.json.bean.AttentionUserBean;
import com.app.chatroom.json.bean.BlackListBean;
import com.app.chatroom.json.bean.MessageBean;
import com.app.chatroom.otherui.VillageUserInfoDialog;
import com.app.chatroom.pic.AsynImageLoader;
import com.app.chatroom.pic.BitmapCacheBlack;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.SystemUtil;
import com.app.chatroom.view.NetImageView;
import com.duom.fjz.iteminfo.BitmapCache;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Comment;

public class BlackListAdapter extends BaseAdapter {
	LayoutInflater li = null;
	VillageUserInfoDialog context;
	OnClickListener mListener;
	OnClickListener mInfoListener;
	public ArrayList<BlackListBean> list = new ArrayList<BlackListBean>();
	public ArrayList<BlackListBean> morelist = new ArrayList<BlackListBean>();
	SharedPreferences sp;
	SystemSettingUtilSp su;

	int pagecount = 2;
	File picfile;
	private ArrayList<String> headerurls = new ArrayList<String>();
	ListView lv;

	public BlackListAdapter(VillageUserInfoDialog c,
			ArrayList<BlackListBean> mList, ListView listView,
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
		BlackListBean auBean = list.get(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = li.inflate(
					R.layout.village_userinfo_dialog_blacklist_items, null);
			viewHolder.userHeaderImage = (ImageView) convertView
					.findViewById(R.id.village_dialog_item_blacklist_header_image);
			viewHolder.userUidTextView = (TextView) convertView
					.findViewById(R.id.village_dialog_item_blacklist_uid_textView);
			viewHolder.userNickTextView = (TextView) convertView
					.findViewById(R.id.village_dialog_item_blacklist_name_textView);
			viewHolder.userSexImage = (ImageView) convertView
					.findViewById(R.id.village_dialog_item_blacklist_sex_image);
			viewHolder.userBlackBtn = (ImageButton) convertView
					.findViewById(R.id.village_userinfo_dialog_item_addblack_btn);
			viewHolder.ivClickImage = (ImageView) convertView
					.findViewById(R.id.village_dialog_item_blacklist_facemask);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

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
		if (picfile.exists()) {
			bmp1 = BitmapCacheBlack.getIntance().getCacheBitmap(filename, 0,
					0);
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
		viewHolder.ivClickImage.setTag(auBean);
		viewHolder.ivClickImage.setOnClickListener(mInfoListener);
		viewHolder.userBlackBtn.setTag(auBean);
		viewHolder.userBlackBtn.setOnClickListener(mListener);
		return convertView;
	}

	static class ViewHolder {
		public TextView userUidTextView;
		public TextView userNickTextView;
		public ImageView userHeaderImage;
		public ImageView userSexImage;
		public ImageButton userBlackBtn;
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
