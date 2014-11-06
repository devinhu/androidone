/*
    ShengDao Android Client, OnDataListener
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.core.network.async;

import com.sd.core.network.http.HttpException;

/**
 * [异步数据处理回调接口类]
 *	
 * @author devin.hu
 * @version 1.0
 * @date 2013-9-24
 *
 **/
public interface OnDataListener {

	/**
	 * 异步耗时方法
	 * @param requsetCode 请求码
	 * @return
	 * @throws HttpException
	 */
	public Object doInBackground(int requsetCode) throws HttpException;
	
	/**
	 * 成功方法（可直接更新UI）
	 * @param requestCode 请求码
	 * @param result 返回结果
	 */
	public void onSuccess(int requestCode, Object result);
	
	/**
	 * 失败方法（可直接更新UI）
	 * @param requestCode 请求码
	 * @param state 返回状态
	 * @param result 返回结果
	 */
	public void onFailure(int requestCode, int state, Object result);
}
