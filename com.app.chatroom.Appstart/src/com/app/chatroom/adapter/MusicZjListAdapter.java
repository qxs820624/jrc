package com.app.chatroom.adapter;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
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
import com.app.chatroom.mgmusic.MusicZjListActivity;
import com.app.chatroom.pic.BitmapCacheRoomList;
import com.app.chatroom.util.SystemUtil;
import com.cmsc.cmmusic.common.data.MusicInfo;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Comment;

public class MusicZjListAdapter extends BaseAdapter {
	LayoutInflater li = null;
	private boolean isLoad = false;
	MusicZjListActivity context;
	OnClickListener listener;
	public ArrayList<MusicInfo> list = new ArrayList<MusicInfo>();

	File picfile;
	private ArrayList<String> headerurls = new ArrayList<String>();
	ListView lv;

	public MusicZjListAdapter(MusicZjListActivity c, ArrayList<MusicInfo> list,
			ListView listview, OnClickListener lis) {
		this.context = c;
		this.list = list;
		this.lv = listview;
		li = LayoutInflater.from(context);
		this.listener = lis;
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
		MusicInfo musicBean = list.get(position);
		if (convertView == null) {
			viewHolder = new ViewHolder2();
			convertView = li.inflate(R.layout.music_list_items, null);
			viewHolder.musicTitleTextView = (TextView) convertView
					.findViewById(R.id.music_items_title_textView);
			viewHolder.musicNameTextView = (TextView) convertView
					.findViewById(R.id.music_items_name_textView);
			viewHolder.musicCountTextView = (TextView) convertView
					.findViewById(R.id.music_items_count_textView);
			viewHolder.musicLogoImage = (ImageView) convertView
					.findViewById(R.id.music_items_logo_image);
			viewHolder.musicLevelImage = (RelativeLayout) convertView
					.findViewById(R.id.music_items_level_imageView);
			viewHolder.musicLevelText = (TextView) convertView
					.findViewById(R.id.music_items_level_textView);
			viewHolder.musicClickImage = (ImageView) convertView
					.findViewById(R.id.music_items_facemask);
			viewHolder.musicMoreImage = (ImageView) convertView
					.findViewById(R.id.music_items_more_ImageView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder2) convertView.getTag();
		}

		ImageView imageView1 = viewHolder.musicLogoImage;
		viewHolder.musicLogoImage.setTag(list.get(position).getSingerPicDir());
		if (SystemUtil.getSDCardMount()) {
			picfile = new File(Environment.getExternalStorageDirectory()
					+ File.separator + context.getPackageName()
					+ ConstantsJrc.PHOTO_PATH + "/"
					+ Comment.getMd5Hash(list.get(position).getSingerPicDir()));
		} else {
			picfile = new File(ConstantsJrc.PROJECT_PATH
					+ context.getPackageName() + ConstantsJrc.PHOTO_PATH + "/"
					+ Comment.getMd5Hash(list.get(position).getSingerPicDir()));
		}

		// imageView1.setImageResource(R.drawable.photo);

		String filename = picfile.getPath().toString();
		Bitmap bmp1 = null;
		if (picfile.exists()) {
			bmp1 = BitmapCacheRoomList.getIntance().getCacheBitmap(filename, 0,
					0);

		}

		if (bmp1 == null) {
			if (!headerurls.contains(list.get(position).getSingerPicDir())) {
				headerurls.add(list.get(position).getSingerPicDir());
				new Thread(new LoadImageRunnable(context, mHandler, 0, list
						.get(position).getSingerPicDir(), filename)).start();
			}
			imageView1.setImageResource(R.drawable.photo);
		} else {
			imageView1.setImageBitmap(bmp1);
		}

		viewHolder.musicTitleTextView.setText(list.get(position).getSongName());// 歌曲名称
		viewHolder.musicNameTextView
				.setText(list.get(position).getSingerName());// 歌手名称
		// if ("0".equals(list.get(position).getHasDolby())) {
		// viewHolder.musicCountTextView.setText("杜比音效：无");// 杜比音效
		// } else {
		// viewHolder.musicCountTextView.setText("杜比音效：有");// 杜比音效
		// }

		viewHolder.musicLevelText.setText(position + 1 + "");
		// viewHolder.musicClickImage.setTag(musicBean);
		// viewHolder.musicClickImage.setOnClickListener(listener);
		viewHolder.musicMoreImage.setTag(musicBean);
		viewHolder.musicMoreImage.setOnClickListener(listener);
		return convertView;
	}

	static class ViewHolder2 {
		public TextView musicTitleTextView;
		public TextView musicNameTextView;
		public TextView musicCountTextView;
		public TextView musicDescTextView;
		public ImageView musicLogoImage;
		public RelativeLayout musicLevelImage;
		public TextView musicLevelText;
		public ImageView musicClickImage;
		public ImageView musicMoreImage;
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
