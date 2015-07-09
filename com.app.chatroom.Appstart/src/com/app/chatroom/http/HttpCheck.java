package com.app.chatroom.http;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.util.Log;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.util.SystemUtil;

public class HttpCheck {

	public static String CheckInfoGet(String uid, String vercode, String ver,
			String pkg, String token) throws ClientProtocolException,
			IOException {

		String strResult = "";
		String strUrl = ConstantsJrc.CHECK + "?" + "&uid=" + uid + "&ver="
				+ ver + "&vercode=" + vercode + "&pkg=" + pkg + "&token="
				+ token;

		strResult = SystemUtil.returnData(strUrl);
		// Log.i("text", "str:" + strResult);
		// 返回服务端返回的数据
		return strResult;

	}
}
