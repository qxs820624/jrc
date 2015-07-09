package com.jianrencun.game;

public class GameListinfo {
	private int gid, uid;
	private String header, nick, nick_c, desc, desc_c, flg, dlink ,date ;
	private int id , zt;

	public GameListinfo(int gid , int uid , String header ,String nick , String nick_c ,String desc , String desc_c, String flg , String dlink , String date , int id ,int zt) {
		// TODO Auto-generated constructor stub
		this.gid = gid ; 
		this.uid = uid ; 
		this.header = header ;
		this.nick = nick ;
		this.nick_c = nick_c;
		this.desc = desc ;
		this.desc_c = desc_c ;
		this.flg = flg ;
		this.dlink = dlink ;	
		this.date = date ;
		this.id = id ;
		this.zt = zt ;
	}

	public int getZt() {
		return zt;
	}

	public void setZt(int zt) {
		this.zt = zt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
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

	public String getNick_c() {
		return nick_c;
	}

	public void setNick_c(String nick_c) {
		this.nick_c = nick_c;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDesc_c() {
		return desc_c;
	}

	public void setDesc_c(String desc_c) {
		this.desc_c = desc_c;
	}

	public String getFlg() {
		return flg;
	}

	public void setFlg(String flg) {
		this.flg = flg;
	}

	public String getDlink() {
		return dlink;
	}

	public void setDlink(String dlink) {
		this.dlink = dlink;
	}
	
}
