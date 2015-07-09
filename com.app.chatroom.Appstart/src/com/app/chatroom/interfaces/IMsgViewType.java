package com.app.chatroom.interfaces;

public interface IMsgViewType {
	// 服务器返回消息
	int IMVT_COM_MSG = 0;
	int IMVT_VIDEO_COM_MSG = 1;
	int IMVT_PIC_COM_MSG = 2;
	// 本地发送消息
	int IMVT_TO_MSG = 3;
	int IMVT_VIDEO_TO_MSG = 4;
	int IMVT_PIC_TO_MSG = 5;

	int IMVT_SYS_TO_MSG = 6;
}
