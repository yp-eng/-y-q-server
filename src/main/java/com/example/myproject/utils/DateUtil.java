package com.example.myproject.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("all")
public class DateUtil {

	public static final String YYYY_MM_DD_T_HH_MM_SS = "yyyy-MM-dd'T'HH:mm:ss";
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	/**
	 * @param week 0 本周 -1 上周 1 下周
	 * @return 周末作为 最后一天 。返回周末的日期
	 */
	public static String getEachWeekLastDay(int week) {
		SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.WEEK_OF_MONTH, week + 1);
		cal.set(Calendar.DAY_OF_WEEK, 1);
		return df.format(cal.getTime());
	}

	/**
	 * @param week 0 本周 -1 上周 1 下周
	 * @return 周一作为第一天，返回周一的日期
	 */
	public static String getEachWeekFirstDay(int week) {
		SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.WEEK_OF_MONTH, week);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return df.format(cal.getTime());
	}

	/**
	 * @param month  0本月 -1 上个月 1下个月
	 * @return
	 */
	public static String getFirstDayOfMonth(int month) {
		SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD);
		// 获取前月的第一天
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.add(Calendar.MONTH, month);
		cal_1.set(Calendar.DAY_OF_MONTH, 1);
		String firstDay = format.format(cal_1.getTime());
		return firstDay;
	}

	/**
	 * @param month 0 本月 -1 上个月 1下个月
	 * @return
	 */
	public static String getLastDayOfMonth(int month) {
		SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD);
		// 获取前月的最后一天
		Calendar cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, month + 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		String lastDay = format.format(cale.getTime());
		return lastDay;
	}

	/**
	 * 获取前几天 或者后几天
	 * @param day
	 * @return
	 */
	public static String getBeforeOrAfterDay(int day) {
		Date d = new Date();
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		SimpleDateFormat myFormatter = new SimpleDateFormat(YYYY_MM_DD);
		return myFormatter.format(now.getTime());
	}

	/**
	 * 获取前几天 或者后几天
	 * @param day
	 * @return
	 */
	public static Date getBeforeOrAfterDate(int day) {
		Date d = new Date();
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	public static String getBeforeOrAfterDay(int day, String format) {
		Date d = new Date();
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		SimpleDateFormat myFormatter = new SimpleDateFormat(format);
		return myFormatter.format(now.getTime());
	}


	public static String getDateByMillisecond(String second) {
		String date = "";
		Calendar calendar = Calendar.getInstance();
		long now = Long.parseLong(second);
		calendar.setTimeInMillis(now);
		DateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		date = formatter.format(calendar.getTime());
		return date;
	}

	public static long getMillisecondByDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		long millionSeconds = 0L;
		try {
			millionSeconds = sdf.parse(date).getTime();
		} catch (ParseException e) {
		}
		return millionSeconds;
	}

	public static long getMillisecondByDateString(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_T_HH_MM_SS);
		long millionSeconds = 0L;
		try {
			millionSeconds = sdf.parse(date).getTime();
		} catch (ParseException e) {
		}
		return millionSeconds;
	}

	/**
	 * 比较两个时间大小
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean compareDate(String date1, String date2) {
		long t1 = getMillisecondByDate(date1);
		long t2 = getMillisecondByDate(date2);
		return t1 - t2 > 0;
	}

	/**
	 * 时间加 day 天
	 *
	 * @throws Exception
	 */
	public static String addDay(String time, int day) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
			Date dt = sdf.parse(time);
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(dt);
			rightNow.add(Calendar.DAY_OF_YEAR, day);// 日期加10天
			Date dt1 = rightNow.getTime();
			return sdf.format(dt1);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

	}

	public static String addHour(String time, int hour) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
			Date dt = sdf.parse(time);
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(dt);
			rightNow.add(Calendar.HOUR, hour);
			Date dt1 = rightNow.getTime();
			return sdf.format(dt1);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 计算两个日期相差多少天
	 * @param dayMax
	 * @param dayMin
	 * @return
	 * @throws Exception
	 */
	public static int diffDay(String dayMax, String dayMin) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD);
			Date d1 = df.parse(dayMax);
			Date d2 = df.parse(dayMin);
			long diff = d1.getTime() - d2.getTime();
			int days = (int) (diff / (1000 * 60 * 60 * 24));
			return days +1 ;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}



	/**
	 * 计算两个日期相差多少天
	 *
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public static int diffDay(Date start, Date end) {
		long diff = end.getTime() - start.getTime();
		int days = (int) (diff / (1000 * 60 * 60 * 24));
		return days;
	}

	/**
	 * 计算两个日期相差多少秒
	 *
	 * @param time
	 * @param time2
	 * @return
	 * @throws Exception
	 */
	public static long diffSecond(String time, String time2) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
			Date d1 = df.parse(time);
			Date d2 = df.parse(time2);
			long diff = d1.getTime() - d2.getTime();
			return diff / 1000;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 得到某年某周的第一天
	 *
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getFirstDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);
		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);
		return getFirstDayOfWeek(cal.getTime());
	}

	/**
	 * 取得指定日期所在周的第一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c.getTime();
	}

	/**
	 * 得到某年某周的最后一天
	 *
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getLastDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);
		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);
		return getLastDayOfWeek(cal.getTime());
	}

	/**
	 * 取得指定日期所在周的最后一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		return c.getTime();
	}

	/**
	 * 日期转周
	 *
	 * @param str
	 *            2015-12-31
	 * @return
	 * @throws ParseException
	 */
	public static String getDayConvertWeek(String str) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD);
		Date date = format.parse(str);
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		String end = (calendar.get(Calendar.WEEK_OF_YEAR) - 1) + "";
		if (end.length() == 1) {
			end = "0" + end;
		}
		if (str.substring(5, 7).equals("12") && end.equals("00")) {
			return (Integer.parseInt(str.substring(0, 4)) + 1) + end;
		}

		return str.substring(0, 4) + end;
	}

	/**
	 * 获取当前时间
	 *
	 * @return
	 */
	public static String getCurrentDate() {
		DateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		return formatter.format(System.currentTimeMillis());
	}

	/**
	 * 获取当前时间
	 *
	 * @return
	 */
	public static String getCurrentDate(String format) {
		DateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(System.currentTimeMillis());
	}

	/**
	 * 获取当前时间
	 *
	 * @return
	 */
	public static String getCurrentDay() {
		DateFormat formatter = new SimpleDateFormat(YYYY_MM_DD);
		return formatter.format(System.currentTimeMillis());
	}

	/**
	 * 获取当前时间
	 *
	 * @return
	 */
	public static String getFormatDay(long time) {
		DateFormat formatter = new SimpleDateFormat(YYYY_MM_DD);
		return formatter.format(time);
	}

	/**
	 *
	 * @param date
	 * @param format "yyyy-MM-dd" | "yyyy-MM-dd HH:mm:ss" | "yyyy-MM-dd'T'HH:mm:ss"
	 * @return
	 */
	public static String getFormatDate(Date date,String format){
		DateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	/**
	 * long转换为 String yyyy-MM-dd HH:mm:ss
	 *
	 * @return String
	 */
	public static String getLongtoDate(long time) {
		Date date = new Date(time);
		SimpleDateFormat sd = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		return sd.format(date);
	}

	/**
	 *
	 * @param datestr
	 * @param format "yyyy-MM-dd" | "yyyy-MM-dd HH:mm:ss" | "yyyy-MM-dd'T'HH:mm:ss"
	 * @return
	 */
	public static Date parseDate(String datestr,String format) {
		SimpleDateFormat sd = new SimpleDateFormat(format);
		try {
			return sd.parse(datestr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 *
	 * 获取前几天 或者后几天
	 *
	 * @param day
	 * @return
	 */
	public static String getBeforeOrAfterDay2(int day) {
		Date d = new Date();
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyyMMdd");
		return myFormatter.format(now.getTime());
	}

	/**
	 *
	 * 获取前几天 或者后几天
	 *
	 * @param day
	 * @return
	 */
	public static String getBeforeOrAfterDay3(int day) {
		Date d = new Date();
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyyMMddhh");
		return myFormatter.format(now.getTime());
	}

	/**
	 * 获取当前时间所属月份的第一天
	 */
	public static String getFirstDayOfMonth(String time) {
		try {
			String result = "";
			if (time.length() == 10) {
				result = time.substring(0, 8);
				result += "01";
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<String> getLastMonth(int num){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String dateString = sdf.format(cal.getTime());
		List<String> rqList = new ArrayList<>();
		//System.out.println("倒序前\n");
		for (int i = 0; i < num; i++) {
			dateString = sdf.format(cal.getTime());
			//System.out.println("dateString" + dateString);
			rqList.add(dateString.substring(0, 7));
			cal.add(Calendar.MONTH, -1);
		}
		Collections.reverse(rqList);
		return  rqList;
		/*System.out.println("倒序后\n");
		for (int i = 0; i < rqList.size(); i++) {
			System.out.println("倒序后日期：" + rqList.get(i));
		}*/
	}

	public static List<String> getLastDay(int num){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = sdf.format(cal.getTime());
		List<String> rqList = new ArrayList<>();
		for (int i = 0; i < num; i++) {
			dateString = sdf.format(cal.getTime());
			rqList.add(dateString);
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		Collections.reverse(rqList);
		return  rqList;
	}

	/**
	 * 初始化日期数据
	 * @param days
	 * @return
	 */
	public static Map<String,String> getInitDaysMap(String dayMax, String dayMin){
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date dateMax = sdf.parse(dayMax);
			Map<String,String> map = new TreeMap<String,String>();
			int days = diffDay(dayMax,dayMin);
			for (int i=days;i>=1;i--){
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(dateMax);
				calendar.add(Calendar.DAY_OF_MONTH, -i+1);
				String dateStr = sdf.format(calendar.getTime());
				map.put(dateStr,dateStr);
			}
			return  map;
		}catch (Exception e){
			return new HashMap();
		}
	}

	/**
	 * 获取两个日期之间的日期
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return 日期集合
	 */
	public static List<String> getBetweenDates(Date start, Date end) {
		List<String> result = new ArrayList<String>();
		Calendar tempStart = Calendar.getInstance();
		tempStart.setTime(start);
		//tempStart.add(Calendar.DAY_OF_YEAR, 1); //包括开始 日期 ，需要注释这行
		Calendar tempEnd = Calendar.getInstance();
		tempEnd.setTime(end);
		while (tempStart.before(tempEnd)) { //包括 结束日期
			Date date = tempStart.getTime();
			String dateStr  = getFormatDate(date,"yyyy-MM-dd");
			result.add(dateStr);
			tempStart.add(Calendar.DAY_OF_YEAR, 1);
		}
		return result;
	}

	/**
	 * 取当月的日期集合
	 * @return 日期集合
	 */
	public static List<String> getThisMonthDay() throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar  cal_1 = Calendar.getInstance();//获取当前日期
		String today = format.format(cal_1.getTime());
		//获取前月的第一天
		cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
		String firstDay = format.format(cal_1.getTime());
		return getBetweenDates(format.parse(firstDay),format.parse(today));
	}

	public static void main(String[] args) throws ParseException {
		System.out.println(getLastDay(30));

		/*Date date = new Date(1490320800000L);
		// long time = new Date().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		System.out.println(sdf.format(date));
		String dd = "2017-03-26T00:04:03+08:00";
		System.out.println(getFormatDate(parseDate(dd, YYYY_MM_DD_T_HH_MM_SS),YYYY_MM_DD_HH_MM_SS));
		long time7 = sdf.parse("2017-03-24 10:00:00").getTime();
		long time8 = sdf.parse("2017-01-13 23:59:59").getTime();
		System.out.println("2017-02-04 00:00:00  " + time7);
		System.out.println("2017-02-04 23:59:59  " + time8);*/
	}
}
