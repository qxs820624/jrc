package com.app.chatroom.json.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class ZjMusicBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<ZjBean> music;

	public ZjMusicBean(ArrayList<ZjBean> music) {
		super();
		this.music = music;
	}

	public ZjMusicBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ArrayList<ZjBean> getMusic() {
		return music;
	}

	public void setMusic(ArrayList<ZjBean> music) {
		this.music = music;
	}

}
