package com.jianrencun.game;

public class Gameiteminfo {
     private String header , name ;
     private int gid ;
     public Gameiteminfo(String header ,String name , int gid) {
		// TODO Auto-generated constructor stub
    	 this.header = header ;
    	 this.name = name ;
    	 this.gid = gid ;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
     
}
