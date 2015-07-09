package com.jianrencun.game;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.jianrencun.chatroom.R;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.chatroom.Appstart;
import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.otherui.VillageUserInfoDialog;
import com.app.chatroom.util.Commond;
import com.duom.fjz.iteminfo.BitmapCache;
import com.duom.fjz.iteminfo.BitmapCacheDzlb;
import com.duom.fjz.iteminfo.Searchpeopleinfo;
import com.jianrencun.dazui.Comment;
import com.jianrencun.dazui.Dazuidetatil;

public class GameAdapter extends BaseAdapter {
	private Context context;
	private List<Gameiteminfo> it;
	private GridView gl;
	ViewHolder holder = null;
	Gameiteminfo item;
	File picfile;

	public GameAdapter(Context context, List<Gameiteminfo> it, GridView gl) {
		this.context = context;
		this.it = it;
		this.gl = gl;
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
					R.layout.game_listitem, null);
			holder = new ViewHolder();
			// holder.itemll = (LinearLayout)itemView.findViewById(R.id.itemll);
	
			// holder.kuang2 = (ImageView) itemView
			// .findViewById(R.id.game_item_kuang2);
			holder.iv1 = (ImageView) itemView.findViewById(R.id.game_item_iv1);
			// holder.iv2 = (ImageView) itemView
			// .findViewById(R.id.game_item_iv2);
			//
			// holder.bnt1 = (Button) itemView
			// .findViewById(R.id.game_leftbnt);
			// holder.bnt2 = (Button) itemView
			// .findViewById(R.id.game_rightbnt);
			holder.tvleft = (TextView) itemView.findViewById(R.id.game_tv_left);
			// holder.tvright = (TextView) itemView
			// .findViewById(R.id.game_tv_right);
			// holder.rl2 = (FrameLayout) itemView
			// .findViewById(R.id.tuijian_item_fl2);
			itemView.setTag(holder);
		} else {
			holder = (ViewHolder) itemView.getTag();
		}
		holder.pt = position;		
		item = it.get(position);
		holder.iv1.setTag(item.getHeader());

		jiance(context,item.getHeader(), holder.iv1 ,mHandler);
		holder.tvleft.setText(item.getName());
		return itemView;
	}

	public static class LoadImageRunnable implements Runnable {
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

				ImageView iv = (ImageView) gl.findViewWithTag(url);
				if (iv != null) {
					BitmapDrawable bd = new BitmapDrawable(bmp);
					iv.setImageDrawable(bd);
				}
			}
		}
	};

	private class ViewHolder {
		public ImageView iv1;
		public int pt;
		public TextView tvleft;
	}

	public static void jiance(Context context , String header, ImageView iv, Handler mhandler) {
		File picfile = new File(Appstart.jrcfile + "/" + Commond.getMd5Hash(header));
		String filename = picfile.getPath().toString();
		 ArrayList<String> urls = new ArrayList<String>();
		Bitmap bmp = null;
		if (picfile.exists()) {
			bmp = BitmapCache.getIntance().getCacheBitmap(filename, 0, 0);
		}
		if (bmp == null) {
			if (!urls.contains(header)) {
				urls.add(header);
				new Thread(new LoadImageRunnable(context, mhandler, 0,
						header, filename)).start();
			}
			iv.setImageResource(R.drawable.liwupic2);
		} else {
			BitmapDrawable bd = new BitmapDrawable(bmp);
			iv.setImageDrawable(bd);			
		}
	}
}
