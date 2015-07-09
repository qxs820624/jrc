package com.app.chatroom.json.bean;

import java.io.Serializable;

public class ZjBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String CPID;
	private String ZJID;
	private String ZJNAME;

	public ZjBean(String cPID, String zJID, String zJNAME) {
		super();
		CPID = cPID;
		ZJID = zJID;
		ZJNAME = zJNAME;
	}

	public ZjBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCPID() {
		return CPID;
	}

	public void setCPID(String cPID) {
		CPID = cPID;
	}

	public String getZJID() {
		return ZJID;
	}

	public void setZJID(String zJID) {
		ZJID = zJID;
	}

	public String getZJNAME() {
		return ZJNAME;
	}

	public void setZJNAME(String zJNAME) {
		ZJNAME = zJNAME;
	}

}
