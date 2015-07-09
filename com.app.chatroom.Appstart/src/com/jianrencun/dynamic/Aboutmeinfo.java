package com.jianrencun.dynamic;


public class Aboutmeinfo {
	private int id;
	private int uid ; 
	private String unick;
	private String uhead;
	private String date ;
	private String afile , pfile , desc ;
	private String desc_c;
	private int type ;
	private int alen;
	private boolean isbdh;
	private String nameclor , numofdiscuss;
	private int aflg;
	private int pid;
	private int touxiangbj ;
	private String biaoti;
	private String jubaolink , title ;
	
	
	public Aboutmeinfo(int id, int uid, String unick, String uhead,
			String date, String afile, String pfile, String desc, String desc_c , int type ,int alen, boolean isbdh ,String nameclor , int aflg , int pid , String numofdiscuss , int touxiangbj , String title ) {
		super();
		this.id = id;
		this.uid = uid;
		this.unick = unick;
		this.uhead = uhead;
		this.date = date;
		this.afile = afile;
		this.pfile = pfile;
		this.desc = desc;
		this.desc_c = desc_c;
		this.type  =  type ;
		this.alen = alen ;
		this.isbdh = isbdh ;
		this.nameclor = nameclor;
		this.aflg = aflg;
		this.pid = pid ;
		this.numofdiscuss = numofdiscuss;
		this.touxiangbj = touxiangbj ;
		this.title = title ;
	}
	
	
	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getJubaolink() {
		return jubaolink;
	}

	public void setJubaolink(String jubaolink) {
		this.jubaolink = jubaolink;
	}


	public int getTouxiangbj() {
		return touxiangbj;
	}



	public void setTouxiangbj(int touxiangbj) {
		this.touxiangbj = touxiangbj;
	}



	public String getNumofdiscuss() {
		return numofdiscuss;
	}



	public void setNumofdiscuss(String numofdiscuss) {
		this.numofdiscuss = numofdiscuss;
	}



	public int getPid() {
		return pid;
	}



	public void setPid(int pid) {
		this.pid = pid;
	}



	public int getAflg() {
		return aflg;
	}

	public void setAflg(int aflg) {
		this.aflg = aflg;
	}



	public String getNameclor() {
		return nameclor;
	}

	public void setNameclor(String nameclor) {
		this.nameclor = nameclor;
	}

	public boolean isIsbdh() {
		return isbdh;
	}

	public void setIsbdh(boolean isbdh) {
		this.isbdh = isbdh;
	}

	public int getType() {
		return type;
	}

	public int getAlen() {
		return alen;
	}

	public void setAlen(int alen) {
		this.alen = alen;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUnick() {
		return unick;
	}
	public void setUnick(String unick) {
		this.unick = unick;
	}
	public String getUhead() {
		return uhead;
	}
	public void setUhead(String uhead) {
		this.uhead = uhead;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getDesc_c() {
		return desc_c;
	}
	public void setDesc_c(String desc_c) {
		this.desc_c = desc_c;
	}
	@Override
	public String toString() {
		return "Pinglunitem [id=" + id + ", uid=" + uid + ", unick=" + unick
				+ ", uhead=" + uhead + ", date=" + date + ", afile=" + afile
				+ ", pfile=" + pfile + ", desc=" + desc + ", desc_c=" + desc_c
				+ "]";
	}
	
	
}
