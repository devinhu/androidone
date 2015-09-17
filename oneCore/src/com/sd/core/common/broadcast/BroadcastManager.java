/*
    ShengDao Android Client, BroadcastManager
    Copyright (c) 2015 ShengDao Tech Company Limited
*/

package com.sd.core.common.broadcast;

import com.sd.core.network.async.AsyncTaskManager;

import de.greenrobot1.event.EventBus;

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
	private OnBroadCastListener onBroadCastListener;
	
	/**
	 * 构造方法
	 * @param context
	 */
	private BroadcastManager() {
		EventBus.getDefault().register(this);  
	}
	
	/**
	 * [获取BroadcastManager实例，单例模式实现] 
	 * @param context
	 * @return
	 */
	public static BroadcastManager getInstance() {
		if (instance == null) {
			synchronized (AsyncTaskManager.class) {
				if (instance == null) {
					instance = new BroadcastManager();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 发送广播
	 * @param broadCode
	 */
	public void broadcast(int broadCode){
		BroadResult result = new BroadResult(broadCode, new Object());
		EventBus.getDefault().post(result);
	}
	
	/**
	 * 发送广播
	 * @param broadCode
	 * @param obj
	 */
	public void broadcast(int broadCode, Object obj){
		BroadResult result = new BroadResult(broadCode, obj);
		EventBus.getDefault().post(result);
	}
	
	/**
	 * 在数据返回到UI线程中处理
	 * @param bean
	 */
	public void onEventMainThread(BroadResult bean){
		if(onBroadCastListener != null){
			onBroadCastListener.onBroadCast(bean.getBroadCode(), bean.getResult());
		}
	}

	public OnBroadCastListener getOnBroadCastListener() {
		return onBroadCastListener;
	}

	public void setOnBroadCastListener(OnBroadCastListener onBroadCastListener) {
		this.onBroadCastListener = onBroadCastListener;
	}
	
	
}
