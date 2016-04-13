/*
    ShengDao Android Client, BaseAction
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import android.content.Context;

import com.sd.core.common.CacheManager;
import com.sd.core.common.parse.JsonMananger;
import com.sd.core.common.parse.XmlMananger;
import com.sd.core.network.http.HttpException;
import com.sd.core.network.http.RequestParams;
import com.sd.core.network.http.SyncHttpClient;
import com.sd.core.utils.NLog;
import com.sd.one.common.Constants;
import com.sd.one.utils.StringUtils;

/**
 * [业务逻辑基础类，实现xml、json直接与JAVA对象互转，初始化网络请求类]
 * 
 * @author devin.hu
 * @version 1.0
 * @date 2013-9-29
 **/
public abstract class BaseAction {

    protected Context mContext;
	protected String ContentType = "text/html";
    protected SyncHttpClient httpManager;
    
    protected int pageSize = 20;
    
    /** 缓存有效期5分钟 **/
    protected final long INVALID_TIME = 5*60;
    /** 缓存有效期30分钟 **/
    protected final long INVALID_TIME_30MIN = 30*60;
    /** 缓存有效期1小时 **/
    protected final long INVALID_TIME_1HOURS = 60*60;
    /** 缓存有效期1天 **/
    protected final long INVALID_TIME_1DAY = 24*60*60;
    /** 缓存有效期1周 **/
    protected final long INVALID_TIME_1WEEK = 7*24*60*60;
    /** 缓存有效期1个月 **/
    protected final long INVALID_TIME_1MONTH = 30*24*60*60;
    
	
	/**
	 * 构造方法
	 * @param context
	 */
	public BaseAction(Context context) {
		this.mContext = context;
		this.httpManager = SyncHttpClient.getInstance(context);
      	CacheManager.setSysCachePath(context.getCacheDir().getPath());
	}
	
	/**
	 * XML字符串转JAVA对象方法
	 * @param xml
	 * @param cls
	 * @return
	 * @throws HttpException 
	 */
	public <T> T xmlToBean(String xml, Class<T> cls) throws HttpException{
		return XmlMananger.getInstance().xmlToBean(xml, cls);
	}
	
	/**
	 *  XML流转JAVA对象方法
	 * @param xml
	 * @param cls
	 * @return
	 * @throws HttpException 
	 */
	public <T> T xmlToBean(InputStream xml, Class<T> cls) throws HttpException{
		return XmlMananger.getInstance().xmlToBean(xml, cls);
	}
	
	/**
	 * JAVA对象转XML方法
	 * @param obj
	 * @return
	 * @throws HttpException 
	 */
	public String beanToXml(Object obj) throws HttpException{
		return XmlMananger.getInstance().beanToXml(obj);
	}
	
	/**
	 * JSON转JAVA对象方法
	 * @param json
	 * @param cls
	 * @return
	 * @throws HttpException 
	 */
	public <T> T jsonToBean(String json, Class<T> cls) throws HttpException {
       T obj = JsonMananger.jsonToBean(json, cls);
       return obj;
	}
	
	/**
	 * JSON转JAVA对象数组方法
	 * @param obj
	 * @param reference
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public <T> List<T> jsonToList(String json, Class<T> cls) throws HttpException, IOException {
		return JsonMananger.jsonToList(json, cls);
	}
	
	/**
	 * JAVA对象转JSON方法
	 * @param obj
	 * @return
	 * @throws HttpException 
	 */
	public String beanTojson(Object obj) throws HttpException {
       return JsonMananger.beanToJson(obj);
	}
	
	/**
	 * 获取处理后的RequestParams对象
	 * @param params
	 * @return
	 */
	public RequestParams getRequestParams(){
		RequestParams params = new RequestParams();
		//TODO 这里处理公共参数，如channelType，APPID等
		return params;
	}
	
	/**
	 * 获取处理后的RequestParams对象
	 * @param params
	 * @return
	 */
	public RequestParams getFinalParams(RequestParams params){
		//TODO 这里处理公共参数，签名等操作
		return getFinalParams(params, true);
	}
	
	/**
	 *  获取处理后的RequestParams对象
	 * @param params
	 * @param isSign 是否签名
	 * @return
	 */
	public RequestParams getFinalParams(RequestParams params, boolean isSign){
		//TODO 这里处理公共参数，如签名、加密等操作
		try {

			String paramsStr = params.getParamString(true);
			MessageDigest md = MessageDigest.getInstance("MD5");
			String firstMd = StringUtils.byte2hex(md.digest(paramsStr.getBytes("utf-8"))).toUpperCase();
			firstMd += Constants.TOKEN;
			String sign = StringUtils.byte2hex(md.digest(firstMd.getBytes("utf-8"))).toUpperCase();
			params.put("sign", sign);
			NLog.e("getSignParams", params.toString());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return params;
	}
	
    /**
     * 获取完整URL方法
     * @param url
     * @return
     */
    protected String getURL(String url) {
        return getURL(url, new String[]{});
    }

    /**
     * 获取完整URL方法
     * @param url
     * @param params
     * @return
     */
    protected String getURL(String url, String... params) {
        StringBuilder urlBilder = new StringBuilder(Constants.DOMAIN).append(url);
        if (params != null) {
            for (String param : params) {
                if (!urlBilder.toString().endsWith("/")) {
                	urlBilder.append("/");
                }
                urlBilder.append(param);
            }
        }
        return urlBilder.toString();
    }
}
