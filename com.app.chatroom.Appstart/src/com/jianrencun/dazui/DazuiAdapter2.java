package com.jianrencun.dazui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.otherui.VillageUserInfoDialog;
import com.app.chatroom.util.Commond;
import com.duom.fjz.iteminfo.BitmapCache;
import com.duom.fjz.iteminfo.BitmapCacheDzlb;
import com.jianrencun.chatroom.R;
import com.weibo.joechan.util.TextUtil;

public class DazuiAdapter2 extends BaseAdapter {
	Context context;
	ViewHolder holder = null;
	List<DazuiIteminfo> it;
	DazuiIteminfo item;
	ListView listview;
	OnClickListener listener, listener2, listener_ding, listener_cai,
			listener_save, listener_share;
	boolean isswith;
	private Handler handler;
	private File picfile;
	private ProgressBar skbar;
	int po, po2;
	private String dazuidown;
	private ArrayList<String> urls = new ArrayList<String>();
	 DisplayMetrics dm;
	public static Thread th;

	public DazuiAdapter2(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		 dm = new DisplayMetrics();   
	        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);   	   
	};

	public DazuiAdapter2(Context context, List<DazuiIteminfo> it,
			ListView listview, OnClickListener listener,
			OnClickListener listener2, boolean isswith,
			OnClickListener listener_ding, OnClickListener listener_cai,
			OnClickListener listener_save, OnClickListener listener_share) {
		this.context = context;
		this.it = it;
		this.listview = listview;
		this.listener2 = listener2;
		this.listener = listener;
		this.isswith = isswith;
		this.listener_ding = listener_ding;
		this.listener_cai = listener_cai;
		this.listener_save = listener_save;
		this.listener_share = listener_share;
		 dm = new DisplayMetrics();   
	        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);   	   
		dazuidown = MainMenuActivity.dazuisdown.toString();
	}

	public OnClickListener getListener() {
		return listener;
	}

	public void setListener(OnClickListener listener) {
		this.listener = listener;
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

	@SuppressLint("NewApi")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View itemView = convertView;

		if (itemView == null) {
			itemView = LayoutInflater.from(context).inflate(
					R.layout.dazuiitem2, null);

			holder = new ViewHolder();
			// holder.itemll = (LinearLayout)itemView.findViewById(R.id.itemll);
			holder.title = (TextView) itemView.findViewById(R.id.dz_sy_title);
			holder.nick = (TextView) itemView.findViewById(R.id.dz_sy_nick);
			holder.date = (TextView) itemView.findViewById(R.id.dz_sy_sctime);
			holder.uc = (TextView) itemView.findViewById(R.id.dz_sy_dingtv);
			holder.dc = (TextView) itemView.findViewById(R.id.dz_sy_caitv);
			holder.size = (TextView) itemView.findViewById(R.id.dz_sy_size);

			holder.dz_ding = (ImageButton) itemView.findViewById(R.id.dz_sy_save);
			holder.dz_cai = (ImageButton) itemView.findViewById(R.id.dz_sy_share);
			holder.dz_sybf = (ImageButton) itemView.findViewById(R.id.dz_sy_bf);

			holder.save = (Button) itemView.findViewById(R.id.dz_sy_bnt1);
			holder.share = (Button) itemView.findViewById(R.id.dz_sy_bnt3);
			holder.dz_pl = (Button) itemView.findViewById(R.id.dz_sy_pl);
			holder.iv = (ImageView) itemView.findViewById(R.id.yyshenhe);
			holder.delete = (ImageButton) itemView
					.findViewById(R.id.dz_mysc_deletepic);
			holder.sk = (ProgressBar) itemView.findViewById(R.id.seekbar);

			holder.flg_pic = (ImageView) itemView.findViewById(R.id.flg_pic);
			

			holder.dazuiliebiao_pb = (ProgressBar) itemView
					.findViewById(R.id.dz_sy_bfpro);
			holder.imageheader = (ImageView) itemView
					.findViewById(R.id.dz_sy_touxiang);
			holder.saveiv = (ImageView) itemView
					.findViewById(R.id.dz_sy_saveiv);
			holder.baqi =  (ImageView) itemView
					.findViewById(R.id.baqi);			
			// holder.da_jindubg=(ImageView)
			// itemView.findViewById(R.id.dz_jindubg);
			itemView.setTag(holder);
		} else {
			holder = (ViewHolder) itemView.getTag();
		}
		item = it.get(position);
		holder.position = position;
		holder.ouid = item.getUid();
		holder.title.setText(item.getTitle());
		holder.nick.setText(item.getUnick());
		holder.uc.setText(String.valueOf(item.getFc()));
		holder.dc.setText(String.valueOf(item.getSc()));
		holder.save.setText(String.valueOf(item.getTc()));
		holder.share.setText(String.valueOf(item.getGc()));
		holder.date.setText(item.getDate());
		holder.size.setText(item.getLen());
		holder.dz_pl.setText(String.valueOf(item.getCcount()));
		holder.dz_sybf.setTag(holder);
		holder.dz_ding.setTag(holder);
		holder.dz_cai.setTag(holder);
		holder.save.setTag(holder);
		holder.share.setTag(holder);
		holder.delete.setTag(holder);
		holder.sk.setTag(position);
		// holder.da_jindubg.setTag(holder);
		holder.flg_pic.setTag(item.getFlg_pic());
//		RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams)holder.flg_pic.getLayoutParams();
//        linearParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
//		linearParams.height=dm.widthPixels*1/7;
//        linearParams.width=dm.widthPixels*1/4;
//        holder.flg_pic.setLayoutParams(linearParams);
		// 头像点击监听
		holder.imageheader.setTag(item.getUhead());
		holder.imageheader.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(it!=null && it.size() != 0){
				if (v.getTag().equals(it.get(position).getUhead())) {
					DazuiActivity.ispause = true;
					Mydazui.mdispause = true;
					Dzmysave.dsispause = true;
					Intent intent = new Intent(context.getApplicationContext(),
							VillageUserInfoDialog.class);

					intent.putExtra("uid",
							String.valueOf(it.get(position).getUid()));
					intent.putExtra("nick", it.get(position).getUnick());
					intent.putExtra("fuid", MainMenuActivity.uid);
					intent.putExtra("type", 2);
					context.startActivity(intent);
				}
				}else{
					Commond.showToast(context, "登录超时！请重新登录！");
				}
			}
		});
		// ///////////////////////评论
		holder.dz_pl.setTag(item.getUhead());
		holder.dz_pl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(it!=null && it.size() != 0){
				if (v.getTag().equals(it.get(position).getUhead())) {
					DazuiActivity.ispause = true;
					Mydazui.mdispause = true;
					Dzmysave.dsispause = true;
					Intent intent = new Intent(context.getApplicationContext(),
							Dazuidetatil.class);
					intent.putExtra("len", it.get(position).getLen().toString());
					intent.putExtra("url", it.get(position).getAfile()
							.toString());
					intent.putExtra("title", it.get(position).getTitle()
							.toString());
					intent.putExtra("ypid", it.get(position).getId());
					intent.putExtra("isfav", it.get(position).getIsfav());
					intent.putExtra("po", position);
					intent.putExtra("which", 0);
					context.startActivity(intent);
				}
				}else{
					Commond.showToast(context, "登录超时！请重新登录！");
				}
			}
		});
		// /////////////删除按钮
		if (isswith == true) {
			holder.delete.setVisibility(View.VISIBLE);
		} else {
			holder.delete.setVisibility(View.GONE);
		}
		// ////////////////////收藏 或 已收藏背景图片
		if (item.getIsfav() == 1) {
			holder.dz_ding.setBackgroundResource(R.drawable.dazuiyisave1);
		} else {
			holder.dz_ding.setBackgroundResource(R.drawable.dazuisave1);
		}
		// /////////////////////////帖子 加精，，加特殊标记处理
		if (item.getFlg_pic().equals("1")) {
			holder.flg_pic.setVisibility(View.VISIBLE);
			holder.flg_pic.setBackgroundResource(R.drawable.pic_zhiding);
		} else if (item.getFlg_pic().equals("2")) {
			holder.flg_pic.setVisibility(View.VISIBLE);
			holder.flg_pic.setBackgroundResource(R.drawable.pic_tuijian);
		} else if (item.getFlg_pic().equals("3")) {
			holder.flg_pic.setVisibility(View.VISIBLE);
			holder.flg_pic.setBackgroundResource(R.drawable.pic_remen);
		} else if (item.getFlg_pic().equals("4")) {
			holder.flg_pic.setVisibility(View.VISIBLE);
			holder.flg_pic.setBackgroundResource(R.drawable.pic_jingcai);
		} else if (item.getFlg_pic().contains("http")) {			
			holder.flg_pic.setVisibility(View.VISIBLE);	
			picfile = new File(dazuidown + "/"
					+ Comment.getMd5Hash(item.getFlg_pic()));
			String filename = picfile.getPath().toString();
			
			Bitmap bmp = null;
			if(picfile.exists()){
			 bmp = BitmapCacheDzlb.getIntance()
						.getCacheBitmap(filename, 0, 0);
			}	
			

			if (bmp == null) {
				if (!urls.contains(item.getFlg_pic())) {
					urls.add(item.getFlg_pic());
					new Thread(new LoadImageRunnable(context, mHandler, 0,
							item.getFlg_pic(), filename)).start();
				}
			} else {
				BitmapDrawable bd = new BitmapDrawable(bmp);
				holder.flg_pic.setBackgroundDrawable(bd);
			}
		} else {
			holder.flg_pic.setVisibility(View.GONE);
		}

		// //////////////////////头像 获取
		if (TextUtils.isEmpty(item.getUhead())) {
			holder.imageheader.setBackgroundResource(R.drawable.defautheader);
		} else {
			holder.imageheader.setBackgroundResource(R.drawable.photo);
			picfile = new File(dazuidown + "/"
					+ Comment.getMd5Hash(item.getUhead()));
			String filename = picfile.getPath().toString();
			Bitmap bmp = null ;
			if(picfile.exists()){
		 bmp = BitmapCacheDzlb.getIntance()
					.getCacheBitmap(filename, 0, 0);
			}

			if (bmp == null) {
				if (!urls.contains(item.getUhead())) {
					urls.add(item.getUhead());
					new Thread(new LoadImageRunnable(context, mHandler, 0,
							item.getUhead(), filename)).start();
				}
				holder.imageheader.setBackgroundResource(R.drawable.photo);
			} else {
				BitmapDrawable bd = new BitmapDrawable(bmp);
				holder.imageheader.setBackgroundDrawable(bd);
			}
		}
		// ////////////////////////////////
		File picfile1 = new File(MainMenuActivity.dazuisdown + File.separator
				+ Comment.getMd5Hash(item.getAfile()) + ".amr");

		if (item.getStatus() == 0) {
			holder.iv.setVisibility(View.VISIBLE);
			holder.iv.setImageResource(R.drawable.passing);
		} else if (item.getStatus() == 1) {
			holder.iv.setVisibility(View.VISIBLE);
			holder.iv.setImageResource(R.drawable.notpass);
		} else if (item.getStatus() == 2) {
			holder.iv.setVisibility(View.VISIBLE);
			holder.iv.setImageResource(R.drawable.passed);
		}
		// ////////////设置昵称颜色
		if (!TextUtil.isEmpty(item.getNameclor())) {
			holder.nick.setTextColor(Color.parseColor(item.getNameclor()));
		} else {
			holder.nick.setTextColor(Color.parseColor("#716d4f"));
		}
		/////设置背景色
		if(item.getType() == 0){
		    holder.baqi.setVisibility(View.GONE);
		}else if(item.getType() == 1){
			 holder.baqi.setVisibility(View.VISIBLE);
			holder.baqi.setBackgroundResource(R.drawable.wanrenmi4);
		}else if(item.getType() == 2){
			 holder.baqi.setVisibility(View.VISIBLE);
			holder.baqi.setBackgroundResource(R.drawable.jinlong4);
		}else if(item.getType() == 3){
			 holder.baqi.setVisibility(View.VISIBLE);
			holder.baqi.setBackgroundResource(R.drawable.tuhao4);
		}else if(item.getType() == 4){
//			holder.baqi.setBackgroundResource(R.drawable.dz_sy_caizhu);
		}else{
			 holder.baqi.setVisibility(View.GONE);
		}

		// beijing jin du tiao
		if (item.getJindubg() == 0) {
			holder.sk.setVisibility(View.GONE);
		} else {
			holder.sk.setVisibility(View.VISIBLE);
		}
		// /////////////////////////设置播放按钮的状态图片
		if (item.getStartorstop() == 1) {
			if (po != holder.position) {
				if (skbar != null) {
					skbar.setProgress(0);
				}
			}
			po = holder.position;
			try {							
			holder.dz_sybf.setBackgroundResource(R.drawable.dazuidetailzt1);
			holder.dazuiliebiao_pb.setVisibility(View.GONE);
			holder.dz_sybf.setEnabled(true);
			} catch (Exception e) {
				// TODO: handle exception
			  Commond.showToast(context, "出问题了！请重新登录！");
			  return null ;
			}

			if (th == null) {
				th = new Thread(new update());
				th.start();
			}

		} else if (item.getStartorstop() == 0) {
			if (po == holder.position) {
				if (skbar != null) {
					skbar.setProgress(0);
				}
			}
			if (picfile1.exists()) {
				holder.dz_sybf
						.setBackgroundResource(R.drawable.dazuitetatil_bf1);
			} else {
				holder.dz_sybf
						.setBackgroundResource(R.drawable.dazuidetatil_bf_weixiazai);
			}
			holder.dz_sybf.setEnabled(true);
			holder.dazuiliebiao_pb.setVisibility(View.GONE);
		} else if (item.getStartorstop() == 2) {
			holder.dazuiliebiao_pb.setVisibility(View.VISIBLE);
			holder.dz_sybf.setEnabled(false);
		} else {
			if (picfile1.exists()) {
				holder.dz_sybf
						.setBackgroundResource(R.drawable.dazuitetatil_bf1);
			} else {
				holder.dz_sybf
						.setBackgroundResource(R.drawable.dazuidetatil_bf_weixiazai);
			}
			holder.dazuiliebiao_pb.setVisibility(View.GONE);
		}
		// /////////////////////////加监听
		holder.dz_sybf.setOnClickListener(listener);
		holder.dz_ding.setOnClickListener(listener_ding);
		holder.dz_cai.setOnClickListener(listener_cai);
		holder.save.setOnClickListener(listener_save);
		holder.share.setOnClickListener(listener_share);
		holder.delete.setOnClickListener(listener2);
		return itemView;
	}

	public class ViewHolder {
		public TextView title, nick, date, size, uc, dc;
		public ImageButton dz_sybf, dz_ding, dz_cai, dazui_save,
				dazui_share,delete;
		public ProgressBar dazuiliebiao_pb;
		public int position, ouid;
		// public RelativeLayout dazuinewrl ;
		public Button  save, dz_pl, share;
		public ImageView imageheader, saveiv, iv, flg_pic ,baqi;
		public LinearLayout dz_sy_ll;
		public ProgressBar sk;

		// public ImageView da_jindubg;
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			if (data != null) {
				String url = data.getString("url");
				String filename = data.getString("filename");

				Bitmap bmp = BitmapCacheDzlb.getIntance().getCacheBitmap(filename,
						0, 0);
				ImageView iv = (ImageView) listview.findViewWithTag(url);
				if (iv != null) {
					BitmapDrawable bd = new BitmapDrawable(bmp);
					iv.setBackgroundDrawable(bd);;
				}
			}
		}
	};

	// Handler mHandler2 = new Handler() {
	// public void handleMessage(Message msg) {
	// Bundle data = msg.getData();
	// if (data != null) {
	// String url = data.getString("url");
	// String filename = data.getString("filename");
	//
	// Bitmap bmp = BitmapCache.getIntance().getCacheBitmap(filename,
	// 0, 0);
	// ImageView iv = (ImageView) listview.findViewWithTag(url);
	// if (iv != null) {
	// BitmapDrawable bd = new BitmapDrawable(bmp);
	// iv.setBackgroundDrawable(bd);
	// }
	// }
	// }
	// };

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

	Handler hd = new Handler() {
		public void handleMessage(android.os.Message mes) {
			if (mes.what == 4) {
				if (skbar == null || po != po2) {
					skbar = (ProgressBar) listview.findViewWithTag(po);
					po2 = po;
					try {
						if (DazuiActivity.lenth != 0) {
							skbar.setMax(DazuiActivity.lenth);
						}
					} catch (Exception e) {
						// TODO: handle exception
						Commond.showToast(context, "亲，播放遇到了问题，请重新进入！");
					}
				}
				if(skbar != null){
				skbar.setProgress(mes.arg1);
				}
			}
		}
	};

	class update implements Runnable {
		@Override
		public void run() {
			while (hd != null
					&& (DazuiActivity.player != null || Mydazui.player != null || Dzmysave.player != null) ) {
				if (MainMenuActivity.borz) {
					Message message = hd.obtainMessage();
					try {
						if (DazuiActivity.player == null
								&& Mydazui.player != null
								&& Dzmysave.player == null) {

							message.arg1 = Mydazui.player.getCurrentPosition() / 1000;
						} else if (Mydazui.player == null
								&& DazuiActivity.player != null
								&& Dzmysave.player == null) {

							message.arg1 = DazuiActivity.player
									.getCurrentPosition() / 1000;
						} else if (Mydazui.player == null
								&& DazuiActivity.player == null
								&& Dzmysave.player != null) {

							message.arg1 = Dzmysave.player.getCurrentPosition() / 1000;
						}
						message.what = 4;
						hd.sendMessage(message);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						break;
					}
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
