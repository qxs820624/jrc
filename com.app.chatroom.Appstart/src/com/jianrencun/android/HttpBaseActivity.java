package com.jianrencun.android;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.app.chatroom.MainMenuActivity;

/**
 * 
 * 
 * @author dky
 * 
 */
public abstract class HttpBaseActivity extends Activity {
	ProgressBar pro;

	public abstract void resultData(String url, String result);

	public class HttpRequestTask extends AsyncTask<String, Integer, String> {
		String mUrl;

		// 执行预处理
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// 显示进度条
			pro = new ProgressBar(HttpBaseActivity.this);
			pro.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onPostExecute(String result) {
			resultData(mUrl, result);
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			mUrl = params[0];
			if (!mUrl.contains("uid=")) {
				if (mUrl.contains("?")) {
					mUrl += "&uid=" + MainMenuActivity.uid;
				} else {
					mUrl += "?uid=" + MainMenuActivity.uid;
				}
			}
			if (!mUrl.contains("token=")) {
				if (mUrl.contains("?")) {
					mUrl += "&token=" + MainMenuActivity.token;
				} else {
					mUrl += "?token=" + MainMenuActivity.token;
				}
			}
			
			String result = null;
			InputStream is = null;
			String strResult = "";
			String sCurrentLine = "";

			try {
				HttpClient client = new DefaultHttpClient();
				HashMap<String, String> data = new HashMap<String, String>();

				if (params.length > 1) {
					String[] keyvalues = params[1].split("&");
					for (String keyvalue : keyvalues) {
						String[] tmp = keyvalue.split("=");
						if (tmp.length == 1) {
							data.put(tmp[0], "");
						} else {
							data.put(tmp[0], tmp[1]);
						}
					}
				}
				HttpUriRequest request = getRequest(params[0], data, "POST");
				request.addHeader("Accept-Encoding", "gzip");
				HttpResponse response = client.execute(request);
				//
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					Header header = response.getEntity().getContentEncoding();
					if (header != null) {
						String str = header.getValue();
						// 判断是否为压缩数据
						if (str != null && str.compareToIgnoreCase("gzip") == 0) {
							is = new GZIPInputStream(response.getEntity()
									.getContent());
						}
					} else {
						is = response.getEntity().getContent();
					}

					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is));

					while ((sCurrentLine = reader.readLine()) != null) {
						strResult += sCurrentLine + "\r\n";
					}
					try {
						if (is != null)
							is.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// System.out.println("strresult:" + strResult);
					result = strResult;
					// result = EntityUtils.toString(response.getEntity());

				}
			} catch (Exception ex) {
				result = ex.getMessage();
				ex.printStackTrace();
			}
			pro.setVisibility(View.GONE);
			return result;
		}

		/**
		 * 
		 * 
		 * @param url
		 * @param params
		 * @param method
		 * @return
		 */
		public HttpUriRequest getRequest(String url,
				Map<String, String> params, String method) {
			if (method.equals("POST")) {
				List<NameValuePair> listParams = new ArrayList<NameValuePair>();
				if (params != null) {
					for (String name : params.keySet()) {
						listParams.add(new BasicNameValuePair(name, params
								.get(name)));
					}
				}
				try {
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
							listParams);
					HttpPost request = new HttpPost(url);
					request.setEntity(entity);
					return request;
				} catch (UnsupportedEncodingException e) {
					// Should not come here, ignore me.
					throw new java.lang.RuntimeException(e.getMessage(), e);
				}
			} else {
				if (url.indexOf("?") < 0) {
					url += "?";
				}
				if (params != null) {
					for (String name : params.keySet()) {
						url += "&" + name + "="
								+ URLEncoder.encode(params.get(name));
					}
				}
				HttpGet request = new HttpGet(url);
				request.addHeader("Accept-Encoding", "gzip");
				return request;
			}
		}
	}
}