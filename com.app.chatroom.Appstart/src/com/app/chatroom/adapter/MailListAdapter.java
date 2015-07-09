package com.app.chatroom.adapter;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.chatroom.adapter.LiwuListAdapter.LoadImageRunnable;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.db.DbUtil;
import com.app.chatroom.json.bean.MailBean;
import com.app.chatroom.pic.AsynImageLoader;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.SystemUtil;
import com.app.chatroom.view.NetImageView;
import com.duom.fjz.iteminfo.BitmapCache;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Comment;

public class MailListAdapter extends BaseAdapter {
	LayoutInflater li = null;
	Context context;
	private boolean isLoad = false;
	SharedPreferences sp;
	SystemSettingUtilSp su;
	public Cursor cursor;
	DbUtil db;
	OnClickListener mListener;
	OnClickListener mListener2;
	OnClickListener mInfoLstener;
	boolean IsShow;
	File picfile;
	private ArrayList<String> headerurls = new ArrayList<String>();
	ListView lv;

	public void showDeleteButton(boolean show) {
		this.IsShow = show;
	}

	public MailListAdapter(Context context, ListView listview,
			OnClickListener listener, OnClickListener listener2,
			OnClickListener infolisten) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.lv = listview;
		li = LayoutInflater.from(context);
		this.mListener = listener;
		this.mListener2 = listener2;
		this.mInfoLstener = infolisten;
		sp = context.getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				Context.MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
		db = new DbUtil(this.context);
		db.Open();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		cursor = db.getFuid(su.getUid(), su.getUid());

		return cursor.getCount();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return cursor.moveToPosition(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		cursor.moveToPosition(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = li.inflate(R.layout.mail_list_items, null);
			viewHolder.tvUserName = (TextView) convertView
					.findViewById(R.id.mail_list_name_textView);
			viewHolder.tvContent = (TextView) convertView
					.findViewById(R.id.mail_list_content_TextView);
			viewHolder.tvMailNum = (TextView) convertView
					.findViewById(R.id.mail_list_num_TextView);
			viewHolder.tvSendTime = (TextView) convertView
					.findViewById(R.id.mail_list_date_TextView);
			viewHolder.imClickImage = (ImageView) convertView
					.findViewById(R.id.mail_list_facemask);
			viewHolder.ivUserPhoto = (ImageView) convertView
					.findViewById(R.id.mail_list_header_image);
			viewHolder.imUserSex = (ImageView) convertView
					.findViewById(R.id.mail_list_sex_image);
			viewHolder.btDelete = (ImageButton) convertView
					.findViewById(R.id.mail_list_del_ImageButton);
			viewHolder.ryItemBg = (RelativeLayout) convertView
					.findViewById(R.id.mail_list_RelativeLayout);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		MailBean mailBean = new MailBean();
		mailBean.setId(Integer.parseInt(cursor.getString(cursor
				.getColumnIndex("m_id"))));
		mailBean.setType(cursor.getString(cursor.getColumnIndex("type")));
		mailBean.setFuid(cursor.getString(cursor.getColumnIndex("fuid")));
		mailBean.setFnick(cursor.getString(cursor.getColumnIndex("fnick")));
		mailBean.setFheader(cursor.getString(cursor.getColumnIndex("fheader")));
		mailBean.setFsex(cursor.getString(cursor.getColumnIndex("fsex")));
		mailBean.setTuid(cursor.getString(cursor.getColumnIndex("tuid")));
		mailBean.setTnick(cursor.getString(cursor.getColumnIndex("tnick")));
		mailBean.setTheader(cursor.getString(cursor.getColumnIndex("theader")));
		mailBean.setTsex(cursor.getString(cursor.getColumnIndex("tsex")));
		mailBean.setContent(cursor.getString(cursor.getColumnIndex("content")));
		mailBean.setAfile(cursor.getString(cursor.getColumnIndex("afile")));
		mailBean.setPfile(cursor.getString(cursor.getColumnIndex("pfile")));
		mailBean.setPtime(cursor.getString(cursor.getColumnIndex("ptime")));
		mailBean.setIsread(cursor.getString(cursor.getColumnIndex("isread")));

		// 异步加载图片，缓存下载
		if (mailBean.getFuid().equals(su.getUid())) {

			ImageView imageView1 = viewHolder.ivUserPhoto;
			viewHolder.ivUserPhoto.setTag(mailBean.getTheader());
			if (SystemUtil.getSDCardMount()) {
				picfile = new File(Environment.getExternalStorageDirectory()
						+ File.separator + context.getPackageName()
						+ ConstantsJrc.PHOTO_PATH + "/"
						+ Comment.getMd5Hash(mailBean.getTheader()));
			} else {
				picfile = new File(ConstantsJrc.PROJECT_PATH
						+ context.getPackageName() + ConstantsJrc.PHOTO_PATH
						+ "/" + Comment.getMd5Hash(mailBean.getTheader()));
			}

			imageView1.setImageResource(R.drawable.photo);

			String filename = picfile.getPath().toString();
			Bitmap bmp1 = null;
			if (picfile.exists()) {
				bmp1 = BitmapCache.getIntance().getCacheBitmap(filename, 0, 0);
			}

			if (bmp1 == null) {
				if (!headerurls.contains(mailBean.getTheader())) {
					headerurls.add(mailBean.getTheader());
					new Thread(new LoadImageRunnable(context, mHandler, 0,
							mailBean.getTheader(), filename)).start();
				}
				imageView1.setImageResource(R.drawable.photo);
			} else {
				imageView1.setImageBitmap(bmp1);
			}

			viewHolder.tvUserName.setText(mailBean.getTnick());
			// 设置男女图片
			int sex = 0;
			if (mailBean.getTsex().equals("")) {
				sex = 0;
			} else {
				sex = Integer.parseInt(mailBean.getTsex());
			}
			switch (sex) {
			case 0:
				viewHolder.imUserSex.setImageDrawable(context.getResources()
						.getDrawable(R.drawable.what));
				break;
			case 1:
				viewHolder.imUserSex.setImageDrawable(context.getResources()
						.getDrawable(R.drawable.woman));
				break;
			case 2:
				viewHolder.imUserSex.setImageDrawable(context.getResources()
						.getDrawable(R.drawable.man));
				break;
			}
		} else {

			ImageView imageView1 = viewHolder.ivUserPhoto;
			viewHolder.ivUserPhoto.setTag(mailBean.getFheader());
			if (SystemUtil.getSDCardMount()) {
				picfile = new File(Environment.getExternalStorageDirectory()
						+ File.separator + context.getPackageName()
						+ ConstantsJrc.PHOTO_PATH + "/"
						+ Comment.getMd5Hash(mailBean.getFheader()));
			} else {
				picfile = new File(ConstantsJrc.PROJECT_PATH
						+ context.getPackageName() + ConstantsJrc.PHOTO_PATH
						+ "/" + Comment.getMd5Hash(mailBean.getFheader()));
			}

			imageView1.setImageResource(R.drawable.photo);

			String filename = picfile.getPath().toString();
			Bitmap bmp1 = null;
			if (picfile.exists()) {
				bmp1 = BitmapCache.getIntance().getCacheBitmap(filename, 0, 0);

			}
			if (bmp1 == null) {
				if (!headerurls.contains(mailBean.getFheader())) {
					headerurls.add(mailBean.getFheader());
					new Thread(new LoadImageRunnable(context, mHandler, 0,
							mailBean.getFheader(), filename)).start();
				}
				imageView1.setImageResource(R.drawable.photo);
			} else {
				imageView1.setImageBitmap(bmp1);
			}

			viewHolder.tvUserName.setText(mailBean.getFnick());
			// 设置男女图片
			int sex = 0;
			if (mailBean.getFsex().equals("")) {
				sex = 0;
			} else {
				sex = Integer.parseInt(mailBean.getFsex());
			}
			switch (sex) {
			case 0:
				viewHolder.imUserSex.setImageDrawable(context.getResources()
						.getDrawable(R.drawable.what));
				break;
			case 1:
				viewHolder.imUserSex.setImageDrawable(context.getResources()
						.getDrawable(R.drawable.woman));
				break;
			case 2:
				viewHolder.imUserSex.setImageDrawable(context.getResources()
						.getDrawable(R.drawable.man));
				break;
			}
		}
		int num = 0;
		if (mailBean.getFuid().equals(su.getUid())) {
			num = db.getNumMail2(Integer.parseInt(mailBean.getTuid()));
		} else {
			num = db.getNumMail(Integer.parseInt(mailBean.getFuid()));
		}
		// System.out.println(num + "");
		if (num > 0) {
			viewHolder.tvMailNum.setText(String.valueOf(num));
		} else {
			viewHolder.tvMailNum.setVisibility(View.GONE);
		}
		viewHolder.tvContent.setText(mailBean.getContent());
		viewHolder.tvSendTime.setText(mailBean.getPtime());

		if (IsShow) {
			viewHolder.btDelete.setVisibility(View.VISIBLE);
			viewHolder.tvMailNum.setVisibility(View.GONE);
		} else {
			viewHolder.btDelete.setVisibility(View.GONE);
			if (num > 0)
				viewHolder.tvMailNum.setVisibility(View.VISIBLE);
		}
		viewHolder.ryItemBg.setTag(mailBean);
		viewHolder.ryItemBg.setOnClickListener(mListener);
		viewHolder.btDelete.setTag(mailBean);
		viewHolder.btDelete.setOnClickListener(mListener2);
		viewHolder.imClickImage.setTag(mailBean);
		viewHolder.imClickImage.setOnClickListener(mInfoLstener);
		return convertView;
	}

	static class ViewHolder {
		public TextView tvUserName;
		public TextView tvContent;
		public ImageView imClickImage;
		public ImageView ivUserPhoto;
		public ImageView imUserSex;
		public ImageButton btDelete;
		public TextView tvSendTime;
		public TextView tvMailNum;
		public RelativeLayout ryItemBg;
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
