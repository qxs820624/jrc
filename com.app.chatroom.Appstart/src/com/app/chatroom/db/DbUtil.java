package com.app.chatroom.db;

import com.app.chatroom.json.bean.MailBean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbUtil {
	private SQLiteDatabase mdb;
	private DataBaseHelper mDbHelper;

	public static final String ID = "_id";
	public static final String MID = "m_id";
	public static final String TYPE = "type";
	public static final String FUID = "fuid";
	public static final String FNICK = "fnick";
	public static final String FHEADER = "fheader";
	public static final String FSEX = "fsex";
	public static final String TUID = "tuid";
	public static final String TNICK = "tnick";
	public static final String THEADER = "theader";
	public static final String TSEX = "tsex";
	public static final String CONTENT = "content";
	public static final String AFILE = "afile";
	public static final String PFILE = "pfile";
	public static final String PTIME = "ptime";
	public static final String ISREAD = "isread";
	public static final String DATABASE_NAME = "JrcDataBase";// 数据库名
	public static final String MAIL_TABLE = "MailTable";// 私信表名
	private static final int DATABASE_VERSION = 1;
	private Context context;
	Cursor cursor1;
	public static final String MAILTABLE_CREATE = "create table " + MAIL_TABLE
			+ "(_id integer primary key autoincrement," + "m_id text,"
			+ "type text," + "fuid text," + "fnick text," + "fheader text,"
			+ "fsex text," + "tuid text," + "tnick text," + "theader text,"
			+ "tsex text," + "content text," + "afile text," + "pfile text,"
			+ "ptime text," + "isread text);";

	private static class DataBaseHelper extends SQLiteOpenHelper {

		public DataBaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			// 创建表
			db.execSQL(MAILTABLE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			// 删除表 重新生成表
			db.execSQL("drop table if exists " + MAIL_TABLE);
			onCreate(db);
		}
	}

	public DbUtil(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		mDbHelper = new DataBaseHelper(context);
	}

	// 创建数据库并打开
	public DbUtil Open() {
		mdb = mDbHelper.getReadableDatabase();
		return this;
	}

	// 关闭数据库
	public void Close() {
		mDbHelper.close();
		mdb.close();
		// cursor1.close();
	}

	// 按表字段插入数据
	public long Insert(int mid, String type, String fuid, String fnick,
			String fheader, String fsex, String tuid, String tnick,
			String theader, String tsex, String content, String afile,
			String pfile, String ptime, String isread) {
		ContentValues value = new ContentValues();
		value.put(MID, mid);
		value.put(TYPE, type);
		value.put(FUID, fuid);
		value.put(FNICK, fnick);
		value.put(FHEADER, fheader);
		value.put(FSEX, fsex);
		value.put(TUID, tuid);
		value.put(TNICK, tnick);
		value.put(THEADER, theader);
		value.put(TSEX, tsex);
		value.put(CONTENT, content);
		value.put(AFILE, afile);
		value.put(PFILE, pfile);
		value.put(PTIME, ptime);
		value.put(ISREAD, isread);
		return mdb.insert(MAIL_TABLE, null, value);
	}

	// 按实体类插入数据
	public long Insert(MailBean mailBean) {
		ContentValues value = new ContentValues();
		value.put(MID, mailBean.getId());
		value.put(TYPE, mailBean.getType());
		value.put(FUID, mailBean.getFuid());
		value.put(FNICK, mailBean.getFnick());
		value.put(FHEADER, mailBean.getFheader());
		value.put(FSEX, mailBean.getFsex());
		value.put(TUID, mailBean.getTuid());
		value.put(TNICK, mailBean.getTnick());
		value.put(THEADER, mailBean.getTheader());
		value.put(TSEX, mailBean.getTsex());
		value.put(CONTENT, mailBean.getContent());
		value.put(AFILE, mailBean.getAfile());
		value.put(PFILE, mailBean.getPfile());
		value.put(PTIME, mailBean.getPtime());
		value.put(ISREAD, mailBean.getIsread());

		return mdb.insert(MAIL_TABLE, null, value);
	}

	/**
	 * 删掉指定FUID数据
	 * 
	 * @param fuid
	 * @return
	 */
	public Boolean delete(int fuid) {
		return mdb.delete(MAIL_TABLE, FUID + "=" + fuid + " or " + TUID + "="
				+ fuid, null) > 0;

	}

	public Boolean delete2(int tuid) {
		return mdb.delete(MAIL_TABLE, TUID + "=" + tuid, null) > 0;
	}

	/**
	 * 删除所有数据
	 * 
	 * @return
	 */
	public Boolean deleteAll() {
		return mdb.delete(MAIL_TABLE, null, null) > 0;
	}

	/**
	 * 获取所有数据
	 * 
	 * @return
	 */
	public Cursor getAllData() {

		Cursor cursor = mdb.query(MAIL_TABLE, new String[] { MID, TYPE, FUID,
				FNICK, FHEADER, FSEX, TUID, TNICK, THEADER, TSEX, CONTENT,
				AFILE, PFILE, PTIME, ISREAD }, null, null, null, null, null);

		return cursor;
	}

	/**
	 * 获取指定人的未读邮件
	 * 
	 * @param fuid
	 *            用户UID
	 * @return
	 */
	public int getNumMail(int fuid) {

		Cursor cursor = mdb.query(MAIL_TABLE, new String[] { MID, TYPE, FUID,
				FNICK, FHEADER, FSEX, TUID, TNICK, THEADER, TSEX, CONTENT,
				AFILE, PFILE, PTIME, ISREAD }, FUID + "=" + fuid
				+ " and isread = 1", null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}

		return cursor.getCount();
	}

	public int getNumMail2(int tuid) {

		Cursor cursor = mdb.query(MAIL_TABLE, new String[] { MID, TYPE, FUID,
				FNICK, FHEADER, FSEX, TUID, TNICK, THEADER, TSEX, CONTENT,
				AFILE, PFILE, PTIME, ISREAD }, TUID + "=" + tuid
				+ " and isread = 1", null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}

		return cursor.getCount();
	}

	/**
	 * 获取邮件数量
	 * 
	 * @param isread
	 *            是否阅读 1=未读，0=已读
	 * @return
	 */
	public int getNoReadMail(int isread, String fuid) {

		cursor1 = mdb.query(MAIL_TABLE, new String[] { MID, TYPE, FUID, FNICK,
				FHEADER, FSEX, TUID, TNICK, THEADER, TSEX, CONTENT, AFILE,
				PFILE, PTIME, ISREAD }, ISREAD + " = " + isread + " and "
				+ FUID + "<>" + fuid, null, null, null, null);
		return cursor1.getCount();
	}

	/**
	 * 获取信息条数，去掉重复FUID项,先按未读降序，再按时间降序
	 * 
	 * @param fuid
	 *            用户UID
	 * @param fuid2
	 *            同上填写一样即可
	 * @return
	 */
	public Cursor getFuid(String fuid, String fuid2) {
		Cursor cursor = mdb.rawQuery("select newtable.* from "
				+ "(select fuid||tuid gg,count(*) cc,max(_id) aid from "
				+ MAIL_TABLE + " where fuid=? group by fuid||tuid" + " union "
				+ "select tuid||fuid gg,count(*) cc,max(_id) aid from "
				+ MAIL_TABLE + " where tuid=? group by tuid||fuid)"
				+ " resulttable " + "left join " + MAIL_TABLE
				+ " newtable on resulttable.aid = newtable._id"
				+ " group by resulttable.gg order by ptime desc", new String[] {
				fuid, fuid2 });

		return cursor;
	}

	/**
	 * 获取指定Fuid的所有邮件
	 */
	public Cursor getFuid(int fuid) {
		Cursor cursor = mdb.query(MAIL_TABLE, new String[] { MID, TYPE, FUID,
				FNICK, FHEADER, FSEX, TUID, TNICK, THEADER, TSEX, CONTENT,
				AFILE, PFILE, PTIME, ISREAD }, FUID + " = " + fuid, null, null,
				null, "ptime asc");
		return cursor;
	}

	/**
	 * 根据Fuid自动排序成对话方式<未读和时间排序>
	 * 
	 * @param fuid
	 * @param tuid
	 * @return
	 */
	public Cursor getFuid2(int fuid, int tuid) {
		Cursor cursor = mdb.query(MAIL_TABLE, new String[] { MID, TYPE, FUID,
				FNICK, FHEADER, FSEX, TUID, TNICK, THEADER, TSEX, CONTENT,
				AFILE, PFILE, PTIME, ISREAD }, FUID + "=" + fuid + " or ("
				+ FUID + "=" + tuid + " and " + TUID + " = " + fuid + ")",
				null, null, null, "ptime asc");

		return cursor;
	}

	/**
	 * 更新是否阅读
	 * 
	 * @param fuid
	 *            用户UID
	 * @param isread
	 *            阅读标识 1=未读 ，0=已读
	 * @return
	 */
	public boolean updateIsread(int fuid, String isread) {
		ContentValues value = new ContentValues();
		value.put(ISREAD, isread);
		return mdb.update(MAIL_TABLE, value, FUID + "=" + fuid, null) > 0;
	}

	public boolean updateIsread2(int tuid, String isread) {
		ContentValues value = new ContentValues();
		value.put(ISREAD, isread);
		return mdb.update(MAIL_TABLE, value, TUID + "=" + tuid, null) > 0;
	}

}
