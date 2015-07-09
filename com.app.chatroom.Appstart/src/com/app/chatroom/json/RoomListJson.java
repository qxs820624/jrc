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
import com.app.chatroom.json.bean.RoomListBean;

public class RoomListJson implements JsonParse<RoomListBean> {
	RoomListBean rlb;
	ArrayList<RoomListBean> roomList = new ArrayList<RoomListBean>();

	@Override
	public ArrayList<RoomListBean> parseJson(String jsonstr) {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = new JSONObject(jsonstr);
			JSONArray jsonArray = jsonObject.getJSONArray("items");
			// System.out.println("jsonArray:" + jsonArray);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject tempJson = jsonArray.optJSONObject(i);
				rlb = new RoomListBean();
				rlb.setId(tempJson.optInt("id"));
				rlb.setName(URLDecoder.decode(tempJson.optString("name")));
				rlb.setLogo(URLDecoder.decode(tempJson.optString("logo")));
				rlb.setDesc(URLDecoder.decode(tempJson.optString("desc")));
				rlb.setTitle(URLDecoder.decode(tempJson.optString("title")));
				rlb.setCount(URLDecoder.decode(tempJson.optString("count")));
				rlb.setHeader_1(URLDecoder.decode(tempJson
						.optString("header_1")));
				rlb.setTname(URLDecoder.decode(tempJson.optString("tname")));
				rlb.setType(tempJson.optInt("type"));
				rlb.setFlg(tempJson.optInt("flg"));
				roomList.add(rlb);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return roomList;
	}

}
