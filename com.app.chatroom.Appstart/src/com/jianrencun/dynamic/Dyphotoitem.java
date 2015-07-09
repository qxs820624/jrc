package com.jianrencun.dynamic;

public class Dyphotoitem {
	private String url , burl;
	private int w, h;
	public Dyphotoitem(String url, int w, int h , String burl) {
		super();
		this.url = url;
		this.w = w;
		this.h = h;
		this.burl = burl ;
	}
	
	
	public String getBurl() {
		return burl;
	}


	public void setBurl(String burl) {
		this.burl = burl;
	}


	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	@Override
	public String toString() {
		return "Dyphotoitem [url=" + url + ", w=" + w + ", h=" + h + "]";
	}
	
}
