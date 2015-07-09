package com.jianrencun.game;

import java.util.List;

public class GamebigInfo {
	private int flg , tp;
	private String title;
	private int uid , jiazubj;
	private String nick, nick_c, score, ord , header , num ;
	
	public GamebigInfo(int flg, String title,int uid , String nick , String nick_c , String score , String ord , String header , String num , int tp , int jiazubj) {
		// TODO Auto-generated constructor stub
		this.flg = flg;
		this.title = title;
		this.uid = uid ;
		this.nick = nick ;
		this.nick_c = this.nick_c ;
		this.score = score ;
		this.ord = ord ;
		this.header = header ;
		this.num = num ;
		this.tp = tp ;
		this.jiazubj = jiazubj ;
	}

	public int getJiazubj() {
		return jiazubj;
	}

	public void setJiazubj(int jiazubj) {
		this.jiazubj = jiazubj;
	}

	public int getTp() {
		return tp;
	}

	public void setTp(int tp) {
		this.tp = tp;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
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

	public String getNick_c() {
		return nick_c;
	}

	public void setNick_c(String nick_c) {
		this.nick_c = nick_c;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getOrd() {
		return ord;
	}

	public void setOrd(String ord) {
		this.ord = ord;
	}

	public int getFlg() {
		return flg;
	}

	public void setFlg(int flg) {
		this.flg = flg;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
}
