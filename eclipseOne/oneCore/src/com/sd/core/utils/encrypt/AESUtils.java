package com.sd.core.utils.encrypt;

import android.annotation.SuppressLint;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * [AES加解密算法]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-11-14
 * 
 **/
public class AESUtils {

	// 配置信息
	private static String WAYS = "AES";
	private static String MODE = "";
	private static String IV = "1234567890123456";
	@SuppressWarnings("unused")
	private static int AES_SIZE = 128;
	@SuppressWarnings("unused")
	private static int pwdLength = 16;
	private static String defaultPwd = null;
	private static boolean isPwd = false;
	private static String ModeCode = "PKCS5Padding";
	private static int type = 0;// 默认

	public static String selectMod(int type) {
		// ECB("ECB", "0"), CBC("CBC", "1"), CFB("CFB", "2"), OFB("OFB", "3");
		switch (type) {
		case 0:
			isPwd = false;
			MODE = WAYS + "/" + AESType.ECB.key() + "/" + ModeCode;

			break;
		case 1:
			isPwd = true;
			MODE = WAYS + "/" + AESType.CBC.key() + "/" + ModeCode;
			break;
		case 2:
			isPwd = true;
			MODE = WAYS + "/" + AESType.CFB.key() + "/" + ModeCode;
			break;
		case 3:
			isPwd = true;
			MODE = WAYS + "/" + AESType.OFB.key() + "/" + ModeCode;
			break;

		}

		return MODE;

	}

	/**
	 * 生成一个固定密钥
	 * 
	 * @param password
	 *            长度必须是16的倍数
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@SuppressLint("TrulyRandom") 
	private static SecretKeySpec toKey(String password)
			throws UnsupportedEncodingException {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(WAYS);

			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();

			System.out.println(new String(enCodeFormat).getBytes("UTF-8"));
			return new SecretKeySpec(enCodeFormat, WAYS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 加密
	public static String Encrypt(String content) throws Exception {

		SecretKeySpec skeySpec = toKey(defaultPwd);
		Cipher cipher = Cipher.getInstance(selectMod(type));// "算法/模式/补码方式"//
		IvParameterSpec iv = new IvParameterSpec(IV.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		if (isPwd == false) {
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		} else {
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		}

		byte[] encrypted = cipher.doFinal(content.getBytes());

		return Base64.encode(new String(encrypted));// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
	}

	// 解密
	public static String Decrypt(String content) throws Exception {
		try {

			SecretKeySpec skeySpec = toKey(defaultPwd);
			Cipher cipher = Cipher.getInstance(selectMod(type));
			IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
			if (isPwd == false) {
				cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			} else {
				cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			}

			byte[] encrypted1 = Base64.decode(content, "UTF-8").getBytes();
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original);
				return originalString;
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	/**
	 * 将二进制转换成十六进制
	 * 
	 * @param buf
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将十六进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	@SuppressWarnings("unused")
	private static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1) {
			return null;
		} else {
			byte[] result = new byte[hexStr.length() / 2];
			for (int i = 0; i < hexStr.length() / 2; i++) {
				int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1),
						16);
				int low = Integer.parseInt(
						hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
				result[i] = (byte) (high * 16 + low);
			}
			return result;
		}
	}

	public static void main(String[] args) throws Exception {
		/*
		 * 加密用的Key 可以用26个字母和数字组成，最好不要用保留字符，虽然不会错，至于怎么裁决，个人看情况而定
		 * 此处使用AES-128-CBC加密模式，key需要为16位。
		 */
		isPwd = false;
		defaultPwd = "admin";
		String resultString = AESUtils.Encrypt("HelloWrod");

		System.out.println(resultString);
		System.out.println(AESUtils.Decrypt(resultString));
	}
}