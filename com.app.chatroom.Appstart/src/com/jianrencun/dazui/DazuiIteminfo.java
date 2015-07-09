package com.jianrencun.dazui;

public class DazuiIteminfo {
    public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getAfile() {
		return afile;
	}


	public void setAfile(String afile) {
		this.afile = afile;
	}


	public int getUid() {
		return uid;
	}


	public void setUid(int uid) {
		this.uid = uid;
	}


	public String getUhead() {
		return uhead;
	}


	public void setUhead(String uhead) {
		this.uhead = uhead;
	}


	public String getUnick() {
		return unick;
	}


	public void setUnick(String unick) {
		this.unick = unick;
	}


	public String getLen() {
		return len;
	}


	public void setLen(String len) {
		this.len = len;
	}


	public int getCcount() {
		return ccount;
	}


	public void setCcount(int ccount) {
		this.ccount = ccount;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}

	String title ; 
    String size ;
    public int getIsfav() {
		return isfav;
	}


	public void setIsfav(int isfav) {
		this.isfav = isfav;
	}

	String date ;
    int id ; 
    String afile ; 
    int uid ;
    String uhead , unick ,  len ;
    int ccount , status , startorstop , isfav;
    String nameclor, flg_pic;
    int fc , sc , uc , dc , jindubg , tc , gc;
    int type ;
    
 public DazuiIteminfo(String title , String size , String date , int id , String afile , int uid , String uhead , String unick,
		 String len , int ccount , int status , int startorstop, int isfav , String nameclor,int fc ,int sc ,int uc,int dc , int jindubg , String flg_pic , int tc , int gc , int type) {
	// TODO Auto-generated constructor stub
	 this.title = title ;
	 this.size = size ; 
	 this.date = date ;
	 this.id = id ; 
	 this.afile = afile;
	 this.uid = uid ;
	 this.uhead = uhead;
	 this.unick = unick;
	 this.len = len;
	 this.ccount =ccount;
	 this.status = status ;
	 this.startorstop = startorstop;
	 this.isfav = isfav;
	 this.nameclor = nameclor;
	 this.fc = fc;
	 this.sc =sc ;
	 this.uc = uc;
	 this.dc = dc ;
	 this.jindubg = jindubg;
	 this.flg_pic = flg_pic;
	 this.tc = tc;
	 this.gc = gc ;
	 this.type = type ;
   }
 



public int getType() {
	return type;
}


public void setType(int type) {
	this.type = type;
}


public int getJindubg() {
	return jindubg;
}


public void setJindubg(int jindubg) {
	this.jindubg = jindubg;
}


public String getFlg_pic() {
	return flg_pic;
}


public void setFlg_pic(String flg_pic) {
	this.flg_pic = flg_pic;
}


public int getTc() {
	return tc;
}


public void setTc(int tc) {
	this.tc = tc;
}


public int getGc() {
	return gc;
}


public void setGc(int gc) {
	this.gc = gc;
}


public int getFc() {
	return fc;
}


public void setFc(int fc) {
	this.fc = fc;
}


public int getSc() {
	return sc;
}


public void setSc(int sc) {
	this.sc = sc;
}


public int getUc() {
	return uc;
}


public void setUc(int uc) {
	this.uc = uc;
}


public int getDc() {
	return dc;
}


public void setDc(int dc) {
	this.dc = dc;
}


public String getNameclor() {
	return nameclor;
}


public void setNameclor(String nameclor) {
	this.nameclor = nameclor;
}


public int getStartorstop() {
	return startorstop;
}


public void setStartorstop(int startorstop) {
	this.startorstop = startorstop;
}


public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
}

public String getSize() {
	return size;
}

public void setSize(String size) {
	this.size = size;
}

public String getDate() {
	return date;
}

public void setDate(String date) {
	this.date = date;
}


@Override
public String toString() {
	return "DazuiIteminfo [title=" + title + ", size=" + size + ", date="
			+ date + ", id=" + id + ", afile=" + afile + ", uid=" + uid
			+ ", uhead=" + uhead + ", unick=" + unick + ", len=" + len
			+ ", ccount=" + ccount + ", status=" + status + ", startorstop="
			+ startorstop + ", isfav=" + isfav + ", nameclor=" + nameclor
			+ ", fc=" + fc + ", sc=" + sc + ", uc=" + uc + ", dc=" + dc + "]";
}





 
}
