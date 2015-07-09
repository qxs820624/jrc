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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.json.bean.LiwuTypeBean;
import com.app.chatroom.json.bean.MessageBean;
import com.app.chatroom.json.bean.RoomListBean;
import com.app.chatroom.otherui.VillageUserInfoDialog;
import com.app.chatroom.pic.AsynImageLoader;
import com.app.chatroom.pic.BitmapCacheLiwuType;
import com.app.chatroom.pic.BitmapCacheRoomList;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.ui.RoomListActivity;
import com.app.chatroom.util.SystemUtil;
import com.app.chatroom.view.NetImageView;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Comment;

public class LiwuTypeListAdapter extends BaseAdapter {
	LayoutInflater li = null;
	VillageUserInfoDialog context;
	OnClickListener listener;
	public ArrayList<LiwuTypeBean> list = new ArrayList<LiwuTypeBean>();
	public ArrayList<MessageBean> msgList = new ArrayList<MessageBean>();
	SharedPreferences sp;
	SystemSettingUtilSp su;
	File picfile;
	private ArrayList<String> headerurls = new ArrayList<String>();
	ListView lv;

	public LiwuTypeListAdapter(VillageUserInfoDialog c,
			ArrayList<LiwuTypeBean> list, ListView listview,
			OnClickListener listen) {
		this.context = c;
		this.list = list;
		this.lv = listview;
		this.listener = listen;
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
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder2 viewHolder = null;
		LiwuTypeBean liwutypeBean = list.get(position);
		if (convertView == null) {
			viewHolder = new ViewHolder2();
			convertView = li.inflate(
					R.layout.village_userinfo_dialog_liwu_type_items, null);
			viewHolder.liwuTypeLogoImage = (ImageView) convertView
					.findViewById(R.id.village_dialog_liwutype_item_header_image);
			viewHolder.liwuNameTextView = (TextView) convertView
					.findViewById(R.id.village_dialog_liwutype_item_name_textView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder2) convertView.getTag();
		}

		ImageView imageView1 = viewHolder.liwuTypeLogoImage;
		viewHolder.liwuTypeLogoImage.setTag(list.get(position).getLogo());
		if (SystemUtil.getSDCardMount()) {
			picfile = new File(Environment.getExternalStorageDirectory()
					+ File.separator + context.getPackageName()
					+ ConstantsJrc.PHOTO_PATH + "/"
					+ Comment.getMd5Hash(list.get(position).getLogo()));
		} else {
			picfile = new File(ConstantsJrc.PROJECT_PATH
					+ context.getPackageName() + ConstantsJrc.PHOTO_PATH + "/"
					+ Comment.getMd5Hash(list.get(position).getLogo()));
		}

		// imageView1.setImageResource(R.drawable.photo);

		String filename = picfile.getPath().toString();
		Bitmap bmp1 = null;
		if(picfile.exists()){
			bmp1 = BitmapCacheLiwuType.getIntance().getCacheBitmap(filename,
					0, 0);
		}

		if (bmp1 == null) {
			if (!headerurls.contains(list.get(position).getLogo())) {
				headerurls.add(list.get(position).getLogo());
				new Thread(new LoadImageRunnable(context, mHandler, 0, list
						.get(position).getLogo(), filename)).start();
			}
			imageView1.setImageResource(R.drawable.photo);
		} else {
			imageView1.setImageBitmap(bmp1);
		}
		viewHolder.liwuNameTextView.setText(liwutypeBean.getName());
	 
		return convertView;
	}

	static class ViewHolder2 {
		public TextView liwuNameTextView;
		public ImageView liwuTypeLogoImage;
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			if (data != null) {
				String url = data.getString("url");
				String filename = data.getString("filename");

				Bitmap bmp = BitmapCacheRoomList.getIntance().getCacheBitmap(
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
