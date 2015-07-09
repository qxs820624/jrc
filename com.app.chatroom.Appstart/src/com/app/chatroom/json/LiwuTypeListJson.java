package com.app.chatroom.json;

import java.net.URLDecoder;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.app.chatroom.interfaces.JsonParse;
import com.app.chatroom.json.bean.AttentionUserBean;
import com.app.chatroom.json.bean.LiwuItemsBean;
import com.app.chatroom.json.bean.LiwuTypeBean;
import com.app.chatroom.json.bean.OnlineUserBean;

public class LiwuTypeListJson implements JsonParse<LiwuTypeBean> {
	LiwuTypeBean lib;
	ArrayList<LiwuTypeBean> liwuList = new ArrayList<LiwuTypeBean>();

	@Override
	public ArrayList<LiwuTypeBean> parseJson(String jsonstr) {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = new JSONObject(jsonstr);
			JSONArray jsonArray = jsonObject.getJSONArray("items");
			// System.out.println("jsonArray:" + jsonArray);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject tempJson = jsonArray.optJSONObject(i);
				lib = new LiwuTypeBean();
				lib.setId(tempJson.optInt("id"));
				lib.setName(URLDecoder.decode(tempJson.optString("name")));
				lib.setLogo(URLDecoder.decode(tempJson.optString("logo")));
				liwuList.add(lib);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return liwuList;
	}

}
