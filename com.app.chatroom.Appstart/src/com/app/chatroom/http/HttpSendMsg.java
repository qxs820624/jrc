package com.app.chatroom.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.http.client.ClientProtocolException;

import android.util.Log;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.updata.FileUpload;
import com.app.chatroom.util.SystemUtil;

public class HttpSendMsg {

	public static String SendMsgPost(int roomid, int type, String uid,
			String touid, String msg, String afile, String pfile,
			String filetype, int alen, String token)
			throws ClientProtocolException, IOException {
		String strResult = "";
		String strUrl = "";
		try {

			strUrl = "roomid=" + roomid + "&type=" + type + "&uid=" + uid
					+ "&touid=" + touid + "&msg=" + URLEncoder.encode(msg)
					+ "&alen=" + alen + "&token=" + token;

		//	System.out.println(strUrl);

			File file = new File(pfile);
			File file2 = new File(afile);
			// Log.i("text", "P:" + pfile);
			// Log.i("text", "A:" + afile);
			if (!msg.equals("")) {
				byte[] by = FileUpload.getInstance().upload(
						ConstantsJrc.SENDMSG, strUrl, null, "");
				strResult = new String(by);
			} else if (file.exists()) {
				byte[] by = FileUpload.getInstance().upload(
						ConstantsJrc.SENDMSG, strUrl, filetype, pfile);

				strResult = new String(by);
			} else if (file2.exists()) {
				byte[] by = FileUpload.getInstance().upload(
						ConstantsJrc.SENDMSG, strUrl, filetype, afile);
				// Log.i("ttt", by.toString());
				strResult = new String(by);
			}

			// URL url = new URL(Constants.SENDMSG);
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
			// System.out.println(conn.getInputStream());
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

	public static String SendMailPost(String uid, String touid, String content,
			String afile, String pfile, String token)
			throws ClientProtocolException, IOException {
		String strResult = "";
		String sCurrentLine = "";
		try {
			String strUrl = "uid=" + uid + "&touid=" + touid + "&content="
					+ URLEncoder.encode(content) + "&afile=" + afile
					+ "&pfile=" + pfile + "&token=" + token;
			// System.out.println(strUrl);
			strResult = SystemUtil
					.returnPostData(ConstantsJrc.SENDMAIL, strUrl);
			// URL url = new URL(ConstantsJrc.SENDMAIL);
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
			// System.out.println(conn.getInputStream());
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
