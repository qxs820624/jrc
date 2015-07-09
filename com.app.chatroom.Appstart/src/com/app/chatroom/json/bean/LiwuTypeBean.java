package com.app.chatroom.json.bean;

public class LiwuTypeBean {
	private int id;
	private String logo;
	private String name;

	public LiwuTypeBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LiwuTypeBean(int id, String logo, String name) {
		super();
		this.id = id;
		this.logo = logo;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return "id:" + this.id + ",logo:" + this.logo + ",name:" + this.name;
	}

}
