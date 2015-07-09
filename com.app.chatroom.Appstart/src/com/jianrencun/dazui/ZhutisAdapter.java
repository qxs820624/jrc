package com.jianrencun.dazui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.app.chatroom.MainMenuActivity;
import com.duom.fjz.iteminfo.BitmapCache2;
import com.duom.fjz.iteminfo.BitmapCacheDzpl;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.DazuiAdapter2.LoadImageRunnable;
import com.weibo.joechan.util.TextUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ZhutisAdapter extends BaseAdapter{
	Context context;
	ViewHolder holder = null;
	List<ZhutisIteminfo> it;
	ZhutisIteminfo item;
	ListView listview;
	private File picfile;
	private String dazuidown;
	private ArrayList<String> urls = new ArrayList<String>();
	
	public ZhutisAdapter(Context context, List<ZhutisIteminfo> it,
			 ListView listview) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.it = it ;
		this.listview = listview;
		
		dazuidown =  MainMenuActivity.dazuisdown.toString();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View itemView = convertView;

		if (itemView == null) {
			itemView = LayoutInflater.from(context).inflate(R.layout.dz_zhutis_item,
					null);
			
			holder = new ViewHolder();
			// holder.itemll = (LinearLayout)itemView.findViewById(R.id.itemll);
			holder.title = (TextView) itemView
					.findViewById(R.id.dz_zhutis_title);
			holder.number = (TextView) itemView.findViewById(R.id.dz_zhutis_number);
			
			holder.iv = (ImageView)itemView.findViewById(R.id.dz_zhutis_imageview);
			holder.rl = (RelativeLayout)itemView.findViewById(R.id.zhutis_rl);
			
			itemView.setTag(holder);
		} else {
			holder = (ViewHolder) itemView.getTag();
		}		
		holder.rl.setBackgroundResource(R.drawable.listitemdazui2);
		
		item = it.get(position);
		holder.title.setText(item.getTitle());
		holder.number.setText(item.getNumber()); 
		holder.iv.setTag(item.getIv());
		
		if (!TextUtils.isEmpty(item.getTitleclor())) {
			holder.title.setTextColor(Color.parseColor(item.getTitleclor()));
		} else {
			holder.title.setTextColor(Color.parseColor("#482c3f"));
		}
		

		// //////////////////////头像 获取
		if (TextUtils.isEmpty(item.getIv())) {
			holder.iv.setBackgroundResource(R.drawable.defautheader);
		} else {
			holder.iv.setBackgroundResource(R.drawable.photo);
			picfile = new File(dazuidown + "/"
					+ Comment.getMd5Hash(item.getIv()));
			String filename = picfile.getPath().toString();
			
			Bitmap bmp = null;
			if(picfile.exists()){
			 bmp = BitmapCache2.getIntance()
						.getCacheBitmap(filename, 0, 0);
			}	

			if (bmp == null) {
				if (!urls.contains(item.getIv())) {
					urls.add(item.getIv());
					new Thread(new LoadImageRunnable(context, mHandler, 0,
							item.getIv(), filename)).start();
				}
				holder.iv.setBackgroundResource(R.drawable.photo);
			} else {
				BitmapDrawable bd = new BitmapDrawable(bmp);
				holder.iv.setBackgroundDrawable(bd);
			}
		}
		
				
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

				Bitmap bmp = BitmapCache2.getIntance().getCacheBitmap(filename,
						0, 0);
				ImageView iv = (ImageView) listview.findViewWithTag(url);
				if (iv != null) {
					BitmapDrawable bd = new BitmapDrawable(bmp);
					iv.setBackgroundDrawable(bd);
				}
			}
		}
	};
	
	public  class ViewHolder {
		public TextView title , number;
		public ImageView iv;
		public RelativeLayout rl;
	}
}
