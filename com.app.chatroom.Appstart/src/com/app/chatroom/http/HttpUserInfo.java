package com.app.chatroom.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.client.ClientProtocolException;

import android.os.Environment;
import android.util.Log;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.updata.FileUpload;
import com.app.chatroom.util.SystemUtil;

public class HttpUserInfo {

	public static String ModifyPhotoPost(String uid, String headerpath,
			String token) throws ClientProtocolException, IOException {
		String strResult = "";
		String sCurrentLine = "";
		try {

			String strUrl = "uid=" + uid + "&token=" + token;

			System.out.println(strUrl);
			File file = new File(headerpath);
			if (file.exists()) {
				byte[] by = FileUpload.getInstance().upload(
						ConstantsJrc.UPLOADPHOTO, strUrl, "header", headerpath);
				// Log.i("ttt", by.toString());
				strResult = new String(by);
			}

			// URL url = new URL(Constants.EDITINFO);
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
			//
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

	public static String ModifyDataPost(String uid, String pwd, String nick,
			String sign, String sex, String phone, String email,
			String birthday, String city, String token)
			throws ClientProtocolException, IOException {
		String strResult = "";
		String sCurrentLine = "";
		try {

			String strUrl = "uid=" + uid + "&pwd=" + pwd + "&nick=" + nick
					+ "&sign=" + sign + "&sex=" + sex + "&phone=" + phone
					+ "&email=" + email + "&birthday=" + birthday + "&city="
					+ city + "&token=" + token;

			// System.out.println(strUrl);
			strResult = SystemUtil
					.returnPostData(ConstantsJrc.EDITINFO, strUrl);
			// URL url = new URL(ConstantsJrc.EDITINFO);
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
			//
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

}
