package cn.ssm.service.spider.baidunews;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarUtil {

	private static final long ONE_MINUTE = 60;
	private static final long ONE_HOUR = 3600;
	private static final long ONE_DAY = 86400;
	private static final long ONE_MONTH = 2592000;
	private static final long ONE_YEAR = 31104000;

	public static Calendar calendar = Calendar.getInstance();

	public static String getDate(String format) {
		SimpleDateFormat simple = new SimpleDateFormat(format);
		return simple.format(calendar.getTime());
	}

	public static String getDateAndMinute() {
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return simple.format(calendar.getTime());
	}

	public static String getFullDate() {
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simple.format(calendar.getTime());
	}

	public static String getYear() {
		return calendar.get(Calendar.YEAR) + "";
	}

	public static String getMonth() {
		int month = calendar.get(Calendar.MONTH) + 1;
		return month + "";
	}

	public static String getDay() {
		return calendar.get(Calendar.DATE) + "";
	}

	public static String get24Hour() {
		return calendar.get(Calendar.HOUR_OF_DAY) + "";
	}

	public static String getMinute() {
		return calendar.get(Calendar.MINUTE) + "";
	}

	public static String getSecond() {
		return calendar.get(Calendar.SECOND) + "";
	}
	
	public static String getNumHoursAgoTime (int NUM) {
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - NUM);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return   df.format(calendar.getTime());
    }
	public static String getNumMinsAgoTime (int NUM) {
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - NUM);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return   df.format(calendar.getTime());
    }

}
