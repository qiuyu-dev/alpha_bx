package com.mysoft.alpha.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	/**
	 * 将日期字符转换为指定格式日期字符.缺省格式为yyyy-MM-dd
	 *
	 * @param dateStr    日期
	 * @param dateFormat 日期格式
	 * @return 按指定格式返回日期
	 */
	public static String getDateByFormat(String dateStr, String dateFormat) {
		if (dateFormat == null || "".equals(dateFormat)) {
			dateFormat = "yyyy-MM-dd HH:mm:ss";
		}
		String str = "";
		try {
			if (dateStr != null && !"".equals(dateStr)) {
				dateStr = dateStr.replaceAll("年", "-");
				dateStr = dateStr.replaceAll("月", "-");
				dateStr = dateStr.replaceAll("日", "");
				dateStr = dateStr.replaceAll("/", "-");
				java.sql.Date dt = java.sql.Date.valueOf(dateStr);
				SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
				str = sdf.format(dt);
			} else {
				str = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 字符串日期转成Calendar
	 *
	 * @param sdate
	 * @return Calendar
	 */
	public static Calendar convertToCalendar(String sdate) {
		Calendar c = Calendar.getInstance();
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date dt = df.parse(sdate);
			c.setTime(dt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	/*
	 * 字符串日期转成Date
	 *
	 * @param sdate
	 *
	 * @return java.util.Date
	 */
	public static Date convertExcelToDate(String sdate, String format) {
		Date dt;
		try {
			if (format.contains("yy")) {
				SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date dt1 = df1.parse("1900-01-01 00:00:00");
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(dt1);
				calendar.add(Calendar.DATE, Float.valueOf(sdate).intValue() - 2);
				dt = calendar.getTime();
			} else {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				dt = df.parse(sdate);
			}
			return dt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date convertExcelToDateBegin(String sdate, String format) {
		Date dt;
		try {
			if (format.contains("yy")) {
				SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date dt1 = df1.parse("1900-01-01 00:00:00");
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(dt1);
				calendar.add(Calendar.DATE, Float.valueOf(sdate).intValue() - 2);
				calendar.set(Calendar.HOUR, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				dt = calendar.getTime();
			} else {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				dt = df.parse(sdate);
			}
			return dt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date convertExcelToDateEnd(String sdate, String format) {
		Date dt;
		try {
			if (format.contains("yy")) {
				SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date dt1 = df1.parse("1900-01-01 00:00:00");
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(dt1);
				calendar.add(Calendar.DATE, Float.valueOf(sdate).intValue() - 2);
				calendar.set(Calendar.HOUR, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				dt = calendar.getTime();
			} else {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				dt = df.parse(sdate);
			}
			return dt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 字符串日期转成Date
	 *
	 * @param sdate
	 *
	 * @return java.util.Date
	 */
	public static Date convertToDate(String sdate) throws Exception {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date dt = df.parse(sdate);
			return dt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getCurrentDate() {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String dt = df.format(new Date());
			return dt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static Date addTime(Date date, int dateType, int addend) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(dateType, addend);
		return calendar.getTime();
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getStringDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取时间 小时:分;秒 HH:mm:ss
	 * 
	 * @return
	 */
	public static String getTimeShort() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date currentTime = new Date();
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDateLong(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrLong(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式时间转换为字符串 yyyy-MM-dd
	 * 
	 * @param dateDate
	 * @param k
	 * @return
	 */
	public static String dateToStr(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 得到现在时间
	 * 
	 * @return
	 */
	public static Date getNow() {
		Date currentTime = new Date();
		return currentTime;
	}

	/**
	 * 提取一个月中的最后一天
	 * 
	 * @param day
	 * @return
	 */
	public static Date getLastDate(long day) {
		Date date = new Date();
		long date_3_hm = date.getTime() - 3600000 * 34 * day;
		Date date_3_hm_date = new Date(date_3_hm);
		return date_3_hm_date;
	}

	/**
	 * 得到现在时间
	 * 
	 * @return 字符串 yyyyMMdd HHmmss
	 */
	public static String getStringToday() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 得到现在小时
	 */
	public static String getHour() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String hour;
		hour = dateString.substring(11, 13);
		return hour;
	}

	/**
	 * 得到现在分钟
	 * 
	 * @return
	 */
	public static String getTime() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String min;
		min = dateString.substring(14, 16);
		return min;
	}

	/**
	 * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
	 * 
	 * @param sformat yyyyMMddhhmmss
	 * @return
	 */
	public static String getUserDate(String sformat) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(sformat);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

}
