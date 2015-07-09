package com.app.chatroom.json;

import java.net.URLDecoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.app.chatroom.interfaces.JsonParse;
import com.app.chatroom.json.bean.BlackListBean;

public class BlackListJson implements JsonParse<BlackListBean> {
	BlackListBean blBean;
	ArrayList<BlackListBean> blackList = new ArrayList<BlackListBean>();

	@Override
	public ArrayList<BlackListBean> parseJson(String jsonstr) {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = new JSONObject(jsonstr);
			JSONArray jsonArray = jsonObject.getJSONArray("items");
			// System.out.println("jsonArray:" + jsonArray);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject tempJson = jsonArray.optJSONObject(i);
				blBean = new BlackListBean();
				blBean.setUid(tempJson.optInt("uid"));
				blBean.setNick(URLDecoder.decode(tempJson.optString("nick")));
				blBean.setHeader(URLDecoder.decode(tempJson.optString("header")));
				blBean.setLevel(URLDecoder.decode(tempJson.optString("level")));
				blBean.setSex(tempJson.optInt("sex"));
				blBean.setNick_c(tempJson.optString("nick_c"));
				blackList.add(blBean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return blackList;
	}

}
