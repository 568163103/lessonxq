package com.beyeon.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class DateUtil extends DateUtils {
	public static String yyyyMMddHHmmss = "yyyyMMddHHmmss";
	public static String yyyyMMddHHmm = "yyyyMMddHHmm";
	public static String yyyyMMddHH = "yyyyMMddHH";
	public static String yyyyMMdd = "yyyyMMdd";
	public static String yyyyMM = "yyyyMM";
	public static String HHmmss = "HHmmss";
	public static String HHmm = "HHmm";
	public static String HH = "HH";

	public static String yyyyMMddHHmmssSpt = "yyyy-MM-dd HH:mm:ss";
	public static String yyyyMMddHHmmSpt = "yyyy-MM-dd HH:mm";
	public static String yyyyMMddSpt = "yyyy-MM-dd";
	public static String yyyyMMSpt = "yyyy-MM";
	public static String HHmmssSpt = "HH:mm:ss";
	public static String HHmmSpt = "HH:mm";

	public static Date subDays(Date source, int i) {
		return addDays(source, -i);
	}

	public static Date subHours(Date source, int i) {
		return addHours(source, -i);
	}

	public static Date subMinutes(Date source, int i) {
		return addMinutes(source, -i);
	}

	public static Date subSeconds(Date source, int i) {
		return addSeconds(source, -i);
	}

	public static Date getFirstDayOfYear(String format) {
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_YEAR, 1);
		return ca.getTime();
	}

	public static Date getLastMonth(String format) {
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, 1);
		ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) - 1);
		return ca.getTime();
	}

	public static Date getLastDayOfYear() {
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.MONTH, Calendar.DECEMBER);
		ca.set(Calendar.DAY_OF_MONTH, 30);
		return ca.getTime();
	}

	public static String format(String format) {
		return new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
	}

	public static String format(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	public static Date parse(String date, String format) {
		try {
			return new SimpleDateFormat(format).parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static Date parse(Date date, String format) {
		try {
			String str = new SimpleDateFormat(format).format(date);
			return new SimpleDateFormat(format).parse(str);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static Date defaultDate() {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(0);
		return c.getTime();
	}

	/**
	 * 获取YYYY-MM-DD hh:mm:ss格式
	 * 
	 * @return
	 */
	public static String getTime() {
		SimpleDateFormat sdfTime = new SimpleDateFormat(
				yyyyMMddHHmmssSpt);
		return sdfTime.format(new Date());
	}

	
	public static void main(String[] args) throws InterruptedException {
		System.out.println(getTime());

	}

	public static Date getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
		return cal.getTime();
	}

	public static Date getFirstDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DATE));
		return cal.getTime();
	}

	public static Date getNextDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DATE);
		cal.set(Calendar.DATE, day + 1);
		return cal.getTime();
	}

	public static Date getBeforeDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DATE);
		cal.set(Calendar.DATE, day - 1);
		return cal.getTime();
	}

	/**
	 * <pre>
	 * 获取N分钟之前的时间
	 * @param date
	 * @param beforeMinute
	 * @return
	 * </pre>
	 */
	public static Date getBeforeMinute(Date date, int beforeMinute) {
		if (beforeMinute > 60) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int minute = cal.get(Calendar.MINUTE);
		cal.set(Calendar.MINUTE, minute - beforeMinute);
		return cal.getTime();
	}

	/** 计算2个时间的差返回秒 */
	public static long betweenDayNumbe(Date dateA, Date dateB) {
		long second = 1000L;
		long munber = 0;
		munber = (dateA.getTime() - dateB.getTime()) / second;// 绝对值
		return munber;
	}
}
