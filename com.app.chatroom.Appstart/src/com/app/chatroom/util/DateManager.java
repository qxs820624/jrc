package com.app.chatroom.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.text.TextUtils;

public class DateManager {
	/**
	 * 转换时间为long型
	 * 
	 * @param dateStr
	 * @return
	 */
	static public long getDate(String dateStr, boolean isShort) {
		long millis = -1;
		Date date = null;
		if (TextUtils.isEmpty(dateStr))
			return millis;
		if (TextUtils.isEmpty(dateStr) || TextUtils.isDigitsOnly(dateStr)) {
			date = new Date(Long.parseLong(dateStr));
		} else {
			String formatStr = "yyyy-MM-dd HH:mm:ss";
			if (isShort) {
				formatStr = "yyyy-MM-dd";
			}
			SimpleDateFormat format = new SimpleDateFormat(formatStr);
			try {
				date = format.parse(dateStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			millis = calendar.getTimeInMillis();
		}
		return millis;
	}

	/**
	 * 获取几分钟前时间
	 * 
	 * @param baseMillis
	 *            开始时间
	 * @param pubMillis
	 *            最新时间
	 * @return
	 */
	static public String strDate(long baseMillis, long pubMillis) {
		long millis = baseMillis - pubMillis;
		if (millis < 0)
			millis = 0;
		millis = millis / 1000;
		// //@////@System.out.println("millis:" + millis);
		if (millis < 60)
			return millis + "秒前";
		else {
			if (millis < 60 * 60)
				return (int) (millis / 60) + "分钟前";
			else {
				if (millis < 24 * 60 * 60)
					return (int) (millis / (60 * 60)) + "小时前";
				else {
					if (millis < 7 * 24 * 60 * 60) {
						int days = (int) (millis / (24 * 60 * 60));
						if (days == 1)
							return "昨天";
						else if (days == 2)
							return "前天";
						Calendar calendar1 = Calendar.getInstance();
						calendar1.setTimeInMillis(baseMillis);
						int d1 = calendar1.get(Calendar.DAY_OF_WEEK);
						if (days >= d1) {
							Calendar calendar2 = Calendar.getInstance();
							calendar2.setTimeInMillis(pubMillis);
							int d2 = calendar2.get(Calendar.DAY_OF_WEEK);
							return "上周" + weekStr(d2);
						}
						return days + "天前";
					} else {
						if (millis < 4 * 7 * 24 * 60 * 60) {
							int weeks = (int) (millis / (7 * 24 * 60 * 60));
							if (weeks == 1) {
								String strWeek = "上周";
								Calendar calendar = Calendar.getInstance();
								calendar.setTimeInMillis(pubMillis);
								int d = calendar.get(Calendar.DAY_OF_WEEK);
								strWeek += weekStr(d);
								return strWeek;
							}
							return weeks + "周前";
						} else {
							if (millis < 29030400/* (12 * 4 * 7 * 24 * 60 * 60) */) {
								return (int) (millis / (4 * 7 * 24 * 60 * 60))
										+ "月前";
							} else {
								int y = (int) (millis / 29030400/*
																 * (12 * 4 * 7 *
																 * 24 * 60 * 60)
																 */);
								if (y <= 0)
									return null;
								return y + "年前";
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 转换周几
	 * 
	 * @param pubdate
	 * @return
	 */
	static public String comDate(String baseDate, String pubDate) {
		long baseMillis = getDate(baseDate, false);
		if (baseMillis == -1)
			return pubDate;

		long pubMillis = getDate(pubDate, false);
		if (pubMillis == -1)
			return pubDate;

		//
		String str = strDate(baseMillis, pubMillis);
		return str == null ? pubDate : str;
	}

	static public String weekStr(int week) {
		switch (week) {
		case 1:
			return "一";
		case 2:
			return "二";
		case 3:
			return "三";
		case 4:
			return "四";
		case 5:
			return "五";
		case 6:
			return "六";
		case 7:
			return "日";
		}
		return week + "";
	}

	/**
	 * 获取手机当前时间
	 * 
	 * @return
	 */
	public static String getPhoneTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String time = formatter.format(curDate);
		// System.out.println("当前时间");
		return time;

	}
}
