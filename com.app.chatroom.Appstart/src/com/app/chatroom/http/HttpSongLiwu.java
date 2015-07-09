package com.app.chatroom.http;

import android.util.Log;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.util.SystemUtil;

public class HttpSongLiwu {
	public static String LiwuGet(int uid, int touid, int gid, String num,
			int flg, int src, int oid, String token) {
		String strResult = "";
		String strUrl = ConstantsJrc.TOGIFT + "?uid=" + uid + "&touid=" + touid
				+ "&gid=" + gid + "&num=" + num + "&flg=" + flg + "&src=" + src
				+ "&oid=" + oid + "&token=" + token;
		strResult = SystemUtil.returnData(strUrl);
		return strResult;
	}
}
