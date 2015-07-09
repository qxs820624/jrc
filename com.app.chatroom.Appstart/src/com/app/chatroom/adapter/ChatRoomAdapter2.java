package com.app.chatroom.adapter;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.jianrencun.chatroom.R;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.download.DownFileRight;
import com.app.chatroom.expressionutil.SmileyParser;
import com.app.chatroom.http.HttpSendMsg;
import com.app.chatroom.imgzoom.ImageZoom;
import com.app.chatroom.interfaces.IMsgViewType;
import com.app.chatroom.json.MessageJson;
import com.app.chatroom.json.bean.ChatMessageBean;
import com.app.chatroom.json.bean.MessageBean;
import com.app.chatroom.pic.BitmapCacheChatRoom;
import com.app.chatroom.pic.NetImageViewCache;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.ui.ChatRoomActivity;
import com.app.chatroom.util.Commond;
import com.app.chatroom.util.DateManager;
import com.app.chatroom.util.PhoneInfo;
import com.app.chatroom.util.SystemUtil;
import com.app.chatroom.view.NetImageView;
import com.jianrencun.dazui.Comment;

public class ChatRoomAdapter2 extends BaseAdapter {

	public ArrayList<ChatMessageBean> list = new ArrayList<ChatMessageBean>();
	private LayoutInflater mInflater;
	private Context context;
	SharedPreferences sp;
	SystemSettingUtilSp su;
	OnClickListener mListener;
	OnClickListener mPicListener;
	OnClickListener mAudioListener;
	OnLongClickListener mTxtLongListener;
	OnLongClickListener mPicLongListener;
	OnLongClickListener mAudioLongListener;
	public MediaPlayer mediaPlayer;
	MediaPlayer mediaCompleted;
	DownFile downFile;
	DownFileRight downFileRight;
	String tnick;
	boolean isPlay = false;// 是否正在播放
	private AnimationDrawable animationDrawable;
	Activity activity;
	ListView lv;
	File picfile;
	private ArrayList<String> headerurls = new ArrayList<String>();
	Animation animation;
	SendMsgThread sendThread;// 发送线程
	public ArrayList<MessageBean> sendMessageList = new ArrayList<MessageBean>();
	int qptype = 0;

	public ChatRoomAdapter2(Context context, Activity activity,
			ArrayList<ChatMessageBean> list, ListView listView,
			OnClickListener listener, OnClickListener listener2,
			OnClickListener listener3, OnLongClickListener longlistener,
			OnLongClickListener longlistener2, OnLongClickListener longlistener3) {
		this.context = context;
		this.mListener = listener;
		this.mPicListener = listener2;
		this.mAudioListener = listener3;
		this.mTxtLongListener = longlistener;
		this.mPicLongListener = longlistener2;
		this.mAudioLongListener = longlistener3;
		this.lv = listView;
		this.list = list;
		this.activity = activity;
		mInflater = LayoutInflater.from(context);
		sp = context.getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				Context.MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);
		downFile = new DownFile();
		downFileRight = new DownFileRight();
		mediaPlayer = new MediaPlayer();
		animation = AnimationUtils.loadAnimation(context, R.anim.content_up);
	}

	public void stopAnimImageView() {
		// for (int i = 0; i < list.size(); i++) {
		// list.get(i).setIsplay(false);
		// }
		for (ChatMessageBean bean : list) {
			bean.setIsplay(false);
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(list.size() - 1 - position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		try {

			ChatMessageBean chatBean = list.get(list.size() - 1 - position);
			if (chatBean.getMsgtype() == 0)
				return IMsgViewType.IMVT_COM_MSG;
			else if (chatBean.getMsgtype() == 1)
				return IMsgViewType.IMVT_VIDEO_COM_MSG;
			else if (chatBean.getMsgtype() == 2)
				return IMsgViewType.IMVT_PIC_COM_MSG;
			else if (chatBean.getMsgtype() == 3)
				return IMsgViewType.IMVT_TO_MSG;
			else if (chatBean.getMsgtype() == 4)
				return IMsgViewType.IMVT_VIDEO_TO_MSG;
			else if (chatBean.getMsgtype() == 5)
				return IMsgViewType.IMVT_PIC_TO_MSG;
			else if (chatBean.getMsgtype() == 6) {
				return IMsgViewType.IMVT_SYS_TO_MSG;
			}
			return 0;
		} catch (Exception e) {
			// TODO: handle exception
			Commond.showToast(context, "遇到问题了，请重新进入房间！");
			return 7;			
		}
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 7;
	}

	@SuppressWarnings({ "null", "unused" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// convertView = null;
		final ChatMessageBean chatBean = list.get(list.size() - 1 - position);
		ViewHolder viewHolder = null;
		ViewHolder2 viewHolder2 = null;
		ViewHolder3 viewHolder3 = null;
		ViewHolder4 viewHolder4 = null;
		ViewHolder5 viewHolder5 = null;
		ViewHolder6 viewHolder6 = null;
		ViewHolder7 viewHolder7 = null;
		if (convertView == null) {
			switch (chatBean.getMsgtype()) {
			case 0:
				viewHolder = new ViewHolder();
				convertView = mInflater.inflate(
						R.layout.chatting_item_msg_text_left, null);
				viewHolder.tvSendTime = (TextView) convertView
						.findViewById(R.id.tv_sendtime_left);
				viewHolder.tvUserName = (TextView) convertView
						.findViewById(R.id.tv_username_left);
				viewHolder.tvContent = (TextView) convertView
						.findViewById(R.id.tv_chatcontent_left);
				viewHolder.ivClickImage = (ImageView) convertView
						.findViewById(R.id.facemask_text_left);
				viewHolder.ivUserPhoto = (ImageView) convertView
						.findViewById(R.id.iv_userhead_left);
				viewHolder.ivPrivateIcon = (ImageView) convertView
						.findViewById(R.id.iv_private_icon_left);
				viewHolder.rl = (RelativeLayout) convertView
						.findViewById(R.id.rl_text_left);
				convertView.setTag(viewHolder);
				break;

			case 1:
				viewHolder2 = new ViewHolder2();
				convertView = mInflater.inflate(
						R.layout.chatting_item_msg_video_left, null);
				viewHolder2.tvSendTime = (TextView) convertView
						.findViewById(R.id.tv_videosendtime_left);
				viewHolder2.ivUserPhoto = (ImageView) convertView
						.findViewById(R.id.iv_audioheader_left);
				viewHolder2.tvUserName = (TextView) convertView
						.findViewById(R.id.tv_audiousername_left);
				viewHolder2.ivClickImage = (ImageView) convertView
						.findViewById(R.id.facemask_audio_left);
				viewHolder2.lyPlayVideo = (LinearLayout) convertView
						.findViewById(R.id.bt_videoplay_left);
				viewHolder2.tvVideoTime = (TextView) convertView
						.findViewById(R.id.tv_video_left_time);
				viewHolder2.ivVideoAnim = (ImageView) convertView
						.findViewById(R.id.iv_video_left_anim);
				viewHolder2.ivVideoAnimStatic = (ImageView) convertView
						.findViewById(R.id.iv_video_left_anim_static);
				viewHolder2.ivVideoPrivateIcon = (ImageView) convertView
						.findViewById(R.id.iv_video_private_icon_left);
				viewHolder2.tvToUserName = (TextView) convertView
						.findViewById(R.id.tv_audio_touser_left);
				viewHolder2.lySay = (LinearLayout) convertView
						.findViewById(R.id.ly_audio_LinearLayout_left);
				viewHolder2.loadProgerss = (ProgressBar) convertView
						.findViewById(R.id.load_audio_left_progressbar);
				viewHolder2.rl = (RelativeLayout) convertView
						.findViewById(R.id.rl_video_left);
				convertView.setTag(viewHolder2);
				break;
			case 2:
				viewHolder3 = new ViewHolder3();
				convertView = mInflater.inflate(
						R.layout.chatting_item_msg_pic_left, null);
				viewHolder3.tvSendTime = (TextView) convertView
						.findViewById(R.id.tv_picsendtime_left);
				viewHolder3.tvUserName = (TextView) convertView
						.findViewById(R.id.pic_username_left);
				viewHolder3.ivUserPhoto = (ImageView) convertView
						.findViewById(R.id.pic_header_left);
				viewHolder3.loadImage = (NetImageView) convertView
						.findViewById(R.id.receiveImagePreview);
				viewHolder3.loadNoImage = (NetImageView) convertView
						.findViewById(R.id.receiveNoImagePreview);
				viewHolder3.progressBar = (ProgressBar) convertView
						.findViewById(R.id.progressBar_left);
				viewHolder3.ivClickImage = (ImageView) convertView
						.findViewById(R.id.facemask_pic_left);
				viewHolder3.lyclick = (LinearLayout) convertView
						.findViewById(R.id.linear_pic_click_left);
				viewHolder3.loadImageBg = (ImageView) convertView
						.findViewById(R.id.pic_content_left);
				viewHolder3.pivateIcon = (ImageView) convertView
						.findViewById(R.id.pic_private_icon_left);
				viewHolder3.rl = (RelativeLayout) convertView
						.findViewById(R.id.rl_pic_left);
				convertView.setTag(viewHolder3);

				break;
			case 3:
				viewHolder4 = new ViewHolder4();
				convertView = mInflater.inflate(
						R.layout.chatting_item_msg_text_right, null);
				viewHolder4.tvSendTime = (TextView) convertView
						.findViewById(R.id.tv_sendtime_right);
				viewHolder4.tvUserName = (TextView) convertView
						.findViewById(R.id.tv_username_right);
				viewHolder4.tvContent = (TextView) convertView
						.findViewById(R.id.tv_chatcontent_right);
				viewHolder4.ivClickImage = (ImageView) convertView
						.findViewById(R.id.facemask_text_right);
				viewHolder4.ivUserPhoto = (NetImageView) convertView
						.findViewById(R.id.iv_userhead_right);
				viewHolder4.btError = (ImageView) convertView
						.findViewById(R.id.bt_texterror_right);
				viewHolder4.ivPrivateIcon = (ImageView) convertView
						.findViewById(R.id.iv_private_icon_right);
				viewHolder4.rl = (RelativeLayout) convertView
						.findViewById(R.id.rl_text_right);
				viewHolder4.progressBar = (ProgressBar) convertView
						.findViewById(R.id.send_text_right_progressbar);
				convertView.setTag(viewHolder4);
				break;
			case 4:
				viewHolder5 = new ViewHolder5();
				convertView = mInflater.inflate(
						R.layout.chatting_item_msg_video_right, null);
				viewHolder5.tvSendTime = (TextView) convertView
						.findViewById(R.id.tv_videosendtime_right);
				viewHolder5.ivUserPhoto = (NetImageView) convertView
						.findViewById(R.id.iv_audioheader_right);
				viewHolder5.tvUserName = (TextView) convertView
						.findViewById(R.id.tv_audiousername_right);
				viewHolder5.ivClickImage = (ImageView) convertView
						.findViewById(R.id.facemask_audio_right);
				viewHolder5.rlPlayVideo = (RelativeLayout) convertView
						.findViewById(R.id.bt_videoplay_right);
				viewHolder5.btError = (ImageView) convertView
						.findViewById(R.id.bt_videoerror_right);
				viewHolder5.tvVideoTime = (TextView) convertView
						.findViewById(R.id.tv_video_right_time);
				viewHolder5.ivVideoAnim = (ImageView) convertView
						.findViewById(R.id.iv_video_right_anim);
				viewHolder5.ivVideoAnimStatic = (ImageView) convertView
						.findViewById(R.id.iv_video_right_anim_static);
				viewHolder5.ivVideoPrivateIcon = (ImageView) convertView
						.findViewById(R.id.iv_video_private_icon_right);
				viewHolder5.tvToUserName = (TextView) convertView
						.findViewById(R.id.tv_audio_touser_right);
				viewHolder5.lySay = (LinearLayout) convertView
						.findViewById(R.id.ly_audio_LinearLayout_right);
				viewHolder5.rl = (RelativeLayout) convertView
						.findViewById(R.id.rl_video_right);
				viewHolder5.progressBar = (ProgressBar) convertView
						.findViewById(R.id.send_video_right_progressbar);
				convertView.setTag(viewHolder5);
				break;
			case 5:
				viewHolder6 = new ViewHolder6();
				convertView = mInflater.inflate(
						R.layout.chatting_item_msg_pic_right, null);
				viewHolder6.tvSendTime = (TextView) convertView
						.findViewById(R.id.tv_picsendtime_right);
				viewHolder6.tvUserName = (TextView) convertView
						.findViewById(R.id.pic_username_right);
				viewHolder6.ivUserPhoto = (NetImageView) convertView
						.findViewById(R.id.pic_header_right);
				viewHolder6.loadImage = (NetImageView) convertView
						.findViewById(R.id.sendImagePreview);
				viewHolder6.ivClickImage = (ImageView) convertView
						.findViewById(R.id.facemask_pic_right);
				viewHolder6.progressBar = (ProgressBar) convertView
						.findViewById(R.id.progressBar_right);
				viewHolder6.lyclick = (LinearLayout) convertView
						.findViewById(R.id.linear_pic_click_right);
				viewHolder6.loadImageBg = (ImageView) convertView
						.findViewById(R.id.pic_content_right);
				viewHolder6.pivateIcon = (ImageView) convertView
						.findViewById(R.id.pic_private_icon_right);
				viewHolder6.rl = (RelativeLayout) convertView
						.findViewById(R.id.rl_pic_right);
				viewHolder6.progressBar = (ProgressBar) convertView
						.findViewById(R.id.send_pic_right_progressbar);
				viewHolder6.btError = (ImageView) convertView
						.findViewById(R.id.bt_picerror_right);
				convertView.setTag(viewHolder6);
				break;
			case 6:
				viewHolder7 = new ViewHolder7();
				convertView = mInflater.inflate(
						R.layout.chatting_item_msg_mid_system, null);
				viewHolder7.tvSysContent = (TextView) convertView
						.findViewById(R.id.tv_chatsyscontent);
				viewHolder7.ivSysIcon = (ImageView) convertView
						.findViewById(R.id.iv_chatsysicon);
				viewHolder7.rl = (RelativeLayout) convertView
						.findViewById(R.id.rl_system_middle);
				convertView.setTag(viewHolder7);
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
			case 3:
				viewHolder4 = (ViewHolder4) convertView.getTag();
				break;
			case 4:
				viewHolder5 = (ViewHolder5) convertView.getTag();
				break;
			case 5:
				viewHolder6 = (ViewHolder6) convertView.getTag();
				break;
			case 6:
				viewHolder7 = (ViewHolder7) convertView.getTag();
			}

		}

		switch (chatBean.getMsgtype()) {
		case 0:

			String color = "";
			String b = "";
			String b1 = "";
			String ncolor = "";
			// 设置文字的颜色
			if (!TextUtils.isEmpty(chatBean.getContent_c())) {
				color = chatBean.getContent_c().replace("#ff", "#");
			} else {
				color = "#0d3c52";
			}
			if (!TextUtils.isEmpty(chatBean.getNick_c())) {
				ncolor = chatBean.getNick_c().replace("#ff", "#");
			} else {
				ncolor = "#8c420c";
			}

			// 是否可以点击链接
			viewHolder.tvContent.setAutoLinkMask(chatBean.getContent_l());

			// 设置文字是否为粗体
			if (chatBean.getContent_b() == 1) {
				b = "<b>";
				b1 = "</b>";
			}

			// 转换表情数据
			if (chatBean.getType() == 1) {
				TextPaint tpaint = viewHolder.tvContent.getPaint();
				tpaint.setFakeBoldText(false);
				CharSequence convertContent = SmileyParser
						.getInstance()
						.addSmileySpans(
								Html.fromHtml("<font color=\"#c0390f\">对</font>"
										+ "<font color=\"#0062bd\">【"
										+ chatBean.getTnick()
										+ "】</font><font color=\"#c0390f\">说：</font>"
										+ b
										+ "<font color=\""
										+ color
										+ "\">"
										+ chatBean.getContent()
										+ "</font>"
										+ b1 + "</body>"));
				viewHolder.tvContent.setText(convertContent);
				viewHolder.ivPrivateIcon.setVisibility(View.VISIBLE);
			} else if (chatBean.getType() == 0) {
				TextPaint tpaint = viewHolder.tvContent.getPaint();
				tpaint.setFakeBoldText(false);
				if (!chatBean.getTnick().equals("")) {
					tnick = chatBean.getTnick();
					CharSequence convertContent = SmileyParser
							.getInstance()
							.addSmileySpans(
									Html.fromHtml("<font color=\"#c0390f\">对</font>"
											+ "<font color=\"#0062bd\">【"
											+ tnick
											+ "】</font><font color=\"#c0390f\">说：</font>"
											+ b
											+ "<font color=\""
											+ color
											+ "\">"
											+ chatBean.getContent()
											+ "</font>" + b1 + "</body>"));
					viewHolder.tvContent.setText(convertContent);
					viewHolder.ivPrivateIcon.setVisibility(View.GONE);
				} else {
					// 设置文字是否为粗体
					if (chatBean.getContent_b() == 1) {
						TextPaint tpaint1 = viewHolder.tvContent.getPaint();
						tpaint.setFakeBoldText(true);
					} else {
						TextPaint tpaint1 = viewHolder.tvContent.getPaint();
						tpaint.setFakeBoldText(false);
					}
					viewHolder.tvContent.setTextColor(Color.parseColor(color));
					CharSequence convertContent = SmileyParser.getInstance()
							.addSmileySpans(chatBean.getContent());
					viewHolder.tvContent.setText(convertContent);
					viewHolder.ivPrivateIcon.setVisibility(View.GONE);
				}
			}

			// viewHolder.tvContent.append(Html.fromHtml("<img src=\""
			// + R.drawable.private_chat_icon + "\">", imageGetter,
			// null));

			/** 加载头像方法一 **/
			ImageView imageView1 = viewHolder.ivUserPhoto;
			// asynImageLoader.showImageAsyn(imageView1, chatBean.getFheader(),
			// R.drawable.photo);
			viewHolder.ivUserPhoto.setTag(chatBean.getFheader());
			if (SystemUtil.getSDCardMount()) {
				picfile = new File(Environment.getExternalStorageDirectory()
						+ File.separator + context.getPackageName()
						+ ConstantsJrc.PHOTO_PATH + "/"
						+ Comment.getMd5Hash(chatBean.getFheader()));
			} else {
				picfile = new File(ConstantsJrc.PROJECT_PATH
						+ context.getPackageName() + ConstantsJrc.PHOTO_PATH
						+ "/" + Comment.getMd5Hash(chatBean.getFheader()));
			}

			imageView1.setImageResource(R.drawable.photo);

			String filename = picfile.getPath().toString();
			Bitmap bmp1 = null;
			if (picfile.exists()) {
				bmp1 = BitmapCacheChatRoom.getIntance().getCacheBitmap(
						filename, 0, 0);
			}

			if (bmp1 == null) {
				if (!headerurls.contains(chatBean.getFheader())) {
					headerurls.add(chatBean.getFheader());
					new Thread(new LoadImageRunnable(context, mHandler, 0,
							chatBean.getFheader(), filename)).start();
				}
				imageView1.setImageResource(R.drawable.photo);
			} else {
				imageView1.setImageBitmap(bmp1);
			}

			// 异步加载图片，缓存下载
			// viewHolder.ivUserPhoto.setImageUrl(chatBean.getFheader(),
			// R.drawable.photo, Environment.getExternalStorageDirectory()
			// + File.separator + context.getPackageName()
			// + ConstantsJrc.PHOTO_PATH,
			// ConstantsJrc.PROJECT_PATH + context.getPackageName()
			// + ConstantsJrc.PHOTO_PATH);

			viewHolder.tvSendTime.setVisibility(View.VISIBLE);
			viewHolder.tvUserName.setText(Html.fromHtml("<font color=\""
					+ ncolor + "\">" + chatBean.getFnick() + "</font>"));

			viewHolder.tvSendTime.setText(DateManager.strDate(
					DateManager.getDate(su.getBTime(), false),
					DateManager.getDate(chatBean.getPd(), false)));

			viewHolder.ivClickImage.setTag(chatBean);
			viewHolder.ivClickImage.setOnClickListener(mListener);
			// if (chatBean.getAnim() == false) {
			// viewHolder.rl.startAnimation(animation);
			// chatBean.setAnim(true);
			// }
			viewHolder.tvContent.setBackgroundResource(selectBubble(
					chatBean.getQp(), true, false));
			viewHolder.tvContent.setTag(chatBean);
			viewHolder.tvContent.setOnLongClickListener(mTxtLongListener);
			break;
		case 1:
			final ImageView leftAnimView = viewHolder2.ivVideoAnim;
			final ImageView leftAnimViewStatic = viewHolder2.ivVideoAnimStatic;
			final ProgressBar progress = viewHolder2.loadProgerss;
			File fileleft = null;
			if (chatBean.isIscome()) {
				fileleft = new File(Environment.getExternalStorageDirectory()
						+ File.separator + context.getPackageName()
						+ ConstantsJrc.AUDIO_PATH + File.separator
						+ Commond.getMd5Hash(chatBean.getAfile()) + ".amr");
			}
			String ncolor2 = "";
			if (!TextUtils.isEmpty(chatBean.getNick_c())) {
				ncolor2 = chatBean.getNick_c().replace("#ff", "#");
			} else {
				ncolor2 = "#8c420c";
			}
			ImageView imageView2 = viewHolder2.ivUserPhoto;
			// asynImageLoader.showImageAsyn(viewHolder2.ivUserPhoto,
			// chatBean.getFheader(), R.drawable.photo);
			viewHolder2.ivUserPhoto.setTag(chatBean.getFheader());
			if (SystemUtil.getSDCardMount()) {
				picfile = new File(Environment.getExternalStorageDirectory()
						+ File.separator + context.getPackageName()
						+ ConstantsJrc.PHOTO_PATH + "/"
						+ Comment.getMd5Hash(chatBean.getFheader()));
			} else {
				picfile = new File(ConstantsJrc.PROJECT_PATH
						+ context.getPackageName() + ConstantsJrc.PHOTO_PATH
						+ "/" + Comment.getMd5Hash(chatBean.getFheader()));
			}

			imageView2.setImageResource(R.drawable.photo);

			String filename2 = picfile.getPath().toString();
			Bitmap bmp2 = null;
			if (picfile.exists()) {
				bmp2 = BitmapCacheChatRoom.getIntance().getCacheBitmap(
						filename2, 0, 0);
			}

			if (bmp2 == null) {
				if (!headerurls.contains(chatBean.getFheader())) {
					headerurls.add(chatBean.getFheader());
					new Thread(new LoadImageRunnable(context, mHandler, 0,
							chatBean.getFheader(), filename2)).start();
				}
				imageView2.setImageResource(R.drawable.photo);
			} else {
				imageView2.setImageBitmap(bmp2);
			}
			// viewHolder2.ivUserPhoto.setDrawingCacheEnabled(false);
			// viewHolder2.ivUserPhoto.setImageUrl(chatBean.getFheader(),
			// R.drawable.photo, Environment.getExternalStorageDirectory()
			// + File.separator + context.getPackageName()
			// + ConstantsJrc.PHOTO_PATH,
			// ConstantsJrc.PROJECT_PATH + context.getPackageName()
			// + ConstantsJrc.PHOTO_PATH);

			if (chatBean.getType() == 1) {
				tnick = chatBean.getTnick();
				viewHolder2.tvToUserName.setText("【" + tnick);
				viewHolder2.lySay.setVisibility(View.VISIBLE);
				viewHolder2.ivVideoPrivateIcon.setVisibility(View.VISIBLE);
			} else if (chatBean.getType() == 0) {
				if (!chatBean.getTnick().equals("")) {
					tnick = chatBean.getTnick();
					viewHolder2.tvToUserName.setText("【" + chatBean.getTnick());
					viewHolder2.lySay.setVisibility(View.VISIBLE);
					viewHolder2.ivVideoPrivateIcon.setVisibility(View.GONE);
				} else {
					viewHolder2.lySay.setVisibility(View.GONE);
					viewHolder2.ivVideoPrivateIcon.setVisibility(View.GONE);
				}
			}

			if (chatBean.getAudioauto() == false) {
				if (isHaveAudioFile(chatBean.getAfile(),
						Environment.getExternalStorageDirectory()
								+ File.separator + context.getPackageName()
								+ ConstantsJrc.AUDIO_PATH,
						ConstantsJrc.PROJECT_PATH + context.getPackageName()
								+ ConstantsJrc.AUDIO_PATH)) {
					viewHolder2.loadProgerss.setVisibility(View.GONE);
				}
			} else {
				if (chatBean.getIsdown() == false) {
					downFile.down(
							chatBean.getAfile(),
							Environment.getExternalStorageDirectory()
									+ File.separator + context.getPackageName()
									+ ConstantsJrc.AUDIO_PATH,
							ConstantsJrc.PROJECT_PATH
									+ context.getPackageName()
									+ ConstantsJrc.AUDIO_PATH,
							viewHolder2.loadProgerss, chatBean,
							fileleft.getPath(), leftAnimView,
							leftAnimViewStatic, chatBean.getAflg());

					chatBean.setIsdown(true);
				}
			}

			viewHolder2.tvUserName.setText(Html.fromHtml("<font color=\""
					+ ncolor2 + "\">" + chatBean.getFnick() + "</font>"));
			viewHolder2.tvSendTime.setText(DateManager.strDate(
					DateManager.getDate(su.getBTime(), false),
					DateManager.getDate(chatBean.getPd(), false)));
			viewHolder2.tvVideoTime.setText(chatBean.getAlen() + "″");
			viewHolder2.tvSendTime.setVisibility(View.VISIBLE);
			viewHolder2.ivClickImage.setTag(chatBean);
			viewHolder2.ivClickImage.setOnClickListener(mListener);
			viewHolder2.lyPlayVideo.setBackgroundResource(selectBubble(
					chatBean.getQp(), true, true));
			viewHolder2.lyPlayVideo.setTag(chatBean);
			viewHolder2.lyPlayVideo.setOnLongClickListener(mAudioLongListener);
			if (chatBean.getIsplay() == false) {
				leftAnimViewStatic.setVisibility(View.VISIBLE);
				leftAnimView.setVisibility(View.GONE);
			}

			// if (chatBean.getAnim() == false) {
			// viewHolder2.rl.startAnimation(animation);
			// chatBean.setAnim(true);
			// }
			viewHolder2.lyPlayVideo.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					File file;
					if (chatBean.isIscome()) {
						if(SystemUtil.getSDCardMount()){
							file = new File(Environment.getExternalStorageDirectory()
									+ File.separator + context.getPackageName()
									+ ConstantsJrc.AUDIO_PATH + File.separator
									+ Commond.getMd5Hash(chatBean.getAfile())
									+ ".amr");
							}else{
								file = new File(ConstantsJrc.PROJECT_PATH
										+ context.getPackageName()
										+ ConstantsJrc.AUDIO_PATH + File.separator
										+ Commond.getMd5Hash(chatBean.getAfile())
										+ ".amr");
							}
					} else {
						file = new File(chatBean.getAfile());
					}

					if (!isHaveAudioFile(
							chatBean.getAfile(),
							Environment.getExternalStorageDirectory()
									+ File.separator + context.getPackageName()
									+ ConstantsJrc.AUDIO_PATH,
							ConstantsJrc.PROJECT_PATH
									+ context.getPackageName()
									+ ConstantsJrc.AUDIO_PATH)) {
						progress.setVisibility(View.VISIBLE);
						downFile.down(
								chatBean.getAfile(),
								Environment.getExternalStorageDirectory()
										+ File.separator
										+ context.getPackageName()
										+ ConstantsJrc.AUDIO_PATH,
								ConstantsJrc.PROJECT_PATH
										+ context.getPackageName()
										+ ConstantsJrc.AUDIO_PATH, progress,
								chatBean, file.getPath(), leftAnimView,
								leftAnimViewStatic, chatBean.getAflg());
					}

					if (chatBean.getAudioauto() == false) {
						if (!isHaveAudioFile(
								chatBean.getAfile(),
								Environment.getExternalStorageDirectory()
										+ File.separator
										+ context.getPackageName()
										+ ConstantsJrc.AUDIO_PATH,
								ConstantsJrc.PROJECT_PATH
										+ context.getPackageName()
										+ ConstantsJrc.AUDIO_PATH)) {
							progress.setVisibility(View.VISIBLE);
							downFile.down(
									chatBean.getAfile(),
									Environment.getExternalStorageDirectory()
											+ File.separator
											+ context.getPackageName()
											+ ConstantsJrc.AUDIO_PATH,
									ConstantsJrc.PROJECT_PATH
											+ context.getPackageName()
											+ ConstantsJrc.AUDIO_PATH,
									progress, chatBean, file.getPath(),
									leftAnimView, leftAnimViewStatic,
									chatBean.getAflg());
							chatBean.setAudioauto(true);
							return;
						}

					}

					if (file.exists()) {
						Uri uri = Uri.fromFile(file);
						stopAnimImageView();
						PlayMusic(chatBean, file.getPath(), leftAnimView,
								leftAnimViewStatic, true);
					} else {
						if (chatBean.getIsdown() == false) {
							DownFile downFile = new DownFile();
							downFile.down(
									chatBean.getAfile(),
									Environment.getExternalStorageDirectory()
											+ File.separator
											+ context.getPackageName()
											+ ConstantsJrc.AUDIO_PATH,
									ConstantsJrc.PROJECT_PATH
											+ context.getPackageName()
											+ ConstantsJrc.AUDIO_PATH,
									progress, chatBean, file.getPath(),
									leftAnimView, leftAnimViewStatic,
									chatBean.getAflg());
							chatBean.setIsdown(false);
						}
					}
				}
			});

			break;
		case 2:

			if (chatBean.getType() == 1) {
				viewHolder3.pivateIcon.setVisibility(View.VISIBLE);
			} else {
				viewHolder3.pivateIcon.setVisibility(View.GONE);
			}
			String ncolor3 = "";
			if (!TextUtils.isEmpty(chatBean.getNick_c())) {
				ncolor3 = chatBean.getNick_c().replace("#ff", "#");
			} else {
				ncolor3 = "#8c420c";
			}
			ImageView imageView3 = viewHolder3.ivUserPhoto;
			// asynImageLoader.showImageAsyn(imageView3, chatBean.getFheader(),
			// R.drawable.photo);
			viewHolder3.ivUserPhoto.setTag(chatBean.getFheader());
			if (SystemUtil.getSDCardMount()) {
				picfile = new File(Environment.getExternalStorageDirectory()
						+ File.separator + context.getPackageName()
						+ ConstantsJrc.PHOTO_PATH + "/"
						+ Comment.getMd5Hash(chatBean.getFheader()));
			} else {
				picfile = new File(ConstantsJrc.PROJECT_PATH
						+ context.getPackageName() + ConstantsJrc.PHOTO_PATH
						+ "/" + Comment.getMd5Hash(chatBean.getFheader()));
			}

			imageView3.setImageResource(R.drawable.photo);

			String filename3 = picfile.getPath().toString();
			Bitmap bmp3 = null;
			if (picfile.exists()) {
				bmp3 = BitmapCacheChatRoom.getIntance().getCacheBitmap(
						filename3, 0, 0);
			}

			if (bmp3 == null) {
				if (!headerurls.contains(chatBean.getFheader())) {
					headerurls.add(chatBean.getFheader());
					new Thread(new LoadImageRunnable(context, mHandler, 0,
							chatBean.getFheader(), filename3)).start();
				}
				imageView3.setImageResource(R.drawable.photo);
			} else {
				imageView3.setImageBitmap(bmp3);
			}

			LayoutParams para3;
			para3 = viewHolder3.loadImage.getLayoutParams();
			para3.width = chatBean.getPw();
			para3.height = chatBean.getPh();
			viewHolder3.loadImage.setLayoutParams(para3);
			viewHolder3.loadImageBg.setLayoutParams(para3);
			viewHolder3.loadNoImage.setLayoutParams(para3);
			viewHolder3.loadImage.setDrawingCacheEnabled(false);

			if (chatBean.getIspicauto() == false) {
				if (isHavePicFile(chatBean.getPfile(),
						Environment.getExternalStorageDirectory()
								+ File.separator + context.getPackageName()
								+ ConstantsJrc.IMAGE_PATH,
						ConstantsJrc.PROJECT_PATH + context.getPackageName()
								+ ConstantsJrc.IMAGE_PATH)) {
					viewHolder3.loadNoImage.setVisibility(View.GONE);
					viewHolder3.loadImage.setVisibility(View.VISIBLE);
					viewHolder3.loadImage.setImageUrl(
							chatBean.getPfile(),
							R.drawable.defaultpic,
							Environment.getExternalStorageDirectory()
									+ File.separator + context.getPackageName()
									+ ConstantsJrc.IMAGE_PATH,
							ConstantsJrc.PROJECT_PATH
									+ context.getPackageName()
									+ ConstantsJrc.IMAGE_PATH);
				} else {
					viewHolder3.loadNoImage.setVisibility(View.VISIBLE);
					viewHolder3.loadImage.setVisibility(View.GONE);
				}
			} else {
				viewHolder3.loadNoImage.setVisibility(View.GONE);
				viewHolder3.loadImage.setVisibility(View.VISIBLE);
				viewHolder3.loadImage.setImageUrl(chatBean.getPfile(),
						R.drawable.defaultpic,
						Environment.getExternalStorageDirectory()
								+ File.separator + context.getPackageName()
								+ ConstantsJrc.IMAGE_PATH,
						ConstantsJrc.PROJECT_PATH + context.getPackageName()
								+ ConstantsJrc.IMAGE_PATH);
			}

			viewHolder3.tvUserName.setText(Html.fromHtml("<font color=\""
					+ ncolor3 + "\">" + chatBean.getFnick() + "</font>"));
			viewHolder3.tvSendTime.setText(DateManager.strDate(
					DateManager.getDate(su.getBTime(), false),
					DateManager.getDate(chatBean.getPd(), false)));
			viewHolder3.progressBar.setVisibility(View.GONE);
			viewHolder3.ivClickImage.setTag(chatBean);
			viewHolder3.ivClickImage.setOnClickListener(mListener);
			viewHolder3.lyclick.setBackgroundResource(selectBubble(
					chatBean.getQp(), true, false));
			viewHolder3.lyclick.setTag(chatBean);
			viewHolder3.lyclick.setOnLongClickListener(mPicLongListener);
			// if (chatBean.getAnim() == false) {
			// viewHolder3.rl.startAnimation(animation);
			// chatBean.setAnim(true);
			// }
			final NetImageView imageLoadNoView = viewHolder3.loadNoImage;
			final NetImageView imageLoadView = viewHolder3.loadImage;
			viewHolder3.lyclick.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (imageLoadNoView.getVisibility() == View.VISIBLE) {
						imageLoadNoView.setVisibility(View.GONE);
						imageLoadView.setVisibility(View.VISIBLE);
						chatBean.setIspicauto(true);
						imageLoadView.setImageUrl(
								chatBean.getPfile(),
								R.drawable.defaultpic,
								Environment.getExternalStorageDirectory()
										+ File.separator
										+ context.getPackageName()
										+ ConstantsJrc.IMAGE_PATH,
								ConstantsJrc.PROJECT_PATH
										+ context.getPackageName()
										+ ConstantsJrc.IMAGE_PATH);
						return;
					}

					String imgurl = "";
					String downpath = "";
					String savepath = "";
					if (chatBean.isIscome()) {
						imgurl = ConstantsJrc.GETBIGIMAGE
								+ "?url="
								+ URLEncoder.encode(chatBean.getPfile())
								+ "&uid="
								+ chatBean.getFuid()
								+ "&sw="
								+ PhoneInfo.getInstance(context).getWidth(
										activity)
								+ "&sh="
								+ PhoneInfo.getInstance(context).getHight(
										activity);
					} else {
						imgurl = chatBean.getPlocalfile();
					}
					if (SystemUtil.getSDCardMount()) {
						downpath = Environment.getExternalStorageDirectory()
								+ File.separator + context.getPackageName()
								+ ConstantsJrc.IMAGE_PATH;
						savepath = Environment.getExternalStorageDirectory()
								+ ConstantsJrc.SAVE_PATH;
					} else {
						downpath = ConstantsJrc.PROJECT_PATH
								+ context.getPackageName()
								+ ConstantsJrc.IMAGE_PATH;
						savepath = ConstantsJrc.PROJECT_PATH
								+ context.getPackageName()
								+ ConstantsJrc.SAVE_PATH;
					}

					Intent intent = new Intent(context.getApplicationContext(),
							ImageZoom.class);
					// 需要请求图片的url(也可以是本地文件)
					intent.putExtra("imageurl", imgurl);
					// 网路图片下载后保存的文件加路径
					intent.putExtra("downpath", downpath);
					// 图片需要保存的文件夹路径
					intent.putExtra("savepath", savepath);
					context.startActivity(intent);
					int version = Integer.valueOf(android.os.Build.VERSION.SDK);
					if (version > 5) {
						((Activity) context).overridePendingTransition(
								R.anim.zoom_enter, R.anim.zoom_exit);
					}
				}
			});

			break;
		case 3:

			String color4 = "";
			String b4 = "";
			String b41 = "";
			String ncolor4 = "";
			// 设置文字的颜色
			if (!TextUtils.isEmpty(chatBean.getContent_c())) {
				color4 = chatBean.getContent_c().replace("#ff", "#");
			} else {
				color4 = "#0d3c52";
			}

			if (!TextUtils.isEmpty(chatBean.getNick_c())) {
				ncolor4 = chatBean.getNick_c().replace("#ff", "#");
			} else {
				ncolor4 = "#8c420c";
			}
			// 是否可以点击链接
			viewHolder4.tvContent.setAutoLinkMask(chatBean.getContent_l());
			// 设置文字是否为粗体
			if (chatBean.getContent_b() == 1) {
				b4 = "<b>";
				b41 = "</b>";
			}
			// 转换表情
			if (chatBean.getType() == 1) {
				TextPaint tpaint = viewHolder4.tvContent.getPaint();
				tpaint.setFakeBoldText(false);
				CharSequence convertContent = SmileyParser
						.getInstance()
						.addSmileySpans(
								Html.fromHtml("<font color=\"#c0390f\">对</font>"
										+ "<font color=\"#0062bd\">【"
										+ chatBean.getTnick()
										+ "】</font><font color=\"#c0390f\">说：</font>"
										+ b4
										+ "<font color=\""
										+ color4
										+ "\">"
										+ chatBean.getContent()
										+ "</font>" + b41 + "</body>"));
				viewHolder4.tvContent.setText(convertContent);
				viewHolder4.ivPrivateIcon.setVisibility(View.VISIBLE);
			} else if (chatBean.getType() == 0) {
				TextPaint tpaint = viewHolder4.tvContent.getPaint();
				tpaint.setFakeBoldText(false);
				if (!chatBean.getTnick().equals("")) {
					tnick = chatBean.getTnick();
					CharSequence convertContent = SmileyParser
							.getInstance()
							.addSmileySpans(
									Html.fromHtml("<font color=\"#c0390f\">对</font>"
											+ "<font color=\"#0062bd\">【"
											+ tnick
											+ "】</font><font color=\"#c0390f\">说：</font>"
											+ b4
											+ "<font color=\""
											+ color4
											+ "\">"
											+ chatBean.getContent()
											+ "</font>" + b41 + "</body>"));
					viewHolder4.tvContent.setText(convertContent);
					viewHolder4.ivPrivateIcon.setVisibility(View.GONE);
				} else {
					// 设置文字是否为粗体
					if (chatBean.getContent_b() == 1) {
						TextPaint tpaint4 = viewHolder4.tvContent.getPaint();
						tpaint.setFakeBoldText(true);
					} else {
						TextPaint tpaint4 = viewHolder4.tvContent.getPaint();
						tpaint.setFakeBoldText(false);
					}
					viewHolder4.tvContent
							.setTextColor(Color.parseColor(color4));
					CharSequence convertContent = SmileyParser.getInstance()
							.addSmileySpans(chatBean.getContent());
					viewHolder4.tvContent.setText(convertContent);
					viewHolder4.ivPrivateIcon.setVisibility(View.GONE);
				}

			}

			ImageView imageView4 = viewHolder4.ivUserPhoto;
			// 异步加载图片，缓存下载

			// viewHolder4.ivUserPhoto.setDrawingCacheEnabled(false);
			viewHolder4.ivUserPhoto.setImageUrl(chatBean.getFheader(),
					R.drawable.photo, Environment.getExternalStorageDirectory()
							+ File.separator + context.getPackageName()
							+ ConstantsJrc.PHOTO_PATH,
					ConstantsJrc.PROJECT_PATH + context.getPackageName()
							+ ConstantsJrc.PHOTO_PATH);

			viewHolder4.tvSendTime.setVisibility(View.VISIBLE);
			viewHolder4.tvUserName.setText(Html.fromHtml("<font color=\""
					+ ncolor4 + "\">" + su.getNick() + "</font>"));
			if (chatBean.isIscome()) {
				viewHolder4.tvSendTime.setText(DateManager.strDate(
						DateManager.getDate(su.getBTime(), false),
						DateManager.getDate(chatBean.getPd(), false)));

			} else {
				viewHolder4.tvSendTime.setText(DateManager.strDate(
						DateManager.getDate(DateManager.getPhoneTime(), false),
						DateManager.getDate(chatBean.getPd(), false)));
				// viewHolder4.tvSendTime.setText(chatBean.getDatetime());
			}

			if (chatBean.getIsOK() == 0) {
				// viewHolder4.btError.setVisibility(View.VISIBLE);
				if (chatBean.getIssend() == false) {
					sendThread = new SendMsgThread(chatBean.getRoomid(),
							chatBean.getType(), String.valueOf(chatBean
									.getFuid()), chatBean.getTuid(),
							chatBean.getContent(), "", "", "", 0,
							viewHolder4.progressBar, viewHolder4.btError,
							chatBean);
					// viewHolder4.progressBar.setVisibility(View.VISIBLE);
					sendThread.start();
					chatBean.setIssend(true);// 标记正在发送禁止操作
				}
			}

			viewHolder4.ivClickImage.setTag(chatBean);
			viewHolder4.ivClickImage.setOnClickListener(mListener);

			viewHolder4.tvContent.setBackgroundResource(selectBubble(
					chatBean.getQp(), false, false));
			viewHolder4.tvContent.setTag(chatBean);
			viewHolder4.tvContent.setOnLongClickListener(mTxtLongListener);
			// if (chatBean.getAnim() == false) {
			// viewHolder4.rl.startAnimation(animation);
			// chatBean.setAnim(true);
			// }
			final ProgressBar pbar = viewHolder4.progressBar;
			final ImageView errorBtn = viewHolder4.btError;
			viewHolder4.btError.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					sendThread = new SendMsgThread(chatBean.getRoomid(),
							chatBean.getType(), String.valueOf(chatBean
									.getFuid()), String.valueOf(chatBean
									.getTuid()), chatBean.getContent(), "", "",
							"", 0, pbar, errorBtn, chatBean);
					// pbar.setVisibility(View.VISIBLE);
					errorBtn.setVisibility(View.GONE);
					sendThread.start();
				}
			});
			break;
		case 4:
			final ImageView rightAnimView = viewHolder5.ivVideoAnim;
			final ImageView rightAnimViewStatic = viewHolder5.ivVideoAnimStatic;
			File fileright = null;
			if (chatBean.isIscome()) {
				fileright = new File(Environment.getExternalStorageDirectory()
						+ File.separator + context.getPackageName()
						+ ConstantsJrc.AUDIO_PATH + File.separator
						+ Commond.getMd5Hash(chatBean.getAfile()) + ".amr");
			} else {
				fileright = new File(chatBean.getAfile());
			}

			String ncolor5 = "";
			if (!TextUtils.isEmpty(chatBean.getNick_c())) {
				ncolor5 = chatBean.getNick_c().replace("#ff", "#");
			} else {
				ncolor5 = "#8c420c";
			}
			// viewHolder5.ivUserPhoto.setDrawingCacheEnabled(false);
			viewHolder5.ivUserPhoto.setImageUrl(chatBean.getFheader(),
					R.drawable.photo, Environment.getExternalStorageDirectory()
							+ File.separator + context.getPackageName()
							+ ConstantsJrc.PHOTO_PATH,
					ConstantsJrc.PROJECT_PATH + context.getPackageName()
							+ ConstantsJrc.PHOTO_PATH);

			if (chatBean.getType() == 1) {
				tnick = chatBean.getTnick();
				viewHolder5.tvToUserName.setText("【" + tnick);
				viewHolder5.lySay.setVisibility(View.VISIBLE);
				viewHolder5.ivVideoPrivateIcon.setVisibility(View.VISIBLE);
			} else if (chatBean.getType() == 0) {
				if (!chatBean.getTnick().equals("")) {
					tnick = chatBean.getTnick();
					viewHolder5.tvToUserName.setText("【" + tnick);
					viewHolder5.lySay.setVisibility(View.VISIBLE);
					viewHolder5.ivVideoPrivateIcon.setVisibility(View.GONE);
				} else {
					viewHolder5.lySay.setVisibility(View.GONE);
					viewHolder5.ivVideoPrivateIcon.setVisibility(View.GONE);
				}
			}

			if (chatBean.isIscome()) {
				viewHolder5.tvSendTime.setText(DateManager.strDate(
						DateManager.getDate(su.getBTime(), false),
						DateManager.getDate(chatBean.getPd(), false)));
				if (chatBean.getIsdown() == false) {
					downFileRight.down(
							chatBean.getAfile(),
							Environment.getExternalStorageDirectory()
									+ File.separator + context.getPackageName()
									+ ConstantsJrc.AUDIO_PATH,
							ConstantsJrc.PROJECT_PATH
									+ context.getPackageName()
									+ ConstantsJrc.AUDIO_PATH, null);
					chatBean.setIsdown(true);
				}
			} else {
				viewHolder5.tvSendTime.setText(DateManager.strDate(
						DateManager.getDate(DateManager.getPhoneTime(), false),
						DateManager.getDate(chatBean.getPd(), false)));
			}

			viewHolder5.tvVideoTime.setText(chatBean.getAlen() + "″");
			viewHolder5.tvUserName.setText(Html.fromHtml("<font color=\""
					+ ncolor5 + "\">" + su.getNick() + "</font>"));
			viewHolder5.tvSendTime.setVisibility(View.VISIBLE);
			viewHolder5.btError.setVisibility(View.GONE);
			viewHolder5.ivClickImage.setTag(chatBean);
			viewHolder5.ivClickImage.setOnClickListener(mListener);
			viewHolder5.rlPlayVideo.setBackgroundResource(selectBubble(
					chatBean.getQp(), false, true));
			viewHolder5.rlPlayVideo.setTag(chatBean);
			viewHolder5.rlPlayVideo.setOnLongClickListener(mAudioLongListener);

			if (chatBean.getIsplay() == false) {
				rightAnimViewStatic.setVisibility(View.VISIBLE);
				rightAnimView.setVisibility(View.GONE);
			}
			// if (chatBean.getAnim() == false) {
			// viewHolder5.rl.startAnimation(animation);
			// chatBean.setAnim(true);
			// }

			if (chatBean.getIsOK() == 0) {
				// viewHolder4.btError.setVisibility(View.VISIBLE);
				if (chatBean.getIssend() == false) {
					sendThread = new SendMsgThread(chatBean.getRoomid(),
							chatBean.getType(), String.valueOf(chatBean
									.getFuid()), chatBean.getTuid(), "",
							chatBean.getAfile(), "", "afile",
							Integer.parseInt(chatBean.getAlen()),
							viewHolder5.progressBar, viewHolder5.btError,
							chatBean);
					// viewHolder5.progressBar.setVisibility(View.VISIBLE);
					sendThread.start();
					chatBean.setIssend(true);// 标记正在发送禁止操作
				}
			}

			final ProgressBar pbar5 = viewHolder5.progressBar;
			final ImageView errorBtn5 = viewHolder5.btError;
			viewHolder5.btError.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					sendThread = new SendMsgThread(chatBean.getRoomid(),
							chatBean.getType(), String.valueOf(chatBean
									.getFuid()), chatBean.getTuid(), "",
							chatBean.getAfile(), "", "afile", Integer
									.parseInt(chatBean.getAlen()), pbar5,
							errorBtn5, chatBean);
					// pbar5.setVisibility(View.VISIBLE);
					errorBtn5.setVisibility(View.GONE);
					sendThread.start();
					chatBean.setIssend(true);// 标记正在发送禁止操作
				}
			});

			viewHolder5.rlPlayVideo.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					File file;
					if (chatBean.isIscome()) {
						if(SystemUtil.getSDCardMount()){
							file = new File(Environment.getExternalStorageDirectory()
									+ File.separator + context.getPackageName()
									+ ConstantsJrc.AUDIO_PATH + File.separator
									+ Commond.getMd5Hash(chatBean.getAfile())
									+ ".amr");
							}else{
								file = new File(ConstantsJrc.PROJECT_PATH
										+ context.getPackageName()
										+ ConstantsJrc.AUDIO_PATH + File.separator
										+ Commond.getMd5Hash(chatBean.getAfile())
										+ ".amr");
							}
					} else {
						file = new File(chatBean.getAfile());
					}

					if (file.exists()) {
						Uri uri = Uri.fromFile(file);
						stopAnimImageView();
						PlayMusic(chatBean, file.getPath(), rightAnimView,
								rightAnimViewStatic, false);

					} else {
						if (chatBean.getIsdown() == false) {
							DownFileRight downFileRight = new DownFileRight();
							downFileRight.down(
									chatBean.getAfile(),
									Environment.getExternalStorageDirectory()
											+ File.separator
											+ context.getPackageName()
											+ ConstantsJrc.AUDIO_PATH,
									ConstantsJrc.PROJECT_PATH
											+ context.getPackageName()
											+ ConstantsJrc.AUDIO_PATH, null);
							chatBean.setIsdown(true);
						}
					}
				}
			});

			break;
		case 5:
			if (chatBean.getType() == 1) {
				viewHolder6.pivateIcon.setVisibility(View.VISIBLE);
			} else {
				viewHolder6.pivateIcon.setVisibility(View.GONE);
			}
			String ncolor6 = "";
			if (!TextUtils.isEmpty(chatBean.getNick_c())) {
				ncolor6 = chatBean.getNick_c().replace("#ff", "#");
			} else {
				ncolor6 = "#8c420c";
			}
			ImageView imageView5 = viewHolder6.ivUserPhoto;
			// 异步加载图片，缓存下载

			// viewHolder6.ivUserPhoto.setDrawingCacheEnabled(false);
			viewHolder6.ivUserPhoto.setImageUrl(chatBean.getFheader(),
					R.drawable.photo, Environment.getExternalStorageDirectory()
							+ File.separator + context.getPackageName()
							+ ConstantsJrc.PHOTO_PATH,
					ConstantsJrc.PROJECT_PATH + context.getPackageName()
							+ ConstantsJrc.PHOTO_PATH);
			if (chatBean.isIscome()) {
				LayoutParams para6;
				para6 = viewHolder6.loadImage.getLayoutParams();
				para6.width = chatBean.getPw();
				para6.height = chatBean.getPh();

				viewHolder6.loadImage.setLayoutParams(para6);
				viewHolder6.loadImageBg.setLayoutParams(para6);
				viewHolder6.loadImage.setDrawingCacheEnabled(false);
				viewHolder6.loadImage.setImageResource(R.drawable.defaultpic);
				viewHolder6.loadImage.setImageUrl(chatBean.getPfile(),
						R.drawable.defaultpic,
						Environment.getExternalStorageDirectory()
								+ File.separator + context.getPackageName()
								+ ConstantsJrc.IMAGE_PATH,
						ConstantsJrc.PROJECT_PATH + context.getPackageName()
								+ ConstantsJrc.IMAGE_PATH);
				viewHolder6.tvSendTime.setText(DateManager.strDate(
						DateManager.getDate(su.getBTime(), false),
						DateManager.getDate(chatBean.getPd(), false)));
			} else {
				LayoutParams para6;
				para6 = viewHolder6.loadImage.getLayoutParams();
				if (chatBean.getPw() > 240) {
					if (chatBean.getPw() > chatBean.getPh()) {
						para6.width = (int) (chatBean.getPw() / 2.75);
						para6.height = (int) (chatBean.getPh() / 2.75);
					} else {
						para6.width = (int) (chatBean.getPw() / 2.75);
						para6.height = (int) (chatBean.getPh() / 2.75);
					}
				} else if (chatBean.getPh() > 320) {
					if (chatBean.getPw() > chatBean.getPh()) {
						para6.width = (int) (chatBean.getPw() / 2.75);
						para6.height = (int) (chatBean.getPh() / 2.75);
					} else {
						para6.width = (int) (chatBean.getPw() / 2.75);
						para6.height = (int) (chatBean.getPh() / 2.75);
					}
				} else {
					para6.width = chatBean.getPw();
					para6.height = chatBean.getPh();
				}
				Bitmap bmp = BitmapFactory.decodeFile(chatBean.getPfile());
				viewHolder6.loadImage.setLayoutParams(para6);
				viewHolder6.loadImageBg.setLayoutParams(para6);
				viewHolder6.loadImage.setImageBitmap(bmp);
				viewHolder6.tvSendTime.setText(DateManager.strDate(
						DateManager.getDate(DateManager.getPhoneTime(), false),
						DateManager.getDate(chatBean.getPd(), false)));
			}

			viewHolder6.tvUserName.setText(Html.fromHtml("<font color=\""
					+ ncolor6 + "\">" + su.getNick() + "</font>"));
			viewHolder6.progressBar.setVisibility(View.GONE);
			viewHolder6.lyclick.setBackgroundResource(selectBubble(
					chatBean.getQp(), false, false));
			viewHolder6.lyclick.setTag(chatBean);
			viewHolder6.lyclick.setOnClickListener(mPicListener);
			viewHolder6.lyclick.setTag(chatBean);
			viewHolder6.lyclick.setOnLongClickListener(mPicLongListener);
			viewHolder6.ivClickImage.setTag(chatBean);
			viewHolder6.ivClickImage.setOnClickListener(mListener);
			// if (chatBean.getAnim() == false) {
			// viewHolder6.rl.startAnimation(animation);
			// chatBean.setAnim(true);
			// }
			if (chatBean.getIsOK() == 0) {
				// viewHolder4.btError.setVisibility(View.VISIBLE);
				if (chatBean.getIssend() == false) {
					sendThread = new SendMsgThread(chatBean.getRoomid(),
							chatBean.getType(), String.valueOf(chatBean
									.getFuid()), chatBean.getTuid(), "", "",
							chatBean.getPfile(), "pfile", 0,
							viewHolder6.progressBar, viewHolder6.btError,
							chatBean);
					// viewHolder6.progressBar.setVisibility(View.VISIBLE);
					sendThread.start();
					chatBean.setIssend(true);// 标记正在发送禁止操作
				}
			}

			final ProgressBar pbar6 = viewHolder6.progressBar;
			final ImageView errorBtn6 = viewHolder6.btError;
			viewHolder6.btError.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					sendThread = new SendMsgThread(chatBean.getRoomid(),
							chatBean.getType(), String.valueOf(chatBean
									.getFuid()), chatBean.getTuid(), "",
							chatBean.getAfile(), "", "afile", Integer
									.parseInt(chatBean.getAlen()), pbar6,
							errorBtn6, chatBean);
					// pbar6.setVisibility(View.VISIBLE);
					errorBtn6.setVisibility(View.GONE);
					sendThread.start();
					chatBean.setIssend(true);// 标记正在发送禁止操作
				}
			});
			break;
		case 6:
			viewHolder7.tvSysContent.setText(chatBean.getContent());
			ImageView imageView7 = viewHolder7.ivSysIcon;
			if (SystemUtil.getSDCardMount()) {
				picfile = new File(Environment.getExternalStorageDirectory()
						+ File.separator + context.getPackageName()
						+ ConstantsJrc.PHOTO_PATH + "/"
						+ Comment.getMd5Hash(chatBean.getIcon()));
			} else {
				picfile = new File(ConstantsJrc.PROJECT_PATH
						+ context.getPackageName() + ConstantsJrc.PHOTO_PATH
						+ "/" + Comment.getMd5Hash(chatBean.getIcon()));
			}
			imageView7.setImageResource(R.drawable.chat_system_mid_icon);
			String filename7 = picfile.getPath().toString();
			Bitmap bmp7 = null;
			if (picfile.exists()) {
				bmp7 = BitmapCacheChatRoom.getIntance().getCacheBitmap(
						filename7, 0, 0);
			}

			if (bmp7 == null) {
				if (!headerurls.contains(chatBean.getIcon())) {
					headerurls.add(chatBean.getIcon());
					new Thread(new LoadImageRunnable(context, mHandler, 0,
							chatBean.getIcon(), filename7)).start();
				}
				imageView7.setImageResource(R.drawable.chat_system_mid_icon);
			} else {
				imageView7.setImageBitmap(bmp7);
			}

			// if (chatBean.getAnim() == false) {
			// viewHolder7.rl.startAnimation(animation);
			// chatBean.setAnim(true);
			// }
		}

		return convertView;
	}

	private void start_animation(int id, ImageView imageview) {
		imageview.setImageResource(id);
		animationDrawable = (AnimationDrawable) imageview.getDrawable();
		animationDrawable.setOneShot(false);
		animationDrawable.start();
	}

	private void stop_animation() {
		if (animationDrawable != null)
			animationDrawable.stop();
	}

	public void clear() {
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
			// mediaPlayer.release();
		}
		try {
			mediaPlayer.prepare();
			mediaPlayer.seekTo(0);
		} catch (Exception e) {
		}
		isPlay = false;
		stop_animation();
	}

	/**
	 * 选择聊天气泡
	 * 
	 * @param qp
	 *            气泡类型
	 * @param leftright
	 *            区分左右聊天气泡，true=左，false=右
	 * @param isaudio
	 *            区分文字音频默认气泡，true 为音频气泡
	 * @return
	 */
	public int selectBubble(int qp, boolean leftright, boolean isaudio) {
		// leftright == ture 是左边气泡，leftright == false 是右边气泡
		// isaudio == true 语音默认气泡

		if (leftright == true) {
			switch (qp) {
			case 0:
				if (isaudio == true) {
					qptype = R.drawable.chat_left_video_bg;
				} else {
					qptype = R.drawable.chat_left_bg;
				}
				break;
			case 1:
				qptype = R.drawable.chat_left_bg1;
				break;
			case 2:
				qptype = R.drawable.chat_left_bg2;
				break;
			case 3:
				qptype = R.drawable.chat_left_bg3;
				break;
			case 4:
				qptype = R.drawable.chat_left_bg4;
				break;
			case 5:
				qptype = R.drawable.chat_left_bg5;
				break;
			case 6:
				qptype = R.drawable.chat_left_bg6;
				break;
			case 7:
				qptype = R.drawable.chat_left_bg7;
				break;
			case 8:
				qptype = R.drawable.chat_left_bg8;
				break;
			case 9:
				qptype = R.drawable.chat_left_bg9;
				break;
			case 10:
				qptype = R.drawable.chat_left_bg10;
				break;
			case 11:
				qptype = R.drawable.chat_left_bg11;
				break;
			case 12:
				qptype = R.drawable.chat_left_bg12;
				break;
			case 13:
				qptype = R.drawable.chat_jinlong_left1;
				break;
			case 14:
				qptype = R.drawable.chat_wanrenmi1;
				break;
			case 15:
				qptype = R.drawable.chat_tuhao_left1;
				break;
			case 16:
				qptype = R.drawable.onemy1;
				break;
			case 17:
				qptype = R.drawable.twomy1;
				break;
			case 18:
				qptype = R.drawable.threemy1;
				break;
			case 19:
				qptype = R.drawable.fourmy1;
				break;
			case 20:
				qptype = R.drawable.fivemy1;
				break;
			case 21:
				qptype = R.drawable.sixmy1;
				break;
			case 22:
				qptype = R.drawable.sevenmy1;
				break;
			case 23:
				qptype = R.drawable.eightmy1;
				break;	
			case 24:
				qptype = R.drawable.nineother;
				break;	
			case 25:
				qptype = R.drawable.tenother;
				break;	
			default:
				qptype = R.drawable.chat_left_bg;
				break;
			}
		} else if (leftright == false) {
			switch (qp) {
			case 0:
				if (isaudio == true) {
					qptype = R.drawable.chat_right_video_bg;
				} else {
					qptype = R.drawable.chat_right_bg;
				}
				break;
			case 1:
				qptype = R.drawable.chat_right_bg1;
				break;
			case 2:
				qptype = R.drawable.chat_right_bg2;
				break;
			case 3:
				qptype = R.drawable.chat_right_bg3;
				break;
			case 4:
				qptype = R.drawable.chat_right_bg4;
				break;
			case 5:
				qptype = R.drawable.chat_right_bg5;
				break;
			case 6:
				qptype = R.drawable.chat_right_bg6;
				break;
			case 7:
				qptype = R.drawable.chat_right_bg7;
				break;
			case 8:
				qptype = R.drawable.chat_right_bg8;
				break;
			case 9:
				qptype = R.drawable.chat_right_bg9;
				break;
			case 10:
				qptype = R.drawable.chat_right_bg10;
				break;
			case 11:
				qptype = R.drawable.chat_right_bg11;
				break;
			case 12:
				qptype = R.drawable.chat_right_bg12;
				break;
			case 13:
				qptype = R.drawable.chat_jinlong_right1;
				break;
			case 14:
				qptype = R.drawable.chat_wanrenmi1;
				break;
			case 15:
				qptype = R.drawable.chat_tuhao_right1;
				break;
			case 16:
				qptype = R.drawable.oneother1;
				break;
			case 17:
				qptype = R.drawable.twoother2;
				break;
			case 18:
				qptype = R.drawable.threeother1;
				break;
			case 19:
				qptype = R.drawable.fourother1;
				break;
			case 20:
				qptype = R.drawable.fiveother1;
				break;
			case 21:
				qptype = R.drawable.sixother1;
				break;
			case 22:
				qptype = R.drawable.sevenother1;
				break;
			case 23:
				qptype = R.drawable.eightother1;
				break;	
			case 24:
				qptype = R.drawable.ninemy;
				break;	
			case 25:
				qptype = R.drawable.tenmy;
				break;	
			}
		}
		return qptype;
	}

	/**
	 * 播放音频
	 * 
	 * @param chatBean
	 *            实体类对象
	 * @param path
	 *            音频路径
	 * @param AnimView
	 *            音频动画
	 * @param AnimViewStatic
	 *            音频静止图片
	 * @param leftorright
	 *            左右
	 */
	public void PlayMusic(final ChatMessageBean chatBean, String path,
			final ImageView AnimView, final ImageView AnimViewStatic,
			final boolean leftorright) {
		try {

			if (mediaPlayer.isPlaying() == true) {
				chatBean.setIsplay(false);
				if (chatBean.getIsplay() == false) {
					AnimViewStatic.setVisibility(View.VISIBLE);
					AnimView.setVisibility(View.GONE);
				}
				if (mediaPlayer.isPlaying()) {
					try {
						mediaPlayer.prepare();
						mediaPlayer.seekTo(0);
					} catch (Exception e) {
						e.printStackTrace();
					}
					mediaPlayer.stop();
				}
				if (leftorright == true) {
					AnimView.setImageResource(R.drawable.left_3);
					AnimViewStatic.setVisibility(View.VISIBLE);
					stop_animation();
				} else {
					AnimView.setImageResource(R.drawable.right_3);
					AnimViewStatic.setVisibility(View.VISIBLE);
					stop_animation();
				}

				/** 如果是点击的当前的，就不自动播放 **/

				if (!path.equals(su.getAudioFlag())) {
					chatBean.setIsplay(true);
					su.setAudioFlag(path);
					if (chatBean.getIsplay() == true) {
						AnimViewStatic.setVisibility(View.GONE);
						AnimView.setVisibility(View.VISIBLE);
					}
					try {

						mediaPlayer.reset();
						File file = new File(path);
						FileInputStream fis = new FileInputStream(file);
						mediaPlayer.setDataSource(fis.getFD());
						mediaPlayer.prepare();
						mediaPlayer.start();

						if (leftorright == true) {
							AnimView.setVisibility(View.VISIBLE);
							AnimViewStatic.setVisibility(View.GONE);
							start_animation(R.anim.left_video_anim, AnimView);
						} else {
							AnimView.setVisibility(View.VISIBLE);
							AnimViewStatic.setVisibility(View.GONE);
							start_animation(R.anim.right_video_anim, AnimView);
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
					}
				}
			} else if (mediaPlayer.isPlaying() == false) {
				su.setAudioFlag(path);
				if (chatBean.getIsplay() == true) {
					AnimViewStatic.setVisibility(View.GONE);
					AnimView.setVisibility(View.VISIBLE);
				}
				try {
					mediaPlayer.reset();
					File file = new File(path);
					FileInputStream fis = new FileInputStream(file);
					mediaPlayer.setDataSource(fis.getFD());
					mediaPlayer.prepare();
					mediaPlayer.start();

					if (leftorright == true) {
						AnimView.setVisibility(View.VISIBLE);
						AnimViewStatic.setVisibility(View.GONE);
						start_animation(R.anim.left_video_anim, AnimView);
					} else {
						AnimView.setVisibility(View.VISIBLE);
						AnimViewStatic.setVisibility(View.GONE);
						start_animation(R.anim.right_video_anim, AnimView);
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
				chatBean.setIsplay(true);
			}

			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub

					if (leftorright == true) {
						AnimView.setVisibility(View.GONE);
						AnimViewStatic.setVisibility(View.VISIBLE);
					} else {
						AnimView.setVisibility(View.GONE);
						AnimViewStatic.setVisibility(View.VISIBLE);
					}
					stop_animation();
					// mediaPlayer.release();
					// mediaCompleted.start();
					// isPlay = false;
					chatBean.setIsplay(false);
				}
			});
			mediaPlayer.setOnErrorListener(new OnErrorListener() {

				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					// TODO Auto-generated method stub
					mediaPlayer.reset();
					if (leftorright == true) {
						AnimView.setVisibility(View.GONE);
						AnimViewStatic.setVisibility(View.VISIBLE);
					} else {
						AnimView.setVisibility(View.GONE);
						AnimViewStatic.setVisibility(View.VISIBLE);
					}
					stop_animation();
					// isPlay = false;
					chatBean.setIsplay(false);
					return false;
				}
			});

			mediaPlayer.setOnSeekCompleteListener(new OnSeekCompleteListener() {

				@Override
				public void onSeekComplete(MediaPlayer mp) {
					// TODO Auto-generated method stub
					if (leftorright == true) {
						AnimView.setVisibility(View.GONE);
						AnimViewStatic.setVisibility(View.VISIBLE);
					} else {
						AnimView.setVisibility(View.GONE);
						AnimViewStatic.setVisibility(View.VISIBLE);
					}
					stop_animation();
				}
			});

		} catch (Exception e) {
e.printStackTrace();
		}
	}

	/**
	 * 判断本地是否有文件
	 * 
	 * @param url
	 * @param savePath
	 * @param savePath2
	 * @return
	 */
	public boolean isHavePicFile(String url, String savePath, String savePath2) {
		String name = Commond.getMd5Hash(url);
		String filePath = NetImageViewCache.getInstance().isCacheFileIsExit(
				savePath, savePath2);
		File file = new File(filePath, name);
		if (file.exists()) {
			return true;
		}
		return false;
	}

	public boolean isHaveAudioFile(String url, String savePath, String savePath2) {
		String name = Commond.getMd5Hash(url) + ".amr";
		String filePath = NetImageViewCache.getInstance().isCacheFileIsExit(
				savePath, savePath2);
		File file = new File(filePath, name);
		if (file.exists()) {
			return true;
		}
		return false;
	}

	static class ViewHolder {
		public TextView tvSendTime;
		public TextView tvUserName;
		public TextView tvContent;
		public ImageView ivClickImage;
		public ImageView ivUserPhoto;
		public ImageView ivPrivateIcon;
		public RelativeLayout rl;// 总布局

	}

	static class ViewHolder2 {
		public TextView tvSendTime;
		public TextView tvUserName;
		public LinearLayout lyPlayVideo;
		public ImageView ivClickImage;
		public ImageView ivUserPhoto;
		public TextView tvVideoTime;
		public ImageView ivVideoAnim;
		public ImageView ivVideoAnimStatic;
		public ImageView ivVideoPrivateIcon;
		public TextView tvToUserName;
		public LinearLayout lySay;
		public ProgressBar loadProgerss;
		public RelativeLayout rl;// 总布局
	}

	static class ViewHolder3 {
		public TextView tvSendTime;
		public TextView tvUserName;
		public ImageView ivUserPhoto;
		public ImageView ivClickImage;
		public ImageView loadImageBg;
		public ProgressBar progressBar;
		public NetImageView loadImage;
		public NetImageView loadNoImage;
		public ImageView pivateIcon;
		public LinearLayout lyclick;
		public RelativeLayout rl;// 总布局
	}

	static class ViewHolder4 {
		public TextView tvSendTime;
		public TextView tvUserName;
		public TextView tvContent;
		public ImageView ivClickImage;
		public NetImageView ivUserPhoto;
		public ImageView btError;
		public ImageView ivPrivateIcon;
		public RelativeLayout rl;// 总布局
		public ProgressBar progressBar;// 进度条
	}

	static class ViewHolder5 {
		public TextView tvSendTime;
		public TextView tvUserName;
		public RelativeLayout rlPlayVideo;
		public ImageView ivClickImage;
		public NetImageView ivUserPhoto;
		public ImageView btError;
		public TextView tvVideoTime;
		public ImageView ivVideoAnim;
		public ImageView ivVideoAnimStatic;
		public ImageView ivVideoPrivateIcon;
		public TextView tvToUserName;
		public LinearLayout lySay;
		public RelativeLayout rl;// 总布局
		public ProgressBar progressBar;// 进度条
	}

	static class ViewHolder6 {
		public TextView tvSendTime;
		public TextView tvUserName;
		public NetImageView ivUserPhoto;
		public ImageView ivClickImage;
		public NetImageView loadImage;
		public ImageView loadImageBg;
		public ImageView pivateIcon;
		public LinearLayout lyclick;
		public ImageView btError;
		public RelativeLayout rl;// 总布局
		public ProgressBar progressBar;// 进度条
	}

	static class ViewHolder7 {
		public TextView tvSysContent;
		public ImageView ivSysIcon;
		public RelativeLayout rl;// 总布局
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			if (data != null) {
				String url = data.getString("url");
				String filename = data.getString("filename");

				Bitmap bmp = BitmapCacheChatRoom.getIntance().getCacheBitmap(
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

	/**
	 * 音频文件下载类
	 * 
	 * @author J.King
	 * 
	 */
	public class DownFile {

		private ArrayList<String> urls = new ArrayList<String>();

		public DownFile() {
			// TODO Auto-generated constructor stub
		}

		public void down(String url, String path, String path2,
				ProgressBar progerss, ChatMessageBean chatbean,
				String audiofile, ImageView anim, ImageView animStatic, int aflg) {
			loadFile(url, path, path2, progerss, chatbean, audiofile, anim,
					animStatic, aflg);
		}

		public void loadFile(String url, String path, String path2,
				ProgressBar progerss, ChatMessageBean chatbean,
				String audiofile, ImageView anim, ImageView animStatic, int aflg) {
			if (!isLocal(url, path, path2)) {
				if (urls.contains(url))
					return;
				urls.add(url);
				if (SystemUtil.getSDCardMount()) {
					new DownLoadFile(progerss, chatbean, audiofile, anim,
							animStatic, aflg).execute(url, path);
				} else {
					new DownLoadFile(progerss, chatbean, audiofile, anim,
							animStatic, aflg).execute(url, path2);
				}
			} else {
				progerss.setVisibility(View.GONE);
			}

		}

		/**
		 * 判断本地文件夹有没有文件
		 * 
		 * @param url
		 *            文件URL
		 * @return
		 */
		private boolean isLocal(String url, String path, String path2) {
			boolean isExit = true;

			String name = Commond.getMd5Hash(url) + ".amr";
			String filePath = isCacheFileIsExit(path, path2);

			File file = new File(filePath, name);

			if (file.exists()) {
				isExit = true;
			} else {
				isExit = false;
			}
			return isExit;
		}

		/**
		 * 判断文件夹是否存在，不存在则创建，并返回文件夹路径
		 * 
		 * @return
		 */
		private String isCacheFileIsExit(String path, String path2) {
			String filePath = "";

			if (SystemUtil.getSDCardMount()) {
				filePath = path;
				File file = new File(filePath);
				if (!file.exists()) {
					file.mkdirs();
				}
			} else {
				filePath = path2;
				File file = new File(filePath);
				if (!file.exists()) {
					file.mkdirs();
				}
			}
			return filePath;
		}

		private byte[] getBytesFromStream(InputStream inputStream) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int len = 0;
			while (len != -1) {
				try {
					len = inputStream.read(b);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (len != -1) {
					baos.write(b, 0, len);
				}
			}

			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return baos.toByteArray();
		}

		private class DownLoadFile extends AsyncTask<String, Integer, String> {
			ProgressBar pro;
			ChatMessageBean chatBean;
			ImageView AnimView;
			ImageView AnimViewStatic;
			String audiofile;
			int audioflg;

			public DownLoadFile() {
			}

			public DownLoadFile(ProgressBar p, ChatMessageBean chatbean,
					String file, ImageView anim, ImageView animstatic, int aflg) {
				this.pro = p;
				this.chatBean = chatbean;
				this.AnimView = anim;
				this.AnimViewStatic = animstatic;
				this.audiofile = file;
				this.audioflg = aflg;
			}

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
			}

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				int count;
				try {
					URL url = new URL(params[0]);
					URLConnection conexion = url.openConnection();
					conexion.connect();

					int lenghtOfFile = conexion.getContentLength();
					// Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);
					InputStream input = new BufferedInputStream(
							url.openStream());
					// Log.i("audio", filePath + "/" + fileUrl);
					OutputStream output = new FileOutputStream(params[1]
							+ File.separator + Commond.getMd5Hash(params[0])
							+ ".amr");
					byte data[] = new byte[10240];
					long total = 0;
					while ((count = input.read(data)) != -1) {
						total += count;
						publishProgress((int) ((total * 100) / lenghtOfFile));
						output.write(data, 0, count);
					}
					output.flush();
					output.close();
					input.close();
				} catch (Exception e) {
					// Log.e("error", e.getMessage().toString());
					// System.out.println(e.getMessage().toString());
				}
				return null;
			}

			@Override
			protected void onProgressUpdate(Integer... values) {
				// TODO Auto-generated method stub
				pro.setVisibility(View.VISIBLE);
				pro.setProgress(values[0]);
				if (values[0] == 100) {
					pro.setVisibility(View.GONE);
				}
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				if (pro != null) {
					pro.setVisibility(View.GONE);
				}
				// System.out.println("auto:" + su.getAudioAuto());
				if (su.getAudioAuto().equals("0")) {
					PlayMusic(chatBean, audiofile, AnimView, AnimViewStatic,
							true);
				}
				if (audioflg == 1) {
					PlayMusic(chatBean, audiofile, AnimView, AnimViewStatic,
							true);
				}
				super.onPostExecute(result);
			}

		}
	}

	/**
	 * 
	 * 发送消息
	 * 
	 * @author J.King
	 * 
	 */
	class SendMsgThread extends Thread {
		private boolean _run = true;
		int roomid = 0;
		int type = 0;
		String uid = "";
		String touid = "";
		String msg = "";
		String afile = "";
		String pfile = "";
		String filetype = "";
		int alen = 0;
		ProgressBar progressBar;
		ImageView iverror;
		ChatMessageBean chatBean;

		public void stopThread(boolean run) {
			this._run = !run;
		}

		public SendMsgThread(int roomid, int type, String uid, String touid,
				String msg, String afile, String pfile, String filetype,
				int alen, ProgressBar p, ImageView iverror,
				ChatMessageBean chatbean) {
			// TODO Auto-generated constructor stub
			this.roomid = roomid;
			this.type = type;
			this.uid = uid;
			this.touid = touid;
			this.msg = msg;
			this.afile = afile;
			this.pfile = pfile;
			this.filetype = filetype;
			this.alen = alen;
			this.progressBar = p;
			this.iverror = iverror;
			this.chatBean = chatbean;
		}

		public SendMsgThread() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			if (_run) {
				try {
					// System.out.println("执行一次");
					sendMessage(roomid, type, uid, touid, msg, afile, pfile,
							filetype, alen, progressBar, iverror, chatBean);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	private Handler msghandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case ConstantsJrc.HANDLER_SHOW_PROGRESS:
				// dialog.setCancelable(true);
				// dialog.show();
				break;
			case ConstantsJrc.HANDLER_CANCEL_PROGRESS:
				// dialog.cancel();
				sendThread.stopThread(false);
				break;
			}
		};
	};

	private void sendMessage(int roomid, int type, String uid, String touid,
			String msg, String afile, String pfile, final String filetype,
			int alen, final ProgressBar progressBar, final ImageView ivError,
			final ChatMessageBean chatbean) throws ClientProtocolException,
			IOException {
		// msghandler.sendEmptyMessage(ConstantsJrc.HANDLER_SHOW_PROGRESS);
		final String result = HttpSendMsg.SendMsgPost(roomid, type,
				su.getUid(), touid, msg, afile, pfile, filetype, alen,
				su.getToken());

		// System.out.println(result);
		MessageJson mj = new MessageJson();
		sendMessageList = mj.parseJson(result);
		// System.out.println("消息返回：" + sendMessageList.toString());

		if (sendMessageList.toString() == null) {
			// System.out.println("失败失败");
			progressBar.setVisibility(View.GONE);
			ivError.setVisibility(View.VISIBLE);
		}

		msghandler.post(new Runnable() {

			@Override
			public void run() {
				try {
					if (!sendMessageList.get(0).getTip().equals("")) {
						Commond.showToast(context, sendMessageList.get(0)
								.getTip());
					}
					if (sendMessageList.get(0).getRet().equals("1")) {
						sendThread.stopThread(false);
						progressBar.setVisibility(View.GONE);
						chatbean.setIsOK(1);
						chatbean.setIssend(true);
						ivError.setVisibility(View.GONE);
						if (filetype.equals("afile"))
							ChatRoomActivity.myMediaSend.start();
					} else if (sendMessageList.get(0).getRet().equals("0")) {
						msghandler
								.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
						sendThread.stopThread(false);

						progressBar.setVisibility(View.GONE);
						ivError.setVisibility(View.VISIBLE);
					} else if (sendMessageList.get(0).getRet().equals("")
							|| sendMessageList.get(0).getRet() == null) {
						msghandler
								.sendEmptyMessage(ConstantsJrc.HANDLER_CANCEL_PROGRESS);
						sendThread.stopThread(false);

						progressBar.setVisibility(View.GONE);
						ivError.setVisibility(View.VISIBLE);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

}
