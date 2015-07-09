package com.app.chatroom.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.expressionutil.SmileyParser;
import com.app.chatroom.interfaces.IMsgViewType;
import com.app.chatroom.json.bean.ChatMessageBean;
import com.app.chatroom.pic.AsynImageLoader;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.DateManager;
import com.jianrencun.chatroom.R;

 
public class ChatRoomAdapter extends BaseAdapter {

	public ArrayList<ChatMessageBean> list = new ArrayList<ChatMessageBean>();
	private LayoutInflater mInflater;
	private Context context;
	SharedPreferences sp;
	SystemSettingUtilSp su;
	OnClickListener mListener;

	String tnick;
	AsynImageLoader asynImageLoader;
	AsynImageLoader asynImageLoader1;
 
	public ChatRoomAdapter(Context context, ArrayList<ChatMessageBean> list,
			OnClickListener listener) {
		this.context = context;
		this.mListener = listener;
		this.list = list;
		mInflater = LayoutInflater.from(context);
		sp = context.getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				Context.MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
		asynImageLoader = new AsynImageLoader(this.context);
		asynImageLoader1 = new AsynImageLoader(this.context);
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
		ChatMessageBean chatBean = list.get(position);
		if (chatBean.isIscome() == true) {
			return IMsgViewType.IMVT_COM_MSG;
		} else {
			return IMsgViewType.IMVT_TO_MSG;
		}
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 6;
	}

	@SuppressWarnings({ "null", "unused" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = null;
		ChatMessageBean chatBean = list.get(position);
		boolean isComMsg = chatBean.isIscome();
		ViewHolder viewHolder = null;
		ViewHolder2 viewHolder2 = null;
		ViewHolder3 viewHolder3 = null;
		viewHolder = new ViewHolder();
		viewHolder2 = new ViewHolder2();
		viewHolder3 = new ViewHolder3();
		if (convertView == null) {
			if (isComMsg == true) {
				switch (chatBean.getMsgtype()) {
				case 0:
					convertView = mInflater.inflate(
							R.layout.chatting_item_msg_text_left, null);
					viewHolder.tvSendTime = (TextView) convertView
							.findViewById(R.id.tv_sendtime_left);
					viewHolder.tvUserName = (TextView) convertView
							.findViewById(R.id.tv_username_left);
					viewHolder.tvContent = (TextView) convertView
							.findViewById(R.id.tv_chatcontent_left);
					viewHolder.tvClickImage = (ImageView) convertView
							.findViewById(R.id.facemask_text_left);
					viewHolder.tvUserPhoto = (ImageView) convertView
							.findViewById(R.id.iv_userhead_left);
					viewHolder.isComMsg = isComMsg;
					break;
				case 1:
					convertView = mInflater.inflate(
							R.layout.chatting_item_msg_video_left, null);
					viewHolder2.tvSendTime = (TextView) convertView
							.findViewById(R.id.tv_videosendtime_left);
					viewHolder2.tvUserPhoto = (ImageView) convertView
							.findViewById(R.id.iv_audioheader_left);
					viewHolder2.tvUserName = (TextView) convertView
							.findViewById(R.id.tv_audiousername_left);
					viewHolder2.btPlayVideo = (Button) convertView
							.findViewById(R.id.bt_videoplay_left);
					viewHolder2.btError = (ImageView) convertView
							.findViewById(R.id.bt_videoerror_left);
					viewHolder.isComMsg = isComMsg;
					break;
				case 2:
					convertView = mInflater.inflate(
							R.layout.chatting_item_msg_pic_left, null);
					viewHolder3.tvSendTime = (TextView) convertView
							.findViewById(R.id.tv_picsendtime_left);
					viewHolder3.tvUserName = (TextView) convertView
							.findViewById(R.id.pic_username_left);
					viewHolder3.tvUserPhoto = (ImageView) convertView
							.findViewById(R.id.pic_header_left);
					viewHolder3.loadImage = (ImageView) convertView
							.findViewById(R.id.receiveImagePreview);
					viewHolder3.progressBar = (ProgressBar) convertView
							.findViewById(R.id.progressBar_left);
					viewHolder3.isComMsg = isComMsg;
					break;
				}

			} else if (isComMsg == false) {
				switch (chatBean.getMsgtype()) {
				case 0:
					convertView = mInflater.inflate(
							R.layout.chatting_item_msg_text_right, null);
					viewHolder.tvSendTime = (TextView) convertView
							.findViewById(R.id.tv_sendtime_right);
					viewHolder.tvUserName = (TextView) convertView
							.findViewById(R.id.tv_username_right);
					viewHolder.tvContent = (TextView) convertView
							.findViewById(R.id.tv_chatcontent_right);
					viewHolder.tvClickImage = (ImageView) convertView
							.findViewById(R.id.facemask_text_right);
					viewHolder.tvUserPhoto = (ImageView) convertView
							.findViewById(R.id.iv_userhead_right);
					viewHolder.btError = (ImageView) convertView
							.findViewById(R.id.bt_texterror_right);
					viewHolder.isComMsg = isComMsg;
					break;
				case 1:
					convertView = mInflater.inflate(
							R.layout.chatting_item_msg_video_right, null);
					viewHolder2.tvSendTime = (TextView) convertView
							.findViewById(R.id.tv_videosendtime_right);
					viewHolder2.tvUserPhoto = (ImageView) convertView
							.findViewById(R.id.iv_audioheader_right);
					viewHolder2.tvUserName = (TextView) convertView
							.findViewById(R.id.tv_audiousername_right);
					viewHolder2.btPlayVideo = (Button) convertView
							.findViewById(R.id.bt_videoplay_right);
					viewHolder2.btError = (ImageView) convertView
							.findViewById(R.id.bt_videoerror_right);
					viewHolder.isComMsg = isComMsg;
					break;
				case 2:
					convertView = mInflater.inflate(
							R.layout.chatting_item_msg_pic_right, null);
					viewHolder3.tvSendTime = (TextView) convertView
							.findViewById(R.id.tv_picsendtime_right);
					viewHolder3.tvUserName = (TextView) convertView
							.findViewById(R.id.pic_username_right);
					viewHolder3.tvUserPhoto = (ImageView) convertView
							.findViewById(R.id.pic_header_right);
					viewHolder3.loadImage = (ImageView) convertView
							.findViewById(R.id.sendImagePreview);
					viewHolder3.progressBar = (ProgressBar) convertView
							.findViewById(R.id.progressBar_right);
					viewHolder3.isComMsg = isComMsg;
					break;
				}

			}

			switch (chatBean.getMsgtype()) {
			case 0:
				convertView.setTag(viewHolder);
				break;
			case 1:
				convertView.setTag(viewHolder2);
				break;
			case 2:
				convertView.setTag(viewHolder3);
				break;
			}

		} else {
			switch (chatBean.getMsgtype()) {
			case 0:
				viewHolder = (ViewHolder) convertView.getTag();
				break;
			case 1:
				viewHolder2 = (ViewHolder2) convertView.getTag();
				break;
			case 2:
				viewHolder3 = (ViewHolder3) convertView.getTag();
				break;
			}

		}

		if (isComMsg) {
			switch (chatBean.getMsgtype()) {
			case 0:
				ImageGetter imageGetter = new ImageGetter() {
					@Override
					public Drawable getDrawable(String source) {
						int id = Integer.parseInt(source);

						// 根据id从资源文件中获取图片对象
						Drawable d = context.getResources().getDrawable(id);
						d.setBounds(0, 0, d.getIntrinsicWidth(),
								d.getIntrinsicHeight());
						return d;
					}
				};
				// 转换表情数据
				if (chatBean.getType() == 1) {
					CharSequence convertContent = SmileyParser
							.getInstance()
							.addSmileySpans(
									Html.fromHtml("<font color=\"#f8fd79\">私</font>"
											+ "<font color=\"#c0390f\">对</font>"
											+ "<font color=\"#0062bd\">["
											+ chatBean.getTnick()
											+ "]</font><font color=\"#c0390f\">说：</font><font color=\"#0d3c52\">"
											+ chatBean.getContent()
											+ "</font></body>"));
					viewHolder.tvContent.append(convertContent);
				} else if (chatBean.getType() == 0) {
					if (!chatBean.getTnick().equals("")) {
						tnick = chatBean.getTnick();
						CharSequence convertContent = SmileyParser
								.getInstance()
								.addSmileySpans(
										Html.fromHtml("<font color=\"#c0390f\">对</font>"
												+ "<font color=\"#0062bd\">["
												+ tnick
												+ "]</font><font color=\"#c0390f\">说：</font><font color=\"#0d3c52\">"
												+ chatBean.getContent()
												+ "</font></body>"));
						viewHolder.tvContent.append(convertContent);
					} else {
						CharSequence convertContent = SmileyParser
								.getInstance()
								.addSmileySpans(chatBean.getContent());
						viewHolder.tvContent.append(convertContent);
					}

				}

				// viewHolder.tvContent.append(Html.fromHtml("<img src=\""
				// + R.drawable.private_chat_icon + "\">", imageGetter,
				// null));

				ImageView imageView = viewHolder.tvUserPhoto;
				// 异步加载图片，缓存下载
				Bitmap bitmap = asynImageLoader.getCacheBitmap(list.get(
						position).getFheader());

				if (bitmap == null) {
					imageView.setImageResource(R.drawable.photo);
				} else {
					imageView.setImageBitmap(bitmap);
				}

				viewHolder.tvSendTime.setVisibility(View.VISIBLE);
				viewHolder.tvUserName.setText(chatBean.getFnick());
				viewHolder.tvSendTime.setText(DateManager.strDate(
						DateManager.getDate(su.getBTime(), false),
						DateManager.getDate(chatBean.getPd(), false)));

				// if (chatBean.getIsOK() == 0) {
				// viewHolder.btError.setVisibility(View.VISIBLE);
				// } else {
				// viewHolder.btError.setVisibility(View.GONE);
				// }
				viewHolder.tvClickImage.setTag(chatBean);
				viewHolder.tvClickImage.setOnClickListener(mListener);
				break;
			case 1:
				viewHolder2.tvSendTime.setVisibility(View.GONE);
				viewHolder2.btError.setVisibility(View.GONE);
				break;
			case 2:
				viewHolder3.tvSendTime.setVisibility(View.GONE);
				viewHolder3.progressBar.setVisibility(View.VISIBLE);
				break;
			}
		} else {
			switch (chatBean.getMsgtype()) {
			case 0:
				// 转换表情
				if (chatBean.getType() == 1) {
					CharSequence convertContent = SmileyParser
							.getInstance()
							.addSmileySpans(
									Html.fromHtml("<font color=\"#f8fd79\">私</font>"
											+ "<font color=\"#c0390f\">对</font>"
											+ "<font color=\"#0062bd\">["
											+ chatBean.getTnick()
											+ "]</font><font color=\"#c0390f\">说：</font><font color=\"#0d3c52\">"
											+ chatBean.getContent()
											+ "</font></body>"));
					viewHolder.tvContent.append(convertContent);
				} else if (chatBean.getType() == 0) {
					if (!chatBean.getTnick().equals("")) {
						tnick = chatBean.getTnick();
						CharSequence convertContent = SmileyParser
								.getInstance()
								.addSmileySpans(
										Html.fromHtml("<font color=\"#c0390f\">对</font>"
												+ "<font color=\"#0062bd\">["
												+ tnick
												+ "]</font><font color=\"#c0390f\">说：</font><font color=\"#0d3c52\">"
												+ chatBean.getContent()
												+ "</font></body>"));
						viewHolder.tvContent.append(convertContent);
					} else {
						CharSequence convertContent = SmileyParser
								.getInstance()
								.addSmileySpans(chatBean.getContent());
						viewHolder.tvContent.append(convertContent);
					}

				}

				ImageView imageView1 = viewHolder.tvUserPhoto;
				// 异步加载图片，缓存下载
				Bitmap bitmap1 = asynImageLoader.getCacheBitmap(list.get(
						position).getFheader());

				if (bitmap1 == null) {
					imageView1.setImageResource(R.drawable.photo);
				} else {
					imageView1.setImageBitmap(bitmap1);
				}

				viewHolder.tvSendTime.setVisibility(View.VISIBLE);
				viewHolder.tvUserName.setText(su.getNick());
				viewHolder.tvSendTime.setText(DateManager.strDate(
						DateManager.getDate(DateManager.getPhoneTime(), false),
						DateManager.getDate(chatBean.getPd(), false)));
				if (chatBean.getIsOK() == 0) {
					viewHolder.btError.setVisibility(View.VISIBLE);
				} else {
					viewHolder.btError.setVisibility(View.GONE);
				}
				viewHolder.tvClickImage.setTag(chatBean);
				viewHolder.tvClickImage.setOnClickListener(mListener);
				break;
			case 1:
				viewHolder2.tvSendTime.setVisibility(View.GONE);
				viewHolder2.btError.setVisibility(View.GONE);
				break;
			case 2:
				viewHolder3.tvSendTime.setVisibility(View.GONE);
				viewHolder3.progressBar.setVisibility(View.VISIBLE);
				break;
			}
		}

		return convertView;
	}

	static class ViewHolder {
		public TextView tvSendTime;
		public TextView tvUserName;
		public TextView tvContent;
		public ImageView tvClickImage;
		public ImageView tvUserPhoto;
		public ImageView btError;
		public boolean isComMsg = true;
	}

	static class ViewHolder2 {
		public TextView tvSendTime;
		public TextView tvUserName;
		public Button btPlayVideo;
		public ImageView tvUserPhoto;
		public ImageView btError;
		public boolean isComMsg = true;
	}

	static class ViewHolder3 {
		public TextView tvSendTime;
		public TextView tvUserName;
		public ImageView tvUserPhoto;
		public ProgressBar progressBar;
		public ImageView loadImage;
		public boolean isComMsg = true;
	}
}
