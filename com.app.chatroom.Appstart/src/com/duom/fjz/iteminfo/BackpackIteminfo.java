package com.duom.fjz.iteminfo;

public class BackpackIteminfo {
	private int id, count, status, flg , cflg , kong;
	private String logo, desc, name , mark;	

	public BackpackIteminfo(int id, String logo, String desc, int count,
			String name , String mark, int status , int flg , int cflg , int kong) {
		// TODO Auto-generated constructor stub
         this.id = id ;
         this.logo = logo ; 
         this.desc = desc ;
         this.count = count ; 
         this.name = name ;
         this.status = status;
         this.flg = flg ;
         this.mark = mark;
         this.cflg = cflg ;
         this.kong = kong ;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getFlg() {
		return flg;
	}

	public void setFlg(int flg) {
		this.flg = flg;
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


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public int getCflg() {
		return cflg;
	}

	public void setCflg(int cflg) {
		this.cflg = cflg;
	}

	public int getKong() {
		return kong;
	}

	public void setKong(int kong) {
		this.kong = kong;
	}

	@Override
	public String toString() {
		return "BackpackIteminfo [id=" + id + ", count=" + count + ", status="
				+ status + ", flg=" + flg + ", cflg=" + cflg + ", logo=" + logo
				+ ", desc=" + desc + ", name=" + name + ", mark=" + mark + "]";
	}


}
