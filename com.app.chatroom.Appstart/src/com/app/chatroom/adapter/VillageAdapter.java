package com.app.chatroom.adapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.http.HttpVillage;
import com.app.chatroom.json.MessageJson;
import com.app.chatroom.json.VillageJson;
import com.app.chatroom.json.VillageMsgJson;
import com.app.chatroom.json.VillagePeopleJson;
import com.app.chatroom.json.bean.MessageBean;
import com.app.chatroom.json.bean.VillageBean;
import com.app.chatroom.json.bean.VillageMsgBean;
import com.app.chatroom.json.bean.VillagePeopleBean;
import com.app.chatroom.pic.AsynImageLoader;
import com.app.chatroom.pic.BitmapCacheVillage;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.ui.VillageActivity;
import com.app.chatroom.util.SystemUtil;
import com.app.chatroom.view.NetImageView;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Comment;

public class VillageAdapter extends BaseAdapter {
	LayoutInflater li = null;
	private boolean isLoad = false;
	VillageActivity context;
	public ArrayList<VillageBean> villageList = new ArrayList<VillageBean>();
	public ArrayList<VillageMsgBean> villageMsgList = new ArrayList<VillageMsgBean>();
	public ArrayList<VillagePeopleBean> villagePeopleList = new ArrayList<VillagePeopleBean>();
	public ArrayList<MessageBean> msgList = new ArrayList<MessageBean>();
	OnClickListener mListener;
	SharedPreferences sp;
	SystemSettingUtilSp su;

	final int VIEW_TYPE = 2;
	final int TYPE_1 = 0;
	final int TYPE_2 = 1;

	File picfile;
	private ArrayList<String> headerurls = new ArrayList<String>();
	ListView lv;

	public VillageAdapter(VillageActivity c, ArrayList<VillagePeopleBean> list,
			ListView listview, OnClickListener listener) {
		this.context = c;
		this.villagePeopleList = list;
		this.lv = listview;
		li = LayoutInflater.from(context);
		sp = context.getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				Context.MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
		this.mListener = listener;
	}

	public void loadPeople(final String uid, final String pd) {
		if (!isLoad) {
			isLoad = true;
			this.context.villageHandler
					.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);
			new Thread(new Runnable() {

				@Override
				public void run() {

					String result;
					try {
						result = HttpVillage.VillageGet(uid, pd, su.getToken());
						VillageJson villageJson = new VillageJson();
						VillageMsgJson villageMsgJson = new VillageMsgJson();
						VillagePeopleJson vilalgePeopleJson = new VillagePeopleJson();
						villageList = villageJson.parseJson(result);
						villageMsgList = villageMsgJson.parseJson(result);
						villagePeopleList = vilalgePeopleJson.parseJson(result);
						MessageJson mj = new MessageJson();
						msgList = mj.parseJsonPd(result);
						if (msgList.get(0).getPd() != null)
							su.setVillagePd(msgList.get(0).getPd());
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					isLoad = false;
					context.villageHandler
							.sendEmptyMessage(ConstantsJrc.HANDLER_ADAPTER_REFRESH);
					context.villageHandler
							.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);

				}
			}).start();
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return villagePeopleList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return villagePeopleList.get(position);
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
		// ViewHolderTop viewHolderTop = null;
		// VillageMsgBean villageMsgBean = villageMsgList.get(0);
		int type = getItemViewType(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = li.inflate(R.layout.village_farmer_items, null);
			viewHolder.userUidTextView = (TextView) convertView
					.findViewById(R.id.village_uid_textView);
			viewHolder.userNickTextView = (TextView) convertView
					.findViewById(R.id.village_name_textView);
			convertView.setTag(viewHolder);
			viewHolder.userMoneyTextView = (TextView) convertView
					.findViewById(R.id.village_score_textView);
			viewHolder.userHeaderImage = (ImageView) convertView
					.findViewById(R.id.village_header_image);
			viewHolder.userSexImage = (ImageView) convertView
					.findViewById(R.id.village_sex_image);
			viewHolder.userLevelImage = (RelativeLayout) convertView
					.findViewById(R.id.village_level_imageView);
			viewHolder.userLevelText = (TextView) convertView
					.findViewById(R.id.village_level_textView);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		// viewHolder.userHeaderImage.setDrawingCacheEnabled(false);
		// viewHolder.userHeaderImage.setImageUrl(villagePeopleList.get(position)
		// .getHeader(), R.drawable.photo,
		// Environment.getExternalStorageDirectory() + File.separator
		// + context.getPackageName() + ConstantsJrc.PHOTO_PATH,
		// ConstantsJrc.PROJECT_PATH + context.getPackageName()
		// + ConstantsJrc.PHOTO_PATH);

		ImageView imageView1 = viewHolder.userHeaderImage;
		viewHolder.userHeaderImage.setTag(villagePeopleList.get(position)
				.getHeader());
		if (SystemUtil.getSDCardMount()) {
			picfile = new File(Environment.getExternalStorageDirectory()
					+ File.separator
					+ context.getPackageName()
					+ ConstantsJrc.PHOTO_PATH
					+ "/"
					+ Comment.getMd5Hash(villagePeopleList.get(position)
							.getHeader()));
		} else {
			picfile = new File(ConstantsJrc.PROJECT_PATH
					+ context.getPackageName()
					+ ConstantsJrc.PHOTO_PATH
					+ "/"
					+ Comment.getMd5Hash(villagePeopleList.get(position)
							.getHeader()));
		}

		imageView1.setImageResource(R.drawable.photo);

		String filename = picfile.getPath().toString();
		Bitmap bmp1 = null;
		if (picfile.exists()) {
			bmp1 = BitmapCacheVillage.getIntance().getCacheBitmap(filename, 0,
					0);
		}

		if (bmp1 == null) {
			if (!headerurls.contains(villagePeopleList.get(position)
					.getHeader())) {
				headerurls.add(villagePeopleList.get(position).getHeader());
				new Thread(new LoadImageRunnable(context, mHandler, 0,
						villagePeopleList.get(position).getHeader(), filename))
						.start();
			}
			imageView1.setImageResource(R.drawable.photo);
		} else {
			imageView1.setImageBitmap(bmp1);
		}

		// 设置男女图片
		switch (villagePeopleList.get(position).getSex()) {
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

		switch (villagePeopleList.get(position).getType()) {
		case 1:
			viewHolder.userLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_1));
			viewHolder.userLevelText.setText(villagePeopleList.get(position)
					.getLevel());
			break;
		case 2:
			viewHolder.userLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_2));
			viewHolder.userLevelText.setText(villagePeopleList.get(position)
					.getLevel());
			break;
		case 3:
			viewHolder.userLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_3));
			viewHolder.userLevelText.setText(villagePeopleList.get(position)
					.getLevel());
			break;
		case 4:
			viewHolder.userLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_4));
			viewHolder.userLevelText.setText(villagePeopleList.get(position)
					.getLevel());
			break;
		case 5:
			viewHolder.userLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_5));
			viewHolder.userLevelText.setText(villagePeopleList.get(position)
					.getLevel());
			break;
		case 6:
			viewHolder.userLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_6));
			viewHolder.userLevelText.setText(villagePeopleList.get(position)
					.getLevel());
			break;
		case 7:
			viewHolder.userLevelImage.setBackgroundDrawable(context
					.getResources().getDrawable(R.drawable.icon_bg_7));
			viewHolder.userLevelText.setText(villagePeopleList.get(position)
					.getLevel());
			break;
		}

		String ncolor = "";
		if (!TextUtils.isEmpty(villagePeopleList.get(position).getNick_c())) {
			ncolor = villagePeopleList.get(position).getNick_c()
					.replace("#ff", "#");
		} else {
			ncolor = "#000000";
		}

		viewHolder.userUidTextView.setText(String.valueOf(villagePeopleList
				.get(position).getUid()));
//		viewHolder.userNickTextView.setText(Html.fromHtml("<font color=\""
//				+ ncolor + "\">" + villagePeopleList.get(position).getNick()
//				+ "</font>"));
		viewHolder.userNickTextView.setText(villagePeopleList.get(position).getNick());
		viewHolder.userNickTextView.setTextColor(Color.parseColor(ncolor));
		viewHolder.userMoneyTextView.setText(villagePeopleList.get(position)
				.getMoney().toString());
		viewHolder.userMoneyTextView.setVisibility(View.GONE);
		return convertView;
	}

	static class ViewHolderTop {
		public NetImageView villageLogoImageView;
		public TextView villageTitleTextView;
		public TextView villageMoneyTextView;
		public TextView villageCountTextView;
		public TextView villageSystemTextView;
	}

	static class ViewHolder {
		public TextView userUidTextView;
		public TextView userNickTextView;
		public TextView userMoneyTextView;
		public ImageView userHeaderImage;
		public ImageView userSexImage;
		public RelativeLayout userLevelImage;
		public TextView userLevelText;

	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			if (data != null) {
				String url = data.getString("url");
				String filename = data.getString("filename");

				Bitmap bmp = BitmapCacheVillage.getIntance().getCacheBitmap(
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
