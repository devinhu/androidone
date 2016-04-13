/*
    ShengDao Android Client, DemoAction
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.service;

import android.content.Context;
import android.text.TextUtils;

import com.sd.core.common.CacheManager;
import com.sd.core.network.http.HttpException;
import com.sd.core.network.http.RequestParams;
import com.sd.one.common.Constants;
import com.sd.one.model.base.BaseResponse;
import com.sd.one.model.response.GetAreaResponse;

/**
 * [本地数据接口测试类]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-11-6
 * 
 **/
public class DemoAction extends BaseAction {

	/**
	 * 构造方法
	 * @param mContext
	 */
	public DemoAction(Context mContext) {
		super(mContext);
	}

	/**
	 * 获取版本信息接口
	 *
	 * @param pageNo
	 * @return
	 * @throws HttpException
	 */
	public BaseResponse getLastedVersion() throws HttpException {
		BaseResponse response = null;

		RequestParams params = getRequestParams();
		params.put("method", "fmms.getLastedVersion");
		params.put("data",  "{}");

		String result = httpManager.post(mContext, Constants.DOMAIN, getFinalParams(params), ContentType);
		if(!TextUtils.isEmpty(result)){
			response = jsonToBean(result, BaseResponse.class);
		}

		return response;
	}

	/**
     * 获取地区列表接口
     * 
     * @return
     * @throws HttpException
     */
    public GetAreaResponse getArea() throws HttpException {
    	GetAreaResponse response = null;
    	String url = getURL("admin/check/getArea");
    	
        RequestParams params = getRequestParams();
        params.put("access_token",  "2049f8982ee84c8e929f92691bc285b6");
        
        //处理缓存有效的情况
        String key = GetAreaResponse.class.getSimpleName();
        if(CacheManager.isInvalidCache(key, INVALID_TIME_30MIN)){
        	response = CacheManager.readObject(key);
        	if(response != null && response.isSucces()){
        		return response;
        	}
        }
        
        //处理网络获取的情况
        String result = httpManager.post(mContext, url, params);
        if(!TextUtils.isEmpty(result)){
            response = jsonToBean(result, GetAreaResponse.class);
            if(response != null && response.isSucces()){
            	CacheManager.writeObject(response, key);
            }
        }
        
        //最后没有数据，还是从缓存取
        if(response == null){
        	response = CacheManager.readObject(key);
        	if(response != null && response.isSucces()){
        		return response;
        	}
        }
    	
        return response;
    }
}
