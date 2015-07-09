package com.jianrencun.game;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.app.chatroom.Appstart;
import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.otherui.SystemMsgWebDialog;
import com.app.chatroom.otherui.VillageUserInfoDialog;
import com.app.chatroom.util.Commond;
import com.app.chatroom.util.PhoneInfo;
import com.duom.fjz.iteminfo.BitmapCache;
import com.duom.fjz.iteminfo.BitmapCacheDzlb;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Comment;
import com.jianrencun.game.GameAdapter.LoadImageRunnable;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GamePaihangbangAdapter extends BaseAdapter {
	private Context context;
	private List<GamebigInfo> it;
	private ListView lv;
	LayoutInflater inflater;
	GamebigInfo item;
	File picfile;
    int typef ;
    Activity activity ;
	public GamePaihangbangAdapter(Context context, List<GamebigInfo> it,
			ListView lv , int typef, Activity activity) {
		this.context = context;
		this.it = it;
		this.lv = lv;
		inflater = LayoutInflater.from(context);
		this.typef = typef ;
		this.activity = activity ;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return it.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return it.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	// 每个convert view都会调用此方法，获得当前所需要的view样式
	@Override
	public int getItemViewType(int position) {
		if (!TextUtils.isEmpty(it.get(position).getNick())) {
			return 1;
		} else if (TextUtils.isEmpty(it.get(position).getNick()) && !TextUtils.isEmpty(it.get(position).getTitle())) {
			return 2;
		} 
		else {
			return 1;
		}
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		ViewHolder2 holder2 = null;
		ViewHolder3 holder3 = null ;
		int type = getItemViewType(position);

		// 无convertView，需要new出各个控件
		if (convertView == null) {
			// 按当前所需的样式，确定new的布局
			switch (type) {
			case 1:
				convertView = inflater.inflate(R.layout.game_paihangbang_item,
						parent, false);
				holder = new ViewHolder();
				holder.content = (TextView) convertView
						.findViewById(R.id.game_paihangbang_content);
				holder.date = (TextView) convertView
						.findViewById(R.id.game_paihangbang_userdate);
				holder.title = (TextView) convertView
						.findViewById(R.id.game_paihangbang_usertitle);
				holder.imageheader = (ImageView) convertView
						.findViewById(R.id.game_paihangbang_header);
				holder.relativell = (RelativeLayout)convertView.findViewById(R.id.game_paihangbang_bigrl);
				holder.ivk = (ImageView) convertView
						.findViewById(R.id.game_paihangbang_headerw);
				holder.num = (TextView) convertView
						.findViewById(R.id.game_paihangbang_num);
				convertView.setTag(holder);
				break;
			case 2:
				holder2 = new ViewHolder2();
				convertView = inflater.inflate(
						R.layout.game_paihang_item_title, parent, false);

				holder2.title_top = (TextView) convertView.findViewById(R.id.game_paihangbang_title_top);
				holder2.notv = (TextView) convertView.findViewById(R.id.phb_no_tv);
				convertView.setTag(holder2);
				break;
			}
		} else {
			// 有convertView，按样式，取得不用的布局
			switch (type) {
			case 1:
				holder = (ViewHolder) convertView.getTag();
				break;
			case 2:
				holder2 = (ViewHolder2) convertView.getTag();
				break;
			case 3:
				holder3 = (ViewHolder3) convertView.getTag();
				break;
			}
		}
		item = it.get(position);
		// 设置资源
		switch (type) {
		case 1:
			holder.imageheader.setTag(item.getHeader());
			holder.imageheader.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (v.getTag().equals(it.get(position).getHeader())) {
						if (TextUtils.isEmpty(it.get(position).getHeader())) {
							Commond.showToast(context, "非本村村民！");
						} else {
																					
							if(it.get(position).getJiazubj() == 0){
								Intent intent = new Intent(context,
										VillageUserInfoDialog.class);
								intent.putExtra("uid", String.valueOf(it.get(position).getUid()));
								intent.putExtra("nick", it.get(position).getNick());
								intent.putExtra("fuid", MainMenuActivity.uid);
								intent.putExtra("type", 2);
								context.startActivity(intent);
								}else if(it.get(position).getJiazubj() == 1){
									Intent intent = new Intent(context,
											SystemMsgWebDialog.class);
									intent.putExtra(
											"link",
											ConstantsJrc.MAINMORE
													+ "?uid="
													+ MainMenuActivity.uid
													+ "&flg=7&w="
													+ PhoneInfo.getInstance(context)
															.getWidth(activity)
													+ "&pkg="
													+ PhoneInfo.getInstance(context)
															.getPackage(activity)
													+ "&ver="
													+ PhoneInfo.getInstance(context)
															.getVersionName(activity)
													+ "&rid=" + it.get(position).getUid());

//									intent.putExtra("roomtype", roombean.getType());
									intent.putExtra("type", "7");
									context.startActivity(intent);
								}
						}
					}
				}
			});
			jiance(context, item.getHeader(), holder.imageheader, mHandler);
			holder.date.setText(item.getOrd());
			if(item.getTp() == 1){
			holder.date.setTextColor(Color.parseColor("#fffbbf"));
			}else{
				holder.date.setTextColor(Color.parseColor("#213642"));	
			}
			holder.title.setText(item.getNick());
			if (!TextUtils.isEmpty(item.getNick_c())) {
				holder.title.setTextColor(Color.parseColor(item.getNick_c()));
			}else{
				if(item.getTp() == 1){
				holder.title.setTextColor(Color.parseColor("#fffbbf"));
				}else{
					holder.title.setTextColor(Color.parseColor("#213642"));
				}
			}
			if(!TextUtils.isEmpty(item.getNum())){
				holder.num.setVisibility(View.VISIBLE);
				holder.num.setText(item.getNum());
				holder.num.setTextColor(Color.parseColor("#ffe639"));
			}else{
				holder.num.setVisibility(View.GONE);
			}
			if(typef == 1){
				holder.relativell.setBackgroundResource(R.drawable.gameitem2); 
				holder.ivk.setImageResource(R.drawable.w_zp);
			}else{
				holder.relativell.setBackgroundResource(R.drawable.gameitem1); 
				holder.ivk.setImageResource(R.drawable.w_ph);
			}
			holder.content.setText(item.getScore());
			if(item.getTp() == 1){
				holder.content.setTextColor(Color.parseColor("#ffe639"));
			}else{
				holder.content.setTextColor(Color.parseColor("#777777"));
			}
			break;
		case 2:
			holder2.title_top.setText(item.getTitle());
			if(TextUtils.isEmpty(it.get(position+1).getNick())){
				holder2.notv.setVisibility(View.VISIBLE);
			}else{
				holder2.notv.setVisibility(View.GONE);
			}
			if(item.getTp() == 1){
			holder2.title_top.setTextColor(Color.parseColor("#ffffff"));
			}else{
				holder2.title_top.setTextColor(Color.parseColor("#3d263f"));
			}
			break;
		case 3:
			
			break;
		}
		return convertView;
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

				ImageView iv = (ImageView) lv.findViewWithTag(url);
				if (iv != null) {
					BitmapDrawable bd = new BitmapDrawable(bmp);
					iv.setImageDrawable(bd);
				}
			}
		}
	};

	private class ViewHolder {
		public ImageView imageheader , ivk;
		public TextView fcount;
		public RelativeLayout relativell;
		public TextView title;
		public TextView content;
		public TextView date , num;
		
	}

	private class ViewHolder2 {
		public TextView title_top , notv;
	}

	private class ViewHolder3 {
		public TextView tv;
	}
	public static void jiance(Context context, String header, ImageView iv,
			Handler mhandler) {
		File picfile = new File(Appstart.jrcfile + "/"
				+ Commond.getMd5Hash(header));
		String filename = picfile.getPath().toString();
		ArrayList<String> urls = new ArrayList<String>();
		Bitmap bmp = null;
		if (picfile.exists()) {
			bmp = BitmapCache.getIntance().getCacheBitmap(filename, 0, 0);
		}
		if (bmp == null) {
			if (!urls.contains(header)) {
				urls.add(header);
				new Thread(new LoadImageRunnable(context, mhandler, 0, header,
						filename)).start();
			}
			iv.setImageResource(R.drawable.liwupic2);
		} else {
			BitmapDrawable bd = new BitmapDrawable(bmp);
			iv.setImageDrawable(bd);
		}
	}
}
