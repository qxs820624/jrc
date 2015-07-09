package com.app.chatroom.http;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.util.Log;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.util.SystemUtil;

public class HttpEnterRoom {

	public static String EnterRoomGet(int roomid, String uid, String sex,
			String imei, String imsi, String model, String osver, String width,
			String height, String vercode, String ver, String phone,
			String sim, String lang, String pkg, String token)
			throws ClientProtocolException, IOException {

		String strResult = "";
		String strUrl = ConstantsJrc.ENTERROOM + "?" + "roomid=" + roomid
				+ "&uid=" + uid + "&sex=" + sex + "&lang=" + lang + "&width="
				+ width + "&height=" + height + "&ver=" + ver + "&vercode="
				+ vercode + "&imei=" + imei + "&imsi=" + imsi + "&osver="
				+ osver + "&pkg=" + pkg + "&model=" + model + "&email=&phone="
				+ phone + "&sim=" + sim + "&token=" + token;

		// System.out.println("房间地址：" + strUrl);
		// HttpResponse httpResponse = null;
		// HttpGet httpRequest = new HttpGet(strUrl);
		//
		// httpResponse = new DefaultHttpClient().execute(httpRequest);
		//
		// // 连接状态成功
		// if (httpResponse.getStatusLine().getStatusCode() == 200) {
		// /* 取出响应字符 */
		// strResult = EntityUtils.toString(httpResponse.getEntity());
		//
		// } else {
		// strResult = "";
		// }
		strResult = SystemUtil.returnData(strUrl);
		// Log.i("text", "str:" + strResult);
		// 返回服务端返回的数据
		return strResult;

	}
}
