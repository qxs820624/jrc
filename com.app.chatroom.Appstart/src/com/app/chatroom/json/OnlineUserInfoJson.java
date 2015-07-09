package com.app.chatroom.json;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.interfaces.JsonParse;
import com.app.chatroom.json.bean.UserBean;
import com.duom.fjz.iteminfo.Iteminfo;
import com.jianrencun.android.CacheData;

public class OnlineUserInfoJson implements JsonParse<UserBean> {
	UserBean userBean = null;
	ArrayList<UserBean> list = new ArrayList<UserBean>();

	@Override
	public ArrayList<UserBean> parseJson(String jsonstr) {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = new JSONObject(jsonstr);
			userBean = new UserBean();
			userBean.setRet(jsonObject.optInt("ret"));
			userBean.setTip(URLDecoder.decode(jsonObject.optString("tip")));
			userBean.setJz(URLDecoder.decode(jsonObject.optString("jz")));
			userBean.setJzc(URLDecoder.decode(jsonObject.optString("jzc")));
			userBean.setJzid(jsonObject.optInt("jzid"));
			userBean.setUid(jsonObject.optInt("uid"));
			userBean.setNick(URLDecoder.decode(jsonObject.optString("nick")));
			userBean.setHeader(URLDecoder.decode(jsonObject.optString("header")));
			userBean.setSign(URLDecoder.decode(jsonObject.optString("sign")));
			userBean.setSex(jsonObject.optString("sex"));
			userBean.setScore(URLDecoder.decode(jsonObject.optString("score")));
			userBean.setLevel(URLDecoder.decode(jsonObject.optString("level")));
			userBean.setBirthday(URLDecoder.decode(jsonObject
					.optString("birthday")));
			userBean.setCity(URLDecoder.decode(jsonObject.optString("city")));
			userBean.setIsf(jsonObject.optInt("isf"));
			userBean.setFc(jsonObject.optInt("fc"));
			userBean.setFc1(jsonObject.optInt("fc1"));
			userBean.setGc(jsonObject.optInt("gc"));
			userBean.setPc(jsonObject.optInt("pc"));
			userBean.setHeader_b(jsonObject.optString("header_b"));
			userBean.setIsb(jsonObject.optInt("isb"));
			userBean.setDzc(jsonObject.optInt("dzc"));
			userBean.setXjc(jsonObject.optInt("xjc"));
			userBean.setBbc(jsonObject.optInt("bbc"));
			
			userBean.setJcl(jsonObject.optString("jcl"));
			userBean.setJc(jsonObject.optInt("jc"));
			userBean.setJct(jsonObject.optInt("jct"));
			userBean.setJcl(URLDecoder.decode(jsonObject.optString("jcl")));
			userBean.setJc(jsonObject.optInt("jc"));
			userBean.setJct(jsonObject.optInt("jct"));
			userBean.setTime(URLDecoder.decode(jsonObject.optString("lt")));
			userBean.setBg(jsonObject.optInt("bg"));

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		list.add(userBean);
		return list;
	}

}
