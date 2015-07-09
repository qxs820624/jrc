package com.duom.fjz.iteminfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.chatroom.Appstart;
import com.app.chatroom.MainMenuActivity;
import com.jianrencun.android.Details;
import com.jianrencun.chatroom.R;

public class Adapterwithpic extends BaseAdapter {
	Context context;
	ViewHolder holder = null;
	Vh v = null;
	WindowManager wm;
	int height;
	List<Iteminfo> it;
	List<String> num;
	boolean flag , isswith;
	Iteminfo item;
	Bitmap bit;
	ListView listview;
	Drawable db;
	File picfile;
	int po;
	OnClickListener listener;
	private Details dt= new Details(); 
	// Thread th = new TestThread();

//	File saveFile = new File(path, ""); 

	private ArrayList<String> urls = new ArrayList<String>();

	public Adapterwithpic(Context context, List<Iteminfo> it,
			List<String> num, ListView listview , boolean isswith) {
		this.context = context;
		this.it = it;
		this.num = num;
		this.listview = listview;
		this.isswith = isswith ;
	}
	public Adapterwithpic(Context context, List<Iteminfo> it,
			List<String> num, ListView listview , boolean isswith , OnClickListener listener) {
		this.context = context;
		this.it = it;
		this.num = num;
		this.listview = listview;
		this.isswith = isswith ;
		this.listener = listener;
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
	public Iteminfo getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	};

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View itemView = convertView;
		ImageView imageView = null;
		Drawable cachedDrawable = null;

		if (itemView == null) {
			itemView = LayoutInflater.from(context).inflate(R.layout.listitem,
					null);
			holder = new ViewHolder();
			// holder.itemll = (LinearLayout)itemView.findViewById(R.id.itemll);
			holder.relative = (RelativeLayout) itemView
					.findViewById(R.id.relative);
			holder.linear = (LinearLayout) itemView.findViewById(R.id.linear);
			holder.listitem = (LinearLayout) itemView
					.findViewById(R.id.listitem);
			holder.content = (TextView) itemView.findViewById(R.id.content);
			holder.date = (TextView) itemView.findViewById(R.id.date);		
			// holder.criticsm = (TextView)
			// itemView.findViewById(R.id.criticsm);
			holder.imageheader = (ImageView) itemView
					.findViewById(R.id.imageview);
			holder.shenhe = (ImageView) itemView
					.findViewById(R.id.shenhe);
			itemView.setTag(holder);
		} else {
			holder = (ViewHolder) itemView.getTag();
		}
		holder.delete =(Button)itemView.findViewById(R.id.mysc_deletepic);
		holder.delete.setTag(holder);
		item = it.get(position);
		holder.positiondele = position ;
		holder.imageheader.setTag(item.getHeader());
		holder.shenhe.setTag(item.getHeader());
		holder.content.setText(item.getContent());
		holder.date.setText(item.getDate());     
		if (TextUtils.isEmpty(item.getHeader())) {
			holder.linear.setVisibility(View.GONE);
		} else {
			holder.linear.setVisibility(View.VISIBLE);
			picfile = new File(Appstart.jrcfile + "/" + getMd5Hash(item.getHeader()));
			String filename = picfile.getPath().toString();
			
			Bitmap bmp = null ;
			if(picfile.exists()){
				 bmp = BitmapCache.getIntance()
						.getCacheBitmap(filename, 0, 0);
			}				
			if (bmp == null) {
				if (!urls.contains(item.getHeader())) {
					urls.add(item.getHeader());					 
					new Thread(new LoadImageRunnable(this.context, mHandler, 0,
							item.getHeader(), filename)).start();
				}
				 holder.imageheader.setImageResource(R.drawable.quesunpic);
			}	
			else{
			holder.imageheader.setImageBitmap(bmp);	
			}
		}
			
		holder.delete.setOnClickListener(listener);
		
		if(item.getStatus() == 0){
			holder.shenhe.setVisibility(View.VISIBLE);
			holder.shenhe.setImageResource(R.drawable.passed);
		}else if(item.getStatus() == 1){
			holder.shenhe.setVisibility(View.VISIBLE);
			holder.shenhe.setImageResource(R.drawable.passing);
		}else if(item.getStatus() == 2){
			holder.shenhe.setVisibility(View.VISIBLE);
			holder.shenhe.setImageResource(R.drawable.notpass);				
		}
		holder.relative.setBackgroundResource(R.drawable.listitem1);
		String url2 = dt.appendNameValue(item.getLink(), "pkg", MainMenuActivity.pakName);
		if (num.contains(url2)) {		
			holder.content.setTextColor(Color.parseColor("#9d8f98"));
			holder.date.setTextColor(Color.parseColor("#b9b5b8"));
		} else {
			holder.content.setTextColor(Color.parseColor("#000000"));
			holder.date.setTextColor(Color.parseColor("#827a80"));
		}
		if(isswith == true){
			holder.delete.setVisibility(View.VISIBLE);
		}else{
			holder.delete.setVisibility(View.GONE);
		}

		return itemView;
	};
	

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			if (data != null) {
				String url = data.getString("url");
				String filename = data.getString("filename");

				Bitmap bmp = BitmapCache.getIntance().getCacheBitmap(filename,
						0, 0);							
				ImageView iv = (ImageView) listview.findViewWithTag(url);
				if (iv != null) {
					iv.setImageBitmap(bmp);
					if(item.getStatus() == 0){
						holder.shenhe.setVisibility(View.VISIBLE);
						holder.shenhe.setImageResource(R.drawable.passed);
					}else if(item.getStatus() == 1){
						holder.shenhe.setVisibility(View.VISIBLE);
						holder.shenhe.setImageResource(R.drawable.passing);
					}else if(item.getStatus() == 2){
						holder.shenhe.setVisibility(View.VISIBLE);
						holder.shenhe.setImageResource(R.drawable.notpass);				
					}
				} else {
					holder.linear.setVisibility(View.INVISIBLE);
				}
			}
		}
	};

	public  class ViewHolder {
		public LinearLayout itemll, listitem, linear;
		public ImageView imageheader ,shenhe;
		public RelativeLayout relative;
		public TextView content;
		public TextView date;
		public TextView criticsm;
		public Button delete;
		public int positiondele;
	}

	public class Vh {
		public ImageView imageheader;
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	public static String getMd5Hash(String url) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(url.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String md5 = number.toString(16);
			return md5;
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	/**
	 * 将url返回的内容保存为文件
	 * 
	 * @param url
	 */
	public static void urlToFile(Context context, String url, String filename) {
		try {
			url= url.replaceAll(" ", "%20");
			HttpGet httpRequest = new HttpGet(url);
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = (HttpResponse) httpclient
					.execute(httpRequest);
			HttpEntity entity = response.getEntity();
			BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(
					entity);
			InputStream s = bufferedHttpEntity.getContent();
			// bitmap = BitmapFactory.decodeStream(is);
			FileOutputStream fos = new FileOutputStream(filename);
			byte[] buffer = new byte[1024];
			int len = s.read(buffer);
			while (len > 0) {
				fos.write(buffer, 0, len);
				len = s.read(buffer);
			}
			fos.flush();
			fos.close();
			fos = null;
			s.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private Drawable downloadDrawable(String imgURL) {
		Drawable drawable = null;
		if (bit == null) {
			try {
				URL url = new URL(imgURL);
				drawable = Drawable.createFromStream(url.openStream(), "src");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			drawable = db;
		}
		return drawable;
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

			urlToFile(mContext, sUrl, mFilename);
			Message msg = new Message();
			Bundle data = new Bundle();
			data.putString("url", sUrl);
			data.putString("filename", mFilename);
			msg.setData(data);
			mHandler.sendMessage(msg);
		}

	}

}
