package com.jianrencun.dazui;

import android.widget.ImageView;
import android.widget.TextView;

public class ZhutisIteminfo {
	private String iv;
	private String title, number , link , titleclor , desc;

	public ZhutisIteminfo(String iv, String title, String number , String link , String titleclor , String desc) {
		// TODO Auto-generated constructor stub
		this.iv = iv ;
		this.title = title ;
		this.number = number;	
		this.link = link ;
		this.titleclor = titleclor;
		this.desc = desc ;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTitleclor() {
		return titleclor;
	}

	public void setTitleclor(String titleclor) {
		this.titleclor = titleclor;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
}
