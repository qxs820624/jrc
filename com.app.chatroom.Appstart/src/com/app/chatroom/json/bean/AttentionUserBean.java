package com.app.chatroom.json.bean;

public class AttentionUserBean {
	private int uid;
	private String nick;
	private String header;
	private int sex;
	private int vip;
	private int followed;
	private boolean isfollow;
	private String nick_c;
	private String time ;

	public AttentionUserBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AttentionUserBean(int uid, String nick, String header, int sex,
			int vip, int followed, boolean isfollow, String nick_c , String time) {
		super();
		this.uid = uid;
		this.nick = nick;
		this.header = header;
		this.sex = sex;
		this.vip = vip;
		this.followed = followed;
		this.isfollow = isfollow;
		this.nick_c = nick_c;
		this.time = time ;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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

	public int getFollowed() {
		return followed;
	}

	public void setFollowed(int followed) {
		this.followed = followed;
	}

	public boolean getIsfollow() {
		return isfollow;
	}

	public void setIsfollow(boolean isfollow) {
		this.isfollow = isfollow;
	}

	public String getNick_c() {
		return nick_c;
	}

	public void setNick_c(String nick_c) {
		this.nick_c = nick_c;
	}

	public String toString() {
		return "用户编号：" + this.uid + ",用户昵称：" + this.nick + ",头像：" + this.header
				+ ",性别：" + this.sex + ",会员：" + this.vip + ",关注状态："
				+ this.followed + "是否:" + this.isfollow + ",昵称颜色："
				+ this.nick_c;
	}

}
