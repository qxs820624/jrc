package com.app.chatroom.json;

import java.net.URLDecoder;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.app.chatroom.interfaces.JsonParse;
import com.app.chatroom.json.bean.MessageBean;

public class MessageJson implements JsonParse<MessageBean> {
	ArrayList<MessageBean> messageList = new ArrayList<MessageBean>();
	MessageBean messageBean;

	@Override
	public ArrayList<MessageBean> parseJson(String jsonstr) {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = new JSONObject(jsonstr);
			messageBean = new MessageBean();
			messageBean.setRet(jsonObject.optString("ret"));
			messageBean.setTip(URLDecoder.decode(jsonObject.optString("tip")));
			messageBean.setMsg(URLDecoder.decode(jsonObject.optString("msg")));			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		messageList.add(messageBean);
		return messageList;
	}

	public ArrayList<MessageBean> parseJsonAuth(String jsonstr) {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = new JSONObject(jsonstr);
			messageBean = new MessageBean();
			messageBean.setRet(jsonObject.optString("ret"));
			messageBean.setTip(URLDecoder.decode(jsonObject.optString("tip")));
			messageBean.setAuth(jsonObject.optString("auth"));
			messageBean.setMsg_c(URLDecoder.decode(jsonObject
					.optString("msg_c")));
			messageBean.setMsg_b(jsonObject.optInt("msg_b"));
			messageBean.setMsg_l(jsonObject.optInt("msg_l"));
			messageBean.setNick_c(jsonObject.optString("nick_c"));
			messageBean.setQp(jsonObject.optInt("qp1"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		messageList.add(messageBean);
		return messageList;
	}

	public ArrayList<MessageBean> parseJsonCountPid(String jsonstr) {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = new JSONObject(jsonstr);
			messageBean = new MessageBean();
			messageBean.setRet(jsonObject.optString("ret"));
			messageBean.setTip(URLDecoder.decode(jsonObject.optString("tip")));
			messageBean.setBtime(URLDecoder.decode(jsonObject
					.optString("btime")));
			messageBean.setCount(jsonObject.optString("count"));
			messageBean.setPid(jsonObject.optString("pid"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		messageList.add(messageBean);
		return messageList;
	}

	public ArrayList<MessageBean> parseJsonPd(String jsonstr) {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = new JSONObject(jsonstr);
			messageBean = new MessageBean();
			messageBean.setRet(jsonObject.optString("ret"));
			messageBean.setTip(URLDecoder.decode(jsonObject.optString("tip")));
			messageBean.setPd(jsonObject.optString("pd"));
			messageBean.setMsg(URLDecoder.decode(jsonObject.optString("msg")));
			messageBean.setPage(jsonObject.optInt("page"));
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		messageList.add(messageBean);
		return messageList;
	}

	public ArrayList<MessageBean> parseJsonHeader(String jsonstr) {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = new JSONObject(jsonstr);
			messageBean = new MessageBean();
			messageBean.setRet(jsonObject.optString("ret"));
			messageBean.setTip(URLDecoder.decode(jsonObject.optString("tip")));
			messageBean.setHeader(URLDecoder.decode(jsonObject
					.optString("header")));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		messageList.add(messageBean);
		return messageList;
	}
}
