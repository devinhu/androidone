/*
    ShengDao Android Client, APPOnCrashListener
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.core.common;

import java.io.File;

import android.content.Context;

/**
 * [系统未捕获异常处理监听类]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-8-20
 * 
 **/
public interface APPOnCrashListener {

	/**
	 * 当系统Crash的时候处理方法，一般弹出友好提示框
	 * @param context
	 */
	public void onCrashDialog(Context context);
	
	/**
	 * 处理收集错误信息，一般发送于服务器
	 * @param crashReport 收集错误信息
	 */
	public void onCrashPost(String crashReport, File file);
}
