package com.app.chatroom.json.bean;

public class MailBean {
	private int id;
	private String type;
	private String fuid;
	private String fnick;
	private String fheader;
	private String fsex;
	private String tuid;
	private String tnick;
	private String theader;
	private String tsex;
	private String content;
	private String afile;
	private String pfile;
	private String ptime;
	private String isread;
	private int iscometype;

	public MailBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MailBean(int id, String type, String fuid, String fnick,
			String fheader, String fsex, String tuid, String tnick,
			String theader, String tsex, String content, String afile,
			String pfile, String ptime, String isread, int iscometype) {
		super();
		this.id = id;
		this.type = type;
		this.fuid = fuid;
		this.fnick = fnick;
		this.fheader = fheader;
		this.fsex = fsex;
		this.tuid = tuid;
		this.tnick = tnick;
		this.theader = theader;
		this.tsex = tsex;
		this.content = content;
		this.afile = afile;
		this.pfile = pfile;
		this.ptime = ptime;
		this.isread = isread;
		this.iscometype = iscometype;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFuid() {
		return fuid;
	}

	public void setFuid(String fuid) {
		this.fuid = fuid;
	}

	public String getFnick() {
		return fnick;
	}

	public void setFnick(String fnick) {
		this.fnick = fnick;
	}

	public String getFheader() {
		return fheader;
	}

	public void setFheader(String fheader) {
		this.fheader = fheader;
	}

	public String getFsex() {
		return fsex;
	}

	public void setFsex(String fsex) {
		this.fsex = fsex;
	}

	public String getTuid() {
		return tuid;
	}

	public void setTuid(String tuid) {
		this.tuid = tuid;
	}

	public String getTnick() {
		return tnick;
	}

	public void setTnick(String tnick) {
		this.tnick = tnick;
	}

	public String getTheader() {
		return theader;
	}

	public void setTheader(String theader) {
		this.theader = theader;
	}

	public String getTsex() {
		return tsex;
	}

	public void setTsex(String tsex) {
		this.tsex = tsex;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAfile() {
		return afile;
	}

	public void setAfile(String afile) {
		this.afile = afile;
	}

	public String getPfile() {
		return pfile;
	}

	public void setPfile(String pfile) {
		this.pfile = pfile;
	}

	public String getPtime() {
		return ptime;
	}

	public void setPtime(String ptime) {
		this.ptime = ptime;
	}

	public String getIsread() {
		return isread;
	}

	public void setIsread(String isread) {
		this.isread = isread;
	}

	public int getIscometype() {
		return iscometype;
	}

	public void setIscometype(int iscometype) {
		this.iscometype = iscometype;
	}

	public String toString() {
		return "ID:" + this.id + ",类型：" + this.type + ",发送用户：" + this.fuid
				+ ",发送用户昵称：" + this.fnick + ",发送用户头像：" + this.fheader
				+ ",发送用户性别：" + this.fsex + ",接收用户：" + this.tuid + ",接收用户昵称："
				+ this.tnick + ",接收用户头像：" + this.theader + ",接收用户性别："
				+ this.tsex + ",私信内容：" + this.content + ",音频文件：" + this.afile
				+ ",图片文件：" + this.pfile + ",发送时间：" + this.ptime + ",是否阅读："
				+ this.isread + ",发送方向：" + this.iscometype;
	}
}
