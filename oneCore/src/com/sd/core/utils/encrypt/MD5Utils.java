package com.sd.core.utils.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.sd.core.utils.NLog;

/**
 * [A brief description]
 * 
 * @author devin.hu
 * @version 1.0
 * @date 2014-2-25
 * 
 **/
public class MD5Utils {

	private static final String tag = MD5Utils.class.getSimpleName();
	
	/**
	 * MD5加密
	 * @param value
	 * @return
	 */
	public static String encrypt(String value) {
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			byte[] e = md.digest(value.getBytes());
			return toHex(e);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return value;
		}
	}

	/**
	 * MD5加密
	 * @param bytes
	 * @return
	 */
	public static String encrypt(byte[] bytes) {
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			byte[] e = md.digest(bytes);
			return toHex(e);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * to Hex
	 * @param bytes
	 * @return
	 */
	private static String toHex(byte bytes[]) {
		StringBuilder hs = new StringBuilder();
		String stmp = "";
		for (int n = 0; n < bytes.length; n++) {
			stmp = Integer.toHexString(bytes[n] & 0xff);
			if (stmp.length() == 1)
				hs.append("0").append(stmp);
			else
				hs.append(stmp);
		}
		return hs.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NLog.e(tag, MD5Utils.encrypt("ssssssss"));
	}

}
