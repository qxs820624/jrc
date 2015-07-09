package com.app.chatroom.json;

import java.net.URLDecoder;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.app.chatroom.interfaces.JsonParse;
import com.app.chatroom.json.bean.OnlineUserBean;

public class OnlineUserListJson implements JsonParse<OnlineUserBean> {
	OnlineUserBean oub;
	ArrayList<OnlineUserBean> onlineList = new ArrayList<OnlineUserBean>();

	@Override
	public ArrayList<OnlineUserBean> parseJson(String jsonstr) {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = new JSONObject(jsonstr);
			JSONArray jsonArray = jsonObject.getJSONArray("items");
			// System.out.println("jsonArray:" + jsonArray);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject tempJson = jsonArray.optJSONObject(i);
				oub = new OnlineUserBean();
				oub.setId(tempJson.optInt("uid"));
				oub.setType(tempJson.optInt("type"));
				oub.setNick(URLDecoder.decode(tempJson.optString("nick")));
				oub.setHeader(URLDecoder.decode(tempJson.optString("header")));
				// oub.setMoney(URLDecoder.decode(tempJson.getString("money")));
				// oub.setSign(URLDecoder.decode(tempJson.getString("sign")));
				oub.setLevel(URLDecoder.decode(tempJson.optString("level")));
				oub.setNick_c(tempJson.optString("nick_c"));
				oub.setSex(tempJson.optInt("sex"));

				onlineList.add(oub);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return onlineList;
	}

}
