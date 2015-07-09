package com.app.chatroom.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.client.ClientProtocolException;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.util.SystemUtil;

public class HttpGetCode {
	public static String GetCodePost(String uid, String phone, String email,
			String token) throws ClientProtocolException, IOException {
		String strResult = "";
		String sCurrentLine = "";
		try {
			String strUrl = "uid=" + uid + "&phone=" + phone + "&email="
					+ email + "&token=" + token;

			// String strUrl = "roomid=" + roomid + "&type=" + type + "&uid="
			// + uid + "&touid=" + touid + "&msg="
			// + URLEncoder.encode(msg);

			// System.out.println(strUrl);
			strResult = SystemUtil
					.returnPostData(ConstantsJrc.CODE_URL, strUrl);
			// URL url = new URL(ConstantsJrc.CODE_URL);
			// URLConnection conn = url.openConnection();
			// // 这里是关键，表示我们要向链接里输出内容
			// conn.setDoOutput(true);
			//
			// // 获得连接输出流
			// OutputStreamWriter out = new OutputStreamWriter(
			// conn.getOutputStream());
			//
			// out.write(strUrl);
			// out.flush();
			// out.close();
			// // System.out.println(conn.getInputStream());
			// /** 请求完成，获取返回数据 **/
			// BufferedReader reader = new BufferedReader(new InputStreamReader(
			// conn.getInputStream()));
			//
			// while ((sCurrentLine = reader.readLine()) != null) {
			// strResult += sCurrentLine + "\r\n";
			// }

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return strResult;

	}

	public static String ReLoadPost(String uid, String phone, String email,
			String pwd) throws ClientProtocolException, IOException {
		String strResult = "";
		String sCurrentLine = "";
		try {
			String strUrl = "uid=" + uid + "&phone=" + phone + "&email="
					+ email + "&pwd=" + pwd;

			// String strUrl = "roomid=" + roomid + "&type=" + type + "&uid="
			// + uid + "&touid=" + touid + "&msg="
			// + URLEncoder.encode(msg);

			// System.out.println(strUrl);

			URL url = new URL(ConstantsJrc.CHOOSEID_URL);
			URLConnection conn = url.openConnection();
			// 这里是关键，表示我们要向链接里输出内容
			conn.setDoOutput(true);

			// 获得连接输出流
			OutputStreamWriter out = new OutputStreamWriter(
					conn.getOutputStream());

			out.write(strUrl);
			out.flush();
			out.close();
			// System.out.println(conn.getInputStream());
			/** 请求完成，获取返回数据 **/
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			while ((sCurrentLine = reader.readLine()) != null) {
				strResult += sCurrentLine + "\r\n";
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return strResult;

	}

}
