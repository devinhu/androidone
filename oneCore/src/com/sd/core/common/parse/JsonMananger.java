/*
    ShengDao Android Client, JsonMananger
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.core.common.parse;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.sd.core.network.http.HttpException;
import com.sd.core.utils.NLog;

/**
 * [JSON解析管理类]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-3-5
 * 
 **/
public class JsonMananger {

	private final String tag = JsonMananger.class.getSimpleName();
	
	/** JsonMananger实例对象 **/ 
	private static JsonMananger instance;

	/**
	 * 得到单例模式JsonMananger对象
	 * @return
	 */
	public static JsonMananger getInstance() {
		if (instance == null) {
			synchronized (JsonMananger.class) {
				if (instance == null) {
					instance = new JsonMananger();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 将json字符串转换成java对象
	 * @param json
	 * @param cls
	 * @return
	 * @throws HttpException 
	 */
	public <T> T jsonToBean(String json, Class<T> cls) throws HttpException {
        return JSON.parseObject(json, cls);
	}

	/**
	 * 将json字符串转换成java List对象
	 * @param json
	 * @param cls
	 * @return
	 * @throws HttpException 
	 */
	public <T> List<T> jsonToList(String json, Class<T> cls) throws HttpException {
        return JSON.parseArray(json, cls);
	}
	
	/**
	 * 将bean对象转化成json字符串
	 * @param obj
	 * @return
	 * @throws HttpException 
	 */
	public String beanToJson(Object obj) throws HttpException{
		String result = "";
		try {
			result = JSON.toJSONString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		NLog.e(tag, "beanToJson: " + result);
		return result;
	}
	
}
