package com.app.chatroom.json;

import java.net.URLDecoder;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.app.chatroom.interfaces.JsonParse;
import com.app.chatroom.json.bean.AttentionUserBean;
import com.app.chatroom.json.bean.LiwuItemsBean;
import com.app.chatroom.json.bean.OnlineUserBean;

public class LiwuListJson implements JsonParse<LiwuItemsBean> {
	LiwuItemsBean lib;
	ArrayList<LiwuItemsBean> liwuList = new ArrayList<LiwuItemsBean>();

	@Override
	public ArrayList<LiwuItemsBean> parseJson(String jsonstr) {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = new JSONObject(jsonstr);
			JSONArray jsonArray = jsonObject.getJSONArray("items");
			// System.out.println("jsonArray:" + jsonArray);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject tempJson = jsonArray.optJSONObject(i);
				lib = new LiwuItemsBean();
				lib.setId(tempJson.optInt("id"));
				lib.setName(URLDecoder.decode(tempJson.optString("name")));
				lib.setLogo(URLDecoder.decode(tempJson.optString("logo")));
				lib.setDesc(URLDecoder.decode(tempJson.optString("desc")));
				lib.setLevel(URLDecoder.decode(tempJson.optString("level")));
				lib.setPrice(URLDecoder.decode(tempJson.optString("price")));
				lib.setMark(URLDecoder.decode(tempJson.optString("mark")));
				lib.setCount(tempJson.optInt("count"));

				liwuList.add(lib);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return liwuList;
	}

}
