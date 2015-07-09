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
import com.app.chatroom.json.bean.MessageBean;
import com.app.chatroom.json.bean.RoomListBean;
import com.app.chatroom.pic.AsynImageLoader;
import com.app.chatroom.pic.BitmapCacheRoomList;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.ui.RoomListActivity;
import com.app.chatroom.util.SystemUtil;
import com.app.chatroom.view.NetImageView;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Comment;

public class RoomListAdapter extends BaseAdapter {
	LayoutInflater li = null;
	private boolean isLoad = false;
	RoomListActivity context;
	OnClickListener listener;
	public ArrayList<RoomListBean> list = new ArrayList<RoomListBean>();
	public ArrayList<MessageBean> msgList = new ArrayList<MessageBean>();
	SharedPreferences sp;
	SystemSettingUtilSp su;
	File picfile;
	private ArrayList<String> headerurls = new ArrayList<String>();
	ListView lv;

	public RoomListAdapter(RoomListActivity c, ArrayList<RoomListBean> list,
			ListView listview, OnClickListener listen) {
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
		RoomListBean roomBean = list.get(position);
		if (convertView == null) {
			viewHolder = new ViewHolder2();
			convertView = li.inflate(R.layout.room_list_items, null);
			viewHolder.roomTitleTextView = (TextView) convertView
					.findViewById(R.id.room_items_title_textView);
			viewHolder.roomNameTextView = (TextView) convertView
					.findViewById(R.id.room_items_name_textView);
			viewHolder.roomCountTextView = (TextView) convertView
					.findViewById(R.id.room_items_count_textView);
			viewHolder.roomLogoImage = (ImageView) convertView
					.findViewById(R.id.room_items_logo_image);
			viewHolder.roomLevelImage = (RelativeLayout) convertView
					.findViewById(R.id.room_items_level_imageView);
			viewHolder.roomLevelText = (TextView) convertView
					.findViewById(R.id.room_items_level_textView);
			viewHolder.roomDescTextView = (TextView) convertView
					.findViewById(R.id.room_items_desc_textView);
			viewHolder.roomClickImage = (ImageView) convertView
					.findViewById(R.id.room_items_facemask);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder2) convertView.getTag();
		}

		// viewHolder.roomLogoImage.setImageUrl(list.get(position).getLogo(),
		// R.drawable.photo, Environment.getExternalStorageDirectory()
		// + File.separator + context.getPackageName()
		// + ConstantsJrc.PHOTO_PATH, ConstantsJrc.PROJECT_PATH
		// + context.getPackageName() + ConstantsJrc.PHOTO_PATH);

		ImageView imageView1 = viewHolder.roomLogoImage;
		viewHolder.roomLogoImage.setTag(list.get(position).getLogo());
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

		//imageView1.setImageResource(R.drawable.photo);

		String filename = picfile.getPath().toString();
		Bitmap bmp1 = null;
		if(picfile.exists()){
			bmp1 = BitmapCacheRoomList.getIntance().getCacheBitmap(filename, 0, 0);

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

		switch (list.get(position).getType()) {
		case 1:
			viewHolder.roomLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_1));
			viewHolder.roomLevelText.setText(list.get(position).getTname());
			break;
		case 2:
			viewHolder.roomLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_2));
			viewHolder.roomLevelText.setText(list.get(position).getTname());
			break;
		case 3:
			viewHolder.roomLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_3));
			viewHolder.roomLevelText.setText(list.get(position).getTname());
			break;
		case 4:
			viewHolder.roomLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_4));
			viewHolder.roomLevelText.setText(list.get(position).getTname());
			break;
		case 5:
			viewHolder.roomLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_5));
			viewHolder.roomLevelText.setText(list.get(position).getTname());
			break;
		case 6:
			viewHolder.roomLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_6));
			viewHolder.roomLevelText.setText(list.get(position).getTname());
			break;
		case 7:
			viewHolder.roomLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_7));
			viewHolder.roomLevelText.setText(list.get(position).getTname());
			break;
		default:
			viewHolder.roomLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_1));
			viewHolder.roomLevelText.setText(list.get(position).getTname());
			break;
		}

		viewHolder.roomTitleTextView.setText(list.get(position).getTitle());
		viewHolder.roomNameTextView.setText(list.get(position).getName());
		viewHolder.roomDescTextView.setText(list.get(position).getDesc());
		viewHolder.roomCountTextView.setText(list.get(position).getCount());
		viewHolder.roomClickImage.setTag(roomBean);
		viewHolder.roomClickImage.setOnClickListener(listener);
		return convertView;
	}

	static class ViewHolder2 {
		public TextView roomTitleTextView;
		public TextView roomNameTextView;
		public TextView roomCountTextView;
		public TextView roomDescTextView;
		public ImageView roomLogoImage;
		public RelativeLayout roomLevelImage;
		public TextView roomLevelText;
		public ImageView roomClickImage;
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			if (data != null) {
				String url = data.getString("url");
				String filename = data.getString("filename");

				Bitmap bmp = BitmapCacheRoomList.getIntance().getCacheBitmap(filename,
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
