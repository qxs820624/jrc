package com.duom.fjz.iteminfo;


public class Pinglunitem {
	private String header;
	private String fcount;
	private String chenghao;
	private String content;
	private String date;
	// private String sign ;
	private String name;
	private String title;
	private String link;
	private String clor ;
	private String uid;
	private int iscuti ;
	private int autolink ;
	private String jubaolink ;
	private String nameclor;

	public Pinglunitem(String header, String fcount, String chenghao,
			String content, String date, String name, String title, String link ,String clor ,int iscuti,String uid , int autolink , String jubaolink ,String nameclor) {
		// TODO Auto-generated constructor stub
          this.chenghao = chenghao;
          this.content = content;
          this.date = date;
          this.name = name;
          this.fcount = fcount ;
          this.header = header;
          this.link = link ;
          this.title  =title ;
          this.clor = clor ;
          this.iscuti = iscuti ;
          this.uid = uid ;
          this.autolink = autolink;
          this.jubaolink = jubaolink;
          this.nameclor = nameclor ;
	}

	
	public String getNameclor() {
		return nameclor;
	}


	public void setNameclor(String nameclor) {
		this.nameclor = nameclor;
	}


	public String getJubaolink() {
		return jubaolink;
	}


	public void setJubaolink(String jubaolink) {
		this.jubaolink = jubaolink;
	}


	public String getUid() {
		return uid;
	}





	public int getAutolink() {
		return autolink;
	}


	public void setAutolink(int autolink) {
		this.autolink = autolink;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}


	public String getClor() {
		return clor;
	}

	public void setClor(String clor) {
		this.clor = clor;
	}

	public int getIscuti() {
		return iscuti;
	}

	public void setIscuti(int iscuti) {
		this.iscuti = iscuti;
	}

	public String getHeader() {  
		return header;
	}

	public String getFcount() {
		return fcount;
	}

	public void setFcount(String fcount) {
		this.fcount = fcount;
	}

	public String getChenghao() {
		return chenghao;
	}

	public void setChenghao(String chenghao) {
		this.chenghao = chenghao;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	// public String getSign() {
	// return sign;
	// }
	// public void setSign(String sign) {
	// this.sign = sign;
	// }
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Pinglunitem [header=" + header + ", fcount=" + fcount
				+ ", chenghao=" + chenghao + ", content=" + content + ", date="
				+ date + ", name=" + name + ", title=" + title + ", link="
				+ link + ", clor=" + clor + ", iscuti=" + iscuti + "]";
	}



}
