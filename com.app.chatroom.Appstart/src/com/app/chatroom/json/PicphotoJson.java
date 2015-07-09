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
import com.app.chatroom.json.bean.PicphotoBean;
import com.app.chatroom.json.bean.PitemsBean;
import com.app.chatroom.json.bean.RoomListBean;

public class PicphotoJson implements JsonParse<PicphotoBean> {
	PicphotoBean pit;
	ArrayList<PicphotoBean> pitList = new ArrayList<PicphotoBean>();

	@Override
	public ArrayList<PicphotoBean> parseJson(String jsonstr) {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = new JSONObject(jsonstr);
			JSONArray jsonArray = jsonObject.getJSONArray("fotos");

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject tempJson = jsonArray.optJSONObject(i);
				int picid = tempJson.optInt("picid");
				String pic1 = URLDecoder.decode(tempJson
						.optString("pic1"));
				String pic2 = URLDecoder.decode(tempJson.optString("pic2"));
				pit = new PicphotoBean(picid, pic1 , pic2);				
				pitList.add(pit);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return pitList;
	}

}