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
import com.app.chatroom.json.bean.PitemsBean;
import com.app.chatroom.json.bean.RoomListBean;

public class PitemsJson implements JsonParse<PitemsBean> {
	PitemsBean pit;
	ArrayList<PitemsBean> pitList = new ArrayList<PitemsBean>();

	@Override
	public ArrayList<PitemsBean> parseJson(String jsonstr) {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = new JSONObject(jsonstr);
			JSONArray jsonArray = jsonObject.getJSONArray("pitems");

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject tempJson = jsonArray.optJSONObject(i);
				pit = new PitemsBean();
				pit.setPic(URLDecoder.decode(tempJson.optString("pic")));
				pitList.add(pit);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return pitList;
	}

}
