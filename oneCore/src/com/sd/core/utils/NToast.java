/*
    ShengDao Android Client, NToast
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.core.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * [Toast工具类]
 * 
 * @author mashidong
 * @version 1.0
 * @date 2014-3-13
 * 
 **/
public class NToast {
	
	public static void shortToast(Context context, int resId) {
		showToast(context, context.getString(resId), Toast.LENGTH_SHORT);
	}
	
	public static void shortToast(Context context, String text) {
		showToast(context, text, Toast.LENGTH_SHORT);
	}

	public static void longToast(Context context, int resId) {
		showToast(context, context.getString(resId), Toast.LENGTH_LONG);
	}
	
	public static void longToast(Context context, String text) {
		showToast(context, text, Toast.LENGTH_LONG);
	}
	
	public static void showToast(Context context, String text, int duration) {
		Toast.makeText(context, text, duration).show();
	}
}
