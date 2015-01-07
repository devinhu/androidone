/*
    SUNEEE Android Client, JavaScriptUtils
    Copyright (c) 2014 SUNEEE Tech Company Limited
*/

package com.sd.one.utils;

import org.apache.cordova.CordovaWebView;

import com.sd.core.common.parse.JsonMananger;
import com.sd.core.network.http.HttpException;
import com.sd.core.utils.NLog;

/**
 * [A brief description]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-8-7
 * 
 **/
public class JavaScriptUtils {

	private static final String tag = JavaScriptUtils.class.getSimpleName(); 
	
	/**
	 * Java发送js封装方法
	 * @param webview
	 * @param jsMethod
	 */
	public static void sendJavascript(CordovaWebView webview, String jsMethod){
		sendJavascript(webview, jsMethod, new String[]{});
	}
	
	/**
	 * Java发送js封装方法
	 * @param webview
	 * @param jsMethod
	 * @param params
	 */
	public static void sendJavascript(final CordovaWebView webview, String jsMethod, Object obj){
		try {
			final StringBuffer sb = new StringBuffer(jsMethod);
			sb.append("(");
			if(obj != null){
				sb.append(JsonMananger.getInstance().beanToJson(obj));
			}
			sb.append(")");
			NLog.e(tag, "sendJavascript : " + sb.toString());
			webview.postDelayed(new Runnable() {
				@Override
				public void run() {
					webview.sendJavascript(sb.toString());
				}
			}, 500);
		} catch (HttpException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Java发送js封装方法
	 * @param webview
	 * @param jsMethod
	 * @param params
	 */
	public static void sendJavascript(CordovaWebView webview, String jsMethod, String...params){
		StringBuffer sb = new StringBuffer(jsMethod);
		sb.append("(");
		if(params != null){
			for(int i=0; i<params.length ;i++){
				sb.append("'" + params[i] +"'");
				if(i < params.length-1){
					sb.append(",");
				}
			}
		}
		sb.append(")");
		NLog.e(tag, "sendJavascript : " + sb.toString());
		webview.sendJavascript(sb.toString());
	}
}
