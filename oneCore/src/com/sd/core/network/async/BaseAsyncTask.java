/*
    ShengDao Android Client, BaseAsyncTask
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.core.network.async;

import org.apache.http.HttpException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

/**
 * [异步处理类]
 * 
 * @author devin.hu
 * @version 1.0
 * @date 2014-2-27
 * 
 **/
public class BaseAsyncTask extends AsyncTask<Object, Integer, Object> {

	private DownLoad bean;
	private Context mContext;
	
	/**
	 * 构造方法
	 * @param bean
	 * @param context
	 */
	public BaseAsyncTask(DownLoad bean, Context context) {
        this.bean = bean;
        this.mContext = context;
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
	
	@Override
	protected Object doInBackground(Object... params) {
		try {
			if(bean.getListener() == null){
				throw new HttpException("============listener is not null============");
			}
			
			//判断网络是否可用
			if(isNetworkConnected(mContext, bean.isCheckNetwork())){
				Object result = bean.getListener().doInBackground(bean.getRequestCode());
				bean.setState(AsyncTaskManager.REQUEST_SUCCESS_CODE);
				bean.setResult(result);
			}else{
				bean.setState(AsyncTaskManager.HTTP_NULL_CODE);
			}
		}catch (Exception e) {
			e.printStackTrace();
			if(e instanceof HttpException){
				bean.setState(AsyncTaskManager.HTTP_ERROR_CODE);
			}else{
				bean.setState(AsyncTaskManager.REQUEST_ERROR_CODE);
			}
			bean.setResult(e);
			return bean;
		} 
		
		return bean;
	}

	@Override
	protected void onPostExecute(Object result) {
		DownLoad bean = (DownLoad)result;
		switch(bean.getState()){
			case AsyncTaskManager.REQUEST_SUCCESS_CODE:
				bean.getListener().onSuccess(bean.getRequestCode(), bean.getResult());
				break;
				
			case AsyncTaskManager.REQUEST_ERROR_CODE:
			case AsyncTaskManager.HTTP_ERROR_CODE:
			case AsyncTaskManager.HTTP_NULL_CODE:
				bean.getListener().onFailure(bean.getRequestCode(), bean.getState(), bean.getResult());
				break;
		}
	}

}
