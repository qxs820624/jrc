package com.app.chatroom.json.bean;

public class VillageBean {
	private int ret;
	private String tip;
	private String pd;
	private String logo;
	private String title;
	private String money;
	private int count;
	private int qd ;

	public VillageBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VillageBean(int ret, String tip, String pd, String logo,
			String title, String money, int count , int qd) {
		super();
		this.ret = ret;
		this.tip = tip;
		this.pd = pd;
		this.logo = logo;
		this.title = title;
		this.money = money;
		this.count = count;
		this.qd = qd ;
	}

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getPd() {
		return pd;
	}

	public void setPd(String pd) {
		this.pd = pd;
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

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getQd() {
		return qd;
	}

	public void setQd(int qd) {
		this.qd = qd;
	}

	public String toString() {
		return "请求结果：" + this.ret + ",提示：" + this.tip + ",标记：" + this.pd
				+ "村Logo:" + this.logo + ",村名:" + this.title + ",财富："
				+ this.money + ",人数：" + this.count;
	}
}
