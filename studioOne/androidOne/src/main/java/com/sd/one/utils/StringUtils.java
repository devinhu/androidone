/*
    ShengDao Android Client, StringUtils
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.net.Uri;
import android.text.TextUtils;

import com.sd.core.utils.NLog;
import com.sd.core.utils.encrypt.MD5;

/**
 * [字符串工具类]
 * 
 * @author devin.hu
 * @version 1.0
 * @date 2014-2-25
 * 
 **/
public class StringUtils {

	private static final String tag = StringUtils.class.getSimpleName();
	
	/**
	 * 判断字符不能为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(TextUtils.isEmpty(str) || "".equals(str)){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断数组不能为空
	 * @param list
	 * @return
	 */
	public static <T> boolean isEmpty(List<T> list){
		if(list == null || list.isEmpty()){
			return true;
		}
		return false;
	}

	/**
	 * 二行制转字符串
	 */
	public static String byte2hex(byte[] b) {
		StringBuffer hs = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs.append("0").append(stmp);
			else
				hs.append(stmp);
		}
		return hs.toString();
	}

	/**
	 * 产生一个随机数
	 * @param size
	 * @return
	 */
	public static int getRandomIndex(int size){
		return (int)(Math.random()*size);
	}
	
    /**
	 * 获取字符串的长度，如果有中文，则每个中文字符计为2位
	 * 
	 * @param value 指定的字符串
	 * @return 字符串的长度
	 */
    public static int getStrlength(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
			String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese)) {
				/* 中文字符长度为2 */
				valueLength += 2;
			} else {
				/* 其他字符长度为1 */
				valueLength += 1;
			}
		}
		NLog.e(tag, "getStrlength : " + valueLength);
		return valueLength;
	}
    
    /**
     * 判断字符串是否全是数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher match = pattern.matcher(str);
		return match.matches();
	}
	
	/**
	 * 验证是否是邮箱格式
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}
	
	/**
	 * 验证手机号码格式
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobile(String mobile) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}
	
	/**
	 * 判断输入的字符串是否为纯汉字
	 * 
	 * @param str 传入的字符窜
	 * @return 如果是纯汉字返回true,否则返回false
	 */
	public static boolean isChinese(String str){
		Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 生成图片唯一名字
	 * 
	 * @param uri
	 * @return
	 */
	public static String getImageName(Uri uri) {
		String path = String.valueOf(System.currentTimeMillis());
		if (uri != null) {
			path = path + uri.getPath().toString();
		}
		path = MD5.encrypt(path) + ".jpg";
		return path;
	}
}
