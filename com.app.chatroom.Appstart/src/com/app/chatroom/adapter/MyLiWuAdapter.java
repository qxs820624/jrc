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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.chatroom.adapter.BguanzhuAdapter.LoadImageRunnable;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.json.bean.MessageBean;
import com.app.chatroom.json.bean.MyLiwuItemsBean;
import com.app.chatroom.otherui.VillageUserInfoDialog;
import com.app.chatroom.pic.AsynImageLoader;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.SystemUtil;
import com.app.chatroom.view.NetImageView;
import com.duom.fjz.iteminfo.BitmapCache;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Comment;

public class MyLiWuAdapter extends BaseAdapter {
	LayoutInflater li = null;
	VillageUserInfoDialog context;
	private boolean isLoad = false;
	OnClickListener mListener;
	OnClickListener mInfoListener;
	public ArrayList<MyLiwuItemsBean> list = new ArrayList<MyLiwuItemsBean>();
	public ArrayList<MyLiwuItemsBean> morelist = new ArrayList<MyLiwuItemsBean>();
	public ArrayList<MessageBean> msgList = new ArrayList<MessageBean>();
	SharedPreferences sp;
	SystemSettingUtilSp su;
	int pagecount = 2;
	File picfile;
	File picfile2;
	private ArrayList<String> headerurls = new ArrayList<String>();
	ListView lv;

	public MyLiWuAdapter(VillageUserInfoDialog c,
			ArrayList<MyLiwuItemsBean> mList, ListView listview,
			OnClickListener infolistener) {
		// TODO Auto-generated constructor stub
		this.context = c;
		this.list = mList;
		this.lv = listview;
		this.mInfoListener = infolistener;
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
		MyLiwuItemsBean myBean = list.get(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = li.inflate(
					R.layout.village_userinfo_dialog_myliwu_items, null);
			viewHolder.userHeaderImage = (ImageView) convertView
					.findViewById(R.id.village_myliwu_items_header_image);
			viewHolder.userDescTextView = (TextView) convertView
					.findViewById(R.id.village_myliwu_items_uid_textView);
			viewHolder.userNickTextView = (TextView) convertView
					.findViewById(R.id.village_myliwu_items_name_textView);
			viewHolder.userSexImage = (ImageView) convertView
					.findViewById(R.id.village_myliwu_items_sex_image);
			viewHolder.userGLogoImage = (NetImageView) convertView
					.findViewById(R.id.village_myliwu_items_logo_imageView);
			viewHolder.ivClickImage = (ImageView) convertView
					.findViewById(R.id.village_myliwu_items_facemask);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.userGLogoImage.setDrawingCacheEnabled(false);
		viewHolder.userGLogoImage.setImageUrl(list.get(position).getGlogo(),
				R.drawable.liwupic, Environment.getExternalStorageDirectory()
						+ File.separator + context.getPackageName()
						+ ConstantsJrc.PHOTO_PATH, ConstantsJrc.PROJECT_PATH
						+ context.getPackageName() + ConstantsJrc.PHOTO_PATH);

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
			bmp1 = BitmapCache.getIntance().getCacheBitmap(filename, 0, 0);
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

		// asynImageLoader.showImageAsyn(viewHolder.userGLogoImage,
		// list.get(position).getGlogo(), R.drawable.photo);

		// ImageView imageView2 = viewHolder.userGLogoImage;
		// viewHolder.userGLogoImage.setTag(list.get(position).getGlogo());
		// if (SystemUtil.getSDCardMount()) {
		// picfile2 = new File(Environment.getExternalStorageDirectory()
		// + File.separator + context.getPackageName()
		// + ConstantsJrc.PHOTO_PATH + "/"
		// + Comment.getMd5Hash(list.get(position).getGlogo()));
		// } else {
		// picfile2 = new File(ConstantsJrc.PROJECT_PATH
		// + context.getPackageName() + ConstantsJrc.PHOTO_PATH + "/"
		// + Comment.getMd5Hash(list.get(position).getGlogo()));
		// }
		//
		// imageView2.setImageResource(R.drawable.photo);
		//
		// String filename2 = picfile2.getPath().toString();
		// Bitmap bmp2 = BitmapCache.getIntance().getCacheBitmap(filename2, 0,
		// 0);
		//
		// if (bmp2 == null) {
		// if (!headerurls.contains(list.get(position).getGlogo())) {
		// headerurls.add(list.get(position).getGlogo());
		// new Thread(new LoadImageRunnable(context, mHandler, 0, list
		// .get(position).getGlogo(), filename)).start();
		// }
		// imageView2.setImageResource(R.drawable.photo);
		// } else {
		// imageView2.setImageBitmap(bmp2);
		// }

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
		viewHolder.userDescTextView.setText(list.get(position).getDesc());
		viewHolder.ivClickImage.setTag(myBean);
		viewHolder.ivClickImage.setOnClickListener(mInfoListener);
		return convertView;
	}

	static class ViewHolder {
		public TextView userDescTextView;
		public TextView userNickTextView;
		public ImageView userHeaderImage;
		public ImageView userSexImage;
		public NetImageView userGLogoImage;
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
