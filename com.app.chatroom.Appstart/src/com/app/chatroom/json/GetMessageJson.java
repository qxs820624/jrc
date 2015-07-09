package com.app.chatroom.json;

import java.net.URLDecoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.app.chatroom.interfaces.JsonParse;
import com.app.chatroom.json.bean.ChatMessageBean;

public class GetMessageJson implements JsonParse<ChatMessageBean> {
	ChatMessageBean cmf;
	ArrayList<ChatMessageBean> list = new ArrayList<ChatMessageBean>();

	@Override
	public ArrayList<ChatMessageBean> parseJson(String jsonstr) {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = new JSONObject(jsonstr);
			JSONArray jsonArray = jsonObject.getJSONArray("msgitems");
			// System.out.println("jsonArray:" + jsonArray);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject tempJson = jsonArray.optJSONObject(i);
				cmf = new ChatMessageBean();
				cmf.setId(tempJson.optInt("id"));
				cmf.setType(tempJson.optInt("type"));
				cmf.setIcon(URLDecoder.decode(tempJson.optString("icon")));
				cmf.setFuid(tempJson.optInt("fuid"));
				cmf.setFnick(URLDecoder.decode(tempJson.optString("fnick")));
				cmf.setFheader(URLDecoder.decode(tempJson.optString("fheader")));
				cmf.setTuid(tempJson.optString("tuid"));
				cmf.setTnick(URLDecoder.decode(tempJson.optString("tnick")));
				cmf.setContent(URLDecoder.decode(tempJson.optString("content")));
				cmf.setAfile(URLDecoder.decode(tempJson.optString("afile")));
				cmf.setAlen(tempJson.optString("alen"));
				cmf.setAsize(tempJson.optString("asize"));
				cmf.setPfile(URLDecoder.decode(tempJson.optString("pfile")));
				cmf.setPd(URLDecoder.decode(tempJson.optString("pd")));
				cmf.setContent_c(URLDecoder.decode(tempJson.optString("content_c")));
				cmf.setContent_b(tempJson.optInt("content_b"));
				cmf.setContent_l(tempJson.optInt("content_l"));
				cmf.setNick_c(tempJson.optString("fnick_c"));
				cmf.setPw(tempJson.optInt("pw"));
				cmf.setPh(tempJson.optInt("ph"));
				cmf.setQp(tempJson.optInt("qp1"));		
				cmf.setIsOK(1);
				list.add(cmf);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
}
