/*
    ShengDao Android Client, PreferencesManager
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.core.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.sd.core.common.parse.JsonMananger;

import java.io.File;

/**
 * [PreferencesManager管理类，提供get和put方法来重写SharedPreferences所提供的方法，更为实用和便捷]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-3-5
 * 
 **/
@SuppressLint({ "SdCardPath", "DefaultLocale" })
public class PreferencesManager {

	private final String tag = PreferencesManager.class.getSimpleName();

	private Context mContext;
	private SharedPreferences preferences;
	private String DATA_URL = "/data/data/";  
	private String SHARED_PREFS = "/shared_prefs";  
	
	private static String shareName = "SHARE_DATA";
	public static final String THEME = "Theme";
	public static final String LANG = "Lang";
	
	private static PreferencesManager instance;

	/**
	 * 构造方法
	 * 
	 * @param context
	 */
	private PreferencesManager(Context context) {
		this(context, shareName);
	}

	/**
	 * 构造方法
	 * 
	 * @param context
	 * @param shareName
	 */
	private PreferencesManager(Context context, String shareName) {
		mContext = context;
		preferences = context.getSharedPreferences(shareName,
				Context.MODE_PRIVATE);
	}

	/**
	 * 得到单例模式的PreferencesManager对象
	 * 
	 * @param context
	 *            上下文
	 * @return
	 */
	public static PreferencesManager getInstance(Context context) {
		return getInstance(context, shareName);
	}

	/**
	 * 得到单例模式的PreferencesManager对象
	 * 
	 * @param context
	 *            上下文
	 * @param shareName
	 *            文件名称
	 * @return
	 */
	public static PreferencesManager getInstance(Context context,
			String shareName) {
		if (instance == null) {
			synchronized (PreferencesManager.class) {
				if (instance == null) {
					instance = new PreferencesManager(context, shareName);
				}
			}
		}
		return instance;
	}

	public void put(String key, boolean value) {
		Editor edit = preferences.edit();
		if (edit != null) {
			edit.putBoolean(key, value);
			edit.commit();
		}
	}

	public void put(String key, String value) {
		Editor edit = preferences.edit();
		if (edit != null) {
			edit.putString(key, value);
			edit.commit();
		}
	}

	public void put(String key, int value) {
		Editor edit = preferences.edit();
		if (edit != null) {
			edit.putInt(key, value);
			edit.commit();
		}
	}

	public void put(String key, float value) {
		Editor edit = preferences.edit();
		if (edit != null) {
			edit.putFloat(key, value);
			edit.commit();
		}
	}

	public void put(String key, long value) {
		Editor edit = preferences.edit();
		if (edit != null) {
			edit.putLong(key, value);
			edit.commit();
		}
	}


	/**
	 * 直接存放对象，反射将根据对象的属性作为key，并将对应的值保存。
	 * 
	 * @param t
	 */
	@SuppressWarnings("rawtypes")
	public <T> void put(T t) {
		try {
			Editor edit = preferences.edit();
			String json = JsonMananger.beanToJson(t);
			edit.putString(t.getClass().getSimpleName(), json);
			edit.commit();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String get(String key) {
		return preferences.getString(key, "");
	}

	public String get(String key, String defValue) {
		return preferences.getString(key, defValue);
	}

	public boolean get(String key, boolean defValue) {
		return preferences.getBoolean(key, defValue);
	}

	public int get(String key, int defValue) {
		return preferences.getInt(key, defValue);
	}

	public float get(String key, float defValue) {
		return preferences.getFloat(key, defValue);
	}

	public long get(String key, long defValue) {
		return preferences.getLong(key, defValue);
	}


	/**
	 * 获取整个对象，跟put(T t)对应使用
	 * 
	 * @param cls
	 * @return
	 */
	public <T> Object get(Class<T> cls) {
		Object obj = null;
		try {
			String json = preferences.getString(cls.getClass().getSimpleName(), "");
			if(!TextUtils.isEmpty(json)){
				obj = JsonMananger.jsonToBean(json, cls);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public int getTheme(int defThemeId) {
		return instance.get(THEME, defThemeId);
	}

	public void setTheme(int themeId) {
		instance.put(THEME, themeId);
	}

	public String getLanguage(String defLang) {
		return instance.get(LANG, defLang);
	}

	public void setLang(String Language) {
		instance.put(LANG, Language);
	}

	public void clearAll() {
		try {
			String fileName = shareName+".xml";
			StringBuilder path = new StringBuilder(DATA_URL).append(mContext.getPackageName()).append(SHARED_PREFS);
			File file = new File(path.toString(), fileName);
			if (file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
