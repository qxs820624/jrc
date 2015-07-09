package com.app.chatroom.adapter;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.app.chatroom.adapter.ChatRoomAdapter2.LoadImageRunnable;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.json.bean.MessageBean;
import com.app.chatroom.json.bean.PitemsBean;
import com.app.chatroom.otherui.VillageUserInfoDialog;
import com.app.chatroom.pic.AsynImageLoader;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.SystemUtil;
import com.app.chatroom.view.NetImageView;
import com.duom.fjz.iteminfo.BitmapCache;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Comment;

public class PitemsAdapter extends BaseAdapter {
	LayoutInflater li = null;
	VillageUserInfoDialog context;
	private boolean isLoad = false;
	public ArrayList<PitemsBean> list = new ArrayList<PitemsBean>();
	public ArrayList<MessageBean> msgList = new ArrayList<MessageBean>();
	SharedPreferences sp;
	SystemSettingUtilSp su;
	int pagecount = 2;
	File picfile;
	private ArrayList<String> headerurls = new ArrayList<String>();
	GridView gv;

	public PitemsAdapter(VillageUserInfoDialog c, ArrayList<PitemsBean> mList,
			GridView gridview) {
		// TODO Auto-generated constructor stub
		this.context = c;
		this.list = mList;
		this.gv = gridview;
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
		PitemsBean pitBean = list.get(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = li.inflate(
					R.layout.village_userinfo_dialog_icon_items, null);
			viewHolder.userIconImage = (ImageView) convertView
					.findViewById(R.id.village_dialog_icon_image);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}


		ImageView imageView1 = viewHolder.userIconImage;
		 viewHolder.userIconImage.setTag(list.get(position).getPic());
		if (SystemUtil.getSDCardMount()) {
			picfile = new File(Environment.getExternalStorageDirectory()
					+ File.separator + context.getPackageName()
					+ ConstantsJrc.PHOTO_PATH + "/"
					+ Comment.getMd5Hash(list.get(position).getPic()));
		} else {
			picfile = new File(ConstantsJrc.PROJECT_PATH
					+ context.getPackageName() + ConstantsJrc.PHOTO_PATH + "/"
					+ Comment.getMd5Hash(list.get(position).getPic()));
		}

		//imageView1.setImageResource(R.drawable.touming);

		String filename = picfile.getPath().toString();
		Bitmap bmp1 = null;
		if(picfile.exists()){
			bmp1 = BitmapCache.getIntance().getCacheBitmap(filename, 0, 0);
		}
	 

		if (bmp1 == null) {
			if (!headerurls.contains(list.get(position).getPic())) {
				headerurls.add(list.get(position).getPic());
				new Thread(new LoadImageRunnable(context, mHandler, 0, list
						.get(position).getPic(), filename)).start();
			}
			imageView1.setImageResource(R.drawable.touming);
		} else {
			imageView1.setImageBitmap(bmp1);
		}

		return convertView;
	}

	static class ViewHolder {
		public ImageView userIconImage;
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			if (data != null) {
				String url = data.getString("url");
				String filename = data.getString("filename");

				Bitmap bmp = BitmapCache.getIntance().getCacheBitmap(filename,
						0, 0);
				ImageView iv = (ImageView) gv.findViewWithTag(url);
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
