package com.app.chatroom.http;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.util.SystemUtil;

public class HttpGetMessage {
	static HttpResponse httpResponse = null;

	public static String GetMessage(int roomid, int uid, String pid,
			String ver, String token) throws ClientProtocolException,
			IOException {

		String strResult = "";
		String strUrl = ConstantsJrc.MSGURL + "?" + "roomid=" + roomid
				+ "&uid=" + uid + "&pid=" + pid + "&ver=" + ver + "&token="
				+ token;

		// System.out.println("消息地址：" + strUrl);
		strResult = SystemUtil.returnData(strUrl);
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
		// Log.i("text", "" + strResult);
		// 返回服务端返回的数据
		return strResult;

	}
}
