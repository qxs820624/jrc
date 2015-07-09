package com.app.chatroom.json.bean;

import java.io.Serializable;

public class ChatMessageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int roomid;
	private int type;// 消息类型 公开、私聊、弹框消息、系统消息
	private String icon;//
	private int fuid;
	private String fheader;
	private String fnick;
	private String tuid;
	private String tnick;
	private String theader;
	private String content;
	private String afile;
	private String alocalfile;
	private String alen;// 播放时间
	private String asize;// 音频大小
	private String pfile;
	private String plocalfile;// 本地图片地址
	private String pd;// 消息发送时间
	private String datetime;// 列表时间
	private int pw;
	private int ph;
	private int aflg;// 音频自动播放
	private String content_c;// 文字颜色
	private int content_b;// 文字加粗
	private int content_l;
	private String nick_c;// 昵称颜色
	private boolean isplay;// 是否正在播放
	private boolean iscome;// 是否是服务器返回
	private boolean isdown;// 是否在下载
	private boolean ispicauto;// 图片是否自动加载
	private boolean audioauto; // 音频是否自动加载
	private boolean anim; // 气泡动画效果
	private int qp; // 气泡类型

	private int isOK;// 是否发送成功
	private boolean issend;// 是否在发送中
	private int msgtype;// 消息框类型，文本、语音、图片

	public ChatMessageBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChatMessageBean(int id, int roomid, int type, String icon, int fuid,
			String fheader, String fnick, String tuid, String tnick,
			String theader, String content, String afile, String alocalfile,
			String alen, String asize, String pfile, String plocalfile,
			String pd, String datetime, int pw, int ph, int aflg,
			String content_c, int content_b, int content_l, String nick_c,
			boolean isplay, boolean iscome, boolean isdown, boolean ispicauto,
			boolean audioauto, boolean anim, int qp, int isOK, boolean issend,
			int msgtype ) {
		super();
		this.id = id;
		this.roomid = roomid;
		this.type = type;
		this.icon = icon;
		this.fuid = fuid;
		this.fheader = fheader;
		this.fnick = fnick;
		this.tuid = tuid;
		this.tnick = tnick;
		this.theader = theader;
		this.content = content;
		this.afile = afile;
		this.alocalfile = alocalfile;
		this.alen = alen;
		this.asize = asize;
		this.pfile = pfile;
		this.plocalfile = plocalfile;
		this.pd = pd;
		this.datetime = datetime;
		this.pw = pw;
		this.ph = ph;
		this.aflg = aflg;
		this.content_c = content_c;
		this.content_b = content_b;
		this.content_l = content_l;
		this.nick_c = nick_c;
		this.isplay = isplay;
		this.iscome = iscome;
		this.isdown = isdown;
		this.ispicauto = ispicauto;
		this.audioauto = audioauto;
		this.anim = anim;
		this.isOK = isOK;
		this.issend = issend;
		this.msgtype = msgtype;
		this.qp = qp ;
	}

	public int getQp() {
		return qp;
	}

	public void setQp(int qp) {
		this.qp = qp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoomid() {
		return roomid;
	}

	public void setRoomid(int roomid) {
		this.roomid = roomid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getFuid() {
		return fuid;
	}

	public void setFuid(int fuid) {
		this.fuid = fuid;
	}

	public String getFheader() {
		return fheader;
	}

	public void setFheader(String fheader) {
		this.fheader = fheader;
	}

	public String getFnick() {
		return fnick;
	}

	public void setFnick(String fnick) {
		this.fnick = fnick;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent_c() {
		return content_c;
	}

	public void setContent_c(String content_c) {
		this.content_c = content_c;
	}

	public String getNick_c() {
		return nick_c;
	}

	public void setNick_c(String nick_c) {
		this.nick_c = nick_c;
	}

	public int getContent_b() {
		return content_b;
	}

	public void setContent_b(int content_b) {
		this.content_b = content_b;
	}

	public int getContent_l() {
		return content_l;
	}

	public void setContent_l(int content_l) {
		this.content_l = content_l;
	}

	public String getAfile() {
		return afile;
	}

	public void setAfile(String afile) {
		this.afile = afile;
	}

	public String getAlocalfile() {
		return alocalfile;
	}

	public void setAlocalfile(String alocalfile) {
		this.alocalfile = alocalfile;
	}

	public String getAlen() {
		return alen;
	}

	public void setAlen(String alen) {
		this.alen = alen;
	}

	public String getAsize() {
		return asize;
	}

	public void setAsize(String asize) {
		this.asize = asize;
	}

	public String getPfile() {
		return pfile;
	}

	public void setPfile(String pfile) {
		this.pfile = pfile;
	}

	public String getPlocalfile() {
		return plocalfile;
	}

	public void setPlocalfile(String plocalfile) {
		this.plocalfile = plocalfile;
	}

	public String getPd() {
		return pd;
	}

	public void setPd(String pd) {
		this.pd = pd;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public int getPw() {
		return pw;
	}

	public void setPw(int pw) {
		this.pw = pw;
	}

	public int getPh() {
		return ph;
	}

	public void setPh(int ph) {
		this.ph = ph;
	}

	public int getAflg() {
		return aflg;
	}

	public void setAflg(int aflg) {
		this.aflg = aflg;
	}

	public boolean isIscome() {
		return iscome;
	}

	public void setIscome(boolean iscome) {
		this.iscome = iscome;
	}

	public int getIsOK() {
		return isOK;
	}

	public void setIsOK(int isOK) {
		this.isOK = isOK;
	}

	public int getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(int msgtype) {
		this.msgtype = msgtype;
	}

	public boolean getIsplay() {
		return isplay;
	}

	public void setIsplay(boolean isplay) {
		this.isplay = isplay;
	}

	public boolean getIsdown() {
		return isdown;
	}

	public void setIsdown(boolean isdown) {
		this.isdown = isdown;
	}

	public boolean getIspicauto() {
		return ispicauto;
	}

	public void setIspicauto(boolean ispicauto) {
		this.ispicauto = ispicauto;
	}

	public boolean getAudioauto() {
		return audioauto;
	}

	public void setAudioauto(boolean audioauto) {
		this.audioauto = audioauto;
	}

	public boolean getAnim() {
		return anim;
	}

	public void setAnim(boolean anim) {
		this.anim = anim;
	}

	public boolean getIssend() {
		return issend;
	}

	public void setIssend(boolean issend) {
		this.issend = issend;
	}


	public String toString() {
		return "ID:" + this.id + ",房间" + this.roomid + ",消息类型" + this.type
				+ ",ICON：" + this.icon + ",用户编号：" + this.fuid + ",用户头像："
				+ this.fheader + ",用户昵称：" + this.fnick + ",对方编号：" + this.tuid
				+ ",对方昵称：" + this.tnick + ",对方头像：" + this.theader + ",消息内容："
				+ this.content + ",文本颜色：" + this.content_c + ",文本粗体"
				+ this.content_b + ",文本可点击：" + this.content_l + ",音频文件："
				+ this.afile + ",本地音频：" + this.alocalfile + ",时长：" + this.alen
				+ ",音频大小：" + this.asize + ",自动播放：" + this.aflg + ",图片文件："
				+ this.pfile + ",本地图片：" + this.plocalfile + ",发送方向："
				+ this.iscome + ",消息种类：" + this.msgtype + ",发送状态：" + this.isOK
				+ ",时间：" + this.pd + ",图片宽：" + this.pw + ",图片高：" + this.ph
				+ ",是否播放中：" + this.isplay + ",是否在下载" + this.isdown + ",自动加载图："
				+ this.ispicauto + ",自动加载音频：" + this.audioauto + ",列表时间："
				+ this.datetime + ",气泡动画：" + this.anim + ",是否发送中："
				+ this.issend + ",气泡类型：" + this.qp;
	}
}
