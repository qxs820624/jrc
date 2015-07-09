package com.app.chatroom.ui;

import java.io.Serializable;
import java.util.ArrayList;

import com.app.chatroom.json.bean.ChatMessageBean;

/**
 * 序列化聊天记录
 * 
 * @author J.King
 * 
 */
public class DeliverData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<ChatMessageBean> list = new ArrayList<ChatMessageBean>();

	public ArrayList<ChatMessageBean> getMsglist() {
		return list;
	}

	public void setPersonList(ArrayList<ChatMessageBean> msglist) {
		this.list = msglist;
	}
}
