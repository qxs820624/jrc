package com.app.chatroom.json.bean;

public class LiwuItemsBean {
	private int id;
	private String name;
	private String logo;
	private String desc;
	private String price;
	private String level;
	private String mark;
	private int count;

	public LiwuItemsBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LiwuItemsBean(int id, String name, String logo, String desc,
			String price, String level, String mark, int count) {
		super();
		this.id = id;
		this.name = name;
		this.logo = logo;
		this.desc = desc;
		this.price = price;
		this.level = level;
		this.mark = mark;
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String toString() {
		return "礼物编号：" + this.id + ",礼物名称：" + this.name + ",Logo：" + this.logo
				+ ",描述：" + this.desc + ",等级：" + this.level + "数量：" + this.count
				+ ",价格：" + this.price + ",礼物描述：" + this.mark;
	}

}
