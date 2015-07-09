package com.app.chatroom.updata;

public class FileUpload {

	private ConnectPoolManager conn;
	private static FileUpload instance;

	public static FileUpload getInstance() {
		if (instance == null)
			synchronized (FileUpload.class) {
				if (instance == null)
					instance = new FileUpload();
			}

		return instance;
	}

	private FileUpload() {
		conn = new ConnectPoolManager(false);
	}

	public void release() {
		if (conn != null)
			conn.releaseAll();
	}

	/**
	 * 上传文件和数据
	 * 
	 * @param url
	 *            请求url
	 * @param formdata
	 *            key=value&key=value形式的post文本数据
	 * @param filename
	 *            需要上传的文件名
	 * @return
	 */
	public byte[] upload(String url, String formdata, String filekey,
			String filename) {
		UploadData upload = new UploadData();
		upload.nType = 2;
		upload.strText = formdata;
		upload.filekey = filekey;
		upload.strFilename = filename;
		upload.strType = "multipart/form-data";
		byte[] temp = conn.read(null, url, upload);
		return temp;
	}
}
