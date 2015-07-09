package com.jianrencun.dynamic;

import java.util.ArrayList;
import java.util.List;

public class Dyinfoitem {
	private int id, flg, uid, headerbj, sex;
	private String nick, nick_c, header, title, desc, desc_c;
	private String afile;
	private int alen;
	private String dytime, dydisnum;
	private List<Dyphotoitem> dyphotolists;
	private ArrayList<String> dyphotourls;
	 private int startorstop ;

	public Dyinfoitem(int id, int flg, int uid, int headerbj, int sex,
			String nick, String nick_c, String header, String title,
			String desc, String desc_c, String afile,
			int alen, String dytime, String dydisnum ,List<Dyphotoitem> dyphotolists , int startorstop ,ArrayList<String> dyphotourls ) {
		super();
		this.id = id;
		this.flg = flg;
		this.uid = uid;
		this.headerbj = headerbj;
		this.sex = sex;
		this.nick = nick;
		this.nick_c = nick_c;
		this.header = header;
		this.title = title;
		this.desc = desc;
		this.desc_c = desc_c;
		this.afile = afile;
		this.alen = alen;
		this.dytime = dytime;
		this.dydisnum = dydisnum;
		this.dyphotolists = dyphotolists ;
		this.startorstop = startorstop;
		this.dyphotourls = dyphotourls ;
	}
	public Dyinfoitem(int id, int flg, int uid, int headerbj, int sex,
			String nick, String nick_c, String header, String title,
			String desc, String desc_c, String afile,
			int alen, String dytime, String dydisnum, int startorstop) {
		super();
		this.id = id;
		this.flg = flg;
		this.uid = uid;
		this.headerbj = headerbj;
		this.sex = sex;
		this.nick = nick;
		this.nick_c = nick_c;
		this.header = header;
		this.title = title;
		this.desc = desc;
		this.desc_c = desc_c;
		this.afile = afile;
		this.alen = alen;
		this.dytime = dytime;
		this.dydisnum = dydisnum;
		this.startorstop = startorstop;
	}
	
	public ArrayList<String> getDyphotourls() {
		return dyphotourls;
	}
	public void setDyphotourls(ArrayList<String> dyphotourls) {
		this.dyphotourls = dyphotourls;
	}
	public int getStartorstop() {
		return startorstop;
	}
	public void setStartorstop(int startorstop) {
		this.startorstop = startorstop;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFlg() {
		return flg;
	}

	public void setFlg(int flg) {
		this.flg = flg;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getHeaderbj() {
		return headerbj;
	}

	public void setHeaderbj(int headerbj) {
		this.headerbj = headerbj;
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

	public String getNick_c() {
		return nick_c;
	}

	public void setNick_c(String nick_c) {
		this.nick_c = nick_c;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getAfile() {
		return afile;
	}

	public void setAfile(String afile) {
		this.afile = afile;
	}

	public int getAlen() {
		return alen;
	}

	public void setAlen(int alen) {
		this.alen = alen;
	}

	public String getDytime() {
		return dytime;
	}

	public void setDytime(String dytime) {
		this.dytime = dytime;
	}

	public String getDydisnum() {
		return dydisnum;
	}

	public void setDydisnum(String dydisnum) {
		this.dydisnum = dydisnum;
	}



	public List<Dyphotoitem> getDyphotolists() {
		return dyphotolists;
	}

	public void setDyphotolists(List<Dyphotoitem> dyphotolists) {
		this.dyphotolists = dyphotolists;
	}

	@Override
	public String toString() {
		return "Dyinfoitem [id=" + id + ", flg=" + flg + ", uid=" + uid
				+ ", headerbj=" + headerbj + ", sex=" + sex + ", nick=" + nick
				+ ", nick_c=" + nick_c + ", header=" + header + ", title="
				+ title + ", desc=" + desc + ", desc_c=" + desc_c + ", afile=" + afile
				+ ", alen=" + alen + ", dytime=" + dytime + ", dydisnum="
				+ dydisnum + ", dytime=" + dyphotolists +"]";
	}

}
