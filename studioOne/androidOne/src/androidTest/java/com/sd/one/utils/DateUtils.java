/*
    ShengDao Android Client, DateUtils
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.utils;

import hirondelle.date4j.DateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.content.Context;

import com.sd.core.common.PreferencesManager;
import com.sd.core.utils.NLog;

/**
 * [时间工具类, 使用的date4j包来build各种时间处理的方法]
 * 
 * @author devin.hu
 * @version 1.0
 * @date 2014-2-25
 * 
 **/
public class DateUtils {

	private static final String tag = DateUtils.class.getSimpleName();
	public static final String UPDATETIME = "updatetime";

	/**
	 * 获取当前时间
	 * @return
	 */
	public static String currentDateTime() {
		return currentDateTime(DateStyle.YYYY_MM_DD);
	}
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public static String currentDateTime(String dateStyle) {
		DateTime now = DateTime.now(TimeZone.getDefault());
		String result = now.format(dateStyle);
		NLog.e(tag, "currentDateTime: " + result);
		return result;
	}

	/** How many days till the next December 25? */
	public static void daysTillChristmas() {
		DateTime today = DateTime.today(TimeZone.getDefault());
		DateTime christmas = DateTime.forDateOnly(today.getYear(), 12, 25);
		int result = 0;
		if (today.isSameDayAs(christmas)) {
			// do nothing
		} else if (today.lt(christmas)) {
			result = today.numDaysFrom(christmas);
		} else if (today.gt(christmas)) {
			DateTime christmasNextYear = DateTime.forDateOnly(today.getYear() + 1, 12, 25);
			result = today.numDaysFrom(christmasNextYear);
		}
		NLog.e(tag, "daysTillChristmas: " + result);
	}

	/**
	 * 从今天起加plusDay天的日期
	 * @param plusDay
	 */
	public static String plusDayFromToday(int plusDay) {
		DateTime today = DateTime.today(TimeZone.getDefault());
		return today.plusDays(plusDay).format(DateStyle.YYYY_MM_DD);
	}

	/**
	 * 从今天起加plusMonth月，plusDay天后是哪一天
	 * @param plusMonth
	 * @param plusDay
	 * @return
	 */
	public static String plusMonthDayFromToday(int plusMonth, int plusDay) {
		DateTime today = DateTime.today(TimeZone.getDefault());
		DateTime result = today.plus(0, plusMonth, plusDay, 0, 0, 0, 0, DateTime.DayOverflow.FirstDay);
		return result.format("YYYY-MM-DD");
	}

	/**
	 * Current number of hours difference between Paris, France and Perth,
	 * Australia.
	 */
	public static void hoursDifferenceBetweenParisAndPerth() {
		// this assumes the time diff is a whole number of hours; other styles
		// are possible
		DateTime paris = DateTime.now(TimeZone.getTimeZone("Europe/Paris"));
		DateTime perth = DateTime.now(TimeZone.getTimeZone("Australia/Perth"));
		int result = perth.getHour() - paris.getHour();
		if (result < 0) {
			result = result + 24;
		}
		//log("Numbers of hours difference between Paris and Perth : " + result);
	}

	/** How many weeks is it since Sep 6, 2010? */
	public static void weeksSinceStart() {
		DateTime today = DateTime.today(TimeZone.getDefault());
		DateTime startOfProject = DateTime.forDateOnly(2010, 9, 6);
		int result = today.getWeekIndex() - startOfProject.getWeekIndex();
		NLog.e(tag, "weeksSinceStart: " + result);
	}

	/** How much time till midnight? */
	public static void timeTillMidnight() {
		DateTime now = DateTime.now(TimeZone.getDefault());
		DateTime midnight = now.plusDays(1).getStartOfDay();
		long result = now.numSecondsFrom(midnight);
		NLog.e(tag, "timeTillMidnight: " + result);
	}

	/** Format using ISO style. */
	public static void imitateISOFormat() {
		DateTime now = DateTime.now(TimeZone.getDefault());
		NLog.e(tag, "imitateISOFormat: " + now.format("YYYY-MM-DDThh:mm:ss"));
	}

	public static void firstDayOfThisWeek() {
		DateTime today = DateTime.today(TimeZone.getDefault());
		DateTime firstDayThisWeek = today; // start value
		int todaysWeekday = today.getWeekDay();
		int SUNDAY = 1;
		if (todaysWeekday > SUNDAY) {
			int numDaysFromSunday = todaysWeekday - SUNDAY;
			firstDayThisWeek = today.minusDays(numDaysFromSunday);
		}
		NLog.e(tag, "firstDayOfThisWeek: " + firstDayThisWeek);
	}

	/** For how many years has the JDK date-time API been suctorial? */
	public static void jdkDatesSuctorial() {
		DateTime today = DateTime.today(TimeZone.getDefault());
		DateTime jdkFirstPublished = DateTime.forDateOnly(1996, 1, 23);
		int result = today.getYear() - jdkFirstPublished.getYear();
		NLog.e(tag, "jdkDatesSuctorial: " + result);
	}

	/**
	 * 得到更新的时间
	 * 
	 * @param context
	 * @param requestCode
	 * @return
	 */
	public static String getUpdateTime(Context context, int requestCode) {
		DateTime now = DateTime.now(TimeZone.getDefault());
		String result = now.format("YYYY-MM-DD hh:mm:ss");
		PreferencesManager sharePFMgr = PreferencesManager.getInstance(context);
		String time = sharePFMgr.get(String.valueOf(requestCode) + UPDATETIME, result);
		NLog.e(tag, "getUpdateTime: " + time);
		return time;
	}

	/**
	 * 得到更新的时间
	 * 
	 * @param context
	 * @param requestCode
	 * @return
	 */
	public static void setUpdateTime(Context context, int requestCode) {
		DateTime now = DateTime.now(TimeZone.getDefault());
		String result = now.format("YYYY-MM-DD hh:mm:ss");
		PreferencesManager sharePFMgr = PreferencesManager.getInstance(context);
		sharePFMgr.put(String.valueOf(requestCode) + UPDATETIME, result);
		NLog.e(tag, "setUpdateTime: " + result);
	}
	
	/**
	 * 根据 timestamp 生成各类时间状态串
	 * 
	 * @param timestamp 距1970 00:00:00 GMT的秒数
	 * @param format 格式
	 * @return 时间状态串(如：刚刚5分钟前)
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getTimeState(String timestamp, String format){
		
		if (timestamp == null || "".equals(timestamp)){
			return "";
		}

		try{
			timestamp = formatTimestamp(timestamp);
			long _timestamp = Long.parseLong(timestamp);
			
			if (System.currentTimeMillis() - _timestamp < 1 * 60 * 1000){
				return "刚刚";
			} else if (System.currentTimeMillis() - _timestamp < 30 * 60 * 1000){
				return ((System.currentTimeMillis() - _timestamp) / 1000 / 60) + "分钟前";
			} else{
				
				Calendar now = Calendar.getInstance();
				Calendar c = Calendar.getInstance();
				c.setTimeInMillis(_timestamp);
				
				if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR) 
						&& c.get(Calendar.MONTH) == now.get(Calendar.MONTH) 
						&& c.get(Calendar.DATE) == now.get(Calendar.DATE)){
					SimpleDateFormat sdf = new SimpleDateFormat("今天 HH:mm");
					return sdf.format(c.getTime());
				}
				
				if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)
						&& c.get(Calendar.MONTH) == now.get(Calendar.MONTH)
						&& c.get(Calendar.DATE) == now.get(Calendar.DATE) - 1){
					SimpleDateFormat sdf = new SimpleDateFormat("昨天 HH:mm");
					return sdf.format(c.getTime());
				} 
				
				else if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)){
					SimpleDateFormat sdf = null;
					if (format != null && !format.equalsIgnoreCase("")){
						sdf = new SimpleDateFormat(format);
					} else{
						sdf = new SimpleDateFormat("M月d日 HH:mm:ss");
					}
					return sdf.format(c.getTime());
				} 
				
				else{
					SimpleDateFormat sdf = null;
					if (format != null && !format.equalsIgnoreCase("")){
						sdf = new SimpleDateFormat(format);
					} else{
						sdf = new SimpleDateFormat("yyyy年M月d日 HH:mm:ss");
					}
					return sdf.format(c.getTime());
				}
			}
		} catch (Exception e){
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 对时间戳格式进行格式化，保证时间戳长度为13位
	 * 
	 * @param timestamp 时间戳
	 * @return 返回为13位的时间戳
	 */
	public static String formatTimestamp(String timestamp){
		if (timestamp == null || "".equals(timestamp)){
			return "";
		}
		
		String tempTimeStamp = timestamp + "00000000000000";
		StringBuffer stringBuffer = new StringBuffer(tempTimeStamp);
		return tempTimeStamp = stringBuffer.substring(0, 13);
	}
}
