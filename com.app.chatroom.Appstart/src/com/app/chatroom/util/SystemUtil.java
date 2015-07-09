package com.app.chatroom.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Environment;
import android.text.ClipboardManager;
import android.util.Log;

import com.app.chatroom.Appstart;
import com.app.chatroom.contants.ConstantsJrc;

public class SystemUtil {
	
	
	 public static List <NameValuePair> params=new ArrayList<NameValuePair>();  
	/**
	 * 递归获得文件夹大小
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static long getFolderSize(File file) throws Exception {
		long size = 0;

		File[] fileList = file.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isDirectory()) {
				size = size + getFolderSize(fileList[i]);
			} else {
				size = size + fileList[i].length();
			}
		}
		return size;
	}

	/**
	 * 递归删除文件和文件夹
	 * 
	 * @param file
	 *            要删除的根目录
	 */
	public static void RecursionDeleteFile(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}
		if (file.isDirectory()) {
			File[] childFile = file.listFiles();
			if (childFile == null || childFile.length == 0) {
				file.delete();
				return;
			}
			for (File f : childFile) {
				RecursionDeleteFile(f);
			}
			file.delete();
		}
	}

	/**
	 * 检测SD卡是否可用
	 * 
	 * @return
	 */
	public static boolean getSDCardMount() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检测网络连接是否好用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo network = cm.getActiveNetworkInfo();
		if (network != null) {
			return network.isAvailable();
		}
		return false;
	}

	/**
	 * 检测WIFI或数据流量
	 * 
	 * @param context
	 * @return
	 */
	public static String checkNetworkInfo(Context context) {
		ConnectivityManager conMan = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE); // mobile// 3G
		State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState();
		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		if (mobile == State.CONNECTED)
			return "mobile";
		if (wifi == State.CONNECTED)
			return "wifi";
		return "disconnected";
	}

	/**
	 * 实现文本复制功能
	 * 
	 * @param content
	 */
	public static void copy(String content, Context context) {
		// 得到剪贴板管理器
		ClipboardManager cmb = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		cmb.setText(content.trim());
	}

	/**
	 * 创建本地JS文件
	 * 
	 * @param path
	 *            文件路径
	 * @param filename
	 *            文件名
	 * @param content
	 *            写入内容
	 * @param append
	 *            是否追加
	 * @throws IOException
	 */
	public static void writeUidJs(String path, String filename, String content,
			boolean append) throws IOException {
		File filepath = new File(path);
		if (!filepath.exists()) {
			filepath.mkdirs();
		}
		File file = new File(filepath.getAbsolutePath() + File.separator
				+ filename);
		FileWriter fw = new FileWriter(file, append);

		if (!file.exists()) {
			file.createNewFile();
		}

		fw.write(content);
		fw.close();
	}

	/**
	 * 查找相关子字符串
	 * @param path 文件路径
	 * @param filename 文件名
	 * @param compareText 比较字符
	 * @return
	 * @throws IOException
	 */
	public static boolean readFileCompareText(String path, String filename,
			String compareText) throws IOException {
		boolean result = false;
		String lineTxt = "";
		String allString = "";
		File file = new File(path + File.separator + filename);
		if (file.exists()) {
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					file));
			BufferedReader bufferedReader = new BufferedReader(read);
		
			while ((lineTxt = bufferedReader.readLine()) != null) {
				allString = allString + lineTxt;
			}
			result = Pattern.compile(compareText).matcher(allString).find();
		}
		
		return result;
	}

	/**
	 * 创建文件
	 * 
	 * @param context
	 */
	public static void makeDir(Context context) {
		// 初始化基本文件夹
		if (SystemUtil.getSDCardMount()) {
			File file = new File(Environment.getExternalStorageDirectory()
					+ File.separator + context.getPackageName()
					+ ConstantsJrc.IMAGE_PATH);
			File file2 = new File(Environment.getExternalStorageDirectory()
					+ File.separator + context.getPackageName()
					+ ConstantsJrc.PHOTO_PATH);
			File file3 = new File(Environment.getExternalStorageDirectory()
					+ File.separator + context.getPackageName()
					+ ConstantsJrc.AUDIO_PATH);

			Appstart.jrcfile = new File(Environment
					.getExternalStorageDirectory().toString()
					+ "/"
					+ context.getPackageName() + ConstantsJrc.PHOTO_PATH);
			Appstart.jrcsave = new File(Environment
					.getExternalStorageDirectory().toString()
					+ "/"
					+ ConstantsJrc.SAVE_PATH);
			if (!Appstart.jrcfile.exists()) {
				Appstart.jrcfile.mkdirs();
			}
			if (!Appstart.jrcsave.exists()) {
				Appstart.jrcsave.mkdirs();
			}
			if (!file.exists()) {
				file.mkdirs();
			}
			if (!file2.exists()) {
				file2.mkdirs();
			}
			if (!file3.exists()) {
				file3.mkdirs();
			}

		} else {
			File file = new File(ConstantsJrc.PROJECT_PATH
					+ context.getPackageName() + ConstantsJrc.IMAGE_PATH);
			File file2 = new File(ConstantsJrc.PROJECT_PATH
					+ context.getPackageName() + ConstantsJrc.PHOTO_PATH);
			File file3 = new File(ConstantsJrc.PROJECT_PATH
					+ context.getPackageName() + ConstantsJrc.AUDIO_PATH);
			Appstart.jrcfile = new File("/data/data/"
					+ context.getPackageName() + ConstantsJrc.PHOTO_PATH);
			if (!Appstart.jrcfile.exists()) {
				Appstart.jrcfile.mkdirs();
			}
			Appstart.jrcsave = new File("/data/data/"
					+ context.getPackageName() + "vePhoto");
			if (!Appstart.jrcsave.exists()) {
				Appstart.jrcsave.mkdirs();
			}
			if (!file.exists()) {
				file.mkdirs();
			}
			if (!file2.exists()) {
				file2.mkdirs();
			}
			if (!file3.exists()) {
				file3.mkdirs();
			}
		}
	}

	/**
	 * 检验是否为图片文件
	 * 
	 * @param picpath
	 * @return
	 */
	public static boolean checkIsPic(String picpath) {
		try {
			// 从SDCARD下读取一个文件
			FileInputStream inputStream = new FileInputStream(picpath);
			byte[] buffer = new byte[2];
			// 文件类型代码
			String filecode = "";
			// 文件类型
			String fileType = "";
			// 通过读取出来的前两个字节来判断文件类型
			if (inputStream.read(buffer) != -1) {
				for (int i = 0; i < buffer.length; i++) {
					// 获取每个字节与0xFF进行与运算来获取高位，这个读取出来的数据不是出现负数
					// 并转换成字符串
					filecode += Integer.toString((buffer[i] & 0xFF));
				}
				Log.i("filetype", filecode);
				// 把字符串再转换成Integer进行类型判断
				switch (Integer.parseInt(filecode)) {
				case 7790:
					fileType = "exe";
					return false;
				case 7784:
					fileType = "midi";
					return false;
				case 8297:
					fileType = "rar";
					return false;
				case 8075:
					fileType = "zip";
					return false;
				case 255216:
					fileType = "jpg";
					return true;
				case 7173:
					fileType = "gif";
					return true;
				case 6677:
					fileType = "bmp";
					return true;
				case 13780:
					fileType = "png";
					return true;
				default:
					fileType = "unknown type: " + filecode;
					return false;
				}
			}
			Log.i("filetype", fileType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获得文件的大小KB,MB
	 * 
	 * @param size
	 * @return
	 */
	public static String CountSize(long size) {
		final double baseKB = 1024.00, baseMB = 1024 * 1024.00;
		String strSize = "";
		if (size < baseKB) {
			strSize = size + " B";
		}
		if (size > baseKB && size < baseMB) {
			BigDecimal b = new BigDecimal((size / baseKB));
			float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
			strSize = String.valueOf(f1 + "KB");
		} else if (size > baseMB) {
			BigDecimal b = new BigDecimal((size / baseMB));
			float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
			strSize = String.valueOf(f1 + "MB");
		}
		return strSize;
	}

	/***
	 * 获取网络数据 自动判断压缩数据<Get方式>
	 * 
	 * @param url
	 * @return
	 */
	public static String returnData(String url) {
		String strResult = "";
		String sCurrentLine = "";
		HttpResponse httpResponse = null;
		InputStream is = null;
		HttpGet httpRequest = new HttpGet(url);
		httpRequest.addHeader("Accept-Encoding", "gzip");
		try {
			httpResponse = new DefaultHttpClient().execute(httpRequest);
			// 连接状态成功
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 取出响应字符 */
				HttpEntity entity = httpResponse.getEntity();
				Header header = entity.getContentEncoding();
				if (header != null) {
					String str = header.getValue();
					// 判断是否为压缩数据
					if (str != null && str.compareToIgnoreCase("gzip") == 0) {
						is = new GZIPInputStream(entity.getContent());
					}
				} else {
					is = entity.getContent();
				}

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));

				while ((sCurrentLine = reader.readLine()) != null) {
					strResult += sCurrentLine + "\r\n";
				}
				// System.out.println("strresult:" + strResult);
			} else {
				strResult = "";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return strResult;
	}

	/**
	 * 获取网络数据 自动判断压缩数据<Post方式>
	 * 
	 * @param url
	 * @return
	 */
	public static String returnPostData(String httpUrl, String strUrl) {
		String strResult = "";
		String sCurrentLine = "";
		HttpURLConnection conn =null;
		try {
			InputStream is = null;
			URL url = new URL(httpUrl);
			conn = (HttpURLConnection) url.openConnection();
			// 这里是关键，表示我们要向链接里输出内容
			conn.setDoOutput(true);
			conn.setRequestProperty("Accept-Encoding", "gzip");
			conn.setRequestMethod("POST");
			// 获得连接输出流
			OutputStreamWriter out = new OutputStreamWriter(
					conn.getOutputStream());
			out.write(strUrl);
			out.flush();
			out.close();

			String str = conn.getContentEncoding();
			if (str != null && str.compareToIgnoreCase("gzip") == 0) {
				is = new GZIPInputStream(conn.getInputStream());
			} else {
				is = conn.getInputStream();
			}
			/** 请求完成，获取返回数据 **/
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));

			while ((sCurrentLine = reader.readLine()) != null) {
				strResult += sCurrentLine + "\r\n";
			}
			is.close();
			reader.close();
			// System.out.println("strresult:" + strResult);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			if(conn!=null)
		     conn.disconnect();
		   }

		return strResult;
	}
	//HttpClint
	public static String returnPostData(String httpUrl,String uid, String pd, String imei,
			String imsi, String model, String osver, String width,
			String height, String vercode, String ver, String phone,
			String sim, String lang, String pkg, String mac, String cid,
			String token) {
		 HttpPost httpRequest =new HttpPost(httpUrl);  
		    //Post运作传送变数必须用NameValuePair[]阵列储存  
		    //传参数 服务端获取的方法为request.getParameter("name")  
//		    if(params.contains("uid")){
//		    	 params.add(new BasicNameValuePair("uid",uid));  
//		    }
//		    
//		    if(params.contains("pd")){
//		    	 params.add(new BasicNameValuePair("pd",pd));  
//		    }
//		    if(params.contains("token")){
//		    	 params.add(new BasicNameValuePair("uid",uid));  
//		    }		
		 if(params.size() != 0  &&!uid.equals(params.get(0).getValue())){
			 params.clear();
		 }
		 if(params != null && params.size() == 0){
		    params.add(new BasicNameValuePair("uid",uid));  
		    params.add(new BasicNameValuePair("pd",pd));  
		    params.add(new BasicNameValuePair("lang",lang));  
		    params.add(new BasicNameValuePair("width",width));  
		    params.add(new BasicNameValuePair("height",height));  
		    params.add(new BasicNameValuePair("ver",ver));  
		    params.add(new BasicNameValuePair("vercode",vercode));  
		    params.add(new BasicNameValuePair("imei",imei));  
		    params.add(new BasicNameValuePair("imsi",imsi));  
		    params.add(new BasicNameValuePair("osver",osver));  
		    params.add(new BasicNameValuePair("pkg",pkg));  
		    params.add(new BasicNameValuePair("model",model));  
		    params.add(new BasicNameValuePair("phone",phone));  
		    params.add(new BasicNameValuePair("sim",sim));  
		    params.add(new BasicNameValuePair("mac",mac));
		    params.add(new BasicNameValuePair("cid",cid));  
		    params.add(new BasicNameValuePair("token",token));  
		 }
		 params.set(1, new BasicNameValuePair("pd",pd));
		 params.set(16, new BasicNameValuePair("token",token));
			InputStream is = null;
			String strResult = "";
			String sCurrentLine = "";
		try {
			//发出HTTP request  
		     httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));  
		     //取得HTTP response  
		     HttpResponse response=new DefaultHttpClient().execute(httpRequest);  
		       
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					Header header = response.getEntity().getContentEncoding();
					if (header != null) {
						String str = header.getValue();
						// 判断是否为压缩数据
						if (str != null && str.compareToIgnoreCase("gzip") == 0) {
							is = new GZIPInputStream(response.getEntity()
									.getContent());
						}
					} else {
						is = response.getEntity().getContent();
					}

					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is));

					while ((sCurrentLine = reader.readLine()) != null) {
						strResult += sCurrentLine + "\r\n";
					}
					try {
						if (is != null)
							is.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// System.out.println("strresult:" + strResult);
					// result = EntityUtils.toString(response.getEntity());

				}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return strResult;
	}

}
