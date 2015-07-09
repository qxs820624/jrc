package com.duom.fjz.iteminfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.app.chatroom.Appstart;
import com.app.chatroom.util.Commond;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Comment;
import com.jianrencun.dazui.DazuiActivity;
import com.umeng.common.Log;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BackpackAdapter extends BaseAdapter {
	private Context context;
	private List<BackpackIteminfo> it;
	private GridView gv;
	ViewHolder holder = null;
	BackpackIteminfo item;
	File picfile;
	private ArrayList<String> urls = new ArrayList<String>();
	private OnClickListener listener1, listener2;

	public BackpackAdapter(Context context, List<BackpackIteminfo> it,
			GridView gv, OnClickListener listener1, OnClickListener listener2) {
		this.context = context;
		this.it = it;
		this.gv = gv;
		this.listener1 = listener1;
		this.listener2 = listener2;
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
		View itemView = convertView;
		ImageView imageView = null;
		Drawable cachedDrawable = null;

		if (itemView == null) {
			itemView = LayoutInflater.from(context).inflate(
					R.layout.backpacksitem, null);
			holder = new ViewHolder();
			// holder.itemll = (LinearLayout)itemView.findViewById(R.id.itemll);
			holder.bp_dj = (ImageView) itemView.findViewById(R.id.bp_dj);
			holder.bp_tag_useing = (ImageView) itemView
					.findViewById(R.id.bp_tag_useing);
			holder.qipao = (Button) itemView.findViewById(R.id.bp_tag_qipao);
			holder.sure = (ImageButton) itemView.findViewById(R.id.bp_sure);
			holder.cancle = (ImageButton) itemView.findViewById(R.id.bp_cancle);
			holder.bp_kongiv = (ImageView) itemView
					.findViewById(R.id.bp_kongiv);
			itemView.setTag(holder);
		} else {
			holder = (ViewHolder) itemView.getTag();
		}
		holder.sure.setTag(holder);
		holder.cancle.setTag(holder);
		holder.bp_kongiv.setTag(holder);
		holder.qipao.setTag(holder);
		holder.bp_tag_useing.setTag(holder);

		item = it.get(position);
		holder.position = position;
		holder.bp_dj.setTag(item.getLogo());

		if (item.getCount() != 0) {
			holder.qipao.setText(String.valueOf(item.getCount()));
		} else {
			holder.qipao.setVisibility(View.GONE);
		}

		if (item.getFlg() == 0) {
			holder.sure.setVisibility(View.VISIBLE);
			holder.sure.setClickable(true);
			if (item.getStatus() == 0) {
				holder.bp_tag_useing.setVisibility(View.GONE);
				holder.sure.setBackgroundResource(R.drawable.bp_shiyong1);
			} else if (item.getStatus() == 1) {
				holder.sure.setBackgroundResource(R.drawable.bp_qiyong1);
				holder.bp_tag_useing.setVisibility(View.VISIBLE);
				if(item.getKong() == 1){
					holder.bp_tag_useing.setVisibility(View.GONE);
				}
			}

			holder.cancle.setBackgroundResource(R.drawable.bp_chushou1);
		} else if (item.getFlg() == 1) {
			holder.sure.setVisibility(View.VISIBLE);
			holder.sure.setClickable(true);
			if (item.getStatus() == 0) {
				holder.bp_tag_useing.setVisibility(View.GONE);
				holder.sure.setBackgroundResource(R.drawable.bp_shiyong1);
			} else if (item.getStatus() == 1) {
				holder.sure.setBackgroundResource(R.drawable.bp_qiyong1);
				holder.bp_tag_useing.setVisibility(View.VISIBLE);
				if(item.getKong() == 1){
					holder.bp_tag_useing.setVisibility(View.GONE);
				}
			}
			holder.cancle.setBackgroundResource(R.drawable.bp_diuqi1);
		} else if (item.getFlg() == 2) {
			holder.sure.setBackgroundResource(R.drawable.cannotuse);
			// holder.sure.setClickable(false);

			holder.bp_tag_useing.setVisibility(View.GONE);

			holder.cancle.setBackgroundResource(R.drawable.bp_chushou1);

		} else if (item.getFlg() == 3) {
			holder.sure.setBackgroundResource(R.drawable.cannotuse);

			holder.bp_tag_useing.setVisibility(View.GONE);

			// holder.sure.setClickable(false);
			holder.cancle.setBackgroundResource(R.drawable.bp_diuqi1);
		}

		// 空
		if (item.getKong() == 1) {
			holder.sure.setVisibility(View.GONE);
			holder.cancle.setVisibility(View.GONE);
			holder.qipao.setVisibility(View.GONE);
			holder.bp_kongiv.setVisibility(View.VISIBLE);
			holder.bp_dj.setVisibility(View.GONE);
		}else{
			holder.sure.setVisibility(View.VISIBLE);
			holder.cancle.setVisibility(View.VISIBLE);
			holder.qipao.setVisibility(View.VISIBLE);
			holder.bp_kongiv.setVisibility(View.GONE);
			holder.bp_dj.setVisibility(View.VISIBLE);
		}
		// holder.bp_tag_useing
		if (TextUtils.isEmpty(item.getLogo())) {

		} else {
			picfile = new File(Appstart.jrcfile + "/"
					+ Commond.getMd5Hash(item.getLogo()));
			String filename = picfile.getPath().toString();

			Bitmap bmp = null;
			if (picfile.exists()) {
				bmp = BitmapCache.getIntance().getCacheBitmap(filename, 0, 0);
			}
			if (bmp == null) {
				if (!urls.contains(item.getLogo())) {
					urls.add(item.getLogo());
					new Thread(new LoadImageRunnable(this.context, mHandler, 0,
							item.getLogo(), filename)).start();
				}
				holder.bp_dj.setImageResource(R.drawable.liwupic);
			} else {
				holder.bp_dj.setImageBitmap(bmp);
			}
		}
		holder.sure.setOnClickListener(listener1);
		holder.cancle.setOnClickListener(listener2);
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
				ImageView iv = (ImageView) gv.findViewWithTag(url);
				if (iv != null) {
					iv.setImageBitmap(bmp);
				}
			}
		}
	};

	public class ViewHolder {
		public ImageView bp_dj, bp_tag_useing, bp_kongiv;
		public Button qipao;
		public int position;
		public ImageButton sure, cancle;
	}
}
