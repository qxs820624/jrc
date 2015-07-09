package com.app.chatroom.adapter;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.expressionutil.SmileyParser;
import com.app.chatroom.interfaces.IMsgViewType;
import com.app.chatroom.json.bean.MailBean;
import com.app.chatroom.pic.AsynImageLoader;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.view.NetImageView;
import com.jianrencun.chatroom.R;

public class MailContentAdapter extends BaseAdapter {

	public ArrayList<MailBean> list = new ArrayList<MailBean>();
	private LayoutInflater mInflater;
	private Context context;
	SharedPreferences sp;
	SystemSettingUtilSp su;
	public OnClickListener mListener;

	public MailContentAdapter(Context context, ArrayList<MailBean> list,
			OnClickListener listener) {
		this.context = context;
		this.list = list;
		this.mListener = listener;
		mInflater = LayoutInflater.from(context);
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
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		MailBean mailBean = list.get(position);
		// System.out.println(mailBean.toString());
		if (mailBean.getIscometype() == 0)
			return IMsgViewType.IMVT_COM_MSG;
		else if (mailBean.getIscometype() == 1)
			return IMsgViewType.IMVT_VIDEO_COM_MSG;
		else if (mailBean.getIscometype() == 2)
			return IMsgViewType.IMVT_PIC_COM_MSG;
		else if (mailBean.getIscometype() == 3)
			return IMsgViewType.IMVT_TO_MSG;
		else if (mailBean.getIscometype() == 4)
			return IMsgViewType.IMVT_VIDEO_TO_MSG;
		else if (mailBean.getIscometype() == 5)
			return IMsgViewType.IMVT_VIDEO_TO_MSG;
		else
			return 0;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 6;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MailBean mailBean = list.get(position);
		ViewHolder viewHolder = null;
		ViewHolder viewHolder2 = null;
		if (convertView == null) {
			switch (mailBean.getIscometype()) {
			case 0:
				viewHolder = new ViewHolder();
				convertView = mInflater.inflate(
						R.layout.mail_item_msg_text_left, null);
				viewHolder.tvSendTime = (TextView) convertView
						.findViewById(R.id.tv_mail_sendtime_left);
				viewHolder.tvUserName = (TextView) convertView
						.findViewById(R.id.tv_mail_username_left);
				viewHolder.tvContent = (TextView) convertView
						.findViewById(R.id.tv_mail_content_left);
				viewHolder.ivClickImage = (ImageView) convertView
						.findViewById(R.id.facemask_mail_left);
				viewHolder.ivUserPhoto = (NetImageView) convertView
						.findViewById(R.id.iv_mail_userhead_left);
				convertView.setTag(viewHolder);
				break;
			case 3:
				viewHolder2 = new ViewHolder();
				convertView = mInflater.inflate(
						R.layout.mail_item_msg_text_right, null);
				viewHolder2.tvSendTime = (TextView) convertView
						.findViewById(R.id.tv_mail_sendtime_right);
				viewHolder2.tvUserName = (TextView) convertView
						.findViewById(R.id.tv_mail_username_right);
				viewHolder2.tvContent = (TextView) convertView
						.findViewById(R.id.tv_mail_content_right);
				viewHolder2.ivClickImage = (ImageView) convertView
						.findViewById(R.id.facemask_mail_right);
				viewHolder2.ivUserPhoto = (NetImageView) convertView
						.findViewById(R.id.iv_mail_userhead_right);
				viewHolder2.btError = (ImageView) convertView
						.findViewById(R.id.bt_mail_texterror_right);
				convertView.setTag(viewHolder2);
				break;
			}

		} else {
			switch (mailBean.getIscometype()) {
			case 0:
				viewHolder = (ViewHolder) convertView.getTag();
				break;
			case 3:
				viewHolder2 = (ViewHolder) convertView.getTag();
				break;
			}

		}

		switch (mailBean.getIscometype()) {
		case 0:
			CharSequence convertContent = SmileyParser.getInstance()
					.addSmileySpans(mailBean.getContent().toString());
			viewHolder.tvContent.setText(convertContent);
			ImageView imageView = viewHolder.ivUserPhoto;
			// 异步加载图片，缓存下载

			viewHolder.ivUserPhoto.setDrawingCacheEnabled(false);
			viewHolder.ivUserPhoto.setImageUrl(mailBean.getFheader(),
					R.drawable.photo, Environment.getExternalStorageDirectory()
							+ File.separator + context.getPackageName()
							+ ConstantsJrc.PHOTO_PATH,
					ConstantsJrc.PROJECT_PATH + context.getPackageName()
							+ ConstantsJrc.PHOTO_PATH);
			viewHolder.tvSendTime.setVisibility(View.VISIBLE);
			viewHolder.tvUserName.setText(mailBean.getFnick().toString());
			viewHolder.tvSendTime.setText(mailBean.getPtime().toString());
			viewHolder.ivClickImage.setTag(mailBean);
			viewHolder.ivClickImage.setOnClickListener(mListener);
			break;

		case 3:
			CharSequence convertContent3 = SmileyParser.getInstance()
					.addSmileySpans(mailBean.getContent().toString());
			viewHolder2.tvContent.setText(convertContent3);
			ImageView imageView1 = viewHolder2.ivUserPhoto;
			// 异步加载图片，缓存下载

			viewHolder2.ivUserPhoto.setDrawingCacheEnabled(false);
			viewHolder2.ivUserPhoto.setImageUrl(mailBean.getFheader(),
					R.drawable.photo, Environment.getExternalStorageDirectory()
							+ File.separator + context.getPackageName()
							+ ConstantsJrc.PHOTO_PATH,
					ConstantsJrc.PROJECT_PATH + context.getPackageName()
							+ ConstantsJrc.PHOTO_PATH);
			viewHolder2.tvSendTime.setVisibility(View.VISIBLE);
			viewHolder2.tvUserName.setText(mailBean.getFnick().toString());
			viewHolder2.btError.setVisibility(View.GONE);
			viewHolder2.tvSendTime.setText(mailBean.getPtime().toString());
			viewHolder2.ivClickImage.setTag(mailBean);
			viewHolder2.ivClickImage.setOnClickListener(mListener);
			break;
		}
		return convertView;
	}

	static class ViewHolder {
		public TextView tvSendTime;
		public TextView tvUserName;
		public TextView tvContent;
		public ImageView ivClickImage;
		public NetImageView ivUserPhoto;
		public ImageView btError;
		public int isComMsg;
	}

}
