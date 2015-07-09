package com.app.chatroom.json.bean;

public class MessageBean {
	private String ret = "";
	private String tip;
	private String pd;
	private String pid;
	private String btime;
	private String count;
	private String header;
	private String auth; // 权限
	private String msg_c; // 文字颜色
	private int msg_b; // 文字加粗
	private int msg_l; // 文字点击链接
	private String nick_c;// 昵称颜色
	private int qp;// 气泡类型
	private String msg ;
	private int page ;

	public MessageBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MessageBean(String ret, String tip, String pd, String pid,
			String btime, String count, String header, String auth,
			String msg_c, int msg_b, int msg_l, String nick_c, int qp , String msg , int page) {
		super();
		this.ret = ret;
		this.tip = tip;
		this.pd = pd;
		this.pid = pid;
		this.btime = btime;
		this.count = count;
		this.header = header;
		this.auth = auth;
		this.msg_c = msg_c;
		this.msg_b = msg_b;
		this.msg_l = msg_l;
		this.nick_c = nick_c;
		this.qp = qp;
		this.msg = msg ;
		this.page = page ;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
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

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getBtime() {
		return btime;
	}

	public void setBtime(String btime) {
		this.btime = btime;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getMsg_c() {
		return msg_c;
	}

	public void setMsg_c(String msg_c) {
		this.msg_c = msg_c;
	}

	public int getMsg_b() {
		return msg_b;
	}

	public void setMsg_b(int msg_b) {
		this.msg_b = msg_b;
	}

	public int getMsg_l() {
		return msg_l;
	}

	public void setMsg_l(int msg_l) {
		this.msg_l = msg_l;
	}

	public String getNick_c() {
		return nick_c;
	}

	public void setNick_c(String nick_c) {
		this.nick_c = nick_c;
	}

	public int getQp() {
		return qp;
	}

	public void setQp(int qp) {
		this.qp = qp;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String toString() {
		return "返回结果：" + this.ret + ",结果：" + this.tip + ",时间戳：" + this.pd
				+ ",消息编号：" + this.pid + ",人数：" + this.count + ",头像："
				+ this.header + ",用户权限：" + this.auth + ",颜色：" + this.msg_c
				+ "加粗：" + this.msg_b + ",点击链接：" + this.msg_l + ",昵称颜色："
				+ this.nick_c + ",气泡类型：" + this.qp;
	}

}
