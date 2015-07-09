package com.app.chatroom.json.bean;

public class VillagePeopleBean {
	private int uid;
	private String no;
	private String nick;
	private String sign;
	private String header;
	private int sex;
	private String money;
	private String level;
	private int type;
	private String nick_c;

	public VillagePeopleBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VillagePeopleBean(int uid, String no, String nick, String sign,
			String header, int sex, String money, String level, int type,
			String nick_c ) {
		super();
		this.uid = uid;
		this.no = no;
		this.nick = nick;
		this.sign = sign;
		this.header = header;
		this.sex = sex;
		this.money = money;
		this.level = level;
		this.type = type;
		this.nick_c = nick_c;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getNick_c() {
		return nick_c;
	}

	public void setNick_c(String nick_c) {
		this.nick_c = nick_c;
	}
	

	public String toString() {
		return "用户编号：" + this.uid + ",排行：" + this.no + ",用户昵称：" + this.nick
				+ ",用户签名：" + this.sign + ",用户头像：" + this.header + ",性别："
				+ this.sex + ",财富：" + this.money + ",等级：" + this.level
				+ ",等级类型：" + this.type + ",昵称颜色：" + this.nick_c;
	}

}
