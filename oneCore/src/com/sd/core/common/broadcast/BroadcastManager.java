/*
    ShengDao Android Client, BroadcastManager
    Copyright (c) 2015 ShengDao Tech Company Limited
*/

package com.sd.core.common.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.sd.core.common.parse.JsonMananger;
import com.sd.core.network.async.AsyncTaskManager;
import com.sd.core.network.http.HttpException;

/**
 * [A brief description]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2015-9-17
 * 
 **/
public class BroadcastManager {

	private static BroadcastManager instance;
	private Context mContext;
	
	/**
	 * 构造方法
	 * @param context
	 */
	private BroadcastManager(Context context) {
		this.mContext = context;
	}
	
	/**
	 * [获取BroadcastManager实例，单例模式实现] 
	 * @param context
	 * @return
	 */
	public static BroadcastManager getInstance(Context context) {
		if (instance == null) {
			synchronized (AsyncTaskManager.class) {
				if (instance == null) {
					instance = new BroadcastManager(context);
				}
			}
		}
		return instance;
	}
	
	/**
	 * 添加
	 * @param broadCode
	 */
	public void addAction(String action, BroadcastReceiver receiver){
		try {
			IntentFilter filter = new IntentFilter();  
			filter.addAction(action); 
			mContext.unregisterReceiver(receiver);
			mContext.registerReceiver(receiver, filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送广播
	 * @param broadCode
	 * @param obj
	 */
	public void sendBroadcast(String action){
		sendBroadcast(action, "");
	}
	
	/**
	 * 发送广播
	 * @param broadCode
	 * @param obj
	 */
	public void sendBroadcast(String action, Object obj){
		try {
			Intent intent = new Intent();
			intent.setAction(action);
			intent.putExtra("result", JsonMananger.beanToJson(obj));
			mContext.sendBroadcast(intent);
		} catch (HttpException e) {
			e.printStackTrace();
		}
	}
}
