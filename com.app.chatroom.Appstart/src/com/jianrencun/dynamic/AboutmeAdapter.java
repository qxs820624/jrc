package com.jianrencun.dynamic;

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
import java.util.ArrayList;
import java.util.List;

import com.app.chatroom.Appstart;
import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.json.bean.ChatMessageBean;
import com.app.chatroom.otherui.VillageUserInfoDialog;
import com.app.chatroom.util.Commond;
import com.app.chatroom.util.SystemUtil;
import com.duom.fjz.iteminfo.BitmapCacheDzpl;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Comment;
import com.jianrencun.dazui.DazuiActivity;
import com.jianrencun.dazui.Dazuidetatil;
import com.jianrencun.dazui.Mydazui;
import com.jianrencun.dazui.Pinglunitem;
import com.jianrencun.dazui.PinglunAdapter.DownFile;
import com.jianrencun.dazui.PinglunAdapter.LoadImageRunnable;

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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AboutmeAdapter extends BaseAdapter {
	Context context ;
	private List<Aboutmeinfo> it ;
	private ListView lv ; 
	File picfile;
	ViewHolder holder = null;;
	private Aboutmeinfo item ;
	private AnimationDrawable animationDrawable;
	boolean isPlay = false;// 是否正在播放;
	public static MediaPlayer mediaPlayer;
	private ArrayList<String> urls = new ArrayList<String>();
	private String dazuidown;
	int po ;
	List<String> pinglunyys;
	
	public AboutmeAdapter(Context context, List<Aboutmeinfo> lists, ListView lv) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.it = lists;
		this.lv = lv;	
		pinglunyys = new ArrayList<String>();
		dazuidown = MainMenuActivity.dazuisdown.toString();
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
					R.layout.aboutitem, null);
			holder = new ViewHolder();

			holder.content = (TextView) itemView
					.findViewById(R.id.about_contentofpinglun);
			holder.date = (TextView) itemView
					.findViewById(R.id.about_uptime);
		
			holder.nick = (TextView) itemView.findViewById(R.id.about_nick);
			holder.title = (TextView) itemView
					.findViewById(R.id.about_item_title);

			holder.yy = (RelativeLayout) itemView.findViewById(R.id.about_dazuiyyll);
			holder.dazui_detatil_yy_pb = (ProgressBar) itemView
					.findViewById(R.id.about_detatil_yy_pb);
			holder.imageheader = (ImageView) itemView
					.findViewById(R.id.aboutheader);
			holder.div= (ImageView) itemView
					.findViewById(R.id.about_div);
			holder.imageheaderw = (ImageView) itemView
			.findViewById(R.id.aboutheaderw);
			holder.dazui_detatil_yy = (ImageView) itemView
					.findViewById(R.id.about_detatil_yy);
			holder.wlt = (ImageView)itemView.findViewById(R.id.aboutwlt);
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
		

//		if(item.getTouxiangbj() == 0){
//			holder.wlt.setVisibility(View.GONE);
//		}else if(item.getTouxiangbj() == 1) {
//			holder.wlt.setVisibility(View.VISIBLE);
//			holder.wlt.setBackgroundResource(R.drawable.mi);
//		}else if(item.getTouxiangbj() == 2) {
//			holder.wlt.setVisibility(View.VISIBLE);
//			holder.wlt.setBackgroundResource(R.drawable.jinlong);
//		}else if(item.getTouxiangbj() == 3) {
//			holder.wlt.setVisibility(View.VISIBLE);
//			holder.wlt.setBackgroundResource(R.drawable.hao);
//		}
//		else if(item.getTouxiangbj() == 4) {
//			holder.wlt.setVisibility(View.VISIBLE);
////			holder.wlt.setBackgroundResource(R.drawable.caizhu);
//		}
		
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

//		holder.date.setTextColor(Color.parseColor("#937656"));
		if(!TextUtils.isEmpty(item.getDate())){
		holder.date.setText(item.getDate());	
		}
//		if(!TextUtils.isEmpty(btime)){
//		holder.date.setText(comDate(btime, item.getDate()));
//		}else{
//			
//		}
		
		// holder.fcount.setText(item.getFcount());
		if(!TextUtils.isEmpty(item.getNameclor())){
			holder.nick.setTextColor(Color.parseColor(item.getNameclor()));		
		}else{
		holder.nick.setTextColor(Color.parseColor("#937656"));
		}
		holder.title.setText(item.getTitle());
	    holder.nick.setText(item.getUnick());
		return itemView;
	}
	public class ViewHolder {
//		public ImageView header;
//		public TextView title;
		public TextView nick;
//		public TextView content;
//		public TextView desc;
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
	
	public void PlayMusic(String path, final ImageView AnimView,
			final boolean leftorright ,  final int position) {
		try {
			if(po != position){				
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
	
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			if (data != null) {
				String url = data.getString("url");
				String filename = data.getString("filename");

				Bitmap bmp = BitmapCacheDzpl.getIntance().getCacheBitmap(filename,
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
