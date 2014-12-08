/*
    ShengDao Android Client, CacheManager
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.core.common;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

import android.os.Environment;
import android.text.TextUtils;

import com.sd.core.utils.NLog;

/**
 * 序列化缓存类，该类主要提供了序列化缓存数据，读取数据，缓存有效性检查，清除缓存方法；
 * 使用该类中的对象必须实现序列化接口，且必须在Application中初始化sysCachePath缓存路径。
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-3-5
 * 
 **/
public class CacheManager {

	/** 日志对象 **/
	private static final String tag = CacheManager.class.getSimpleName();
	/**
	 * 缓存路径, 建议在BaseApplication中设置 建议设置为getCacheDir().getPath()
	 **/
	private static String sysCachePath;

	public static String getSysCachePath() {
		return sysCachePath;
	}

	public static void setSysCachePath(String sysCachePath) {
		CacheManager.sysCachePath = sysCachePath;
	}

	/**
	 * 在debug模式下保存测试数据到磁盘上，路径为SD卡下面的testData文件
	 * 
	 * @param xmlResult
	 * @param fileName
	 */
	public static <T> void saveTestData(String xmlResult, String fileName) {
		try {
			String state = Environment.getExternalStorageState();
			// 判断如果有SD卡且是Debug模式
			if (Environment.MEDIA_MOUNTED.equals(state) && NLog.isDebug()) {
				String sdCardPath = Environment.getExternalStorageDirectory()
						.getPath();
				StringBuilder path = new StringBuilder(sdCardPath).append(
						File.separator).append("testData");

				File file = new File(path.toString());
				if (!file.exists()) {
					file.mkdirs();
				}

				path = path.append(File.separator).append(fileName)
						.append(".txt");
				file = new File(path.toString());

				FileOutputStream output = new FileOutputStream(file);
				BufferedOutputStream os = new BufferedOutputStream(output);
				os.write(xmlResult.getBytes());
				os.flush();
				os.close();

				NLog.e(tag, "saveTestData success: " + path);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存缓存对象
	 * 
	 * @param object
	 *            缓存对象
	 * @param key
	 *            对应的key
	 * @return true表示成功，false表示失败
	 */
	public static <T> boolean writeObject(Object object, String key) {
		try {
			String path = getCachePath(key);
			FileOutputStream fos = new FileOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(object);
			oos.flush();
			oos.close();

			// update LastModified
			File file = new File(path);
			if (file.exists()) {
				file.setLastModified(System.currentTimeMillis());
				NLog.e(tag, "writeObject object success : " + path);
				return true;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 根据key获取缓存对象
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T readObject(String key) {
		T obj = null;
		try {
			String cachePath = getCachePath(key);
			File file = new File(cachePath);
			if (file.exists()) {
				FileInputStream fis = new FileInputStream(cachePath);
				ObjectInputStream ois = new ObjectInputStream(fis);
				obj = (T) ois.readObject();
				ois.close();
			}
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (OptionalDataException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 检查缓存是否有效
	 * 
	 * @param key
	 *            缓存key
	 * @param timeout
	 *            超时时间
	 * @return true表示有效，false表示无锡
	 */
	public static boolean isInvalidCache(String key, long timeout) {
		String path = getCachePath(key);
		File file = new File(path);
		if (file.exists()) {
			long last = file.lastModified();
			long current = System.currentTimeMillis();
			if (current - last < timeout * 1000) {
				NLog.e(tag, "the cahce is effect : " + path);
				return true;
			}
		}
		NLog.e(tag, "the cahce is invalid : " + path);
		return false;
	}

	/**
	 * 根据key得到缓存路径
	 * 
	 * @param key
	 * @return
	 */
	public static <T> String getCachePath(String key) {
		if (TextUtils.isEmpty(sysCachePath)) {
			throw new IllegalArgumentException(
					"CacheManager sysCachePath is not null.");
		}
		StringBuilder path = new StringBuilder();
		path.append(sysCachePath);
		path.append(File.separator);
		path.append(key);
		return path.toString();
	}

	/**
	 * 根据key清除对应缓存
	 * 
	 * @param <T>
	 * @return boolean
	 */
	public static <T> boolean clearCache(String key) {
		File file = new File(getCachePath(key));
		if (file.exists()) {
			return file.delete();
		}
		return false;
	}

	/**
	 * 清楚所有缓存
	 * 
	 * @return
	 */
	public static boolean clearAll() {
		if (!TextUtils.isEmpty(sysCachePath)) {
			File file = new File(sysCachePath);
			return removeDir(file);
		} else {
			NLog.e(tag, "sysCachePath is null");
		}
		return false;
	}

	public static boolean removeDir(File file) {
		File[] files = file.listFiles();
		for (File f : files) {
			if (f.isDirectory())// 递归调用
			{
				removeDir(f);
			} else {
				f.delete();
			}
		}
		// 一层目录下的内容都删除以后，删除掉这个文件夹
		return file.delete();
	}
}
