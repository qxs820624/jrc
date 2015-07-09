package com.duom.fjz.iteminfo;

public class Iteminfo {
     private String content , date , header ,link , flink ,rlink ,clink ,dlink; 
     private String criticism , support ,oppose ,save;
     private int isfav ,status ,id;
     
     public Iteminfo(String content , String date , String header , String ccount , String dcount , String ucount ,String save ,String link
    		 , String flink ,String rlink ,String clink ,int isfav , int status ,int id ,String dlink) {
		// TODO Auto-generated constructor stub
    	 this.content = content;
    	 this.date = date ;
    	 this.header = header;
    	 this.criticism = ccount ; 
    	 this.support = dcount ;
    	 this.oppose = ucount ;
    	 this.save = save ;
    	 this.link = link ;
    	 this.flink = flink ;
    	 this.rlink = rlink ;
    	 this.clink = clink ;
    	 this.isfav = isfav ;
    	 this.status = status ;
    	 this.id = id ;
    	 this.dlink = dlink ;
	}
     public Iteminfo(){
    	 
     }
     
   public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


public String getDlink() {
		return dlink;
	}
	public void setDlink(String dlink) {
		this.dlink = dlink;
	}
public int getStatus() {
	return status;
   }
   public void setStatus(int status) {
	this.status = status;
   }
	public int getIsfav() {
		return isfav;
	}

	public void setIsfav(int isfav) {
		this.isfav = isfav;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getClink() {
		return clink;
	}

	public void setClink(String clink) {
		this.clink = clink;
	}

	public String getFlink() {
		return flink;
	}

	public void setFlink(String flink) {
		this.flink = flink;
	}

	public String getRlink() {
		return rlink;
	}

	public void setRlink(String rlink) {
		this.rlink = rlink;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getCriticism() {
		return criticism;
	}

	public void setCriticism(String criticism) {
		this.criticism = criticism;
	}

	public String getSupport() {
		return support;
	}

	public void setSupport(String support) {
		this.support = support;
	}

	public String getOppose() {
		return oppose;
	}

	public void setOppose(String oppose) {
		this.oppose = oppose;
	}

	 public String getSave() {
		return save;
	}
	 public void setSave(String save) {
		this.save = save;
	}
	 public String getLink() {  
		return link;
	}
	 public void setLink(String link) {
		this.link = link;
	}
	@Override
	public String toString() {
		return "Iteminfo [content=" + content + ", date=" + date + ", header="
				+ header + ", criticism=" + criticism + ", support=" + support
				+ ", oppose=" + oppose + "]";
	}
     
}
