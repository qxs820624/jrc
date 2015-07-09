package com.duom.fjz.iteminfo;

public class Searchpeopleinfo {
	private int uid, sex;
	private String nick, header, nickclor, level;

	public Searchpeopleinfo(int uid , int sex , String nick , String header , String nickclor , String level) {
		// TODO Auto-generated constructor stub
		this.uid = uid ;
		this.sex = sex ;
		this.nick = nick ;
		this.header = header ;
		this.nickclor = nickclor ;
		this.level = level ;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
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

	public String getNickclor() {
		return nickclor;
	}

	public void setNickclor(String nickclor) {
		this.nickclor = nickclor;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
}
