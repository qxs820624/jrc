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
import com.app.chatroom.json.bean.VillagePeopleBean;
import com.app.chatroom.pic.AsynImageLoader;
import com.app.chatroom.pic.BitmapCacheVillage;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.ui.VillageActivity;
import com.app.chatroom.util.SystemUtil;
import com.app.chatroom.view.NetImageView;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Comment;

public class VillageDayScoreAdapter extends BaseAdapter {
	LayoutInflater li = null;
	private boolean isLoad = false;
	VillageActivity context;
	public ArrayList<VillagePeopleBean> list = new ArrayList<VillagePeopleBean>();
	public ArrayList<MessageBean> msgList = new ArrayList<MessageBean>();
	SharedPreferences sp;
	SystemSettingUtilSp su;
	File picfile;
	private ArrayList<String> headerurls = new ArrayList<String>();
	ListView lv;

	public VillageDayScoreAdapter(VillageActivity c,
			ArrayList<VillagePeopleBean> list, ListView listview) {
		this.context = c;
		this.list = list;
		this.lv = listview;
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
		if (convertView == null) {
			viewHolder = new ViewHolder2();
			convertView = li.inflate(R.layout.village_farmerdayscore_items,
					null);
			viewHolder.userdengji = (TextView) convertView
					.findViewById(R.id.village_dayscore_level_textView);
			viewHolder.userNickTextView = (TextView) convertView
					.findViewById(R.id.village_dayscore_name_textView);
			viewHolder.userMoneyTextView = (TextView) convertView
					.findViewById(R.id.village_dayscore_score_textView);
			viewHolder.userHeaderImage = (ImageView) convertView
					.findViewById(R.id.village_dayscore_header_image);
			viewHolder.userSexImage = (ImageView) convertView
					.findViewById(R.id.village_dayscore_sex_image);
			viewHolder.userNoTextView = (TextView) convertView
					.findViewById(R.id.village_dayscore_no_TextView);
			viewHolder.userLevelImage = (RelativeLayout) convertView
					.findViewById(R.id.village_dayscore_level_imageView);
			viewHolder.jyt= (ImageView) convertView
					.findViewById(R.id.village_dayscore_level_imageView2);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder2) convertView.getTag();
		}
		// asynImageLoader.showImageAsyn(viewHolder.userHeaderImage,
		// list.get(position).getHeader(), R.drawable.photo);
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
			bmp1 = BitmapCacheVillage.getIntance().getCacheBitmap(filename, 0, 0);

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

		switch (position) {
		case 0:
			viewHolder.jyt.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.jin));
	         viewHolder.userLevelImage.setVisibility(View.GONE);
	         viewHolder.userdengji.setVisibility(View.GONE);
	         viewHolder.userNoTextView.setVisibility(View.VISIBLE);
			break;
		case 1:
			viewHolder.jyt.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.yin));
	         viewHolder.userLevelImage.setVisibility(View.GONE);
	         viewHolder.userdengji.setVisibility(View.GONE);
	         viewHolder.userNoTextView.setVisibility(View.VISIBLE);
			break;
		case 2:
			viewHolder.jyt.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.tong));
	         viewHolder.userLevelImage.setVisibility(View.GONE);
	         viewHolder.userdengji.setVisibility(View.GONE);
	         viewHolder.userNoTextView.setVisibility(View.VISIBLE);
			break;

		default:
			viewHolder.jyt.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.wanrenmitouming));
	         viewHolder.userLevelImage.setVisibility(View.VISIBLE);
	         viewHolder.userdengji.setVisibility(View.VISIBLE);
//	         viewHolder.userNoTextView.setVisibility(View.GONE);
			break;
		}
		switch (list.get(position).getType()) {
		case 1:
			viewHolder.userLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_1));
			viewHolder.userdengji.setText(list.get(position).getLevel());
			break;
		case 2:
			viewHolder.userLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_2));
			viewHolder.userdengji.setText(list.get(position).getLevel());
			break;
		case 3:
			viewHolder.userLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_3));
			viewHolder.userdengji.setText(list.get(position).getLevel());
			break;
		case 4:
			viewHolder.userLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_4));
			viewHolder.userdengji.setText(list.get(position).getLevel());
			break;
		case 5:
			viewHolder.userLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_5));
			viewHolder.userdengji.setText(list.get(position).getLevel());
			break;
		case 6:
			viewHolder.userLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_6));
			viewHolder.userdengji.setText(list.get(position).getLevel());
			break;
		case 7:
			viewHolder.userLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_7));
			viewHolder.userdengji.setText(list.get(position).getLevel());
			break;
		default:
			viewHolder.userLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_1));
			viewHolder.userdengji.setText(list.get(position).getLevel());
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

		viewHolder.userNoTextView.setText(list.get(position).getNo());
		viewHolder.userMoneyTextView.setText(list.get(position).getMoney()
				.toString());
		return convertView;
	}

	static class ViewHolder2 {
		public TextView userNickTextView;
		public TextView userMoneyTextView , userdengji;
		public ImageView userHeaderImage;
		public ImageView userSexImage;
		public TextView userNoTextView;
		public RelativeLayout userLevelImage ;
		public ImageView jyt;
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			if (data != null) {
				String url = data.getString("url");
				String filename = data.getString("filename");

				Bitmap bmp = BitmapCacheVillage.getIntance().getCacheBitmap(filename,
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
