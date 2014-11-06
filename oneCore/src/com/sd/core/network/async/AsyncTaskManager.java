/*
    ShengDao Android Client, AsyncTaskManager
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.core.network.async;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.os.Build;

import com.sd.core.utils.NLog;

/**
 * [A brief description]
 *	
 * @author devin.hu
 * @version 1.0
 * @date 2013-9-24
 *
 **/
public class AsyncTaskManager {

	private final String tag = AsyncTaskManager.class.getSimpleName();
	
	/** 发生请求成功 **/
	public static final int REQUEST_SUCCESS_CODE = 200; 
	/** 发生请求失败 **/
	public static final int REQUEST_ERROR_CODE = -999; 
	/** 网络有问题 **/
	public static final int HTTP_ERROR_CODE = -200;
	/** 网络不可用 **/
	public static final int HTTP_NULL_CODE = -400;
	
	private Context mContext;
	private static AsyncTaskManager instance;
	private static ExecutorService mExecutorService;
	private static Map<Integer, WeakReference<BaseAsyncTask>> requestMap;

	/**
	 * 构造方法
	 * @param context
	 */
	private AsyncTaskManager(Context context) {
		mContext = context;
		mExecutorService = Executors.newFixedThreadPool(5);
		requestMap = new WeakHashMap<Integer, WeakReference<BaseAsyncTask>>();
	}
	
	/**
	 * [获取AsyncTaskManager实例，单例模式实现] 
	 * @param context
	 * @return
	 */
	public static AsyncTaskManager getInstance(Context context) {
		if (instance == null) {
			synchronized (AsyncTaskManager.class) {
				if (instance == null) {
					instance = new AsyncTaskManager(context);
				}
			}
		}
		return instance;
	}

	/**
	 * 发送请求（默认检查网络）
	 * 
	 * @param requestCode 请求码
	 * @param listener 回调监听
	 */
	public void request(int requestCode, OnDataListener listener){
		request(requestCode, true, listener);
	}
	
	/**
	 * 发送请求
	 * 
	 * @param requestCode 请求码
	 * @param isCheckNetwork 是否检查网络，true检查，false不检查
	 * @param listener 回调监听
	 */
	public void request(int requestCode, boolean isCheckNetwork, OnDataListener listener){
		DownLoad bean = new DownLoad(requestCode, isCheckNetwork, listener);
		if(requestCode > 0){
			BaseAsyncTask mAsynctask = new BaseAsyncTask(bean, mContext);
			//after version 2.3 added executeOnExecutor method. 
			//before 2.3 only run five asyntask, more than five must wait 
			if(Build.VERSION.SDK_INT >= 11){
				mAsynctask.executeOnExecutor(mExecutorService);
			}else{
				mAsynctask.execute();
			}
			requestMap.put(requestCode, new WeakReference<BaseAsyncTask>(mAsynctask));
		}else{
			NLog.e(tag, "the error is requestCode < 0");
		}
	}
	
	/**
	 * 取消请求
	 * @param requestCode 请求码
	 */
	public void cancelRequest(int requestCode) {
		WeakReference<BaseAsyncTask> requestTask = requestMap.get(requestCode);
    	if(requestTask != null) {
    		BaseAsyncTask request = requestTask.get();
            if(request != null) {
            	request.cancel(true);
            	request = null;
            }
        }
        requestMap.remove(requestCode);
	}
	
	
	/**
     * 取消所有请求
     */
    public void cancelRequest() {
        if(requestMap != null){
            Iterator<Entry<Integer, WeakReference<BaseAsyncTask>>> it = requestMap.entrySet().iterator();
            while(it.hasNext()){
                Entry<Integer, WeakReference<BaseAsyncTask>> entry = (Entry<Integer, WeakReference<BaseAsyncTask>>) it.next();
                Integer requestCode = entry.getKey();
                cancelRequest(requestCode);
            }
            requestMap.clear();
        }
    }
	
}
