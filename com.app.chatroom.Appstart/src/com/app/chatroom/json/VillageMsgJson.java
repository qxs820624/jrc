package com.app.chatroom.json;

import java.net.URLDecoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app.chatroom.interfaces.JsonParse;
import com.app.chatroom.json.bean.VillageMsgBean;

public class VillageMsgJson implements JsonParse<VillageMsgBean> {
	VillageMsgBean villageMsgBean;
	ArrayList<VillageMsgBean> list = new ArrayList<VillageMsgBean>();

	@Override
	public ArrayList<VillageMsgBean> parseJson(String jsonstr) {
		// TODO Auto-generated method stub

		try {
			JSONObject jsonObject = new JSONObject(jsonstr);
			JSONArray jsonArray = jsonObject.getJSONArray("msgs");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject tempJson = jsonArray.optJSONObject(i);
				villageMsgBean = new VillageMsgBean();
				villageMsgBean.setId(tempJson.optInt("id"));
				villageMsgBean.setLink(URLDecoder.decode(tempJson
						.optString("link")));
				villageMsgBean.setContent(URLDecoder.decode(tempJson
						.optString("content")));
				list.add(villageMsgBean);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
