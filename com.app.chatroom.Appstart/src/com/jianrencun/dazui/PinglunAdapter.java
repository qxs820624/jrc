package com.jianrencun.dazui;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
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
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.json.bean.ChatMessageBean;
import com.app.chatroom.otherui.VillageUserInfoDialog;
import com.app.chatroom.util.Commond;
import com.app.chatroom.util.SystemUtil;
import com.duom.fjz.iteminfo.BitmapCacheDzlb;
import com.duom.fjz.iteminfo.BitmapCacheDzpl;
import com.jianrencun.chatroom.R;
import com.jianrencun.dynamic.Dydetatil;
import com.jianrencun.dynamic.Dynamic;
import com.jianrencun.dynamic.Dyphotoitem;

public class PinglunAdapter extends BaseAdapter {

	Context context;
	ViewHolder holder = null;;
	WindowManager wm;
	int height;
	File picfile;
	Pinglunitem item;
	List<Pinglunitem> it;
	List<String> pinglunyys;
	String btime;
	ListView list;
	boolean isPlay = false;// 是否正在播放;
	public static MediaPlayer mediaPlayer;
	// MediaPlayer mediaCompleted;
	private AnimationDrawable animationDrawable;
	private ArrayList<String> urls = new ArrayList<String>();
	ImageView iv;
	boolean isbdh ;
	private String dazuidown;
	int po ;
	 DisplayMetrics dm;

	public PinglunAdapter(Context context, List<Pinglunitem> items,
			String btime, ListView list , boolean isbdh) {
		this.context = context;
		this.it = items;
		this.btime = btime;
		this.list = list;
		this.isbdh = isbdh;
		pinglunyys = new ArrayList<String>();
		dazuidown = MainMenuActivity.dazuisdown.toString();
		 dm = new DisplayMetrics();   
	        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);   	      
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return it.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View itemView = convertView;
		ImageView imageView = null;
		Drawable cachedDrawable = null;
		String uid = "";
		int autolink = 0;

		if (itemView == null) {
			itemView = LayoutInflater.from(context).inflate(
					R.layout.pinglunitem, null);
			holder = new ViewHolder();
			holder.relativell = (RelativeLayout) itemView
					.findViewById(R.id.relativell);
            holder.pinglun_header_rl = (RelativeLayout) itemView
					.findViewById(R.id.pinglun_header_rl);
			holder.pinglunll = (LinearLayout) itemView
					.findViewById(R.id.pinglunll);
			holder.uickll= (LinearLayout) itemView
					.findViewById(R.id.ll1);
			holder.content = (TextView) itemView
					.findViewById(R.id.contentofpinglun);
		
			holder.date = (TextView) itemView.findViewById(R.id.userdate);
			holder.dazui_detatil_time = (TextView) itemView
					.findViewById(R.id.dazui_detatil_time);
			holder.title = (TextView) itemView.findViewById(R.id.usertitle);
			holder.yy = (RelativeLayout) itemView.findViewById(R.id.dazuiyyll);
			holder.dazui_detatil_yy_pb = (ProgressBar) itemView
					.findViewById(R.id.dazui_detatil_yy_pb);
			holder.imageheader = (ImageView) itemView
					.findViewById(R.id.pinglunheader);
			holder.div= (ImageView) itemView
					.findViewById(R.id.dz_div);
			holder.imageheaderw = (ImageView) itemView
			.findViewById(R.id.pinglunheaderw);
			holder.dazui_detatil_yy = (ImageView) itemView
					.findViewById(R.id.dazui_detatil_yy);
			holder.numofdiscuss = (TextView)itemView.findViewById(R.id.dazui_detatil_numofdiscuss);
			holder.wlt = (ImageView)itemView.findViewById(R.id.wlt);
			itemView.setTag(holder);
		} else {
			holder = (ViewHolder) itemView.getTag();
		}

		item = it.get(position);
		if (!TextUtils.isEmpty(item.getDesc_c())) {
			holder.content.setTextColor(Color.parseColor(item.getDesc_c()));
		} else {
			holder.content.setTextColor(Color.parseColor("#482c3f"));
		}
		
		if(!TextUtils.isEmpty(item.getNumofdiscuss())){
			holder.numofdiscuss.setVisibility(View.VISIBLE);
			holder.numofdiscuss.setText(item.getNumofdiscuss());
			holder.numofdiscuss.setTextSize(12);
		}else{
			holder.numofdiscuss.setVisibility(View.GONE);
		}

		if(item.getPid() != 0){
			
			
//			holder.content.setl
			RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams)holder.imageheader.getLayoutParams();
	        linearParams.height=dm.widthPixels*1/9;
	        linearParams.width=dm.widthPixels*1/9;
	        holder.imageheader.setLayoutParams(linearParams);
			RelativeLayout.LayoutParams linearParams2 = (RelativeLayout.LayoutParams)holder.imageheaderw.getLayoutParams();
	        linearParams2.height=dm.widthPixels*1/9;
	        linearParams2.width=dm.widthPixels*1/9;
	        holder.imageheaderw.setLayoutParams(linearParams2);
	        RelativeLayout.LayoutParams linearParams3 = (RelativeLayout.LayoutParams)holder.div.getLayoutParams();
			linearParams3.height=dm.heightPixels*1/15;
	        holder.div.setLayoutParams(linearParams3);
	        holder.relativell.setBackgroundResource(R.drawable.dzui_huifulb1);
	       
	        RelativeLayout.LayoutParams linearParams4 = (RelativeLayout.LayoutParams)holder.wlt.getLayoutParams();
			linearParams4.height=dm.widthPixels*1/18;
			linearParams4.width=dm.widthPixels*1/20;
	        holder.wlt.setLayoutParams(linearParams4);
	        
	        RelativeLayout.LayoutParams linearParams5 = (RelativeLayout.LayoutParams)holder.pinglun_header_rl.getLayoutParams();
			linearParams5.height=dm.widthPixels*1/9;
			linearParams5.width=dm.widthPixels*1/9;
			holder.pinglun_header_rl.setLayoutParams(linearParams5);
	        
			holder.content.setGravity(Gravity.CENTER_VERTICAL);
			holder.content.setTextSize(15);
	        holder.title.setTextSize(12);
	        holder.date.setTextSize(12);
		}else{  	
			holder.content.setGravity(Gravity.NO_GRAVITY);
			holder.content.setTextSize(17);
			 holder.title.setTextSize(14);
			 holder.date.setTextSize(14);
			RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams)holder.imageheader.getLayoutParams();
	        linearParams.height=dm.widthPixels*1/7;
	        linearParams.width=dm.widthPixels*1/7;
	        holder.imageheader.setLayoutParams(linearParams);
			RelativeLayout.LayoutParams linearParams2 = (RelativeLayout.LayoutParams)holder.imageheaderw.getLayoutParams();
	        linearParams2.height=dm.widthPixels*1/7;
	        linearParams2.width=dm.widthPixels*1/7;
	        holder.imageheaderw.setLayoutParams(linearParams2);
	        RelativeLayout.LayoutParams linearParams3 = (RelativeLayout.LayoutParams)holder.div.getLayoutParams();
	        linearParams3.height=dm.heightPixels*1/7;
	        holder.div.setLayoutParams(linearParams3);
	        holder.relativell.setBackgroundResource(R.drawable.dazui_pinglunlb1);
	        
	        RelativeLayout.LayoutParams linearParams4 = (RelativeLayout.LayoutParams)holder.wlt.getLayoutParams();
			linearParams4.height=dm.widthPixels*1/15;
			linearParams4.width=dm.widthPixels*1/16;
	        holder.wlt.setLayoutParams(linearParams4);
	        
	        RelativeLayout.LayoutParams linearParams5 = (RelativeLayout.LayoutParams)holder.pinglun_header_rl.getLayoutParams();
			linearParams5.height=dm.widthPixels*1/6;
			linearParams5.width=dm.widthPixels*1/6; 
			holder.pinglun_header_rl.setLayoutParams(linearParams5);
		}
		if(item.getTouxiangbj() == 0){
			holder.wlt.setVisibility(View.GONE);
		}else if(item.getTouxiangbj() == 1) {
			holder.wlt.setVisibility(View.VISIBLE);
			holder.wlt.setBackgroundResource(R.drawable.mi);
		}else if(item.getTouxiangbj() == 2) {
			holder.wlt.setVisibility(View.VISIBLE);
			holder.wlt.setBackgroundResource(R.drawable.jinlong);
		}else if(item.getTouxiangbj() == 3) {
			holder.wlt.setVisibility(View.VISIBLE);
			holder.wlt.setBackgroundResource(R.drawable.hao);
		}
		else if(item.getTouxiangbj() == 4) {
			holder.wlt.setVisibility(View.VISIBLE);
//			holder.wlt.setBackgroundResource(R.drawable.caizhu);
		}
		if(!TextUtils.isEmpty(item.getNameclor())){
			holder.title.setTextColor(Color.parseColor(item.getNameclor()));
		}
		
		holder.yy.setTag(item.getAfile());
		holder.dazui_detatil_yy_pb.setTag(item.getAfile());		

		if (item.getType() == 0) {
			holder.content.setVisibility(View.VISIBLE);
			holder.yy.setVisibility(View.GONE);
			holder.content.setText(item.getDesc());
		} else if (item.getType() == 1) {
			holder.content.setVisibility(View.GONE);
			holder.yy.setVisibility(View.VISIBLE);
			holder.dazui_detatil_time.setText(String.valueOf(item.getAlen()));
		} else {
			holder.content.setVisibility(View.VISIBLE);
			holder.yy.setVisibility(View.GONE);
		}
		

		final ImageView rightAnimView = holder.dazui_detatil_yy;
		final ProgressBar pb = holder.dazui_detatil_yy_pb;

		if(item.isIsbdh()){
			start_animation(R.anim.left_video_anim, rightAnimView);
		}else{
			rightAnimView.setImageResource(R.drawable.left_3);
		}
		
		
		if(item.getAflg() == 1){
				File file;
				holder.dazui_detatil_yy.setTag(position);
				file = new File(Environment.getExternalStorageDirectory()
						+ File.separator + context.getPackageName()
						+ ConstantsJrc.AUDIO_PATH + File.separator
						+ Commond.getMd5Hash(item.getAfile())
						+ ".amr");
				if (file.exists()) {
					Uri uri = Uri.fromFile(file);
					PlayMusic(file.getPath(), rightAnimView, false , position);
				} else {

					// pinglunyys.add(item.getAfile());
					pb.setVisibility(View.VISIBLE);
					// new Thread(new myThread(item.getAfile(),
					// myHandler)).start();
					DownFile downFile = new DownFile();
					downFile.down(
							item.getAfile(),
							Environment.getExternalStorageDirectory()
									+ File.separator
									+ context.getPackageName()
									+ ConstantsJrc.AUDIO_PATH,
							ConstantsJrc.PROJECT_PATH
									+ context.getPackageName()
									+ ConstantsJrc.AUDIO_PATH, pb,
							file.getPath(), rightAnimView ,  position);
				}
			
		}
		// ////////////////
		// 点击语音播放
		// //////
		holder.yy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (v.getTag().equals(it.get(position).getAfile())) {
					File file = null;
					holder.dazui_detatil_yy.setTag(position);
					if(SystemUtil.getSDCardMount()){
					file = new File(Environment.getExternalStorageDirectory()
							+ File.separator + context.getPackageName()
							+ ConstantsJrc.AUDIO_PATH + File.separator
							+ Commond.getMd5Hash(it.get(position).getAfile())
							+ ".amr");
					}else{
						file = new File(ConstantsJrc.PROJECT_PATH
								+ context.getPackageName()
								+ ConstantsJrc.AUDIO_PATH + File.separator
								+ Commond.getMd5Hash(it.get(position).getAfile())
								+ ".amr");
					}
					if (file.exists()) {
						Uri uri = Uri.fromFile(file);
						PlayMusic(file.getPath(), rightAnimView, false , position);
					} else {
						// pinglunyys.add(item.getAfile());
						pb.setVisibility(View.VISIBLE);
						// new Thread(new myThread(item.getAfile(),
						// myHandler)).start();
						DownFile downFile = new DownFile();
						downFile.down(
								it.get(position).getAfile(),
								Environment.getExternalStorageDirectory()
										+ File.separator
										+ context.getPackageName()
										+ ConstantsJrc.AUDIO_PATH,
								ConstantsJrc.PROJECT_PATH
										+ context.getPackageName()
										+ ConstantsJrc.AUDIO_PATH, pb,
								file.getPath(), rightAnimView ,  position);
					}
				}
			}
		});
		// /////////////////////

		// 头像点击监听
		holder.imageheader.setTag(item.getUhead());
		holder.imageheader.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (v.getTag().equals(it.get(position).getUhead())) {
					Dazuidetatil.detatilispause = true;
					Intent intent = new Intent(context.getApplicationContext(),
							VillageUserInfoDialog.class);

					intent.putExtra("uid",
							String.valueOf(it.get(position).getUid()));
					intent.putExtra("nick", it.get(position).getUnick());
					intent.putExtra("fuid", MainMenuActivity.uid);
					intent.putExtra("type", 2);
					context.startActivity(intent);
				}
			}
		});
		if (TextUtils.isEmpty(item.getUhead())) {
			holder.imageheader.setImageResource(R.drawable.defautheader);
		} else {
			holder.imageheader.setImageResource(R.drawable.photo);
			picfile = new File(dazuidown + "/"
					+ Comment.getMd5Hash(item.getUhead()));
			String filename = picfile.getPath().toString();
			
			Bitmap bmp = null;
			if(picfile.exists()){
			 bmp = BitmapCacheDzpl.getIntance()
						.getCacheBitmap(filename, 0, 0);
			}	

			if (bmp == null) {
				if (!urls.contains(item.getUhead())) {
					urls.add(item.getUhead());
					new Thread(new LoadImageRunnable(context, mHandler, 0,
							item.getUhead(), filename)).start();
				}
				holder.imageheader.setImageResource(R.drawable.photo);
			} else {
				holder.imageheader.setImageBitmap(bmp);
			}
		}

		holder.date.setTextColor(Color.parseColor("#937656"));
		if(!TextUtils.isEmpty(btime)){
		holder.date.setText(comDate(btime, item.getDate()));
		}else{
			holder.date.setText(item.getDate());	
		}
		
		// holder.fcount.setText(item.getFcount());
		if(!TextUtils.isEmpty(item.getNameclor())){
			holder.title.setTextColor(Color.parseColor(item.getNameclor()));		
		}else{
		holder.title.setTextColor(Color.parseColor("#937656"));
		}
		holder.title.setText(item.getUnick());

		// holder.chenghao.setText(item.getChenghao());

		

		return itemView;
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			if (data != null) {
				String url = data.getString("url");
				String filename = data.getString("filename");

				Bitmap bmp = BitmapCacheDzpl.getIntance().getCacheBitmap(filename,
						0, 0);
				ImageView iv = (ImageView) list.findViewWithTag(url);
				if (iv != null) {
					iv.setImageBitmap(bmp);
				}
			}
		}
	};

	class ViewHolder {
		public LinearLayout pinglunll , uickll;
		public ImageView imageheader, dazui_detatil_yy ,imageheaderw , div ,wlt;
		public TextView fcount;
		public RelativeLayout relativell , pinglun_header_rl;
		public TextView title;
		public TextView content;
		public RelativeLayout yy;
		public TextView date;
		public String uid;
		public TextView dazui_detatil_time;
		public ProgressBar dazui_detatil_yy_pb;
		public int pid ;
		public TextView numofdiscuss;

	}

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
	 * 
	 * @param pubdate
	 * @return
	 */
	static public String comDate(String baseDate, String pubDate) {
		long baseMillis = getDate(baseDate, false);
		if (baseMillis == -1)
			return pubDate;

		long pubMillis = getDate(pubDate, false);
		if (pubMillis == -1)
			return pubDate;

		//
		String str = strDate(baseMillis, pubMillis);
		return str == null ? pubDate : str;
	}

	static public String weekStr(int week) {
		switch (week) {
		case 1:
			return "一";
		case 2:
			return "二";
		case 3:
			return "三";
		case 4:
			return "四";
		case 5:
			return "五";
		case 6:
			return "六";
		case 7:
			return "日";
		}
		return week + "";
	}

	/**
	 * 
	 * @param dateStr
	 * @return
	 */
	static private long getDate(String dateStr, boolean isShort) {
		long millis = -1;
		Date date = null;
		if (TextUtils.isEmpty(dateStr))
			return millis;
		if (TextUtils.isEmpty(dateStr) || TextUtils.isDigitsOnly(dateStr)) {
			date = new Date(Long.parseLong(dateStr));
		} else {
			String formatStr = "yyyy-MM-dd HH:mm:ss";
			if (isShort) {
				formatStr = "yyyy-MM-dd";
			}
			DateFormat format = new SimpleDateFormat(formatStr);
			try {
				date = format.parse(dateStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			millis = calendar.getTimeInMillis();
		}
		return millis;
	}

	/**
	 * 
	 * @param pubdate
	 * @return
	 */
	static public String strDate(long baseMillis, long pubMillis) {
		long millis = baseMillis - pubMillis;
		millis = millis / 1000;
		// //@////@System.out.println("millis:" + millis);
		if (millis < 60)
			return millis + "秒前";
		else {
			if (millis < 60 * 60)
				return (int) (millis / 60) + "分钟前";
			else {
				if (millis < 24 * 60 * 60)
					return (int) (millis / (60 * 60)) + "小时前";
				else {
					if (millis < 7 * 24 * 60 * 60) {
						int days = (int) (millis / (24 * 60 * 60));
						if (days == 1)
							return "昨天";
						else if (days == 2)
							return "前天";
						Calendar calendar1 = Calendar.getInstance();
						calendar1.setTimeInMillis(baseMillis);
						int d1 = calendar1.get(Calendar.DAY_OF_WEEK);
						if (days >= d1) {
							Calendar calendar2 = Calendar.getInstance();
							calendar2.setTimeInMillis(pubMillis);
							int d2 = calendar2.get(Calendar.DAY_OF_WEEK);
							return "上周" + weekStr(d2);
						}
						return days + "天前";
					} else {
						if (millis < 4 * 7 * 24 * 60 * 60) {
							int weeks = (int) (millis / (7 * 24 * 60 * 60));
							if (weeks == 1) {
								String strWeek = "上周";
								Calendar calendar = Calendar.getInstance();
								calendar.setTimeInMillis(pubMillis);
								int d = calendar.get(Calendar.DAY_OF_WEEK);
								strWeek += weekStr(d);
								return strWeek;
							}
							return weeks + "周前";
						} else {
							if (millis < 29030400/* (12 * 4 * 7 * 24 * 60 * 60) */) {
								return (int) (millis / (4 * 7 * 24 * 60 * 60))
										+ "月前";
							} else {
								int y = (int) (millis / 29030400/*
																 * (12 * 4 * 7 *
																 * 24 * 60 * 60)
																 */);
								if (y <= 0)
									return null;
								return y + "年前";
							}
						}
					}
				}
			}
		}
	}

	public void PlayMusic(String path, final ImageView AnimView,
			final boolean leftorright ,  final int position) {
		try {
			if(po != position){
//				

				if(mediaPlayer == null){
					mediaPlayer = new MediaPlayer();
				}
				if(mediaPlayer.isPlaying()){
					mediaPlayer.stop();
					if(MainMenuActivity.borz == true){
						if(DazuiActivity.player ==null && Mydazui.player!= null && Dynamic.dyplayer == null){
							 if(!Mydazui.player.isPlaying()){
								 Mydazui.player.start();
							 }
						}else if(DazuiActivity.player !=null && Mydazui.player == null&& Dynamic.dyplayer == null){	
							 if(!DazuiActivity.player.isPlaying()){
								 DazuiActivity.player.start();
							 }
						}else if(DazuiActivity.player ==null && Mydazui.player == null&& Dynamic.dyplayer != null){	
							 if(!Dynamic.dyplayer.isPlaying()){
								 Dynamic.dyplayer.start();
							 }
						}
					}
					if(Dydetatil.borz ==true){
						if(Dydetatil.player != null){
							 if(!Dydetatil.player.isPlaying()){
								 Dydetatil.player.start();
							 }
						}
					}
					mediaPlayer = null ;
					 it.get(po).setIsbdh(false);
					 mediaPlayer = new MediaPlayer();
				  stop_animation();
//					if((Integer)holder.dazui_detatil_yy.getTag() == po+1){
//						ImageView iv = (ImageView)list.findViewWithTag(po);
//                        iv.setBackgroundResource(R.drawable.left_3);
//					}
				}else{
					mediaPlayer = null ;
					 it.get(po).setIsbdh(false);
					 mediaPlayer = new MediaPlayer();
//				  stop_animation();
				}
//				mediaPlayer.reset();// 重置
				File file = new File(path);
				FileInputStream fis = new FileInputStream(file);
				mediaPlayer.setDataSource(fis.getFD());
				mediaPlayer.prepare();
				mediaPlayer.start();

					if(DazuiActivity.player ==null && Mydazui.player!= null&& Dynamic.dyplayer == null){
						 if(Mydazui.player.isPlaying()){
							 Mydazui.player.pause();
						 }
					}else if(DazuiActivity.player !=null && Mydazui.player == null&& Dynamic.dyplayer == null){
						 if(DazuiActivity.player.isPlaying()){
							 DazuiActivity.player.pause();
						 }
					}else if(DazuiActivity.player ==null && Mydazui.player == null&& Dynamic.dyplayer != null){	
						 if(Dynamic.dyplayer.isPlaying()){
							 Dynamic.dyplayer.pause();
						 }
					}
						if(Dydetatil.player != null){
							 if(Dydetatil.player.isPlaying()){
								 Dydetatil.player.pause();
							 }
						}					
				 it.get(position).setIsbdh(true);
				start_animation(R.anim.left_video_anim, AnimView);
				
				mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

					@Override
					public void onCompletion(MediaPlayer mp) {
						// TODO Auto-generated method stub

						AnimView.setImageResource(R.drawable.left_3);
						 it.get(position).setIsbdh(false);
						 mediaPlayer = null ;
						 stop_animation();
						// mediaPlayer.release();
						// mediaCompleted.start();
						isPlay = false;
						
						if(MainMenuActivity.borz == true){
							if(DazuiActivity.player ==null && Mydazui.player!= null && Dynamic.dyplayer == null){
								 if(!Mydazui.player.isPlaying()){
									 Mydazui.player.start();
								 }
							}else if(DazuiActivity.player !=null && Mydazui.player == null&& Dynamic.dyplayer == null){
								 if(!DazuiActivity.player.isPlaying()){
									 DazuiActivity.player.start();
								 }
							}else if(DazuiActivity.player ==null && Mydazui.player == null&& Dynamic.dyplayer != null){	
								 if(!Dynamic.dyplayer.isPlaying()){
									 Dynamic.dyplayer.start();
								 }
							}							
						}
						if(Dydetatil.borz ==true){
							if(Dydetatil.player != null){
								 if(!Dydetatil.player.isPlaying()){
									 Dydetatil.player.start();
								 }
							}
						}
					}
				});
			}else{
              if (isPlay == false) {
				// su.setAudioFlag(path);
				try {
					// mediaPlayer = MediaPlayer.create(context,
					// uri);
					if (mediaPlayer == null) {
						mediaPlayer = new MediaPlayer();
//						mediaPlayer.reset();// 重置
						File file = new File(path);
						if(!file.exists()){
							Commond.showToast(context, "亲，没发现播放文件哦！");
						  return ;
						}
						FileInputStream fis = new FileInputStream(file);
						mediaPlayer.setDataSource(fis.getFD());
						mediaPlayer.prepare();
						mediaPlayer.start();
						if(DazuiActivity.player ==null && Mydazui.player!= null&& Dynamic.dyplayer == null){
							 if(Mydazui.player.isPlaying()){
								 Mydazui.player.pause();
							 }
						}else if(DazuiActivity.player !=null && Mydazui.player == null&& Dynamic.dyplayer == null){
							 if(DazuiActivity.player.isPlaying()){
								 DazuiActivity.player.pause();
							 }
						}else if(DazuiActivity.player ==null && Mydazui.player == null&& Dynamic.dyplayer != null){	
							 if(Dynamic.dyplayer.isPlaying()){
								 Dynamic.dyplayer.pause();
							 }
						}	
							if(Dydetatil.player != null){
								 if(Dydetatil.player.isPlaying()){
									 Dydetatil.player.pause();
								 }							
						}
						 it.get(position).setIsbdh(true);
						start_animation(R.anim.left_video_anim, AnimView);

//						isPlay = true;
					}else if(mediaPlayer != null){
						if(mediaPlayer.isPlaying()){
							mediaPlayer.pause();
							stop_animation();
							if(MainMenuActivity.borz == true){
								if(DazuiActivity.player ==null && Mydazui.player!= null&& Dynamic.dyplayer == null){
									 if(!Mydazui.player.isPlaying()){
										 Mydazui.player.start();
									 }
								}else if(DazuiActivity.player !=null && Mydazui.player == null&& Dynamic.dyplayer == null){
							
									 if(!DazuiActivity.player.isPlaying()){
										 DazuiActivity.player.start();
									 }
								}else if(DazuiActivity.player ==null && Mydazui.player == null&& Dynamic.dyplayer != null){	
									 if(!Dynamic.dyplayer.isPlaying()){
										 Dynamic.dyplayer.start();
									 }
								}
							}
							if(Dydetatil.borz ==true){
								if(Dydetatil.player != null){
									 if(!Dydetatil.player.isPlaying()){
										 Dydetatil.player.start();
									 }
								}
							}
							 it.get(position).setIsbdh(false);
							AnimView.setImageResource(R.drawable.left_3);
						}else{
							mediaPlayer.start();
							if(DazuiActivity.player ==null && Mydazui.player!= null&& Dynamic.dyplayer == null){
								 if(Mydazui.player.isPlaying()){
									 Mydazui.player.pause();
								 }
							}else if(DazuiActivity.player !=null && Mydazui.player == null&& Dynamic.dyplayer == null){
								 if(DazuiActivity.player.isPlaying()){
									 DazuiActivity.player.pause();
								 }
							}else if(DazuiActivity.player ==null && Mydazui.player == null&& Dynamic.dyplayer != null){	
								 if(Dynamic.dyplayer.isPlaying()){
									 Dynamic.dyplayer.pause();
								 }
							}
								if(Dydetatil.player != null){
									 if(Dydetatil.player.isPlaying()){
										 Dydetatil.player.pause();
									 }
								}							
							 it.get(position).setIsbdh(true);
							start_animation(R.anim.left_video_anim, AnimView);
						}
					}
					mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

						@Override
						public void onCompletion(MediaPlayer mp) {
							// TODO Auto-generated method stub

							AnimView.setImageResource(R.drawable.left_3);
							 it.get(position).setIsbdh(false);
							 mediaPlayer = null ;
							 stop_animation();
							// mediaPlayer.release();
							// mediaCompleted.start();
							isPlay = false;
							if(MainMenuActivity.borz == true){
								if(DazuiActivity.player ==null && Mydazui.player!= null && Dynamic.dyplayer == null){
									 if(!Mydazui.player.isPlaying()){
										 Mydazui.player.start();
									 }
								}else if(DazuiActivity.player !=null && Mydazui.player == null&& Dynamic.dyplayer == null){

									 if(!DazuiActivity.player.isPlaying()){
										 DazuiActivity.player.start();
									 }
								}else if(DazuiActivity.player ==null && Mydazui.player == null&& Dynamic.dyplayer != null){

									 if(!Dynamic.dyplayer.isPlaying()){
										 Dynamic.dyplayer.start();
									 }
								}
							}
							if(Dydetatil.borz ==true){
								if(Dydetatil.player != null){
									 if(!Dydetatil.player.isPlaying()){
										 Dydetatil.player.start();
									 }
								}
							}
						}
					});
								
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

			 }
			}
            po = position ;

			mediaPlayer.setOnErrorListener(new OnErrorListener() {

				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					// TODO Auto-generated method stub
//					mediaPlayer.reset();
					mediaPlayer = null;
					AnimView.setImageResource(R.drawable.right_3);
					 it.get(position).setIsbdh(false);
					 stop_animation();
					isPlay = false;
					return false;
				}
			});

			mediaPlayer.setOnSeekCompleteListener(new OnSeekCompleteListener() {

				@Override
				public void onSeekComplete(MediaPlayer mp) {
					// TODO Auto-generated method stub
					mediaPlayer = null ;
					AnimView.setImageResource(R.drawable.left_3);
					 it.get(position).setIsbdh(false);
					//
					 stop_animation();
				}
			});

		} catch (Exception e) {

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
				ProgressBar progerss, String audiofile, ImageView anim , int position) {
			loadFile(url, path, path2, progerss, audiofile, anim , position);
		}

		public void loadFile(String url, String path, String path2,
				ProgressBar progerss, String audiofile, ImageView anim , int position) {
			if (!isLocal(url, path, path2)) {
				if (urls.contains(url))
					return;
				urls.add(url);
				if (SystemUtil.getSDCardMount()) {
					new DownLoadFile(progerss, audiofile, anim , position).execute(url,
							path);
				} else {
					new DownLoadFile(progerss, audiofile, anim , position).execute(url,
							path2 );
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

		private class DownLoadFile extends AsyncTask<String, String, String> {
			ProgressBar pro;
			ChatMessageBean chatBean;
			ImageView AnimView;
			ImageView AnimViewStatic;
			String audiofile;
			int position ;

			public DownLoadFile() {
			}

			public DownLoadFile(ProgressBar p, String file, ImageView anim , int position) {
				this.pro = p;

				this.AnimView = anim;

				this.audiofile = file;
				this.position = position;
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
						// publishProgress("" + (int) ((total * 100) /
						// lenghtOfFile));
						output.write(data, 0, count);
					}
					output.flush();
					output.close();
					input.close();
				} catch (Exception e) {
                   e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onProgressUpdate(String... values) {
				// TODO Auto-generated method stub
				super.onProgressUpdate(values);
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				if (pro != null) {
					pro.setVisibility(View.GONE);
				}

				PlayMusic(audiofile, AnimView, false , position);
				super.onPostExecute(result);
			}

		}
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
}
