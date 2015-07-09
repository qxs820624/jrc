package com.app.chatroom.json.bean;

public class RoomListBean {
	private int id;
	private String logo;
	private String title;
	private String name;
	private String desc;
	private String count;
	private String header_1;
	private int type;
	private String tname;
	private int flg;

	public RoomListBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RoomListBean(int id, String logo, String title, String name,
			String desc, String count, String header_1, int type, String tname,
			int flg) {
		super();
		this.id = id;
		this.logo = logo;
		this.title = title;
		this.name = name;
		this.desc = desc;
		this.count = count;
		this.header_1 = header_1;
		this.type = type;
		this.tname = tname;
		this.flg = flg;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getHeader_1() {
		return header_1;
	}

	public void setHeader_1(String header_1) {
		this.header_1 = header_1;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public int getFlg() {
		return flg;
	}

	public void setFlg(int flg) {
		this.flg = flg;
	}

	public String toSring() {
		return "房间编号：" + this.id + ",Logo:" + this.logo + ",房间名：" + this.title
				+ ",老板名：" + this.name + ",房间描述：" + this.desc + ",人数："
				+ this.count + ",顶部Logo:" + this.header_1 + ",房间类型名："
				+ this.tname + ",类型颜色：" + this.type + ",家族成员：" + this.flg;
	}
}
