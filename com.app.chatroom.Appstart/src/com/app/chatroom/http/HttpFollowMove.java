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

public class HttpFollowMove {
	// static HttpResponse httpResponse = null;

	public static String FollowMoveGet(int uid, int tuid, int flg, String token)
			throws ClientProtocolException, IOException {

		String strResult = "";
		String strUrl = ConstantsJrc.FOLLOWMOVE + "?" + "uid=" + uid + "&tuid="
				+ tuid + "&flg=" + flg + "&token=" + token;
 
		strResult = SystemUtil.returnData(strUrl);
		// Log.i("text", "" + strResult);
		// 返回服务端返回的数据
		return strResult;

	}
}
