package cn.com.dingduoduo.utils.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期工具类
 *
 * @author aidee-jyhe
 */
public class DateUtils {
	private static Logger logger = LoggerFactory.getLogger(DateUtils.class);

	/**
	 * 日期运算
	 *
	 * @param date
	 *            源
	 * @param part
	 *            操作部份
	 * @param value
	 *            改变值
	 * @return 计算后的日期
	 */
	public static Date add(Date date, int part, int value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(part, value);
		return calendar.getTime();
	}

	/**
	 * 日期运算
	 *
	 *            源
	 * @param part
	 *            操作部份
	 * @param value
	 *            改变值
	 * @return 计算后的日期
	 */
	public static long add(long start, int part, int value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(start);
		calendar.add(part, value);
		return calendar.getTimeInMillis();
	}

	/**
	 * 获得N日后的时间
	 * 
	 * @param date
	 *            源
	 * @param days
	 *            天数
	 * @return
	 */
	public static Date getAfterDay(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}

	/**
	 * 获得N日后的时间
	 * 
	 * @param date
	 *            源
	 * @param days
	 *            天数
	 * @return
	 */
	public static long getAfterDay(long date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date);
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTimeInMillis();
	}

	/**
	 * 取两个日期的差值
	 *
	 * @param from
	 *            开始时间
	 * @param to
	 *            结束时间
	 * @param part
	 *            Calendar.SECOND--相关多少秒,Calendar.MINUTE--相关多少分,Calendar.
	 *            HOUR_OF_DAY-时,other-天
	 * @return 差值
	 */
	public static long getDiff(Date from, Date to, int part) {
		if (to == null || from == null)
			return 0;
		return getDiff(from.getTime(), to.getTime(), part);
	}

	/**
	 * 取两个时间戳的差值
	 * 
	 * @param fromTimeStamp
	 *            开始时间
	 * @param toTimeStamp
	 *            结束时间
	 * @param part
	 *            Calendar.SECOND--相关多少秒,Calendar.MINUTE--相关多少分,Calendar.
	 *            HOUR_OF_DAY-时,other-天
	 * @return
	 */
	public static long getDiff(long fromTimeStamp, long toTimeStamp, int part) {
		long d = toTimeStamp - fromTimeStamp;
		switch (part) {
		case Calendar.SECOND:
			return d / 1000;
		case Calendar.MINUTE:
			return d / 1000 / 60;
		case Calendar.HOUR_OF_DAY:
			return d / 1000 / 60 / 60;
		default:
			return d / 1000 / 60 / 60 / 24;
		}
	}

	/**
	 * 日期格式化函数,格式：yyyy-MM-dd HH:mm:ss
	 *
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss", null);
	}

	/**
	 * 日期格式化函数,缺省时区
	 *
	 * @param date
	 * @param format
	 * @return
	 */
	public static String format(Date date, String format) {
		return format(date, format, null);
	}

	/**
	 * 日期格式化函数
	 *
	 * @param date
	 * @param format
	 * @param timeZone
	 *            时区如东八区GMT+8
	 * @return
	 */
	public static String format(Date date, String format, String timeZone) {
		if (date == null)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		if (timeZone != null && !timeZone.trim().equals(""))
			formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
		return formatter.format(date);
	}

	/**
	 * YYYY年MM月dd日 HH:mm
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateInChinese(Date date) {
		return DateUtils.format(date, "YYYY年MM月dd日") + " " + DateUtils.getWeekOfDate(date) + " "
				+ DateUtils.format(date, "HH:mm");
	}

	/**
	 * 字符转换为日期。
	 *
	 * @param source
	 * @return
	 */
	public static Date stringToDate(String source) {
		return stringToDate(source, "yyyy-MM-dd HH:mm:ss", null);
	}

	/**
	 * 字符转换为日期。
	 *
	 * @param source
	 * @param pattern
	 *            日期格式串如yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date stringToDate(String source, String pattern) {
		return stringToDate(source, pattern, null);
	}

	/**
	 * 字符串转换为指定时区时间
	 *
	 * @param value
	 * @param pattern
	 *            如yyyy-MM-dd HH:mm:ss
	 * @param timeZone
	 *            如东八区GMT +8
	 * @return
	 */
	public static Date stringToDate(String value, String pattern, String timeZone) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date = null;
		if (value == null || "".equals(value))
			return date;
		if (timeZone != null && !timeZone.trim().equals(""))
			format.setTimeZone(TimeZone.getTimeZone(timeZone));
		try {
			date = format.parse(value);
		} catch (java.text.ParseException e) {
			logger.warn("Parse string {} to date {} is failed", value, pattern, e);
		}
		return date;
	}

	/**
	 * 显示时间，如果与当前时间差别小于一天，则自动用**秒(分，小时)前，如果大于一天则用format规定的格式显示
	 *
	 * @param ctime
	 *            时间
	 */
	public static String showTime(Date ctime) {
		return showTime(ctime, null);
	}

	/**
	 * 显示时间，如果与当前时间差别小于一天，则自动用**秒(分，小时)前，如果大于一天则用format规定的格式显示
	 *
	 * @param ctime
	 *            时间
	 * @param format
	 *            格式描述:例如:yyyy-MM-dd yyyy-MM-dd HH:mm:ss
	 */
	public static String showTime(Date ctime, String format) {
		String r = "";
		if (ctime == null)
			return r;
		if (format == null)
			format = "yyyy-MM-dd HH:mm";
		long result = getDiff(ctime, new Date(), Calendar.SECOND);
		if (result < 60) {
			r = result + "秒前";
		} else if (result >= 60 && result < 3600) {
			long seconds = result / 60;
			r = seconds + "分钟前";
		} else if (result >= 3600 && result < 86400) {
			long seconds = result / 3600;
			r = seconds + "小时前";
		} else {
			r = format(ctime, format);
		}
		return r;
	}

	/**
	 * 获取当前日期是一个星期的第几天 <br>
	 * 星期天是0
	 */
	public static int getDayOfWeek(Date time) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return w;
	}

	/**
	 * 获取当前日期是星期几
	 *
	 * @return
	 */
	public static String getWeekOfDate(Date time) {
		if (time == null)
			return "";
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		return weekDays[getDayOfWeek(time)];
	}

	/**
	 * 获取当前日期是星期几
	 *
	 * @return
	 */
	public static String getWeekOfDate2(Date time) {
		if (time == null)
			return "";
		String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		return weekDays[getDayOfWeek(time)];
	}

	/**
	 * <pre>
	 * 两个日期相差的天数,注意是"相隔"还是"相差",例如2017-04-09 23:59:59与2017-04-10 00:00:00.相隔1天(relateHMS为false),相差0天(relateHMS为true)
	 * </pre>
	 *
	 * @param from
	 *            开始日期
	 * @param to
	 *            结束日期
	 * @param relateHMS
	 *            是否关联时分秒
	 * @return
	 */
	public static int getDayDiff(Date from, Date to, boolean relateHMS) {
		String formatPattern = "yyyy-MM-dd";
		long fromTimestamp;
		long toTimestamp;
		if (relateHMS) {
			fromTimestamp = from.getTime();
			toTimestamp = to.getTime();
		} else {
			fromTimestamp = stringToDate(format(from, formatPattern), formatPattern).getTime();
			toTimestamp = stringToDate(format(to, formatPattern), formatPattern).getTime();
		}

		return (int) ((toTimestamp - fromTimestamp) / (1000 * 60 * 60 * 24));
	}

	/**
	 * <pre>
	 * 两个日期相差的天数,注意是"相隔"还是"相差",例如2017-04-09 23:59:59与2017-04-10 00:00:00.相隔1天(relateHMS为false),相差0天(relateHMS为true)
	 * </pre>
	 *
	 * @param from
	 *            开始日期(yyyy-MM-dd HH:mm:ss格式)
	 * @param to
	 *            结束日期
	 * @param relateHMS
	 *            是否关联时分秒
	 * @return
	 */
	public static int getDayDiff(String from, String to, boolean relateHMS) {
		String formatPattern = "yyyy-MM-dd HH:mm:ss";
		Date fromDate = stringToDate(from, formatPattern);
		Date toDate = stringToDate(to, formatPattern);
		return getDayDiff(fromDate, toDate, relateHMS);
	}

	/**
	 * 得到两日期相差几个月
	 */
	public static int getMonthDiff(String startDate, String endDate) {
		Date startDate1 = stringToDate(startDate, "yyyy-MM-dd");
		Date endDate1 = stringToDate(endDate, "yyyy-MM-dd");
		return getMonthDiff(startDate1, endDate1);
	}

	/**
	 * 得到两日期相差几个月
	 */
	public static int getMonthDiff(Date startDate, Date endDate) {
		Calendar starCal = Calendar.getInstance();
		starCal.setTime(startDate);
		int sYear = starCal.get(Calendar.YEAR);
		int sMonth = starCal.get(Calendar.MONTH);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);
		int eYear = endCal.get(Calendar.YEAR);
		int eMonth = endCal.get(Calendar.MONTH);

		return ((eYear - sYear) * 12 + (eMonth - sMonth));
	}

	public static int getAgeByBirthday(Date birthday) {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthday)) {
			throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthday);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		}
		return age;
	}

	/**
	 * 获得当天的23:59:59
	 * 
	 * @author xiaqiang
	 * @createtime 2015年6月4日 下午11:51:18
	 *
	 * @param c
	 * @return
	 */
	public static Calendar getLastTimeForADay(Calendar c) {
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 23, 59, 59);
		return c;
	}

	/**
	 * 获得当天的23:59:59
	 * 
	 * @author xiaqiang
	 * @createtime 2015年6月4日 下午11:51:18
	 *
	 * @param date
	 * @return
	 */
	public static Date getLastTimeForADay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 23, 59, 59);
		return cal.getTime();
	}

	/**
	 * 获得当天的23:59:59
	 * 
	 * @author xiaqiang
	 * @createtime 2015年6月4日 下午11:51:18
	 *
	 * @param date
	 * @return
	 */
	public static long getLastTimeForADay(long timestamp) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 23, 59, 59);
		return cal.getTimeInMillis();
	}

	/**
	 * 当前日历，这里用中国时间表示
	 * 
	 * @return 以当地时区表示的系统当前日历
	 */
	public static Calendar getCalendar() {
		return Calendar.getInstance();
	}

	/**
	 * 指定Date获得Calendar
	 * 
	 * @return 以当地时区表示的系统当前日历
	 */
	public static Calendar getCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public static Calendar setStartDay(Calendar cal) {
		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	public static Calendar setISO8601StartDay(Calendar cal) {
		cal.set(11, 8);
		cal.set(12, 0);
		cal.set(13, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	public static Calendar setEndDay(Calendar cal) {
		cal.set(11, 23);
		cal.set(12, 59);
		cal.set(13, 59);
		return cal;
	}

	/**
	 * 获得今天的00:00:00 时间
	 * 
	 * @return
	 */
	public static Calendar getTodayStartTime() {
		Calendar c = getCalendar();
		c = setStartDay(c);
		return c;
	}

	/**
	 * 获得当天ISO8601的00:00:00 时间
	 * 
	 * @return
	 */
	public static Calendar getISO8601TodayStartTime(Calendar c) {
		c = setISO8601StartDay(c);
		return c;
	}

	/**
	 * 获得当天ISO8601的00:00:00 时间
	 * 
	 * @return
	 */
	public static Calendar getISO8601TodayStartTime(Date date) {
		return setISO8601StartDay(getCalendar(date));
	}

	/**
	 * 获得当天的00:00:00 时间
	 * 
	 * @return
	 */
	public static Calendar getTodayStartTime(Calendar c) {
		c = setStartDay(c);
		return c;
	}

	/**
	 * 获得当天的00:00:00 时间
	 * 
	 * @return
	 */
	public static Calendar getTodayStartTime(Date date) {
		Calendar c = setStartDay(getCalendar(date));
		return c;
	}

	/**
	 * 获得今天的23:59:59 时间
	 * 
	 * @return
	 */
	public static Calendar getTodayLastTime() {
		Calendar c = getCalendar();
		c = setEndDay(c);
		return c;
	}

	/**
	 * 获得今天的23:59:59 时间
	 * 
	 * @return
	 */
	public static Calendar getTodayLastTime(Calendar c) {
		c = setEndDay(c);
		return c;
	}

	/**
	 * 获得今天的23:59:59 时间
	 * 
	 * @return
	 */
	public static Calendar getTodayLastTime(Date date) {
		Calendar c = getCalendar(date);
		c = setEndDay(c);
		return c;
	}

	/**
	 * 是否同一天
	 * 
	 * @param day1
	 * @param day2
	 * @return
	 */
	public static boolean isSameDay(Date day1, Date day2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String ds1 = sdf.format(day1);
		String ds2 = sdf.format(day2);
		if (ds1.equals(ds2)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 给定calendar，返回当月的第一天的第0秒，eg: 2016-07-01 00:00:00 注意calendar类的月份是0-based
	 * 
	 * @param cal
	 * @return
	 */
	public static Date getBeginningTimeOfMonth(final Calendar cal) {
		return getBeginningTimeOfMonth(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
	}

	/**
	 * 给定年月，返回当月的第一天的第0秒，eg: 2016-07-01 00:00:00
	 * 
	 * @param year
	 *            month
	 * @return
	 */
	public static Date getBeginningTimeOfMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);

		return c.getTime();
	}

	/**
	 * 返回当月最后一天的日期
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = convert(date);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	/**
	 * 返回当月第一天的日期
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar calendar = convert(date);
		calendar.set(Calendar.DATE, calendar.getMinimum(Calendar.DATE));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 将日期转换为日历
	 * 
	 * @param date
	 *            日期
	 * @return 日历
	 */
	private static Calendar convert(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * 验证一个字符串是否为日期
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isValidDate(String s) {
		try {
			// 指定日期格式为四位年/两位月份/两位日期，注意yyyy-MM-dd其中MM为大写
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
			// 设置lenient为false.
			// 否则SimpleDateFormat会比较宽松地验证日期，比如2004/02/29会被接受，并转换成2004/03/01
			dateFormat.setLenient(false);
			dateFormat.parse(s);
			return true;
		} catch (Exception e) {
			logger.warn("Valid date {} is failed", s, e);
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}

	/**
	 * 10位时间戳转Date
	 * 
	 * @param tenTimestamp
	 * @return
	 */
	public static Date toDate(long tenTimestamp) {
		long temp = tenTimestamp * 1000l;
		return new Date(temp);
	}
}
