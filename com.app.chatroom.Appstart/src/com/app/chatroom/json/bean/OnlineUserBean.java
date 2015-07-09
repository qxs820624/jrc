package com.app.chatroom.json.bean;

public class OnlineUserBean {
	private int id;
	private String nick;
	private String header;
	private String money;
	private String sign;
	private String level;
	private String nick_c;
	private int sex;
	private int type;

	public OnlineUserBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OnlineUserBean(int id, String nick, String header, String money,
			String sign, String level, String nick_c, int sex, int type) {
		super();
		this.id = id;
		this.nick = nick;
		this.header = header;
		this.money = money;
		this.sign = sign;
		this.level = level;
		this.nick_c = nick_c;
		this.sex = sex;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getNick_c() {
		return nick_c;
	}

	public void setNick_c(String nick_c) {
		this.nick_c = nick_c;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String toString() {
		return "用户编号：" + this.id + ",用户昵称：" + this.nick + ",头像：" + this.header
				+ ",积分：" + this.money + ",签名：" + this.sign + ",等级："
				+ this.level + ",用户类型：" + this.type + ",性别：" + this.sex
				+ ",昵称颜色：" + this.nick_c;
	}

}
