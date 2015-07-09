package com.app.chatroom.json;

import java.net.URLDecoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app.chatroom.interfaces.JsonParse;
import com.app.chatroom.json.bean.VillagePeopleBean;

public class VillagePeopleJson implements JsonParse<VillagePeopleBean> {
	VillagePeopleBean villagePeopleBean;
	ArrayList<VillagePeopleBean> list = new ArrayList<VillagePeopleBean>();

	@Override
	public ArrayList<VillagePeopleBean> parseJson(String jsonstr) {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = new JSONObject(jsonstr);
			JSONArray jsonArray = jsonObject.getJSONArray("items");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject tempJson = jsonArray.optJSONObject(i);
				villagePeopleBean = new VillagePeopleBean();
				villagePeopleBean.setUid(tempJson.optInt("uid"));
				villagePeopleBean.setNo(URLDecoder.decode(tempJson
						.optString("no")));
				villagePeopleBean.setNick(URLDecoder.decode(tempJson
						.optString("nick")));
				villagePeopleBean.setSign(URLDecoder.decode(tempJson
						.optString("sign")));
				villagePeopleBean.setHeader(URLDecoder.decode(tempJson
						.optString("header")));
				villagePeopleBean.setLevel(URLDecoder.decode(tempJson
						.optString("level")));
				villagePeopleBean.setMoney(URLDecoder.decode(tempJson
						.optString("money")));
				villagePeopleBean.setType(tempJson.optInt("type"));
				villagePeopleBean.setSex(tempJson.optInt("sex"));
				villagePeopleBean.setNick_c(tempJson.optString("nick_c"));
				list.add(villagePeopleBean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
