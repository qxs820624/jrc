package com.app.chatroom.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.app.chatroom.pic.NetImageViewCache;
import com.app.chatroom.util.Commond;
import com.app.chatroom.util.SystemUtil;

public class NetImageView extends ImageView {

	private int resId;

	public NetImageView(Context context) {
		super(context);
	}

	public NetImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NetImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setImageUrl(String url, int resid, String path, String path2) {

		this.resId = resid;
		Bitmap bitmap = NetImageViewCache.getInstance().getBitmap(url, path,
				path2);
		if (bitmap != null) {
			// System.out.println("loadImage:" + url);
			this.setImageBitmap(bitmap);
		} else {
			//
			//this.setImageResource(resId);
			new NetImageDownLoad().execute(url, path, path2);
		}
	}

	private boolean saveStreamToFile(InputStream inputStream, String savepath) {
		try {
			FileOutputStream fos = new FileOutputStream(savepath);
			byte[] b = new byte[1024];
			int len = inputStream.read(b);
			while (len != -1) {
				fos.write(b, 0, len);
				len = inputStream.read(b);
			}
			fos.flush();
			fos.close();
			return true;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
	}

	private class NetImageDownLoad extends AsyncTask<String, Void, Bitmap> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		String mPicUrl = "";

		@Override
		protected Bitmap doInBackground(String... params) {
			mPicUrl = params[0];
			URL url = null;
			InputStream inputStream = null;
			HttpURLConnection urlConnection = null;
			Bitmap bmp = null;
			try {
				url = new URL(params[0]);
				urlConnection = (HttpURLConnection) url.openConnection();
				urlConnection.setRequestMethod("GET");
				urlConnection.setConnectTimeout(10000);
				inputStream = urlConnection.getInputStream();
				String path = "";
				String savepath = params[1];
				String savepath2 = params[2];
				if (SystemUtil.getSDCardMount()) {
					File file = new File(savepath);
					if (!file.exists()) {
						file.mkdirs();
					}
					path = savepath + File.separator
							+ Commond.getMd5Hash(mPicUrl);
				} else {
					File file = new File(savepath2);
					if (!file.exists()) {
						file.mkdirs();
					}
					path = savepath2 + File.separator
							+ Commond.getMd5Hash(mPicUrl);
				}
				// System.out.println("doInBackground path:" + path);
				// System.out.println("doInBackground url:" + url);
				if (saveStreamToFile(inputStream, path))
					bmp = BitmapFactory.decodeFile(path);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (null != inputStream) {
					try {
						inputStream.close();
						inputStream = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (null != urlConnection) {
					urlConnection.disconnect();
					urlConnection = null;
				}
			}
			return bmp;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			if (null != result) {
				NetImageView.this.setImageBitmap(result);
				NetImageViewCache.getInstance().put(mPicUrl, result, true);
			} else {
				NetImageView.this.setImageResource(resId);
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}
	}
}
