package com.app.chatroom.json;

import java.net.URLDecoder;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
import com.app.chatroom.interfaces.JsonParse;
import com.app.chatroom.json.bean.UserBean;

public class UserInfoJson implements JsonParse<UserBean> {

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
			userBean.setUid(jsonObject.optInt("uid"));
			userBean.setPd(jsonObject.optString("pd"));
			userBean.setNick(URLDecoder.decode(jsonObject.optString("nick")));
			userBean.setHeader(URLDecoder.decode(jsonObject.optString("header")));
			userBean.setSign(URLDecoder.decode(jsonObject.optString("sign")));
			userBean.setSex(jsonObject.optString("sex"));
			userBean.setScore(URLDecoder.decode(jsonObject.optString("score")));
			userBean.setLevel(URLDecoder.decode(jsonObject.optString("level")));
			userBean.setBirthday(jsonObject.optString("birthday"));
			userBean.setCity(URLDecoder.decode(jsonObject.optString("city")));
			userBean.setMcount(jsonObject.optInt("mcount"));
			userBean.setTcount(jsonObject.optInt("tcount"));
			userBean.setVname(URLDecoder.decode(jsonObject.optString("vname")));
			userBean.setVsize(jsonObject.optInt("vsize"));
			userBean.setVurl(URLDecoder.decode(jsonObject.optString("vurl")));
			userBean.setToken(URLDecoder.decode(jsonObject.optString("token")));
			userBean.setMd(jsonObject.optInt("md"));
			userBean.setMg(jsonObject.optInt("mg"));
			userBean.setMs(jsonObject.optInt("ms"));
			userBean.setIstj(jsonObject.optInt("istj"));
			userBean.setIsshop(jsonObject.optInt("isshop"));
			userBean.setDj(URLDecoder.decode(jsonObject.optString("dj")));
			userBean.setHelp(URLDecoder.decode(jsonObject.optString("help")));
			userBean.setShop(URLDecoder.decode(jsonObject.optString("shop")));
			userBean.setHd(URLDecoder.decode(jsonObject.optString("hd")));
			userBean.setFbg(URLDecoder.decode(jsonObject.optString("fbg")));
			userBean.setFfg(URLDecoder.decode(jsonObject.optString("ffg")));
			userBean.setJcl(URLDecoder.decode(jsonObject.optString("jcl")));
			userBean.setJc(jsonObject.optInt("jc"));
			userBean.setJct(jsonObject.optInt("jct"));
			userBean.setTip1(URLDecoder.decode(jsonObject.optString("tip2")));
			userBean.setTip2(URLDecoder.decode(jsonObject.optString("tip3")));
			userBean.setTip3(URLDecoder.decode(jsonObject.optString("tip4")));
			userBean.setTip4(URLDecoder.decode(jsonObject.optString("tip5")));
			userBean.setTip5(URLDecoder.decode(jsonObject.optString("tip1")));
			userBean.setTip0(URLDecoder.decode(jsonObject.optString("tip0")));
			// System.out.println(">>>>>" + userBean.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		list.add(userBean);
		return list;
	}

}
