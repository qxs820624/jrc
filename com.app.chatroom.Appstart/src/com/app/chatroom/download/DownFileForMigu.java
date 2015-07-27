package com.app.chatroom.download;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.app.chatroom.util.SystemUtil;

/**
 * 音频文件下载类
 * 
 * @author J.King
 * 
 */
public abstract class  DownFileForMigu {
	public abstract void resultData(String result);
	private ArrayList<String> urls = new ArrayList<String>();
	public Context mContext;
	public DownFileForMigu() {
		// TODO Auto-generated constructor stub
	}
	public DownFileForMigu(Context context) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
	}

	public void down(String url, String name, String path, String path2,
			ProgressBar progerss) {
		loadFile(url, name, path, path2, progerss);
	}

	public void loadFile(String url, String name, String path, String path2,
			ProgressBar progerss) {
		if (!isLocal(name, path, path2)) {
			if (urls.contains(url))
				return;
			urls.add(url);
			if (SystemUtil.getSDCardMount()) {
				new DownLoadFile(progerss).execute(url, path, name);
			} else {
				new DownLoadFile(progerss).execute(url, path2, name);
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

		String name = url;
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

		public DownLoadFile() {
		}

		public DownLoadFile(ProgressBar p) {
			this.pro = p;
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
				// Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);
				InputStream input = new BufferedInputStream(url.openStream());
				// Log.i("audio", filePath + "/" + fileUrl);
				OutputStream output = new FileOutputStream(params[1]
						+ File.separator + params[2]
						+ ".mp3");
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
				// Log.e("error", e.getMessage().toString());
				// System.out.println(e.getMessage().toString());
				return "false";
			}
			return "true";
		}

		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			if (pro != null)
				pro.setVisibility(View.GONE);
			resultData(result);
			super.onPostExecute(result);
		}

	}
}
