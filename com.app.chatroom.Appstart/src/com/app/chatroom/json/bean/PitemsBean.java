package com.app.chatroom.json.bean;

public class PitemsBean {

	private String pic;

	public PitemsBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PitemsBean(String pic) {
		super();
		this.pic = pic;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String toString() {
		return "图片Icon：" + this.pic;
	}

}
