package com.jianrencun.game;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.app.chatroom.Appstart;
import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.otherui.VillageUserInfoDialog;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;
import com.duom.fjz.iteminfo.BitmapCache;
import com.duom.fjz.iteminfo.BitmapCacheDzlb;
import com.jianrencun.android.Details;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Comment;
import com.jianrencun.game.GameAdapter.LoadImageRunnable;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameListAdapter extends BaseAdapter{
	private Context context;
	private List<GameListinfo> it;
	ListView lv;
	ViewHolder holder = null;
	GameListinfo item;
	File picfile;
	OnClickListener listener ;
	private ArrayList<String> urls = new ArrayList<String>();
	String btime ;

	public GameListAdapter(Context context, List<GameListinfo> it, ListView lv , OnClickListener listener , String btime) {
		this.context = context;
		this.it = it;
		this.lv = lv;
		this.listener = listener ;
		this.btime = btime ;
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
		View itemView = convertView;

		if (itemView == null) {
			itemView = LayoutInflater.from(context).inflate(
					R.layout.pinglunitem, null);
			holder = new ViewHolder();
			// holder.itemll = (LinearLayout)itemView.findViewById(R.id.itemll);	
			holder.relativell = (RelativeLayout) itemView
					.findViewById(R.id.relativell);
			holder.pinglunll = (LinearLayout) itemView
					.findViewById(R.id.pinglunll);
			holder.content = (TextView) itemView
					.findViewById(R.id.contentofpinglun);
			holder.date = (TextView) itemView.findViewById(R.id.userdate);
			// holder.fcount = (TextView)
			// itemView.findViewById(R.id.numfcount);
			holder.title = (TextView) itemView.findViewById(R.id.usertitle);
			// holder.chenghao = (TextView)
			// itemView.findViewById(R.id.userchenghao);
			holder.imageheader = (ImageView) itemView
					.findViewById(R.id.pinglunheader);
			holder.imageheader= (ImageView) itemView
					.findViewById(R.id.pinglunheaderw);
			holder.tiaozhanta = (Button)itemView.findViewById(R.id.tiaozhanta);
			itemView.setTag(holder);
		} else {
			holder = (ViewHolder) itemView.getTag();
		}
		
		item = it.get(position);
		holder.position = position;
		itemView.setTag(itemView.getId(), item);
		holder.tiaozhanta.setTag(holder);
		
		if(item.getId() != 0){
			if(item.getZt() == 0){
			holder.tiaozhanta.setVisibility(View.VISIBLE);
			holder.tiaozhanta.setBackgroundResource(R.drawable.tiaozhanta1);
			}else{
				holder.tiaozhanta.setBackgroundResource(R.drawable.yitiaozhan);
			}
		}else{
			holder.tiaozhanta.setVisibility(View.GONE);
		}
		
		holder.tiaozhanta.setOnClickListener(listener);
		
		holder.imageheader.setTag(item.getHeader());
		holder.imageheader.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (v.getTag().equals(it.get(position).getHeader())) {
					if (TextUtils.isEmpty(it.get(position).getHeader())) {
						Commond.showToast(context, "非本村村民！");
					} else {
						Intent intent = new Intent(context,
								VillageUserInfoDialog.class);
						intent.putExtra("uid", String.valueOf(it.get(position).getUid()));
						intent.putExtra("nick", it.get(position).getNick());
						intent.putExtra("fuid", MainMenuActivity.uid);
						intent.putExtra("type", 2);
						context.startActivity(intent);
					}
				}
			}
		});
		holder.imageheader.setImageResource(R.drawable.w_ph);
		jiance(item.getHeader(), holder.imageheader);
		holder.content.setText(item.getDesc());
		if (!TextUtils.isEmpty(item.getDesc_c())) {
			holder.content.setTextColor(Color.parseColor(item.getDesc_c()));
		} else {
			holder.content.setTextColor(Color.parseColor("#482c3f"));
		}

		if (!TextUtils.isEmpty(item.getNick_c())) {
			holder.title.setTextColor(Color.parseColor(item.getNick_c()));
		} else {
			holder.title.setTextColor(Color.parseColor("#827a80"));
		}

      
		holder.date.setText(Details.comDate(btime, item.getDate()));
		// holder.fcount.setText(item.getFcount());
		holder.title.setText(item.getNick());
		// holder.chenghao.setText(item.getChenghao());
		holder.relativell.setBackgroundResource(R.drawable.gameitem1);

		return itemView;
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

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			if (data != null) {
				String url = data.getString("url");
				String filename = data.getString("filename");

				Bitmap bmp = BitmapCacheDzlb.getIntance().getCacheBitmap(
						filename, 0, 0);

				ImageView iv = (ImageView) lv.findViewWithTag(url);
				if (iv != null) {
					BitmapDrawable bd = new BitmapDrawable(bmp);
					iv.setImageDrawable(bd);
				}
			}
		}
	};

	public class ViewHolder {
		public LinearLayout pinglunll;
		public ImageView imageheader , ivk ;;
		public TextView fcount;
		public RelativeLayout relativell;
		public TextView title;
		public TextView content;
		public TextView date;
		public String uid;
		public Button tiaozhanta ;
		public int position ;
	}

	public void jiance(String header, ImageView iv) {
		picfile = new File(Appstart.jrcfile + "/" + Commond.getMd5Hash(header));
		String filename = picfile.getPath().toString();

		Bitmap bmp = null;
		if (picfile.exists()) {
			bmp = BitmapCache.getIntance().getCacheBitmap(filename, 0, 0);
		}
		if (bmp == null) {
			if (!urls.contains(header)) {
				urls.add(header);
				new Thread(new LoadImageRunnable(this.context, mHandler, 0,
						header, filename)).start();
			}
			iv.setBackgroundResource(R.drawable.liwupic2);
		} else {
			BitmapDrawable bd = new BitmapDrawable(bmp);
			iv.setBackgroundDrawable(bd);
		}
	}
}
