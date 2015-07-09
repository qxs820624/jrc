package com.app.chatroom.adapter;

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
import android.widget.FrameLayout;
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

public class SearchAdapter extends BaseAdapter {
	private Context context;
	private List<Searchpeopleinfo> it;
	private ListView lv;
	ViewHolder holder = null;
	Searchpeopleinfo item, item2, item3;
	File picfile;
	boolean xianshi;
	private ImageView iv_num1, iv_num2, iv_num3 , sexnum1 , sexnum2 , sexnum3;
	private TextView tv1 ,tv2 ,tv3 ;
	private ArrayList<String> urls = new ArrayList<String>();

	public SearchAdapter(Context context, List<Searchpeopleinfo> it,
			ListView lv, boolean xianshi, ImageView iv1, ImageView iv2,
			ImageView iv3 ,TextView tv1 , TextView tv2 , TextView tv3 , ImageView sexnum1 , ImageView sexnum2 ,ImageView sexnum3) {
		this.context = context;
		this.it = it;
		this.lv = lv;
		this.xianshi = xianshi;
		this.iv_num1 = iv1;
		this.iv_num2 = iv2;
		this.iv_num3 = iv3;
		this.tv1 = tv1 ;
		this.tv2 = tv2 ;
		this.tv3 = tv3 ;
		this.sexnum1 = sexnum1 ; 
		this.sexnum2 = sexnum2 ; 
		this.sexnum3 = sexnum3 ; 
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (it.size() % 3 != 0) {
			return (it.size() / 3 + 1 - 1);
		} else {
			return (it.size() / 3 - 1);
		}

	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return it.size() / 3;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View itemView = convertView;

		if (itemView == null) {
			itemView = LayoutInflater.from(context).inflate(
					R.layout.tuijian_listviewitem, null);
			holder = new ViewHolder();
			// holder.itemll = (LinearLayout)itemView.findViewById(R.id.itemll);

			holder.kuang1 = (ImageView) itemView
					.findViewById(R.id.tuijian_item_kuang1);
			holder.kuang2 = (ImageView) itemView
					.findViewById(R.id.tuijian_item_kuang2);
			holder.kuang3 = (ImageView) itemView
					.findViewById(R.id.tuijian_item_kuang3);
			holder.iv1 = (ImageView) itemView
					.findViewById(R.id.tuijian_item_iv1);
			holder.iv2 = (ImageView) itemView
					.findViewById(R.id.tuijian_item_iv2);
			holder.iv3 = (ImageView) itemView
					.findViewById(R.id.tuijian_item_iv3);
			holder.sexl = (ImageView) itemView
					.findViewById(R.id.tuijian_sex_left);
			holder.sexc = (ImageView) itemView
					.findViewById(R.id.tuijian_sex_center);
			holder.sexr = (ImageView) itemView
					.findViewById(R.id.tuijian_sex_right);
			holder.tvleft = (TextView) itemView
					.findViewById(R.id.tv_left);
			holder.tvcenter = (TextView) itemView
					.findViewById(R.id.tv_center);
			holder.tvright = (TextView) itemView
					.findViewById(R.id.tv_right);
			holder.rl1 = (FrameLayout) itemView
					.findViewById(R.id.tuijian_item_fl1);
			holder.rl2 = (FrameLayout) itemView
					.findViewById(R.id.tuijian_item_fl2);
			holder.rl3 = (FrameLayout) itemView
					.findViewById(R.id.tuijian_item_fl3);
			itemView.setTag(holder);
		} else {
			holder = (ViewHolder) itemView.getTag();
		}

		holder.pt = position;
		holder.kuang1.setBackgroundResource(R.drawable.sf_putongkuang);
		holder.kuang2.setBackgroundResource(R.drawable.sf_putongkuang);
		holder.kuang3.setBackgroundResource(R.drawable.sf_putongkuang);

		if (it.size() > (position + 1) * 3) {
			item = it.get((position + 1) * 3);
			holder.iv1.setTag(item.getHeader());
		}
		if (it.size() > ((position + 1) * 3 + 1)) {
			item2 = it.get((position + 1) * 3 + 1);
			holder.iv2.setTag(item2.getHeader());
		} 
		if (it.size() > ((position + 1) * 3 + 2)) {
			item3 = it.get((position + 1) * 3 + 2);
			holder.iv3.setTag(item3.getHeader());
		}
		// if(xianshi == true){
		// if(position == 0){
		// holder.sf_ivkuang.setBackgroundResource(R.drawable.numone);
		// }else if(position == 1){
		// holder.sf_ivkuang.setBackgroundResource(R.drawable.numtwo);
		// }else if(position == 2){
		// holder.sf_ivkuang.setBackgroundResource(R.drawable.numthree);
		// }else{
		// holder.sf_ivkuang.setBackgroundResource(R.drawable.sf_putongkuang);
		// }
		// }else{
		// holder.sf_ivkuang.setBackgroundResource(R.drawable.sf_putongkuang);
		// }

		// holder.bp_tag_useing
		if (TextUtils.isEmpty(item.getHeader())) {
//			if (it.size() <= ((position + 1) * 3 + 1)) {			
//				holder.rl2.setVisibility(View.GONE);
//			}else{
//				holder.rl2.setVisibility(View.VISIBLE);
//			}
//			if (it.size() <= ((position + 1) * 3 + 2)) {				
//				holder.rl3.setVisibility(View.GONE);
//			}else{
//				holder.rl3.setVisibility(View.VISIBLE);
//			}
		} else {
			jiance(it.get(0).getHeader(), iv_num1);
			jiance(it.get(1).getHeader(), iv_num2);
			jiance(it.get(2).getHeader(), iv_num3);
			if(it.get(0).getSex() == 0){
				sexnum1.setImageResource(R.drawable.what);
			}else if(it.get(0).getSex() == 1){
				sexnum1.setImageResource(R.drawable.woman);
			}else if(it.get(0).getSex() == 2){
				sexnum1.setImageResource(R.drawable.man);
			}
			if(it.get(1).getSex() == 0){
				sexnum2.setImageResource(R.drawable.what);
			}else if(it.get(1).getSex() == 1){
				sexnum2.setImageResource(R.drawable.woman);
			}else if(it.get(1).getSex() == 2){
				sexnum2.setImageResource(R.drawable.man);
			}
			if(it.get(2).getSex() == 0){
				sexnum3.setImageResource(R.drawable.what);
			}else if(it.get(2).getSex() == 1){
				sexnum3.setImageResource(R.drawable.woman);
			}else if(it.get(2).getSex() == 2){
				sexnum3.setImageResource(R.drawable.man);
			}
			
			tv1.setText(it.get(0).getNick());
			tv2.setText(it.get(1).getNick());
			tv3.setText(it.get(2).getNick());
			if (it.size() > (position + 1) * 3) {
				holder.rl1.setVisibility(View.VISIBLE);
				jiance(item.getHeader(), holder.iv1);
				holder.tvleft.setText(item.getNick());
				if(!TextUtils.isEmpty(item.getNickclor())){
					holder.tvleft.setTextColor(Color.parseColor(item.getNickclor()));
					}else{
						holder.tvleft.setTextColor(Color.parseColor("#774220"));
					}
				if(item.getSex() == 0){
					holder.sexl.setImageResource(R.drawable.what);
				}else if(item.getSex() == 1){
					holder.sexl.setImageResource(R.drawable.woman);
				}else if(item.getSex() == 2){
					holder.sexl.setImageResource(R.drawable.man);
				}
			}else{
				holder.tvleft.setText("");
				holder.rl1.setVisibility(View.GONE);
			}
			if (it.size() > ((position + 1) * 3 + 1)) {
				holder.rl2.setVisibility(View.VISIBLE);
				jiance(item2.getHeader(), holder.iv2);
				holder.tvcenter.setText(item2.getNick());
				if(!TextUtils.isEmpty(item2.getNickclor())){
					holder.tvcenter.setTextColor(Color.parseColor(item2.getNickclor()));
					}else{
						holder.tvcenter.setTextColor(Color.parseColor("#774220"));
					}
				if(item2.getSex() == 0){
					holder.sexc.setImageResource(R.drawable.what);
				}else if(item2.getSex() == 1){
					holder.sexc.setImageResource(R.drawable.woman);
				}else if(item2.getSex() == 2){
					holder.sexc.setImageResource(R.drawable.man);
				}
			} else {
				holder.tvcenter.setText("");
				holder.rl2.setVisibility(View.GONE);
			}
			if (it.size() > ((position + 1) * 3 + 2)) {
				holder.rl3.setVisibility(View.VISIBLE);
				jiance(item3.getHeader(), holder.iv3);
				holder.tvright.setText(item3.getNick());
				if(!TextUtils.isEmpty(item3.getNickclor())){
					holder.tvright.setTextColor(Color.parseColor(item3.getNickclor()));
					}else{
						holder.tvright.setTextColor(Color.parseColor("#774220"));
					}
				if(item3.getSex() == 0){
					holder.sexr.setImageResource(R.drawable.what);
				}else if(item3.getSex() == 1){
					holder.sexr.setImageResource(R.drawable.woman);
				}else if(item3.getSex() == 2){
					holder.sexr.setImageResource(R.drawable.man);
				}
			} else {
				holder.tvright.setText("");
				holder.rl3.setVisibility(View.GONE);
			}
		}
		// holder.sf_iv.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// Log.e(":zzzz", "ddd"+it.get(holder.pt).getHeader());
		// if (v.getTag().equals(it.get(holder.pt).getHeader())) {
		// Dazuidetatil.detatilispause = true;
		// Intent intent = new Intent(context.getApplicationContext(),
		// VillageUserInfoDialog.class);
		//
		// intent.putExtra("uid",
		// String.valueOf(it.get(holder.pt).getUid()));
		// intent.putExtra("nick", it.get(holder.pt).getNick());
		// intent.putExtra("fuid", MainMenuActivity.uid);
		// intent.putExtra("type", 2);
		// context.startActivity(intent);
		// }
		// }
		// });
		iv_num1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context.getApplicationContext(),
						VillageUserInfoDialog.class);

				intent.putExtra("uid", String.valueOf(it.get(0).getUid()));
				intent.putExtra("nick", it.get(0).getNick());
				intent.putExtra("fuid", MainMenuActivity.uid);
				intent.putExtra("type", 2);
				context.startActivity(intent);
			}
		});
		iv_num2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context.getApplicationContext(),
						VillageUserInfoDialog.class);

				intent.putExtra("uid", String.valueOf(it.get(1).getUid()));
				intent.putExtra("nick", it.get(1).getNick());
				intent.putExtra("fuid", MainMenuActivity.uid);
				intent.putExtra("type", 2);
				context.startActivity(intent);
			}
		});
		iv_num3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context.getApplicationContext(),
						VillageUserInfoDialog.class);

				intent.putExtra("uid", String.valueOf(it.get(2).getUid()));
				intent.putExtra("nick", it.get(2).getNick());
				intent.putExtra("fuid", MainMenuActivity.uid);
				intent.putExtra("type", 2);
				context.startActivity(intent);
			}
		});
		holder.iv1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (v.getTag().equals(it.get((position+1) * 3).getHeader())) {
					Dazuidetatil.detatilispause = true;
					Intent intent = new Intent(context.getApplicationContext(),
							VillageUserInfoDialog.class);

					intent.putExtra("uid",
							String.valueOf(it.get((position+1) * 3).getUid()));
					intent.putExtra("nick", it.get((position+1) * 3).getNick());
					intent.putExtra("fuid", MainMenuActivity.uid);
					intent.putExtra("type", 2);
					context.startActivity(intent);
				}
			}
		});
		holder.iv2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (v.getTag().equals(it.get((position+1) * 3 + 1).getHeader())) {
					Intent intent = new Intent(context.getApplicationContext(),
							VillageUserInfoDialog.class);

					intent.putExtra("uid",
							String.valueOf(it.get((position+1) * 3 + 1).getUid()));
					intent.putExtra("nick", it.get((position+1) * 3 + 1).getNick());
					intent.putExtra("fuid", MainMenuActivity.uid);
					intent.putExtra("type", 2);
					context.startActivity(intent);
				}
			}
		});
		holder.iv3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (v.getTag().equals(it.get((position+1) * 3 + 2).getHeader())) {
					Intent intent = new Intent(context.getApplicationContext(),
							VillageUserInfoDialog.class);

					intent.putExtra("uid",
							String.valueOf(it.get((position+1) * 3 + 2).getUid()));
					intent.putExtra("nick", it.get((position+1) * 3 + 2).getNick());
					intent.putExtra("fuid", MainMenuActivity.uid);
					intent.putExtra("type", 2);
					context.startActivity(intent);
				}
			}
		});

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
					iv.setBackgroundDrawable(bd);
				}
			}
		}
	};

	private class ViewHolder {
		public ImageView iv1, iv2, iv3 , sexl , sexc , sexr,kuang1 ,kuang2,kuang3;
		public int pt;
		public TextView tvleft ,tvcenter , tvright ;
	    public FrameLayout rl1, rl2 ,rl3;
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
