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
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.chatroom.adapter.VillagePeopleAdapter.LoadImageRunnable;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.json.bean.LiwuItemsBean;
import com.app.chatroom.json.bean.MessageBean;
import com.app.chatroom.otherui.VillageUserInfoDialog;
import com.app.chatroom.pic.AsynImageLoader;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.SystemUtil;
import com.app.chatroom.view.NetImageView;
import com.duom.fjz.iteminfo.BitmapCache;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Comment;

public class LiwuListAdapter extends BaseAdapter {
	LayoutInflater li = null;
	VillageUserInfoDialog context;
	private boolean isLoad = false;
	OnClickListener mListener;
	public ArrayList<LiwuItemsBean> list = new ArrayList<LiwuItemsBean>();
	public ArrayList<LiwuItemsBean> morelist = new ArrayList<LiwuItemsBean>();
	public ArrayList<MessageBean> msgList = new ArrayList<MessageBean>();
	SharedPreferences sp;
	SystemSettingUtilSp su;
	int pagecount = 2;
	File picfile;
	private ArrayList<String> headerurls = new ArrayList<String>();
	GridView lv;

	public LiwuListAdapter(VillageUserInfoDialog c,
			ArrayList<LiwuItemsBean> mList, GridView gridview,
			OnClickListener listener) {
		// TODO Auto-generated constructor stub
		this.context = c;
		this.list = mList;
		this.lv = gridview;
		this.mListener = listener;
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
		LiwuItemsBean liwuBean = list.get(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = li.inflate(
					R.layout.village_userinfo_dialog_liwu_items, null);
			viewHolder.liwuLogoImage = (ImageView) convertView
					.findViewById(R.id.village_dialog_liwu_item_logo_imageView);
			viewHolder.liwuNameTextView = (TextView) convertView
					.findViewById(R.id.village_dialog_liwu_item_name_textView);
			viewHolder.liwuPriceTextView = (TextView) convertView
					.findViewById(R.id.village_dialog_liwu_item_price_textView);
			viewHolder.liwuGiveBtn = (ImageButton) convertView
					.findViewById(R.id.village_dialog_liwu_item_give_btn);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		// viewHolder.liwuLogoImage.setDrawingCacheEnabled(false);
		// viewHolder.liwuLogoImage.setImageUrl(list.get(position).getLogo(),
		// R.drawable.photo, Environment.getExternalStorageDirectory()
		// + File.separator + context.getPackageName()
		// + ConstantsJrc.PHOTO_PATH, ConstantsJrc.PROJECT_PATH
		// + context.getPackageName() + ConstantsJrc.PHOTO_PATH);

		ImageView imageView1 = viewHolder.liwuLogoImage;
		viewHolder.liwuLogoImage.setTag(list.get(position).getLogo());
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

		//imageView1.setImageResource(R.drawable.liwupic);

		String filename = picfile.getPath().toString();
		Bitmap bmp1 = null;
		if (picfile.exists()) {
			bmp1 = BitmapCache.getIntance().getCacheBitmap(filename, 0, 0);
		}

		if (bmp1 == null) {
			if (!headerurls.contains(list.get(position).getLogo())) {
				headerurls.add(list.get(position).getLogo());
				new Thread(new LoadImageRunnable(context, mHandler, 0, list
						.get(position).getLogo(), filename)).start();
			}
			imageView1.setImageResource(R.drawable.liwupic);
		} else {
			imageView1.setImageBitmap(bmp1);
		}

		viewHolder.liwuNameTextView.setText(String.valueOf(list.get(position)
				.getDesc()));
		viewHolder.liwuPriceTextView.setText(list.get(position).getPrice());
		viewHolder.liwuGiveBtn.setTag(liwuBean);
		viewHolder.liwuGiveBtn.setOnClickListener(mListener);
		return convertView;
	}

	static class ViewHolder {
		public TextView liwuNameTextView;
		public ImageView liwuLogoImage;
		public TextView liwuPriceTextView;
		public ImageButton liwuGiveBtn;
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
