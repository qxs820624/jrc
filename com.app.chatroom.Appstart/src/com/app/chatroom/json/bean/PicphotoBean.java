package com.app.chatroom.json.bean;

public class PicphotoBean {
	private int picid;
	private String picsmall, picbig;

	public PicphotoBean(int picid, String picsmall, String picbig) {
		// TODO Auto-generated constructor stub
		this.picid = picid ;
		this.picsmall = picsmall;
		this.picbig = picbig;
	}

	public int getPicid() {
		return picid;
	}

	public void setPicid(int picid) {
		this.picid = picid;
	}

	public String getPicsmall() {
		return picsmall;
	}

	public void setPicsmall(String picsmall) {
		this.picsmall = picsmall;
	}

	public String getPicbig() {
		return picbig;
	}

	public void setPicbig(String picbig) {
		this.picbig = picbig;
	}
	
}
