package com.app.chatroom.json.bean;

public class BlackListBean {
	private int uid;
	private String nick;
	private String header;
	private String level;
	private int sex;
	private String nick_c;

	public BlackListBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BlackListBean(int uid, String nick, String header, String level,
			int sex, String nick_c) {
		super();
		this.uid = uid;
		this.nick = nick;
		this.header = header;
		this.level = level;
		this.sex = sex;
		this.nick_c = nick_c;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getNick_c() {
		return nick_c;
	}

	public void setNick_c(String nick_c) {
		this.nick_c = nick_c;
	}

	public String toString() {
		return "用户编号：" + this.uid + ",用户昵称：" + this.nick + ",头像：" + this.header
				+ ",性别：" + this.sex + ",等级：" + this.level + ",昵称颜色："
				+ this.nick_c;
	}
}
