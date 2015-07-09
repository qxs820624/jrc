package com.app.chatroom.json;

import java.net.URLDecoder;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.app.chatroom.interfaces.JsonParse;
import com.app.chatroom.json.bean.AttentionUserBean;
import com.app.chatroom.json.bean.OnlineUserBean;

public class AttentionUserListJson implements JsonParse<AttentionUserBean> {
	AttentionUserBean aub;
	ArrayList<AttentionUserBean> attentionList = new ArrayList<AttentionUserBean>();

	@Override
	public ArrayList<AttentionUserBean> parseJson(String jsonstr) {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = new JSONObject(jsonstr);
			JSONArray jsonArray = jsonObject.getJSONArray("items");
			// System.out.println("jsonArray:" + jsonArray);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject tempJson = jsonArray.optJSONObject(i);
				aub = new AttentionUserBean();
				aub.setUid(tempJson.optInt("uid"));
				aub.setNick(URLDecoder.decode(tempJson.optString("nick")));
				aub.setHeader(URLDecoder.decode(tempJson.optString("header")));
				aub.setVip(tempJson.optInt("vip"));
				aub.setSex(tempJson.optInt("sex"));
				aub.setFollowed(tempJson.optInt("followed"));
				aub.setNick_c(tempJson.optString("nick_c"));
				aub.setTime(URLDecoder.decode(tempJson.optString("lt")));
				aub.setIsfollow(true);
				attentionList.add(aub);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return attentionList;
	}

}
