package com.app.chatroom.json.bean;

public class VillageMsgBean {
	private int id;
	private String link;
	private String content;

	public VillageMsgBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VillageMsgBean(int id, String link, String content) {
		super();
		this.id = id;
		this.link = link;
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String toString() {
		return "公告编号：" + this.id + ",链接：" + this.link + ",公告内容：" + this.content;
	}
}
