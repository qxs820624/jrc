package com.app.chatroom.json;

import java.net.URLDecoder;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import com.app.chatroom.interfaces.JsonParse;
import com.app.chatroom.json.bean.MailBean;

public class GetMailJson implements JsonParse<MailBean> {
	MailBean mailBean;
	ArrayList<MailBean> list = new ArrayList<MailBean>();

	@Override
	public ArrayList<MailBean> parseJson(String jsonstr) {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = new JSONObject(jsonstr);
			JSONArray jsonArray = jsonObject.getJSONArray("mitems");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject tempJson = jsonArray.optJSONObject(i);
				mailBean = new MailBean();
				mailBean.setId(tempJson.optInt("id"));
				mailBean.setType(tempJson.optString("type"));
				mailBean.setFuid(tempJson.optString("fuid"));
				mailBean.setFnick(URLDecoder.decode(tempJson.optString("fnick")));
				mailBean.setFheader(URLDecoder.decode(tempJson
						.optString("fheader")));
				mailBean.setFsex(tempJson.optString("fsex"));
				mailBean.setTuid(tempJson.optString("tuid"));
				mailBean.setTnick(URLDecoder.decode(tempJson.optString("tnick")));
				mailBean.setTheader(URLDecoder.decode(tempJson
						.optString("theader")));
				mailBean.setTsex(tempJson.optString("tsex"));
				mailBean.setContent(URLDecoder.decode(tempJson
						.optString("content")));
				mailBean.setAfile(URLDecoder.decode(tempJson.optString("afile")));
				mailBean.setPfile(URLDecoder.decode(tempJson.optString("pfile")));
				mailBean.setPtime(URLDecoder.decode(tempJson.optString("ptime")));
				mailBean.setIsread("1");
				list.add(mailBean);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

}
