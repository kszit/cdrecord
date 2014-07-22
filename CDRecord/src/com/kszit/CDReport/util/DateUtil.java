package com.kszit.CDReport.util;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {

	/**
	 * 日期增加N天后的日期
	 * 
	 * @param d
	 * @param addDates
	 * @return
	 */
	public static Date addDates(Date d, int addDates) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.DATE, addDates);
		cal.getTime();
		Date return_date = new Date(cal.getTimeInMillis());
		return return_date;
	}
	/**
	 * 日期增加N天后的日期
	 * 
	 * @param d
	 * @param addDates
	 * @return
	 */
	public static java.util.Date addDates(java.util.Date d, int addDates,String temp) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.DATE, addDates);
		cal.getTime();
		Date return_date = new Date(cal.getTimeInMillis());
		return return_date;
	}
	/**
	 * 当前年份
	 * 
	 * @return
	 */
	public static String currentYear() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		return cal.get(Calendar.YEAR) + "";
	}

	/**
	 * 当前月份
	 * 
	 * @return
	 */
	public static String currentMonth() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		return (cal.get(Calendar.MARCH) + 1) + "";
	}

	/**
	 * 返回年份
	 * 
	 * @param date
	 *            日期
	 * @return 返回年份
	 */
	public static int getYear(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.YEAR);
	}

	/**
	 * 返回月份
	 * 
	 * @param date
	 *            日期
	 * @return 返回月份
	 */
	public static int getMonth(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.MONTH) + 1;
	}

	/**
	 * 返回日份
	 * 
	 * @param date
	 *            日期
	 * @return 返回日份
	 */
	public static int getDay(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 
	* getDatyByStr("yyyy-MM-dd" 格式的字符串返回日期) <br> 
	* TODO(这里描述这个方法适用条件 – 无)  <br>
	* TODO(这里描述这个方法的执行流程 – 无)  <br>
	* TODO(这里描述这个方法的使用方法 – 无)  <br>
	* TODO(这里描述这个方法的注意事项 – 无)  <br>
	* @param name  
	* @param @return 设定文件  - 无
	* @return String DOM对象  - 无
	* @Exception 异常对象  - 无
	* @since  -
	 */
	public static java.util.Date getDatyByStr(String dateStr){
			DateFormat dd=new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date=null;
			try {
				date = dd.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date;
	}
	/**
	 * 
	* getDatesOfYear(  日期在当前年份中的第几天（天数）) <br> 
	* TODO(这里描述这个方法适用条件 – 无)  <br>
	* TODO(这里描述这个方法的执行流程 – 无)  <br>
	* TODO(这里描述这个方法的使用方法 – 无)  <br>
	* TODO(这里描述这个方法的注意事项 – 无)  <br>
	* @param name  
	* @param @return 设定文件  - 无
	* @return String DOM对象  - 无
	* @Exception 异常对象  - 无
	* @since  -
	 */
	public static int getDatesOfYear(String datestr){
	    java.util.Date date = getDatyByStr(datestr);
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    return cal.get(Calendar.DAY_OF_YEAR);
	}
	
	public static String formatDate(java.util.Date  date){
		String fmt = "";
		fmt = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		String dateStr = sdf.format(date);
		return dateStr;
	}
	
	public static String getDateByDaysOfYear(String year,int days){
		java.util.Date firstDayOfYear = getDatyByStr(year+"-01-01");
		 java.util.Date date = addDates(firstDayOfYear,days,"");
		 return formatDate(date);
	}

	public static void main(String[] a) {
		System.out.println(getDatesOfYear("2012-02-01"));
	}

}
