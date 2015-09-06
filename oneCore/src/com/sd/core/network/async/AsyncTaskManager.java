/*
    ShengDao Android Client, AsyncTaskManager
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.core.network.async;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sd.core.network.http.HttpException;
import com.sd.core.utils.NLog;

import de.greenrobot1.event.EventBus;

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

	/**
	 * 构造方法
	 * @param context
	 */
	private AsyncTaskManager(Context context) {
		mContext = context;
		EventBus.getDefault().register(this);  
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
	public void request(final int requestCode, final boolean isCheckNetwork, final OnDataListener listener){
		if(requestCode > 0){
			EventBus.getDefault().post(new AsyncRequest(requestCode, isCheckNetwork, listener));
		}else{
			NLog.e(tag, "the error is requestCode < 0");
		}
	}
	
	/**
	 * 异步线程
	 * @param bean
	 */
	public void onEventAsync(AsyncRequest bean) {
		AsyncResult result = new AsyncResult(bean.getRequestCode(), bean.isCheckNetwork(), bean.getListener());
		try {
			if(!isNetworkConnected(mContext, bean.isCheckNetwork())){
				result.setState(HTTP_NULL_CODE);
			}else{
				Object object = bean.getListener().doInBackground(bean.getRequestCode());
				result.setResult(object);
				result.setState(REQUEST_SUCCESS_CODE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setResult(e);
			if(e instanceof HttpException){
				result.setState(HTTP_ERROR_CODE);
			}else{
				result.setState(REQUEST_ERROR_CODE);
			}
		}
		EventBus.getDefault().post(result);
	}  
	
	
	/**
	 * 在数据返回到UI线程中处理
	 * @param bean
	 */
	public void onEventMainThread(AsyncResult bean){
		switch(bean.getState()){
			case REQUEST_SUCCESS_CODE:
				bean.getListener().onSuccess(bean.getRequestCode(), bean.getResult());
				break;
				
			case REQUEST_ERROR_CODE:
			case HTTP_ERROR_CODE:
			case HTTP_NULL_CODE:
				bean.getListener().onFailure(bean.getRequestCode(), bean.getState(), bean.getResult());
				break;
		}
	}
	
	/**
     * 取消所有请求
     */
    public void cancelRequest() {
    	EventBus.getDefault().unregister(this);
    }
	
    /**
	 * 判断网络是否可用
	 * @param context
	 * @param isCheckNetwork 是否检查网络，true表示检查，false表示不检查
	 * @return
	 */
	public boolean isNetworkConnected(Context context, boolean isCheckNetwork) {
		if(isCheckNetwork){
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo ni = cm.getActiveNetworkInfo();
			return ni != null && ni.isConnectedOrConnecting();
		}else{
			return true;
		}
	}
}
