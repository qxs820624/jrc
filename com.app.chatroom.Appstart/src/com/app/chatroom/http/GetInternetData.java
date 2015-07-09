package com.app.chatroom.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

/**
 * @author Administrator 获取网络数据
 */
public class GetInternetData {
	/**
	 * 获取网络地址的数据，以流的形式返回
	 * 
	 * @param uri
	 *            网络url地址
	 * @return 输入流
	 * @throws IOException
	 */
	public static InputStream getInputStream(String uri) throws IOException {
		URL url = new URL(uri);
		// System.out.println("传入链接：" + url);
		InputStream inStream = null;
		// 打开指定地址的链接
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// 设置请求方式
		conn.setRequestMethod("GET");
		conn.setDefaultUseCaches(true);
		// 设置超时时间
		conn.setConnectTimeout(100 * 1000);
		conn.setReadTimeout(100 * 1000);
		conn.setDoInput(true);
		// 得到请求内容的长度
		conn.getContentLength();
		// 得到请求内容的类型
		conn.getContentType();
		// 响应吗为200
		if (conn.getResponseCode() == 200) {
			inStream = conn.getInputStream();
		} else {
			// throw new RuntimeException("net error");
		}

		if (conn != null) {
			conn.disconnect();
		}

		// 返回得到的流
		return inStream;
	}
}
