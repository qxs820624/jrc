package com.jianrencun.android;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.duom.fjz.iteminfo.CateDetailEntity;
import com.duom.fjz.iteminfo.Iteminfo;

public class CacheData {
	public static ArrayList<CategoryEntity> cateList = new ArrayList<CategoryEntity>(); ;
	public static ArrayList<CateDetailEntity> detailcontent ;
	static File sddetail ;
	static String detailgorys ;
	static File sdsc ;
	static String myscgorys;
	public static String catePd = "";
	/**
	 * 
	 * @param context
	 * @return
	 */
	public static void getCategoryList(Context context) {
		try {
			// 取出的分类列表
			FileInputStream fis = context.openFileInput("categorys");
			// pd值
			int len = fis.read();
			byte[] buff = new byte[len];
			fis.read(buff);
			catePd = new String(buff);
			// 个数
			int count = fis.read();
			for (int i = 0; i < count; i++) {
				CategoryEntity entity = new CategoryEntity();
				// 分类链接
				len = fis.read();
				buff = new byte[len];
				fis.read(buff);
				entity.setLink(new String(buff));
				// 分类标题
				len = fis.read();
				buff = new byte[len];
				fis.read(buff);
				entity.setName(new String(buff));
				//
				cateList.add(entity);
			}
			fis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 * @param context
	 * @param list
	 * @param pd
	 */
	public static void setCategoryList(Context context) {
		try {
			// 保存返回的分类列表
			FileOutputStream fos = context.openFileOutput("categorys", 0);
			// pd值
			byte[] buff = catePd.getBytes();
			fos.write(buff.length);
			fos.write(buff);
			// 个数
			fos.write(cateList.size());
			for (int i = 0; i < cateList.size(); i++) {
				CategoryEntity entity = cateList.get(i);

				// 分类链接
				buff = entity.getLink().getBytes();
				fos.write(buff.length);
				fos.write(buff);
				// 分类标题
				buff = entity.getName().getBytes();
				fos.write(buff.length);
				fos.write(buff);				
			}
			fos.flush();
			fos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void save(String filename, byte[] buffer, Context context) {
		try {
			FileOutputStream outStream = new FileOutputStream(filename);
			outStream.write(buffer);
			outStream.close();
		} catch (FileNotFoundException e) {
			return;
		} catch (IOException e) {
			return;
		}
	}

	public static String load(String filename, Context context) {
		try {
			FileInputStream inStream = new FileInputStream(filename);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length = -1;
			while ((length = inStream.read(buffer)) != -1) {
				stream.write(buffer, 0, length);
			}
			stream.close();
			inStream.close();
			// text.setText(stream.toString());
			return stream.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			return null;
		}
	}
	

	public static void getDetailcontent(Context context ,String url) {
		try {
			// 取出的分类列表
			FileInputStream fis = context.openFileInput(url);
			// pd值
			int len = fis.read();
			byte[] buff = new byte[len];
			fis.read(buff);
			// Qidongyemian.catePd = new String(buff);
			// 个数
			int count = fis.read();
			for (int i = 0; i < count; i++) {
				CateDetailEntity entity = new CateDetailEntity();
				// 分类链接
				len = fis.read();
				buff = new byte[len];
				fis.read(buff);
				entity.setLink(new String(buff));
				// 分类标题
				len = fis.read();
				buff = new byte[len];
				fis.read(buff);
				entity.setContent(new String(buff));
				//
				// 分类标题
				len = fis.read();
				buff = new byte[len];
				fis.read(buff);
				entity.setFeedback(new String(buff));
				detailcontent.clear();
				detailcontent.add(entity);
			}
			fis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 * @param context
	 * @param list
	 * @param pd
	 */
	public static void setDetailcontent(Context context , String url) {
		try {
			// 保存返回的分类列表
			FileOutputStream fos = context.openFileOutput(url , 0);
			// pd值
						byte[] buff;
			fos.write(detailcontent.size());
			for (int i = 0; i < detailcontent.size(); i++) {
				CateDetailEntity entity = detailcontent.get(i);

				// 分类链接
				buff = entity.getLink().getBytes();
				fos.write(buff.length);
				fos.write(buff);
				// 分类标题
				buff = entity.getContent().getBytes();
				fos.write(buff.length);
				fos.write(buff);				
				// 分类feedback
				buff = entity.getFeedback().getBytes();
				fos.write(buff.length);
				fos.write(buff);					
			}
			fos.flush();
			fos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


}
