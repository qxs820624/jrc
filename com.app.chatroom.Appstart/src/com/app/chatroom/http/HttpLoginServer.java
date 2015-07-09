package com.app.chatroom.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.commons.codec.Encoder;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.util.SystemUtil;

public class HttpLoginServer {
	static HttpResponse httpResponse = null;

	/**
	 * 用户登录GET方式
	 * 
	 * @param uid
	 * @param imei
	 * @param imsi
	 * @param model
	 * @param osver
	 * @param width
	 * @param height
	 * @param vercode
	 * @param ver
	 * @param phone
	 * @param sim
	 * @param lang
	 * @param pkg
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String LoginGet(String uid, String pd, String imei,
			String imsi, String model, String osver, String width,
			String height, String vercode, String ver, String phone,
			String sim, String lang, String pkg) throws ClientProtocolException, IOException {

		String strResult = "";
		String strUrl = ConstantsJrc.LOGIN + "?" + "uid=" + uid + "&lang="
				+ lang + "&width=" + width + "&height=" + height + "&ver="
				+ ver + "&vercode=" + vercode + "&imei=" + imei + "&imsi="
				+ imsi + "&osver=" + osver + "&pkg=" + pkg + "&model=" + model
				+ "&email=&phone=" + phone + "&sim=" + sim  ;
		// System.out.println(strUrl);

		// HttpGet httpRequest = new HttpGet(strUrl);
		// httpResponse = new DefaultHttpClient().execute(httpRequest);
		//
		// // 连接状态成功
		// if (httpResponse.getStatusLine().getStatusCode() == 200) {
		// /* 取出响应字符 */
		// strResult = EntityUtils.toString(httpResponse.getEntity());
		// } else {
		// strResult = "";
		// }
		strResult = SystemUtil.returnData(strUrl);
		// Log.i("text", "" + strResult);
		// 返回服务端返回的数据
		return strResult;

	}

	/**
	 * 用户登陆Post方式
	 * 
	 * @param uid
	 * @param imei
	 * @param imsi
	 * @param model
	 * @param osver
	 * @param width
	 * @param height
	 * @param vercode
	 * @param ver
	 * @param phone
	 * @param sim
	 * @param lang
	 * @param pkg
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String LoginPost(String uid, String pd, String imei,
			String imsi, String model, String osver, String width,
			String height, String vercode, String ver, String phone,
			String sim, String lang, String pkg, String mac, String cid,
			String token) throws ClientProtocolException, IOException {
		String strResult = "";
		String sCurrentLine = "";
		try {

//			lang = URLEncoder.encode(lang);
			String strUrl = "uid=" + uid + "&pd=" + pd + "&lang=" + lang
					+ "&width=" + width + "&height=" + height + "&ver=" + ver
					+ "&vercode=" + vercode + "&imei=" + imei + "&imsi=" + imsi
					+ "&osver=" + osver + "&pkg=" + pkg + "&model=" + model
					+ "&email=&phone=" + phone + "&sim=" + sim + "&mac=" + mac
					+ "&cid=" + cid + "&token=" + token;

//			 System.out.println(strUrl);
			strResult = SystemUtil.returnPostData(ConstantsJrc.LOGIN, uid,  pd, imei,
					imsi,  model, osver,  width,
					height, vercode, ver, phone,
					sim,  lang, pkg, mac, cid,
					token);
			// URL url = new URL(ConstantsJrc.LOGIN);
			// HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			// // 这里是关键，表示我们要向链接里输出内容
			// conn.setDoOutput(true);
			// conn.setRequestProperty("Accept-Encoding", "gzip");
			//
			// // 获得连接输出流
			// OutputStreamWriter out = new OutputStreamWriter(
			// conn.getOutputStream());
			// out.write(strUrl);
			// out.flush();
			// out.close();
			//
			// conn.getContentEncoding();
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
