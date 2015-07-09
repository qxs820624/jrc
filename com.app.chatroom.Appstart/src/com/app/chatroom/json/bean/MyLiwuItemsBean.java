package com.app.chatroom.json.bean;

public class MyLiwuItemsBean {
	private int uid;
	private String header;
	private String nick;
	private int sex;
	private int vip;
	private String desc;
	private String glogo;
	private String nick_c;

	public MyLiwuItemsBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MyLiwuItemsBean(int uid, String header, String nick, int sex,
			int vip, String desc, String glogo, String nick_c) {
		super();
		this.uid = uid;
		this.header = header;
		this.nick = nick;
		this.sex = sex;
		this.vip = vip;
		this.desc = desc;
		this.glogo = glogo;
		this.nick_c = nick_c;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getVip() {
		return vip;
	}

	public void setVip(int vip) {
		this.vip = vip;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getGlogo() {
		return glogo;
	}

	public void setGlogo(String glogo) {
		this.glogo = glogo;
	}

	public String getNick_c() {
		return nick_c;
	}

	public void setNick_c(String nick_c) {
		this.nick_c = nick_c;
	}

	public String toString() {
		return "用户编号：" + this.uid + ",用户名称：" + this.nick + ",头像：" + this.header
				+ ",描述：" + this.desc + ",性别：" + this.sex + "VIP：" + this.vip
				+ ",礼物图片：" + this.glogo + ",昵称颜色：" + this.nick_c;
	}

}
