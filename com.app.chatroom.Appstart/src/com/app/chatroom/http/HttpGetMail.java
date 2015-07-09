package com.app.chatroom.http;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.util.SystemUtil;

public class HttpGetMail {
	static HttpResponse httpResponse = null;

	public static String GetMail(int uid, String pd, String token)
			throws ClientProtocolException, IOException {

		String strResult = "";
		String strUrl = ConstantsJrc.MAILLISTURL + "?" + "uid=" + uid + "&pd="
				+ pd + "&token=" + token;
		// System.out.println("邮件地址：" + strUrl);
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
}
