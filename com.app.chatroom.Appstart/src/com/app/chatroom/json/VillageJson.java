package com.app.chatroom.json;

import java.net.URLDecoder;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.app.chatroom.interfaces.JsonParse;
import com.app.chatroom.json.bean.VillageBean;

public class VillageJson implements JsonParse<VillageBean> {
	VillageBean villageBean = null;
	ArrayList<VillageBean> list = new ArrayList<VillageBean>();

	@Override
	public ArrayList<VillageBean> parseJson(String jsonstr) {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = new JSONObject(jsonstr);
			villageBean = new VillageBean();
			villageBean.setRet(jsonObject.optInt("ret"));
			villageBean.setTip(URLDecoder.decode(jsonObject.optString("tip")));
			villageBean.setPd(jsonObject.optString("pd"));
			villageBean.setTitle(URLDecoder.decode(jsonObject
					.optString("title")));
			villageBean
					.setLogo(URLDecoder.decode(jsonObject.optString("logo")));
			villageBean.setMoney(URLDecoder.decode(jsonObject
					.optString("money")));
			villageBean.setCount(jsonObject.optInt("count"));
			villageBean.setQd(jsonObject.optInt("qd"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		list.add(villageBean);
		return list;
	}

}
