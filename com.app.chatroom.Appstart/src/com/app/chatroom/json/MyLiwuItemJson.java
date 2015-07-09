package com.app.chatroom.json;

import java.net.URLDecoder;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.app.chatroom.interfaces.JsonParse;
import com.app.chatroom.json.bean.AttentionUserBean;
import com.app.chatroom.json.bean.LiwuItemsBean;
import com.app.chatroom.json.bean.MyLiwuItemsBean;
import com.app.chatroom.json.bean.OnlineUserBean;

public class MyLiwuItemJson implements JsonParse<MyLiwuItemsBean> {
	MyLiwuItemsBean myliwuBean;
	ArrayList<MyLiwuItemsBean> liwuList = new ArrayList<MyLiwuItemsBean>();

	@Override
	public ArrayList<MyLiwuItemsBean> parseJson(String jsonstr) {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = new JSONObject(jsonstr);
			JSONArray jsonArray = jsonObject.getJSONArray("items");
			// System.out.println("jsonArray:" + jsonArray);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject tempJson = jsonArray.optJSONObject(i);
				myliwuBean = new MyLiwuItemsBean();
				myliwuBean.setUid(tempJson.optInt("uid"));
				myliwuBean.setHeader(URLDecoder.decode(tempJson
						.optString("header")));
				myliwuBean
						.setNick(URLDecoder.decode(tempJson.optString("nick")));
				myliwuBean
						.setDesc(URLDecoder.decode(tempJson.optString("desc")));
				myliwuBean.setGlogo(URLDecoder.decode(tempJson
						.optString("glogo")));
				myliwuBean.setSex(tempJson.getInt("sex"));
				myliwuBean.setVip(tempJson.getInt("vip"));
				myliwuBean.setNick_c(tempJson.optString("nick_c"));
				liwuList.add(myliwuBean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return liwuList;
	}

}
