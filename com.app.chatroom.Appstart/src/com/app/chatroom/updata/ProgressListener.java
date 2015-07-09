package com.app.chatroom.updata;


public interface ProgressListener {

	public void onProgress(Object tag, int progress);

	public void onToastError(String msg);
	
	public void onDialogError(String msg);

	public void onBaseUrl(String url);
}